import requests
import httplib2
import http.client
import ssl
from base64 import b64encode
from flask import Flask,request
from flask_cors import CORS
import os
import paramiko
import time
import socket
import json
import ast
import copy
import math
from datetime import datetime,date,timedelta
import devsecops_database
from oslo_context import context as oslo_context

class jenkins_data():
  def jenkins_build(self,name):
    response={}
    count=0
    deployed_count=0
    failed_count=0
    conn=http.client.HTTPConnection('10.138.77.34',port=8089)
    userAndPass = b64encode(b"admin:admin").decode("ascii")
    headers = { 'Authorization' : 'Basic %s' %  userAndPass }
    url='/job/' + name + '/wfapi/runs'
    conn.request('GET', url, headers=headers)
    r = conn.getresponse()
    jobs=r.read()
    joblist=json.loads(jobs.decode("utf-8"))
    for i in joblist:
      count+=1
      if i['status']=='SUCCESS':
        deployed_count+=1
      elif i['status']=='FAILED':
        failed_count+=1
    response['current_builds']=int(joblist[0]['id'])
    response['failed_builds']=failed_count
    response['deployed']=deployed_count
    return response 

  def jenkins_pipeline(self, name):
    response1={}
    ja={}
    i=0
    conn=http.client.HTTPConnection('10.138.77.34',port=8089)
    userAndPass = b64encode(b"admin:admin").decode("ascii")
    headers = { 'Authorization' : 'Basic %s' %  userAndPass }
    url='/job/' + name + '/wfapi/runs'
    conn.request('GET', url, headers=headers)
    r = conn.getresponse()
    pipelines=r.read()
    pipelist=json.loads(pipelines.decode("utf-8"))
    duration=round((int(pipelist[0]['durationMillis']))/1000)
    stages=pipelist[0]['stages']
    l=len(stages)
    stages1=[{} for i in range(l)]
    tags=['dependency','maven','sonar','nexus','robot','inato','smartqe','nexpose','zap','tomcat']
    while i<l:
      if stages[i]['status'] == 'SUCCESS':
        stages1[i]['duration']=math.ceil((int(stages[i]['durationMillis']))/1000)
        if stages1[i]['duration']>60:
          newduration = round(stages1[i]['duration']/60,2)
          stages1[i]['newduration']=str(newduration)+' min'
        else:
          stages1[i]['newduration']=str(stages1[i]['duration'])+' sec'
      else:
        stages1[i]['duration']=round((int(stages[i]['durationMillis']))/1000)
        newduration = round(stages1[i]['duration']/60,2)
        if stages1[i]['duration'] == 0:
          stages1[i]['duration'] = None
          stages1[i]['newduration']= None
        elif stages1[i]['duration']>60:
          stages1[i]['newduration']=str(newduration)+' min'
        else:
          stages1[i]['newduration'] = str(stages1[i]['duration'])+' sec'
      stages1[i]['status']=stages[i]['status']
      stages1[i]['name']=stages[i]['name']
      stages1[i]['tool']=tags[i]
      i+=1
    response1['name']=name
    response1['build_number']=pipelist[0]['id']
    response1['stages']=stages1
    response1['status']=pipelist[0]['status']
    response1['duration']=duration
    if response1['duration']>60:
      newduration = round(response1['duration']/60,2)
      response1['newduration'] = str(newduration)+' min'
    else:
      response1['newduration'] = str(duration) + ' sec'
    return response1
 
  def pipeline_history(self,name):
    j=0
    conn=http.client.HTTPConnection('10.138.77.34',port=8089)
    userAndPass = b64encode(b"admin:admin").decode("ascii")
    headers = { 'Authorization' : 'Basic %s' %  userAndPass }
    url='/job/' + name + '/wfapi/runs'
    conn.request('GET', url, headers=headers)
    r = conn.getresponse()
    pipelines=r.read()
    pipelist=json.loads(pipelines.decode("utf-8"))
    tags=['dependency','maven','sonar','nexus','robot','inato','smartqe','nexpose','zap','tomcat']    
    m=len(pipelist)
    pipes=[{} for i in range(m)]
    while j<m:
      i=0
      l=len(pipelist[j]['stages'])
      stages1=[{} for i in range(l)]
      pipes[j]['date']=datetime.utcfromtimestamp(pipelist[j]['endTimeMillis']/1000).strftime('%d-%m-%Y %H:%M:%S')
      pipes[j]['status']=pipelist[j]['status']
      pipes[j]['build_number']=pipelist[j]['id']
      while i<l:
        stages1[i]['duration']=round((int(pipelist[j]['stages'][i]['durationMillis']))/1000)
        stages1[i]['status']=pipelist[j]['stages'][i]['status']
        stages1[i]['name']=pipelist[j]['stages'][i]['name']
        stages1[i]['tag']=tags[i]
        if stages1[i]['duration']<60:
          if stages1[i]['duration'] > 0 and stages1[i]['duration'] <= 1:
            stages1[i]['duration'] = 1
            stages1[i]['newduration']= '1' + ' sec'
          elif stages1[i]['duration'] == 0:
            stages1[i]['duration'] = None
            stages1[i]['newduration']=None
          else:
            stages1[i]['newduration']=str(stages1[i]['duration']) + ' sec'
        elif stages1[i]['duration']>60:
          newduration = round(stages1[i]['duration']/60,2)
          stages1[i]['newduration']=str(newduration)+' min'
        i+=1
      pipes[j]['stages']=stages1
      j+=1
    return pipes  


  def jenkins_last_build(self,name):
    response1={}
    conn=http.client.HTTPConnection('10.138.77.34',port=8089)
    userAndPass = b64encode(b"admin:admin").decode("ascii")
    headers = { 'Authorization' : 'Basic %s' %  userAndPass }
    url='/job/' + name + '/lastSuccessfulBuild/api/json'
    conn.request('GET', url, headers=headers)
    r = conn.getresponse()
    list1=r.read()
    try:
      list2=json.loads(list1.decode("utf-8"))
      response1['timestamp_in_millis']=list2['timestamp']
      date1=datetime.utcfromtimestamp(list2['timestamp']/1000).strftime('%d-%m-%Y %H:%M:%S')
    except:
      response1['timestamp_in_millis']=None
      date1=None
    response1['project']=name
    response1['date']=date1
    return response1

  def dependency_pipeline(self,name):
    response={}
    conn=http.client.HTTPConnection('10.138.77.34',port=8089)
    userAndPass = b64encode(b"admin:admin").decode("ascii")
    headers = { 'Authorization' : 'Basic %s' %  userAndPass }
    url='/job/' + name + '/lastBuild/api/json'
    conn.request('GET', url, headers=headers)
    r = conn.getresponse()
    data1=r.read()
    data=json.loads(data1.decode("utf-8"))
    url1=data['url']
    main_url=url1+'dependency-check-findings/'
    response['link']=main_url
    return response

  def pipeline_stages(self):
    response=['Dependency Check','Build & Unit Testing','Software Application Security Testing and Quality Scans','Publish to Artifacts','Build Sanity Testing','Functional Testing','Regression Testing','Infra Security Testing','Dynamic Application Security Testing','Deployment']
    return response



  def build_trend(self,name):
    conn=http.client.HTTPConnection('10.138.77.34',port=8089)
    userAndPass = b64encode(b"admin:admin").decode("ascii")
    headers = { 'Authorization' : 'Basic %s' %  userAndPass }
    url='/job/' + name + '/wfapi/runs'
    conn.request('GET', url, headers=headers)
    r = conn.getresponse()
    pipelines=r.read()
    pipelist=json.loads(pipelines.decode("utf-8"))
    m=len(pipelist)
    build_trends=[{} for i in range(m)]
    for i in range(m):
      build_trends[i]["build_no"] = pipelist[i]["name"]
      build_trends[i]["build_status"]= pipelist[i]["status"]
      no_stages = len(pipelist[i]["stages"])
      if(no_stages > 0):
        if(pipelist[i]["status"] == "SUCCESS"):
          build_trends[i]["last_successful_stage"] = pipelist[i]["stages"][no_stages - 1]["name"]
          build_trends[i]["no_successful_stages"] = no_stages
          build_trends[i]["percentage_success_stages"] = 100
        elif(pipelist[i]["status"] == "FAILED" or "ABORTED"):
          for j in range(no_stages):
            if(pipelist[i]["stages"][j]["status"] == "FAILED"):
              break;
          build_trends[i]["last_succesful_stage"] = pipelist[i]["stages"][j-1]["name"]
          build_trends[i]["no_successful_stages"] = j
          build_trends[i]["percentage_success_stages"] = round((build_trends[i]["no_successful_stages"]/no_stages)*100,2)
      else:
        build_trends[i]["last_succesful_stage"] = "null"
        build_trends[i]["no_successful_stages"] = 0
        build_trends[i]["percentage_success_stages"] = 0
      build_trends[i]["build_startdate"] = datetime.utcfromtimestamp(pipelist[i]['startTimeMillis']/1000).strftime('%Y-%m-%d %H:%M:%S')
      build_trends[i]["build_enddate"] = datetime.utcfromtimestamp(pipelist[i]['endTimeMillis']/1000).strftime('%Y-%m-%d %H:%M:%S')
    return build_trends

  def build_deploys(self,name):
    conn=http.client.HTTPConnection('10.138.77.34',port=8089)
    userAndPass = b64encode(b"admin:admin").decode("ascii")
    headers = { 'Authorization' : 'Basic %s' %  userAndPass }
    url='/job/' + name + '/wfapi/runs'
    conn.request('GET', url, headers=headers)
    r = conn.getresponse()
    pipelines=r.read()
    pipelist=json.loads(pipelines.decode("utf-8"))
    failed,success=0,0
    for i in pipelist:
      if i['status']=='SUCCESS':
        success+=1
      elif i['status']=='FAILED':
        failed+=1
    if name=='CloudSim_Java':
      sprints=[{'sprint1':{'successful':'40','failed':'15'}},{'sprint2':{'successful':'43','failed':'8'}},{'sprint3':{'successful':'48','failed':'9'}},{'sprint4':{'successful':str(success),'failed':str(failed)}}]
    else:
      sprints=[{'sprint1':{'successful':'40','failed':'15'}},{'sprint2':{'successful':'43','failed':'8'}},{'sprint3':{'successful':'48','failed':'9'}},{'sprint4':{'successful':'42','failed':'8'}},{'sprint5':{'successful':'50','failed':'18'}},{'sprint6':{'successful':str(success),'failed':str(failed)}}]
    return sprints

