import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {SignUpComponent} from "./sign-up/sign-up.component";
import {ForgotpasswordComponent} from "./forgotpassword/forgotpassword.component";
import {EditAccountComponent} from "./edit-account/edit-account.component";
import {isLoggedOutGuard} from "../../utils/guards/isLoggedOutGuard";
import {isLoggedInGuard} from "../../utils/guards/isLoggedInGuard";
import {ManageClientsComponent} from "./manage-clients/manage-clients.component";
import {isAdminGuard} from "../../utils/guards/isAdminGuard";
import {UserDetailsComponent} from "./user-details/user-details.component";

const routes: Routes = [
  {path:'', redirectTo: 'login', pathMatch: 'full'},
  {path: 'login', component: LoginComponent, canActivate: [isLoggedOutGuard]},
  {path: 'signup', component:SignUpComponent, canActivate: [isLoggedOutGuard]},
  {path:'forgotpassword', component: ForgotpasswordComponent, canActivate: [isLoggedOutGuard]},
  {path:'editaccount', component:EditAccountComponent, canActivate: [isLoggedInGuard]},
  {path:'manageclients', component: ManageClientsComponent, canActivate:[isAdminGuard]},
  {path: 'user/:username', component: UserDetailsComponent, canActivate: [isAdminGuard]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AccountRoutingModule { }
