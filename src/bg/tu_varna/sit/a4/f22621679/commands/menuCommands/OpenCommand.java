package bg.tu_varna.sit.a4.f22621679.commands.menuCommands;

import bg.tu_varna.sit.a4.f22621679.commands.Command;
import bg.tu_varna.sit.a4.f22621679.engine.EngineImpl;
import bg.tu_varna.sit.a4.f22621679.modules.XML;
import bg.tu_varna.sit.a4.f22621679.parser.XMLParser;

import java.io.File;
import java.io.IOException;

public class OpenCommand extends Command {
    private EngineImpl engine;
    private XMLParser xmlParser;
    public OpenCommand(EngineImpl engine, XMLParser xmlParser) {
        this.engine = engine;
        this.xmlParser = xmlParser;
    }

    /***
     * Executes the "open" command functionality.
     * @param data An array of Strings representing the user's command and potentially additional arguments.
     * @param xml An XML object (potentially empty) on which the command will operate.
     */
    @Override
    public   void execute(String[] data, XML xml)  {
        File file=new File(data[1]);
        if(file.exists()){
            System.out.println("Successfully opened "+data[1]);
             XML xml1=xmlParser.parse(data[1]);
            engine.setXml(xml1);
            return;
        }
        try {
            file.createNewFile();
           xml.setPath(data[1]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
