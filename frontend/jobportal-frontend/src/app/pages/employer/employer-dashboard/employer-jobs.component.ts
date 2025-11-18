import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmployerJobService, Job } from '../../../services/employer-job.service';
import { EmployerJobFormComponent } from './employer-job-form.component';

@Component({
  selector: 'app-employer-jobs',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employer-jobs.component.html',
  styleUrls: ['./employer-jobs.component.css']
})
export class EmployerJobsComponent implements OnInit {
  jobs: Job[] = [];

  constructor(private jobService: EmployerJobService) {}

  ngOnInit() {
    this.loadJobs();
  }

  loadJobs() {
    this.jobService.getJobs().subscribe({
      next: (res) => this.jobs = res,
      error: (err) => Swal.fire('Error', 'Failed to load jobs', 'error')
    });
  }

  showJobForm(job?: Job) {
    Swal.fire({
      title: job ? 'Edit Job' : 'Add Job',
      html: `<div id="jobFormContainer"></div>`,
      didOpen: () => {
        const formCmp = new EmployerJobFormComponent();
        if(job) formCmp.job = {...job};
        formCmp.submitCallback = (updatedJob) => {
          if(job) {
            this.jobService.updateJob({...updatedJob, id: job.id}).subscribe({
              next: () => { this.loadJobs(); Swal.fire('Success', 'Job updated', 'success'); },
              error: () => Swal.fire('Error', 'Update failed', 'error')
            });
          } else {
            this.jobService.addJob(updatedJob).subscribe({
              next: () => { this.loadJobs(); Swal.fire('Success', 'Job added', 'success'); },
              error: () => Swal.fire('Error', 'Add failed', 'error')
            });
          }
          Swal.close();
        };
        formCmp.render('#jobFormContainer');
      },
      showConfirmButton: false
    });
  }

  deleteJob(job: Job) {
    Swal.fire({
      title: 'Are you sure?',
      text: `Delete job: ${job.title}?`,
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Yes, delete'
    }).then(result => {
      if(result.isConfirmed) {
        this.jobService.deleteJob(job.id!).subscribe({
          next: () => { this.loadJobs(); Swal.fire('Deleted!', 'Job has been deleted.', 'success'); },
          error: () => Swal.fire('Error', 'Delete failed', 'error')
        });
      }
    });
  }
}
