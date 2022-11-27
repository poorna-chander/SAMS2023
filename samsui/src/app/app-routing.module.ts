import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingUserComponent } from './landing-user/landing-user.component';
import { LoginComponent } from './login/login.component';
import { SubmitterFormComponent } from './submitter-form/submitter-form.component';
import { SubmitterRevisionComponent } from './submitter-revision/submitter-revision.component';
import { SubmitterSubmissionsComponent } from './submitter-submissions/submitter-submissions.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'landing', component: LandingUserComponent },
  { path: 'revise/:paperId', component: SubmitterRevisionComponent }
];


export const routing = RouterModule.forRoot(routes);

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
