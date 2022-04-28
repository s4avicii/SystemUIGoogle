package com.android.systemui.keyguard;

import java.util.ArrayList;
import java.util.concurrent.Executor;

public final class DismissCallbackRegistry {
    public final ArrayList<DismissCallbackWrapper> mDismissCallbacks = new ArrayList<>();
    public final Executor mUiBgExecutor;

    public DismissCallbackRegistry(Executor executor) {
        this.mUiBgExecutor = executor;
    }
}
