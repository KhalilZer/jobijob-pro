import { Component, EventEmitter, output } from '@angular/core';

import { FileUpload } from 'primeng/fileupload';

@Component({
  selector: 'app-step-3',
  imports: [FileUpload],
  templateUrl: './step-3.html',
  styleUrl: './step-3.css',
})
export class Step3 {
  onSelectImage = output<File>();

  onPhotoSelected(event: any) {
    const file: File = event.files[0];

    this.onSelectImage.emit(file);
  }
}
