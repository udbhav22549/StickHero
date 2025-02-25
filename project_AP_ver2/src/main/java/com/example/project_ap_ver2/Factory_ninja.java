package com.example.project_ap_ver2;

//Factoryu producing ninjas

public class Factory_ninja implements Factory_produced{
    private String id;
    @Override
    public void id(String id) {
        this.id = id;
    }
    public Ninja get_product(){
        return Ninja.get_instance();
    }
}
