package com.vapeur.beans;

import java.io.Serializable;

public class Platform implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private String acronym;

    // Constructeurs
    public Platform() {
    }

    public Platform(int id, String name) {
        setId(id);
        setName(name);
        setAcronym(acronym);
    }

    // Getters et setters
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
    
    

    public String getAcronym() {
		return acronym;
	}

	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}

	@Override
	public String toString() {
		return "Platform [id=" + id + ", name=" + name + ", acronym=" + acronym + "]";
	}

}