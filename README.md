UNG - Databases - Dr. K

Members:
1. Jonathan Ray
2. Jackson Persyn
3. Douglas Richardson
4. Justin Nguyen
5. James Reilly
6. Christian Rodriguez

Setup
	- Please follow the following general steps to run this project.
		- 1. You will need to unzip the JavaDB.zip folder. 
		- 2. Download and install MySQL Server Community Edition and make sure to leave the box checked for the MySQL Workbench and use "1234" as the passcode to the root account.
			- A. Open MySQL Workbench and authenticate with the server when prompted with the root credential you used during your Server install.
			- B. In MySQL Workbench create a new schema with the name, "test". 
				- i. NOTE: THIS IS RECOMMENDED: If at any time you would like to any time you would like to change the name of the schema (database name), just create the database name you would like and change it in the appropriate place in the Driver.java class. You will need to scroll down and edit the getConnection() method and change "test", "root", and "1234" with the name of the schema you created, the username for MySQL, and the password for MySQL. You can now run the program.
		- 3. If you wish to use Eclipse.
			- A. Download and Install the Eclipse Java IDE
			- B. Then you are able to import the project into the Eclipse by right-clicking the project explorer and click import
			- C. Under the "general" folder, select "Project from Folder or Archive" and select the JavaDB folder from the file path that you unzipped the JavaDB.zip folder to
			- D. Right-click the Driver.java class and select Run

Initial Programming Environment:
	- Our group used the following tools and programs in our programming / testing environment:
		- We used Java SE 14.0
		- MySQL version 8.0.21
		- MySQL Community Edition Server
		- Eclipse IDE 
