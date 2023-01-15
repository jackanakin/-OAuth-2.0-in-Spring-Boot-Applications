# Realm, Client, Token and Code Exchange

### Configure the realm and it's client application

1. Create a Realm with ID: app
2. Add an user and set a password
3. Add a new Client to the Realm:
  a) Client ID: my-resource-server
  b) Client type: OpenID Connect
  c) Set a Valid redirect URIs: http://localhost:8081/callback

### 1. Authorization Code Flow Example (Standard Flow)
Client login in Keycloack realm and is redirect to the callback URI after
* Check "Standard flow" in realm settings
* Login and get the code
* Exchange the code for token

1. Simulate user login:
```
http://localhost:8080/realms/app/protocol/openid-connect/auth?client_id=my-resource-server&redirect_uri=http://localhost:8081/callback&state=adwadaw&response_type=code&scope=openid
```
Authenticate and copy the "code" generated in the URL
2. Exchange the "code" for the token:
```
var myHeaders = new Headers();
myHeaders.append("Content-Type", "application/x-www-form-urlencoded");

var urlencoded = new URLSearchParams();
urlencoded.append("code", "code");
urlencoded.append("grant_type", "authorization_code");
urlencoded.append("client_id", "my-resource-server");
urlencoded.append("client_secret", "client_secret");
urlencoded.append("redirect_uri", "http://localhost:8081/callback");
urlencoded.append("scope", "openid profile email phone");

var requestOptions = {
  method: 'POST',
  headers: myHeaders,
  body: urlencoded,
  redirect: 'follow'
};

fetch("http://localhost:8080/realms/app/protocol/openid-connect/token", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));
```
### 2. Password Credentials Flow Example
Client exchanges user/password directly for token
* Check "Direct access grants" in realm settings
* Post and get the token

```
var myHeaders = new Headers();
myHeaders.append("Content-Type", "application/x-www-form-urlencoded");
myHeaders.append("Cookie", "AUTH_SESSION_ID_LEGACY=0e055361-98a7-4679-9fc9-43a896311d4e");

var urlencoded = new URLSearchParams();
urlencoded.append("grant_type", "password");
urlencoded.append("client_id", "my-resource-server");
urlencoded.append("client_secret", "client_secret");
urlencoded.append("scope", "openid profile email phone");
urlencoded.append("username", "jardel");
urlencoded.append("password", "mypassword");

var requestOptions = {
  method: 'POST',
  headers: myHeaders,
  body: urlencoded,
  redirect: 'follow'
};

fetch("http://localhost:8080/realms/app/protocol/openid-connect/token", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));
```
### 3. PKCE Example
Client is SPA or native app (not secure)
Same as Standard Flow but with code challenge

1. Use PkceutilApplication to generate challenge and verifier
2. Simulate user login
```
http://localhost:8080/realms/app/protocol/openid-connect/auth?client_id=my-resource-server&redirect_uri=http://localhost:8081/callback&state=adwadaw&response_type=code&scope=openid&code_challenge_method=S256&code_challenge=random_code_challenge
```
Authenticate and copy the "code" generated in the URL
3. Exchange the "code" alongside code verifier for the token:
```
var myHeaders = new Headers();
myHeaders.append("Content-Type", "application/x-www-form-urlencoded");
myHeaders.append("Cookie", "AUTH_SESSION_ID_LEGACY=0a6ab074-78c5-4536-b3ce-16580f4cce4d; KC_RESTART=eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJlNGRkNWVjNi0yMzU0LTQ0YWUtYTZmMC1lMGU0N2JmMGIzOWIifQ.eyJjaWQiOiJteS1yZXNvdXJjZS1zZXJ2ZXIiLCJwdHkiOiJvcGVuaWQtY29ubmVjdCIsInJ1cmkiOiJodHRwOi8vbG9jYWxob3N0OjgwODEvY2FsbGJhY2siLCJhY3QiOiJBVVRIRU5USUNBVEUiLCJub3RlcyI6eyJzY29wZSI6Im9wZW5pZCIsImlzcyI6Imh0dHA6Ly9sb2NhbGhvc3Q6ODA4MC9yZWFsbXMvYXBwIiwicmVzcG9uc2VfdHlwZSI6ImNvZGUiLCJjb2RlX2NoYWxsZW5nZV9tZXRob2QiOiJTMjU2IiwicmVkaXJlY3RfdXJpIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL2NhbGxiYWNrIiwic3RhdGUiOiJhZHdhZGF3IiwiY29kZV9jaGFsbGVuZ2UiOiJuMW9aSGRpSWZJN3NTYURhNkJhcVhkbHJBSC1CdHhFQ05WVXZHWHdDUmtVIn19.BfDL5Jep84sFEx_za8stRnP10H2IepRKyFlfkVh7SP8");

var urlencoded = new URLSearchParams();
urlencoded.append("grant_type", "authorization_code");
urlencoded.append("client_id", "my-resource-server");
urlencoded.append("client_secret", "client_secret");
urlencoded.append("code", "code");
urlencoded.append("redirect_uri", "http://localhost:8081/callback");
urlencoded.append("code_verifier", "code_verifier");

var requestOptions = {
  method: 'POST',
  headers: myHeaders,
  body: urlencoded,
  redirect: 'follow'
};

fetch("http://localhost:8080/realms/app/protocol/openid-connect/token", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));
```
### 4. Client Credentials Flow
Machine to machine flow
```
var myHeaders = new Headers();
myHeaders.append("Content-Type", "application/x-www-form-urlencoded");
myHeaders.append("Cookie", "AUTH_SESSION_ID_LEGACY=0a6ab074-78c5-4536-b3ce-16580f4cce4d");

var urlencoded = new URLSearchParams();
urlencoded.append("grant_type", "client_credentials");
urlencoded.append("client_id", "my-resource-server");
urlencoded.append("client_secret", "client_secret");
urlencoded.append("scope", "openid profile email phone");

var requestOptions = {
  method: 'POST',
  headers: myHeaders,
  body: urlencoded,
  redirect: 'follow'
};

fetch("http://localhost:8080/realms/app/protocol/openid-connect/token", requestOptions)
  .then(response => response.text())
  .then(result => console.log(result))
  .catch(error => console.log('error', error));
```
