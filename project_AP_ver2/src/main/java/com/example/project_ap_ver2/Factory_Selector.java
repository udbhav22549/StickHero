package com.example.project_ap_ver2;

//ABSTRACT FACTORY used to slect between differnt factories

public class Factory_Selector {
    public static Factory_produced factory(int i){
        if(i == 0){
            return new  Factory_ninja();
        }
        else if(i == 1){
            return new Factory_cherry();
        }
        else {
            return null;
        }
    }
}
