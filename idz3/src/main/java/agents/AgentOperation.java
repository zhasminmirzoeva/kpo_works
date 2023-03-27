package agents;

import utils.Operation;
import jade.core.Agent;
public class AgentOperation extends Agent {
    private Operation operation;
    public void setOperation(Operation operation) {
        this.operation = operation;
    }
    public Operation getOperation() {
        return operation;
    }
    public AgentOperation() {

    }
    public AgentOperation(Operation operation) {
        this.operation = operation;
    }
    @Override
    protected void setup() {
        System.out.println("Operation Agent " + getAID().getName() + " is ready");
        System.out.println("Operation: " + operation);
    }
}
