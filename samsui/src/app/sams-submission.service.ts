import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { catchError, map, tap } from 'rxjs/operators';
import { SessionService } from './session.service';

@Injectable({
  providedIn: 'root'
})
export class SamsSubmissionService {
  private submission = 'http://localhost:8080/paper/submit';
  private notification = 'http://localhost:8080/user/notification';
  private user = 'http://localhost:8080/user/';

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
      userId: this.sessionService.get("user_id")
      
    });
  }

  submitForm(file: File, title: any, authors: any, contact: any, submitterID: any) : Observable<HttpEvent<{}>> {
        console.log(title)
    
        const data: FormData = new FormData();
        data.append('title', title);
        data.append('file', file);
        data.append('authors', authors);
        data.append('contact', contact);
        debugger;

    console.log(data)

    const req = new HttpRequest(
      'POST',
      this.submission,
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

  public handleUserError<T>(result?: T) {
    debugger;
    return (error: any): Observable<T> => {
      return of(result as T);
    };
  }
}
