package com.vapeur.beans;

import java.io.Serializable;

public class Video implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private int videoType;
    private int gameId;

    // Constructeurs
    public Video() {
    }

    public Video(int id, String name, int videoType, int gameId) {
        setId(id);
        setName(name);
        setVideoType(videoType);
        setGameId(gameId);
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

    public int getVideoType() {
        return videoType;
    }

    public void setVideoType(int videoType) {
        this.videoType = videoType;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    // Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", videoType=" + videoType +
                ", gameId=" + gameId +
                '}';
    }
}
