import { Component } from '@angular/core';
import {ShoppingCartService} from "../../services/shopping-cart.service";
import {WishlistService} from "../../services/wishlist.service";
import {AuthService} from "../../services/auth.service";
import {WishlistItems} from "../../models/WishlistItems";
import {LicenseType} from "../../models/LicenseType";
import {Observable} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.css'
})
export class WishlistComponent {
  wishlistItems: WishlistItems[] = [];

  constructor(
    private _shoppingCartService: ShoppingCartService,
    private _wishlistService: WishlistService,
    private _authService: AuthService
  ) {}

  ngOnInit(): void {
    this.loadWishlistItems();
  }

  addToCart(item: WishlistItems): void {
    const username = this._authService.getUsername();
    if (!username) {
      console.error('User is not logged in.');
      return;
    }

    const isProduction = item.itemType === 'Production';

    const licenseType = item.licenseType || LicenseType.BASIC;

    this._shoppingCartService.addItemToCart(username, item.id, isProduction, licenseType).subscribe({
      next: () => {
        console.log('Item added to cart successfully');
        this.removeFromWishlist(item);
      },
      error: (error) => console.error('Failed to add item to cart', error)
    });
  }

  removeFromWishlist(item: WishlistItems): void {
    const username = this._authService.getUsername();
    if (!username) {
      console.error('User is not logged in.');
      return;
    }

    const productionId = item.itemType === 'Production' ? item.id : undefined;
    const samplePackId = item.itemType === 'SamplePack' ? item.id : undefined;

    this._wishlistService.removeItemFromWishlist(username, productionId, samplePackId).subscribe({
      next: () => {
        console.log('Item removed from wishlist successfully');
        // Refresh the displayed wishlist items
        this.refreshWishlist();
      },
      error: (error) => console.error('Failed to remove item from wishlist', error)
    });
  }


  refreshWishlist(): void {
    const username = this._authService.getUsername();
    this._wishlistService.getWishlist(username!).subscribe({
      next: (items) => this.wishlistItems = items,
      error: (error) => console.error('Failed to load wishlist items', error)
    });
  }


  loadWishlistItems(): void {
    const username = this._authService.getUsername();
    if (username) {
      this._wishlistService.getWishlist(username).subscribe({
        next: (items) => this.wishlistItems = items,
        error: (error) => console.error('Failed to load wishlist items', error)
      });
    } else {
      console.error('No user is currently logged in.');
    }
  }
}
