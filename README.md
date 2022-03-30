# uK295_Mischa_Strauss

### How to setup my project:
1. Download and run maildev in docker:
   ```$ docker run -p 1080:1080 -p 1025:1025 soulteary/maildev```
2. Download and run postgres docker:
   ```docker run -p 5432:5432 -e POSTGRES_PASSWORD=30DBrootPW48 --name uek295db postgres```
3. Clone the latest release:
    ssh: ```git clone git@github.com:mimasala/uK295_Mischa_Strauss.git```
    https: ```git clone https://github.com/mimasala/uK295_Mischa_Strauss.git```

4. Open ```sbdemo01``` in intelliJ and run the application.
   

### Run Postman tests:
1. import ```uk295Tests.postman_collection.json``` in the resources folder in postman
2. run collection

#### "Special" Features:
-   Confirm your account through mail
-   Unconfirmed Accounts will be deleted every minute
-   Add Authorities to Users
