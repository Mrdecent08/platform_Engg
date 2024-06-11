import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdBuildTrendsComponent } from './pd-build-trends.component';

describe('PdBuildTrendsComponent', () => {
  let component: PdBuildTrendsComponent;
  let fixture: ComponentFixture<PdBuildTrendsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdBuildTrendsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdBuildTrendsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
