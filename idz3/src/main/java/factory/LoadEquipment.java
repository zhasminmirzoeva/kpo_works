package factory;

import utils.Equipment;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoadEquipment implements ILoad<Equipment> {

    private String pathToEquipmentFile;
    public LoadEquipment(String pathToEquipmentFile) {
        this.pathToEquipmentFile = pathToEquipmentFile;
    }
    @Override
    public Map<Integer, Equipment> load() {
        Map<Integer, Equipment> equipments = new HashMap<>();
        JSONObject json = this.getJSON(pathToEquipmentFile);
        if (json == null) {
            return null;
        }
        for (String key : json.keySet()) {
            JSONArray array = json.getJSONArray(key);
            for (Object value : array) {
                JSONObject currentMap = (JSONObject) value;
                Equipment equipment;
                try {
                    equipment = new Equipment((Integer) currentMap.get("equip_id"),
                            (Integer) currentMap.get("equip_type"),
                            (String) currentMap.get("equip_name"),
                            (Boolean) currentMap.get("equip_active"));
                } catch (Exception e) {
                    System.out.println("Error in equipment.txt file");
                    return null;
                }
                equipments.put(equipment.getType(), equipment);
            }
        }
        return equipments;
    }
}
