import { AnimateTimings } from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import {FormArray, FormBuilder, FormGroup} from '@angular/forms';
import {ThemePalette} from '@angular/material/core';


export interface pcmName {
  name: string;
  checked:boolean;
}

const pcmDetails: pcmName[] = [
  {name: "pcm1", checked: false},
  {name: "pcm2", checked: false},
  {name: "pcm3", checked: false},
  {name: "pcm4", checked: false}
];


@Component({
  selector: 'app-pcc-assignpaper',
  templateUrl: './pcc-assignpaper.component.html',
  styleUrls: ['./pcc-assignpaper.component.css']
})

export class PccAssignpaperComponent implements OnInit {
  
  public checks: Array<pcmName> = [
    {name: "pcm1", checked: false},
    {name: "pcm2", checked: false},
    {name: "pcm3", checked: false},
    {name: "pcm4", checked: false}
  ];

  public checked_options: Map<string,boolean> = new Map();

  private _fb: any;

  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }

  updateCheckedOptions(choice: any, event: any) {
    console.log(choice)
    if (this.checked_options.get(choice["name"])) {
    this.checked_options.set(choice["name"], false)} else{
      this.checked_options.set(choice["name"], true)
    }

    console.log(this.checked_options)
    }

    submit_pcms(){
      // output of submit
      console.log(this.checked_options)
    }

}
