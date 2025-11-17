import { Component } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.html',
  imports: [FormsModule]
})
export class Login {

  email = "";
  password = "";

  constructor(private auth: AuthService, private router: Router) {}

  login() {
    this.auth.login({ email: this.email, password: this.password }).subscribe({
      next: (res: any) => {
        this.auth.saveUser(res.role, res.id);

        Swal.fire("Success", "Login Successful", "success");

        if (res.role === "ADMIN") this.router.navigate(["/admin-dashboard"]);
        if (res.role === "EMPLOYER") this.router.navigate(["/employer-dashboard"]);
        if (res.role === "CANDIDATE") this.router.navigate(["/candidate-dashboard"]);
      },
      error: () => Swal.fire("Error", "Invalid Credentials", "error")
    });
  }
}
