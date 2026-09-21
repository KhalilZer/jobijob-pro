import { Routes } from '@angular/router';
import { WorkerLayout } from '../../layout/worker-layout/worker-layout';

export const WORKER_ROUTES: Routes = [
  {
    path: '',
    component: WorkerLayout,

    children: [
      {
        path: 'home',
        loadComponent: () =>
          import('./home-page-worker/home-page-worker').then((m) => m.HomePageWorker),
      },
      {
        path: 'profile',
        loadComponent: () =>
          import('./profile-page-worker/profile-page-worker').then((m) => m.ProfilePageWorker),
      },
      {
        path: '',
        redirectTo: 'home',
        pathMatch: 'full',
      },
    ],
  },
];
