package com.example.project_ap_ver2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class Ninja {
    private int score;
    private int cherries;
    private static Ninja ninja = null;
    private ImageView Character = new ImageView();
    private Ninja(){
        this.score = 0;
        this.cherries = 0;
        this.Character.setX(70);
        this.Character.setY(340);
        this.Character.setFitHeight(50);
        this.Character.setFitWidth(25);
        this.Character.setImage(new Image(getClass().getResourceAsStream("/Image/ninja2_nostick.png")));
    }


    public static Ninja get_instance(){
        if(ninja == null){
            ninja = new Ninja();
            return ninja;
        }
        else {
            return new Ninja();
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCherries() {
        return cherries;
    }

    public void setCherries(int cherries) {
        this.cherries = cherries;
    }

    public ImageView getCharacter() {
        return Character;
    }

    public void setCharacter(ImageView character) {
        Character = character;
    }

    public void revive(){

    }
    public void move(){

    }
    public void flip(){

    }
    public int strech_Stick(){
        return 0;
    }
}
