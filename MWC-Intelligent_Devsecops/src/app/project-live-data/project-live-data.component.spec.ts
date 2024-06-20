import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectLiveDataComponent } from './project-live-data.component';

describe('ProjectLiveDataComponent', () => {
  let component: ProjectLiveDataComponent;
  let fixture: ComponentFixture<ProjectLiveDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectLiveDataComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectLiveDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
