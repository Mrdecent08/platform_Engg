import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-dev-ops-pipe-line',
  templateUrl: './dev-ops-pipe-line.component.html',
  styleUrls: ['./dev-ops-pipe-line.component.scss']
})
export class DevOpsPipeLineComponent implements OnInit, OnDestroy {

  hoveredPrj = '';
  tooltiptext = 'tooltiptext';
  projectDetailBox = '';
  buildStages ;
  widthx ;

  projects ;
  toolName;
  toolData;
  clearInterval;
  toolDisplayName = {
    "dependency": "Dependency Check",
    "smartqe": "Smart QE",
    "nexus": "NEXUS"
  }
  imagePath = {
    "dependency": "",
    "git": "/assets/images/git.png",
    "maven": "/assets/images/maven.png",
    "sonar": "/assets/images/sonarqube.png",
    "inato": "/assets/images/inato.png",
    "robot": "/assets/images/robotframework.png",
    "smartqe": "",
    "nexus": "",
    "tomcat": "/assets/images/apache.png",
    "nexpose": "/assets/images/insightvm.png",
    "zap": "/assets/images/ZAP.png",
  };
  toolImagePath;
  toolKeys = {
    "dependency": [{"key": "link", "displayName": "Report", "displayValue": "Dependency Report", "type":"link"}],
    "git": [{
              "key": "branch_name", 
              "displayName": "Branch Name", "type":"string"
            },
            {"key": "owner", "displayName": "Owner", "type":"string"},
          ],
    "maven" : [{"key": "status", "displayName": "Build Status", "type":"string"}],
    "sonar": [
      {"key": "vulnerabilities", "displayName": "Vulnerabilities", "type":"string_link", "differentKey": "vul_link"},
      {"key": "bugs", "displayName": "Bugs", "type":"string_link", "differentKey": "bugs_link"},
      {"key": "codesmells", "displayName": "Code Smells", "type":"string_link", "differentKey": "code_link"},
      ],
    "inato": [  {"key": "test_cases", "displayName": "Total No. Of Test Cases", "type":"string"},
                {"key": "impacted", "displayName": "Impacted Test Cases", "type":"string"},     
             ],
    "robot": [
            /*   {"key": "critical_tests", "displayName": "Critical Tests"}, */
              {"key": "test_cases", "displayName": "Test No. Of Test Cases", "type":"string"},
              {"key": "critical_tests", "displayName": "Critical Test Count", "type":"string"},
              {"key": "tests_passed", "displayName": "No. Of Tests Passed", "type":"string"},
              {"key": "tests_failed", "displayName": "No. Of Tests Failed", "type":"string"}
            ],
    "smartqe": [],
    "nexus": [{"key": "status", "displayName": "Status", "type":"string"}],
    "tomcat": [{"key": "status", "displayName": "Build Deployment Status", "type":"string"}],
    "nexpose": [
                {"key": "ip", "displayName": "IP Address", "type":"string"},
                {"key": "os", "displayName": "OS", "type":"string"},
                {"key": "riskscore", "displayName": "Risk Score", "type":"string"},
                {"key": "vulnerabilities['total']", "displayName": "Vulnerabilities", "type":"string"},
                {"key": "lastscandate", "displayName": "Last Scaned Date", "type":"string"},
                {"key": "link", "displayName": "URL", "displayValue": "Link to Report1", "type":"link"}
              ],
    "zap": [{"key": "link", "displayName": "Report", "displayValue": "Click here for ZAP Report", "type":"link"}]
  };
  toolKeysObject;
  isLoading = true;
  error = false;

  constructor(
    private router: Router, 
    private devSecOpsService : DevSecOpsService, 
    private mockDevSecOpsService: MockDevSecOpsService) { }

  ngOnInit() {
    this.getData();
    this.clearInterval = setInterval(() => {
       this.getData(); 
      }, 5000);
  }

  getData() {
    this.devSecOpsService.getBuildStages().subscribe((response)=>{      
      this.buildStages = response;
      if(this.buildStages.length == 0) {
        this.widthx = 87/10;
      } else {
        this.widthx = 87/this.buildStages.length;
      }
    });
    this.devSecOpsService.getProjectsData().subscribe((response)=>{      
      this.projects = response;
      this.isLoading = false;
      this.error = false;
    }, (error)=> {
      this.error = true;
      this.projects = [];
    });

    // Uncomment this to get data from MockService

    /* this.buildStages = this.mockDevSecOpsService.getBuildStages();
    if(this.buildStages.length == 0) {
      this.widthx = 87/10;
    } else {
      this.widthx = 87/this.buildStages.length;
    }
    this.projects = this.mockDevSecOpsService.getProjectsData();
    this.isLoading = false;  */
  }

  getToolDetails(project, tool, index) {
    let obj = {};
    obj["name"] = project.name;
    this.toolName = tool;
    this.tooltiptext = 'tooltiptext'+ index;
    this.projectDetailBox = project.status;
    this.hoveredPrj = project;
    
    this.devSecOpsService.getToolData(tool, JSON.stringify(obj)).subscribe((response)=>{
      this.toolData = response;
      this.toolKeysObject = this.toolKeys[this.toolName];
      this.toolImagePath = (this.imagePath[this.toolName]);
    },(error) => {
      this.toolData = '';
      this.toolImagePath = '';
      this.toolKeysObject = [];
    });
    
  }
  goToProjectDetailsPage(project) {
    localStorage.setItem('projectName', project.name);
    this.devSecOpsService.changeisProjectDetailsPage(true);
    this.router.navigate(['/project/'+project.name]);
  }

  showDetailsOfPrj(prj, index) {
    this.hoveredPrj = prj;
    this.tooltiptext = 'tooltiptext'+ index;
  }
  removeDetails() {
    this.hoveredPrj = '';
  }
  getClass() {
    return [this.tooltiptext, this.projectDetailBox];
  }
  resetData() {
    this.hoveredPrj = '';
    this.toolName = '';
    this.toolData = '';
  }

  goToPage(key) {
    window.open(this.toolData[key], '_blank');
  }
  ngOnDestroy() {
    this.resetData();
    if (this.clearInterval) {
      clearInterval(this.clearInterval);
    }
  }

}
