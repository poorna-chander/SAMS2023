import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NotificationsComponent } from '../notifications/notifications.component';
import { SamsSubmissionService } from '../sams-submission.service';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-landing-pcc',
  templateUrl: './landing-pcc.component.html',
  styleUrls: ['./landing-pcc.component.css']
})
export class LandingPccComponent implements OnInit {

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