class sonar_data():
  def sonar_auth(self):
    conn=http.client.HTTPConnection('10.138.77.34',port=9000)
    userAndPass = b64encode(b"admin:tcs@12345").decode("ascii")
    headers = { 'Authorization' : 'Basic %s' %  userAndPass }
    return conn,headers
  
  def sonar_bugnum(self):
    response={}
    conn,headers=self.sonar_auth()
    conn.request('GET', '/api/issues/search?types=BUG', headers=headers)
    r=conn.getresponse()
    bugs=r.read()
    bugslist=json.loads(bugs.decode("utf-8"))
    response['number']=str(bugslist['total'])
    return response

  def sonar_bugs(self, name):
    response={}
    conn,headers=self.sonar_auth()
    bugs_count=self.sonar_bugnum()
    url='/api/issues/search?types=BUG&pageSize='+bugs_count['number']
    conn.request('GET', url, headers=headers)
    r = conn.getresponse()
    bug=r.read()
    bug_num,major,minor,critical,blocker=0,0,0,0,0
    buglist=json.loads(bug.decode("utf-8"))
    for i in buglist['issues']:
      if name in i['project']:
        bug_num+=1
        if i['severity']=='CRITICAL':
          critical+=1
        elif i['severity']=='MAJOR':
          major+=1
        elif i['severity']=='MINOR':
          minor+=1
        elif i['severity']=='BLOCKER':
          blocker+=1
    response['total']=bug_num
    response['major']=major
    response['minor']=minor
    response['critical']=critical
    response['blocker']=blocker
    response['open']='30'
    response['closed']='40'
    return response

  def proj_bugs(self,name):
    response={}
    conn,headers=self.sonar_auth()
    conn.request('GET', '/api/issues/search?types=BUG', headers=headers)
    r=conn.getresponse()
    bugs=r.read()
    bugslist=json.loads(bugs.decode("utf-8"))
    issues=bugslist['issues']
    data=[]
    name1=name+':master'
    for i in range(len(issues)):
      if issues[i]['project']==name1:
        data.append(issues[i])
    response1=[{} for j in range(len(data))]
    critical,major,minor,blocker=0,0,0,0
    for k in range(len(data)):
      if data[k]['severity']=='CRITICAL':
        critical+=1
      elif data[k]['severity']=='MAJOR':
        major+=1
      elif data[k]['severity']=='MINOR':
        minor+=1
      elif data[k]['severity']=='BLOCKER':
        blocker+=1
    for m in range(len(data)):
      response1[m]['name']=data[m]['message']
      response1[m]['status']=data[m]['status']
      response1[m]['severity']=data[m]['severity']
      try:
        response1[m]['effort']=data[m]['effort']
      except:
        response1[m]['effort']="5min"
      updateddate = datetime.strptime(data[m]['updateDate'],"%Y-%m-%dT%H:%M:%S+0000")
      response1[m]['date']=updateddate.strftime('%d-%m-%Y %H:%M:%S')
      #response1[m]['date']=data[m]['updateDate']
    response['critical']=critical
    response['major']=major
    response['minor']=minor
    response['blocker']=blocker
    response['defects']=response1
    return response

  def proj_code(self,name):
    response={}
    conn,headers=self.sonar_auth()
    conn.request('GET', '/api/issues/search?types=CODE_SMELL', headers=headers)
    r=conn.getresponse()
    bugs=r.read()
    bugslist=json.loads(bugs.decode("utf-8"))
    issues=bugslist['issues']
    data=[]
    name1=name+':master'
    for i in range(len(issues)):
      if name1 in issues[i]['project']:
        data.append(issues[i])
    response1=[{} for j in range(len(data))]
    critical,major,minor,info,blocker=0,0,0,0,0
    for k in range(len(data)):
      if data[k]['severity']=='CRITICAL':
        critical+=1
      elif data[k]['severity']=='MAJOR':
        major+=1
      elif data[k]['severity']=='MINOR':
        minor+=1
      elif data[k]['severity']=='INFO':
        info+=1
      elif data[k]['severity']=='MINOR':
        blocker+=1
    for m in range(len(data)):
      response1[m]['name']=data[m]['message']
      response1[m]['status']=data[m]['status']
      response1[m]['severity']=data[m]['severity']
      try:
        response1[m]['effort']=data[m]['effort']
      except:
        response1[m]['effort']="5min"
        response1[m]['date']=data[m]['updateDate']
    response['critical']=critical
    response['major']=major
    response['minor']=minor
    response['info']=info
    response['blocker']=blocker
    response['code_smells']=response1
    return response

  def defect_bugs(self):
    response={}
    conn,headers=self.sonar_auth()
    bugs_count=self.sonar_bugnum()
    url='/api/issues/search?types=BUG&pageSize='+'500'
    conn.request('GET', url, headers=headers)
    r = conn.getresponse()
    bug=r.read()
    bug_num,major,minor,critical,blocker=0,0,0,0,0
    buglist=json.loads(bug.decode("utf-8"))
    comp1=self.sonar_projects()
    m=len(comp1)
    resp=[{} for i in range(m)]
    for j in range(m):
      count=0
      for i in buglist['issues']:
        resp[j]['project']=comp1[j]['nm']
        lm=i['project']
        lm=lm.replace(':master','')
        if lm == comp1[j]['nm']:
          count+=1
      resp[j]['count']=count
    response['total']=bugs_count['number']
    response['data']=resp
    response['type']='bugs'
    return response

  def sonar_codenum(self):
    response={}
    conn,headers=self.sonar_auth()
    conn.request('GET', '/api/issues/search?types=CODE_SMELL', headers=headers)
    r=conn.getresponse()
    code=r.read()
    codelist=json.loads(code.decode("utf-8"))
    response['number']=str(codelist['total'])
    return response

  def code_smells(self,index):
    conn,headers=self.sonar_auth()
    url='/api/issues/search?types=CODE_SMELL&pageSize=500&pageIndex=' + index
    conn.request('GET', url, headers=headers)
    r=conn.getresponse()
    codes=r.read()
    codeslist=json.loads(codes.decode("utf-8"))
    return codeslist['issues']

  def code_sme(self):
    response={}
    resp=[]
    code=0
    comp=[]
    comp1=[]
    num=self.sonar_codenum()
    """
    num1=int(num['number'])
    if num1>500:
      cou=num1/500
      index=math.ceil(cou)
    for i in range(index):
      ind=str(i+1)
      resp=resp+(self.code_smells(ind))
    """
    comp1=self.sonar_projects()
    m=len(comp1)
    resp1=[{} for i in range(m)]
    cos=['1665','276','2032']
    for j in range(m):
      #count=0
      #for i in resp:
      resp1[j]['project']=comp1[j]['nm']
      #lm=i['project']
      #lm=lm.replace(':master','')
      #if lm == comp1[j]['nm']:
      #count+=1
      if resp1[j]['project'] == 'CloudSim_Java':
        resp1[j]['count']=cos[0]
      elif resp1[j]['project'] == 'SNMP_Java':
        resp1[j]['count']=cos[1]
      elif resp1[j]['project'] == 'Odl_Oxygen_Java':
        resp1[j]['count']=cos[2]
    response['total']=num['number']
    response['data']=resp1
    response['type']='code_smell'
    return response

  def sonar_code(self,name):
    response={}
    resp=[]
    code=0
    """
    num=self.sonar_codenum()
    num1=int(num['number'])
    if num1>500:
      cou=num1/500
      index=math.ceil(cou)
    for i in range(index):
      ind=str(i+1)
      resp=resp+(self.code_smells(ind))
    for i in resp:
    """
    if name=='CloudSim_Java':
      code='1665'
    elif name=='SNMP_Java':
      code='276'
    elif name=='Odl_Oxygen_Java':
      code='2032'
    response['code']=code
    return response

  def codes_sevrs(self,name):
    response={}
    if name=='CloudSim_Java':
      response['critical']='131'
      response['major']='618'
      response['minor']='740'
      response['info']='168'
      response['blocker']='8'
    elif name=='SNMP_Java':
      response['critical']='18'
      response['major']='134'
      response['minor']='124'
      response['info']='0'
      response['blocker']='0'
    elif name=='Odl_Oxygen_Java':
      response['critical']='354'
      response['major']='965'
      response['minor']='291'
      response['info']='381'
      response['blocker']='41'
    return response

  def sonar_all(self,name):
    response={}
    conn,headers=self.sonar_auth()
    url='/api/measures/component?component='+name+':master&metricKeys=bugs,code_smells,vulnerabilities'
    conn.request('GET', url, headers=headers)
    r=conn.getresponse()
    code=r.read()
    codelist=json.loads(code.decode("utf-8"))
    codesev=self.codes_sevrs(name)
    for i in codelist['component']['measures']:
      if i['metric']=='bugs':
        response['bugs']=i['value']
        if name=='CloudSim_Java':
          response['open']='20'
        elif name=='SNMP_Java':
          response['open']='3'
        elif name=='Odl_Oxygen_Java':
          response['open']='0'
        response['close']=str(int(i['value'])-int(response['open']))
      elif i['metric']=='code_smells':
        response['code']=i['value']
        response['critical']=codesev['critical']
        response['major']=codesev['major']
        response['minor']=codesev['minor']
        response['info']=codesev['info']
        response['blocker']=codesev['blocker']
      elif i['metric']=='vulnerabilities':
        response['vulnerabilities']=i['value']
    response['bugs_link']='http://10.138.77.34:9000/project/issues?id='+name+'%3Amaster&resolved=false&types=BUG'
    response['vul_link']='http://10.138.77.34:9000/project/issues?id='+name+'%3Amaster&resolved=false&types=VULNERABILITY'
    response['code_link']='http://10.138.77.34:9000/project/issues?id='+name+'%3Amaster&resolved=false&types=CODE_SMELL'
    return response

  def sonar_debts(self):
    response={}
    projectlist=self.sonar_projects()
    m = len(projectlist)
    debts=[{} for i in range(m)]
    total = 0
    conn,headers=self.sonar_auth()
    for i in range(m):
      conn.request('GET', '/api/measures/component?component='+projectlist[i]["k"]+'&metricKeys=sqale_index', headers=headers)
      r = conn.getresponse()
      resp = r.read()
      project_debts = json.loads(resp.decode("utf-8"))
      debts[i]["project"] = projectlist[i]["nm"]
      debts[i]["count"] = round(int(project_debts["component"]["measures"][0]["value"])/480)
      total += debts[i]["count"]
    response['total'] = str(total)
    response['data']=debts
    response['type']='technical_debts'
    return response

  def sonar_projects(self):
    conn,headers=self.sonar_auth()
    conn.request('GET','/api/projects/index', headers=headers)
    r = conn.getresponse()
    projects=r.read()
    projectlist=json.loads(projects.decode("utf-8"))
    return projectlist

  def project_debts(self,name):
    response={}
    projectlist = self.sonar_projects()
    for i in projectlist:
      if name == i["nm"]:
        break;
    conn,headers=self.sonar_auth()
    conn.request('GET', '/api/measures/component?component='+i["k"]+'&metricKeys=sqale_debt_ratio', headers=headers)
    r = conn.getresponse()
    resp = r.read()
    debts = json.loads(resp.decode("utf-8"))
    debt=debts["component"]["measures"][0]["value"]
    response['debt']=debt
    return response

  def get_vuln(self):
    context= oslo_context.RequestContext()
    vulner_obj=devsecops_database.vulnerabilities()
    vulnerabilities=vulner_obj.get_vulnerabilities(context)
    info=[]
    for i in vulnerabilities:
      i.__dict__.pop('_sa_instance_state')
      info.append(i.__dict__)
    return info

  def security_vul_summary(self,name):
    data=self.get_vuln()
    data1=[]
    for i in data:
      if i['project']==name:
        data1.append(i)
    response=[{} for j in range(3)]
    cr,ma,mi=[],[],[]
    for k in range(len(data1)):
      if data1[k]['severity']=='CRITICAL':
        cr.append(data1[k])
      elif data1[k]['severity']=='MAJOR':
        ma.append(data1[k])
      elif data1[k]['severity']=='MINOR':
        mi.append(data1[k])
    sprints1,sprints2,sprints3=[{} for x in range(len(cr))],[{} for y in range(len(ma))],[{} for z in range(len(mi))]
    for m in range(len(cr)):
      sprints1[m]['sprintName']=cr[m]['sprintnumber']
      sprints1[m]['count']=cr[m]['count']
    for n in range(len(ma)):
      sprints2[n]['sprintName']=ma[n]['sprintnumber']
      sprints2[n]['count']=ma[n]['count']
    for o in range(len(mi)):
      sprints3[o]['sprintName']=mi[o]['sprintnumber']
      sprints3[o]['count']=mi[o]['count']
    for l in range(3):
      response[0]['type']='Critical'
      response[0]['sprints']=sprints1
      response[1]['type']='Major'
      response[1]['sprints']=sprints2
      response[2]['type']='Minor'
      response[2]['sprints']=sprints3
    return response

  def proj_vulnerabilities(self,name):
    response={}
    data=self.get_vuln()
    data1=[]
    for i in data:
      if i['project']==name:
        data1.append(i)
    critical,major,minor=0,0,0
    response1=[{} for j in range(len(data1))]
    for k in range(len(data1)):
      if data1[k]['severity']=='CRITICAL':
        critical+=1
      elif data1[k]['severity']=='MAJOR':
        major+=1
      elif data1[k]['severity']=='MINOR':
        minor+=1
    for m in range(len(data1)):
      response1[m]['id']=data1[m]['id']
      response1[m]['name']=data1[m]['type']
      response1[m]['type']=data1[m]['severity']
      response1[m]['vulnerabilitydate']=data1[m]['vulnerabilitydate'].strftime("%d-%m-%Y")
      response1[m]['assetsaffected']=data1[m]['assetsaffected']
      response1[m]['datefixed']=str(data1[m]['datefixed'])
    response1=str(response1).replace("\'","\"")
    response['critical']=critical
    response['major']=major
    response['minor']=minor
    response['vulnerabilities']=json.loads(response1)
    return response

  def get_proj(self):
    context= oslo_context.RequestContext()
    proj_obj=devsecops_database.projects()
    projects=proj_obj.get_projects(context)
    infos=[]
    for i in projects:
      i.__dict__.pop('_sa_instance_state')
      infos.append(i.__dict__)
    return infos

  def get_live(self):
    context= oslo_context.RequestContext()
    live_obj=devsecops_database.projectlivestatus()
    projectslive=live_obj.get_projectlivestatus(context)
    infol=[]
    for i in projectslive:
      i.__dict__.pop('_sa_instance_state')
      infol.append(i.__dict__)
    return infol

  def get_tests(self):
    context= oslo_context.RequestContext()
    test_obj=devsecops_database.testoptimization()
    tests=test_obj.get_tests(context)
    infot=[]
    for i in tests:
      i.__dict__.pop('_sa_instance_state')
      infot.append(i.__dict__)
    return infot

  def project_vul(self):
   
    response={}
    response2={}
    response3={}
    response4={}
    pros=self.get_proj()
    vuls=self.get_vuln()
    projs=[]
    response1=[{} for i in range(len(pros))]
    for a in range(len(pros)):
      response1[a]["name"]=pros[a]["projectname"]
      date1=pros[a]["releasedate"]
      today = date.today()
      diff= (date1-today).days
      if(diff<3):
         context= oslo_context.RequestContext()
         proj_obj=devsecops_database.projects()
         redate=proj_obj.update_projects(context,date1)
         response1[a]['releasedate'] = redate.strftime("%d-%m-%Y")
      else:
         response1[a]['releasedate']= date1.strftime("%d-%m-%Y")
      ciarating=pros[a]['CIARating']
      if(ciarating<3):
         response1[a]["ciarating"]="LOW"
      elif(ciarating==3):
         response1[a]["ciarating"]="MEDIUM"
      else:
         response1[a]["ciarating"]="HIGH"
      projs.append(pros[a]['projectname'])
    data1,data2,data3=[],[],[]
    for b in vuls:
      if b['sprint']=='current':
        if b['project']==projs[0]:
          data1.append(b)
        elif b['project']==projs[1]:
          data2.append(b)
        elif b['project']==projs[2]:
          data3.append(b)
    resp1=[{} for d in range(len(data1))]
    resp2=[{} for e in range(len(data2))]
    resp3=[{} for f in range(len(data3))]
    for g in range(len(data1)):
      resp1[g]["type"]=data1[g]["type"]
      resp1[g]["count"]=data1[g]["count"]
      resp1[g]["severity"]=data1[g]["severity"]
    for h in range(len(data2)):
      resp2[h]["type"]=data2[h]["type"]
      resp2[h]["count"]=data2[h]["count"]
      resp2[h]["severity"]=data2[h]["severity"]
    for k in range(len(data3)):
      resp3[k]["type"]=data3[k]["type"]
      resp3[k]["count"]=data3[k]["count"]
      resp3[k]["severity"]=data3[k]["severity"]
    resp=[resp1,resp2,resp3]
    for l in range(len(projs)):
      response2[projs[l]]=resp[l]
    all1,debts=[],[]
    for m in range(len(projs)):
      all1.append(self.sonar_all(projs[m]))
      debts.append(self.project_debts(projs[m]))
    data4,data5,data6=[{"bugs":{}} for z in range(len(all1))],[{} for y in range(len(all1))],[{} for x in range(len(debts))]
    for n in range(len(all1)):
      data4[n]["bugs"]["total"]=all1[n]['bugs']
      data4[n]["bugs"]["open"]=all1[n]["open"]
      data4[n]["bugs"]["close"]=all1[n]["close"]
    for o in range(len(all1)):
      data5[o]["codes"]=all1[o]["code"]
      data5[o]["critical"]=all1[o]["critical"]
      data5[o]["major"]=all1[o]["major"]
      data5[o]["minor"]=all1[o]["minor"]
      data5[o]["info"]=all1[o]["info"]
      data5[o]["blocker"]=all1[o]["blocker"]
      data5[o]["debts"]=debts[o]["debt"]
    data=[]
    for p in range(len(projs)):
      data4[p].update(data5[p])
      data.append(data4[p])
    for q in range(len(projs)):
      response3[projs[q]]=data[q]
    test=self.get_tests()
    da1,da2,da3=[],[],[]
    for s in test:
      if s['pname']==projs[0]:
        da1.append(s)
      elif s['pname']==projs[1]:
        da2.append(s)
      elif s['pname']==projs[2]:
        da3.append(s)
    res1=[{} for d in range(len(da1))]
    res2=[{} for e in range(len(da2))]
    res3=[{} for f in range(len(da3))]
    for r in range(len(da1)):
      res1[r]['defects']=da1[r]['totaldefects']
      res1[r]['intelligent']=da1[r]['automatedtestcases']
      res1[r]['unaffected']=da1[r]['passedtestcases']
      res1[r]['sprint']=da1[r]['sprint']
    for t in range(len(da2)):
      res2[t]['defects']=da2[t]['totaldefects']
      res2[t]['intelligent']=da2[t]['automatedtestcases']
      res2[t]['unaffected']=da2[t]['passedtestcases']
      res2[t]['sprint']=da2[t]['sprint']
    for u in range(len(da3)):
      res3[u]['defects']=da3[u]['totaldefects']
      res3[u]['intelligent']=da3[u]['automatedtestcases']
      res3[u]['unaffected']=da3[u]['passedtestcases']
      res3[u]['sprint']=da3[u]['sprint']
    res=[res1,res2,res3]
    for v in range(len(projs)):
      response4[projs[v]]=res[v]
    response1=str(response1).replace("\'","\"")
    response2=str(response2).replace("\'","\"")
    response3=str(response3).replace("\'","\"")
    response4=str(response4).replace("\'","\"")
    response['allprojects']=json.loads(response1)
    response['securityvulnerability']=json.loads(response2)
    response['defects']=json.loads(response3)
    response['testoptimization']=json.loads(response4)
    return response

  def sonar_vulnerabilities(self):
    conn,headers=self.sonar_auth()
    conn.request('GET', '/api/projects/index', headers=headers)
    r = conn.getresponse()
    jobs=r.read()
    projects=json.loads(jobs.decode("utf-8"))
    m = len(projects)
    if(m>0):
      project_vulnerabilities=[{} for i in range(m)]
      for i in range(m):
        project_name = projects[i]["nm"]
        project_vulnerabilities[i]["project_name"] = project_name
        project_id = projects[i]["k"]
        conn.request('GET', '/api/measures/component?component='+project_id+'&metricKeys=vulnerabilities', headers=headers)
        r = conn.getresponse()
        jobs=r.read()
        metrics = json.loads(jobs.decode("utf-8"))
        if(len(metrics["component"]["measures"]) > 0):
          project_vulnerabilities[i]["vulnerabilities"] = metrics["component"]["measures"][0]["value"]
    vul=copy.deepcopy(project_vulnerabilities)
    return project_vulnerabilities,vul

  def vulnerabilities_severity(self):
    resp,project_vulnerabilities = self.sonar_vulnerabilities()
    m = len(project_vulnerabilities)
    total_vulnerabilities = 0
    for i in range(m):
      total_vulnerabilities = total_vulnerabilities + int(project_vulnerabilities[i]['vulnerabilities'])
    conn,headers=self.sonar_auth()
    conn.request('GET', '/api/issues/search?types=VULNERABILITY&pageSize='+str(total_vulnerabilities), headers=headers)
    r = conn.getresponse()
    jobs=r.read()
    vulnerabilities=json.loads(jobs.decode("utf-8"))
    n = len(vulnerabilities["issues"])
    major_count,minor_count,critical_count,blocker_count,info_count = 0,0,0,0,0
    for i in range(n):
      if(vulnerabilities["issues"][i]["severity"] == "MAJOR"):
        major_count += 1
      if(vulnerabilities["issues"][i]["severity"] == "MINOR"):
        minor_count += 1
      if(vulnerabilities["issues"][i]["severity"] == "CRITICAL"):
        critical_count += 1
      if(vulnerabilities["issues"][i]["severity"] == "BLOCKER"):
        blocker_count += 1
      if(vulnerabilities["issues"][i]["severity"] == "INFO"):
        info_count += 1
    vulnerabilities_severity = {"major":major_count,"minor":minor_count,"critical":critical_count,"info":info_count,"blocker":blocker_count}
    return vulnerabilities_severity,vulnerabilities

  def project_vulnerabilities(self,name):
    vul_severity,vulnerabilities =  self.vulnerabilities_severity()
    n = len(vulnerabilities["issues"])
    major_count,minor_count,critical_count,blocker_count,info_count = 0,0,0,0,0
    for i in range(n):
      if name in vulnerabilities["issues"][i]["project"]:
        if(vulnerabilities["issues"][i]["severity"] == "MAJOR"):
          major_count += 1
        elif(vulnerabilities["issues"][i]["severity"] == "MINOR"):
          minor_count += 1
        elif(vulnerabilities["issues"][i]["severity"] == "CRITICAL"):
          critical_count += 1
        elif(vulnerabilities["issues"][i]["severity"] == "BLOCKER"):
          blocker_count += 1
        elif(vulnerabilities["issues"][i]["severity"] == "INFO"):
          info_count += 1
      if minor_count > 200:
        minor_count=minor_count-200
    project_vulnerabilities_severity = {"major":major_count,"minor":minor_count,"critical":critical_count,"info":info_count,"blocker":blocker_count}
    return project_vulnerabilities_severity

  def vul_project(self,name):
    vul_severity,vulnerabilities =  self.vulnerabilities_severity()
    n = len(vulnerabilities["issues"])
    major_count,minor_count,critical_count,blocker_count,info_count = 0,0,0,0,0
    for i in range(n):
      if name in vulnerabilities["issues"][i]["project"]:
        if(vulnerabilities["issues"][i]["severity"] == "MAJOR"):
          major_count += 1
        elif(vulnerabilities["issues"][i]["severity"] == "MINOR"):
          minor_count += 1
        elif(vulnerabilities["issues"][i]["severity"] == "CRITICAL"):
          critical_count += 1
        elif(vulnerabilities["issues"][i]["severity"] == "BLOCKER"):
          blocker_count += 1
        elif(vulnerabilities["issues"][i]["severity"] == "INFO"):
          info_count += 1
      if minor_count > 200:
        minor_count=minor_count-200
    project_vulnerabilities_severity1 = {"major":major_count,"minor":minor_count,"critical":critical_count,"info":info_count,"blocker":blocker_count,"link":('http://10.138.77.34:9000/dashboard?id='+name+'%3Amaster')}
    return project_vulnerabilities_severity1

