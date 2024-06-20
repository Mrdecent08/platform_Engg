import { Component, ViewEncapsulation, OnInit, OnDestroy } from '@angular/core';
import { DevSecOpsService } from './service.component';
import { MockDevSecOpsService } from './mockService.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent implements OnInit, OnDestroy {
  expandDropDown;
  lastBuildData;
  projectsArray ;
  isProjectDetailsPageSubscription;
  isDetailsPage = false;
  constructor(
    private devSecOpsService: DevSecOpsService, 
    private router: Router,
    private mockDevSecOpsService : MockDevSecOpsService
  ) { 

    this.isProjectDetailsPageSubscription = this.devSecOpsService.isProjectDetailsPage.subscribe(value => {
      this.isDetailsPage = value;
    });

  }

  openDropdown() {
    this.expandDropDown = !this.expandDropDown;
  }
  
  ngOnInit() {
    localStorage.setItem('projectName', '');
    this.devSecOpsService.getProjects().subscribe((response)=>{      
      this.projectsArray = response;
    });

    // Uncomment this to get data from MockService
    /* this.projectsArray = this.mockDevSecOpsService.getProjects(); */
  }

  goToProjectDetailsPage(projectName) {
    localStorage.setItem('projectName', projectName);
    this.expandDropDown = !this.expandDropDown;
    this.devSecOpsService.changeisProjectDetailsPage(true);
    this.router.navigate(['/project/'+projectName]);
  }
  isLogedIn() {
    if(localStorage.getItem('isLoggedIn') == 'true'){
      return true;
    } else {
      return false;
    }
  }
  logout() {
    localStorage.setItem("isLoggedIn", 'false');
    localStorage.setItem('projectName', '');
    this.devSecOpsService.changeisProjectDetailsPage(false);
    this.router.navigate(['/login']);
  }
  getProjectName() {
    if (localStorage.getItem('projectName')){
      return true;
    } else {
      return false;
    }
  }

 

  goToDashBoard() {
    localStorage.setItem('projectName', '');
    this.devSecOpsService.changeisProjectDetailsPage(false);
    this.router.navigate(['/dashboard']);
  }

  ngOnDestroy() {
    if (this.isProjectDetailsPageSubscription) {
      this.isProjectDetailsPageSubscription.unsubscribe();
    }
  }
  
}
