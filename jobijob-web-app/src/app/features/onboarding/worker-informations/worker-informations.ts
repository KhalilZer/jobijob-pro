import { Component, computed, inject, signal } from '@angular/core';

import { Step1 } from './step-1/step-1';

import { MyLocation } from '../../../shared/models/my-location';
import { Step2 } from './step-2/step-2';
import { AuthService } from '../../auth/auth-service';
import { CreateUserRequest } from '../../auth/auth.model';
import { MessageService } from 'primeng/api';
@Component({
  selector: 'app-worker-informations',
  imports: [Step1, Step2],
  templateUrl: './worker-informations.html',
  styleUrl: './worker-informations.css',
})
export class WorkerInformations {
  authService = inject(AuthService);
  messageService = inject(MessageService);

  sectors = signal<number[]>([]);
  location = signal<MyLocation | null>(null);

  currentStep = signal<number>(1);

  validStep1 = computed(() => this.sectors().length > 0 && this.location() !== null);

  //Listeners
  onSelectSecotrs(sectors: number[]) {
    this.sectors.set(sectors);
  }
  onLocationSelected(location: MyLocation) {
    this.location.set(location);
  }
  //EndListenners

  next() {
    //Check Location and currentStep
    if (!this.validStep1()) {
      alert('cannot');
      return;
    }

    //call CreateUser Endpoint

    const request: CreateUserRequest = {
      sectorsId: this.sectors(),
      isWorker: true,
      ...this.location()!,
    };

    this.authService.createUser(request).subscribe({
      next: (resp) => {
        this.messageService.add({
          severity: 'success',
          summary: 'Success',
          detail: resp.message,
        });
        this.currentStep.set(this.currentStep() + 1);
      },
    });
  }
}
