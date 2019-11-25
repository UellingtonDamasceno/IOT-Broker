package util;

/**
 *
 * @author Uellington Damasceno
 */
public class Settings {
    
    public enum Types{
        API("API"),
        SERVER("SERVER"),
        PHONE("PHONE");
        
        private final String type;
        
        private Types(String value){
            this.type = value;
        }
        
        public String getType(){
            return this.type;
        }
        
    }
    
    public enum Models{
        S10,
        S10_NOTE,
        NOTE8,
        NOTE_8_PRO,
        IPHONE_11,
        IPHONE_11_PRO;
    }
    
    public enum Brand{
        GOOGLE, 
        IBM, 
        AWS,
        CLOUD, 
        DELL;
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
    
    
    /**
     * Juntar as telas de configuração de conexão e despositivos.
     */
    public enum Scenes {
        INITIAL_SETTING("InitialSettings.fxml", false),
        SUBSCRIBER_DASHBOARD("SubscriberDashboard.fxml", true),
        ALL_TOPICS("Alltopics.fxml", true);

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
        
        @Override
        public String toString(){
            return this.value;
        }
    }

}
