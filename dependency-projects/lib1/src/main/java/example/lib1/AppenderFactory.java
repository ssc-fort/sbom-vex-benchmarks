package example.lib1;

import org.apache.logging.log4j.core.net.server.TcpSocketServer;

import java.io.IOException;

/**
 * Factory that constructs a TcpSocketServer and returns it as Runnable.
 * The library does not invoke any methods on the server — it only provides the instance.
 * The vulnerable call path is exercised by the caller (the app) via dynamic dispatch.
 *
 * TcpSocketServer extends AbstractSocketServer which implements Runnable.
 * The concrete type is never exposed at this API boundary.
 */
public class AppenderFactory {

    public static Runnable createSocketServer(int port) throws IOException {
        return TcpSocketServer.createSerializedSocketServer(port);
    }
}
