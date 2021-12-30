# spring-boot-keycloak-demo1

### Things todo list:

1. Clone this repository: `git clone https://hendisantik4@bitbucket.org/hendisantik4/spring-boot-keycloak-sample.git`
2. Navigate to the folder: `cd spring-boot-keycloak-sample`
3. Run the application: `mvn clean spring-boot:run`

Add New User

```shell
curl --location --request POST 'localhost:8888/keycloak/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "username": "user7",
    "password": "1234",
    "firstName": "firstName",
    "lastName": "lastName",
    "email": "naruto7@yopmail.com",
     "description": "A test user",
    "businessId": [
                    "ID1", "ID2", "ID3"
                  ]
        
}'
```

Get All Users

```shell
curl --location --request GET 'localhost:8888/keycloak/users'
```