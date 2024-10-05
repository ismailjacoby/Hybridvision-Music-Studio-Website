import {Component,OnInit } from '@angular/core';
import {AuthService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {ShoppingCartService} from "../../../services/shopping-cart.service";


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit{
  isMenuOpen = false;
  isDropdownOpen: boolean = false;
  isClient: boolean = this._authService.isClient();
  isAdmin: boolean = this._authService.isAdmin();
  isCartDropdownOpen: boolean = false;



  constructor(private _shoppingCartService: ShoppingCartService,
              private readonly _authService: AuthService,
              private _router: Router) {}

  ngOnInit() {
    const username = this._authService.getUsername();
    if (username) {
      this._shoppingCartService.getCartItems(username).subscribe({
        next: (items) => {
        },
        error: (error) => console.error(error),
      });
    }
    this._authService.connectedUser.subscribe(user => {
      this.isClient = this._authService.isClient();
      this.isAdmin = this._authService.isAdmin();
    });
  }

  /*
   * Logout from website
   */
  logout(){
    this.closeModal();
    return this._authService.logout();
  }

  /*
  * Check if user is logged in
  */
  isLoggedIn(): boolean{
    return this._authService.isLoggedIn();
  }

  /*
  * Toggle Menu
  */
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }

  /*
  * Toggle Dropdown
  */
  toggleDropdown(){
    this.isDropdownOpen = !this.isDropdownOpen;
  }

  /*
  * Open Modal
  */
  openModal() {
    document.getElementById('myModal')!.style.display = "block";
  }

  /*
  * Close Modal
  */
  closeModal() {
    document.getElementById('myModal')!.style.display = "none";
  }

  /* Cart Dropdown */

  toggleCartDropdown() {
    this.isCartDropdownOpen = true;
  }

  closeCartDropdown() {
    this.isCartDropdownOpen = false;
  }



}
