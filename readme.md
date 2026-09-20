# Backend dev technical test
We want to offer a new feature to our customers showing similar products to the one they are currently seeing. To do this we agreed with our front-end applications to create a new REST API operation that will provide them the product detail of the similar products for a given one. [Here](./similarProducts.yaml) is the contract we agreed.

We already have an endpoint that provides the product Ids similar for a given one. We also have another endpoint that returns the product detail by product Id. [Here](./existingApis.yaml) is the documentation of the existing APIs.

**Create a Spring boot application that exposes the agreed REST API on port 5000.**

![Diagram](./assets/diagram.jpg "Diagram")

Note that _Test_ and _Mocks_ components are given, you must only implement _yourApp_.

## Testing and Self-evaluation
You can run the same test we will put through your application. You just need to have docker installed.

First of all, you may need to enable file sharing for the `shared` folder on your docker dashboard -> settings -> resources -> file sharing.

### 1. Start the mocks and infrastructure
Start the mocks and metrics infrastructure with the following command:
```bash
docker compose up -d simulado influxdb grafana
```
Check that mocks are working with a sample request to [http://localhost:3001/product/1/similarids](http://localhost:3001/product/1/similarids).

### 2. Run the application locally
Make sure port `5000` is free, then run the Spring Boot application using the Maven Wrapper:

- **Run in development mode:**
  ```bash
  ./mvnw spring-boot:run
  ```
- **Or package and run the JAR:**
  ```bash
  ./mvnw clean package
  java -jar target/similar-products-0.0.1-SNAPSHOT.jar
  ```

You can test that the application is running by querying:
```bash
curl http://localhost:5000/product/1/similar
```

### 3. Run unit and integration tests
To run the automated tests:
```bash
./mvnw test
```

### 4. Execute performance and load tests (k6)
To execute the k6 test suite against the running application:
```bash
docker compose run --rm k6 run scripts/test.js
```
Browse [http://localhost:3000/d/Le2Ku9NMk/k6-performance-test](http://localhost:3000/d/Le2Ku9NMk/k6-performance-test) to view the real-time Grafana dashboard and results.

## Architecture and Design Decisions
This project follows **Hexagonal Architecture (Ports and Adapters)**. For detailed architectural rationale regarding framework selection, error handling, and concurrency models, see [ARCHITECTURE_DECISION.md](./ARCHITECTURE_DECISION.md).

## Evaluation
The following topics will be considered:
- Code clarity and maintainability
- Performance
- Resilience

