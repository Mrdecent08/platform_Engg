import { Component, OnInit } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss']
})
export class OverviewComponent implements OnInit {

  date = new Date(2020, 1, 24,);
  overViewDetails;
  vulnerabilitiesData

  constructor(private devSecOpsService: DevSecOpsService, private mockDevSecOpsService: MockDevSecOpsService) { }

  ngOnInit() {

    this.devSecOpsService.getOverViewData().subscribe((response)=>{      
      this.overViewDetails = response;
    });
    
    this.devSecOpsService.getvulnerabilitiesData().subscribe((response)=>{      
      this.vulnerabilitiesData = response;
    });

   // Uncomment this to get data from MockService
    /* this.overViewDetails = this.mockDevSecOpsService.getOverViewData();
    this.vulnerabilitiesData = this.mockDevSecOpsService.getvulnerabilitiesData(); */
  }



}
