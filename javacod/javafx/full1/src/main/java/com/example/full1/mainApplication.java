package com.example.full1;

import com.example.full1.controller.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class mainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene menuScene = loadScene("/com/example/full1/menu-view.fxml");
        primaryStage.setScene(menuScene);
        primaryStage.setTitle("Main Window");
        primaryStage.show();
    }
    public  Scene loadScene(String fxmlFile) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Object controller = loader.getController();
        return new Scene(root);
    }
    }

