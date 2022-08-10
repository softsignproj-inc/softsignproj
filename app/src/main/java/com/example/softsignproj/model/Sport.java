package com.example.softsignproj.model;

public class Sport implements Comparable<Sport>{

    private String name;
    public Sport(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public int compareTo(Sport o) {
        return name.compareToIgnoreCase(o.getName());
    }

    @Override
    public boolean equals(Object o){
        if (o instanceof Sport){
            return name.equalsIgnoreCase(((Sport) o).getName());
        }
        return false;
    }
}
