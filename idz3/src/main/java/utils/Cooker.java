package utils;

import java.util.Objects;
public class Cooker {
    private int id;
    private String name;
    private boolean isActive;
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
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public Cooker() {

    }
    public Cooker(int id, String name, boolean isActive) {
        this.id = id;
        this.name = name;
        this.isActive = isActive;
    }
    @Override
    public String toString() {
        return "Cooker {" +
                " id = " + id +
                ", name = " + name  +
                ", isActive = " + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cooker cook = (Cooker) o;

        if (id != cook.id) return false;
        if (isActive != cook.isActive) return false;
        return Objects.equals(name, cook.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }
}