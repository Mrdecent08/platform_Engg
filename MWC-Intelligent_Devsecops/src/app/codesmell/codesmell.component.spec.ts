import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CodesmellComponent } from './codesmell.component';

describe('CodesmellComponent', () => {
  let component: CodesmellComponent;
  let fixture: ComponentFixture<CodesmellComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CodesmellComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CodesmellComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
