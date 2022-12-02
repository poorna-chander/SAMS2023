import { Component, OnInit } from '@angular/core';
import { SessionService } from '../session.service';
import {MatDatepickerInputEvent, MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';


@Component({
  selector: 'app-landing-admin',
  templateUrl: './landing-admin.component.html',
  styleUrls: ['./landing-admin.component.css']
})
export class LandingAdminComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  logOut() : void{

  }

}
