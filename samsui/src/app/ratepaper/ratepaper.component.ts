import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ComponentInteractionService } from '../component-interaction.service';
import { SessionService } from '../session.service';


export interface Paper {
  title: string;
  revision: number;
  paperId: number;
}

const paper: Paper[] = [
  {title: "model driven development", revision: 5, paperId: 1},
];


@Component({
  selector: 'app-ratepaper',
  templateUrl: './ratepaper.component.html',
  styleUrls: ['./ratepaper.component.css']
})
export class RatepaperComponent implements OnInit {
  paper_name: any;
  paper_id: any;
  rating: any;

  constructor() { }

  ngOnInit(): void {
    this.paper_name = paper[0].title;
    this.paper_id = paper[0].paperId;
  }

  rate(): void {

  }

  loginPage(): void {
    
  }


}
