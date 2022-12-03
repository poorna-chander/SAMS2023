import { AnimateTimings } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {ThemePalette} from '@angular/material/core';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { ComponentInteractionService } from '../component-interaction.service';
import { SamsSubmissionService } from '../sams-submission.service';


export interface PCMName {
  id: number,
  name: string;
  checked:boolean;
}


@Component({
  selector: 'app-pcc-assignpaper',
  templateUrl: './pcc-assignpaper.component.html',
  styleUrls: ['./pcc-assignpaper.component.css']
})

export class PccAssignpaperComponent implements OnInit {
  
  constructor(
    private samsSubmissionService: SamsSubmissionService,
    private componentInteractionService: ComponentInteractionService,
    private route: ActivatedRoute,
    private router: Router) {}

  public checks: Array<PCMName> = [];

  public checked_options: Map<number,boolean> = new Map();
  paperId: number;
  private _fb: any;
  isSubmitDisable: boolean = true;
  noOfCheckedPcms: number = 0;

  ngOnInit(): void {
    this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
    this.paperId = Number.parseInt(this.route.snapshot.url[1].path);
    this.setData();
  }

  public setData(): void{
    this.samsSubmissionService.getAllPcms().subscribe({next: (pcmData) =>{
      this.checks = [];
      pcmData.forEach((data: any) => {
        this.checks.push({name: data.name, id: data.id, checked: false});
      });
    }}
    );
  }

  updateCheckedOptions(choice: any, event: any) {
    if (this.checked_options.get(choice["id"])) {
      this.checked_options.set(choice["id"], false);
    this.noOfCheckedPcms -=  1;
    } else{
      this.checked_options.set(choice["id"], true);
      this.noOfCheckedPcms +=  1;
    }
    this.isSubmitDisable = !(this.noOfCheckedPcms == 3);
  }

    submitPcmAssignments(){
      let pcmIds: any = [];
      this.checked_options.forEach((value: boolean, key: number) => {
        if(value){
          pcmIds.push(key);
        }
      });
      this.submitAssignCall(pcmIds);
    }

    submitAssignCall(pcmIds: any = []): void{
       this.samsSubmissionService.assignPaper(this.paperId, pcmIds[0]).subscribe({next: (pcmData) =>{
        pcmIds.splice(0, 1);
        if(pcmIds.length > 0){
          this.submitAssignCall(pcmIds);
        }else{
          this.router.navigate(['/home_pcc']);
        }
      }}
      );
    }

}
