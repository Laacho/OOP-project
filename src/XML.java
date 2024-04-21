import java.util.LinkedHashMap;
import java.util.Map;

public class XML {
    private String id;
    private static String idName;
    private Map<String,String> attributes;
    //name john




    public XML() {
        this.attributes = new LinkedHashMap<>();
    }

    public static String getIdName() {
        return idName;
    }

    public static void setIdName(String idName) {
        XML.idName = idName;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("Id: ").append(this.id);
        for (Map.Entry<String,String> kvp: attributes.entrySet()){
            sb.append(kvp.getKey()).append(": ").append(kvp.getValue()).append("\n");
        }
        return sb.toString();
    }
}
