import bluetooth
import json
import requests
counter = 0
port = 1
stopper =1

def posthttp(Mac_addr,Class):
	payload = {}
	payload['macadd']= Mac_addr
	payload['lecclass'] = Class
	payload_json = json.dumps(payload) 
	print payload_json
	try:
		r=requests.post("https://bleserver.herokuapp.com/attendance/",payload_json)
		print r.status_code
	except:
		print "Post Failed"		


try:
	serversocket = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
	serversocket.bind(("",port))
	serversocket.listen(1)
	print "Bluetooth Listening Successful"
except:
	print "Initial serversocket failed"
	stopper =0
while True and stopper!=0:
	print "Counting " , counter
	flag = 0
	if counter ==10:
		print "Counter Reset Loop"
		
		try:
			serversocket.close()
			serversocket = bluetooth.BluetoothSocket(bluetooth.RFCOMM)
			serversocket.bind(("",port))
			serversocket.listen(1)
		except:
			print "Server Reinitialization Failed"
			flag =1
		counter =0
	print "Listening ......"
	
	try:
		clientsocket,address = 	serversocket.accept()
		print "Accepted Address" ,address[0]
	except:
		print "Client Socket Acceptance Failure"
		flag = 1
	try:
		data = 	clientsocket.recv(1024)
		print "Received Data ->", data
	except:
		print "Data Receiving Failed"
		flag = 1
	
	counter = counter +1
	
	try:
		clientsocket.close()
	except:
		print "Closing connection Failed"

	if flag == 0:
		posthttp(address[0],"273")
	else:
		print "Post Failure due to Earlier Failures"


