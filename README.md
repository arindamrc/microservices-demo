# Microservices Demo

The problem statement is defined [in this text file](Code_evaluation.txt).

The main target of the application is to filter incoming requests and log them based on their validity. Although the requirements maybe interpreted as a straightforward CRUD application, there are non-functional scalability requirements that adds to the complexity of the problem. The main talking point here is that the application will receive billions of requests per day. This rules out monolithic single server applications as they would not be able to handle the load. Thus the requirements are implemented as a few separate microservices, each of which are capable of having more than one instance running simultaneously.

The components of this application are:

1. The [**gateway**](gateway): This uses Netflix's *Zuul* to implement the *API Gateway pattern*. All requests are routed through the gateway to the respective microservices. Even internal communications between microservices also use the gateway as the intermediary. This ensures that all applications are loosely coupled and location agnostic.
2. The [**service-registry**](service-registry): This provides *Service Discovery* facilities to the microservices. This is a *Eureka* server. All microservices register to the service-registry to allow themselves to be reachable.
3. The [**customer-service**](customer-service): To make the application loosely coupled, the customer database is accessible only through this service. Using this service customer addition, deletion, activation and deactivation is possible. This service publishes deletion events on a *RabbitMQ* so that any other listening services may receive notifications and act accordingly.
4. The [**filter-service**](filter-service): This application handles the crux of the request filtering logic as outlined in the [requirements file](Code_evaluation.txt). It also listens for the customer deletion events published on the RabbitMQ and deletes the statistics for those customers who have been deleted. It also makes RESTful queries to the customer-service to find out if a customer id exists and whether it is active.

Splitting up the tasks into microservices allows us to scale the application to our needs. Each of these microservices can have multiple instances which allows them to handle more requests. Load Balancing is baked into the Eureka service discovery and registration. Ribbon is used to choose between instances of available services. It is a simple configuration annotation that is added to each of these projects. The customer-service and the filter-service use REST to expose their api endpoints.

However, there is still scope for improvement in the architecture:

1. Server-side caching can be added to the services in customer-service. Since the results returned by this service are mostly static (just customer details which do not change very frequently) it lends itself nicely to caching strategies. Spring cache abstraction can be used to handle the implementation.
2. Using a simpler datastore than a relational database to log the requests. Simple key-value stores like REDIS are far more suitable for the job. It would be massively scalable and much faster than traditional RDBMS system in this particular scenario.
3. Although multiple instances of each service could be spun up, they would all share the same database, making it a bottleneck. The simple file-based or in-memory database H2 in this project is not really suitable for a production level system (although H2 does provide simple clustering capabilities).

There is also a separate User Interface provided in the project called [**ui**](ui). It provides a basic user interface to access and test the services.

To run this application, the following steps must be performed:

1. Install RabbitMQ. An installation script is provided in the project root folder called [**rmq_install.sh**](rmq_install.sh). It has been tested only for Ubuntu 16.04. 
2. Run the RabbitMQ service:

    `sudo service rabbitmq-server start`

3. Go to the root of each project and type the following:

    `$./mvnw spring-boot:run`

    This must be done for all four projects in this order:

    1. service-registry
    2. gateway
    3. customer-service
    4. filter-service

4. Install jetty. You can download it [here](https://www.eclipse.org/jetty/download.html). Extract it to the location of your choice. Go to the root of the project called *ui* and execute the following command:

    `$java -jar <jetty_extract_location>/start.jar`.

    This starts up the UI at [localhost:9090/ui](http://localhost:9090/ui).



