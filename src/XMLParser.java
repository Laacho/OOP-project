
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {

    private String header;
    //root
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

    public void select(String id, String key, Map<String, String> attributes) {
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

    public void set(String id, String key, String value, Map<String, String> attributes) {
        if (!attributes.isEmpty()) {
            for (XML xml : entity) {
                if (xml.getId() .equals( id)) {
                    attributes.put(key, value);
                }
            }
        }
    }

    public Map<String, String> children(String id, Map<String, String> attributes) {
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

    public  Map<String, String> child(String id,int n,Map<String, String> attributes){
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

    public List<String> text(String id, Map<String, String> attributes) {
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


    public void delete(String id,String key,Map<String,String> attributes){
        if(!attributes.isEmpty()){
            for (XML xml : entity) {
                if (xml.getId() .equals( id)) {
                    xml.getAttributes().remove(key);
                    break;
                }
            }
        }
    }

    public void newChild(String id,Map<String,String> attributes){
        for (XML value : entity) {
            if (value.getId() .equals( id)) {
                System.out.println("Id Already exists!");
                return;
            }
        }
        XML xml=new XML(id);
        this.entity.add(xml);
    }

    public List<String> xPath(String xPath, Map<String,String> attributes){

        List<String> result=new ArrayList<>();
        List<String> words=wordsSplitter(xPath);
        if(xPath.contains("/")){
            if(xPath.contains("[") && xPath.contains("]")){
                //person/address[0]
                Pattern pattern=Pattern.compile("[\\d+_]+");
               Matcher  matcher= pattern.matcher(xPath);
                 String index="-1";
                 if(matcher.find()) {
                      index = matcher.group();
                 }
                for(XML xml:entity){
                    if(xml.getId().equals(index)){
                        String temp=attributes.get(words.get(1));
                        result.add(temp);
                        return result;
                    }
                }
            }
            else{
                //  person/address
                String toSearchFor=xPath.split("/")[1];
                for (XML value : entity) {
                    if(value.getAttributes().containsKey(toSearchFor)){
                        result.add(value.getAttributes().get(toSearchFor));
                    }
                }
            }
        }
        else if(xPath.contains("@")){
        //person(@id)
            if(words.get(1).equalsIgnoreCase("id")){
                for(XML xml:entity){
                    result.add(xml.getId());
                }
            }
            else{
                for(XML xml:entity){
                    if(xml.getAttributes().containsKey(words.get(1))){
                        result.add(xml.getAttributes().get(words.get(1)));
                    }
                }
            }
        }
        else if(xPath.contains("=")){
        //person(address=”USA”)/name
            String typeConstraint=words.get(1);//address
            String constraint=words.get(2);//usa
           for(XML xml:entity){
               if(xml.getAttributes().containsKey(words.get(3)) &&
                xml.getAttributes().get(typeConstraint).equalsIgnoreCase(constraint)){
                   result.add(xml.getAttributes().get(words.get(3)));
               }
           }

        }


        return result;
    }
    private List<String> wordsSplitter(String xPath){
        Pattern pattern=Pattern.compile("[a-zA-Z]+");
        Matcher matcher= pattern.matcher(xPath);
        List<String> words=new ArrayList<>();
        while (matcher.find()) {
            words.add(matcher.group());
        }
        return words;
    }





}
