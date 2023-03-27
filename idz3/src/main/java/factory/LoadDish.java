package factory;

import utils.Dish;
import lombok.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
public class LoadDish implements ILoad<Dish> {
    private String pathToMenuFile;
    public LoadDish(String pathToMenuFile) {
        this.pathToMenuFile = pathToMenuFile;
    }

    @Override
    public Map<Integer, Dish> load() {
        Map<Integer, Dish> dishes = new HashMap<>();
        JSONObject json = this.getJSON(pathToMenuFile);
        if (json == null) {
            return null;
        }
        for (String key : json.keySet()) {
            JSONArray array = json.getJSONArray(key);
            for (Object value : array) {
                JSONObject currentMap = (JSONObject) value;
                if (currentMap.keySet().size() != 4) {
                    System.out.println("Error in menu_dishes.txt file");
                    return null;
                }
                Dish dish;
                try {
                    dish = new Dish((Integer) currentMap.get("menu_dish_id"), Double.parseDouble(currentMap.get("menu_dish_price").
                            toString()),(Boolean) currentMap.get("menu_dish_active"),
                            (Integer) currentMap.get("menu_dish_card"));
                } catch (Exception e) {
                    System.out.println("Error in menu_dishes.txt file");
                    return null;
                }
                dishes.put(dish.getId(), dish);
            }
        }
        return dishes;
    }
}
