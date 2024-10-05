import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { Error404Component } from './components/shared/error404/error404.component';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/shared/navbar/navbar.component';
import { FooterComponent } from './components/shared/footer/footer.component';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { ServicesComponent } from './components/services/services.component';
import { ProductionsComponent } from './components/productions/productions.component';
import { SamplePacksComponent } from './components/sample-packs/sample-packs.component';
import { ContactComponent } from './components/contact/contact.component';
import { FaqComponent } from './components/guidelines/faq/faq.component';
import { LicensingInfoComponent } from './components/guidelines/licensing-info/licensing-info.component';
import { TermsOfUseComponent } from './components/guidelines/terms-of-use/terms-of-use.component';
import { PrivacyPolicyComponent } from './components/guidelines/privacy-policy/privacy-policy.component';
import { UnsubscribeComponent } from './components/unsubscribe/unsubscribe.component';
import {HttpClientModule} from "@angular/common/http";
import { AccountComponent } from './components/account/account.component';
import { ShoppingCartComponent } from './components/shopping-cart/shopping-cart.component';
import {NgOptimizedImage} from "@angular/common";
import { WishlistComponent } from './components/wishlist/wishlist.component';
import { MyOrdersComponent } from './components/my-orders/my-orders.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';


@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    FooterComponent,
    HomeComponent,
    AboutComponent,
    ServicesComponent,
    ProductionsComponent,
    SamplePacksComponent,
    ContactComponent,
    FaqComponent,
    LicensingInfoComponent,
    TermsOfUseComponent,
    PrivacyPolicyComponent,
    Error404Component,
    UnsubscribeComponent,
    AccountComponent,
    ShoppingCartComponent,
    WishlistComponent,
    MyOrdersComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        HttpClientModule,
        ReactiveFormsModule,
        NgOptimizedImage
    ],
  providers: [
    provideAnimationsAsync()
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
