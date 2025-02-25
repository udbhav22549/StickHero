package com.example.project_ap_ver2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cherries {
    private float X_pos;
    private boolean is_Up;
    private ImageView cherry_img;

    public Cherries() {
        this.cherry_img = new ImageView();
        cherry_img.setFitWidth(25);
        cherry_img.setFitHeight(25);
        cherry_img.setImage(new Image(getClass().getResourceAsStream("/Image/SHARINGAN.png")));
    }

    public float getX_pos() {
        return X_pos;
    }

    public void setX_pos(float x_pos)
    {
        X_pos = x_pos;
        this.cherry_img.setX(X_pos);
    }

    public boolean isIs_Up() {

        return is_Up;
    }

    public void setIs_Up(boolean is_Up) {
        this.is_Up = is_Up;
        if(is_Up){
            this.cherry_img.setY(360);
        }
        else {
            this.cherry_img.setY(420);
        }
    }

    public boolean is_Touching(){

        return false;
    }

    public ImageView return_cherryimg(){
        return this.cherry_img;
    }

}
