import sqlalchemy as sqla
from sqlalchemy.ext import declarative
from oslo_db.sqlalchemy import models
from sqlalchemy.sql.schema import Sequence

class DbBase(models.ModelBase):
  @declarative.declared_attr
  def __tablename__(cls):
    return cls.__name__.lower()
BaseV1 = declarative.declarative_base(cls=DbBase)
class project(BaseV1):
 """Schema of project table"""
 __tablename__ = "Project"
 projectid = sqla.Column(sqla.Integer, primary_key=True, nullable=False)
 projectname = sqla.Column(sqla.String(40),nullable=False)
 owner = sqla.Column(sqla.String(40),nullable=False)
 releasedate = sqla.Column(sqla.TIMESTAMP(timezone=True), nullable=False) 
 prereleasedate = sqla.Column(sqla.TIMESTAMP(timezone=True), nullable=False)
 lastsucessfulbuild = sqla.Column(sqla.TIMESTAMP(timezone=True), nullable=False)
 CIARating = sqla.Column(sqla.Integer, nullable=False)
class testoptimization(BaseV1):
 """Schema of testOptimization table"""
 __tablename__ = "Functestoptimization"
 testid = sqla.Column(sqla.Integer, primary_key=True, nullable=False)
 projectid =  sqla.Column(sqla.Integer, sqla.ForeignKey('Project.projectid'), nullable=False)
 pname = sqla.Column(sqla.String(40),nullable=False)
 totaltestcases = sqla.Column(sqla.Integer, nullable=False)	
 passedtestcases = sqla.Column(sqla.Integer, nullable=False)
 failedtestcases = sqla.Column(sqla.Integer, nullable=False)
 totaldefects = sqla.Column(sqla.Integer, nullable=False)
 automatedtestcases = sqla.Column(sqla.Integer, nullable=False)
 sprint =  sqla.Column(sqla.String(10),nullable=False)
 Unimpactedtestcases= sqla.Column(sqla.String(50), nullable=False)
class securityvulnerabilities(BaseV1):
 """Schema of Vulnerabilities table"""
 __tablename__ = "vulnerabilities"
 id = sqla.Column(sqla.Integer, primary_key=True, nullable=False)
 type = sqla.Column(sqla.String(50),nullable=False)
 project = sqla.Column(sqla.String(25),nullable=False)
 sprint = sqla.Column(sqla.String(15),nullable=False)
 count = sqla.Column(sqla.Integer,nullable=False)
 severity = sqla.Column(sqla.String(15),nullable=False)
 sprintnumber = sqla.Column(sqla.String(10),nullable=False)
 assetsaffected = sqla.Column(sqla.Integer, nullable=False)
 vulnerabilitydate = sqla.Column(sqla.Date,nullable=False)
 datefixed = sqla.Column(sqla.Date,nullable=False)
class projectlivestatus(BaseV1):
 """Schema of Projectlivestatus table"""
 __tablename__ = "Projectlivestatus"
 id = sqla.Column(sqla.Integer, primary_key=True, nullable=False)
 projectname = sqla.Column(sqla.String(50),nullable=False)
 sprint = sqla.Column(sqla.String(10),nullable=False)
 status = sqla.Column(sqla.String(10),nullable=False)
 sprintrelease = sqla.Column(sqla.String(5),nullable=False)
class issues(BaseV1):
 """Schema of issues table"""
 __tablename__ = "issues"
 issue_id = sqla.Column(sqla.Integer,primary_key=True, nullable=False)
 vulnerability = sqla.Column(sqla.String(200),nullable=False)
 Date = sqla.Column(sqla.Date,nullable=False)
 status = sqla.Column(sqla.String(75),nullable=False)
