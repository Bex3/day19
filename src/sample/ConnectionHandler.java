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
    private Stroke stroke = null;


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



        Platform.runLater(new RunnableGC(gc, stroke));



        String inputLine;
        System.out.println(in.readLine());
        System.out.println(in.readLine());

        while ((inputLine = in.readLine()) != null) {
            System.out.println("test");
            int counter = 2;
            while(counter != 0) {
                if ((inputLine.split("=")[0]).equals("gcSender=")) {
                    gc = jsonRestoreGC((inputLine.split("=")[1]));
                    counter--;
                } else if ((inputLine.split("=")[0]).equals("strokeSender=")) {
                    gc = jsonRestoreGC((inputLine.split("=")[1]));
                    counter--;
                }
            }
            Main myMain = new Main ();
            myMain.startSecondStage();
            Platform.runLater(new RunnableGC(gc, stroke));
            counter = 0;
        }

/*

            String serverResponse = in.readLine();
            Scanner inputScanner = new Scanner(System.in);
            String inputLine = inputScanner.nextLine();
*/

        clientSocket.close();
    }

    public GraphicsContext jsonRestoreGC(String jsonTD) {
        JsonParser toDoItemParser = new JsonParser();
        GraphicsContext item = toDoItemParser.parse(jsonTD, GraphicsContext.class);

        return item;
    }

    public Stroke jsonRestoreStroke(String jsonTD) {
        JsonParser toDoItemParser = new JsonParser();
        Stroke item = toDoItemParser.parse(jsonTD, Stroke.class);

        return item;
    }


}



