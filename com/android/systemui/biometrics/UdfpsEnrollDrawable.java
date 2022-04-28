package com.android.systemui.biometrics;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import com.android.p012wm.shell.C1777R;

public final class UdfpsEnrollDrawable extends UdfpsDrawable {
    public final Paint mBlueFill;
    public float mCurrentScale = 1.0f;
    public float mCurrentX;
    public float mCurrentY;
    public UdfpsEnrollHelper mEnrollHelper;
    public final Drawable mMovingTargetFpIcon;
    public final Paint mSensorOutlinePaint;
    public RectF mSensorRect;
    public boolean mShouldShowEdgeHint = false;
    public boolean mShouldShowTipHint = false;
    public final C07031 mTargetAnimListener;
    public AnimatorSet mTargetAnimatorSet;

    public final void draw(Canvas canvas) {
        if (!this.isIlluminationShowing) {
            UdfpsEnrollHelper udfpsEnrollHelper = this.mEnrollHelper;
            if (udfpsEnrollHelper == null || udfpsEnrollHelper.isCenterEnrollmentStage()) {
                RectF rectF = this.mSensorRect;
                if (rectF != null) {
                    canvas.drawOval(rectF, this.mSensorOutlinePaint);
                }
                this.fingerprintDrawable.draw(canvas);
                this.fingerprintDrawable.setAlpha(this._alpha);
                this.mSensorOutlinePaint.setAlpha(this._alpha);
                return;
            }
            canvas.save();
            canvas.translate(this.mCurrentX, this.mCurrentY);
            RectF rectF2 = this.mSensorRect;
            if (rectF2 != null) {
                float f = this.mCurrentScale;
                canvas.scale(f, f, rectF2.centerX(), this.mSensorRect.centerY());
                canvas.drawOval(this.mSensorRect, this.mBlueFill);
            }
            this.mMovingTargetFpIcon.draw(canvas);
            canvas.restore();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0027, code lost:
        if (r0 != false) goto L_0x002b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateTipHintVisibility() {
        /*
            r6 = this;
            com.android.systemui.biometrics.UdfpsEnrollHelper r0 = r6.mEnrollHelper
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x002a
            java.util.Objects.requireNonNull(r0)
            int r3 = r0.mTotalSteps
            r4 = -1
            if (r3 == r4) goto L_0x0026
            int r5 = r0.mRemainingSteps
            if (r5 != r4) goto L_0x0013
            goto L_0x0026
        L_0x0013:
            int r4 = r3 - r5
            int r3 = r0.getStageThresholdSteps(r3, r1)
            if (r4 < r3) goto L_0x0026
            int r3 = r0.mTotalSteps
            r5 = 2
            int r0 = r0.getStageThresholdSteps(r3, r5)
            if (r4 >= r0) goto L_0x0026
            r0 = r1
            goto L_0x0027
        L_0x0026:
            r0 = r2
        L_0x0027:
            if (r0 == 0) goto L_0x002a
            goto L_0x002b
        L_0x002a:
            r1 = r2
        L_0x002b:
            boolean r0 = r6.mShouldShowTipHint
            if (r0 != r1) goto L_0x0030
            return
        L_0x0030:
            r6.mShouldShowTipHint = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.UdfpsEnrollDrawable.updateTipHintVisibility():void");
    }

    public UdfpsEnrollDrawable(Context context) {
        super(context);
        new Handler(Looper.getMainLooper());
        Paint paint = new Paint(0);
        this.mSensorOutlinePaint = paint;
        paint.setAntiAlias(true);
        paint.setColor(context.getColor(C1777R.color.udfps_moving_target_fill));
        paint.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint(0);
        this.mBlueFill = paint2;
        paint2.setAntiAlias(true);
        paint2.setColor(context.getColor(C1777R.color.udfps_moving_target_fill));
        paint2.setStyle(Paint.Style.FILL);
        Drawable drawable = context.getResources().getDrawable(C1777R.C1778drawable.ic_kg_fingerprint, (Resources.Theme) null);
        this.mMovingTargetFpIcon = drawable;
        drawable.setTint(context.getColor(C1777R.color.udfps_enroll_icon));
        drawable.mutate();
        this.fingerprintDrawable.setTint(context.getColor(C1777R.color.udfps_enroll_icon));
        this.mTargetAnimListener = new Animator.AnimatorListener() {
            public final void onAnimationCancel(Animator animator) {
            }

            public final void onAnimationRepeat(Animator animator) {
            }

            public final void onAnimationStart(Animator animator) {
            }

            public final void onAnimationEnd(Animator animator) {
                UdfpsEnrollDrawable.this.updateTipHintVisibility();
            }
        };
    }

    public final void onSensorRectUpdated(RectF rectF) {
        super.onSensorRectUpdated(rectF);
        this.mSensorRect = rectF;
    }

    public final void setAlpha(int i) {
        super.setAlpha(i);
        this.mSensorOutlinePaint.setAlpha(i);
        this.mBlueFill.setAlpha(i);
        this.mMovingTargetFpIcon.setAlpha(i);
        invalidateSelf();
    }

    public final void updateFingerprintIconBounds(Rect rect) {
        super.updateFingerprintIconBounds(rect);
        this.mMovingTargetFpIcon.setBounds(rect);
        invalidateSelf();
    }
}
