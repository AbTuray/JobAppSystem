import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class EmployerService {

  private baseUrl = "http://localhost:8080/employer";

  constructor(private http: HttpClient) {}

  registerEmployer(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, data);
  }

  loginEmployer(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/login`, data);
  }

  getJobsByEmployer(): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseUrl}/employer/jobs`);
}

addJob(job: any): Observable<any> {
  return this.http.post(`${this.baseUrl}/employer/job`, job);
}

updateJob(id: number, job: any): Observable<any> {
  return this.http.put(`${this.baseUrl}/employer/job/${id}`, job);
}

deleteJob(id: number): Observable<any> {
  return this.http.delete(`${this.baseUrl}/employer/job/${id}`, { responseType: 'text' });
}


  // addJob(job: any): Observable<any> {
  //   return this.http.post(`${this.baseUrl}/job`, job);
  // }
  

  // updateJob(id: number, job: any): Observable<any> {
  //   return this.http.put(`${this.baseUrl}/job/${id}`, job);
  // }
  

  // deleteJob(id: number): Observable<any> {
  //   return this.http.delete(`${this.baseUrl}/job/${id}`, { responseType: "text" });
  // }
  

  // getJobsByEmployer(employerId: number): Observable<any[]> {
  //   return this.http.get<any[]>(`${this.baseUrl}/jobs?employerId=${employerId}`);
  // }

  //  getJobsByEmployer(): Observable<any[]> {
//     return this.http.get<any[]>(`${this.baseUrl}/jobs`);
//  }

}

// Add a job (backend assigns the logged-in employer automatically)
  // addJob(job: any): Observable<any> {
  //   return this.http.post(`${this.baseUrl}/job`, job);
  // }

  // // Update a job
  // updateJob(jobId: number, job: any): Observable<any> {
  //   return this.http.put(`${this.baseUrl}/job/${jobId}`, job);
  // }

  // // Delete a job
  // deleteJob(jobId: number): Observable<any> {
  //   return this.http.delete(`${this.baseUrl}/job/${jobId}`, { responseType: 'text' });
  // }

  // // Load all jobs for the logged-in employer
  // getJobsByEmployer(): Observable<any[]> {
  //   return this.http.get<any[]>(`${this.baseUrl}/jobs`);
  // }


