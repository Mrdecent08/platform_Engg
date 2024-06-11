import { Component, OnInit , Input, OnChanges} from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-pd-sec-vul-summary-graph',
  templateUrl: './pd-sec-vul-summary-graph.component.html',
  styleUrls: ['./pd-sec-vul-summary-graph.component.scss']
})
export class PdSecVulSummaryGraphComponent implements OnInit {
  secVulnerabilitySummaryGraph = [];
  secVulnerData;
  @Input() projectName;
  error = false;
  displayNameOfSprint = {
                          "sprint1": "Sprint 1",
                          "sprint2": "Sprint 2",
                          "sprint3": "Sprint 3",
                          "sprint4": "Sprint 4",
                          "sprint5": "Sprint 5",
                          "sprint6": "Sprint 6",
                        };
  displayNameKeys = Object.keys(this.displayNameOfSprint);

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
    this.devSecOpsService.getProjectSecurityVulnerabilitySummary(obj).subscribe((response)=> {
      this.prepareChartData(response);
    }, (error)=> {
      this.error = true;
    });

    // Uncomment this to get data from MockService
   /*  let response = this.mockDevSecOpsService.getProjectSecurityVulnerabilitySummary(); 
    this.prepareChartData(response); */
  }
  prepareChartData(data) {
    this.secVulnerabilitySummaryGraph = [];
    for(let i=0; i < data.length; i++) {
      let chartDataObj = {};
      chartDataObj["name"] = data[i].type;
      chartDataObj["series"] = [];
      let beforeSprints = data[i].sprints;
      let sprints = beforeSprints.sort(function(a,b) {
        const bandA = a["sprintName"].toUpperCase();
        const bandB = b["sprintName"].toUpperCase();
        if (bandA > bandB) {
          return 1;
        } else if (bandA < bandB) {
          return -1;
        }
      } );
      for(let j=0; j < sprints.length; j++) {
        let reqObj = {};
        let res = sprints[j];
        let sprintName = (this.displayNameKeys.indexOf(res["sprintName"]) > -1) ? this.displayNameOfSprint[res["sprintName"]] : res["sprintName"];
        reqObj["name"] = sprintName;
        reqObj["value"] = res["count"];

        chartDataObj["series"].push(reqObj);
      }
      
      this.secVulnerabilitySummaryGraph.push(chartDataObj);
    }
  }

  colorScheme = {
    domain: ['#F06293', '#FFB265', '#939FED']
  };
}
