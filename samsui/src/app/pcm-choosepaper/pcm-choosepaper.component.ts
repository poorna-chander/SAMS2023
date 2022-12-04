import { Component, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { SamsSubmissionService } from '../sams-submission.service';

@Component({
  selector: 'app-pcm-choosepaper',
  templateUrl: './pcm-choosepaper.component.html',
  styleUrls: ['./pcm-choosepaper.component.css']
})
export class PcmChoosepaperComponent implements OnInit {
  displayedColumns: string[] = ['title', 'revision', 'paperId', "choose"];
  subscription!: Subscription;
  paperDetails: MatTableDataSource<any>[];
  constructor( private samsSubmissionService: SamsSubmissionService,
    private componentInteractionService: ComponentInteractionService,
    private route: ActivatedRoute) {
      this.subscription = this.componentInteractionService.componentTypeMessage.subscribe((data: COMPONENT_TYPE_MESSAGE) => {
        if(data == COMPONENT_TYPE_MESSAGE.PCM_TAB_CHOOSE_PAPERS){
          this.setData();
        }
       });
    }


  ngOnInit(): void {
    this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
     this.setData();
  }
  
  setData(): void{
    this.samsSubmissionService.getAllPCMPapersMeta().subscribe({next: (paperDetails) =>{
       this.paperDetails = paperDetails;
      }}
      );
  }

}
