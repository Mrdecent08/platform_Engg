import { Component, OnInit, ViewEncapsulation, Output, EventEmitter } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-security-policy-compliance',
  templateUrl: './security-policy-compliance.component.html',
  styleUrls: ['./security-policy-compliance.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class SecurityPolicyComplianceComponent implements OnInit {

  colorScheme = {
    domain: ['#79C450', '#F06293']
  };

  securityCompliancePieData;
  showLabels = false;
  explodeSlices = true;
  doughnut = true;
  error = false;

  feildIssuesData = [
      {
      "status":"fixed",
      "vulnerability":"StreamCorruptedException and NullPointerException in OpenDaylight odl-mdsal-xsql",
      "issue_id":1
      },
      {
    "status":"fixed",
      "vulnerability":"Password change does not result in Karaf clearing cache, allowing old password to still be used",
      "issue_id":2
      },
      {
      "status":"fixed",
      "vulnerability":"Denial of Service in OpenDaylight odl-mdsal-xsql",
      "issue_id":3
      },
      {
      "status":"fixed",
      "vulnerability":"Denial of Service in adding flows in Open-Daylight(Sending multiple API requests)",
      "issue_id":4
      },
      {
        "status":"New",
        "vulnerability":"Denial of Service in adding flows in Open-Daylight(Sending multiple API requests)",
        "issue_id":5
        }
    ];
  value = 5;
  feildIssues;

  @Output() openModal = new EventEmitter();
  clearInterval;
  constructor(
    private devSecOpsService : DevSecOpsService,
    private mockDevSecOpsService: MockDevSecOpsService) { }

  ngOnInit() {
    this.clearInterval = setInterval(() => {
      this.getData(); 
     }, 5000);

     this.getData(); 
  }

  openModalDialog() {
    if(this.feildIssues.length > 0) {
     this.openModal.emit(this.feildIssues);
    }
   }

   getData() {
     this.devSecOpsService.getProjectFeildIssues({}).subscribe((response)=> {  
      this.feildIssues = response;
    });

    /* this.devSecOpsService.getSecurityPolicyCompliance().subscribe((response)=>{
      this.securityCompliancePieData = [
        {
          "name": "Passed",
          "value": +response["passed"]
        },
        {
          "name": "Failed",
          "value": +response["failed"]
        }
      ];
    }, (error)=> {
      this.error = true;
    }); */

    
    /* let response = this.mockDevSecOpsService.getSecurityPolicyCompliance();
    this.securityCompliancePieData = [
      {
        "name": "Passed",
        "value": +response["passed"]
      },
      {
        "name": "Failed",
        "value": +response["failed"]
      }
    ]; */
   }

}
