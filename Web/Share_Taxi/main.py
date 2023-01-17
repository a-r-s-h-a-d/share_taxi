
from flask import Flask

from public import public
from admin import admin
from api import api

app=Flask(__name__)
app.secret_key="secretkey"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(api,url_prefix='/api')

app.run(debug=True,host="192.168.114.145",port=5034)
