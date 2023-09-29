import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        int port = 2626;
        int backLog = 10;
        InetAddress address = InetAddress.getByName("192.168.234.1");
        // ServerSocket serverSocket = new ServerSocket(port);
        ServerSocket serverSocket = new ServerSocket(port, backLog, address);

        System.out.println("Server is listening on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress().getHostName());

            // Create a thread to handle the client's request
            Thread clientHandler = new ClientHandler(clientSocket);
            clientHandler.start();
        }

        // serverSocket.close();
    }
}

class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // Input stream to receive data from the client
            InputStream inputStream = clientSocket.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
            DataInputStream dataInputStream = new DataInputStream(bufferedInputStream);

            // Read the file name and size from the client
            String fileName = dataInputStream.readUTF();
            dataInputStream.readLong();

            System.out.println("Receiving file: " + fileName);

            // Output stream to save the received file
            String filePath = "downloads\\" + fileName;

            // Check extention of file
            String fileExtention = filePath.substring(filePath.lastIndexOf('.') + 1);
            // then write file to server's disk
            if (fileExtention.equals("txt"))
                writeTextfile(filePath, dataInputStream);
            else
                writeBinaryfile(filePath, dataInputStream);

            // Close socket
            clientSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeTextfile(String filePath, DataInputStream dataInputStream) {
        try {
            InputStreamReader in = new InputStreamReader(dataInputStream);
            BufferedReader reader = new BufferedReader(in);
            FileWriter fileWriter = new FileWriter(new File(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                fileWriter.write(line);
                fileWriter.write("\n");
            }

            fileWriter.close();
            in.close();
            dataInputStream.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeBinaryfile(String filePath, DataInputStream dataInputStream) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            byte[] buffer = new byte[1024];
            int bytesRead;
            long totalBytesReceived = 0;

            while ((bytesRead = dataInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
                totalBytesReceived += bytesRead;
                // fileSize -= bytesRead;
            }

            System.out.println("File received successfully.\nSize: " + totalBytesReceived + " bytes");

            // Close streams
            fileOutputStream.close();
            dataInputStream.close();
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
