import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import {UserPayload} from '../../Models/interfaces/user-payload';

@Injectable({
  providedIn: 'root'
})
export class JwtServiceService {

  constructor() { }

  getDecodedToken (token: string | null) {
    return token ? jwtDecode<UserPayload>(token) : null;
  }
}
