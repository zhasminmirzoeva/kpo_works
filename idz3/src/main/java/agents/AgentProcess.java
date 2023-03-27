package agents;
import utils.Operation;
import jade.core.Agent;
import java.util.List;
import org.json.JSONObject;
public class AgentProcess extends Agent {
    private int id;
    private int orderDish;
    private String started;
    private String ended;
    private boolean isActive;
    private List<Operation> operations;
    public AgentProcess(int id, int orderDish, String started, String ended, boolean isActive, List<Operation> operations) {
        this.id = id;
        this.orderDish = orderDish;
        this.started = started;
        this.ended = ended;
        this.isActive = isActive;
        this.operations = operations;
    }

    public String toString() {
        return "{\n" +
                "\"proc_id\": " + id +
                ",\n\"ord_dish\": " + orderDish +
                ",\n\"proc_started\": \"" + started + "\"" +
                "\"\n,\"proc_ended\": \"" + ended + "\"" +
                ",\n\"proc_active\"=" + isActive +
                ",\nprocessOperations=" + operations +
                '}';
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject()
                .put("proc_id", id)
                .put("ord_dish", orderDish)
                .put("proc_started", started)
                .put("proc_ended", ended)
                .put("proc_active", isActive);
        for (Operation operation : operations) {
            json.append("proc_operations", new JSONObject().put("proc_oper", operation));
        }
        return json;
    }
}
