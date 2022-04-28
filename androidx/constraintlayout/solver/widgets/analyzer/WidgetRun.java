package androidx.constraintlayout.solver.widgets.analyzer;

import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;

public abstract class WidgetRun implements Dependency {
    public DimensionDependency dimension = new DimensionDependency(this);
    public ConstraintWidget.DimensionBehaviour dimensionBehavior;
    public DependencyNode end = new DependencyNode(this);
    public RunType mRunType = RunType.NONE;
    public int matchConstraintsType;
    public int orientation = 0;
    public boolean resolved = false;
    public RunGroup runGroup;
    public DependencyNode start = new DependencyNode(this);
    public ConstraintWidget widget;

    public enum RunType {
        NONE,
        CENTER
    }

    public static void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i) {
        dependencyNode.targets.add(dependencyNode2);
        dependencyNode.margin = i;
        dependencyNode2.dependencies.add(dependencyNode);
    }

    public static DependencyNode getTarget(ConstraintAnchor constraintAnchor) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        int ordinal = constraintAnchor2.mType.ordinal();
        if (ordinal == 1) {
            return constraintWidget.horizontalRun.start;
        }
        if (ordinal == 2) {
            return constraintWidget.verticalRun.start;
        }
        if (ordinal == 3) {
            return constraintWidget.horizontalRun.end;
        }
        if (ordinal == 4) {
            return constraintWidget.verticalRun.end;
        }
        if (ordinal != 5) {
            return null;
        }
        return constraintWidget.verticalRun.baseline;
    }

    public abstract void apply();

    public abstract void applyToWidget();

    public abstract void clear();

    public abstract boolean supportsWrapComputation();

    public void update(Dependency dependency) {
    }

    public final int getLimitedDimension(int i, int i2) {
        int i3;
        if (i2 == 0) {
            ConstraintWidget constraintWidget = this.widget;
            int i4 = constraintWidget.mMatchConstraintMaxWidth;
            i3 = Math.max(constraintWidget.mMatchConstraintMinWidth, i);
            if (i4 > 0) {
                i3 = Math.min(i4, i);
            }
            if (i3 == i) {
                return i;
            }
        } else {
            ConstraintWidget constraintWidget2 = this.widget;
            int i5 = constraintWidget2.mMatchConstraintMaxHeight;
            int max = Math.max(constraintWidget2.mMatchConstraintMinHeight, i);
            if (i5 > 0) {
                max = Math.min(i5, i);
            }
            if (i3 == i) {
                return i;
            }
        }
        return i3;
    }

    public long getWrapDimension() {
        DimensionDependency dimensionDependency = this.dimension;
        if (dimensionDependency.resolved) {
            return (long) dimensionDependency.value;
        }
        return 0;
    }

    public WidgetRun(ConstraintWidget constraintWidget) {
        this.widget = constraintWidget;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:23:0x0053, code lost:
        if (r10.matchConstraintsType == 3) goto L_0x00bb;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void updateRunCenter(androidx.constraintlayout.solver.widgets.ConstraintAnchor r13, androidx.constraintlayout.solver.widgets.ConstraintAnchor r14, int r15) {
        /*
            r12 = this;
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = getTarget(r13)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = getTarget(r14)
            boolean r2 = r0.resolved
            if (r2 == 0) goto L_0x0106
            boolean r2 = r1.resolved
            if (r2 != 0) goto L_0x0012
            goto L_0x0106
        L_0x0012:
            int r2 = r0.value
            int r13 = r13.getMargin()
            int r13 = r13 + r2
            int r2 = r1.value
            int r14 = r14.getMargin()
            int r2 = r2 - r14
            int r14 = r2 - r13
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r12.dimension
            boolean r4 = r3.resolved
            r5 = 1056964608(0x3f000000, float:0.5)
            if (r4 != 0) goto L_0x00bb
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = r12.dimensionBehavior
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r4 != r6) goto L_0x00bb
            int r4 = r12.matchConstraintsType
            if (r4 == 0) goto L_0x00b4
            r7 = 1
            if (r4 == r7) goto L_0x00a4
            r8 = 2
            if (r4 == r8) goto L_0x0075
            r8 = 3
            if (r4 == r8) goto L_0x003f
            goto L_0x00bb
        L_0x003f:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r12.widget
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r9 = r4.horizontalRun
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = r9.dimensionBehavior
            if (r10 != r6) goto L_0x0056
            int r10 = r9.matchConstraintsType
            if (r10 != r8) goto L_0x0056
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r10 = r4.verticalRun
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = r10.dimensionBehavior
            if (r11 != r6) goto L_0x0056
            int r6 = r10.matchConstraintsType
            if (r6 != r8) goto L_0x0056
            goto L_0x00bb
        L_0x0056:
            if (r15 != 0) goto L_0x005a
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r9 = r4.verticalRun
        L_0x005a:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r6 = r9.dimension
            boolean r8 = r6.resolved
            if (r8 == 0) goto L_0x00bb
            float r4 = r4.mDimensionRatio
            if (r15 != r7) goto L_0x006b
            int r6 = r6.value
            float r6 = (float) r6
            float r6 = r6 / r4
            float r6 = r6 + r5
            int r4 = (int) r6
            goto L_0x0071
        L_0x006b:
            int r6 = r6.value
            float r6 = (float) r6
            float r4 = r4 * r6
            float r4 = r4 + r5
            int r4 = (int) r4
        L_0x0071:
            r3.resolve(r4)
            goto L_0x00bb
        L_0x0075:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r12.widget
            java.util.Objects.requireNonNull(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.mParent
            if (r3 == 0) goto L_0x00bb
            if (r15 != 0) goto L_0x0083
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r3 = r3.horizontalRun
            goto L_0x0085
        L_0x0083:
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r3 = r3.verticalRun
        L_0x0085:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r3.dimension
            boolean r4 = r3.resolved
            if (r4 == 0) goto L_0x00bb
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r12.widget
            if (r15 != 0) goto L_0x0092
            float r4 = r4.mMatchConstraintPercentWidth
            goto L_0x0094
        L_0x0092:
            float r4 = r4.mMatchConstraintPercentHeight
        L_0x0094:
            int r3 = r3.value
            float r3 = (float) r3
            float r3 = r3 * r4
            float r3 = r3 + r5
            int r3 = (int) r3
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r4 = r12.dimension
            int r3 = r12.getLimitedDimension(r3, r15)
            r4.resolve(r3)
            goto L_0x00bb
        L_0x00a4:
            int r3 = r3.wrapValue
            int r3 = r12.getLimitedDimension(r3, r15)
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r4 = r12.dimension
            int r3 = java.lang.Math.min(r3, r14)
            r4.resolve(r3)
            goto L_0x00bb
        L_0x00b4:
            int r4 = r12.getLimitedDimension(r14, r15)
            r3.resolve(r4)
        L_0x00bb:
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r3 = r12.dimension
            boolean r4 = r3.resolved
            if (r4 != 0) goto L_0x00c2
            return
        L_0x00c2:
            int r3 = r3.value
            if (r3 != r14) goto L_0x00d1
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r14 = r12.start
            r14.resolve(r13)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r12 = r12.end
            r12.resolve(r2)
            return
        L_0x00d1:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r14 = r12.widget
            if (r15 != 0) goto L_0x00db
            java.util.Objects.requireNonNull(r14)
            float r14 = r14.mHorizontalBiasPercent
            goto L_0x00e0
        L_0x00db:
            java.util.Objects.requireNonNull(r14)
            float r14 = r14.mVerticalBiasPercent
        L_0x00e0:
            if (r0 != r1) goto L_0x00e7
            int r13 = r0.value
            int r2 = r1.value
            r14 = r5
        L_0x00e7:
            int r2 = r2 - r13
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r15 = r12.dimension
            int r15 = r15.value
            int r2 = r2 - r15
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r15 = r12.start
            float r13 = (float) r13
            float r13 = r13 + r5
            float r0 = (float) r2
            float r0 = r0 * r14
            float r0 = r0 + r13
            int r13 = (int) r0
            r15.resolve(r13)
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r13 = r12.end
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r14 = r12.start
            int r14 = r14.value
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r12 = r12.dimension
            int r12 = r12.value
            int r14 = r14 + r12
            r13.resolve(r14)
        L_0x0106:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.analyzer.WidgetRun.updateRunCenter(androidx.constraintlayout.solver.widgets.ConstraintAnchor, androidx.constraintlayout.solver.widgets.ConstraintAnchor, int):void");
    }

    public final void addTarget(DependencyNode dependencyNode, DependencyNode dependencyNode2, int i, DimensionDependency dimensionDependency) {
        dependencyNode.targets.add(dependencyNode2);
        dependencyNode.targets.add(this.dimension);
        dependencyNode.marginFactor = i;
        dependencyNode.marginDependency = dimensionDependency;
        dependencyNode2.dependencies.add(dependencyNode);
        dimensionDependency.dependencies.add(dependencyNode);
    }

    public static DependencyNode getTarget(ConstraintAnchor constraintAnchor, int i) {
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 == null) {
            return null;
        }
        ConstraintWidget constraintWidget = constraintAnchor2.mOwner;
        WidgetRun widgetRun = i == 0 ? constraintWidget.horizontalRun : constraintWidget.verticalRun;
        int ordinal = constraintAnchor2.mType.ordinal();
        if (ordinal == 1 || ordinal == 2) {
            return widgetRun.start;
        }
        if (ordinal == 3 || ordinal == 4) {
            return widgetRun.end;
        }
        return null;
    }
}
