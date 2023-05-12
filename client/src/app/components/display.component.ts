import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { UploadService } from '../service/upload.service';
import { RetrieveService } from '../service/retrieve.service';
import { Bundle } from '../models/bundle';

@Component({
  selector: 'app-display',
  templateUrl: './display.component.html',
  styleUrls: ['./display.component.css']
})
export class DisplayComponent implements OnInit, OnDestroy {
  bundleId!: string;
  param$!: Subscription;

  title!: string;
  name!: string;
  date!: Date;
  comments!: string;
  imgUrls!: string[];

  constructor(private activatedRoute: ActivatedRoute, private retrieveSvc: RetrieveService) { }

  ngOnInit(): void {
    this.param$ = this.activatedRoute.params.subscribe(
      (params) => {
        this.bundleId = params['bundleId'];
        this.retrieveSvc.getBundle(this.bundleId)
        .then(
          (response: Bundle) => {
            console.log('Bundle retreived successfully.');
            this.title = response.title;
            this.name = response.name;
            this.date = new Date(response.date);
            this.comments = response.comments;
            this.imgUrls = response.imageUrls;
          },
          error => {
            console.log('Error while retrieving bundle', error);
          }
        );
      }
    );
  }

  ngOnDestroy(): void { this.param$.unsubscribe(); }
}
