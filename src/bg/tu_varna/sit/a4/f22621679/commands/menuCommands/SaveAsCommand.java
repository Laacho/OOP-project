package bg.tu_varna.sit.a4.f22621679.commands.menuCommands;

import bg.tu_varna.sit.a4.f22621679.commands.Command;
import bg.tu_varna.sit.a4.f22621679.modules.XML;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveAsCommand extends Command {
    /***
     *  A concrete implementation of the Command interface specifically designed for handling the "saveas" command.
     *  Executes the "saveas" command functionality, saving the content of the XML object to a specified file.
     * @param data  An array of Strings representing the user's command and potentially additional arguments.
     * @param xml The XML object whose content will be saved.
     */
    @Override
    public  void execute(String[] data, XML xml) {
        File file = new File(data[1]);
        try (FileWriter fw = new FileWriter(data[1])) {
            fw.write(xml.write());
            xml.setPath(data[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
