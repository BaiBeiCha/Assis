package org.baibei.tools;

public class ThreadTools {

    public static Thread findThreadByName(String threadName) {
        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.getName().equals(threadName)) {
                return thread;
            }
        }
        return null;
    }
}
