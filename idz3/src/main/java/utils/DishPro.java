package utils;

public class DishPro {
    private int orderDishId;
    private int menuDishId;
    public void setOrderDishId(int orderDishId) {
        this.orderDishId = orderDishId;
    }
    public int getOrderDishId() {
        return orderDishId;
    }
    public void setMenuDishId(int menuDishId) {
        this.menuDishId = menuDishId;
    }
    public int getMenuDishId() {
        return menuDishId;
    }
    public DishPro() {

    }
    public DishPro(int orderDishId, int menuDishId) {
        this.orderDishId = orderDishId;
        this.menuDishId = menuDishId;
    }
}



