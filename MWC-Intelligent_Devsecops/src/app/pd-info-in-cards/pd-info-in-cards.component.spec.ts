import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdInfoInCardsComponent } from './pd-info-in-cards.component';

describe('PdInfoInCardsComponent', () => {
  let component: PdInfoInCardsComponent;
  let fixture: ComponentFixture<PdInfoInCardsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdInfoInCardsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdInfoInCardsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
