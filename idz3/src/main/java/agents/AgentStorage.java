package agents;

import constants.Constants;
import utils.Product;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AgentStorage extends Agent {
    private Map<Integer, Product> products = new HashMap<>();
    private Map<Integer, Integer> connectedIdWithType = new HashMap<>();

    @Override
    protected void setup() {
        System.out.println("Hello! Storage agent " + getAID().getName() + " is ready");
        products = (Map<Integer, Product>) getArguments()[0];
        connectedIdWithType = (Map<Integer, Integer>) getArguments()[1];
        JSONObject allProducts = new JSONObject();
        JSONObject availableProducts = new JSONObject();
        for (Map.Entry<Integer, Product> entry : products.entrySet()) {
            JSONObject entryValue = products.get(entry.getKey()).toJSON();
            availableProducts.put(entryValue.get("prod_item_type").toString(), entryValue);
        }
        allProducts.put("products", availableProducts);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg = receive();
                if (msg != null) {
                    String title = msg.getContent();
                    String ontology = msg.getOntology();
                    ACLMessage reply = msg.createReply();
                    if (Constants.getProducts.equals(ontology)) {
                        JSONObject jsonObject = new JSONObject(title);
                        jsonObject.put("products", availableProducts);
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setOntology(Constants.getProducts);
                        reply.setContent(jsonObject.toString());
                        myAgent.send(reply);
                    } else if (Constants.prepareProducts.equals(title)) {
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setContent("not-available");
                        myAgent.send(reply);
                    } else if (Constants.reserveProducts.equals(ontology)) {
                        JSONObject response = new JSONObject(msg.getContent());
                        System.out.println("WARE Response: " + response);
                        JSONObject dishes = (JSONObject) response.get("dishes");
                        JSONObject resources = new JSONObject();
                        for (String dishesId : dishes.keySet()) {
                            JSONObject products = dishes.getJSONObject(dishesId).getJSONObject("products");
                            int amount = dishes.getJSONObject(dishesId).getInt("amount");
                            for (String productId : products.keySet()) {
                                double quantity = products.getDouble(productId) * amount;
                                if (resources.has(productId)) {
                                    resources.put(productId, resources.getDouble(productId) + quantity);
                                } else {
                                    resources.put(productId, quantity);
                                }
                            }
                        }
                        System.out.println("Resources: " + resources);
                        JSONObject reservedProductsId = new JSONObject();
                        reservedProductsId.put("reservedProductsId", new JSONArray());
                        for (String keyId : resources.keySet()) {
                            try {
                                double quantity = (double) resources.get(keyId);
                                System.out.println("product in cycle: " + products);
                                int productId = Integer.parseInt(keyId);
                                System.out.println("productId: " + productId);
                                System.out.println("connectedIdWithType: " + connectedIdWithType);
                                int productTypeId = connectedIdWithType.get(productId);
                                System.out.println("keyIntId: " + productId + ", productTypeId: " + productTypeId);
                                String agent = msg.getSender().getLocalName() + "_" + keyId + "_" + productTypeId;
                                products.get(productTypeId).setQuantity(products.get(productTypeId).getQuantity() - quantity);
                                reservedProductsId.getJSONArray("reservedProductsId").put(agent);
                                AgentController orderAgentController = getContainerController().createNewAgent(agent, com.kpo.agents.AgentProduct.class.getName(), new Object[]{quantity});
                                orderAgentController.start();
                            } catch (StaleProxyException e) {
                                System.out.println("Error!");
                            }
                        }
                        reply.setPerformative(ACLMessage.PROPOSE);
                        reply.setOntology(Constants.reserveProducts + msg.getSender().getLocalName());
                        reply.setContent(reservedProductsId.toString());
                        myAgent.send(reply);
                    }
                }
                block();
            }
        });
    }
}
