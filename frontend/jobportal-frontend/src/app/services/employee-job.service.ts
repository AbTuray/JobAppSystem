import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EmployeeJobService {

  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  // Get all jobs or search by query
  searchJobs(query?: string): Observable<any[]> {
    const q = query ? `?q=${query}` : '';
    return this.http.get<any[]>(`${this.baseUrl}/employee/jobs${q}`);
  }

  // Apply for a job
  applyForJob(application: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/employee/apply`, application);
  }

  // Edit existing application
  editApplication(id: number, application: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/employee/application/${id}`, application);
  }

  // Delete application
  deleteApplication(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/employee/application/${id}`);
  }
}
