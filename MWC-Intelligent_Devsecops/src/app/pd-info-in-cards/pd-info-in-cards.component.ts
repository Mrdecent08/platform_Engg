import { Component, OnInit , Input, Output, EventEmitter,  OnChanges} from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-pd-info-in-cards',
  templateUrl: './pd-info-in-cards.component.html',
  styleUrls: ['./pd-info-in-cards.component.scss']
})
export class PdInfoInCardsComponent implements OnInit, OnChanges {

  projectRiskInCardData;
  @Input() projectName; 
  isLoading = true;
  isModalOpen = false;
  error = false;
  feildIssues;
  feildIssuesData = [ 
  {
    "vulnerability": "Denial of service in SNMP_Java",
    "description": "Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java",
    "createdOn": "06/02/2020",
    "featuresAffected": "Java Snmp",
    "status": "Reproduce Issue",
  },
  {
    "vulnerability": "Denial of service in SNMP_Java",
    "description": "Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java",
    "createdOn": "06/02/2020",
    "featuresAffected": "Java Snmp",
    "status": "Reproduce Issue",
  },
  {
    "vulnerability": "Denial of service in SNMP_Java",
    "description": "Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java",
    "createdOn": "06/02/2020",
    "featuresAffected": "Java Snmp",
    "status": "Reproduce Issue",
  },
  {
    "vulnerability": "Denial of service in SNMP_Java",
    "description": "Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java",
    "createdOn": "06/02/2020",
    "featuresAffected": "Java Snmp",
    "status": "Reproduce Issue",
  },
  {
    "vulnerability": "Denial of service in SNMP_Java",
    "description": "Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java",
    "createdOn": "06/02/2020",
    "featuresAffected": "Java Snmp",
    "status": "Reproduce Issue",
  },
  {
    "vulnerability": "Denial of service in SNMP_Java",
    "description": "Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java",
    "createdOn": "06/02/2020",
    "featuresAffected": "Java Snmp",
    "status": "Reproduce Issue",
  },
  {
    "vulnerability": "Denial of service in SNMP_Java",
    "description": "Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java",
    "createdOn": "06/02/2020",
    "featuresAffected": "Java Snmp",
    "status": "Reproduce Issue",
  },
  {
    "vulnerability": "Denial of service in SNMP_Java",
    "description": "Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java Denial of service in SNMP_Java",
    "createdOn": "06/02/2020",
    "featuresAffected": "Java Snmp",
    "status": "Reproduce Issue",
  }
];

/* xyz = [
  {
  "issue_id":"1",
  "description":"The exploit connects to the OpenDaylight controller running in localhost, port6653, and sends 100 000 OpenFlow hello packets.",
  "created_on":datetime.date(2020,
  1, 5), "vulnerability":"Denial of Service in OpenDaylight",
  "feature_affected":"odl-l2switch-switch"
  },
  {
  "issue_id":"2",
  "description":"The first REST API requests are successful and the controller returns the transaction ID. After a certain number of successful requests, the controller returns the stack trace of a NullPointerException in the HTTP response. Afterwards, the user is not able to add flows to the sameswitch, unless the controller is restarted",
  "created_on":datetime.date(2020,
  1, 15), "vulnerability":"Denial of Service in adding flows in Open-Daylight(Sending multiple API requests)",
  "feature_affected":"Odl-restconf"
  },
  {
  "issue_id":"3",
  "description":"Odl-mdsal-xsql component exposes two ports 40004 and 34343 for users to query or update database tables using XSQL. The component is vulnerable to several denial of service attack.",
  "created_on":datetime.date(2020,
  1, 18), "vulnerability":"Denial of Service in OpenDaylight odl-mdsal-xsql",
  "feature_affected":"Odl-mdsal-xsql"
  },
  {
  "issue_id":"4",
  "description":"StreamCorruptedException and NullPointerException in OpenDaylight odl-mdsal-xsql",
  "created_on":datetime.date(2020,
  1, 21), "vulnerability":"StreamCorruptedException and NullPointerException in OpenDaylight odl-mdsal-xsql",
  "feature_affected":"Odl-mdsal-xsql"
  },
  {
  "issue_id":"5",
  "description":"Password change does not result in Karaf clearing cache, allowing old password to still be used",
  "created_on":datetime.date(2020,
  1, 25), "vulnerability":"Password change does not result in Karaf clearing cache, allowing old password to still be used",
  "feature_affected":"aaa"
  }
  ] */
  /* {"keys": ["abc", "sdf", "poi"]} */
  @Output() openModal = new EventEmitter();
  

  constructor(
    private devSecOpsService : DevSecOpsService,
    private mockDevSecOpsService : MockDevSecOpsService
  ) { }

  ngOnInit() {
    this.getData();
    localStorage.setItem('isModalOpen', 'false');
  }

  ngOnChanges(changes) {
    if(!changes['projectName'].firstChange) {
      this.getData();
    }
  }
  getData() {
    let obj = {"name": this.projectName};
    this.devSecOpsService.getProjectInfoInCards(obj).subscribe((response)=> {  
      this.projectRiskInCardData = response;
      this.isLoading = false;
    }, (error)=> {
      this.isLoading = false;
      this.error = true;
    });
    this.devSecOpsService.getProjectFeildIssues(obj).subscribe((response)=> {  
      this.feildIssues = response;
    });

    // Uncomment this to get data from MockService
    /* this.projectRiskInCardData = this.mockDevSecOpsService.getProjectInfoInCards();
    this.isLoading = false; */
  }
  openModalDialog() {
   if(this.projectRiskInCardData.fieldissues > 0) {
    this.openModal.emit(this.feildIssuesData);
   }
  }

  closeModal() {
    this.isModalOpen = false;
  }
}
