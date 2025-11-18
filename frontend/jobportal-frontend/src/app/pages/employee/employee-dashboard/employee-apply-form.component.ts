import { Component, Input } from '@angular/core';
import Swal from 'sweetalert2';

export interface Application {
  id?: number;
  jobId: number;
  employeeName: string;
  email: string;
  resume: string;
}

@Component({
  selector: 'app-employee-apply-form',
  standalone: true,
  templateUrl: './employee-apply-form.component.html',
  styleUrls: ['./employee-apply-form.component.css']
})
export class EmployeeApplyFormComponent {
  @Input() application: Application = { jobId: 0, employeeName:'', email:'', resume:'' };
  submitCallback: (app: Application) => void = () => {};

  onSubmit() {
    if(!this.application.employeeName || !this.application.email) {
      Swal.fire('Error', 'Please fill in all fields', 'error');
      return;
    }
    this.submitCallback({...this.application});
  }

  // Temporary render for SweetAlert modal
  render(selector: string) {
    const container = document.querySelector(selector);
    if(container) container.innerHTML = this.template();
  }

  template() {
    return `
      <div>
        <div class="mb-2">
          <label>Name</label>
          <input id="empName" class="form-control" value="${this.application.employeeName}" />
        </div>
        <div class="mb-2">
          <label>Email</label>
          <input id="empEmail" class="form-control" value="${this.application.email}" />
        </div>
        <div class="mb-2">
          <label>Resume</label>
          <textarea id="empResume" class="form-control">${this.application.resume}</textarea>
        </div>
        <button class="btn btn-success mt-2" onclick="
          const app = {
            jobId: ${this.application.jobId},
            employeeName: (document.getElementById('empName') as HTMLInputElement).value,
            email: (document.getElementById('empEmail') as HTMLInputElement).value,
            resume: (document.getElementById('empResume') as HTMLTextAreaElement).value
          };
          (${this.submitCallback.toString()})(app);
        ">Submit</button>
      </div>
    `;
  }
}
