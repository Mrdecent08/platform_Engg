import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DevSecOpsService {

  constructor(
   private http:HttpClient
  ) { }

  backendURL = 'http://10.138.77.149:12399/';
  selectedProject = '';
  public isProjectDetailsPage = new BehaviorSubject<boolean>(false);

  changeisProjectDetailsPage(value) {
    this.isProjectDetailsPage.next(value);
  }

  login(obj) {
    return this.http.post(this.backendURL +'login', obj);
  }

  getProjects() {  
    return this.http.get(this.backendURL +'projects');
  }

  getOverViewData() {
    return this.http.get(this.backendURL +'overview');
  }

  getvulnerabilitiesData() {
    return this.http.get(this.backendURL + 'vulnerabilities');
  }

  getBuildStages() {
    return this.http.get(this.backendURL +'build_stages');
  }

  getProjectsData() {
      return this.http.get(this.backendURL +'projectpipelines');
  }
 
  getTestOptimizedData() {
    return this.http.get( this.backendURL+'testoptimization');
  }

  getTop10PrjByVulnerabilites() {
    return this.http.get(this.backendURL +'topvulnerableprojects');
  }
  
  getLastBuildProjects() {
    return this.http.get(this.backendURL +'lastbuild');
  }

  getToolData(tool, obj) {
    return this.http.post(this.backendURL + tool, obj);
  }

  getIndividualPrjBuildInfo(obj) {
    return this.http.post(this.backendURL +'totalbuilds', obj);
  }

  getIndividualPrjBuildDetails(obj) {
    return this.http.post(this.backendURL +'projectdetails', obj);
  }

  getBuildHistoryOfProject(obj) {  
    return this.http.post(this.backendURL +'buildhistory',obj);
  }

  getProjectVulnerability(obj) {  
    return this.http.post(this.backendURL +'projectvulnerability', obj);
  }

  getBuildTrendsData(obj) {
    return this.http.post(this.backendURL +'buildtrends', obj);
  }

  getCodeSmellDetails() {
    return this.http.get(this.backendURL +'alldefects');
  }
  getUpcomingReleases() {
    return this.http.get(this.backendURL +'upcomingreleases');
  }
  getOverALLSecVulnerabilities() {
    return this.http.get(this.backendURL +'securityvulnerabilities');
  }

  getSecurityPolicyCompliance() {
    return this.http.get(this.backendURL +'securitypolicycompliance');
  }

  getProjectInfoInCards(obj) {
    //integrated
    return this.http.post(this.backendURL +'projectrisk', obj);
  }
  getProjectSecurityVulnerabilitySummary(obj) {
    return this.http.post(this.backendURL +'securityvulnerabilitysummary', obj);
  }
  getProjectDeploymentStatistics(obj) {
    return this.http.post(this.backendURL +'deploymentstatistics', obj);
  }
  getProjectTestOptimization(obj) {
    return this.http.post(this.backendURL +'projtestoptimization', obj);
  }
  getProjvulnerability(obj) {
    return this.http.post(this.backendURL +'projvulnerability', obj);
  }
  getProjectDefects(obj) {
    return this.http.post(this.backendURL +'projdefects', obj);
  }
  
  getProjectsLiveData() {
    return this.http.get(this.backendURL +'projectlive');
  }

  getProjectCodeSmells(obj) {
    return this.http.post(this.backendURL +'projcodesmells', obj);
  }

  getProjectFeildIssues(obj) {
    return this.http.get(this.backendURL +'fieldissues');
  }
 
}
