import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { SamsSubmissionService } from '../sams-submission.service';

export interface Submission {
  title: string;
  revision: number;
  paperId: number;
}

@Component({
  selector: 'app-submitter-submissions',
  templateUrl: './submitter-submissions.component.html',
  styleUrls: ['./submitter-submissions.component.css']
})
export class SubmitterSubmissionsComponent implements OnInit {
  displayedColumns: string[] = ['title', 'paperId', 'revision', "button"];
  paperDetails: any[];
  subscription!: Subscription;
  constructor( private samsSubmissionService: SamsSubmissionService,
    private componentInteractionService: ComponentInteractionService) { }

  ngOnInit(): void {
    this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
      if(data == COMPONENT_TYPE_MESSAGE.SUBMITTER_VIEW_INITIALIZE){
        this.setData();
      }
     });
     this.setData();
  }

  setData(): void{
    this.samsSubmissionService.getAllSubmissions().subscribe({next: (paperDetails) =>{
      let paperIdVsHighestRevision = new Map<string, number>();
      paperDetails.forEach((data: any) => {
          let existingRevision: any;
          if(paperIdVsHighestRevision.has(data.paperId)){
            existingRevision = paperIdVsHighestRevision.get(data.paperId);
          }
          let newRevision = data.revisionNo;
          if(existingRevision !== undefined){
            if(existingRevision > newRevision){
              newRevision = existingRevision;
            }
          }
          paperIdVsHighestRevision.set(data.paperId, newRevision);
      });

      paperDetails.forEach((data: any) => {
        if(paperIdVsHighestRevision.has(data.paperId) && paperIdVsHighestRevision.get(data.paperId) == data.revisionNo){
          data.latestRevision = true;
        }else{
          data.latestRevision = false;
        }
    });

    this.paperDetails = paperDetails;
    }}
    );
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
}
