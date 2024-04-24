import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLMaker implements XMLCreator {

    private String rootElem;
    private String atrName;
    private XMLParser xmlParser = new XMLParser();
    private String fileName="XML.txt";

    @Override
    public XMLParser creteXML()  {
        setRootElem();
        setStaticName();
        assignXMLattributes();
        return this.xmlParser;

    }


    private void setRootElem( ) {
        try (FileReader fileReader = new FileReader(fileName)) {
            Scanner scanner = new Scanner(fileReader);
            String header = scanner.nextLine().trim().split("<")[1].split(">")[0];
            this.xmlParser.setHeader(header);
            this.rootElem=header;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setStaticName(){
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


    private void assignXMLattributes() {
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
                       Pattern pattern = Pattern.compile("<\\w+\\s+id=”(\\w+)”>");
                       Matcher matcher = pattern.matcher(s);
                        if (matcher.find()) {
                            String id = matcher.group(1);
                            xml.setId(id);
                        } else {
                            throw new IllegalArgumentException("No attribute found!");
                        }
                    } else {
                        Pattern pattern = Pattern.compile("<(\\w+)>(.*?)</\\1>"); //
                        Matcher  matcher = pattern.matcher(s);
                        if (matcher.find()) {
                            String atr = matcher.group(1);
                            String value = matcher.group(2);
                            xml.getAttributes().put(atr,value);//slagame v mapa name->John Smith
                        }
                    }
                    s= scanner.nextLine().trim();
                    if (s.equals(toEnd)) {
                        break;
                    }
                }
                this.xmlParser.getEntity().add(xml);
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

    public String getFileName() {
        return fileName;
    }
}
