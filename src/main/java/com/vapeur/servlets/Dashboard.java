package com.vapeur.servlets;

import static com.vapeur.config.Debug.prln;
import static com.vapeur.config.ConnexionVerification.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.config.Database;
import com.vapeur.config.DatabaseException;
import com.vapeur.dao.GameDAO;

@WebServlet(urlPatterns = { "/", "/dashboard" })
public class Dashboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Dashboard() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		// Vérif 404 -------------
		String requestURI = request.getRequestURI();
		prln(requestURI);
		if (!requestURI.equals("/VapeurBackOffice/") && !requestURI.equals("/VapeurBackOffice/dashboard")) {
			response.sendRedirect("404");
			return;
		}
		
		try {
			if(checkAdmin(session)) {
				prln("servlet Dashboard : admin loggué");
				GameDAO gamedao = new GameDAO();
				
				ArrayList<Object> list = new ArrayList<>(gamedao.auHasard());
				request.setAttribute("list", list);
				// ----------------------
				request.setAttribute("pageTitle", "Vapeur.Admin : Dashboard" );
				request.getRequestDispatcher("WEB-INF/app/dashboard.jsp").forward(request, response);
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
