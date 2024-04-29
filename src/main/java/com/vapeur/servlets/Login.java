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

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		try {
			if(checkAdmin(session)) {
				prln("login : admin loggué");
				response.sendRedirect("dashboard");
			}else {
				prln("login : admin PAS loggué");
				request.setAttribute("pageTitle", "Vapeur.Admin : Login" );
				//request.setAttribute("errorMsg", "Veuillez vous connecter pour accéder à cette page");
				request.getRequestDispatcher("WEB-INF/login/login.jsp").forward(request, response);
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Vérification que les champs sont pas null AJOUTER VERIFICATION VALIDATION
		if (request.getParameter("email") != null && request.getParameter("password") != null) {
			prln("Formulaire login OK !");
			try {
				Database.connect();
				AdminDAO admindao = new AdminDAO();
				Admin authorizedAdmin;
				try {
					authorizedAdmin = admindao.login(request.getParameter("email"), request.getParameter("password"));
					if (authorizedAdmin != null) {
						prln("login OK !");
						// Si tout est OK création de la session
						HttpSession session = request.getSession();
						session.setAttribute("admin", authorizedAdmin);
						response.sendRedirect("dashboard");
					} else {
						prln("login KO !");
						doGet(request, response);
					}
				} catch (DAOException e) {
					request.setAttribute("errorMsg", e.getMessage());
					e.printStackTrace();

					request.getRequestDispatcher("WEB-INF/login/login.jsp").forward(request, response);
				}
			}catch (Exception e) {
				request.setAttribute("errorMsg", "La base de donnée est indisponible. Merci de revenir plus tard." );
				request.getRequestDispatcher("WEB-INF/app/login.jsp").forward(request, response);
			}
			

		} else {
			prln("Formulaire login KO !");
			request.setAttribute("errorMsg", "Erreur d'entrée du formulaire" );
			doGet(request, response);
		}
	}

}
