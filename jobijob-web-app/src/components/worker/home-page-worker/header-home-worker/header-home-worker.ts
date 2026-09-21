import { Component, input, output } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-header-home-worker',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './header-home-worker.html',
  styleUrl: './header-home-worker.css',
})
export class HeaderHomeWorker {
  searchForm = input.required<FormGroup>();
  onSearch = output();

  onSubmit() {
    this.onSearch.emit();
  }
}
