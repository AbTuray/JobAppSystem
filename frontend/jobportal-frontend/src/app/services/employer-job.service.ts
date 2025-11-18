import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class EmployerJobService {
  private baseUrl = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getMyJobs(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/employer/jobs`);
  }

  addJob(job: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/employer/job`, job);
  }

  updateJob(id: number, job: any): Observable<any> {
    return this.http.put(`${this.baseUrl}/employer/job/${id}`, job);
  }

  deleteJob(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/employer/job/${id}`);
  }
}
