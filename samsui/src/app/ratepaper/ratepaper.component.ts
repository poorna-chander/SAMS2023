import { TmplAstRecursiveVisitor } from '@angular/compiler';
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
  from: any = 'PCC';
  questionnaire: any = [];

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
    if(this.from === "PCC"){
    this.samsSubmissionService.getSubmissionBasedOnPaperId(this.paper_id).subscribe({next: (paperDetails) =>{
      this.paper_name = paperDetails.title;
      if(paperDetails.rating != null){
        this.rating = paperDetails.rating;
      }
      }}
      );
    }else if(this.from === "PCM"){
        this.samsSubmissionService.getAllAssignedPapers().subscribe({next: (paperDetails) =>{
          paperDetails.forEach((paperData: any) => {
            if(paperData.paperId === this.paper_id){
              this.paper_name = paperData.title;
                if(!(paperData.rating === null || paperData.rating === undefined)){
                  this.router.navigate(['/home_pcm']);
                }
            }else{
              this.router.navigate(['/home_pcm']);
            }

         });

          }}
          );
        this.samsSubmissionService.getQuestionnaire().subscribe({next: (questionnaire) =>{
          questionnaire.forEach((question: any) => {
            question.answer = "";
          })
          this.questionnaire = questionnaire;
          }}
          );
      }
  }

  rate(): void {
    if(this.validate()){
      if(this.from === "PCC"){
        this.samsSubmissionService.ratePaper(this.paper_id, this.rating).subscribe({next: (response) =>{
          console.log(response);
            this.router.navigate(['home_pcc']);
            setTimeout(() =>{this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.PCC_TAB_RATE_PAPERS);}, 500);  
        }}
        );
      }else if(this.from === "PCM"){
        this.samsSubmissionService.reviewPaper(this.paper_id, this.getReviews(), this.rating).subscribe({next: (response) =>{
          console.log(response);
            this.router.navigate(['home_pcm']);
            setTimeout(() =>{this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.PCM_TAB_RATE_ASSIGNED_PAPERS);}, 500);  
        }}
        );
      }
     
    }

  }

  validate(): boolean{
    if(this.from === "PCC"){
      return (this.rating !== undefined && this.rating !== null && this.rating <= 10 && this.rating >= 0);
    }else if(this.from === "PCM"){
       let isQuestionsFilled: boolean = !(this.questionnaire.length === 0);
       this.questionnaire.forEach((question: any) => {
        if(question.answer.trim() === ""){
          isQuestionsFilled = false;
          return;
        }
      })
      return (this.rating !== undefined && this.rating !== null && this.rating <= 10 && this.rating >= 0 && isQuestionsFilled);
    }
    return false;
  }

  getReviews(): {} {
    let reviews:any = {};
    this.questionnaire.forEach((question: any) => {
      reviews[question.id] = question.answer;
    })

    return reviews;
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
