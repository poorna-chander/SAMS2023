import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl, FormArray } from '@angular/forms';
import { Location } from '@angular/common';
import { SamsSubmissionService } from '../sams-submission.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { ComponentInteractionService, COMPONENT_TYPE_MESSAGE } from '../component-interaction.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-submitter-form',
  templateUrl: './submitter-form.component.html',
  styleUrls: ['./submitter-form.component.css']
})
export class SubmitterFormComponent implements OnInit {
  subscription!: Subscription;

  constructor(
    private componentInteractionService: ComponentInteractionService,
    private samsSubmissionService: SamsSubmissionService,
    private route: ActivatedRoute) {}

  selectedFile: any;
  currentFileUpload: any;
  isFileAdded: boolean;
  isChooseFileDisabled: boolean = false;
  isSubmitDisable: boolean = true;

  ngOnInit(): void {
    this.componentInteractionService.redirectToDefault(this.route.snapshot.url[0].path);
  }

  onSubmit(paperSubmission: any): void {
    console.log(paperSubmission)
    if(this.isFileAdded){
      this.samsSubmissionService.submitForm(this.selectedFile[0], paperSubmission.title, paperSubmission.authors, paperSubmission.email, 1).subscribe((event: { type: HttpEventType; }) => {
        if (event instanceof HttpResponse) {
           this.isFileAdded = false;
           alert('File Successfully Uploaded');
           this.componentInteractionService.componentTypeMessage.next(COMPONENT_TYPE_MESSAGE.SUBMITTER_TAB_VIEW);
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

  ngOnDestroy(): void {
    this.subscription?.unsubscribe();
  }
  
  }

