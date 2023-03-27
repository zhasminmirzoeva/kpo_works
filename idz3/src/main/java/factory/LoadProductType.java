package factory;

import utils.ProductType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
public class LoadProductType implements ILoad<ProductType> {
    private String pathToProductFile;
    public LoadProductType(String pathToProductFile) {
        this.pathToProductFile = pathToProductFile;
    }

    @Override
    public Map<Integer, ProductType> load() {
        Map<Integer, ProductType> dishes = new HashMap<>();
        JSONObject json = this.getJSON(pathToProductFile);
        if (json == null) {
            return null;
        }
        for (String key : json.keySet()) {
            JSONArray array = json.getJSONArray(key);
            for (Object value : array) {
                JSONObject currentMap = (JSONObject) value;
                ProductType dish;
                try {
                    /*dish = ProductType.builder()
                            .id((Integer) currentMap.get("prod_type_id"))
                            .name((String) currentMap.get("prod_type_name"))
                            .isFood((Boolean) currentMap.get("prod_is_food"))
                            .build();*/
                    dish = new ProductType((Integer) currentMap.get("prod_type_id"),
                            (String) currentMap.get("prod_type_name"),
                            (Boolean) currentMap.get("prod_is_food"));
                } catch (Exception e) {
                    System.out.println("Error in product_types.txt file");
                    return null;
                }
                dishes.put(dish.getId(), dish);
            }
        }
        return dishes;
    }
}
