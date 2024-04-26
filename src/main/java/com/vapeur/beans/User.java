package com.vapeur.beans;

import java.io.Serializable;
import java.util.regex.Pattern;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String email;
    private String nickname;
    private String password;
    private String firstname;
    private String lastname;
    private boolean active;
    private String shippingAddress;
    private String avatar;

    // Constructeurs
    public User() {
    }
    
    public User(String email, String nickname, String password ) throws BeanException {
        setEmail(email);
        setNickname(nickname);
        setPassword(password);
    }

    public User(int id, String email, String nickname, String password, String firstname, String lastname, boolean active, String shippingAddress, String avatar) throws BeanException {
        setId(id);
        setEmail(email);
        setNickname(nickname);
        setPassword(password);
        setFirstname(firstname);
        setLastname(lastname);
        setActive(active);
        setShippingAddress(shippingAddress);
        setAvatar(avatar);
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws BeanException {
    	String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    	
    	if(Pattern.compile(regex).matcher(email).matches()) {
    		this.email = email;
    	}else {
    		throw new BeanException("L'email n'est pas valide");
    	}
    	
        
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) throws BeanException {
    	
    	if(nickname.length() < 3) {
    		throw new BeanException("Le pseudonyme est trop court");
    	}else if(nickname.length() > 30){
    		throw new BeanException("Le pseudonyme est trop long");
    	}else {
    		this.nickname = nickname;
    	}
        
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
    	if(firstname != null) {
    		this.firstname = firstname.substring(0,1).toUpperCase() + firstname.substring(1).toLowerCase();
    	}else {
    		this.firstname = null;
    	}
        
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
    	if(lastname != null) {
    		this.lastname = lastname.substring(0,1).toUpperCase() + lastname.substring(1).toLowerCase();
    	}else {
    		this.lastname = null;
    	}
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	// Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", active=" + active +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }
}
