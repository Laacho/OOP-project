package bg.tu_varna.sit.a4.f22621679.parser;

import bg.tu_varna.sit.a4.f22621679.modules.Attribute;
import bg.tu_varna.sit.a4.f22621679.modules.XML;
import bg.tu_varna.sit.a4.f22621679.verification.Verification;
import bg.tu_varna.sit.a4.f22621679.namespaces.XMLNamespaces;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/***
 * An implementation of the Parser interface that parses XML files and builds an in-memory representation of the XML document structure.
 */
public class XMLParser implements Parser {
    /***
     * Parses an XML file from the specified file name and returns an XML object representing the parsed document structure.
     * @param fileName  The path to the XML file to be parsed.
     * @return An XML object representing the parsed XML document, or null if the file is empty.
     */
    @Override
    public XML parse(String fileName) {
        Verification verification = new Verification();
        fileName = verification.correction(fileName);

        Map<String, List<Attribute>> map = new LinkedHashMap<>();
        String rootElem = "";
        String elemName = "";
        try (FileReader fileReader = new FileReader(fileName)) {
            Scanner scanner = new Scanner(fileReader);
            if (!scanner.hasNextLine()) {
                return null;
            }

            List<Attribute> attributes = new ArrayList<>();
            Deque<Attribute> stack = new ArrayDeque<>();
            Deque<String> ends = new ArrayDeque<>();

            String first = scanner.nextLine().trim();

            rootElem = first.split("\\s+")[0].replace("<", "");
            String toEndPeople = addToCorrectFormat(rootElem);//people

            XMLNamespaces nameSpaces = new XMLNamespaces();
            if (first.contains("xmlns")) {
                String[] split = first.split("\\s+");
                for (int i = 1; i < split.length; i++) {
                    String[] res = split[i].split("=");
                    if (i == split.length - 1) {
                        res[1] = res[1].replace(">", "");
                    }
                    if (res[0].contains(":")) {
                        String[] krai = res[0].split(":");
                        nameSpaces.addNamespace(krai[1], res[1]);
                    } else {
                        nameSpaces.addNamespace(res[0], res[1]);
                    }
                }

            }

            String nextLine = scanner.nextLine().trim();
            String id = getIdFirst(nextLine);
            map.put(id, new ArrayList<>());

            elemName = getElemName(nextLine);
            String toEndStoeva = addToCorrectFormat(elemName);
            while (scanner.hasNextLine()) {
                String lineObshto = scanner.nextLine().trim();
                if (lineObshto.equals(toEndStoeva)) {
                    lineObshto = scanner.nextLine().trim();
                    if (lineObshto.equals(toEndPeople)) {
                        break;
                    }
                    id = getId(lineObshto, map);
                    attributes.clear();
                    nameSpaces = new XMLNamespaces();
                    continue;
                }

                if (lineObshto.contains("/") && lineObshto.contains("</")) {
                    String nameAtr = "";
                    XMLNamespaces toCheck = new XMLNamespaces();
                    if (lineObshto.contains(":")) {
                        String[] split = lineObshto.split(":");
                        split[0] = split[0].replace("<", "");

                        if (nameSpaces.getNamespaces().containsKey(split[0])) {
                            toCheck.addNamespace(split[0], nameSpaces.getNamespaces().get(split[0]));
                            nameAtr = split[1].split(">")[0];
                        } else {
                            nameAtr = lineObshto.substring(lineObshto.indexOf("</") + 2, lineObshto.lastIndexOf(">"));//name
                        }
                    } else {
                        nameAtr = lineObshto.substring(lineObshto.indexOf("</") + 2, lineObshto.lastIndexOf(">"));//name
                    }

                    String toEnd = addToCorrectFormat(nameAtr);
                    if (!toEnd.equals(ends.peek())) {
                        String valueAtr = lineObshto.substring(lineObshto.indexOf(">") + 1, lineObshto.lastIndexOf("<"));//Petur
                        Attribute a = new Attribute(nameAtr);
                        a.setValue(valueAtr);
                        a.setNamespaces(toCheck);
                        if (stack.isEmpty()) {
                            map.get(id).add(a);
                        } else {
                            stack.peek().addAttribute(a);
                        }
                    } else {
                        if (!stack.isEmpty()) {
                            connectWithPreviousNode(stack, toEnd, ends, map, id);
                        }
                    }

                } else {
                    String get = getAtrName(lineObshto);
                    Attribute at = new Attribute(get);
                    if (!lineObshto.contains("xmlns")) {
                        String nameSpaceIme = lineObshto.split(":")[0].replace("<", "");
                        if (nameSpaces.getNamespaces().containsKey(nameSpaceIme)) {
                            at.addNamespace(nameSpaceIme, nameSpaces.getNamespaces().get(nameSpaceIme));
                        }
                    }
                    stack.push(at);
                    ends.push(addToCorrectFormat(get));
                }
                if (lineObshto.contains("xmlns")) {
                    //<name xmlns:na="http://www.example.com/name">John Smith</name>
                    //na
                    //http://www.example.com/name
                    String[] split = lineObshto.split(":")[1].split(">")[0].split("\\s+");
                    String link = lineObshto.split("=")[1].split(">")[0];
                    String nameSpaceAtr = lineObshto.split("=")[0].split(":")[1];
                        if (lineObshto.contains("/") && lineObshto.contains("</")) {
                            map.get(id).get(map.get(id).size() - 1).addNamespace(nameSpaceAtr, link);
                        } else {
                            stack.peek().addNamespace(nameSpaceAtr, link);
                        }
                        nameSpaces.addNamespace(nameSpaceAtr, link);

                }
            }
        } catch (IOException e) {
            File file = new File("/src", "");
            throw new RuntimeException(e);
        }
        XML xml = new XML(rootElem, elemName);
        xml.setMap(map);
        xml.setPath(fileName);
        return xml;
    }

