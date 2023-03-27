package factory;

import utils.EquipmentType;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoadEquipmentType implements ILoad<EquipmentType> {
    private String pathToEquipmentTypesFile;
    public LoadEquipmentType(String pathToEquipmentTypesFile) {
        this.pathToEquipmentTypesFile = pathToEquipmentTypesFile;
    }

    @Override
    public Map<Integer, EquipmentType> load() {
        Map<Integer, EquipmentType> equipmentTypes = new HashMap<>();
        JSONObject json = this.getJSON(pathToEquipmentTypesFile);
        if (json == null) {
            return null;
        }
        for (String key : json.keySet()) {
            JSONArray array = json.getJSONArray(key);
            for (Object value : array) {
                JSONObject currentMap = (JSONObject) value;
                EquipmentType equipmentType;
                try {
                    /*equipmentType = EquipmentType.builder()
                            .id((Integer) currentMap.get("equip_type_id"))
                            .name((String) currentMap.get("equip_type_name"))
                            .build();*/
                    equipmentType = new EquipmentType((Integer) currentMap.get("equip_type_id"),
                            (String) currentMap.get("equip_type_name"));
                } catch (Exception e) {
                    System.out.println("Error in equipment_types.txt file");
                    return null;
                }
                equipmentTypes.put(equipmentType.getId(), equipmentType);
            }
        }
        return equipmentTypes;
    }
}
