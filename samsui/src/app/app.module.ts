import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {MatDividerModule} from '@angular/material/divider';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { MatFormFieldModule, MAT_FORM_FIELD_DEFAULT_OPTIONS} from '@angular/material/form-field';
import { LoginComponent } from './login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import {FormBuilder} from '@angular/forms';
// import {MaterialExampleModule} from '../material.module';
import {MatInputModule} from '@angular/material/input'
import {MatButtonModule} from '@angular/material/button';
import { LandingUserComponent } from './landing-user/landing-user.component';
import {MatTabsModule} from '@angular/material/tabs';
import { SubmitterFormComponent } from './submitter-form/submitter-form.component';
import {MatCardModule} from '@angular/material/card';
import {MatRadioModule} from '@angular/material/radio';
import { SubmitterSubmissionsComponent } from './submitter-submissions/submitter-submissions.component';
import { AngularMaterialModule } from './angular-material.module';
import { SubmitterRevisionComponent } from './submitter-revision/submitter-revision.component';
import { HttpClientModule } from '@angular/common/http';
import {MatTableModule} from '@angular/material/table';
import { NotificationsComponent } from './notifications/notifications.component';
import { LandingPccComponent } from './landing-pcc/landing-pcc.component';
import { PccViewallpapersComponent } from './pcc-viewallpapers/pcc-viewallpapers.component';
import { PccRatepaperComponent } from './pcc-ratepaper/pcc-ratepaper.component';
import { PccCompletedpapersComponent } from './pcc-completedpapers/pcc-completedpapers.component';
import { PccNotificationsComponent } from './pcc-notifications/pcc-notifications.component';
import { LandingPcmComponent } from './landing-pcm/landing-pcm.component';
import { PcmChoosepaperComponent } from './pcm-choosepaper/pcm-choosepaper.component';
import { PcmRateassignedComponent } from './pcm-rateassigned/pcm-rateassigned.component';
import { PcmNotificationsComponent } from './pcm-notifications/pcm-notifications.component';
import { PccAssignpaperComponent } from './pcc-assignpaper/pcc-assignpaper.component';
import {MatCheckboxModule} from '@angular/material/checkbox';
import { RatepaperComponent } from './ratepaper/ratepaper.component';
import { LandingAdminComponent } from './landing-admin/landing-admin.component';
import { AdminSetDeadlinesComponent } from './admin-set-deadlines/admin-set-deadlines.component';
import {MatDatepickerInputEvent, MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    LandingUserComponent,
    SubmitterFormComponent,
    SubmitterSubmissionsComponent,
    SubmitterRevisionComponent,
    NotificationsComponent,
    LandingPccComponent,
    PccViewallpapersComponent,
    PccRatepaperComponent,
    PccCompletedpapersComponent,
    PccNotificationsComponent,
    LandingPcmComponent,
    PcmChoosepaperComponent,
    PcmRateassignedComponent,
    PcmNotificationsComponent,
    PccAssignpaperComponent,
    RatepaperComponent,
    LandingAdminComponent,
    AdminSetDeadlinesComponent
  ],
  imports: [
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatTableModule,
    MatSlideToggleModule,
    MatDividerModule,
    MatDatepickerModule,
    MatNativeDateModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatTabsModule,
    MatCardModule,
    MatRadioModule,
    MatCheckboxModule,
    AngularMaterialModule,
    MatRippleModule
    // MaterialExampleModule
  ],
  providers: [
    {provide: MAT_FORM_FIELD_DEFAULT_OPTIONS, useValue: {appearance: 'outline'}},
    NotificationsComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
