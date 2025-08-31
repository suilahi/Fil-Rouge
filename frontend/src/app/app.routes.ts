import { Routes } from '@angular/router';
import {HomeComponent} from './Feautures/home/home.component';
import {SeanceComponent} from './Feautures/seance/seance.component';
import {RegistreComponent} from './Feautures/authentification/registre/registre.component';
import {LoginComponent} from './Feautures/authentification/login/login.component';
import {AdminComponent} from './Feautures/Admin/admin.component';
import {MembreComponent} from './Feautures/membre/membre.component';
import {authGuardsGuard} from './core/guards/auth/auth-guards.guard';
import {adminGuardsGuard} from './core/guards/admin/admin-guards.guard';
import {membreGuard} from './core/guards/membre/membre.guard';

export const routes: Routes = [
  {path:'', component:HomeComponent},
  {path:'home', component:HomeComponent},
  {path:'seance',component:SeanceComponent},
  {path:'registre',component:RegistreComponent,canActivate:[authGuardsGuard]},
  {path:'login',component:LoginComponent,canActivate:[authGuardsGuard]},
  { path: 'admin', component: AdminComponent ,canActivate:[adminGuardsGuard]},
  {path:'membre',component:MembreComponent,canActivate:[membreGuard]},
];
