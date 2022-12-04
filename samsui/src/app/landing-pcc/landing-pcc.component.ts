import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { NotificationsComponent } from '../notifications/notifications.component';
import { SamsSubmissionService } from '../sams-submission.service';
import { SessionService } from '../session.service';
import {MatDatepickerInputEvent, MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';

@Component({
  selector: 'app-landing-pcc',
  templateUrl: './landing-pcc.component.html',
  styleUrls: ['./landing-pcc.component.css']
})
export class LandingPccComponent implements OnInit {
  subscription!: Subscription;
  selectedIndex = 0;
  
  selectTab(index: number): void {
    this.selectedIndex = index;
  }

  constructor(private samsSubmissionService: SamsSubmissionService,
    private sessionService: SessionService,
    public notificationComponent: NotificationsComponent,
    private router: Router,
    private componentInteractionService: ComponentInteractionService,
     private route: ActivatedRoute) { 

    }

  ngOnInit(): void {
    this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
    this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
      if(data == COMPONENT_TYPE_MESSAGE.PCC_TAB_ASSIGN_PAPERS){
        this.selectTab(0);
      }else if(data == COMPONENT_TYPE_MESSAGE.PCC_TAB_RATE_PAPERS){
        this.selectTab(1);
      }else if(data == COMPONENT_TYPE_MESSAGE.PCC_TAB_COMPLETED_PAPERS){
        this.selectTab(2);
      }else if(data == COMPONENT_TYPE_MESSAGE.NOTIFICATION_VIEW_INITIALIZE){
        this.selectTab(3);
      }
     });
  }

  openTab(type: any){
    if(type.tab.textLabel == "Assign Papers"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.PCC_TAB_ASSIGN_PAPERS);
      this.selectedIndex = 0;
    }else if(type.tab.textLabel == "Rate Papers"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.PCC_TAB_RATE_PAPERS);
      this.selectedIndex = 1;
    }else if(type.tab.textLabel == "Completed Papers"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.PCC_TAB_COMPLETED_PAPERS);
      this.selectedIndex = 2;
    }else if(type.tab.textLabel == "Notifications"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.NOTIFICATION_VIEW_INITIALIZE);
      this.selectedIndex = 3;
    }
  }

  logOut(){
    this.sessionService.logOut();
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

}
