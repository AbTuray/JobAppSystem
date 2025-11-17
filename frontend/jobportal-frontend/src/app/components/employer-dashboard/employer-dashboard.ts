import { Component, OnInit } from '@angular/core';
import { EmployerService } from '../../services/employer';
import { CandidateService } from '../../services/candidate';
import { AuthService } from '../../services/auth.service';
import { ApplicationService } from '../../services/application';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-employer-dashboard',
  templateUrl: './employer-dashboard.html',
  styleUrls: ['./employer-dashboard.css'],
  imports: [CommonModule, FormsModule]
})
export class EmployerDashboard implements OnInit {

  jobs: any[] = [];
  applicantsForSelectedJob: any[] = [];
  employerId: number = 0;
  selectedJobId: number | null = null;

  constructor(
    private employerService: EmployerService,
    private candidateService: CandidateService,
    private auth: AuthService,
    private applicationService: ApplicationService
  ) { }

  ngOnInit(): void {
    const id = this.auth.getUserId();
    this.employerId = id ?? 0;
    if (this.employerId) this.loadJobs();
  }

  loadJobs() {
    this.employerService.getJobsByEmployer().subscribe({
      next: res => this.jobs = res,
      error: err => Swal.fire('Error', 'Could not load your jobs: ' + err.message, 'error')
    });
  }


  viewApplicants(jobId?: number) {
    if (!jobId) return;
    this.selectedJobId = jobId;
    this.candidateService.getApplicationsByJob(jobId).subscribe({
      next: res => this.applicantsForSelectedJob = res,
      error: () => Swal.fire('Error', 'Could not load applicants', 'error')
    });
  }

  acceptApplicant(appId?: number) {
    if (!appId) return;
    this.candidateService.updateApplicationStatus(appId, 'Accepted').subscribe({
      next: () => {
        Swal.fire('Accepted', 'Applicant accepted', 'success');
        if (this.selectedJobId) this.viewApplicants(this.selectedJobId);
      },
      error: () => Swal.fire('Error', 'Failed to accept applicant', 'error')
    });
  }

  removeApplicant(appId?: number) {
    if (!appId) return;
    this.candidateService.deleteApplication(appId).subscribe({
      next: () => {
        Swal.fire('Removed', 'Applicant removed', 'success');
        if (this.selectedJobId) this.viewApplicants(this.selectedJobId);
      },
      error: () => Swal.fire('Error', 'Failed to remove applicant', 'error')
    });
  }

  viewResume(candidateId: number) {
    this.applicationService.downloadResume(candidateId).subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        window.open(url); // opens PDF in new tab if browser supports
      },
      error: () => Swal.fire('Error', 'Could not load resume', 'error')
    });
  }

  // Optional: navigate to add job page or open a modal (not implemented here)
  newJob: any = { title: '', location: '', salary: 0, employer: { id: 0 } };

  addJob() {
    this.employerService.addJob(this.newJob).subscribe({
      next: () => {
        Swal.fire('Success', 'Job added successfully', 'success');
        this.newJob = { title: '', location: '', salary: 0 }; // reset
        this.loadJobs();
      },
      error: err => Swal.fire('Error', 'Failed to add job: ' + (err.error?.message ?? ''), 'error')
    });
  }




  editJob(job: any) {
    this.newJob = { ...job, employer: { id: this.employerId } };
    this.selectedJobId = job.id;
  }

  updateJob() {
    if (!this.selectedJobId) return;

    this.employerService.updateJob(this.selectedJobId, this.newJob).subscribe({
      next: () => {
        Swal.fire('Success', 'Job updated successfully', 'success');
        this.newJob = { title: '', location: '', salary: 0, employer: { id: 0 } };
        this.selectedJobId = null;
        this.loadJobs();
      },
      error: (err) => Swal.fire('Error', 'Failed to update job: ' + err.message, 'error')
    });
  }



  deleteJob(jobId: number) {
    this.employerService.deleteJob(jobId).subscribe({
      next: () => {
        Swal.fire('Deleted', 'Job deleted successfully', 'success');
        this.loadJobs();
      },
      error: (err) => Swal.fire('Error', 'Failed to delete job: ' + err.message, 'error')
    });
  }


}


