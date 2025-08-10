import { Routes } from '@angular/router';
import {HomeComponent} from './Feautures/home/home.component';
import {SeanceComponent} from './Feautures/seance/seance.component';
import {RegistreComponent} from './Feautures/authentification/registre/registre.component';
import {LoginComponent} from './Feautures/authentification/login/login.component';
import {AdminComponent} from './Feautures/Admin/admin.component';

export const routes: Routes = [
  {path:'', component:HomeComponent},
  {path:'home', component:HomeComponent},
  {path:'seance',component:SeanceComponent},
  {path:'registre',component:RegistreComponent},
  {path:'login',component:LoginComponent},
  { path: 'admin', component: AdminComponent }
];
