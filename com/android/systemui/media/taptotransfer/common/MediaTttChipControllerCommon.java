package com.android.systemui.media.taptotransfer.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.android.internal.widget.CachingIconView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.media.taptotransfer.common.MediaTttChipState;
import com.android.systemui.util.concurrency.DelayableExecutor;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaTttChipControllerCommon.kt */
public abstract class MediaTttChipControllerCommon<T extends MediaTttChipState> {
    public Runnable cancelChipViewTimeout;
    public final int chipLayoutRes;
    public ViewGroup chipView;
    public final Context context;
    public final DelayableExecutor mainExecutor;
    @SuppressLint({"WrongConstant"})
    public final WindowManager.LayoutParams windowLayoutParams;
    public final WindowManager windowManager;

    public abstract void updateChipView(T t, ViewGroup viewGroup);

    public final void displayChip(T t) {
        ViewGroup viewGroup = this.chipView;
        if (viewGroup == null) {
            View inflate = LayoutInflater.from(this.context).inflate(this.chipLayoutRes, (ViewGroup) null);
            Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
            this.chipView = (ViewGroup) inflate;
        }
        ViewGroup viewGroup2 = this.chipView;
        Intrinsics.checkNotNull(viewGroup2);
        updateChipView(t, viewGroup2);
        if (viewGroup == null) {
            this.windowManager.addView(this.chipView, this.windowLayoutParams);
        }
        Runnable runnable = this.cancelChipViewTimeout;
        if (runnable != null) {
            runnable.run();
        }
        this.cancelChipViewTimeout = this.mainExecutor.executeDelayed(new MediaTttChipControllerCommon$displayChip$1(this), 3000);
    }

    public MediaTttChipControllerCommon(Context context2, WindowManager windowManager2, DelayableExecutor delayableExecutor, int i) {
        this.context = context2;
        this.windowManager = windowManager2;
        this.mainExecutor = delayableExecutor;
        this.chipLayoutRes = i;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.gravity = 49;
        layoutParams.type = 2020;
        layoutParams.flags = 32;
        layoutParams.setTitle("Media Transfer Chip View");
        layoutParams.format = -3;
        layoutParams.setTrustedOverlay();
        this.windowLayoutParams = layoutParams;
    }

    /* renamed from: setIcon$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo9152x42993fc1(T t, ViewGroup viewGroup) {
        int i;
        CachingIconView requireViewById = viewGroup.requireViewById(C1777R.C1779id.app_icon);
        requireViewById.setContentDescription(t.getAppName(this.context));
        Drawable appIcon = t.getAppIcon(this.context);
        if (appIcon != null) {
            i = 0;
        } else {
            i = 8;
        }
        requireViewById.setImageDrawable(appIcon);
        requireViewById.setVisibility(i);
    }
}
