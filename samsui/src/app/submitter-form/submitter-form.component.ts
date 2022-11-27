import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, Validators, FormGroup, FormControl, FormArray } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-submitter-form',
  templateUrl: './submitter-form.component.html',
  styleUrls: ['./submitter-form.component.css']
})
export class SubmitterFormComponent implements OnInit {

  constructor(private fb: FormBuilder,
    private location: Location) {}

  ngOnInit(): void {
  }
  
  onSubmit(): void {
  
    }

  @ViewChild('fileInput') fileInput: ElementRef | undefined;
  fileAttr = 'Choose File';
  uploadFileEvt(imgFile: any) {
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
      // this.fileInput.nativeElement.value = '';
    } else {
      this.fileAttr = 'Choose File';
    }
  }
  
  }

