package com.example.project_ap_ver2;

import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.Test;

import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainGame_Controller {
    // There is a differential of 30 b/w the ninja and rod x coordinate
    Random rand = new Random();
    private Ninja ninja;
    private Cherries cherries;
    private Rectangle rectangle = new Rectangle();

    private RotateTransition rotate_cherry = new RotateTransition();
    private boolean isAPressed = false;
    private boolean animation_playing = false;

    private boolean is_flippable = false;
    private boolean paused = false;
    private boolean initialized = true;

    private boolean flipped = false;
    private boolean has_increased;
    private boolean is_increasing;
    private int distance_from_pillar;
    private int pillar_width;
    private Rectangle obstacle1 = new Rectangle();
    private Rectangle staring_platform =  new Rectangle();
    private static final long INCREMENT_INTERVAL = 10; // milliseconds
    private static final double HEIGHT_INCREMENT = 0.5;
    private final Lock executorServiceLock = new ReentrantLock();
    private ScheduledExecutorService executorService;

    private Label pause_label;
    private Button pause_button;
    private Label score;
    private Label cherry;

    @FXML
    AnchorPane pane2;
    @FXML
    AnchorPane papa_pane;
    @FXML
    AnchorPane pause_pane;
    @FXML
    Label resume_label;
    @FXML
    Label save_label;
    @FXML
    Label quit_label;
    @FXML
    Button resume_button;
    @FXML
    Button save_button;
    @FXML
    Button quit_button;
    @FXML
    AnchorPane fail_screen;
    @FXML
    Label game_over;
    @FXML
    Label revive;
    @FXML
    Label end_score;
    @FXML
    ImageView retry_endscreen;
    @FXML
    Button retry_endscreen_button;
    @FXML
    Button revive_button;
    @FXML
    Label highsore_label;


    private Stage stage;
    private Scene scene;
    private Parent root;


    //used for danda enlargement
    public void startHeightIncrease() {
        executorServiceLock.lock();
        pause_button.setVisible(false);
        pause_label.setVisible(false);
        try {
            if (executorService == null || executorService.isShutdown()) {
                executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(() -> {
                    Platform.runLater(() -> {
                        double currentHeight = rectangle.getHeight();
                        double newHeight = currentHeight + 1.5;
                        double newY = rectangle.getY() - 1.5;
                        rectangle.setHeight(newHeight);
                        rectangle.setY(newY);
                    });
                }, 0, INCREMENT_INTERVAL, TimeUnit.MILLISECONDS);
            }
        } finally {
            executorServiceLock.unlock();
        }
    }

    // This method is used to stop almost all the animations used
    public void stopHeightIncrease() {
        executorServiceLock.lock();
        try {
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
            distance_check();
        } finally {
            executorServiceLock.unlock();
        }
    }

    //This method is used to move the player left to right
    //this takes an integer as input and can have 3 outcomes
    public void startleft(int i) {

        // 1. When the danda is too short

        if(i == 0){
            executorServiceLock.lock();
            try {
                double originalX = ninja.getCharacter().getX();
                double originalY = ninja.getCharacter().getY();

                if (executorService == null || executorService.isShutdown()) {
                    executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.scheduleAtFixedRate(() -> {
                        Platform.runLater(() -> {

                            double currentX = ninja.getCharacter().getX();
                            double newX = currentX + 2;
                            if (newX > originalX+25) {
                                double currentY = ninja.getCharacter().getY();
                                double newY = currentY + 3;
                                ninja.getCharacter().setY(newY);
                                if(newY > pane2.getHeight()){
                                    System.out.println("HII");
                                    stopleft();
                                    end_screen();
                                }
                            }
                            else {
                                ninja.getCharacter().setX(newX);
                            }
                        });
                    }, 0, INCREMENT_INTERVAL, TimeUnit.MILLISECONDS);
                }
            } finally {
                executorServiceLock.unlock();
            }
        }

        //when the danda is too long

        else if(i == 1){
            executorServiceLock.lock();
            try {
                AtomicBoolean collided = new AtomicBoolean(false);
                double originalX = ninja.getCharacter().getX();
                double originalY = ninja.getCharacter().getY();

                if (executorService == null || executorService.isShutdown()) {
                    executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.scheduleAtFixedRate(() -> {
                        Platform.runLater(() -> {

                            double currentX = ninja.getCharacter().getX();
                            cherry_collision();

                            if(currentX < obstacle1.getX()){
                                if(collided.get()){
                                    is_flippable = false;
                                }
                                else{
                                    is_flippable = true;
                                }
                            }
                            else {
                                is_flippable = false;
                            }

                            if(currentX+25 > obstacle1.getX() && flipped ){
                                double currentY = ninja.getCharacter().getY();
                                double newY = currentY + 2;
                                collided.set(true);
                                ninja.getCharacter().setY(newY);
                                if(newY > pane2.getHeight()){
                                    System.out.println("HIII");
                                    stopleft();
                                    end_screen();
                                }
                            }else {
                            if((originalX+rectangle.getHeight()+25) - currentX > 50){
                            double newX = currentX + 3;
                            if (newX > originalX+rectangle.getHeight()+25) {
                                double currentY = ninja.getCharacter().getY();
                                double newY = currentY + 2;
                                ninja.getCharacter().setY(newY);
                                if(newY > pane2.getHeight()){
                                    System.out.println("HII");
                                    stopleft();
                                    end_screen();
                                }
                            }
                            else {
                                ninja.getCharacter().setX(newX);
                            }
                            }
                            else {
                                double newX = currentX + 0.9;
                                if (newX > originalX+rectangle.getHeight()+25) {
                                    double currentY = ninja.getCharacter().getY();
                                    double newY = currentY + 2;
                                    ninja.getCharacter().setY(newY);
                                    if(newY > pane2.getHeight()){
                                        System.out.println("HIII");
                                        stopleft();
                                        end_screen();
                                    }
                                }
                                else {
                                    ninja.getCharacter().setX(newX);
                                }
                            }
                            }
                        });
                    }, 0, INCREMENT_INTERVAL, TimeUnit.MILLISECONDS);
                }
            } finally {
                executorServiceLock.unlock();
            }
        }

        //when the danda is just right!

        else{
            executorServiceLock.lock();
            AtomicBoolean collided = new AtomicBoolean(false);
            try {
                double originalX = ninja.getCharacter().getX();

                if (executorService == null || executorService.isShutdown()) {
                    executorService = Executors.newSingleThreadScheduledExecutor();
                    executorService.scheduleAtFixedRate(() -> {
                        Platform.runLater(() -> {
                            double currentX = ninja.getCharacter().getX();

                            cherry_collision();

                            if(currentX < obstacle1.getX()){
                                if(collided.get()){
                                    is_flippable = false;
                                }
                                else{
                                    is_flippable = true;
                                }
                            }
                            else {
                                is_flippable = false;
                            }

                            if(currentX+25 > obstacle1.getX() && flipped ){
                                collided.set(true);
                                double currentY = ninja.getCharacter().getY();
                                double newY = currentY + 2;
                                ninja.getCharacter().setY(newY);
                                if(newY > pane2.getHeight()){
                                    System.out.println("HIII");
                                    stopleft();
                                    end_screen();
                                }
                            }
                            else {
                            if((obstacle1.getX()+obstacle1.getWidth()-30) - currentX > 50){
                                double newX = currentX + 3;
                                if (newX > obstacle1.getX()+obstacle1.getWidth()-30) {
                                    System.out.println("YAYYYYYYYYYYYYYYYYYYYYYY");
                                    ninja.setScore(ninja.getScore()+1);
                                    score.setText(Integer.toString(ninja.getScore()));
                                    stopleft();
                                    scene_change(1);

                                }
                                else {
                                    ninja.getCharacter().setX(newX);
                                }
                            }
                            else {
                                double newX = currentX + 0.9;
                                if (newX > obstacle1.getX()+obstacle1.getWidth()-30) {
                                    System.out.println("YAYYYYYYYYYYYYYYYYYYYYYY");
                                    ninja.setScore(ninja.getScore()+1);
                                    score.setText(Integer.toString(ninja.getScore()));
                                    stopleft();
                                    scene_change(1);

                                }
                                else {
                                    ninja.getCharacter().setX(newX);
                                }
                            }
                            }
                        });
                    }, 0, INCREMENT_INTERVAL, TimeUnit.MILLISECONDS);
                }
            } finally {
                executorServiceLock.unlock();
            }
        }
    }

    // Same as the stop method before
    public void stopleft() {
        executorServiceLock.lock();
        try {
            if (executorService != null && !executorService.isShutdown()) {
                executorService.shutdown();
            }
        } finally {
            executorServiceLock.unlock();
        }
    }
    // initialising the values
    public void initialize() {
        Factory_ninja fact_n = (Factory_ninja) Factory_Selector.factory(0);
        this.ninja = fact_n.get_product();
        if(Title_Controller.countinued){
            Title_Controller.countinued = false;
            Scanner in = null;
            try {
                in = new Scanner(new BufferedInputStream(new FileInputStream("SAVES.txt")));
                if(in.hasNext()){
                    in.next();
                    if(in.hasNext()){
                        ninja.setScore(Integer.parseInt(in.next()));
                        if (in.hasNext()){
                            ninja.setCherries(Integer.parseInt(in.next()));
                        }
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }finally {
                if(in != null){
                    in.close();
                }
            }

        }
        Factory_cherry fact_c =(Factory_cherry) Factory_Selector.factory(1);
        this.cherries = fact_c.get_product();
        System.out.println("////////////////////");
        System.out.println(pane2.getHeight());


        //SCORE DISPLAY
        score = new Label();
        score.setFont(new Font("Arial", 30));
        score.setLayoutX(750);
        score.setLayoutY(20);
        score.setText(Integer.toString(ninja.getScore()));
        ImageView score_img = new  ImageView();
        score_img.setFitHeight(25);
        score_img.setFitWidth(25);
        score_img.setX(700);
        score_img.setY(25);
        score_img.setImage(new Image(getClass().getResourceAsStream("/Image/star_2.png")));

        //CHERRIES
        cherry = new Label();
        cherry.setFont(new Font("Arial", 30));
        cherry.setLayoutX(750);
        cherry.setLayoutY(50);
        cherry.setText(Integer.toString(ninja.getCherries()));
        ImageView cherry_img = new  ImageView();
        cherry_img.setFitHeight(25);
        cherry_img.setFitWidth(25);
        cherry_img.setX(700);
        cherry_img.setY(55);
        cherry_img.setImage(new Image(getClass().getResourceAsStream("/Image/SHARINGAN.png")));
        rotate_cherry.setCycleCount(1);
        rotate_cherry.setByAngle(360);
        rotate_cherry.setDuration(Duration.millis(1000));
        rotate_cherry.setNode(cherry_img);
        pause_button = new Button();
        pause_button.setLayoutY(5);
        pause_button.setPrefHeight(20);
        pause_button.setPrefWidth(110);
        pause_button.setText("Pause");
        pause_button.setOpacity(0);
        //PAUSE BUTTON
        pause_label = new Label("PAUSE");
        pause_label.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/Kamikaze3DGradient.ttf"), 32));
        pause_button.setOnAction(event -> {

            if(pane2!=null){
               pause_screen();
            }
            else {
                System.out.println("pane hai khaali");
            }
        });


        //backdrop
        ImageView bg = new ImageView();
        bg.setX(0);
        bg.setY(0);
        bg.setImage(new Image(getClass().getResourceAsStream("/Image/bg_3.jpg")));
        bg.setFitWidth(800);
        bg.setFitHeight(600);

        // properties for the danda
        rectangle.setHeight(45);
        rectangle.setWidth(4);
        rectangle.setX(96);
        rectangle.setY(345);
        rectangle.setFill(Color.RED);

        Stop[] stops = new Stop[] { new Stop(0, Color.rgb(0,65,106)), new Stop(1, Color.rgb(228, 229, 230))};
        LinearGradient linear = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        // starting platform
        staring_platform.setY(390);
        staring_platform.setX(0);
        staring_platform.setHeight(210);
        staring_platform.setWidth(100);
        staring_platform.setFill(Color.BLACK);

        System.out.println("Starting platform x co-ord");
        System.out.println(staring_platform.getX());

        //next pillar
        int distance_from_start = rand.nextInt(150,640);
        obstacle1.setX(distance_from_start);
        this.pillar_width = rand.nextInt(100,800-distance_from_start);
        this.distance_from_pillar = distance_from_start - 100;
        obstacle1.setWidth(pillar_width);
        obstacle1.setY(390);
        obstacle1.setHeight(210);
        obstacle1.setFill(Color.BLACK);


        //keypress detection
        pane2.setOnKeyPressed(event -> {


            if (event.getCode().equals(KeyCode.SHIFT) && !has_increased && !is_increasing && !paused) {
                startHeightIncrease();
                is_increasing = true;
            }
            if(event.getCode().equals(KeyCode.CONTROL) && is_flippable){
                System.out.println("OYE LUCKY LUCKY OYE!");
                ninja_flip();
            }
        });

        pane2.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyCode.SHIFT) && !has_increased && is_increasing && !paused) {
                has_increased = true;
                is_increasing = false;
                stopHeightIncrease();
            }
        });


        //adding elements to pane
        if (pane2 != null) {
            pane2.getChildren().add(bg);
            pane2.getChildren().addAll(rectangle,staring_platform,obstacle1,ninja.getCharacter(),pause_label,pause_button,score,score_img,cherry,cherry_img);
            //pane2.setBackground(Background.fill(linear));

        }

    }

    //pause button method

    //new pillar generation
    public void generate_pillars(AnchorPane pane , int x){

        System.out.println("<<<<<HARI OM>>>>>");
        //regenerating the danda and pillars
        if (pane2 != null) {
            pane2.getChildren().removeAll(staring_platform,obstacle1,rectangle);
            this.rectangle = new Rectangle();
            rectangle.setHeight(45);
            rectangle.setWidth(4);
            rectangle.setX(96);
            rectangle.setY(390);
            rectangle.setFill(Color.RED);
            int distance_from_start = rand.nextInt(150,640);
            this.distance_from_pillar = distance_from_start - 100;
            obstacle1.setX(distance_from_start);
            int pillar_width = rand.nextInt(100,800-distance_from_start);
            obstacle1.setWidth(pillar_width);
            obstacle1.setY(600);
            obstacle1.setHeight(210);
            obstacle1.setFill(Color.BLACK);
            pane2.getChildren().addAll(rectangle,staring_platform,obstacle1);
        }


        //pillar animation
        executorServiceLock.lock();
        try {
            if (executorService == null || executorService.isShutdown()) {
                AtomicBoolean flag = new AtomicBoolean(true);
                executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(() -> {
                    Platform.runLater(() -> {
                        double currentY = obstacle1.getY();
                        double newY = currentY - 1.5;
                        if(rectangle.getY() != 345){
                            rectangle.setY(rectangle.getY()-0.3);
                        }
                        if(obstacle1.getY() == 390){
                            stopleft();
                            if (x == 0){
                                ninja.getCharacter().setX(70);
                                ninja.getCharacter().setY(340);
                                ninja.getCharacter().setScaleY(1);
                                this.flipped = false;
                            }
                            int random = rand.nextInt(1,10);
                            if(random>5){
                                generate_cherry();
                            }
                            this.has_increased = false;
                            pause_button.setVisible(true);
                            pause_label.setVisible(true);
                        }
                        else {
                        obstacle1.setY(newY);
                        if(flag.get()){
                            obstacle1.setX(obstacle1.getX()-2);
                            flag.set(false);
                        }
                        else {
                            obstacle1.setX(obstacle1.getX()+2);
                            flag.set(true);
                        }
                        }
                    });
                }, 0, INCREMENT_INTERVAL, TimeUnit.MILLISECONDS);
            }
        } finally {
            executorServiceLock.unlock();
        }

    }

    private void generate_cherry(){
        int x_coord = rand.nextInt(102,distance_from_pillar+70);
        int y_coord = rand.nextInt(1,10);
        this.cherries.setX_pos(x_coord);
        if(y_coord>1 && y_coord<6){
            this.cherries.setIs_Up(true);
        }
        else {
            this.cherries.setIs_Up(false);
        }
        if(pane2.getChildren().contains(this.cherries.return_cherryimg())){

        }
        else {
        pane2.getChildren().add(this.cherries.return_cherryimg());
        }

    }

    //distance check for the danda
    private void distance_check(){

        //danda too short
        if(this.distance_from_pillar > rectangle.getHeight()){
            System.out.println(distance_from_pillar);
            System.out.println(rectangle.getHeight());
            System.out.println("Fail");
            rotate_danda(0);
        }

        //danda too long
        else if(this.distance_from_pillar+pillar_width < rectangle.getHeight()){
            System.out.println(distance_from_pillar);
            System.out.println(rectangle.getHeight());
            System.out.println("Fail_toolong");
            rotate_danda(1);

        }

        //danda just right
        else{
            rotate_danda(2);
        }
    }

    private void rotate_danda(int i){
        //danda too small
        if(i == 0){
        //This here is the rotation animation for the stick
            rectangle.setHeight(2*rectangle.getHeight());
            RotateTransition rotate = new RotateTransition();
            rotate.setAutoReverse(false);
            rotate.byAngleProperty();
            rotate.setByAngle(90);
            rotate.setCycleCount(1);

            rotate.setDuration(Duration.millis(500));
            rotate.setNode(rectangle);
            rotate.play();

            TranslateTransition translate = new TranslateTransition();
            translate.setAutoReverse(false);
            translate.setCycleCount(1);
            translate.setDuration(Duration.millis(1000));
            translate.setByY(210);
            translate.setNode(rectangle);

            rotate.setOnFinished(event -> {
                rectangle.setHeight(rectangle.getHeight()/2);
                rectangle.setY(rectangle.getY()+rectangle.getHeight()/2);
                rectangle.setX(rectangle.getX()+rectangle.getHeight()/2);
                translate.play();

            });
            translate.setOnFinished(event -> {
                startleft(0);

            });

        }
        //danda too large
        else if(i == 1){
            rectangle.setHeight(2*rectangle.getHeight());
            RotateTransition rotate = new RotateTransition();
            rotate.setAutoReverse(false);
            rotate.byAngleProperty();
            rotate.setByAngle(90);
            rotate.setCycleCount(1);

            rotate.setDuration(Duration.millis(750));
            rotate.setNode(rectangle);
            rotate.play();
            rotate.setOnFinished(event -> {
                rectangle.setHeight(rectangle.getHeight()/2);
                rectangle.setY(rectangle.getY()+rectangle.getHeight()/2);
                rectangle.setX(rectangle.getX()+rectangle.getHeight()/2);
                startleft(1);;

            });
        }
        else{
            //danda just right
            rectangle.setHeight(2*rectangle.getHeight());
            RotateTransition rotate = new RotateTransition();
            rotate.setAutoReverse(false);
            rotate.byAngleProperty();
            rotate.setByAngle(90);
            rotate.setCycleCount(1);
            rotate.setDuration(Duration.millis(750));
            rotate.setNode(rectangle);
            rotate.play();
            rotate.setOnFinished(event -> {
                rectangle.setHeight(rectangle.getHeight()/2);
                rectangle.setY(rectangle.getY()+rectangle.getHeight()/2);
                rectangle.setX(rectangle.getX()+rectangle.getHeight()/2);
                startleft(2);;

            });
        }
    }

    private void ninja_flip(){
        if(flipped){
            this.ninja.getCharacter().setY(340);
            this.ninja.getCharacter().setScaleY(1);
            flipped = false;
        }
        else {
            this.ninja.getCharacter().setY(395);
            this.ninja.getCharacter().setScaleY(-1);
            flipped = true;
        }
    }

    private void cherry_collision(){
        if((ninja.getCharacter().getX()>cherries.getX_pos()&& ninja.getCharacter().getX()<cherries.getX_pos()+ 25) && cherries.isIs_Up() == !this.flipped){
            if(pane2.getChildren().contains(this.cherries.return_cherryimg())){
                pane2.getChildren().remove(this.cherries.return_cherryimg());
                ninja.setCherries(ninja.getCherries()+1);
                cherry.setText(Integer.toString(ninja.getCherries()));
                rotate_cherry.play();
            }
        }
    }

    private void scene_change(int i){
        //shifting the scene
        executorServiceLock.lock();
        try {
            double originalX = obstacle1.getX();
            if(pane2.getChildren().contains(cherries.return_cherryimg())){
                pane2.getChildren().remove(cherries.return_cherryimg());
            }
            if (executorService == null || executorService.isShutdown()) {
                executorService = Executors.newSingleThreadScheduledExecutor();
                executorService.scheduleAtFixedRate(() -> {
                    Platform.runLater(() -> {
                        double currentX_obs1 = obstacle1.getX();

                        double currentX_ninja = ninja.getCharacter().getX();


                        double currentX_startingplatform = staring_platform.getX();


                        double currentX_rect = rectangle.getX();

                        if(currentX_obs1 - (-1*(obstacle1.getWidth())+100) > 50){
                            double newX_obs1 = currentX_obs1 - 3;
                            double newX_ninja = currentX_ninja - 3;
                            double newX_startingplatform = currentX_startingplatform - 3;
                            double newX_rect = currentX_rect - 3;
                            if(newX_obs1 <= -1*(obstacle1.getWidth())+100 ){
                                stopleft();
                                if (pane2 != null) {
                                    pane2.getChildren().removeAll(staring_platform);
                                    staring_platform.setY(390);
                                    staring_platform.setX(0);
                                    staring_platform.setHeight(210);
                                    staring_platform.setWidth(100);
                                    staring_platform.setFill(Color.BLACK);
                                    pane2.getChildren().add(staring_platform);
                                }
                                    generate_pillars(pane2,i);

                            }
                            else {
                                //System.out.println(-1*(rectangle.getHeight()));
                                if(newX_rect > -1*(rectangle.getHeight())){
                                    rectangle.setX(newX_rect);
                                }
                                obstacle1.setX(newX_obs1);
                                ninja.getCharacter().setX(newX_ninja);
                                staring_platform.setX(newX_startingplatform);
                            }
                        }
                        else {
                            double newX_obs1 = currentX_obs1 - 1;
                            double newX_ninja = currentX_ninja - 1;
                            double newX_startingplatform = currentX_startingplatform - 1;
                            double newX_rect = currentX_rect - 1;
                            if(newX_obs1 <= -1*(obstacle1.getWidth())+100 ){
                                stopleft();
                                if (pane2 != null) {
                                    pane2.getChildren().removeAll(staring_platform);
                                    staring_platform.setY(390);
                                    staring_platform.setX(0);
                                    staring_platform.setHeight(210);
                                    staring_platform.setWidth(100);
                                    staring_platform.setFill(Color.BLACK);
                                    pane2.getChildren().add(staring_platform);
                                }
                                generate_pillars(pane2,i);

                            }
                            else {
                                //System.out.println(-1*(rectangle.getHeight()));
                                if(newX_rect > -1*(rectangle.getHeight())){
                                    rectangle.setX(newX_rect);
                                }
                                obstacle1.setX(newX_obs1);
                                ninja.getCharacter().setX(newX_ninja);
                                staring_platform.setX(newX_startingplatform);
                            }
                        }


                        });
                    }, 0, INCREMENT_INTERVAL, TimeUnit.MILLISECONDS);
                }
                        }

        finally {
            executorServiceLock.unlock();
        }
        }
    public void quit_game(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("title_screen.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    private void save_game() throws IOException {
        System.out.println("ll");
        PrintWriter out = null;
        Scanner in = null;
        String high_score = "null";
        try {
            in = new Scanner(new  BufferedInputStream(new FileInputStream("SAVES.txt")));
            if(in.hasNext()){
                high_score = in.next();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            in.close();
        }
        try {
            out = new PrintWriter(new FileOutputStream("SAVES.txt"));
            if (high_score.equals("null")){
                out.println(0);
            }
            else {
                out.println(high_score);
            }
            out.println(ninja.getScore());
            out.println(ninja.getCherries());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(out != null){
            out.close();
            }
        }
    }

    private void end_screen(){
        fail_screen.setVisible(true);
        //Group group = new Group(pane2);
        // Apply blur effect to the Group
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(5); // Set the radius of the blur
        pane2.setEffect(blur);
        fail_screen.getChildren().removeAll(game_over,revive,end_score,retry_endscreen,retry_endscreen_button,revive_button,highsore_label);
        ImageView end_scroll = new ImageView();
        end_scroll.setFitHeight(400);
        end_scroll.setFitWidth(280);
        end_scroll.setImage(new Image(getClass().getResourceAsStream("/Image/pause_menu.png")));
        retry_endscreen.setImage(new Image(getClass().getResourceAsStream("/Image/retry.png")));
        end_score.setText(Integer.toString(ninja.getScore()));
        end_score.setFont(new Font("Calibri", 30));
        highsore_label.setVisible(false);

        Scanner in = null;
        PrintWriter out = null;
        try {
            in = new Scanner(new BufferedInputStream(new FileInputStream("SAVES.txt")));
            if(in.hasNext()){
                if(Integer.parseInt(in.next()) < ninja.getScore()){
                    try {
                        highsore_label.setVisible(true);
                        out = new PrintWriter(new FileOutputStream("SAVES.txt"));
                        out.println(ninja.getScore());
                        String j="";
                        while(in.hasNext()){
                            j = in.next();
                            out.println(j);
                        }
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }finally {
                        if(out != null){
                            out.close();
                        }
                    }
                }
            }
            else {
                try {
                    highsore_label.setVisible(true);
                    out = new PrintWriter(new FileOutputStream("SAVES.txt"));
                    out.println(ninja.getScore());
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }finally {
                    if(out != null){
                        out.close();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            if(in != null){
                in.close();
            }
        }

        revive.setText("REVIVE");
        revive.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/Kamikaze3DGradient.ttf"), 32));

        game_over.setText("GAME OVER");
        game_over.setFont(new Font("Calibri", 30));

        fail_screen.getChildren().addAll(end_scroll,game_over,revive,end_score,retry_endscreen,retry_endscreen_button,revive_button,highsore_label);

        retry_endscreen_button.setOnAction(event -> {
            fail_screen.setVisible(false);
            try {
                root = FXMLLoader.load(getClass().getResource("main_game.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        });

        revive_button.setOnAction(event -> {
            if(ninja.getCherries() >= 3){
                fail_screen.setVisible(false);
                pane2.setEffect(null);
                scene_change(0);
                ninja.setCherries(ninja.getCherries()-3);
                cherry.setText(Integer.toString(ninja.getCherries()));
            }
            else {

            }

        });



    }

    private void pause_screen(){
        paused = true;
        pause_button.setVisible(false);
        pause_label.setVisible(false);
        //Group group = new Group(pane2);
        // Apply blur effect to the Group
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(5); // Set the radius of the blur
        pane2.setEffect(blur);
        pause_pane.setVisible(true);

        ImageView pause_menu = new ImageView();
        pause_menu.setFitHeight(400);
        pause_menu.setFitWidth(280);
        //pause_pain.setLayoutX();
        //pause_pain.setLayoutY();
        pause_menu.setImage(new Image(getClass().getResourceAsStream("/Image/pause_menu.png")));
        this.pause_pane.getChildren().removeAll(resume_label,save_label,quit_label,resume_button,save_button,quit_button);

        resume_label.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/Kamikaze3DGradient.ttf"), 32));
        resume_label.setText("RESUME");

        save_label.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/Kamikaze3DGradient.ttf"), 32));
        save_label.setText("SAVE");

        quit_label.setFont(Font.loadFont(getClass().getResourceAsStream("/Font/Kamikaze3DGradient.ttf"), 32));
        quit_label.setText("QUIT");


        this.pause_pane.getChildren().addAll(pause_menu,resume_label,save_label,quit_label,resume_button,save_button,quit_button);

        resume_button.setOnAction(event1 -> {
            paused = false;
            System.out.println("HI");
            pause_button.setVisible(true);
            pause_label.setVisible(true);
            pane2.setEffect(null);
            pause_pane.setVisible(false);
        });

        quit_button.setOnAction(event1 -> {
            try {
                this.quit_game(event1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        save_button.setOnAction(event1 -> {
            try {
                this.save_game();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

    }

    //ignore this buttopn
    public void button_left(){
        ninja.getCharacter().setX(ninja.getCharacter().getX()-5);
        System.out.println(ninja.getCharacter().getX());
        ninja.getCharacter().setX(0);
    }

}
