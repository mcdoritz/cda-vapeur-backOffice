package com.vapeur.beans;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;

    private int gameId;
    private int orderId;
    private int quantity;
    private float unitPrice;

    // Constructeurs
    public OrderDetail() {
    }

    public OrderDetail(int gameId, int orderId, int quantity, float unitPrice) {
        setGameId(gameId);
        setOrderId(orderId);
        setQuantity(quantity);
        setUnitPrice(unitPrice);
    }

    // Getters et setters
    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(float unitPrice) {
        this.unitPrice = unitPrice;
    }

    // Méthode toString pour l'affichage
    @Override
    public String toString() {
        return "OrderDetail{" +
                "gameId=" + gameId +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
