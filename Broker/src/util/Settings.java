package util;

/**
 *
 * @author Uellington Conceição
 */
public class Settings {

    public enum REQUEST_TYPE{
        ERROR("ERROR"), 
        HTTP("HTTP");
        
        private String type;
        private REQUEST_TYPE(String type){
            this.type = type;
        }
        
        @Override
        public String toString(){
            return this.type;
        }
    }
    
    public enum HTTP {
        GET("GET"),
        POST("POST"),
        DELETE("DELETE"),
        UPDATE("UPDATE");

        private String verb;

        private HTTP(String verb) {
            this.verb = verb;
        }

        public String toString() {
            return this.verb;
        }
    }

}
