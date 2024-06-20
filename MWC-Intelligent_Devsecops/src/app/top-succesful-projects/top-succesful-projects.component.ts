import { Component, OnInit } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-top-succesful-projects',
  templateUrl: './top-succesful-projects.component.html',
  styleUrls: ['./top-succesful-projects.component.scss']
})
export class TopSuccesfulProjectsComponent implements OnInit {

  lastBuildData;
  lastBuildDataR;
  date = new Date();

  constructor(
    private devSecOpsService : DevSecOpsService,
    private mockDevSecOpsService : MockDevSecOpsService
  ) { }

  ngOnInit() {
    this.devSecOpsService.getLastBuildProjects().subscribe((response)=>{      
      this.lastBuildDataR = response;
      this.lastBuildData = this.lastBuildDataR.filter((val) => {
        return (val.timestamp_in_millis !== null)
      });
      this.lastBuildData.sort(function(a,b){
        return b.timestamp_in_millis - a.timestamp_in_millis;
      });
    });

   // Uncomment this to get data from MockService
    /* this.lastBuildData = this.mockDevSecOpsService.getLastBuildProjects();
    this.lastBuildData.sort(function(a,b){
      return b.timestamp_in_millis - a.timestamp_in_millis;
    }); */
  }
  
}
