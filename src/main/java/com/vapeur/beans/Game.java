package com.vapeur.beans;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Game implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String title;
    private String description;
    private int classification;
    private float price;
    private Date releaseDate;
    private float usersAvgScore;
    private int totalReviews;
    private boolean controllerSupport;
    private boolean requires3rdPartyAccount;
    private int stock;
    private ArrayList<String> tags;
    private int developerId;
    private Developer developer;
    private ArrayList<Genre> genres;
    private ArrayList<Mode> modes;
    private int platformId;
    private Platform platform;
    private ArrayList<Video> videos;
    private ArrayList<Language> languages;
    private ArrayList<Comment> comments;
    private Comment comment; //Pour affichage du commentaire d'un joueur en particulier

    // Constructeurs
    public Game() {
    }
    
    //Light pour liste des jeux
    public Game(int id, String title,  float price, Date releaseDate, float usersAvgScore, 
    		int totalReviews, int stock, ArrayList<String> tags, Platform platform) {
    	
        setId(id);
        setTitle(title);
        setPrice(price);
        setReleaseDate(releaseDate);
        setUsersAvgScore(usersAvgScore);
        setTotalReviews(totalReviews);
        setStock(stock);
        setTags(tags);
        setPlatform(platform);
    }
    
    //Library
    public Game(int id, String title,  float price, Date releaseDate, float usersAvgScore, 
    		int totalReviews, int stock, ArrayList<String> tags, Platform platform, Comment comment) {
    	
        setId(id);
        setTitle(title);
        setPrice(price);
        setReleaseDate(releaseDate);
        setUsersAvgScore(usersAvgScore);
        setTotalReviews(totalReviews);
        setStock(stock);
        setTags(tags);
        setPlatform(platform);
        setComment(comment);
    }
    
    //Titre et stock
    public Game(String title, int stock) {
    	setTitle(title);
    	setStock(stock);
    }
    
    //Complet pour page détail (moins Comment d'un seul joueur)
    public Game(int id, String title, String description, int classification, float price, Date releaseDate, float usersAvgScore, 
    		int totalReviews, boolean controllerSupport, boolean requires3rdPartyAccount, int stock, ArrayList<String> tags, int developerId, Developer developer,
    		ArrayList<Genre> genres, ArrayList<Mode> modes, int platformId, Platform platform, ArrayList<Video> videos, ArrayList<Language> languages, ArrayList<Comment> comments) {
    	
        setId(id);
        setTitle(title);
        setDescription(description);
        setClassification(classification);
        setPrice(price);
        setReleaseDate(releaseDate);
        setUsersAvgScore(usersAvgScore);
        setTotalReviews(totalReviews);
        setControllerSupport(controllerSupport);
        setRequires3rdPartyAccount(requires3rdPartyAccount);
        setStock(stock);
        setTags(tags);
        setDeveloperId(id);
        setDeveloper(developer);
        setGenres(genres);
        setModes(modes);
        setPlatformId(platformId);
        setPlatform(platform);
        setVideos(videos);
        setLanguages(languages);
        setComments(comments);
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getClassification() {
        return classification;
    }

    public void setClassification(int classification) {
        this.classification = classification;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public float getUsersAvgScore() {
        return usersAvgScore;
    }

    public void setUsersAvgScore(float usersAvgScore) {
        this.usersAvgScore = usersAvgScore;
    }
    

    public int getTotalReviews() {
		return totalReviews;
	}

	public void setTotalReviews(int totalReviews) {
		this.totalReviews = totalReviews;
	}

	public boolean isControllerSupport() {
        return controllerSupport;
    }

    public void setControllerSupport(boolean controllerSupport) {
        this.controllerSupport = controllerSupport;
    }

    public boolean isRequires3rdPartyAccount() {
        return requires3rdPartyAccount;
    }

    public void setRequires3rdPartyAccount(boolean requires3rdPartyAccount) {
        this.requires3rdPartyAccount = requires3rdPartyAccount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }
   

    public int getDeveloperId() {
		return developerId;
	}

	public void setDeveloperId(int developerId) {
		this.developerId = developerId;
	}

	public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }

    public ArrayList<Genre> getGenres() {
		return genres;
	}

	public void setGenres(ArrayList<Genre> genres) {
		this.genres = genres;
	}

	public ArrayList<Mode> getModes() {
		return modes;
	}

	public void setModes(ArrayList<Mode> modes) {
		this.modes = modes;
	}

	public int getPlatformId() {
		return platformId;
	}

	public void setPlatformId(int platformId) {
		this.platformId = platformId;
	}

	public Platform getPlatform() {
		return platform;
	}

	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	public ArrayList<Video> getVideos() {
		return videos;
	}

	public void setVideos(ArrayList<Video> videos) {
		this.videos = videos;
	}

	public ArrayList<Language> getLanguages() {
		return languages;
	}

	public void setLanguages(ArrayList<Language> languages) {
		this.languages = languages;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", title=" + title + ", description=" + description + ", classification="
				+ classification + ", price=" + price + ", releaseDate=" + releaseDate + ", usersAvgScore="
				+ usersAvgScore + ", totalReviews=" + totalReviews + ", controllerSupport=" + controllerSupport
				+ ", requires3rdPartyAccount=" + requires3rdPartyAccount + ", stock=" + stock + ", tags=" + tags
				+ ", developerId=" + developerId + ", developer=" + developer + ", genres=" + genres + ", modes="
				+ modes + ", platformId=" + platformId + ", platform=" + platform + ", videos=" + videos
				+ ", languages=" + languages + "]";
	}

	

}