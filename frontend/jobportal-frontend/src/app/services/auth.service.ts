import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // Register employer
  registerEmployer(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register/employer`, data);
  }

  // Register employee
  registerEmployee(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register/employee`, data);
  }

  // Login (Spring Security handles authentication)
  login(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, data);
  }

  // Logout
  logout(): Observable<any> {
    return this.http.post(`${this.baseUrl}/logout`, {});
  }
}
