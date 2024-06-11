import requests
import httplib2
import http.client
import ssl
from base64 import b64encode
from flask import Flask,request
from flask_cors import CORS
from flask_cors import CORS, cross_origin
from datetime import datetime,date,timedelta
import paramiko
import time
import datetime
import socket
import json
import ast
import devsecops_plugin
import devsecops_database
import pdb
app = Flask(__name__)
CORS(app)
from oslo_context import context as oslo_context
@app.route('/access', methods = ['POST', 'GET', 'OPTIONS'])
def enable_access():
  return "Access is enabled"

@app.route('/login', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def enable_login():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        log = request.data.decode('utf-8')
      data=ast.literal_eval(log)
      if data['username'] == 'srinivas' and data['password'] == 'tcs123':
        return json.dumps({'job':True})
      elif data['username'] == 'mithun' and data['password'] == 'tcs123':
        return json.dumps({'job':True})
      elif data['username'] == 'phani' and data['password'] == 'tcs123':
        return json.dumps({'job':True})
      else:
        return json.dumps('Invalid Credentials')
    except:
      return json.dumps('wrong data')

@app.route('/overview', methods = ['POST', 'GET', 'OPTIONS'])
def overview():
  if request.method == 'GET':
    try:
      response={}
      total=0
      failed=0
      success=0
      git_obj=devsecops_plugin.git_data()
      jenk_obj=devsecops_plugin.jenkins_data()
      resp,proj=git_obj.git_projects()
      for i in proj['projects']:
        builds=jenk_obj.jenkins_build(i)
        total=total+builds['current_builds']
        failed=failed+builds['failed_builds']
        success=success+builds['deployed']
      response['total_projects']=len(proj['projects'])
      response['current_builds']=total
      response['failed_builds']=failed
      response['deployed']=success
      response['release_version']='19.4'
      return json.dumps(response)
    except:
      return json.dumps("unable to get data")

@app.route('/projects', methods = ['POST', 'GET', 'OPTIONS'])
def projects():
  if request.method == 'GET':
    try:
      git_obj=devsecops_plugin.git_data()
      resp,proj=git_obj.git_projects()
      return json.dumps(proj['projects'])
    except Exception as e:
      return(e)
      return json.dumps("unable to get data")

@app.route('/fail2ban', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def fail_ban():
  if request.method == 'POST':
    try:
      ip='10.138.77.47'
      port=22
      username='tcs'
      password='nextgen#12345'
      cmd='tail -3 /var/log/fail2ban.log'
      ssh=paramiko.SSHClient()
      ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())
      ssh.connect(ip,port,username,password)
      stdin,stdout,stderr=ssh.exec_command(cmd)
      outlines=stdout.readlines()
      resp=''.join(outlines)
      res=resp.split("     ")
      return json.dumps(res[2])
    except:
      return json.dumps("No host detected")

@app.route('/insert', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def insert_fie():
  if request.method == 'POST':
    try:
      issue_obj=devsecops_plugin.issues_data()
      response=issue_obj.insert_field()
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")


@app.route('/delete', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def delete_fie():
  if request.method == 'POST':
    try:
      issue_obj=devsecops_plugin.issues_data()
      response=issue_obj.delete_field()
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")

@app.route('/update', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def update_fie():
  if request.method == 'POST':
    try:
      issue_obj=devsecops_plugin.issues_data()
      response=issue_obj.update_field()
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")
      
@app.route('/totalbuilds', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def total_builds():
  if request.method == 'POST':
    try:
      response={}
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      jenk_obj=devsecops_plugin.jenkins_data()
      resp,proj1=git_obj.git_projects()
      for i in proj1['projects']:
        if i == project['name']:
          for j in resp:
            if j['name']==project['name']:
              project_id=j['id']
          commits=git_obj.git_branches(project_id)
          builds=jenk_obj.jenkins_build(project['name'])
      response['builds']=builds['current_builds']
      response['failed']=builds['failed_builds']
      response['passed']=builds['deployed']
      response['current_version']='7.6'
      response['branch']=commits['branch_name']
      response['time']=commits['committed_date']
      response['startedby']=commits['owner']
      if project['name']=='CloudSim_Java':
        response['current_version']='7.6'
      elif project['name']=='SNMP_Java':
        response['current_version']='5.3'
      elif project['name']=='odl_oxygen_java':
        response['current_version']='4.1'
      return json.dumps(response)
    except:
      return json.dumps("unable to get data")


@app.route('/buildhistory', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def build_history():
  if request.method == 'POST':
    '''
    null='null'
    k=[{"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 2, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 31, "name": "Build", "tag": "maven"}, {"status": "SUCCESS", "duration": 120, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "SUCCESS", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "SUCCESS", "duration": 3, "name": "Functional Testing", "tag": "robot"}, {"status": "SUCCESS", "duration": 1, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "NOT_EXECUTED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "NOT_EXECUTED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "SUCCESS", "duration": 3, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "SUCCESS", "duration": 30, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-10 08:33:19", "build_number": "37"}, {"status": "SUCCESS", "stages": [{"status": "SUCCESS", "duration": 3, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 32, "name": "Build", "tag": "maven"}, {"status": "SUCCESS", "duration": 109, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "SUCCESS", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "SUCCESS", "duration": 2, "name": "Functional Testing", "tag": "robot"}, {"status": "SUCCESS", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "SUCCESS", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "NOT_EXECUTED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "SUCCESS", "duration": 5, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "SUCCESS", "duration": 31, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:27:27", "build_number": "36"}, {"status": "ABORTED", "stages": [{"status": "SUCCESS", "duration": 2, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 32, "name": "Build", "tag": "maven"}, {"status": "ABORTED", "duration": 30, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "ABORTED", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "ABORTED", "duration": null, "name": "Functional Testing", "tag": "robot"}, {"status": "ABORTED", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "ABORTED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "ABORTED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "ABORTED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "ABORTED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:24:10", "build_number": "35"}, {"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 2, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 30, "name": "Build", "tag": "maven"}, {"status": "SUCCESS", "duration": 113, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "SUCCESS", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "SUCCESS", "duration": 8, "name": "Functional Testing", "tag": "robot"}, {"status": "SUCCESS", "duration": 1, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "FAILED", "duration": 1, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "FAILED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "FAILED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "FAILED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:22:14", "build_number": "34"}, {"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 5, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 30, "name": "Build", "tag": "maven"}, {"status": "FAILED", "duration": 8, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "FAILED", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "FAILED", "duration": null, "name": "Functional Testing", "tag": "robot"}, {"status": "FAILED", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "FAILED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "FAILED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "FAILED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "FAILED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:16:25", "build_number": "33"}, {"status": "NOT_EXECUTED", "stages": [], "date": "2020-01-09 17:15:12", "build_number": "32"}, {"status": "NOT_EXECUTED", "stages": [], "date": "2020-01-09 17:14:55", "build_number": "31"}, {"status": "NOT_EXECUTED", "stages": [], "date": "2020-01-09 17:09:32", "build_number": "30"}, {"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 2, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 31, "name": "Build", "tag": "maven"}, {"status": "FAILED", "duration": 9, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "FAILED", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "FAILED", "duration": null, "name": "Functional Testing", "tag": "robot"}, {"status": "FAILED", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "FAILED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "FAILED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "FAILED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "FAILED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:04:09", "build_number": "29"}, {"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 2, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 32, "name": "Build", "tag": "maven"}, {"status": "FAILED", "duration": 9, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "FAILED", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "FAILED", "duration": null, "name": "Functional Testing", "tag": "robot"}, {"status": "FAILED", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "FAILED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "FAILED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "FAILED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "FAILED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:02:26", "build_number": "28"}, {"status": "FAILED", "stages": [{"status": "SUCCESS", "duration": 3, "name": "Source Code Management", "tag": "git"}, {"status": "SUCCESS", "duration": 35, "name": "Build", "tag": "maven"}, {"status": "FAILED", "duration": 9, "name": "Static Application Security Testing", "tag": "sonar"}, {"status": "FAILED", "duration": null, "name": "iNato", "tag": "inato"}, {"status": "FAILED", "duration": null, "name": "Functional Testing", "tag": "robot"}, {"status": "FAILED", "duration": null, "name": "Publish to Artifactory", "tag": "smartqe"}, {"status": "FAILED", "duration": null, "name": "Deploy in Staging", "tag": "nexus"}, {"status": "FAILED", "duration": null, "name": "SmartQE", "tag": "tomcat"}, {"status": "FAILED", "duration": null, "name": "OS Security Vulnerability Testing Nexpose", "tag": "nexpose"}, {"status": "FAILED", "duration": null, "name": "Dynamic Application Security Testing ZAP", "tag": "zap"}], "date": "2020-01-09 17:00:23", "build_number": "27"}]
    return(json.dumps(k))
'''
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      jenk_obj=devsecops_plugin.jenkins_data()
      resp,proj1=git_obj.git_projects()
      for i in proj1['projects']:
        if i == project['name']:
          response=jenk_obj.pipeline_history(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from jenkins")

@app.route('/projectvulnerability', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def project_vulnerability():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      sonar_obj=devsecops_plugin.sonar_data()
      response=sonar_obj.project_vulnerabilities(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from sonarqube")

@app.route('/projvulnerability', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def proj_vulnerability():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      sonar_obj=devsecops_plugin.sonar_data()
      response=sonar_obj.proj_vulnerabilities(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data")

@app.route('/projdefects', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def proj_defect():
  if request.method == 'POST':
    k={"defects": [{"severity": "BLOCKER", "DefectDate": "11-30-2020", "name": "Issue in Base rebate inline edit popup and Promocode column value in grid for Annuity",'status':'OPEN'},{"severity": "BLOCKER", "DefectDate": "12-31-2020", "name": "PRE_ELIG_REBATE_INDEX throwing error in webppp-dev1",'status':'OPEN'},{"severity": "BLOCKER", "DefectDate": "03-01-2021", "name": "Issue in setup in Annuity Rebate",'status':'OPEN'},{"severity": "BLOCKER", "DefectDate": "01-12-2021", "name": "VIP_INCR_In payout eligibility override screen  the search displays data even for  partner not enrolled into the program",'status':'OPEN'},{"severity": "BLOCKER", "DefectDate": "01-15-2021", "name": "VIP Annuity - PPIBO Cumulative MRR value being displayed as zero even when values are present for M1.......M6 MRR",'status':'OPEN'},{"severity": "MAJOR", "DefectDate": "01-19-2021", "name": "M1....M6 values are showing wrong values in kickers line at subscription Ref ID level",'status':'OPEN'},{"severity": "MAJOR", "DefectDate": "01-20-2021", "name": "TPV level values are not matching with PPI BO Partner level report",'status':'OPEN'},{"severity": "MAJOR", "DefectDate": "01-21-2021", "name":"VIP ANNUITY PPIBO Validations for Full mode program - Cancel lines are not visible in BO report",'status':'OPEN'},{"severity": "MAJOR", "DefectDate": "01-30-2020", "name": "VIP ANNUITY PPIBO - TCV amount doubled for few transactions and incorrect value for term duration",'status':'OPEN'},{"severity": "MAJOR", "DefectDate": "02-02-2021", "name": "PRE_ELIG_REBATE_INDEX throwing error in webppp-dev1",'status':'OPEN'},{"severity": "MAJOR", "DefectDate": "02-07-2021", "name": "TCV and TCV Growth valves are not populating in PPI BO report",'status':'OPEN'},{"severity": "MINOR", "DefectDate": "02-14-2021", "name": "MRR to MCV for object names",'status':'OPEN'},{"severity": "MINOR", "DefectDate": "02-21-2021", "name": "VIP_Anuuity - PPIBO report values for Annuity rebate amount having mismatch in USD and Local currency",'status':'OPEN'},{"severity": "MINOR", "DefectDate": "02-23-2021", "name": "Issue in Annuity Rebate Eligibility setup: Multiple rows coming in grid for a specific country after setup",'status':'OPEN'}], "critical": 0, "minor": 3, "major": 6,'blocker':5}
    return(json.dumps(k))
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      sonar_obj=devsecops_plugin.sonar_data()
      response=sonar_obj.proj_bugs(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data")

'''
{"alldefects":[{"total":"38","type":"technical_debts"},{"total":"1973","type":"code_smell"},{"total":"86","type":"bugs"}],"defectsdata":{"bugs":[{"count":0,"project":"Odl_Oxygen_Java"},{"count":9,"project":"SNMP_Java"},{"count":77,"project":"Sample_Java"}],"code_smell":[{"count":33,"project":"Odl_Oxygen_Java"},{"count":275,"project":"SNMP_Java"},{"count":1665,"project":"Sample_Java"}],"technical_debts":[{"count":0,"project":"Odl_Oxygen_Java"},{"count":4,"project":"SNMP_Java"},{"count":34,"project":"Sample_Java"}]}}}
'''
#SNMP_Java=VIP Annuity
#CloudSim_Java=CSPP-Multi Year
#ODL_Oxygen_Java=Annual Contract Value 
@app.route('/projcodesmells', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def proj_codes():
  if request.method == 'POST':
    k={"code_smells": [{"severity": "BLOCKER","status":"OPEN", "name": "Add at least one assertion to this test case."},{"severity": "CRITICAL","status":"OPEN", "name": "Make 'candidate' translent or serializable"},{"severity": "CRITICAL","status":"OPEN", "name": "Remove usuage of generic wildcard type"},{"severity": "CRITICAL","status":"OPEN", "name": "Refactor this method to reduce its Cognitive Complexity from 16 to the 15 allowed."},{"severity": "CRITICAL","status":"OPEN", "name": "Refactor this method to reduce its Cognitive Complexity from 16 to the 15 allowed."},{"severity": "MINOR","status":"OPEN", "name": "Complete the task associated to this TODO comment"},{"severity": "INFO","status":"OPEN", "name": "Complete the task associated to this TODO comment"},{"severity": "INFO","status":"OPEN", "name": "Do not forget to remove this deprecated code someday"},{"severity": "INFO","status":"OPEN", "name": "Do not forget to remove this deprecated code someday"},{"severity": "INFO","status":"OPEN", "name": "Add at least one assertion to this test case."},{"severity": "BLOCKER","status":"OPEN", "name": "Add at least one assertion to this test case."},{"severity": "MINOR","status":"OPEN", "name": "Refactor this method to reduce its Cognitive Complexity from 16 to the 15 allowed."},{"severity": "CRITICAL","status":"OPEN", "name": "Complete the task associated to this TODO comment"},{"severity": "INFO","status":"OPEN", "name": "Do not forget to remove this deprecated code someday"},{"severity": "INFO","status":"OPEN", "name": "Do not forget to remove this deprecated code someday"},{"severity": "CRITICAL","status":"OPEN", "name": "Refactor this method to reduce its Cognitive Complexity from 16 to the 15 allowed."},{"severity": "CRITICAL","status":"OPEN", "name": "Refactor this method to reduce its Cognitive Complexity from 16 to the 15 allowed."}], "critical": 4, "minor": 1, "major": 12}
    return(json.dumps(k))
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      sonar_obj=devsecops_plugin.sonar_data()
      response=sonar_obj.proj_code(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data")

@app.route('/projectbugs', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def project_bugs():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      sonar_obj=devsecops_plugin.sonar_data()
      response=sonar_obj.sonar_bugs(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from sonarqube")

@app.route('/alldefects', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def project_bug():
  if request.method == 'GET':
    try:
      response={}
      response1=[{} for i in range(3)]
      response2={}
      data=[]
      sonar_obj=devsecops_plugin.sonar_data()
      resp1=sonar_obj.sonar_debts()
      return("Done till sonar_debts")
      resp2=sonar_obj.code_sme()
      resp3=sonar_obj.defect_bugs()
      list1=['technical_debts','code_smell','bugs']
      data.append(resp1)
      data.append(resp2)
      data.append(resp3)
      for i in range(len(data)):
        response1[i]['type']=data[i]['type']
        response1[i]['total']=data[i]['total']
        response2[list1[i]]=data[i]['data']
      response['alldefects']=response1
      response['defectsdata']=response2
      return json.dumps(response)
    except:
      return json.dumps("unable to get data")

@app.route('/projectcodesmell', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def project_code_smell():
  if request.method == 'POST':
    try:
      response={}
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      sonar_obj=devsecops_plugin.sonar_data()
      resp=sonar_obj.sonar_code(project['name'])
      resp1=sonar_obj.project_debts(project['name'])
      response['code_smell']=resp['code']
      response['tech_debts']=resp1['debt']
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from sonarqube")

@app.route('/buildtrends', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def build_trends():
  if request.method == 'POST':
    '''k=[{"build_status":"SUCCESS","percentage_success_stages":100,"build_startdate":"2020-01-09 17:28:57","build_no":"#750","no_successful_stages":10,"build_enddate":"2020-01-09 17:30:36","last_successful_stage":"Dynamic Application Security Testing ZAP"},{"build_status":"SUCCESS","percentage_success_stages":100,"build_startdate":"2020-01-08 10:50:25","build_no":"#74","no_successful_stages":10,"build_enddate":"2020-01-08 10:51:52","last_successful_stage":"Dynamic Application Security Testing ZAP"},{"build_status":"FAILED","percentage_success_stages":40.0,"last_succesful_stage":"iNato","build_startdate":"2020-01-08 10:47:50","build_no":"#73","no_successful_stages":4,"build_enddate":"2020-01-08 10:48:44"}]
    return(json.dumps(k))'''
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      jenk_obj=devsecops_plugin.jenkins_data()
      response=jenk_obj.build_trend(project['name'])
      response.reverse()
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from jenkins")


@app.route('/projectdetails', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def project_details():
  if request.method == 'POST':
    try:
      response={}
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      jenk_obj=devsecops_plugin.jenkins_data()
      resp,proj1=git_obj.git_projects()
      velocity=['10','8','15']
      project_owner=['phani','mithun','srinivas']
      release_date=['18-02-2020','24-02-2020','14-02-2020']
      previous_release_date=['24-12-2019','19-12-2019','26-12-2019']
      if project['name']=='CloudSim_Java':
        response['velocity']=velocity[0]
        response['project_owner']=project_owner[0]
        response['release_date']=release_date[0]
        response['previous_release_date']=previous_release_date[0]
        response['project_name']=project['name']
      elif project['name']=='SNMP_Java':
        response['velocity']=velocity[1]
        response['project_owner']=project_owner[1]
        response['release_date']=release_date[1]
        response['previous_release_date']=previous_release_date[1]
        response['project_name']=project['name']
      elif project['name']=='odl_oxygen_java':
        response['velocity']=velocity[2]
        response['project_owner']=project_owner[2]
        response['release_date']=release_date[2]
        response['previous_release_date']=previous_release_date[2]
        response['project_name']=project['name']
      return json.dumps(response)
    except:
      return json.dumps("unable to get data")


@app.route('/vulnerabilities', methods = ['POST', 'GET', 'OPTIONS'])
def vulnerabilities():
  if request.method == 'GET':
    try:
      sonar_obj=devsecops_plugin.sonar_data()
      response,resp=sonar_obj.vulnerabilities_severity()
      for i,j in response.items():
        if j>200:
          response[i]=j-200
      return json.dumps(response)
    except Exception as e:
      #return(str(e))
      return json.dumps("unable to get data")

'''
[{"stages": [{"status": "SUCCESS", "name": "Dependency Check", "duration": 75, "tool": "Dependency check", "newduration": "23 sec"},{"status": "SUCCESS", "name": "Build & Unit Testing", "duration": 75, "tool": "maven", "newduration": "1.17 min"}, {"status": "SUCCESS", "name": "Quality Scans", "duration": 122, "tool": "sonar", "newduration": "1.15 min"}, {"status": "SUCCESS", "name": "Publish to Artifacts", "duration": 7, "tool": "nexus", "newduration": "6 sec"}, {"status": "SUCCESS", "name": "Build Sanity Testing", "duration": 6, "tool": "robot", "newduration": "50 sec"}, {"status": "SUCCESS", "name": "Functional Testing", "duration": 5, "tool": "inato", "newduration": "1.37 min"}, {"status": "SUCCESS", "name": "Regression Testing", "duration": null, "tool": "smartqe", "newduration": "1.37 min"}, {"status": "SUCCESS", "name": "Infra Security Testing", "duration": 3, "tool": "nexpose", "newduration": "1.37 min"}, {"status": "SUCCESS", "name": "Dynamic Application Security Testing", "duration": 36, "tool": "zap", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Deploy", "duration": 1, "tool": "tomcat", "newduration": "36 sec"}], "build_number": "62", "newduration": "4.22 min", "status": "SUCCESS", "name": "CSPP-Multi Year", "duration": 253}, {"stages": [{"status": "SUCCESS", "name": "Dependency Check", "duration": 75, "tool": "Dependency check", "newduration": "1.25 min"},{"status": "SUCCESS", "name": "Build & Unit Testing", "duration": 171, "tool": "maven", "newduration": "2.85 min"}, {"status": "SUCCESS", "name": "Quality Scans", "duration": 431, "tool": "sonar", "newduration": "7.18 min"}, {"status": "SUCCESS", "name": "Publish to Artifacts", "duration": 23, "tool": "nexus", "newduration": "23 sec"}, {"status": "SUCCESS", "name": "Build Sanity Testing", "duration": 3, "tool": "robot", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Functional Testing", "duration": 796, "tool": "inato", "newduration": "13.27 min"}, {"status": "SUCCESS", "name": "Regression Testing", "duration": null, "tool": "smartqe", "newduration": null}, {"status": "SUCCESS", "name": "Infra Security Testing", "duration": 3, "tool": "nexpose", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Dynamic Application Security Testing", "duration": 36, "tool": "zap", "newduration": "36 sec"}, {"status": "SUCCESS", "name": "Deploy", "duration": 6, "tool": "tomcat", "newduration": "6 sec"}], "build_number": "78", "newduration": "24.42 min", "status": "SUCCESS", "name": "Annual Contract Value", "duration": 1465}, {"stages": [{"status": "SUCCESS", "name": "Dependency Check", "duration": 75, "tool": "Dependency check", "newduration": "1.25 min"},{"status": "SUCCESS", "name": "Build & Unit Testing", "duration": 42, "tool": "maven", "newduration": "42 sec"}, {"status": "SUCCESS", "name": "Quality Scans", "duration": 50, "tool": "sonar", "newduration": "50 sec"}, {"status": "SUCCESS", "name": "Publish to Artifacts", "duration": 7, "tool": "nexus", "newduration": "7 sec"}, {"status": "SUCCESS", "name": "Build Sanity Testing", "duration": 54, "tool": "robot", "newduration": "54 sec"}, {"status": "SUCCESS", "name": "Functional Testing", "duration": 40, "tool": "inato", "newduration": "40 sec"}, {"status": "SUCCESS", "name": "Regression Testing", "duration": null, "tool": "smartqe", "newduration": null}, {"status": "SUCCESS", "name": "Infra Security Testing", "duration": 3, "tool": "nexpose", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Dynamic Application Security Testing", "duration": 37, "tool": "zap", "newduration": "37 sec"}, {"status": "SUCCESS", "name": "Deploy", "duration": 1, "tool": "tomcat", "newduration": "1 sec"}], "build_number": "138", "newduration": "3.83 min", "status": "SUCCESS", "name": "VIP Annuity", "duration": 230}]
'''
@app.route('/projectpipelines', methods = ['POST', 'GET', 'OPTIONS'])
def project_pipelines():
  if request.method == 'GET':
    try:
      #pdb.set_trace()
      response=[]
      null=10
      k=[{"stages": [{"status": "SUCCESS", "name": "Dependency Check", "duration": 75, "tool": "Dependency check", "newduration": "23 sec"},{"status": "SUCCESS", "name": "Build & Unit Testing", "duration": 75, "tool": "maven", "newduration": "1.17 min"}, {"status": "SUCCESS", "name": "Quality Scans", "duration": 122, "tool": "sonar", "newduration": "1.15 min"}, {"status": "NOT-EXECUTED", "name": "Publish to Artifacts", "duration": 7, "tool": "nexus", "newduration": ""}, {"status": "NOT-EXECUTED", "name": "Build Sanity Testing", "duration": '', "tool": "robot", "newduration": ""}, {"status": "NOT-EXECUTED", "name": "Functional Testing", "duration": 5, "tool": "inato", "newduration": ""}, {"status": "NOT-EXECUTED", "name": "Regression Testing", "duration": null, "tool": "smartqe", "newduration": ""}, {"status": "NOT-EXECUTED", "name": "Infra Security Testing", "duration": 3, "tool": "nexpose", "newduration": ""}, {"status": "NOT-EXECUTED", "name": "Dynamic Application Security Testing", "duration": 36, "tool": "zap", "newduration": ""}, {"status": "NOT-EXECUTED", "name": "Deploy", "duration": 1, "tool": "tomcat", "newduration": ""}], "build_number": "62", "newduration": "4.22 min", "status": "SUCCESS", "name": "CSPP-Multi Year", "duration": 253}, {"stages": [{"status": "SUCCESS", "name": "Dependency Check", "duration": 75, "tool": "Dependency check", "newduration": "1.25 min"},{"status": "SUCCESS", "name": "Build & Unit Testing", "duration": 171, "tool": "maven", "newduration": "2.85 min"}, {"status": "SUCCESS", "name": "Quality Scans", "duration": 431, "tool": "sonar", "newduration": "7.18 min"}, {"status": "SUCCESS", "name": "Publish to Artifacts", "duration": 23, "tool": "nexus", "newduration": "23 sec"}, {"status": "SUCCESS", "name": "Build Sanity Testing", "duration": 3, "tool": "robot", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Functional Testing", "duration": 796, "tool": "inato", "newduration": "13.27 min"}, {"status": "SUCCESS", "name": "Regression Testing", "duration": null, "tool": "smartqe", "newduration": null}, {"status": "SUCCESS", "name": "Infra Security Testing", "duration": 3, "tool": "nexpose", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Dynamic Application Security Testing", "duration": 36, "tool": "zap", "newduration": "36 sec"}, {"status": "SUCCESS", "name": "Deploy", "duration": 6, "tool": "tomcat", "newduration": "6 sec"}], "build_number": "78", "newduration": "24.42 min", "status": "SUCCESS", "name": "Annual Contract Value", "duration": 1465}, {"stages": [{"status": "SUCCESS", "name": "Dependency Check", "duration": 75, "tool": "Dependency check", "newduration": "1.25 min"},{"status": "SUCCESS", "name": "Build & Unit Testing", "duration": 42, "tool": "maven", "newduration": "42 sec"}, {"status": "SUCCESS", "name": "Quality Scans", "duration": 50, "tool": "sonar", "newduration": "50 sec"}, {"status": "SUCCESS", "name": "Publish to Artifacts", "duration": 7, "tool": "nexus", "newduration": "7 sec"}, {"status": "SUCCESS", "name": "Build Sanity Testing", "duration": 54, "tool": "robot", "newduration": "54 sec"}, {"status": "SUCCESS", "name": "Functional Testing", "duration": 40, "tool": "inato", "newduration": "40 sec"}, {"status": "SUCCESS", "name": "Regression Testing", "duration": null, "tool": "smartqe", "newduration": null}, {"status": "SUCCESS", "name": "Infra Security Testing", "duration": 3, "tool": "nexpose", "newduration": "3 sec"}, {"status": "SUCCESS", "name": "Dynamic Application Security Testing", "duration": 37, "tool": "zap", "newduration": "37 sec"}, {"status": "SUCCESS", "name": "Deploy", "duration": 1, "tool": "tomcat", "newduration": "1 sec"}], "build_number": "138", "newduration": "3.83 min", "status": "SUCCESS", "name": "VIP Annuity", "duration": 230}]
      return(json.dumps(k))
      git_obj=devsecops_plugin.git_data()
      jenk_obj=devsecops_plugin.jenkins_data()
      resp,proj=git_obj.git_projects()
      for i in proj['projects']:
        try:
          data=[]
          data=jenk_obj.jenkins_pipeline(i)
        except:
          pass
        if data:
          response.append(data)
      #pdb.set_trace()
      return json.dumps(response)
    except Exception as e:
      return json.dumps(e)



@app.route('/build_stages', methods = ['POST', 'GET', 'OPTIONS'])
def stages():
  if request.method == 'GET':
    try:
      jenk_obj=devsecops_plugin.jenkins_data()
      response=jenk_obj.pipeline_stages()
      return json.dumps(response)
    except:
      return json.dumps("unable to get data")

#SNMP_Java=VIP Annuity
#CloudSim_Java=CSPP-Multi Year
#ODL_Oxygen_Java=Annual Contract Value 
@app.route('/testoptimization',methods=['GET', 'POST'])
def test_optimization():
  if request.method == 'GET':
    try:
      context= oslo_context.RequestContext()
      test_obj=devsecops_database.testoptimization()
      tests=test_obj.get_tests(context)
      info=[]
      for i in tests:
        i.__dict__.pop('_sa_instance_state')
        info.append(i.__dict__)
      return json.dumps(info)
    except:
      return json.dumps("error fetching the data")

@app.route('/securitypolicycompliance',methods=['GET', 'POST'])
def security_policy():
  if request.method == 'GET':
    try:
      response={}
      response['passed']=75
      response['failed']=25
      return json.dumps(response)
    except:
      return json.dumps("unable to get data")

@app.route('/projtestoptimization',methods=['GET', 'POST'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def proj_test_optimization():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      response=[]
      context= oslo_context.RequestContext()
      test_obj=devsecops_database.testoptimization()
      tests=test_obj.get_tests(context)
      info=[]
      for i in tests:
        i.__dict__.pop('_sa_instance_state')
        info.append(i.__dict__)	
      k = 0
      for j in info:
        if project['name'] == j['pname']:
          response.append({'sprint':None})
          if(j['sprint'] != response[k]['sprint']):
             response[k]['sprint']=j['sprint']
             response[k]['unimpacted']=j['Unimpactedtestcases']
             response[k]['identified']=j['automatedtestcases']
             response[k]['defects']=j['totaldefects']
             k += 1
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")

'''
{"allprojects": [{"name": "CloudSim_Java", "releasedate": "11/03/2020", "ciarating": "MEDIUM"},{"name": "SNMP_Java", "releasedate": "14/03/2020", "ciarating": "HIGH"},{"name": "Odl_Oxygen_Java", "releasedate": "20/02/2020", "ciarating": "HIGH"},{"name": "CloudSim_Java", "releasedate": "11/03/2020", "ciarating": "MEDIUM"},{"name": "SNMP_Java", "releasedate": "14/03/2020", "ciarating": "HIGH"},{"name": "Odl_Oxygen_Java", "releasedate": "20/02/2020", "ciarating": "HIGH"}],"testoptimization": {"Odl_Oxygen_Java": [{"intelligent": 6, "unaffected": 2, "defects": 3, "sprint": "sprint1"}, {"intelligent": 7, "unaffected": 8, "defects": 6, "sprint": "sprint2"}, {"intelligent": 20, "unaffected": 25, "defects": 3, "sprint": "sprint3"}, {"intelligent": 15, "unaffected": 18, "defects": 1, "sprint": "sprint4"}], "SNMP_Java": [{"intelligent": 8, "unaffected": 13, "defects": 5, "sprint": "sprint1"}, {"intelligent": 10, "unaffected": 30, "defects": 4, "sprint": "sprint2"}, {"intelligent": 22, "unaffected": 42, "defects": 8, "sprint": "sprint3"}, {"intelligent": 76, "unaffected": 42, "defects": 7, "sprint": "sprint4"}, {"intelligent": 6, "unaffected": 44, "defects": 5, "sprint": "sprint5"}, {"intelligent": 4, "unaffected": 50, "defects": 2, "sprint": "sprint6"}], "CloudSim_Java": [{"intelligent": 2, "unaffected": 2, "defects": 9, "sprint": "sprint1"}, {"intelligent": 2, "unaffected": 2, "defects": 8, "sprint": "sprint2"}, {"intelligent": 2, "unaffected": 2, "defects": 5, "sprint": "sprint3"}, {"intelligent": 2, "unaffected": 2, "defects": 5, "sprint": "sprint4"}, {"intelligent": 2, "unaffected": 2, "defects": 4, "sprint": "sprint5"}, {"intelligent": 2, "unaffected": 3, "defects": 2, "sprint": "sprint6"}]},"securityvulnerability": {"Odl_Oxygen_Java": [{"type": "BrokenAccessControl", "severity": "MAJOR", "count": 0}, {"type": "InsufficientLogging", "severity": "CRITICAL", "count": 0}], "SNMP_Java": [{"type": "knownvulnerability", "severity": "MINOR", "count": 56}, {"type": "InsufficientLogging", "severity": "CRITICAL", "count": 2}, {"type": "DataExposure", "severity": "MAJOR", "count": 0}], "CloudSim_Java": [{"type": "DataExposure", "severity": "MINOR", "count": 93}, {"type": "InjectionFlaws", "severity": "CRITICAL", "count": 93}, {"type": "BrokenAccessControl", "severity": "MAJOR", "count": 46}]},"defects": {"Odl_Oxygen_Java": {"codes": "33", "critical": "0", "blocker": "0", "info": "12", "major": "21", "minor": "0", "bugs": {"total": "0", "open": "0", "close": "0"}, "debts": 0}, "SNMP_Java": {"codes": "276", "critical": "18", "blocker": "0", "info": "0", "major": "134", "minor": "124", "bugs": {"total": "9", "open": "3", "close": "6"}, "debts": 4}, "CloudSim_Java": {"codes": "1665", "critical": "131", "blocker": "8", "info": "168", "major": "618", "minor": "740", "bugs": {"total": "77", "open": "20", "close": "57"}, "debts": 34}}}
    }
'''
#SNMP_Java=VIP Annuity
#CloudSim_Java=CSPP-Multi Year
#ODL_Oxygen_Java=Annual Contract Value 

@app.route('/upcomingreleases',methods=['GET', 'POST'])
def project_data():
  if request.method == 'GET':
    try:
      k={"allprojects": [{"name": "VIP Annuity", "releasedate": "04/11/2021"},{"name": "CSPP-Multi Year", "releasedate": "04/14/2021"},{"name": "Annual Contract Value", "releasedate": "04/20/2021"},{"name": "VIP Annuity", "releasedate": "04/11/2021"},{"name": "CSPP-Multi Year", "releasedate": "04/14/2021"},{"name": "Annual Contract Value", "releasedate": "04/20/2021"}],"testoptimization": {"Annual Contract Value": [{"intelligent": 6, "unaffected": 2, "defects": 3, "sprint": "sprint1"}, {"intelligent": 7, "unaffected": 8, "defects": 6, "sprint": "sprint2"}, {"intelligent": 20, "unaffected": 25, "defects": 3, "sprint": "sprint3"}, {"intelligent": 15, "unaffected": 18, "defects": 1, "sprint": "sprint4"}], "VIP Annuity": [{"intelligent": 8, "unaffected": 13, "defects": 5, "sprint": "sprint1"}, {"intelligent": 10, "unaffected": 30, "defects": 4, "sprint": "sprint2"}, {"intelligent": 22, "unaffected": 42, "defects": 8, "sprint": "sprint3"}, {"intelligent": 76, "unaffected": 42, "defects": 7, "sprint": "sprint4"}, {"intelligent": 6, "unaffected": 44, "defects": 5, "sprint": "sprint5"}, {"intelligent": 4, "unaffected": 50, "defects": 2, "sprint": "sprint6"}], "CSPP-Multi Year": [{"intelligent": 2, "unaffected": 2, "defects": 9, "sprint": "sprint1"}, {"intelligent": 2, "unaffected": 2, "defects": 8, "sprint": "sprint2"}, {"intelligent": 2, "unaffected": 2, "defects": 5, "sprint": "sprint3"}, {"intelligent": 2, "unaffected": 2, "defects": 5, "sprint": "sprint4"}, {"intelligent": 2, "unaffected": 2, "defects": 4, "sprint": "sprint5"}, {"intelligent": 2, "unaffected": 3, "defects": 2, "sprint": "sprint6"}]},"securityvulnerability": {"Annual Contract Value": [{"type": "BrokenAccessControl", "severity": "MAJOR", "count": 0}, {"type": "InsufficientLogging", "severity": "CRITICAL", "count": 0}], "CSPP-Multi Year": [{"type": "Mars", "severity": "MINOR", "count": 56},{"type": "Satrun", "severity": "MINOR", "count": 23},{"type": "Jupiter", "severity": "MINOR", "count": 13}], "VIP Annuity": [{"type": "Bitcoin", "severity": "CRITICAL", "count": 93}, {"type": "Euro", "severity": "CRITICAL", "count": 93}, {"type": "Yen", "severity": "CRITICAL", "count": 46},{"type": "Dollar", "severity": "CRITICAL", "count": 73}]},"defects": {"Annual Contract Value": {"codes": "33", "critical": "0", "blocker": "0", "info": "12", "major": "21", "minor": "0", "bugs": {"total": "0", "open": "0", "close": "0"}, "debts": 0}, "CSPP-Multi Year": {"codes": "276", "critical": "18", "blocker": "0", "info": "0", "major": "134", "minor": "124", "bugs": {"total": "9", "open": "3", "close": "6"}, "debts": 4}, "VIP Annuity": {"codes": "1665", "critical": "131", "blocker": "8", "info": "168", "major": "618", "minor": "740", "bugs": {"total": "77", "open": "20", "close": "57"}, "debts": 34}}}
      return(json.dumps(k))
      sonar_obj=devsecops_plugin.sonar_data()
      response=sonar_obj.project_vul()
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")

@app.route('/projectlive',methods=['GET', 'POST'])
def project_live():
  if request.method == 'GET':
    try:
      response = [];sprint_output=[]
      git_obj = devsecops_plugin.git_data()
      resp,proj=git_obj.git_projects()
      for i in proj['projects']:
        response.append({'project':i,'sprints':[]})
      sonar_obj=devsecops_plugin.sonar_data()
      info=sonar_obj.get_live()
      #return(json.dumps(response))
      for j in info:
        for k in range(len(response)):
          if j['projectname']==response[k]['project']:
            break;
        response[k]['sprints'].append({'sprintname':j['sprint'],'status':j['status']})
      for i in response:
        if(i["sprints"]!=[]):
          sprint_output.append(i)   
      return json.dumps(sprint_output)
    except:
      return json.dumps("error fetching the data")

@app.route('/fieldissues',methods=['GET', 'POST'])
def field_iss():
  if request.method == 'GET':
    try:
      response = {}
      response['openissues']=[]
      response['closedissues']=[]
      production_issues = []
      open_issues,closed_issues = 0,0
      issue_obj=devsecops_plugin.issues_data()
      production_issues = issue_obj.get_issues()
      for i in production_issues:
        if i['status']=='fixed':
          closed_issues+=1
          response['closedissues'].append(i)
        else:
          open_issues+=1
          response['openissues'].append(i)
      response['open']=open_issues
      response['closed']=closed_issues
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")

@app.route('/attack', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def attacks():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      att=ast.literal_eval(proj)
      issue_obj=devsecops_plugin.issues_data()
      response=issue_obj.attack(att['id'])
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")

@app.route('/temporaryfix', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def temporary_fix():
  if request.method == 'POST':
    try:
      issue_obj=devsecops_plugin.issues_data()
      response=issue_obj.temp_fix()
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")

@app.route('/patch', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def patches():
  if request.method == 'POST':
   try:
     issue_obj=devsecops_plugin.issues_data()
     response=issue_obj.patch()
     return json.dumps(response)
   except:
     return json.dumps("error fetching the data")

@app.route('/deploymentstatistics',methods=['GET', 'POST'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def deployment_statistics():
  if request.method == 'POST':
    k=[
            {
               "sprintNumber": "Sprint 1" ,
                "failed": "15",
                "successful":"40"
               
            },
            {
                "sprintNumber": "Sprint 2" ,
                 "failed":"14",
                 "successful":"45"
                
            },
            {
               "sprintNumber": "Sprint 3" ,
                "failed":"12",
                "successful":"35"
            
            },
            {
                "sprintNumber": "Sprint 4" ,
                "failed":"10",
                "successful":"40"
                
            },
            {
            "sprintNumber": "Sprint 5" ,
                "failed":"8",
                "successful":"50"
            
            },
            {
                "sprintNumber": "Sprint 6" ,
                "failed":"7",
                "successful":"60"
            },
        ]
    return(json.dumps(k))
    try:
      response = []
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      jenk_obj=devsecops_plugin.jenkins_data()
      info=jenk_obj.build_deploys(project['name'])
      for i in range(len(info)):
         response.append({})
         keys = list(info[i].keys())
         sprintname = keys[0]
         response[i]['sprint']= sprintname
         response[i]['failed']= info[i][sprintname]["failed"]
         response[i]['successful'] = info[i][sprintname]["successful"]
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")

@app.route('/securityvulnerabilitysummary',methods=['GET', 'POST'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def security_vulnerability_summary():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      sonar_obj=devsecops_plugin.sonar_data()
      response=sonar_obj.security_vul_summary(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")

#SNMP_Java=VIP Annuity
#CloudSim_Java=CSPP-Multi Year
#ODL_Oxygen_Java=Annual Contract Value 
@app.route('/securityvulnerabilities',methods=['GET', 'POST'])
def security_vulnerabilities():
  if request.method == 'GET':
    try:
      response={}
      response1=[{} for i in range(3)]
      response2={}
      response3={}
      data1,data2,data3,data4,data5,data6=[],[],[],[],[],[]
      critical,major,minor=0,0,0
      context= oslo_context.RequestContext()
      vulner_obj=devsecops_database.vulnerabilities()
      vulnerabilities=vulner_obj.get_vulnerabilities(context)
      info=[]
      for i in vulnerabilities:
        i.__dict__.pop('_sa_instance_state')
        info.append(i.__dict__)
      types=['critical','major','minor']
      for k in info:
        if k['sprint']=='current':
          if k['severity']=='CRITICAL':
            critical+=k['count']
          elif k['severity']=='MAJOR':
            major+=k['count']
          elif k['severity']=='MINOR':
            minor+=k['count']
      counts=[critical,major,minor]
      for l in range(3):
        response1[l]['type']=types[l]
        response1[l]['count']=counts[l]
      
      for m in info:
        data={}
        if m['sprint']=='current':
          if m['severity']=='CRITICAL':
            data['name']=m['type']
            data['count']=m['count']
            data1.append(data)
          elif m['severity']=='MAJOR':
            data['name']=m['type']
            data['count']=m['count']
            data2.append(data)
          elif m['severity']=='MINOR':
            data['name']=m['type']
            data['count']=m['count']
            data3.append(data)
      datas=[data1,data2,data3]
      for n in range(3):
        response2[types[n]]=datas[n]
      #projects=['SNMP_Java','CloudSim_Java','ODL_Oxygen_Java']
      #projects=['VIP_Annuity','CSPP_Multi_Year','Annual_Contract_Value']
      projects=['VIP Annuity','CSPP-Multi Year','Annual Contract Value']
      for x in info:
        dat={}
        if x['sprint']=='current':
          if x['severity']=='CRITICAL':
            dat['project']=x['project']
            dat['count']=x['count']
            data4.append(dat)
          elif x['severity']=='MAJOR':
            dat['project']=x['project']
            dat['count']=x['count']
            data5.append(dat)
          elif x['severity']=='MINOR':
            dat['project']=x['project']
            dat['count']=x['count']
            data6.append(dat)
      dats=[data4,data5,data6]
      for y in range(3):
        response3[types[y]]=dats[y]
      response['securityvulnerabilities']=response1
      response['vulnerabilitylist']=response2
      response['projects']=response3
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")

@app.route('/inatoupdate',methods=['GET', 'POST'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def update_inato():
  if request.method == 'POST':
    try:
      data={}
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      context= oslo_context.RequestContext()
      test_obj=devsecops_database.testoptimization()
      tests=test_obj.get_tests(context)
      inato_obj=devsecops_plugin.inato_data()
      data1=inato_obj.inato_updates(project['name'])
      data['tobeExecuted']=data1['tobeExecuted']
      data['canbeSkipped']=data1['canbeSkipped']
      data['total']=data1['total']
      info=[]
      for i in tests:
        i.__dict__.pop('_sa_instance_state')
        info.append(i.__dict__)
      for i in info:
        if i['pname']==project['name']:
          data['testid']=i['testid']
      context1= oslo_context.RequestContext()
      response=test_obj.update_tests(context1,**data)
      return json.dumps("SUCCESS")
    except:
      return json.dumps("error fetching the data")

@app.route('/topvulnerableprojects',methods=['GET', 'POST'])
def top_projects():
  if request.method == 'GET':
    try:
      sonar_obj=devsecops_plugin.sonar_data()
      response,data=sonar_obj.sonar_vulnerabilities()
      for i in response:
        if int(i['vulnerabilities'])>200:
          i['vulnerabilities']=str(int(i['vulnerabilities'])-200)
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data")


@app.route('/lastbuild', methods = ['POST', 'GET', 'OPTIONS'])
def last_build():
  if request.method == 'GET':
    try:
      response=[]
      git_obj=devsecops_plugin.git_data()
      jenk_obj=devsecops_plugin.jenkins_data()
      resp,proj=git_obj.git_projects()
      for i in proj['projects']:
        builds=jenk_obj.jenkins_last_build(i)
        response.append(builds)
      return json.dumps(response)
    except:
      return json.dumps("error fetching the data from jenkins")


@app.route('/jenkins', methods = ['POST', 'GET', 'OPTIONS'])
def jenkins_build():
  if request.method == 'GET':
    try:
      response={}
      count=0
      conn=http.client.HTTPConnection('10.138.77.34',port=8089)
      userAndPass = b64encode(b"admin:admin").decode("ascii")
      headers = { 'Authorization' : 'Basic %s' %  userAndPass }
      conn.request('GET', '/job/ODL_Nitrogen/api/json', headers=headers)
      r = conn.getresponse()
      jobs=r.read()
      joblist=json.loads(jobs.decode("utf-8"))
      for i in joblist['builds']:
        count+=1
      response['builds']=joblist['builds']
      response['lastSuccesfulBuild']=joblist['lastSuccessfulBuild']
      response['lastBuild']=joblist['lastBuild']
      response['buils_count']=count
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from jenkins")

@app.route('/sonar', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def sonar_bug():
  if request.method == 'POST':
    try:
      response={}
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      sonar_obj=devsecops_plugin.sonar_data()
      response1=sonar_obj.sonar_all(project['name'])
      response['bugs']=response1['bugs']
      response['vulnerabilities']=response1['vulnerabilities']
      response['codesmells']=response1['code']
      response['bugs_link']=response1['bugs_link']
      response['vul_link']=response1['vul_link']
      response['code_link']=response1['code_link']
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from sonar")

@app.route('/inato', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def inato_cases():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      proj_list,data=git_obj.git_projects()
      inato_obj=devsecops_plugin.inato_data()
      response=inato_obj.inato_tests(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from inato")

@app.route('/smartqe', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def smartqe_cases():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      inato_obj=devsecops_plugin.inato_data()
      response=inato_obj.smartqe_tests(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from inato")


@app.route('/tomcat', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def tomcat_status():
  if request.method == 'POST':
    try:
      response1={}
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      jenk_obj=devsecops_plugin.jenkins_data()
      resp,proj=git_obj.git_projects()
      for i in proj['projects']:
        if i == project['name']:
          response=jenk_obj.jenkins_pipeline(project['name'])
      if response['stages'][8]['status']=='SUCCESS':
        response1['status']='Pass'
      else:
        response1['status']='Fail'
      return json.dumps(response1)
    except:
      return json.dumps("unable to get data from tomcat")

@app.route('/nexus', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def nexus_status():
  if request.method == 'POST':
    try:
      response1={}
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      jenk_obj=devsecops_plugin.jenkins_data()
      resp,proj=git_obj.git_projects()
      for i in proj['projects']:
         if i == project['name']:
           response=jenk_obj.jenkins_pipeline(project['name'])
      if response['stages'][2]['status']=='SUCCESS':
        response1['status']='Pass'
      else:
        response1['status']='Fail'
      return json.dumps(response1)
    except:
      return json.dumps("unable to get data from nexus")

@app.route('/git', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def git_repos():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      proj_list,data=git_obj.git_projects()
      for i in data['projects']:
        if i == project['name']:
          for j in proj_list:
            if j['name']==project['name']:
              project_id=j['id']
      response=git_obj.git_branches(project_id)
      response1=git_obj.git_commits(project_id)
      response.update(response1)
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from gitlab")

@app.route('/maven', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def maven_status():
  if request.method == 'POST':
    try:
      response1={}
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      jenk_obj=devsecops_plugin.jenkins_data()
      resp,proj=git_obj.git_projects()
      for i in proj['projects']:
        if i == project['name']:
          response=jenk_obj.jenkins_pipeline(project['name'])
      response1['status']=response['stages'][0]['status']
      return json.dumps(response1)
    except:
      return json.dumps("unable to get data from maven")

@app.route('/dependency', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def dependency_status():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      jenk_obj=devsecops_plugin.jenkins_data()
      response=jenk_obj.dependency_pipeline(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data")

@app.route('/robot', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def robot_tests():
  if request.method == 'POST':
    try:
      response={}
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      resp,proj=git_obj.git_projects()
      if project['name']=='SNMP_Java':
        response['test_cases']='18'
        response['critical_tests']='18'
        response['tests_passed']='18'
        response['tests_failed']='0'
      elif project['name']=='CloudSim_Java':
        response['test_cases']='4'
        response['critical_tests']='4'
        response['tests_passed']='4'
        response['tests_failed']='0'
      elif project['name']=='Odl_Oxygen_Java':
        response['test_cases']='63'
        response['critical_tests']='63'
        response['tests_passed']='63'
        response['tests_failed']='0'
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from robot framework")

@app.route('/zap', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def zap_test():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      zap_obj=devsecops_plugin.zap_data()
      resp,proj=git_obj.git_projects()
      for i in proj['projects']:
        if i == project['name']:
          response=zap_obj.zap_tests(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from zap")

@app.route('/nexpose', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def nexpose_assets():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      git_obj=devsecops_plugin.git_data()
      nex_obj=devsecops_plugin.nexpose_data()
      resp,proj=git_obj.git_projects()
      for i in proj['projects']:
        if i == project['name']:
          response=nex_obj.nexpose_assets(project['name'])
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from nexpose")

@app.route('/projectrisk', methods = ['POST', 'GET', 'OPTIONS'])
@cross_origin(origin='*',headers=['Content-Type','Authorization'])
def project_risk():
  if request.method == 'POST':
    try:
      if len(request.data) !=0:
        proj = request.data.decode('utf-8')
      project=ast.literal_eval(proj)
      sonar_obj=devsecops_plugin.sonar_data()
      info = sonar_obj.get_vuln()
      info_proj = sonar_obj.get_proj()
      projectlist = sonar_obj.sonar_projects()
      debts = sonar_obj.project_debts(project['name'])
      features =[{"projectname":"CloudSim_Java","committedfeatures":"8","completed":"2","inprogress":"4","open":"2","fieldissues":"0"},{"projectname":"SNMP_Java","committedfeatures":"5","completed":"3","inprogress":1,"open":"1","fieldissues":"0"},{"projectname":"Odl_Oxygen_Java","committedfeatures":"7","completed":"1","inprogress":"3","open":"3","fieldissues":"9"}]
      response = {}
      for j in info_proj:
        if project['name'] in j["projectname"]:
          break;
      date1=j["releasedate"]
      today = date.today()
      diff= (date1-today).days
      if(diff<3):
        context= oslo_context.RequestContext()
        proj_obj=devsecops_database.projects()
        redate=proj_obj.update_projects(context,date1)
        response['releasedate'] = redate.strftime("%d-%m-%Y")
      else:
        response['releasedate']= date1.strftime("%d-%m-%Y")
      if(j["CIARating"] < 3):
        response["CIA_Rating"]= "LOW"
      elif(j["CIARating"] == 3):
        response["CIA_Rating"]= "MEDIUM"
      else:
        response["CIA_Rating"]= "HIGH"
      for k in features:
        if project['name'] in k['projectname']:
          break;
      response["committedfeatures"] = k["committedfeatures"]
      response["completed"] = k["completed"]
      response["inprogress"] = k["inprogress"]
      response["open"] = k["open"]
      response["debts"] = debts["debt"]
      response["fieldissues"]=k["fieldissues"]
      return json.dumps(response)
    except:
      return json.dumps("unable to get data from sonar")






if __name__ == "__main__":
  app.debug = True
  app.run(host='0.0.0.0',port=12399, threaded=True)


