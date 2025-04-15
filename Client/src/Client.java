import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String serverUrl = "http://localhost:8080/weather/";
        Scanner scanner = new Scanner(System.in);

        HttpURLConnection conn = null;

        try {
            while (true) {
                System.out.print("Enter city name (or 'quit' to exit): ");
                String city = scanner.nextLine();

                if (city.equalsIgnoreCase("quit")) break;

                URL url = new URL(serverUrl + city);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        System.out.println(line);
                    }

                    System.out.println("----------------------------------------------------------\n");
                }
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }

        conn.disconnect();
        scanner.close();

    }
}