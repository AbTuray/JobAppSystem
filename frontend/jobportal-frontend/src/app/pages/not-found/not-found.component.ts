import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [RouterModule],
  template: `
    <div class="text-center mt-5">
      <h1 class="display-4">404</h1>
      <p class="lead">Page not found</p>
      <a class="btn btn-primary" routerLink="/">Go home</a>
    </div>
  `
})
export class NotFoundComponent {}
