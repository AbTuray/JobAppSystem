import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { EmployeeJobService } from '../../../services/employee-job.service';

@Component({
  selector: 'app-employee-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employee-dashboard.html'
})
export class EmployeeDashboard implements OnInit {

  jobs: any[] = [];
  applications: any[] = [];
  searchQuery = '';
  newApplication: any = { jobId: 0, resume: '', coverLetter: '' }; // adjust fields to your backend

  constructor(private employeeService: EmployeeJobService) {}

  ngOnInit() {
    this.loadJobs();
  }

  loadJobs() {
    this.employeeService.searchJobs(this.searchQuery).subscribe({
      next: res => this.jobs = res,
      error: () => Swal.fire('Error', 'Cannot load jobs', 'error')
    });
  }

  search() {
    this.loadJobs();
  }

  apply(jobId: number) {
    this.newApplication.jobId = jobId;
    Swal.fire({
      title: 'Apply for Job',
      html: `
        <input id="resume" class="swal2-input" placeholder="Resume link" value="${this.newApplication.resume}">
        <input id="coverLetter" class="swal2-input" placeholder="Cover Letter" value="${this.newApplication.coverLetter}">
      `,
      preConfirm: () => ({
        jobId: jobId,
        resume: (document.getElementById('resume') as any).value,
        coverLetter: (document.getElementById('coverLetter') as any).value
      })
    }).then(res => {
      if (res.isConfirmed) {
        this.employeeService.applyForJob(res.value).subscribe({
          next: () => Swal.fire('Applied!', 'Application submitted', 'success')
        });
      }
    });
  }

  editApplication(app: any) {
    Swal.fire({
      title: 'Edit Application',
      html: `
        <input id="resume" class="swal2-input" value="${app.resume}">
        <input id="coverLetter" class="swal2-input" value="${app.coverLetter}">
      `,
      preConfirm: () => ({
        resume: (document.getElementById('resume') as any).value,
        coverLetter: (document.getElementById('coverLetter') as any).value
      })
    }).then(res => {
      if (res.isConfirmed) {
        this.employeeService.editApplication(app.id, res.value).subscribe({
          next: () => Swal.fire('Updated!', 'Application updated', 'success')
        });
      }
    });
  }

  deleteApplication(app: any) {
    Swal.fire({
      title: 'Delete Application?',
      icon: 'warning',
      showCancelButton: true
    }).then(r => {
      if (r.isConfirmed) {
        this.employeeService.deleteApplication(app.id).subscribe({
          next: () => Swal.fire('Deleted', 'Application removed', 'success')
        });
      }
    });
  }
}
