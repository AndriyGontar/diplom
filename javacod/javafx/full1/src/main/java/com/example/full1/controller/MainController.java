package com.example.full1.controller;

import com.example.full1.buisnesLogic.HttpClient;
import com.example.full1.buisnesLogic.ImageOperation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private int height=16;
    private int weight=16;
    private boolean up= true;
    private  boolean vertical=true;
    private  boolean left=false;
    @FXML
    private ImageView importImage1;

    @FXML
    private ImageView importImage2;
    @FXML
    private TextArea nameImg;

    public void loadScene(ActionEvent event,String name) {
        try {
            root = FXMLLoader.load(getClass().getResource(name));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    public void onSettingButtonClick(ActionEvent actionEvent) {
        loadScene(actionEvent,"/com/example/full1/setting-view.fxml");
    }

    @FXML
    public void onImportImageButtonClick(ActionEvent actionEvent) {
        loadScene(actionEvent,"/com/example/full1/import-view.fxml");
    }

    @FXML
    public void onCreateImageButtonClick(ActionEvent actionEvent) {
        loadScene(actionEvent,"/com/example/full1/createImage-view.fxml");
    }

    @FXML
    public void onConvertTextButtonClick(ActionEvent actionEvent) {
        loadScene(actionEvent,"/com/example/full1/text-view.fxml");
    }

    @FXML
    public void onSelectButtonClick(ActionEvent actionEvent) {
        loadScene(actionEvent,"/com/example/full1/select-view.fxml");
    }
    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) {
        loadScene(actionEvent,"/com/example/full1/menu-view.fxml");
    }

    @FXML
    private void onOpenFolderButtonClick(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Файли зображень", "*.png", "*.jpg", "*.jpeg")
        );
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            System.out.println(selectedFile.toURI().toString());
            importImage1.setImage(image);
            var img=ImageOperation.readImage(selectedFile.getAbsolutePath());
            var newImg= ImageOperation.bicubicInterpolate(img,height,weight);
            ImageOperation.saveImage(weight,height,newImg);
            URL imageUrl = new URL("file:/D:/study/diplom/javacod/javafx/full1/src/main/resources/output.png");
            if (imageUrl != null) {
                Image image2 = new Image(imageUrl.toString());
                importImage2.setImage(image2);
                // Друк URL у консоль
                System.out.println("URL ресурсу зображення: " + imageUrl);
            } else {
                System.out.println("faol");
            }

        }

    }

    public void onSendImgButtonClick(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        var q=ImageOperation.readImage("D:/study/diplom/javacod/javafx/full1/src/main/resources/output.png");
        HttpClient httpClient = new HttpClient();
        httpClient.sendImg(q,nameImg.getText(),up,left,vertical);
    }
}
