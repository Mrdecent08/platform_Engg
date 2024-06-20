import { Component, OnInit, HostListener } from '@angular/core';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  modalInputData;
  isModalOpen = false;
  @HostListener('document:keydown', ['$event'])
  function(event) {
    if(event.key=="Escape"){
        this.closeModalDialog();
    }
  }

  constructor() { }
  
  ngOnInit() {
    localStorage.setItem('projectName', '');
  }

  openModalDialog(data) {
    this.modalInputData = data;
    this.isModalOpen = true;
  }

  closeModalDialog() {
    this.isModalOpen = false;
  }

}
