package androidx.slice.compat;

import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.net.Uri;
import androidx.slice.SliceItemHolder;
import kotlinx.coroutines.internal.Symbol;

public final class SliceProviderCompat {

    /* renamed from: androidx.slice.compat.SliceProviderCompat$2 */
    public final class C03502 {
        public static final Symbol NULL = new Symbol("NULL");
        public static final Symbol UNINITIALIZED = new Symbol("UNINITIALIZED");

        public void handle(SliceItemHolder sliceItemHolder) {
            throw null;
        }
    }

    public static class ProviderHolder implements AutoCloseable {
        public final ContentProviderClient mProvider;

        public final void close() {
            ContentProviderClient contentProviderClient = this.mProvider;
            if (contentProviderClient != null) {
                contentProviderClient.close();
            }
        }

        public ProviderHolder(ContentProviderClient contentProviderClient) {
            this.mProvider = contentProviderClient;
        }
    }

    public static ProviderHolder acquireClient(ContentResolver contentResolver, Uri uri) {
        ContentProviderClient acquireUnstableContentProviderClient = contentResolver.acquireUnstableContentProviderClient(uri);
        if (acquireUnstableContentProviderClient != null) {
            return new ProviderHolder(acquireUnstableContentProviderClient);
        }
        throw new IllegalArgumentException("No provider found for " + uri);
    }
}
