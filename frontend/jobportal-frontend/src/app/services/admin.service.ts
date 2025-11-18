import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AdminService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // Jobs
  getAllJobs(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/admin/jobs`);
  }

  // Employers
  getAllEmployers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/admin/employers`);
  }

  // Employees
  getAllEmployees(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/admin/employees`);
  }

  // Applications
  getAllApplications(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/admin/applications`);
  }

  // Approve application
  approveApplication(id: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/admin/applications/${id}/approve`, {});
  }

  // Delete Employer
  deleteEmployer(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/admin/employer/${id}`);
  }

  // Delete Employee
  deleteEmployee(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/admin/employee/${id}`);
  }
}
