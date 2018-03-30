###########################
rest-money-transfer-service
###########################


API
===

+----------------------------------------------------------------------------+------------------------------------+-------------------------------------------------------+
| Action                                                                     | Description                        | Expected Result                                       |
+----------------------------------------------------------------------------+------------------------------------+-------------------------------------------------------+
| GET /account/{accountId}                                                    | Account info                       | 200 OK, JSON: {"id":<account_id>,"balance":<balance>} |
+----------------------------------------------------------------------------+------------------------------------+-------------------------------------------------------+
| POST /account/transfer                                                      | Transferring money between accounts | 204 No Content                                        |
| JSON: {"sender":<account_id>, "recipient":<account_id>, "amount":<amount>} |                                    |                                                       |
+----------------------------------------------------------------------------+------------------------------------+-------------------------------------------------------+

WADL
----

http://localhost:8080/application.wadl


Build and Run
=============

::

  mvn package
  java -jar target/transfer-server.jar

Use CTRL-C to stop server.

Predefined Data and cURL example
================================

+------------+---------+
| Account Id | Balance |
+------------+---------+
|          1 | 100000  |
+------------+---------+
|          2 | 200000  |
+------------+---------+
|          3 | 300000  |
+------------+---------+

::

  curl -v http://localhost:8080/account/1; echo
  curl -v http://localhost:8080/account/2; echo 
  curl -v -X POST -H "Content-Type: application/json" http://localhost:8080/account/transfer -d '{"sender":1, "recipient":2, "amount":100000}'; echo
  curl -v http://localhost:8080/account/1; echo
  curl -v http://localhost:8080/account/2; echo 
