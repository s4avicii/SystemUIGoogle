package okio;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import java.nio.ByteBuffer;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;
import okio.internal.BufferKt;

/* compiled from: RealBufferedSource.kt */
public final class RealBufferedSource implements BufferedSource {
    public final Buffer bufferField = new Buffer();
    public boolean closed;
    public final Source source;

    public final long read(Buffer buffer, long j) {
        if (!this.closed) {
            Buffer buffer2 = this.bufferField;
            Objects.requireNonNull(buffer2);
            if (buffer2.size == 0 && this.source.read(this.bufferField, 8192) == -1) {
                return -1;
            }
            Buffer buffer3 = this.bufferField;
            Objects.requireNonNull(buffer3);
            return this.bufferField.read(buffer, Math.min(8192, buffer3.size));
        }
        throw new IllegalStateException("closed".toString());
    }

    public final void close() {
        if (!this.closed) {
            this.closed = true;
            this.source.close();
            Buffer buffer = this.bufferField;
            Objects.requireNonNull(buffer);
            buffer.skip(buffer.size);
        }
    }

    public final long indexOfElement(ByteString byteString) {
        if (!this.closed) {
            long j = 0;
            while (true) {
                long indexOfElement = this.bufferField.indexOfElement(byteString, j);
                if (indexOfElement != -1) {
                    return indexOfElement;
                }
                Buffer buffer = this.bufferField;
                Objects.requireNonNull(buffer);
                long j2 = buffer.size;
                if (this.source.read(this.bufferField, 8192) == -1) {
                    return -1;
                }
                j = Math.max(j, j2);
            }
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public final boolean isOpen() {
        return !this.closed;
    }

    public final boolean request(long j) {
        boolean z;
        if (j >= 0) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            throw new IllegalArgumentException(Intrinsics.stringPlus("byteCount < 0: ", Long.valueOf(j)).toString());
        } else if (!this.closed) {
            do {
                Buffer buffer = this.bufferField;
                Objects.requireNonNull(buffer);
                if (buffer.size >= j) {
                    return true;
                }
            } while (this.source.read(this.bufferField, 8192) != -1);
            return false;
        } else {
            throw new IllegalStateException("closed".toString());
        }
    }

    public final int select(Options options) {
        if (!this.closed) {
            while (true) {
                int selectPrefix = BufferKt.selectPrefix(this.bufferField, options, true);
                if (selectPrefix == -2) {
                    if (this.source.read(this.bufferField, 8192) == -1) {
                        break;
                    }
                } else if (selectPrefix != -1) {
                    ByteString byteString = options.byteStrings[selectPrefix];
                    Objects.requireNonNull(byteString);
                    this.bufferField.skip((long) byteString.getSize$external__okio__android_common__okio_lib());
                    return selectPrefix;
                }
            }
            return -1;
        }
        throw new IllegalStateException("closed".toString());
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("buffer(");
        m.append(this.source);
        m.append(')');
        return m.toString();
    }

    public RealBufferedSource(InputStreamSource inputStreamSource) {
        this.source = inputStreamSource;
    }

    public final int read(ByteBuffer byteBuffer) {
        Buffer buffer = this.bufferField;
        Objects.requireNonNull(buffer);
        if (buffer.size == 0 && this.source.read(this.bufferField, 8192) == -1) {
            return -1;
        }
        return this.bufferField.read(byteBuffer);
    }
}
