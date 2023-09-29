package UploadHandle;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class FileSender {
    // File to send
    private String filePath;
    // Destination's IP address
    private String serverAddress = "127.0.0.1"; // Replace with the server's IP address
    // Destination's port
    private int serverPort = 12345; // Replace with the server's port

    // Injection IP, Port & FilePath
    public FileSender(String Address, int Port, String filePath) {
        this.filePath = filePath;
        this.serverAddress = Address;
        this.serverPort = Port;

    }

    // Send file via JavaSocket
    public void send() {
        try {
            Socket clientSocket = new Socket(serverAddress, serverPort);
            System.out.println("Connected to server");

            // Get the file name and size
            File fileToSend = new File(filePath);
            String fileName = fileToSend.getName();
            long fileSize = fileToSend.length();

            // Output stream to send data to the server
            OutputStream outputStream = clientSocket.getOutputStream();
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
            DataOutputStream dataOutputStream = new DataOutputStream(bufferedOutputStream);

            // Send file name and size to the server
            dataOutputStream.writeUTF(fileName);
            dataOutputStream.writeLong(fileSize);

            // Input stream to read the file and send it to the server
            FileInputStream fileInputStream = new FileInputStream(fileToSend);
            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                dataOutputStream.write(buffer, 0, bytesRead);
                dataOutputStream.flush();
            }

            System.out.println("File sent successfully.\nSize: " + fileSize + " bytes");

            // Close streams and socket
            fileInputStream.close();
            dataOutputStream.close();
            clientSocket.close();

        } catch (IOException e) {

        }
    }
}
