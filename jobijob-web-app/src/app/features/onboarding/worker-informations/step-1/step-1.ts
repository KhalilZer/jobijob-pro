import { Component, computed, effect, inject, output, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { map } from 'rxjs';
import { SectorService } from '../../../sectors/sector-service';
import { Sector, sectorPlusIcon } from '../../../sectors/sector-model';
import { SECTOR_ICONS } from '../../../sectors/constants/sector-icons';

import { MultiSelectModule } from 'primeng/multiselect';
import { FormsModule } from '@angular/forms';
import { GeoapifyGeocoderAutocompleteModule } from '@geoapify/angular-geocoder-autocomplete';
import { MyLocation } from '../../../../shared/models/my-location';

@Component({
  selector: 'app-step-1',
  imports: [MultiSelectModule, FormsModule, GeoapifyGeocoderAutocompleteModule],
  templateUrl: './step-1.html',
  styleUrl: './step-1.css',
})
export class Step1 {
  sectorService = inject(SectorService);
  selectedSectors = signal<number[]>([]);

  //Outputs
  sectors = output<number[]>();
  locationSelected = output<MyLocation>();

  constructor() {
    effect(() => {
      this.sectors.emit(this.selectedSectors());
    });
  }
  readonly allSectors = toSignal(this.sectorService.allSectors().pipe(map((resp) => resp.data)), {
    initialValue: [],
  });

  readonly items = computed<sectorPlusIcon[]>(() => {
    return this.allSectors().map((sector: Sector) => {
      return {
        ...sector,
        icon: SECTOR_ICONS[sector.code],
      };
    });
  });
  placeSelect(location: any) {
    const loca: MyLocation = {
      city: location.properties.city,
      countryCode: location.properties.country_code,
      latitude: location.properties.lat,
      longitude: location.properties.lon,
      postalCode: location.properties.postcode,
    };
    this.locationSelected.emit(loca);
  }
}
