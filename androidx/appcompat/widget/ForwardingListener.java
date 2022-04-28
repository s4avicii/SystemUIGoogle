package androidx.appcompat.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import androidx.appcompat.view.menu.ShowableListMenu;
import java.util.Objects;

public abstract class ForwardingListener implements View.OnTouchListener, View.OnAttachStateChangeListener {
    public int mActivePointerId;
    public DisallowIntercept mDisallowIntercept;
    public boolean mForwarding;
    public final int mLongPressTimeout;
    public final float mScaledTouchSlop;
    public final View mSrc;
    public final int mTapTimeout;
    public final int[] mTmpLocation = new int[2];
    public TriggerLongPress mTriggerLongPress;

    public class DisallowIntercept implements Runnable {
        public DisallowIntercept() {
        }

        public final void run() {
            ViewParent parent = ForwardingListener.this.mSrc.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }
    }

    public class TriggerLongPress implements Runnable {
        public TriggerLongPress() {
        }

        public final void run() {
            ForwardingListener forwardingListener = ForwardingListener.this;
            Objects.requireNonNull(forwardingListener);
            forwardingListener.clearCallbacks();
            View view = forwardingListener.mSrc;
            if (view.isEnabled() && !view.isLongClickable() && forwardingListener.onForwardingStarted()) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                long uptimeMillis = SystemClock.uptimeMillis();
                MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 3, 0.0f, 0.0f, 0);
                view.onTouchEvent(obtain);
                obtain.recycle();
                forwardingListener.mForwarding = true;
            }
        }
    }

    public abstract ShowableListMenu getPopup();

    public abstract boolean onForwardingStarted();

    public final void onViewAttachedToWindow(View view) {
    }

    public final void onViewDetachedFromWindow(View view) {
        this.mForwarding = false;
        this.mActivePointerId = -1;
        DisallowIntercept disallowIntercept = this.mDisallowIntercept;
        if (disallowIntercept != null) {
            this.mSrc.removeCallbacks(disallowIntercept);
        }
    }

    public final void clearCallbacks() {
        TriggerLongPress triggerLongPress = this.mTriggerLongPress;
        if (triggerLongPress != null) {
            this.mSrc.removeCallbacks(triggerLongPress);
        }
        DisallowIntercept disallowIntercept = this.mDisallowIntercept;
        if (disallowIntercept != null) {
            this.mSrc.removeCallbacks(disallowIntercept);
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0084, code lost:
        if (r4 != 3) goto L_0x0102;
     */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006c  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x010b  */
    /* JADX WARNING: Removed duplicated region for block: B:59:0x010d  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0110  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean onTouch(android.view.View r12, android.view.MotionEvent r13) {
        /*
            r11 = this;
            boolean r12 = r11.mForwarding
            r0 = 3
            r1 = 1
            r2 = 0
            if (r12 == 0) goto L_0x006f
            android.view.View r3 = r11.mSrc
            androidx.appcompat.view.menu.ShowableListMenu r4 = r11.getPopup()
            if (r4 == 0) goto L_0x005f
            boolean r5 = r4.isShowing()
            if (r5 != 0) goto L_0x0016
            goto L_0x005f
        L_0x0016:
            androidx.appcompat.widget.DropDownListView r4 = r4.getListView()
            if (r4 == 0) goto L_0x005f
            boolean r5 = r4.isShown()
            if (r5 != 0) goto L_0x0023
            goto L_0x005f
        L_0x0023:
            android.view.MotionEvent r5 = android.view.MotionEvent.obtainNoHistory(r13)
            int[] r6 = r11.mTmpLocation
            r3.getLocationOnScreen(r6)
            r3 = r6[r2]
            float r3 = (float) r3
            r6 = r6[r1]
            float r6 = (float) r6
            r5.offsetLocation(r3, r6)
            int[] r3 = r11.mTmpLocation
            r4.getLocationOnScreen(r3)
            r6 = r3[r2]
            int r6 = -r6
            float r6 = (float) r6
            r3 = r3[r1]
            int r3 = -r3
            float r3 = (float) r3
            r5.offsetLocation(r6, r3)
            int r3 = r11.mActivePointerId
            boolean r3 = r4.onForwardedEvent(r5, r3)
            r5.recycle()
            int r13 = r13.getActionMasked()
            if (r13 == r1) goto L_0x0058
            if (r13 == r0) goto L_0x0058
            r13 = r1
            goto L_0x0059
        L_0x0058:
            r13 = r2
        L_0x0059:
            if (r3 == 0) goto L_0x005f
            if (r13 == 0) goto L_0x005f
            r13 = r1
            goto L_0x0060
        L_0x005f:
            r13 = r2
        L_0x0060:
            if (r13 != 0) goto L_0x006c
            boolean r13 = r11.onForwardingStopped()
            if (r13 != 0) goto L_0x0069
            goto L_0x006c
        L_0x0069:
            r13 = r2
            goto L_0x0125
        L_0x006c:
            r13 = r1
            goto L_0x0125
        L_0x006f:
            android.view.View r3 = r11.mSrc
            boolean r4 = r3.isEnabled()
            if (r4 != 0) goto L_0x0079
            goto L_0x0102
        L_0x0079:
            int r4 = r13.getActionMasked()
            if (r4 == 0) goto L_0x00d6
            if (r4 == r1) goto L_0x00d2
            r5 = 2
            if (r4 == r5) goto L_0x0088
            if (r4 == r0) goto L_0x00d2
            goto L_0x0102
        L_0x0088:
            int r0 = r11.mActivePointerId
            int r0 = r13.findPointerIndex(r0)
            if (r0 < 0) goto L_0x0102
            float r4 = r13.getX(r0)
            float r13 = r13.getY(r0)
            float r0 = r11.mScaledTouchSlop
            float r5 = -r0
            int r6 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r6 < 0) goto L_0x00c3
            int r5 = (r13 > r5 ? 1 : (r13 == r5 ? 0 : -1))
            if (r5 < 0) goto L_0x00c3
            int r5 = r3.getRight()
            int r6 = r3.getLeft()
            int r5 = r5 - r6
            float r5 = (float) r5
            float r5 = r5 + r0
            int r4 = (r4 > r5 ? 1 : (r4 == r5 ? 0 : -1))
            if (r4 >= 0) goto L_0x00c3
            int r4 = r3.getBottom()
            int r5 = r3.getTop()
            int r4 = r4 - r5
            float r4 = (float) r4
            float r4 = r4 + r0
            int r13 = (r13 > r4 ? 1 : (r13 == r4 ? 0 : -1))
            if (r13 >= 0) goto L_0x00c3
            r13 = r1
            goto L_0x00c4
        L_0x00c3:
            r13 = r2
        L_0x00c4:
            if (r13 != 0) goto L_0x0102
            r11.clearCallbacks()
            android.view.ViewParent r13 = r3.getParent()
            r13.requestDisallowInterceptTouchEvent(r1)
            r13 = r1
            goto L_0x0103
        L_0x00d2:
            r11.clearCallbacks()
            goto L_0x0102
        L_0x00d6:
            int r13 = r13.getPointerId(r2)
            r11.mActivePointerId = r13
            androidx.appcompat.widget.ForwardingListener$DisallowIntercept r13 = r11.mDisallowIntercept
            if (r13 != 0) goto L_0x00e7
            androidx.appcompat.widget.ForwardingListener$DisallowIntercept r13 = new androidx.appcompat.widget.ForwardingListener$DisallowIntercept
            r13.<init>()
            r11.mDisallowIntercept = r13
        L_0x00e7:
            androidx.appcompat.widget.ForwardingListener$DisallowIntercept r13 = r11.mDisallowIntercept
            int r0 = r11.mTapTimeout
            long r4 = (long) r0
            r3.postDelayed(r13, r4)
            androidx.appcompat.widget.ForwardingListener$TriggerLongPress r13 = r11.mTriggerLongPress
            if (r13 != 0) goto L_0x00fa
            androidx.appcompat.widget.ForwardingListener$TriggerLongPress r13 = new androidx.appcompat.widget.ForwardingListener$TriggerLongPress
            r13.<init>()
            r11.mTriggerLongPress = r13
        L_0x00fa:
            androidx.appcompat.widget.ForwardingListener$TriggerLongPress r13 = r11.mTriggerLongPress
            int r0 = r11.mLongPressTimeout
            long r4 = (long) r0
            r3.postDelayed(r13, r4)
        L_0x0102:
            r13 = r2
        L_0x0103:
            if (r13 == 0) goto L_0x010d
            boolean r13 = r11.onForwardingStarted()
            if (r13 == 0) goto L_0x010d
            r13 = r1
            goto L_0x010e
        L_0x010d:
            r13 = r2
        L_0x010e:
            if (r13 == 0) goto L_0x0125
            long r5 = android.os.SystemClock.uptimeMillis()
            r7 = 3
            r8 = 0
            r9 = 0
            r10 = 0
            r3 = r5
            android.view.MotionEvent r0 = android.view.MotionEvent.obtain(r3, r5, r7, r8, r9, r10)
            android.view.View r3 = r11.mSrc
            r3.onTouchEvent(r0)
            r0.recycle()
        L_0x0125:
            r11.mForwarding = r13
            if (r13 != 0) goto L_0x012d
            if (r12 == 0) goto L_0x012c
            goto L_0x012d
        L_0x012c:
            r1 = r2
        L_0x012d:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.ForwardingListener.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public ForwardingListener(View view) {
        this.mSrc = view;
        view.setLongClickable(true);
        view.addOnAttachStateChangeListener(this);
        this.mScaledTouchSlop = (float) ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        int tapTimeout = ViewConfiguration.getTapTimeout();
        this.mTapTimeout = tapTimeout;
        this.mLongPressTimeout = (ViewConfiguration.getLongPressTimeout() + tapTimeout) / 2;
    }

    public boolean onForwardingStopped() {
        ShowableListMenu popup = getPopup();
        if (popup == null || !popup.isShowing()) {
            return true;
        }
        popup.dismiss();
        return true;
    }
}
