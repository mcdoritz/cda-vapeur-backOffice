package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.beans.Developer;
import com.vapeur.beans.Game;
import com.vapeur.beans.GameLanguage;
import com.vapeur.beans.Genre;
import com.vapeur.beans.Language;
import com.vapeur.beans.Mode;
import com.vapeur.beans.Platform;
import com.vapeur.config.MajCommentsToApprove;
import com.vapeur.dao.DeveloperDAO;
import com.vapeur.dao.GameDAO;
import com.vapeur.dao.GenreDAO;
import com.vapeur.dao.LanguageDAO;
import com.vapeur.dao.ModeDAO;
import com.vapeur.dao.PlatformDAO;

/**
 * Servlet implementation class GameDetails
 */
@WebServlet("/gameDetails")
public class GameDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GameDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());
		request.setCharacterEncoding("UTF-8");

		try {
			if(checkAdmin(session)) {
				prln("servlet gameDetail : admin loggué");
				
				DeveloperDAO developerdao = new DeveloperDAO();
				GenreDAO genredao = new GenreDAO();
				ModeDAO modedao = new ModeDAO();
				PlatformDAO platformdao = new PlatformDAO();
				LanguageDAO languagedao = new LanguageDAO();
				
				ArrayList<Genre> genres = new ArrayList<>(genredao.readAll());
				ArrayList<Mode> modes = new ArrayList<>(modedao.readAll());
				ArrayList<Platform> platforms = new ArrayList<>(platformdao.readAll());
				ArrayList<Developer> developers = new ArrayList<>(developerdao.readAll());
				ArrayList<Language> languages = new ArrayList<>(languagedao.readAll());
				
				request.setAttribute("genres", genres);
				request.setAttribute("modes", modes);
				request.setAttribute("platforms", platforms);
				request.setAttribute("developers", developers);
				request.setAttribute("languages", languages);
				
				if(request.getParameter("id") != null) {
					
					int game_id = Integer.valueOf(request.getParameter("id"));
					GameDAO gamedao = new GameDAO();
					Game game = new Game();
					game = gamedao.getById(game_id);
					
					if(request.getParameter("action") != null && request.getParameter("action").equals("archive")) {
						Byte status = null;
						if(game.getStatus() == 0) {
							status = 2;
							response.sendRedirect("games?list&action=desarchivedOk");
						}else {
							status = 0;
							response.sendRedirect("games?list&action=archivedOk");
						}
						try {
							gamedao.updateArchive(game_id, status);
						} catch (Exception e) {
							response.sendRedirect("games?list&action=archivedKo");
							e.printStackTrace();
						}
						

					}else if(Integer.valueOf(request.getParameter("id")) == 0){ //Ajout
						request.setAttribute("pageTitle", "Vapeur.Admin : Ajout d'un jeu" );
						request.getRequestDispatcher("WEB-INF/app/gameDetails.jsp").forward(request, response);
						
					}else { // Modif

						request.setAttribute("pageTitle", "Vapeur.Admin : Modification d'un jeu" );

						List<Language> gameLanguages = languagedao.readAllByGameId(game_id);
						ArrayList<Boolean> interfaceSupport = new ArrayList<>();
						ArrayList<Boolean> audioSupport = new ArrayList<>();
						ArrayList<Boolean> subtitlesSupport = new ArrayList<>();
						ArrayList<Integer> languageId = new ArrayList<>();
						ArrayList<String> languageName = new ArrayList<>();
						
						for(Language l:gameLanguages) {
							prln("id : " + l.getId() + " " + l.getLanguage() + " : " + l.isInterfaceSupport() + " " + l.isFullAudioSupport() + " " + l.isSubtitles());

							languageId.add(l.getId());
							languageName.add(l.getLanguage());
							interfaceSupport.add(l.isInterfaceSupport());
							audioSupport.add(l.isFullAudioSupport());
							subtitlesSupport.add(l.isSubtitles());
						
						}
							
						if(game.getTitle() != null) {
							
							request.setAttribute("languageName", languageName);
							request.setAttribute("languageId", languageId);
							request.setAttribute("audioSupport", audioSupport);
							request.setAttribute("interfaceSupport", interfaceSupport);
							request.setAttribute("subtitlesSupport", subtitlesSupport);
							request.setAttribute("gameLanguages", gameLanguages );
							request.setAttribute("game", game );
							request.setAttribute("pageTitle", "Vapeur.Admin : Modification d'un jeu" );
						}else {
							request.setAttribute("errorMsg", "Erreur, pas de jeu trouvé." );
						}
						
						request.getRequestDispatcher("WEB-INF/app/gameDetails.jsp").forward(request, response);
					}

				}
			}else {
				response.sendRedirect("login");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prln("erreur : " + e.getMessage());
			request.setAttribute("errorMsg", e.getMessage() );
			response.sendRedirect("login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());
		request.setCharacterEncoding("UTF-8");
		try {
			if(checkAdmin(session)) {
				prln("servlet gameDetail Post : admin loggué");
				
				//Vérif formulaire
				
			    Map<String, String[]> champs = request.getParameterMap();
			    Boolean validé = true;
			    Game game = new Game();
			    
			 // Parcourir le map pour récupérer les valeurs associées à chaque nom de champ
		        for (Map.Entry<String, String[]> entry : champs.entrySet()) {
		            String name = entry.getKey(); // Tous les names
		            String[] values = entry.getValue(); // Tableau des données entrées 
		            
		            // Afficher les valeurs associées à chaque nom de champ
		            prln("Name du champ : " + name);
		            for(String str:values) {
		            	prln(str);
		            }
		            for (String value : values) {
		                if(value == null) {
		                	prln("champ " + name + " vide.");
		                	validé = false;
		                	break;
		                }
		            }
		            if(!validé) {
		            	break;
		            }
		        }
			    
			    if(validé) {
			    	prln("game_id : " + request.getParameter("game_id"));
			    	prln("formulaire validé !");
			    	if(request.getParameter("game_id") != "") {
			    		if(Integer.valueOf(request.getParameter("game_id")) > 0){
				    		game.setId(Integer.valueOf(request.getParameter("game_id")));
				    	}
			    	}

			    	game.setTitle(request.getParameter("title"));
			    	game.setDescription(request.getParameter("description"));
			    	game.setClassification(Integer.valueOf(request.getParameter("classification")));
			    	game.setPrice(Float.valueOf(request.getParameter("price")));
			    	
			    	// DATE C'EST COMPLIQUE MAIS C'EST COMPLIQUE
			    	String dateStr = request.getParameter("release-date");
			    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    	java.util.Date utilDate = sdf.parse(dateStr);
			    	java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			    	game.setReleaseDate(sqlDate);
			    	
			    	if(request.getParameter("controller-support") != null) {
			    		game.setControllerSupport(Boolean.valueOf(true));
			    	}
			    	if(request.getParameter("requires-3rd-party-account-support") != null) {
			    		game.setRequires3rdPartyAccount(Boolean.valueOf(true));
			    	}
			    	game.setStock(Integer.valueOf(request.getParameter("stock")));
			    	game.setDeveloperId(Integer.valueOf(request.getParameter("developer")));
			    	game.setPlatformId(Integer.valueOf(request.getParameter("platform")));

			    	if(request.getParameter("status") != null) {
			    		game.setStatus(Byte.valueOf(request.getParameter("status")));
			    	}else {
			    		game.setStatus(Byte.valueOf((byte) 1));
			    	}
			    	
			    	
			    	GenreDAO genredao = new GenreDAO();
			    	ArrayList<Genre> genres = new ArrayList<>();
			    	String[] genresForm = request.getParameterValues("genres");
			    	for(String genre_id : genresForm) {
			    		genres.add(genredao.getById(Integer.valueOf(genre_id)));
			    	}
			    	
			    	game.setGenres(genres);
			    	
			    	ModeDAO modedao = new ModeDAO();
			    	ArrayList<Mode> modes = new ArrayList<>();
			    	String[] modesForm = request.getParameterValues("modes");
			    	for(String mode_id : modesForm) {
			    		modes.add(modedao.getById(Integer.valueOf(mode_id)));
			    	}
			    	
			    	game.setModes(modes);
			    	
			    	// LANGUAGES :
			    	LanguageDAO languagedao = new LanguageDAO();
			    	
			    	ArrayList<Language> allLanguages = new ArrayList<>(languagedao.readAll());
			    	ArrayList<GameLanguage> gameLanguages = new ArrayList<>();			
			    	
			    	String[] interfaceForm = request.getParameterValues("interface-support");
			    	String[] fullAudioForm = request.getParameterValues("full-audio-support");
			    	String[] subtitlesForm = request.getParameterValues("subtitles-support");
			    	
			    	
			    	for(Language l:allLanguages) {
			    		GameLanguage gameLanguage = new GameLanguage();
			    		
			    		//Vérifier si le language est présent soit dans l'interface, soit dans l'audio soit dans les sous titres.
			    		for(String languageInterfaceId : interfaceForm) {
			    			if(languageInterfaceId.equals(String.valueOf(l.getId()))){
			    				gameLanguage.setGameId(game.getId());
			    				gameLanguage.setLanguageId(l.getId());
			    				gameLanguage.setInterfaceSupport(true);
				    		}
			    		}
			    		
			    		for(String languageFullAudioId : fullAudioForm) {
			    			if(languageFullAudioId.equals(String.valueOf(l.getId()))){
			    				gameLanguage.setGameId(game.getId());
			    				gameLanguage.setLanguageId(l.getId());
			    				gameLanguage.setFullAudioSupport(true);
				    		}
			    		}
			    		
			    		for(String languagesubtitlesFormId : subtitlesForm) {
			    			if(languagesubtitlesFormId.equals(String.valueOf(l.getId()))){
			    				gameLanguage.setGameId(game.getId());
			    				gameLanguage.setLanguageId(l.getId());
			    				gameLanguage.setSubtitles(true);
				    		}
			    		}
			    		prln(gameLanguage.toString());
			    		
			    		if(gameLanguage.getLanguageId() != 0) {
			    			gameLanguages.add(gameLanguage);
			    		}
			    		
			    	}
			    	
			    	game.setGameLanguages(gameLanguages);
			    	
			    	GameDAO gamedao = new GameDAO();
			    	
			    	
			    	
			    	if(gamedao.save(game)) {
			    		if(game.getId() == 0) {
			    			response.sendRedirect("games?list&action=addOk");
			    		}else {
			    			response.sendRedirect("games?list&action=saveOk");
			    		}
						
					}else {
						response.sendRedirect("games?list&action=saveKo");
					}


			    }else {
			    	prln("formulaire PAS validé !");
			    }		
				

				doGet(request, response);
			}else {
				response.sendRedirect("login");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prln("erreur : " + e.getMessage());
			request.setAttribute("errorMsg", e.getMessage() );
			response.sendRedirect("login");
		}
	}

}
