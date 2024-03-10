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

        Map TheMap = new Map(512,512,64,2,4,8);






        Scene mapScene = TheMap.miniMap;


        stage.setScene(mapScene);
        stage.setFullScreen(true);
        stage.setResizable(false);









        stage.setScene(mapScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    //Creates an ImageView for the map


    //Creates a scrollPane for the map

}