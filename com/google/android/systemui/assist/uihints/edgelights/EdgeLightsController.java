package com.google.android.systemui.assist.uihints.edgelights;

import android.content.Context;
import android.view.ViewGroup;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.assist.AssistLogger;
import com.google.android.systemui.assist.uihints.NgaMessageHandler;
import com.google.android.systemui.assist.uihints.edgelights.EdgeLightsView;
import java.util.Objects;

public final class EdgeLightsController implements NgaMessageHandler.AudioInfoListener, NgaMessageHandler.EdgeLightsInfoListener {
    public final AssistLogger mAssistLogger;
    public final Context mContext;
    public final EdgeLightsView mEdgeLightsView;
    public ModeChangeThrottler mThrottler;

    public interface ModeChangeThrottler {
    }

    public final EdgeLightsView.Mode getMode() {
        EdgeLightsView edgeLightsView = this.mEdgeLightsView;
        Objects.requireNonNull(edgeLightsView);
        return edgeLightsView.mMode;
    }

    public final void onAudioInfo(float f, float f2) {
        EdgeLightsView edgeLightsView = this.mEdgeLightsView;
        Objects.requireNonNull(edgeLightsView);
        edgeLightsView.mMode.onAudioLevelUpdate(f2);
    }

    public EdgeLightsController(Context context, ViewGroup viewGroup, AssistLogger assistLogger) {
        this.mContext = context;
        this.mAssistLogger = assistLogger;
        this.mEdgeLightsView = (EdgeLightsView) viewGroup.findViewById(C1777R.C1779id.edge_lights);
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onEdgeLightsInfo(java.lang.String r10, boolean r11) {
        /*
            r9 = this;
            java.util.Objects.requireNonNull(r10)
            int r0 = r10.hashCode()
            r1 = 4
            r2 = 3
            r3 = 2
            r4 = 1
            java.lang.String r5 = "FULL_LISTENING"
            r6 = 0
            switch(r0) {
                case -1911007510: goto L_0x003c;
                case 2193567: goto L_0x0031;
                case 429932431: goto L_0x0026;
                case 1387022046: goto L_0x001b;
                case 1971150571: goto L_0x0012;
                default: goto L_0x0011;
            }
        L_0x0011:
            goto L_0x0047
        L_0x0012:
            boolean r0 = r10.equals(r5)
            if (r0 != 0) goto L_0x0019
            goto L_0x0047
        L_0x0019:
            r0 = r1
            goto L_0x0048
        L_0x001b:
            java.lang.String r0 = "FULFILL_PERIMETER"
            boolean r0 = r10.equals(r0)
            if (r0 != 0) goto L_0x0024
            goto L_0x0047
        L_0x0024:
            r0 = r2
            goto L_0x0048
        L_0x0026:
            java.lang.String r0 = "HALF_LISTENING"
            boolean r0 = r10.equals(r0)
            if (r0 != 0) goto L_0x002f
            goto L_0x0047
        L_0x002f:
            r0 = r3
            goto L_0x0048
        L_0x0031:
            java.lang.String r0 = "GONE"
            boolean r0 = r10.equals(r0)
            if (r0 != 0) goto L_0x003a
            goto L_0x0047
        L_0x003a:
            r0 = r4
            goto L_0x0048
        L_0x003c:
            java.lang.String r0 = "FULFILL_BOTTOM"
            boolean r0 = r10.equals(r0)
            if (r0 != 0) goto L_0x0045
            goto L_0x0047
        L_0x0045:
            r0 = r6
            goto L_0x0048
        L_0x0047:
            r0 = -1
        L_0x0048:
            java.lang.String r7 = "EdgeLightsController"
            r8 = 0
            if (r0 == 0) goto L_0x007a
            if (r0 == r4) goto L_0x0074
            if (r0 == r3) goto L_0x0067
            if (r0 == r2) goto L_0x005f
            if (r0 == r1) goto L_0x0057
            r11 = r8
            goto L_0x0082
        L_0x0057:
            com.google.android.systemui.assist.uihints.edgelights.mode.FullListening r11 = new com.google.android.systemui.assist.uihints.edgelights.mode.FullListening
            android.content.Context r0 = r9.mContext
            r11.<init>(r0, r6)
            goto L_0x0082
        L_0x005f:
            com.google.android.systemui.assist.uihints.edgelights.mode.FulfillPerimeter r11 = new com.google.android.systemui.assist.uihints.edgelights.mode.FulfillPerimeter
            android.content.Context r0 = r9.mContext
            r11.<init>(r0)
            goto L_0x0082
        L_0x0067:
            java.lang.String r11 = "Rendering full instead of half listening for now."
            android.util.Log.i(r7, r11)
            com.google.android.systemui.assist.uihints.edgelights.mode.FullListening r11 = new com.google.android.systemui.assist.uihints.edgelights.mode.FullListening
            android.content.Context r0 = r9.mContext
            r11.<init>(r0, r4)
            goto L_0x0082
        L_0x0074:
            com.google.android.systemui.assist.uihints.edgelights.mode.Gone r11 = new com.google.android.systemui.assist.uihints.edgelights.mode.Gone
            r11.<init>()
            goto L_0x0082
        L_0x007a:
            com.google.android.systemui.assist.uihints.edgelights.mode.FulfillBottom r0 = new com.google.android.systemui.assist.uihints.edgelights.mode.FulfillBottom
            android.content.Context r1 = r9.mContext
            r0.<init>(r1, r11)
            r11 = r0
        L_0x0082:
            if (r11 != 0) goto L_0x0099
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r11 = "Invalid edge lights mode: "
            r9.append(r11)
            r9.append(r10)
            java.lang.String r9 = r9.toString()
            android.util.Log.e(r7, r9)
            return
        L_0x0099:
            com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda21 r0 = new com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda21
            r1 = 5
            r0.<init>(r9, r11, r1)
            com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController$ModeChangeThrottler r9 = r9.mThrottler
            if (r9 != 0) goto L_0x00a7
            r0.run()
            goto L_0x00d6
        L_0x00a7:
            com.google.android.systemui.assist.uihints.NgaUiController$$ExternalSyntheticLambda2 r9 = (com.google.android.systemui.assist.uihints.NgaUiController$$ExternalSyntheticLambda2) r9
            java.lang.Object r9 = r9.f$0
            com.google.android.systemui.assist.uihints.NgaUiController r9 = (com.google.android.systemui.assist.uihints.NgaUiController) r9
            java.util.Objects.requireNonNull(r9)
            android.animation.ValueAnimator r11 = r9.mInvocationAnimator
            if (r11 == 0) goto L_0x00bd
            boolean r11 = r11.isStarted()
            if (r11 == 0) goto L_0x00bd
            r9.mPendingEdgeLightsModeChange = r0
            goto L_0x00d6
        L_0x00bd:
            boolean r11 = r9.mShowingAssistUi
            if (r11 != 0) goto L_0x00d1
            boolean r10 = r5.equals(r10)
            if (r10 == 0) goto L_0x00d1
            r9.mInvocationInProgress = r4
            r10 = 1065353216(0x3f800000, float:1.0)
            r9.onInvocationProgress(r6, r10)
            r9.mPendingEdgeLightsModeChange = r0
            goto L_0x00d6
        L_0x00d1:
            r9.mPendingEdgeLightsModeChange = r8
            r0.run()
        L_0x00d6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.assist.uihints.edgelights.EdgeLightsController.onEdgeLightsInfo(java.lang.String, boolean):void");
    }
}
