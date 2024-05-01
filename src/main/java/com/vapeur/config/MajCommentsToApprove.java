package com.vapeur.config;

import com.vapeur.dao.CommentDAO;
import static com.vapeur.config.Debug.*;

public class MajCommentsToApprove {
	
	public static int returnCount() {
		Database.connect();
		CommentDAO commentdao = new CommentDAO();
		
		int count = commentdao.countAllNotApprovedComments();
		prln(count + " commentaires à approuver");
		return count;
	}

}
