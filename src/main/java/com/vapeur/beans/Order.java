package com.vapeur.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int id;
    private Date date;
    private float amount;
    private int totalQuantity;
    private int userId;
    private String name;
    private ArrayList<OrderDetail> orderDetails;

    // Constructeurs
    public Order() {
    }

    public Order(int id, Date date, float amount, int totalQuantity, int userId, String name, ArrayList<OrderDetail> orderDetails) {
        setId(id);
        setDate(date);
        setAmount(amount);
        setTotalQuantity(totalQuantity);
        setUserId(userId);
        setName(name);
        setOrderDetails(orderDetails);
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public int getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(int totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<OrderDetail> getOrderDetails() {
		return orderDetails;
	}

	public void setOrderDetails(ArrayList<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	// Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                ", userId=" + userId +
                '}';
    }
}
