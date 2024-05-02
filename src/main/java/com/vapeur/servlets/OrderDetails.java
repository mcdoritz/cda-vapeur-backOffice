package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vapeur.beans.Developer;
import com.vapeur.beans.User;
import com.vapeur.dao.DAOException;
import com.vapeur.dao.DeveloperDAO;
import com.vapeur.dao.OrderDAO;
import com.vapeur.dao.UserDAO;
import com.vapeur.config.Database;
import com.vapeur.config.MajCommentsToApprove;
/**
 * Servlet implementation class UserDetails
 */
@WebServlet("/orderDetails")
public class OrderDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public OrderDetails() {
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
				prln("servlet orderDetails : admin loggué");
			
				
				if(request.getParameter("user_id") != null && Integer.valueOf(request.getParameter("user_id")) > 0 && request.getParameter("order_id") != null && Integer.valueOf(request.getParameter("order_id")) > 0) {
					
					int user_id = Integer.valueOf(request.getParameter("user_id"));
					int order_id = Integer.valueOf(request.getParameter("order_id"));
					OrderDAO orderdao = new OrderDAO();
					UserDAO userdao = new UserDAO();
					
					request.setAttribute("user", userdao.getById(user_id));
					request.setAttribute("ordersList", orderdao.readAllByOrderId(order_id));
							
				}else {
					request.setAttribute("errorMSg", "Erreur avec l'Id");
					doGet(request, response);
				}
				

				request.getRequestDispatcher("WEB-INF/app/orderDetails.jsp").forward(request, response);
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
				
				if(request.getParameter("email") != null && request.getParameter("nickname") != null && request.getParameter("user_id") != null) {
					prln("formulaire validé !");
					
					User user = new User();
					int user_id = 0;
					if(request.getParameter("user_id") != "") {
						if(Integer.valueOf(request.getParameter("user_id")) > 0){
							user_id = Integer.valueOf(request.getParameter("user_id"));
							user.setId(user_id);
				    	}
					}
					prln("ok 1");
					user.setNickname(request.getParameter("nickname"));
					user.setEmail(request.getParameter("email"));
					prln("ok 2");
					if(request.getParameter("active") != null) {
						user.setActive(true);
			    	}else {
			    		user.setActive(false);
			    	}
					prln("ok 3");
					
					
					if(!request.getParameter("firstname").equals(null) && request.getParameter("firstname").length() > 0) {
						user.setFirstname(request.getParameter("firstname"));
					}
					prln("ok 5");
					if(!request.getParameter("lastname").equals(null) && request.getParameter("lastname").length() > 0) {
						user.setLastname(request.getParameter("lastname"));
					}
					prln("ok 6");
					if(!request.getParameter("shippingAddress").equals(null) && request.getParameter("shippingAddress").length() > 0) {
						user.setShippingAddress(request.getParameter("shippingAddress"));
					}
					prln("ok 7");
					
					UserDAO userdao = new UserDAO();
					
					User userVerif = new User();
					userVerif = userdao.getById(user_id);
			    	
			    	if(userVerif != null) {
			    		user.setPassword(userVerif.getPassword());
			    		if(userdao.save(user, true)) {
							response.sendRedirect("users?list&action=saveOk");
						}else {
							response.sendRedirect("users?list&action=saveKo");
						}
			    	}
			    	
			    	
				}else {
			    	prln("formulaire PAS validé !");
			    	response.sendRedirect("users?list&action=saveKo");
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
