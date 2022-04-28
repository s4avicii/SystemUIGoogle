package androidx.slice.widget;

public final class SliceViewPolicy {
    public PolicyChangeListener mListener;
    public int mMaxHeight = 0;
    public int mMaxSmallHeight = 0;

    public interface PolicyChangeListener {
    }
}
