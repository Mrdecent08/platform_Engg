import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DevOpsPipeLineComponent } from './dev-ops-pipe-line.component';

describe('DevOpsPipeLineComponent', () => {
  let component: DevOpsPipeLineComponent;
  let fixture: ComponentFixture<DevOpsPipeLineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DevOpsPipeLineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DevOpsPipeLineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
