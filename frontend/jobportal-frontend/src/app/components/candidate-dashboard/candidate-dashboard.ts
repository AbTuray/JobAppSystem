import { Component, OnInit } from '@angular/core';
import { JobService } from '../../services/job';
import { CandidateService } from '../../services/candidate';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';
import { CommonModule, NgFor, NgIf } from '@angular/common';

@Component({
  selector: 'app-candidate-dashboard',
  templateUrl: './candidate-dashboard.html',
  styleUrls: ['./candidate-dashboard.css'],
  imports: [NgIf, NgFor, CommonModule]
})
export class CandidateDashboard implements OnInit {

  jobs: any[] = [];
  myApplications: any[] = [];
  candidateId: number = 0;

  constructor(
    private jobService: JobService,
    private candidateService: CandidateService,
    private auth: AuthService
  ) {}

  ngOnInit(): void {
    const id = this.auth.getUserId();
    this.candidateId = id ?? 0;
    this.loadJobs();
    if (this.candidateId) this.loadMyApplications();
  }

  loadJobs() {
    this.jobService.getAllJobs().subscribe({
      next: res => this.jobs = res,
      error: () => Swal.fire('Error','Could not load jobs','error')
    });
  }

  apply(jobId?: number) {
    if (!this.candidateId) {
      Swal.fire('Error','You must be logged in as candidate','error');
      return;
    }
    if (!jobId) {
      Swal.fire('Error','Invalid job id','error');
      return;
    }

    this.candidateService.applyForJob(this.candidateId, jobId).subscribe({
      next: () => {
        Swal.fire('Success','Applied successfully','success');
        this.loadMyApplications();
      },
      error: (err) => Swal.fire('Error', err?.error || 'Apply failed','error')
    });
  }

  loadMyApplications() {
    this.candidateService.viewApplications(this.candidateId).subscribe({
      next: res => this.myApplications = res,
      error: () => Swal.fire('Error','Could not load your applications','error')
    });
  }

  deleteApplication(appId?: number) {
    if (!appId) return;
    this.candidateService.deleteApplication(appId).subscribe({
      next: () => {
        Swal.fire('Deleted','Application removed','success');
        this.loadMyApplications();
      },
      error: () => Swal.fire('Error','Failed to delete application','error')
    });
  }
}
