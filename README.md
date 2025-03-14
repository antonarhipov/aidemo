# Convex Hull Algorithm Demo

This project demonstrates different algorithms for computing the convex hull of a set of points. It provides a RESTful API for computing convex hulls and a web interface for visualizing the results.

## Features

- Compute convex hulls using different algorithms:
  - Graham's Scan (O(n log n) time complexity)
  - Gift Wrapping / Jarvis March (O(nh) time complexity, where h is the number of points on the hull)
- RESTful API with OpenAPI documentation
- Performance tracking and comparison between algorithms
- Web dashboard for visualizing results and statistics
- Persistent storage of calculation results

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application:

```bash
./gradlew bootRun
```

The application will be available at http://localhost:8080

## API Documentation

The API documentation is available at:
- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI Specification: http://localhost:8080/api-docs

## Web Dashboard

The web dashboard is available at http://localhost:8080 and provides:
- Performance comparison between algorithms
- Usage statistics
- Recent calculation results
- Test interface for running convex hull calculations

## Database Console

The H2 database console is available at http://localhost:8080/h2-console with the following connection details:
- JDBC URL: jdbc:h2:file:./data/convexhull
- Username: sa
- Password: password

## API Endpoints

### Convex Hull Endpoints

- `POST /api/convexhull`: Compute convex hull using the default algorithm
- `POST /api/convexhull/{algorithm}`: Compute convex hull using the specified algorithm
- `GET /api/convexhull/algorithms`: Get available algorithms

### Calculation Results Endpoints

- `GET /api/results`: Get all calculation results
- `GET /api/results/algorithm/{algorithmType}`: Get calculation results by algorithm type
- `GET /api/results/stats/average-times`: Get average calculation times for all algorithm types

## Project Structure

- `src/main/java/me/arhan/aidemo/math`: Convex hull algorithms
- `src/main/java/me/arhan/aidemo/controller`: REST controllers
- `src/main/java/me/arhan/aidemo/service`: Business logic
- `src/main/java/me/arhan/aidemo/entity`: Database entities
- `src/main/java/me/arhan/aidemo/repository`: Data access layer
- `src/main/java/me/arhan/aidemo/dto`: Data transfer objects
- `src/main/java/me/arhan/aidemo/config`: Configuration classes
- `src/main/resources/static`: Web interface

## License

This project is licensed under the MIT License - see the LICENSE file for details.