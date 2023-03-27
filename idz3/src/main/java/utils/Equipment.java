package utils;

import java.util.Objects;

public class Equipment {
    private int id;
    private int type;
    private String name;
    private boolean isActive;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public Equipment() {

    }
    public Equipment(int id, int type, String name, boolean isActive) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.isActive = isActive;
    }
    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", equipmentType_id=" + type +
                ", name='" + name + '\'' +
                ", active=" + isActive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Equipment equipment = (Equipment) o;

        if (id != equipment.id) return false;
        if (type != equipment.type) return false;
        if (isActive != equipment.isActive) return false;
        return Objects.equals(name, equipment.name);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + type;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (isActive ? 1 : 0);
        return result;
    }

}
