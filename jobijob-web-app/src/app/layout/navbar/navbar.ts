import { Component, inject } from '@angular/core';
import { KeycloakService } from '../../features/auth/keycloakService';
import { AsyncPipe } from '@angular/common';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-navbar',
  imports: [AsyncPipe],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  keycloakService = inject(KeycloakService);

  isAuthenticated = toSignal(this.keycloakService.isAuthenticated$);
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
