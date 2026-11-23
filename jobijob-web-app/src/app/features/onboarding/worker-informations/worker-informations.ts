import { Component, computed, inject, signal } from '@angular/core';

import { Step1 } from './step-1/step-1';

import { MyLocation } from '../../../shared/models/my-location';
import { Step2 } from './step-2/step-2';
import { AuthService } from '../../auth/auth-service';
import { CreateUserRequest } from '../../auth/auth.model';
import { MessageService } from 'primeng/api';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Step2Form } from '../onboarding-model';
import { toLocalDate } from '../../../shared/utils/date-utils';
import { removeEmptyValues } from '../../../shared/utils/remove-empty-values-util';
import { maxBirthDateValidator } from '../../../shared/validators/max-birthdate-validator';
import { Step3 } from './step-3/step-3';
import { switchMap } from 'rxjs';
import { Router } from '@angular/router';
@Component({
  selector: 'app-worker-informations',
  imports: [Step1, Step2, Step3],
  templateUrl: './worker-informations.html',
  styleUrl: './worker-informations.css',
})
export class WorkerInformations {
  authService = inject(AuthService);
  messageService = inject(MessageService);
  router = inject(Router);
  currentStep = signal<number>(1);

  //STEP 1
  sectors = signal<number[]>([]);
  location = signal<MyLocation | null>(null);
  validStep1 = computed(() => this.sectors().length > 0 && this.location() !== null);

  onSelectSecotrs(sectors: number[]) {
    this.sectors.set(sectors);
  }
  onLocationSelected(location: MyLocation) {
    this.location.set(location);
  }
  //END STEP1

  //STEP 2
  step2Form = new FormGroup<Step2Form>({
    fullName: new FormControl('', {
      nonNullable: true,
      validators: [Validators.minLength(10), Validators.maxLength(100), Validators.required],
    }),
    birthDate: new FormControl(new Date(), {
      nonNullable: true,
      validators: [maxBirthDateValidator(), Validators.required],
    }),
    phone: new FormControl('', {
      nonNullable: true,
    }),
    description: new FormControl('', {
      nonNullable: true,
      validators: [Validators.minLength(10), Validators.maxLength(100)],
    }),
  });
  //END STEP 2

  //STEP 3
  imageSelected = signal<File | null>(null);

  onImageSelected(file: File) {
    this.imageSelected.set(file);
  }
  //END STEP 3
  next() {
    if (this.currentStep() === 1) {
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

    if (this.currentStep() === 2) {
      if (!this.step2Form.valid) {
        return;
      }

      const formRaw = this.step2Form.getRawValue();
      const payload = removeEmptyValues(formRaw);

      console.log(this.authService.userPublicId);
      return;
      this.authService
        .updateUser(this.authService.userPublicId!, {
          ...payload,
          birthDate: new Date(toLocalDate(payload.birthDate!)),
        })
        .subscribe({
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

    if (this.currentStep() === 3) {
      this.authService
        .requestUrlUpload(this.imageSelected()?.type!)
        .pipe(
          switchMap((resp) =>
            this.authService.uploadImage(resp.data.uploadUrl, this.imageSelected()!).pipe(
              switchMap(() =>
                this.authService.updateUser(this.authService.userPublicId!, {
                  photo: resp.data.objectKey,
                }),
              ),
            ),
          ),
        )
        .subscribe({
          next: (resp) => {
            this.messageService.add({
              severity: 'success',
              summary: 'Success',
              detail: 'Photo uploaded succefully',
            });
            this.router.navigate(['/']);
          },
        });
    }
  }
}
