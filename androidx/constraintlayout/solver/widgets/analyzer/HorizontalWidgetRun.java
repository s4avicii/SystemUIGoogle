package androidx.constraintlayout.solver.widgets.analyzer;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import java.util.Objects;

public final class HorizontalWidgetRun extends WidgetRun {
    public static int[] tempDimensions = new int[2];

    public static void computeInsetRatio(int[] iArr, int i, int i2, int i3, int i4, float f, int i5) {
        int i6 = i2 - i;
        int i7 = i4 - i3;
        if (i5 == -1) {
            int i8 = (int) ((((float) i7) * f) + 0.5f);
            int i9 = (int) ((((float) i6) / f) + 0.5f);
            if (i8 <= i6) {
                iArr[0] = i8;
                iArr[1] = i7;
            } else if (i9 <= i7) {
                iArr[0] = i6;
                iArr[1] = i9;
            }
        } else if (i5 == 0) {
            iArr[0] = (int) ((((float) i7) * f) + 0.5f);
            iArr[1] = i7;
        } else if (i5 == 1) {
            iArr[0] = i6;
            iArr[1] = (int) ((((float) i6) * f) + 0.5f);
        }
    }

    public final void clear() {
        this.runGroup = null;
        this.start.clear();
        this.end.clear();
        this.dimension.clear();
        this.resolved = false;
    }

