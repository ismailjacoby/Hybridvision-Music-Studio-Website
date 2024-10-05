import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {LicenseType} from "../models/LicenseType";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class WishlistService {

  private apiUrl = environment.apiUrl;

  constructor(private _http: HttpClient) {}

  getWishlist(username: string): Observable<any> {
    let params = new HttpParams().set('username', username);
    return this._http.get<any>(`${this.apiUrl}/wishlist/list`, { params });
  }

  addItemToWishlist(username: string, productionId?: number, samplePackId?: number): Observable<any> {
    let params = new HttpParams().set('username', username);
    if (productionId !== undefined) {
      params = params.set('productionId', productionId.toString());
    }
    if (samplePackId !== undefined) {
      params = params.set('samplePackId', samplePackId.toString());
    }
    return this._http.post(`${this.apiUrl}/wishlist/add`, {}, { params });
  }

  removeItemFromWishlist(username: string, productionId?: number, samplePackId?: number): Observable<any> {
    let params = new HttpParams().set('username', username);
    if (productionId) {
      params = params.set('productionId', productionId.toString());
    } else if (samplePackId) {
      params = params.set('samplePackId', samplePackId.toString());
    }
    return this._http.delete(`${this.apiUrl}/wishlist/remove`, { params, responseType: 'text' });
  }
}
