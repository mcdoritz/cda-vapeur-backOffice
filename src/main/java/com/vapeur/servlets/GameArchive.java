package com.vapeur.servlets;

import static com.vapeur.config.Debug.prln;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.beans.User;
import com.vapeur.config.Database;

import com.vapeur.config.MajCommentsToApprove;

/**
 * Servlet implementation class GameDelete
 */
@WebServlet("/gameDelete")
public class GameArchive extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public GameArchive() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());

		if(session != null) {
			if(session.getAttribute("user") != null) {
				User user = (User) session.getAttribute("user");
				if(user.getId() != 0) {
					prln("user.getId est pas null");
					try {
						Database.connect();
						
						//récupérer les informations 
						
						
						
					} catch (Exception e) {
						prln(e.getMessage());
						e.printStackTrace();
						request.setAttribute("errorMsg", "La base de donnée est indisponible. Merci de revenir plus tard." );
					}
				}
			}
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());
		doGet(request, response);
	}

}
