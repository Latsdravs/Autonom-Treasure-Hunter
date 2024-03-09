package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Group root = new Group();


        int size=64;
        Scene scene = new Scene(root,600,600, Color.BLACK);
        ArrayList<Line> lines = new ArrayList<>();
        for (int i = 0; i < 1001; i++) {
            Line temp = new Line();
            temp.setStartX(i*size);
            temp.setEndX(i*size);

            temp.setStartY(0);
            temp.setEndY(2000);
            temp.setStroke(Color.GAINSBORO);
            temp.setStrokeWidth(2);
            root.getChildren().add(temp);
            lines.add(temp);
        }
        for (int j = 0; j < 1001; j++) {
            Line temp = new Line();
            temp.setStartX(0);
            temp.setEndX(2000);

            temp.setStartY(j*size);
            temp.setEndY(j*size);

            temp.setStroke(Color.GAINSBORO);
            temp.setStrokeWidth(2);
            root.getChildren().add(temp);
            lines.add(temp);
        }
        Line line = new Line();


        root.getChildren().add(line);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}