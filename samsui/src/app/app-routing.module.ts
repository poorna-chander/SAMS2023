import { APP_INITIALIZER, Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPccComponent } from './landing-pcc/landing-pcc.component';
import { LandingPcmComponent } from './landing-pcm/landing-pcm.component';
import { LandingUserComponent } from './landing-user/landing-user.component';
import { LoginComponent } from './login/login.component';
import { SessionService } from './session.service';
import { SubmitterRevisionComponent } from './submitter-revision/submitter-revision.component';
import {PccAssignpaperComponent} from './pcc-assignpaper/pcc-assignpaper.component'
import { LandingAdminComponent } from './landing-admin/landing-admin.component';
import { RatepaperComponent } from './ratepaper/ratepaper.component';


function initializeAppFactory(sessionService: SessionService): () => any {
  let isAdmin = sessionService.getIsAdmin();
  let isSubmitter = sessionService.getIsSubmitter();
  let isPCM = sessionService.getIsPCM();
  let isPCC = sessionService.getIsPCC();
  if(isAdmin){
    changeRedirectTo("home_admin", false);
  }else if(isSubmitter){
    changeRedirectTo("home_submitter", false);
  }else if(isPCM){
    changeRedirectTo("home_pcm", false);
  }else if(isPCC){
    changeRedirectTo("home_pcc", false);
  }else{
    changeRedirectTo("login", true);
  }
  return () => "";
 }

const routes: Routes = [
  { path: '',   redirectTo: "login", pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home_submitter', component: LandingUserComponent },
  { path: 'home_pcc', component: LandingPccComponent },
  { path: 'home_pcm', component: LandingPcmComponent },
  { path: 'home_admin', component: LandingAdminComponent },
  { path: 'revise/:paperId', component: SubmitterRevisionComponent },
  { path: 'assign/:paperId', component: PccAssignpaperComponent },
  { path: 'rate/pcc/:paperId', component: RatepaperComponent },
  { path: 'rate/pcm/:paperId', component: RatepaperComponent },
];

function changeRedirectTo(path: any, isLoginNeeded: boolean): any{
  routes.forEach((route, index) => {
    if(route.path == ''){
      route.redirectTo = "/" + path;
    }
  })

}

export const routing = RouterModule.forRoot(routes);

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [{
    provide: APP_INITIALIZER,
    useFactory: initializeAppFactory,
    deps: [SessionService],
    multi: true
  }]
})
export class AppRoutingModule {
 

 }
