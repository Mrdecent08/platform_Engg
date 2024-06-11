import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class MockDevSecOpsService {

  constructor(
  
  ) { }

  backendURL = 'http://10.138.77.149:12399/';

  getProjects() {  
    return [ 
        "SNMP_Java",
        "Sample_Java",
     ];
  }

  getOverViewData() {
    return { 
        "current_builds":14,
        "deployed":3,
        "failed_builds":7,
        "total_projects":2,
        "release_version":"19.4"
     };
  }

  getvulnerabilitiesData() {
    return { 
        "minor":234,
        "info":0,
        "critical":0,
        "blocker":0,
        "major":0
     };
  }

  getBuildStages() {  
    return [
        "Build & Unit Testing", 
        "Quality Scans", 
        "Publish to Artifacts", 
        "Build Sanity Testing", 
        "Functional Testing", 
        "Regression Testing",
        "Infra Security Testing",
        "Dynamic Application Security Testing",
        "Deploy"
    ]
  }

  getProjectsData() {
    return [{"stages": [{"status": "SUCCESS", "name": "Build & Unit Testing", "duration": 75, "tool": "maven", "newduration": "1.25 min"}, {"status": "SUCCESS", "name": "Quality Scans", "duration": 122, "tool": "sonar", "newduration": "2.03 min"}, {"status": "SUCCESS", "name": "Publish to Artifacts", "duration": 7, "tool": "nexus", "newduration": "7 sec"}, {"status": "SUCCESS", "name": "Build Sanity Testing", "duration": 6, "tool": "robot", "newduration": "6 sec"}, {"status": "SUCCESS", "name": "Functional Testing", "duration": 5, "tool": "inato", "newduration": "5 sec"}, {"status": "NOT_EXECUTED", "name": "Regression Testing", "duration": null, "tool": "smartqe", "newduration": null}, {"status": "SUCCESS", "name": "Infra Security Testing", "duration": 3, "tool": "nexpose", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Dynamic Application Security Testing", "duration": 36, "tool": "zap", "newduration": "36 sec"}, {"status": "SUCCESS", "name": "Deploy", "duration": 1, "tool": "tomcat", "newduration": "1 sec"}], "build_number": "62", "newduration": "4.22 min", "status": "SUCCESS", "name": "CloudSim_Java", "duration": 253}, {"stages": [{"status": "SUCCESS", "name": "Build & Unit Testing", "duration": 171, "tool": "maven", "newduration": "2.85 min"}, {"status": "SUCCESS", "name": "Quality Scans", "duration": 431, "tool": "sonar", "newduration": "7.18 min"}, {"status": "SUCCESS", "name": "Publish to Artifacts", "duration": 23, "tool": "nexus", "newduration": "23 sec"}, {"status": "SUCCESS", "name": "Build Sanity Testing", "duration": 3, "tool": "robot", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Functional Testing", "duration": 796, "tool": "inato", "newduration": "13.27 min"}, {"status": "NOT_EXECUTED", "name": "Regression Testing", "duration": null, "tool": "smartqe", "newduration": null}, {"status": "SUCCESS", "name": "Infra Security Testing", "duration": 3, "tool": "nexpose", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Dynamic Application Security Testing", "duration": 36, "tool": "zap", "newduration": "36 sec"}, {"status": "SUCCESS", "name": "Deploy", "duration": 6, "tool": "tomcat", "newduration": "6 sec"}], "build_number": "78", "newduration": "24.42 min", "status": "SUCCESS", "name": "Odl_Oxygen_Java", "duration": 1465}, {"stages": [{"status": "SUCCESS", "name": "Build & Unit Testing", "duration": 42, "tool": "maven", "newduration": "42 sec"}, {"status": "SUCCESS", "name": "Quality Scans", "duration": 50, "tool": "sonar", "newduration": "50 sec"}, {"status": "SUCCESS", "name": "Publish to Artifacts", "duration": 7, "tool": "nexus", "newduration": "7 sec"}, {"status": "SUCCESS", "name": "Build Sanity Testing", "duration": 54, "tool": "robot", "newduration": "54 sec"}, {"status": "SUCCESS", "name": "Functional Testing", "duration": 40, "tool": "inato", "newduration": "40 sec"}, {"status": "NOT_EXECUTED", "name": "Regression Testing", "duration": null, "tool": "smartqe", "newduration": null}, {"status": "SUCCESS", "name": "Infra Security Testing", "duration": 3, "tool": "nexpose", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Dynamic Application Security Testing", "duration": 37, "tool": "zap", "newduration": "37 sec"}, {"status": "SUCCESS", "name": "Deploy", "duration": 1, "tool": "tomcat", "newduration": "1 sec"}], "build_number": "138", "newduration": "3.83 min", "status": "SUCCESS", "name": "SNMP_Java", "duration": 230}]
  }
  
  getTestOptimizedData() {
    return [ 
        { 
           "tobeExecuted":50,
           "canbeSkipped":5,
           "total":100,
           "projectid":9,
           "pname":"SNMP_Java",
           "testid":1
        },
        { 
           "tobeExecuted":14,
           "canbeSkipped":1,
           "total":15,
           "projectid":8,
           "pname":"Sample_Java",
           "testid":2
        }
     ];
  }

  getTop10PrjByVulnerabilites() {
    return [ 
        { 
           "project_name":"SNMP_Java",
           "vulnerabilities":"2"
        },
        { 
           "project_name":"Sample_Java",
           "vulnerabilities":"232"
        },
        { 
           "project_name":"project3",
           "vulnerabilities":"8"
        },
        { 
           "project_name":"project4",
           "vulnerabilities":"13"
        },
        { 
           "project_name":"project5",
           "vulnerabilities":"21"
        },
        { 
           "project_name":"project6",
           "vulnerabilities":"41"
        },
        { 
           "project_name":"project7",
           "vulnerabilities":"18"
        },
        { 
           "project_name":"project8",
           "vulnerabilities":"23"
        },
        { 
           "project_name":"project9",
           "vulnerabilities":"33"
        },
        { 
           "project_name":"project10",
           "vulnerabilities":"0"
        },
        { 
           "project_name":"project11",
           "vulnerabilities":"167"
        },
        { 
           "project_name":"project12",
           "vulnerabilities":"316"
        },
        { 
           "project_name":"project13",
           "vulnerabilities":"99"
        }
     ];
  }

  getLastBuildProjects() {
    return [ 
        { 
           "project":"SNMP_Java",
           "date":"2020-01-09 17:28:57",
           "timestamp_in_millis":1578590937162
        },
        { 
           "project":"Sample_Java",
           "date":"2020-01-09 17:24:24",
           "timestamp_in_millis":1578590664885
        }
     ];
  }

  getToolData(tool, obj) {
    return;
  }


  getIndividualPrjBuildInfo(project_name) {
    if (project_name == 'SNMP_Java') {
        return { 
            "time":"2019-12-31T11:21:10.000+00:00",
            "passed":2,
            "branch":"master",
            "failed":1,
            "current_version":"1244",
            "builds":3,
            "startedby":"Administrator"
         };
    } else if (project_name == 'Sample_Java') {
        return { 
            "time":"2019-10-17T06:20:37.000+00:00",
            "passed":1,
            "branch":"master",
            "failed":6,
            "current_version":"1244",
            "builds":11,
            "startedby":"sindhu dodda"
         };
    } else {
        return { 
            "time":"2019-12-31T11:21:10.000+00:00",
            "passed":2,
            "branch":"master",
            "failed":1,
            "current_version":"1244",
            "builds":3,
            "startedby":"Administrator"
         };
    }
    
  }

  getIndividualPrjBuildDetails(project_name) {   
    if (project_name == 'SNMP_Java') {
        return { 
            "project_owner":"Vegulla",
            "project_name":"SNMP_Java",
            "velocity":"15",
            "release_date":"2020-01-08",
            "previous_release_date":"2019-12-02"
         };
    } else if (project_name == 'Sample_Java') {
        return { 
            "project_owner":"Srinivas",
            "project_name":"SNMP_Java",
            "velocity":"15",
            "release_date":"2020-01-08",
            "previous_release_date":"2019-12-02"
         };
    } else {
        return { 
            "project_owner":"Default",
            "project_name":"No Such Project",
            "velocity":"15",
            "release_date":"2020-01-08",
            "previous_release_date":"2019-12-02"
         };
    }
  }

  getBuildHistoryOfProject(project_name) {  
    if (project_name == 'SNMP_Java') {
        return [ 
            { 
               "status":"SUCCESS",
               "stages":[ 
                  { 
                     "status":"SUCCESS",
                     "duration":1,
                     "name":"Source Code Management",
                     "tag":"git"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":6,
                     "name":"Build",
                     "tag":"maven"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":28,
                     "name":"Static Application Security Testing",
                     "tag":"sonar"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":19,
                     "name":"iNato",
                     "tag":"inato"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":9,
                     "name":"Functional Testing",
                     "tag":"robot"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":null,
                     "name":"Publish to Artifactory",
                     "tag":"smartqe"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":1,
                     "name":"Deploy in Staging",
                     "tag":"nexus"
                  },
                  { 
                     "status":"NOT_EXECUTED",
                     "duration":null,
                     "name":"SmartQE",
                     "tag":"tomcat"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":3,
                     "name":"OS Security Vulnerability Testing Nexpose",
                     "tag":"nexpose"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":30,
                     "name":"Dynamic Application Security Testing ZAP",
                     "tag":"zap"
                  }
               ],
               "date":"2020-01-09 17:30:36",
               "build_number":"75"
            },
            { 
               "status":"SUCCESS",
               "stages":[ 
                  { 
                     "status":"SUCCESS",
                     "duration":1,
                     "name":"Source Code Management",
                     "tag":"git"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":6,
                     "name":"Build",
                     "tag":"maven"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":28,
                     "name":"Static Application Security Testing",
                     "tag":"sonar"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":12,
                     "name":"iNato",
                     "tag":"inato"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":6,
                     "name":"Functional Testing",
                     "tag":"robot"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":null,
                     "name":"Publish to Artifactory",
                     "tag":"smartqe"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":1,
                     "name":"Deploy in Staging",
                     "tag":"nexus"
                  },
                  { 
                     "status":"NOT_EXECUTED",
                     "duration":null,
                     "name":"SmartQE",
                     "tag":"tomcat"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":3,
                     "name":"OS Security Vulnerability Testing Nexpose",
                     "tag":"nexpose"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":30,
                     "name":"Dynamic Application Security Testing ZAP",
                     "tag":"zap"
                  }
               ],
               "date":"2020-01-08 10:51:52",
               "build_number":"74"
            },
            { 
               "status":"FAILED",
               "stages":[ 
                  { 
                     "status":"SUCCESS",
                     "duration":1,
                     "name":"Source Code Management",
                     "tag":"git"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":6,
                     "name":"Build",
                     "tag":"maven"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":28,
                     "name":"Static Application Security Testing",
                     "tag":"sonar"
                  },
                  { 
                     "status":"SUCCESS",
                     "duration":12,
                     "name":"iNato",
                     "tag":"inato"
                  },
                  { 
                     "status":"FAILED",
                     "duration":6,
                     "name":"Functional Testing",
                     "tag":"robot"
                  },
                  { 
                     "status":"FAILED",
                     "duration":null,
                     "name":"Publish to Artifactory",
                     "tag":"smartqe"
                  },
                  { 
                     "status":"FAILED",
                     "duration":null,
                     "name":"Deploy in Staging",
                     "tag":"nexus"
                  },
                  { 
                     "status":"FAILED",
                     "duration":null,
                     "name":"SmartQE",
                     "tag":"tomcat"
                  },
                  { 
                     "status":"FAILED",
                     "duration":null,
                     "name":"OS Security Vulnerability Testing Nexpose",
                     "tag":"nexpose"
                  },
                  { 
                     "status":"FAILED",
                     "duration":null,
                     "name":"Dynamic Application Security Testing ZAP",
                     "tag":"zap"
                  }
               ],
               "date":"2020-01-08 10:48:44",
               "build_number":"73"
            }
         ]
    } else if (project_name == 'Sample_Java') {
        return [{"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 2, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 31, "name": "Build", "tag": "maven"}, {"status": "SUCCESS", "duration": 120, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "SUCCESS", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "SUCCESS", "duration": 3, "name": "Functional Testing", "tag": "robot"}, {"status": "SUCCESS", "duration": 1, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "NOT_EXECUTED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "NOT_EXECUTED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "SUCCESS", "duration": 3, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "SUCCESS", "duration": 30, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-10 08:33:19", "build_number": "37"}, {"status": "SUCCESS", "stages": [{"status": "SUCCESS", "duration": 3, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 32, "name": "Build", "tag": "maven"}, {"status": "SUCCESS", "duration": 109, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "SUCCESS", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "SUCCESS", "duration": 2, "name": "Functional Testing", "tag": "robot"}, {"status": "SUCCESS", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "SUCCESS", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "NOT_EXECUTED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "SUCCESS", "duration": 5, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "SUCCESS", "duration": 31, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:27:27", "build_number": "36"}, {"status": "ABORTED", "stages": [{"status": "SUCCESS", "duration": 2, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 32, "name": "Build", "tag": "maven"}, {"status": "ABORTED", "duration": 30, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "ABORTED", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "ABORTED", "duration": null, "name": "Functional Testing", "tag": "robot"}, {"status": "ABORTED", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "ABORTED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "ABORTED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "ABORTED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "ABORTED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:24:10", "build_number": "35"}, {"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 2, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 30, "name": "Build", "tag": "maven"}, {"status": "SUCCESS", "duration": 113, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "SUCCESS", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "SUCCESS", "duration": 8, "name": "Functional Testing", "tag": "robot"}, {"status": "SUCCESS", "duration": 1, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "FAILED", "duration": 1, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "FAILED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "FAILED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "FAILED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:22:14", "build_number": "34"}, {"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 5, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 30, "name": "Build", "tag": "maven"}, {"status": "FAILED", "duration": 8, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "FAILED", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "FAILED", "duration": null, "name": "Functional Testing", "tag": "robot"}, {"status": "FAILED", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "FAILED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "FAILED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "FAILED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "FAILED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:16:25", "build_number": "33"}, {"status": "NOT_EXECUTED", "stages": [], "date": "2020-01-09 17:15:12", "build_number": "32"}, {"status": "NOT_EXECUTED", "stages": [], "date": "2020-01-09 17:14:55", "build_number": "31"}, {"status": "NOT_EXECUTED", "stages": [], "date": "2020-01-09 17:09:32", "build_number": "30"}, {"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 2, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 31, "name": "Build", "tag": "maven"}, {"status": "FAILED", "duration": 9, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "FAILED", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "FAILED", "duration": null, "name": "Functional Testing", "tag": "robot"}, {"status": "FAILED", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "FAILED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "FAILED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "FAILED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "FAILED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:04:09", "build_number": "29"}, {"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 2, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 32, "name": "Build", "tag": "maven"}, {"status": "FAILED", "duration": 9, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "FAILED", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "FAILED", "duration": null, "name": "Functional Testing", "tag": "robot"}, {"status": "FAILED", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "FAILED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "FAILED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "FAILED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "FAILED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:02:26", "build_number": "28"}, {"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 3, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 35, "name": "Build", "tag": "maven"}, {"status": "FAILED", "duration": 9, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "FAILED", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "FAILED", "duration": null, "name": "Functional Testing", "tag": "robot"}, {"status": "FAILED", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "FAILED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "FAILED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "FAILED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "FAILED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:00:23", "build_number": "27"}];
    } else {
        return [];
    }
  }

  getBuildTrendsData(project_name) {
    if (project_name == 'SNMP_Java') {
        return [
            { 
               "build_status":"SUCCESS",
               "percentage_success_stages":100,
               "build_startdate":"2020-01-09 17:28:57",
               "build_no":"#750",
               "no_successful_stages":10,
               "build_enddate":"2020-01-09 17:30:36",
               "last_successful_stage":"Dynamic Application Security Testing ZAP"
            },
            { 
               "build_status":"SUCCESS",
               "percentage_success_stages":100,
               "build_startdate":"2020-01-08 10:50:25",
               "build_no":"#74",
               "no_successful_stages":10,
               "build_enddate":"2020-01-08 10:51:52",
               "last_successful_stage":"Dynamic Application Security Testing ZAP"
            },
            { 
               "build_status":"FAILED",
               "percentage_success_stages":40.0,
               "last_succesful_stage":"iNato",
               "build_startdate":"2020-01-08 10:47:50",
               "build_no":"#73",
               "no_successful_stages":4,
               "build_enddate":"2020-01-08 10:48:44"
            }
         ]
         ;
    } else if (project_name == 'Sample_Java') {
        return [ 
            { 
               "build_status":"FAILED",
               "percentage_success_stages":90.0,
               "last_succesful_stage":"OS Security Vulnerability Testing Nexpose",
               "build_startdate":"2020-01-10 08:30:07",
               "build_no":"#37",
               "no_successful_stages":9,
               "build_enddate":"2020-01-10 08:33:19"
            },
            { 
               "build_status":"SUCCESS",
               "percentage_success_stages":100,
               "build_startdate":"2020-01-09 17:24:24",
               "build_no":"#36",
               "no_successful_stages":10,
               "build_enddate":"2020-01-09 17:27:27",
               "last_successful_stage":"Dynamic Application Security Testing ZAP"
            },
            { 
               "build_status":"ABORTED",
               "percentage_success_stages":90.0,
               "last_succesful_stage":"OS Security Vulnerability Testing Nexpose",
               "build_startdate":"2020-01-09 17:23:05",
               "build_no":"#35",
               "no_successful_stages":9,
               "build_enddate":"2020-01-09 17:24:10"
            },
            { 
               "build_status":"FAILED",
               "percentage_success_stages":60.0,
               "last_succesful_stage":"Publish to Artifactory",
               "build_startdate":"2020-01-09 17:19:38",
               "build_no":"#34",
               "no_successful_stages":6,
               "build_enddate":"2020-01-09 17:22:14"
            },
            { 
               "build_status":"FAILED",
               "percentage_success_stages":20.0,
               "last_succesful_stage":"Build",
               "build_startdate":"2020-01-09 17:15:35",
               "build_no":"#33",
               "no_successful_stages":2,
               "build_enddate":"2020-01-09 17:16:25"
            },
            { 
               "build_status":"NOT_EXECUTED",
               "percentage_success_stages":0,
               "last_succesful_stage":"null",
               "build_startdate":"2020-01-09 17:15:12",
               "build_no":"#32",
               "no_successful_stages":0,
               "build_enddate":"2020-01-09 17:15:12"
            },
            { 
               "build_status":"NOT_EXECUTED",
               "percentage_success_stages":0,
               "last_succesful_stage":"null",
               "build_startdate":"2020-01-09 17:14:54",
               "build_no":"#31",
               "no_successful_stages":0,
               "build_enddate":"2020-01-09 17:14:55"
            },
            { 
               "build_status":"NOT_EXECUTED",
               "percentage_success_stages":0,
               "last_succesful_stage":"null",
               "build_startdate":"2020-01-09 17:09:31",
               "build_no":"#30",
               "no_successful_stages":0,
               "build_enddate":"2020-01-09 17:09:32"
            },
            { 
               "build_status":"FAILED",
               "percentage_success_stages":20.0,
               "last_succesful_stage":"Build",
               "build_startdate":"2020-01-09 17:03:25",
               "build_no":"#29",
               "no_successful_stages":2,
               "build_enddate":"2020-01-09 17:04:09"
            },
            { 
               "build_status":"FAILED",
               "percentage_success_stages":20.0,
               "last_succesful_stage":"Build",
               "build_startdate":"2020-01-09 17:01:42",
               "build_no":"#28",
               "no_successful_stages":2,
               "build_enddate":"2020-01-09 17:02:26"
            },
            { 
               "build_status":"FAILED",
               "percentage_success_stages":20.0,
               "last_succesful_stage":"Build",
               "build_startdate":"2020-01-09 16:59:33",
               "build_no":"#27",
               "no_successful_stages":2,
               "build_enddate":"2020-01-09 17:00:23"
            }
         ];
    } else {
        return [ 
            { 
               "build_status":"SUCCESS",
               "percentage_success_stages":100,
               "build_startdate":"2020-01-09 17:28:57",
               "build_no":"#75",
               "no_successful_stages":10,
               "build_enddate":"2020-01-09 17:30:36",
               "last_successful_stage":"Dynamic Application Security Testing ZAP"
            },
            { 
               "build_status":"SUCCESS",
               "percentage_success_stages":100,
               "build_startdate":"2020-01-08 10:50:25",
               "build_no":"#74",
               "no_successful_stages":10,
               "build_enddate":"2020-01-08 10:51:52",
               "last_successful_stage":"Dynamic Application Security Testing ZAP"
            },
            { 
               "build_status":"FAILED",
               "percentage_success_stages":40.0,
               "last_succesful_stage":"iNato",
               "build_startdate":"2020-01-08 10:47:50",
               "build_no":"#73",
               "no_successful_stages":4,
               "build_enddate":"2020-01-08 10:48:44"
            }
         ];
    }
  }

  getProjectVulnerability(project_name) {
    if (project_name == 'SNMP_Java') {
        return {minor: 232, info: 0, critical: 0, blocker: 0, major: 0};
    } else if (project_name == 'Sample_Java') {
        return {"minor": 2, "info": 0, "critical": 0, "blocker": 0, "major": 0};
    } else {
        return {minor: 24, info: 0, critical: 0, blocker: 0, major: 0};
    }
  }

  getSecurityVulnerabilities() {
      return [[{"count": 21, "type": "Critical"}, {"count": 46, "type": "Major"}, {"count": 24, "type": "Minor"}], {"Minor": [{"name": "InsecureDeserialization", "count": 7}, {"name": "Cross-SiteScripting", "count": 6}, {"name": "ComponentKnownVulnerability", "count": 11}], "Major": [{"name": "SensitiveDataExposure", "count": 15}, {"name": "SensitiveDataExposure", "count": 10}, {"name": "XMLExternalEntities", "count": 12}, {"name": "SensitiveDataExposure", "count": 9}], "Critical": [{"name": "InjectionFlaws", "count": 3}, {"name": "InsufficientLogging", "count": 11}, {"name": "InsufficientLogging", "count": 7}]}, 
      
      {"Minor": [{"count": 8, "project": "Sample_Java"}, {"count": 10, "project": "SNMP_Java"}, {"count": 6, "project": "Odl_Oxygen_Java"},  ], 
      
      "Major": [{"count": 15, "project": "Sample_Java"}, {"count": 14, "project": "SNMP_Java"}, {"count": 17, "project": "Odl_Oxygen_Java"}],
      
      
      "Critical": [{"count": 10, "project": "SNMP_Java"}, {"count": 5, "project": "Odl_Oxygen_Java"}, {"count": 6, "project": "Sample_Java"}  ]}];
  }
  getCodeSmellDetails() {
      return {
        "alldefects":[
        {
        "total":"38",
        "type":"technical_debts"
        },
        {
        "total":"1973",
        "type":"code_smell"
        },
        {
        "total":"86",
        "type":"bugs"
        }
        ],
        "defectsdata":{
        "bugs":[
        {
        "count":0,
        "project":"Odl_Oxygen_Java"
        },
        {
        "count":9,
        "project":"SNMP_Java"
        },
        {
        "count":77,
        "project":"Sample_Java"
        }
        ],
        "code_smell":[
        {
        "count":33,
        "project":"Odl_Oxygen_Java"
        },
        {
        "count":275,
        "project":"SNMP_Java"
        },
        {
        "count":1665,
        "project":"Sample_Java"
        }
        ],
        "technical_debts":[
        {
        "count":0,
        "project":"Odl_Oxygen_Java"
        },
        {
        "count":4,
        "project":"SNMP_Java"
        },
        {
        "count":34,
        "project":"Sample_Java"
        }
        ]
        }
    }
  }
  getUpcomingReleases() {
      return {"allprojects": [
          {"name": "CloudSim_Java", "releasedate": "11/03/2020", "ciarating": "MEDIUM"}, 
          {"name": "SNMP_Java", "releasedate": "14/03/2020", "ciarating": "HIGH"}, 
          {"name": "Odl_Oxygen_Java", "releasedate": "20/02/2020", "ciarating": "HIGH"},
          {"name": "CloudSim_Java", "releasedate": "11/03/2020", "ciarating": "MEDIUM"}, 
          {"name": "SNMP_Java", "releasedate": "14/03/2020", "ciarating": "HIGH"}, 
          {"name": "Odl_Oxygen_Java", "releasedate": "20/02/2020", "ciarating": "HIGH"}
        
        ],
      
      
      
      "testoptimization": {"Odl_Oxygen_Java": [{"intelligent": 6, "unaffected": 2, "defects": 3, "sprint": "sprint1"}, {"intelligent": 7, "unaffected": 8, "defects": 6, "sprint": "sprint2"}, {"intelligent": 20, "unaffected": 25, "defects": 3, "sprint": "sprint3"}, {"intelligent": 15, "unaffected": 18, "defects": 1, "sprint": "sprint4"}], "SNMP_Java": [{"intelligent": 8, "unaffected": 13, "defects": 5, "sprint": "sprint1"}, {"intelligent": 10, "unaffected": 30, "defects": 4, "sprint": "sprint2"}, {"intelligent": 22, "unaffected": 42, "defects": 8, "sprint": "sprint3"}, {"intelligent": 76, "unaffected": 42, "defects": 7, "sprint": "sprint4"}, {"intelligent": 6, "unaffected": 44, "defects": 5, "sprint": "sprint5"}, {"intelligent": 4, "unaffected": 50, "defects": 2, "sprint": "sprint6"}], "CloudSim_Java": [{"intelligent": 2, "unaffected": 2, "defects": 9, "sprint": "sprint1"}, {"intelligent": 2, "unaffected": 2, "defects": 8, "sprint": "sprint2"}, {"intelligent": 2, "unaffected": 2, "defects": 5, "sprint": "sprint3"}, {"intelligent": 2, "unaffected": 2, "defects": 5, "sprint": "sprint4"}, {"intelligent": 2, "unaffected": 2, "defects": 4, "sprint": "sprint5"}, {"intelligent": 2, "unaffected": 3, "defects": 2, "sprint": "sprint6"}]},

      
      "securityvulnerability": {"Odl_Oxygen_Java": [{"type": "BrokenAccessControl", "severity": "MAJOR", "count": 0}, {"type": "InsufficientLogging", "severity": "CRITICAL", "count": 0}], "SNMP_Java": [{"type": "knownvulnerability", "severity": "MINOR", "count": 56}, {"type": "InsufficientLogging", "severity": "CRITICAL", "count": 2}, {"type": "DataExposure", "severity": "MAJOR", "count": 0}], "CloudSim_Java": [{"type": "DataExposure", "severity": "MINOR", "count": 93}, {"type": "InjectionFlaws", "severity": "CRITICAL", "count": 93}, {"type": "BrokenAccessControl", "severity": "MAJOR", "count": 46}]}, 
      
      
      "defects": {"Odl_Oxygen_Java": {"codes": "33", "critical": "0", "blocker": "0", "info": "12", "major": "21", "minor": "0", "bugs": {"total": "0", "open": "0", "close": "0"}, "debts": 0}, "SNMP_Java": {"codes": "276", "critical": "18", "blocker": "0", "info": "0", "major": "134", "minor": "124", "bugs": {"total": "9", "open": "3", "close": "6"}, "debts": 4}, "CloudSim_Java": {"codes": "1665", "critical": "131", "blocker": "8", "info": "168", "major": "618", "minor": "740", "bugs": {"total": "77", "open": "20", "close": "57"}, "debts": 34}}}
    }
    getSecurityPolicyCompliance() {
        return {"passed": 75, "failed": 25}
    }

    getProjectSecurityVulnerabilitySummary() {
        return [
            {
                "type": "Critical",
                "sprints": [
                    { 
                    "sprintName": "Sprint 1",
                    "count": 20    
                    },
                    { 
                        "sprintName": "Sprint 2",
                        "count": 18    
                    },
                    { 
                    "sprintName": "Sprint 3",
                    "count": 16    
                    },
                    { 
                        "sprintName": "Sprint 4",
                        "count": 14    
                    },
                    { 
                    "sprintName": "Sprint 5",
                    "count": 10    
                    },
                    { 
                        "sprintName": "Sprint 6",
                        "count": 8    
                    }
                ]
            },
            {
                "type": "Major",
                "sprints": [
                    { 
                    "sprintName": "Sprint 1",
                    "count": 16    
                    },
                    { 
                        "sprintName": "Sprint 2",
                        "count": 14    
                    },
                    { 
                    "sprintName": "Sprint 3",
                    "count": 11    
                    },
                    { 
                        "sprintName": "Sprint 4",
                        "count": 8    
                    },
                    { 
                    "sprintName": "Sprint 5",
                    "count":5    
                    },
                    { 
                        "sprintName": "Sprint 6",
                        "count": 2   
                    }
                ]
            },
            {
                "type": "Minor",
                "sprints": [
                    { 
                    "sprintName": "Sprint 1",
                    "count": 15    
                    },
                    { 
                        "sprintName": "Sprint 2",
                        "count": 26    
                    },
                    { 
                    "sprintName": "Sprint 3",
                    "count": 20    
                    },
                    { 
                        "sprintName": "Sprint 4",
                        "count": 18    
                    },
                    { 
                    "sprintName": "Sprint 5",
                    "count": 15    
                    },
                    { 
                        "sprintName": "Sprint 6",
                        "count": 12   
                    }
                ]
            },
      
      ]
    }

    getProjectDeploymentStatistics() {
      return [
            {
               "sprintNumber": "Sprint 1" ,
                "failed": "15",
                "successful":"40"
               
            },
            {
                "sprintNumber": "Sprint 2" ,
                 "failed":"14",
                 "successful":"45"
                
            },
            {
               "sprintNumber": "Sprint 3" ,
                "failed":"12",
                "successful":"35"
            
            },
            {
                "sprintNumber": "Sprint 4" ,
                "failed":"10",
                "successful":"40"
                
            },
            {
            "sprintNumber": "Sprint 5" ,
                "failed":"8",
                "successful":"50"
            
            },
            {
                "sprintNumber": "Sprint 6" ,
                "failed":"7",
                "successful":"60"
            },
        ];
    }

    getProjectTestOptimization() {
        return [
            {
                "sprintNumber": "Sprint 1" ,
                "identified":21,
                "defects":3,
                "unimpacted":42
            },
            {
                "sprintNumber": "Sprint 2" ,
                "identified":33,
                "defects":5,
                "unimpacted":52
            },
            {
                "sprintNumber": "Sprint 3" ,
                "identified":10,
            "defects":6,
            "unimpacted":12
            },
            {
                "sprintNumber": "Sprint 4" ,
                "identified":2,
                "defects":5,
                "unimpacted":8
            },
            {
                "sprintNumber": "Sprint 5" ,
                "identified":60,
            "defects":8,
            "unimpacted":63
            },
            {
            "sprintNumber": "Sprint 6" ,
            "identified":4,
            "defects":3,
            "unimpacted":7
            },

        ] 
    }

    getProjectInfoInCards() {
        return {"risk": "HIGH", "CIA_Rating": 3, "avg_days": 4, "release_date": "01/06/2020", "days_to_go": 10}
    }

    getProjectsLiveData() {
        return [
            {
                "project":"CloudSim_Java",
                "sprints":[
                    {
                    "status":"deployed",
                    "sprintname":"sprint1"
                    },
                    {
                    "status":"failed",
                    "sprintname":"sprint2"
                    },
                    {
                    "status":"deployed",
                    "sprintname":"sprint3"
                    },
                    {
                    "status":"deployed",
                    "sprintname":"sprint4"
                    },
                    {
                    "status":"deployed",
                    "sprintname":"sprint5"
                    },
                    {
                    "status":"deployed",
                    "sprintname":"sprint6"
                    }
                ]
            },
            {
                "project":"odl_oxygen_java",
                "sprints":[
                    {
                        "status":"failed",
                        "sprintname":"sprint1"
                        },
                        {
                        "status":"deployed",
                        "sprintname":"sprint2"
                        },
                        {
                        "status":"deployed",
                        "sprintname":"sprint3"
                        },
                        {
                        "status":"deployed",
                        "sprintname":"sprint4"
                        },
                        {
                        "status":"failed",
                        "sprintname":"sprint5"
                        },
                        {
                        "status":"deployed",
                        "sprintname":"sprint6"
                        },
                ]
            },
            {
                "project":"SNMP_Java",
                "sprints":[  
                    {
                    "status":"deployed",
                    "sprintname":"sprint1"
                    },
                    {
                    "status":"deployed",
                    "sprintname":"sprint2"
                    },
                    {
                    "status":"failed",
                    "sprintname":"sprint3"
                    },
                    {
                    "status":"deployed",
                    "sprintname":"sprint4"
                    },
                    {
                    "status":"deployed",
                    "sprintname":"sprint5"
                    },
                    {
                    "status":"deployed",
                    "sprintname":"sprint6"
                    }
                ]
            }
        ]
    }

    getOverALLSecVulnerabilities() {
        return {
            
        "securityvulnerabilities": [{"type": "critical", "count": 95}, {"type": "major", "count": 46}, {"type": "minor", "count": 149}], 
        
        "projects":   {"critical": [{"project": "SNMP_Java", "count": 2}, {"project": "Odl_Oxygen_Java", "count": 0}, {"project": "CloudSim_Java", "count": 93}], "minor": [{"project": "CloudSim_Java", "count": 93}, {"project": "SNMP_Java", "count": 56}], "major": [{"project": "Odl_Oxygen_Java", "count": 0}, {"project": "SNMP_Java", "count": 0}, {"project": "CloudSim_Java", "count": 46}]}, 
        
        "vulnerabilitylist": {"critical": [{"count": 2, "name": "1InsufficientLogging"}, {"count": 10, "name": "InsufficientLogging"}, {"count": 93, "name": "InjectionFlaws"}, {"count": 9, "name": "2InjectionFlaws"}, {"count": 29, "name": "3InjectionFlaws"}], 
        
        "minor": [{"count": 93, "name": "DataExposure"}, {"count": 56, "name": "knownvulnerability"}], 
        
        "major": [{"count": 10, "name": "BrokenAccessControl2"}, {"count": 20, "name": "DataExposure"}, {"count": 46, "name": "BrokenAccessControl"}]}
    
      }
    }
}
