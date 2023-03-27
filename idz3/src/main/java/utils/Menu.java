package utils;

import java.util.Map;

public class Menu {
    private Map<Integer, Dish> dishes;
    public void setDishes(Map<Integer, Dish> dishes) {
        this.dishes = dishes;
    }
    public Map<Integer, Dish> getDishes() {
        return dishes;
    }
    public Menu(Map<Integer, Dish> dishes) {
        this.dishes = dishes;
    }
    @Override
    public String toString() {
        return "Menu {" +
                "menu = " + dishes +
                '}';
    }
}