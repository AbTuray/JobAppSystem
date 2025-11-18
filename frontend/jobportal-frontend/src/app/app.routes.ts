import { Routes } from '@angular/router';
import { Login } from './pages/auth/login/login';
import { Register } from './pages/auth/register/register';
import { EmployerDashboard } from './pages/employer/employer-dashboard/employer-dashboard';
import { EmployeeDashboard } from './pages/employee/employee-dashboard/employee-dashboard';
import { AdminDashboard } from './pages/admin/admin-dashboard/admin-dashboard';

import {Home} from './pages/home/home';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'login', component: Login },
  { path: 'register', component: Register },
  { path: 'employer', component: EmployerDashboard },
  { path: 'employee', component: EmployeeDashboard },
  { path: 'admin', component: AdminDashboard },
  { path: '**', redirectTo: '' }
];
