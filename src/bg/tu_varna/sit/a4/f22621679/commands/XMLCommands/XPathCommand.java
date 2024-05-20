package bg.tu_varna.sit.a4.f22621679.commands.XMLCommands;

import bg.tu_varna.sit.a4.f22621679.commands.Command;
import bg.tu_varna.sit.a4.f22621679.modules.XML;

public class XPathCommand extends Command {
    /***
     * A concrete implementation of the Command interface specifically designed for handling the "xpath" command.
     * @param data An array of Strings representing the user's command and potentially additional arguments.
     * @param xml The XML object on which the command will operate.
     */
    @Override
  public  void execute(String[] data, XML xml) {
        StringBuilder sb=new StringBuilder();
        for (int i = 1; i < data.length-1; i++) {
            sb.append(data[i]).append(" ");
        }
        sb.append(data[data.length-1]);
        xml.xPath(sb.toString());
    }
}
