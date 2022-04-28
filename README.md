# barak-banking
Banking Application Assignment: Backend Engineering Role

### Features

- Creation of account
- Deletion of account
- Money deposit
- Money withdrawal
- Money transfer
- International transfer
- Getting the balance
- List of account


### API Contract

#### Signup
URL
```
http://localhost:8080/api/v1/customer/signup
```
Request
```
{
  "Email": "email@gmail.com",
  "password": "password",
  "firstName": "mostafa",
  "lastName": "rady",
  "eidNo": "748-1992-000-00",
  "address": "address"
}
```
Response :- 200
```
{
  "id": 1,
  "cif": "1519056425",
  "firstName": "mostafa",
  "lastName": "rady",
  "eidNo": "748-1992-000-00",
  "address": "address",
  "password": "$2a$10$qPA3T7A7r3coS2mN1IZrA.fLsb9NuTa4wHenTx5mTkma8oEGSzsBa",
  "email": "email@gmail.com",
  "accounts": null
}
```
Response:- validation error
```
{
  "statusCode": 400,
  "timestamp": "2022-04-28T08:50:27.100+00:00",
  "message": "[FieldName:-email, ErrorMessage:-Email is required]",
  "description": "VALIDATION_ERROR"
}
```
Response:- existing customer
```
{
  "statusCode": 500,
  "timestamp": "2022-04-28T08:59:25.523+00:00",
  "message": "There is a customer exist with same email",
  "description": "INTERNAL_SERVER_ERROR"
}
```
#### Login
URL:-
```
http://localhost:8080/api/v1/customer/auth
```
Request:-
```
{
  "Email": "email@gmail.com",
  "password": "password"
}
```
Response:-
```
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbEBnbWFpbC5jb20iLCJleHAiOjE2NTExNTUyODUsImlhdCI6MTY1MTEzNzI4NX0.9tdTwnWIq4vUrz08HL2SiuCMDhQaOd2BDgdoUryZKurPnX4aDObOc4MebRLAHWoEZ1FysBleYhVVttzVh_Ej3Q"
}
```
```
{
  "statusCode": 401,
  "timestamp": "2022-04-28T09:48:02.785+00:00",
  "message": "Customer not found with email: string",
  "description": "UNAUTHORIZED_ERROR"
}
```
#### Create Account
URL:-
```
http://localhost:8080/internal/v1/account
```
Request:-
```
{
  "currency": "USD",
  "accountType": "CURRENT"
}
```
Response:-
```
{
    "accountNo": 2,
    "iban": null,
    "availableBalance": 0,
    "customerId": null,
    "currency": "USD",
    "accountType": "CURRENT"
}
```
#### Account List &  Balance
URL:-
```
http://localhost:8080/internal/v1/account/
```
Response:-
```
[
    {
        "accountNo": 2,
        "availableBalance": 0.00,
        "currency": "USD",
        "accountType": "CURRENT"
    }
]
```
#### Close Account
URL:-
```
http://localhost:8080/internal/v1/account/account?accountNo={accountNo}
```
#### Money Deposit
URL:-
```
http://localhost:8080/webhook/v1/money/deposit
```
Request:-
```
{
  "accountNo": 3,
  "amount": 4000
}
```
Response:-
```
{
    "referenceNumber": "9bcd5f02-5d87-4bf2-bcf9-057fef0697f3",
    "transactionAmount": 4000,
    "accountNo": 3,
    "transactionType": "DEBIT",
    "transactionDescription": "DEPOSIT"
}
```

#### Money Withdrwal
URL:-
```
http://localhost:8080/webhook/v1/money/withdraw
```
Request:-
```
{
  "accountNo": 3,
  "amount": 500
}
```
Response:-
```
{
    "referenceNumber": "b6ad8da9-4167-44c8-99a8-e9785bb3a7eb",
    "transactionAmount": 500,
    "accountNo": null,
    "transactionType": "DEBIT",
    "transactionDescription": "WITHDRAW"
}
```

#### Local Transfer
URL:-
```
http://localhost:8080/internal/v1/transfer/local
```
Request:-
```
{
  "fromAccountNo": 3,
  "toAccountNo": 2,
  "amount": 500
}
```
Response:-
```
{
    "referenceNumber": "91db4332-cf4c-4211-820e-73da51ec98f4",
    "transactionAmount": 500,
    "accountNo": null,
    "transactionType": "CREDIT",
    "transactionDescription": "LOCAL_TRANSFER"
}
```
```
{
    "statusCode": 500,
    "timestamp": "2022-04-28T12:31:30.784+00:00",
    "message": "You don't have enough balance",
    "description": "INTERNAL_SERVER_ERROR"
}
```
#### International Transfer
URL:-
```
http://localhost:8080/internal/v1/transfer/International
```

#### Sweagger
`
http://localhost:8080/swagger-ui/index.htm
`
                
### FlowChart

```flow
st=>start: signup
op=>operation: Login operation
cond=>condition: Successful Yes or No?
e=>end: token

st->op->cond
cond(yes)->e
cond(no)->op
```

### Sequence Diagram
                    
```seq
FE->BE: signup 
Note right of BE: request validation 
BE-->FE: user 
FE->>BE: login
Note right of BE: validate username\npassword 
BE-->FE: token 
FE->BE: create account 
Note right of BE: validate acount details 
BE->DB:save account
BE-->FE: accountNo 
FE->BE: money-deposit
BE->BE:check-account-active
BE->DB:topup account balance \n save transaction
BE-->FE: reference number 
FE->BE: money-withdrwal
BE->BE:check-account-active \n check-balance
BE->DB:deduct from account balance \n save transaction
BE-->FE: reference number 
FE->BE: local-transfer
BE->BE:check-from-account-active
BE->BE:check-from-account-balance
BE->DB:deduct from account balance 
BE->DB:save from account transaction 
BE->BE:check-to-account-active
BE->DB:topup to account balance 
BE->DB:save to account transaction 
BE-->FE: reference number 
```

### End
