import { Component } from '@angular/core';
import { SidebarWorker } from '../sidebar-worker/sidebar-worker';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-worker-layout',
  imports: [SidebarWorker, RouterOutlet],
  templateUrl: './worker-layout.html',
  styleUrl: './worker-layout.css',
})
export class WorkerLayout {}
