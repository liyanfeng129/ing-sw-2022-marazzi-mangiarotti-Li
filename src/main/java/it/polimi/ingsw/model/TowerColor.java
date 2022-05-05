package it.polimi.ingsw.model;

import java.io.Serializable;

public enum TowerColor implements Serializable {
    WHITE ("WHITE"),BLACK ("BLACK"),GREY ("GREY"),NO_COLOR ("NO_COLOR");

    private final String name;

    private TowerColor(String s) {
        name = s;
    }


    @Override
    public String toString(){
        return this.name;
    }

}
