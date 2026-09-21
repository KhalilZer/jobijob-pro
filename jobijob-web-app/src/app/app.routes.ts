import { Routes } from '@angular/router';
import { isAuthenticatedGuard } from './shared/guards/is-authenticated-guard';
import { HomePageNotAuth } from './features/home-page-not-auth/home-page-not-auth';
import { WorkerLayout } from './layout/worker-layout/worker-layout';

export const routes: Routes = [
  {
    path: '',
    component: HomePageNotAuth,
  },
  {
    path: 'worker',
    loadChildren: () => import('./features/worker/worker.route').then((m) => m.WORKER_ROUTES),
  },
  {
    path: 'onboarding',
    canActivate: [isAuthenticatedGuard],
    loadChildren: () =>
      import('./features/onboarding/onboarding.routes').then((m) => m.ONBOARDING_ROUTES),
  },
];
