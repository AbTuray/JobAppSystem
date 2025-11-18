import { Component, Input } from '@angular/core';

interface Job {
  id?: number;
  title: string;
  description: string;
  location: string;
  salary: string;
}

@Component({
  selector: 'app-employer-job-form',
  standalone: true,
  templateUrl: './employer-job-form.component.html',
  styleUrls: ['./employer-job-form.component.css']
})
export class EmployerJobFormComponent {
  @Input() job: Job = { title:'', description:'', location:'', salary:'' };
  submitCallback: (job: Job) => void = () => {};

  onSubmit() {
    if(!this.job.title || !this.job.description) return;
    this.submitCallback({...this.job});
  }

  // Temporary render to DOM inside SweetAlert
  render(selector: string) {
    const container = document.querySelector(selector);
    if(container) container.innerHTML = this.template();
  }

  template() {
    return `
      <div>
        <div class="mb-2">
          <label>Title</label>
          <input id="jobTitle" class="form-control" value="${this.job.title}" />
        </div>
        <div class="mb-2">
          <label>Description</label>
          <textarea id="jobDesc" class="form-control">${this.job.description}</textarea>
        </div>
        <div class="mb-2">
          <label>Location</label>
          <input id="jobLoc" class="form-control" value="${this.job.location}" />
        </div>
        <div class="mb-2">
          <label>Salary</label>
          <input id="jobSal" class="form-control" value="${this.job.salary}" />
        </div>
        <button class="btn btn-success mt-2" onclick="
          const j = {
            title: (document.getElementById('jobTitle') as HTMLInputElement).value,
            description: (document.getElementById('jobDesc') as HTMLTextAreaElement).value,
            location: (document.getElementById('jobLoc') as HTMLInputElement).value,
            salary: (document.getElementById('jobSal') as HTMLInputElement).value
          };
          (${this.submitCallback.toString()})(j);
        ">Submit</button>
      </div>
    `;
  }
}
