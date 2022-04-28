package com.android.p012wm.shell.common.magnetictarget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PointF;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.VelocityTracker;
import android.view.View;
import androidx.dynamicanimation.animation.FloatPropertyCompat;
import com.android.p012wm.shell.animation.PhysicsAnimator;
import com.android.p012wm.shell.common.DismissCircleView;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function5;

/* renamed from: com.android.wm.shell.common.magnetictarget.MagnetizedObject */
/* compiled from: MagnetizedObject.kt */
public abstract class MagnetizedObject<T> {
    public Function5<? super MagneticTarget, ? super Float, ? super Float, ? super Boolean, ? super Function0<Unit>, Unit> animateStuckToTarget;
    public final PhysicsAnimator<T> animator;
    public final ArrayList<MagneticTarget> associatedTargets = new ArrayList<>();
    public boolean flingToTargetEnabled;
    public float flingToTargetMinVelocity;
    public float flingToTargetWidthPercent;
    public float flingUnstuckFromTargetMinVelocity;
    public PhysicsAnimator.SpringConfig flungIntoTargetSpringConfig;
    public boolean hapticsEnabled;
    public MagnetListener magnetListener;
    public boolean movedBeyondSlop;
    public final int[] objectLocationOnScreen = new int[2];
    public PhysicsAnimator.SpringConfig springConfig;
    public float stickToTargetMaxXVelocity;
    public MagneticTarget targetObjectIsStuckTo;
    public PointF touchDown;
    public int touchSlop;
    public final T underlyingObject;
    public final VelocityTracker velocityTracker = VelocityTracker.obtain();
    public final VibrationAttributes vibrationAttributes;
    public final Vibrator vibrator;
    public final FloatPropertyCompat<? super T> xProperty;
    public final FloatPropertyCompat<? super T> yProperty;

    /* renamed from: com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagnetListener */
    /* compiled from: MagnetizedObject.kt */
    public interface MagnetListener {
        void onReleasedInTarget();

        void onStuckToTarget();

        void onUnstuckFromTarget(float f, float f2, boolean z);
    }

    public abstract float getHeight(T t);

    public abstract void getLocationOnScreen(T t, int[] iArr);

    public abstract float getWidth(T t);

    /* renamed from: com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget */
    /* compiled from: MagnetizedObject.kt */
    public static final class MagneticTarget {
        public final PointF centerOnScreen = new PointF();
        public int magneticFieldRadiusPx;
        public final View targetView;
        public final int[] tempLoc = new int[2];

        public MagneticTarget(DismissCircleView dismissCircleView, int i) {
            this.targetView = dismissCircleView;
            this.magneticFieldRadiusPx = i;
        }
    }

    /* renamed from: cancelAnimations$frameworks__base__libs__WindowManager__Shell__android_common__WindowManager_Shell */
    public final void mo16491x36f74c31() {
        this.animator.cancel(this.xProperty, this.yProperty);
    }

    public final MagnetListener getMagnetListener() {
        MagnetListener magnetListener2 = this.magnetListener;
        if (magnetListener2 != null) {
            return magnetListener2;
        }
        return null;
    }

