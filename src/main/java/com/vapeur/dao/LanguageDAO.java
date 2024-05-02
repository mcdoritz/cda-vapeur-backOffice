package com.vapeur.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.vapeur.beans.GameLanguage;
import com.vapeur.beans.Genre;
import com.vapeur.beans.Language;
import com.vapeur.beans.Mode;
import com.vapeur.config.Database;

import static com.vapeur.config.Debug.*;

public class LanguageDAO {

    public void save(Language object) {
        try {
            if (object.getId() != 0) {
                String query = "UPDATE languages SET locale_code = ?, language = ? WHERE id = ?";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query)) {
                    ps.setString(1, object.getLocaleCode());
                    ps.setString(2, object.getLanguage());
                    ps.setInt(3, object.getId());

                    ps.executeUpdate();
                }
                String objectInfos = "Language ID: " + object.getId();
                bddSays("update", true, object.getId(), objectInfos);
            } else {
                String query = "INSERT INTO languages (locale_code, language) VALUES (?, ?)";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setString(1, object.getLocaleCode());
                    ps.setString(2, object.getLanguage());

                    ps.executeUpdate();
                    
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            String objectInfos = "Language ID: " + generatedKeys.getInt(1);
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
    
    public void updateLinksBetweenGameAndLanguages(int game_id, ArrayList<GameLanguage> gameLanguages) {
        try {
        	PreparedStatement ps1 = Database.connexion.prepareStatement("DELETE FROM games_languages WHERE game_id = ?");
            ps1.setInt(1, game_id);
            
            
            String query = "INSERT INTO games_languages (game_id, language_id, interface, full_audio, subtitles) VALUES ";
            int index = 0;
            ArrayList<Integer> languages_id = new ArrayList<>();
            for(GameLanguage gl:gameLanguages) {
            	query += "(?, ?, ?, ?, ?), ";
            	index += 5;
            	languages_id.add(gl.getLanguageId());
            }
            
            query = query.substring(0, query.length()-2) + ";";
            prln(query);
            
            PreparedStatement ps2 = Database.connexion.prepareStatement(query);
            int j = 0;
            for(int i = 1; i <= index; i+=5) {
            	ps2.setInt(i, game_id);
            	ps2.setInt(i+1, languages_id.get(j) );
            	ps2.setBoolean(i+2, gameLanguages.get(j).isInterfaceSupport());
            	ps2.setBoolean(i+3, gameLanguages.get(j).isFullAudioSupport());
            	ps2.setBoolean(i+4, gameLanguages.get(j).isSubtitles());
            	j++;
            }
            
            
            
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

    public Language getById(int language_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT * FROM languages WHERE id = ?");
            ps.setInt(1, language_id);

            ResultSet resultat = ps.executeQuery();

            Language object = new Language();
            
            while (resultat.next()) {
                object.setId(resultat.getInt("id"));
                object.setLocaleCode(resultat.getString("locale_code"));
                object.setLanguage(resultat.getString("language"));
            }
            
            String objectInfos = "Language ID: " + object.getId();
            bddSays("read", true, object.getId(), objectInfos);
            return object;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, language_id, null);
            return null;
        }
    }
    
    public List<Language> readAll() {
        ArrayList<Language> languagesList = new ArrayList<>();
        String query = "SELECT * FROM languages";
        try {
            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {
                Language object = new Language();

                object.setId(resultat.getInt("id"));
                object.setLocaleCode(resultat.getString("locale_code"));
                object.setLanguage(resultat.getString("language"));

                languagesList.add(object);
            }
            bddSays("readAll", true, languagesList.size(), null);
            return languagesList;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            return null;
        }
    }
    
    public List<Language> readAllByGameId(int game_id) {
        ArrayList<Language> languagesList = new ArrayList<>();
        String query = "SELECT languages.id AS language_id, languages.language AS language_name, languages.locale_code AS language_code, games_languages.interface AS language_interface, games_languages.full_audio AS language_full_audio, games_languages.subtitles AS language_subtitles FROM games_languages JOIN languages ON games_languages.language_id = languages.id WHERE game_id = ?";
        try {
            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ps.setInt(1, game_id);
            ResultSet resultat = ps.executeQuery();

            while (resultat.next()) {
                Language object = new Language();

                object.setId(resultat.getInt("language_id"));
                object.setLanguage(resultat.getString("language_name"));
                object.setLocaleCode(resultat.getString("language_code"));
                object.setFullAudioSupport(resultat.getBoolean("language_full_audio"));
                object.setInterfaceSupport(resultat.getBoolean("language_interface"));
                object.setSubtitles(resultat.getBoolean("language_subtitles"));

                languagesList.add(object);
            }
            bddSays("readAll", true, languagesList.size(), null);
            return languagesList;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            return null;
        }
    }

    public void delete(int language_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("DELETE FROM languages WHERE id = ?");
            ps.setInt(1, language_id);

            ps.executeUpdate();

            bddSays("delete", true, language_id, null);

        } catch (Exception ex) {
            bddSays("delete", false, language_id, null);
            ex.printStackTrace();
        }
    }
}