package kotlin.p018io;

import java.io.File;

/* renamed from: kotlin.io.NoSuchFileException */
/* compiled from: Exceptions.kt */
public final class NoSuchFileException extends FileSystemException {
    public NoSuchFileException(File file) {
        super(file, (File) null, "The source file doesn't exist.");
    }
}
