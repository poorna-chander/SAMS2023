import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SamsSubmissionService {
  private submission = 'http://localhost:8080/paper/submit';

  constructor( private http: HttpClient
  ) {}

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/json',
    }),
  };

  submitForm(file: File, title: any, authors: any, contact: any, submitterID: any) : Observable<HttpEvent<{}>> {
        console.log(title)
    
        const data: FormData = new FormData;
        data.append('title', title);
        data.append('file', file);
        data.append('authors', authors);
        data.append('contact', contact);

    //     const newRequest = new HttpRequest('POST', this.submission , data, {
    //     reportProgress: true,
    //     responseType: 'text'
    // });
    // return this.http.request(newRequest);

    console.log(data)
    return this.http
    .post<any>(this.submission, data, { headers: {"submitterId" : "4"} }
    ).pipe(
      map((response) => {
        return response;
      }),
      catchError(this.handleUserError<any>({ "status": "failure" }))
    );
  }

  public handleUserError<T>(result?: T) {
    debugger;
    return (error: any): Observable<T> => {
      return of(result as T);
    };
  }
}
