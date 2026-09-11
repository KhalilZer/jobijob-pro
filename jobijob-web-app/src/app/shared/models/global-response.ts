export interface GlobalResponse<T> {
  success: boolean;
  data: T;
  message: string;
}
