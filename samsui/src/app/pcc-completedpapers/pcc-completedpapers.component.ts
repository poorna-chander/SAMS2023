import { Component, OnInit } from '@angular/core';
// import { SamsPccService } from '../sams-submission.service';

export interface completedPaper {
  title: string;
  revision: number;
  paperId: number;
  rating: number;

}

const completedPapers: completedPaper[] = [
  {title: "model driven development", revision: 5, paperId: 1, rating: 5},
  {title: "collaborative software development", revision: 5, paperId: 1, rating: 5},
  {title: "software construction", revision: 5, paperId: 1, rating: 9},
  {title: "SMAR", revision: 5, paperId: 1, rating:10}
];

@Component({
  selector: 'app-pcc-completedpapers',
  templateUrl: './pcc-completedpapers.component.html',
  styleUrls: ['./pcc-completedpapers.component.css']
})
export class PccCompletedpapersComponent implements OnInit {
  displayedColumns: string[] = ['title', 'revision', 'paperId', "finalRating"];
  // dataSource = completedPapers;
  paperDetails = completedPapers;
  constructor() { }

  ngOnInit(): void {
  }

}
