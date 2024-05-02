package com.vapeur.dao;

import java.sql.ResultSet;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vapeur.beans.Platform;
import com.vapeur.config.Database;

import static com.vapeur.config.Debug.*;

public class PlatformDAO {

    public Boolean save(Platform object) {
        try {
            if (object.getId() != 0) {
                String query = "UPDATE platforms SET name = ?, acronym = ? WHERE id = ?";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query)) {
                    ps.setString(1, object.getName());
                    ps.setString(2, object.getAcronym());
                    ps.setInt(3, object.getId());

                    ps.executeUpdate();
                }
                String objectInfos = "Platform ID: " + object.getId();
                bddSays("update", true, object.getId(), objectInfos);
                return true;
            } else {
                String query = "INSERT INTO platforms (name, acronym) VALUES (?, ?)";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, object.getName());
                    ps.setString(2, object.getAcronym());

                    ps.executeUpdate();
                    
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            String objectInfos = "Platform ID: " + generatedKeys.getInt(1);
                            bddSays("create", true, generatedKeys.getInt(1), objectInfos);
                            return true;
                        } else {
                            bddSays("create", false, object.getId(), null);
                            throw new SQLException("L'insertion a échoué, aucun ID généré n'a été récupéré.");
                        }
                    }
                }
                
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Platform getById(int platform_id) {
        try {
        	String query = "SELECT * FROM platforms WHERE id = ?";

            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ps.setInt(1, platform_id);

            ResultSet resultat = ps.executeQuery();

            Platform object = new Platform();

        	while (resultat.next()) {
                object.setId(resultat.getInt("id"));
                object.setName(resultat.getString("name").toUpperCase());
                object.setAcronym(resultat.getString("acronym").toUpperCase());
            }
            
            
            String objectInfos = "Platform ID: " + object.getId();
            //bddSays("read", true, object.getId(), objectInfos);
            return object;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, platform_id, null);
            return null;
        }
    }
    
    
    public List<Platform> readAll() {
        ArrayList<Platform> platformsList = new ArrayList<>();
        String query = "SELECT * FROM platforms";
        try {
            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {
                Platform object = new Platform();

                object.setId(resultat.getInt("id"));
                object.setAcronym(resultat.getString("acronym"));
                object.setName(resultat.getString("name").toUpperCase());

                platformsList.add(object);
            }
            bddSays("readAll", true, platformsList.size(), null);
            return platformsList;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            return null;
        }
    }

    public void delete(int platform_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("DELETE FROM platforms WHERE id = ?");
            ps.setInt(1, platform_id);

            ps.executeUpdate();

            bddSays("delete", true, platform_id, null);

        } catch (Exception ex) {
            bddSays("delete", false, platform_id, null);
            ex.printStackTrace();
        }
    }
}
