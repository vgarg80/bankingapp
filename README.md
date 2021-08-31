**Steps to deploy the application on local machine**

1)  Unzip the project and import the project as maven project. let it update all maven dependencies. if you get error in POM.XML then check your config and update accordingly.

2) Make sure you have MySQL installed on your machine. Update **application.properties** with your MySQL username and password.

3) Create a schema with name as **transactional** in MySQL.

4) start the server, 2 tables will be created automatically in schema. 

5) To enter data in tables, run URL **localhost:8080/save** (change port as per your application) as type post and JSON as body with sample JSON object
>**{
   "mobilenumber":"9810537",
   "creditLimit": "10000"
>}**

Repeat this with multiple data

6) To login **localhost:8080/login**, sample JSON, token will be generated which will be further used
>**{
	"mobilenumber": "9810537"
>}**

7) To enter spend transactions call **localhost:8080/spend** sample JSON, also add authorization code retrieved from /login API
>**{
    "transactionDate":"2020-08-31",
    "description": "this is my transaction",
    "amount": "10"
>}**
