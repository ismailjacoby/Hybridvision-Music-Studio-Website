import {Component, OnInit} from '@angular/core';
import {OrderService} from "../../services/order.service";
import {AuthService} from "../../services/auth.service";

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrl: './my-orders.component.css'
})
export class MyOrdersComponent implements OnInit {
  orders: any = [];

  constructor(private _orderService: OrderService, private _authService: AuthService) { }

  ngOnInit(): void {
    this.loadUserOrders();
  }

  loadUserOrders(): void {
    const username = this._authService.getUsername();
    if (username) {
      this._orderService.getOrdersByUsername(username).subscribe({
        next: (orders) => this.orders = orders,
        error: (error) => console.error('Failed to load orders', error)
      });
    } else {
      console.error('Username is not available');
    }
  }
}
