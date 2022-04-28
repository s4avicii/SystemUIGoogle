package com.android.systemui.statusbar.policy;

import com.android.systemui.statusbar.policy.VariableDateView;

/* compiled from: VariableDateViewController.kt */
public final class VariableDateViewController$onMeasureListener$1 implements VariableDateView.OnMeasureListener {
    public final /* synthetic */ VariableDateViewController this$0;

    public VariableDateViewController$onMeasureListener$1(VariableDateViewController variableDateViewController) {
        this.this$0 = variableDateViewController;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:7:0x0024, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual(r1, r2.longerPattern) == false) goto L_0x0026;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasureAction(int r6) {
        /*
            r5 = this;
            com.android.systemui.statusbar.policy.VariableDateViewController r0 = r5.this$0
            int r1 = r0.lastWidth
            if (r6 == r1) goto L_0x00a3
            T r1 = r0.mView
            com.android.systemui.statusbar.policy.VariableDateView r1 = (com.android.systemui.statusbar.policy.VariableDateView) r1
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.freezeSwitching
            if (r1 != 0) goto L_0x009f
            int r1 = r0.lastWidth
            if (r6 <= r1) goto L_0x0026
            java.lang.String r1 = r0.datePattern
            T r2 = r0.mView
            com.android.systemui.statusbar.policy.VariableDateView r2 = (com.android.systemui.statusbar.policy.VariableDateView) r2
            java.util.Objects.requireNonNull(r2)
            java.lang.String r2 = r2.longerPattern
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            if (r1 != 0) goto L_0x009f
        L_0x0026:
            int r1 = r0.lastWidth
            java.lang.String r2 = ""
            if (r6 >= r1) goto L_0x0035
            java.lang.String r1 = r0.datePattern
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r2)
            if (r1 == 0) goto L_0x0035
            goto L_0x009f
        L_0x0035:
            java.util.Date r1 = r0.currentTime
            T r3 = r0.mView
            com.android.systemui.statusbar.policy.VariableDateView r3 = (com.android.systemui.statusbar.policy.VariableDateView) r3
            java.util.Objects.requireNonNull(r3)
            java.lang.String r3 = r3.longerPattern
            android.icu.text.DateFormat r3 = com.android.systemui.statusbar.policy.VariableDateViewControllerKt.getFormatFromPattern(r3)
            java.lang.String r1 = com.android.systemui.statusbar.policy.VariableDateViewControllerKt.getTextForFormat(r1, r3)
            T r3 = r0.mView
            com.android.systemui.statusbar.policy.VariableDateView r3 = (com.android.systemui.statusbar.policy.VariableDateView) r3
            java.util.Objects.requireNonNull(r3)
            android.text.TextPaint r3 = r3.getPaint()
            float r1 = android.text.Layout.getDesiredWidth(r1, r3)
            float r3 = (float) r6
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x0069
            T r1 = r0.mView
            com.android.systemui.statusbar.policy.VariableDateView r1 = (com.android.systemui.statusbar.policy.VariableDateView) r1
            java.util.Objects.requireNonNull(r1)
            java.lang.String r1 = r1.longerPattern
            r0.changePattern(r1)
            goto L_0x009f
        L_0x0069:
            java.util.Date r1 = r0.currentTime
            T r4 = r0.mView
            com.android.systemui.statusbar.policy.VariableDateView r4 = (com.android.systemui.statusbar.policy.VariableDateView) r4
            java.util.Objects.requireNonNull(r4)
            java.lang.String r4 = r4.shorterPattern
            android.icu.text.DateFormat r4 = com.android.systemui.statusbar.policy.VariableDateViewControllerKt.getFormatFromPattern(r4)
            java.lang.String r1 = com.android.systemui.statusbar.policy.VariableDateViewControllerKt.getTextForFormat(r1, r4)
            T r4 = r0.mView
            com.android.systemui.statusbar.policy.VariableDateView r4 = (com.android.systemui.statusbar.policy.VariableDateView) r4
            java.util.Objects.requireNonNull(r4)
            android.text.TextPaint r4 = r4.getPaint()
            float r1 = android.text.Layout.getDesiredWidth(r1, r4)
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x009c
            T r1 = r0.mView
            com.android.systemui.statusbar.policy.VariableDateView r1 = (com.android.systemui.statusbar.policy.VariableDateView) r1
            java.util.Objects.requireNonNull(r1)
            java.lang.String r1 = r1.shorterPattern
            r0.changePattern(r1)
            goto L_0x009f
        L_0x009c:
            r0.changePattern(r2)
        L_0x009f:
            com.android.systemui.statusbar.policy.VariableDateViewController r5 = r5.this$0
            r5.lastWidth = r6
        L_0x00a3:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.VariableDateViewController$onMeasureListener$1.onMeasureAction(int):void");
    }
}
