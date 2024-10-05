import {Component} from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../../services/account.service";
import {passwordMinLength} from "../../../utils/validators/passwordMinLength";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  errorMessage: string = '';
  loginForm!: FormGroup;


  constructor(private _authService: AuthService,
              private _router: Router,
              private _accountService: AccountService,
              private _formBuilder: FormBuilder) {
    this.loginForm = _formBuilder.group({
        username: _formBuilder.control('', Validators.required),
        password: _formBuilder.control('', [Validators.required,passwordMinLength()])
      }
    )
  }

  onSubmit(){
      this._authService.login(this.loginForm.value.username,this.loginForm.value.password).subscribe(
        response =>{
          this._authService.userRole = response.role;
          this._router.navigate(['home'])
        }, error =>{
          console.log(error);
          if(error.status == 403){
            this.errorMessage = 'Invalid username or password';
          } else{
            this.errorMessage = error.error.message;
          }
        }
      )

  }
}
