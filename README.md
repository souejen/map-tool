# map-tool
A service that will return the geographic (straight line) distance between two postal codes in the UK

Java SpringBoot + Hibernate for Rest API call with spock as unit test.

## Develop with: 

| 	Software	 | 	Version	 | Purpose	 | 
| 	:-----:	 | 	:-----:	 | :-----:	 | 
| 	MariaDB	| 	11.3	| 	Storage	 | 
| 	Java JDK	| 	17	| 	Java SpringBoot + Hibernate	 | 
| 	Postman	| 		| 	API call	 | 

### steps to run the backend for development
To Launch database server on local enviroment
1. Import database into MariaDB, under path "~src\main\resources\database": dump-wcc-202405280832

To run code on IDE (IntelliJ)
1. Right click on parent project and look for "Maven" then click for "Reload Project"
2. Update database connection or server information under "application.properties" file
3. Add Run Configuration with Java 17 and path for "com.wcc.DemoApplication" then Run

Call API on Postman
1. Import path "~src\main\resources\postmain-api": api-sample.postman_collection.json
2. Now u can call TO get and update it
