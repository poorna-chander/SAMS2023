import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { SamsSubmissionService } from '../sams-submission.service';

@Component({
  selector: 'app-pcc-completedpapers',
  templateUrl: './pcc-completedpapers.component.html',
  styleUrls: ['./pcc-completedpapers.component.css']
})
export class PccCompletedpapersComponent implements OnInit {
  displayedColumns: string[] = ['title', 'revision', 'paperId', "finalRating"];
  subscription!: Subscription;
  paperDetails = [];
  constructor( private samsSubmissionService: SamsSubmissionService,
    private componentInteractionService: ComponentInteractionService,
    private route: ActivatedRoute) { }

    ngOnInit(): void {
      this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
      this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
        if(data == COMPONENT_TYPE_MESSAGE.PCC_TAB_COMPLETED_PAPERS){
          this.setData();
        }
       });
       this.setData();
    }
  
    setData(): void{
      this.samsSubmissionService.getRatingCompletedSubmissions().subscribe({next: (paperDetails) =>{
         this.paperDetails = paperDetails;
        }}
        );
    }

}
