package example.app;

import org.apache.logging.log4j.core.net.server.TcpSocketServer;

import java.io.IOException;

/**
 * Static (explicit) access to the vulnerable class.
 *
 * The concrete type TcpSocketServer is named directly at the call site.
 * Reachable by any static analysis tool — a simple grep or import scan suffices.
 *
 * CVE-2017-5645: TcpSocketServer deserializes log events from untrusted network
 * sources without validation, enabling remote code execution.
 * Fixed in log4j-core 2.8.2 (apache/logging-log4j2@5dcc192).
 */
public class StaticScenario {

    public static void run() {
        System.out.println("[static] Instantiating TcpSocketServer directly");
        try {
            TcpSocketServer<?> server = TcpSocketServer.createSerializedSocketServer(0);
            System.out.println("[static] TcpSocketServer instance: " + server.getClass().getName());
            server.shutdown();
        } catch (IOException e) {
            System.out.println("[static] TcpSocketServer created, caught expected IO: " + e.getMessage());
        }
    }
}
