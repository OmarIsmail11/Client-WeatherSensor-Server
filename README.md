# Weather System API

## About
This is a project where a server receives weather updates from several sensors via TCP connections and allows multiple HTTP clients to fetch live weather data using a LAN connection.

Built Using Java and Spring Boot.

This project was an assignment for the CMPS211 - Advanced Programming Techniques Course.

## System Features
### Sensor (Java TCP client)

● Is a console-based client that connects to the TCP server.

● Allows users to enter a city name and a value for temperature and humidity.

● Sends the details entered and the timestamp to the server.

● The sensor receives an acknowledgement from the server (e.g., "Data received successfully").

![image](https://github.com/user-attachments/assets/6f167200-0230-4a61-8d4f-da7062970f55)


### Client (Using Java HTTP Client)

● Takes a city name from the user through the console.

● Sends a GET request to the server to fetch the weather information of the city.

● Displays the weather (temperature and humidity) in the console.

● If no data exists for the requested city, the server returns "No data available".

![image](https://github.com/user-attachments/assets/b2a82c05-f446-4ed0-9e99-cec09ef4e678)


### Server (Spring Boot REST API + TCP Server)

![image](https://github.com/user-attachments/assets/a5b8b5ff-53df-4d7c-934a-b2dce9026cc7)

#### 1. TCP Server

● Uses Java ServerSocket to listen for incoming connections from sensors.

● Handles multiple TCP clients using multithreading (each sensor connection is handled in a separate thread).

● Receives weather information from the sensor.

● Sends an acknowledgment back to the sensor.

● Stores only the latest weather reading for each city.

#### 2. HTTP Server using Spring Boot
Implemented a GET endpoint to do the following:

● Receives the city name from the user.

● Returns the weather information (e.g. {“city”: “Cairo”, “temp”: 25, “humidity”: “40%”}) or an error message (e.g. {“message”: "No data available"}).

● Ensures thread safety when handling concurrent HTTP and TCP requests.

● Spring Boot automatically handles multiple HTTP requests in parallel. However, since multiple sensors update shared data concurrently, threads are used for reading and writing weather data.


## Collaborators
- Omar Ahmed Asker
- Omar Ahmed Reda
  
## Demo



https://github.com/user-attachments/assets/ee53e83a-fc11-4d06-abbb-cd29d006709c

