from flask import *
from database import *
import demjson
import uuid


api = Blueprint('api',__name__)

@api.before_request
def beep():
	import winsound
	winsound.Beep(2500,100)


@api.route('/login',methods=['get','post'])
def login():
	data={}
	# data.update(request.args)
	username = request.args['uname']
	password = request.args['pass']
	q = "select * from tbl_login where uname='%s' and password='%s'" % (username,password)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)

@api.route('/user_register',methods=['get','post'])
def user_register():
	data={}
	uname = request.args['uname']
	password = request.args['pass']
	fname = request.args['fname']
	lname = request.args['lname']
	gender = request.args['gender']
	phone = request.args['phone']
	email = request.args['email']
	
	q="SELECT * FROM tbl_login WHERE uname='%s'"%(uname)
	res=select(q)
	if res:
		data['status']  = 'already'
		data['method']  = 'user_register'
	else:

		q="insert into tbl_login(uname,password,login_type)values('%s','%s','rider')" % (uname,password)
		id=insert(q)
		q="insert into tbl_riderregistration(ride_fname,rider_lname,rider_gender,rider_phnumber,rider_email,loginid)values('%s','%s','%s','%s','%s','%s')" % (fname,lname,gender,phone,email,id)
		print(q)
		id=insert(q)

		if(id>0):
			data['status']  = 'success'
			data['method']  = 'user_register'
		else:
			data['status']	= 'failed'
	data['method']  = 'user_register'
	return demjson.encode(data)

