package utils;

import lombok.*;
import org.json.JSONObject;

public class Operation {
    private int id;
    private int process;
    private int card;
    private String started;
    private String ended;
    private int equipId;
    private int cookerId;
    private boolean isActive;
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setProcess(int process) {
        this.process = process;
    }
    public int getProcess() {
        return process;
    }
    public void setCard(int card) {
        this.card = card;
    }
    public int getCard() {
        return card;
    }
    public void setStarted(String started) {
        this.started = started;
    }
    public String getStarted(String started) {
        return started;
    }
    public void setEnded(String ended) {
        this.ended = ended;
    }
    public String getEnded() {
        return ended;
    }
    public void setEquipId(int equipId) {
        this.equipId = equipId;
    }
    public int getEquipId() {
        return equipId;
    }
    public void setCookerId(int cookerId) {
        this.cookerId = cookerId;
    }
    public int getCookerId() {
        return cookerId;
    }
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    public boolean getIsActive() {
        return isActive;
    }
    public Operation() {

    }
    public Operation(int id, int process, int card, String started, String ended, int equipId, int cookerId, boolean isActive) {
        this.id = id;
        this.process = process;
        this.card = card;
        this.started = started;
        this.ended = ended;
        this.equipId = equipId;
        this.cookerId = cookerId;
        this.started = started;
        this.ended = ended;
        this.isActive = isActive;
    }

    @SneakyThrows
    public String toString() {
        return "{\n" +
                "\"proc_id\": " + id +
                ",\n\"ord_dish\": " + card +
                ",\n\"proc_started\": \"" + started + "\"" +
                "\"\n,\"proc_ended\": \"" + ended + "\"" +
                ",\n\"proc_active\"=" + isActive +
                ",\nprocessOperations=" + process + '}';
    }

    public JSONObject toJson() {
        return new JSONObject()
                .put("oper_id", id)
                .put("oper_proc", process)
                .put("oper_card", card)
                .put("oper_started", started)
                .put("oper_ended", ended)
                .put("oper_equip_id", equipId) // нововведения в json
                .put("oper_coocker_id", cookerId)
                .put("oper_active", isActive);
    }
}
