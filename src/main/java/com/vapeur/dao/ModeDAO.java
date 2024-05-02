package com.vapeur.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vapeur.beans.Genre;
import com.vapeur.beans.Mode;
import com.vapeur.config.Database;

import static com.vapeur.config.Debug.*;

public class ModeDAO {

    public void save(Mode object) {
        try {
            if (object.getId() != 0) {
                String query = "UPDATE modes SET name = ? WHERE id = ?";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query)) {
                    ps.setString(1, object.getName());
                    ps.setInt(2, object.getId());

                    ps.executeUpdate();
                }
                String objectInfos = "Mode ID: " + object.getId();
                bddSays("update", true, object.getId(), objectInfos);
            } else {
                String query = "INSERT INTO modes (name) VALUES (?)";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, object.getName());

                    ps.executeUpdate();
                    
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            String objectInfos = "Mode ID: " + generatedKeys.getInt(1);
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
    
    public void updateLinksBetweenGameAndModes(int game_id, ArrayList<Mode> modes) {
        try {
        	PreparedStatement ps1 = Database.connexion.prepareStatement("DELETE FROM games_modes WHERE game_id = ?");
            ps1.setInt(1, game_id);
            
            
            String query = "INSERT INTO games_modes (game_id, mode_id) VALUES ";
            int index = 0;
            ArrayList<Integer> modes_id = new ArrayList<>();
            for(Mode m:modes) {
            	query += "(?, ?), ";
            	index += 2;
            	modes_id.add(m.getId());
            }
            
            query = query.substring(0, query.length()-2) + ";";
            
            PreparedStatement ps2 = Database.connexion.prepareStatement(query);
            int j = 0;
            for(int i = 1; i <= index; i+=2) {
            	ps2.setInt(i, game_id);
            	ps2.setInt(i+1, modes_id.get(j) );
            	j++;
            }
            
            prln(query);
            
            try {
            	ps1.executeUpdate();
                ps2.executeUpdate();
            } catch (Exception ex) {
            	ex.printStackTrace();
            }
            
            
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public Mode getById(int mode_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT * FROM modes WHERE id = ?");
            ps.setInt(1, mode_id);

            ResultSet resultat = ps.executeQuery();

            Mode object = new Mode();
            
            while (resultat.next()) {
                object.setId(resultat.getInt("id"));
                object.setName(resultat.getString("name"));
            }
            
            String objectInfos = "Mode ID: " + object.getId();
            bddSays("read", true, object.getId(), objectInfos);
            return object;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, mode_id, null);
            return null;
        }
    }
    
    public List<Mode> readAll() {
        ArrayList<Mode> modesList = new ArrayList<>();
        String query = "SELECT * FROM modes";
        try {
            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {
                Mode object = new Mode();

                object.setId(resultat.getInt("id"));
                object.setName(resultat.getString("name"));

                modesList.add(object);
            }
            bddSays("readAll", true, modesList.size(), null);
            return modesList;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<Mode> readAllByGameId(int game_id) {
        ArrayList<Mode> modesList = new ArrayList<>();
        String query = "SELECT modes.id AS mode_id, modes.name AS mode_name FROM games_modes JOIN modes ON games_modes.mode_id = modes.id WHERE game_id = ?";
        try {
            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ps.setInt(1, game_id);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {
                Mode object = new Mode();

                object.setId(resultat.getInt("mode_id"));
                object.setName(resultat.getString("mode_name"));

                modesList.add(object);
            }
            bddSays("readAll", true, modesList.size(), null);
            return modesList;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            return null;
        }
    }
    
    public Mode getNameAndIdById(int id) {
		try {

			PreparedStatement ps = Database.connexion.prepareStatement(
					"SELECT name FROM modes WHERE id = ?;");
			ps.setInt(1, id);
			ResultSet resultat = ps.executeQuery();
			Mode object = new Mode();
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
					.prepareStatement("SELECT COUNT(*) AS total FROM modes");
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

    public void delete(int mode_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("DELETE FROM modes WHERE id = ?");
            ps.setInt(1, mode_id);

            ps.executeUpdate();

            bddSays("delete", true, mode_id, null);

        } catch (Exception ex) {
            bddSays("delete", false, mode_id, null);
            ex.printStackTrace();
        }
    }
}