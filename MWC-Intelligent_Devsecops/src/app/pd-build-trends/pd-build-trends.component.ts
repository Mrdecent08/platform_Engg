import { Component, OnInit, Input, OnChanges} from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-pd-build-trends',
  templateUrl: './pd-build-trends.component.html',
  styleUrls: ['./pd-build-trends.component.scss']
})
export class PdBuildTrendsComponent implements OnInit, OnChanges {

  expandDropDown;
  buildTrendsData;
  chartData = [];
  customColors = [];
  @Input() projectName;
  clearInterval;
  
  constructor(
    private devSecOpsService: DevSecOpsService,
    private mockDevSecOpsService : MockDevSecOpsService
  ) { }

  ngOnInit() {
    this.getData();
    this.clearInterval = setInterval(() => {
      this.getData(); 
     }, 5000);
  }

  ngOnChanges(changes) {
    if(!changes['projectName'].firstChange) {
      this.getData();
    }
  }
  getData() {
    let obj = {"name": this.projectName};
    this.devSecOpsService.getBuildTrendsData(obj).subscribe((response)=> {    
      this.buildTrendsData = response;
      this.prepareChartData(response);
    });
    
    // Uncomment this to get data from MockService
   /*  this.buildTrendsData = this.mockDevSecOpsService.getBuildTrendsData(this.projectName);
    this.prepareChartData(this.buildTrendsData); */
  }

  prepareChartData(resp) {
    this.chartData = [];
    this.customColors = [];
    for(let i=0; i < resp.length; i++) {
      let chartDataObj = {};
      let customColorObj = {};
      chartDataObj["name"] = resp[i].build_no;
      chartDataObj["value"] = resp[i].percentage_success_stages;
      this.chartData.push(chartDataObj);

      customColorObj["name"] = resp[i].build_no;
      customColorObj["value"] = (resp[i].build_status == "SUCCESS") ? 'green' : 'red';
      this.customColors.push(customColorObj);
    }
  }

  colorScheme = {
    domain: ['#5AA454', '#A10A28', '#C7B42C', '#AAAAAA']
  };

  openDropdown() {
    this.expandDropDown = !this.expandDropDown;
  }
  
}
