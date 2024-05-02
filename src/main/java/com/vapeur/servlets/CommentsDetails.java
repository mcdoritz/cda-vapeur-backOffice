package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.beans.Comment;
import com.vapeur.beans.Developer;
import com.vapeur.beans.Game;
import com.vapeur.beans.Genre;
import com.vapeur.beans.Language;
import com.vapeur.beans.Mode;
import com.vapeur.beans.Platform;
import com.vapeur.config.MajCommentsToApprove;
import com.vapeur.dao.CommentDAO;
import com.vapeur.dao.DeveloperDAO;
import com.vapeur.dao.GameDAO;
import com.vapeur.dao.GenreDAO;
import com.vapeur.dao.LanguageDAO;
import com.vapeur.dao.ModeDAO;
import com.vapeur.dao.PlatformDAO;

/**
 * Servlet implementation class GameDetails
 */
@WebServlet("/commentsDetails")
public class CommentsDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsDetails() {
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
				prln("servlet commentDetail : admin loggué");

				if(request.getParameter("game_id") != null && Integer.valueOf(request.getParameter("game_id")) > 0) {
					
					int game_id = Integer.valueOf(request.getParameter("game_id"));
					CommentDAO commentdao = new CommentDAO();
					GameDAO gamedao = new GameDAO();
					
					Game game = gamedao.getById(game_id);

					ArrayList<Comment> commentsApproved = new ArrayList<>(commentdao.getByGameId(game_id, true));
					ArrayList<Comment> commentsNOTapproved = new ArrayList<>(commentdao.getByGameId(game_id, false));
					
					if(commentsApproved.size() > 0 ||  commentsNOTapproved.size() > 0) {
						
						request.setAttribute("game", game);
						request.setAttribute("approvedCommentsList", commentsApproved);
						request.setAttribute("NOTapprovedCommentsList", commentsNOTapproved);
					}else {
						request.setAttribute("errorMsg", "Pas de commentaire trouvé pour " + game.getTitle() );
					}
				}else {
					request.setAttribute("errorMsg", "Erreur, pas de jeu récupéré" );
				}

				request.getRequestDispatcher("WEB-INF/app/commentsDetails.jsp").forward(request, response);
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
		request.setCharacterEncoding("UTF-8");
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());
		HttpSession session = request.getSession(false);
		
		try {
			if(checkAdmin(session)) {
				prln("servlet commentDetails Post : admin loggué");
				
				//Vérif formulaire
				
				String button = request.getParameter("action");
				prln("****** BUTTON : " + button);
							    
			    //Vérif id du commentaire
			    if(request.getParameter("game_id") != null && request.getParameter("user_id") != null) {
			    	int game_id = Integer.valueOf(request.getParameter("game_id"));
			    	int user_id = Integer.valueOf(request.getParameter("user_id"));
			    	CommentDAO commentdao = new CommentDAO();
			    	Comment commentVerif = commentdao.getById(user_id, game_id);
			    	
			    	if(commentVerif != null) {
			    		prln("commentVerif " +commentVerif.getUserNickname());
			    		Comment comment = new Comment();

			    		comment.setGameId(commentVerif.getGameId());
			    		comment.setUserId(commentVerif.getUserId());
			    		comment.setScore((int) commentVerif.getScore());
			    		comment.setUserNickname(commentVerif.getUserNickname());
			    		comment.setUploaded(commentVerif.getUploaded());
			    		comment.setModerated(true);
			    		
			    		prln(request.getParameter("content"));
			    		
						if(button.equals("approve") || button.equals("modify") ) {
							comment.setContent(request.getParameter("content"));
						}else {
							comment.setContent("");
						}
						prln(comment.getContent());
			    		prln("GAME ID :" + comment.getGameId());
			    		prln("USER ID :" + comment.getUserId());
			    		if(commentdao.save(comment)) {
			    			if(button.equals("approve")) {
			    				request.setAttribute("infoMsg", "Commentaire de " + comment.getUserNickname() + " APPROUVE ! ");
							}else if (button.equals("refuse")){
								request.setAttribute("infoMsg", "Commentaire de " + comment.getUserNickname() + " REFUSE ! ");
							}else {
								request.setAttribute("infoMsg", "Commentaire de " + comment.getUserNickname() + " MODIFIE ! ");
							}
			    			
			    		}else {
			    			request.setAttribute("errorMsg", "Erreur avec l'enregistrement");
			    		}
			    		
			    		
			    	}else {
			    		request.setAttribute("errorMsg", "Erreur avec la récupération de l'ID du commentaire. ");
			    	}
			    	
			    }else {
			    	request.setAttribute("errorMsg", "Erreur dans l'ID du commentaire");
			    }
			    doGet(request, response);
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
