### Team -> Pi-Thons 
#### Project - Automatic Attendence System Using BLE
#### Team Members

    > Revanth Penugonda
    > Venkat Bhardwaj Senapathi
    > Sirichandana Gaddampally


The Aim of the Project was to create a system that could automatically take the attendence of the students in the class eliminating the need for Manual processes to note Attendence. The teacher can see the attendence of all the students presnt in the class. 

###### Components Used -
* **Raspberrypi 3**  as a BLE Sensor that accepts the student requests for marking attendence and updates the server (A Django Python App hosted on Heroku) through REST POST. Created a script to act as a Bluetooth Listener. Keeps listening until a request is received, once served gets back to listening state.
* **Android App** to help student register for the System with valid data and help them connect to the BLE and notify the PI to mark his attendence.
* **Heroku hosted Django Python App** that connects with the database hosted on Amazon RDS. The Server has several RestAPI's that work for student registration and for attendence.
    * Has complete control over the MySQL databse. All requests are placed to the server which serves them.

###### Architecture-
Below image is our Architectural Design 
![alt tag](https://github.com/revaries/pi-thons/blob/master/Architectural%20Design.PNG) 
    
