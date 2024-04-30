package com.vapeur.dao;

import static com.vapeur.config.Debug.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.vapeur.beans.Admin;
import com.vapeur.config.Database;
import com.vapeur.dao.AdminDAO;

public class AdminDAO {

	public Boolean save(Admin object) throws DAOException {
		try {

			String hashPassword = BCrypt.hashpw(object.getPassword(), BCrypt.gensalt());

			if (object.getId() != 0) {

				String query = "UPDATE admins SET email = ?,  password = ?, firstname = ?, lastname = ?, active = ? WHERE id = ?";

				try (PreparedStatement ps = Database.connexion.prepareStatement(query)) {
					ps.setString(1, object.getEmail());
					ps.setString(2, hashPassword);
					ps.setString(3, object.getFirstname());
					ps.setString(4, object.getLastname());
					ps.setBoolean(5, object.isActive());
					ps.setInt(6, object.getId());

					ps.executeUpdate();
				}
				return true;

			} else {
				String query = "INSERT INTO admins (email, password, firstname, lastname, active) VALUES (?, ?, ?, ?, ?)";

				try (PreparedStatement ps = Database.connexion.prepareStatement(query,
						Statement.RETURN_GENERATED_KEYS)) {
					ps.setString(1, object.getEmail());
					ps.setString(2, hashPassword);
					ps.setString(3, object.getFirstname());
					ps.setString(4, object.getLastname());
					ps.setBoolean(5, true); // Hé oui !

					ps.executeUpdate();

					try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
						if (generatedKeys.next()) {
							return true;
						} else {
							bddSays("create", false, object.getId(), null);
							throw new DAOException("Erreur avec la base de données");

						}
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public Admin getById(int admin_id) {
		try {

			PreparedStatement ps = Database.connexion.prepareStatement("SELECT * FROM admins WHERE id = ?");
			ps.setInt(1, admin_id);

			ResultSet resultat = ps.executeQuery();

			Admin object = new Admin();

			while (resultat.next()) {
				object.setId(resultat.getInt("id"));
				object.setEmail(resultat.getString("email"));
				object.setPassword(resultat.getString("password"));
				object.setFirstname(resultat.getString("firstname"));
				object.setLastname(resultat.getString("lastname"));
				object.setActive(resultat.getBoolean("active"));
			}

			String objectInfos = object.getFirstname() + " " + object.getLastname();
			bddSays("read", true, object.getId(), objectInfos);
			return object;

		} catch (Exception ex) {
			ex.printStackTrace();
			bddSays("read", false, admin_id, null);
			return null;
		}
	}

	public Admin login(String email, String password) throws DAOException {
		try {

			PreparedStatement ps = Database.connexion
					.prepareStatement("SELECT id, password FROM admins WHERE email = ? AND active = true");
			ps.setString(1, email);

			ResultSet resultat = ps.executeQuery();

			Admin object = new Admin();

			if (!resultat.next()) {

				throw new Exception("Admin non trouvé dans la BDD");
			} else {
				object.setId(resultat.getInt("id"));
				object.setPassword(resultat.getString("password"));

				String objectInfos = object.getId() + " " + object.getEmail();
				bddSays("read", true, object.getId(), objectInfos);

				if (BCrypt.checkpw(password, object.getPassword())) {

					Admin authorizedAdmin = getById(object.getId());

					return authorizedAdmin;

				} else {
					throw new Exception("Les identifiants entrés ne sont pas bons.");
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			prln("Erreur avec la récupération de l'admin");
			throw new DAOException(ex.getMessage());
		}
	}

	public List<Admin> readAll(String status) {

		ArrayList<Admin> adminsList = new ArrayList<>();
		String query = "SELECT * FROM admins";
		try {
			switch (status) {
			case "active":
				query += " WHERE active = true";
				break;
			case "archived":
				query += " WHERE active = false";
				break;
			}

			PreparedStatement ps = Database.connexion.prepareStatement(query);
			ResultSet resultat = ps.executeQuery();

			while (resultat.next()) {
				Admin objet = new Admin();

				objet.setId(resultat.getInt("id"));
				objet.setEmail(resultat.getString("email"));
				objet.setPassword(resultat.getString("password"));
				objet.setFirstname(resultat.getString("firstname"));
				objet.setLastname(resultat.getString("lastname"));
				objet.setActive(resultat.getBoolean("active"));

				adminsList.add(objet);
			}
			bddSays("readAll", true, adminsList.size(), null);
			return adminsList;
		} catch (Exception ex) {
			bddSays("readAll", false, 0, null);
			ex.printStackTrace();
			return null;
		}
	}

	public void delete(int admin_id) {
		// TODO Auto-generated method stub
		try {

			PreparedStatement ps = Database.connexion.prepareStatement("DELETE FROM admins WHERE id = ?");
			ps.setInt(1, admin_id);

			ps.executeUpdate();

			bddSays("delete", true, admin_id, null);

		} catch (Exception ex) {
			bddSays("delete", false, admin_id, null);
			ex.printStackTrace();
		}

	}

}
