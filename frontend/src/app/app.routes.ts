import { Routes } from '@angular/router';
import {HomeComponent} from './Feautures/home/home.component';
import {SeanceComponent} from './Feautures/seance/seance.component';

export const routes: Routes = [
  {path:'', component:HomeComponent},
  {path:'home', component:HomeComponent},
  {path:'seance',component:SeanceComponent}

];
