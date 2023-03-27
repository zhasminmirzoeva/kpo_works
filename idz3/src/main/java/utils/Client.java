package utils;

import java.util.List;
public class Client {
    private int id;
    private String name;
    private String orderStarted;
    private String orderEnded;
    private int orderTotal;
    private List<DishPro> orderDishes;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setOrderStarted(String  orderStarted) {
        this.orderStarted = orderStarted;
    }
    public String getOrderStarted() {
        return orderStarted;
    }
    public void setOrderEnded(String orderEnded) {
        this.orderEnded = orderEnded;
    }
    public String getOrderEnded() {
        return orderEnded;
    }
    public void setOrderTotal(int orderTotal) {
        this.orderTotal = orderTotal;
    }
    public int getOrderTotal() {
        return orderTotal;
    }
    public void setOrderDishes(List<DishPro> orderDishes) {
        this.orderDishes = orderDishes;
    }
    public List<DishPro> getOrderDishes() {
        return orderDishes;
    }
    public Client(){

    }
    public Client(int id, String name, String orderStarted, String orderEnded, int orderTotal) {
        this.id = id;
        this.name = name;
        this.orderStarted = orderStarted;
        this.orderEnded = orderEnded;
        this.orderTotal = orderTotal;
    }
}