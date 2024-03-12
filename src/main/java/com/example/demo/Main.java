package com.example.demo;

import Controller.startSceneController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("startPage.fxml"));
        Parent root = loader.load();
        startSceneController controller = loader.getController();

        EventHandler<ActionEvent> startGame = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Scene game = controller.getTheMap().miniMap;
                stage.setScene(game);
            }
        };

        controller.getStartButton().setOnAction(startGame);


       // Map TheMap = new Map(512,512,64,2,4,8);

        Scene scene = new Scene(root, 800, 600); // Genişlik ve yükseklik ayarlanabilir
        stage.setScene(scene);

        stage.setTitle("Autonom Treasure Hunter");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}