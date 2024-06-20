import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdTestOptimizationComponent } from './pd-test-optimization.component';

describe('PdTestOptimizationComponent', () => {
  let component: PdTestOptimizationComponent;
  let fixture: ComponentFixture<PdTestOptimizationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdTestOptimizationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdTestOptimizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
