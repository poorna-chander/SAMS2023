import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { NotificationsComponent } from '../notifications/notifications.component';
import { SamsSubmissionService } from '../sams-submission.service';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-landing-pcm',
  templateUrl: './landing-pcm.component.html',
  styleUrls: ['./landing-pcm.component.css']
})
export class LandingPcmComponent implements OnInit {
  subscription!: Subscription;
  selectedIndex = 0;

  constructor(
    private sessionService: SessionService,
    public notificationComponent: NotificationsComponent,
    private router: Router,
    private componentInteractionService: ComponentInteractionService,
     private route: ActivatedRoute) { 
    }

  selectTab(index: number): void {
    this.selectedIndex = index;
  }
  
  ngOnInit(): void {
    this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
    this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
      if(data == COMPONENT_TYPE_MESSAGE.PCM_TAB_CHOOSE_PAPERS){
        this.selectTab(0);
      }else if(data == COMPONENT_TYPE_MESSAGE.PCM_TAB_RATE_ASSIGNED_PAPERS){
        this.selectTab(1);
      }else if(data == COMPONENT_TYPE_MESSAGE.PCM_TAB_VIEW_PAPERS){
        this.selectTab(2);
      }else if(data == COMPONENT_TYPE_MESSAGE.NOTIFICATION_VIEW_INITIALIZE){
        this.selectTab(3);
      }
     });
  }

  openTab(type: any){
    if(type.tab.textLabel == "Choose Papers"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.PCM_TAB_CHOOSE_PAPERS);
      this.selectedIndex = 0;
    }else if(type.tab.textLabel == "Rate Assigned Papers"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.PCM_TAB_RATE_ASSIGNED_PAPERS);
      this.selectedIndex = 1;
    }else if(type.tab.textLabel == "View Papers"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.PCM_TAB_VIEW_PAPERS);
      this.selectedIndex = 3;
    }else if(type.tab.textLabel == "Notifications"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.NOTIFICATION_VIEW_INITIALIZE);
      this.selectedIndex = 4;
    }else{
      this.selectedIndex = 2;
    }
  }

  logOut(){
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

}
