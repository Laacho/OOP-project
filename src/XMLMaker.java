import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLMaker implements XMLCreator {

    private String rootElem;
    private String atrName;
    private XMLParser xmlParser = new XMLParser();


    @Override
    public void creteXML() throws FileNotFoundException {
        setRootElem("XML.txt", xmlParser);
        setStaticName("XML.txt",xmlParser);
        assignXMLattributes("XML.txt", xmlParser);
        System.out.println();

    }


    private void setRootElem(String fileName, XMLParser xmlParser) {
        try (FileReader fileReader = new FileReader(fileName)) {
            Scanner scanner = new Scanner(fileReader);
            String header = scanner.nextLine().trim().split("<")[1].split(">")[0];
            this.xmlParser.setHeader(header);
            this.rootElem=header;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setStaticName(String fileName,XMLParser xmlParser){
        try(FileReader fileReader = new FileReader(fileName)) {
            Scanner scanner = new Scanner(fileReader);
            scanner.nextLine();
            String attrName = scanner.nextLine().trim();//<person id="1">
            Pattern pattern = Pattern.compile("<(\\w+)");
            Matcher matcher = pattern.matcher(attrName);
            String tagName;
            if (matcher.find()) {
                tagName = matcher.group(1);//person
                XML.setIdName(tagName);
                this.atrName=tagName;
            } else {
                throw new IllegalArgumentException("No attribute found!");
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    private void assignXMLattributes(String fileName, XMLParser xmlParser) {
        try (FileReader fileReader = new FileReader(fileName)) {
            Scanner scanner = new Scanner(fileReader);
            scanner.nextLine();
                String toEnd="</"+this.atrName+">";
                String toReturn="</"+this.rootElem+">";
            while (true) {
                XML xml = new XML();
                String s = scanner.nextLine().trim();
                if( s.equals(toReturn)){
                    break;
                }
                while (true) {
                    if (s.contains("id")) {//person id='0'
                       Pattern pattern = Pattern.compile("<(\\w+)\\s+id=”(\\d+)”>");
                       Matcher matcher = pattern.matcher(s);

                        if (matcher.find()) {
                            String id = matcher.group(2);
                            xml.setId(id);
                        } else {
                            throw new IllegalArgumentException("No attribute found!");
                        }
                    } else {
                        Pattern pattern = Pattern.compile("<(\\w+)>(.*?)</\\1>");
                        Matcher  matcher = pattern.matcher(s);
                        if (matcher.find()) {
                            String atr = matcher.group(1);
                            String value = matcher.group(2);
                            xml.getAttributes().put(atr, value);//slagame v mapa name->John Smith
                        }
                    }
                    s= scanner.nextLine().trim();
                    if (s.equals(toEnd)) {
                        break;
                    }
                }
                xmlParser.getEntity().add(xml);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public String getAtrName() {
        return atrName;
    }

    public String getRootElem() {
        return rootElem;
    }

}
