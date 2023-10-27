package org.kettingpowered.ketting.common.utils;

public class ShortenedStackTrace {

    private final Throwable cause;
    private final StackTraceElement[] stackTrace;
    private final int maxElements;

    public ShortenedStackTrace(Throwable error, int maxElements) {
        this.cause = findCause(error);
        this.stackTrace = cause.getStackTrace();
        this.maxElements = maxElements;
    }

    public static Throwable findCause(Throwable error) {
        while (error.getCause() != null) {
            error = error.getCause();
        }
        return error;
    }

    public void print() {
        if (cause.getMessage() != null || !cause.getMessage().isEmpty())
            System.err.println(cause.getMessage());
        for (int i = 0; i < maxElements; i++) {
            System.err.println(stackTrace[i]);
        }
    }
}
