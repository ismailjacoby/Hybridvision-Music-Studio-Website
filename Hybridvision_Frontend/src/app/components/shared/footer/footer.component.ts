import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {NewsletterService} from "../../../services/newsletter.service";

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {
  form!: FormGroup;
  successMessage: string | null = null;
  errorMessage: string | null = null;

  constructor(private _formBuilder: FormBuilder, private _newsletterService: NewsletterService) {
    this.form = this._formBuilder.group({
      email: ['', [Validators.required, Validators.email]]
    })
  }

  /*
  * Subscribe to a newsletter
  */
  subscribe():void{
    if(this.form.valid){
      const email = this.form.value.email;
      this._newsletterService.subscribeToNewsLetter(email).subscribe(
        ()=>{
          this.successMessage = 'Subscribed successfully'
          this.errorMessage = null;
          this.form.reset();
        }, (error) =>{
          this.successMessage = null;
          this.errorMessage = 'Failed to subscribe. Please try again later.'
          console.error('Failed to subscribe: ', error)
        }
      );
    } else {
      this.errorMessage = 'Please enter a valid email address.';
    }
  }
}
