import { Component, inject } from '@angular/core';
import { KeycloakService } from '../../features/auth/keycloakService';
import { AsyncPipe } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';
import { AuthService } from '../../features/auth/auth-service';

@Component({
  selector: 'app-navbar',
  imports: [],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  keycloakService = inject(KeycloakService);

  isAuthenticated = this.keycloakService.isAuthenticatedFunction();

  authService = inject(AuthService);

  connectedUser = toSignal(this.authService.connectedUser$);
  //
  login() {
    this.keycloakService.login();
  }
  register() {
    this.keycloakService.register();
  }
  logout() {
    this.keycloakService.logout();
  }
}
