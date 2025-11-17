import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CandidateService {

  private baseUrl = "http://localhost:8080/candidate";

  constructor(private http: HttpClient) {}

  registerCandidate(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, data);
  }

  loginCandidate(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, data);
  }

  searchJobs(keyword?: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/jobs${keyword ? `?keyword=${keyword}` : ''}`);
  }

  applyForJob(candidateId: number, jobId: number): Observable<any> {
    return this.http.post(`${this.baseUrl}/apply?candidateId=${candidateId}&jobId=${jobId}`, {});
  }

  viewApplications(candidateId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/applications?candidateId=${candidateId}`);
  }

  deleteApplication(appId: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/application/${appId}`, { responseType: "text" });
  }

  getApplicationsByJob(jobId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/job/${jobId}/applications`);
  }

  updateApplicationStatus(appId: number, status: string): Observable<any> {
    return this.http.put(`${this.baseUrl}/application/${appId}/status?status=${status}`, {});
  }

  downloadResume(candidateId: number) {
  return this.http.get(`${this.baseUrl}/resume?candidateId=${candidateId}`, { responseType: 'blob' });
}

}
