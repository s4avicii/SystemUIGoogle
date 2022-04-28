package com.google.android.systemui.gamedashboard;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.android.p012wm.shell.C1777R;
import java.util.function.Consumer;

public final class FloatingEntryButton {
    public final int mButtonHeight;
    public boolean mCanShow = true;
    public final Context mContext;
    public final ImageView mEntryPointButton;
    public final View mFloatingView;
    public boolean mIsShowing = false;
    public final int mMargin;
    public Consumer<Boolean> mVisibilityChangedCallback;
    public final WindowManager mWindowManager;

    public final boolean hide() {
        if (!this.mIsShowing) {
            return false;
        }
        this.mWindowManager.removeViewImmediate(this.mFloatingView);
        this.mIsShowing = false;
        Consumer<Boolean> consumer = this.mVisibilityChangedCallback;
        if (consumer == null) {
            return true;
        }
        consumer.accept(Boolean.FALSE);
        return true;
    }

    public FloatingEntryButton(Context context) {
        this.mContext = context;
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        View inflate = LayoutInflater.from(context).inflate(C1777R.layout.game_dashboard_floating_entry_point, (ViewGroup) null);
        this.mFloatingView = inflate;
        inflate.setVisibility(0);
        ImageView imageView = (ImageView) inflate.findViewById(C1777R.C1779id.game_dashboard_entry_button);
        this.mEntryPointButton = imageView;
        imageView.setVisibility(0);
        Resources resources = context.getResources();
        this.mMargin = Math.max(resources.getDimensionPixelSize(C1777R.dimen.game_dashboard_floating_entry_point_margin), resources.getDimensionPixelSize(C1777R.dimen.rounded_corner_content_padding));
        this.mButtonHeight = resources.getDimensionPixelSize(C1777R.dimen.game_dashboard_floating_entry_point_height);
    }
}
