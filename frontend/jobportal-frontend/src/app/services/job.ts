import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  private baseUrl = "http://localhost:8080/job";

  constructor(private http: HttpClient) {}

  getAllJobs(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}/all`);
  }

  getJobById(id: number): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${id}`);
  }
}
