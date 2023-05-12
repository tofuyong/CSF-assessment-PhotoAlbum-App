import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { Bundle } from '../models/bundle';

@Injectable({
  providedIn: 'root'
})
export class RetrieveService {

  private RETRIEVE_URL = "/api/{bundleId}";
  private RETRIEVE_ALL_URL = "/api/all";

  constructor(private httpClient: HttpClient) { }

  getBundle(bundleId: string): Promise<Bundle> {
    const url = this.RETRIEVE_URL.replace("{bundleId}", bundleId);
    return firstValueFrom(this.httpClient.get<Bundle>(url));
  }

  getAllBundles(): Promise<Bundle[]>  {
    const url = this.RETRIEVE_ALL_URL;
    return firstValueFrom(this.httpClient.get<Bundle[]>(url));
  }
}
