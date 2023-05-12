import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UploadComponent } from './components/upload.component';
import { DisplayComponent } from './components/display.component';
import { LandingComponent } from './components/landing.component';


const routes: Routes = [
  { path: '', title: 'Landing Page', component: LandingComponent },
  { path: 'upload', title: 'Upload File', component: UploadComponent },
  { path: 'display/:bundleId', title: 'Display', component: DisplayComponent },
  { path: '**', redirectTo: '',  pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }