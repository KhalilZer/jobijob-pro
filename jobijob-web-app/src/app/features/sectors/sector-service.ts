import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { GlobalResponse } from '../../shared/models/global-response';
import { Sector } from './sector-model';

@Injectable({
  providedIn: 'root',
})
export class SectorService {
  userUrl = environment.userApi;
  httpClient = inject(HttpClient);

  allSectors() {
    return this.httpClient.get<GlobalResponse<Sector[]>>(`${this.userUrl}/sectors`);
  }
}
