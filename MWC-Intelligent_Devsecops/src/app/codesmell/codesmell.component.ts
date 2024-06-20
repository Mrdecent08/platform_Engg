import { Component, OnInit } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';
import { StarRatingComponent } from 'ng-starrating';
import { Router } from '@angular/router';

@Component({
  selector: 'app-codesmell',
  templateUrl: './codesmell.component.html',
  styleUrls: ['./codesmell.component.scss']
})
export class CodesmellComponent implements OnInit {

  cardObjects = []; /* = [
    { 
      "type": "Technical Debts",
      "count" : "600 Days",
      "key": "technicalDebt"
    },
    { 
      "type": "Code Smell",
      "count" : 2700,
      "key": "codeSmell"
    },
    { 
      "type": "Defects",
      "count" : 90,
      "key": "defects"
    },
  ] */;

  top3CritcalProject = {}/* [
    { "projectName": "SNMP_Java",
      "technicalDebt": "100 Days",
      "codeSmell": 200,
      "defects": 30   
    },
    { "projectName": "Sample_Java",
      "technicalDebt": "200 Days",
      "codeSmell": 1500,
      "defects": 40     
    },
    { "projectName": "ODL_OXYGEN_JAVA",
      "technicalDebt": "300 Days",
      "codeSmell": 1000,
      "defects": 20      
    },
  ] */
  isLoading = true;
  error = false;
  selectedCard = 'code_smell';
  codeSmellDetails = {};
  totalCount = {};
  displayName = { "technical_debts": "Technical Debts",
                  "code_smell": "Code Smell",
                  "bugs": "Defects"
                };

  constructor(
    private devSecOpsService: DevSecOpsService, 
    private mockDevSecOpsService: MockDevSecOpsService,
    private router: Router
  ) { }

  ngOnInit() {
    this.devSecOpsService.getCodeSmellDetails().subscribe((response)=>{      
      this.prepareData(response);
    }, (error)=> {
      this.isLoading = false
      this.error = true;
    });
    /* let res = this.mockDevSecOpsService.getCodeSmellDetails();
    this.prepareData(res); */
  }

  prepareData(response) {
    this.cardObjects = [];
    this.top3CritcalProject = {};
    let cardObj = response['alldefects'];
    for(let card of  cardObj) {
      this.totalCount[card.type] = card.total;
    }
    this.cardObjects = cardObj;
    this.top3CritcalProject = response['defectsdata'];
    for(let arr in this.top3CritcalProject) {
      let proj = this.top3CritcalProject[arr]
      proj.sort(function(a,b) {
        return b['count'] - a['count'];
      })
    }
    this.isLoading = false;
  }

  setTheTab(cardType) {
    this.selectedCard = cardType;
  }

  goToProjectDetailsPage(project) {
    localStorage.setItem('projectName', project.project);
    this.devSecOpsService.changeisProjectDetailsPage(true);
    this.router.navigate(['/project/'+project.project]);
  }

}
