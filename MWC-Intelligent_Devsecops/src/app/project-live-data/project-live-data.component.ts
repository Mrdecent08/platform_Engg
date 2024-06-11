import { Component, OnInit } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-project-live-data',
  templateUrl: './project-live-data.component.html',
  styleUrls: ['./project-live-data.component.scss']
})
export class ProjectLiveDataComponent implements OnInit {

  constructor(
    private devSecOpsService: DevSecOpsService,
    private mockDevSecOpsService : MockDevSecOpsService
  ) { }
  
  liveData;
  sprintData;
  widthx;
  error = false;
  ngOnInit() {
    this.devSecOpsService.getProjectsLiveData().subscribe((response)=>{
      this.liveData = response; 
      this.sprintData=this.liveData[1].sprints;
      if(this.sprintData.length == 0) {
        this.widthx = 60/6;
      } else {
        this.widthx = 60/this.sprintData.length;
      }
    }, (error)=> {
      this.error = true;
    });
    /* let response = this.mockDevSecOpsService.getProjectsLiveData();
    this.liveData = response;
    this.sprintData=this.liveData[0].sprints;
    if(this.sprintData.length == 0) {
      this.widthx = 60/6;
    } else {
      this.widthx = 60/this.sprintData.length;
    } */
  }
   

}
