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

import com.vapeur.beans.User;
import com.vapeur.dao.UserDAO;
import com.vapeur.config.MajCommentsToApprove;
/**
 * Servlet implementation class UserDetails
 */
@WebServlet("/userDetails")
public class UserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserDetails() {
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
				prln("servlet userDetails : admin loggué");
			
				
				if(request.getParameter("id") != null && Integer.valueOf(request.getParameter("id")) > 0) {
					
					int user_id = Integer.valueOf(request.getParameter("id"));
					UserDAO userdao = new UserDAO();

					User user = new User();
			

					user = userdao.getById(user_id);

					if(user.getNickname() != null) {
						
						request.setAttribute("user", user );
						request.setAttribute("pageTitle", "Vapeur.Admin : Modification d'un user" );
					}else {
						request.setAttribute("errorMsg", "Erreur, pas de user trouvé." );
					}
				}else {
					request.setAttribute("pageTitle", "Vapeur.Admin : Ajout d'un user" );
				}

				request.getRequestDispatcher("WEB-INF/app/userDetails.jsp").forward(request, response);
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
