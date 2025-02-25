package com.example.project_ap_ver2;

import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class Title_Controller {
    @FXML
    AnchorPane pane1;
    @FXML
    ImageView title_img;
    @FXML
    Button play;
    @FXML
    ImageView start_cherry;

    public static boolean countinued;
    public static boolean start_sequence = false;
    private FadeTransition fade;
    private RotateTransition rotate;
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void initialize() {
        title_img.setImage(new Image(getClass().getResourceAsStream("/Image/start_background.jpg")));

        start_cherry.setImage(new Image(getClass().getResourceAsStream("/Image/SHARINGAN.png")));

        fade = new FadeTransition();
        fade.setDuration(Duration.millis(1000));
        fade.setFromValue(10);
        fade.setToValue(0);
        fade.setCycleCount(1);
        fade.setAutoReverse(true);
        fade.setNode(pane1);

        rotate = new RotateTransition();
        rotate.setCycleCount(10000);
        rotate.setByAngle(720);
        rotate.setDuration(Duration.millis(2500));
        rotate.setNode(start_cherry);
        rotate.play();
        }
    public void play(ActionEvent event) throws IOException {



        //playing the transition
        fade.play();
        fade.setOnFinished(event1-> {
            try {
                root = FXMLLoader.load(getClass().getResource("main_game.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            rotate.stop();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        });
    }
    public void countinue(ActionEvent event) throws IOException {
        countinued = true;
        System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKKKKK");


        //playing the transition
        fade.play();
        fade.setOnFinished(event1-> {
            try {
                root = FXMLLoader.load(getClass().getResource("main_game.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            rotate.stop();
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        });
    }
}