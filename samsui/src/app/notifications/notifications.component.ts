import { Component, OnInit } from '@angular/core';
import { SamsSubmissionService } from '../sams-submission.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  public notifications: any[];

  constructor( private samsSubmissionService: SamsSubmissionService) { }

  ngOnInit(): void {
    this.samsSubmissionService.getNotifications().subscribe({next: (notifications) =>{
      this.notifications = notifications;
    }}
    );
  }

}
