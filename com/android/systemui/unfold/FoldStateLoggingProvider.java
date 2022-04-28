package com.android.systemui.unfold;

import com.android.systemui.statusbar.policy.CallbackController;

/* compiled from: FoldStateLoggingProvider.kt */
public interface FoldStateLoggingProvider extends CallbackController<FoldStateLoggingListener> {

    /* compiled from: FoldStateLoggingProvider.kt */
    public interface FoldStateLoggingListener {
        void onFoldUpdate(FoldStateChange foldStateChange);
    }

    void init();
}
