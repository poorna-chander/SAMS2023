import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl, FormArray } from '@angular/forms';
import { Location } from '@angular/common';
import { SamsSubmissionService } from '../sams-submission.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';

@Component({
  selector: 'app-submitter-revision',
  templateUrl: './submitter-revision.component.html',
  styleUrls: ['./submitter-revision.component.css']
})
export class SubmitterRevisionComponent implements OnInit {

  constructor(
    private samsSubmissionService: SamsSubmissionService,
    private componentInteractionService: ComponentInteractionService,
    private route: ActivatedRoute,
    private router: Router) {}

  selectedFile: any;
  currentFileUpload: any;
  isFileAdded: boolean;
  isChooseFileDisabled: boolean = false;
  paperId: number;
  paperDetails: any;
  title: any;
  authors: any;
  contact: any;
  subscription!: Subscription;
  isSubmitDisable: boolean = false;
 

  ngOnInit(): void {
    this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
    this.paperId = Number.parseInt(this.route.snapshot.url[1].path);
     this.setData();
  }

  public setData(): void{
    this.samsSubmissionService.getSubmissionBasedOnPaperId(this.paperId).subscribe({next: (paperDetails) =>{
      this.paperDetails = paperDetails;
      this.title = paperDetails.title;
      this.authors = paperDetails.authors;
      this.contact = paperDetails.contact;
      this.selectedFile = [];
      this.selectedFile[0] = {};
      this.selectedFile[0].name = paperDetails.fileName + "." + paperDetails.fileExtension;
      this.isFileAdded = true;
    }}
    );
  }

  onSubmit(paperSubmission: any): void {
    console.log(paperSubmission)
    if(this.isFileAdded){
      this.samsSubmissionService.reviseForm(this.selectedFile[0], paperSubmission.title, paperSubmission.authors, paperSubmission.email, this.paperId).subscribe((event: { type: HttpEventType; }) => {
        if (event instanceof HttpResponse) {
           alert('File Successfully Uploaded');
           
           this.router.navigate(['home_submitter']).then(() => {
            this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.SUBMITTER_TAB_VIEW);
          });
        }
       }
      );
    }
  }
  
  @ViewChild('fileInput') fileInput: ElementRef;
  fileAttr = 'Choose File';
  uploadFileEvt(imgFile: any) {
    this.selectedFile = imgFile.target.files;
    this.isFileAdded = true;
    this.fileAttr = this.selectedFile[0].name;
    this.isSubmitDisable = false;
  }

  cancel(){
    this.router.navigate(['home_submitter']);
    this.isFileAdded = false;
  }

  removeFileAdded(){
    this.isFileAdded = false;
    this.selectedFile = undefined;
    this.isSubmitDisable = true;
  }

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }

}
