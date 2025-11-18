import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Navbar } from '../../core/navbar/navbar';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterModule, Navbar],
  templateUrl: './home.component.html'
})
export class Home {}
