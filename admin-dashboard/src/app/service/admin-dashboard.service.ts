import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AdminDashboardService {

  private SERVER_URL = environment.server

  constructor(private http: HttpClient) { 



  }

  public getHttpTraces(): Observable<any> {
    return this.http.get(`${this.SERVER_URL}/httptrace`);
  }
}
