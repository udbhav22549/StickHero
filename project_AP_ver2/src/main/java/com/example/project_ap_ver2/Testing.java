package com.example.project_ap_ver2;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Testing {
    @Test
    public void ninja_specification_test(){
        Ninja ninja= Ninja.get_instance();
        Double height = ninja.getCharacter().getFitHeight();
        Double width = ninja.getCharacter().getFitWidth();
        Double x_pos = ninja.getCharacter().getX();
        Double y_pos = ninja.getCharacter().getY();
        boolean result = false;
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = false;
        boolean result4 = false;
        if(height == 50){
            result1 = true;
        }
        else {
            result1 = false;
        }
        if(width == 25){
            result2 = true;
        }
        else {
            result2 = false;
        }
        if(x_pos == 70){
            result3 = true;
        }
        else {
            result3 = false;
        }
        if(y_pos == 340){
            result4 = true;
        }
        else {
            result4 = false;
        }
        if(result1 && result2 && result3 && result4){
            result = true;
        }
        else {
            result = false;
        }
        assertTrue(result);
    }

    @Test
    public void cherry_specification_test(){
        Cherries cherries= new Cherries();
        Double height = cherries.return_cherryimg().getFitHeight();
        Double width = cherries.return_cherryimg().getFitWidth();
        Double x_pos = cherries.return_cherryimg().getX();
        Double y_pos = cherries.return_cherryimg().getY();
        boolean result = false;
        boolean result1 = false;
        boolean result2 = false;
        boolean result3 = false;
        boolean result4 = false;
        if(height == 25){
            result1 = true;
        }
        else {
            result1 = false;
        }
        if(width == 25){
            result2 = true;
        }
        else {
            result2 = false;
        }
        if(x_pos == 0){
            result3 = true;
        }
        else {
            result3 = false;
        }
        if(y_pos == 0){
            result4 = true;
        }
        else {
            result4 = false;
        }
        if(result1 && result2 && result3 && result4){
            result = true;
        }
        else {
            result = false;
        }
        assertTrue(result);
    }

    @Test
    public void Save_test(){
        Scanner in = null;
        boolean result = false;
        try {
            in = new Scanner(new BufferedInputStream(new FileInputStream("SAVES.txt")));
            while(in.hasNext()){
                if(in.hasNextBigInteger()){
                    in.next();
                    result = true;
                }
                else {
                    result = false;
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }finally {
            if(in != null){
                in.close();
            }
    }
        assertTrue(result);
    }
}
