import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.util.Scanner;


public class Main {
    public static void main(String[] args)
    {
        try
        {
            Socket socket = new Socket("localhost", 6666);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter city: ");
            String city = scanner.nextLine();
            System.out.println("Enter temperature: ");
            String temperature = scanner.nextLine();
            System.out.println("Enter humidity: ");
            String humidity = scanner.nextLine();

            String timestamp = LocalDateTime.now().toString();

            String message = city + "," + temperature + "," + humidity + "," + timestamp;
            out.println(message);

            String response = in.readLine(); // Get server acknowledgment
            System.out.println("Server: " + response);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}