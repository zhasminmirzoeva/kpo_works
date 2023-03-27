package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.Map;

public class OperationPro {
    private int type;
    private int equipType;
    private double time;
    private int asyncPoint;
    private List<Map<String, Number>> productsId;
    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
    public void getEquipType(int equipType) {
        this.equipType = equipType;
    }
    public int getEquipType() {
        return equipType;
    }
    public void setTime(double time) {
        this.time = time;
    }
    public double getTime() {
        return time;
    }
    public void setAsyncPoint(int asyncPoint) {
        this.asyncPoint = asyncPoint;
    }
    public int getAsyncPoint() {
        return asyncPoint;
    }
    public void setProductsId(List<Map<String, Number>> productsId) {
        this.productsId = productsId;
    }
    public List<Map<String, Number>> getProductsId() {
        return productsId;
    }
    public OperationPro() {

    }
    public OperationPro(int type, int equipType, double time, int asyncPoint) {
        this.type = type;
        this.equipType =equipType;
        this.time = time;
        this.asyncPoint = asyncPoint;
    }


    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("oper_type", type);
        jsonObject.put("equip_type", equipType);
        jsonObject.put("oper_time", time);
        jsonObject.put("oper_async_point", asyncPoint);
        jsonObject.put("oper_products", new JSONArray());
        for (Map<String, Number> product : productsId) {
            JSONObject productJson = new JSONObject();
            productJson.put("prod_type", product.get("prod_type"));
            productJson.put("prod_quiantity", product.get("prod_quiantity"));
            jsonObject.append("oper_products", productJson);
        }
        return jsonObject;
    }
}
