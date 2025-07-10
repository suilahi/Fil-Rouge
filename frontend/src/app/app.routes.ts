import { Routes } from '@angular/router';
import {HomeComponent} from './Feautures/home/home.component';
import {SeanceComponent} from './Feautures/seance/seance.component';
import {RegistreComponent} from './Feautures/authentification/registre/registre.component';

export const routes: Routes = [
  {path:'', component:HomeComponent},
  {path:'home', component:HomeComponent},
  {path:'seance',component:SeanceComponent},
  {path:'registre',component:RegistreComponent}

];
