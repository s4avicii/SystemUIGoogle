package com.android.systemui.biometrics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.biometrics.SensorLocationInternal;
import android.hardware.fingerprint.FingerprintSensorPropertiesInternal;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.widget.FrameLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.doze.DozeReceiver;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UdfpsView.kt */
public final class UdfpsView extends FrameLayout implements DozeReceiver {
    public static final /* synthetic */ int $r8$clinit = 0;
    public UdfpsAnimationViewController<?> animationViewController;
    public String debugMessage;
    public final Paint debugTextPaint;
    public UdfpsSurfaceView ghbmView;
    public UdfpsHbmProvider hbmProvider;
    public final int hbmType;
    public boolean isIlluminationRequested;
    public final long onIlluminatedDelayMs;
    public FingerprintSensorPropertiesInternal sensorProperties;
    public final RectF sensorRect = new RectF();
    public final float sensorTouchAreaCoefficient;

    public final void stopIllumination() {
        this.isIlluminationRequested = false;
        UdfpsAnimationViewController udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null) {
            udfpsAnimationViewController.getView().onIlluminationStopped();
            udfpsAnimationViewController.getView().postInvalidate();
        }
        UdfpsSurfaceView udfpsSurfaceView = this.ghbmView;
        if (udfpsSurfaceView != null) {
            udfpsSurfaceView.mGhbmIlluminationListener = null;
            udfpsSurfaceView.setVisibility(4);
        }
        UdfpsHbmProvider udfpsHbmProvider = this.hbmProvider;
        if (udfpsHbmProvider != null) {
            udfpsHbmProvider.disableHbm();
        }
    }

    public final void doIlluminate(Surface surface, Runnable runnable) {
        if (this.ghbmView != null && surface == null) {
            Log.e("UdfpsView", "doIlluminate | surface must be non-null for GHBM");
        }
        UdfpsHbmProvider udfpsHbmProvider = this.hbmProvider;
        if (udfpsHbmProvider != null) {
            udfpsHbmProvider.enableHbm(this.hbmType, surface, new UdfpsView$doIlluminate$1(this, runnable));
        }
    }

    public final void dozeTimeTick() {
        UdfpsAnimationViewController udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null && udfpsAnimationViewController.getView().dozeTimeTick()) {
            udfpsAnimationViewController.getView().postInvalidate();
        }
    }

    public final void onFinishInflate() {
        if (this.hbmType == 0) {
            this.ghbmView = (UdfpsSurfaceView) findViewById(C1777R.C1779id.hbm_view);
        }
    }

    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        UdfpsAnimationViewController<?> udfpsAnimationViewController = this.animationViewController;
        if (udfpsAnimationViewController != null) {
            Intrinsics.checkNotNull(udfpsAnimationViewController);
            if (!udfpsAnimationViewController.shouldPauseAuth()) {
                return true;
            }
            return false;
        }
        return true;
    }

    /* JADX WARNING: type inference failed for: r6v1, types: [java.lang.AutoCloseable, android.content.res.TypedArray] */
    /* JADX WARNING: Code restructure failed: missing block: B:19:0x006e, code lost:
        r5 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x006f, code lost:
        androidx.cardview.R$attr.closeFinally(r6, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0072, code lost:
        throw r5;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public UdfpsView(android.content.Context r5, android.util.AttributeSet r6) {
        /*
            r4 = this;
            r4.<init>(r5, r6)
            android.graphics.RectF r0 = new android.graphics.RectF
            r0.<init>()
            r4.sensorRect = r0
            android.graphics.Paint r0 = new android.graphics.Paint
            r0.<init>()
            r1 = 1
            r0.setAntiAlias(r1)
            r2 = -16776961(0xffffffffff0000ff, float:-1.7014636E38)
            r0.setColor(r2)
            r2 = 1107296256(0x42000000, float:32.0)
            r0.setTextSize(r2)
            r4.debugTextPaint = r0
            android.content.res.Resources$Theme r0 = r5.getTheme()
            int[] r2 = com.android.systemui.R$styleable.UdfpsView
            r3 = 0
            android.content.res.TypedArray r6 = r0.obtainStyledAttributes(r6, r2, r3, r3)
            boolean r0 = r6.hasValue(r1)     // Catch:{ all -> 0x006c }
            if (r0 == 0) goto L_0x0060
            r0 = 0
            float r0 = r6.getFloat(r1, r0)     // Catch:{ all -> 0x006c }
            r2 = 0
            androidx.cardview.R$attr.closeFinally(r6, r2)
            r4.sensorTouchAreaCoefficient = r0
            android.content.res.Resources r6 = r5.getResources()
            r0 = 17694951(0x10e00e7, float:2.6081928E-38)
            int r6 = r6.getInteger(r0)
            long r2 = (long) r6
            r4.onIlluminatedDelayMs = r2
            boolean r6 = android.os.Build.IS_ENG
            if (r6 != 0) goto L_0x0052
            boolean r6 = android.os.Build.IS_USERDEBUG
            if (r6 == 0) goto L_0x005d
        L_0x0052:
            android.content.ContentResolver r5 = r5.getContentResolver()
            r6 = -2
            java.lang.String r0 = "com.android.systemui.biometrics.UdfpsSurfaceView.hbmType"
            int r1 = android.provider.Settings.Secure.getIntForUser(r5, r0, r1, r6)
        L_0x005d:
            r4.hbmType = r1
            return
        L_0x0060:
            java.lang.String r4 = "UdfpsView must contain sensorTouchAreaCoefficient"
            java.lang.IllegalArgumentException r5 = new java.lang.IllegalArgumentException     // Catch:{ all -> 0x006c }
            java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x006c }
            r5.<init>(r4)     // Catch:{ all -> 0x006c }
            throw r5     // Catch:{ all -> 0x006c }
        L_0x006c:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x006e }
        L_0x006e:
            r5 = move-exception
            androidx.cardview.R$attr.closeFinally(r6, r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.UdfpsView.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.v("UdfpsView", "onAttachedToWindow");
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.v("UdfpsView", "onDetachedFromWindow");
    }

    public final void onDraw(Canvas canvas) {
        boolean z;
        super.onDraw(canvas);
        if (!this.isIlluminationRequested) {
            String str = this.debugMessage;
            if (str == null || str.length() == 0) {
                z = true;
            } else {
                z = false;
            }
            if (!z) {
                String str2 = this.debugMessage;
                Intrinsics.checkNotNull(str2);
                canvas.drawText(str2, 0.0f, 160.0f, this.debugTextPaint);
            }
        }
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        SensorLocationInternal location;
        super.onLayout(z, i, i2, i3, i4);
        UdfpsAnimationViewController<?> udfpsAnimationViewController = this.animationViewController;
        int i7 = 0;
        if (udfpsAnimationViewController == null) {
            i5 = 0;
        } else {
            i5 = udfpsAnimationViewController.getPaddingX();
        }
        UdfpsAnimationViewController<?> udfpsAnimationViewController2 = this.animationViewController;
        if (udfpsAnimationViewController2 == null) {
            i6 = 0;
        } else {
            i6 = udfpsAnimationViewController2.getPaddingY();
        }
        FingerprintSensorPropertiesInternal fingerprintSensorPropertiesInternal = this.sensorProperties;
        if (!(fingerprintSensorPropertiesInternal == null || (location = fingerprintSensorPropertiesInternal.getLocation()) == null)) {
            i7 = location.sensorRadius;
        }
        int i8 = i7 * 2;
        this.sensorRect.set((float) i5, (float) i6, (float) (i5 + i8), (float) (i8 + i6));
        UdfpsAnimationViewController udfpsAnimationViewController3 = this.animationViewController;
        if (udfpsAnimationViewController3 != null) {
            udfpsAnimationViewController3.getView().getDrawable().onSensorRectUpdated(new RectF(this.sensorRect));
        }
    }
}
