package utils;


import org.json.JSONObject;

public class Product {
    private int id;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    private int type;
    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    private String company;
    public void setCompany(String company) {
        this.company = company;
    }
    public String getCompany() {
        return company;
    }
    private String unit;
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getUnit() {
        return unit;
    }
    private double quantity;
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public double getQuantity() {
        return quantity;
    }
    private double cost;
    public void setCost(double cost) {
        this.cost = cost;
    }
    public double getCost() {
        return cost;
    }
    private String delivered;
    public void setDelivered(String delivered) {
        this.delivered = delivered;
    }
    public String getDelivered() {
        return delivered;
    }
    private String validUntil;
    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }
    public String  getValidUntil() {
        return validUntil;
    }
    public Product() {

    }
    public Product(int id, int type, String name, String company, String unit,
                   double quantity, double cost, String delivered, String validUntil) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.company = company;
        this.unit = unit;
        this.quantity = quantity;
        this.cost = cost;
        this.delivered = delivered;
        this.validUntil = validUntil;
    }



    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put("prod_item_id", id);
        json.put("prod_item_type", type);
        json.put("prod_item_name", name);
        json.put("prod_item_company", company);
        json.put("prod_item_unit", unit);
        json.put("prod_item_quantity", quantity);
        json.put("prod_item_cost", cost);
        json.put("prod_item_delivered", delivered);
        json.put("prod_item_valid_until", validUntil);
        return json;
    }
}