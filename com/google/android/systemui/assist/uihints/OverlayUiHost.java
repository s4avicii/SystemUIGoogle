package com.google.android.systemui.assist.uihints;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public final class OverlayUiHost {
    public boolean mAttached = false;
    public boolean mFocusable = false;
    public WindowManager.LayoutParams mLayoutParams;
    public final AssistUIView mRoot;
    public final WindowManager mWindowManager;

    public OverlayUiHost(Context context, TouchOutsideHandler touchOutsideHandler) {
        AssistUIView assistUIView = (AssistUIView) LayoutInflater.from(context).inflate(C1777R.layout.assist_ui, (ViewGroup) null, false);
        this.mRoot = assistUIView;
        Objects.requireNonNull(assistUIView);
        assistUIView.mTouchOutside = touchOutsideHandler;
        this.mWindowManager = (WindowManager) context.getSystemService("window");
    }
}
