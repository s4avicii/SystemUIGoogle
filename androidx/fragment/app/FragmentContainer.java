package androidx.fragment.app;

import android.graphics.Typeface;
import android.view.View;

public abstract class FragmentContainer {
    public abstract View onFindViewById(int i);

    public abstract void onFontRetrievalFailed(int i);

    public abstract void onFontRetrieved(Typeface typeface, boolean z);

    public abstract boolean onHasView();
}
