import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopVulnerableProjectsComponent } from './top-vulnerable-projects.component';

describe('TopVulnerableProjectsComponent', () => {
  let component: TopVulnerableProjectsComponent;
  let fixture: ComponentFixture<TopVulnerableProjectsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopVulnerableProjectsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopVulnerableProjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
