package com.example.ultimateraptrivia;

public class Score {
    private String higschore;
    private String name;

    private Score(){}

    public String getHigschore(){
        return higschore;
    }

    public String getName(){
        return name;
    }

    public void setHighscore(String higschore){
        this.higschore = higschore;
    }

    public void setName(String name){
        this.name = name;
    }
}
