import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import Swal from 'sweetalert2';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html'
})
export class Register {

  userType: 'employee' | 'employer' = 'employee';
  registerData: any = { firstName:'', lastName:'', email:'', password:'' };

  constructor(private authService: AuthService, private router: Router) {}

  register() {
    const service = this.userType === 'employee' ?
      this.authService.registerEmployee(this.registerData) :
      this.authService.registerEmployer(this.registerData);

    service.subscribe({
      next: () => {
        Swal.fire('Success', `${this.userType} registered`, 'success').then(() => {
          this.router.navigate(['/login']);
        });
      },
      error: () => Swal.fire('Error', 'Registration failed', 'error')
    });
  }
}
