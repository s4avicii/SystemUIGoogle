package com.android.keyguard;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import java.lang.ref.WeakReference;

public class KeyguardMessageArea extends TextView {
    public static final Object ANNOUNCE_TOKEN = new Object();
    public boolean mAltBouncerShowing;
    public boolean mBouncerVisible;
    public ViewGroup mContainer;
    public ColorStateList mDefaultColorState;
    public final Handler mHandler;
    public CharSequence mMessage;
    public ColorStateList mNextMessageColorState = ColorStateList.valueOf(-1);
    public int mTopMargin;

    public static class AnnounceRunnable implements Runnable {
        public final WeakReference<View> mHost;
        public final CharSequence mTextToAnnounce;

        public final void run() {
            View view = this.mHost.get();
            if (view != null) {
                view.announceForAccessibility(this.mTextToAnnounce);
            }
        }

        public AnnounceRunnable(View view, CharSequence charSequence) {
            this.mHost = new WeakReference<>(view);
            this.mTextToAnnounce = charSequence;
        }
    }

    public final void onDensityOrFontScaleChanged() {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(2132017508, new int[]{16842901});
        setTextSize(0, (float) obtainStyledAttributes.getDimensionPixelSize(0, 0));
        obtainStyledAttributes.recycle();
    }

    public final void onThemeChanged() {
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(new int[]{16842806});
        ColorStateList valueOf = ColorStateList.valueOf(obtainStyledAttributes.getColor(0, -65536));
        obtainStyledAttributes.recycle();
        this.mDefaultColorState = valueOf;
        update();
    }

    public final void update() {
        int i;
        CharSequence charSequence = this.mMessage;
        if (TextUtils.isEmpty(charSequence) || (!this.mBouncerVisible && !this.mAltBouncerShowing)) {
            i = 4;
        } else {
            i = 0;
        }
        setVisibility(i);
        setText(charSequence);
        ColorStateList colorStateList = this.mDefaultColorState;
        if (this.mNextMessageColorState.getDefaultColor() != -1) {
            colorStateList = this.mNextMessageColorState;
            this.mNextMessageColorState = ColorStateList.valueOf(-1);
        }
        if (this.mAltBouncerShowing) {
            colorStateList = ColorStateList.valueOf(-1);
        }
        setTextColor(colorStateList);
    }

    public KeyguardMessageArea(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        setLayerType(2, (Paint) null);
        this.mHandler = new Handler(Looper.myLooper());
        onThemeChanged();
    }

    /* JADX WARNING: type inference failed for: r0v2, types: [android.view.View] */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.android.keyguard.KeyguardMessageArea findSecurityMessageDisplay(android.view.View r2) {
        /*
            r0 = 2131428181(0x7f0b0355, float:1.8478E38)
            android.view.View r1 = r2.findViewById(r0)
            com.android.keyguard.KeyguardMessageArea r1 = (com.android.keyguard.KeyguardMessageArea) r1
            if (r1 != 0) goto L_0x0016
            android.view.View r1 = r2.getRootView()
            android.view.View r0 = r1.findViewById(r0)
            r1 = r0
            com.android.keyguard.KeyguardMessageArea r1 = (com.android.keyguard.KeyguardMessageArea) r1
        L_0x0016:
            if (r1 == 0) goto L_0x0019
            return r1
        L_0x0019:
            java.lang.RuntimeException r0 = new java.lang.RuntimeException
            java.lang.String r1 = "Can't find keyguard_message_area in "
            java.lang.StringBuilder r1 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r1)
            java.lang.Class r2 = r2.getClass()
            r1.append(r2)
            java.lang.String r2 = r1.toString()
            r0.<init>(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardMessageArea.findSecurityMessageDisplay(android.view.View):com.android.keyguard.KeyguardMessageArea");
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mContainer = (ViewGroup) getRootView().findViewById(C1777R.C1779id.keyguard_message_area_container);
    }

    public final void setMessage(CharSequence charSequence) {
        if (!TextUtils.isEmpty(charSequence)) {
            this.mMessage = charSequence;
            update();
            Handler handler = this.mHandler;
            Object obj = ANNOUNCE_TOKEN;
            handler.removeCallbacksAndMessages(obj);
            this.mHandler.postAtTime(new AnnounceRunnable(this, getText()), obj, SystemClock.uptimeMillis() + 250);
            return;
        }
        this.mMessage = null;
        update();
    }
}
