import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-main-dashboard',
  imports: [CommonModule, RouterModule], // <-- important],
  templateUrl: './main-dashboard.html',
  styleUrl: './main-dashboard.css',
})
export class MainDashboard {

}
