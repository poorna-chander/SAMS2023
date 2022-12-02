import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationsComponent } from '../notifications/notifications.component';
import { SamsSubmissionService } from '../sams-submission.service';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-landing-pcm',
  templateUrl: './landing-pcm.component.html',
  styleUrls: ['./landing-pcm.component.css']
})
export class LandingPcmComponent implements OnInit {

  ngOnInit(): void {
  }

  constructor(
              private sessionService: SessionService,
              public notificationComponent: NotificationsComponent,
              private router: Router) { 
              }

  logOut(){
    this.sessionService.logOut();
    this.router.navigate(['/'])
            .then(() => {
                    window.location.reload();
                 });
  }

}
