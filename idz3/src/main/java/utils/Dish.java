package utils;

import org.json.JSONObject;

public class Dish {
    private int id;
    private double price;
    private boolean isActive;
    private int cardId;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public double getPrice() {
        return price;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public void cardId(int cardId) {
        this.cardId = cardId;
    }
    public int getCardId() {
        return cardId;
    }
    public Dish() {

    }
    public Dish(int id, double price, boolean isActive, int cardId) {
        this.id = id;
        this.price = price;
        this.isActive = isActive;
        this.cardId = cardId;
    }
    @Override
    public String toString() {
        return "Dish {" +
                "id = " + id +
                ", recipe_id = " + cardId +
                ", price = " + price +
                ", isAvailable = " + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish menuItem = (Dish) o;
        if (id != menuItem.id) return false;
        if (cardId != menuItem.cardId) return false;
        if (price != menuItem.price) return false;
        return isActive == menuItem.isActive;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + cardId;
        result = (int) (31 * result + price);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
    public JSONObject toJSON() {
        return new JSONObject()
                .put("menu_dish_id", id)
                .put("menu_dish_card", cardId)
                .put("menu_dish_price", price)
                .put("menu_dish_active", isActive);
    }
}
