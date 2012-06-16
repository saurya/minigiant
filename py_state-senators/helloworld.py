import cgi

from google.appengine.api import users
from google.appengine.ext import webapp
from google.appengine.ext import db
from google.appengine.ext.webapp.util import run_wsgi_app

class MainPage(webapp.RequestHandler):
    def get(self):
        self.response.out.write("""
          <html>
            <body>
              <form action="/getSenator" method="post">
                <div><input type="text"  name="content" ></text></div>
                <div><input type="submit" value="Enter State"></div>
              </form>
            </body>
          </html>""")


class GetSenator(webapp.RequestHandler):
    def post(self):
      
       currstate=self.request.get('content')
       
       strsenators=getanswer(currstate)
       self.response.out.write('<html><body>Here is th answer:<pre>')
       self.response.out.write("The list of senators of "+currstate+" "+strsenators)
       self.response.out.write('</pre></body></html>')
    
       
 
class FindSenator(db.Model):
     firstname=db.StringProperty(required=True)
     lastname=db.StringProperty(required=True)
     state=db.StringProperty(required=True)
application = webapp.WSGIApplication(
                                     [('/', MainPage),
                                      ('/getSenator', GetSenator)],
                                     debug=True)

def getanswer(currstate):
        q = db.GqlQuery("SELECT * FROM FindSenator WHERE state = :cstate Limit 2",cstate=currstate )
        result=q.fetch(2)
        cnt=0
        strsenators=""
        for i in  result:
            strsenators=strsenators+" "+str(cnt+1)+"."+i.firstname+" "+i.lastname
            cnt+=1
        return strsenators    
def dbstuff():
       import urllib2
    
       contents=urllib2.urlopen("http://www.senate.gov/general/contact_information/senators_cfm.xml")
       t=contents.read()

       from collections import namedtuple

       

       id=0
    
       first_name=[]
       last_name=[]
       state_name=[]
    
       from xml.dom import minidom
    
       d=minidom.parseString(t)
    
    
       for key in (db.GqlQuery("SELECT * FROM FindSenator")):
                                     db.delete(key)


       for i in range(0,100):
	     
             first_name.append(d.getElementsByTagName("first_name")[i].childNodes[0].nodeValue)
             last_name.append(d.getElementsByTagName("last_name")[i].childNodes[0].nodeValue)
             state_name.append(d.getElementsByTagName("state")[i].childNodes[0].nodeValue)

       for i in range(0,100):
            rt=FindSenator(firstname=first_name[i],lastname=last_name[i],state=state_name[i])
            rt.put()

def main():
    dbstuff()
    run_wsgi_app(application)

if __name__ == "__main__":
    main()
