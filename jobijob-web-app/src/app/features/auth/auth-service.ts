import { HttpClient, HttpContext } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { GlobalResponse } from '../../shared/models/global-response';
import {
  CreateUserRequest,
  GenerateUploadUrlResponse,
  MeResponse,
  UpdateUserRequest,
} from './auth.model';
import { SKIP_ERROR_TOAST } from '../../shared/models/skip-error-toast';
import { BehaviorSubject, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  userApiUrl = environment.userApi;

  private connectedUserSubject = new BehaviorSubject<MeResponse | null>(null);

  connectedUser$ = this.connectedUserSubject.asObservable();
  get connectedUser(): MeResponse | null {
    return this.connectedUserSubject.value;
  }

  get userPublicId(): string | null {
    return this.connectedUserSubject.value?.userProfile.publicId ?? null;
  }

  me() {
    return this.http
      .get<GlobalResponse<MeResponse | null>>(`${this.userApiUrl}/me`, {
        context: new HttpContext().set(SKIP_ERROR_TOAST, true),
      })
      .pipe(
        tap((resp) => {
          if (resp.success) {
            this.connectedUserSubject.next(resp.data);
          }
        }),
      );
  }
  createUser(payload: CreateUserRequest) {
    return this.http.post<GlobalResponse<MeResponse>>(`${this.userApiUrl}`, payload).pipe(
      tap((resp) => {
        if (resp.success) {
          this.connectedUserSubject.next(resp.data);
        }
      }),
    );
  }

  updateUser(publicId: string, paylaod: UpdateUserRequest) {
    return this.http.patch<GlobalResponse<MeResponse>>(`${this.userApiUrl}/${publicId}`, paylaod);
  }
  requestUrlUpload(contentType: string) {
    return this.http.post<GlobalResponse<GenerateUploadUrlResponse>>(
      `${this.userApiUrl}/me/photo/upload-url`,
      { contentType: contentType },
    );
  }
  uploadImage(url: string, file: File) {
    return this.http.put(url, file, {
      headers: {
        'Content-Type': file.type,
      },
    });
  }
}
