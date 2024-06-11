import { Component, OnInit } from '@angular/core';
import { DevSecOpsService } from '../service.component';
import { MockDevSecOpsService } from '../mockService.component';
import { FormBuilder, Validators, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  loginForm: FormGroup;
  loginError;

  constructor(
    private devSecOpsService: DevSecOpsService, 
    private fb: FormBuilder,
    private router: Router) { }

  ngOnInit() {
    localStorage.setItem("isLoggedIn", 'false');
    this.loginForm = this.fb.group({
      username: ['srinivas', Validators.required],
      password: ['tcs123', Validators.required],
    });
  }

  get username() { 
    return this.loginForm.get('username'); 
  }
  get password() { 
    return this.loginForm.get('password'); 
  }

  onLogin(loginData) {
    this.devSecOpsService.login(loginData).subscribe(
      (response: any) => {
        if(response["job"] == true) {
          this.loginError = false;
        //  this.storage.set("isLoggedIn", "true");
          localStorage.setItem("isLoggedIn", 'true');
         // this.loginService.isLoggedIn = true;
          if(localStorage.getItem("redirectUrl")) {
            this.router.navigate([localStorage.getItem("redirectUrl")]);
          } else {
            this.router.navigate(['/dashboard']);
          }
        } else {
          this.loginError = true;
         // this.loginService.isLoggedIn = false;
        }       
      }, (error) => {

      });
  }

}
