package bg.tu_varna.sit.a4.f22621679.modules;

import bg.tu_varna.sit.a4.f22621679.namespaces.XMLNamespaces;

import java.util.*;

/***
 *  Represents an XML attribute associated with an XML element.
 */
public class Attribute {
    private String name;
    private String value;
    private List<Attribute> attributes;
    private XMLNamespaces namespaces;
    public Attribute(String name) {
        this.name = name;
        attributes = new ArrayList<>();
        namespaces = new XMLNamespaces();
    }

    /***
     * invokes the method from XMLNamespaces class to add a namespace
     * @param prefix name of the namespace
     * @param uri url of the name
     */
    public void addNamespace(String prefix, String uri) {
        namespaces.addNamespace(prefix, uri);
    }
    public XMLNamespaces getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(XMLNamespaces namespaces) {
        this.namespaces = namespaces;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    /***
     * adds an attribute to the list and returns the same attribute
     * @param attribute
     * @return attribute
     */
    public Attribute addAttribute(Attribute attribute) {
        attributes.add(attribute);
        return attribute;
    }

    /***
     * removes a given attribute from the list
     * @param attribute
     */
    public void removeAttribute(Attribute attribute) {
        attributes.remove(attribute);
    }

    /***
     * Generates a String representation of the XML element, including its name, attributes (if any), and value (if any).
     * @param indent The indentation level (number of spaces) used for formatting the output. A larger indent value creates a more indented output.
     * @return  String representing the formatted XML element
     */
    public String toString(int indent) {
        for (int i = 0; i < indent*indent; i++) {
            System.out.print(" ");
        }
        StringBuilder sb=new StringBuilder();
        if(!namespaces.getNamespaces().keySet().isEmpty()) {
            Set<String> strings = namespaces.getNamespaces().keySet();
            for (String string : strings) {
                String s = namespaces.getNamespaces().get(string);
                s=s.substring(s.lastIndexOf("/")+1,s.lastIndexOf("\""));
                if(s.equals(name)) {
                    sb.append(name).append(" ");
                    sb.append("xmlns:").append(string).append("=").append(namespaces.getNamespaces().get(string));
                }
                else{
                    sb.append(string).append(":").append(name).append(" ");
                }
                //	<name xmlns:na="http://www.example.com/name">John Smith</name>
            }
        }
        else{
            sb.append(name).append(" ");
        }
        if(value != null) {
            sb.append(value);
        }
        return sb.toString();
    }

    /***
     * Prints a formatted representation of the XML element and its attributes to the standard output stream.
     * Recursively prints all child attributes with increased indentation
     * @param i The indentation level (number of spaces) used for formatting the output. A larger indent value creates a more indented output.
     */
    public void print(int i){
        System.out.println(this.toString(i));
        for (Attribute attribute : attributes) {
            attribute.print(i+1);
        }
    }

    /***
     *  Writes a formatted representation of the XML element, including its attributes (if any), to the provided StringBuilder object.
     * @param i The indentation level (number of spaces) used for formatting the output. A larger indent value creates a more indented output.
     * @param sb The StringBuilder object to which the formatted element representation will be appended.
     * @return The complete String representation of the formatted XML element, retrieved from the StringBuilder object.
     */
    public String write(int i,StringBuilder sb){
        this.addTags(i,sb);
        for (Attribute attribute : attributes) {
            attribute.write(i+1,sb);
        }
        return sb.toString();
    }

    /***
     * Appends the formatted opening and closing tags of the XML element to the provided StringBuilder object, along with the element's value (if any).
     * @param indent  The indentation level (number of spaces) used for formatting the output.
     * @param sb The StringBuilder object to which the formatted element representation will be appended.
     */
    public void addTags(int indent,StringBuilder sb) {
       sb.append(" ".repeat(Math.max(0, indent * indent)));
       sb.append("<");
        if(!namespaces.getNamespaces().keySet().isEmpty()) {
            Set<String> strings = namespaces.getNamespaces().keySet();
            for (String string : strings) {
                String s = namespaces.getNamespaces().get(string);
                s=s.substring(s.lastIndexOf("/")+1,s.lastIndexOf("\""));

                if(s.equals(name)) {
                    sb.append(name).append(" ");
                    sb.append("xmlns:").append(string).append("=").append(namespaces.getNamespaces().get(string));
                }
                else{
                    sb.append(string).append(":").append(name);
                }
                //	<name xmlns:na="http://www.example.com/name">John Smith</name>
            }
        }
        else{
            sb.append(name);
        }
        sb.append(">");
        if(value != null) {
            sb.append(value).append("</").append(name).append(">\n");
        }
        else{
            sb.append("\n");
        }

    }
}
