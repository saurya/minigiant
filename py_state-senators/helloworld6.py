import cgi
import urllib2
from xml.dom import minidom
from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext.webapp.util import run_wsgi_app
from collections import namedtuple
import cgi
import datetime
import urllib
import wsgiref.handlers

from google.appengine.ext import db

class Senator(db.model):
     senator_firstname=db.StringProperty(multiline=False)
     senator_lastname=db.StringProperty(multiline=False)
     senator_state=db.StringProperty(multiline=False)

def main():
 
     id=0
     senator=[]
	  
	 
    
     for i in range(0,100):
         senator_firstname= d.getElementsByTagName("first_name")[i].childNodes[0].nodeValue
         senator_lastname= d.getElementsByTagName("last_name")[i].childNodes[0].nodeValue
         senator_state= d.getElementsByTagName("state")[i].childNodes[0].nodeValue
         senator.append(Senator(id,state,senator_firstname,senator_lastname))
	 sentr=Senator(senator_firstname=senator_firstname,senator_lastname=senator_lastname)
	 sentr.senator_state=senator_state
	 sentr.put()
		 
         
    

    

