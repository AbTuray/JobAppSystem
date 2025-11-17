import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {

  constructor(private auth: AuthService, private router: Router) {}

  canActivate(route: any): boolean {
    const requiredRole = route.data['role'];
    const userRole = this.auth.getRole();

    // NOT LOGGED IN
    if (!userRole) {
      this.router.navigate(['/login']);
      return false;
    }

    // LOGGED IN BUT WRONG ROLE
    if (requiredRole && requiredRole !== userRole) {
      this.redirectUser(userRole);
      return false;
    }

    return true;
  }

  private redirectUser(role: string) {
    if (role === 'CANDIDATE') this.router.navigate(['/candidate-dashboard']);
    if (role === 'EMPLOYER') this.router.navigate(['/employer-dashboard']);
    if (role === 'ADMIN') this.router.navigate(['/admin-dashboard']);
  }
}
