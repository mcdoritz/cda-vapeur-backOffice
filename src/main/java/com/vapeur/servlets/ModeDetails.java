package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;
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
import com.vapeur.beans.Mode;
import com.vapeur.beans.Language;
import com.vapeur.beans.Mode;
import com.vapeur.beans.Platform;
import com.vapeur.dao.DeveloperDAO;
import com.vapeur.dao.GameDAO;
import com.vapeur.dao.ModeDAO;
import com.vapeur.dao.LanguageDAO;
import com.vapeur.dao.ModeDAO;
import com.vapeur.dao.PlatformDAO;

import com.vapeur.config.MajCommentsToApprove;

/**
 * Servlet implementation class Developers
 */
@WebServlet("/modeDetails")
public class ModeDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModeDetails() {
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
				prln("servlet modeDetail : admin loggué");
			
				
				if(request.getParameter("id") != null && Integer.valueOf(request.getParameter("id")) > 0) {
					
					int mode_id = Integer.valueOf(request.getParameter("id"));
					ModeDAO modedao = new ModeDAO();

					Mode mode = new Mode();
			

					mode = modedao.getById(mode_id);

					if(mode.getName() != null) {
						
						request.setAttribute("mode", mode );
						request.setAttribute("pageTitle", "Vapeur.Admin : Modification d'un mode" );
					}else {
						request.setAttribute("errorMsg", "Erreur, pas de mode trouvé." );
					}
				}else {
					request.setAttribute("pageTitle", "Vapeur.Admin : Ajout d'un mode" );
				}

				request.getRequestDispatcher("WEB-INF/app/modeDetails.jsp").forward(request, response);
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
				prln("servlet modeDetails Post : admin loggué");
				
				//Vérif formulaire
				
				if(request.getParameter("name") != null && request.getParameter("mode_id") != null) {
					prln("formulaire validé !");
					
					Mode mode = new Mode();
					
					if(request.getParameter("mode_id") != "") {
						if(Integer.valueOf(request.getParameter("mode_id")) > 0){
							mode.setId(Integer.valueOf(request.getParameter("mode_id")));
				    	}
					}
					
					mode.setName(request.getParameter("name"));

					
					ModeDAO modedao = new ModeDAO();
			    	
			    	
			    	if(modedao.save(mode)) {
						response.sendRedirect("modes?list&action=saveOk");
					}else {
						response.sendRedirect("modes?list&action=saveKo");
					}
			    	
				}else {
			    	prln("formulaire PAS validé !");
			    	response.sendRedirect("modes?list&action=saveKo");
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