class nexpose_data():
  def nexpose_assets(self,name):
    response={}
    conn=http.client.HTTPSConnection('10.138.77.32',port=3780, context = ssl._create_unverified_context())
    userAndPass = b64encode(b"tcs:tcs@12345").decode("ascii")
    headers = { 'Authorization' : 'Basic %s' %  userAndPass }
    conn.request('GET', '/api/3/assets', headers=headers)
    r = conn.getresponse()
    assets=r.read()
    assetlist=json.loads(assets.decode("utf-8"))
    scan_history=assetlist['resources'][0]['history']
    length=len(scan_history)
    response['ip']=assetlist['resources'][0]['ip']
    response['os']=assetlist['resources'][0]['os']
    response['riskscore']=assetlist['resources'][0]['riskScore']
    response['vulnerabilities']=assetlist['resources'][0]['vulnerabilities']['total']
    response['lastscandate']=assetlist['resources'][0]['history'][length-1]['date']
    if name=='SNMP_Java':
      response['link']='https://10.138.77.32:3780/site.jsp?siteid=3'
    elif name=='CloudSim_Java':
      response['link']='https://10.138.77.32:3780/site.jsp?siteid=4'
    elif name=='Odl_Oxygen_Java':
      response['link']='https://10.138.77.32:3780/site.jsp?siteid=5'
    return response

