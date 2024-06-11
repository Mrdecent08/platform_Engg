import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdBuildInfoComponent } from './pd-build-info.component';

describe('PdBuildInfoComponent', () => {
  let component: PdBuildInfoComponent;
  let fixture: ComponentFixture<PdBuildInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdBuildInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdBuildInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
