package com.android.p012wm.shell;

import com.android.p012wm.shell.ShellCommandHandlerImpl;
import java.io.PrintWriter;

/* renamed from: com.android.wm.shell.ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda1 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ ShellCommandHandlerImpl.HandlerImpl f$0;
    public final /* synthetic */ boolean[] f$1;
    public final /* synthetic */ String[] f$2;
    public final /* synthetic */ PrintWriter f$3;

    public /* synthetic */ ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda1(ShellCommandHandlerImpl.HandlerImpl handlerImpl, boolean[] zArr, String[] strArr, PrintWriter printWriter) {
        this.f$0 = handlerImpl;
        this.f$1 = zArr;
        this.f$2 = strArr;
        this.f$3 = printWriter;
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r11 = this;
            com.android.wm.shell.ShellCommandHandlerImpl$HandlerImpl r0 = r11.f$0
            boolean[] r1 = r11.f$1
            java.lang.String[] r2 = r11.f$2
            java.io.PrintWriter r11 = r11.f$3
            java.util.Objects.requireNonNull(r0)
            com.android.wm.shell.ShellCommandHandlerImpl r0 = com.android.p012wm.shell.ShellCommandHandlerImpl.this
            java.util.Objects.requireNonNull(r0)
            int r3 = r2.length
            r4 = 2
            r5 = 0
            if (r3 >= r4) goto L_0x0017
            goto L_0x01ae
        L_0x0017:
            r3 = 1
            r6 = r2[r3]
            java.util.Objects.requireNonNull(r6)
            r7 = -1
            int r8 = r6.hashCode()
            r9 = 4
            r10 = 3
            switch(r8) {
                case -968877417: goto L_0x006d;
                case -840336141: goto L_0x0061;
                case -91197669: goto L_0x0056;
                case 3198785: goto L_0x004b;
                case 3433178: goto L_0x0040;
                case 295561529: goto L_0x0034;
                case 1522429422: goto L_0x0028;
                default: goto L_0x0027;
            }
        L_0x0027:
            goto L_0x0078
        L_0x0028:
            java.lang.String r8 = "setSideStagePosition"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L_0x0032
            goto L_0x0078
        L_0x0032:
            r7 = 6
            goto L_0x0078
        L_0x0034:
            java.lang.String r8 = "removeFromSideStage"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L_0x003e
            goto L_0x0078
        L_0x003e:
            r7 = 5
            goto L_0x0078
        L_0x0040:
            java.lang.String r8 = "pair"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L_0x0049
            goto L_0x0078
        L_0x0049:
            r7 = r9
            goto L_0x0078
        L_0x004b:
            java.lang.String r8 = "help"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L_0x0054
            goto L_0x0078
        L_0x0054:
            r7 = r10
            goto L_0x0078
        L_0x0056:
            java.lang.String r8 = "moveToSideStage"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L_0x005f
            goto L_0x0078
        L_0x005f:
            r7 = r4
            goto L_0x0078
        L_0x0061:
            java.lang.String r8 = "unpair"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L_0x006b
            goto L_0x0078
        L_0x006b:
            r7 = r3
            goto L_0x0078
        L_0x006d:
            java.lang.String r8 = "setSideStageVisibility"
            boolean r6 = r6.equals(r8)
            if (r6 != 0) goto L_0x0077
            goto L_0x0078
        L_0x0077:
            r7 = r5
        L_0x0078:
            java.lang.String r6 = "Error: task id should be provided as arguments"
            switch(r7) {
                case 0: goto L_0x0193;
                case 1: goto L_0x0174;
                case 2: goto L_0x0147;
                case 3: goto L_0x00eb;
                case 4: goto L_0x00bf;
                case 5: goto L_0x00a0;
                case 6: goto L_0x007f;
                default: goto L_0x007d;
            }
        L_0x007d:
            goto L_0x01ae
        L_0x007f:
            int r6 = r2.length
            if (r6 >= r10) goto L_0x0089
            java.lang.String r0 = "Error: side stage position should be provided as arguments"
            r11.println(r0)
            goto L_0x01ae
        L_0x0089:
            java.lang.Integer r11 = new java.lang.Integer
            r2 = r2[r4]
            r11.<init>(r2)
            int r11 = r11.intValue()
            java.util.Optional<com.android.wm.shell.splitscreen.SplitScreenController> r0 = r0.mSplitScreenOptional
            com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda6 r2 = new com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda6
            r2.<init>(r11)
            r0.ifPresent(r2)
            goto L_0x01af
        L_0x00a0:
            int r7 = r2.length
            if (r7 >= r10) goto L_0x00a8
            r11.println(r6)
            goto L_0x01ae
        L_0x00a8:
            java.lang.Integer r11 = new java.lang.Integer
            r2 = r2[r4]
            r11.<init>(r2)
            int r11 = r11.intValue()
            java.util.Optional<com.android.wm.shell.splitscreen.SplitScreenController> r0 = r0.mSplitScreenOptional
            com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda0 r2 = new com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda0
            r2.<init>(r11, r5)
            r0.ifPresent(r2)
            goto L_0x01af
        L_0x00bf:
            int r6 = r2.length
            if (r6 >= r9) goto L_0x00c9
            java.lang.String r0 = "Error: two task ids should be provided as arguments"
            r11.println(r0)
            goto L_0x01ae
        L_0x00c9:
            java.lang.Integer r11 = new java.lang.Integer
            r4 = r2[r4]
            r11.<init>(r4)
            int r11 = r11.intValue()
            java.lang.Integer r4 = new java.lang.Integer
            r2 = r2[r10]
            r4.<init>(r2)
            int r2 = r4.intValue()
            java.util.Optional<com.android.wm.shell.apppairs.AppPairsController> r0 = r0.mAppPairsOptional
            com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda7 r4 = new com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda7
            r4.<init>(r11, r2)
            r0.ifPresent(r4)
            goto L_0x01af
        L_0x00eb:
            java.lang.String r0 = "Window Manager Shell commands:"
            r11.println(r0)
            java.lang.String r0 = "  help"
            r11.println(r0)
            java.lang.String r0 = "      Print this help text."
            r11.println(r0)
            java.lang.String r0 = "  <no arguments provided>"
            r11.println(r0)
            java.lang.String r0 = "    Dump Window Manager Shell internal state"
            r11.println(r0)
            java.lang.String r0 = "  pair <taskId1> <taskId2>"
            r11.println(r0)
            java.lang.String r0 = "  unpair <taskId>"
            r11.println(r0)
            java.lang.String r0 = "    Pairs/unpairs tasks with given ids."
            r11.println(r0)
            java.lang.String r0 = "  moveToSideStage <taskId> <SideStagePosition>"
            r11.println(r0)
            java.lang.String r0 = "    Move a task with given id in split-screen mode."
            r11.println(r0)
            java.lang.String r0 = "  removeFromSideStage <taskId>"
            r11.println(r0)
            java.lang.String r0 = "    Remove a task with given id in split-screen mode."
            r11.println(r0)
            java.lang.String r0 = "  setSideStageOutline <true/false>"
            r11.println(r0)
            java.lang.String r0 = "    Enable/Disable outline on the side-stage."
            r11.println(r0)
            java.lang.String r0 = "  setSideStagePosition <SideStagePosition>"
            r11.println(r0)
            java.lang.String r0 = "    Sets the position of the side-stage."
            r11.println(r0)
            java.lang.String r0 = "  setSideStageVisibility <true/false>"
            r11.println(r0)
            java.lang.String r0 = "    Show/hide side-stage."
            r11.println(r0)
            goto L_0x01af
        L_0x0147:
            int r7 = r2.length
            if (r7 >= r10) goto L_0x014e
            r11.println(r6)
            goto L_0x01ae
        L_0x014e:
            java.lang.Integer r11 = new java.lang.Integer
            r4 = r2[r4]
            r11.<init>(r4)
            int r11 = r11.intValue()
            int r4 = r2.length
            if (r4 <= r10) goto L_0x0168
            java.lang.Integer r4 = new java.lang.Integer
            r2 = r2[r10]
            r4.<init>(r2)
            int r2 = r4.intValue()
            goto L_0x0169
        L_0x0168:
            r2 = r3
        L_0x0169:
            java.util.Optional<com.android.wm.shell.splitscreen.SplitScreenController> r0 = r0.mSplitScreenOptional
            com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda8 r4 = new com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda8
            r4.<init>(r11, r2)
            r0.ifPresent(r4)
            goto L_0x01af
        L_0x0174:
            int r6 = r2.length
            if (r6 >= r10) goto L_0x017d
            java.lang.String r0 = "Error: task id should be provided as an argument"
            r11.println(r0)
            goto L_0x01ae
        L_0x017d:
            java.lang.Integer r11 = new java.lang.Integer
            r2 = r2[r4]
            r11.<init>(r2)
            int r11 = r11.intValue()
            java.util.Optional<com.android.wm.shell.apppairs.AppPairsController> r0 = r0.mAppPairsOptional
            com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda5 r2 = new com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda5
            r2.<init>(r11)
            r0.ifPresent(r2)
            goto L_0x01af
        L_0x0193:
            int r6 = r2.length
            if (r6 >= r10) goto L_0x019c
            java.lang.String r0 = "Error: side stage visibility should be provided as arguments"
            r11.println(r0)
            goto L_0x01ae
        L_0x019c:
            java.lang.Boolean r11 = new java.lang.Boolean
            r2 = r2[r4]
            r11.<init>(r2)
            java.util.Optional<com.android.wm.shell.splitscreen.SplitScreenController> r0 = r0.mSplitScreenOptional
            com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda2 r2 = new com.android.wm.shell.ShellCommandHandlerImpl$$ExternalSyntheticLambda2
            r2.<init>(r11, r5)
            r0.ifPresent(r2)
            goto L_0x01af
        L_0x01ae:
            r3 = r5
        L_0x01af:
            r1[r5] = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.ShellCommandHandlerImpl$HandlerImpl$$ExternalSyntheticLambda1.run():void");
    }
}
