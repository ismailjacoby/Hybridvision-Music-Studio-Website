import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  private apiUrl = environment.apiUrl;
  constructor(private _http: HttpClient) { }

  getOrdersByUsername(username: string): Observable<any[]> {
    let params = new HttpParams().set('username', username);
    return this._http.get<any[]>(`${this.apiUrl}/orders/list`, {params});
  }
}
