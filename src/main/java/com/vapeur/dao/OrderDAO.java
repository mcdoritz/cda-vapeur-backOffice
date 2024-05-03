package com.vapeur.dao;

import static com.vapeur.config.Debug.bddSays;
import static com.vapeur.config.Debug.prln;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.vapeur.beans.Order;
import com.vapeur.beans.OrderDetail;
import com.vapeur.beans.User;
import com.vapeur.config.Database;

public class OrderDAO {

    public int save(Order object) throws DAOException {
        try {
            if (object.getId() != 0) {
                String query = "UPDATE orders SET date = ?, user_id = ? WHERE id = ?";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query)) {
                    ps.setDate(1, (Date) object.getDate());
                    ps.setInt(2, object.getUserId());
                    ps.setInt(3, object.getId());

                    ps.executeUpdate();
                }
                String objectInfos = "Order ID: " + object.getId();
                bddSays("update", true, object.getId(), objectInfos);
                return object.getId();
            } else {
                String query = "INSERT INTO orders (date, user_id) VALUES (?, ?)";
                
                try (PreparedStatement ps = Database.connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                	Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    ps.setTimestamp(1, timestamp);
                    ps.setInt(2, object.getUserId());

                    ps.executeUpdate();
                    
                    try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                        	int id = generatedKeys.getInt(1);
                            String objectInfos = "Order ID: " + id;
                            bddSays("create", true, id, objectInfos);
                            return id;
                        } else {
                            bddSays("create", false, object.getId(), null);
                            throw new DAOException("Erreur avec la BDD, contactez le service client.");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new DAOException("Erreur avec la bdd");
        }
		
    }

