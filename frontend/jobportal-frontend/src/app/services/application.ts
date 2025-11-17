import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Application } from '../models/application.model';

@Injectable({
  providedIn: 'root',
})
export class ApplicationService {

  private baseUrl = 'http://localhost:8080/application';

  constructor(private http: HttpClient) { }

  // getApplicationsByCandidate(candidateId: number): Observable<Application[]> {
  //   return this.http.get<Application[]>(`${this.baseUrl}/candidate/${candidateId}`);
  // }

  // Get applications for a job
  getApplicationsByJob(jobId: number) {
    return this.http.get<Application[]>(`http://localhost:8080/candidate/job/${jobId}/applications`);
  }

  // Update application status
  updateApplicationStatus(applicationId: number, status: string) {
    return this.http.put(`http://localhost:8080/candidate/application/${applicationId}/status?status=${status}`, {});
  }

  // Delete application
  deleteApplication(applicationId: number) {
    return this.http.delete(`http://localhost:8080/candidate/application/${applicationId}`);
  }

  apply(jobId: number, candidateId: number) {
  return this.http.post(`http://localhost:8080/candidate/apply?jobId=${jobId}&candidateId=${candidateId}`, {});
}

getApplicationsByCandidate(candidateId: number) {
  return this.http.get<any[]>(`http://localhost:8080/candidate/${candidateId}/applications`);
}

downloadResume(candidateId: number) {
  return this.http.get(`http://localhost:8080/candidate/resume?candidateId=${candidateId}`, { responseType: 'blob' });
}


}
