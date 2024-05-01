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

import com.vapeur.beans.Admin;
import com.vapeur.config.MajCommentsToApprove;
import com.vapeur.dao.AdminDAO;

/**
 * Servlet implementation class AdminDetails
 */
@WebServlet("/adminDetails")
public class AdminDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminDetails() {
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
				prln("servlet adminDetails : admin loggué");
			
				
				if(request.getParameter("id") != null && Integer.valueOf(request.getParameter("id")) > 0) {
					
					Admin currentAdmin = new Admin();
					
					currentAdmin = (Admin) request.getAttribute("admin");
					
					if(Integer.valueOf(request.getParameter("id")) == currentAdmin.getId()){
						int admin_id = Integer.valueOf(request.getParameter("id"));
						AdminDAO admindao = new AdminDAO();

						Admin admin = new Admin();
				

						admin = admindao.getById(admin_id);

						if(admin.getEmail() != null) {
							
							request.setAttribute("admin", admin );
							request.setAttribute("pageTitle", "Vapeur.Admin : Modification d'un admin" );
						}else {
							request.setAttribute("errorMsg", "Erreur, pas de admin trouvé." );
						}
					}else {
						request.setAttribute("errorMsg", "Erreur, vous ne pouvez pas modifier un autre admin." );
					}
					
					
				}else {
					request.setAttribute("pageTitle", "Vapeur.Admin : Ajout d'un admin" );
				}

				request.getRequestDispatcher("WEB-INF/app/adminDetails.jsp").forward(request, response);
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
