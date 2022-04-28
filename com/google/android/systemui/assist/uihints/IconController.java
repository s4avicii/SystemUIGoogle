package com.google.android.systemui.assist.uihints;

import android.app.PendingIntent;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSFooterViewController$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.input.TouchActionRegion;
import java.util.Objects;
import java.util.Optional;

public final class IconController implements NgaMessageHandler.KeyboardInfoListener, NgaMessageHandler.ZerostateInfoListener, ConfigurationController.ConfigurationListener, TouchActionRegion {
    public boolean mHasAccurateLuma;
    public final KeyboardIconView mKeyboardIcon;
    public boolean mKeyboardIconRequested;
    public PendingIntent mOnKeyboardIconTap;
    public PendingIntent mOnZerostateIconTap;
    public final ZeroStateIconView mZeroStateIcon;
    public boolean mZerostateIconRequested;

    public final void onHideKeyboard() {
        this.mKeyboardIconRequested = false;
        this.mOnKeyboardIconTap = null;
        maybeUpdateIconVisibility(this.mKeyboardIcon, false);
    }

    public final void onHideZerostate() {
        this.mZerostateIconRequested = false;
        this.mOnZerostateIconTap = null;
        maybeUpdateIconVisibility(this.mZeroStateIcon, false);
    }

    public final Optional<Region> getTouchActionRegion() {
        Region region = new Region();
        if (this.mKeyboardIcon.getVisibility() == 0) {
            Rect rect = new Rect();
            this.mKeyboardIcon.getHitRect(rect);
            region.union(rect);
        }
        if (this.mZeroStateIcon.getVisibility() == 0) {
            Rect rect2 = new Rect();
            this.mZeroStateIcon.getHitRect(rect2);
            region.union(rect2);
        }
        if (region.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(region);
    }

    public final void onDensityOrFontScaleChanged() {
        KeyboardIconView keyboardIconView = this.mKeyboardIcon;
        Objects.requireNonNull(keyboardIconView);
        keyboardIconView.mKeyboardIcon.setImageDrawable(keyboardIconView.getContext().getDrawable(C1777R.C1778drawable.ic_keyboard));
        ZeroStateIconView zeroStateIconView = this.mZeroStateIcon;
        Objects.requireNonNull(zeroStateIconView);
        zeroStateIconView.mZeroStateIcon.setImageDrawable(zeroStateIconView.getContext().getDrawable(C1777R.C1778drawable.ic_explore));
    }

    public final void onShowKeyboard(PendingIntent pendingIntent) {
        boolean z;
        if (pendingIntent != null) {
            z = true;
        } else {
            z = false;
        }
        this.mKeyboardIconRequested = z;
        this.mOnKeyboardIconTap = pendingIntent;
        maybeUpdateIconVisibility(this.mKeyboardIcon, z);
    }

    public final void onShowZerostate(PendingIntent pendingIntent) {
        boolean z;
        if (pendingIntent != null) {
            z = true;
        } else {
            z = false;
        }
        this.mZerostateIconRequested = z;
        this.mOnZerostateIconTap = pendingIntent;
        maybeUpdateIconVisibility(this.mZeroStateIcon, z);
    }

    public IconController(ViewGroup viewGroup, ConfigurationController configurationController) {
        KeyboardIconView keyboardIconView = (KeyboardIconView) viewGroup.findViewById(C1777R.C1779id.keyboard);
        this.mKeyboardIcon = keyboardIconView;
        keyboardIconView.setOnClickListener(new IconController$$ExternalSyntheticLambda0(this, 0));
        ZeroStateIconView zeroStateIconView = (ZeroStateIconView) viewGroup.findViewById(C1777R.C1779id.zerostate);
        this.mZeroStateIcon = zeroStateIconView;
        zeroStateIconView.setOnClickListener(new QSFooterViewController$$ExternalSyntheticLambda0(this, 1));
        configurationController.addCallback(this);
    }

    public final void maybeUpdateIconVisibility(FrameLayout frameLayout, boolean z) {
        boolean z2;
        boolean z3 = true;
        int i = 0;
        if (frameLayout.getVisibility() == 0) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (!z || !this.mHasAccurateLuma) {
            z3 = false;
        }
        if (z2 != z3) {
            if (!z3) {
                i = 8;
            }
            frameLayout.setVisibility(i);
        }
    }
}
