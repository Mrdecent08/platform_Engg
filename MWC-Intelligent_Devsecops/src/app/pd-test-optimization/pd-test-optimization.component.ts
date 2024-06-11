import { Component, OnInit , Input, OnChanges, ViewEncapsulation} from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-pd-test-optimization',
  templateUrl: './pd-test-optimization.component.html',
  styleUrls: ['./pd-test-optimization.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class PdTestOptimizationComponent implements OnInit {

  projectTestOptimizedData = [];
  @Input() projectName;

  displayNameOfSprint = {
    "sprint1": "Sprint 1",
    "sprint2": "Sprint 2",
    "sprint3": "Sprint 3",
    "sprint4": "Sprint 4",
    "sprint5": "Sprint 5",
    "sprint6": "Sprint 6",
  };
  error = false;
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
    this.devSecOpsService.getProjectTestOptimization(obj).subscribe((response)=> {  
      this.prepareChartData(response);
    }, (error)=> {
      this.error = true;
    });

    // Uncomment this to get data from MockService
     /* let response = this.mockDevSecOpsService.getProjectTestOptimization();
     this.prepareChartData(response); */
    }
  
    prepareChartData(data) {
      this.projectTestOptimizedData = [];
      for(let i=0; i < data.length; i++) {
        let chartDataObj = {};
        let sprintName = (this.displayNameKeys.indexOf(data[i].sprint) > -1) ? this.displayNameOfSprint[data[i].sprint] : data[i].sprint;
        chartDataObj["name"] = sprintName;
        chartDataObj["series"] = [
          {
            "name": "Unimpacted Test Cases",
            "value": +data[i].unimpacted
          },
          {
            "name": "Intelligently Identified Test Cases",
            "value": +data[i].identified
          },
          
          {
            "name": "Defects",
            "value": data[i].defects
          }
        ];
        
        this.projectTestOptimizedData.push(chartDataObj);
      }
    }
  
    colorScheme = {
      domain: ['#B9B9B9', '#45BCE0',  '#FFB265']
    };

}