    public final void reset() {
        this.resolved = false;
        this.start.clear();
        this.start.resolved = false;
        this.end.clear();
        this.end.resolved = false;
        this.dimension.resolved = false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0041, code lost:
        if (r3.mListDimensionBehaviors[0] == r1) goto L_0x0043;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x00ac, code lost:
        if (r3.mListDimensionBehaviors[0] == r1) goto L_0x00ae;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void apply() {
        /*
            r6 = this;
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r6.widget
            boolean r4 = r3.measured
            if (r4 == 0) goto L_0x0015
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r4 = r6.dimension
            int r3 = r3.getWidth()
            r4.resolve(r3)
        L_0x0015:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r6.dimension
            boolean r3 = r3.resolved
            r4 = 0
            if (r3 != 0) goto L_0x0092
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r6.widget
            java.util.Objects.requireNonNull(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r3.mListDimensionBehaviors
            r3 = r3[r4]
            r6.dimensionBehavior = r3
            if (r3 == r0) goto L_0x00d2
            if (r3 != r1) goto L_0x0082
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r6.widget
            java.util.Objects.requireNonNull(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.mParent
            if (r3 == 0) goto L_0x003a
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r3.mListDimensionBehaviors
            r5 = r5[r4]
            if (r5 == r2) goto L_0x0043
        L_0x003a:
            java.util.Objects.requireNonNull(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r3.mListDimensionBehaviors
            r5 = r5[r4]
            if (r5 != r1) goto L_0x0082
        L_0x0043:
            int r0 = r3.getWidth()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mLeft
            int r1 = r1.getMargin()
            int r0 = r0 - r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mRight
            int r1 = r1.getMargin()
            int r0 = r0 - r1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r2 = r3.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.start
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r4.mLeft
            int r4 = r4.getMargin()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r1, r2, r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.end
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r2 = r3.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.end
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mRight
            int r3 = r3.getMargin()
            int r3 = -r3
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r1, r2, r3)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r6 = r6.dimension
            r6.resolve(r0)
            return
        L_0x0082:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = r6.dimensionBehavior
            if (r1 != r2) goto L_0x00d2
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            int r2 = r2.getWidth()
            r1.resolve(r2)
            goto L_0x00d2
        L_0x0092:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = r6.dimensionBehavior
            if (r3 != r1) goto L_0x00d2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r6.widget
            java.util.Objects.requireNonNull(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.mParent
            if (r3 == 0) goto L_0x00a5
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r3.mListDimensionBehaviors
            r5 = r5[r4]
            if (r5 == r2) goto L_0x00ae
        L_0x00a5:
            java.util.Objects.requireNonNull(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r3.mListDimensionBehaviors
            r2 = r2[r4]
            if (r2 != r1) goto L_0x00d2
        L_0x00ae:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.start
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r1 = r3.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.start
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mLeft
            int r2 = r2.getMargin()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r0, r1, r2)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.end
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r1 = r3.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r6.mRight
            int r6 = r6.getMargin()
            int r6 = -r6
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r0, r1, r6)
            return
        L_0x00d2:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            boolean r2 = r1.resolved
            r3 = 1
            if (r2 == 0) goto L_0x01de
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            boolean r5 = r2.measured
            if (r5 == 0) goto L_0x01de
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r2.mListAnchors
            r1 = r0[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x0153
            r1 = r0[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x0153
            boolean r0 = r2.isInHorizontalChain()
            if (r0 == 0) goto L_0x0112
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.start
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r1.mListAnchors
            r1 = r1[r4]
            int r1 = r1.getMargin()
            r0.margin = r1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.end
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r6.mListAnchors
            r6 = r6[r3]
            int r6 = r6.getMargin()
            int r6 = -r6
            r0.margin = r6
            goto L_0x03d7
        L_0x0112:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r0.mListAnchors
            r0 = r0[r4]
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.getTarget(r0)
            if (r0 == 0) goto L_0x012d
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r2.mListAnchors
            r2 = r2[r4]
            int r2 = r2.getMargin()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r1, r0, r2)
        L_0x012d:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r0.mListAnchors
            r0 = r0[r3]
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.getTarget(r0)
            if (r0 == 0) goto L_0x0149
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.end
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r2.mListAnchors
            r2 = r2[r3]
            int r2 = r2.getMargin()
            int r2 = -r2
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r1, r0, r2)
        L_0x0149:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.start
            r0.delegateToWidgetRun = r3
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r6 = r6.end
            r6.delegateToWidgetRun = r3
            goto L_0x03d7
        L_0x0153:
            r1 = r0[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x017d
            r0 = r0[r4]
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.getTarget(r0)
            if (r0 == 0) goto L_0x03d7
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r2.mListAnchors
            r2 = r2[r4]
            int r2 = r2.getMargin()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r1, r0, r2)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.end
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r6 = r6.dimension
            int r6 = r6.value
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r0, r1, r6)
            goto L_0x03d7
        L_0x017d:
            r1 = r0[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x01a9
            r0 = r0[r3]
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.getTarget(r0)
            if (r0 == 0) goto L_0x03d7
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.end
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r2.mListAnchors
            r2 = r2[r3]
            int r2 = r2.getMargin()
            int r2 = -r2
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r1, r0, r2)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.start
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.end
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r6 = r6.dimension
            int r6 = r6.value
            int r6 = -r6
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r0, r1, r6)
            goto L_0x03d7
        L_0x01a9:
            boolean r0 = r2 instanceof androidx.constraintlayout.solver.widgets.Helper
            if (r0 != 0) goto L_0x03d7
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r2.mParent
            if (r0 == 0) goto L_0x03d7
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r0 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.CENTER
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r2.getAnchor(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            if (r0 != 0) goto L_0x03d7
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            java.util.Objects.requireNonNull(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.mParent
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r0 = r0.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.start
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            int r2 = r2.getX()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r1, r0, r2)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.end
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r6 = r6.dimension
            int r6 = r6.value
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r0, r1, r6)
            goto L_0x03d7
        L_0x01de:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = r6.dimensionBehavior
            if (r2 != r0) goto L_0x030d
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            int r2 = r0.mMatchConstraintDefaultWidth
            r5 = 2
            if (r2 == r5) goto L_0x02e4
            r5 = 3
            if (r2 == r5) goto L_0x01ee
            goto L_0x030d
        L_0x01ee:
            int r2 = r0.mMatchConstraintDefaultHeight
            if (r2 != r5) goto L_0x0293
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r6.start
            r2.updateDelegate = r6
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r6.end
            r2.updateDelegate = r6
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r2 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r5 = r2.start
            r5.updateDelegate = r6
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.end
            r2.updateDelegate = r6
            r1.updateDelegate = r6
            boolean r0 = r0.isInVerticalChain()
            if (r0 == 0) goto L_0x0260
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r6.dimension
            java.util.ArrayList r0 = r0.targets
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r1 = r1.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r1.dimension
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r0.dimension
            r1.updateDelegate = r6
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            java.util.ArrayList r1 = r1.targets
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.start
            r1.add(r0)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r6.dimension
            java.util.ArrayList r0 = r0.targets
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r1 = r1.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.start
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.end
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            goto L_0x030d
        L_0x0260:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            boolean r0 = r0.isInHorizontalChain()
            if (r0 == 0) goto L_0x0284
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            java.util.ArrayList r0 = r0.targets
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r6.dimension
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r1 = r1.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r1.dimension
            r0.add(r1)
            goto L_0x030d
        L_0x0284:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            java.util.ArrayList r0 = r0.targets
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            goto L_0x030d
        L_0x0293:
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            java.util.ArrayList r1 = r1.targets
            r1.add(r0)
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.start
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.end
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r6.dimension
            r0.delegateToWidgetRun = r3
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r6.dimension
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.end
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.start
            java.util.ArrayList r0 = r0.targets
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.end
            java.util.ArrayList r0 = r0.targets
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            goto L_0x030d
        L_0x02e4:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.mParent
            if (r0 != 0) goto L_0x02e9
            goto L_0x030d
        L_0x02e9:
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            java.util.ArrayList r1 = r1.targets
            r1.add(r0)
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r6.dimension
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r6.dimension
            r0.delegateToWidgetRun = r3
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            r0.add(r1)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r6.dimension
            java.util.ArrayList r0 = r0.dependencies
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.end
            r0.add(r1)
        L_0x030d:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            r2 = r1[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x0362
            r2 = r1[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x0362
            boolean r0 = r0.isInHorizontalChain()
            if (r0 == 0) goto L_0x0342
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.start
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r1.mListAnchors
            r1 = r1[r4]
            int r1 = r1.getMargin()
            r0.margin = r1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.end
            androidx.constraintlayout.solver.widgets.ConstraintWidget r6 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r6.mListAnchors
            r6 = r6[r3]
            int r6 = r6.getMargin()
            int r6 = -r6
            r0.margin = r6
            goto L_0x03d7
        L_0x0342:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r0.mListAnchors
            r0 = r0[r4]
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.getTarget(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r1.mListAnchors
            r1 = r1[r3]
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.getTarget(r1)
            r0.addDependency(r6)
            r1.addDependency(r6)
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun$RunType r0 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.RunType.CENTER
            r6.mRunType = r0
            goto L_0x03d7
        L_0x0362:
            r2 = r1[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x0389
            r0 = r1[r4]
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.getTarget(r0)
            if (r0 == 0) goto L_0x03d7
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r2.mListAnchors
            r2 = r2[r4]
            int r2 = r2.getMargin()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r1, r0, r2)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.end
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r6.dimension
            r6.addTarget(r0, r1, r3, r2)
            goto L_0x03d7
        L_0x0389:
            r2 = r1[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x03b2
            r0 = r1[r3]
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.getTarget(r0)
            if (r0 == 0) goto L_0x03d7
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.end
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r2.mListAnchors
            r2 = r2[r3]
            int r2 = r2.getMargin()
            int r2 = -r2
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r1, r0, r2)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.start
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.end
            r2 = -1
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r6.dimension
            r6.addTarget(r0, r1, r2, r3)
            goto L_0x03d7
        L_0x03b2:
            boolean r1 = r0 instanceof androidx.constraintlayout.solver.widgets.Helper
            if (r1 != 0) goto L_0x03d7
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r0.mParent
            if (r1 == 0) goto L_0x03d7
            java.util.Objects.requireNonNull(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.mParent
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r0 = r0.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.start
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r6.widget
            int r2 = r2.getX()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.addTarget(r1, r0, r2)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r6.end
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r6.start
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r6.dimension
            r6.addTarget(r0, r1, r3, r2)
        L_0x03d7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun.apply():void");
    }

    public final void applyToWidget() {
        DependencyNode dependencyNode = this.start;
        if (dependencyNode.resolved) {
            ConstraintWidget constraintWidget = this.widget;
            int i = dependencyNode.value;
            Objects.requireNonNull(constraintWidget);
            constraintWidget.f15mX = i;
        }
    }

    public final boolean supportsWrapComputation() {
        if (this.dimensionBehavior != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || this.widget.mMatchConstraintDefaultWidth == 0) {
            return true;
        }
        return false;
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("HorizontalRun ");
        ConstraintWidget constraintWidget = this.widget;
        Objects.requireNonNull(constraintWidget);
        m.append(constraintWidget.mDebugName);
        return m.toString();
    }

    /* JADX WARNING: Code restructure failed: missing block: B:99:0x027f, code lost:
        if (r13 != 1) goto L_0x02de;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void update(androidx.constraintlayout.solver.widgets.analyzer.Dependency r21) {
        /*
            r20 = this;
            r0 = r20
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun$RunType r2 = r0.mRunType
            int r2 = r2.ordinal()
            r3 = 3
            r4 = 0
            if (r2 == r3) goto L_0x03fc
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r0.dimension
            boolean r5 = r2.resolved
            r6 = 1056964608(0x3f000000, float:0.5)
            r7 = 1
            if (r5 != 0) goto L_0x02de
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = r0.dimensionBehavior
            if (r5 != r1) goto L_0x02de
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r0.widget
            int r8 = r5.mMatchConstraintDefaultWidth
            r9 = 2
            if (r8 == r9) goto L_0x02c7
            if (r8 == r3) goto L_0x0026
            goto L_0x02de
        L_0x0026:
            int r8 = r5.mMatchConstraintDefaultHeight
            r9 = -1
            if (r8 == 0) goto L_0x005e
            if (r8 != r3) goto L_0x002e
            goto L_0x005e
        L_0x002e:
            int r3 = r5.mDimensionRatioSide
            if (r3 == r9) goto L_0x004d
            if (r3 == 0) goto L_0x0042
            if (r3 == r7) goto L_0x0038
            r3 = r4
            goto L_0x0059
        L_0x0038:
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r5.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r3 = r3.value
            float r3 = (float) r3
            float r5 = r5.mDimensionRatio
            goto L_0x0056
        L_0x0042:
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r5.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r3 = r3.value
            float r3 = (float) r3
            float r5 = r5.mDimensionRatio
            float r3 = r3 / r5
            goto L_0x0057
        L_0x004d:
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r5.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r3.dimension
            int r3 = r3.value
            float r3 = (float) r3
            float r5 = r5.mDimensionRatio
        L_0x0056:
            float r3 = r3 * r5
        L_0x0057:
            float r3 = r3 + r6
            int r3 = (int) r3
        L_0x0059:
            r2.resolve(r3)
            goto L_0x02de
        L_0x005e:
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r2 = r5.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r2.start
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.end
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r5.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r8.mTarget
            if (r8 == 0) goto L_0x006c
            r8 = r7
            goto L_0x006d
        L_0x006c:
            r8 = r4
        L_0x006d:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r5.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r10.mTarget
            if (r10 == 0) goto L_0x0075
            r10 = r7
            goto L_0x0076
        L_0x0075:
            r10 = r4
        L_0x0076:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r5.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r11.mTarget
            if (r11 == 0) goto L_0x007e
            r11 = r7
            goto L_0x007f
        L_0x007e:
            r11 = r4
        L_0x007f:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r5.mBottom
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r12.mTarget
            if (r12 == 0) goto L_0x0087
            r12 = r7
            goto L_0x0088
        L_0x0087:
            r12 = r4
        L_0x0088:
            int r15 = r5.mDimensionRatioSide
            if (r8 == 0) goto L_0x01c6
            if (r10 == 0) goto L_0x01c6
            if (r11 == 0) goto L_0x01c6
            if (r12 == 0) goto L_0x01c6
            java.util.Objects.requireNonNull(r5)
            float r5 = r5.mDimensionRatio
            boolean r8 = r3.resolved
            if (r8 == 0) goto L_0x00f8
            boolean r8 = r2.resolved
            if (r8 == 0) goto L_0x00f8
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.start
            boolean r6 = r1.readyToSolve
            if (r6 == 0) goto L_0x00f7
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r6 = r0.end
            boolean r6 = r6.readyToSolve
            if (r6 != 0) goto L_0x00ac
            goto L_0x00f7
        L_0x00ac:
            java.util.ArrayList r1 = r1.targets
            java.lang.Object r1 = r1.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r1
            int r1 = r1.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r6 = r0.start
            int r6 = r6.margin
            int r14 = r1 + r6
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.end
            java.util.ArrayList r1 = r1.targets
            java.lang.Object r1 = r1.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r1
            int r1 = r1.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r6 = r0.end
            int r6 = r6.margin
            int r1 = r1 - r6
            int r6 = r3.value
            int r3 = r3.margin
            int r16 = r6 + r3
            int r3 = r2.value
            int r2 = r2.margin
            int r17 = r3 - r2
            int[] r2 = tempDimensions
            r13 = r2
            r8 = r15
            r15 = r1
            r18 = r5
            r19 = r8
            computeInsetRatio(r13, r14, r15, r16, r17, r18, r19)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r0.dimension
            r3 = r2[r4]
            r1.resolve(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            r1 = r2[r7]
            r0.resolve(r1)
        L_0x00f7:
            return
        L_0x00f8:
            r8 = r15
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r0.start
            boolean r10 = r9.resolved
            if (r10 == 0) goto L_0x0154
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r0.end
            boolean r11 = r10.resolved
            if (r11 == 0) goto L_0x0154
            boolean r11 = r3.readyToSolve
            if (r11 == 0) goto L_0x0153
            boolean r11 = r2.readyToSolve
            if (r11 != 0) goto L_0x010e
            goto L_0x0153
        L_0x010e:
            int r11 = r9.value
            int r9 = r9.margin
            int r14 = r11 + r9
            int r9 = r10.value
            int r10 = r10.margin
            int r15 = r9 - r10
            java.util.ArrayList r9 = r3.targets
            java.lang.Object r9 = r9.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r9
            int r9 = r9.value
            int r10 = r3.margin
            int r16 = r9 + r10
            java.util.ArrayList r9 = r2.targets
            java.lang.Object r9 = r9.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r9
            int r9 = r9.value
            int r10 = r2.margin
            int r17 = r9 - r10
            int[] r9 = tempDimensions
            r13 = r9
            r18 = r5
            r19 = r8
            computeInsetRatio(r13, r14, r15, r16, r17, r18, r19)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r10 = r0.dimension
            r11 = r9[r4]
            r10.resolve(r11)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r0.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r10 = r10.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r10 = r10.dimension
            r9 = r9[r7]
            r10.resolve(r9)
            goto L_0x0154
        L_0x0153:
            return
        L_0x0154:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r0.start
            boolean r10 = r9.readyToSolve
            if (r10 == 0) goto L_0x01c5
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r0.end
            boolean r10 = r10.readyToSolve
            if (r10 == 0) goto L_0x01c5
            boolean r10 = r3.readyToSolve
            if (r10 == 0) goto L_0x01c5
            boolean r10 = r2.readyToSolve
            if (r10 != 0) goto L_0x0169
            goto L_0x01c5
        L_0x0169:
            java.util.ArrayList r9 = r9.targets
            java.lang.Object r9 = r9.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r9
            int r9 = r9.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r0.start
            int r10 = r10.margin
            int r14 = r9 + r10
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = r0.end
            java.util.ArrayList r9 = r9.targets
            java.lang.Object r9 = r9.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r9
            int r9 = r9.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r10 = r0.end
            int r10 = r10.margin
            int r15 = r9 - r10
            java.util.ArrayList r9 = r3.targets
            java.lang.Object r9 = r9.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r9 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r9
            int r9 = r9.value
            int r3 = r3.margin
            int r16 = r9 + r3
            java.util.ArrayList r3 = r2.targets
            java.lang.Object r3 = r3.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r3
            int r3 = r3.value
            int r2 = r2.margin
            int r17 = r3 - r2
            int[] r2 = tempDimensions
            r13 = r2
            r18 = r5
            r19 = r8
            computeInsetRatio(r13, r14, r15, r16, r17, r18, r19)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r0.dimension
            r5 = r2[r4]
            r3.resolve(r5)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r0.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r3.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r3.dimension
            r2 = r2[r7]
            r3.resolve(r2)
            goto L_0x02de
        L_0x01c5:
            return
        L_0x01c6:
            r13 = r15
            if (r8 == 0) goto L_0x024f
            if (r11 == 0) goto L_0x024f
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r0.start
            boolean r2 = r2.readyToSolve
            if (r2 == 0) goto L_0x024e
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r0.end
            boolean r2 = r2.readyToSolve
            if (r2 != 0) goto L_0x01d9
            goto L_0x024e
        L_0x01d9:
            java.util.Objects.requireNonNull(r5)
            float r2 = r5.mDimensionRatio
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r0.start
            java.util.ArrayList r3 = r3.targets
            java.lang.Object r3 = r3.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r3
            int r3 = r3.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r5 = r0.start
            int r5 = r5.margin
            int r3 = r3 + r5
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r5 = r0.end
            java.util.ArrayList r5 = r5.targets
            java.lang.Object r5 = r5.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r5 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r5
            int r5 = r5.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r8 = r0.end
            int r8 = r8.margin
            int r5 = r5 - r8
            if (r13 == r9) goto L_0x022b
            if (r13 == 0) goto L_0x022b
            if (r13 == r7) goto L_0x0208
            goto L_0x02de
        L_0x0208:
            int r5 = r5 - r3
            int r3 = r0.getLimitedDimension(r5, r4)
            float r5 = (float) r3
            float r5 = r5 / r2
            float r5 = r5 + r6
            int r5 = (int) r5
            int r8 = r0.getLimitedDimension(r5, r7)
            if (r5 == r8) goto L_0x021b
            float r3 = (float) r8
            float r3 = r3 * r2
            float r3 = r3 + r6
            int r3 = (int) r3
        L_0x021b:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r0.dimension
            r2.resolve(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r2 = r2.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r2.dimension
            r2.resolve(r8)
            goto L_0x02de
        L_0x022b:
            int r5 = r5 - r3
            int r3 = r0.getLimitedDimension(r5, r4)
            float r5 = (float) r3
            float r5 = r5 * r2
            float r5 = r5 + r6
            int r5 = (int) r5
            int r8 = r0.getLimitedDimension(r5, r7)
            if (r5 == r8) goto L_0x023e
            float r3 = (float) r8
            float r3 = r3 / r2
            float r3 = r3 + r6
            int r3 = (int) r3
        L_0x023e:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r0.dimension
            r2.resolve(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r2 = r2.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r2.dimension
            r2.resolve(r8)
            goto L_0x02de
        L_0x024e:
            return
        L_0x024f:
            if (r10 == 0) goto L_0x02de
            if (r12 == 0) goto L_0x02de
            boolean r8 = r3.readyToSolve
            if (r8 == 0) goto L_0x02c6
            boolean r8 = r2.readyToSolve
            if (r8 != 0) goto L_0x025c
            goto L_0x02c6
        L_0x025c:
            java.util.Objects.requireNonNull(r5)
            float r5 = r5.mDimensionRatio
            java.util.ArrayList r8 = r3.targets
            java.lang.Object r8 = r8.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r8 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r8
            int r8 = r8.value
            int r3 = r3.margin
            int r8 = r8 + r3
            java.util.ArrayList r3 = r2.targets
            java.lang.Object r3 = r3.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r3
            int r3 = r3.value
            int r2 = r2.margin
            int r3 = r3 - r2
            if (r13 == r9) goto L_0x02a4
            if (r13 == 0) goto L_0x0282
            if (r13 == r7) goto L_0x02a4
            goto L_0x02de
        L_0x0282:
            int r3 = r3 - r8
            int r2 = r0.getLimitedDimension(r3, r7)
            float r3 = (float) r2
            float r3 = r3 * r5
            float r3 = r3 + r6
            int r3 = (int) r3
            int r8 = r0.getLimitedDimension(r3, r4)
            if (r3 == r8) goto L_0x0295
            float r2 = (float) r8
            float r2 = r2 / r5
            float r2 = r2 + r6
            int r2 = (int) r2
        L_0x0295:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r0.dimension
            r3.resolve(r8)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r0.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r3.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r3.dimension
            r3.resolve(r2)
            goto L_0x02de
        L_0x02a4:
            int r3 = r3 - r8
            int r2 = r0.getLimitedDimension(r3, r7)
            float r3 = (float) r2
            float r3 = r3 / r5
            float r3 = r3 + r6
            int r3 = (int) r3
            int r8 = r0.getLimitedDimension(r3, r4)
            if (r3 == r8) goto L_0x02b7
            float r2 = (float) r8
            float r2 = r2 * r5
            float r2 = r2 + r6
            int r2 = (int) r2
        L_0x02b7:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r0.dimension
            r3.resolve(r8)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r0.widget
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r3.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r3.dimension
            r3.resolve(r2)
            goto L_0x02de
        L_0x02c6:
            return
        L_0x02c7:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r5.mParent
            if (r3 == 0) goto L_0x02de
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r3 = r3.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r3.dimension
            boolean r8 = r3.resolved
            if (r8 == 0) goto L_0x02de
            float r5 = r5.mMatchConstraintPercentWidth
            int r3 = r3.value
            float r3 = (float) r3
            float r3 = r3 * r5
            float r3 = r3 + r6
            int r3 = (int) r3
            r2.resolve(r3)
        L_0x02de:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r0.start
            boolean r3 = r2.readyToSolve
            if (r3 == 0) goto L_0x03fb
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r0.end
            boolean r5 = r3.readyToSolve
            if (r5 != 0) goto L_0x02ec
            goto L_0x03fb
        L_0x02ec:
            boolean r2 = r2.resolved
            if (r2 == 0) goto L_0x02fb
            boolean r2 = r3.resolved
            if (r2 == 0) goto L_0x02fb
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r0.dimension
            boolean r2 = r2.resolved
            if (r2 == 0) goto L_0x02fb
            return
        L_0x02fb:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r0.dimension
            boolean r2 = r2.resolved
            if (r2 != 0) goto L_0x0343
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = r0.dimensionBehavior
            if (r2 != r1) goto L_0x0343
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.widget
            int r3 = r2.mMatchConstraintDefaultWidth
            if (r3 != 0) goto L_0x0343
            boolean r2 = r2.isInHorizontalChain()
            if (r2 != 0) goto L_0x0343
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.start
            java.util.ArrayList r1 = r1.targets
            java.lang.Object r1 = r1.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r0.end
            java.util.ArrayList r2 = r2.targets
            java.lang.Object r2 = r2.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r2
            int r1 = r1.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r0.start
            int r4 = r3.margin
            int r1 = r1 + r4
            int r2 = r2.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r4 = r0.end
            int r4 = r4.margin
            int r2 = r2 + r4
            int r4 = r2 - r1
            r3.resolve(r1)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.end
            r1.resolve(r2)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            r0.resolve(r4)
            return
        L_0x0343:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r0.dimension
            boolean r2 = r2.resolved
            if (r2 != 0) goto L_0x03a5
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = r0.dimensionBehavior
            if (r2 != r1) goto L_0x03a5
            int r1 = r0.matchConstraintsType
            if (r1 != r7) goto L_0x03a5
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.start
            java.util.ArrayList r1 = r1.targets
            int r1 = r1.size()
            if (r1 <= 0) goto L_0x03a5
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.end
            java.util.ArrayList r1 = r1.targets
            int r1 = r1.size()
            if (r1 <= 0) goto L_0x03a5
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.start
            java.util.ArrayList r1 = r1.targets
            java.lang.Object r1 = r1.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r0.end
            java.util.ArrayList r2 = r2.targets
            java.lang.Object r2 = r2.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r2
            int r1 = r1.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r0.start
            int r3 = r3.margin
            int r1 = r1 + r3
            int r2 = r2.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r3 = r0.end
            int r3 = r3.margin
            int r2 = r2 + r3
            int r2 = r2 - r1
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r0.dimension
            int r1 = r1.wrapValue
            int r1 = java.lang.Math.min(r2, r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.widget
            int r3 = r2.mMatchConstraintMaxWidth
            int r2 = r2.mMatchConstraintMinWidth
            int r1 = java.lang.Math.max(r2, r1)
            if (r3 <= 0) goto L_0x03a0
            int r1 = java.lang.Math.min(r3, r1)
        L_0x03a0:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r2 = r0.dimension
            r2.resolve(r1)
        L_0x03a5:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r0.dimension
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x03ac
            return
        L_0x03ac:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.start
            java.util.ArrayList r1 = r1.targets
            java.lang.Object r1 = r1.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r0.end
            java.util.ArrayList r2 = r2.targets
            java.lang.Object r2 = r2.get(r4)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = (androidx.constraintlayout.solver.widgets.analyzer.DependencyNode) r2
            int r3 = r1.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r4 = r0.start
            int r4 = r4.margin
            int r3 = r3 + r4
            int r4 = r2.value
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r5 = r0.end
            int r5 = r5.margin
            int r4 = r4 + r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r0.widget
            java.util.Objects.requireNonNull(r5)
            float r5 = r5.mHorizontalBiasPercent
            if (r1 != r2) goto L_0x03dc
            int r3 = r1.value
            int r4 = r2.value
            r5 = r6
        L_0x03dc:
            int r4 = r4 - r3
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r0.dimension
            int r1 = r1.value
            int r4 = r4 - r1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.start
            float r2 = (float) r3
            float r2 = r2 + r6
            float r3 = (float) r4
            float r3 = r3 * r5
            float r3 = r3 + r2
            int r2 = (int) r3
            r1.resolve(r2)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.end
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r0.start
            int r2 = r2.value
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            int r0 = r0.value
            int r2 = r2 + r0
            r1.resolve(r2)
        L_0x03fb:
            return
        L_0x03fc:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r0.widget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mRight
            r0.updateRunCenter(r2, r1, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun.update(androidx.constraintlayout.solver.widgets.analyzer.Dependency):void");
    }

    public HorizontalWidgetRun(ConstraintWidget constraintWidget) {
        super(constraintWidget);
        this.start.type = DependencyNode.Type.LEFT;
        this.end.type = DependencyNode.Type.RIGHT;
        this.orientation = 0;
    }
}
