package okio;

import java.io.IOException;
import java.io.InterruptedIOException;

/* compiled from: Timeout.kt */
public class Timeout {
    public void throwIfReached() throws IOException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedIOException("interrupted");
        }
    }
}
