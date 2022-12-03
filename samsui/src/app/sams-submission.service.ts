import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';
import { HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { catchError, map } from 'rxjs/operators';
import { SessionService } from './session.service';

@Injectable({
  providedIn: 'root'
})
export class SamsSubmissionService {
  private paper = 'http://localhost:8080/paper/';
  private papers = 'http://localhost:8080/papers';
  private notification = 'http://localhost:8080/user/notification';
  private user = 'http://localhost:8080/user/';
  private domain = 'http://localhost:8080/';

  constructor( private http: HttpClient,
              private sessionService: SessionService
  ) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  public getHeaders(): any {
    return new HttpHeaders({
      userId: this.sessionService.get("user_id"),
      "Access-Control-Allow-Origin": "http://localhost:4200"
      
    });
  }

  submitForm(file: File, title: any, authors: any, contact: any, submitterID: any) : Observable<HttpEvent<{}>> {
        console.log(title)
    
        const data: FormData = new FormData();
        data.append('title', title);
        data.append('file', file);
        data.append('authors', authors);
        data.append('contact', contact);

    console.log(data)

    const req = new HttpRequest(
      'POST',
      this.paper + "submit",
      data,
      {
        reportProgress: true,
        responseType: 'json',
        headers:  this.getHeaders()
      }
    );

    return this.http.request(req);
  }

  reviseForm(file: File, title: any, authors: any, contact: any, paperId: any) : Observable<HttpEvent<{}>> {
    console.log(title)

    const data: FormData = new FormData();
    data.append('title', title);
    if(file == undefined){
      data.append('file', file);
    }
    data.append('authors', authors);
    data.append('contact', contact);
    data.append('paperId', paperId);

console.log(data)

const req = new HttpRequest(
  'POST',
  this.paper + "revise",
  data,
  {
    reportProgress: true,
    responseType: 'json',
    headers:  this.getHeaders()
  }
);

return this.http.request(req);
}

  getNotifications() : Observable<any> {
    return this.http.get<any>(this.notification, { headers: this.getHeaders() }).pipe(map(response => {
      return response;
    }))
  }

  authenticateUser(username: any, password: any): Observable<any> {
    console.log(this.user + "authenticate" + ", username = " + username + ", password = " + password);
    const body = { username: username, password: password };
    return this.http.post<any>(this.user + "authenticate", body).pipe(map(response => {
      return response;
    }),
      catchError(this.handleUserError<any>({ "status": "failure" }))
    );

  }

  registerUser(username: any, password: any, type: any): Observable<any> {
    console.log(this.user + "register" + ", username = " + username + ", password = " + password);
    const body = { username: username, password: password, type: type };
    return this.http.post<any>(this.user + "register", body).pipe(map(response => {
      return response;
    }),
      catchError(this.handleUserError<any>({ "status": "failure" }))
    );

  }

  getAllSubmissions(): Observable<any> {
    return this.http.get<any>(this.papers, { headers: this.getHeaders() }).pipe(map(response => {
      return response;
    }))
  }

  getSubmissionBasedOnPaperId(paperId: number): Observable<any> {
    return this.http.get<any>(this.paper + paperId, { headers: this.getHeaders() }).pipe(map(response => {
      return response;
    }))
  }

  getAllSubmissionsAssignmentPending(): Observable<any> {
    return this.http.get<any>(this.papers + "/" + "pcc/assignment/pending", { headers: this.getHeaders() }).pipe(map(response => {
      return response;
    }))
  }

  getRatingCompletedSubmissions(): Observable<any> {
    return this.http.get<any>(this.papers + "/" + "pcc/rating/completed", { headers: this.getHeaders() }).pipe(map(response => {
      return response;
    }))
  }

  getAllPcms(): Observable<any> {
    return this.http.get<any>(this.domain + "pcm", { headers: this.getHeaders() }).pipe(map(response => {
      return response;
    }))
  }

  assignPaper(paperId: number, pcmId: number): Observable<any> {
      const body = { paper_id: paperId, pcm_id: pcmId };
      return this.http.put<any>(this.papers + "/" + "pcc/assignment/assign", body, { headers: this.getHeaders() }).pipe(
      map(response => {
        return response;
      }),
      catchError(this.handleUserError<any>({ "status": "failure" }))
    );
  }

  getAllPCMRatedPapers(): Observable<any> {
    return this.http.get<any>(this.papers + "/" + "pcc/assignment/review/completed", { headers: this.getHeaders() }).pipe(map(response => {
      return response;
    }))
  }

  ratePaper(paperId: number, rating: number): Observable<any> {
    const body = { paper_id: paperId, rating: rating };
    return this.http.put<any>(this.papers + "/" + "pcc/rate", body, { headers: this.getHeaders() }).pipe(
    map(response => {
      return response;
    }),
    catchError(this.handleUserError<any>({ "status": "failure" }))
  );
}

  public handleUserError<T>(result?: T) {
    return (error: any): Observable<T> => {
      return of(result as T);
    };
  }
}
