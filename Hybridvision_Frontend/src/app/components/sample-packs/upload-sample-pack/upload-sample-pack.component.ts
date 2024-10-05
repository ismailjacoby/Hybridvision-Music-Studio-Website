import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpEventType } from '@angular/common/http';
import {SamplePackService} from "../../../services/sample-pack.service";

@Component({
  selector: 'app-upload-sample-pack',
  templateUrl: './upload-sample-pack.component.html',
  styleUrls: ['./upload-sample-pack.component.css']
})
export class UploadSamplePackComponent implements OnInit {
  uploadSampleForm!: FormGroup;
  selectedCoverImageName: string = '';
  selectedAudioFileName: string = '';
  uploadProgress: number = 0;
  uploadMessage: string = '';

  constructor(private fb: FormBuilder, private _samplePackService: SamplePackService) { }

  ngOnInit() {
    this.uploadSampleForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      price: ['', Validators.required],
      coverImage: [null, Validators.required],
      audioUrl: [null, Validators.required]
    });
  }

  onFileSelected(event: any, fieldName: string) {
    const file: File = event.target.files[0];
    if (file) {
      this.uploadSampleForm.patchValue({ [fieldName]: file });
      this.uploadSampleForm.get(fieldName)!.updateValueAndValidity();

      if (fieldName === 'coverImage') {
        this.selectedCoverImageName = file.name;
      } else if (fieldName === 'audioUrl') {
        this.selectedAudioFileName = file.name;
      }
    }
  }

  onDragOver(event: any) {
    event.preventDefault();
  }

  onDrop(event: any, fieldName: string) {
    event.preventDefault();
    const file = event.dataTransfer.files[0];
    this.onFileSelected({ target: { files: [file] } }, fieldName);
  }

  onSubmit() {
    if (this.uploadSampleForm.invalid) {
      return;
    }

    const formData = new FormData();
    formData.append('title', this.uploadSampleForm.get('title')!.value);
    formData.append('description', this.uploadSampleForm.get('description')!.value);
    formData.append('price', this.uploadSampleForm.get('price')!.value);
    formData.append('coverImage', this.uploadSampleForm.get('coverImage')!.value);
    formData.append('audioUrl', this.uploadSampleForm.get('audioUrl')!.value);

    this._samplePackService.uploadSamplePack(formData).subscribe(event => {
      if (event.type === HttpEventType.UploadProgress) {
        this.uploadProgress = Math.round(100 * event.loaded / event.total!);
      } else if (event.type === HttpEventType.Response) {
        this.uploadMessage = 'Upload successful!';
        this.uploadSampleForm.reset();
        this.selectedCoverImageName = '';
        this.selectedAudioFileName = '';
        this.uploadProgress = 0;
      }
    }, error => {
      this.uploadMessage = 'Upload failed. Please try again.';
      console.error(error);
    });
  }
}