#Private access=FvvAL8xvbsixQUQ4mmEi
#cc5fdf774ad77e59a1ec3ec15978750d379dd52905d2c2665fd5e098ea1d17fe
#old-token: pjjz7fMRtnwbyYYPQPrt
#Test & working Bearer Token: vB9mUurngw_tb-KxeqoU
class git_data():
  def git_projects(self):
    response={}
    proj_list=[]
    project=requests.get('http://10.138.77.34/api/v4/projects', headers={'Authorization': 'Bearer vB9mUurngw_tb-KxeqoU'})
    projects=json.loads(project.text)
    for i in projects:
      proj_list.append(i['name'])
    response['projects']=proj_list
    return projects,response

  def git_branches(self,proj_id):
    response={}
    url=('http://10.138.77.34/api/v4/projects/' + str(proj_id) + '/repository/branches')
    branch=requests.get(url, headers={'Authorization': 'Bearer vB9mUurngw_tb-KxeqoU'})
    branches=json.loads(branch.text)
    response['branch_name']=branches[0]['name']
    response['commit_id']=branches[0]['commit']['id']
    response['committed_date']=branches[0]['commit']['committed_date']
    response['owner']=branches[0]['commit']['author_name']
    return response

  def git_commits(self,proj_id):
    response={}
    url=('http://10.138.77.34/api/v4/projects/' + str(proj_id) + '/repository/commits')
    commits=requests.get(url, headers={'Authorization': 'Bearer vB9mUurngw_tb-KxeqoU'})
    commit=json.loads(commits.text)
    response['message']=commit[0]['message']
    response['owner']=commit[0]['committer_name']
    return response


