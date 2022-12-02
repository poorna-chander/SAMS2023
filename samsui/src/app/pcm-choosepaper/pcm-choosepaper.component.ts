import { Component, OnInit } from '@angular/core';

export interface Papers {
  title: string;
  revision: number;
  paperId: number;
}

const paperData: Papers[] = [
  {title: "model driven development", revision: 5, paperId: 1},
  {title: "collaborative software development", revision: 5, paperId: 1},
  {title: "software construction", revision: 5, paperId: 1},
  {title: "SMAR", revision: 5, paperId: 1}
];

@Component({
  selector: 'app-pcm-choosepaper',
  templateUrl: './pcm-choosepaper.component.html',
  styleUrls: ['./pcm-choosepaper.component.css']
})
export class PcmChoosepaperComponent implements OnInit {

  displayedColumns: string[] = ['title', 'revision', 'paperId', "choose"];
  paperDetails: any[];

  constructor() { }

  ngOnInit(): void {
    this.paperDetails = paperData;
  }

}
