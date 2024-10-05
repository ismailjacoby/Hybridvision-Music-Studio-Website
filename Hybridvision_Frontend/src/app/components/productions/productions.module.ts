import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProductionsRoutingModule } from './productions-routing.module';
import { UploadProductionsComponent } from './upload-productions/upload-productions.component';
import { BeatsComponent } from './beats/beats.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";


@NgModule({
  declarations: [
    UploadProductionsComponent,
    BeatsComponent,

  ],
  imports: [
    CommonModule,
    ProductionsRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class ProductionsModule { }
