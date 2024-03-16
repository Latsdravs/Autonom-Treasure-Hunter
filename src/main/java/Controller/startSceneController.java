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
    private VBox messagesContainer;
    private LinkedList<String> messages;
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

    private void addButtonClicked(String buttonName) {
        String message = buttonName + " tuşuna basıldı";

        // Yeni mesajı ekleyip güncellenmiş listeyi göster
        messages.addFirst(message);
        updateMessagesContainer();
    }

    private void updateMessagesContainer() {
        // Mesajları temizle ve güncellenmiş listeyi göster
        messagesContainer.getChildren().clear();

        for (String message : messages) {
            messagesContainer.getChildren().add(createMessageLabel(message));
        }
    }

    private Label createMessageLabel(String message) {
        Label label = new Label(message);
        label.setStyle("-fx-border-color: black;");
        return label;
    }

    @FXML
    private void start() {
        // Başlatma işlemlerini buraya ekle
        if (theMap != null) {

            //messagesContainer = new VBox(10);
            //messagesContainer.setAlignment(Pos.TOP_RIGHT);
            //messages = new LinkedList<>();

            //Button buttonA = new Button("A");
            //buttonA.setOnAction(e -> addButtonClicked("A"));

            //Button buttonB = new Button("B");
            //buttonB.setOnAction(e -> addButtonClicked("B"));

            //Button buttonC = new Button("C");
            //buttonC.setOnAction(e -> addButtonClicked("C"));

            //Button buttonD = new Button("D");
            //buttonD.setOnAction(e -> addButtonClicked("D"));

            //VBox root = new VBox(20);
            //root.getChildren().addAll(buttonA, buttonB, buttonC, buttonD, messagesContainer);



            Scene mapScene = theMap.getMap();
            Scene miniMapScene = theMap.getMiniMap();
            AnchorPane anchorPane = (AnchorPane) theMap.getMiniMap().getRoot();
            ScrollPane scrollPane = erisimYontemi2(anchorPane);
            double step = 0.02; // Kaydırma adım miktarı
            scrollPane.setVvalue(0); // Dikey değer için (0.0 - 1.0 arası)
            scrollPane.setHvalue(0); // Yatay değer için (0.0 - 1.0 arası)

            anchorPane.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case W:
                        scrollPane.setVvalue(scrollPane.getVvalue() - step);
                        break;
                    case S:
                        scrollPane.setVvalue(scrollPane.getVvalue() + step);
                        break;
                    case A:
                        scrollPane.setHvalue(scrollPane.getHvalue() - step);
                        break;
                    case D:
                        scrollPane.setHvalue(scrollPane.getHvalue() + step);
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
                        theMap.update(-2);
                    }else if(event.getCode() == KeyCode.A){
                        theMap.update(-1);
                    }else if(event.getCode() == KeyCode.S){
                        theMap.update(1);
                    }else if(event.getCode() == KeyCode.D){
                        theMap.update(2);
                    }

                });





                miniMapScene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.F) {
                        toggleFullScreen(theStage);
                 } else if (event.getCode() == KeyCode.M) {
                        toggleMap(theStage);
                        if(fullscreen) theStage.setFullScreen(true);
                    } else if (event.getCode() == KeyCode.T) {
                        theMap.toggle();
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