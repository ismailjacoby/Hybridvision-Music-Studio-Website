import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Account} from "../components/account/sign-up/sign-up.component";
import {Observable, of} from "rxjs";
import {User} from "../models/User";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private apiUrl = environment.apiUrl;

  constructor(private _http: HttpClient) { }

  signUp(account: Account): Observable<any>{
    return this._http.post<any>(`${this.apiUrl}/account/signup`, account)
  }

  forgotPassword(email: String): Observable<any>{
    return this._http.post<any>(`${this.apiUrl}/account/forgotpassword`, { email });
  }

  getUserByUsername(username: string): Observable<User> {
    return this._http.get<User>(`${this.apiUrl}/account/user/${username}`);
  }

  editAccount(user: User): Observable<any> {
    return this._http.post<any>(`${this.apiUrl}/account/editaccount`, user);
  }
  deactivateAccount(username: string): Observable<any> {
    return this._http.patch<any>(`${this.apiUrl}/account/deactivate/${username}`, null);
  }
  blockAccount(username: string): Observable<any>{
    return this._http.patch<any>(`${this.apiUrl}/account/block/${username}`,null);
  }

  getAllClient(): Observable<User[]>{
    return this._http.get<User[]>(`http://localhost:8080/account/clients/all`);
  }


}
