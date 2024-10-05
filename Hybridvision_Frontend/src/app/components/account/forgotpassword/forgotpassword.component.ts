import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../../services/account.service";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrl: './forgotpassword.component.css'
})
export class ForgotpasswordComponent {
  form!: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';

  constructor(private _formBuilder: FormBuilder, private _accountService: AccountService) {
    this.form = _formBuilder.group({
      email: _formBuilder.control('', [Validators.required,Validators.email]),
    })
  }

  sendResetEmail() {
    this._accountService.forgotPassword(this.form.value.email).subscribe(
      ()=>{
        this.successMessage = "Email successfully sent"
        this.errorMessage = '';

      }, errorResponse => {
        switch(errorResponse.status) {
          case 400:
            const errorBody = errorResponse.error;
            this.errorMessage = (errorBody && errorBody.email) ? errorBody.email : 'An error occurred. Please try again later.';
            break;
          case 404:
            this.errorMessage = 'Email not found';
            break;
          default:
            this.errorMessage = 'An unexpected error occurred. Please try again later.';
            break;
        }
      }
    );
  }}
