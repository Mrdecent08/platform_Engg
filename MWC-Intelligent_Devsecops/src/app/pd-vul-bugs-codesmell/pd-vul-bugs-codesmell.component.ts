import { Component, OnInit, Input, ViewEncapsulation} from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-pd-vul-bugs-codesmell',
  templateUrl: './pd-vul-bugs-codesmell.component.html',
  styleUrls: ['./pd-vul-bugs-codesmell.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class PdVulBugsCodesmellComponent implements OnInit {

  @Input() projectName;

  constructor(
    private devSecOpsService: DevSecOpsService,
    private mockDevSecOpsService : MockDevSecOpsService
  ) { }

  projectDetails;
  buildData;
  activeTab = 'secVul';
  rows=[];
  isLoading = true;
  error = false;
  filterData;
  rowsPerPage = 10;
  recperpage = [5,10,15,20];
  totalPages;
  currentPage = 1;
  projVulnerabilityData;
  totalRecords ;
  keysOfTable;
  sortOrder = true;
  
  overallKeyObjs = {
    "vulnerabilities" : {
      "id":   {
        "key": "id",
        "displayName": "ID",
        "width": "50",
        "isVisible": false,
        "importance": 1
      },
      "name": {
        "key": "name",
        "displayName": "Name",
        "width": "300",
        "isVisible": true,
        "importance": 2
      },        
      "type":   {
        "key": "type",
        "displayName": "Type",
        "width": "100",
        "isVisible": true,
        "importance": 3
      },
      "assetsaffected": {
        "key": "assetsaffected",
        "displayName": "Assets Affected",
        "width": "100",
        "isVisible": false,
        "importance": 4
      },
      "vulnerabilitydate": {
        "key": "vulnerabilitydate",
        "displayName": "Vulnerability Date",
        "width": "130",
        "isVisible": true,
        "importance": 5
      },
      "datefixed":  {
        "key": "datefixed",
        "displayName": "Date Fixed",
        "width": "150",
        "isVisible": false,
        "importance": 6
      },
    },
    "defects" : {
      "name": {
        "key": "name",
        "displayName": "Name",
        "width": "400",
        "isVisible": true,
        "importance": 1
      },
      "date": {
        "key": "date",
        "displayName": "Defect Date",
        "width": "200",
        "isVisible": true,
        "importance": 2
      },
      "severity":{
        "key": "severity",
        "displayName": "Severity",
        "width": "75",
        "isVisible": true,
        "importance": 3
      }, 
      "status": {
        "key": "status",
        "displayName": "Status",
        "width": "75",
        "isVisible": true,
        "importance": 4
      },
      "effort": {
        "key": "effort",
        "displayName": "Effort",
        "width": "75",
        "isVisible": false,
        "importance": 5
      },
    },
    "code_smells" : {
      "name": {
        "key": "name",
        "displayName": "Name",
        "width": "500",
        "isVisible": true,
        "importance": 1
      },
      "severity":{
        "key": "severity",
        "displayName": "Severity",
        "width": "75",
        "isVisible": true,
        "importance": 2
      }, 
      "status": {
        "key": "status",
        "displayName": "Status",
        "width": "75",
        "isVisible": true,
        "importance": 3
      },
      "effort": {
        "key": "effort",
        "displayName": "Effort",
        "width": "75",
        "isVisible": false,
        "importance": 4
      },
    }
  };
  keysObject = {};
  /* keysObject = {
    "id":   {
      "key": "id",
      "displayName": "ID",
      "width": "50",
      "importance": 1
    },
    "name": {
      "key": "name",
      "displayName": "Name",
      "width": "300",
      "importance": 2
    },        
    "type":   {
      "key": "type",
      "displayName": "Type",
      "width": "100",
      "importance": 3
    },
    "assetsaffected": {
      "key": "assetsaffected",
      "displayName": "Assets Affected",
      "width": "100",
      "importance": 4
    },
    "vulnerabilitydate": {
      "key": "vulnerabilitydate",
      "displayName": "Vulnerability Date",
      "width": "130",
      "importance": 5
    },
    "datefixed":  {
      "key": "datefixed",
      "displayName": "Date Fixed",
      "width": "150",
      "importance": 6
    },
    "date": {
      "key": "date",
      "displayName": "Date",
      "width": "100",
      "importance": 7
    },
    "effort": {
      "key": "effort",
      "displayName": "Effort",
      "width": "100",
      "importance": 8
    },
    "severity":{
      "key": "severity",
      "displayName": "Severity",
      "width": "150",
      "importance": 9
    }, 
    "status": {
      "key": "status",
      "displayName": "Status",
      "width": "150",
      "importance": 10
    }   
  }; */
      tableHeaderObj = [];
  countOfTabs = {
    /* "secVulnerabilites" : */ 
  }
  projectDefectsData;
  projectCodeSmellsData;
  ngOnInit() {
    this.getData();
  }

  ngOnChanges(changes) {
    if(!changes['projectName'].firstChange) {
      this.getData();
    }
  }

  getData() {
    let obj = {"name" : this.projectName};
    this.devSecOpsService.getProjvulnerability(obj).subscribe((response)=>{      
     this.projVulnerabilityData = response;
     this.totalRecords =  this.projVulnerabilityData["vulnerabilities"];
     if(this.totalRecords.length > 0) {
      this.countOfTabs["secVulnerabilites"] = this.projVulnerabilityData["vulnerabilities"].length;
      this.keysObject = this.overallKeyObjs["vulnerabilities"];
      this.keysOfTable = Object.keys(this.totalRecords[0]);
      this.prepareTableHeaderObj();
      this.sortTheTable('type', true);
     }
     this.isLoading = false;
    }, (error)=> {
      this.isLoading = false;
      this.error = true;
    }); 
    this.devSecOpsService.getProjectDefects(obj).subscribe((response)=>{      
      this.projectDefectsData = response;
      this.countOfTabs["defects"] = this.projectDefectsData["defects"].length;
    });
    this.devSecOpsService.getProjectCodeSmells(obj).subscribe((response)=>{    
      this.projectCodeSmellsData = response;
      this.countOfTabs["code_smells"] = this.projectCodeSmellsData["code_smells"].length;
    });

    // Uncomment this to get data from MockService
    /* this.buildData = this.mockDevSecOpsService.getIndividualPrjBuildInfo(this.projectName);
    this.projectDetails = this.mockDevSecOpsService.getIndividualPrjBuildDetails(this.projectName); */

  }

  prepareTableHeaderObj() {
    this.tableHeaderObj = [];
    for(let i=0; i<this.keysOfTable.length; i++) {
      if( (this.keysOfTable[i] == 'rowIndex')) {
        continue;
      } else {
        let resObj = this.keysObject[this.keysOfTable[i]];
        if(resObj == undefined && (this.keysOfTable[i] != 'rowIndex')) {
          resObj = {};
          resObj["key"] = this.keysOfTable[i];
          resObj["displayName"] = this.keysOfTable[i];
          resObj["width"] = '150';
          resObj['isVisible'] = true;
          resObj["importance"] = 12;
        }
        //Push into table header only if it is to be visible to user
        if(resObj['isVisible'] == true) {
          this.tableHeaderObj.push(resObj);
        }
      }
    }
    this.tableHeaderObj.sort((a,b) => {return a["importance"] - b["importance"]});
  }

  setTheTab(tabName) {
    this.activeTab = tabName;
    if(tabName == 'defects'){
      this.totalRecords =  this.projectDefectsData["defects"];
      this.keysObject = this.overallKeyObjs["defects"];
      this.sortTheTable('severity', true);
    } else if (tabName == 'secVul') {
      this.totalRecords =  this.projVulnerabilityData["vulnerabilities"];
      this.keysObject = this.overallKeyObjs["vulnerabilities"];
      this.sortTheTable('type', true);
    } else if (tabName == 'codeSmell') {
      this.totalRecords =  this.projectCodeSmellsData["code_smells"];
      this.keysObject = this.overallKeyObjs["code_smells"];
      this.sortTheTable('severity', true);
    }
    if(this.totalRecords.length > 0) {
      this.keysOfTable = Object.keys(this.totalRecords[0]);
    } else {
      this.keysOfTable = [];
    }
    this.prepareTableHeaderObj();
    /* this.currentPage = 1;
    this.calculatePages(); */
  }

 /*  updateFilter(event, colName, innerColname) {
    let searchValue = event.target.value.toLowerCase();
    if (!searchValue) {
      this.rows = this.filterData;
    }
    let temp
    if (innerColname) {
       temp = this.filterData.filter(function(currItem) {
        return currItem[colName][innerColname].toLowerCase().indexOf(searchValue) !== -1 || !searchValue;
      });
    } else {
       temp = this.filterData.filter(function(currItem) {
        return currItem[colName].toLowerCase().indexOf(searchValue) !== -1 || !searchValue;
      });
    }

    this.rows = temp;
  } */
  calculatePages() {
    this.totalPages = ((this.totalRecords.length % this.rowsPerPage) == 0) ? (this.totalRecords.length / this.rowsPerPage): Math.ceil(this.totalRecords.length /this.rowsPerPage);
    this.paginate();
  }
  

  updateRecPerPage(page) {
    this.rowsPerPage = page;
    this.currentPage = 1;
    this.calculatePages();
  }

  paginate() {
    if(this.totalRecords.length > 0) {
      let resRows =  this.totalRecords.slice(((this.currentPage-1)*this.rowsPerPage), ((this.currentPage)*this.rowsPerPage));
      this.rows = resRows;
    } else {
      this.rows = [];
    }
  }

  previous() {
    this.currentPage -= 1;
    this.paginate();
  }
  next() {
    this.currentPage += 1;
    this.paginate();
  }

  getRowClass(row) {
    if(row.rowIndex % 2 === 0) {
      return 'tableRow evenRow'
    } else {
      return 'tableRow'
    }
  }

  sortTheTable(columnName, sortOrder) {
    //From table always send false
    //From TS send 1st time so send true and goes into else condition and sortOrder true(ascending)
    //If condition from table so always toggle between Ascending and Descending
    if(!sortOrder) {
      console.log("hi before", this.sortOrder)
      this.sortOrder = !this.sortOrder;
      console.log("hi after", this.sortOrder)
    } else {
      this.sortOrder = true
    }
    console.log("hiiii sort", this.sortOrder, columnName)
    if(this.totalRecords.length > 1) {
      this.totalRecords.sort((a,b) => {
        const firstObj = a[columnName].toUpperCase();
        const secondObj = b[columnName].toUpperCase();
  
        let comparison = 0;
        if (firstObj > secondObj) {
          comparison = this.sortOrder ? 1 :  -1;
        } else if (firstObj < secondObj) {
          comparison =  this.sortOrder ? -1 :  1;
        }
        return comparison;
      });
    }
    this.currentPage = 1;
    this.calculatePages();
  }
}
