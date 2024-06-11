import { Component, OnInit, HostListener } from '@angular/core';
import {ActivatedRoute, ParamMap} from '@angular/router';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';

@Component({
  selector: 'app-project-detail-page',
  templateUrl: './project-detail-page.component.html',
  styleUrls: ['./project-detail-page.component.scss']
})
export class ProjectDetailPageComponent implements OnInit {

  projectName;
  newPrJName;
  isModalOpen = false;
  modalInputData;

  @HostListener('document:keydown', ['$event'])
  function(event) {
    if(event.key=="Escape"){
        this.closeModalDialog();
    }
  } 

  constructor(private route: ActivatedRoute,
    private devSecOpsService: DevSecOpsService) { }

  ngOnInit() {
    this.route.paramMap.subscribe(params => {    
      this.projectName = params.get("id");
      localStorage.setItem('projectName', this.projectName);
    });
    this.devSecOpsService.changeisProjectDetailsPage(true);
  }

  openModalDialog(data) {
    this.modalInputData = data;
    this.isModalOpen = true;
  }

  closeModalDialog() {
    this.isModalOpen = false;
  }
}
