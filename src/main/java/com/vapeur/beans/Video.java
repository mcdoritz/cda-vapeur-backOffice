package com.vapeur.beans;

import java.io.Serializable;

public class Video implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String url;
    private int gameId;

    // Constructeurs
    public Video() {
    }

    public Video(int id, String url, int gameId) {
        setId(id);
        setUrl(url);
        setGameId(gameId);
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

	@Override
	public String toString() {
		return "Video [id=" + id + ", url=" + url + ", gameId=" + gameId + "]";
	}

}
