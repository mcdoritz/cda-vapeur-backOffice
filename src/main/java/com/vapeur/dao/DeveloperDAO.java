package com.vapeur.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vapeur.beans.Developer;
import com.vapeur.beans.Mode;
import com.vapeur.config.Database;

import static com.vapeur.config.Debug.*;

public class DeveloperDAO {

    public Boolean save(Developer object) {
        try {
            if (object.getId() != 0) {
            	prln("update developer");
                String query = "UPDATE developers SET name = ?, creation_date = ?, country = ?, url_instagram = ?, url_x = ?, url_facebook = ?, url_website = ? WHERE id = ?";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query)) {
                    ps.setString(1, object.getName());
                    ps.setDate(2, new java.sql.Date(object.getCreationDate().getTime()));
                    ps.setString(3, object.getCountry());
                    ps.setString(4, object.getUrlInstagram());
                    ps.setString(5, object.getUrlX());
                    ps.setString(6, object.getUrlFacebook());
                    ps.setString(7, object.getUrlWebsite());
                    ps.setInt(8, object.getId());

                    ps.executeUpdate();
                    
                    
                }
                String objectInfos = "Developer ID: " + object.getId();
                bddSays("update", true, object.getId(), objectInfos);
                return true;
            } else {
            	prln("insert developer");
                String query = "INSERT INTO developers (name, creation_date, country, url_instagram, url_x, url_facebook, url_website) VALUES (?, ?, ?, ?, ?, ?, ?)";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, object.getName());
                    ps.setDate(2, new java.sql.Date(object.getCreationDate().getTime()));
                    ps.setString(3, object.getCountry());
                    ps.setString(4, object.getUrlInstagram());
                    ps.setString(5, object.getUrlX());
                    ps.setString(6, object.getUrlFacebook());
                    ps.setString(7, object.getUrlWebsite());

                    ps.executeUpdate();
                    
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            String objectInfos = "Developer ID: " + generatedKeys.getInt(1);
                            bddSays("create", true, generatedKeys.getInt(1), objectInfos);
                        } else {
                            bddSays("create", false, object.getId(), null);
                            throw new SQLException("L'insertion a échoué, aucun ID généré n'a été récupéré.");
                        }
                    }
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Developer getById(int developer_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT * FROM developers WHERE id = ?");
            ps.setInt(1, developer_id);

            ResultSet resultat = ps.executeQuery();

            Developer object = new Developer();
            
            while (resultat.next()) {
                object.setId(resultat.getInt("id"));
                object.setName(resultat.getString("name"));
                object.setCreationDate(resultat.getDate("creation_date"));
                object.setCountry(resultat.getString("country"));
                object.setUrlInstagram(resultat.getString("url_instagram"));
                object.setUrlX(resultat.getString("url_x"));
                object.setUrlFacebook(resultat.getString("url_facebook"));
                object.setUrlWebsite(resultat.getString("url_website"));
            }
            
            String objectInfos = "Developer ID: " + object.getId();
            bddSays("read", true, object.getId(), objectInfos);
            return object;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, developer_id, null);
            return null;
        }
    }
    
    public List<Developer> readAll() {
        ArrayList<Developer> developersList = new ArrayList<>();
        String query = "SELECT * FROM developers";
        try {
            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {
                Developer object = new Developer();

                object.setId(resultat.getInt("id"));
                object.setName(resultat.getString("name"));
                object.setCreationDate(resultat.getDate("creation_date"));
                object.setCountry(resultat.getString("country"));
                object.setUrlInstagram(resultat.getString("url_instagram"));
                object.setUrlX(resultat.getString("url_x"));
                object.setUrlFacebook(resultat.getString("url_facebook"));
                object.setUrlWebsite(resultat.getString("url_website"));

                developersList.add(object);
            }
            bddSays("readAll", true, developersList.size(), null);
            return developersList;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            return null;
        }
    }
    
    public Developer getNameAndIdById(int id) {
		try {

			PreparedStatement ps = Database.connexion.prepareStatement(
					"SELECT name FROM developers WHERE id = ?;");
			ps.setInt(1, id);
			ResultSet resultat = ps.executeQuery();
			Developer object = new Developer();
			while (resultat.next()) {
				object.setId(id);
				object.setName(resultat.getString("name"));
			}
			return object;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
    
    public int countAll() {
		try {
			PreparedStatement ps = Database.connexion
					.prepareStatement("SELECT COUNT(*) AS total FROM developers");
			ResultSet resultat = ps.executeQuery();

			int total = 0;

			while (resultat.next()) {
				total = resultat.getInt("total");
			}

			return total;
		} catch (Exception ex) {
			ex.printStackTrace();
			return 0;
		}
	}

    public void delete(int developer_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("DELETE FROM developers WHERE id = ?");
            ps.setInt(1, developer_id);

            ps.executeUpdate();

            bddSays("delete", true, developer_id, null);

        } catch (Exception ex) {
            bddSays("delete", false, developer_id, null);
            ex.printStackTrace();
        }
    }
}
