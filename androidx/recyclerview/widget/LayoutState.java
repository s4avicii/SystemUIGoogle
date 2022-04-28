package androidx.recyclerview.widget;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

public final class LayoutState {
    public int mAvailable;
    public int mCurrentPosition;
    public int mEndLine = 0;
    public boolean mInfinite;
    public int mItemDirection;
    public int mLayoutDirection;
    public boolean mRecycle = true;
    public int mStartLine = 0;
    public boolean mStopInFocusable;

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("LayoutState{mAvailable=");
        m.append(this.mAvailable);
        m.append(", mCurrentPosition=");
        m.append(this.mCurrentPosition);
        m.append(", mItemDirection=");
        m.append(this.mItemDirection);
        m.append(", mLayoutDirection=");
        m.append(this.mLayoutDirection);
        m.append(", mStartLine=");
        m.append(this.mStartLine);
        m.append(", mEndLine=");
        return Insets$$ExternalSyntheticOutline0.m11m(m, this.mEndLine, '}');
    }
}
