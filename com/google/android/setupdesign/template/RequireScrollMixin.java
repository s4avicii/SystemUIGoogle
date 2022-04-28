package com.google.android.setupdesign.template;

import android.os.Handler;
import android.os.Looper;
import com.google.android.setupcompat.template.Mixin;

public final class RequireScrollMixin implements Mixin {
    public boolean everScrolledToBottom = false;
    public final Handler handler = new Handler(Looper.getMainLooper());
    public boolean requiringScrollToBottom = false;
}
