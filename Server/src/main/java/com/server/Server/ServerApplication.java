package com.server.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.*;

@SpringBootApplication
public class ServerApplication {
	private static ConcurrentHashMap<String, String> database = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		new Thread(ServerApplication::startTcpServer).start(); // Start TCP server
	}
	@RestController
	@RequestMapping("/weather")
	public static class Controller {
		@GetMapping("/{city}")
		public Map<String, String> getWeather(@PathVariable String city) {
			String weather = database.get(city);
			if (weather == null) {
				return Map.of("message", "No data available");
			}
			return Map.of("city", city, "weather", weather);
		}
	}
	private static void startTcpServer() {
		try (ServerSocket serverSocket = new ServerSocket(6666)) {
			System.out.println("TCP Server is running...");
			while (true) {
				Socket socket = serverSocket.accept();
				new Thread(() -> handleClient(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void handleClient(Socket socket) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

			String data = in.readLine();
			String[] parts = data.split(",");
			if (parts.length == 4) {
				database.put(parts[0], "Temperature: " + parts[1] + "°C, Humidity: " + parts[2] + "%, Timestamp: " + parts[3]);
				out.println("Data received successfully");
			} else {
				out.println("Invalid data format"); // Error message
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
