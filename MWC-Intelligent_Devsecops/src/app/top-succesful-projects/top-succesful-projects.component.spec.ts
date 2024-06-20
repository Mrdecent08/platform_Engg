import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopSuccesfulProjectsComponent } from './top-succesful-projects.component';

describe('TopSuccesfulProjectsComponent', () => {
  let component: TopSuccesfulProjectsComponent;
  let fixture: ComponentFixture<TopSuccesfulProjectsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopSuccesfulProjectsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopSuccesfulProjectsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
