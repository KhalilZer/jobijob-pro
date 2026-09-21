import { Component, inject, input, output } from '@angular/core';
import { FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FILTER_BY_DATE } from '../../../../app/features/worker/home-page-worker/constants/filters-constants';
import { SectorService } from '../../../../app/features/sectors/sector-service';
import { toSignal } from '@angular/core/rxjs-interop';

@Component({
  selector: 'app-filters-home-worker',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './filters-home-worker.html',
  styleUrl: './filters-home-worker.css',
})
export class FiltersHomeWorker {
  filterByDate = FILTER_BY_DATE;
  filterForm = input.required<FormGroup>();

  sectorService = inject(SectorService);

  allSectors = toSignal(this.sectorService.allSectors());
}
