import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-pd-vulnerabilites',
  templateUrl: './pd-vulnerabilites.component.html',
  styleUrls: ['./pd-vulnerabilites.component.scss']
})
export class PdVulnerabilitesComponent implements OnInit, OnChanges {

  vulnerabilitiesData;
  @Input() projectName; 

  projects = [
    { name: "Project 1", releaseDate: "28/12/2019"},
    { name: "Project 2", releaseDate: "28/12/2019"},
    { name: "Project 3", releaseDate: "28/12/2019"},
    { name: "Project 4", releaseDate: "28/12/2019"},
    { name: "Project 5", releaseDate: "28/12/2019"},
    { name: "Project 6", releaseDate: "28/12/2019"},
  ];

  constructor(
    private devSecOpsService : DevSecOpsService,
    private mockDevSecOpsService : MockDevSecOpsService
  ) { }

  ngOnInit() {
    this.getData();
  }

  ngOnChanges(changes) {
    if(!changes['projectName'].firstChange) {
      this.getData();
    }
  }
  getData() {
    let obj = {"name": this.projectName};
    this.devSecOpsService.getProjectVulnerability(obj).subscribe((response)=> {  
      this.vulnerabilitiesData = response;
    });

    // Uncomment this to get data from MockService
    /* this.vulnerabilitiesData = this.mockDevSecOpsService.getProjectVulnerability(this.projectName); */
  }
}
