package com.vapeur.beans;

import java.io.Serializable;
import java.sql.Timestamp;

public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String content;
    private Timestamp uploaded;
    private int score;
    private int userId;
    private String userNickname;
    private int gameId;

    public Comment() {
    }

    public Comment(String content, Timestamp uploaded, int score, int userId, String userNickname, int gameId) throws BeanException {
    	
        setContent(content);
        setUploaded(uploaded);
        setScore(score);
        setUserId(userId);
        setUserNickname(userNickname);
        setGameId(gameId);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) throws BeanException {
    	if(content.length() < 6000) {
    		if(content.length() > 25) {
    			this.content = content;
    		}else {
        		throw new BeanException("L'évaluation est trop courte !");
        	}
    	}else {
    		throw new BeanException("L'évaluation est trop longue !");
    	}
        
    }

    public Timestamp getUploaded() {
        return uploaded;
    }

    public void setUploaded(Timestamp uploaded) {
        this.uploaded = uploaded;
    }

    public float getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}

	public String getUserNickname() {
		return userNickname;
	}

	public void setUserNickname(String userNickname) {
		this.userNickname = userNickname;
	}

	@Override
    public String toString() {
        return "Comment{" +
                ", content='" + content + '\'' +
                ", uploaded=" + uploaded +
                ", score=" + score +
                ", userId=" + userId +
                '}';
    }
}