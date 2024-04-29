package com.vapeur.config;

import javax.servlet.http.HttpSession;

import com.vapeur.beans.Admin;
import com.vapeur.beans.User;
import com.vapeur.dao.AdminDAO;

import static com.vapeur.config.Debug.*;

public class ConnexionVerification {

	public static Boolean checkAdmin(HttpSession session) throws Exception {
		if (session != null) {
			prln("session pas null");
			if (session.getAttribute("admin") != null) {
				prln("admin trouvé dans session");
				Admin admin = new Admin();
				admin = (Admin) session.getAttribute("admin");
				if (admin.getId() > 0) {
					prln("admin id valide");
					Database.connect();
					AdminDAO admindao = new AdminDAO();
					
					Admin adminbdd = admindao.getById(admin.getId());
					
					if(adminbdd != null) {
						prln("admin id trouvé dans bdd");
						return true;
					}else {
						throw new Exception("Erreur l'admin");
					}
					
				}else {
					throw new Exception("Erreur avec l'ID");
				}
			}else {
				return false;
			}
		}else {
			return false;
		}
	}

}
