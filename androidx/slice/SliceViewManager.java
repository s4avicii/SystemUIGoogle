package androidx.slice;

import android.net.Uri;

public abstract class SliceViewManager {

    public interface SliceCallback {
    }

    public abstract void pinSlice(Uri uri);

    public abstract void unpinSlice(Uri uri);
}
