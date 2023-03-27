package agents;

import constants.Constants;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import static constants.Constants.*;
public class AgentSupervisor extends Agent {
    Map<String, Double> timesForVisitors = new HashMap<>();

    @Override
    protected void setup() {
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                if (aclMessage != null) {
                    String message = aclMessage.getOntology();
                    String visitorName;
                    if (Objects.equals(message, getMenu)) {
                        visitorName = aclMessage.getSender().getLocalName(); // LocalName == vistor.getName() по условию
                        timesForVisitors.put(visitorName, Double.parseDouble(aclMessage.getContent()));
                        double time = timesForVisitors.get(visitorName);
                        timesForVisitors.put(visitorName, time);
                        ACLMessage msgToWarehouse = new ACLMessage(ACLMessage.INFORM);
                        msgToWarehouse.addReceiver(new AID(storageAgent, AID.ISLOCALNAME));
                        msgToWarehouse.setOntology(Constants.getProducts);
                        msgToWarehouse.setContent(new JSONObject().put("user_name", visitorName).put("time", time).toString());
                        send(msgToWarehouse);
                    } else if (Objects.equals(message, getProducts)) {
                        JSONObject messageFromWarehouse = new JSONObject(aclMessage.getContent());
                        ACLMessage msgToMenuAgent = new ACLMessage(ACLMessage.INFORM);
                        msgToMenuAgent.addReceiver(new AID(menuAgent, AID.ISLOCALNAME));
                        msgToMenuAgent.setContent(messageFromWarehouse.toString());
                        msgToMenuAgent.setOntology(Constants.getAvailableDishes);
                        send(msgToMenuAgent);
                    } else if (Objects.equals(message, sendDishes)) {
                        JSONObject response = new JSONObject(aclMessage.getContent());
                        visitorName = response.getString("user_name");
                        response.remove("user_name");
                        response.remove("time");
                        ACLMessage replyToVisitor = new ACLMessage(ACLMessage.INFORM);
                        replyToVisitor.addReceiver(new AID(visitorName, AID.ISLOCALNAME));
                        replyToVisitor.setOntology(Constants.sendDishes);
                        replyToVisitor.setContent(response.toString());
                        send(replyToVisitor);
                    } else if (Objects.equals(message, order)) {
                        JSONObject order = new JSONObject(aclMessage.getContent());
                        visitorName = order.getString("user_name");
                        System.out.println("Super-visor received order from " + visitorName + " with content " + order);
                        try {
                            AgentController orderAgentController = getContainerController().createNewAgent(orderAgent + visitorName, agents.AgentOrder.class.getName(), new Object[]{order});
                            orderAgentController.start();
                        } catch (StaleProxyException e) {
                            System.out.println("Ошибка при создании агента-заказа");
                        }
                    } else {
                        System.out.println("Error with ontology");
                    }
                }
                block();
            }
        });

    }
    private class Queries extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                String request = msg.getContent();
                JSONArray array = new JSONArray(request);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object = array.getJSONObject(i);

                }
            }
            block();
        }
    }


    private JSONObject getAvailableDishes(double needTime) {
        JSONObject menu = getAllDishesFromMenu();
        JSONArray availableDishes = new JSONArray();
        for (int i = 0; i < menu.length(); i++) {
            JSONObject dish = menu.getJSONObject(String.valueOf(i));
            double time = dish.getDouble("time");
            if (time > needTime) {
                continue;
            }
            availableDishes.put(dish);
        }
        return new JSONObject().put("dishes", availableDishes);
    }

    private JSONObject getAllDishesFromMenu() {
        return null;
    }


}