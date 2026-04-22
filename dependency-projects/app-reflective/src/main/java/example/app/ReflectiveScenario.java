package example.app;

import java.lang.reflect.Method;

/**
 * Reflective access to the vulnerable class.
 *
 * The vulnerable class name appears only as a string literal — there is no import,
 * no type reference, and no static call site. The class is loaded entirely at
 * runtime via the reflection API.
 *
 * CHA cannot resolve this: it has no visibility into runtime string values.
 * Detection requires either string-tracking analysis, dynamic analysis, or
 * specialised reflection-aware call graph construction (e.g. TamiFlex, SOLAR).
 *
 * CVE-2017-5645: TcpSocketServer deserializes log events from untrusted network
 * sources without validation, enabling remote code execution.
 * Fixed in log4j-core 2.8.2 (apache/logging-log4j2@5dcc192).
 */
public class ReflectiveScenario {

    private static final String VULNERABLE_CLASS = "org.apache.logging.log4j.core.net.server.TcpSocketServer";

    public static void run() {
        System.out.println("[reflective] Loading class by name: " + VULNERABLE_CLASS);
        try {
            Class<?> clazz = Class.forName(VULNERABLE_CLASS);
            System.out.println("[reflective] Loaded: " + clazz.getName());

            // Inspect the static factory method without invoking it
            Method factory = clazz.getMethod("createSerializedSocketServer", int.class);
            System.out.println("[reflective] Factory method found: " + factory);
        } catch (ClassNotFoundException e) {
            System.err.println("[reflective] Class not found: " + e.getMessage());
        } catch (NoSuchMethodException e) {
            System.err.println("[reflective] Method not found: " + e.getMessage());
        }
    }
}
