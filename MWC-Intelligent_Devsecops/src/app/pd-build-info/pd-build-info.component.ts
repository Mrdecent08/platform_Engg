import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-pd-build-info',
  templateUrl: './pd-build-info.component.html',
  styleUrls: ['./pd-build-info.component.scss']
})
export class PdBuildInfoComponent implements OnInit, OnChanges {

  @Input() projectName;

  projectDetailObj = [ 
    { "displayName":"Velocity",
      "key": "velocity"
    },
    {  "displayName":"Project Owner",
       "key": "project_owner"
    },
    {  "displayName":"Release Date",
       "key": "release_date"},
    {  "displayName":"Previous Release Date",
       "key": "previous_release_date"
    }
  ];
  currentVersionKeys = [
    { "displayName":"Branch",
      "key": "branch",
      "isDate": false
    },
    /* {  "displayName":"Commit",
      "key": "commit",
      "isDate": false
    }, */
    {  "displayName":"Time",
      "key": "time",
      "isDate": true
    },
    {  "displayName":"Started By",
      "key": "startedby",
      "isDate": false
    }
  ];


  constructor(
    private devSecOpsService: DevSecOpsService,
    private mockDevSecOpsService : MockDevSecOpsService
  ) { }

  projectDetails;
  buildData;
  ngOnInit() {
    this.getData();
  }

  ngOnChanges(changes) {
    if(!changes['projectName'].firstChange) {
      this.getData();
    }
  }

  getData() {
    let obj = {"name" : this.projectName};
    this.devSecOpsService.getIndividualPrjBuildInfo(obj).subscribe((response)=>{      
      this.buildData = response;
    }); 
    this.devSecOpsService.getIndividualPrjBuildDetails(obj).subscribe((response)=>{      
      this.projectDetails = response;
    });

    // Uncomment this to get data from MockService
    /* this.buildData = this.mockDevSecOpsService.getIndividualPrjBuildInfo(this.projectName);
    this.projectDetails = this.mockDevSecOpsService.getIndividualPrjBuildDetails(this.projectName); */

  }

}
