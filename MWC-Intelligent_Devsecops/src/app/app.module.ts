import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ReactiveFormsModule } from '@angular/forms';

import { NgxChartsModule } from '@swimlane/ngx-charts';
import { NgxDatatableModule } from '@swimlane/ngx-datatable';
import { RatingModule } from 'ng-starrating';
/* import {TooltipModule} from "ngx-tooltip"; */
import { TooltipModule } from 'ng2-tooltip-directive';

import { MatInputModule,
  MatButtonModule,
  MatSelectModule,
  MatToolbarModule,
  MatSidenavModule,
  MatIconModule,
  MatPaginatorModule,
  MatSortModule,
  MatTableModule,
  MatListModule,
  MatDatepickerModule,
  MatNativeDateModule } from '@angular/material';
import { OverviewComponent } from './overview/overview.component';
import { TopVulnerableProjectsComponent } from './top-vulnerable-projects/top-vulnerable-projects.component';
import { TestOptimizationComponent } from './test-optimization/test-optimization.component';
import { TopSuccesfulProjectsComponent } from './top-succesful-projects/top-succesful-projects.component';
import { DevOpsPipeLineComponent } from './dev-ops-pipe-line/dev-ops-pipe-line.component';
import { ProjectDetailPageComponent } from './project-detail-page/project-detail-page.component';
import { PdBuildInfoComponent } from './pd-build-info/pd-build-info.component';
import { PdCurrentBuildComponent } from './pd-current-build/pd-current-build.component';
import { PdVulnerabilitesComponent } from './pd-vulnerabilites/pd-vulnerabilites.component';
import { PdBuildTrendsComponent } from './pd-build-trends/pd-build-trends.component';
import { SecurityPolicyComplianceComponent } from './security-policy-compliance/security-policy-compliance.component';
import { SecurityVulnerabilityComponent } from './security-vulnerability/security-vulnerability.component';
import { CodesmellComponent } from './codesmell/codesmell.component';
import { ProjectLiveComponent } from './project-live/project-live.component';
import { UpcomingReleasesComponent } from './upcoming-releases/upcoming-releases.component';
import { PdVulBugsCodesmellComponent } from './pd-vul-bugs-codesmell/pd-vul-bugs-codesmell.component';
import { PdInfoInCardsComponent } from './pd-info-in-cards/pd-info-in-cards.component';
import { PdDeploymentStatisticsComponent } from './pd-deployment-statistics/pd-deployment-statistics.component';
import { PdTestOptimizationComponent } from './pd-test-optimization/pd-test-optimization.component';
import { PdSecVulSummaryGraphComponent } from './pd-sec-vul-summary-graph/pd-sec-vul-summary-graph.component';
import { ProjectLiveDataComponent } from './project-live-data/project-live-data.component';
import { LoginComponent } from './login/login.component';
import { ModalDialogComponent } from './modal-dialog/modal-dialog.component';

@NgModule({
  declarations: [
    AppComponent,
    DashboardComponent,
    OverviewComponent,
    TopVulnerableProjectsComponent,
    TestOptimizationComponent,
    TopSuccesfulProjectsComponent,
    DevOpsPipeLineComponent,
    ProjectDetailPageComponent,
    PdBuildInfoComponent,
    PdCurrentBuildComponent,
    PdVulnerabilitesComponent,
    PdBuildTrendsComponent,
    SecurityPolicyComplianceComponent,
    SecurityVulnerabilityComponent,
    CodesmellComponent,
    ProjectLiveComponent,
    UpcomingReleasesComponent,
    PdVulBugsCodesmellComponent,
    PdInfoInCardsComponent,
    PdDeploymentStatisticsComponent,
    PdTestOptimizationComponent,
    PdSecVulSummaryGraphComponent,
    ProjectLiveDataComponent,
    LoginComponent,
    ModalDialogComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    NgxChartsModule,
    NgxDatatableModule,
    TooltipModule,
    RatingModule,
    MatInputModule,
    MatButtonModule,
    MatSelectModule,
    MatIconModule,
    MatSidenavModule,
    MatToolbarModule,
    MatPaginatorModule,
    MatSortModule,
    MatTableModule,
    MatListModule,
    MatDatepickerModule,
    MatNativeDateModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
