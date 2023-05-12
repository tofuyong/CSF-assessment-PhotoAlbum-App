import { Component, OnInit } from '@angular/core';
import { RetrieveService } from '../service/retrieve.service';
import { Bundle } from '../models/bundle';

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  bundles!: Bundle[]
  
  constructor (private retrieveSvc: RetrieveService) { }

  ngOnInit(): void {
      this.retrieveSvc.getAllBundles()
      .then(
        (r) => {
          const response = r;
          this.bundles = [];
  
          for (const rr of response) {
            const bundle = new Bundle (
              rr.bundleId,
              rr.name,
              rr.title,
              rr.comments,
              rr.date,
              rr.imageUrls
            );
            this.bundles.push(bundle);
          }
        }
      )
      .catch (
        (error) => {
          console.error("Error fetching bundles:", error);
          this.bundles = []; 
        }
      )
  }

}
