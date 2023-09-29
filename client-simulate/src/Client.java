import java.io.*;

import UploadHandle.FileSender;
import UploadHandle.ListProcesses;

public class Client {
    public static void main(String[] args) throws IOException {
        String toIPAddr = "192.168.234.1";
        int port = 2626;
        String txtfilePath = "asset\\processes.txt";
        String imgfilePath = "asset\\blue.jpg";

        // ListProcesses listProcesses = new ListProcesses();
        // listProcesses.write(txtfilePath);

        FileSender sender = new FileSender(toIPAddr, port, imgfilePath);
        sender.send();
    }
}
