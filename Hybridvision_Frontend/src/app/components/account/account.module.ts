import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AccountRoutingModule } from './account-routing.module';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {LoginComponent} from "./login/login.component";
import {SignUpComponent} from "./sign-up/sign-up.component";
import {HttpClientModule} from "@angular/common/http";
import { ForgotpasswordComponent } from './forgotpassword/forgotpassword.component';
import { EditAccountComponent } from './edit-account/edit-account.component';
import { ManageClientsComponent } from './manage-clients/manage-clients.component';
import { UserDetailsComponent } from './user-details/user-details.component';


@NgModule({
  declarations: [
    LoginComponent,
    SignUpComponent,
    ForgotpasswordComponent,
    EditAccountComponent,
    ManageClientsComponent,
    UserDetailsComponent
  ],
  imports: [
    CommonModule,
    AccountRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule
  ]
})
export class AccountModule { }
