package com.vapeur.beans;

import java.io.Serializable;
import java.sql.Date;

public class Developer implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private Date creationDate;
    private String country;
    private String urlInstagram;
    private String urlX;
    private String urlFacebook;
    private String urlWebsite;

    // Constructeurs
    public Developer() {
    }

    public Developer(int id, String name, Date creationDate, String country, String urlInstagram, String urlX, String urlFacebook, String urlWebsite) {
        setId(id);
        setName(name);
        setCreationDate(creationDate);
        setCountry(country);
        setUrlInstagram(urlInstagram);
        setUrlX(urlX);
        setUrlFacebook(urlFacebook);
        setUrlWebsite(urlWebsite);
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrlInstagram() {
        return urlInstagram;
    }

    public void setUrlInstagram(String urlInstagram) {
        this.urlInstagram = urlInstagram;
    }

    public String getUrlX() {
        return urlX;
    }

    public void setUrlX(String urlX) {
        this.urlX = urlX;
    }

    public String getUrlFacebook() {
        return urlFacebook;
    }

    public void setUrlFacebook(String urlFacebook) {
        this.urlFacebook = urlFacebook;
    }

    public String getUrlWebsite() {
        return urlWebsite;
    }

    public void setUrlWebsite(String urlWebsite) {
        this.urlWebsite = urlWebsite;
    }

    // Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "Developer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", creationDate=" + creationDate +
                ", country='" + country + '\'' +
                ", urlInstagram='" + urlInstagram + '\'' +
                ", urlX='" + urlX + '\'' +
                ", urlFacebook='" + urlFacebook + '\'' +
                ", urlWebsite='" + urlWebsite + '\'' +
                '}';
    }
}