    public Order getById(int order_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT * FROM orders WHERE id = ?");
            ps.setInt(1, order_id);

            ResultSet resultat = ps.executeQuery();

            Order object = new Order();
            
            while (resultat.next()) {
                object.setId(resultat.getInt("id"));
                object.setDate(resultat.getDate("date"));
                object.setUserId(resultat.getInt("user_id"));
            }
            
            String objectInfos = "Order ID: " + object.getId();
            bddSays("read", true, object.getId(), objectInfos);
            return object;

        } catch (Exception ex) {
            ex.printStackTrace();
            bddSays("read", false, order_id, null);
            return null;
        }
    }
    
    public List<Order> readAll() {
        ArrayList<Order> ordersList = new ArrayList<>();
        String query = "SELECT orders.id, orders.date, orders.user_id, SUM(order_details.quantity) AS quantity, ROUND(SUM(order_details.unit_price*order_details.quantity),2) AS amount FROM orders JOIN order_details ON orders.id = order_details.order_id GROUP BY orders.id ORDER BY orders.date DESC";
        try {
            PreparedStatement ps = Database.connexion.prepareStatement(query);
            ResultSet resultat = ps.executeQuery();
            
            UserDAO userdao = new UserDAO();

            while (resultat.next()) {
                Order object = new Order();
                
                object.setId(resultat.getInt("id"));
                object.setDate(resultat.getDate("date"));
                object.setUserId(resultat.getInt("user_id"));
                //Utilisation de name pour afficher le nom du user
                object.setName(userdao.getById(resultat.getInt("user_id")).getNickname());
                object.setTotalQuantity(resultat.getInt("quantity"));
                object.setAmount(resultat.getFloat("amount"));

                ordersList.add(object);
            }
            bddSays("readAll", true, ordersList.size(), null);
            return ordersList;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            return null;
        }
    }
    
	public List<Order> readAllByUserId(int user_id) throws DAOException {
        ArrayList<Order> ordersList = new ArrayList<>();      
        
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT orders.id AS order_id, orders.date AS order_date, SUM(order_details.quantity) AS totalQuantity, SUM(order_details.unit_price) AS amount, games.id AS game_id, GROUP_CONCAT(games.title SEPARATOR ', ') AS name FROM order_details JOIN orders ON order_details.order_id = orders.id JOIN games ON order_details.game_id = games.id WHERE order_details.order_id IN (SELECT id FROM orders WHERE user_id = ?) GROUP BY orders.date ORDER BY orders.id");
            ps.setInt(1, user_id);
            ResultSet resultat = ps.executeQuery();
            
            DecimalFormat decimals = new DecimalFormat("#.##");
            OrderDetailDAO orderdetailsdao = new OrderDetailDAO();

            while (resultat.next()) {
                Order object = new Order();
                
                if(resultat.getInt("order_id") != 0) {
                	int order_id = resultat.getInt("order_id");
                	object.setId(order_id);
                    object.setDate(resultat.getDate("order_date"));
                    
                    ArrayList<OrderDetail> orderDetails = new ArrayList<>();
                    
                    orderDetails = orderdetailsdao.getByOrderId(order_id);
                    Float amount = (float) 0;
                    int totalQuantity = 0;
                    
                    for(OrderDetail od:orderDetails) {
                    	amount += od.getUnitPrice() * od.getQuantity();
                    	totalQuantity += od.getQuantity();
                    }
                    
                    
                    object.setAmount(amount);
                    object.setTotalQuantity(totalQuantity);
                    object.setUserId(user_id);
                    
                    String gamesNames = resultat.getString("name");
                    if(gamesNames != null) {
                    	int gamesNameLength = gamesNames.length() > 50 ? 50 : gamesNames.length();
                    	String dottts = gamesNameLength >= 50 ? " ..." : "";
                    	prln(gamesNames.substring(0, gamesNameLength)+ dottts);
                        object.setName(gamesNames.substring(0, gamesNameLength)+ dottts);

                    }
                    
                    ordersList.add(object);
                    for(Order o:ordersList) {
                    	prln(o.getAmount() + " € x " + o.getTotalQuantity());
                    }
                    bddSays("readAll", true, ordersList.size(), null);
                }else {
                	prln("Orderlist null");
                	ordersList = null;
                }
            }
            prln("Orderlist size : " + ordersList.size());
            return ordersList;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            throw new DAOException("Erreur avec la BDD dans la récupération des commandes");
        }
    }
	
	public List<Order> readAllByOrderId(int order_id) throws DAOException {
        ArrayList<Order> ordersList = new ArrayList<>();      
        
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT DISTINCT orders.id AS order_id, orders.date AS order_date, order_details.quantity AS quantity, order_details.unit_price AS amount, games.id AS game_id, games.title AS title FROM order_details JOIN orders ON order_details.order_id = orders.id JOIN games ON order_details.game_id = games.id WHERE order_details.order_id IN (SELECT id FROM orders WHERE order_id = ?) ORDER BY game_id");
            ps.setInt(1, order_id);
            ResultSet resultat = ps.executeQuery();
            
            DecimalFormat decimals = new DecimalFormat("#.##");
            OrderDetailDAO orderdetailsdao = new OrderDetailDAO();

            while (resultat.next()) {
                Order object = new Order();
                
                object.setId(order_id);
                object.setDate(resultat.getDate("order_date"));
                object.setAmount(resultat.getFloat("amount"));
                object.setTotalQuantity(resultat.getInt("quantity"));
                object.setName(resultat.getString("title"));
                object.setGameId(resultat.getInt("game_id"));
                
                ordersList.add(object);
            }
            if(ordersList.size() > 0) {
            	return ordersList;
            }else {
            	return null;
            }
            
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            throw new DAOException("Erreur avec la BDD dans la récupération des commandes");
        }
    }
	
	public int countOrdersByUserId(int user_id) throws DAOException {
    
        
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT COUNT(*) AS count FROM orders WHERE user_id = ?");
            ps.setInt(1, user_id);
            ResultSet resultat = ps.executeQuery();
            
            int count = 0;

            while (resultat.next()) {
                count = resultat.getInt("count");
            }
            return count;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            throw new DAOException("Erreur avec la BDD dans la récupération des commandes");
        }
    }
	
	public float countAmountByUserId(int user_id) throws DAOException {

        try {
            PreparedStatement ps = Database.connexion.prepareStatement("SELECT quantity, unit_price FROM order_details JOIN orders ON order_details.order_id = orders.id WHERE orders.user_id = ?");
            ps.setInt(1, user_id);
            ResultSet resultat = ps.executeQuery();
            
            float amount = 0;

            while (resultat.next()) {
                amount += resultat.getInt("quantity") * resultat.getFloat("unit_price");
            }
            return amount;
        } catch (Exception ex) {
            bddSays("readAll", false, 0, null);
            ex.printStackTrace();
            throw new DAOException("Erreur avec la BDD dans la récupération des commandes");
        }
    }

    public void delete(int order_id) {
        try {
            PreparedStatement ps = Database.connexion.prepareStatement("DELETE FROM orders WHERE id = ?");
            ps.setInt(1, order_id);

            ps.executeUpdate();

            bddSays("delete", true, order_id, null);

        } catch (Exception ex) {
            bddSays("delete", false, order_id, null);
            ex.printStackTrace();
        }
    }
}
