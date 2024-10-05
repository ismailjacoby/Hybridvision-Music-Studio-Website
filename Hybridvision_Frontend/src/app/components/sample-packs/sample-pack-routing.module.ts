import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {SamplePacksListComponent} from "./sample-packs-list/sample-packs-list.component";
import {UploadSamplePackComponent} from "./upload-sample-pack/upload-sample-pack.component";
import {SamplePackInformationComponent} from "./sample-pack-information/sample-pack-information.component";

const routes: Routes = [
  {path:'',redirectTo:'list', pathMatch:'full'},
  {path:'list', component:SamplePacksListComponent},
  {path:'upload', component:UploadSamplePackComponent},
  {path:':id', component:SamplePackInformationComponent}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class SamplePackRoutingModule { }
