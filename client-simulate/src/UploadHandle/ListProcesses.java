package UploadHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class ListProcesses {

    // private String filePath;

    // public ListProcesses() {

    // }

    public void write(String filePath) {
        try {
            // Create a process builder to run the 'tasklist' command on Windows
            ProcessBuilder processBuilder;
            if (System.getProperty("os.name").startsWith("Windows")) {
                processBuilder = new ProcessBuilder("tasklist");
            } else {
                // For Linux/macOS, you can use 'ps' command
                processBuilder = new ProcessBuilder("ps", "-e");
            }

            // Start the process
            Process process = processBuilder.start();
            InputStreamReader inputStreamReader = new InputStreamReader(process.getInputStream());

            // Read the output of the process
            BufferedReader reader = new BufferedReader(inputStreamReader);
            FileWriter fileWriter = new FileWriter(new File(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                fileWriter.write(line);
                fileWriter.write("\n");
            }

            fileWriter.close();

            // Wait for the process to finish
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Process list retrieved successfully.");
            } else {
                System.err.println("Error: Unable to retrieve process list.");
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
