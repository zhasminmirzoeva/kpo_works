package utils;

import java.util.Objects;

public class ProductType {
    private int id;
    private String name;
    private boolean isFood;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setIsFood(boolean isFood) {
        this.isFood = isFood;
    }
    public ProductType() {

    }
    public ProductType(int id, String name, boolean isFood) {
        this.id = id;
        this.name = name;
        this.isFood = isFood;
    }
    @Override
    public String toString() {
        return "ProductType { " +
                " id = " + id +
                ", name = '" + name + '\'' +
                ", is_food = " + isFood +
                '}' + '\'';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductType that = (ProductType) o;

        if (id != that.id) return false;
        if (isFood != that.isFood) return false;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isFood ? 1 : 0);
        return result;
    }

}

