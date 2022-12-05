import { Component, OnInit } from '@angular/core';
import {MatDatepickerInputEvent, MatDatepickerModule} from '@angular/material/datepicker';
import { MatNativeDateModule, MatRippleModule } from '@angular/material/core';
import { SamsSubmissionService } from '../sams-submission.service';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-admin-set-deadlines',
  templateUrl: './admin-set-deadlines.component.html',
  styleUrls: ['./admin-set-deadlines.component.css']
})
export class AdminSetDeadlinesComponent implements OnInit {
  paperSubmissionDeadline: Date;
  reviewSubmissionDeadline: Date;
  currentDate: Date;
  subscription!: Subscription;
  constructor( private samsSubmissionService: SamsSubmissionService,
    private componentInteractionService: ComponentInteractionService,
    private route: ActivatedRoute) {
      this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
        if(data == COMPONENT_TYPE_MESSAGE.ADMIN_TAB_SET_DEADLINE){
          this.setData();
        }
       });
    }

  ngOnInit(): void {
    this.paperSubmissionDeadline = new Date();
    this.reviewSubmissionDeadline = new Date();
    this.currentDate = new Date();
    this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
    this.setData();
  }

  setData(): void{
    this.samsSubmissionService.getDeadlines().subscribe({next: (deadlineData) =>{
      this.paperSubmissionDeadline = new Date(parseInt(deadlineData["PAPER_SUBMISSION_DEADLINE"]));
      this.reviewSubmissionDeadline = new Date(parseInt(deadlineData["REVIEW_SUBMISSION_DEADLINE"]));
  }
  });
}

  submit(): void{
    this.samsSubmissionService.updateDeadline("PAPER_SUBMISSION_DEADLINE", this.paperSubmissionDeadline.getTime()).subscribe({next: (deadlineData) =>{
      this.samsSubmissionService.updateDeadline("REVIEW_SUBMISSION_DEADLINE", this.reviewSubmissionDeadline.getTime()).subscribe({next: (deadlineData) =>{
        window.location.reload();
      }
      });
  }
  });
  }

  back() : void {

  }


}
