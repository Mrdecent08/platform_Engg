import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';


@Component({
  selector: 'app-project-live',
  templateUrl: './project-live.component.html',
  styleUrls: ['./project-live.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class ProjectLiveComponent implements OnInit {

  projectsData = [
    { 
      "projectName": "SNMP_Java",
      "liveOn": "31/12/2019"  
    },
    { "projectName": "Sample_Java",
       "liveOn": "26/12/2019"    
    },
    { "projectName": "ODL_OXYGEN_JAVA",
      "liveOn": "15/01/2020"      
    },
  ]

  liveData;
  sprintData;
  widthx;
  showTooltip;
  error = false;
  constructor(
    private devSecOpsService: DevSecOpsService,
    private mockDevSecOpsService : MockDevSecOpsService
  ) { }

  ngOnInit() {
    this.devSecOpsService.getProjectsLiveData().subscribe((response)=>{
      this.liveData = response; 
      this.sprintData=this.liveData[2].sprints;
      if(this.sprintData.length == 0) {
        this.widthx = 60/6;
      } else {
        this.widthx = 60/this.sprintData.length;
      }
    }, (error)=> {
      this.error = true;
    });
   /*  let response = this.mockDevSecOpsService.getProjectsLiveData();
    this.liveData = response;
    this.sprintData=this.liveData[0].sprints;
    if(this.sprintData.length == 0) {
      this.widthx = 60/6;
    } else {
      this.widthx = 60/this.sprintData.length;
    } */
  }

  displayTooltip(sprint, event) {
    this.showTooltip = true;
  }

}
