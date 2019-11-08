package controller.backend;

import model.SmartDevice;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class ProtcolController {
    public SmartDevice createDevice(String json){
        JSONObject smart = new JSONObject(json);
        System.out.println(smart.get("brand"));
        return null;
    }
}
