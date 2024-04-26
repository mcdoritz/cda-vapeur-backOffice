package com.vapeur.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.vapeur.config.Debug.*;

@WebServlet("/404")
public class Error404 extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Error404() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		
		prln("Servlet erreur 404");
		request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
		
		request.getRequestDispatcher("WEB-INF/error/404.jsp").forward(request, response);
	}

}
