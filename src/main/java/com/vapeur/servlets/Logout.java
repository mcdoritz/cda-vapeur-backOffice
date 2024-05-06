package com.vapeur.servlets;

import static com.vapeur.config.Debug.prln;
import static com.vapeur.config.ConnexionVerification.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.beans.Admin;
import com.vapeur.beans.User;
import com.vapeur.config.Database;
import com.vapeur.dao.AdminDAO;
import com.vapeur.dao.DAOException;
import com.vapeur.dao.UserDAO;

import com.vapeur.config.MajCommentsToApprove;

/**
 * Servlet implementation class Login
 */
@WebServlet("/logout")
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Logout() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		session.invalidate();
		request.setAttribute("pageTitle", "Vapeur.Admin : Login" );
		//request.setAttribute("errorMsg", "Veuillez vous connecter pour accéder à cette page");
		request.getRequestDispatcher("WEB-INF/login/login.jsp").forward(request, response);
	}

}
