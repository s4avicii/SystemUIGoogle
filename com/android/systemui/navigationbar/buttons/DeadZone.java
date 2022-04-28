package com.android.systemui.navigationbar.buttons;

import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.util.FloatProperty;
import android.util.Slog;
import android.view.MotionEvent;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dependency;
import com.android.systemui.navigationbar.NavigationBarController;
import com.android.systemui.navigationbar.NavigationBarView;
import java.util.Objects;

public final class DeadZone {
    public static final C09341 FLASH_PROPERTY = new FloatProperty<DeadZone>() {
        public final Object get(Object obj) {
            DeadZone deadZone = (DeadZone) obj;
            Objects.requireNonNull(deadZone);
            return Float.valueOf(deadZone.mFlashFrac);
        }

        public final void setValue(Object obj, float f) {
            DeadZone deadZone = (DeadZone) obj;
            Objects.requireNonNull(deadZone);
            deadZone.mFlashFrac = f;
            deadZone.mNavigationBarView.postInvalidate();
        }
    };
    public final C09352 mDebugFlash = new Runnable() {
        public final void run() {
            ObjectAnimator.ofFloat(DeadZone.this, DeadZone.FLASH_PROPERTY, new float[]{1.0f, 0.0f}).setDuration(150).start();
        }
    };
    public int mDecay;
    public final int mDisplayId;
    public int mDisplayRotation;
    public float mFlashFrac = 0.0f;
    public int mHold;
    public long mLastPokeTime;
    public final NavigationBarController mNavBarController;
    public final NavigationBarView mNavigationBarView;
    public boolean mShouldFlash;
    public int mSizeMax;
    public int mSizeMin;
    public boolean mVertical;

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        boolean z;
        if (motionEvent.getToolType(0) == 3) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 4) {
            this.mLastPokeTime = motionEvent.getEventTime();
            if (this.mShouldFlash) {
                this.mNavigationBarView.postInvalidate();
            }
            return true;
        }
        if (action == 0) {
            this.mNavBarController.touchAutoDim(this.mDisplayId);
            int size = (int) getSize(motionEvent.getEventTime());
            if (!this.mVertical ? motionEvent.getY() >= ((float) size) : this.mDisplayRotation != 3 ? motionEvent.getX() >= ((float) size) : motionEvent.getX() <= ((float) (this.mNavigationBarView.getWidth() - size))) {
                z = false;
            } else {
                z = true;
            }
            if (z) {
                StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("consuming errant click: (");
                m.append(motionEvent.getX());
                m.append(",");
                m.append(motionEvent.getY());
                m.append(")");
                Slog.v("DeadZone", m.toString());
                if (this.mShouldFlash) {
                    this.mNavigationBarView.post(this.mDebugFlash);
                    this.mNavigationBarView.postInvalidate();
                }
                return true;
            }
        }
        return false;
    }

    public final float getSize(long j) {
        int i;
        int i2 = this.mSizeMax;
        if (i2 == 0) {
            return 0.0f;
        }
        long j2 = j - this.mLastPokeTime;
        int i3 = this.mHold;
        int i4 = this.mDecay;
        if (j2 > ((long) (i3 + i4))) {
            i = this.mSizeMin;
        } else if (j2 < ((long) i3)) {
            return (float) i2;
        } else {
            float f = (float) i2;
            i = (int) MotionController$$ExternalSyntheticOutline0.m7m((float) this.mSizeMin, f, ((float) (j2 - ((long) i3))) / ((float) i4), f);
        }
        return (float) i;
    }

    public final void onConfigurationChanged(int i) {
        this.mDisplayRotation = i;
        Resources resources = this.mNavigationBarView.getResources();
        this.mHold = resources.getInteger(C1777R.integer.navigation_bar_deadzone_hold);
        this.mDecay = resources.getInteger(C1777R.integer.navigation_bar_deadzone_decay);
        this.mSizeMin = resources.getDimensionPixelSize(C1777R.dimen.navigation_bar_deadzone_size);
        this.mSizeMax = resources.getDimensionPixelSize(C1777R.dimen.navigation_bar_deadzone_size_max);
        boolean z = true;
        if (resources.getInteger(C1777R.integer.navigation_bar_deadzone_orientation) != 1) {
            z = false;
        }
        this.mVertical = z;
        this.mShouldFlash = resources.getBoolean(C1777R.bool.config_dead_zone_flash);
        this.mFlashFrac = 0.0f;
        this.mNavigationBarView.postInvalidate();
    }

    public DeadZone(NavigationBarView navigationBarView) {
        this.mNavigationBarView = navigationBarView;
        this.mNavBarController = (NavigationBarController) Dependency.get(NavigationBarController.class);
        this.mDisplayId = navigationBarView.getContext().getDisplayId();
        onConfigurationChanged(0);
    }
}