@api.route('/finished_ride',methods=['get','post'])
def finished_ride():
	data={}
	riderid = request.args['riderid']
	q = "select * from tbl_booking,req_aprvl,request,tbl_driverregistration,tbl_vehicle,tbl_location where tbl_location.driver_id=tbl_driverregistration.driver_id and  tbl_booking.rider_id=request.rider_id and request.req_id=tbl_booking.req_id AND request.req_id=req_aprvl.req_id and req_aprvl.driver_id=tbl_driverregistration.driver_id and tbl_booking.vehicle_id=tbl_vehicle.vehicle_id and tbl_vehicle.driver_id=tbl_driverregistration.driver_id and request.status='droped'  and request.rider_id=(SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s')" % (riderid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'finished_ride'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'finished_ride'
	return  demjson.encode(data)

@api.route('/advance',methods=['get','post'])
def advance():
	data={}
	amt = request.args['amt']
	bookid = request.args['bookid']
	balance = request.args['balance']
	apr_ids=request.args['apr_ids']
	if balance !="null":
		a=float(amt)
		t=float(balance)

		advance=a-t
		print(advance)

		q="update tbl_booking set amount='%s' where book_id='%s'"% (balance,bookid)
		update(q)
		q="INSERT INTO tbl_advancepay(apr_id,amount,balance,date,time)VALUES('%s','%s','%s',curdate(),curtime())"% (bookid,advance,balance)
		id=insert(q)

	else:
		q="update tbl_booking set amount='0' where book_id='%s'"% (bookid)
		update(q)
		q="UPDATE `tbl_advancepay` SET `amount`=`amount`+'%s',`balance`='0',`date`=CURDATE(),`time`=NOW() WHERE `apr_id`='%s'"%(amt,apr_ids)
		update(q)


	# if a<t:
	# 	c=t-a
	# 	q="update tbl_booking set amount='%s' where book_id='%s'"% (c,bookid)
	# 	update(q)
	# 	q="INSERT INTO tbl_advancepay(apr_id,amount,balance,date,time)VALUES('%s','%s','%s',curdate(),curtime())"% (bookid,amt,c)
	# 	id=insert(q)
	# 	if(id>0):
	# 		data['status']  = 'success'
	# 		data['method']  ='advance'
	# 	else:
	# 		data['status']	= 'failed'
	data['status']  = 'success'
	data['method']  ='advance'
	return demjson.encode(data)

@api.route('/addcomplaint',methods=['get','post'])
def addcomplaint():
	data={}
	riderid = request.args['riderid']
	msg = request.args['msg']
	
	q="INSERT INTO tbl_complaint(rider_id,complaint,date,status) VALUES((SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s'),'%s',curdate(),'pending')" % (riderid,msg)
	id=insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  = 'addcomplaint'
	else:
		data['status']	= 'failed'
		data['method']  = 'addcomplaint'
	return demjson.encode(data)

@api.route('/viewcomplaint',methods=['get','post'])
def viewcomplaint():
	data={}
	riderid = request.args['riderid']
	q = "SELECT complaint,reply FROM tbl_complaint where rider_id=(SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s') and status='replied'" % (riderid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'viewcomplaint'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'viewcomplaint'
	return  demjson.encode(data)

@api.route('/feedback',methods=['get','post'])
def feedback():
	data={}
	uid = request.args['uid']
	msg = request.args['msg']
	
	q="INSERT INTO tbl_feedback  VALUES(null,(SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s'),'%s',curdate(),curtime(),'ok')" % (uid,msg)
	id=insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  = 'feedback'
	else:
		data['status']	= 'failed'
		data['method']  = 'feedback'
	return demjson.encode(data)

@api.route('/check_req',methods=['get','post'])
def check_req():
	data={}
	uid = request.args['uid']
	q = "SELECT * FROM request WHERE rider_id =(SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s') AND status= 'pending'" % (uid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'check_req'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'check_req'
	return  demjson.encode(data)

@api.route('/addrating',methods=['get','post'])
def addrating():
	data={}
	reqid = request.args['reqid']
	rating = request.args['rating']
	
	q="INSERT INTO rating (req_id,rating)values('%s','%s')" % (reqid,rating)
	id=insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  = 'addrating'
	else:
		data['status']	= 'failed'
		data['method']  = 'addrating'
	return demjson.encode(data)

@api.route('/sentrequest',methods=['get','post'])
def sentrequest():
	data={}
	travelto = request.args['travelto']
	fromplace = request.args['fromplace']
	noof = request.args['noof']
	flati = request.args['flati']
	flongi = request.args['flongi']
	tlati = request.args['tlati']
	tlongi = request.args['tlongi']
	uid = request.args['uid']
	q="insert into request values (null,(SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s'),'%s','%s','%s','%s','%s',curdate(),curtime(),'pending')" % (uid,flati,flongi,tlati,tlongi,noof)
	id=insert(q)
	q="insert into tbl_booking (rider_id,vehicle_id,booking_from,booking_to,booking_date,booking_time,amount,booking_status,req_id)values((SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s'),'0','%s','%s',curdate(),curtime(),'0','pending',(select max(req_id) from request))" % (uid,fromplace,travelto)
	id=insert(q)

	if(id>0):
		data['status']  = 'success'
		data['method']  = 'sentrequest'
	else:
		data['status']	= 'failed'
		data['method']  = 'sentrequest'
	return demjson.encode(data)

@api.route('/req_status',methods=['get','post'])
def req_status():
	data={}
	riderid = request.args['riderid']
	# q = "select * from request,tbl_booking,req_aprvl,tbl_driverregistration,tbl_vehicle,tbl_location where tbl_location.driver_id=tbl_driverregistration.driver_id and  tbl_booking.rider_id=request.rider_id and request.req_id=tbl_booking.req_id AND request.req_id=req_aprvl.req_id and req_aprvl.driver_id=tbl_driverregistration.driver_id and tbl_booking.vehicle_id=tbl_vehicle.vehicle_id and tbl_vehicle.driver_id=tbl_driverregistration.driver_id  and request.rider_id=(SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s')" % (riderid)
	q = "SELECT d.`driver_fname`,d.`driver_lname`,v.`vehicle_type`,v.`no_of_seats`,v.`vehicle_no`,l.`lattitude`,l.`longitude`,b.`book_id`,r.`status`,b.`amount`,req_aprvl.`apr_id` AS apr_id  FROM request r,tbl_booking b,req_aprvl,tbl_driverregistration d,tbl_vehicle v,tbl_location l WHERE l.driver_id=d.driver_id AND  b.rider_id=r.rider_id AND r.req_id=b.req_id AND r.req_id=req_aprvl.req_id AND req_aprvl.driver_id=d.driver_id AND b.vehicle_id=v.vehicle_id AND v.driver_id=d.driver_id  AND r.rider_id=(SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s') and b.amount !='0'" % (riderid)
	print(q)
	res = select(q)
	print(res)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'req_status'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'req_status'
	return  demjson.encode(data)

@api.route('/currentridetoshare',methods=['get','post'])
def currentridetoshare():
	data={}
	riderid = request.args['riderid']
	q = "SELECT * FROM request,req_aprvl,tbl_booking,tbl_riderregistration,tbl_vehicle WHERE tbl_riderregistration.rider_id=request.rider_id AND req_aprvl.req_id=request.req_id AND tbl_booking.req_id=request.req_id AND request.rider_id=(SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s') AND request.status='picked'  AND  tbl_booking.vehicle_id=tbl_vehicle.vehicle_id AND req_aprvl.driver_id=tbl_vehicle.driver_id" % (riderid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'currentridetoshare'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'currentridetoshare'
	return  demjson.encode(data)

@api.route('/sharetoothers',methods=['get','post'])
def sharetoothers():
	data={}
	reqid = request.args['reqid']
	riderid = request.args['riderid']
	
	q="update request set status='shared' where req_id='%s'" % (reqid)
	id=update(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  = 'sharetoothers'
	else:
		data['status']	= 'failed'
		data['method']  = 'sharetoothers'
	return demjson.encode(data)

@api.route('/view_shared_ride',methods=['get','post'])
def view_shared_ride():
	data={}
	riderid = request.args['riderid']
	lati = request.args['lati']
	longi = request.args['longi']

	q = "SELECT *,(3959 * ACOS ( COS ( RADIANS('%s') ) * COS( RADIANS( flatitude) ) * COS( RADIANS( flongitude ) - RADIANS('%s') ) + SIN ( RADIANS('%s') ) * SIN( RADIANS(flatitude ) ))) AS user_distance  FROM request,tbl_booking,tbl_riderregistration,tbl_vehicle WHERE  tbl_vehicle.vehicle_id=tbl_booking.vehicle_id AND tbl_booking.req_id=request.req_id AND tbl_booking.rider_id=request.rider_id AND tbl_riderregistration.rider_id=request.rider_id AND request.req_id=tbl_booking.req_id  AND request.status='shared' and request.rider_id!=(SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s') HAVING user_distance<31.068 ORDER BY user_distance ASC" % (lati,longi,lati,riderid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'view_shared_ride'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'view_shared_ride'
	return  demjson.encode(data)

@api.route('/send_share_req',methods=['get','post'])
def send_share_req():
	data={}
	travelto = request.args['travelto']
	balseat = request.args['balseat']
	fromplace= request.args['fromplace']
	noof = request.args['noof']
	flati = request.args['flati']
	flongi = request.args['flongi']
	tlati = request.args['tlati']
	tlongi = request.args['tlongi']
	uid = request.args['uid']
	vehicleid= request.args['vehicleid']
	driverid= request.args['driverid']

	q="insert into request values (null,(SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s'),'%s','%s','%s','%s','%s',curdate(),curtime(),'share_request')" % (uid,flati,flongi,tlati,tlongi,noof)
	id=insert(q)
	q="insert into tbl_booking (rider_id,vehicle_id,booking_from,booking_to,booking_date,booking_time,amount,booking_status,req_id)values((SELECT rider_id FROM tbl_riderregistration WHERE loginid='%s'),'%s','%s','%s',curdate(),curtime(),'0','pending',(select max(req_id) from request))" % (uid,vehicleid,fromplace,travelto)
	id=insert(q)
	q="insert into req_aprvl(req_id,driver_id)values((select max(req_id) from request),'%s')" % (driverid)
	id=insert(q)
	q="update tbl_vehicle set availability='%s' where vehicle_id='%s'" % (balseat,vehicleid)
	c=update(q)

	if(c>0):
		data['status']  = 'success'
		data['method']  = 'send_share_req'
	else:
		data['status']	= 'failed'
		data['method']  = 'send_share_req'
	return demjson.encode(data)

# -------------------------------------

@api.route('/actionshare',methods=['get','post'])
def actionshare():
	data={}
	req_id = request.args['req_id']
	status = request.args['status']
	total= request.args['total']
	if status=="accepted":
		q="UPDATE request SET status='%s' WHERE req_id='%s'" % (status,req_id)
		c=update(q)
		q="UPDATE tbl_booking SET amount='%s' WHERE req_id='%s'" % (total,req_id)
		c=update(q)
		q="update req_aprvl set status='%s', amount='%s' where req_id='%s'" % (status,total,req_id)
		c=update(q)
		if c>0:
			data['status']  = 'success'
			data['method']  = 'actionshare'
		else:
			data['status']  = 'failed'
			data['method']  = 'actionshare'
	else:
		q="update req_aprvl set status='%s' where  req_id='%s'" % (status,req_id)
		c=update(q)
		q="UPDATE request SET status='%s' WHERE req_id='%s'" % (status,req_id)
		c=update(q)
		if c>0:
			data['status']	= 'success'
			data['method']  = 'actionshare'
		else:
			data['status']  = 'failed'
			data['method']  = 'actionshare'
	return demjson.encode(data)

@api.route('/dropuser',methods=['get','post'])
def dropuser():
	data={}
	req_id = request.args['req_id']
	
	q="update request set status='droped' where req_id='%s'" % (req_id)
	c=update(q)
	if(c>0):
		data['status']  = 'success'
		data['method']  = 'dropuser'
	else:
		data['status']	= 'failed'
		data['method']  = 'dropuser'
	return demjson.encode(data)

@api.route('/picked_current_ride',methods=['get','post'])
def picked_current_ride():
	data={}
	driver_id = request.args['driver_id']
	q = "SELECT * FROM request,req_aprvl,tbl_booking,tbl_riderregistration WHERE tbl_riderregistration.rider_id=request.rider_id AND req_aprvl.req_id=request.req_id AND tbl_booking.req_id=request.req_id AND req_aprvl.driver_id=(select driver_id from tbl_driverregistration where loginid='%s') AND (request.status='shared' OR request.status='picked' OR request.status='accepted')  ORDER BY request.req_id DESC" % (driver_id)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'picked_current_ride'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'picked_current_ride'
	return  demjson.encode(data)

@api.route('/updatelocation',methods=['get','post'])
def updatelocation():
	data={}
	latti = request.args['latti']
	longi = request.args['longi']
	uid = request.args['uid']
	q = "SELECT * FROM tbl_location WHERE driver_id=(select driver_id from tbl_driverregistration where loginid='%s')" % (uid)
	res = select(q)
	if(len(res) > 0):
		q = "UPDATE tbl_location SET  lattitude='%s',longitude='%s',date=curdate(),time=curtime() WHERE driver_id=(select driver_id from tbl_driverregistration where loginid='%s')" % (latti,longi,uid)
		id = update(q)
		if id>0:
			data['status']  = 'success'
			data['method']  = 'updatelocation'
		else:
			data['status']  = 'failed'
			data['method']  = 'updatelocation'
	else:
		q = "INSERT INTO tbl_location(driver_id,lattitude,longitude,date,time,availability)VALUES((select driver_id from tbl_driverregistration where loginid='%s'),'%s','%s',curdate(),curtime(),'ok')" % (uid,latti,longi)
		id = insert(q)
		if id>0:
			data['status']  = 'success'
			data['method']  = 'updatelocation'
		else:
			data['status']  = 'failed'
			data['method']  = 'updatelocation'
	return  demjson.encode(data)

@api.route('/dlogin',methods=['get','post'])
def dlogin():
	data={}
	uname = request.args['uname']
	password = request.args['pass']
	q = "select l.loginid,l.login_type,d.driver_status from tbl_login l,tbl_driverregistration d where l.uname='%s' and l.password='%s' and l.login_type='driver' and l.loginid=d.loginid" % (uname,password)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'dlogin'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'dlogin'
	return  demjson.encode(data)

@api.route('/view_vehicle',methods=['get','post'])
def view_vehicle():
	data={}
	driverid = request.args['driverid']
	q = "SELECT * FROM tbl_vehicle WHERE driver_id=(select driver_id from tbl_driverregistration where loginid='%s')" % (driverid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'view_vehicle'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'view_vehicle'
	return  demjson.encode(data)

@api.route('/req_response',methods=['get','post'])
def req_response():
	data={}
	req_id = request.args['req_id']
	status = request.args['status']
	amount = request.args['amount']
	driver_id = request.args['driver_id']
	vehid = request.args['vehid']
	available_seats = request.args['available_seats']

	q="UPDATE request SET status='%s' WHERE req_id='%s'" % (status,req_id)
	c=update(q)
	q="UPDATE tbl_vehicle  SET availability='%s' WHERE driver_id=(select driver_id from tbl_driverregistration where loginid='%s')" % (available_seats,driver_id)
	c=update(q)
	q="UPDATE tbl_booking SET vehicle_id='%s',amount='%s' WHERE req_id='%s'" % (vehid,amount,req_id)
	c=update(q)
	q="INSERT INTO req_aprvl VALUES(null,'%s',(select driver_id from tbl_driverregistration where loginid='%s'),'%s','%s')" % (req_id,driver_id,amount,status)
	id=insert(q)

	if(id>0):
		data['status']  = 'success'
		data['method']  = 'req_response'
	else:
		data['status']	= 'failed'
		data['method']  = 'req_response'
	return demjson.encode(data)

@api.route('/view_payments',methods=['get','post'])
def view_payments():
	data={}
	logid = request.args['logid']
	q = "SELECT req_aprvl.`amount` AS amt,request.*,tbl_booking.*,tbl_riderregistration.*,tbl_advancepay.* FROM req_aprvl,request,tbl_booking,tbl_riderregistration,tbl_advancepay WHERE tbl_advancepay.apr_id=tbl_booking.book_id and req_aprvl.req_id=request.req_id AND tbl_booking.rider_id=tbl_riderregistration.rider_id AND tbl_booking.req_id=request.req_id AND req_aprvl.driver_id=(select driver_id from tbl_driverregistration where loginid='%s')" % (logid)
	res = select(q)
	if(len(res) > 0):
		i=0
		for row in res:
			if row['status']=="shared" or row['status']=="accepted":
				tot = float(row['amt']) - ((float(row['amt'])) * 20 / 100)
				bal = round(((tot - ((tot * 10) / 100))),2)
				res[i]["amt"]=tot
				res[i]["balance"]=bal
				data['status']  = 'success'
				data['method']  = 'view_payments'
				data['data'] = res
			else :
				data['status']  = 'success'
				data['method']  = 'view_payments'
				data['data'] = res
			i=i+1
	else:
		data['status']	= 'failed'
		data['method']  = 'view_payments'
	return  demjson.encode(data)

@api.route('/current_ride',methods=['get','post'])
def current_ride():
	data={}
	driver_id = request.args['driver_id']
	q = "SELECT * FROM request,req_aprvl,tbl_booking,tbl_riderregistration WHERE tbl_riderregistration.rider_id=request.rider_id AND req_aprvl.req_id=request.req_id AND tbl_booking.req_id=request.req_id AND req_aprvl.driver_id=(select driver_id from tbl_driverregistration where loginid='%s') AND (request.status='approved' or request.status='accepted') order by request.req_id desc" % (driver_id)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'current_ride'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'current_ride'
	return  demjson.encode(data)

@api.route('/pickuser',methods=['get','post'])
def pickuser():
	data={}
	req_id = request.args['req_id']
	
	q="update request set status='picked' where req_id='%s'" % (req_id)
	c=update(q)
	if(c>0):
		data['status']  = 'success'
		data['method']  = 'pickuser'
	else:
		data['status']	= 'failed'
		data['method']  = 'pickuser'
	return demjson.encode(data)

@api.route('/view_ratings',methods=['get','post'])
def view_ratings():
	data={}
	logid = request.args['logid']
	q = "SELECT * FROM request,req_aprvl,rating,tbl_riderregistration WHERE req_aprvl.req_id=request.req_id AND request.rider_id=tbl_riderregistration.rider_id AND req_aprvl.driver_id=(select driver_id from tbl_driverregistration where loginid='%s') AND rating.req_id=req_aprvl.req_id" % (logid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'view_ratings'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'view_ratings'
	return  demjson.encode(data)

@api.route('/registration',methods=['get','post'])
def registration():
	data = {}	
	fname = request.form['fname']
	lname = request.form['lname']
	gender = request.form['gender']
	hname = request.form['hname']
	city = request.form['city']
	pincode=request.form['pincode']	
	email = request.form['email']
	dob=request.form['dob']	
	phone = request.form['phone']
	license = request.form['license']
	exp = request.form['exp']
	uname = request.form['uname']
	password = request.form['pass']

	image=request.files['photo']

	path='static/uploads/'+str(uuid.uuid4())+image.filename
	image.save(path)
	
	q="insert into tbl_login values(null,'%s','%s','driver')"%(uname,password)
	res=insert(q)
	q="insert into tbl_driverregistration values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s',curdate(),'Inactive','%s')"%(fname,lname,hname,city,pincode,gender,email,dob,phone,license,path,exp,res)	
	print(q)
	id=insert(q)
	if id:
		data['data'] = id
		data['status'] = 'success'
	else:
		data['status'] = 'failed'
	return demjson.encode(data)

@api.route('/d_view_route',methods=['get','post'])
def d_view_route():
	data={}
	drid = request.args['drid']
	q = "SELECT *  FROM request,tbl_booking,tbl_riderregistration,req_aprvl WHERE req_aprvl.req_id=request.req_id AND req_aprvl.driver_id=(select driver_id from tbl_driverregistration where loginid='%s') AND tbl_booking.rider_id=request.rider_id AND tbl_riderregistration.rider_id=request.rider_id AND request.req_id=tbl_booking.req_id ORDER BY req_aprvl.req_id DESC " % (drid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'd_view_route'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'd_view_route'
	return  demjson.encode(data)

@api.route('/view_share_req',methods=['get','post'])
def view_share_req():
	data={}
	driver_id = request.args['driver_id']
	q = "SELECT tbl_vehicle.*,tbl_vehicle.`amount` AS amt,request.*,req_aprvl.*,tbl_booking.*,tbl_riderregistration.* FROM tbl_vehicle,request,req_aprvl,tbl_booking,tbl_riderregistration WHERE tbl_booking.vehicle_id=tbl_vehicle.vehicle_id AND tbl_riderregistration.rider_id=request.rider_id AND req_aprvl.req_id=request.req_id AND tbl_booking.req_id=request.req_id AND req_aprvl.driver_id=(select driver_id from tbl_driverregistration where loginid='%s') AND request.status='share_request'  ORDER BY request.req_id DESC" % (driver_id)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'view_share_req'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'view_share_req'
	return  demjson.encode(data)


@api.route('/vehicle_reg',methods=['get','post'])
def vehicle_reg():
	data={}
	vname = request.args['vname']
	seats = request.args['seats']
	vno = request.args['vno']
	amount = request.args['amount']
	driverid = request.args['driverid']
	
	q="INSERT INTO tbl_vehicle VALUES(null,(select driver_id from tbl_driverregistration where loginid='%s'),'%s','%s','%s','%s','available')" % (driverid,vname,seats,vno,amount)
	id=insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  = 'vehicle_reg'
	else:
		data['status']	= 'failed'
		data['method']  = 'vehicle_reg'
	return demjson.encode(data)

@api.route('/vehiclelist',methods=['get','post'])
def vehiclelist():
	data={}
	logid = request.args['logid']
	q = "SELECT * FROM tbl_vehicle WHERE driver_id=(select driver_id from tbl_driverregistration where loginid='%s')" % (logid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'vehiclelist'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'vehiclelist'
	return  demjson.encode(data)

@api.route('/notification',methods=['get','post'])
def notification():
	data={}
	lati = request.args['lati']
	longi = request.args['longi']
	logid = request.args['logid']

	q = "SELECT *,(3959 * ACOS ( COS ( RADIANS('%s') ) * COS( RADIANS( flatitude) ) * COS( RADIANS( flongitude ) - RADIANS('%s') ) + SIN ( RADIANS('%s') ) * SIN( RADIANS(flatitude ) ))) AS user_distance FROM request,tbl_booking,tbl_riderregistration WHERE tbl_booking.rider_id=request.rider_id AND tbl_riderregistration.rider_id=request.rider_id AND request.req_id=tbl_booking.req_id and tbl_booking.booking_status='pending' AND (request.status='pending' or request.status='rejected')  HAVING user_distance<31.068 ORDER BY user_distance ASC" % (lati,longi,lati)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'notification'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'notification'
	return  demjson.encode(data)



@api.route('/Driver_forgot_password',methods=['get','post'])
def Driver_forgot_password():
	data={}
	# data.update(request.args)
	uname = request.args['uname']
	email = request.args['email']
	phone = request.args['phone']

	q = "SELECT * FROM `tbl_login` INNER JOIN `tbl_driverregistration` USING(`loginid`) WHERE `driver_email`='%s' AND `driver_phn`='%s' AND `uname`='%s' AND `login_type`='driver'"%(email,phone,uname)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)


@api.route('/Driver_set_new_password',methods=['get','post'])
def Driver_set_new_password():
	data={}
	loginid = request.args['loginid']
	password = request.args['password']
	c_password = request.args['c_password']

	if password==c_password:
		q="UPDATE `tbl_login` SET `password`='%s' WHERE `loginid`='%s'"%(c_password,loginid)
		update(q)
		data['status']  = 'success'
	else:
		data['status']	= 'failed'
	return demjson.encode(data)



@api.route('/Rider_forgot_password',methods=['get','post'])
def Rider_forgot_password():
	data={}
	# data.update(request.args)
	uname = request.args['uname']
	email = request.args['email']
	phone = request.args['phone']

	q = "SELECT * FROM `tbl_login` INNER JOIN `tbl_riderregistration` USING(`loginid`) WHERE `rider_email`='%s' AND `rider_phnumber`='%s' AND `uname`='%s' AND `login_type`='rider'"%(email,phone,uname)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)



@api.route('/Rider_set_new_password',methods=['get','post'])
def Rider_set_new_password():
	data={}
	loginid = request.args['loginid']
	password = request.args['password']
	c_password = request.args['c_password']

	if password==c_password:
		q="UPDATE `tbl_login` SET `password`='%s' WHERE `loginid`='%s'"%(c_password,loginid)
		update(q)
		data['status']  = 'success'
	else:
		data['status']	= 'failed'
	return demjson.encode(data)




@api.route('/viewrider')
def viewrider():
	data={}
	riderid=request.args['riderid']
	q="SELECT *,`rider_phnumber` as contact_number FROM `tbl_riderregistration` WHERE `loginid`='%s'"%(riderid)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']='viewrider'
	return demjson.encode(data)


	

@api.route('/Check_payment')
def Check_payment():
	data={}
	apr_ids=request.args['apr_ids']
	q="SELECT * FROM `tbl_advancepay` WHERE `apr_id`='%s' "%(apr_ids)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	data['method']='Check_payment'
	return demjson.encode(data)
