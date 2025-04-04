import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Sensor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            Socket socket;
            PrintWriter out;
            BufferedReader in;

            try {
                socket = new Socket("localhost", 6666);
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                System.out.println("Enter city (or 'quit' to exit): ");
                String city = scanner.nextLine();
                if (city.equalsIgnoreCase("quit")) break;

                System.out.println("Enter temperature: ");
                String temperature = scanner.nextLine();
                System.out.println("Enter humidity: ");
                String humidity = scanner.nextLine();

                String timestamp = LocalDateTime.now().toString();
                String message = city + "," + temperature + "," + humidity + "," + timestamp;

                System.out.println("Sending: " + message);
                out.println(message);

                String response = in.readLine();
                System.out.println("Server: " + response);
                System.out.println("----------------------------------------------------------");

            } catch (IOException e) {
                System.err.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}