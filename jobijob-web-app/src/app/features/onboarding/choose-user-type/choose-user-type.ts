import { Component, computed, effect, inject, signal } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-choose-user-type',
  imports: [],
  templateUrl: './choose-user-type.html',
  styleUrl: './choose-user-type.css',
})
export class ChooseUserType {
  userType = signal<'worker' | 'owner' | null>(null);

  router = inject(Router);

  constructor() {
    effect(() => {
      this.userType() === 'worker' && this.router.navigate(['/onboarding/worker']);
      this.userType() === 'owner' && this.router.navigate(['/onboarding/owner']);
    });
  }
}
