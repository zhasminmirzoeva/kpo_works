package factory;

import utils.Client;
import utils.DishPro;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadClient implements factory.ILoad<Client> {
    private String pathToVisitorsFile;
    public LoadClient(String pathToVisitorsFile) {
        this.pathToVisitorsFile = pathToVisitorsFile;
    }

    @Override
    public Map<Integer, Client> load() {
        int newUserID = 0;
        Map<Integer, Client> visitors = new HashMap<>();
        JSONObject json = this.getJSON(pathToVisitorsFile);
        if (json == null) {
            return null;
        }
        for (String key : json.keySet()) {
            // Проходимся по всем ключам("главные" ключи) в самом json(на данный момент это только dish_cards)
            JSONArray array = json.getJSONArray(key);
            for (Object value : array) {
                // Проходимся повсем значениям "главных" ключей
                JSONObject currentMap = (JSONObject) value;
                // Создаем карточку посетителя
                Client client;
                try {
                    client = new Client(newUserID++, (String) currentMap.get("vis_name"),
                            (String) currentMap.get("vis_ord_started"),
                            (String) currentMap.get("vis_ord_ended"),
                            (Integer) currentMap.get("vis_ord_total"));
                } catch (Exception e) {
                    System.out.println("Error in visitors.txt file");
                    return null;
                }

                List<DishPro> dishPros = new ArrayList<>();
                // У визитора есть список "суперблюд" - сложная фигня
                JSONArray dishesArray = currentMap.getJSONArray("vis_ord_dishes");
                for (Object dish : dishesArray) {
                    // Проходимся по всем свойствам суперблюд
                    try {
                        JSONObject superDishJSON = (JSONObject) dish;
                        DishPro dishPro = new DishPro((Integer) superDishJSON.get("ord_dish_id"),
                                (Integer) superDishJSON.get("menu_dish"));
                        dishPros.add(dishPro);  // Добавляем супероперацию в список суперопераций
                    } catch (Exception e) {
                        // Ошибка при чтении
                    }
                }
                client.setOrderDishes(dishPros);

                visitors.put(client.getId(), client);
            }
        }
        return visitors;
    }
}
