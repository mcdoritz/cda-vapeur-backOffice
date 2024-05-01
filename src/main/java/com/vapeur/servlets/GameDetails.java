package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;
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
import com.vapeur.beans.Genre;
import com.vapeur.beans.Language;
import com.vapeur.beans.Mode;
import com.vapeur.beans.Platform;
import com.vapeur.dao.DeveloperDAO;
import com.vapeur.dao.GameDAO;
import com.vapeur.dao.GenreDAO;
import com.vapeur.dao.LanguageDAO;
import com.vapeur.dao.ModeDAO;
import com.vapeur.dao.PlatformDAO;

import com.vapeur.config.MajCommentsToApprove;

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
				
				if(request.getParameter("id") != null && Integer.valueOf(request.getParameter("id")) > 0) {
					
					int game_id = Integer.valueOf(request.getParameter("id"));
					GameDAO gamedao = new GameDAO();

					Game game = new Game();
					
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
					game = gamedao.getById(game_id);
					
					for(Language l:game.getLanguages()) {
						prln(l.getLanguage());
					}
					
					for(String l:languageName) {
						prln(l);
					}
					
					for(int i:languageId) {
						prln(i);
					}
					
					for(Boolean b:interfaceSupport) {
						prln(b);
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
				}else {
					request.setAttribute("pageTitle", "Vapeur.Admin : Ajout d'un jeu" );
				}

				request.getRequestDispatcher("WEB-INF/app/gameDetails.jsp").forward(request, response);
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
		try {
			if(checkAdmin(session)) {
				prln("servlet gameDetail Post : admin loggué");
				
				//Vérif formulaire
				
			    Map<String, String[]> champs = request.getParameterMap();
			    Boolean validé = true;
			    
			    for (String[] valeurs : champs.values()) {
			        for (String value : valeurs) {
			            if (value == null || value.trim().isEmpty()) {
			            	prln("erreur : " + value);
			            	validé = false;
			                break;
			            }
			            prln("erreur : " + value);
			        }
			        //Si validé est false, on arrête la boucle principale
			        if (!validé) {
			            break;
			        }
			    }
			    
			    if(validé) {
			    	prln("formulaire validé !");
			    	Map<String, String[]> donneesEntrees = request.getParameterMap();
			        
			        // Parcourir le map pour récupérer les valeurs associées à chaque nom de champ
			        for (Map.Entry<String, String[]> entry : donneesEntrees.entrySet()) {
			            String name = entry.getKey(); // Tous les names
			            String[] values = entry.getValue(); // Tableau des données entrées 
			            
			            // Afficher les valeurs associées à chaque nom de champ
			            System.out.println("Name du champ : " + name);
			            for (String value : values) {
			                prln("Valeur : " + value);
			            }
			        }

			    }else {
			    	prln("formulaire PAS validé !");
			    }
				
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
				

				request.getRequestDispatcher("WEB-INF/app/gameDetails.jsp").forward(request, response);
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
