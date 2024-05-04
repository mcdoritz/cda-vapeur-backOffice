package com.vapeur.servlets;

import static com.vapeur.config.ConnexionVerification.checkAdmin;
import static com.vapeur.config.Debug.prln;

import java.io.File;
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

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.vapeur.beans.Developer;
import com.vapeur.beans.Game;
import com.vapeur.beans.GameLanguage;
import com.vapeur.beans.Genre;
import com.vapeur.beans.Language;
import com.vapeur.beans.Mode;
import com.vapeur.beans.Platform;
import com.vapeur.config.MajCommentsToApprove;
import com.vapeur.dao.DeveloperDAO;
import com.vapeur.dao.GameDAO;
import com.vapeur.dao.GenreDAO;
import com.vapeur.dao.LanguageDAO;
import com.vapeur.dao.ModeDAO;
import com.vapeur.dao.PlatformDAO;

/**
 * Servlet implementation class GameDetails
 */
@WebServlet("/gameMedias")
public class GameMedias extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GameMedias() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		request.setAttribute("notifs", MajCommentsToApprove.returnCount());
		request.setCharacterEncoding("UTF-8");

		try {
			if (checkAdmin(session)) {
				prln("servlet gameMedias : admin loggué");

				if (request.getParameter("id") != null) {

					int game_id = Integer.valueOf(request.getParameter("id"));
					GameDAO gamedao = new GameDAO();
					Game game = new Game();
					game = gamedao.getById(game_id);
					
					//Images du jeu
					String relativeDirectoryPath = "/assets/images/games/" + game.getId() + "/";
					String absoluteDirectoryPath = getServletContext().getRealPath(relativeDirectoryPath);
					File directory = new File(absoluteDirectoryPath);
					
					ArrayList<String> images = new ArrayList<>();
					prln(relativeDirectoryPath);
					if (directory.isDirectory()) {
					    File[] files = directory.listFiles();
					    prln("nombre d'images : " + files.length);
					    for (File file : files) {
					    	
					    	images.add(file.getName());

					    }
					} else {
					    System.out.println("Le chemin spécifié n'est pas un répertoire.");
					}
					
					request.setAttribute("images", images);
					
					request.setAttribute("game", game);
					request.getRequestDispatcher("WEB-INF/app/gameMedias.jsp").forward(request, response);
				} else {
					request.setAttribute("errorMsg", "Erreur avec l'URL de la page");

				}
			} else {
				response.sendRedirect("login");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			prln("erreur : " + e.getMessage());
			request.setAttribute("errorMsg", e.getMessage());
			response.sendRedirect("login");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    HttpSession session = request.getSession(false);
	    request.setAttribute("notifs", MajCommentsToApprove.returnCount());
	    request.setCharacterEncoding("UTF-8");
	    try {
	        if (checkAdmin(session)) {
	            prln("servlet gameMedias Post : admin loggué ******************************************");
	            
	            if(request.getParameter("id") != null && Integer.valueOf(request.getParameter("id")) > 0) {
	            	prln("ok");
	            	int game_id = Integer.valueOf(request.getParameter("id"));
	            	prln(game_id);
	            	
	            	if(request.getParameter("imageDelete") == null) {
	            		if (ServletFileUpload.isMultipartContent(request)) {
			                ServletRequestContext ctx = new ServletRequestContext(request); // Utiliser ServletRequestContext
			                try {
			                    List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(ctx);
			                    for (FileItem item : items) {
			                        if (!item.isFormField()) {
			                            String fileName = new File(item.getName()).getName();
			                            String relativePath = "/assets/images/games/" + game_id + "/";
			                            String absolutePath = getServletContext().getRealPath(relativePath);
			                            String filePath = absolutePath + fileName; // Chemin où les fichiers seront sauvegardés
			                            File folder = new File(absolutePath);
			                            if (!folder.exists()) {
			                                if (folder.mkdirs()) {
			                                    prln("Dossier créé avec succès : " + folder.getAbsolutePath());
			                                } else {
			                                    prln("Impossible de créer le dossier : " + folder.getAbsolutePath());
			                                }
			                            } else {
			                                prln("Le dossier existe déjà : " + folder.getAbsolutePath());
			                            }
			                            File uploadedFile = new File(filePath);
			                            item.write(uploadedFile);
			                        }
			                    }
			                    prln("Tous les fichiers ont été téléchargés avec succès.");
			                } catch (IOException e) {
			                    prln("Erreur lors du téléchargement des fichiers : " + e.getMessage());
			                } catch (Exception e) {
			                    prln("Erreur inattendue : " + e.getMessage());
			                }
			            } else {
			                prln("Le formulaire ne prend pas en charge le téléchargement de fichiers.");
			            }
	            	}else {
	            		prln("suppression d'images");
	            		//Vérif formulaire
	    				
	    			    Map<String, String[]> champs = request.getParameterMap();
	    			    Boolean validé = true;
	    			    ArrayList<String> cheminsVersImages = new ArrayList<>();
	    			    
	    			    // Parcourir le map pour récupérer les valeurs associées à chaque nom de champ
	    		        for (Map.Entry<String, String[]> entry : champs.entrySet()) {
	    		            String name = entry.getKey(); // Tous les names
	    		            String[] values = entry.getValue(); // Tableau des données entrées 
	    		            
	    		            // Afficher les valeurs associées à chaque nom de champ
	    		            prln("Name du champ : " + name);
	    		            if(!name.equals("id")) {
	    		            	for(String str:values) {
		    		            	prln(str);
		    		            	cheminsVersImages.add(str);
		    		            }
		    		            for (String value : values) {
		    		                if(value == null) {
		    		                	prln("champ " + name + " vide.");
		    		                	validé = false;
		    		                	break;
		    		                }
		    		            }
		    		            if(!validé) {
		    		            	break;
		    		            }
	    		            }
	    		            
	    		        }
	    		        
	    		        for(String chemin:cheminsVersImages) {
	    		        	String relativePath = "assets/images/games/"+game_id+"/"+chemin;
	    		        	String absolutePath = getServletContext().getRealPath(relativePath);
	    		        	File cheminToDelete = new File(absolutePath);
	    		        	if (cheminToDelete.delete()) {
	    		        	    System.out.println("Fichier supprimé avec succès : " + cheminToDelete);
	    		        	} else {
	    		        	    System.out.println("Impossible de supprimer le fichier : " + cheminToDelete);
	    		        	}
	    		        }
	            	}
	            	
	            }
	            doGet(request, response);
	        } else {
	            response.sendRedirect("login");
	        }
	    } catch (Exception e) {
	        // TODO Auto-generated catch block
	        prln("erreur : " + e.getMessage());
	        request.setAttribute("errorMsg", e.getMessage());
	        response.sendRedirect("login");
	    }
	}


}
