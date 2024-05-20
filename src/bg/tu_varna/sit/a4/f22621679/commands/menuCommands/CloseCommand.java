package bg.tu_varna.sit.a4.f22621679.commands.menuCommands;

import bg.tu_varna.sit.a4.f22621679.commands.Command;
import bg.tu_varna.sit.a4.f22621679.modules.XML;

public class CloseCommand extends Command {

    /***
     * prints that a file is successfully closed and resets the path
     */
    @Override
    public void execute(String[] data, XML xml) {
        System.out.println("Successfully closed "+xml.getPath());
        xml.setPath(null);
    }
}
