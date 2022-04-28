package androidx.activity.contextaware;

import android.content.Context;
import java.util.concurrent.CopyOnWriteArraySet;

public final class ContextAwareHelper {
    public volatile Context mContext;
    public final CopyOnWriteArraySet mListeners = new CopyOnWriteArraySet();
}
