import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';
import { Router } from '@angular/router';
@Component({
  selector: 'app-upcoming-releases',
  templateUrl: './upcoming-releases.component.html',
  styleUrls: ['./upcoming-releases.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class UpcomingReleasesComponent implements OnInit {

  upComingRleaseProjectsTotal = [];
  upComingRleaseProjects;
  selectedProjectName ;
  currentStartIndex = 0;

  defectsColorScheme = {
    domain: [ 'skyblue', '#79C450' ]
  };
  codeSmellColorScheme = {
    domain: [ "#F06293", "#FFB265", "#939FED", "#17a2b8" ]
  };
  /* colorScheme = {
    domain: ['#f94667']
  }; */
  testOptimizedColorScheme = {
    domain: [/* "#FFA460", */"#77F4FF", "#9C9EA2"]
  };
  customColors = [];
  customCol = {
    "MAJOR": "#FFB265",
    "MINOR": "#939FED",
    "CRITICAL": "#F06293"
  };
  displayNameSecVulnerabilities = {
    "InsufficientLogging": "Insufficient Logging",
    "InjectionFlaws": "Injection Flaws",
    "BrokenAccessControl": "Broken Access Control",
    "DataExposure": "Sensitive Data Exposure",
    "knownvulnerability": "Known Vulnerability",
    "InsecureDeserialization": "Insecure Deserialization",
    "AuthenticationProblems": "Authentication Problems",
    "Misconfiguration": "Misconfiguration"
  };
  projects;
  vulnerablePrjs = [];
  vulnerableTypes = [];
  secVulnerabilityView = [];
  secVulnerableBarPadding;
  secVulnerableXAXISTICKS = [];
  securityVulnerabilityTypes;
  defectCodeSmellData;
  testoptimizationData;
  bugsData;
  codeSmellValue;
  testOptimizedChartData;
  isLoading = true;
  noPieChart = false;
  noSecVulChart = false;
  noCodeSmellChart = false;
  slectedIndexInCardsRow = 0;
  secVulGraphContainerHeight = 400;
  error = false;

  constructor(
    private devSecOpsService : DevSecOpsService,
    private mockDevSecOpsService: MockDevSecOpsService,
    private router: Router) { }

  ngOnInit() {
    this.devSecOpsService.getUpcomingReleases().subscribe((response)=>{      
      this.upComingRleaseProjectsTotal = response["allprojects"];
    this.upComingRleaseProjects = this.upComingRleaseProjectsTotal.slice(0,3);
    this.selectedProjectName = this.upComingRleaseProjects[0].name;
    this.isLoading = false;
    this.securityVulnerabilityTypes= response["securityvulnerability"];
    this.defectCodeSmellData = response["defects"];
    this.testoptimizationData = response["testoptimization"];
    this.prepareAllchartsData();
    }, (error)=> {
      this.isLoading = false;
      this.error = true;
    });

    /* let response = this.mockDevSecOpsService.getUpcomingReleases();
    this.upComingRleaseProjectsTotal = response["allprojects"];
    this.upComingRleaseProjects = this.upComingRleaseProjectsTotal.slice(0,3);
    this.selectedProjectName = this.upComingRleaseProjects[0].name;
    this.isLoading = false;
    this.securityVulnerabilityTypes= response["securityvulnerability"];
    this.defectCodeSmellData = response["defects"];
    this.testoptimizationData = response["testoptimization"];
    this.prepareAllchartsData(); */

  }

  changeProjects(next_previous) {
    if( next_previous == 'next') {
      this.currentStartIndex = this.currentStartIndex + 1;
    } else if (next_previous == 'previous') {
      this.currentStartIndex = this.currentStartIndex - 1;
    }
    this.upComingRleaseProjects = this.upComingRleaseProjectsTotal.slice(this.currentStartIndex,this.currentStartIndex+3);
    this.selectedProjectName = this.upComingRleaseProjects[this.slectedIndexInCardsRow].name;
    this.prepareAllchartsData();
  }
  prepareAllchartsData() {
    this.prepareChartData(this.securityVulnerabilityTypes[this.selectedProjectName]);
    this.prepareDefectCSData(this.defectCodeSmellData[this.selectedProjectName]);
    this.prepareTestOptimizedData(this.testoptimizationData[this.selectedProjectName]);
  }
  setTheCard(project, index) {
    this.selectedProjectName = project.name;
    this.slectedIndexInCardsRow = index;
    this.prepareAllchartsData();
  }

  prepareChartData(data) {
    this.vulnerableTypes = [];

    for(let i=0; i < data.length; i++) {
      let reqObj = {};
      let customColorObj = {};
      let secVulDisplayName = (this.displayNameSecVulnerabilities[data[i]["type"]]) ? this.displayNameSecVulnerabilities[data[i]["type"]] : data[i]["type"];
      reqObj = { 
                "name" : secVulDisplayName,
                "value" : +data[i]["count"]
              };
      this.vulnerableTypes.push(reqObj);

      customColorObj["name"] = secVulDisplayName;
      customColorObj["value"] = this.customCol[data[i]["severity"]];
      this.customColors.push(customColorObj);       
    }
    this.vulnerableTypes = this.vulnerableTypes.filter(val => (val['value'] > 0));
    //Sorting data in Descending Order
    if(this.vulnerableTypes.length > 1) {
      this.vulnerableTypes.sort(function(a,b){
        return b['value'] - a['value'];
      });
    }
    //Code to show no Sec vulnerabilities
    if(this.vulnerableTypes.length > 0) {
      this.noSecVulChart = false;
    } else {
      this.noSecVulChart = true;
    }
    
    //Code to manipulate height of graph
    let height = 0;
    if(this.vulnerableTypes.length > 0 && this.vulnerableTypes.length < 5) {
      height = (this.vulnerableTypes.length+1) * 80;
      this.secVulnerableBarPadding = 40;
    } else if (this.vulnerableTypes.length > 4) {
      height = 400;
      this.secVulnerableBarPadding = 12;
    }
    if(this.vulnerableTypes.length > 0) {
      let final = this.vulnerableTypes[0]["value"];
      let middle = Math.round(final/2);
      this.secVulnerableXAXISTICKS = [0, middle, final];
      this.secVulGraphContainerHeight = height;
    }
  }

  prepareDefectCSData(data) {
    let bugData = data['bugs'];
    if(bugData['total'] == 0) {
      this.noPieChart = true;
    } else {
      this.noPieChart = false;
    }
    if(data['critical'] == 0 && data['major'] == 0 && data['minor'] == 0 && data['info'] == 0) {
      this.noCodeSmellChart = true;
    } else {
      this.noCodeSmellChart = false;
    }
    this.bugsData = [
      {
        "name": "Open",
        "value": bugData['open']
      },
      {
        "name": "Close",
        "value": bugData['close']
      }
    ];
    this.codeSmellValue =[
      {
        "name": "Critical",
        "value": +data['critical']
      },
      {
        "name": "Major",
        "value": +data['major']
      },
      {
        "name": "Minor",
        "value": +data['minor']
      },
      {
        "name": "Info",
        "value": +data['info']
      }
    ];
  }

  prepareTestOptimizedData(projectTestData){
    this.testOptimizedChartData = [];
    let typesOfDefects = [/* "defects", */ "intelligent", "unaffected" ];
    let displayName = {"intelligent" : "Intelligent Identified Test Cases",
                      /* "defects": "Defects",  */
                       "unaffected": "Unaffected Test Cases"}

    for(let i=0; i<typesOfDefects.length; i++) {
      let resObj = {};
      resObj["name"]=displayName[typesOfDefects[i]];
      let series = [];
      for(let j=0;j<projectTestData.length; j++) {
        let serObj = {};
        serObj = {
          "value": projectTestData[j][typesOfDefects[i]],
          "name": projectTestData[j]["sprint"]
        };
        series.push(serObj);
      }
      resObj["series"]=series;
      this.testOptimizedChartData.push(resObj);
    }
  }

  goToProjectDetailsPage() {
    localStorage.setItem('projectName', this.selectedProjectName);
    this.devSecOpsService.changeisProjectDetailsPage(true);
    this.router.navigate(['/project/'+this.selectedProjectName]);
  }

}
