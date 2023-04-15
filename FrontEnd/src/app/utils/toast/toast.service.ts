import { Injectable } from '@angular/core';

export interface ToastInfo {
  body: string;
  delay?: number;
  type: 'bg-success' | 'bg-danger' | 'bg-info' | 'bg-warning';
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  toasts: ToastInfo[] = [];

  constructor() { }

  showSuccess(body: string, delay?: number) {
    this.show({ body, delay, type: 'bg-success' });
  }

  showError(body: string, delay?: number) {
    this.show({ body, delay, type: 'bg-danger' });
  }

  showInfo(body: string, delay?: number) {
    this.show({ body, delay, type: 'bg-info' });
  }

  showWarning(body: string, delay?: number) {
    this.show({ body, delay, type: 'bg-warning' });
  }
  show(toast: ToastInfo) {
		this.toasts.push(toast);
	}

	remove(toast: ToastInfo) {
		this.toasts = this.toasts.filter((t) => t !== toast);
	}

	clear() {
		this.toasts.splice(0, this.toasts.length);
	}
}