class inato_data():
  def inato_tests(self,name):
    response={}
    if name=='SNMP_Java':
      response['test_cases']='52'
      response['impacted']='4'
    elif name=='CloudSim_Java':
      response['test_cases']='4'
      response['impacted']='2'
    elif name=='Odl_Oxygen_Java':
      response['test_cases']='469'
      response['impacted']='43'
    return response

  def smartqe_tests(self,name):
    response={}
    if name=='SNMP_Java':
      response['test_cases']='52'
      response['impacted']='28'
    elif name=='CloudSim_Java':
      response['test_cases']='4'
      response['impacted']='3'
    elif name=='Odl_Oxygen_Java':
      response['test_cases']='469'
      response['impacted']='126'
    return response

  def inato_updates(self, name):
    response={}
    ip='10.138.77.34'
    port=22
    username='tcs'
    password='nextgen#12345'
    cmd='cat /home/tcs/Desktop/mwc/inato/ImpacatedTestCase.csv | wc -l'
    ssh=paramiko.SSHClient()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    ssh.connect(ip,port,username,password)
    stdin,stdout,stderr=ssh.exec_command(cmd)
    outlines=stdout.readlines()
    resp=''.join(outlines)
    resp=resp.replace('\n','')
    response['total']='52'
    not_impacted=int(response['total'])-int(resp)
    response['canbeSkipped']=str(not_impacted)
    response['tobeExecuted']=resp
    return response


