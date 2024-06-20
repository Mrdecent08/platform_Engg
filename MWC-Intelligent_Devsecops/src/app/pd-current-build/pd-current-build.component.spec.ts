import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdCurrentBuildComponent } from './pd-current-build.component';

describe('PdCurrentBuildComponent', () => {
  let component: PdCurrentBuildComponent;
  let fixture: ComponentFixture<PdCurrentBuildComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdCurrentBuildComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdCurrentBuildComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
