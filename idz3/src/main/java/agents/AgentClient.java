package agents;


import constants.Constants;
import utils.Client;
import utils.DishPro;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import org.json.JSONObject;
import java.util.Scanner;
public class AgentClient extends Agent {
    private Client client;
    private Double time;
    public void setVisitor(Client client) {
        this.client = client;
    }
    public Client getVisitor() {
        return client;
    }

    public AgentClient() {

    }
    public AgentClient(Client client, Double time) {
        this.client = client;
        this.time = time;
    }

    @Override
    protected void setup() {
        System.out.println("Input time");
        time = new Scanner(System.in).nextDouble();
        client = getArguments()[0] instanceof Client ? (Client) getArguments()[0] : null;
        System.out.printf("Client agent %s is ready %n", client.getName());
        assert client != null;
        System.out.println("Hello! Client agent " + client.getName() + " is ready");
        addBehaviour(new OneShotBehaviour(this) {
            @Override
            public void action() {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.addReceiver(new AID("supervisorAgent", AID.ISLOCALNAME));
                msg.setContent(time.toString());
                msg.setOntology(Constants.getMenu);
                send(msg);
            }
        });
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msgFromSuperVisor = receive();
                if (msgFromSuperVisor != null) {
                    String ontology = msgFromSuperVisor.getOntology();
                    String content = msgFromSuperVisor.getContent();
                    JSONObject order = new JSONObject();
                    JSONObject dishes = new JSONObject();

                    if (Constants.sendDishes.equals(ontology)) {
                        JSONObject jsonObject = new JSONObject(content);
                        JSONObject dishesJson = jsonObject.getJSONObject("dishes");
                        JSONObject productsJson = jsonObject.getJSONObject("products");
                        for (DishPro dishPro : client.getOrderDishes()) {
                            if (dishesJson.has(String.valueOf(dishPro.getMenuDishId()))) {
                                JSONObject temp = new JSONObject();
                                temp.put("products", productsJson.get(String.valueOf(dishPro.getMenuDishId())));
                                temp.put("amount", "1");
                                String menuDishId = String.valueOf(dishPro.getMenuDishId());
                                if (dishes.has(menuDishId)) {
                                    JSONObject temp2 = dishes.getJSONObject(menuDishId);
                                    temp2.put("amount", String.valueOf(Integer.parseInt(temp2.getString("amount")) + 1));
                                    dishes.put(menuDishId, temp2);
                                    continue;
                                }
                                dishes.put(menuDishId, temp);
                            }
                        }
                        order.put("user_name", client.getName());
                        order.put("dishes", dishes);
                        ACLMessage msgToSuperVisor = new ACLMessage(ACLMessage.INFORM);
                        msgToSuperVisor.addReceiver(new AID(Constants.superVisorAgent, AID.ISLOCALNAME));
                        msgToSuperVisor.setContent(order.toString());
                        msgToSuperVisor.setOntology(Constants.order);
                        send(msgToSuperVisor);
                    }
                }
                block();
            }
        });
    }
}
