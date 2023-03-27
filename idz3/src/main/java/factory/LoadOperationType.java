package factory;

import utils.OperationType;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
public class LoadOperationType implements ILoad<OperationType> {

    private String pathToOperationFile;
    public LoadOperationType(String pathToOperationFile) {
        this.pathToOperationFile = pathToOperationFile;
    }
    @Override
    public Map<Integer, OperationType> load() {
        Map<Integer, OperationType> operationTypes = new HashMap<>();
        JSONObject json = this.getJSON(pathToOperationFile);
        if (json == null) {
            return null;
        }
        for (String key : json.keySet()) {
            JSONArray array = json.getJSONArray(key);
            for (Object value : array) {
                JSONObject currentMap = (JSONObject) value;
                OperationType operationType;
                try {
                   /* operationType = OperationType.builder()
                            .id((Integer) currentMap.get("oper_type_id"))
                            .name((String) currentMap.get("oper_type_name"))
                            .build();*/
                    operationType = new OperationType((Integer) currentMap.get("oper_type_id"),
                            (String) currentMap.get("oper_type_name"));
                } catch (Exception e) {
                    System.out.println("Error in operation_types.txt file");
                    return null;
                }
                operationTypes.put(operationType.getId(), operationType);
            }
        }
        return operationTypes;
    }
}
