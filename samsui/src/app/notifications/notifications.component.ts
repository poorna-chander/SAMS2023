import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { SamsSubmissionService } from '../sams-submission.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {
  public notifications: any[];
  subscription!: Subscription;

  constructor( private samsSubmissionService: SamsSubmissionService,
    private componentInteractionService: ComponentInteractionService) {
      this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
        if(data == COMPONENT_TYPE_MESSAGE.NOTIFICATION_VIEW_INITIALIZE){
          this.setData();
        }
       });
     }

  ngOnInit(): void {
   
  }

  setData(): void{
    this.samsSubmissionService.getNotifications().subscribe({next: (notifications) =>{
      this.notifications = notifications;
    }}
    );
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

}
