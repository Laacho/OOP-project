package bg.tu_varna.sit.a4.f22621679.engine;


import bg.tu_varna.sit.a4.f22621679.commands.menuCommands.*;
import bg.tu_varna.sit.a4.f22621679.modules.XML;
import bg.tu_varna.sit.a4.f22621679.parser.XMLParser;
import bg.tu_varna.sit.a4.f22621679.commands.Command;
import bg.tu_varna.sit.a4.f22621679.commands.XMLCommands.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
/***
 * An implementation class for the Engine interface that manages the main loop and user interactions in the XML editor application.
 */
public class EngineImpl implements Engine {

    private final Map<String, Command> methods;
    private XML xml;
    private final Scanner scanner;

    public EngineImpl(  Scanner scanner) {
        this.xml = new XML(null,null);
        this.scanner = scanner;
        this.methods = new HashMap<>();
    }

    /***
     * Populates the internal map with available commands and their corresponding implementations.
     */
    public void fillPolly() throws NoSuchMethodException {
        //specifics
        Command print= new PrintCommand();
        Command select=new SelectCommand();
        Command set=new SetCommand();
        Command children=new ChildrenCommand();
        Command child=new ChildCommand();
        Command text=new TextCommand();
        Command delete=new DeleteCommand();
        Command newChild=new NewChildCommand();
        Command xPath=new XPathCommand();
        //main
        Command open=new OpenCommand(this,new XMLParser());
        Command save=new SaveCommand();
        Command saveas=new SaveAsCommand();
        Command close=new CloseCommand();
        Command help=new HelpCommand();
        methods.put("print", print);
        methods.put("select", select);
        methods.put("set", set);
        methods.put("children", children);
        methods.put("child", child);
        methods.put("text", text);
        methods.put("delete", delete);
        methods.put("newchild", newChild);
        methods.put("xpath", xPath);
        methods.put("open", open);
        methods.put("save", save);
        methods.put("saveas", saveas);
        methods.put("close", close);
        methods.put("help",help);
    }

    /***
     * The main loop of the application engine, reading user input, executing commands, and handling exceptions
     */
    @Override
    public void run() {
        try {
            fillPolly();
            while (true){
                String line=scanner.nextLine();
                String[] tokens =line.split("\\s+");
                if(tokens[0].equalsIgnoreCase("exit")){
                    break;
                }
                if(methods.containsKey(tokens[0])){
                    methods.get(tokens[0]).execute(tokens,xml);
                }
                else System.out.println("Invalid command");
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public void setXml(XML xml) {
        this.xml = xml;
    }
}
