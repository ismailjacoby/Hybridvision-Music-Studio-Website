import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class NewsletterService {

  private apiUrl = environment.apiUrl

  constructor(private _http: HttpClient) { }

  subscribeToNewsLetter(email: string): Observable<any>{
    return this._http.post<any>(`${this.apiUrl}/newsletter/subscribe`, { email });
  }
}
