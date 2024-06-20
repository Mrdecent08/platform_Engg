import { Component, OnInit , Input, OnChanges, ViewEncapsulation} from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-pd-deployment-statistics',
  templateUrl: './pd-deployment-statistics.component.html',
  styleUrls: ['./pd-deployment-statistics.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class PdDeploymentStatisticsComponent implements OnInit {

  deploymentStatisticsGraphData = [];
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
    this.devSecOpsService.getProjectDeploymentStatistics(obj).subscribe((response)=> {  
      this.prepareChartData(response);
    }, (error)=> {
      this.error = true;
    });

    // Uncomment this to get data from MockService
    /* let response = this.mockDevSecOpsService.getProjectDeploymentStatistics();
    this.prepareChartData(response); */
  }

  prepareChartData(data) {
    this.deploymentStatisticsGraphData = [];
    for(let i=0; i < data.length; i++) {
      let chartDataObj = {};
      let sprintName = (this.displayNameKeys.indexOf(data[i].sprint) > -1) ? this.displayNameOfSprint[data[i].sprint] : data[i].sprint;
      chartDataObj["name"] = sprintName;
      chartDataObj["series"] = [
        {
          "name": "Successful",
          "value": data[i].successful
        },
        {
          "name": "Failed",
          "value": data[i].failed
        }
      ];
      this.deploymentStatisticsGraphData.push(chartDataObj);
    }
  }

  colorScheme = {
    domain: ['#8AE3A6', '#FF84AE']
  };

}
