import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { MessageService } from 'primeng/api';
import { catchError, throwError } from 'rxjs';
import { SKIP_ERROR_TOAST } from '../models/skip-error-toast';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const messageService = inject(MessageService);

  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      const message = error.error?.message ?? 'Something went wrong';
      if (!req.context.get(SKIP_ERROR_TOAST)) {
        messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: message,
        });
      }
      return throwError(() => error);
    }),
  );
};
