package bg.tu_varna.sit.a4.f22621679.commands.menuCommands;

import bg.tu_varna.sit.a4.f22621679.commands.Command;
import bg.tu_varna.sit.a4.f22621679.modules.XML;

import java.io.FileWriter;
import java.io.IOException;

public class SaveCommand extends Command {
    /***
     * A concrete implementation of the Command interface specifically designed for handling the "save" command.
     * Executes the "save" command functionality, saving the content of the XML object to its previously opened file (if any).
     * @param data  An array of Strings representing the user's command
     * @param xml  The XML object whose content will be saved.
     */
    @Override
    public  void execute(String[] data, XML xml) {
        String path = xml.getPath();
        try (FileWriter fw = new FileWriter(path)) {
            fw.write(xml.write());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
