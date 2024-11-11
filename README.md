# Nordnet assignment

## Java version 17 
## Features

- **Create Order**: Submit a new order specifying ticker, order side, volume, price, and currency.
- **Fetch Order by ID**: Retrieve an existing order by its unique identifier.
- **Order Summary**: Get summary statistics (average, max, min price) for a specific ticker, order side, and date.

### Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/hillallo/orderbook.git
   cd orderbook-microservice
   ```
2. Build the project
    ```bash
    mvn clean install
   ```
3. Run the application
      ```bash 
    mvn spring-boot:run   
      ```

4.  Use Swagger-UI to test the APIs
    ```bash 
    http://localhost:8080/swagger-ui/index.html/
    ```