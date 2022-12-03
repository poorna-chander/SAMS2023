import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { SamsSubmissionService } from '../sams-submission.service';
// import { SamsPCCService } from '../sams-submission.service';


export interface Submission {
  title: string;
  revision: number;
  paperId: number;
}

const SubmissionData: Submission[] = [
  {title: "model driven development", revision: 5, paperId: 1},
  {title: "collaborative software development", revision: 5, paperId: 1},
  {title: "software construction", revision: 5, paperId: 1},
  {title: "SMAR", revision: 5, paperId: 1}
];

@Component({
  selector: 'app-pcc-ratepaper',
  templateUrl: './pcc-ratepaper.component.html',
  styleUrls: ['./pcc-ratepaper.component.css']
})
export class PccRatepaperComponent implements OnInit {
  displayedColumns: string[] = ['title', 'revision', 'paperId', "rate"];
  paperDetails: MatTableDataSource<any>[];
  subscription!: Subscription;

  constructor( private samsSubmissionService: SamsSubmissionService,
    private componentInteractionService: ComponentInteractionService,
    private route: ActivatedRoute) { }

    ngOnInit(): void {
      this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
      this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
        if(data == COMPONENT_TYPE_MESSAGE.PCC_TAB_RATE_PAPERS){
          this.setData();
        }
       });
       this.setData();
    }
  
    setData(): void{
      this.samsSubmissionService.getAllPCMRatedPapers().subscribe({next: (paperDetails) =>{
        debugger;
         this.paperDetails = paperDetails;
        }}
        );
    }

    ngOnDestroy(): void {
      this.subscription?.unsubscribe();
    }

}
