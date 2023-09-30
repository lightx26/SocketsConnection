import java.io.*;
import java.net.*;
import ClientHandler.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 2626;
        int backLog = 10;
        InetAddress address = InetAddress.getByName("0.0.0.0");
        // ServerSocket serverSocket = new ServerSocket(port);
        ServerSocket serverSocket = new ServerSocket(port, backLog, address);

        System.out.println("Server is listening on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Create a thread to handle the client's request
            Thread clientHandler = new ClientHandler(clientSocket);
            clientHandler.start();
        }

        // serverSocket.close();
    }
}
