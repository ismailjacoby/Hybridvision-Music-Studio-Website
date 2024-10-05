import {Component, OnInit} from '@angular/core';
import {SamplePackService} from "../../../services/sample-pack.service";
import {SamplePack} from "../../../models/SamplePack";
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../../services/auth.service";
import {ShoppingCartService} from "../../../services/shopping-cart.service";
import {LicenseType} from "../../../models/LicenseType";


@Component({
  selector: 'app-sample-pack-information',
  templateUrl: './sample-pack-information.component.html',
  styleUrl: './sample-pack-information.component.css'
})
export class SamplePackInformationComponent implements OnInit{
  id!:number;
  title!:string;
  price!: number;
  description!: string;
  audioUrl!:string;
  coverUrl!:string;
  samplePack?: SamplePack;
  isLoggedIn = this._authService.isLoggedIn();

  constructor(private _samplePackService: SamplePackService,
              private _route: ActivatedRoute,
              private _authService: AuthService,
              private _shoppingCartService: ShoppingCartService) {

  }

  ngOnInit() {
    this.getSamplePack();
  }

  addToCart(itemId :number) {
    const username = this._authService.getUsername();
    if (username) {
      this._shoppingCartService.addItemToCart(username, itemId, false,LicenseType.BASIC).subscribe({
        next: () => console.log('Item added to cart successfully'),
        error: (error) => console.error('Failed to add item to cart', error)
      });
    } else {
      console.error('User is not logged in');
    }
  }

  getSamplePack(): void {
    // Retrieve the sample pack ID from the route parameters
    const id = +this._route.snapshot.paramMap.get('id')!;
    this._samplePackService.getSamplePackById(id).subscribe({
      next: (samplePack: SamplePack) => {
        this.samplePack = samplePack;
        this.id = samplePack.id;
        this.title = samplePack.title;
        this.description = samplePack.description;
        this.audioUrl = samplePack.audioUrl;
        this.coverUrl = samplePack.coverImageUrl;
      },
      error: (err) => console.error('Failed to fetch sample pack:', err)
    });
  }
}
