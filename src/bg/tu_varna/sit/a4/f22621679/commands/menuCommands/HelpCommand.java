package bg.tu_varna.sit.a4.f22621679.commands.menuCommands;

import bg.tu_varna.sit.a4.f22621679.commands.Command;
import bg.tu_varna.sit.a4.f22621679.modules.XML;

public class HelpCommand extends Command {

    /***
     * Prints a list of supported commands and their descriptions.
     * @param data
     * @param xml
     */
    @Override
    public  void execute(String[] data, XML xml) {
        System.out.println("The following commands are supported:\n" +
                "                open <file>     opens <file>\n" +
                "                close           closes currently opened file\n" +
                "                save            saves the currently open file\n" +
                "                saveas <file>   saves the currently open file in <file>\n" +
                "                help            prints this information\n" +
                "                exit            exists the program\n" +
                "                print           prints the xml file\n" +
                "                select          retrieves the value of an attribute based on the given element ID and attribute key\n" +
                "                set             assigns a value to an attribute\n" +
                "                children        displays a list of attributes of nested elements\n" +
                "                child           accesses the nth child element\n" +
                "                text            accesses the text content of an element\n" +
                "                delete          deletes an element attribute based on the key\n" +
                "                newchild        adds a new child element. The new element does not have any attributes, except for the ID\n" +
                "                xpath           Performs simple XPath queries on the given element and printing the result");
    }
}
