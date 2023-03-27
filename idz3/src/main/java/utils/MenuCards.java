package utils;

import java.util.Map;
public class MenuCards {
    Map<Integer, DishCard> menuCards;
    public void setMenuCards(Map<Integer, DishCard> menuCards) {
        this.menuCards = menuCards;
    }
    public Map<Integer, DishCard> getMenuCards() {
        return menuCards;
    }
    public MenuCards(Map<Integer, DishCard> menuCards) {
        this.menuCards = menuCards;
    }
    @Override
    public String toString() {
        return "MenuCards {" +
                " dishCard = " + menuCards +
                '}';
    }
}

