# pp-checkout-poc
PayPal Checkout Java Quarkus


# Requirements
Java 1.8 Above or Graalvm(optional)</br>
Maven</br>
Paypal Sandbox Account</br>

# Quick Setup

Install dependencies:
```bash
mvn package
```

To run
```bash
mvn compile quarkus:dev
```

The package Id can for this sample can be 1 or 2</br>
1 = 1000php</br>
2 = 2000php</br>

POST</br>
http://localhost:8080/paypal/make/payment?packageId=2 </br>

it will return a redirect url for paypal transaction. Open the link to procced the transaction.
