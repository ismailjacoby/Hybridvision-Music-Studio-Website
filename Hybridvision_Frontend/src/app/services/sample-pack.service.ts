import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent} from "@angular/common/http";
import {Observable} from "rxjs";
import {SamplePack} from "../models/SamplePack";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class SamplePackService {
  private apiUrl = environment.apiUrl;

  constructor(private _http: HttpClient) { }

  uploadSamplePack(formData: FormData): Observable<HttpEvent<any>> {
    return this._http.post<any>(`${this.apiUrl}/samplepack/upload`, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }

  getSamplePacks(): Observable<SamplePack[]> {
    return this._http.get<SamplePack[]>(`${this.apiUrl}/samplepack/list`);
  }

  getSamplePackById(id: number): Observable<SamplePack> {
    return this._http.get<SamplePack>(`${this.apiUrl}/samplepack/${id}`);
  }

  deleteSamplePack(samplePackId: number): Observable<any> {
    return this._http.delete(`${this.apiUrl}/samplepack/delete/${samplePackId}`);
  }
}
