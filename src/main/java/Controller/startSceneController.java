package Controller;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
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
    private Timeline timeline;

    private Map theMap;
    private java.util.Map<String, VBox> chestNotificationBoxes = new HashMap<>();
    private LinkedList<String> messages;
    private VBox messagesContainer;

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
            ScrollPane scrollPane = (ScrollPane) erisimYontemi(anchorPane);

            VBox root = new VBox(10);

            for (String key : new String[]{"Zumrut Sandik toplandi!", "Altin Sandik toplandi!" ,"Gumus Sandik toplandi!","Bakir Sandik toplandi!"}) {
                VBox notificationBox = new VBox(5);
                notificationBox.setAlignment(Pos.TOP_RIGHT);
                chestNotificationBoxes.put(key, notificationBox);
                root.getChildren().add(notificationBox);
            }

            anchorPane.getChildren().addAll(root);
            AnchorPane.setTopAnchor(root, 10.0);
            AnchorPane.setRightAnchor(root, 10.0);

            AnchorPane miniAnchorPane = (AnchorPane) theMap.getMiniMap().getRoot();
            ScrollPane miniScrollPane = erisimYontemi(miniAnchorPane);
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

            messagesContainer = new VBox(15);
            messagesContainer.setAlignment(Pos.TOP_LEFT);
            messages = new LinkedList<>();

            anchorPane.getChildren().add(messagesContainer);
            AnchorPane.setTopAnchor(messagesContainer, 10.0);
            AnchorPane.setLeftAnchor(messagesContainer, 10.0);
            timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.009), e -> callMe())

            );

            // If you want to repeat indefinitely:
            timeline.setCycleCount(Animation.INDEFINITE);

            timeline.play();



            try {
                Thread.sleep(1000);
                System.out.println("uyudum");// 1 saniye uyku
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } else {
            showAlert("Uyarı", "Harita Eksik", "Lütfen önce harita oluşturun.");
        }
    }
    private void callMe(){

        ArrayList<String> objects = theMap.update();
        if(theMap.finish)timeline.stop();

        for (String key : objects) {
            if(key!=null) {
                if (chestNotificationBoxes.containsKey(key)) {
                    int x = theMap.playerGetX();
                    int y = theMap.playerGetY();
                    VBox notificationBox = chestNotificationBoxes.get(key);
                    Label notification = new Label(key + " (" + x + "," + y + ") konumunda bulundu.");
                    notificationBox.getChildren().add(0, notification);
                } else {
                    messages.addFirst(key+" kesfedildi!");
                    updateMessagesContainer();
                }
            }
        }
    }
    private ScrollPane erisimYontemi(AnchorPane anchorPane) {

        for (javafx.scene.Node node : anchorPane.getChildren()) {
            return (ScrollPane) node;
        }
        return null;
    }

    private void updateMessagesContainer() {
        messagesContainer.getChildren().clear();
        if(messages.size()>15) messages.removeLast();

        for (String message : messages) {
            messagesContainer.getChildren().add(createMessageLabel(message));
        }

    }

    private Label createMessageLabel(String message) {
        Label label = new Label(message);
        label.setStyle("-fx-border-color: black;");
        return label;
    }
}