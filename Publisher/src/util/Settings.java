package util;

/**
 *
 * @author Uellington Damasceno
 */
public class Settings {
    
    public enum DevicesTypes{
        TV("TELEVISION"),
        TELEFONE("PHONE"),
        GELADEIRA("REFRIGERATOR");
        
        private String type;
        
        private DevicesTypes(String value){
            this.type = value;
        }
        
        public String getType(){
            return this.type;
        }
        
    }
    
    public enum DevicesModels{
        S10,
        S10_NOTE,
        NOTE8,
        NOTE_8_PRO,
        IPHONE_11,
        IPHONE_11_PRO;
    }
    
    public enum DevicesBrand{
        APPLE, 
        XIAOMI, 
        SAMSUNG,
        LG, 
        REDMI,
        ONE_PLUS;
    }
    
    public enum Connection{
        DEFAULT_PORT(9999),
        DEFAULT_IP(127001);
    
        private final int value;
     
        private Connection(int value){
            this.value = value;
        }
        
        @Override
        public String toString(){
            return (this.value == 9999) ? String.valueOf(value) : "127.0.0.1"; 
        }
    }
    
    public enum Verbs{
        DELETE, //Delete
        GET, // Read
        POST, // Create
        PUT, // Replace
        PATCH; // Modify
    }
    
    public enum Route{
        TOPIC,
        PUBLISHER,
        SUBSCRIPER,
    }
    
    /**
     * Juntar as telas de configuração de conexão e despositivos.
     */
    public enum Scenes {
        INITIAL_SETTING("InitialSettings.fxml", false),
        PUBLISHER_DASHBOARD("PublisherDashboard.fxml", false);

        private final String value;
        private final boolean cache;

        private Scenes(String value, boolean cache) {
            this.value = value;
            this.cache = cache;
        }

        public String getValue() {
            return this.value;
        }

        public boolean isCache() {
            return this.cache;
        }
    }

}