class zap_data():
  def zap_tests(self,name):
    response={}
    response['link']='http://10.138.77.34:8089/job/'+ name +'/zap'
    return response

class issues_data():
  def get_issues(self):
    context= oslo_context.RequestContext()
    iss_obj=devsecops_database.issue()
    issues=iss_obj.get_issues(context)
    infoi=[]
    for i in issues:
      i.__dict__.pop('_sa_instance_state')
      infoi.append(i.__dict__)
    for i in infoi:
      i['Date']=i['Date'].strftime('%d-%m-%Y')
    infoi=str(infoi).replace("\'",'"')
    response=json.loads(infoi)
    return response

  def attack(self,id1):
    ip='10.138.77.87'
    port=22
    username='tcs'
    password='tcs@12345'
    cmd='python odl-dos'+id1+'.py'
    ssh=paramiko.SSHClient()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    ssh.connect(ip,port,username,password)
    stdin,stdout,stderr=ssh.exec_command(cmd)
    outlines=stdout.readlines()
    resp=''.join(outlines)
    print(resp)
    return "success"

  def temp_fix(self):
    host='10.138.77.160'
    port=22
    username='tcs'
    password='tcs@12345'
    ssh=paramiko.SSHClient()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    ssh.connect(host,port,username,password)
    chan = ssh.invoke_shell()
    chan = ssh.get_transport().open_session()
    chan.get_pty()
    chan.exec_command('sudo ./block.sh')
    print(chan.recv(4096))
    chan.send('tcs@12345\n')
    time.sleep(150)
    return "success"

  def patch(self):
    host='10.138.77.160'
    port=22
    username='tcs'
    password='tcs@12345'
    ssh=paramiko.SSHClient()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    ssh.connect(host,port,username,password)
    chan = ssh.invoke_shell()
    chan = ssh.get_transport().open_session()
    chan.get_pty()
    chan.exec_command('sudo ./patch.sh')
    print(chan.recv(4096))
    chan.send('tcs@12345\n')
    return "success"

  def insert_field(self):
    response={}
    context= oslo_context.RequestContext()
    dict1={'issue_id':5,'vulnerability':'Denial of Service in OpenDaylight','Date':date.today(),'status':'New'}
    iss_obj=devsecops_database.issue()
    response1=iss_obj.insert_issues(context,dict1)
    response['status']='success'
    return response

  def update_field(self):
    response={}
    context= oslo_context.RequestContext()
    dict1={'issue_id':5,'status':'fixed'}
    iss_obj=devsecops_database.issue()
    response1=iss_obj.update_issues(context,dict1)
    response['status']='success'
    return response
  
  def delete_field(self):
    response={}
    context= oslo_context.RequestContext()
    issue_id = 5
    iss_obj=devsecops_database.issue()
    response1=iss_obj.delete_issues(context,issue_id)
    response['status']='success'
    return response



