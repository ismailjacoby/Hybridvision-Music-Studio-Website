import {Component, OnInit} from '@angular/core';
import {AccountService} from "../../../services/account.service";
import {User} from "../../../models/User";
import {ActivatedRoute} from "@angular/router";
import {OrderService} from "../../../services/order.service";

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
  styleUrl: './user-details.component.css'
})
export class UserDetailsComponent implements OnInit{
  client!: User;
  orders: any[] = [];

  constructor(private _accountService: AccountService,
              private _route: ActivatedRoute,
              private _orderService: OrderService) {
  }

  ngOnInit(): void {
    const username = this._route.snapshot.paramMap.get('username');
    if(username){
      this._accountService.getUserByUsername(username).subscribe((data) => {
        this.client = data;
      });
    }

    this.loadUserOrders(username!);
  }

  loadUserOrders(username:string):void{
    this._orderService.getOrdersByUsername(username).subscribe({
      next: (orders) =>{
        this.orders=orders;
      },
      error: (error) => console.error('Failed to load orders', error)
    });
  }

  changeActiveStatus(username: string){
    this._accountService.deactivateAccount(username).subscribe(()=>
      this._accountService.getUserByUsername(username).subscribe(
        (data)=> this.client=data));
  }

  changeBlockedStatus(username:string){
    this._accountService.blockAccount(username).subscribe(()=>
      this._accountService.getUserByUsername(username).subscribe(
        (data)=>this.client=data));
  }

}
