import { HttpClient, HttpContext } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { GlobalResponse } from '../../shared/models/global-response';
import { CreateUserRequest, MeResponse } from './auth.model';
import { SKIP_ERROR_TOAST } from '../../shared/models/skip-error-toast';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  userApiUrl = environment.userApi;

  me() {
    return this.http.get<GlobalResponse<MeResponse | null>>(`${this.userApiUrl}/me`, {
      context: new HttpContext().set(SKIP_ERROR_TOAST, true),
    });
  }
  createUser(payload: CreateUserRequest) {
    return this.http.post<GlobalResponse<MeResponse>>(`${this.userApiUrl}`, payload);
  }
}
