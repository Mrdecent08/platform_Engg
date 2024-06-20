import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdDeploymentStatisticsComponent } from './pd-deployment-statistics.component';

describe('PdDeploymentStatisticsComponent', () => {
  let component: PdDeploymentStatisticsComponent;
  let fixture: ComponentFixture<PdDeploymentStatisticsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdDeploymentStatisticsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdDeploymentStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
