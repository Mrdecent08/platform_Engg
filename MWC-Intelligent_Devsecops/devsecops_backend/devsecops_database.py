from oslo_config import cfg
from oslo_context import context as oslo_context
from oslo_db import options
from oslo_db.sqlalchemy import enginefacade, utils
from sqlalchemy import and_, or_
from datetime import datetime,date,timedelta
import models
import os, sys
path = os.path.abspath(os.path.join(os.getcwd(), os.pardir))
sys.path.append(path)
enginefacade.transaction_context_provider(oslo_context.RequestContext)
CONF = cfg.CONF
options.set_defaults(CONF,connection= "mysql+pymysql://root:openstack@localhost/MWCdevsecops?charset=utf8")

def generate_id(sample_db, string, prefix):
  try:
    last = int(sample_db[-1].__dict__[string][-4:]) + 1
  except:
    last = 1
  new_id = prefix + format(last, "04d")
  return new_id


class testoptimization():

    def __init__(self):
      pass

    def get_tests(self, context):
      with enginefacade.reader.using(context):
        try:
          que = utils.model_query(models.testoptimization, context.session)
          test_db = que.all()
          return test_db
        except Exception:
          return None

    def update_tests(self, context, **kwargs):
      if 'testid' in kwargs.keys():
        with enginefacade.reader.using(context):
          test_db = utils.model_query(models.testoptimization, context.session).filter(models.testoptimization.testid == kwargs.get('testid')).first()
          for key in kwargs.keys():
            if key not in ['testid']:
              test_db.update( {key: str(kwargs.get(key))})
          context.session.add(test_db)
          return test_db

class vulnerabilities():

    def __init__(self):
      pass

    def get_vulnerabilities(self, context):
      with enginefacade.reader.using(context):
       try:
         que = utils.model_query(models.securityvulnerabilities, context.session)
         securityvul_db = que.all()
         return securityvul_db
       except Exception:
          return None

class projects():

    def __init__(self):
      pass

    def get_projects(self, context):
      with enginefacade.reader.using(context):
       try:
         que = utils.model_query(models.project, context.session)
         project_db = que.all()
         return project_db
       except Exception:
         return None

    def update_projects(self,context,kwargs):
      with enginefacade.writer.using(context):
        try:
          nrdate = kwargs + timedelta(days=30)
          project_db = utils.model_query(models.project, context.session).filter(models.project.releasedate == kwargs).first()
          for key in project_db.keys():
            if key =='releasedate':
              break;
          project_db.update({key: nrdate})
          context.session.add(project_db)
          return project_db['releasedate']
        except Exception:
          return None




class projectlivestatus():

    def __init__(self):
      pass

    def get_projectlivestatus(self, context):
      with enginefacade.reader.using(context):
       try:
         que = utils.model_query(models.projectlivestatus, context.session)
         projectlive_db = que.all()
         return projectlive_db
       except Exception:
         return None

class issue():

    def __init__(self):
      pass

    def get_issues(self, context):
      with enginefacade.reader.using(context):
       try:
         que = utils.model_query(models.issues, context.session)
         issues_db = que.all()
         return issues_db
       except Exception:
         return None
    
    def insert_issues(self,context,issues_dict):
      with enginefacade.writer.using(context):
       try:
         issues_db = models.issues(**issues_dict)
         context.session.add(issues_db)
         context.session.flush()
         return issues_db
       except Exception:
         return None

    def update_issues(self,context,kwargs):
      with enginefacade.writer.using(context):
       try:
         issues_db = utils.model_query(models.issues, context.session).filter(models.issues.issue_id == kwargs.get('issue_id')).first()
         for key in issues_db.keys():
            if key =='status':
              break;
         issues_db.update({key: kwargs['status']})
         context.session.add(issues_db)
         return issues_db
       except Exception:
         return None
									
			































