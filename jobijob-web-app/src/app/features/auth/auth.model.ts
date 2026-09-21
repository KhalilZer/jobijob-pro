import { MyLocation } from '../../shared/models/my-location';
import { SectorResponse } from '../sectors/sector-model';

export interface MeResponse {
  userProfile: UserResponse;
  isWorker: boolean;
  workerProfile: WorkerResponse | null;
  profileCompletion: number;
}

export interface UserResponse {
  publicId: string;
  email: string;
  fullName: string;
  birthDate: string | null;
  phone: string | null;
  photo: string | null;
  photoUrl: string | null;
  status: UserStatus;
  createdAt: string;
}

export interface WorkerResponse {
  publicId: string;
  city: string;
  postalCode: string;
  countryCode: string;
  latitude: number;
  longitude: number;
  description: string | null;
  available: boolean;
  sectors: SectorResponse[];
  createdAt: string;
}

export enum UserStatus {
  ACTIVE = 'ACTIVE',
  INACTIVE = 'INACTIVE',
  SUSPENDED = 'SUSPENDED',
}

export interface CreateUserRequest extends MyLocation {
  sectorsId: number[];
  isWorker: boolean;
}

export interface UpdateUserRequest {
  sectors?: number[];
  city?: string;
  postalCode?: string;
  countryCode?: string;
  latitude?: number;
  longitude?: number;
  fullName?: string;
  birthDate?: Date | null;
  phone?: string;
  description?: string;
  photo?: string;
}

export interface GenerateUploadUrlResponse {
  uploadUrl: string;
  objectKey: string;
}
