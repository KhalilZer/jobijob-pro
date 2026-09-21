import { Component, input } from '@angular/core';
import {
  FormControl,
  FormGroup,
  FormsModule,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { DatePickerModule } from 'primeng/datepicker';
import { InputMaskModule } from 'primeng/inputmask';
import IntlTelInput from '@intl-tel-input/angular';
import 'intl-tel-input/styles';
import { Step2Form } from '../../onboarding-model';

@Component({
  selector: 'app-step-2',
  imports: [FormsModule, DatePickerModule, InputMaskModule, ReactiveFormsModule, IntlTelInput],
  templateUrl: './step-2.html',
  styleUrl: './step-2.css',
})
export class Step2 {
  loadUtils = () => import('intl-tel-input/utils');

  form = input.required<FormGroup<Step2Form>>();
}
