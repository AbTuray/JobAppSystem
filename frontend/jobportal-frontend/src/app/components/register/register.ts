import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.html',
  imports: [FormsModule, CommonModule, RouterModule]
})
export class RegisterComponent {

  // Mode can be candidate or employer
  mode: "candidate" | "employer" = "candidate";

  // Form object with all possible fields
  form: any = {
    firstName: '',
    lastName: '',
    email: '',
    phone: '',
    address: '',
    password: '',
    office: ''  // required for employer
  };

  constructor(private auth: AuthService) {}

  register() {
    // Basic front-end validation
    if (!this.form.firstName || !this.form.lastName || !this.form.email || !this.form.password) {
      Swal.fire("Error", "Please fill all required fields", "error");
      return;
    }

    // If employer, ensure office is provided
    if (this.mode === 'employer' && !this.form.office) {
      Swal.fire("Error", "Office is required for employer", "error");
      return;
    }

    if (this.mode === "candidate") {
      this.auth.registerCandidate(this.form).subscribe({
        next: () => Swal.fire("Success","Candidate Registered","success"),
        error: (err) => Swal.fire("Error", err.error?.message || "Failed","error")
      });
    } else {
      this.auth.registerEmployer(this.form).subscribe({
        next: () => Swal.fire("Success","Employer Registered","success"),
        error: (err) => Swal.fire("Error", err.error?.message || "Failed","error")
      });
    }
  }
}
