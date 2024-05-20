package bg.tu_varna.sit.a4.f22621679.commands;


import bg.tu_varna.sit.a4.f22621679.modules.XML;

public abstract class Command {
    /***
     * An abstract base class representing a command within the XML editor application.
     * Executes the functionality of the specific command.
     * @param data An array of Strings representing the user's command and potentially additional arguments.
     * @param xml The XML object on which the command will operate.
     */
    public abstract void execute(String[] data, XML xml) ;
}
