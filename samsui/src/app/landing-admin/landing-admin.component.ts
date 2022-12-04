import { Component, OnInit } from '@angular/core';
import { SessionService } from '../session.service';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { NotificationsComponent } from '../notifications/notifications.component';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';


@Component({
  selector: 'app-landing-admin',
  templateUrl: './landing-admin.component.html',
  styleUrls: ['./landing-admin.component.css']
})
export class LandingAdminComponent implements OnInit {

  subscription!: Subscription;
  selectedIndex = 0;
  
  selectTab(index: number): void {
    this.selectedIndex = index;
  }

  constructor(private sessionService: SessionService,
    public notificationComponent: NotificationsComponent,
    private router: Router,
    private componentInteractionService: ComponentInteractionService,
     private route: ActivatedRoute) { 

    }

  ngOnInit(): void {
    //this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
    this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
      if(data == COMPONENT_TYPE_MESSAGE.ADMIN_TAB_SET_DEADLINE){
        this.selectTab(0);
      }else if(data == COMPONENT_TYPE_MESSAGE.ADMIN_TAB_SET_TEMPLATE){
        this.selectTab(1);
      }
     });
  }

  openTab(type: any){
    if(type.tab.textLabel == "Set Deadlines"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.ADMIN_TAB_SET_DEADLINE);
      this.selectedIndex = 0;
    }else if(type.tab.textLabel == "Set Templates"){
      this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.ADMIN_TAB_SET_TEMPLATE);
      this.selectedIndex = 1;
    }
  }

  logOut() : void{
    this.sessionService.logOut();
    this.router.navigate(['/']);
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

}
