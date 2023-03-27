package agents;

import utils.Equipment;
import jade.core.Agent;

public class AgentEquipment extends Agent {
    private Equipment equipment;
    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Equipment getEquipment() {
        return equipment;
    }
    public AgentEquipment() {

    }
    public AgentEquipment(Equipment equipment) {
        this.equipment = equipment;
    }
}
