import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({ providedIn: 'root' })
export class AuthService {

  baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  login(data: any) {
    return this.http.post(`${this.baseUrl}/login`, data);
  }

  registerEmployer(data: any) {
    return this.http.post(`${this.baseUrl}/register/employer`, data);
  }

  registerCandidate(data: any) {
    return this.http.post(`${this.baseUrl}/register/candidate`, data);
  }

  saveUser(role: string, id: number) {
    localStorage.setItem("role", role);
    localStorage.setItem("userId", id.toString());
  }

  getRole() {
    return localStorage.getItem("role");
  }

  getUserId() {
    const id = localStorage.getItem("userId");
    return id ? Number(id) : null;
  }

  logout() {
    localStorage.clear();
  }
}
