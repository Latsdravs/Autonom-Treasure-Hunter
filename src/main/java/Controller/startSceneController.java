package Controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import com.example.demo.Map;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;

public class startSceneController {

    @FXML
    private TextField firstNumberField;

    @FXML
    private TextField secondNumberField;

    @FXML
    private Button createNewMapButton;

    @FXML
    private Button startButton;

    private Map theMap;
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
    private void toggleFullScreen(Stage stage) {
        if (stage.isFullScreen()) {
            stage.setFullScreen(false);
        } else {
            stage.setFullScreen(true);
        }
    }


    public Button getStartButton() {
        return startButton;
    }

    @FXML
    private void start() {
        // Başlatma işlemlerini buraya ekle
        if (theMap != null) {

               // FXMLLoader loader = new FXMLLoader(getClass().getResource("mapScene.fxml"));
               // Parent root = loader.load();

               // MapSceneController mapSceneController = loader.getController();
               // mapSceneController.initData(theMap);

                Stage stage = new Stage();
                stage.setTitle("Autonom Treasure Hunter");

                Scene mapScene = theMap.getMiniMap();

                Button fullScreenButton = new Button("Toggle FullScreen");

                mapScene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.F) {
                        toggleFullScreen(stage);
                    }
                });
                stage.setScene(mapScene);
                stage.show();


                // Ana sahneyi kapatma
             //   Stage currentStage = (Stage) startButton.getScene().getWindow();
            //    currentStage.close();


        } else {
            showAlert("Uyarı", "Harita Eksik", "Lütfen önce harita oluşturun.");
        }
    }
}