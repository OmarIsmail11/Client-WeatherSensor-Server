package com.server.Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootApplication
public class ServerApplication {
	private static ConcurrentHashMap<String, Weather> database = new ConcurrentHashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		new Thread(ServerApplication::startTcpServer).start();
	}

	@RestController
	@RequestMapping("/weather")
	public static class Controller {
		@GetMapping("/{city}")
		public Map<String, String> getWeather(@PathVariable String city) {
			Weather weather = database.get(city);
			if (weather == null) {
				return Map.of("message", "No data available");
			}
			return Map.of(
					"city", city,
					"temp", weather.temperature,
					"humidity", weather.humidity
			);
		}
	}

	private static void startTcpServer() {
		try (ServerSocket serverSocket = new ServerSocket(6666)) {
			System.out.println("TCP Server is running...");
			while (true) {
				Socket socket = serverSocket.accept();
				new Thread(() -> handleSensor(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void handleSensor(Socket socket) {
		try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

			String data = in.readLine();
			String[] message = data.split(",");

			String city = message[0];
			String temperature = message[1];
			String humidity = message[2];
			String timestamp = message[3];

			Weather weather = new Weather(temperature, humidity, timestamp);
			database.put(city, weather);
			out.println("Data received successfully");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static class Weather {
		String temperature;
		String humidity;
		String timestamp;

		Weather(String temperature, String humidity, String timestamp) {
			this.temperature = temperature;
			this.humidity = humidity;
			this.timestamp = timestamp;
		}
	}
}