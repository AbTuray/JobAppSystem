import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html'
})
export class Login {

  loginData = { email: '', password: '' };

  constructor(private authService: AuthService, private router: Router) {}

  login() {
    this.authService.login(this.loginData).subscribe({
      next: () => {
        Swal.fire('Success', 'Logged in successfully', 'success').then(() => {
          // Redirect based on role (simplified for practice)
          if (this.loginData.email.includes('admin')) {
            this.router.navigate(['/admin']);
          } else if (this.loginData.email.includes('employer')) {
            this.router.navigate(['/employer']);
          } else {
            this.router.navigate(['/employee']);
          }
        });
      },
      error: () => Swal.fire('Error', 'Login failed', 'error')
    });
  }
}
