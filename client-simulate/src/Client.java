
import java.time.LocalTime;

import UploadHandle.Sender;
import UploadHandle.ListProcesses;

public class Client {
    public static void main(String[] args) throws Exception {
        String toIPAddr = "127.0.0.1";
        int port = 2626;
        String txtfilePath = "asset\\processes";
        // String imgfilePath = "asset\\blue.jpg";

        ListProcesses listProcesses = new ListProcesses();

        Thread thread = new Thread();

        while (true) {
            listProcesses.write(txtfilePath);
            Sender sender = new Sender(toIPAddr, port);
            sender.sendFile(txtfilePath);
            thread.sleep(3000);
        }

        // sender.close();
    }
}
