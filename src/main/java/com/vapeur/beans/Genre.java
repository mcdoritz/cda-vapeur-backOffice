package com.vapeur.beans;

import java.io.Serializable;

public class Genre implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;

    public Genre() {
    }

    public Genre(int id, String name) {
        setId(id);
        setName(name);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}