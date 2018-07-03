package com.crouchingrobot.setsgame;

public class Spot {
    private Spot parent = null;
    private char color = 'X';

    public Spot(Spot inParent, char inColor){
        parent = inParent;
        color = inColor;
    }
    public Spot(Spot in,Spot inParent){
        parent = inParent;
        color = in.color;
    }

    public char getColor(){
        return color;
    }

    public Spot getParent(){
        return parent;
    }

    public Spot removePeice(){
        color = 'X';
        return parent;
    }

}
