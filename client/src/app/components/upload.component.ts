import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { UploadService } from '../service/upload.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {

  uploadForm!: FormGroup
  selectedFile!: File;
  title!: string;

  constructor(private fb: FormBuilder, private uploadSvc: UploadService, private router: Router) { }

  ngOnInit(): void {
    this.uploadForm = this.createForm();
  }

  createForm() {
    return this.fb.group({
      name: this.fb.control('', Validators.required),
      title: this.fb.control('', [ Validators.required, Validators.minLength(3) ] ),
      comments: this.fb.control(''),
      // archive: this.fb.control('', Validators.required)
    }); 
  }

  hasError(input: string): boolean {
    return !!(this.uploadForm.get(input)?.invalid && this.uploadForm.get(input)?.dirty)
  }

  onFileSelected(event: Event) {
    const target = event.target as HTMLInputElement;
    const file: File = (target.files as FileList)[0];
    this.selectedFile = file; // retrieves the selected file and stores it in the selectedFile property
  }

  submit() {
    if (this.uploadForm.valid) {
      const formData = new FormData();
        formData.append('name', this.uploadForm.get('name')?.value);
        formData.append('title', this.uploadForm.get('title')?.value);
        formData.append('comments', this.uploadForm.get('comments')?.value);
        formData.append('archive', this.selectedFile, this.selectedFile.name); // name is optional

        // Call service method to send this formData to the server
        this.uploadSvc.upload(formData)
        .then(
          response => {
            const bundleId: string = response.bundleId;
            console.log('Upload completed successfully. Bundle ID: ', bundleId);
            this.router.navigate(['/display', bundleId]);
          },
          error => {
            console.log('Error while uploading', error);
            alert('Failed to upload');
          }
        );
        
    };
  }


}
