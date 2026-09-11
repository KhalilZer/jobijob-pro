import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { IftaLabelModule } from 'primeng/iftalabel';
import { InputTextModule } from 'primeng/inputtext';
@Component({
  selector: 'app-step-2',
  imports: [FormsModule, IftaLabelModule, InputTextModule],
  templateUrl: './step-2.html',
  styleUrl: './step-2.css',
})
export class Step2 {}
