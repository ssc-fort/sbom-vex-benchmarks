package example.lib1;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Dummy class. Uses log4j2 for basic logging only — no access to vulnerable components.
 */
public class Greeter {

    private static final Logger logger = LogManager.getLogger(Greeter.class);

    public String greet(String name) {
        logger.info("greet called for: {}", name);
        return "Hello, " + name + "!";
    }
}
