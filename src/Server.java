import sample.ConnectionHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by bearden-tellez on 9/2/16.
 */
public class Server {
    public static void main(String[] args) {
        System.out.println("Booting server");
        try {
            ServerSocket serverListener = new ServerSocket(8005);
            System.out.println("Ready to listen");

            while (true) {
                Socket incomingConnection = serverListener.accept();
                ConnectionHandler handler = new ConnectionHandler(incomingConnection);
                Thread theThread = new Thread(handler);
                theThread.start();
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}