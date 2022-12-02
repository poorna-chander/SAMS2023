import { APP_INITIALIZER, Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingUserComponent } from './landing-user/landing-user.component';
import { LoginComponent } from './login/login.component';
import { SessionService } from './session.service';
import { SubmitterRevisionComponent } from './submitter-revision/submitter-revision.component';


function initializeAppFactory(sessionService: SessionService): () => any {
  let isAdmin = sessionService.getIsAdmin();
  let isSubmitter = sessionService.getIsSubmitter();
  let isPCM = sessionService.getIsPCM();
  let isPCC = sessionService.getIsPCC();
  if(isAdmin){
    changeRedirectTo("", false);
  }else if(isSubmitter){
    changeRedirectTo("home_submitter", false);
  }else if(isPCM){
    changeRedirectTo("", false);
  }else if(isPCC){
    changeRedirectTo("", false);
  }else{
    changeRedirectTo("login", true);
  }
  return () => "";
 }

const routes: Routes = [
  { path: '',   redirectTo: "login", pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'home_submitter', component: LandingUserComponent },
  { path: 'revise/:paperId', component: SubmitterRevisionComponent }
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
