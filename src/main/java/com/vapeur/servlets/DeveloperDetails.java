package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.beans.Developer;
import com.vapeur.beans.Game;
import com.vapeur.beans.GameLanguage;
import com.vapeur.beans.Genre;
import com.vapeur.beans.Language;
import com.vapeur.beans.Mode;
import com.vapeur.beans.Platform;
import com.vapeur.dao.DeveloperDAO;
import com.vapeur.dao.GameDAO;
import com.vapeur.dao.GenreDAO;
import com.vapeur.dao.LanguageDAO;
import com.vapeur.dao.ModeDAO;
import com.vapeur.dao.PlatformDAO;

import com.vapeur.config.MajCommentsToApprove;

/**
 * Servlet implementation class Developers
 */
@WebServlet("/developerDetails")
public class DeveloperDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeveloperDetails() {
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
				prln("servlet developerDetail : admin loggué");
			
				
				if(request.getParameter("id") != null && Integer.valueOf(request.getParameter("id")) > 0) {
					
					int developer_id = Integer.valueOf(request.getParameter("id"));
					DeveloperDAO developerdao = new DeveloperDAO();

					Developer developer = new Developer();
			

					developer = developerdao.getById(developer_id);

					if(developer.getName() != null) {
						
						request.setAttribute("developer", developer );
						request.setAttribute("pageTitle", "Vapeur.Admin : Modification d'un développeur" );
					}else {
						request.setAttribute("errorMsg", "Erreur, pas de développeur trouvé." );
					}
				}else {
					request.setAttribute("pageTitle", "Vapeur.Admin : Ajout d'un développeur" );
				}

				request.getRequestDispatcher("WEB-INF/app/developerDetails.jsp").forward(request, response);
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
				
				if(request.getParameter("name") != null && request.getParameter("creation-date") != null && request.getParameter("developer_id") != null) {
					prln("formulaire validé !");
					
					Developer developer = new Developer();
					
					if(request.getParameter("developer_id") != "") {
						if(Integer.valueOf(request.getParameter("developer_id")) > 0){
				    		developer.setId(Integer.valueOf(request.getParameter("developer_id")));
				    	}
					}
					
					developer.setName(request.getParameter("name"));
					
					if(request.getParameter("name") != null) {
						developer.setCountry(request.getParameter("name"));
					}
					
					if(request.getParameter("creation-date") != null) {
						// DATE C'EST COMPLIQUE MAIS C'EST COMPLIQUE
				    	String dateStr = request.getParameter("creation-date");
				    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				    	java.util.Date utilDate = sdf.parse(dateStr);
				    	java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
						developer.setCreationDate(sqlDate);
					}
					
					if(request.getParameter("instagram") != null) {
						developer.setUrlInstagram(request.getParameter("instagram"));
					}else {
						developer.setUrlInstagram(null);
					}
					
					if(request.getParameter("X") != null) {
						developer.setUrlX(request.getParameter("X"));
					}else {
						developer.setUrlX(null);
					}
					
					if(request.getParameter("facebook") != null) {
						developer.setUrlFacebook(request.getParameter("facebook"));
					}else {
						developer.setUrlFacebook(null);
					}
					
					if(request.getParameter("website") != null) {
						developer.setUrlWebsite(request.getParameter("website"));
					}else {
						developer.setUrlWebsite(null);
					}
					
					DeveloperDAO developerdao = new DeveloperDAO();
			    	
			    	
			    	if(developerdao.save(developer)) {
						response.sendRedirect("developers?list&action=saveOk");
					}else {
						response.sendRedirect("developers?list&action=saveKo");
					}
			    	
				}else {
			    	prln("formulaire PAS validé !");
			    	response.sendRedirect("developers?list&action=saveKo");
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
