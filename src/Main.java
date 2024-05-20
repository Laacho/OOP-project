import bg.tu_varna.sit.a4.f22621679.engine.Engine;
import bg.tu_varna.sit.a4.f22621679.engine.EngineImpl;

import java.util.*;

public class Main {
    /***
     The program entry point.
     * Create an Engine object using an EngineImpl implementation with a Scanner for user input and starts the engine.
     * @param args Command-line arguments passed to the program
     */
    public static void main(String[] args) {
        Engine engine=new EngineImpl(new Scanner(System.in));
        engine.run();
    }
}
