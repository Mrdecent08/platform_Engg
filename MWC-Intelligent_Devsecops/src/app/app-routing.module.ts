import { NgModule } from '@angular/core';
import { Routes, RouterModule, } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ProjectDetailPageComponent } from './project-detail-page/project-detail-page.component';
import { LoginComponent } from './login/login.component';
import { AuthGuard } from './auth.guard';

const routes: Routes = [
  { path:'dashboard', component : DashboardComponent},
  { path: 'project/:id', component: ProjectDetailPageComponent},
  { path:'login', component : LoginComponent },
  { path:'', redirectTo: '/login', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
