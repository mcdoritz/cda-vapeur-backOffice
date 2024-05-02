package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.beans.Developer;
import com.vapeur.beans.Genre;
import com.vapeur.dao.GenreDAO;

import com.vapeur.config.MajCommentsToApprove;

/**
 * Servlet implementation class Developers
 */
@WebServlet("/genres")
public class Genres extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Genres() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());
		
		if(request.getParameter("action") != null) {
			if(request.getParameter("action").equals("saveOk")){
				request.setAttribute("infoMsg", "Genre enregistré !");
			}else if(request.getParameter("action").equals("saveKo")){
				request.setAttribute("errorMsg", "Erreur, genre non enregistré !");
			}
		}

		try {
			if(checkAdmin(session)) {
				prln("servlet genres : admin loggué");
				GenreDAO genredao = new GenreDAO();
				
				ArrayList<Genre> genresList = new ArrayList<>(genredao.readAll());
				
				request.setAttribute("table", "genres");
				request.setAttribute("genresList", genresList);
				
				
				// ----------------------
				request.setAttribute("pageTitle", "Vapeur.Admin : Genres" );
				request.getRequestDispatcher("WEB-INF/app/genres.jsp").forward(request, response);
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
