package agents;

import constants.Constants;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import org.json.JSONArray;
import org.json.JSONObject;
import static constants.Constants.*;

public class AgentOrder extends Agent {
    private JSONObject query;
    private JSONObject reservedProducts;

    @Override
    protected void setup() {
        System.out.println("OrderAgent " + getAID().getName() + " is ready");
        if (getArguments()[0] != null) {
            query = new JSONObject(getArguments()[0].toString());
        } else {
            new JSONObject();
        }
        reservedProducts = new JSONObject();
        System.out.println("Query: " + query);
        JSONObject dishes;
        if (query.get("dishes") != null) {
            dishes = (JSONObject) query.get("dishes");
        } else {
            dishes = null;
        }
        if (dishes == null) {
            takeDown();
        }
        JSONObject order = new JSONObject();
        order.put("dishes", dishes);
        order.put("user_name", query.get("user_name"));
        System.out.println("Dishes: " + order);
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                if (order == null) {
                    takeDown();
                }
                ACLMessage aclMessage = new ACLMessage(ACLMessage.INFORM);
                aclMessage.addReceiver(new AID(storageAgent, AID.ISLOCALNAME));
                aclMessage.setOntology(reserveProducts);
                aclMessage.setContent(order.toString());
                send(aclMessage);
            }
        });

        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage queryToMenu = new ACLMessage(ACLMessage.INFORM);
                queryToMenu.addReceiver(new AID(menuAgent, AID.ISLOCALNAME));
                queryToMenu.setOntology(getMenuCards);
                queryToMenu.setContent(order.toString());
                send(queryToMenu);
            }
        });

        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage response = receive();
                if (response != null) {
                    String ontology = response.getOntology();
                    String content = response.getContent();
                    if (time.equals(ontology)) {
                    } else if (getMenuCards.equals(ontology)) {
                        JSONArray cardsFromMenu = new JSONArray(content);
                        System.out.println("CARDS from menu: " + cardsFromMenu);
                        for (int i = 0; i < cardsFromMenu.length(); i++) {
                            JSONObject card = cardsFromMenu.getJSONObject(i);
                            System.out.println("DishCard: " + card);
                        }
                    } else if ((Constants.reserveProducts + this.getAgent().getLocalName()).equals(ontology)) {
                        reservedProducts = new JSONObject(content);
                        System.out.println("Reserved products: " + reservedProducts);
                    }
                }
                block();
            }
        });
    }

    @Override
    protected void takeDown() {
        System.out.println("Agent order " + getAID().getName() + " is terminating");
    }
}
