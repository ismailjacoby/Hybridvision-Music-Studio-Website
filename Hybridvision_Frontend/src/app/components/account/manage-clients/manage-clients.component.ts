import {Component, OnInit} from '@angular/core';
import {User} from "../../../models/User";
import {AccountService} from "../../../services/account.service";
import {debounceTime, distinctUntilChanged, Subject} from "rxjs";

@Component({
  selector: 'app-manage-clients',
  templateUrl: './manage-clients.component.html',
  styleUrl: './manage-clients.component.css'
})
export class ManageClientsComponent implements OnInit{
  clients: User[] = [];
  filteredClients: User[] = [];
  searchTerm: string = '';
  sortColumn: string | null = null;
  sortDirection: 'asc' | 'desc' = 'asc';
  searchTerms= new Subject<string>();

  constructor(private _accountService: AccountService) {}

  ngOnInit(): void {
    this._accountService.getAllClient().subscribe((data) => {
      this.clients = data;
      this.filteredClients = data;
    });

    this.searchTerms.pipe(
      debounceTime(300),
      distinctUntilChanged()
    ).subscribe(term => {
      this.searchTerm = term;
      this.filterClients();
    });
  }

  changeActiveStatus(username: string){
    this._accountService.deactivateAccount(username).subscribe(()=>
      this._accountService.getAllClient().subscribe(
        (data)=> this.filteredClients=data));
  }

  changeBlockedStatus(username:string){
    this._accountService.blockAccount(username).subscribe(()=>
      this._accountService.getAllClient().subscribe(
        (data)=>this.filteredClients=data));
  }

  sortData(column: keyof User): void {
    if (this.sortColumn === column) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortColumn = column;
      this.sortDirection = 'asc';
    }

    this.clients.sort((a, b) => {
      let aValue = a[column];
      let bValue = b[column];

      if (typeof aValue === 'string' && typeof bValue === 'string') {
        aValue = aValue.toLowerCase();
        bValue = bValue.toLowerCase();
      }

      if (aValue < bValue) {
        return this.sortDirection === 'asc' ? -1 : 1;
      }
      if (aValue > bValue) {
        return this.sortDirection === 'asc' ? 1 : -1;
      }
      return 0;
    });
  }

  filterClients(): void {
    if (!this.searchTerm) {
      this.filteredClients = this.clients;
    } else {
      const lowerCaseSearchTerm = this.searchTerm.toLowerCase();
      this.filteredClients = this.clients.filter(client =>
        client.username.toLowerCase().includes(lowerCaseSearchTerm) ||
        client.firstName.toLowerCase().includes(lowerCaseSearchTerm) ||
        client.lastName.toLowerCase().includes(lowerCaseSearchTerm) ||
        client.email.toLowerCase().includes(lowerCaseSearchTerm)
      );
    }
  }

}




