import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectLiveComponent } from './project-live.component';

describe('ProjectLiveComponent', () => {
  let component: ProjectLiveComponent;
  let fixture: ComponentFixture<ProjectLiveComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProjectLiveComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectLiveComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
