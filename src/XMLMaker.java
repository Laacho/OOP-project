import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLMaker implements XMLCreator{
    @Override
    public void creteXML() throws FileNotFoundException {
        try(FileReader fileReader=new FileReader("NewFile.txt")) {
            Scanner scanner=new Scanner(fileReader);
           while (scanner.hasNext()){
               parseSimpleXML(scanner.nextLine());
           }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    public static void parseSimpleXML(String xmlString) {

        // Define element start and end tags based on your specific XML format
        String startTag = "element";
        String endTag="</"+startTag+">";

        // Track nesting level to identify nested elements
        int nestingLevel = 0;

        StringBuilder currentElement = new StringBuilder();
        boolean inElement = false;

        for (char c : xmlString.toCharArray()) {
            if (c == '<') {
                inElement = true;
                nestingLevel++;
            } else if (c == '>') {
                inElement = false;
                nestingLevel--;

                // Check if element content is not empty and handle based on nesting level
                if (!currentElement.isEmpty()) {
                    if (nestingLevel == 0) {
                        // Top-level element, process content
                        System.out.println("Element Content: " + currentElement.toString().trim());
                    } else {
                        // Nested element, append content with indentation for clarity
                        System.out.printf("%" + (nestingLevel * 2) + "s%s%n", "", currentElement.toString().trim());
                    }
                    currentElement.setLength(0); // Reset for next element
                }
            } else if (inElement) {
                currentElement.append(c);
            }
        }
    }
    private List<String> wordsSplitter(String line){
       Pattern pattern=Pattern.compile("[a-zA-Z]+");
        Matcher matcher= pattern.matcher(line);
        List<String> words=new ArrayList<>();
       while (matcher.find()) {
           words.add(matcher.group());
      }
        return words;
    }

}
