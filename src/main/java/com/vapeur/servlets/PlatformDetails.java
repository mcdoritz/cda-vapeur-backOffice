package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.beans.Developer;
import com.vapeur.beans.Game;
import com.vapeur.beans.Platform;
import com.vapeur.beans.Language;
import com.vapeur.beans.Mode;
import com.vapeur.beans.Platform;
import com.vapeur.dao.DeveloperDAO;
import com.vapeur.dao.GameDAO;
import com.vapeur.dao.PlatformDAO;
import com.vapeur.dao.LanguageDAO;
import com.vapeur.dao.ModeDAO;
import com.vapeur.dao.PlatformDAO;
import com.vapeur.config.MajCommentsToApprove;
import com.vapeur.config.MajCommentsToApprove;

import com.vapeur.config.MajCommentsToApprove;
/**
 * Servlet implementation class Developers
 */
@WebServlet("/platformDetails")
public class PlatformDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PlatformDetails() {
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
				prln("servlet platformDetail : admin loggué");
			
				
				if(request.getParameter("id") != null && Integer.valueOf(request.getParameter("id")) > 0) {
					
					int platform_id = Integer.valueOf(request.getParameter("id"));
					PlatformDAO platformdao = new PlatformDAO();

					Platform platform = new Platform();
			

					platform = platformdao.getById(platform_id);

					if(platform.getName() != null) {
						
						request.setAttribute("platform", platform );
						request.setAttribute("pageTitle", "Vapeur.Admin : Modification d'une platforme" );
					}else {
						request.setAttribute("errorMsg", "Erreur, pas de platforme trouvé." );
					}
				}else {
					request.setAttribute("pageTitle", "Vapeur.Admin : Ajout d'une platforme" );
				}

				request.getRequestDispatcher("WEB-INF/app/platformDetails.jsp").forward(request, response);
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
		HttpSession session = request.getSession(false);
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());
		request.setCharacterEncoding("UTF-8");
		try {
			if(checkAdmin(session)) {
				prln("servlet developerDetails Post : admin loggué");
				
				//Vérif formulaire
				
				if(request.getParameter("name") != null && request.getParameter("acronym") != null && request.getParameter("platform_id") != null) {
					prln("formulaire validé !");
					
					Platform platform = new Platform();
					
					if(request.getParameter("platform_id") != "") {
						if(Integer.valueOf(request.getParameter("platform_id")) > 0){
							platform.setId(Integer.valueOf(request.getParameter("platform_id")));
				    	}
					}
					platform.setAcronym(request.getParameter("acronym"));
					platform.setName(request.getParameter("name"));

					
					PlatformDAO platformdao = new PlatformDAO();
			    	
			    	
			    	if(platformdao.save(platform)) {
						response.sendRedirect("platforms?list&action=saveOk");
					}else {
						response.sendRedirect("platforms?list&action=saveKo");
					}
			    	
				}else {
			    	prln("formulaire PAS validé !");
			    	response.sendRedirect("platforms?list&action=saveKo");
			    }
				
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

}
