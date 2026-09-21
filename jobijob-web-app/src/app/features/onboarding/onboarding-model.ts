import { FormControl, FormGroup } from '@angular/forms';

export type Step2Form = {
  fullName: FormControl<string>;
  birthDate: FormControl<Date | null>;
  phone: FormControl<string>;
  description: FormControl<string>;
};
