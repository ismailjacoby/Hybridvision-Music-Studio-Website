import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AccountService} from "../../../services/account.service";
import {User} from "../../../models/User";
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Component({
  selector: 'app-edit-account',
  templateUrl: './edit-account.component.html',
  styleUrl: './edit-account.component.css'
})
export class EditAccountComponent implements OnInit{
  form!: FormGroup;
  editMode: boolean = false;
  successMessage: string = '';
  errorMessage: string = '';
  isAdmin: boolean = this._authService.isAdmin();
  isClient: boolean = this._authService.isClient();

  constructor(private _formBuilder: FormBuilder, private _accountService: AccountService, private _authService: AuthService, private _router : Router) {
  }

  ngOnInit(): void {
    this.editMode = false;
    this.form = this._formBuilder.group({
      username: [{value: '',disabled: true},Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    })
    this.loadUserData();
  }

  loadUserData(): void {
    const username = localStorage.getItem('username');
    if (username) {
      this._accountService.getUserByUsername(username).subscribe({
        next: (user: User) => {
          this.form.patchValue({
            username: localStorage.getItem('username'),
            firstName: user.firstName,
            lastName: user.lastName,
            email: user.email
          });
          this.form.get('username')!.disable();
          this.toggleFormState();
        },
        error: (error) => {
          console.error('Error fetching user data', error);
          this.errorMessage = 'Failed to load user data';
        }
      });
    }
  }

  toggleEditMode(): void {
    this.editMode = !this.editMode;
    this.toggleFormState();
  }

  toggleFormState(): void {
    if (this.editMode) {
      this.form.enable();
      this.form.get('username')!.disable();
    } else {
      this.form.disable();
    }
  }

  save(): void {
    if (this.form.valid) {
      this._accountService.editAccount(this.form.getRawValue()).subscribe({
        next: () => {
          console.log('Account updated successfully');
          this.successMessage = 'Account updated successfully';
          this.editMode = false;
          this.toggleFormState();
        },
        error: (error) => {
          console.error('Error updating account', error);
          this.errorMessage = 'Error updating account';
        }
      });
    }
  }

  deleteAccount() {
    const username = localStorage.getItem('username');
    if(username){
      this._accountService.deactivateAccount(username).subscribe({
        next: () => {
          console.log('Account deleted successfully');
          this.successMessage = 'Account deleted successfully';
          this._authService.logout();
          this._router.navigate(['account/login']);
        },
        error: (error) => {
          this.errorMessage = error.error.message;
        }
      });
    }
  }

  openModal() {
    document.getElementById('deleteModal')!.style.display = "block";
  }

  closeModal() {
    document.getElementById('deleteModal')!.style.display = "none";
  }
}
