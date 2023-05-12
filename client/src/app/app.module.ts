import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { UploadComponent } from './components/upload.component';
import { AppRoutingModule } from './app-routing.module';
import { DisplayComponent } from './components/display.component';
import { LandingComponent } from './components/landing.component';

@NgModule({
  declarations: [
    AppComponent,
    UploadComponent,
    DisplayComponent,
    LandingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
