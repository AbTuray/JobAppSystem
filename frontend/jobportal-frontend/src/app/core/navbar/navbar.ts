import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './navbar.html'
})
export class Navbar {

  constructor(private authService: AuthService, private router: Router) {}

  isLoggedIn(): boolean {
    return !!localStorage.getItem('userRole');
  }

  getRole(): string | null {
    return localStorage.getItem('userRole');
  }

  logout() {
    Swal.fire({
      title: 'Logout?',
      icon: 'question',
      showCancelButton: true
    }).then(res => {
      if (res.isConfirmed) {
        this.authService.logout().subscribe(() => {
          localStorage.removeItem('userRole');
          Swal.fire('Logged out', '', 'success').then(() => {
            this.router.navigate(['/login']);
          });
        });
      }
    });
  }
}
