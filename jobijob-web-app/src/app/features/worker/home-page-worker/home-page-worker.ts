import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FilterForm, SearchForm } from './home-page-worker-model';
import { FiltersHomeWorker } from '../../../../components/worker/home-page-worker/filters-home-worker/filters-home-worker';
import { PostsHomeWorker } from '../../../../components/worker/home-page-worker/posts-home-worker/posts-home-worker';
import { HeaderHomeWorker } from '../../../../components/worker/home-page-worker/header-home-worker/header-home-worker';

@Component({
  selector: 'app-home-page-worker',
  imports: [HeaderHomeWorker, PostsHomeWorker, FiltersHomeWorker],
  templateUrl: './home-page-worker.html',
  styleUrl: './home-page-worker.css',
})
export class HomePageWorker {
  searchForm = new FormGroup<SearchForm>({
    search: new FormControl('', {
      nonNullable: true,
    }),
    city: new FormControl('', {
      nonNullable: true,
    }),
  });

  filterForm = new FormGroup<FilterForm>({
    category: new FormControl(null),
    date: new FormControl(null),
    fromPrice: new FormControl(0, {
      validators: [Validators.min(0)],
    }),
    toPrice: new FormControl(9999, {
      validators: [Validators.min(0)],
    }),
  });

  ngOnInit(): void {
    this.filterForm.valueChanges.subscribe(() => {
      if (this.filterForm.invalid) {
        return;
      }
      console.log(this.filterForm.getRawValue());
    });
  }
  handleSearch() {
    const form = this.searchForm.getRawValue();
    console.log(form);
  }
}
