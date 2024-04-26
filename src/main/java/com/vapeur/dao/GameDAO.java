package com.vapeur.dao;

import static com.vapeur.config.Debug.bddSays;
import static com.vapeur.config.Debug.prln;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.vapeur.beans.Comment;
import com.vapeur.beans.Game;
import com.vapeur.beans.GameResults;
import com.vapeur.beans.Genre;
import com.vapeur.beans.Language;
import com.vapeur.beans.Mode;
import com.vapeur.config.Database;

public class GameDAO {
	
	private int limitPerPage = 12;

	public void save(Game object) {
		try {
			// Préparer les tags array->string
			String tags = "";
			ArrayList<String> arrayTags = new ArrayList<>(object.getTags());
			int i = 0;
			for (String t : arrayTags) {
				if (i < arrayTags.size()) {
					tags += t.toUpperCase() + " ";
				} else {
					tags += t.toUpperCase();
				}

				i++;
			}

			if (object.getId() != 0) {
				String query = "UPDATE games SET title = ?, description = ?, classification = ?, price = ?, release_date = ?, users_avg_score = ?, total_reviews = ?, controller_support = ?, requires_3rd_party_account = ?, stock = ?, tags = ?, developer_id = ?, platform_id = ? WHERE id = ?";

				try (PreparedStatement ps = Database.connexion.prepareStatement(query)) {
					ps.setString(1, object.getTitle());
					ps.setString(2, object.getDescription());
					ps.setInt(3, object.getClassification());
					ps.setFloat(4, object.getPrice());
					ps.setDate(5, new java.sql.Date(object.getReleaseDate().getTime()));
					ps.setFloat(6, object.getUsersAvgScore());
					ps.setInt(7, object.getTotalReviews());
					ps.setBoolean(8, object.isControllerSupport());
					ps.setBoolean(9, object.isRequires3rdPartyAccount());
					ps.setInt(10, object.getStock());
					ps.setString(11, tags);
					ps.setInt(12, object.getDeveloperId());
					ps.setInt(13, object.getPlatformId());
					ps.setInt(14, object.getId());
					ps.executeUpdate();
				}
				String objectInfos = object.getTitle();
				bddSays("update", true, object.getId(), objectInfos);
			} else {
				String query = "INSERT INTO games (title, description, classification, price, release_date, users_avg_score, total_reviews, controller_support, requires_3rd_party_account, stock, tags, developer_id, platform_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				try (PreparedStatement ps = Database.connexion.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, object.getTitle());
					ps.setString(2, object.getDescription());
					ps.setInt(3, object.getClassification());
					ps.setFloat(4, object.getPrice());
					ps.setDate(5, new java.sql.Date(object.getReleaseDate().getTime()));
					ps.setFloat(6, object.getUsersAvgScore());
					ps.setInt(7, object.getTotalReviews());
					ps.setBoolean(8, object.isControllerSupport());
					ps.setBoolean(9, object.isRequires3rdPartyAccount());
					ps.setInt(10, object.getStock());
					ps.setString(11, tags);
					ps.setInt(12, object.getDeveloperId());
					ps.setInt(12, object.getPlatformId());
					ps.executeUpdate();
					try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							String objectInfos = object.getTitle();
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
	
	public Game getStockAndTitle(int game_id) {
		
		int stock = 0;
		String title = null;
		
		try {

			if (game_id != 0) {			

				try (PreparedStatement ps = Database.connexion.prepareStatement("SELECT title, stock FROM games WHERE id = ?")) {
					ps.setInt(1, game_id);
					ResultSet resultat = ps.executeQuery();

					while (resultat.next()) {
						stock = resultat.getInt("stock");
						title = resultat.getString("title");
					}
					
					prln("Stock du jeu " + title + " : " + stock  + " unités.");
					return new Game(title, stock);
				}
			}else {
				return null;
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public void updateStock(int game_id, int stockModif) {
		try {
			prln("stockmodif : " + stockModif);
			if (stockModif != 0 && game_id != 0) {		

				try (PreparedStatement ps = Database.connexion.prepareStatement("UPDATE games SET stock = stock + ? WHERE id = ?")) {
					ps.setInt(1, stockModif);
					ps.setInt(2, game_id);
					
					ps.executeUpdate();
					prln("Stock du jeu " + game_id + " mis à jour");
				}
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// Tout prendre
	public Game getById(int game_id) {
		try {

			PreparedStatement ps = Database.connexion.prepareStatement(
					"SELECT games.id, title, description, classification, price, release_date, users_avg_score, controller_support, requires_3rd_party_account, total_reviews, stock, tags, developer_id, platforms.id AS platform_id, platforms.name AS platform_name, platforms.acronym AS platform_acronym FROM games JOIN platforms ON games.platform_id = platforms.id  JOIN developers ON games.developer_id = developers.id WHERE games.id = ?;");
			ps.setInt(1, game_id);
			ResultSet resultat = ps.executeQuery();
			Game object = new Game();
			PlatformDAO platformdao = new PlatformDAO();
			ModeDAO modedao = new ModeDAO();
			GenreDAO genredao = new GenreDAO();
			LanguageDAO languagedao = new LanguageDAO();
			DeveloperDAO developerdao = new DeveloperDAO();
			CommentDAO commentdao = new CommentDAO();

			while (resultat.next()) {

				object.setId(game_id);
				object.setTitle(resultat.getString("title"));
				object.setDescription(resultat.getString("description"));
				object.setClassification(resultat.getInt("classification"));
				object.setPrice(resultat.getFloat("price"));
				object.setReleaseDate(resultat.getDate("release_date"));
				object.setUsersAvgScore(resultat.getFloat("users_avg_score"));
				object.setTotalReviews(resultat.getInt("total_reviews"));
				object.setControllerSupport(resultat.getBoolean("controller_support"));
				object.setRequires3rdPartyAccount(resultat.getBoolean("requires_3rd_party_account"));
				object.setStock(resultat.getInt("stock"));

				String tags = resultat.getString("tags");
				String[] arrayTags = tags.split(" ");
				ArrayList<String> arrayListTags = new ArrayList<>();

				for (String t : arrayTags) {
					arrayListTags.add(t.toUpperCase());
				}

				object.setTags(arrayListTags);

				object.setPlatform(platformdao.getById(resultat.getInt("platform_id")));

				ArrayList<Mode> modesList = new ArrayList<>(modedao.readAllByGameId(game_id));
				ArrayList<Genre> genresList = new ArrayList<>(genredao.readAllByGameId(game_id));
				ArrayList<Language> languagesList = new ArrayList<>(languagedao.readAllByGameId(game_id));

				object.setModes(modesList);
				object.setGenres(genresList);
				object.setLanguages(languagesList);
				object.setDeveloper(developerdao.getById(resultat.getInt("developer_id")));
				object.setComments(commentdao.getByGameIdOnlyWithContent(game_id));

			}
			String objectInfos = object.getTitle();
			bddSays("read", true, object.getId(), objectInfos);
			return object;
		} catch (Exception ex) {
			ex.printStackTrace();
			bddSays("read", false, game_id, null);
			return null;
		}
	}

	// Sers à lister les jeux, inutile de tout prendre donc.
	public GameResults readAll(int page, ArrayList<Integer> genres_id, ArrayList<Integer> modes_id,
			ArrayList<Integer> languages_id, ArrayList<Integer> platforms_id, ArrayList<Integer> developers_id) {
		ArrayList<Game> gamesList = new ArrayList<>();

		int min;
		if (page < 2) {
			min = page;

		} else {
			min = (page - 1) * limitPerPage + 1;
		}

		try {

			// Construction de la query ------------------------
			String query = "SELECT DISTINCT games.id, title, price, release_date, users_avg_score, total_reviews, stock, platforms.id AS platform_id, platforms.name AS platform_name, platforms.acronym AS platform_acronym FROM games JOIN platforms ON games.platform_id = platforms.id ";
			String queryJoins = "";
			String queryConditions = "";
			
			int index = 0;
			if(genres_id.size() > 0) {
				queryJoins += " JOIN games_genres ON games_genres.game_id = games.id JOIN genres ON games_genres.genre_id = genres.id ";
				queryConditions += " AND (";
				
				for (int g : genres_id) {
					queryConditions += "games_genres.genre_id = ? ";
					if(index != genres_id.size()-1) {
						queryConditions += "OR ";
					}
					index++;
				}
				queryConditions += " ) ";
			}
			
			
			index = 0;
			if(modes_id.size() > 0) {
				queryJoins += " JOIN games_modes ON games_modes.game_id = games.id JOIN modes ON games_modes.mode_id = modes.id ";
				queryConditions += " AND (";
				
				for (int m : modes_id) {
					queryConditions += "games_modes.mode_id = ? ";
					if(index != modes_id.size()-1) {
						queryConditions += "OR ";
					}
					index++;
				}
				queryConditions += " ) ";
			}
			
			
			
			if(languages_id.size() > 0) {
				queryJoins += " JOIN games_languages ON games_languages.game_id = games.id JOIN languages ON games_languages.language_id = languages.id ";
				queryConditions += " AND (";
				index = 0;
				for (int l : languages_id) {
					queryConditions += "games_languages.language_id = ? ";
					if(index != languages_id.size()-1) {
						queryConditions += "OR ";
					}
					index++;
				}
				queryConditions += " ) ";
			}
			
			
			
			if(platforms_id.size() > 0) {
				queryConditions += " AND (";
				index = 0;
				for (int p : platforms_id) {
					queryConditions += "platform_id = ? ";
					if(index != platforms_id.size()-1) {
						queryConditions += "OR ";
					}
					index++;
				}
				queryConditions += ") ";
			}

			if(developers_id.size() > 0) {
				queryConditions += " AND (";
				index = 0;
				for (int d : developers_id) {
					queryConditions += "developer_id = ? ";
					if(index != developers_id.size()-1) {
						queryConditions += "OR ";
					}
					index++;
				}
				queryConditions += " ) ";
			}
			prln("queryjoins : " + queryJoins);
			prln("queryConditions : " + queryConditions);

			query += queryJoins + " WHERE stock > 0" + queryConditions + " ORDER BY games.title ASC LIMIT ?,?";			
			String queryCount = "SELECT COUNT(DISTINCT games.id) AS total FROM games " + queryJoins + " WHERE stock > 0" + queryConditions;
			
			prln(query);
			prln(queryCount);

			// -------------------------------------------------------------

			PreparedStatement ps = Database.connexion.prepareStatement(query);
			
			index = 1;
			
			for (int g : genres_id) {
				ps.setInt(index, g);
				index++;
			}
			for (int m : modes_id) {
				ps.setInt(index, m);
				index++;
			}
			for (int l : languages_id) {
				ps.setInt(index, l);
				index++;
			}
			for (int p : platforms_id) {
				ps.setInt(index, p);
				index++;
			}
			for (int d : developers_id) {
				ps.setInt(index, d);
				index++;
			}
			
			ps.setInt(index, min);
			ps.setInt(index+1, limitPerPage);
			ResultSet resultat = ps.executeQuery();

			PlatformDAO platformdao = new PlatformDAO();

			while (resultat.next()) {
				Game object = new Game();
				object.setId(resultat.getInt("id"));
				object.setTitle(resultat.getString("title"));
				object.setPrice(resultat.getFloat("price"));
				object.setReleaseDate(resultat.getDate("release_date"));
				object.setUsersAvgScore(resultat.getFloat("users_avg_score"));
				object.setTotalReviews(resultat.getInt("total_reviews"));
				object.setStock(resultat.getInt("stock"));
				object.setPlatform(platformdao.getById(resultat.getInt("platform_id")));
				gamesList.add(object);
			}
			int totalResults = 0;
			
			// S'il y a autant que 12 résultats dans la recherche, alors voir s'il y en a plus :
			if(gamesList.size() <= limitPerPage) {
				prln("gamesList.size <= limitPerpage");
				totalResults = countAll(queryCount, genres_id, modes_id, languages_id, platforms_id, developers_id);
			}else {
				prln("gamesList.size != limitPerpage");
				totalResults = gamesList.size();
			}

			GameResults gameresults = new GameResults(gamesList, totalResults);
			prln("gr:" + gameresults.getGames().size());
			prln("gr:" + gameresults.getTotalResults());
			return gameresults;
		} catch (Exception ex) {
			bddSays("readAll", false, 0, null);
			ex.printStackTrace();
			return null;
		}
	}
	
	public ArrayList<Game> readSuggestions(ArrayList<Integer> gamesNotToShow, ArrayList<Integer> genres_id, ArrayList<Integer> modes_id) {
		ArrayList<Game> gamesList = new ArrayList<>();
		ArrayList<Game> finalGamesList = new ArrayList<>();

		try {

			// Construction de la query ------------------------
			String query = "SELECT DISTINCT games.id, games.title FROM games ";
			String queryJoins = "";
			String queryConditions = " AND ";

			for (int g : gamesNotToShow) {
				queryConditions += "games.id != ? AND ";
			}
			queryConditions = queryConditions.substring(0, queryConditions.length() - 4);
			queryConditions += " AND (";

			if(genres_id.size() > 0) {
				queryJoins += " JOIN games_genres ON games_genres.game_id = games.id JOIN genres ON games_genres.genre_id = genres.id ";
				
				for (int g : genres_id) {
					queryConditions += "games_genres.genre_id = ? OR ";
				}
			}
			
			if(modes_id.size() > 0) {
				queryJoins += " JOIN games_modes ON games_modes.game_id = games.id JOIN modes ON games_modes.mode_id = modes.id ";
				
				for (int m : modes_id) {
					queryConditions += "games_modes.mode_id = ? OR ";
				}
				
			}
			queryConditions = queryConditions.substring(0, queryConditions.length() - 3);
			queryConditions += " ) ";
			prln(queryConditions);

			query += queryJoins + " WHERE stock > 0" + queryConditions + " ORDER BY RAND()";			

			prln(query);

			// -------------------------------------------------------------

			PreparedStatement ps = Database.connexion.prepareStatement(query);
			
			int index = 1;
			
			for (int game_id : gamesNotToShow) {
				ps.setInt(index, game_id);
				index++;
			}
			
			for (int g : genres_id) {
				ps.setInt(index, g);
				index++;
			}
			for (int m : modes_id) {
				ps.setInt(index, m);
				index++;
			}

			ResultSet resultat = ps.executeQuery();


			while (resultat.next()) {
				Game object = new Game();
				object.setId(resultat.getInt("id"));
				object.setTitle(resultat.getString("title"));
				gamesList.add(object);
			}
			
			//Supprimer au hasard pour n'en garder que 4
			Random hasard = new Random();
			int dé = 0;
			
			if(gamesList.size() >= 5) {
				for(int i = 1 ; i <= 4; i++) {
					dé = hasard.nextInt(gamesList.size());
					finalGamesList.add(gamesList.get(dé));
					gamesList.remove(dé);
				}
				return finalGamesList;
			}else {
				return gamesList;
			}
			

			
		} catch (Exception ex) {
			bddSays("readAll", false, 0, null);
			ex.printStackTrace();
			return null;
		}
	}
	
	public Boolean isGameInUserLibrary(int game_id, int user_id) {
		try {
			PreparedStatement ps = Database.connexion.prepareStatement("SELECT COUNT(*) AS count FROM order_details JOIN orders ON order_details.order_id = orders.id WHERE orders.user_id = ? AND game_id = ? ");
			ps.setInt(1, user_id);
			ps.setInt(2, game_id);
			
			ResultSet resultat = ps.executeQuery();
			int count = 0;
			while (resultat.next()) {
				count = resultat.getInt("count");
			}
			
			if(count > 0) {
				return true;
			}else {
				return false;
			}
			
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public GameResults library(int user_id) {
		
		try {
			PreparedStatement ps = Database.connexion.prepareStatement("SELECT DISTINCT games.id AS game_id, title, price, release_date, users_avg_score, total_reviews, stock, platforms.id AS platform_id, platforms.name AS platform_name, platforms.acronym AS platform_acronym, COALESCE(comments.score, -1) AS score FROM games LEFT JOIN comments ON games.id = comments.game_id AND comments.user_id = ? JOIN platforms ON games.platform_id = platforms.id WHERE games.id IN (SELECT game_id FROM order_details WHERE order_details.order_id IN (SELECT orders.id FROM orders WHERE user_id = ?))");
			ps.setInt(1, user_id);
			ps.setInt(2, user_id);
			
			ResultSet resultat = ps.executeQuery();
			
			ArrayList<Game> gamesList = new ArrayList<>();
			GenreDAO genredao = new GenreDAO();
			ModeDAO modedao = new ModeDAO();
			
			PlatformDAO platformdao = new PlatformDAO();

			while (resultat.next()) {
				Game object = new Game();
				Comment comment = new Comment();
				int game_id = resultat.getInt("game_id");
				
				object.setId(game_id);
				object.setTitle(resultat.getString("title"));
				object.setPrice(resultat.getFloat("price"));
				object.setReleaseDate(resultat.getDate("release_date"));
				object.setUsersAvgScore(resultat.getFloat("users_avg_score"));
				object.setTotalReviews(resultat.getInt("total_reviews"));
				object.setStock(resultat.getInt("stock"));
				object.setPlatform(platformdao.getById(resultat.getInt("platform_id")));
				comment.setScore(resultat.getInt("score"));
				object.setComment(comment);
				
				ArrayList<Genre> genresList = new ArrayList<>(genredao.readAllByGameId(game_id));
				object.setGenres(genresList);
				
				ArrayList<Mode> modesList = new ArrayList<>(modedao.readAllByGameId(game_id));
				object.setModes(modesList);
				gamesList.add(object);
			}
			
			GameResults gameresults = new GameResults(gamesList, gamesList.size());
			return gameresults;
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public GameResults readSearched(int page, String search) throws DAOException {
		ArrayList<Game> gamesList = new ArrayList<>();

		int min;
		if (page < 2) {
			min = page;

		} else {
			min = (page - 1) * 12 + 1;
		}

		// Construction de la query ------------------------
		String query = "SELECT DISTINCT games.id, title, price, release_date, users_avg_score, total_reviews, stock, platforms.id AS platform_id, platforms.name AS platform_name, platforms.acronym AS platform_acronym FROM games JOIN platforms ON games.platform_id = platforms.id WHERE stock > 0 AND games.title LIKE ? ";
		prln(search);
		if (search != "") {
			if (search.length() > 3) {
				if(search.length() < 50) {
					
					// Diviser pour mieux chercher
					String[] arraySearch = search.split(" ");
					
					// i = 1 car dans la query on a déjà AND games.title LIKE...
					for(int i = 1; i < arraySearch.length; i++) {
						query += "OR games.title LIKE ? ";
					}
					
					query += " ORDER BY games.title ASC LIMIT ?,12 ";
					
					try {
						PreparedStatement ps = Database.connexion.prepareStatement(query);
						int index = 1;
						for(int i = 0; i < arraySearch.length; i++) {
							ps.setString(i+1, "%" + arraySearch[i] + "%");
							index++;
						}

						ps.setInt(index, min);
						ResultSet resultat = ps.executeQuery();

						PlatformDAO platformdao = new PlatformDAO();

						while (resultat.next()) {
							Game object = new Game();
							object.setId(resultat.getInt("id"));
							object.setTitle(resultat.getString("title"));
							object.setPrice(resultat.getFloat("price"));
							object.setReleaseDate(resultat.getDate("release_date"));
							object.setUsersAvgScore(resultat.getFloat("users_avg_score"));
							object.setTotalReviews(resultat.getInt("total_reviews"));
							object.setStock(resultat.getInt("stock"));
							object.setPlatform(platformdao.getById(resultat.getInt("platform_id")));
							gamesList.add(object);
						}
						int totalResults = 0;
						
						// S'il y a autant que 12 résultats dans la recherche, alors voir s'il y en a plus :
						if(gamesList.size() == limitPerPage) {
							totalResults = countAllSearch(search);
						}else {
							totalResults = gamesList.size();
						}

						GameResults gameresults = new GameResults(gamesList, totalResults);
						
						return gameresults;
						
					} catch (Exception ex) {
						bddSays("readAll", false, 0, null);
						ex.printStackTrace();
						throw new DAOException("Erreur avec la base de données.");
					}
				
				}else {
					throw new DAOException("La recherche est trop longue");
				}
				

			} else {

				throw new DAOException("La recherche est trop courte");

			}
		}else {
			throw new DAOException("Il n'y a rien dans la recherche");
		}
		

	}

	public Game getNameAndIdById(int id) {
		try {

			PreparedStatement ps = Database.connexion.prepareStatement("SELECT title FROM games WHERE id = ?;");
			ps.setInt(1, id);
			ResultSet resultat = ps.executeQuery();
			Game object = new Game();
			while (resultat.next()) {
				object.setId(id);
				object.setTitle(resultat.getString("title"));
			}
			return object;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public int countAll(String query, ArrayList<Integer> genres_id, ArrayList<Integer> modes_id,
			ArrayList<Integer> languages_id, ArrayList<Integer> platforms_id, ArrayList<Integer> developers_id) {
		try {
			// Pour la sélection au hasard pour la landing page :
			if(query == "all") {
				query =  "SELECT COUNT(*) AS total FROM games WHERE stock > 0";
			}
			
			prln("countALL QUERY : " + query);
			PreparedStatement ps = Database.connexion
					.prepareStatement(query);
			
			int index = 1;
			
			if(genres_id != null && modes_id != null && languages_id != null && platforms_id != null && developers_id != null) {
				for (int g : genres_id) {
					ps.setInt(index, g);
					index++;
				}
				for (int m : modes_id) {
					ps.setInt(index, m);
					index++;
				}
				for (int l : languages_id) {
					ps.setInt(index, l);
					index++;
				}
				for (int p : platforms_id) {
					ps.setInt(index, p);
					index++;
				}
				for (int d : developers_id) {
					ps.setInt(index, d);
					index++;
				}
			}

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

	public int countAllSearch(String search) throws DAOException {
		String query = "SELECT COUNT(*) games.id, title, price, release_date, users_avg_score, total_reviews, stock, platforms.id AS platform_id, platforms.name AS platform_name, platforms.acronym AS platform_acronym FROM games JOIN platforms ON games.platform_id = platforms.id WHERE stock > 0 AND games.title LIKE ? ";
		// Diviser pour mieux chercher
		String[] arraySearch = search.split(" ");
		
		// i = 1 car dans la query on a déjà AND games.title LIKE...
		for(int i = 1; i < arraySearch.length; i++) {
			query += "OR games.title LIKE ? ";
		}
		if (search != "") {
			if (search.length() > 3) {
				if(search.length() < 50) {
					try {
						PreparedStatement ps = Database.connexion.prepareStatement(query);
						for(int i = 0; i < arraySearch.length; i++) {
							ps.setString(i+1, "%" + arraySearch[i] + "%");
						}
						ResultSet resultat = ps.executeQuery();

						int total = 0;

						while (resultat.next()) {
							total = resultat.getInt("total");
						}

						return total;
					} catch (Exception ex) {
						ex.printStackTrace();
						throw new DAOException("Erreur avec la base de données");
					}
				}else {
					throw new DAOException("La recherche est trop longue");
				}
				
			}else {
				throw new DAOException("La recherche est trop courte");
			}
			
		}else {
			throw new DAOException("Il n'y a rien dans la recherche");
		}
		
	}

	public void delete(int game_id) {
		try {
			PreparedStatement ps = Database.connexion.prepareStatement("DELETE FROM games WHERE id = ?");
			ps.setInt(1, game_id);
			ps.executeUpdate();
			bddSays("delete", true, game_id, null);
		} catch (Exception ex) {
			bddSays("delete", false, game_id, null);
			ex.printStackTrace();
		}
	}
	
	
	
	
	public void update(int game_id) {
		prln("update scores of game_id : " + game_id);
		try {
			PreparedStatement ps = Database.connexion.prepareStatement("UPDATE games SET total_reviews = ( SELECT COUNT(comments.game_id) FROM comments WHERE comments.game_id = ?);");
			ps.setInt(1, game_id);
			ps.executeUpdate();
			
			PreparedStatement ps1 = Database.connexion.prepareStatement("UPDATE games SET users_avg_score = (SELECT ROUND(AVG(comments.score),2) FROM comments WHERE comments.game_id = ?);");
			ps1.setInt(1, game_id);
			ps1.executeUpdate();
			
			bddSays("update", true, game_id, null);
		} catch (Exception ex) {
			bddSays("update", false, game_id, null);
			ex.printStackTrace();
		}
	}

	/*
	 * Récupère 1 jeu, 1 genre, 1 developer, 1 mode de jeu
	 */
	public ArrayList<Object> auHasard() {
		try {

			ModeDAO modedao = new ModeDAO();
			GenreDAO genredao = new GenreDAO();
			DeveloperDAO developerdao = new DeveloperDAO();

			Random dé = new Random();

			ArrayList<Object> list = new ArrayList<>();

			// Game
			list.add(getNameAndIdById(dé.nextInt(countAll("all", null, null, null, null, null) - 1) + 1));

			// Genre
			list.add(genredao.getNameAndIdById(dé.nextInt(genredao.countAll() - 1) + 1));

			// Déveloper
			list.add(developerdao.getNameAndIdById(dé.nextInt(developerdao.countAll() - 1) + 1));

			// Mode
			list.add(modedao.getNameAndIdById(dé.nextInt(modedao.countAll() - 1) + 1));

			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
