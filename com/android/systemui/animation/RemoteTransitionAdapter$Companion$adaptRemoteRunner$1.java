package com.android.systemui.animation;

import android.os.IBinder;
import android.view.IRemoteAnimationRunner;
import android.view.SurfaceControl;
import android.window.IRemoteTransition;
import android.window.IRemoteTransitionFinishedCallback;
import android.window.TransitionInfo;

/* compiled from: RemoteTransitionAdapter.kt */
public final class RemoteTransitionAdapter$Companion$adaptRemoteRunner$1 extends IRemoteTransition.Stub {
    public final /* synthetic */ IRemoteAnimationRunner $runner;

    public final void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, IRemoteTransitionFinishedCallback iRemoteTransitionFinishedCallback) {
    }

    public RemoteTransitionAdapter$Companion$adaptRemoteRunner$1(IRemoteAnimationRunner iRemoteAnimationRunner) {
        this.$runner = iRemoteAnimationRunner;
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x00a4 A[LOOP:0: B:3:0x002b->B:26:0x00a4, LOOP_END] */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00a1 A[EDGE_INSN: B:60:0x00a1->B:25:0x00a1 ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void startAnimation(android.os.IBinder r19, android.window.TransitionInfo r20, android.view.SurfaceControl.Transaction r21, android.window.IRemoteTransitionFinishedCallback r22) {
        /*
            r18 = this;
            r3 = r20
            r0 = r21
            android.util.ArrayMap r4 = new android.util.ArrayMap
            r4.<init>()
            r1 = 0
            android.view.RemoteAnimationTarget[] r7 = com.android.systemui.animation.RemoteTransitionAdapter$Companion.wrapTargets(r3, r1, r0, r4)
            r2 = 1
            android.view.RemoteAnimationTarget[] r8 = com.android.systemui.animation.RemoteTransitionAdapter$Companion.wrapTargets(r3, r2, r0, r4)
            android.view.RemoteAnimationTarget[] r9 = new android.view.RemoteAnimationTarget[r1]
            java.util.List r5 = r20.getChanges()
            int r5 = r5.size()
            int r5 = r5 + -1
            r6 = 3
            r10 = 0
            r11 = 0
            r12 = 2
            if (r5 < 0) goto L_0x00a9
            r15 = r1
            r16 = r15
            r13 = r11
            r14 = r13
            r11 = r10
        L_0x002b:
            int r17 = r5 + -1
            java.util.List r1 = r20.getChanges()
            java.lang.Object r1 = r1.get(r5)
            android.window.TransitionInfo$Change r1 = (android.window.TransitionInfo.Change) r1
            android.app.ActivityManager$RunningTaskInfo r5 = r1.getTaskInfo()
            if (r5 == 0) goto L_0x0063
            android.app.ActivityManager$RunningTaskInfo r5 = r1.getTaskInfo()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r5)
            int r5 = r5.getActivityType()
            if (r5 != r12) goto L_0x0063
            int r5 = r1.getMode()
            if (r5 == r2) goto L_0x0059
            int r5 = r1.getMode()
            if (r5 != r6) goto L_0x0057
            goto L_0x0059
        L_0x0057:
            r15 = 0
            goto L_0x005a
        L_0x0059:
            r15 = r2
        L_0x005a:
            java.util.List r5 = r20.getChanges()
            r5.size()
            r13 = r1
            goto L_0x006b
        L_0x0063:
            int r5 = r1.getFlags()
            r5 = r5 & r12
            if (r5 == 0) goto L_0x006b
            r14 = r1
        L_0x006b:
            android.window.WindowContainerToken r5 = r1.getParent()
            if (r5 != 0) goto L_0x009f
            int r5 = r1.getEndRotation()
            if (r5 < 0) goto L_0x009f
            int r5 = r1.getEndRotation()
            int r2 = r1.getStartRotation()
            if (r5 == r2) goto L_0x009f
            int r2 = r1.getEndRotation()
            int r5 = r1.getStartRotation()
            int r16 = r2 - r5
            android.graphics.Rect r2 = r1.getEndAbsBounds()
            int r2 = r2.width()
            float r2 = (float) r2
            android.graphics.Rect r1 = r1.getEndAbsBounds()
            int r1 = r1.height()
            float r1 = (float) r1
            r11 = r1
            r10 = r2
        L_0x009f:
            if (r17 >= 0) goto L_0x00a4
            r1 = r16
            goto L_0x00ae
        L_0x00a4:
            r5 = r17
            r1 = 0
            r2 = 1
            goto L_0x002b
        L_0x00a9:
            r13 = r11
            r14 = r13
            r1 = 0
            r15 = 0
            r11 = r10
        L_0x00ae:
            androidx.drawerlayout.widget.DrawerLayout$2 r2 = new androidx.drawerlayout.widget.DrawerLayout$2
            r2.<init>()
            androidx.drawerlayout.widget.DrawerLayout$2 r5 = new androidx.drawerlayout.widget.DrawerLayout$2
            r5.<init>()
            if (r13 == 0) goto L_0x00d7
            if (r1 == 0) goto L_0x00d7
            android.window.WindowContainerToken r16 = r13.getParent()
            if (r16 == 0) goto L_0x00d7
            android.window.WindowContainerToken r6 = r13.getParent()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            android.window.TransitionInfo$Change r6 = r3.getChange(r6)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            android.view.SurfaceControl r6 = r6.getLeash()
            androidx.drawerlayout.widget.DrawerLayout.C01322.setup(r0, r6, r1, r10, r11)
        L_0x00d7:
            if (r15 == 0) goto L_0x0149
            java.util.List r1 = r20.getChanges()
            int r1 = r1.size()
            int r1 = r1 + -1
            if (r1 < 0) goto L_0x012d
        L_0x00e5:
            int r6 = r1 + -1
            java.util.List r10 = r20.getChanges()
            java.lang.Object r10 = r10.get(r1)
            android.window.TransitionInfo$Change r10 = (android.window.TransitionInfo.Change) r10
            android.view.SurfaceControl r11 = r10.getLeash()
            java.lang.Object r11 = r4.get(r11)
            android.view.SurfaceControl r11 = (android.view.SurfaceControl) r11
            java.util.List r13 = r20.getChanges()
            java.lang.Object r13 = r13.get(r1)
            android.window.TransitionInfo$Change r13 = (android.window.TransitionInfo.Change) r13
            int r13 = r13.getMode()
            boolean r10 = android.window.TransitionInfo.isIndependent(r10, r3)
            if (r10 != 0) goto L_0x0111
        L_0x010f:
            r13 = 3
            goto L_0x0128
        L_0x0111:
            if (r13 == r12) goto L_0x0117
            r10 = 4
            if (r13 == r10) goto L_0x0117
            goto L_0x010f
        L_0x0117:
            kotlin.jvm.internal.Intrinsics.checkNotNull(r11)
            java.util.List r10 = r20.getChanges()
            int r10 = r10.size()
            r13 = 3
            int r10 = r10 * r13
            int r10 = r10 - r1
            r0.setLayer(r11, r10)
        L_0x0128:
            if (r6 >= 0) goto L_0x012b
            goto L_0x012d
        L_0x012b:
            r1 = r6
            goto L_0x00e5
        L_0x012d:
            int r1 = r8.length
            int r1 = r1 + -1
            if (r1 < 0) goto L_0x0174
        L_0x0132:
            int r6 = r1 + -1
            r10 = r8[r1]
            android.view.SurfaceControl r10 = r10.leash
            r0.show(r10)
            r1 = r8[r1]
            android.view.SurfaceControl r1 = r1.leash
            r10 = 1065353216(0x3f800000, float:1.0)
            r0.setAlpha(r1, r10)
            if (r6 >= 0) goto L_0x0147
            goto L_0x0174
        L_0x0147:
            r1 = r6
            goto L_0x0132
        L_0x0149:
            if (r13 == 0) goto L_0x0155
            android.view.SurfaceControl r6 = r13.getLeash()
            java.lang.Object r6 = r4.get(r6)
            android.view.SurfaceControl r6 = (android.view.SurfaceControl) r6
        L_0x0155:
            if (r14 == 0) goto L_0x0174
            if (r1 == 0) goto L_0x0174
            android.window.WindowContainerToken r6 = r14.getParent()
            if (r6 == 0) goto L_0x0174
            android.window.WindowContainerToken r6 = r14.getParent()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            android.window.TransitionInfo$Change r6 = r3.getChange(r6)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r6)
            android.view.SurfaceControl r6 = r6.getLeash()
            androidx.drawerlayout.widget.DrawerLayout.C01322.setup(r0, r6, r1, r10, r11)
        L_0x0174:
            r21.apply()
            com.android.systemui.animation.RemoteTransitionAdapter$Companion$adaptRemoteRunner$1$startAnimation$animationFinishedCallback$1 r10 = new com.android.systemui.animation.RemoteTransitionAdapter$Companion$adaptRemoteRunner$1$startAnimation$animationFinishedCallback$1
            r0 = r10
            r1 = r2
            r2 = r5
            r3 = r20
            r5 = r22
            r0.<init>(r1, r2, r3, r4, r5)
            r0 = r18
            android.view.IRemoteAnimationRunner r5 = r0.$runner
            r6 = 0
            r5.onAnimationStart(r6, r7, r8, r9, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.RemoteTransitionAdapter$Companion$adaptRemoteRunner$1.startAnimation(android.os.IBinder, android.window.TransitionInfo, android.view.SurfaceControl$Transaction, android.window.IRemoteTransitionFinishedCallback):void");
    }
}
