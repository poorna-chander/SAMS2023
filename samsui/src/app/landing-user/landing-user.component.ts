import { Component, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { NotificationsComponent } from '../notifications/notifications.component';
import { SamsSubmissionService } from '../sams-submission.service';
import { SessionService } from '../session.service';
import { SubmitterRevisionComponent } from '../submitter-revision/submitter-revision.component';

@Component({
  selector: 'app-landing-user',
  templateUrl: './landing-user.component.html',
  styleUrls: ['./landing-user.component.css']
})
export class LandingUserComponent implements OnInit {
  selectedIndex = 1;
  subscription!: Subscription;

  selectTab(index: number): void {
    this.selectedIndex = index;
  }

  constructor(private samsSubmissionService: SamsSubmissionService,
     private sessionService: SessionService,
     private componentInteractionService: ComponentInteractionService,
     private router: Router) { 

     }

  ngOnInit(): void {
    this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
      debugger;
      if(data == COMPONENT_TYPE_MESSAGE.SUBMITTER_TAB_SUBMIT){
        this.selectTab(0);
      }else if(data == COMPONENT_TYPE_MESSAGE.SUBMITTER_TAB_VIEW){
        this.selectTab(1);
      }else if(data == COMPONENT_TYPE_MESSAGE.SUBMITTER_TAB_NOTIFICATION){
        this.selectTab(2);
      }
     });
  }

  openTab(type: any){
    if(type.tab.textLabel == "View Papers"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.SUBMITTER_VIEW_INITIALIZE);
    }else if(type.tab.textLabel == "Notifications"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.NOTIFICATION_VIEW_INITIALIZE);
    }
  }

  logOut(){
    this.sessionService.logOut();
    this.router.navigate(['/'])
            .then(() => {
                    window.location.reload();
                 });
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
