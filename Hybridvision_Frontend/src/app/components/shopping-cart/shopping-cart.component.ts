import {Component, Injectable} from '@angular/core';
import {CartItems} from "../../models/CartItems";
import {ShoppingCartService} from "../../services/shopping-cart.service";
import {AuthService} from "../../services/auth.service";
import {CheckoutService} from "../../services/checkout.service";
import {loadStripe} from "@stripe/stripe-js";
import {environment} from "../../../environments/environment";
import {HttpClient} from "@angular/common/http";



@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrl: './shopping-cart.component.css'
})

export class ShoppingCartComponent {
  cartItems: CartItems[] = [];
  total: number = 0;
  stripePromise = loadStripe(environment.stripe);

  constructor(private _shoppingCartService: ShoppingCartService,
              private _authService: AuthService,
              private _checkoutService: CheckoutService,
              private _http: HttpClient) {}

  ngOnInit() {
    const username = this._authService.getUsername();
    this._shoppingCartService.getCartItems(username!).subscribe({
      next: (items) => {
        this.cartItems = items;
        this.calculateTotal();
      },
      error: (error) => console.error(error),
    });
  }



  removeItem(cartItemId: number): void {
    const username = this._authService.getUsername();
    if (username) {
      this._shoppingCartService.removeItemFromCart(username, cartItemId).subscribe({
        next: () => {
          this.loadCartItems();
        },
        error: (error) => {
          console.error('Error removing cart item:', error);
        }
      });
    }
  }

  loadCartItems() {
    const username = this._authService.getUsername();
      this._shoppingCartService.getCartItems(username!).subscribe({
        next: (items) => {
          this.cartItems = items;
          this.calculateTotal();
        },
        error: (error) => console.error(error)
      });
  }

  calculateTotal() {
    this.total = this.cartItems.reduce((acc, item) => acc + item.price, 0);
  }

  processOrder(): void {
    const username = this._authService.getUsername();
    if (username) {
      this._checkoutService.processCheckout(username).subscribe({
        next: (orderDto) => {
          console.log('Order processed successfully:', orderDto);
        },
        error: (error) => console.error('Error processing order:', error),
      });
    }
  }

  async checkout(): Promise<void> {

    const checkoutSessionRequest = {
      items: this.cartItems.map(item => ({
        name: item.title,
        stripePriceId: item.stripePriceId,
        quantity: item.quantity || 1,
        amount: 1
      })),
      currency: 'eur',
      successUrl: 'http://localhost:4200/myorders',
      cancelUrl: 'http://localhost:4200/cart',
    };


    try {
      const stripe = await this.stripePromise;
      this._http.post(`${environment.apiUrl}/api/payment`, checkoutSessionRequest)
        .subscribe((data: any) => {
          if (data && data.id) {
            stripe!.redirectToCheckout({
              sessionId: data.id,
            }).then((result) => {
              if (result.error) {
                alert(result.error.message);
              }
            });
          } else {
            console.error('Invalid response from server:', data);
          }
        }, error => {
          console.error('Error creating checkout session:', error);
        });
      this.processOrder();
    } catch (error) {
      console.error('Stripe initialization error:', error);
    }
  }
}
