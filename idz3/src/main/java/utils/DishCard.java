package utils;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.List;
import java.util.Objects;

public class DishCard {
    private int id;
    private String dishName;
    private String description;
    private double time;
    private List<OperationPro> operationPros;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setDishName(int id) {
        this.dishName = dishName;
    }
    public String getDIshName() {
        return dishName;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String  getDescription() {
        return description;
    }
    public void setTime(double time) {
        this.time = time;
    }
    public double getTime() {
        return time;
    }
    public void setSuperOperations(List<OperationPro> operationPros) {
        this.operationPros = operationPros;
    }
    public List<OperationPro> getSuperOperations() {
        return operationPros;
    }
    public DishCard() {

    }
    public DishCard(int id, String dishName, String description, double time) {
        this.id = id;
        this.dishName = dishName;
        this.description = description;
        this.time = time;
    }
    @Override
    public String toString() {
        return "DishCard {" +
                "id = " + id +
                ", name =  " + dishName + '\'' +
                ", description = " + description + '\'' +
                ", time = " + time +
                ", superOperation=" + operationPros +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DishCard dishCard = (DishCard) o;

        if (id != dishCard.id) return false;
        if (Double.compare(dishCard.time, time) != 0) return false;
        if (!Objects.equals(dishName, dishCard.dishName)) return false;
        if (!Objects.equals(description, dishCard.description)) return false;
        return Objects.equals(operationPros, dishCard.operationPros);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (dishName != null ? dishName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(time);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (operationPros != null ? operationPros.hashCode() : 0);
        return result;
    }

    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("card_id", id);
        jsonObject.put("dish_name", dishName);
        jsonObject.put("card_descr", description);
        jsonObject.put("card_time", time);
        jsonObject.put("operations", operationPros);
        JSONArray jsonArray = new JSONArray();
        for (OperationPro operationPro : operationPros) {
            jsonArray.put(operationPro.toJson());
        }
        return jsonObject;
    }

}