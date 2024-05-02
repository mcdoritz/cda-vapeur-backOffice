package com.vapeur.dao;

import static com.vapeur.config.Debug.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.vapeur.beans.User;
import com.vapeur.config.Database;
import com.vapeur.dao.UserDAO;

public class UserDAO {

	
	public Boolean save(User object, Boolean admin) throws DAOException {
		try {
			
			String hashPassword = BCrypt.hashpw(object.getPassword(), BCrypt.gensalt());

			if (object.getId() != 0) {

			       String query = "UPDATE users SET email = ?, nickname = ?, password = ?, firstname = ?, lastname = ?, active = ?, shipping_address = ?, avatar = ? WHERE id = ?";
			        
			        try (PreparedStatement ps = Database.connexion.prepareStatement(query)) {
			            ps.setString(1, object.getEmail());
			            ps.setString(2, object.getNickname());
			            if(admin) {
			            	ps.setString(3, object.getPassword());
			            }else {
			            	ps.setString(3, hashPassword);
			            }
			            
			            ps.setString(4, object.getFirstname());
			            ps.setString(5, object.getLastname());
			            ps.setBoolean(6, object.isActive());
			            ps.setString(7, object.getShippingAddress());
			            ps.setString(8, object.getAvatar());
			            ps.setInt(9, object.getId());

			            ps.executeUpdate();
			        }
				String objectInfos = object.getNickname() + " " + object.getEmail();
				bddSays("update", true, object.getId(), objectInfos);
				return true;

			} else {
				String query = "INSERT INTO users (email, nickname, password, firstname, lastname, active, shipping_address, avatar) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		        
		        try (PreparedStatement ps = Database.connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
		            ps.setString(1, object.getEmail());
		            ps.setString(2, object.getNickname());
		            ps.setString(3, hashPassword);
		            ps.setString(4, object.getFirstname());
		            ps.setString(5, object.getLastname());
		            ps.setBoolean(6, true); // Hé oui !
		            ps.setString(7, object.getShippingAddress());
		            ps.setString(8, object.getAvatar());

		            ps.executeUpdate();
		            
		            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
		                if (generatedKeys.next()) {
		                	String objectInfos = object.getNickname() + " " + object.getEmail();
		                	bddSays("create", true, generatedKeys.getInt(1), objectInfos);
		                	return true;
		                } else {
		                	bddSays("create", false, object.getId(), null);
		                    throw new DAOException("Erreur avec la base de données");
		                    
		                }
		            }
		        }
			}
			

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		
		
	}

	
	public User getById(int user_id) {
		try {

			PreparedStatement ps = Database.connexion.prepareStatement("SELECT * FROM users WHERE id = ?");
	        ps.setInt(1, user_id);

			ResultSet resultat = ps.executeQuery();

			User object = new User();
			
			while (resultat.next()) {
                object.setId(resultat.getInt("id"));
                object.setEmail(resultat.getString("email"));
                object.setNickname(resultat.getString("nickname"));
                object.setPassword(resultat.getString("password"));
                object.setFirstname(resultat.getString("firstname"));
                object.setLastname(resultat.getString("lastname"));
                object.setActive(resultat.getBoolean("active"));
                object.setShippingAddress(resultat.getString("shipping_address"));
                object.setAvatar(resultat.getString("avatar"));
            }
			
			String objectInfos = object.getFirstname() + " " + object.getLastname();
			bddSays("read", true, object.getId(), objectInfos);
			return object;

		} catch (Exception ex) {
			ex.printStackTrace();
			bddSays("read", false, user_id, null);
			return null;
		}
	}
	
	public User login(String email, String password) throws DAOException {
		try {

			PreparedStatement ps = Database.connexion.prepareStatement("SELECT id, password FROM users WHERE email = ? AND active = true");
	        ps.setString(1, email);

			ResultSet resultat = ps.executeQuery();

			User object = new User();
			
			if(!resultat.next()) {
                
                throw new DAOException("Utilisateur non trouvé dans la BDD");
            }else {
            	object.setId(resultat.getInt("id"));
                object.setPassword(resultat.getString("password"));
                
                String objectInfos = object.getId() + " " + object.getEmail();
    			bddSays("read", true, object.getId(), objectInfos);
    			
    			if (BCrypt.checkpw(password, object.getPassword())) {
    				
    				User authorizedUser = getById(object.getId());
    				
    				return authorizedUser;
    				
    			}else {
    				throw new DAOException("Le mot de passe n'est pas bon.");
    			}	
            }
			
					

		} catch (Exception ex) {
			ex.printStackTrace();
			prln("Erreur avec la récupération de l'user");
			return null;
		}
	}

	
	public List<User> readAll(String status) {

        ArrayList<User> usersList = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
        	switch (status) {
        	case "active":
        		query += " WHERE active = true";
        		break;
        	case "archived":
        		query += " WHERE active = false";
        		break;
        	}
        	
            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {
                User objet = new User();

                objet.setId(resultat.getInt("id"));
                objet.setEmail(resultat.getString("email"));
                objet.setNickname(resultat.getString("nickname"));
                objet.setPassword(resultat.getString("password"));
                objet.setFirstname(resultat.getString("firstname"));
                objet.setLastname(resultat.getString("lastname"));
                objet.setActive(resultat.getBoolean("active"));
                objet.setShippingAddress(resultat.getString("shipping_address"));
                objet.setAvatar(resultat.getString("avatar"));

                usersList.add(objet);
            }
			bddSays("readAll", true, usersList.size(), null);
            return usersList;
        } catch (Exception ex) {
        	bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            return null;
        }
	}

	
	public void delete(int user_id) {
		// TODO Auto-generated method stub
		try {

			PreparedStatement ps = Database.connexion.prepareStatement("DELETE FROM users WHERE id = ?");
			ps.setInt(1, user_id);

			ps.executeUpdate();

			bddSays("delete", true, user_id, null);

		} catch (Exception ex) {
			bddSays("delete", false, user_id, null);
			ex.printStackTrace();
		}
		
	}

}
