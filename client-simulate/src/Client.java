
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalTime;

import UploadHandle.Sender;
import UploadHandle.ListProcesses;

public class Client {
    public static void main(String[] args) throws Exception {
        String toIPAddr = "192.168.234.1";
        int port = 2626;
        String txtfilePath = "asset\\processes-client1.txt";
        String mp4filePath = "asset\\video.mp4";
        // String imgfilePath = "asset\\blue.jpg";

        // ListProcesses listProcesses = new ListProcesses();

        // Thread thread = new Thread();

        // while (true) {
        // listProcesses.write(txtfilePath);
        Socket clienSocket = new Socket(toIPAddr, port);
        Sender sender = new Sender(clienSocket);
        sender.sendFile(txtfilePath);
        // thread.sleep(3000);
        // }

        sender.close();
    }
}
