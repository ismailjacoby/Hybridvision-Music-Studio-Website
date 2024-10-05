import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../../services/account.service";
import {Route, Router} from "@angular/router";
import {passwordMinLength} from "../../../utils/validators/passwordMinLength";

export interface Account{
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrl: './sign-up.component.css'
})
export class SignUpComponent {
  form!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;


  constructor(private _formBuilder: FormBuilder, private _accountService: AccountService) {
    this.form= _formBuilder.group({
      username: _formBuilder.control('', Validators.required),
      firstName: _formBuilder.control('', Validators.required),
      lastName: _formBuilder.control('', Validators.required),
      email: _formBuilder.control('', [Validators.required,Validators.email]),
      password: _formBuilder.control('', [Validators.required,passwordMinLength()]),
    })
  }

  signUp(){
    const account: Account = {
      username: this.form.get('username')!.value,
      firstName: this.form.get('firstName')!.value,
      lastName: this.form.get('lastName')!.value,
      email: this.form.get('email')!.value,
      password: this.form.get('password')!.value,
    }

    this._accountService.signUp(account).subscribe(
      ()=>{
        this.successMessage = 'User registered successfully'
        this.errorMessage = null;
        this.resetForm();
      }, (error) => {
        if (error.error.message) {
          this.errorMessage = error.error.message;
        } else {
          this.errorMessage = 'Error registering user';
        }
        this.successMessage = null;
      }
    )
  }

  resetForm(){
    this.form.reset();
  }
}
