package com.vapeur.servlets;

import static com.vapeur.config.Debug.prln;

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

@WebServlet(urlPatterns = { "/", "/landing" })
public class Landing extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Landing() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		// Vérif 404 -------------
		String requestURI = request.getRequestURI();
		prln(requestURI);
		if (!requestURI.equals("/VapeurBackOffice/") && !requestURI.equals("/VapeurBackOffice/landing")) {
			response.sendRedirect("404");
			return;
		}
		
		try {
			Database.connect();
			GameDAO gamedao = new GameDAO();
			
			ArrayList<Object> list = new ArrayList<>(gamedao.auHasard());
			request.setAttribute("list", list);
		}catch (Exception e) {
			request.setAttribute("errorMsg", "La base de donnée est indisponible. Merci de revenir plus tard." );
		}
		

		// ----------------------
		request.getRequestDispatcher("WEB-INF/app/landing.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
