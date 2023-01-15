# Realm, Client, Token and Code Exchange

### Configure the realm and it's client application

1. Create a Realm with ID: app
2. Add an user and set a password
3. Add a new Client to the Realm:
  a) Client ID: my-resource-server
  b) Client type: OpenID Connect
  c) Set a Valid redirect URIs: http://localhost:8081/callback
  d) Leave only Standard flow checked, Client authentication and Authorization toggled

### Usage example

1. Simulate the user login:
  Go to http://localhost:8080/realms/app/protocol/openid-connect/auth?client_id=my-resource-server&redirect_uri=http://localhost:8081/callback&state=adwadaw&response_type=code&scope=openid
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
