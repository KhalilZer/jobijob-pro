import { MyLocation } from '../../shared/models/my-location';

export interface MeResponse {
  publicId: string;
  fullName: string;
  email: string;
}

export interface CreateUserRequest extends MyLocation {
  sectorsId: number[];
  isWorker: boolean;
}
