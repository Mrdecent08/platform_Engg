import { Component, OnInit, Output, Input, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-modal-dialog',
  templateUrl: './modal-dialog.component.html',
  styleUrls: ['./modal-dialog.component.scss']
})
export class ModalDialogComponent implements OnInit {

  @Output() modalDialogClose = new EventEmitter();
  headers;
  responseObj;
  //@Input() inputData;
  @Input()
  set inputData(inputData: string) {
    if(inputData.length > 0) {
      this.responseObj = inputData;
      this.headers = Object.keys(this.responseObj[0]);
      this.prepareTableHeaderObj();
      console.log("hi headers",this.headers )
    }
  }

  
  displayHeaders = {
      "issue_id":   {
        "key": "issue_id",
        "displayName": "Issue ID",
        "width": "50",
        "isVisible": true,
        "importance": 1
      },
      "vulnerability": {
        "key": "vulnerability",
        "displayName": "Vulnerability",
        "width": "300",
        "isVisible": true,
        "importance": 2
      },        
      "status":   {
        "key": "status",
        "displayName": "Status",
        "width": "100",
        "isVisible": true,
        "importance": 3
      },
      "Date": {
        "key": "Date",
        "displayName": "Date",
        "width": "100",
        "isVisible": true,
        "importance": 4
      },
      /* "assetsaffected": {
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
      }, */
    
  };
  tableHeaderObj = [];
  displayHeadersNew = {
    "vulnerability": "Vulnerability",
    "description": "Description",
    "created_on": "Created On",
    "feature_affected": "Features Affected",
    "issue_id": "Issue ID",
    "rowIndex": "#"
  };
  constructor() { }

  ngOnInit() {

  }

  prepareTableHeaderObj() {
    this.tableHeaderObj = [];
    for(let i=0; i<this.headers.length; i++) {
      if( (this.headers[i] == 'rowIndex')) {
        continue;
      } else {
        let resObj = this.displayHeaders[this.headers[i]];
        if(resObj == undefined && (this.headers[i] != 'rowIndex')) {
          resObj = {};
          resObj["key"] = this.headers[i];
          resObj["displayName"] = this.headers[i];
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
    console.log("hi tableHeaderObj", this.tableHeaderObj);
  }

  closeModal() {
    this.modalDialogClose.emit(true);
  }

}
