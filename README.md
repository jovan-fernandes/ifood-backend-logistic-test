# Instructions:

- Before running, you will need Docker and Docker Compose installed (https://docs.docker.com/install/)  

- How to run?
    ``` docker-compose up ```

- There is a Swagger for easily test the application: http://localhost:8081/swagger-ui.html    

- Note: Everytime you rerun the application, the mongodb database is cleared and some data are putted.     
     

# iFood Backend Test - Logistic - Vehicle Routing Problem

You have been hired to create a completely new micro-service to route orders minimizing overall distance. This problem is well known as Vehicle Routing Problem, please find a reference for it at (https://en.wikipedia.org/wiki/Vehicle_routing_problem). 

Your micro-service must provide us the following endpoints with these minimal mandatory attributes:
- Restaurants: create, update and get by id
`{"id": 1, "lat" : "0.0", "lon" : "0.0"}`
- Clients: create, update and get by id
`{"id": 1, "lat" : "0.0", "lon" : "0.0"}`
- Orders: Create, get by id and query by restaurant with optional filtering by delivery time. The pickup time is the moment when the dish is ready and the delivery time is the due time to deliver the dish.
```json
{
    "id": 1,
    "restaurantId": 1,
    "clientId": 1,
    "pickup": "2018-06-05T13:37:00Z",
    "delivery": "2018-06-05T13:54:00Z"
}
```
_* This example ilustrates the problem. Please, use the right types for the attributes._

Your micro-service also must provide an endpoint which returns the solution proposed following the example below.
```json
{
	"routes": [
		{
			"orders": [1, 2, 5]
		}
		{
			"orders": [3]
		}
	]
}
```
_* Same consideration. The ids are the orders that are picked and delivered in one route by a driver._

## You must consider that:

- You have limitless drivers.
	-> but it is better to minimize the number of drivers.
- A worker can do at most 3 deliveries of the same restaurant at the same time (i.e. in the same route)
- A worker can only do deliveries of a single restaurant at a time (i.e. in the same route)
- You should try to avoid delivering an order after the delivery time
- You CAN'T pick up an order before the pickup time
- The driver is on the restaurant at the pickup time
- Consider that the driver goes 0.1 units in line each 5 minute.
- Each order must be optimized only once
- You should elaborate the algorithm to solve the Vehicle Routing Problem (do not use libraries for that)

Hints: your system is growing and the constraints can change, furthermore, new constraints can be added. Prepare your system to be easy to change/configure and easy to add new constraints.

## Non-functional requeriments

Now consider that your micro-services are a success inside IFood, it must be prepared to be fault tolerant, responsive and resilient.

Use whatever tools and frameworks you feel comfortable with, and briefly elaborate on your solution, architecture details, choice of patterns and frameworks.
	**The only restriction is to use Java.**

Also, make it easy to deploy/run your service(s) locally (consider using some container/vm solution for this).
We expect that you solve this test in about 8 hours. Once done, share your code with us.