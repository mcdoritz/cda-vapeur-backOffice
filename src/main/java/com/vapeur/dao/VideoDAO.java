package com.vapeur.dao;

import static com.vapeur.config.Debug.bddSays;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vapeur.beans.Video;
import com.vapeur.config.Database;

public class VideoDAO {

    public void save(Video object) {
        try {
            if (object.getId() != 0) {
                String query = "UPDATE videos SET url = ?, game_id = ? WHERE id = ?";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query)) {
                    ps.setString(1, object.getUrl());
                    ps.setInt(2, object.getGameId());
                    ps.setInt(3, object.getId());

                    ps.executeUpdate();
                }
                String objectInfos = "Video ID: " + object.getId();
                bddSays("update", true, object.getId(), objectInfos);
            } else {
                String query = "INSERT INTO videos (url, game_id) VALUES (?, ?)";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, object.getUrl());
                    ps.setInt(2, object.getGameId());

                    ps.executeUpdate();
                    
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            String objectInfos = "Video ID: " + generatedKeys.getInt(1);
                            bddSays("create", true, generatedKeys.getInt(1), objectInfos);
                        } else {
                            bddSays("create", false, object.getId(), null);
                            throw new SQLException("L'insertion a échoué, aucun ID généré n'a été récupéré.");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Video getById(int video_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT * FROM videos WHERE id = ?");
            ps.setInt(1, video_id);

            ResultSet resultat = ps.executeQuery();

            Video object = new Video();
            
            while (resultat.next()) {
                object.setId(resultat.getInt("id"));
                object.setUrl(resultat.getString("url"));
                object.setGameId(resultat.getInt("game_id"));
            }
            
            String objectInfos = "Video ID: " + object.getId();
            bddSays("read", true, object.getId(), objectInfos);
            return object;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, video_id, null);
            return null;
        }
    }
    
    public ArrayList<Video> getByGameId(int game_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT * FROM videos WHERE game_id = ?");
            ps.setInt(1, game_id);

            ResultSet resultat = ps.executeQuery();

           
            ArrayList<Video> videos = new ArrayList<>();
            
            while (resultat.next()) {
            	Video object = new Video();
                object.setId(resultat.getInt("id"));
                object.setUrl(resultat.getString("url"));
                object.setGameId(resultat.getInt("game_id"));
                videos.add(object);
            }
            
            return videos;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, game_id, null);
            return null;
        }
    }
    
    public List<Video> readAll() {
        ArrayList<Video> videosList = new ArrayList<>();
        String query = "SELECT * FROM videos";
        try {
            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {
                Video object = new Video();

                object.setId(resultat.getInt("id"));
                object.setUrl(resultat.getString("name"));
                object.setGameId(resultat.getInt("game_id"));

                videosList.add(object);
            }
            bddSays("readAll", true, videosList.size(), null);
            return videosList;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            return null;
        }
    }

    public void delete(int video_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("DELETE FROM videos WHERE id = ?");
            ps.setInt(1, video_id);

            ps.executeUpdate();

            bddSays("delete", true, video_id, null);

        } catch (Exception ex) {
            bddSays("delete", false, video_id, null);
            ex.printStackTrace();
        }
    }
}
