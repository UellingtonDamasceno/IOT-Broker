package controller.backend;

import model.Phone;
import model.Refrigerator;
import model.SmartDevice;
import model.Television;
import util.Settings.DevicesBrand;
import util.Settings.DevicesModels;
import util.Settings.DevicesTypes;

/**
 *
 * @author Uellington Damasceno
 */
public class FactoryController {
    
    public SmartDevice createSmartDevice(DevicesTypes type, String id, DevicesBrand brand, DevicesModels model){
        String sType = type.toString();
        String sBrand = brand.toString();
        String sModel = model.toString();
        switch(type){
            case TV:
                return new Television(sType, sBrand, sModel);
            case TELEFONE:
                return new Phone(sType, sBrand, sModel);
            case GELADEIRA:
                return new Refrigerator(sType, sBrand, sModel);
            default:
                throw new AssertionError(type.name());
        }
        
    }
}
