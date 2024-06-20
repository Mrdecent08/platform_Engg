import { Component, OnInit, Input, OnChanges, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-pd-current-build',
  templateUrl: './pd-current-build.component.html',
  styleUrls: ['./pd-current-build.component.scss']
})
export class PdCurrentBuildComponent implements OnInit, OnChanges, OnDestroy {

  hoveredPrj;
  tooltiptext = 'tooltiptext';
  buildStages ;
    widthx ;
  buildHistory = [];
  buildHistoryResponse = [];
  //buildHistory;
  @Input() projectName;  
  clearInterval;
  projects ;
  error = false;
  constructor(
    private router: Router, 
    private devSecOpsService : DevSecOpsService,
    private mockDevSecOpsService : MockDevSecOpsService
  ) { }

  ngOnInit() {
    this.getData();
    this.clearInterval = setInterval(() => {
      this.getData(); 
     }, 5000);
  }

  ngOnChanges(changes) {
    if(!changes['projectName'].firstChange) {
      this.getData();
    }
  }
  getData() {
    this.devSecOpsService.getBuildStages().subscribe((response)=>{      
      this.buildStages = response;
      if(this.buildStages.length == 0) {
        this.widthx = 87/10;
      } else {
        this.widthx = 87/this.buildStages.length;
      }
    }); 
    let obj = {"name": this.projectName};
    this.devSecOpsService.getBuildHistoryOfProject(obj).subscribe((response) => {
      this.buildHistory = [];
      this.error = false;
      for(let index in response) {
        if(response[index]["status"] == 'SUCCESS' || response[index]["status"] == 'FAILED' || response[index]["status"] == 'IN_PROGRESS') {
          this.buildHistory.push(response[index]);
        }
      }
    }, (error)=> {
      this.error = true;
      this.buildHistory = [];
    });

   // Uncomment this to get data from MockService
    /* this.buildStages = this.mockDevSecOpsService.getBuildStages();
    if(this.buildStages.length == 0) {
      this.widthx = 75/10;
    } else {
      this.widthx = 75/this.buildStages.length;
    }
    this.buildHistory = [];
    this.buildHistoryResponse = this.mockDevSecOpsService.getBuildHistoryOfProject(this.projectName);
    for(let index in this.buildHistoryResponse) {
      if(this.buildHistoryResponse[index]["status"] == 'SUCCESS' || this.buildHistoryResponse[index]["status"] == 'FAILED') {
        this.buildHistory.push(this.buildHistoryResponse[index]);
      }
    } */
  }

  goToProjectDetails(x) {
    this.devSecOpsService.changeisProjectDetailsPage(true);
    this.router.navigate(['/project/'+x]);
  }

  showDetailsOfPrj(prj, index) {
    this.hoveredPrj = prj;
    this.tooltiptext = 'tooltiptext'+ index;
  }
  removeDetails() {
    this.hoveredPrj = '';
  }
  getClass() {
    return this.tooltiptext;
  }

  ngOnDestroy() {
    if (this.clearInterval) {
      clearInterval(this.clearInterval);
    }
  }
}
