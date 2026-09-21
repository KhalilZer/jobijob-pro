import { HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { KeycloakService } from './keycloakService';
import { environment } from '../../../environments/environment';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const keycloakService = inject(KeycloakService);
  const token = keycloakService.getToken();

  const excludedUrls = [environment.miniIoUrl];

  if (excludedUrls.some((url) => req.url.startsWith(url))) {
    return next(req);
  }

  const reqClone = req.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });
  return next(reqClone);
};
