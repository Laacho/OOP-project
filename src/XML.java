import java.util.LinkedHashMap;
import java.util.Map;

public class XML {
    private String id;
    private static String idName;
    private Map<String,String> attributes=new LinkedHashMap<>();
    //name john


    public XML(String id, Map<String, String> attributes) {
        this.id = id;
        this.attributes = attributes;
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
