import { inject } from '@angular/core';
import { CanActivateFn } from '@angular/router';
import { KeycloakService } from '../../features/auth/keycloakService';

export const isAuthenticatedGuard: CanActivateFn = (route, state) => {
  const keycloakService = inject(KeycloakService);

  return keycloakService.isAuthenticatedFunction();
};
