
import constants.Constants;
import utils.*;
import factory.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.Map;
import static constants.Constants.*;
public class Main {
    public static void main(String[] args) {
        LoadDish loadDish = new LoadDish("./src/main/java/files/menu_dishes.txt");
        LoadCard loadCard = new LoadCard("./src/main/java/files/dish_cards.txt");
        LoadProduct loadProduct = new LoadProduct("./src/main/java/files/products.txt");
        LoadClient loadClient = new LoadClient("./src/main/java/files/visitors_orders.txt");
        Map<Integer, Dish> dishMap = loadDish.load();
        Map<Integer, DishCard> dishCardMap = loadCard.load();
        Map<Integer, Product> productMap = loadProduct.load();
        Map<Integer, Client> visitorMap = loadClient.load();
        Runtime runtime = Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        ContainerController containerController = runtime.createMainContainer(profile);
        AgentController superVisorAgent, warehouseAgent, visitorAgent, menuAgent;
        try {
            superVisorAgent = containerController.createNewAgent(Constants.superVisorAgent, "agents.AgentSupervisor", null);
            superVisorAgent.start();
            Map<Integer, Product> dataForWarehouseAgent = productMap;
            Map<Integer, Integer> connectorIdWithType = LoadProduct.kostilConnectIdWithType(dataForWarehouseAgent);
            warehouseAgent = containerController.createNewAgent(storageAgent, "agents.AgentStorage", new Object[]{dataForWarehouseAgent, connectorIdWithType});
            warehouseAgent.start();
            for (Client client : visitorMap.values()) {
                client.setName(client.getName().trim());
                visitorAgent = containerController.createNewAgent(client.getName(), "agents.AgentClient", new Object[]{client});
                visitorAgent.start();
            }
            menuAgent = containerController.createNewAgent(Constants.menuAgent, "agents.AgentMenu", new Object[]{dishMap, dishCardMap});
            menuAgent.start();
        } catch (StaleProxyException ex) {
            ex.printStackTrace();
            System.out.println("Error!");
        }
    }
}
