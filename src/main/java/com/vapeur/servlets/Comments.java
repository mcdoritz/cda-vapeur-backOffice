package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;

import com.vapeur.config.MajCommentsToApprove;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.beans.GameResults;
import com.vapeur.dao.CommentDAO;
import com.vapeur.dao.GameDAO;
import com.vapeur.config.MajCommentsToApprove;
/**
 * Servlet implementation class Comments
 */
@WebServlet("/comments")
public class Comments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Comments() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());
		request.setCharacterEncoding("UTF-8");

		try {
			if(checkAdmin(session)) {
				prln("servlet comments : admin loggué");
				GameDAO gamedao = new GameDAO();
				
				GameResults gameresults = gamedao.adminReadAll((byte)2);
				
				request.setAttribute("table", "gamesComments");
				request.setAttribute("totalGames", gameresults.getTotalResults());
				request.setAttribute("gamesList", gameresults.getGames());
				
				
				// ----------------------
				request.setAttribute("pageTitle", "Vapeur.Admin : Commentaires" );
				request.getRequestDispatcher("WEB-INF/app/comments.jsp").forward(request, response);
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
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}

}
