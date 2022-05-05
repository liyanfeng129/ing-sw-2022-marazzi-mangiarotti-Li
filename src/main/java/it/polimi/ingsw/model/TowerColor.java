package it.polimi.ingsw.model;

public enum TowerColor {
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
