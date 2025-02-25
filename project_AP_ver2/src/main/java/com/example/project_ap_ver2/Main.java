package com.example.project_ap_ver2;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("title_screen.fxml"));
        Scene scene = new Scene(root,800,600);
        stage.setTitle("STICK HERO");
        stage.setResizable(false);
        Image logo = new Image(getClass().getResourceAsStream("/Image/window_logo.png"));
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}