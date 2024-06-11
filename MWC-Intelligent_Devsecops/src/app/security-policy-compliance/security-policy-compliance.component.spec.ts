import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SecurityPolicyComplianceComponent } from './security-policy-compliance.component';

describe('SecurityPolicyComplianceComponent', () => {
  let component: SecurityPolicyComplianceComponent;
  let fixture: ComponentFixture<SecurityPolicyComplianceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SecurityPolicyComplianceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SecurityPolicyComplianceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
