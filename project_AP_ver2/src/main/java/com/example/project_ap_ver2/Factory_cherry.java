package com.example.project_ap_ver2;

//factory prooducing Cherries

public class Factory_cherry implements Factory_produced{
    private String id;
    @Override
    public void id(String id) {
        this.id = id;
    }

    public Cherries get_product(){
        return new Cherries();
    }
}
