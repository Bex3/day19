package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main extends Application {

    final double DEFAULT_SCENE_WIDTH = 800;
    final double DEFAULT_SCENE_HEIGHT = 600;
    double strokeSize = 2;
    boolean isDrawing = true;
    double xPosition;
    double yPosition;

    GraphicsContext gcSecond = null;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");

        //we're using a grid layout
        GridPane grid = new  GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25,25,25,25));
        grid.setGridLinesVisible(true);
        //ADD STUFF FROM TIYO

        // add buttons and canvas to the grid
        Text sceneTitle = new Text("Welcome to Paint application");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0);

        Button button = new Button("Sample paint button");
        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.TOP_LEFT);
        hbButton.getChildren().add(button);
        grid.add(hbButton, 0, 1); //column first then row

        Button secondButton = new Button ("Connect to a friend, if you dare");
        hbButton.getChildren().add(secondButton);
        //grid.add(SecondButton, 0, 1);


        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("I can switch to another scene here ...");
//                primaryStage.setScene(loginScene);
               startSecondStage();
            }
        });

        secondButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Connecting to a new person");
                client();
            }
        });

        // add canvas
        Canvas canvas = new Canvas(DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT-100);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.setStroke(Color.BLUE);
        gc.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
        gc.setLineWidth(5);

        canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent e) {
//                System.out.println("x: " + e.getX() + ", y: " + e.getY());
                if (isDrawing) {
                    if (e.isDragDetect()) {
                        gc.strokeOval(e.getX(), e.getY(), strokeSize, strokeSize);
                        xPosition = (e.getX());
                        yPosition = (e.getY());
                        if (gcSecond != null) {
                            gcSecond.strokeOval(xPosition,yPosition, strokeSize, strokeSize);
                        }
                    }

                } else if (!isDrawing){
                    gc.strokeOval(e.getX(), e.getY(), 0, 0);
                }

//                addStroke(e.getX(), e.getY(), 10);

            }
        });

        grid.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent e) {
                System.out.println(e.getCode());
                System.out.println(e.getText());
                System.out.println("Key pressed " + e.getCode());
                if (e.getCode().equals(KeyCode.D)) {
                        isDrawing = !isDrawing;
                    System.out.println(isDrawing);
                } else if (e.getCode() == KeyCode.UP) {
                    strokeSize += 1;
                } else if (e.getCode() == KeyCode.DOWN){
                    strokeSize -= 1;
                }
            }
        });


        grid.add(canvas, 0, 2);




        // set iyr grid layout on the scene
        Scene defaultScene = new Scene (grid, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);


        primaryStage.setScene(defaultScene);
        primaryStage.show();


    }

    public void startSecondStage() {
        Stage secondaryStage = new Stage();
        secondaryStage.setTitle("Welcome to JavaFX");

        // we're using a grid layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setGridLinesVisible(true);
//        grid.setPrefSize(primaryStage.getMaxWidth(), primaryStage.getMaxHeight());

        Canvas canvas = new Canvas(DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT-100);
        grid.add(canvas, 0, 2);
        gcSecond = canvas.getGraphicsContext2D();
        gcSecond.setFill(Color.GREEN);
        gcSecond.setStroke(Color.BLUE);
        gcSecond.setStroke(Color.color(Math.random(), Math.random(), Math.random()));
        gcSecond.setLineWidth(5);




        // add buttons and canvas to the grid
        Text sceneTitle = new Text("Welcome to Paint application");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(sceneTitle, 0, 0);

        Button button = new Button("Sample paint button");
        HBox hbButton = new HBox(10);
        hbButton.setAlignment(Pos.TOP_LEFT);
        hbButton.getChildren().add(button);
        grid.add(hbButton, 0, 1);






        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println("I can switch to another scene here ...");
            }
        });


        // add canvas
        //Canvas canvas = new Canvas(DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT-100);


        // set our grid layout on the scene
        Scene defaultScene = new Scene(grid, DEFAULT_SCENE_WIDTH, DEFAULT_SCENE_HEIGHT);

        secondaryStage.setScene(defaultScene);
        System.out.println("About to show the second stage");


        //secondaryStage.show();
    }

    public void client () {
        try {
            // connect to the server on the target port
            Socket clientSocket = new Socket("localhost", 8005);

            // once we connect to the server, we also have an input and output stream
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // send the server an arbitrary message
            out.println("Marvin says hello!");
            // read what the server returns
            String serverResponse = in.readLine();

            // close the connection
            clientSocket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);


    }
}
