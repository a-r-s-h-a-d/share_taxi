from flask import *
from database import *
public=Blueprint('public',__name__)

@public.route('/',methods=['post','get'])
def publichome():
	return render_template('publichome.html')
@public.route('/login',methods=['post','get'])
def login():
	if 'submit' in request.form:
		username=request.form['username']
		passw=request.form['password']

		q="select * from tbl_login where uname='%s' and password='%s'"%(username,passw)
		res=select(q)
		print(res)
		if res:
			session['logid']=res[0]['loginid']
			if res[0]['login_type']=="admin":
				return redirect(url_for("admin.adminhome"))
			
		else:
			flash('Invalid username or password !!!')
	return render_template('login.html')
	