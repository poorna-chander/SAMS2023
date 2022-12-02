import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ComponentInteractionService } from '../component-interaction.service';
import { SamsSubmissionService } from '../sams-submission.service';
import { SessionService } from '../session.service';

/** @title Form field appearance variants */
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit{
  username: string = '';
  password: string = '';
  user_type: string = '';
  isInvalid: boolean = false;
  isLoginPage: boolean = true;
  public userDetails: any;

  constructor( private samsSubmissionService: SamsSubmissionService, private route: ActivatedRoute, private router: Router, private sessionService: SessionService, private componentInteraction : ComponentInteractionService) { }
  
  ngOnInit(){
    this.componentInteraction.redirectToDefault(this.route.snapshot.url[0].path);
  }

  getErrorMessage() {
    if (this.username.trim() === "") {
      return 'You must enter username';
    }

    if (this.password.trim() === "") {
      return 'You must enter password';
    }

    return "Invalid Credentials";
  }
  
  registerPage(){
    this.username = "";
    this.password = "";
    this.isInvalid = false;
    this.isLoginPage = false;
  }

  loginPage(){
    this.username = "";
    this.password = "";
    this.isInvalid = false;
    this.isLoginPage = true;
  }

  login() {
    if (this.username.trim() !== '' && this.password.trim() !== '') {
      this.samsSubmissionService
        .authenticateUser(this.username, this.password)
        .subscribe({
          next: (userDetails) => {
            console.log(userDetails);
            this.userDetails = userDetails;
            if (
              userDetails.hasOwnProperty('status') &&
              userDetails.status == 'failure'
            ) {
              this.isInvalid = true;
              this.sessionService.logInGuest();
            } else {
              this.isInvalid = false;
              this.user_type = userDetails.type;
              this.sessionService.logIn(
                userDetails.username,
                userDetails.user_id,
                this.user_type
              );
              this.router.navigate(['home_submitter']).then(() => {
                window.location.reload();
              });
            }
          },
        });
    }else{
      this.isInvalid = true;
    }
  }

  register() {
    if (this.username.trim() !== '' && this.password.trim() !== '') {
      this.samsSubmissionService
        .registerUser(this.username, this.password, "SUBMITTER")
        .subscribe({
          next: (userDetails) => {
            console.log(userDetails);
            this.userDetails = userDetails;
            if (
              userDetails.hasOwnProperty('status') &&
              userDetails.status == 'failure'
            ) {
              this.isInvalid = true;
            } else {
              this.isInvalid = false;
              this.router.navigate(['login']).then(() => {
                window.location.reload();
              });
            }
          },
        });
    }else{
      this.isInvalid = true;
    }
  }
}


