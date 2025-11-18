import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { AdminService } from '../../../services/admin.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin-dashboard.html'
})
export class AdminDashboard implements OnInit {

  jobs: any[] = [];
  employers: any[] = [];
  employees: any[] = [];
  applications: any[] = [];

  constructor(private adminService: AdminService) {}

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.adminService.getAllJobs().subscribe(res => this.jobs = res);
    this.adminService.getAllEmployers().subscribe(res => this.employers = res);
    this.adminService.getAllEmployees().subscribe(res => this.employees = res);
    this.adminService.getAllApplications().subscribe(res => this.applications = res);
  }

  approve(app: any) {
    Swal.fire({
      title: `Approve ${app.employeeName}?`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonText: 'Yes'
    }).then(r => {
      if (r.isConfirmed) {
        this.adminService.approveApplication(app.id).subscribe(() => {
          Swal.fire('Approved', 'Application approved', 'success');
          this.loadData();
        });
      }
    });
  }

  deleteEmployer(employer: any) {
    Swal.fire({
      title: `Delete Employer ${employer.firstName}?`,
      icon: 'warning',
      showCancelButton: true
    }).then(r => {
      if (r.isConfirmed) {
        this.adminService.deleteEmployer(employer.id).subscribe(() => {
          Swal.fire('Deleted', 'Employer removed', 'success');
          this.loadData();
        });
      }
    });
  }

  deleteEmployee(employee: any) {
    Swal.fire({
      title: `Delete Employee ${employee.firstName}?`,
      icon: 'warning',
      showCancelButton: true
    }).then(r => {
      if (r.isConfirmed) {
        this.adminService.deleteEmployee(employee.id).subscribe(() => {
          Swal.fire('Deleted', 'Employee removed', 'success');
          this.loadData();
        });
      }
    });
  }
}
