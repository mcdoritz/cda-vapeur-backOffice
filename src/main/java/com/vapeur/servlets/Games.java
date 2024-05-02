package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.beans.GameResults;
import com.vapeur.config.Database;
import com.vapeur.dao.GameDAO;

import com.vapeur.config.MajCommentsToApprove;

/**
 * Servlet implementation class Games
 */
@WebServlet("/games")
public class Games extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Games() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());
		request.setCharacterEncoding("UTF-8");
		
		if(request.getParameter("action") != null) {
			if(request.getParameter("action").equals("desarchivedOk")) {
				request.setAttribute("infoMsg", "Jeu désarchivé !");
			}else if(request.getParameter("action").equals("archivedOk")){
				request.setAttribute("infoMsg", "Jeu archivé !");
			}else if(request.getParameter("action").equals("archivedKo")){
				request.setAttribute("errorMsg", "Erreur avec l'archivage du jeu");
			}
		}

		try {
			if(checkAdmin(session)) {
				prln("servlet games : admin loggué");

				GameDAO gamedao = new GameDAO();
				
				if(request.getParameter("list") != null) {
					prln("jeux en ventes");
					GameResults gameresults = gamedao.adminReadAll(false);
					if(gameresults.getTotalResults() != 0) {
						request.setAttribute("totalGames", gameresults.getTotalResults());
						request.setAttribute("gamesList", gameresults.getGames());
					}else {
						request.setAttribute("infoMsg", "Aucun jeu trouvé");
					}
				}else if (request.getParameter("archived") != null) {
					prln("jeux archivés");
					GameResults gameresults = gamedao.adminReadAll(true);
					if(gameresults.getTotalResults() != 0) {
						request.setAttribute("archive", true);
						request.setAttribute("totalGames", gameresults.getTotalResults());
						request.setAttribute("gamesList", gameresults.getGames());
					}else {
						request.setAttribute("infoMsg", "Aucun jeu trouvé");
					}
					
				}else {
					request.setAttribute("errorMsg", "Erreur avec l'URL");
				}
				
				
				
				request.setAttribute("table", "games");
				
				request.setAttribute("pageTitle", "Vapeur.Admin : Jeux" );

				request.getRequestDispatcher("WEB-INF/app/games.jsp").forward(request, response);
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
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
