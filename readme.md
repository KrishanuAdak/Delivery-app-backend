# Real-Time Delivery Application

A real-time delivery application built using
Spring Boot microservices.

## Technologies

- Java 17
- Spring Boot
- Spring Security
- JWT
- Apache Kafka
- Redis
- MySQL
- WebSocket

## Microservices

| Service | Responsibility |
|---|---|
| Auth Service | Authentication and OTP |
| User Service | User management |
| Driver Service | Driver management |
| Ride Service | Ride booking |
| Chat Service | Real-time messaging |
| API Gateway | Request routing |

## Architecture

The application follows a microservices architecture.

Services communicate using REST APIs, Kafka
and WebSocket, 
All services are registered in eureka server.

