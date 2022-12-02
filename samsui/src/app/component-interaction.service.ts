import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import { SessionService } from './session.service';

@Injectable({
  providedIn: 'root'
})
export class ComponentInteractionService {

  componentTypeMessage: Subject<COMPONENT_TYPE_MESSAGE> = new Subject<COMPONENT_TYPE_MESSAGE>();
  constructor(private sessionService: SessionService, private router: Router) { }

  public redirectToDefault(currentPath: any){
    let isAdmin = this.sessionService.getIsAdmin();
    let isSubmitter = this.sessionService.getIsSubmitter();
    let isPCM = this.sessionService.getIsPCM();
    let isPCC = this.sessionService.getIsPCC();
    if(isSubmitter){
      if(!["home_submitter","revise"].includes(currentPath)){
        this.router.navigate(['home_submitter']).then(() => {
          window.location.reload();
        });
      }
    }else if(isAdmin){
      
    }else if(isPCM){
      if(!["home_pcm"].includes(currentPath)){
        this.router.navigate(['home_pcm']).then(() => {
          window.location.reload();
        });
      }
    }else if(isPCC){
      if(!["home_pcc"].includes(currentPath)){
        this.router.navigate(['home_pcc']).then(() => {
          window.location.reload();
        });
      }
    }else{
      if(!["login"].includes(currentPath)){
        this.router.navigate(['login']).then(() => {
          window.location.reload();
        });
      }

    }
  }
  public loadHomePageComponentBasedOnUserType(){
    let isAdmin = this.sessionService.getIsAdmin();
    let isSubmitter = this.sessionService.getIsSubmitter();
    let isPCM = this.sessionService.getIsPCM();
    let isPCC = this.sessionService.getIsPCC();
    debugger;
    if(isSubmitter){
      this.router.navigate(['home_submitter']).then(() => {
        window.location.reload();
      });
    }else if(isAdmin){
      
    }else if(isPCM){
      this.router.navigate(['home_pcm']).then(() => {
        window.location.reload();
      });
    }else if(isPCC){
      this.router.navigate(['home_pcc']).then(() => {
        window.location.reload();
      });
    }else{
      this.router.navigate(['login']).then(() => {
        window.location.reload();
      });
    }
  }
}
export enum COMPONENT_TYPE_MESSAGE {
  SUBMITTER_VIEW_INITIALIZE,
  NOTIFICATION_VIEW_INITIALIZE,
  SUBMITTER_TAB_SUBMIT,
  SUBMITTER_TAB_VIEW,
  SUBMITTER_TAB_NOTIFICATION,
  PCC_TAB_ASSIGN_PAPERS,
  PCC_TAB_RATE_PAPERS,
  PCC_TAB_COMPLETED_PAPERS,
  PCC_TAB_NOTIFICATION,
  PCM_TAB_CHOOSE_PAPERS,
  PCM_TAB_RATE_ASSIGNED_PAPERS,
  PCM_TAB_VIEW_PAPERS,
  PCM_TAB_NOTIFICATION
}
