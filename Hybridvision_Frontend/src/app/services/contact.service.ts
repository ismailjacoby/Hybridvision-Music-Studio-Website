import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ContactService {

  private apiUrl = environment.apiUrl

  constructor(private _http: HttpClient) { }

  sendContactForm(contactFormData: any): Observable<any>{
    return this._http.post(`${this.apiUrl}/contact`, contactFormData,
      { responseType: 'text' });
  }
}