    public final boolean getObjectStuckToTarget() {
        if (this.targetObjectIsStuckTo != null) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:104:0x023d A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean maybeConsumeMotionEvent(android.view.MotionEvent r15) {
        /*
            r14 = this;
            java.util.ArrayList<com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget> r0 = r14.associatedTargets
            int r0 = r0.size()
            r1 = 0
            if (r0 != 0) goto L_0x000a
            return r1
        L_0x000a:
            int r0 = r15.getAction()
            r2 = 0
            if (r0 != 0) goto L_0x006a
            java.util.ArrayList<com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget> r0 = r14.associatedTargets
            java.util.Iterator r0 = r0.iterator()
        L_0x0017:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x0031
            java.lang.Object r3 = r0.next()
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget r3 = (com.android.p012wm.shell.common.magnetictarget.MagnetizedObject.MagneticTarget) r3
            java.util.Objects.requireNonNull(r3)
            android.view.View r4 = r3.targetView
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget$updateLocationOnScreen$1 r5 = new com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget$updateLocationOnScreen$1
            r5.<init>(r3)
            r4.post(r5)
            goto L_0x0017
        L_0x0031:
            java.util.ArrayList<com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget> r0 = r14.associatedTargets
            int r0 = r0.size()
            if (r0 <= 0) goto L_0x0054
            java.util.ArrayList<com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget> r0 = r14.associatedTargets
            java.lang.Object r0 = r0.get(r1)
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget r0 = (com.android.p012wm.shell.common.magnetictarget.MagnetizedObject.MagneticTarget) r0
            java.util.Objects.requireNonNull(r0)
            android.view.View r0 = r0.targetView
            android.content.Context r0 = r0.getContext()
            android.view.ViewConfiguration r0 = android.view.ViewConfiguration.get(r0)
            int r0 = r0.getScaledTouchSlop()
            r14.touchSlop = r0
        L_0x0054:
            android.view.VelocityTracker r0 = r14.velocityTracker
            r0.clear()
            r14.targetObjectIsStuckTo = r2
            android.graphics.PointF r0 = r14.touchDown
            float r3 = r15.getRawX()
            float r4 = r15.getRawY()
            r0.set(r3, r4)
            r14.movedBeyondSlop = r1
        L_0x006a:
            float r0 = r15.getRawX()
            float r3 = r15.getX()
            float r0 = r0 - r3
            float r3 = r15.getRawY()
            float r4 = r15.getY()
            float r3 = r3 - r4
            r15.offsetLocation(r0, r3)
            android.view.VelocityTracker r4 = r14.velocityTracker
            r4.addMovement(r15)
            float r0 = -r0
            float r3 = -r3
            r15.offsetLocation(r0, r3)
            boolean r0 = r14.movedBeyondSlop
            r3 = 1
            if (r0 != 0) goto L_0x00b2
            float r0 = r15.getRawX()
            android.graphics.PointF r4 = r14.touchDown
            float r4 = r4.x
            float r0 = r0 - r4
            float r4 = r15.getRawY()
            android.graphics.PointF r5 = r14.touchDown
            float r5 = r5.y
            float r4 = r4 - r5
            double r5 = (double) r0
            double r7 = (double) r4
            double r4 = java.lang.Math.hypot(r5, r7)
            float r0 = (float) r4
            int r4 = r14.touchSlop
            float r4 = (float) r4
            int r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1))
            if (r0 <= 0) goto L_0x00b1
            r14.movedBeyondSlop = r3
            goto L_0x00b2
        L_0x00b1:
            return r1
        L_0x00b2:
            java.util.ArrayList<com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget> r0 = r14.associatedTargets
            java.util.Iterator r0 = r0.iterator()
        L_0x00b8:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L_0x00ee
            java.lang.Object r4 = r0.next()
            r5 = r4
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget r5 = (com.android.p012wm.shell.common.magnetictarget.MagnetizedObject.MagneticTarget) r5
            float r6 = r15.getRawX()
            java.util.Objects.requireNonNull(r5)
            android.graphics.PointF r7 = r5.centerOnScreen
            float r7 = r7.x
            float r6 = r6 - r7
            double r6 = (double) r6
            float r8 = r15.getRawY()
            android.graphics.PointF r9 = r5.centerOnScreen
            float r9 = r9.y
            float r8 = r8 - r9
            double r8 = (double) r8
            double r6 = java.lang.Math.hypot(r6, r8)
            float r6 = (float) r6
            int r5 = r5.magneticFieldRadiusPx
            float r5 = (float) r5
            int r5 = (r6 > r5 ? 1 : (r6 == r5 ? 0 : -1))
            if (r5 >= 0) goto L_0x00ea
            r5 = r3
            goto L_0x00eb
        L_0x00ea:
            r5 = r1
        L_0x00eb:
            if (r5 == 0) goto L_0x00b8
            goto L_0x00ef
        L_0x00ee:
            r4 = r2
        L_0x00ef:
            r6 = r4
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget r6 = (com.android.p012wm.shell.common.magnetictarget.MagnetizedObject.MagneticTarget) r6
            boolean r0 = r14.getObjectStuckToTarget()
            if (r0 != 0) goto L_0x00fc
            if (r6 == 0) goto L_0x00fc
            r0 = r3
            goto L_0x00fd
        L_0x00fc:
            r0 = r1
        L_0x00fd:
            boolean r4 = r14.getObjectStuckToTarget()
            if (r4 == 0) goto L_0x010f
            if (r6 == 0) goto L_0x010f
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget r4 = r14.targetObjectIsStuckTo
            boolean r4 = kotlin.jvm.internal.Intrinsics.areEqual(r4, r6)
            if (r4 != 0) goto L_0x010f
            r4 = r3
            goto L_0x0110
        L_0x010f:
            r4 = r1
        L_0x0110:
            r11 = 2
            r12 = 5
            r13 = 1000(0x3e8, float:1.401E-42)
            if (r0 != 0) goto L_0x0147
            if (r4 == 0) goto L_0x0119
            goto L_0x0147
        L_0x0119:
            if (r6 != 0) goto L_0x0188
            boolean r0 = r14.getObjectStuckToTarget()
            if (r0 == 0) goto L_0x0188
            android.view.VelocityTracker r0 = r14.velocityTracker
            r0.computeCurrentVelocity(r13)
            r14.mo16491x36f74c31()
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagnetListener r0 = r14.getMagnetListener()
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget r4 = r14.targetObjectIsStuckTo
            kotlin.jvm.internal.Intrinsics.checkNotNull(r4)
            android.view.VelocityTracker r4 = r14.velocityTracker
            float r4 = r4.getXVelocity()
            android.view.VelocityTracker r5 = r14.velocityTracker
            float r5 = r5.getYVelocity()
            r0.onUnstuckFromTarget(r4, r5, r1)
            r14.targetObjectIsStuckTo = r2
            r14.vibrateIfEnabled(r11)
            goto L_0x0188
        L_0x0147:
            android.view.VelocityTracker r4 = r14.velocityTracker
            r4.computeCurrentVelocity(r13)
            android.view.VelocityTracker r4 = r14.velocityTracker
            float r4 = r4.getXVelocity()
            android.view.VelocityTracker r5 = r14.velocityTracker
            float r5 = r5.getYVelocity()
            if (r0 == 0) goto L_0x0165
            float r0 = java.lang.Math.abs(r4)
            float r7 = r14.stickToTargetMaxXVelocity
            int r0 = (r0 > r7 ? 1 : (r0 == r7 ? 0 : -1))
            if (r0 <= 0) goto L_0x0165
            return r1
        L_0x0165:
            r14.targetObjectIsStuckTo = r6
            r14.mo16491x36f74c31()
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagnetListener r0 = r14.getMagnetListener()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            r0.onStuckToTarget()
            kotlin.jvm.functions.Function5<? super com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget, ? super java.lang.Float, ? super java.lang.Float, ? super java.lang.Boolean, ? super kotlin.jvm.functions.Function0<kotlin.Unit>, kotlin.Unit> r0 = r14.animateStuckToTarget
            java.lang.Float r7 = java.lang.Float.valueOf(r4)
            java.lang.Float r8 = java.lang.Float.valueOf(r5)
            java.lang.Boolean r9 = java.lang.Boolean.FALSE
            r10 = 0
            r5 = r0
            r5.invoke(r6, r7, r8, r9, r10)
            r14.vibrateIfEnabled(r12)
        L_0x0188:
            int r0 = r15.getAction()
            if (r0 != r3) goto L_0x0262
            android.view.VelocityTracker r0 = r14.velocityTracker
            r0.computeCurrentVelocity(r13)
            android.view.VelocityTracker r0 = r14.velocityTracker
            float r0 = r0.getXVelocity()
            android.view.VelocityTracker r4 = r14.velocityTracker
            float r4 = r4.getYVelocity()
            r14.mo16491x36f74c31()
            boolean r5 = r14.getObjectStuckToTarget()
            if (r5 == 0) goto L_0x01ce
            float r15 = -r4
            float r1 = r14.flingUnstuckFromTargetMinVelocity
            int r15 = (r15 > r1 ? 1 : (r15 == r1 ? 0 : -1))
            if (r15 <= 0) goto L_0x01bc
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagnetListener r15 = r14.getMagnetListener()
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget r1 = r14.targetObjectIsStuckTo
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            r15.onUnstuckFromTarget(r0, r4, r3)
            goto L_0x01cb
        L_0x01bc:
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagnetListener r15 = r14.getMagnetListener()
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget r0 = r14.targetObjectIsStuckTo
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            r15.onReleasedInTarget()
            r14.vibrateIfEnabled(r12)
        L_0x01cb:
            r14.targetObjectIsStuckTo = r2
            return r3
        L_0x01ce:
            java.util.ArrayList<com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget> r5 = r14.associatedTargets
            java.util.Iterator r5 = r5.iterator()
        L_0x01d4:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x023e
            java.lang.Object r6 = r5.next()
            r7 = r6
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget r7 = (com.android.p012wm.shell.common.magnetictarget.MagnetizedObject.MagneticTarget) r7
            float r8 = r15.getRawX()
            float r9 = r15.getRawY()
            boolean r10 = r14.flingToTargetEnabled
            if (r10 != 0) goto L_0x01ee
            goto L_0x023a
        L_0x01ee:
            java.util.Objects.requireNonNull(r7)
            android.graphics.PointF r10 = r7.centerOnScreen
            float r10 = r10.y
            int r12 = (r9 > r10 ? 1 : (r9 == r10 ? 0 : -1))
            if (r12 >= 0) goto L_0x0200
            float r12 = r14.flingToTargetMinVelocity
            int r12 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r12 <= 0) goto L_0x0208
            goto L_0x0206
        L_0x0200:
            float r12 = r14.flingToTargetMinVelocity
            int r12 = (r4 > r12 ? 1 : (r4 == r12 ? 0 : -1))
            if (r12 >= 0) goto L_0x0208
        L_0x0206:
            r12 = r3
            goto L_0x0209
        L_0x0208:
            r12 = r1
        L_0x0209:
            if (r12 != 0) goto L_0x020c
            goto L_0x023a
        L_0x020c:
            r12 = 0
            int r12 = (r0 > r12 ? 1 : (r0 == r12 ? 0 : -1))
            if (r12 != 0) goto L_0x0213
            r12 = r3
            goto L_0x0214
        L_0x0213:
            r12 = r1
        L_0x0214:
            if (r12 != 0) goto L_0x021d
            float r12 = r4 / r0
            float r8 = r8 * r12
            float r9 = r9 - r8
            float r10 = r10 - r9
            float r8 = r10 / r12
        L_0x021d:
            android.view.View r9 = r7.targetView
            int r9 = r9.getWidth()
            float r9 = (float) r9
            float r10 = r14.flingToTargetWidthPercent
            float r9 = r9 * r10
            android.graphics.PointF r7 = r7.centerOnScreen
            float r7 = r7.x
            float r10 = (float) r11
            float r9 = r9 / r10
            float r10 = r7 - r9
            int r10 = (r8 > r10 ? 1 : (r8 == r10 ? 0 : -1))
            if (r10 <= 0) goto L_0x023a
            float r7 = r7 + r9
            int r7 = (r8 > r7 ? 1 : (r8 == r7 ? 0 : -1))
            if (r7 >= 0) goto L_0x023a
            r7 = r3
            goto L_0x023b
        L_0x023a:
            r7 = r1
        L_0x023b:
            if (r7 == 0) goto L_0x01d4
            r2 = r6
        L_0x023e:
            r6 = r2
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget r6 = (com.android.p012wm.shell.common.magnetictarget.MagnetizedObject.MagneticTarget) r6
            if (r6 == 0) goto L_0x0261
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagnetListener r15 = r14.getMagnetListener()
            r15.onStuckToTarget()
            r14.targetObjectIsStuckTo = r6
            kotlin.jvm.functions.Function5<? super com.android.wm.shell.common.magnetictarget.MagnetizedObject$MagneticTarget, ? super java.lang.Float, ? super java.lang.Float, ? super java.lang.Boolean, ? super kotlin.jvm.functions.Function0<kotlin.Unit>, kotlin.Unit> r5 = r14.animateStuckToTarget
            java.lang.Float r7 = java.lang.Float.valueOf(r0)
            java.lang.Float r8 = java.lang.Float.valueOf(r4)
            java.lang.Boolean r9 = java.lang.Boolean.TRUE
            com.android.wm.shell.common.magnetictarget.MagnetizedObject$maybeConsumeMotionEvent$1 r10 = new com.android.wm.shell.common.magnetictarget.MagnetizedObject$maybeConsumeMotionEvent$1
            r10.<init>(r14, r6)
            r5.invoke(r6, r7, r8, r9, r10)
            return r3
        L_0x0261:
            return r1
        L_0x0262:
            boolean r14 = r14.getObjectStuckToTarget()
            return r14
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.common.magnetictarget.MagnetizedObject.maybeConsumeMotionEvent(android.view.MotionEvent):boolean");
    }

    @SuppressLint({"MissingPermission"})
    public final void vibrateIfEnabled(int i) {
        if (this.hapticsEnabled) {
            this.vibrator.vibrate(VibrationEffect.createPredefined(i), this.vibrationAttributes);
        }
    }

    public MagnetizedObject(Context context, T t, FloatPropertyCompat<? super T> floatPropertyCompat, FloatPropertyCompat<? super T> floatPropertyCompat2) {
        this.underlyingObject = t;
        this.xProperty = floatPropertyCompat;
        this.yProperty = floatPropertyCompat2;
        Function1<Object, ? extends PhysicsAnimator<?>> function1 = PhysicsAnimator.instanceConstructor;
        this.animator = PhysicsAnimator.Companion.getInstance(t);
        Object systemService = context.getSystemService("vibrator");
        Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.os.Vibrator");
        this.vibrator = (Vibrator) systemService;
        this.vibrationAttributes = VibrationAttributes.createForUsage(18);
        this.touchDown = new PointF();
        this.animateStuckToTarget = new MagnetizedObject$animateStuckToTarget$1(this);
        this.flingToTargetEnabled = true;
        this.flingToTargetWidthPercent = 3.0f;
        this.flingToTargetMinVelocity = 4000.0f;
        this.flingUnstuckFromTargetMinVelocity = 4000.0f;
        this.stickToTargetMaxXVelocity = 2000.0f;
        this.hapticsEnabled = true;
        PhysicsAnimator.SpringConfig springConfig2 = new PhysicsAnimator.SpringConfig(1500.0f, 1.0f);
        this.springConfig = springConfig2;
        this.flungIntoTargetSpringConfig = springConfig2;
    }
}
