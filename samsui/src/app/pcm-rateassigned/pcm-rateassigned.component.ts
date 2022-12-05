import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { SamsSubmissionService } from '../sams-submission.service';

@Component({
  selector: 'app-pcm-rateassigned',
  templateUrl: './pcm-rateassigned.component.html',
  styleUrls: ['./pcm-rateassigned.component.css']
})
export class PcmRateassignedComponent implements OnInit {

  displayedColumns: string[] = ['title', 'paperId', "rate"];
  subscription!: Subscription;
  paperDetails: MatTableDataSource<any>[];
  constructor( private samsSubmissionService: SamsSubmissionService,
    private componentInteractionService: ComponentInteractionService,
    private route: ActivatedRoute) {
      this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
        if(data == COMPONENT_TYPE_MESSAGE.PCM_TAB_RATE_ASSIGNED_PAPERS){
          this.setData();
        }
       });
    }

    ngOnInit(): void {
      this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
       this.setData();
    }
    
    setData(): void{
      this.samsSubmissionService.getAllAssignedPapers().subscribe({next: (paperDetails) =>{
        paperDetails.forEach((paperData: any) => {
           if(paperData.rating === null || paperData.rating === undefined){
            paperData.rating_done = false;
           }else{
            paperData.rating_done = true;
           }
        });
        this.paperDetails = paperDetails;
    }
    });
  }

}
