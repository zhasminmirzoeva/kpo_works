package agents;

import utils.Cooker;
import jade.core.Agent;
public class AgentCooker extends Agent {
    private Cooker cooker;
    public void setCooker(Cooker cooker) {
        this.cooker = cooker;
    }
    public Cooker getCooker() {
        return cooker;
    }
    public AgentCooker() {

    }
    public AgentCooker(Cooker cooker) {
        this.cooker = cooker;
    }

}