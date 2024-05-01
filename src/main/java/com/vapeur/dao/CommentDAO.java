package com.vapeur.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.vapeur.beans.Comment;
import com.vapeur.beans.User;
import com.vapeur.config.Database;
import static com.vapeur.config.Debug.*;

public class CommentDAO {

    public Boolean save(Comment object) {
    	prln("SAVE " + object.getContent());
        try {
            if (getById(object.getUserId(), object.getGameId()) != null) {
            	prln("update comment....");
                String query = "UPDATE comments SET content = ?, uploaded = ?, score = ? , moderated = ? WHERE user_id = ? AND game_id = ?";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query)) {
                    ps.setString(1, object.getContent());
                    ps.setTimestamp(2, object.getUploaded());
                    ps.setFloat(3, object.getScore());
                    ps.setBoolean(4, object.getModerated());
                    ps.setInt(5, object.getUserId());
                    ps.setInt(6, object.getGameId());

                    ps.executeUpdate();
                    
                    GameDAO gamedao = new GameDAO();
                    gamedao.update(object.getGameId());
                }
                String objectInfos = "Comment ID: " + object.getUserId() + "-" + object.getGameId();
                return true;

            } else {
            	prln("Comment n'existe pas dans la BDD");
                String query = "INSERT INTO comments (content, uploaded, score, user_id, game_id, moderated) VALUES (?, ?, ?, ?, ?, ?)";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, object.getContent());
                    ps.setTimestamp(2, object.getUploaded());
                    ps.setFloat(3, object.getScore());
                    ps.setInt(4, object.getUserId());
                    ps.setInt(5, object.getGameId());
                    ps.setBoolean(6, object.getModerated());

                    ps.executeUpdate();
                    
