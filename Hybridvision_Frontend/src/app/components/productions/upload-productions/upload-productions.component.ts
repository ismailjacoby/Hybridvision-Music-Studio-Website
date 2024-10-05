import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpEvent, HttpEventType} from "@angular/common/http";
import {Genre} from "../../../models/Genre";
import {LicenseType} from "../../../models/LicenseType";
import {ProductionService} from "../../../services/production.service";

@Component({
  selector: 'app-upload-productions',
  templateUrl: './upload-productions.component.html',
  styleUrl: './upload-productions.component.css'
})
export class UploadProductionsComponent {
  uploadForm!: FormGroup;
  genres: string[] = Object.values(Genre).filter(value => true);
  uploadProgress: number = 0;
  uploadMessage: string = '';
  selectedMp3FileName: string = '';
  selectedWavFileName: string = '';
  selectedZipFileName: string = '';
  selectedCoverImageName: string = '';

  constructor(private _formBuilder: FormBuilder, private _http: HttpClient, private _productionService: ProductionService) { }

  ngOnInit() {
    this.uploadForm = this._formBuilder.group({
      title: ['', Validators.required],
      bpm: ['', [Validators.required, Validators.min(20), Validators.max(200), Validators.pattern('^[0-9]+$')]],
      releaseDate: ['', Validators.required],
      coverImage: ['', Validators.required],
      audioMp3: ['', Validators.required],
      audioWav: [''],
      audioZip: [''],
      genre: ['', Validators.required],
    });
  }

  onFileSelected(event: any, controlName: string): void {
    const file = event.target.files[0];
    if (file) {
      this.uploadForm.get(controlName)?.setValue(file);
      if (controlName === 'audioMp3') {
        this.selectedMp3FileName = file.name;
      } else if (controlName === 'coverImage') {
        this.selectedCoverImageName = file.name;
      }
    }
  }

  onDragOver(event: Event): void {
    event.preventDefault();
  }

  onDrop(event: any, type: string): void {
    event.preventDefault();
    const files = event.dataTransfer.files;
    if (files.length > 0) {
      this.uploadForm.get(type)?.setValue(files[0]);
      const fileName = files[0].name;
      switch (type) {
        case 'audioMp3':
          this.selectedMp3FileName = fileName;
          break;
        case 'coverImage':
          this.selectedCoverImageName = fileName;
          break;
      }
    }
  }

  onSubmit(): void {
    console.log("Submitting...")

    if (!this.uploadForm.valid) {
      this.uploadMessage = "Please fill out the form correctly.";
      return;
    }

    const formData = new FormData();

    formData.append('title', this.uploadForm.get('title')!.value);
    formData.append('bpm', this.uploadForm.get('bpm')!.value);
    formData.append('releaseDate', this.uploadForm.get('releaseDate')!.value);
    formData.append('genre', this.uploadForm.get('genre')!.value.toUpperCase());
    formData.append('coverImage', this.uploadForm.get('coverImage')!.value);
    formData.append('audioMp3', this.uploadForm.get('audioMp3')!.value);
    if (this.uploadForm.get('audioWav')!.value) {
      formData.append('audioWav', this.uploadForm.get('audioWav')!.value);
    }
    if (this.uploadForm.get('audioZip')!.value) {
      formData.append('audioZip', this.uploadForm.get('audioZip')!.value);
    }

    this._productionService.uploadProduction(formData).subscribe(event => {
      this.handleUploadEvent(event);
    }, error => {
      console.error('Upload failed', error);
      this.uploadMessage = "Upload failed. Please try again.";
    });
  }

  private handleUploadEvent(event: HttpEvent<any>): void {
    if (event.type === HttpEventType.UploadProgress && event.total) {
      this.uploadProgress = Math.round(100 * event.loaded / event.total);
    } else if (event.type === HttpEventType.Response) {
      console.log('Successfully uploaded', event.body);
      this.uploadMessage = "Successfully uploaded!";
      this.uploadForm.reset();
      this.uploadProgress = 0;
      // Reset file names after successful upload
      this.selectedMp3FileName = '';
      this.selectedWavFileName = '';
      this.selectedZipFileName = '';
      this.selectedCoverImageName = '';
    }
  }

}
