package com.android.systemui.controls.management;

import java.util.function.Consumer;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity$loadControls$1$2<T> implements Consumer {
    public final /* synthetic */ ControlsFavoritingActivity this$0;

    public ControlsFavoritingActivity$loadControls$1$2(ControlsFavoritingActivity controlsFavoritingActivity) {
        this.this$0 = controlsFavoritingActivity;
    }

    public final void accept(Object obj) {
        this.this$0.cancelLoadRunnable = (Runnable) obj;
    }
}
