package factory;

import utils.DishCard;
import utils.OperationPro;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class LoadCard implements ILoad<DishCard> {
    private String pathToCardsFile;

    public LoadCard(String pathToCardsFile) {
        this.pathToCardsFile = pathToCardsFile;
    }
    @Override
    public Map<Integer, DishCard> load() {
        Map<Integer, DishCard> cards = new HashMap<>();
        JSONObject json = this.getJSON(pathToCardsFile);
        if (json == null) {
            return null;
        }
        for (String key : json.keySet()) {
            // Проходимся по всем ключам("главные" ключи) в самом json(на данный момент это только dish_cards)
            JSONArray array = json.getJSONArray(key);
            for (Object value : array) {
                // Проходимся повсем значениям "главных" ключей
                JSONObject currentMap = (JSONObject) value;
                // Создаем карточку
                DishCard dishCard;
                try {
                    dishCard = new DishCard((Integer) currentMap.get("card_id"),
                            (String) currentMap.get("dish_name"),
                            (String) currentMap.get("card_descr"),
                            Double.parseDouble(currentMap.get("card_time").toString()));
                } catch (Exception e) {
                    System.out.println("Error in dish_cards.txt file");
                    return null;
                }
                List<OperationPro> operationPros = new ArrayList<>();
                // У карточки есть "супероперации" - сложная фигня
                JSONArray operationsArray = currentMap.getJSONArray("operations");
                for (Object operation : operationsArray) {
                    // Проходимся по всем свойствам суперопераций
                    try {
                        JSONObject superOperationJSON = (JSONObject) operation;
                        OperationPro operationPro = new OperationPro((Integer) superOperationJSON.get("oper_type"),
                                (Integer) superOperationJSON.get("equip_type"),
                                Double.parseDouble(superOperationJSON.get("oper_time").toString()),
                                (Integer) superOperationJSON.get("oper_async_point"));

                        // У "супероперации" есть продукты, с которыми она работает. Хранит в себе пока id этих продуктов и их количество
                        List<Map<String, Number>> operProducts = new ArrayList<>();
                        JSONArray productsArray = superOperationJSON.getJSONArray("oper_products");
                        for (Object product : productsArray) {

                            // Проходимся по всем продуктам, которые задействованы в супероперации
                            JSONObject currentProduct = (JSONObject) product;
                            Map<String, Number> productMap = new HashMap<>();
                            productMap.put("prod_type", (Number) currentProduct.get("prod_type"));
                            productMap.put("prod_quantity", (Number) currentProduct.get("prod_quantity"));
                            operProducts.add(productMap); // Добавляем продукт в список продуктов
                        }
                        operationPro.setProductsId(operProducts);  // Добавляем список продуктов в супероперацию
                        operationPros.add(operationPro);  // Добавляем супероперацию в список суперопераций
                    } catch (Exception e) {

                        System.out.println("Error in dish_cards.txt file");
                        //System.exit(1);
                    }
                }
                dishCard.setSuperOperations(operationPros);

                cards.put(dishCard.getId(), dishCard);
            }
        }
        return cards;

        //return cards;
    }
}
