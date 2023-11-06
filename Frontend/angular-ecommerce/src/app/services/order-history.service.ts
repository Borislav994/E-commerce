import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class OrderHistoryService {

  private orderUrl = environment.luv2shopApiUrl + '/orders';

  constructor(private httpClient: HttpClient) { }

  getOrderHistory(theEmail: string,  thePage: number, thePageSize: number): Observable<any>{

    // need to build url based on the customer email
    const orderHistoryUrl = `${this.orderUrl}/search/findByCustomerEmail?theEmail=${theEmail}&page=${thePage}&size=${thePageSize}`;

    return this.httpClient.get<any>(orderHistoryUrl);
  }
}
