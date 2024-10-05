import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { SamplePackRoutingModule } from './sample-pack-routing.module';
import { SamplePacksListComponent } from './sample-packs-list/sample-packs-list.component';
import { UploadSamplePackComponent } from './upload-sample-pack/upload-sample-pack.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { SamplePackInformationComponent } from './sample-pack-information/sample-pack-information.component';


@NgModule({
  declarations: [
    SamplePacksListComponent,
    UploadSamplePackComponent,
    SamplePackInformationComponent
  ],
  imports: [
    CommonModule,
    SamplePackRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class SamplePackModule { }
