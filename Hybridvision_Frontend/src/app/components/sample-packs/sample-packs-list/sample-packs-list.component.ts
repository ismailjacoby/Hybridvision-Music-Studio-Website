import {Component, OnInit} from '@angular/core';
import {SamplePack} from "../../../models/SamplePack";
import {SamplePackService} from "../../../services/sample-pack.service";
import {AuthService} from "../../../services/auth.service";
import {ShoppingCartService} from "../../../services/shopping-cart.service";
import {LicenseType} from "../../../models/LicenseType";
import {WishlistService} from "../../../services/wishlist.service";


@Component({
  selector: 'app-sample-packs-list',
  templateUrl: './sample-packs-list.component.html',
  styleUrl: './sample-packs-list.component.css'
})
export class SamplePacksListComponent implements OnInit{
  samplePacks: SamplePack[]  = [];
  isAdmin: boolean = this._authService.isAdmin();
  isLoggedIn = this._authService.isLoggedIn();

  constructor(private _samplePackService: SamplePackService,
              private _authService: AuthService,
              private _shoppingCartService: ShoppingCartService,
              private _wishlistService: WishlistService) {
  }

  ngOnInit() {
    this.getSamplePacks();
  }

  getSamplePacks() {
    this._samplePackService.getSamplePacks().subscribe(
      data => {
        this.samplePacks = data;
      },
      error => console.error(error)
    );
  }

  addToCart(itemId: number): void {
    const username = this._authService.getUsername();
    if (username) {
      this._shoppingCartService.addItemToCart(username, itemId, false,this.LicenseType.BASIC).subscribe({
        next: () => console.log('Item added to cart successfully'),
        error: (error) => console.error('Failed to add item to cart', error)
      });
    } else {
      console.error('User is not logged in');
    }
  }

  deleteSamplePack(samplePackId: number): void {
    // Confirmation dialog to make sure the user wants to delete the sample pack
    if (!confirm('Are you sure you want to delete this sample pack?')) {
      return;
    }

    // Call the service method to delete the sample pack by its ID
    this._samplePackService.deleteSamplePack(samplePackId).subscribe({
      next: () => {
        console.log('Sample pack deleted successfully');
        // Refresh the list of sample packs
        this.getSamplePacks();
      },
      error: (error) => {
        console.error('Failed to delete sample pack', error);
      }
    });
  }

  addToWishlist(samplePackId: number): void {
    const username = this._authService.getUsername();
    if (username) {
      // Assuming addToWishlist can accept either a productionId or a samplePackId
      this._wishlistService.addItemToWishlist(username, undefined, samplePackId).subscribe({
        next: () => console.log(`Sample pack ${samplePackId} added to wishlist successfully`),
        error: (error) => console.error(`Failed to add sample pack to wishlist`, error)
      });
    } else {
      console.error('User is not logged in');
    }
  }

  protected readonly LicenseType = LicenseType;
}
