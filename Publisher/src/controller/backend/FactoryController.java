package controller.backend;

import model.Device;
import util.Settings.DevicesBrand;
import util.Settings.DevicesModels;
import util.Settings.DevicesTypes;

/**
 *
 * @author Uellington Damasceno
 */
public class FactoryController {
    
    public Device createSmartDevice(DevicesTypes type, String id, DevicesBrand brand, DevicesModels model){
        String sType = type.toString();
        String sBrand = brand.toString();
        String sModel = model.toString();
        return new Device(sType, sBrand, sModel);
        
    }
}
