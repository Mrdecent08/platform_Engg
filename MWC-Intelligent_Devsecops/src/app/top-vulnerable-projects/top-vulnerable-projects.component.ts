import { Component, OnInit } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-top-vulnerable-projects',
  templateUrl: './top-vulnerable-projects.component.html',
  styleUrls: ['./top-vulnerable-projects.component.scss']
})
export class TopVulnerableProjectsComponent implements OnInit {

  date = new Date();

  view: any[] = [];

  // options
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  gradient: boolean = false;
  showLegend: boolean = true;
  showXAxisLabel: boolean = true;
  yAxisLabel: string = 'Projects';
  showYAxisLabel: boolean = true;
  xAxisLabel: string = 'Vulnerabilites';

  colorScheme = {
    domain: [ '#f94667']
  };
  
  showGraph;
  projects;
  vulnerablePrjs = [];
  vulnerablePrjsChartData = [];
  xAxisTicks= [];
  constructor(private devSecOpsService: DevSecOpsService, private mockDevSecOpsService: MockDevSecOpsService) { }

  ngOnInit() {

    this.devSecOpsService.getTop10PrjByVulnerabilites().subscribe((response)=>{      
      this.projects = response;
      this.prepareChartData();     
    });

    // Uncomment this to get data from MockService
    /* this.projects = this.mockDevSecOpsService.getTop10PrjByVulnerabilites();
    this.prepareChartData(); */
  }

  prepareChartData() {

    for(let i=0; i < this.projects.length; i++) {
      let reqObj = {};
      reqObj = { 
                "name" : this.projects[i]["project_name"],
                "value" : +this.projects[i]["vulnerabilities"]
              };
      this.vulnerablePrjs.push(reqObj);       
    }

    this.showGraph = true;
    //Sorting data in Descending Order
    this.vulnerablePrjs.sort(function(a,b){
      return b.value - a.value;
    });
    //Showing only 10 Projects Data
    this.vulnerablePrjsChartData = this.vulnerablePrjs.slice(0,5);
  }

}
