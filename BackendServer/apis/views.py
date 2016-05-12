from django.shortcuts import render

# Create your views here.

from django.shortcuts import render
import MySQLdb
from rest_framework.decorators import api_view
from rest_framework.response import Response
from apis.models import Attendencedetails,Studentdetails
from django.http import HttpResponse
import json
from datetime import datetime
from pytz import timezone
import logging
# Create your views here.


@api_view(['POST'])
def registerstudent(request):
    flag=0
    if  request.method == 'POST':
        body_unicode = request.body.decode('utf-8')
        body = json.loads(body_unicode)
        r = Studentdetails(first_name=body['first'],last_name=body['last'],student_id=body['Id'],mac_address=body['Mac'],lecclass="273")
        try:
            r.save()
        except:
            logging.warning("Database Save Failed")
            flag=1
        if flag ==1:
            return HttpResponse(status=400)
        else:
            return HttpResponse(status=204)


@api_view(['POST'])
def markattendance(request):
    flag = 0
    if  request.method == 'POST':
        body_unicode = request.body.decode('utf-8')
        body = json.loads(body_unicode)
        dateformat = "%Y-%m-%d"
        timeformat = "%H:%M:%S"
        now = datetime.now(timezone('US/Pacific'))
        datenow = str(now.strftime(dateformat))
        timenow = str(now.strftime(timeformat))
        a = Attendencedetails(mac_address=body['macadd'],lecclass=body['lecclass'],date=datenow,time=timenow)
        try:
            a.save()
        except Exception, e:
            logging.warning(str(e))
            logging.warning("DataBase save error")
            flag=1
        if flag == 1:
            return HttpResponse(status=400)
        else:
            return HttpResponse(status=201)





