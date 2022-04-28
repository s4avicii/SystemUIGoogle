package androidx.constraintlayout.solver.widgets.analyzer;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.appcompat.view.SupportMenuInflater$$ExternalSyntheticOutline0;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public final class ChainRun extends WidgetRun {
    public int chainStyle;
    public ArrayList<WidgetRun> widgets = new ArrayList<>();

    public final void applyToWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            this.widgets.get(i).applyToWidget();
        }
    }

    public final void clear() {
        this.runGroup = null;
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
    }

    public final ConstraintWidget getFirstVisibleWidget() {
        for (int i = 0; i < this.widgets.size(); i++) {
            WidgetRun widgetRun = this.widgets.get(i);
            ConstraintWidget constraintWidget = widgetRun.widget;
            Objects.requireNonNull(constraintWidget);
            if (constraintWidget.mVisibility != 8) {
                return widgetRun.widget;
            }
        }
        return null;
    }

    public final void apply() {
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            it.next().apply();
        }
        int size = this.widgets.size();
        if (size >= 1) {
            ConstraintWidget constraintWidget = this.widgets.get(0).widget;
            ConstraintWidget constraintWidget2 = this.widgets.get(size - 1).widget;
            if (this.orientation == 0) {
                ConstraintAnchor constraintAnchor = constraintWidget.mLeft;
                ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
                DependencyNode target = WidgetRun.getTarget(constraintAnchor, 0);
                int margin = constraintAnchor.getMargin();
                ConstraintWidget firstVisibleWidget = getFirstVisibleWidget();
                if (firstVisibleWidget != null) {
                    margin = firstVisibleWidget.mLeft.getMargin();
                }
                if (target != null) {
                    WidgetRun.addTarget(this.start, target, margin);
                }
                DependencyNode target2 = WidgetRun.getTarget(constraintAnchor2, 0);
                int margin2 = constraintAnchor2.getMargin();
                ConstraintWidget lastVisibleWidget = getLastVisibleWidget();
                if (lastVisibleWidget != null) {
                    margin2 = lastVisibleWidget.mRight.getMargin();
                }
                if (target2 != null) {
                    WidgetRun.addTarget(this.end, target2, -margin2);
                }
            } else {
                ConstraintAnchor constraintAnchor3 = constraintWidget.mTop;
                ConstraintAnchor constraintAnchor4 = constraintWidget2.mBottom;
                DependencyNode target3 = WidgetRun.getTarget(constraintAnchor3, 1);
                int margin3 = constraintAnchor3.getMargin();
                ConstraintWidget firstVisibleWidget2 = getFirstVisibleWidget();
                if (firstVisibleWidget2 != null) {
                    margin3 = firstVisibleWidget2.mTop.getMargin();
                }
                if (target3 != null) {
                    WidgetRun.addTarget(this.start, target3, margin3);
                }
                DependencyNode target4 = WidgetRun.getTarget(constraintAnchor4, 1);
                int margin4 = constraintAnchor4.getMargin();
                ConstraintWidget lastVisibleWidget2 = getLastVisibleWidget();
                if (lastVisibleWidget2 != null) {
                    margin4 = lastVisibleWidget2.mBottom.getMargin();
                }
                if (target4 != null) {
                    WidgetRun.addTarget(this.end, target4, -margin4);
                }
            }
            this.start.updateDelegate = this;
            this.end.updateDelegate = this;
        }
    }

    public final ConstraintWidget getLastVisibleWidget() {
        for (int size = this.widgets.size() - 1; size >= 0; size--) {
            WidgetRun widgetRun = this.widgets.get(size);
            ConstraintWidget constraintWidget = widgetRun.widget;
            Objects.requireNonNull(constraintWidget);
            if (constraintWidget.mVisibility != 8) {
                return widgetRun.widget;
            }
        }
        return null;
    }

    public final long getWrapDimension() {
        int size = this.widgets.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            WidgetRun widgetRun = this.widgets.get(i);
            j = ((long) widgetRun.end.margin) + widgetRun.getWrapDimension() + j + ((long) widgetRun.start.margin);
        }
        return j;
    }

    public final boolean supportsWrapComputation() {
        int size = this.widgets.size();
        for (int i = 0; i < size; i++) {
            if (!this.widgets.get(i).supportsWrapComputation()) {
                return false;
            }
        }
        return true;
    }

    public final String toString() {
        String str;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("ChainRun ");
        if (this.orientation == 0) {
            str = "horizontal : ";
        } else {
            str = "vertical : ";
        }
        m.append(str);
        String sb = m.toString();
        Iterator<WidgetRun> it = this.widgets.iterator();
        while (it.hasNext()) {
            String m2 = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(sb, "<");
            sb = SupportMenuInflater$$ExternalSyntheticOutline0.m4m(m2 + it.next(), "> ");
        }
        return sb;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:104:0x01aa, code lost:
        if (r1 != r10) goto L_0x01d2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:112:0x01d0, code lost:
        if (r1 != r10) goto L_0x01d2;
     */
    /* JADX WARNING: Removed duplicated region for block: B:60:0x00dc  */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x00ee  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void update(androidx.constraintlayout.solver.widgets.analyzer.Dependency r27) {
        /*
            r26 = this;
            r0 = r26
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r0.start
            boolean r2 = r2.resolved
            if (r2 == 0) goto L_0x0446
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r0.end
            boolean r2 = r2.resolved
            if (r2 != 0) goto L_0x0012
            goto L_0x0446
        L_0x0012:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.widget
            java.util.Objects.requireNonNull(r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r2.mParent
            if (r2 == 0) goto L_0x0024
            boolean r4 = r2 instanceof androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer
            if (r4 == 0) goto L_0x0024
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r2 = (androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer) r2
            boolean r2 = r2.mIsRtl
            goto L_0x0025
        L_0x0024:
            r2 = 0
        L_0x0025:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r4 = r0.end
            int r4 = r4.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r5 = r0.start
            int r5 = r5.value
            int r4 = r4 - r5
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r5 = r0.widgets
            int r5 = r5.size()
            r6 = 0
        L_0x0035:
            r7 = -1
            r8 = 8
            if (r6 >= r5) goto L_0x004e
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r9 = r0.widgets
            java.lang.Object r9 = r9.get(r6)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r9 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r9
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r9.widget
            java.util.Objects.requireNonNull(r9)
            int r9 = r9.mVisibility
            if (r9 != r8) goto L_0x004f
            int r6 = r6 + 1
            goto L_0x0035
        L_0x004e:
            r6 = r7
        L_0x004f:
            int r9 = r5 + -1
            r10 = r9
        L_0x0052:
            if (r10 < 0) goto L_0x0069
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r11 = r0.widgets
            java.lang.Object r11 = r11.get(r10)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r11 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r11
            androidx.constraintlayout.solver.widgets.ConstraintWidget r11 = r11.widget
            java.util.Objects.requireNonNull(r11)
            int r11 = r11.mVisibility
            if (r11 != r8) goto L_0x0068
            int r10 = r10 + -1
            goto L_0x0052
        L_0x0068:
            r7 = r10
        L_0x0069:
            r10 = 0
        L_0x006a:
            r12 = 2
            if (r10 >= r12) goto L_0x0110
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 0
            r18 = 0
        L_0x0075:
            if (r14 >= r5) goto L_0x0100
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r3 = r0.widgets
            java.lang.Object r3 = r3.get(r14)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r3 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r3
            androidx.constraintlayout.solver.widgets.ConstraintWidget r12 = r3.widget
            java.util.Objects.requireNonNull(r12)
            int r12 = r12.mVisibility
            if (r12 != r8) goto L_0x008a
            goto L_0x00f9
        L_0x008a:
            int r17 = r17 + 1
            if (r14 <= 0) goto L_0x0095
            if (r14 < r6) goto L_0x0095
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r12 = r3.start
            int r12 = r12.margin
            int r15 = r15 + r12
        L_0x0095:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r12 = r3.dimension
            int r8 = r12.value
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r3.dimensionBehavior
            if (r11 == r1) goto L_0x009f
            r11 = 1
            goto L_0x00a0
        L_0x009f:
            r11 = 0
        L_0x00a0:
            if (r11 == 0) goto L_0x00c2
            int r12 = r0.orientation
            if (r12 != 0) goto L_0x00b1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = r3.widget
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r13 = r13.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r13 = r13.dimension
            boolean r13 = r13.resolved
            if (r13 != 0) goto L_0x00b1
            return
        L_0x00b1:
            r13 = 1
            if (r12 != r13) goto L_0x00bf
            androidx.constraintlayout.solver.widgets.ConstraintWidget r12 = r3.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r12 = r12.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r12 = r12.dimension
            boolean r12 = r12.resolved
            if (r12 != 0) goto L_0x00bf
            return
        L_0x00bf:
            r20 = r8
            goto L_0x00d8
        L_0x00c2:
            r20 = r8
            r13 = 1
            int r8 = r3.matchConstraintsType
            if (r8 != r13) goto L_0x00d0
            if (r10 != 0) goto L_0x00d0
            int r8 = r12.wrapValue
            int r16 = r16 + 1
            goto L_0x00d6
        L_0x00d0:
            boolean r8 = r12.resolved
            if (r8 == 0) goto L_0x00d8
            r8 = r20
        L_0x00d6:
            r11 = 1
            goto L_0x00da
        L_0x00d8:
            r8 = r20
        L_0x00da:
            if (r11 != 0) goto L_0x00ee
            int r16 = r16 + 1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = r3.widget
            float[] r8 = r8.mWeight
            int r11 = r0.orientation
            r8 = r8[r11]
            r11 = 0
            int r12 = (r8 > r11 ? 1 : (r8 == r11 ? 0 : -1))
            if (r12 < 0) goto L_0x00ef
            float r18 = r18 + r8
            goto L_0x00ef
        L_0x00ee:
            int r15 = r15 + r8
        L_0x00ef:
            if (r14 >= r9) goto L_0x00f9
            if (r14 >= r7) goto L_0x00f9
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r3.end
            int r3 = r3.margin
            int r3 = -r3
            int r15 = r15 + r3
        L_0x00f9:
            int r14 = r14 + 1
            r8 = 8
            r12 = 2
            goto L_0x0075
        L_0x0100:
            if (r15 < r4) goto L_0x010b
            if (r16 != 0) goto L_0x0105
            goto L_0x010b
        L_0x0105:
            int r10 = r10 + 1
            r8 = 8
            goto L_0x006a
        L_0x010b:
            r3 = r16
            r8 = r17
            goto L_0x0115
        L_0x0110:
            r3 = 0
            r8 = 0
            r15 = 0
            r18 = 0
        L_0x0115:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r0.start
            int r10 = r10.value
            if (r2 == 0) goto L_0x011f
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r0.end
            int r10 = r10.value
        L_0x011f:
            r11 = 1056964608(0x3f000000, float:0.5)
            if (r15 <= r4) goto L_0x0136
            r12 = 1073741824(0x40000000, float:2.0)
            if (r2 == 0) goto L_0x012f
            int r13 = r15 - r4
            float r13 = (float) r13
            float r13 = r13 / r12
            float r13 = r13 + r11
            int r12 = (int) r13
            int r10 = r10 + r12
            goto L_0x0136
        L_0x012f:
            int r13 = r15 - r4
            float r13 = (float) r13
            float r13 = r13 / r12
            float r13 = r13 + r11
            int r12 = (int) r13
            int r10 = r10 - r12
        L_0x0136:
            if (r3 <= 0) goto L_0x0245
            int r12 = r4 - r15
            float r12 = (float) r12
            float r13 = (float) r3
            float r13 = r12 / r13
            float r13 = r13 + r11
            int r13 = (int) r13
            r14 = 0
            r16 = 0
        L_0x0143:
            if (r14 >= r5) goto L_0x01f5
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r11 = r0.widgets
            java.lang.Object r11 = r11.get(r14)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r11 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r11
            r20 = r13
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = r11.widget
            java.util.Objects.requireNonNull(r13)
            int r13 = r13.mVisibility
            r21 = r15
            r15 = 8
            if (r13 != r15) goto L_0x015e
            goto L_0x01db
        L_0x015e:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r13 = r11.dimensionBehavior
            if (r13 != r1) goto L_0x01db
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r13 = r11.dimension
            boolean r15 = r13.resolved
            if (r15 != 0) goto L_0x01db
            r15 = 0
            int r19 = (r18 > r15 ? 1 : (r18 == r15 ? 0 : -1))
            if (r19 <= 0) goto L_0x017f
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = r11.widget
            float[] r15 = r15.mWeight
            r22 = r10
            int r10 = r0.orientation
            r10 = r15[r10]
            float r10 = r10 * r12
            float r10 = r10 / r18
            r15 = 1056964608(0x3f000000, float:0.5)
            float r10 = r10 + r15
            int r10 = (int) r10
            goto L_0x0183
        L_0x017f:
            r22 = r10
            r10 = r20
        L_0x0183:
            int r15 = r0.orientation
            if (r15 != 0) goto L_0x01ad
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = r11.widget
            r23 = r12
            int r12 = r15.mMatchConstraintMaxWidth
            int r15 = r15.mMatchConstraintMinWidth
            r24 = r1
            int r1 = r11.matchConstraintsType
            r25 = r2
            r2 = 1
            if (r1 != r2) goto L_0x019f
            int r1 = r13.wrapValue
            int r1 = java.lang.Math.min(r10, r1)
            goto L_0x01a0
        L_0x019f:
            r1 = r10
        L_0x01a0:
            int r1 = java.lang.Math.max(r15, r1)
            if (r12 <= 0) goto L_0x01aa
            int r1 = java.lang.Math.min(r12, r1)
        L_0x01aa:
            if (r1 == r10) goto L_0x01d5
            goto L_0x01d2
        L_0x01ad:
            r24 = r1
            r25 = r2
            r23 = r12
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r11.widget
            int r2 = r1.mMatchConstraintMaxHeight
            int r1 = r1.mMatchConstraintMinHeight
            int r12 = r11.matchConstraintsType
            r15 = 1
            if (r12 != r15) goto L_0x01c5
            int r12 = r13.wrapValue
            int r12 = java.lang.Math.min(r10, r12)
            goto L_0x01c6
        L_0x01c5:
            r12 = r10
        L_0x01c6:
            int r1 = java.lang.Math.max(r1, r12)
            if (r2 <= 0) goto L_0x01d0
            int r1 = java.lang.Math.min(r2, r1)
        L_0x01d0:
            if (r1 == r10) goto L_0x01d5
        L_0x01d2:
            int r16 = r16 + 1
            r10 = r1
        L_0x01d5:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r11.dimension
            r1.resolve(r10)
            goto L_0x01e3
        L_0x01db:
            r24 = r1
            r25 = r2
            r22 = r10
            r23 = r12
        L_0x01e3:
            int r14 = r14 + 1
            r13 = r20
            r15 = r21
            r10 = r22
            r12 = r23
            r1 = r24
            r2 = r25
            r11 = 1056964608(0x3f000000, float:0.5)
            goto L_0x0143
        L_0x01f5:
            r24 = r1
            r25 = r2
            r22 = r10
            r21 = r15
            if (r16 <= 0) goto L_0x0236
            int r3 = r3 - r16
            r1 = 0
            r2 = 0
        L_0x0203:
            if (r1 >= r5) goto L_0x0234
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r10 = r0.widgets
            java.lang.Object r10 = r10.get(r1)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r10 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r10
            androidx.constraintlayout.solver.widgets.ConstraintWidget r11 = r10.widget
            java.util.Objects.requireNonNull(r11)
            int r11 = r11.mVisibility
            r12 = 8
            if (r11 != r12) goto L_0x0219
            goto L_0x0231
        L_0x0219:
            if (r1 <= 0) goto L_0x0222
            if (r1 < r6) goto L_0x0222
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r11 = r10.start
            int r11 = r11.margin
            int r2 = r2 + r11
        L_0x0222:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r11 = r10.dimension
            int r11 = r11.value
            int r2 = r2 + r11
            if (r1 >= r9) goto L_0x0231
            if (r1 >= r7) goto L_0x0231
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r10.end
            int r10 = r10.margin
            int r10 = -r10
            int r2 = r2 + r10
        L_0x0231:
            int r1 = r1 + 1
            goto L_0x0203
        L_0x0234:
            r15 = r2
            goto L_0x0238
        L_0x0236:
            r15 = r21
        L_0x0238:
            int r1 = r0.chainStyle
            r2 = 2
            if (r1 != r2) goto L_0x0243
            if (r16 != 0) goto L_0x0243
            r1 = 0
            r0.chainStyle = r1
            goto L_0x024f
        L_0x0243:
            r1 = 0
            goto L_0x024f
        L_0x0245:
            r24 = r1
            r25 = r2
            r22 = r10
            r21 = r15
            r1 = 0
            r2 = 2
        L_0x024f:
            if (r15 <= r4) goto L_0x0253
            r0.chainStyle = r2
        L_0x0253:
            if (r8 <= 0) goto L_0x025b
            if (r3 != 0) goto L_0x025b
            if (r6 != r7) goto L_0x025b
            r0.chainStyle = r2
        L_0x025b:
            int r2 = r0.chainStyle
            r10 = 1
            if (r2 != r10) goto L_0x0301
            if (r8 <= r10) goto L_0x0266
            int r4 = r4 - r15
            int r8 = r8 - r10
            int r4 = r4 / r8
            goto L_0x026d
        L_0x0266:
            if (r8 != r10) goto L_0x026c
            int r4 = r4 - r15
            r2 = 2
            int r4 = r4 / r2
            goto L_0x026d
        L_0x026c:
            r4 = r1
        L_0x026d:
            if (r3 <= 0) goto L_0x0270
            r4 = r1
        L_0x0270:
            r3 = r1
            r10 = r22
        L_0x0273:
            if (r3 >= r5) goto L_0x0446
            if (r25 == 0) goto L_0x027c
            int r1 = r3 + 1
            int r1 = r5 - r1
            goto L_0x027d
        L_0x027c:
            r1 = r3
        L_0x027d:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r2 = r0.widgets
            java.lang.Object r1 = r2.get(r1)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r1 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r1.widget
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mVisibility
            r8 = 8
            if (r2 != r8) goto L_0x029d
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            r2.resolve(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            r1.resolve(r10)
            r12 = r24
            goto L_0x02fb
        L_0x029d:
            if (r3 <= 0) goto L_0x02a4
            if (r25 == 0) goto L_0x02a3
            int r10 = r10 - r4
            goto L_0x02a4
        L_0x02a3:
            int r10 = r10 + r4
        L_0x02a4:
            if (r3 <= 0) goto L_0x02b5
            if (r3 < r6) goto L_0x02b5
            if (r25 == 0) goto L_0x02b0
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            int r2 = r2.margin
            int r10 = r10 - r2
            goto L_0x02b5
        L_0x02b0:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            int r2 = r2.margin
            int r10 = r10 + r2
        L_0x02b5:
            if (r25 == 0) goto L_0x02bd
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.end
            r2.resolve(r10)
            goto L_0x02c2
        L_0x02bd:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            r2.resolve(r10)
        L_0x02c2:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r1.dimension
            int r8 = r2.value
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r1.dimensionBehavior
            r12 = r24
            if (r11 != r12) goto L_0x02d3
            int r11 = r1.matchConstraintsType
            r13 = 1
            if (r11 != r13) goto L_0x02d3
            int r8 = r2.wrapValue
        L_0x02d3:
            if (r25 == 0) goto L_0x02d7
            int r10 = r10 - r8
            goto L_0x02d8
        L_0x02d7:
            int r10 = r10 + r8
        L_0x02d8:
            if (r25 == 0) goto L_0x02e0
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            r2.resolve(r10)
            goto L_0x02e5
        L_0x02e0:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.end
            r2.resolve(r10)
        L_0x02e5:
            r2 = 1
            r1.resolved = r2
            if (r3 >= r9) goto L_0x02fb
            if (r3 >= r7) goto L_0x02fb
            if (r25 == 0) goto L_0x02f5
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            int r1 = r1.margin
            int r1 = -r1
            int r10 = r10 - r1
            goto L_0x02fb
        L_0x02f5:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            int r1 = r1.margin
            int r1 = -r1
            int r10 = r10 + r1
        L_0x02fb:
            int r3 = r3 + 1
            r24 = r12
            goto L_0x0273
        L_0x0301:
            r12 = r24
            if (r2 != 0) goto L_0x0396
            int r4 = r4 - r15
            r2 = 1
            int r8 = r8 + r2
            int r4 = r4 / r8
            if (r3 <= 0) goto L_0x030c
            r4 = r1
        L_0x030c:
            r3 = r1
            r10 = r22
        L_0x030f:
            if (r3 >= r5) goto L_0x0446
            if (r25 == 0) goto L_0x0318
            int r1 = r3 + 1
            int r1 = r5 - r1
            goto L_0x0319
        L_0x0318:
            r1 = r3
        L_0x0319:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r2 = r0.widgets
            java.lang.Object r1 = r2.get(r1)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r1 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r1.widget
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mVisibility
            r8 = 8
            if (r2 != r8) goto L_0x0337
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            r2.resolve(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            r1.resolve(r10)
            goto L_0x0392
        L_0x0337:
            if (r25 == 0) goto L_0x033b
            int r10 = r10 - r4
            goto L_0x033c
        L_0x033b:
            int r10 = r10 + r4
        L_0x033c:
            if (r3 <= 0) goto L_0x034d
            if (r3 < r6) goto L_0x034d
            if (r25 == 0) goto L_0x0348
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            int r2 = r2.margin
            int r10 = r10 - r2
            goto L_0x034d
        L_0x0348:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            int r2 = r2.margin
            int r10 = r10 + r2
        L_0x034d:
            if (r25 == 0) goto L_0x0355
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.end
            r2.resolve(r10)
            goto L_0x035a
        L_0x0355:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            r2.resolve(r10)
        L_0x035a:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r1.dimension
            int r8 = r2.value
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r1.dimensionBehavior
            if (r11 != r12) goto L_0x036d
            int r11 = r1.matchConstraintsType
            r13 = 1
            if (r11 != r13) goto L_0x036d
            int r2 = r2.wrapValue
            int r8 = java.lang.Math.min(r8, r2)
        L_0x036d:
            if (r25 == 0) goto L_0x0371
            int r10 = r10 - r8
            goto L_0x0372
        L_0x0371:
            int r10 = r10 + r8
        L_0x0372:
            if (r25 == 0) goto L_0x037a
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            r2.resolve(r10)
            goto L_0x037f
        L_0x037a:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.end
            r2.resolve(r10)
        L_0x037f:
            if (r3 >= r9) goto L_0x0392
            if (r3 >= r7) goto L_0x0392
            if (r25 == 0) goto L_0x038c
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            int r1 = r1.margin
            int r1 = -r1
            int r10 = r10 - r1
            goto L_0x0392
        L_0x038c:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            int r1 = r1.margin
            int r1 = -r1
            int r10 = r10 + r1
        L_0x0392:
            int r3 = r3 + 1
            goto L_0x030f
        L_0x0396:
            r8 = 2
            if (r2 != r8) goto L_0x0446
            int r2 = r0.orientation
            if (r2 != 0) goto L_0x03a5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.widget
            java.util.Objects.requireNonNull(r2)
            float r2 = r2.mHorizontalBiasPercent
            goto L_0x03ac
        L_0x03a5:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.widget
            java.util.Objects.requireNonNull(r2)
            float r2 = r2.mVerticalBiasPercent
        L_0x03ac:
            if (r25 == 0) goto L_0x03b2
            r8 = 1065353216(0x3f800000, float:1.0)
            float r2 = r8 - r2
        L_0x03b2:
            int r4 = r4 - r15
            float r4 = (float) r4
            float r4 = r4 * r2
            r2 = 1056964608(0x3f000000, float:0.5)
            float r4 = r4 + r2
            int r2 = (int) r4
            if (r2 < 0) goto L_0x03bd
            if (r3 <= 0) goto L_0x03be
        L_0x03bd:
            r2 = r1
        L_0x03be:
            if (r25 == 0) goto L_0x03c3
            int r10 = r22 - r2
            goto L_0x03c5
        L_0x03c3:
            int r10 = r22 + r2
        L_0x03c5:
            r3 = r1
        L_0x03c6:
            if (r3 >= r5) goto L_0x0446
            if (r25 == 0) goto L_0x03cf
            int r1 = r3 + 1
            int r1 = r5 - r1
            goto L_0x03d0
        L_0x03cf:
            r1 = r3
        L_0x03d0:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r2 = r0.widgets
            java.lang.Object r1 = r2.get(r1)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r1 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r1.widget
            java.util.Objects.requireNonNull(r2)
            int r2 = r2.mVisibility
            r4 = 8
            if (r2 != r4) goto L_0x03ef
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            r2.resolve(r10)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            r1.resolve(r10)
            r13 = 1
            goto L_0x0443
        L_0x03ef:
            if (r3 <= 0) goto L_0x0400
            if (r3 < r6) goto L_0x0400
            if (r25 == 0) goto L_0x03fb
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            int r2 = r2.margin
            int r10 = r10 - r2
            goto L_0x0400
        L_0x03fb:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            int r2 = r2.margin
            int r10 = r10 + r2
        L_0x0400:
            if (r25 == 0) goto L_0x0408
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.end
            r2.resolve(r10)
            goto L_0x040d
        L_0x0408:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            r2.resolve(r10)
        L_0x040d:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r1.dimension
            int r8 = r2.value
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r1.dimensionBehavior
            if (r11 != r12) goto L_0x041d
            int r11 = r1.matchConstraintsType
            r13 = 1
            if (r11 != r13) goto L_0x041e
            int r8 = r2.wrapValue
            goto L_0x041e
        L_0x041d:
            r13 = 1
        L_0x041e:
            if (r25 == 0) goto L_0x0422
            int r10 = r10 - r8
            goto L_0x0423
        L_0x0422:
            int r10 = r10 + r8
        L_0x0423:
            if (r25 == 0) goto L_0x042b
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            r2.resolve(r10)
            goto L_0x0430
        L_0x042b:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.end
            r2.resolve(r10)
        L_0x0430:
            if (r3 >= r9) goto L_0x0443
            if (r3 >= r7) goto L_0x0443
            if (r25 == 0) goto L_0x043d
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            int r1 = r1.margin
            int r1 = -r1
            int r10 = r10 - r1
            goto L_0x0443
        L_0x043d:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            int r1 = r1.margin
            int r1 = -r1
            int r10 = r10 + r1
        L_0x0443:
            int r3 = r3 + 1
            goto L_0x03c6
        L_0x0446:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.ChainRun.update(androidx.constraintlayout.solver.widgets.analyzer.Dependency):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:39:0x00ad  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00b5  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ChainRun(androidx.constraintlayout.solver.widgets.ConstraintWidget r5, int r6) {
        /*
            r4 = this;
            r4.<init>(r5)
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            r4.widgets = r5
            r4.orientation = r6
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r4.widget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r5.getPreviousChainMember(r6)
        L_0x0012:
            r3 = r6
            r6 = r5
            r5 = r3
            if (r5 == 0) goto L_0x001e
            int r6 = r4.orientation
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r5.getPreviousChainMember(r6)
            goto L_0x0012
        L_0x001e:
            r4.widget = r6
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r5 = r4.widgets
            int r0 = r4.orientation
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L_0x002b
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r0 = r6.horizontalRun
            goto L_0x0031
        L_0x002b:
            if (r0 != r2) goto L_0x0030
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r6.verticalRun
            goto L_0x0031
        L_0x0030:
            r0 = r1
        L_0x0031:
            r5.add(r0)
            int r5 = r4.orientation
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r6.getNextChainMember(r5)
        L_0x003a:
            if (r5 == 0) goto L_0x0055
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r6 = r4.widgets
            int r0 = r4.orientation
            if (r0 != 0) goto L_0x0045
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r0 = r5.horizontalRun
            goto L_0x004b
        L_0x0045:
            if (r0 != r2) goto L_0x004a
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r5.verticalRun
            goto L_0x004b
        L_0x004a:
            r0 = r1
        L_0x004b:
            r6.add(r0)
            int r6 = r4.orientation
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.getNextChainMember(r6)
            goto L_0x003a
        L_0x0055:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r5 = r4.widgets
            java.util.Iterator r5 = r5.iterator()
        L_0x005b:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x0077
            java.lang.Object r6 = r5.next()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r6 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r6
            int r0 = r4.orientation
            if (r0 != 0) goto L_0x0070
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r6.widget
            r6.horizontalChainRun = r4
            goto L_0x005b
        L_0x0070:
            if (r0 != r2) goto L_0x005b
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r6.widget
            r6.verticalChainRun = r4
            goto L_0x005b
        L_0x0077:
            int r5 = r4.orientation
            if (r5 != 0) goto L_0x008d
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r4.widget
            java.util.Objects.requireNonNull(r5)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.mParent
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r5 = (androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer) r5
            java.util.Objects.requireNonNull(r5)
            boolean r5 = r5.mIsRtl
            if (r5 == 0) goto L_0x008d
            r5 = r2
            goto L_0x008e
        L_0x008d:
            r5 = 0
        L_0x008e:
            if (r5 == 0) goto L_0x00a9
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r5 = r4.widgets
            int r5 = r5.size()
            if (r5 <= r2) goto L_0x00a9
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r5 = r4.widgets
            int r6 = r5.size()
            int r6 = r6 - r2
            java.lang.Object r5 = r5.get(r6)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r5 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.widget
            r4.widget = r5
        L_0x00a9:
            int r5 = r4.orientation
            if (r5 != 0) goto L_0x00b5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r4.widget
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mHorizontalChainStyle
            goto L_0x00bc
        L_0x00b5:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r4.widget
            java.util.Objects.requireNonNull(r5)
            int r5 = r5.mVerticalChainStyle
        L_0x00bc:
            r4.chainStyle = r5
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.ChainRun.<init>(androidx.constraintlayout.solver.widgets.ConstraintWidget, int):void");
    }
}
