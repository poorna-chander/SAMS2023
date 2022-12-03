import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { SamsSubmissionService } from '../sams-submission.service';
import { SessionService } from '../session.service';

@Component({
  selector: 'app-ratepaper',
  templateUrl: './ratepaper.component.html',
  styleUrls: ['./ratepaper.component.css']
})
export class RatepaperComponent implements OnInit {
  paper_name: any;
  paper_id: any;
  rating: number;
  subscription!: Subscription;
  isRatingDisable: boolean = true;
  from: any;

  constructor( private samsSubmissionService: SamsSubmissionService,
    private componentInteractionService: ComponentInteractionService,
    private route: ActivatedRoute,
    private router: Router) { }

  ngOnInit(): void {
    this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
    this.paper_id = Number.parseInt(this.route.snapshot.url[2].path);
    this.from = this.route.snapshot.url[1].path === 'pcc' ? "PCC" : "PCM";
    this.setData();
  }

  sendit(data: any){
    console.log("Value",data);
    console.log(this.rating);
 }

  setData(): void{
    this.samsSubmissionService.getSubmissionBasedOnPaperId(this.paper_id).subscribe({next: (paperDetails) =>{
      this.paper_name = paperDetails.title;
      if(paperDetails.rating != null){
        this.rating = paperDetails.rating;
      }
      }}
      );
  }

  rate(): void {
    if(this.rating !== undefined && this.rating !== null && this.rating <= 10 && this.rating >= 0){
      this.samsSubmissionService.ratePaper(this.paper_id, this.rating).subscribe({next: (response) =>{
        console.log(response);
        if(this.from === "PCC"){
          this.router.navigate(['home_pcc']);
          setTimeout(() =>{this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.PCC_TAB_RATE_PAPERS);}, 500);
        }else{
          this.router.navigate(['home_pcm']); 
        }
        
      }}
      );
    }

  }

  cancel(): void{
    if(this.from === "PCC"){
      this.router.navigate(['home_pcc']);
      setTimeout(() =>{this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.PCC_TAB_RATE_PAPERS);}, 500);
    }else{
      this.router.navigate(['home_pcm']); 
    }
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

}
