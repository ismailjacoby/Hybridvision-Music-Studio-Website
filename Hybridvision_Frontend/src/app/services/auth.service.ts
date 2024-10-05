import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable, tap} from "rxjs";
import {Auth} from "../models/Auth";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {UserRoles} from "../models/UserRoles";
import {environment} from "../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private apiUrl = environment.apiUrl
  public userRole!: string;

  connectedUser = new BehaviorSubject<Auth | null>(null);

  constructor(private readonly _http: HttpClient, private readonly _router: Router) {
    const token = localStorage.getItem('token');
    if(token){
      const role = localStorage.getItem('role');
      if(role){
        this.userRole = role;
      }
      let roleConnected: UserRoles;
      if(role === 'ADMIN'){
        roleConnected = UserRoles.ADMIN;
      } else{
        roleConnected = UserRoles.CLIENT;
      }
      this.connectedUser.next({
        token: token,
        role: roleConnected,
        username: localStorage.getItem('username') || ''
      });
    }
  }

  login(username: string, password: string): Observable<any>{
    console.log('Sending login request to:', this.apiUrl);
    console.log('Request body:', { username, password });
    return this._http.post<Auth>(`${this.apiUrl}/account/login`,{username, password}).pipe(
      tap(value => {
        console.log('Login response received:', value);
        localStorage.setItem('token', value.token);
        localStorage.setItem('role', value.role.toString());
        this.userRole = value.role.toString();
        localStorage.setItem('username', value.username);
        this.connectedUser.next(value);
        this._router.navigate(['home'])
      })
    );
  }

  logout(){
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('username');
    this.userRole = '';
    this.connectedUser.next(null);
    this._router.navigate(['account/login'])
  }

  getUsername(): string | null{
    return localStorage.getItem('username');
  }

  isLoggedIn(){
    return !!localStorage.getItem('token');
  }

  isAdmin(){
    return localStorage.getItem('role') === 'ADMIN'
  }

  isClient(){
    return localStorage.getItem('role') === 'CLIENT'
  }

  getUserRole(): string{
    return this.userRole;
  }
}
