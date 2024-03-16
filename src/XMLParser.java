
import java.util.*;

public class XMLParser {

    private String header;
    // nachalo na xml file

    private List<String> children;

    private List<XML> entity;
    //<person id=”0”>
    //      <name>John Smith</name>
    //      <address>USA</address>
    // </person>


    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public List<XML> getEntity() {
        return entity;
    }

    public XMLParser(String header) {
        this.header = header;
        this.entity = new ArrayList<>();
        this.children=new ArrayList<>();
    }

    private void select(String id, String key, Map<String, String> attributes) {
        //poluchava id=0 key=name
        if (!attributes.isEmpty()) {
            for (XML xml : entity) {
                if (xml.getId() .equals( id)) {
                    if (attributes.containsKey(key)) {
                        System.out.println(attributes.get(key));
                    }
                }
            }
        }
    }

    private void set(String id, String key, String value, Map<String, String> attributes) {
        if (!attributes.isEmpty()) {
            for (XML xml : entity) {
                if (xml.getId() .equals( id)) {
                    attributes.put(key, value);
                }
            }
        }
    }

    private Map<String, String> children(String id, Map<String, String> attributes) {
        Map<String, String> result = new LinkedHashMap<>();
        if (!attributes.isEmpty()) {
            for (XML xml : entity) {
                if (xml.getId() .equals( id)) {
                    result = xml.getAttributes();
                    return result;
                }
            }
        }
        return result;
    }

    private  Map<String, String> child(String id,int n,Map<String, String> attributes){
        Map<String, String> result=new HashMap<>();
        if (!attributes.isEmpty()) {
            for (XML xml : entity) {
                if (xml.getId() .equals( id)) {

                    int counter=0;
                    for(Map.Entry<String,String> kvp: attributes.entrySet()){
                        if(counter==n){
                            result.put(kvp.getKey(), kvp.getValue());
                            return result;
                        }
                        counter++;
                    }
                }
            }
        }
        return result;
    }

    private List<String> text(String id, Map<String, String> attributes) {
        List<String> result=new ArrayList<>();
        if (!attributes.isEmpty()) {
            for (XML xml : entity) {
                if (xml.getId() .equals( id)) {
                    Map<String,String> data=xml.getAttributes();
                    for(Map.Entry<String,String> kvp: data.entrySet()){
                        result.add(kvp.getValue());
                    }
                    return result;
                }
            }
        }
        return result;
    }


    private void delete(String id,String key,Map<String,String> attributes){
        if(!attributes.isEmpty()){
            for (XML xml : entity) {
                if (xml.getId() .equals( id)) {
                    xml.getAttributes().remove(key);
                    break;
                }
            }
        }
    }

    private void newChild(String id,Map<String,String> attributes){
        for (XML value : entity) {
            if (value.getId() .equals( id)) {
                System.out.println("Id Already exists!");
                return;
            }
        }
        XML xml=new XML(id,null);
        this.entity.add(xml);
    }

    private void xPath(String id,String xPath,Map<String,String> attributes){



    }




    //5 1 4 5 1 8 3 5

}
