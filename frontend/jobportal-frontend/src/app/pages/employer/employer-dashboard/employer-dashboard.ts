import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { EmployerJobService } from '../../../services/employer-job.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-employer-dashboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './employer-dashboard.html'
})
export class EmployerDashboard implements OnInit {

  jobs: any[] = [];
  showAddForm = false;

  newJob = { title: '', location: '', salary: '' };

  constructor(private jobService: EmployerJobService) {}

  ngOnInit() {
    this.loadJobs();
  }

  loadJobs() {
    this.jobService.getMyJobs().subscribe({
      next: res => this.jobs = res,
      error: () => Swal.fire("Error", "Could not load jobs", "error")
    });
  }

  saveJob(form: any) {
    this.jobService.addJob(this.newJob).subscribe({
      next: () => {
        Swal.fire("Success", "Job Added", "success");
        this.showAddForm = false;
        form.reset();
        this.loadJobs();
      }
    });
  }

  edit(job: any) {
    Swal.fire({
      title: "Edit Job",
      html: `
        <input id="title" class="swal2-input" value="${job.title}">
        <input id="location" class="swal2-input" value="${job.location}">
        <input id="salary" class="swal2-input" value="${job.salary}">
      `,
      preConfirm: () => ({
        title: (document.getElementById('title') as any).value,
        location: (document.getElementById('location') as any).value,
        salary: (document.getElementById('salary') as any).value
      })
    }).then(res => {
      if (res.isConfirmed) {
        this.jobService.updateJob(job.id, res.value).subscribe(() => {
          Swal.fire("Updated", "Job updated", "success");
          this.loadJobs();
        });
      }
    });
  }

  remove(job: any) {
    Swal.fire({
      title: "Delete Job?",
      icon: "warning",
      showCancelButton: true
    }).then(r => {
      if (r.isConfirmed) {
        this.jobService.deleteJob(job.id).subscribe(() => {
          Swal.fire("Deleted", "Job removed", "success");
          this.loadJobs();
        });
      }
    });
  }
}
