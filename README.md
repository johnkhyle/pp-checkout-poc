# pp-checkout-poc
PayPal Checkout Java Quarkus


#Requirements
Java 1.8 Above or Graalvm(optional)
Maven
Paypal Sandbox Account

#Quick Setup

Install dependencies:
```bash
mvn package
```

To run
```bash
mvn compile quarkus:dev
```

The package Id can for this sample can be 1 or 2
1 = 1000php
2 - 2000php

POST
http://localhost:8080/paypal/make/payment?packageId=2

it will return a redirect url for paypal transaction