                    GameDAO gamedao = new GameDAO();
                    gamedao.update(object.getGameId());
                    return true;
                    
                }catch (Exception e) {
                	e.printStackTrace();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		return null;
    }

    public Comment getById(int user_id, int game_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT * FROM comments WHERE user_id = ? AND game_id = ?");
            ps.setInt(1, user_id);
            ps.setInt(2, game_id);

            ResultSet resultat = ps.executeQuery();

            Comment object = new Comment();
            UserDAO userdao = new UserDAO();
            
            while (resultat.next()) {
            	User user = userdao.getById(user_id);
                object.setContent(resultat.getString("content"));
                object.setUploaded(resultat.getTimestamp("uploaded"));
                object.setScore(resultat.getInt("score"));
                object.setUserId(user_id);
                object.setGameId(game_id);
                object.setUserNickname(user.getNickname());
                object.setModerated(resultat.getBoolean("moderated"));
            }
            if(object.getContent() != null) {
            	return object;
            }else {
            	prln("commentdao.getById retourne null");
            	return null;
            }
            
            

        } catch (Exception ex) {
            ex.printStackTrace();

            return null;
        }
    }
    
    public Comment getScore(int user_id, int game_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT score FROM comments WHERE user_id = ? AND game_id = ?");
            ps.setInt(1, user_id);
            ps.setInt(2, game_id);

            ResultSet resultat = ps.executeQuery();

            Comment object = new Comment();
            
            while (resultat.next()) {
                object.setScore(resultat.getInt("score"));
                object.setGameId(game_id);
                object.setUserId(user_id);
            }
            
            return object;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public ArrayList<Comment> getByUserId(int user_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT * FROM comments WHERE user_id = ?");
            ps.setInt(1, user_id);

            ResultSet resultat = ps.executeQuery();

            Comment object = new Comment();
            ArrayList<Comment> listComments = new ArrayList<>();
            
            UserDAO userdao = new UserDAO();
            
            
            
            while (resultat.next()) {
            	User user = userdao.getById(user_id);
                object.setContent(resultat.getString("content"));
                object.setUploaded(resultat.getTimestamp("uploaded"));
                object.setScore(resultat.getInt("score"));
                object.setUserId(user_id);
                object.setGameId(resultat.getInt("game_id"));
                object.setModerated(resultat.getBoolean("moderated"));
                object.setUserNickname(user.getNickname());
                
                listComments.add(object);
            }
            
            return listComments;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, user_id, null);
            return null;
        }
    }
    
    public ArrayList<Comment> getByGameId(int game_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT comments.content AS content, comments.uploaded AS uploaded, comments.score AS score, comments.user_id AS user_id, users.nickname AS user_nickname, comments.moderated AS moderated FROM comments JOIN users ON comments.user_id = users.id  WHERE game_id = ? ORDER BY uploaded DESC");
            ps.setInt(1, game_id);

            ResultSet resultat = ps.executeQuery();

            
            ArrayList<Comment> listComments = new ArrayList<>();
            
            UserDAO userdao = new UserDAO();
            
           
            
            while (resultat.next()) {
            	Comment object = new Comment();
            	User user = userdao.getById(resultat.getInt("user_id"));
                object.setContent(resultat.getString("content"));
                object.setUploaded(resultat.getTimestamp("uploaded"));
                object.setScore(resultat.getInt("score"));
                object.setUserId(resultat.getInt("user_id"));
                object.setGameId(game_id);
                object.setUserNickname(user.getNickname());
                object.setModerated(resultat.getBoolean("moderated"));
                listComments.add(object);
            }
            
            return listComments;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, game_id, null);
            return null;
        }
    }
    
    public ArrayList<Comment> getByGameId(int game_id, Boolean approved) {
        try {
        	
        	String query = "SELECT comments.content AS content, comments.uploaded AS uploaded, comments.score AS score, comments.user_id AS user_id, users.nickname AS user_nickname, comments.moderated AS moderated FROM comments JOIN users ON comments.user_id = users.id  WHERE game_id = ? AND content != '' AND moderated = ";
            
        	if(approved) {
        		query += " true ";
        	}else {
        		query += " false ";
        	}
        	
        	query += "ORDER BY uploaded DESC";
        	PreparedStatement ps = Database.connexion.prepareStatement(query);
            ps.setInt(1, game_id);

            ResultSet resultat = ps.executeQuery();
            
            UserDAO userdao = new UserDAO();

            ArrayList<Comment> listComments = new ArrayList<>();
            
            while (resultat.next()) {
            	Comment object = new Comment();
            	User user = userdao.getById(resultat.getInt("user_id"));
                object.setContent(resultat.getString("content"));
                object.setUploaded(resultat.getTimestamp("uploaded"));
                object.setScore(resultat.getInt("score"));
                object.setUserId(resultat.getInt("user_id"));
                object.setGameId(game_id);
                object.setUserNickname(user.getNickname());
                object.setModerated(resultat.getBoolean("moderated"));
                listComments.add(object);
            }
            
            return listComments;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, game_id, null);
            return null;
        }
    }
    
    public int countCommentsById(int game_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT COUNT(*) AS count FROM comments WHERE game_id = ?");
            ps.setInt(1, game_id);

            ResultSet resultat = ps.executeQuery();
            int count = 0;
            
            while (resultat.next()) {
            	count = resultat.getInt("count");
            }
            
            return count;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, game_id, null);
            return 0;
        }
    }
    
    public int countNotApprovedCommentsById(int game_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT COUNT(*) AS count FROM comments WHERE game_id = ? AND content != '' AND moderated = false");
            ps.setInt(1, game_id);

            ResultSet resultat = ps.executeQuery();
            int count = 0;
            
            while (resultat.next()) {
            	count = resultat.getInt("count");
            }
            
            return count;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, game_id, null);
            return 0;
        }
    }
    
    public int countAllNotApprovedComments() {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT COUNT(*) AS count FROM comments WHERE content != '' AND moderated = false");

            ResultSet resultat = ps.executeQuery();
            int count = 0;
            
            while (resultat.next()) {
            	count = resultat.getInt("count");
            }
            
            return count;

        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
        }
    }
    
    //Pour affichage sur la page du jeu
    public ArrayList<Comment> getByGameIdOnlyWithContent(int game_id, Boolean moderated) {
        try {
        	
        	String query = "SELECT comments.content AS content, comments.uploaded AS uploaded, comments.score AS score, comments.user_id AS user_id, users.nickname AS user_nickname FROM comments JOIN users ON comments.user_id = users.id  WHERE game_id = ? AND comments.content != ''";
            if(moderated) {
            	query += " AND moderated = true";
            }
            
            query += " ORDER BY uploaded DESC";
        	
        	PreparedStatement ps = Database.connexion.prepareStatement(query);
            ps.setInt(1, game_id);

            ResultSet resultat = ps.executeQuery();

            
            ArrayList<Comment> listComments = new ArrayList<>();
            UserDAO userdao = new UserDAO();
            
            while (resultat.next()) {
            	Comment object = new Comment();
            	User user = userdao.getById(resultat.getInt("user_id"));
                object.setContent(resultat.getString("content"));
                object.setUploaded(resultat.getTimestamp("uploaded"));
                object.setScore(resultat.getInt("score"));
                object.setUserId(resultat.getInt("user_id"));
                object.setGameId(game_id);
                object.setUserNickname(user.getNickname());
                listComments.add(object);
            }
            
            return listComments;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, game_id, null);
            return null;
        }
    }
    
    public List<Comment> readAll() {
        ArrayList<Comment> commentsList = new ArrayList<>();
        String query = "SELECT * FROM comments";
        try {
            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {
                Comment object = new Comment();

                object.setContent(resultat.getString("content"));
                object.setUploaded(resultat.getTimestamp("uploaded"));
                object.setScore(resultat.getInt("score"));
                object.setUserId(resultat.getInt("user_id"));
                object.setGameId(resultat.getInt("game_id"));
                object.setModerated(resultat.getBoolean("moderated"));

                commentsList.add(object);
            }
            bddSays("readAll", true, commentsList.size(), null);
            return commentsList;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            return null;
        }
    }

    public void delete(int game_id, int user_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("DELETE FROM comments WHERE game_id = ? AND user_id = ?");
            ps.setInt(1, game_id);
            ps.setInt(2, user_id);

            ps.executeUpdate();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
