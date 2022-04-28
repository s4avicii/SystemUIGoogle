package com.android.p012wm.shell.startingsurface.phone;

import com.android.p012wm.shell.startingsurface.StartingWindowTypeAlgorithm;

/* renamed from: com.android.wm.shell.startingsurface.phone.PhoneStartingWindowTypeAlgorithm */
public final class PhoneStartingWindowTypeAlgorithm implements StartingWindowTypeAlgorithm {
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0133 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x0135  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final int getSuggestedWindowType(android.window.StartingWindowInfo r21) {
        /*
            r20 = this;
            r0 = r21
            int r1 = r0.startingWindowTypeParameter
            r2 = r1 & 1
            r3 = 1
            r4 = 0
            if (r2 == 0) goto L_0x000c
            r2 = r3
            goto L_0x000d
        L_0x000c:
            r2 = r4
        L_0x000d:
            r5 = r1 & 2
            if (r5 == 0) goto L_0x0013
            r5 = r3
            goto L_0x0014
        L_0x0013:
            r5 = r4
        L_0x0014:
            r6 = r1 & 4
            if (r6 == 0) goto L_0x001a
            r6 = r3
            goto L_0x001b
        L_0x001a:
            r6 = r4
        L_0x001b:
            r7 = r1 & 8
            if (r7 == 0) goto L_0x0021
            r7 = r3
            goto L_0x0022
        L_0x0021:
            r7 = r4
        L_0x0022:
            r8 = r1 & 16
            if (r8 == 0) goto L_0x0028
            r8 = r3
            goto L_0x0029
        L_0x0028:
            r8 = r4
        L_0x0029:
            r9 = r1 & 32
            if (r9 == 0) goto L_0x002f
            r9 = r3
            goto L_0x0030
        L_0x002f:
            r9 = r4
        L_0x0030:
            r10 = -2147483648(0xffffffff80000000, float:-0.0)
            r10 = r10 & r1
            if (r10 == 0) goto L_0x0037
            r10 = r3
            goto L_0x0038
        L_0x0037:
            r10 = r4
        L_0x0038:
            r1 = r1 & 64
            if (r1 == 0) goto L_0x003e
            r1 = r3
            goto L_0x003f
        L_0x003e:
            r1 = r4
        L_0x003f:
            android.app.ActivityManager$RunningTaskInfo r11 = r0.taskInfo
            int r11 = r11.topActivityType
            r12 = 2
            if (r11 != r12) goto L_0x0048
            r11 = r3
            goto L_0x0049
        L_0x0048:
            r11 = r4
        L_0x0049:
            boolean r13 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            r14 = 5
            r16 = 3
            r17 = 4
            if (r13 == 0) goto L_0x009e
            com.android.wm.shell.protolog.ShellProtoLogGroup r13 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r15 = 9
            java.lang.Object[] r15 = new java.lang.Object[r15]
            java.lang.Boolean r18 = java.lang.Boolean.valueOf(r2)
            r15[r4] = r18
            java.lang.Boolean r18 = java.lang.Boolean.valueOf(r5)
            r15[r3] = r18
            java.lang.Boolean r18 = java.lang.Boolean.valueOf(r6)
            r15[r12] = r18
            java.lang.Boolean r18 = java.lang.Boolean.valueOf(r7)
            r15[r16] = r18
            java.lang.Boolean r18 = java.lang.Boolean.valueOf(r8)
            r15[r17] = r18
            java.lang.Boolean r18 = java.lang.Boolean.valueOf(r9)
            r15[r14] = r18
            r18 = 6
            java.lang.Boolean r19 = java.lang.Boolean.valueOf(r10)
            r15[r18] = r19
            r18 = 7
            java.lang.Boolean r19 = java.lang.Boolean.valueOf(r1)
            r15[r18] = r19
            java.lang.Boolean r18 = java.lang.Boolean.valueOf(r11)
            r19 = 8
            r15[r19] = r18
            r14 = 262143(0x3ffff, float:3.6734E-40)
            r12 = -801237687(0xffffffffd03e1549, float:-1.27562639E10)
            r4 = 0
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r13, r12, r14, r4, r15)
        L_0x009e:
            if (r11 != 0) goto L_0x00b2
            if (r6 == 0) goto L_0x00a8
            if (r2 != 0) goto L_0x00a8
            if (r5 == 0) goto L_0x00b2
            if (r8 != 0) goto L_0x00b2
        L_0x00a8:
            if (r9 == 0) goto L_0x00ad
            r3 = r16
            goto L_0x00b1
        L_0x00ad:
            if (r10 == 0) goto L_0x00b1
            r3 = r17
        L_0x00b1:
            return r3
        L_0x00b2:
            if (r5 == 0) goto L_0x0146
            if (r7 == 0) goto L_0x0138
            android.window.TaskSnapshot r2 = r0.taskSnapshot
            if (r2 != 0) goto L_0x00d6
            boolean r2 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r2 == 0) goto L_0x00fe
            android.app.ActivityManager$RunningTaskInfo r0 = r0.taskInfo
            int r0 = r0.taskId
            long r4 = (long) r0
            com.android.wm.shell.protolog.ShellProtoLogGroup r0 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r2 = 1626392290(0x60f0c6e2, float:1.38798425E20)
            java.lang.Object[] r6 = new java.lang.Object[r3]
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r5 = 0
            r6[r5] = r4
            r4 = 0
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r0, r2, r3, r4, r6)
            goto L_0x00fe
        L_0x00d6:
            android.content.ComponentName r4 = r2.getTopActivityComponent()
            android.app.ActivityManager$RunningTaskInfo r5 = r0.taskInfo
            android.content.ComponentName r5 = r5.topActivity
            boolean r4 = r4.equals(r5)
            if (r4 != 0) goto L_0x0100
            boolean r2 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r2 == 0) goto L_0x00fe
            android.app.ActivityManager$RunningTaskInfo r0 = r0.taskInfo
            android.content.ComponentName r0 = r0.topActivity
            java.lang.String r0 = java.lang.String.valueOf(r0)
            com.android.wm.shell.protolog.ShellProtoLogGroup r2 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r4 = -1011603105(0xffffffffc3b4295f, float:-360.3232)
            java.lang.Object[] r5 = new java.lang.Object[r3]
            r6 = 0
            r5[r6] = r0
            r0 = 0
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r2, r4, r6, r0, r5)
        L_0x00fe:
            r5 = 0
            goto L_0x0131
        L_0x0100:
            android.app.ActivityManager$RunningTaskInfo r0 = r0.taskInfo
            android.content.res.Configuration r0 = r0.configuration
            android.app.WindowConfiguration r0 = r0.windowConfiguration
            int r0 = r0.getRotation()
            int r2 = r2.getRotation()
            boolean r4 = com.android.p012wm.shell.protolog.ShellProtoLogCache.WM_SHELL_STARTING_WINDOW_enabled
            if (r4 == 0) goto L_0x012e
            long r4 = (long) r0
            long r6 = (long) r2
            com.android.wm.shell.protolog.ShellProtoLogGroup r8 = com.android.p012wm.shell.protolog.ShellProtoLogGroup.WM_SHELL_STARTING_WINDOW
            r12 = 128462746(0x7a82f9a, float:2.5305808E-34)
            r13 = 2
            java.lang.Object[] r14 = new java.lang.Object[r13]
            java.lang.Long r4 = java.lang.Long.valueOf(r4)
            r5 = 0
            r14[r5] = r4
            java.lang.Long r4 = java.lang.Long.valueOf(r6)
            r14[r3] = r4
            r4 = 5
            r5 = 0
            com.android.p012wm.shell.protolog.ShellProtoLogImpl.m80v(r8, r12, r4, r5, r14)
        L_0x012e:
            if (r0 != r2) goto L_0x00fe
            r5 = r3
        L_0x0131:
            if (r5 == 0) goto L_0x0135
            r0 = 2
            return r0
        L_0x0135:
            if (r11 != 0) goto L_0x0138
            return r16
        L_0x0138:
            if (r1 != 0) goto L_0x0146
            if (r11 != 0) goto L_0x0146
            if (r9 == 0) goto L_0x0141
            r3 = r16
            goto L_0x0145
        L_0x0141:
            if (r10 == 0) goto L_0x0145
            r3 = r17
        L_0x0145:
            return r3
        L_0x0146:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.startingsurface.phone.PhoneStartingWindowTypeAlgorithm.getSuggestedWindowType(android.window.StartingWindowInfo):int");
    }
}
