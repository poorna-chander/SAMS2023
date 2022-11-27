import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl, FormArray } from '@angular/forms';
import { Location } from '@angular/common';
import { SamsSubmissionService } from '../sams-submission.service';
import { HttpEventType, HttpResponse } from '@angular/common/http';

@Component({
  selector: 'app-submitter-form',
  templateUrl: './submitter-form.component.html',
  styleUrls: ['./submitter-form.component.css']
})
export class SubmitterFormComponent implements OnInit {


  constructor(private fb: FormBuilder,
    private location: Location,
    private samsSubmissionService: SamsSubmissionService) {}

  selectedFiles: any;
  currentFileUpload: any;

  ngOnInit(): void {
  }

  onSubmit(paperSubmission: any): void {
    this.currentFileUpload = this.selectedFiles.item(0);
    console.log(paperSubmission)
    this.samsSubmissionService.submitForm(this.currentFileUpload, paperSubmission.title, paperSubmission.authors, paperSubmission.email, 1).subscribe((event: { type: HttpEventType; }) => {
      if (event.type === HttpEventType.UploadProgress) {
      } else if (event instanceof HttpResponse) {
         alert('File Successfully Uploaded');
      }
      this.selectedFiles = null;
     }
    );
  }
  
  @ViewChild('fileInput') fileInput: ElementRef;
  fileAttr = 'Choose File';
  uploadFileEvt(imgFile: any) {
    this.selectedFiles = imgFile.target.files;
    if (imgFile.target.files && imgFile.target.files[0]) {
      this.fileAttr = '';
      Array.from(imgFile.target.files).forEach((file: any) => {
        this.fileAttr += file.name + ' - ';
      });
      // HTML5 FileReader API
      let reader = new FileReader();
      reader.onload = (e: any) => {
        let image = new Image();
        image.src = e.target.result;
        image.onload = (rs) => {
          let imgBase64Path = e.target.result;
        };
      };
      reader.readAsDataURL(imgFile.target.files[0]);
      // Reset if duplicate image uploaded again
      this.fileInput.nativeElement.value = '';
    } else {
      this.fileAttr = 'Choose File';
    }
  }
  
  }

