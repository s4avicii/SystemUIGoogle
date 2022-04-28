package androidx.activity;

import java.util.concurrent.CopyOnWriteArrayList;

public abstract class OnBackPressedCallback {
    public CopyOnWriteArrayList<Cancellable> mCancellables = new CopyOnWriteArrayList<>();
    public boolean mEnabled = false;

    public abstract void handleOnBackPressed();
}
