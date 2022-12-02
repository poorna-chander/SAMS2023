import { Component, OnInit } from '@angular/core';
import {MatDatepickerInputEvent, MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';

@Component({
  selector: 'app-admin-set-deadlines',
  templateUrl: './admin-set-deadlines.component.html',
  styleUrls: ['./admin-set-deadlines.component.css']
})
export class AdminSetDeadlinesComponent implements OnInit {
  startDate: Date;
  constructor() { }

  ngOnInit(): void {
    this.startDate = new Date(2022, 12, 1);
  }

  getdeadline2(type: string, event: MatDatepickerInputEvent<Date>){
    console.log(event.value);
  }
  getdeadline1(type: string, event: MatDatepickerInputEvent<Date>){
    console.log(event.value);
  }

  back() : void {

  }


}
