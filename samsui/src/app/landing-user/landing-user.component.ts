import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationsComponent } from '../notifications/notifications.component';
import { SamsSubmissionService } from '../sams-submission.service';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-landing-user',
  templateUrl: './landing-user.component.html',
  styleUrls: ['./landing-user.component.css']
})
export class LandingUserComponent implements OnInit {

  constructor(private samsSubmissionService: SamsSubmissionService,
     private sessionService: SessionService,
     public notificationComponent: NotificationsComponent,
     private router: Router) { 

     }

  ngOnInit(): void {
  }


  logOut(){
    this.sessionService.logOut();
    this.router.navigate(['/'])
            .then(() => {
                    window.location.reload();
                 });
  }

}
