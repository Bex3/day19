package sample;

import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.scene.canvas.GraphicsContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by bearden-tellez on 9/2/16.
 */
public class ServerThisServer implements Runnable{
    Socket clientSocket = null;
    GraphicsContext gc = null;

    public ServerThisServer(GraphicsContext gc) {
        this.gc = gc;
    }

//    public ServerThisServer() {
//
//    }

    public void run(){
        try {
            serverRunning();
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }



    private void serverRunning() throws IOException{
  //  public static void main(String[] args) {
        System.out.println("Booting server");
        try {
            ServerSocket serverListener = new ServerSocket(8005);
            System.out.println("Ready to listen");

            while (true) {
                Socket incomingConnection = serverListener.accept();
                ConnectionHandler handler = new ConnectionHandler(incomingConnection, gc);
                Thread theThread = new Thread(handler);
                theThread.start();


            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}