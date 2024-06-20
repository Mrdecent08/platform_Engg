import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdSecVulSummaryGraphComponent } from './pd-sec-vul-summary-graph.component';

describe('PdSecVulSummaryGraphComponent', () => {
  let component: PdSecVulSummaryGraphComponent;
  let fixture: ComponentFixture<PdSecVulSummaryGraphComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdSecVulSummaryGraphComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdSecVulSummaryGraphComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
