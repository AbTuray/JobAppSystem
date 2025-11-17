import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = "http://localhost:8080/admin";

  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(`${this.baseUrl}/login?username=${username}&password=${password}`, {}, { responseType: "text" });
  }

  getAllEmployers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/employers`);
  }

  getAllCandidates(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/candidates`);
  }

  deleteEmployer(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/employer/${id}`, { responseType: "text" });
  }

  deleteCandidate(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/candidate/${id}`, { responseType: "text" });
  }

  viewApplications(status: string = "Pending"): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/applications?status=${status}`);
  }

  approveApplication(id: number): Observable<any> {
    return this.http.put(`${this.baseUrl}/approve/${id}`, {});
  }
}
