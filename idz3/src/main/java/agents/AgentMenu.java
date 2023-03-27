package agents;

import constants.Constants;
import utils.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import jade.core.AID;
import jade.core.Agent;
import jade.domain.FIPAException;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.lang.acl.ACLMessage;
import static constants.Constants.*;
public class AgentMenu extends Agent {
    private Menu menu;
    private MenuCards menuCards;
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
    public Menu getMenu() {
        return menu;
    }
    public void setMenuCards(MenuCards menuCards) {
        this.menuCards = menuCards;
    }
    public AgentMenu(Menu menu, MenuCards menuCards) {
        this.menu = menu;
        this.menuCards = menuCards;
    }
    public AgentMenu() {

    }

    @Override
    protected void setup() {
        System.out.println("Menu agent started");
        menu = new Menu((Map<Integer, Dish>) getArguments()[0]);
        menuCards = new MenuCards((Map<Integer, DishCard>) getArguments()[1]);
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage aclMessage = receive();
                if (aclMessage != null) {
                    String ontology = aclMessage.getOntology();
                    String content = aclMessage.getContent();
                    if (getAvailableDishes.equals(ontology)) {
                        System.out.println("Menu agent received message from super-visor");
                        JSONObject query = new JSONObject(content);
                        JSONObject availableProducts = (JSONObject) query.get("products");
                        double needTime = query.getDouble("time");
                        JSONObject result = null;
                        try {
                            result = getSatisfying(needTime, availableProducts);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        query.remove("products");
                        query.put("dishes", result.get("dishes"));
                        query.put("products", result.get("products"));
                        ACLMessage replyToSuperVisor = new ACLMessage(ACLMessage.INFORM);
                        replyToSuperVisor.addReceiver(new AID(superVisorAgent, AID.ISLOCALNAME));
                        replyToSuperVisor.setOntology(Constants.sendDishes);
                        replyToSuperVisor.setContent(query.toString());
                        send(replyToSuperVisor);
                    } else if (getMenuCards.equals(ontology)) {
                        System.out.println("Menu agent received message from " + aclMessage.getSender().getLocalName() + " request: " + content);
                        JSONObject request = new JSONObject(content);
                        JSONObject dishes = (JSONObject) request.get("dishes");
                        JSONArray cardsToSend = new JSONArray();
                        Map<Integer, Dish> menuDishes = menu.getDishes();
                        Map<Integer, DishCard> cards = menuCards.getMenuCards();
                        for (String dishId : dishes.keySet()) {
                            DishCard dishCard = cards.get(menuDishes.get(Integer.parseInt(dishId)).getCardId());
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("dish_id", dishCard.getId());
                            jsonObject.put("dishCard", dishCard.toJson());
                            cardsToSend.put(jsonObject);
                        }
                        ACLMessage answer = aclMessage.createReply();
                        answer.setPerformative(ACLMessage.INFORM);
                        answer.setOntology(getMenuCards);
                        answer.setContent(String.valueOf(cardsToSend));
                        send(answer);
                    }
                }
                block();
            }
        });
    }

    @Override
    protected void takeDown() {
        System.out.println("Menu agent finished");
        try {
            DFService.deregister(this);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        System.out.println("Agent " + getAID().getName() + " terminating.");
    }

    public JSONObject getDishes() {
        JSONObject dishes = new JSONObject();
        dishes.put("dishes", new JSONArray());
        for (Dish dish : menu.getDishes().values()) {
            JSONObject dishJson = new JSONObject();
            dishJson.put("menu_dish_id", dish.getId());
            dishJson.put("menu_dish_price", dish.getPrice());
            dishJson.put("menu_dish_active", dish.getIsActive());
            dishJson.put("menu_dish_card", dish.getCardId());
            dishes.getJSONArray("dishes").put(dishJson);
        }
        return dishes;
    }

    private JSONObject getSatisfying(double needTime, JSONObject productsFromWarehouse) throws IOException {
        JSONObject resultDishes = new JSONObject();
        JSONObject resultProducts = new JSONObject();
        DishCard currentDishCard;
        ArrayList<OperationPro> cardOperationPro;
        for (Dish dish : menu.getDishes().values()) {
            JSONObject productThatUsedForThisDish = new JSONObject();
            if (!dish.getIsActive()) {
                continue;
            }
            boolean canCookThisDish = true;

            currentDishCard = menuCards.getMenuCards().get(dish.getCardId());
            if (currentDishCard == null) {
                continue;
            }
            cardOperationPro = (ArrayList<OperationPro>) currentDishCard.getSuperOperations();
            for (OperationPro operationPro : cardOperationPro) {
                if (!canCookThisDish) {
                    break;
                }
                double timeForCooking = operationPro.getTime();
                List<Map<String, Number>> productsIdList = operationPro.getProductsId();
                if (timeForCooking > needTime) {
                    canCookThisDish = false;
                    continue;
                }
                productThatUsedForThisDish = new JSONObject();
                for (Map<String, Number> product : productsIdList) {
                    JSONObject productFromWarehouseCharacteristics = (JSONObject) productsFromWarehouse.get(product.get("prod_type").toString());
                    double quantityAvailableOnWarehouse = productFromWarehouseCharacteristics.getDouble("prod_item_quantity");
                    if (quantityAvailableOnWarehouse < product.get("prod_quantity").doubleValue()) {
                        canCookThisDish = false;
                        break;
                    }
                    productThatUsedForThisDish.put(product.get("prod_type").toString(), product.get("prod_quantity").doubleValue());
                }
            }
            if (!canCookThisDish) {
                break;
            }
            resultDishes.put(String.valueOf(dish.getId()), dish.toJSON());
            resultProducts.put(String.valueOf(dish.getId()), productThatUsedForThisDish);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("oper_card", dish.getCardId());
            try {
                PrintWriter printWriter = new PrintWriter(new FileWriter(("./src/main/java/com/kpo/files/operation_log.txt")));
                printWriter.write(jsonObject.toString());
            }catch(Exception ex) {

            }
        }
        JSONObject result = new JSONObject();
        result.put("dishes", resultDishes);
        result.put("products", resultProducts);
        return result;
    }

}