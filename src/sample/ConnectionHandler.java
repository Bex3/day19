package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import static sun.management.snmp.jvminstr.JvmThreadInstanceEntryImpl.ThreadStateMap.Byte0.runnable;

/**
 * Created by bearden-tellez on 9/2/16.
 */
public class ConnectionHandler implements Runnable{
    Socket clientSocket = null;

    public void run() {
        try {
            handleIncomingConnections(clientSocket);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public ConnectionHandler(Socket incomingConnection) {
        this.clientSocket = incomingConnection;
    }


    private void handleIncomingConnections(Socket incomingConnection) throws IOException {
        System.out.println("Connected");

        System.out.println("clientSocket = " + clientSocket);

        System.out.println("Connected with " + clientSocket.getInetAddress().getHostAddress());

        String name = clientSocket.getInetAddress().getHostAddress();

        // once we connect to the server, we also have an input and output stream
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            Main myMain = new Main ();
            myMain.startSecondStage();

            String serverResponse = in.readLine();
            Scanner inputScanner = new Scanner(System.in);
            String inputLine = inputScanner.nextLine();

        clientSocket.close();
    }
}

