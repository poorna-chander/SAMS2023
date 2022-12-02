import { Component, OnInit } from '@angular/core';
// import { SamsPCCService } from '../sams-submission.service';


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
  selector: 'app-pcc-ratepaper',
  templateUrl: './pcc-ratepaper.component.html',
  styleUrls: ['./pcc-ratepaper.component.css']
})
export class PccRatepaperComponent implements OnInit {
  displayedColumns: string[] = ['title', 'revision', 'paperId', "rate"];
  // dataSource = SubmissionData;
  paperDetails: any[];

  // constructor( private samsPCCService: SamsPCCService) { }
  // constructor() { }

  ngOnInit(): void {
    this.paperDetails = SubmissionData;
  }

}
