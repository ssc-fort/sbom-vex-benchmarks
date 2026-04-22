package example.app;

import example.lib1.AppenderFactory;

import java.io.IOException;

/**
 * Dynamic dispatch access to the vulnerable class.
 *
 * The app only references the Runnable interface — the concrete type (TcpSocketServer)
 * is never named here. lib1's AppenderFactory constructs the TcpSocketServer instance
 * and returns it as Runnable. Any call on the Runnable is resolved by the JVM at
 * runtime via virtual dispatch to TcpSocketServer (via AbstractSocketServer).
 *
 * CHA (Class Hierarchy Analysis) can still reveal this: it follows the virtual call
 * on Runnable and finds TcpSocketServer as a reachable implementing type.
 * A naive grep or import scan of this file would not detect the vulnerability.
 */
public class DynamicScenario {

    public static void run() {
        System.out.println("[dynamic] Obtaining socket server from lib1 factory");
        try {
            // Call site only knows Runnable — concrete type TcpSocketServer is not named here
            Runnable server = AppenderFactory.createSocketServer(0);
            System.out.println("[dynamic] Server runtime type: " + server.getClass().getName());
        } catch (IOException e) {
            System.out.println("[dynamic] Server created, caught expected IO: " + e.getMessage());
        }
    }
}
