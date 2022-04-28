package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import com.google.android.systemui.assist.uihints.NgaUiController$$ExternalSyntheticLambda3;
import java.util.Objects;

public class NotificationPanelView extends PanelView {
    public final Paint mAlphaPaint;
    public int mCurrentPanelAlpha;
    public boolean mDozing;
    public RtlChangeListener mRtlChangeListener;

    public interface RtlChangeListener {
    }

    public final boolean shouldDelayChildPressedState() {
        return true;
    }

    public final boolean hasOverlappingRendering() {
        return !this.mDozing;
    }

    public final void onRtlPropertiesChanged(int i) {
        RtlChangeListener rtlChangeListener = this.mRtlChangeListener;
        if (rtlChangeListener != null) {
            NgaUiController$$ExternalSyntheticLambda3 ngaUiController$$ExternalSyntheticLambda3 = (NgaUiController$$ExternalSyntheticLambda3) rtlChangeListener;
            Objects.requireNonNull(ngaUiController$$ExternalSyntheticLambda3);
            NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) ngaUiController$$ExternalSyntheticLambda3.f$0;
            Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
            Objects.requireNonNull(notificationPanelViewController);
            if (i != notificationPanelViewController.mOldLayoutDirection) {
                KeyguardAffordanceHelper keyguardAffordanceHelper = notificationPanelViewController.mAffordanceHelper;
                Objects.requireNonNull(keyguardAffordanceHelper);
                keyguardAffordanceHelper.initIcons();
                notificationPanelViewController.mOldLayoutDirection = i;
            }
        }
    }

    public NotificationPanelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mAlphaPaint = paint;
        setWillNotDraw(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY));
        setBackgroundColor(0);
    }

    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mCurrentPanelAlpha != 255) {
            canvas.drawRect(0.0f, 0.0f, (float) canvas.getWidth(), (float) canvas.getHeight(), this.mAlphaPaint);
        }
    }
}
