import { Injectable } from '@angular/core';
import Keycloak from 'keycloak-js';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class KeycloakService {
  constructor() {}

  private authenticatedSubject = new BehaviorSubject<boolean | null>(null);
  isAuthenticated$ = this.authenticatedSubject.asObservable();

  private readonly keycloak: Keycloak = new Keycloak({
    url: 'http://localhost:8180',
    realm: 'jobijob-pro',
    clientId: 'jobijob-web',
  });

  async init(): Promise<void> {
    const isAuthenticated = await this.keycloak.init({
      onLoad: 'check-sso',
      pkceMethod: 'S256',
    });
    this.authenticatedSubject.next(isAuthenticated);
  }

  login(): Promise<void> {
    return this.keycloak.login();
  }

  register(): Promise<void> {
    return this.keycloak.register();
  }

  logout(): Promise<void> {
    return this.keycloak.logout({
      redirectUri: window.location.origin,
    });
  }

  getToken(): string | undefined {
    return this.keycloak.token;
  }
  isAuthenticatedFunction(): boolean {
    return this.keycloak.authenticated;
  }
}
