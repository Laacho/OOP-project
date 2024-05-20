package bg.tu_varna.sit.a4.f22621679.modules;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 *  This class represents an XML document structure. It provides functionalities for building, manipulating, and searching within the XML structure.
 */
public class XML {
    /***
     * String header->The root element name of the XML document.
     *Map< String, List< Attribute>> map-> A map that stores elements identified by their IDs and their associated attributes.
     * String atrName -> The name of the attribute used as the primary identifier for elements within the document (if applicable).
     * String path ->An internal path used for tracking the current location within the XML structure during XPath operations (for debugging purposes)
     */
    private String header;
    private Map<String, List<Attribute>> map;
    //id i negovite attributi
    private String atrName;
    private String path;

    public XML(String header, String atrName) {
        this.header = header;
        this.atrName = atrName;
        this.map = new LinkedHashMap<>();
        path = null;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Map<String, List<Attribute>> getMap() {
        return map;
    }

    public void setMap(Map<String, List<Attribute>> map) {
        this.map = map;
    }

    public String getAtrName() {
        return atrName;
    }

    public void setAtrName(String atrName) {
        this.atrName = atrName;
    }


    public void addToMap(String id, Attribute attr) {
        if (map.containsKey(id)) {
            map.get(id).add(attr);
        } else {
            List<Attribute> list = new ArrayList<>();
            list.add(attr);
            map.put(id, list);
        }
    }

    /***
     * Prints the content of the XML structure in a human-readable format
     */
    public void print() {
        System.out.println("Header: " + header);
        for (Map.Entry<String, List<Attribute>> kvp : map.entrySet()) {
            System.out.println(this.atrName + " with id: " + kvp.getKey());
            for (Attribute attr : kvp.getValue()) {
                attr.print(1);
            }
        }
    }

    /***
     * Generates a String representation of the entire XML document
     * @return  A String containing the well-formed XML document
     */
    public String write() {
        StringBuilder sb = new StringBuilder();
        sb.append("<").append(header).append(">").append("\n");
        for (Map.Entry<String, List<Attribute>> kvp : map.entrySet()) {
            // <person id="0">
            sb.append("<").append(this.atrName).append(" id=\"").append(kvp.getKey()).append("\">").append("\n");
            for (Attribute attr : kvp.getValue()) {
                attr.write(1, sb);
            }
            sb.append("</").append(this.atrName).append(">\n");
        }
        sb.append("</").append(header).append(">");
        return sb.toString();
    }

    /***
     * Selects an element based on its ID and searches for a specific attribute within that element or its descendants
     * @param id The ID of the element to search within.
     * @param key  The name of the attribute to search for.
     */
    public void select(String id, String key) {
        if (map.containsKey(id)) {
            Attribute attr = null;
            List<Attribute> attributes = map.get(id);
            Deque<List<Attribute>> stack = new ArrayDeque<>();
            stack.push(attributes);
            while (!stack.isEmpty()) {
                List<Attribute> temp = stack.pop();
                for (Attribute attribute : temp) {
                    stack.push(attribute.getAttributes());
                    if (attribute.getName().equals(key)) {
                        attributes = attribute.getAttributes();
                        attr = new Attribute(attribute.getName());
                        attr.setValue(attribute.getValue());
                        stack.clear();
                        break;
                    }
                }
            }
            stack.push(attributes);
            Map<String, Boolean> visited = new HashMap<>();

            System.out.print(attr.getName());
            if (attr.getValue() != null) {
                System.out.println(" " + attr.getValue());
            } else System.out.println();
            while (!stack.isEmpty()) {
                List<Attribute> temp = stack.pop();
                for (Attribute attribute : temp) {
                    if (visited.containsKey(attribute.getName())) {
                        continue;
                    } else {
                        visited.put(attribute.getName(), true);
                        if (attribute.getValue() != null) {
                            System.out.println(attribute.getName() + " " + attribute.getValue());
                        } else {
                            System.out.println(attribute.getName() + ": " + attribute.getAttributes().size());
                        }

                    }
                    if (!attribute.getAttributes().isEmpty()) {
                        stack.offer(temp);
                        stack.push(attribute.getAttributes());
                        break;
                    }
                }
            }
        }
    }

    /***
     * Sets the value of a specific attribute within an element identified by its ID.
     * @param id  The ID of the element containing the attribute.
     * @param key The name of the attribute to modify.
     * @param value The new value to be assigned to the attribute.
     */
    public void set(String id, String key, String value) {
        if (map.containsKey(id)) {
            List<Attribute> attributes = map.get(id);
            Deque<List<Attribute>> stack = new ArrayDeque<>();
            stack.push(attributes);
            while (!stack.isEmpty()) {
                List<Attribute> temp = stack.pop();
                for (Attribute attribute : temp) {
                    stack.push(attribute.getAttributes());
                    if (attribute.getName().equals(key) && attribute.getValue() != null) {
                        attribute.setValue(value);
                        return;
                    }
                }
            }
        }
    }

    /***
     * Lists the child attributes of an element identified by its ID.
     * @param id The ID of the element whose children attributes will be listed.
     */
    public void children(String id) {
        Map<String, List<String>> pirati = new LinkedHashMap<>();
        if (map.containsKey(id)) {
            List<Attribute> attributes = map.get(id);
            Deque<List<Attribute>> stack = new ArrayDeque<>();
            stack.push(attributes);
            while (!stack.isEmpty()) {
                List<Attribute> temp = stack.pop();
                for (Attribute attribute : temp) {
                    if (attribute.getAttributes().size() > 1) {
                        List<String> list = new ArrayList<>();
                        for (Attribute attributeAttribute : attribute.getAttributes()) {
                            list.add(attributeAttribute.getName());
                        }
                        pirati.put(attribute.getName(), list);
                    }
                    stack.push(attribute.getAttributes());
                }
            }
        }
        if(!pirati.isEmpty())
            pirati.forEach((k,v)-> System.out.println(k+"->"+String.join(",",v)));
        else System.out.println("This attribute has no children!");
    }

    /***
     * Prints the names of the child attributes of an element identified by its ID, optionally followed by their values if they exist.
     * @param id  The ID of the element whose child attributes will be displayed.
     * @param n The index of the specific child attribute to display (zero-based indexing)
     */
    public void child(String id, int n) {// ne broim vlojenite
        if (map.containsKey(id)) {
            List<Attribute> attributes = map.get(id);
            Attribute attribute = attributes.get(n - 1);
            Map<String, Boolean> visited = new HashMap<>();
            Deque<List<Attribute>> stack = new ArrayDeque<>();
            stack.push(attribute.getAttributes());
            System.out.print(attribute.getName() + ":");
            while (!stack.isEmpty()) {
                List<Attribute> temp = stack.pop();
                for (Attribute attributeAttribute : temp) {
                    stack.push(attributeAttribute.getAttributes());
                    if (visited.containsKey(attributeAttribute.getName())) {
                        continue;
                    } else {
                        visited.put(attributeAttribute.getName(), true);
                        System.out.print(attributeAttribute.getName() + " ");
                    }
                }
            }
            System.out.println();
        }
    }

    /***
     * Extracts and prints the text value of an element identified by its ID, traversing through its descendants if necessary.
     * @param id The ID of the element whose text value will be extracted.
     */
    public void text(String id) {
        if (map.containsKey(id)) {
            Map<String, Boolean> visited = new HashMap<>();
            List<Attribute> attributes = map.get(id);
            Deque<List<Attribute>> stack = new ArrayDeque<>();
            stack.push(attributes);
            while (!stack.isEmpty()) {
                List<Attribute> temp = stack.pop();
                for (Attribute attribute : temp) {
                    stack.push(attribute.getAttributes());
                    if (visited.containsKey(attribute.getName())) {
                        continue;
                    } else {
                        visited.put(attribute.getName(), true);
                        if (attribute.getValue() != null)
                            System.out.println(attribute.getValue());
                    }
                    if (!attribute.getAttributes().isEmpty()) {
                        stack.offer(temp);
                        stack.push(attribute.getAttributes());
                        break;
                    }
                }
            }
        }
    }

    /***
     * Deletes a specific attribute identified by its name from an element identified by its ID.
     * @param id  The ID of the element containing the attribute to delete.
     * @param key The name of the attribute to be deleted.
     */
    public void delete(String id, String key) {
        if (map.containsKey(id)) {
            List<Attribute> attributes = map.get(id);
            Deque<List<Attribute>> stack = new ArrayDeque<>();
            stack.push(attributes);
            while (!stack.isEmpty()) {
                List<Attribute> temp = stack.pop();
                for (Attribute attribute : temp) {
                    stack.push(attribute.getAttributes());
                    if(attribute.getName().equals(key)) {
                       // attribute.removeAttribute(attribute);
                        map.get(id).remove(attribute);
                        return ;
                    }
                    for (Attribute attribute1 : attribute.getAttributes()) {
                        if (attribute1.getName().equals(key)) {
                            attribute.removeAttribute(attribute1);
                            return;
                        }
                    }
                }
            }
        }
    }

    /***
     * Creates a new child element within the XML structure identified by a specific ID.
     * @param id The ID of the element that will become the parent of the new child element.
     * @throws IllegalArgumentException If an element with the provided ID already exists
     */
    public void newChild(String id) {
        if (!map.containsKey(id)) {
            map.put(id, new ArrayList<>());
        } else {
            throw new IllegalArgumentException("id already exists");
        }
    }

    /***
     * Executes an XPath expression to search and navigate within the XML structure.
     * @param xPath The XPath expression to be evaluated.
     * @return A list of Strings containing the results of the XPath evaluation (depending on the expression)
     */
    public List<String> xPath(String xPath) {
        if(xPath.contains("child::")){
            xPathAxes(xPath);
            return null;
        }
        List<String> result = new ArrayList<>();
        List<String> words = wordsSplitter(xPath);
        if (words.size() < 2) {
            System.out.println("Invalid command!");
            return result;
        }
        if (xPath.contains("[") && xPath.contains("]")) {
            String id = xPath.split("/")[1].split("\\[")[1].split("\\]")[0];//address 1_1]
            //words -> person,address
            String toSearch = words.get(1);
            if (atrName.equals(words.get(0))) {
                if (map.containsKey(id)) {
                    List<Attribute> attributes = map.get(id);
                    Deque<List<Attribute>> stack = new ArrayDeque<>();
                    stack.push(attributes);
                    while (!stack.isEmpty()) {
                        List<Attribute> temp = stack.pop();
                        for (Attribute attribute : temp) {
                            stack.push(attribute.getAttributes());
                            if (attribute.getName().equals(toSearch)) {
                                attribute.print(1);
                                stack.clear();
                                break;
                            }
                        }
                    }
                }
            }
        } else if (xPath.contains("=")) {
            if (atrName.equals(words.get(0))) {
                List<Attribute> attributes = getAttributes(words);
            }
        } else if (xPath.contains("@")) {
            if (atrName.equals(words.get(0))) {
                if (words.get(1).equalsIgnoreCase("id")) {
                    Set<String> strings = map.keySet();
                    for (String string : strings) {
                        System.out.println(string);
                    }
                } else {
                    findByName(words);
                }
            }
        } else { //person/address
            if (atrName.equals(words.get(0))) {
                findByName(words);
            }
        }
        return result;
    }

    /***
     * Internal helper method for processing child:: axes in XPath expressions.
     * @param command The XPath expression containing the child:: axes clause.
     */
    public void xPathAxes(String command) {
        //child::book
        String c = command.split("::")[1];
        //child, book
        Deque<List<Attribute>> stack = new ArrayDeque<>();
        Map<String,List<Attribute>> result = new LinkedHashMap<>();
        for (Map.Entry<String, List<Attribute>> kvp : map.entrySet()) {
            stack.push(kvp.getValue());
            while (!stack.isEmpty()) {
                List<Attribute> temp = stack.pop();
                for (Attribute attribute : temp) {
                    stack.push(attribute.getAttributes());
                    if (attribute.getName().equals(c)) {
                        result.put(kvp.getKey(), attribute.getAttributes());
                    }
                }
            }
        }
        for (Map.Entry<String, List<Attribute>> kvp : result.entrySet()) {
            System.out.println("id: "+kvp.getKey());
              for (Attribute attribute : kvp.getValue()) {
                   attribute.print(1);
            }
        }
    }

    /***
     * Internal helper method for finding elements by name during XPath evaluation
     * @param words  A list of words extracted from the XPath expression.
     */
    private void findByName(List<String> words) {
        Deque<List<Attribute>> stack = new ArrayDeque<>();
        for (Map.Entry<String, List<Attribute>> kvp : map.entrySet()) {
            stack.push(kvp.getValue());
            while (!stack.isEmpty()) {
                List<Attribute> temp = stack.pop();
                for (Attribute attribute : temp) {
                    stack.push(attribute.getAttributes());
                    if (attribute.getName().equals(words.get(words.size()-1))) {
                        attribute.print(1);
                    }
                }
            }
        }
    }

    /***
     *Internal helper method for retrieving attributes based on conditions in an XPath expression.
     * @param words A list of words extracted from the XPath expression.
     * @return A list of Attribute objects matching the conditions in the XPath expression.
     */
    private List<Attribute> getAttributes(List<String> words) {
        List<Attribute> attributes = new ArrayList<>();
        Deque<List<Attribute>> stack = new ArrayDeque<>();
        for (Map.Entry<String, List<Attribute>> kvp : map.entrySet()) {
            stack.push(kvp.getValue());
            while (!stack.isEmpty()) {
                List<Attribute> temp = stack.pop();
                for (Attribute attribute : temp) {
                    stack.push(attribute.getAttributes());
                    if (attribute.getName().equals(words.get(1))) {
                        if (attribute.getValue() != null && attribute.getValue().equals(words.get(2))) {
                            attributes=temp;
                            printXpath(words.get(3),attributes);
                        }
                    }
                }
            }
        }
        return attributes;
    }

    /***
     * Internal helper method for printing elements during XPath evaluation based on a search term.
     * @param toSearch The name of the element to search for.
     * @param list  A list of Attribute objects representing the current context in the XML structure.
     */
    public void printXpath( String toSearch,List<Attribute> list) {
        Deque<List<Attribute>> stack = new ArrayDeque<>();
        stack.push(list);
        while (!stack.isEmpty()) {
            List<Attribute> temp = stack.pop();
            for (Attribute attribute : temp) {
                stack.push(attribute.getAttributes());
                if(attribute.getName().equals(toSearch)) {
//                    attribute.print(1);
                    attribute.print(1);
                }
            }
        }
    }

    /***
     * Internal helper method for splitting XPath expressions into a list of words.
     * @param xPath The XPath expression to be split.
     * @return A list of Strings containing the individual words from the XPath expression.
     */
    private List<String> wordsSplitter(String xPath) {
        Pattern pattern = Pattern.compile("[a-zA-Z +]+");
        Matcher matcher = pattern.matcher(xPath);
        List<String> words = new ArrayList<>();
        while (matcher.find()) {
            String toAdd = matcher.group().replaceAll("\\s+", " ");
            words.add(toAdd);
        }
        return words;
    }

}
