#############
Url Shortener
#############

Building
========

Checkout source code and run 

::
  
  $ mvn clean package -DskipTests

Running
=======

::

  $ java -jar target/urlshortener-0.0.1-SNAPSHOT.jar

Typical Scenario
================

This doc is also avaialable at '/help/':

::

  $ curl  localhost:8080/help/ | less

Create testAccountX
-------------------

::

  $ curl -H 'Content-Type: application/json' -X POST localhost:8080/config/account -d '{ "accountId": "testAccountX"}'; echo
  {"success":true,"description":"Account was created","password":"YVlBF3bE"}

Register url with default (302) redirect 
-----------------------------------------

::

  $ curl -u testAccountX:YVlBF3bE -H 'Content-Type: application/json' -X POST localhost:8080/config/register -d '{ "url": "https://google.com/search?q=test1"}'; echo
  {"shortUrl":"http://localhost:8080/aA4","redirectType":302}

Register url with 301 redirect 
-------------------------------

::

  $ curl -u testAccountX:YVlBF3bE -H 'Content-Type: application/json' -X POST localhost:8080/config/register -d '{ "url": "https://google.com/search?q=test2", "redirectType": 301}'; echo
  {"shortUrl":"http://localhost:8080/aA5","redirectType":301}

Check redirecting for short urls
--------------------------------

::

  $ curl -v http://localhost:8080/aA4
  + Hostname was NOT found in DNS cache
  + Trying 127.0.0.1...
  + Connected to localhost (127.0.0.1) port 8080 (#0)
  > GET /aA4 HTTP/1.1
  > User-Agent: curl/7.35.0
  > Host: localhost:8080
  > Accept: */*
  >
  < HTTP/1.1 302
  < X-Application-Context: application
  < Location: https://google.com/search?q=test1
  < Content-Length: 0
  < Date: Mon, 25 Dec 2017 11:36:17 GMT
  <
  + Connection #0 to host localhost left intact


::

  $ curl -v http://localhost:8080/aA4
  + Hostname was NOT found in DNS cache
  + Trying 127.0.0.1...
  + Connected to localhost (127.0.0.1) port 8080 (#0)
  > GET /aA4 HTTP/1.1
  > User-Agent: curl/7.35.0
  > Host: localhost:8080
  > Accept: */*
  >
  < HTTP/1.1 302
  < X-Application-Context: application
  < Location: https://google.com/search?q=test1
  < Content-Length: 0
  < Date: Mon, 25 Dec 2017 11:36:17 GMT
  <
  + Connection #0 to host localhost left intact

Use redirect multiple times for statistic
-----------------------------------------
  
  Note that browsers can caches '301 direct' and don't request original location in future.

Check statistic
---------------

::

  $ curl -u testAccountX:YVlBF3bE -H 'Content-Type: application/json' localhost:8080/statistic/testAccountX ; echo
  {"https://google.com/search?q=test1":5,"https://google.com/search?q=test2":8}


