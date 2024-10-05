import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {HomeComponent} from "./components/home/home.component";
import {AboutComponent} from "./components/about/about.component";
import {ServicesComponent} from "./components/services/services.component";
import {ContactComponent} from "./components/contact/contact.component";
import {FaqComponent} from "./components/guidelines/faq/faq.component";
import {LicensingInfoComponent} from "./components/guidelines/licensing-info/licensing-info.component";
import {TermsOfUseComponent} from "./components/guidelines/terms-of-use/terms-of-use.component";
import {PrivacyPolicyComponent} from "./components/guidelines/privacy-policy/privacy-policy.component";
import {Error404Component} from "./components/shared/error404/error404.component";
import {UnsubscribeComponent} from "./components/unsubscribe/unsubscribe.component";
import {ShoppingCartComponent} from "./components/shopping-cart/shopping-cart.component";
import {isClientGuard} from "./utils/guards/isClientGuard";
import {WishlistComponent} from "./components/wishlist/wishlist.component";
import {isLoggedInGuard} from "./utils/guards/isLoggedInGuard";
import {MyOrdersComponent} from "./components/my-orders/my-orders.component";




const routes: Routes = [
  {path:'', redirectTo:'home', pathMatch:'full'},
  {path:'home', component:HomeComponent},
  {path:'about', component:AboutComponent},
  {path:'services', component:ServicesComponent},
  {path:'productions', loadChildren: ()=>import('./components/productions/productions.module').then(m => m.ProductionsModule)},
  {path:'samplepacks', loadChildren: ()=>import('./components/sample-packs/sample-pack.module').then(m => m.SamplePackModule)},
  {path:'contact', component:ContactComponent},
  {path:'account', loadChildren: ()=>import('./components/account/account.module').then(m => m.AccountModule)},
  {path:'licensing', component: LicensingInfoComponent},
  {path:'termsofuse', component:TermsOfUseComponent},
  {path:'privacypolicy', component:PrivacyPolicyComponent},
  {path:'unsubscribe',component:UnsubscribeComponent},
  {path:'myorders', component: MyOrdersComponent, canActivate: [isClientGuard]},
  {path: 'cart', component: ShoppingCartComponent,canActivate: [isLoggedInGuard]},
  {path:'wishlist',  component: WishlistComponent,canActivate: [isLoggedInGuard]},
  {path:'faq', component: FaqComponent},
  {path:'**', component: Error404Component}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
