import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdVulBugsCodesmellComponent } from './pd-vul-bugs-codesmell.component';

describe('PdVulBugsCodesmellComponent', () => {
  let component: PdVulBugsCodesmellComponent;
  let fixture: ComponentFixture<PdVulBugsCodesmellComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdVulBugsCodesmellComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdVulBugsCodesmellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