    /***
     * This private static method extracts the attribute name from a given line of XML content.
     * @param lineObshto  The line of XML content to be parsed for the attribute name.
     * @return  The extracted attribute name as a String.
     */
    private static String getAtrName(String lineObshto) {
        if (lineObshto.contains("xmlns"))
            return lineObshto.split("\\s+")[0].replace("<", "");
        else if(lineObshto.contains(":"))
            return lineObshto.split(":")[1].replace(">", "");
        else
            return lineObshto.split("<")[1].replace(">", "");

    }

    /***
     *  This private static method extracts the element name from a given line of XML content.
     * @param nextLine  The line of XML content to be parsed for the element name.
     * @return The extracted element name as a String.
     */
    private static String getElemName(String nextLine) {
        return nextLine.split(" ")[0].split("<")[1];
    }

    /***
     * This private static method extracts the element ID from the first occurrence of double quotes in a given line of XML content.
     * @param nextLine The line of XML content to be parsed for the element ID.
     * @return The extracted element ID as a String, or an empty string if no ID is found.
     */
    private static String getIdFirst(String nextLine) {
        return nextLine.substring(nextLine.indexOf("\"") + 1, nextLine.lastIndexOf("\""));
    }

    /***
     * This private static method handles connecting child elements with their parent element and associating attributes during parsing.
     * @param stack Deque containing Attribute objects representing nested elements and their attributes.
     * @param toEnd The expected closing tag for the current element.
     * @param ends A Deque containing expected closing tags for elements on the stack.
     * @param map A Map storing element IDs and their corresponding attribute lists.
     * @param id The ID of the current element being processed.
     */
    private static void connectWithPreviousNode(Deque<Attribute> stack, String toEnd, Deque<String> ends, Map<String, List<Attribute>> map, String id) {
        Attribute last = stack.peek();
        if (toEnd.equals(ends.peek())) {
            stack.pop();
            ends.pop();
            if (stack.isEmpty()) {
                map.get(id).add(last);
            } else {
                stack.peek().addAttribute(last);
            }
        }
    }

    /***
     * This private static method extracts the element ID and adds a new entry to the attribute map for the element.
     * @param lineObshto The line of XML content to be parsed for the element ID.
     * @param map  A Map storing element IDs and their corresponding attribute lists.
     * @return  The extracted element ID as a String.
     */
    private static String getId(String lineObshto, Map<String, List<Attribute>> map) {
        String id = getIdFirst(lineObshto);
        map.put(id, new ArrayList<>());
        return id;
    }

    /***
     * This private static method constructs the closing tag for a given element name.
     * @param string The element name for which to construct the closing tag.
     * @return The constructed closing tag as a String in the format "</elementName>".
     */
    private static String addToCorrectFormat(String string) {
        return "</" + string + ">";
    }

}
