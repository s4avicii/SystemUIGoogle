package androidx.recyclerview.widget;

public final class OpReorderer {
    public final Callback mCallback;

    public interface Callback {
    }

    public OpReorderer(Callback callback) {
        this.mCallback = callback;
    }
}
