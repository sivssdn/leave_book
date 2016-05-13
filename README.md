
# leave_book
The project aims at digitalizing the process of entry-exit at Ashoka using HID. 

Languages:
Core logic is in Java and jsp. Front end relies on HTML,CSS and JavaScript.

###Module dependencies
jsp-api

mysql-connector-java-5.0.8-bin

servlet-api

Apache poi 3.9

org.json

DataBase.java:

Contains function to perform select, insert, update with transaction support.
close() is necessary for making transaction to commit

Validator.java

Contains functions to check if the input is valid date and time. Also contain function to test if the date out is less than or equal to date in.

controller.jsp

Receives input from html forms. 
Used for giving permission to one time permission users.
Checks as per date out if the permission to go out on a particular day exists, if exists then updates the time out, date in and time in, else inserts the permission in the table.
Outputs String 'success' if insert operation was successful, 'updated' for the update operation and 'failed' for neither of the two, and other form processing.

<<<<<<< HEAD
MakeExcel.java
Responsible from reading form mysql database and write into excel file
=======
Excel.java
Responsible from reading form mysql database and write into excel file
>>>>>>> c444661e8ab5af64686d6c1a170f5e3356220606
