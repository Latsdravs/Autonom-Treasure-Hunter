package Controller;


import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.demo.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

public class startSceneController {

    @FXML
    private TextField firstNumberField;

    @FXML
    private TextField secondNumberField;

    @FXML
    private Button createNewMapButton;

    @FXML
    private Button startButton;
    private Stage theStage;

    public Stage getTheStage() {
        return theStage;
    }

    public void setTheStage(Stage theStage) {
        this.theStage = theStage;
    }

    private Map theMap;
    private java.util.Map<Character, VBox> notificationBoxes = new HashMap<>();
    @FXML
    private void createNewMap() {
        try {
            int firstNumber = Integer.parseInt(firstNumberField.getText());
            int secondNumber = Integer.parseInt(secondNumberField.getText());
            // Yapılacak işlemleri buraya ekle
            this.theMap = new Map(firstNumber,secondNumber,64,2,4,8);
            showInformation("Bilgi", "Harita Oluşturuldu", "Rastgele bir harita oluşturuldu.");
        } catch (NumberFormatException e) {
            // Hata durumunu ele al
            showAlert("Hata", "Sayı girişi hatası", "Lütfen geçerli sayılar girin.");
        }
    }
    public Map getTheMap() {
        return theMap;
    }
    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showInformation(String title, String header, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    boolean fullscreen = false;
    private void toggleFullScreen(Stage stage) {
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
            fullscreen = false;
        } else {
            stage.setFullScreen(true);
            fullscreen = true;
        }

    }


    private void toggleMap(Stage stage) {
        if (stage.getScene()==theMap.getMiniMap()) {
            stage.setScene(theMap.getMap());
        } else {
            stage.setScene(theMap.getMiniMap());
        }
    }

    @FXML
    private void start() {
        if (theMap != null) {
            Scene mapScene = theMap.getMap();
            Scene miniMapScene = theMap.getMiniMap();

            AnchorPane anchorPane = (AnchorPane) theMap.getMap().getRoot();
            ScrollPane scrollPane = (ScrollPane) erisimYontemi2(anchorPane);


            VBox root = new VBox(10);

            for (char key : new char[]{'a', 'b', 'c', 'd'}) {
                VBox notificationBox = new VBox(5);
                notificationBox.setAlignment(Pos.TOP_RIGHT);
                notificationBoxes.put(key, notificationBox);
                root.getChildren().add(notificationBox);
            }

            anchorPane.getChildren().addAll(root);
            AnchorPane.setTopAnchor(root, 10.0);
            AnchorPane.setRightAnchor(root, 10.0);

            anchorPane.setOnKeyPressed(event -> {
                char key = event.getText().toLowerCase().charAt(0);
                if (notificationBoxes.containsKey(key)) {
                    VBox notificationBox = notificationBoxes.get(key);
                    Label notification = new Label(key + " tuşuna basıldı");
                    notificationBox.getChildren().add(0, notification);


                }
            });


            AnchorPane miniAnchorPane = (AnchorPane) theMap.getMiniMap().getRoot();
            ScrollPane miniScrollPane = erisimYontemi2(miniAnchorPane);
            double miniStep = 0.02; // Kaydırma miktarı
            miniScrollPane.setVvalue(0); // Dikey değer 0-1
            miniScrollPane.setHvalue(0); // Yatay değer 0-1

            miniAnchorPane.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case W:
                        miniScrollPane.setVvalue(miniScrollPane.getVvalue() - miniStep);
                        break;
                    case S:
                        miniScrollPane.setVvalue(miniScrollPane.getVvalue() + miniStep);
                        break;
                    case A:
                        miniScrollPane.setHvalue(miniScrollPane.getHvalue() - miniStep);
                        break;
                    case D:
                        miniScrollPane.setHvalue(miniScrollPane.getHvalue() + miniStep);
                        break;

                    default:
                        break;
                }
            });

            mapScene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.F) {
                    toggleFullScreen(theStage);
                } else if (event.getCode() == KeyCode.M) {
                    toggleMap(theStage);
                    if(fullscreen) theStage.setFullScreen(true);
                }else if(event.getCode() == KeyCode.W){
                    theMap.update(-1);
                }else if(event.getCode() == KeyCode.A){
                    theMap.update(-2);
                }else if(event.getCode() == KeyCode.S){
                    theMap.update(1);
                }else if(event.getCode() == KeyCode.D){
                    theMap.update(2);
                } else if (event.getCode()==KeyCode.SPACE) {

                }

            });


            miniMapScene.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.F) {
                    toggleFullScreen(theStage);
                } else if (event.getCode() == KeyCode.M) {
                    toggleMap(theStage);
                    if(fullscreen) theStage.setFullScreen(true);
                } else if (event.getCode() == KeyCode.T) {

                } else if (event.getCode()==KeyCode.U) {
                    theMap.update(-1);


                } else if (event.getCode()==KeyCode.J) {
                    theMap.update(1);
                } else if (event.getCode()==KeyCode.H) {
                    theMap.update(-2);
                } else if (event.getCode()==KeyCode.K) {
                    theMap.update(2);
                }
            });



            theStage.setScene(mapScene);

        } else {
            showAlert("Uyarı", "Harita Eksik", "Lütfen önce harita oluşturun.");
        }
    }
    private ScrollPane erisimYontemi2(AnchorPane anchorPane) {

        for (javafx.scene.Node node : anchorPane.getChildren()) {
            return (ScrollPane) node;
        }
        return null;
    }
}