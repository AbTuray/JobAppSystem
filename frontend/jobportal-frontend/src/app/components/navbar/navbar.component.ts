import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  imports: [CommonModule]
})
export class NavbarComponent {

  constructor(public auth: AuthService, private router: Router) {}

  isLoggedIn(): boolean {
    return this.auth.getRole() !== null;
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}
