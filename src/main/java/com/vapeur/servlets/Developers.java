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
import com.vapeur.dao.DeveloperDAO;

import com.vapeur.config.MajCommentsToApprove;

/**
 * Servlet implementation class Developers
 */
@WebServlet("/developers")
public class Developers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Developers() {
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
				request.setAttribute("infoMsg", "Développeur enregistré !");
			}else if(request.getParameter("action").equals("saveKo")){
				request.setAttribute("errorMsg", "Erreur, Développeur non enregistré !");
			}
		}

		try {
			if(checkAdmin(session)) {
				prln("servlet developers : admin loggué");
				DeveloperDAO developerdao = new DeveloperDAO();
				
				ArrayList<Developer> developersList = new ArrayList<>(developerdao.readAll());
				
				request.setAttribute("table", "developers");
				request.setAttribute("developersList", developersList);
				
				
				// ----------------------
				request.setAttribute("pageTitle", "Vapeur.Admin : Développeurs" );
				request.getRequestDispatcher("WEB-INF/app/developers.jsp").forward(request, response);
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
