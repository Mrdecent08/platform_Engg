import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TestOptimizationComponent } from './test-optimization.component';

describe('TestOptimizationComponent', () => {
  let component: TestOptimizationComponent;
  let fixture: ComponentFixture<TestOptimizationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TestOptimizationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TestOptimizationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
