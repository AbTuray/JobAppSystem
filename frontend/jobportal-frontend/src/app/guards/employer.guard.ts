import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class EmployerGuard implements CanActivate {

  constructor(private auth: AuthService, private router: Router) {}

  canActivate(): boolean {
    if (this.auth.getRole() === "EMPLOYER") return true;

    this.router.navigate(['/login']);
    return false;
  }
}
