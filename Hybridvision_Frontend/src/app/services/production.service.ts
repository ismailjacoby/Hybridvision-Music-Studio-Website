import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";
import {Production} from "../models/Production";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class ProductionService {
  private apiUrl = environment.apiUrl;

  constructor(private _http : HttpClient) { }

  uploadProduction(production: FormData): Observable<HttpEvent<any>> {
    return this._http.post<HttpEvent<any>>(`${this.apiUrl}/productions/upload`, production, {
      observe: 'events',
      reportProgress: true
    });
  }

  getProductionById(productionId: number): Observable<Production> {
    return this._http.get<Production>(`${this.apiUrl}/productions/${productionId}`);
  }

  getAllProductions(): Observable<Production[]> {
    return this._http.get<Production[]>(`${this.apiUrl}/productions/list`);
  }

  editProduction(productionId: number, productionData: FormData): Observable<any> {
    return this._http.put(`${this.apiUrl}/productions/${productionId}`, productionData);
  }

  deleteProductions(productionId: number): Observable<any>{
    return this._http.delete<any>(`${this.apiUrl}/productions/delete/${productionId}`);
  }
}
