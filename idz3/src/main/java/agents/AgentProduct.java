package com.kpo.agents;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentProduct extends Agent {
    double quantity;
    @Override
    protected void setup() {
        System.out.println("Product agent " + getAID().getName() + " is ready");
        quantity = getArguments()[0] != null ? Double.parseDouble(getArguments()[0].toString()) : 0;

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage receive = receive();
                if (receive == null) {
                    block();
                }
            }
        });
    }
}