import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar-worker',
  imports: [],
  templateUrl: './sidebar-worker.html',
  styleUrl: './sidebar-worker.css',
})
export class SidebarWorker {
  router = inject(Router);
}
