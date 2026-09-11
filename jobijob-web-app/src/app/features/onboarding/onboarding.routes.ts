import { Routes } from '@angular/router';

export const ONBOARDING_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () =>
      import('./choose-user-type/choose-user-type').then((m) => m.ChooseUserType),
  },

  {
    path: 'worker',
    loadComponent: () =>
      import('./worker-informations/worker-informations').then((m) => m.WorkerInformations),
  },
  {
    path: 'owner',
    loadComponent: () =>
      import('./owner-informations/owner-informations').then((m) => m.OwnerInformations),
  },
];
