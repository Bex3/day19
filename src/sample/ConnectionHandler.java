package sample;

import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import jodd.json.JsonParser;

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
    private GraphicsContext gc = null;

    public ConnectionHandler(Socket clientSocket, GraphicsContext gc) {
        this.clientSocket = clientSocket;
        this.gc = gc;
    }

    private Stroke stroke = null;


    public void run() {
        try {
            handleIncomingConnections(clientSocket);
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    private void handleIncomingConnections(Socket incomingConnection) throws IOException {
        System.out.println("Connected");

        System.out.println("clientSocket = " + clientSocket);

        System.out.println("Connected with " + clientSocket.getInetAddress().getHostAddress());

        String name = clientSocket.getInetAddress().getHostAddress();

        // once we connect to the server, we also have an input and output stream
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


//        String inputLine;
        String inputLine = in.readLine();
//        System.out.println("first line received: " + inputLine);
        //out.println("Message rec'd loud & clear, or so I think");

        System.out.println("herp");
        Stroke myStroke = jsonRestoreStroke(inputLine);
        System.out.println("derp");
        gc.strokeOval(myStroke.x, myStroke.y, myStroke.strokeS, myStroke.strokeS);
//        Platform.runLater(new RunnableGC(this.gc, myStroke));


//       while ((inputLine = in.readLine()) != null) {
           out.println("hiya");
//
        }

    public Stroke jsonRestoreStroke(String jsonString) {
        JsonParser strokeParser = new JsonParser();
        Stroke item = strokeParser.parse(jsonString, Stroke.class);

        return item;
    }

//    public class RunnableGC implements Runnable {
//
//        private GraphicsContext gc = null;
//        private Stroke stroke = null;
//
//        public RunnableGC(GraphicsContext gc, Stroke stroke) {
//            this.gc = gc;
//            this.stroke = stroke;
//        }
//
//        public void run() {
//            gc.strokeOval(stroke.x, stroke.y, stroke.strokeS, stroke.strokeS); // <---- this is the actual work we need to do
//        }
//    }


}



