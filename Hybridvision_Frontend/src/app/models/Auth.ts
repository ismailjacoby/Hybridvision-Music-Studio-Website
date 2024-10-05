import {UserRoles} from "./UserRoles";
import {Validators} from "@angular/forms";

export interface Auth{
  username: string;
  token: string;
  role: UserRoles;
}

export const LOGIN_FORM ={
  username: ['', Validators.required],
  password: ['', Validators.required]
}
