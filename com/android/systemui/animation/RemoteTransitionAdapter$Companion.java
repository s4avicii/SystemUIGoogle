package com.android.systemui.animation;

/* compiled from: RemoteTransitionAdapter.kt */
public final class RemoteTransitionAdapter$Companion {
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0072  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:57:0x019e  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x01a0  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x01ce  */
    /* JADX WARNING: Removed duplicated region for block: B:62:0x01da  */
    /* JADX WARNING: Removed duplicated region for block: B:65:0x01e7  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.view.RemoteAnimationTarget[] wrapTargets(android.window.TransitionInfo r30, boolean r31, android.view.SurfaceControl.Transaction r32, android.util.ArrayMap r33) {
        /*
            r0 = r32
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            java.util.List r2 = r30.getChanges()
            int r2 = r2.size()
            r4 = 0
        L_0x0010:
            if (r4 >= r2) goto L_0x023b
            int r5 = r4 + 1
            java.util.List r6 = r30.getChanges()
            java.lang.Object r6 = r6.get(r4)
            android.window.TransitionInfo$Change r6 = (android.window.TransitionInfo.Change) r6
            int r7 = r6.getFlags()
            r8 = 2
            r7 = r7 & r8
            r9 = 1
            r10 = r31
            if (r7 == 0) goto L_0x002b
            r7 = r9
            goto L_0x002c
        L_0x002b:
            r7 = 0
        L_0x002c:
            if (r10 == r7) goto L_0x0034
            r4 = r33
            r29 = r2
            goto L_0x0236
        L_0x0034:
            java.util.List r7 = r30.getChanges()
            int r7 = r7.size()
            int r18 = r7 - r4
            android.view.RemoteAnimationTarget r4 = new android.view.RemoteAnimationTarget
            android.app.ActivityManager$RunningTaskInfo r7 = r6.getTaskInfo()
            if (r7 == 0) goto L_0x0050
            android.app.ActivityManager$RunningTaskInfo r7 = r6.getTaskInfo()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r7)
            int r7 = r7.taskId
            goto L_0x0051
        L_0x0050:
            r7 = -1
        L_0x0051:
            r12 = r7
            int r7 = r6.getMode()
            r11 = 3
            r13 = 4
            if (r7 == r9) goto L_0x0064
            if (r7 == r8) goto L_0x0062
            if (r7 == r11) goto L_0x0064
            if (r7 == r13) goto L_0x0062
            r7 = r8
            goto L_0x0065
        L_0x0062:
            r7 = r9
            goto L_0x0065
        L_0x0064:
            r7 = 0
        L_0x0065:
            android.window.WindowContainerToken r14 = r6.getParent()
            if (r14 == 0) goto L_0x007d
            int r14 = r6.getFlags()
            r14 = r14 & r8
            if (r14 == 0) goto L_0x007d
            android.view.SurfaceControl r8 = r6.getLeash()
            r15 = r30
            r29 = r2
            r14 = r8
            goto L_0x018d
        L_0x007d:
            android.view.SurfaceControl$Builder r14 = new android.view.SurfaceControl$Builder
            r14.<init>()
            android.view.SurfaceControl r15 = r6.getLeash()
            java.lang.String r15 = r15.toString()
            java.lang.String r3 = "_transition-leash"
            java.lang.String r3 = kotlin.jvm.internal.Intrinsics.stringPlus(r15, r3)
            android.view.SurfaceControl$Builder r3 = r14.setName(r3)
            android.view.SurfaceControl$Builder r3 = r3.setContainerLayer()
            android.window.WindowContainerToken r14 = r6.getParent()
            if (r14 != 0) goto L_0x00a5
            android.view.SurfaceControl r14 = r30.getRootLeash()
            r15 = r30
            goto L_0x00b9
        L_0x00a5:
            android.window.WindowContainerToken r14 = r6.getParent()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r14)
            r15 = r30
            android.window.TransitionInfo$Change r14 = r15.getChange(r14)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r14)
            android.view.SurfaceControl r14 = r14.getLeash()
        L_0x00b9:
            android.view.SurfaceControl$Builder r3 = r3.setParent(r14)
            android.view.SurfaceControl r3 = r3.build()
            java.util.List r14 = r30.getChanges()
            int r14 = r14.size()
            int r14 = r14 - r18
            int r13 = r30.getType()
            if (r13 == r9) goto L_0x00da
            int r13 = r30.getType()
            if (r13 != r11) goto L_0x00d8
            goto L_0x00da
        L_0x00d8:
            r13 = 0
            goto L_0x00db
        L_0x00da:
            r13 = r9
        L_0x00db:
            java.util.List r17 = r30.getChanges()
            int r17 = r17.size()
            int r11 = r6.getMode()
            android.view.SurfaceControl r8 = r30.getRootLeash()
            r0.reparent(r3, r8)
            android.graphics.Rect r8 = r6.getStartAbsBounds()
            int r8 = r8.left
            android.graphics.Point r9 = r30.getRootOffset()
            int r9 = r9.x
            int r8 = r8 - r9
            float r8 = (float) r8
            android.graphics.Rect r9 = r6.getStartAbsBounds()
            int r9 = r9.top
            r29 = r2
            android.graphics.Point r2 = r30.getRootOffset()
            int r2 = r2.y
            int r9 = r9 - r2
            float r2 = (float) r9
            r0.setPosition(r3, r8, r2)
            r0.show(r3)
            r2 = 0
            r8 = 1
            if (r11 == r8) goto L_0x0145
            r8 = 2
            if (r11 == r8) goto L_0x012e
            r8 = 3
            if (r11 == r8) goto L_0x0145
            r8 = 4
            if (r11 == r8) goto L_0x012e
            java.util.List r8 = r30.getChanges()
            int r8 = r8.size()
            int r8 = r8 + r17
            int r8 = r8 - r14
            r0.setLayer(r3, r8)
            goto L_0x0166
        L_0x012e:
            if (r13 == 0) goto L_0x0136
            int r8 = r17 - r14
            r0.setLayer(r3, r8)
            goto L_0x0166
        L_0x0136:
            java.util.List r8 = r30.getChanges()
            int r8 = r8.size()
            int r8 = r8 + r17
            int r8 = r8 - r14
            r0.setLayer(r3, r8)
            goto L_0x0166
        L_0x0145:
            if (r13 == 0) goto L_0x0161
            java.util.List r8 = r30.getChanges()
            int r8 = r8.size()
            int r8 = r8 + r17
            int r8 = r8 - r14
            r0.setLayer(r3, r8)
            int r8 = r6.getFlags()
            r8 = r8 & 8
            if (r8 != 0) goto L_0x0166
            r0.setAlpha(r3, r2)
            goto L_0x0166
        L_0x0161:
            int r8 = r17 - r14
            r0.setLayer(r3, r8)
        L_0x0166:
            android.view.SurfaceControl r8 = r6.getLeash()
            r0.reparent(r8, r3)
            android.view.SurfaceControl r8 = r6.getLeash()
            r9 = 1065353216(0x3f800000, float:1.0)
            r0.setAlpha(r8, r9)
            android.view.SurfaceControl r8 = r6.getLeash()
            r0.show(r8)
            android.view.SurfaceControl r8 = r6.getLeash()
            r0.setPosition(r8, r2, r2)
            android.view.SurfaceControl r2 = r6.getLeash()
            r8 = 0
            r0.setLayer(r2, r8)
            r14 = r3
        L_0x018d:
            int r2 = r6.getFlags()
            r3 = 4
            r2 = r2 & r3
            if (r2 != 0) goto L_0x01a0
            int r2 = r6.getFlags()
            r3 = 1
            r2 = r2 & r3
            if (r2 == 0) goto L_0x019e
            goto L_0x01a0
        L_0x019e:
            r2 = 0
            goto L_0x01a1
        L_0x01a0:
            r2 = 1
        L_0x01a1:
            r16 = 0
            android.graphics.Rect r3 = new android.graphics.Rect
            r8 = 0
            r3.<init>(r8, r8, r8, r8)
            r19 = 0
            android.graphics.Rect r8 = r6.getEndAbsBounds()
            android.graphics.Point r9 = r6.getEndRelOffset()
            android.graphics.Rect r13 = new android.graphics.Rect
            r13.<init>(r8)
            int r8 = r9.x
            int r9 = r9.y
            r13.offsetTo(r8, r9)
            android.graphics.Rect r8 = new android.graphics.Rect
            android.graphics.Rect r9 = r6.getEndAbsBounds()
            r8.<init>(r9)
            android.app.ActivityManager$RunningTaskInfo r9 = r6.getTaskInfo()
            if (r9 == 0) goto L_0x01da
            android.app.ActivityManager$RunningTaskInfo r9 = r6.getTaskInfo()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9)
            android.content.res.Configuration r9 = r9.configuration
            android.app.WindowConfiguration r9 = r9.windowConfiguration
            goto L_0x01df
        L_0x01da:
            android.app.WindowConfiguration r9 = new android.app.WindowConfiguration
            r9.<init>()
        L_0x01df:
            r22 = r9
            android.app.ActivityManager$RunningTaskInfo r9 = r6.getTaskInfo()
            if (r9 == 0) goto L_0x01f6
            android.app.ActivityManager$RunningTaskInfo r9 = r6.getTaskInfo()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9)
            boolean r9 = r9.isRunning
            if (r9 != 0) goto L_0x01f3
            goto L_0x01f6
        L_0x01f3:
            r23 = 0
            goto L_0x01f8
        L_0x01f6:
            r23 = 1
        L_0x01f8:
            r24 = 0
            android.graphics.Rect r9 = new android.graphics.Rect
            r25 = r9
            android.graphics.Rect r11 = r6.getStartAbsBounds()
            r9.<init>(r11)
            android.app.ActivityManager$RunningTaskInfo r26 = r6.getTaskInfo()
            boolean r27 = r6.getAllowEnterPip()
            r28 = -1
            r11 = r4
            r9 = r13
            r13 = r7
            r15 = r2
            r17 = r3
            r20 = r9
            r21 = r8
            r11.<init>(r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27, r28)
            r1.add(r4)
            android.view.SurfaceControl r2 = r6.getLeash()
            int r3 = r1.size()
            r4 = 1
            int r3 = r3 - r4
            java.lang.Object r3 = r1.get(r3)
            android.view.RemoteAnimationTarget r3 = (android.view.RemoteAnimationTarget) r3
            android.view.SurfaceControl r3 = r3.leash
            r4 = r33
            r4.put(r2, r3)
        L_0x0236:
            r4 = r5
            r2 = r29
            goto L_0x0010
        L_0x023b:
            r2 = 0
            android.view.RemoteAnimationTarget[] r0 = new android.view.RemoteAnimationTarget[r2]
            java.lang.Object[] r0 = r1.toArray(r0)
            java.lang.String r1 = "null cannot be cast to non-null type kotlin.Array<T of kotlin.collections.ArraysKt__ArraysJVMKt.toTypedArray>"
            java.util.Objects.requireNonNull(r0, r1)
            android.view.RemoteAnimationTarget[] r0 = (android.view.RemoteAnimationTarget[]) r0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.animation.RemoteTransitionAdapter$Companion.wrapTargets(android.window.TransitionInfo, boolean, android.view.SurfaceControl$Transaction, android.util.ArrayMap):android.view.RemoteAnimationTarget[]");
    }
}
