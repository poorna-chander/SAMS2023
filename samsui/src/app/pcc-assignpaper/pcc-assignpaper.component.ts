import { AnimateTimings } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {ThemePalette} from '@angular/material/core';


export interface pcmName {
  name: string;
  completed:boolean;
  color:ThemePalette;
}

const pcmDetails: pcmName[] = [
  {name: "pcm1", completed: false, color:'primary'},
  {name: "pcm2", completed: false, color:'primary'},
  {name: "pcm3", completed: false, color:'primary'},
  {name: "pcm4", completed: false, color:'primary'}
];


@Component({
  selector: 'app-pcc-assignpaper',
  templateUrl: './pcc-assignpaper.component.html',
  styleUrls: ['./pcc-assignpaper.component.css']
})

export class PccAssignpaperComponent implements OnInit {
  pcmDetails: any;
  allComplete: boolean = false;

  updateAllComplete() {
    this.allComplete = this.pcmDetails != null && this.pcmDetails.every((t: { completed: any; }) => t.completed);
  }

  someComplete(): boolean {
    if (this.pcmDetails == null) {
      return false;
    }
    return this.pcmDetails.filter((t: { completed: any; }) => t.completed).length > 0 && !this.allComplete;
  }

  constructor(private _formBuilder: FormBuilder) {}

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  setAll(completed: boolean) {
    this.allComplete = completed;
    if (this.pcmDetails == null) {
      return;
    }
    this.pcmDetails.forEach((t: { completed: boolean; }) => (t.completed = completed));
  }

}
