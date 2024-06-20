import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdVulnerabilitesComponent } from './pd-vulnerabilites.component';

describe('PdVulnerabilitesComponent', () => {
  let component: PdVulnerabilitesComponent;
  let fixture: ComponentFixture<PdVulnerabilitesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdVulnerabilitesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdVulnerabilitesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
