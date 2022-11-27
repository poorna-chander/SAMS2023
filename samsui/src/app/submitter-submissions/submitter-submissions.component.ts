import { Component, OnInit } from '@angular/core';

export interface Submission {
  title: string;
  revision: number;
  paperId: number;
}

const SubmissionData: Submission[] = [
  {title: "model driven development", revision: 5, paperId: 1},
  {title: "collaborative software development", revision: 5, paperId: 1},
  {title: "software construction", revision: 5, paperId: 1},
  {title: "SMAR", revision: 5, paperId: 1}
];

@Component({
  selector: 'app-submitter-submissions',
  templateUrl: './submitter-submissions.component.html',
  styleUrls: ['./submitter-submissions.component.css']
})
export class SubmitterSubmissionsComponent implements OnInit {
  displayedColumns: string[] = ['title', 'revision', 'paperId', "button"];
  dataSource = SubmissionData;
  constructor() { 

  }

  ngOnInit(): void {
    
  }

}
