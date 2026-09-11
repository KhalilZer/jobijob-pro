import {
  ApplicationConfig,
  importProvidersFrom,
  inject,
  provideAppInitializer,
  provideBrowserGlobalErrorListeners,
} from '@angular/core';
import { provideRouter, Router } from '@angular/router';

import { routes } from './app.routes';
import {
  HttpErrorResponse,
  HttpStatusCode,
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import { authInterceptor } from './features/auth/auth.interceptor';
import { KeycloakService } from './features/auth/keycloakService';
import { AuthService } from './features/auth/auth-service';
import { providePrimeNG } from 'primeng/config';
import Aura from '@primeuix/themes/aura';
import { GeoapifyGeocoderAutocompleteModule } from '@geoapify/angular-geocoder-autocomplete';
import { environment } from '../environments/environment';
import { errorInterceptor } from './shared/interceptors/error-interceptor';
import { MessageService } from 'primeng/api';

export const appConfig: ApplicationConfig = {
  providers: [
    MessageService,
    provideHttpClient(withInterceptors([authInterceptor, errorInterceptor])),

    providePrimeNG({
      theme: {
        preset: Aura,
      },
    }),
    importProvidersFrom(GeoapifyGeocoderAutocompleteModule.withConfig(environment.geoapifyApiKey)),
    provideBrowserGlobalErrorListeners(),
    provideRouter(routes),
    provideAppInitializer(async () => {
      const keycloakService = inject(KeycloakService);
      const authService = inject(AuthService);
      const router = inject(Router);

      await keycloakService.init();

      if (keycloakService.isAuthenticatedFunction()) {
        authService.me().subscribe({
          next(res) {
            console.log('hello');
            console.log(res);
          },
          error(error) {
            if (error instanceof HttpErrorResponse) {
              if (error.status == HttpStatusCode.NotFound) {
                //Clearly user not yet created we must redirect him to ONBOARDING
                router.navigate(['/onboarding']);
              }
            }
          },
        });
      }
    }),
  ],
};
