import { Component, OnInit } from '@angular/core';
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
     private notificationsComponent: NotificationsComponent,
     private sessionService: SessionService,
     private router: Router) { }

  ngOnInit(): void {
  }

  tabClick(tab: any) {
    console.log(tab);
    if(tab.tab.textLabel === "Notifications"){
      this.samsSubmissionService.getNotifications().subscribe({next: (notifications) =>{
        this.notificationsComponent.notifications = notifications;
      }}
      );
    }
  }

  logOut(){
    this.sessionService.logOut();
    this.router.navigate(['/'])
            .then(() => {
                    window.location.reload();
                 });
  }

}
