## Running the application
  
Main method is located in Application class in com.worldpay

**Below is an example to post an offer(offer expiry in ms):**

Method: POST

URL: localhost:8080/offers

Body: 


{ 
   "description":"offer for TESLA",
   "productId": 2,
   "currency":"GBP",
   "price": 44000.44,
   "expiry": 100000
}

Example response:

{
    "id": 1,
    "description": "offer for TESLA",
    "productId": 2,
    "currency": "GBP",
    "price": 44000.44,
    "expiry": 100000
}

**Example to query the above offer(place offer id at the end of the url):**

Method: GET

URL: localhost:8080/offers/1

Example response:

{
    "id": 1,
    "description": "offer for TESLA",
    "productId": 2,
    "currency": "GBP",
    "price": 44000.44,
    "expiry": 100000
}


**Example to delete the above offer(place offer id at the end of the url):**

Method: DELETE

URL: localhost:8080/offers/1

Example response:

"offer id: 1 deleted"

### Products

Products are located in src/main/resources/productList.csv, they will be persisted to the database at the start of the application. More products can be added but application will need to be restarted for changes to take effect.

**Assumptions**

productIds in productList.csv will be in chronological order

controller to persist products on demand not required
