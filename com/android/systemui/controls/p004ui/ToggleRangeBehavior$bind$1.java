package com.android.systemui.controls.p004ui;

import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.Objects;

/* renamed from: com.android.systemui.controls.ui.ToggleRangeBehavior$bind$1 */
/* compiled from: ToggleRangeBehavior.kt */
public final class ToggleRangeBehavior$bind$1 extends View.AccessibilityDelegate {
    public final /* synthetic */ ToggleRangeBehavior this$0;

    public final boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x00a0  */
    /* JADX WARNING: Removed duplicated region for block: B:24:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean performAccessibilityAction(android.view.View r8, int r9, android.os.Bundle r10) {
        /*
            r7 = this;
            r0 = 0
            r1 = 1
            r2 = 16
            if (r9 != r2) goto L_0x0038
            com.android.systemui.controls.ui.ToggleRangeBehavior r2 = r7.this$0
            java.util.Objects.requireNonNull(r2)
            boolean r2 = r2.isToggleable
            if (r2 != 0) goto L_0x0011
            goto L_0x0097
        L_0x0011:
            com.android.systemui.controls.ui.ToggleRangeBehavior r2 = r7.this$0
            com.android.systemui.controls.ui.ControlViewHolder r2 = r2.getCvh()
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.controls.ui.ControlActionCoordinator r2 = r2.controlActionCoordinator
            com.android.systemui.controls.ui.ToggleRangeBehavior r3 = r7.this$0
            com.android.systemui.controls.ui.ControlViewHolder r3 = r3.getCvh()
            com.android.systemui.controls.ui.ToggleRangeBehavior r4 = r7.this$0
            java.util.Objects.requireNonNull(r4)
            java.lang.String r4 = r4.templateId
            if (r4 == 0) goto L_0x002c
            goto L_0x002d
        L_0x002c:
            r4 = 0
        L_0x002d:
            com.android.systemui.controls.ui.ToggleRangeBehavior r5 = r7.this$0
            java.util.Objects.requireNonNull(r5)
            boolean r5 = r5.isChecked
            r2.toggle(r3, r4, r5)
            goto L_0x0095
        L_0x0038:
            r2 = 32
            if (r9 != r2) goto L_0x0051
            com.android.systemui.controls.ui.ToggleRangeBehavior r2 = r7.this$0
            com.android.systemui.controls.ui.ControlViewHolder r2 = r2.getCvh()
            java.util.Objects.requireNonNull(r2)
            com.android.systemui.controls.ui.ControlActionCoordinator r2 = r2.controlActionCoordinator
            com.android.systemui.controls.ui.ToggleRangeBehavior r3 = r7.this$0
            com.android.systemui.controls.ui.ControlViewHolder r3 = r3.getCvh()
            r2.longPress(r3)
            goto L_0x0095
        L_0x0051:
            android.view.accessibility.AccessibilityNodeInfo$AccessibilityAction r2 = android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS
            int r2 = r2.getId()
            if (r9 != r2) goto L_0x0097
            if (r10 == 0) goto L_0x0097
            java.lang.String r2 = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE"
            boolean r3 = r10.containsKey(r2)
            if (r3 != 0) goto L_0x0064
            goto L_0x0097
        L_0x0064:
            float r2 = r10.getFloat(r2)
            com.android.systemui.controls.ui.ToggleRangeBehavior r3 = r7.this$0
            java.util.Objects.requireNonNull(r3)
            android.service.controls.templates.RangeTemplate r4 = r3.getRangeTemplate()
            float r4 = r4.getMinValue()
            android.service.controls.templates.RangeTemplate r3 = r3.getRangeTemplate()
            float r3 = r3.getMaxValue()
            r5 = 0
            r6 = 1176256512(0x461c4000, float:10000.0)
            float r2 = android.util.MathUtils.constrainedMap(r5, r6, r4, r3, r2)
            int r2 = (int) r2
            com.android.systemui.controls.ui.ToggleRangeBehavior r3 = r7.this$0
            java.util.Objects.requireNonNull(r3)
            boolean r4 = r3.isChecked
            r3.updateRange(r2, r4, r1)
            com.android.systemui.controls.ui.ToggleRangeBehavior r2 = r7.this$0
            r2.endUpdateRange()
        L_0x0095:
            r2 = r1
            goto L_0x0098
        L_0x0097:
            r2 = r0
        L_0x0098:
            if (r2 != 0) goto L_0x00a0
            boolean r7 = super.performAccessibilityAction(r8, r9, r10)
            if (r7 == 0) goto L_0x00a1
        L_0x00a0:
            r0 = r1
        L_0x00a1:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.controls.p004ui.ToggleRangeBehavior$bind$1.performAccessibilityAction(android.view.View, int, android.os.Bundle):boolean");
    }

    public ToggleRangeBehavior$bind$1(ToggleRangeBehavior toggleRangeBehavior) {
        this.this$0 = toggleRangeBehavior;
    }

    public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
        int i = 0;
        float levelToRangeValue = this.this$0.levelToRangeValue(0);
        ToggleRangeBehavior toggleRangeBehavior = this.this$0;
        float levelToRangeValue2 = toggleRangeBehavior.levelToRangeValue(toggleRangeBehavior.getClipLayer().getLevel());
        float levelToRangeValue3 = this.this$0.levelToRangeValue(10000);
        double stepValue = (double) this.this$0.getRangeTemplate().getStepValue();
        if (stepValue == Math.floor(stepValue)) {
            i = 1;
        }
        int i2 = i ^ 1;
        ToggleRangeBehavior toggleRangeBehavior2 = this.this$0;
        Objects.requireNonNull(toggleRangeBehavior2);
        if (toggleRangeBehavior2.isChecked) {
            accessibilityNodeInfo.setRangeInfo(AccessibilityNodeInfo.RangeInfo.obtain(i2, levelToRangeValue, levelToRangeValue3, levelToRangeValue2));
        }
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SET_PROGRESS);
    }
}
