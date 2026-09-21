import { FormControl } from '@angular/forms';

export type SearchForm = {
  search: FormControl<string>;
  city: FormControl<string>;
};

export type FilterForm = {
  category: FormControl<number | null>;
  date: FormControl<string | null>;
  fromPrice: FormControl<number | null>;
  toPrice: FormControl<number | null>;
};
