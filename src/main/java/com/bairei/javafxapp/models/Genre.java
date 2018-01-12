package com.bairei.javafxapp.models;

public enum  Genre {
    ROCK ("Rock"), METAL ("Metal"), POP ("Pop"), JAZZ ("Jazz"), BLUES ("Blues"), PROGRESSIVE ("Progressive"),
    ALTERNATIVE ("Alternative"), PUNK ("Punk"), HARDCORE ("Hardcore"), GRINDCORE("Grindcore"), THRASH("Thrash Metal"),
    RAP("Rap"), FUNK("Funk"), SLUDGE("Sludge Metal"), GROOVE("Groove Metal"), DEATH("Death Metal"),
    BLACK("Black Metal"), MATH ("Math Metal"), MATHCORE ("Mathcore");

    private final String name;

    Genre(String name){
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
