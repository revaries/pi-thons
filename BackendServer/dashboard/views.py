from django.shortcuts import render
from django.http import HttpResponse
import logging
from models import Attendencedetails,Studentdetails
import MySQLdb
from datetime import datetime
from pytz import timezone
# Create your views here.
def index(request):

    # return HttpResponse('Hello from Python!')
    dbconnection = MySQLdb.connect("pithons.cliuzdfelez1.us-east-1.rds.amazonaws.com","pithons","273_pithons","ATTENDENCEDB")
    cursor = dbconnection.cursor()
    now = datetime.now(timezone('US/Pacific'))
    dateformat = "%Y-%m-%d"
    stringdate = str(now.strftime(dateformat))
    logging.warning(stringdate)
    sqlcommand = "SELECT StudentDetails.First_Name, StudentDetails.Last_Name,StudentDetails.LecClass,StudentDetails.Student_Id FROM "+"StudentDetails,AttendenceDetails WHERE StudentDetails.Mac_Address = AttendenceDetails.Mac_Address AND AttendenceDetails.LecClass = " + "273"
    totalregistered = "SELECT" + " count(distinct(Student_Id)) from StudentDetails"
    present = "select"+" count(distinct(Mac_Address)) from AttendenceDetails"
    cursor.execute(sqlcommand)
    logging.warning("Here")
    data = cursor.fetchall()
    cursor.execute(totalregistered)
    totalstrength = cursor.fetchall()[0][0]
    totalcounter = 0
    cursor.execute(present)
    present = 0
    presentstrength = cursor.fetchall()[0][0]
    dbconnection.close()
    logging.warning(presentstrength)
    logging.warning(totalstrength)
    '''now = datetime.now()
    table_return = Studentdetails.objects.filter(Mac_Address=Attendencedetails.objects.get(lecclass='273').Mac_Address).values_list('First_Name', 'Last_Name', 'LecClass', 'Student_Id', flat=True)
    '''

    return render(request, 'homepage.html',{'dataobj':data,'present':presentstrength,'absent':(totalstrength-presentstrength)})

