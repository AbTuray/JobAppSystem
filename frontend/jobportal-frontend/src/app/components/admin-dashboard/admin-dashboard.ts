import { Component, OnInit } from '@angular/core';
import { AdminService } from '../../services/admin';
import { JobService } from '../../services/job';
import Swal from 'sweetalert2';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.html',
  styleUrls: ['./admin-dashboard.css'],
  imports: [CommonModule]
})
export class AdminDashboard implements OnInit {

  employers: any[] = [];
  candidates: any[] = [];
  pendingApplications: any[] = [];
  jobs: any[] = [];

  constructor(
    private adminService: AdminService,
    private jobService: JobService
  ) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    this.getEmployers();
    this.getCandidates();
    this.getJobs();
    this.getPendingApplications();
  }

  getEmployers() {
    this.adminService.getAllEmployers().subscribe({
      next: res => this.employers = res,
      error: () => Swal.fire('Error','Failed to load employers','error')
    });
  }

  getCandidates() {
    this.adminService.getAllCandidates().subscribe({
      next: res => this.candidates = res,
      error: () => Swal.fire('Error','Failed to load candidates','error')
    });
  }

  getJobs() {
    this.jobService.getAllJobs().subscribe({
      next: res => this.jobs = res,
      error: () => Swal.fire('Error','Failed to load jobs','error')
    });
  }

  getPendingApplications() {
    this.adminService.viewApplications('Pending').subscribe({
      next: res => this.pendingApplications = res,
      error: () => Swal.fire('Error','Failed to load applications','error')
    });
  }

  approveApplication(appId?: number) {
    if (!appId) return;
    this.adminService.approveApplication(appId).subscribe({
      next: () => {
        Swal.fire('Approved','Application approved','success');
        this.getPendingApplications();
      },
      error: () => Swal.fire('Error','Failed to approve','error')
    });
  }

  deleteEmployer(empId?: number) {
    if (!empId) return;
    this.adminService.deleteEmployer(empId).subscribe({
      next: () => {
        Swal.fire('Deleted','Employer removed','success');
        this.getEmployers();
      },
      error: () => Swal.fire('Error','Failed to delete employer','error')
    });
  }

  deleteCandidate(candId?: number) {
    if (!candId) return;
    this.adminService.deleteCandidate(candId).subscribe({
      next: () => {
        Swal.fire('Deleted','Candidate removed','success');
        this.getCandidates();
      },
      error: () => Swal.fire('Error','Failed to delete candidate','error')
    });
  }
}
