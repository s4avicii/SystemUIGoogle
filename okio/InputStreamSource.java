package okio;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.io.IOException;
import java.io.InputStream;
import kotlin.text.StringsKt__StringsKt;

/* compiled from: JvmOkio.kt */
public final class InputStreamSource implements Source {
    public final InputStream input;
    public final Timeout timeout;

    public final long read(Buffer buffer, long j) {
        boolean z;
        boolean z2 = false;
        try {
            this.timeout.throwIfReached();
            Segment writableSegment$external__okio__android_common__okio_lib = buffer.writableSegment$external__okio__android_common__okio_lib(1);
            int read = this.input.read(writableSegment$external__okio__android_common__okio_lib.data, writableSegment$external__okio__android_common__okio_lib.limit, (int) Math.min(8192, (long) (8192 - writableSegment$external__okio__android_common__okio_lib.limit)));
            if (read != -1) {
                writableSegment$external__okio__android_common__okio_lib.limit += read;
                long j2 = (long) read;
                buffer.size += j2;
                return j2;
            } else if (writableSegment$external__okio__android_common__okio_lib.pos != writableSegment$external__okio__android_common__okio_lib.limit) {
                return -1;
            } else {
                buffer.head = writableSegment$external__okio__android_common__okio_lib.pop();
                SegmentPool.recycle(writableSegment$external__okio__android_common__okio_lib);
                return -1;
            }
        } catch (AssertionError e) {
            int i = Okio__JvmOkioKt.$r8$clinit;
            if (e.getCause() != null) {
                String message = e.getMessage();
                if (message == null) {
                    z = false;
                } else {
                    z = StringsKt__StringsKt.contains$default(message, "getsockname failed");
                }
                if (z) {
                    z2 = true;
                }
            }
            if (z2) {
                throw new IOException(e);
            }
            throw e;
        }
    }

    public final void close() {
        this.input.close();
    }

    public InputStreamSource(InputStream inputStream, Timeout timeout2) {
        this.input = inputStream;
        this.timeout = timeout2;
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("source(");
        m.append(this.input);
        m.append(')');
        return m.toString();
    }
}
