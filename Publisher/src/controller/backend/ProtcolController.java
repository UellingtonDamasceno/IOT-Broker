package controller.backend;

import model.Device;
import org.json.JSONObject;

/**
 *
 * @author Uellington Damasceno
 */
public class ProtcolController {
    public Device createDevice(String json){
        JSONObject smart = new JSONObject(json);
        System.out.println(smart.get("brand"));
        return null;
    }
}
