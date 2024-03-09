package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Group root = new Group();





        AnchorPane mapAnchor = new AnchorPane();
        ScrollPane mapScrollO = addMapScroll(root);
        mapAnchor.getChildren().add(mapScrollO);
        AnchorPane.setLeftAnchor(mapScrollO, 0.0);
        AnchorPane.setTopAnchor(mapScrollO, 0.0);
        AnchorPane.setRightAnchor(mapScrollO, 0.0);
        AnchorPane.setBottomAnchor(mapScrollO, 0.0);
        Scene mapScene = new Scene(mapAnchor,600,600);
        stage.setScene(mapScene);
        stage.setFullScreen(true);
        stage.setResizable(false);
        mapScrollO.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        mapScrollO.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        int grid_x = 32;
        int grid_y = 32;
        int width = 2;
        int size=64;

        Circle ball = new Circle();
        ball.setCenterX(32+width);
        ball.setCenterY(32+width);
        ball.setRadius(32);
        ball.setStroke(Color.FIREBRICK);
        ball.setFill(Color.FIREBRICK);
        root.getChildren().add(ball);

        for (int i = 0; i <= grid_x; i++) {
            Line temp = new Line();
            temp.setStartX(i*(size+width*2));
            temp.setEndX(i*(size+width*2));

            temp.setStartY(0);
            temp.setEndY((size+width*2)*grid_y);
            temp.setStroke(Color.GAINSBORO);
            temp.setStrokeWidth(width*2);
            root.getChildren().add(temp);

        }
        for (int j = 0; j <= grid_y; j++) {
            Line temp = new Line();
            temp.setStartX(0);
            temp.setEndX((size+width*2)*grid_x);

            temp.setStartY(j*(size+width*2));
            temp.setEndY(j*(size+width*2));

            temp.setStroke(Color.GAINSBORO);
            temp.setStrokeWidth(width*2);
            root.getChildren().add(temp);

        }






        stage.setScene(mapScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    //Creates an ImageView for the map


    //Creates a scrollPane for the map
    private ScrollPane addMapScroll(Node node)
    {
        ScrollPane mapScroll = new ScrollPane();

        mapScroll.setContent(node);
        mapScroll.setPannable(true);
        return mapScroll;
    }
}