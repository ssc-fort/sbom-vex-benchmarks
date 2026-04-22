package example.app;

import example.lib1.Greeter;

/**
 * Negative test case — no reachability to the vulnerable class.
 *
 * This app depends on lib1 (which brings in log4j:1.2.17 transitively),
 * but only calls Greeter.greet(). The call graph never reaches SocketServer
 * or SocketAppender.
 *
 * A correct scanner should flag the vulnerable artifact in the dependency tree
 * but should NOT report an exploitable call path to the vulnerable class.
 * Flagging a reachable call path here is a false positive.
 */
public class NegativeScenario {

    public static void run() {
        Greeter greeter = new Greeter();
        String message = greeter.greet("world");
        System.out.println("[negative] " + message);
    }
}
