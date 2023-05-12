import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  private UPLOAD_URL = "/api/upload";

  constructor(private httpClient: HttpClient) { }

  upload(formData: FormData): Promise<any> {
    return firstValueFrom(this.httpClient.post(this.UPLOAD_URL, formData, { responseType: 'json' }));
  }
  
}
