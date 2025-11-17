import { Routes } from '@angular/router';
import { MainDashboard } from './components/main-dashboard/main-dashboard';
import { Login } from './components/login/login';
import { RegisterComponent } from './components/register/register';
import { CandidateDashboard } from './components/candidate-dashboard/candidate-dashboard';
import { EmployerDashboard } from './components/employer-dashboard/employer-dashboard';
import { AdminDashboard } from './components/admin-dashboard/admin-dashboard';
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';
import { AdminGuard } from './guards/admin.guard';
import { EmployerGuard } from './guards/employer.guard';
import { CandidateGuard } from './guards/candidate.guard';
export const routes: Routes = [

  { path: '', component: MainDashboard },

  { path: 'login', component: Login },
  { path: 'register', component: RegisterComponent },

  {
    path: 'candidate-dashboard',
    component: CandidateDashboard,
    canActivate: [AuthGuard,RoleGuard],
    data: { role: 'CANDIDATE' }
  },

  {
    path: 'employer-dashboard',
    component: EmployerDashboard,
    canActivate: [AuthGuard,RoleGuard],
    data: { role: 'EMPLOYER' }
  },

  {
    path: 'admin-dashboard',
    component: AdminDashboard,
    canActivate: [AuthGuard,RoleGuard],
    data: { role: 'ADMIN' }
  },

  { path: '**', redirectTo: '' } // fallback
  
// [AuthGuard, RoleGuard], data: { role: 'CANDIDATE' }
// [AuthGuard, RoleGuard], data: { role: 'EMPLOYER' }
// [AuthGuard, RoleGuard], data: { role: 'ADMIN' }
];
