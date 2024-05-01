package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.vapeur.config.MajCommentsToApprove;
/**
 * Servlet implementation class Developers
 */
@WebServlet("/genreDetails")
public class GenreDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GenreDetails() {
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
				prln("servlet genreDetail : admin loggué");
			
				
				if(request.getParameter("id") != null && Integer.valueOf(request.getParameter("id")) > 0) {
					
					int genre_id = Integer.valueOf(request.getParameter("id"));
					GenreDAO genredao = new GenreDAO();

					Genre genre = new Genre();
			

					genre = genredao.getById(genre_id);

					if(genre.getName() != null) {
						
						request.setAttribute("genre", genre );
						request.setAttribute("pageTitle", "Vapeur.Admin : Modification d'un genre" );
					}else {
						request.setAttribute("errorMsg", "Erreur, pas de genre trouvé." );
					}
				}else {
					request.setAttribute("pageTitle", "Vapeur.Admin : Ajout d'un genre" );
				}

				request.getRequestDispatcher("WEB-INF/app/genreDetails.jsp").forward(request, response);
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
		doGet(request, response);
	}

}
