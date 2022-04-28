package com.android.systemui.biometrics;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class UdfpsEnrollView$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ UdfpsEnrollView f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ UdfpsEnrollView$$ExternalSyntheticLambda1(UdfpsEnrollView udfpsEnrollView, int i, int i2) {
        this.f$0 = udfpsEnrollView;
        this.f$1 = i;
        this.f$2 = i2;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:35:0x00f2, code lost:
        if (r0 != false) goto L_0x00f6;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r10 = this;
            com.android.systemui.biometrics.UdfpsEnrollView r0 = r10.f$0
            int r1 = r10.f$1
            int r10 = r10.f$2
            int r2 = com.android.systemui.biometrics.UdfpsEnrollView.$r8$clinit
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.biometrics.UdfpsEnrollProgressBarDrawable r2 = r0.mFingerprintProgressDrawable
            java.util.Objects.requireNonNull(r2)
            r3 = 1
            r2.mAfterFirstTouch = r3
            r4 = 0
            r2.updateState(r1, r10, r4)
            com.android.systemui.biometrics.UdfpsEnrollDrawable r10 = r0.mFingerprintDrawable
            java.util.Objects.requireNonNull(r10)
            com.android.systemui.biometrics.UdfpsEnrollHelper r0 = r10.mEnrollHelper
            if (r0 != 0) goto L_0x0022
            goto L_0x00fd
        L_0x0022:
            boolean r0 = r0.isCenterEnrollmentStage()
            r1 = 2
            if (r0 != 0) goto L_0x00d6
            android.animation.AnimatorSet r0 = r10.mTargetAnimatorSet
            if (r0 == 0) goto L_0x0038
            boolean r0 = r0.isRunning()
            if (r0 == 0) goto L_0x0038
            android.animation.AnimatorSet r0 = r10.mTargetAnimatorSet
            r0.end()
        L_0x0038:
            com.android.systemui.biometrics.UdfpsEnrollHelper r0 = r10.mEnrollHelper
            android.graphics.PointF r0 = r0.getNextGuidedEnrollmentPoint()
            float r2 = r10.mCurrentX
            float r5 = r0.x
            int r6 = (r2 > r5 ? 1 : (r2 == r5 ? 0 : -1))
            if (r6 != 0) goto L_0x0054
            float r6 = r10.mCurrentY
            float r7 = r0.y
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 == 0) goto L_0x004f
            goto L_0x0054
        L_0x004f:
            r10.updateTipHintVisibility()
            goto L_0x00d9
        L_0x0054:
            float[] r6 = new float[r1]
            r6[r4] = r2
            r6[r3] = r5
            android.animation.ValueAnimator r2 = android.animation.ValueAnimator.ofFloat(r6)
            com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda0 r5 = new com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda0
            r5.<init>(r10, r4)
            r2.addUpdateListener(r5)
            float[] r5 = new float[r1]
            float r6 = r10.mCurrentY
            r5[r4] = r6
            float r6 = r0.y
            r5[r3] = r6
            android.animation.ValueAnimator r5 = android.animation.ValueAnimator.ofFloat(r5)
            com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda1 r6 = new com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda1
            r6.<init>(r10)
            r5.addUpdateListener(r6)
            float r6 = r0.x
            r7 = 0
            int r6 = (r6 > r7 ? 1 : (r6 == r7 ? 0 : -1))
            if (r6 != 0) goto L_0x008b
            float r0 = r0.y
            int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r0 != 0) goto L_0x008b
            r0 = r3
            goto L_0x008c
        L_0x008b:
            r0 = r4
        L_0x008c:
            if (r0 == 0) goto L_0x0091
            r6 = 600(0x258, double:2.964E-321)
            goto L_0x0093
        L_0x0091:
            r6 = 800(0x320, double:3.953E-321)
        L_0x0093:
            float[] r0 = new float[r1]
            r0 = {0, 1078530011} // fill-array
            android.animation.ValueAnimator r0 = android.animation.ValueAnimator.ofFloat(r0)
            r0.setDuration(r6)
            com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda2 r8 = new com.android.systemui.biometrics.UdfpsEnrollDrawable$$ExternalSyntheticLambda2
            r8.<init>(r10)
            r0.addUpdateListener(r8)
            android.animation.AnimatorSet r8 = new android.animation.AnimatorSet
            r8.<init>()
            r10.mTargetAnimatorSet = r8
            android.view.animation.AccelerateDecelerateInterpolator r9 = new android.view.animation.AccelerateDecelerateInterpolator
            r9.<init>()
            r8.setInterpolator(r9)
            android.animation.AnimatorSet r8 = r10.mTargetAnimatorSet
            r8.setDuration(r6)
            android.animation.AnimatorSet r6 = r10.mTargetAnimatorSet
            com.android.systemui.biometrics.UdfpsEnrollDrawable$1 r7 = r10.mTargetAnimListener
            r6.addListener(r7)
            android.animation.AnimatorSet r6 = r10.mTargetAnimatorSet
            r7 = 3
            android.animation.Animator[] r7 = new android.animation.Animator[r7]
            r7[r4] = r2
            r7[r3] = r5
            r7[r1] = r0
            r6.playTogether(r7)
            android.animation.AnimatorSet r0 = r10.mTargetAnimatorSet
            r0.start()
            goto L_0x00d9
        L_0x00d6:
            r10.updateTipHintVisibility()
        L_0x00d9:
            com.android.systemui.biometrics.UdfpsEnrollHelper r0 = r10.mEnrollHelper
            if (r0 == 0) goto L_0x00f5
            int r2 = r0.mTotalSteps
            r5 = -1
            if (r2 == r5) goto L_0x00f1
            int r6 = r0.mRemainingSteps
            if (r6 != r5) goto L_0x00e7
            goto L_0x00f1
        L_0x00e7:
            int r5 = r2 - r6
            int r0 = r0.getStageThresholdSteps(r2, r1)
            if (r5 < r0) goto L_0x00f1
            r0 = r3
            goto L_0x00f2
        L_0x00f1:
            r0 = r4
        L_0x00f2:
            if (r0 == 0) goto L_0x00f5
            goto L_0x00f6
        L_0x00f5:
            r3 = r4
        L_0x00f6:
            boolean r0 = r10.mShouldShowEdgeHint
            if (r0 != r3) goto L_0x00fb
            goto L_0x00fd
        L_0x00fb:
            r10.mShouldShowEdgeHint = r3
        L_0x00fd:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.UdfpsEnrollView$$ExternalSyntheticLambda1.run():void");
    }
}
