from flask import *
from database import *
import uuid
import demjson

admin=Blueprint('admin',__name__)
@admin.route('/adminhome',methods=['post','get'])
def adminhome():
	return render_template('adminhome.html')

@admin.route('/approve_driver',methods=['get','post'])
def approve_driver():
	data={}
	if "action" in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action="none"
	if action=="approve":
		q="update tbl_driverregistration set driver_status='Active' where driver_id='%s'"%(id)
		update(q)
		flash('Approved successfully !')
	if action=="reject":
		q="update tbl_driverregistration set driver_status='Reject' where driver_id='%s'"%(id)
		update(q)
		flash('Rejected successfully !')
	q="select * from tbl_driverregistration"
	res=select(q)
	data['driver']=res
	return render_template("admin_approve_driver.html",data=data)
@admin.route('/view_pendingbookings',methods=['post','get'])
def view_pendingbookings():
	data={}
	q="SELECT r.*,b.*,v.vehicle_no,v.vehicle_type,re.`status` FROM tbl_riderregistration r,tbl_booking b, tbl_vehicle v,request re WHERE b.rider_id = r.rider_id AND b.vehicle_id = v.vehicle_id AND r.`rider_id`=re.`rider_id` AND re.`status`='pending'"
	res=select(q)
	data['view_pendingbookings']=res
	return render_template("admin_view_pendingbookings.html",data=data)	
@admin.route('/view_feedbacks',methods=['post','get'])
def view_feedbacks():
	data={}
	q="select c.*,r.ride_fname,r.rider_lname from tbl_feedback c,Tbl_riderregistration r where c.rider_id=r.rider_id"
	res=select(q)
	data['view_feedbacks']=res
	return render_template("admin_viewfeedback.html",data=data)	
@admin.route('/view_complaints',methods=['post','get'])
def view_complaints():
	data={}
	if "action" in request.args:
		action=request.args['action']
		id=request.args['comid']
	else:
		action="none"
	if action=="sendreply":
		data['sendreply']="sendreply"
	if "sendreply" in request.form:
		reply=request.form['reply']
		q="update tbl_complaint set reply='%s',status='replied' where complaint_id='%s'"%(reply,id)
		update(q)
		flash('Updated successfully !!!')
		return redirect(url_for('admin.view_complaints'))
	q="select * from tbl_complaint inner join tbl_riderregistration using(rider_id)"
	res=select(q)
	data['view_complaints']=res
	return render_template("admin_view_complaints.html",data=data)	
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
@admin.route('/logout')
def logout():
	session.clear()
	return redirect(url_for('public.publichome'))
