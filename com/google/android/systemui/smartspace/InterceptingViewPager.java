package com.google.android.systemui.smartspace;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.viewpager.widget.ViewPager;
import com.android.p012wm.shell.pip.PipTaskOrganizer$$ExternalSyntheticLambda3;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2;
import com.android.systemui.dreams.touch.DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda3;
import com.android.systemui.wmshell.WMShell$7$$ExternalSyntheticLambda2;
import com.google.android.systemui.elmyra.sensors.CHREGestureSensor$$ExternalSyntheticLambda0;

public class InterceptingViewPager extends ViewPager {
    public static final /* synthetic */ int $r8$clinit = 0;
    public boolean mHasPerformedLongPress;
    public boolean mHasPostedLongPress;
    public final Runnable mLongPressCallback;
    public final EventProxy mSuperOnIntercept;
    public final EventProxy mSuperOnTouch;

    public interface EventProxy {
        boolean delegateEvent(MotionEvent motionEvent);
    }

    public InterceptingViewPager(Context context) {
        super(context);
        this.mSuperOnTouch = new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda3(this);
        this.mSuperOnIntercept = new CHREGestureSensor$$ExternalSyntheticLambda0(this);
        this.mLongPressCallback = new WMShell$7$$ExternalSyntheticLambda2(this, 8);
    }

    public final void cancelScheduledLongPress() {
        if (this.mHasPostedLongPress) {
            this.mHasPostedLongPress = false;
            removeCallbacks(this.mLongPressCallback);
        }
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return handleTouchOverride(motionEvent, this.mSuperOnIntercept);
    }

    public final boolean onTouchEvent(MotionEvent motionEvent) {
        return handleTouchOverride(motionEvent, this.mSuperOnTouch);
    }

    public final boolean handleTouchOverride(MotionEvent motionEvent, EventProxy eventProxy) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mHasPerformedLongPress = false;
            if (isLongClickable()) {
                cancelScheduledLongPress();
                this.mHasPostedLongPress = true;
                postDelayed(this.mLongPressCallback, (long) ViewConfiguration.getLongPressTimeout());
            }
        } else if (action == 1 || action == 3) {
            cancelScheduledLongPress();
        }
        if (this.mHasPerformedLongPress) {
            cancelScheduledLongPress();
            return true;
        } else if (!eventProxy.delegateEvent(motionEvent)) {
            return false;
        } else {
            cancelScheduledLongPress();
            return true;
        }
    }

    public InterceptingViewPager(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mSuperOnTouch = new InterceptingViewPager$$ExternalSyntheticLambda0(this);
        this.mSuperOnIntercept = new DreamOverlayTouchMonitor$3$$ExternalSyntheticLambda2(this);
        this.mLongPressCallback = new PipTaskOrganizer$$ExternalSyntheticLambda3(this, 7);
    }
}
