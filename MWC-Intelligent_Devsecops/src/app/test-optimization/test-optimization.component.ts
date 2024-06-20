import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-test-optimization',
  templateUrl: './test-optimization.component.html',
  styleUrls: ['./test-optimization.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class TestOptimizationComponent implements OnInit {

  testOptimizedData ;
  chartData = [];
  error = false;
  showXAxis: boolean = true;
  showYAxis: boolean = true;
  gradient: boolean = true;
  showLegend: boolean = true;
  showXAxisLabel: boolean = true;
  xAxisLabel: string = 'Projects';
  showYAxisLabel: boolean = true;
  yAxisLabel: string = 'Total Test Cases';
  legendTitle: string = '';

  constructor(
    private devSecOpsService : DevSecOpsService,
    private mockDevSecOpsService : MockDevSecOpsService
    ) { }

  ngOnInit() {
    this.devSecOpsService.getTestOptimizedData().subscribe((response)=>{      
      this.testOptimizedData = response;
      this.prepareChartData(response);
    }, (error)=> {
      this.error = true;
    });

    // Uncomment this to get data from MockService
    /* this.testOptimizedData = this.mockDevSecOpsService.getTestOptimizedData();
    this.prepareChartData(this.testOptimizedData); */
  }
  
  prepareChartData(resp) {

    for(let i=0; i < resp.length; i++) {
      let chartDataObj = {};
      chartDataObj["name"] = resp[i].pname;
      chartDataObj["series"] = [
        {
          "name": "New",
          "value": resp[i].total
        },
        {
          "name": "Impacted",
          "value": resp[i].tobeExecuted
        },
        {
          "name": "Not Impacted",
          "value": resp[i].canbeSkipped
        }
      ];
      this.chartData.push(chartDataObj);
    }
  }

  colorScheme = {
    domain: ['skyblue', 'black', 'green']
  };

}
