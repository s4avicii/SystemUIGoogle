package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph;
import java.util.Arrays;
import java.util.Objects;

public final class ConstraintWidgetContainer extends WidgetContainer {
    public BasicMeasure mBasicMeasureSolver = new BasicMeasure(this);
    public DependencyGraph mDependencyGraph = new DependencyGraph(this);
    public boolean mHeightMeasuredTooSmall = false;
    public ChainHead[] mHorizontalChainsArray = new ChainHead[4];
    public int mHorizontalChainsSize = 0;
    public boolean mIsRtl = false;
    public BasicMeasure.Measurer mMeasurer = null;
    public int mOptimizationLevel = 7;
    public int mPaddingLeft;
    public int mPaddingTop;
    public LinearSystem mSystem = new LinearSystem();
    public ChainHead[] mVerticalChainsArray = new ChainHead[4];
    public int mVerticalChainsSize = 0;
    public boolean mWidthMeasuredTooSmall = false;

    public final void addChain(ConstraintWidget constraintWidget, int i) {
        if (i == 0) {
            int i2 = this.mHorizontalChainsSize + 1;
            ChainHead[] chainHeadArr = this.mHorizontalChainsArray;
            if (i2 >= chainHeadArr.length) {
                this.mHorizontalChainsArray = (ChainHead[]) Arrays.copyOf(chainHeadArr, chainHeadArr.length * 2);
            }
            ChainHead[] chainHeadArr2 = this.mHorizontalChainsArray;
            int i3 = this.mHorizontalChainsSize;
            chainHeadArr2[i3] = new ChainHead(constraintWidget, 0, this.mIsRtl);
            this.mHorizontalChainsSize = i3 + 1;
        } else if (i == 1) {
            int i4 = this.mVerticalChainsSize + 1;
            ChainHead[] chainHeadArr3 = this.mVerticalChainsArray;
            if (i4 >= chainHeadArr3.length) {
                this.mVerticalChainsArray = (ChainHead[]) Arrays.copyOf(chainHeadArr3, chainHeadArr3.length * 2);
            }
            ChainHead[] chainHeadArr4 = this.mVerticalChainsArray;
            int i5 = this.mVerticalChainsSize;
            chainHeadArr4[i5] = new ChainHead(constraintWidget, 1, this.mIsRtl);
            this.mVerticalChainsSize = i5 + 1;
        }
    }

    public final void addChildrenToSolver(LinearSystem linearSystem) {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
        ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.WRAP_CONTENT;
        addToSolver(linearSystem);
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i);
            if ((constraintWidget instanceof VirtualLayout) || (constraintWidget instanceof Guideline)) {
                constraintWidget.addToSolver(linearSystem);
            }
        }
        for (int i2 = 0; i2 < size; i2++) {
            ConstraintWidget constraintWidget2 = this.mChildren.get(i2);
            if (constraintWidget2 instanceof ConstraintWidgetContainer) {
                ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget2.mListDimensionBehaviors;
                ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = dimensionBehaviourArr[0];
                ConstraintWidget.DimensionBehaviour dimensionBehaviour4 = dimensionBehaviourArr[1];
                if (dimensionBehaviour3 == dimensionBehaviour2) {
                    constraintWidget2.setHorizontalDimensionBehaviour(dimensionBehaviour);
                }
                if (dimensionBehaviour4 == dimensionBehaviour2) {
                    constraintWidget2.setVerticalDimensionBehaviour(dimensionBehaviour);
                }
                constraintWidget2.addToSolver(linearSystem);
                if (dimensionBehaviour3 == dimensionBehaviour2) {
                    constraintWidget2.setHorizontalDimensionBehaviour(dimensionBehaviour3);
                }
                if (dimensionBehaviour4 == dimensionBehaviour2) {
                    constraintWidget2.setVerticalDimensionBehaviour(dimensionBehaviour4);
                }
            } else {
                ConstraintWidget.DimensionBehaviour dimensionBehaviour5 = ConstraintWidget.DimensionBehaviour.MATCH_PARENT;
                constraintWidget2.mHorizontalResolution = -1;
                constraintWidget2.mVerticalResolution = -1;
                if (this.mListDimensionBehaviors[0] != dimensionBehaviour2 && constraintWidget2.mListDimensionBehaviors[0] == dimensionBehaviour5) {
                    int i3 = constraintWidget2.mLeft.mMargin;
                    int width = getWidth() - constraintWidget2.mRight.mMargin;
                    ConstraintAnchor constraintAnchor = constraintWidget2.mLeft;
                    constraintAnchor.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor);
                    ConstraintAnchor constraintAnchor2 = constraintWidget2.mRight;
                    constraintAnchor2.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor2);
                    linearSystem.addEquality(constraintWidget2.mLeft.mSolverVariable, i3);
                    linearSystem.addEquality(constraintWidget2.mRight.mSolverVariable, width);
                    constraintWidget2.mHorizontalResolution = 2;
                    constraintWidget2.f15mX = i3;
                    int i4 = width - i3;
                    constraintWidget2.mWidth = i4;
                    int i5 = constraintWidget2.mMinWidth;
                    if (i4 < i5) {
                        constraintWidget2.mWidth = i5;
                    }
                }
                if (this.mListDimensionBehaviors[1] != dimensionBehaviour2 && constraintWidget2.mListDimensionBehaviors[1] == dimensionBehaviour5) {
                    int i6 = constraintWidget2.mTop.mMargin;
                    int height = getHeight() - constraintWidget2.mBottom.mMargin;
                    ConstraintAnchor constraintAnchor3 = constraintWidget2.mTop;
                    constraintAnchor3.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor3);
                    ConstraintAnchor constraintAnchor4 = constraintWidget2.mBottom;
                    constraintAnchor4.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor4);
                    linearSystem.addEquality(constraintWidget2.mTop.mSolverVariable, i6);
                    linearSystem.addEquality(constraintWidget2.mBottom.mSolverVariable, height);
                    if (constraintWidget2.mBaselineDistance > 0 || constraintWidget2.mVisibility == 8) {
                        ConstraintAnchor constraintAnchor5 = constraintWidget2.mBaseline;
                        constraintAnchor5.mSolverVariable = linearSystem.createObjectVariable(constraintAnchor5);
                        linearSystem.addEquality(constraintWidget2.mBaseline.mSolverVariable, constraintWidget2.mBaselineDistance + i6);
                    }
                    constraintWidget2.mVerticalResolution = 2;
                    constraintWidget2.f16mY = i6;
                    int i7 = height - i6;
                    constraintWidget2.mHeight = i7;
                    int i8 = constraintWidget2.mMinHeight;
                    if (i7 < i8) {
                        constraintWidget2.mHeight = i8;
                    }
                }
                if (!(constraintWidget2 instanceof VirtualLayout) && !(constraintWidget2 instanceof Guideline)) {
                    constraintWidget2.addToSolver(linearSystem);
                }
            }
        }
        if (this.mHorizontalChainsSize > 0) {
            Chain.applyChainConstraints(this, linearSystem, 0);
        }
        if (this.mVerticalChainsSize > 0) {
            Chain.applyChainConstraints(this, linearSystem, 1);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x00e8  */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x010e  */
    /* JADX WARNING: Removed duplicated region for block: B:73:0x013b A[EDGE_INSN: B:73:0x013b->B:59:0x013b ?: BREAK  , SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean directMeasureWithOrientation(boolean r13, int r14) {
        /*
            r12 = this;
            androidx.constraintlayout.solver.widgets.analyzer.DependencyGraph r12 = r12.mDependencyGraph
            java.util.Objects.requireNonNull(r12)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r0 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_PARENT
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r1 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r3 = 1
            r13 = r13 & r3
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r4 = r12.container
            r5 = 0
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = r4.getDimensionBehaviour(r5)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r6 = r12.container
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = r6.getDimensionBehaviour(r3)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r7 = r12.container
            int r7 = r7.getX()
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r8 = r12.container
            int r8 = r8.getY()
            if (r13 == 0) goto L_0x008a
            if (r4 == r1) goto L_0x002c
            if (r6 != r1) goto L_0x008a
        L_0x002c:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r9 = r12.mRuns
            java.util.Iterator r9 = r9.iterator()
        L_0x0032:
            boolean r10 = r9.hasNext()
            if (r10 == 0) goto L_0x0049
            java.lang.Object r10 = r9.next()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r10 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r10
            int r11 = r10.orientation
            if (r11 != r14) goto L_0x0032
            boolean r10 = r10.supportsWrapComputation()
            if (r10 != 0) goto L_0x0032
            r13 = r5
        L_0x0049:
            if (r14 != 0) goto L_0x006b
            if (r13 == 0) goto L_0x008a
            if (r4 != r1) goto L_0x008a
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r13 = r12.container
            r13.setHorizontalDimensionBehaviour(r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r13 = r12.container
            int r1 = r12.computeWrap(r13, r5)
            r13.setWidth(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r13 = r12.container
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r1 = r13.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r1.dimension
            int r13 = r13.getWidth()
            r1.resolve(r13)
            goto L_0x008a
        L_0x006b:
            if (r13 == 0) goto L_0x008a
            if (r6 != r1) goto L_0x008a
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r13 = r12.container
            r13.setVerticalDimensionBehaviour(r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r13 = r12.container
            int r1 = r12.computeWrap(r13, r3)
            r13.setHeight(r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r13 = r12.container
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r1 = r13.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r1.dimension
            int r13 = r13.getHeight()
            r1.resolve(r13)
        L_0x008a:
            if (r14 != 0) goto L_0x00b1
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r13 = r12.container
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r13.mListDimensionBehaviors
            r8 = r1[r5]
            if (r8 == r2) goto L_0x0098
            r1 = r1[r5]
            if (r1 != r0) goto L_0x00be
        L_0x0098:
            int r13 = r13.getWidth()
            int r13 = r13 + r7
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r12.container
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r0 = r0.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.end
            r0.resolve(r13)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r12.container
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r0 = r0.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            int r13 = r13 - r7
            r0.resolve(r13)
            goto L_0x00d8
        L_0x00b1:
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r13 = r12.container
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r13.mListDimensionBehaviors
            r7 = r1[r3]
            if (r7 == r2) goto L_0x00c0
            r1 = r1[r3]
            if (r1 != r0) goto L_0x00be
            goto L_0x00c0
        L_0x00be:
            r13 = r5
            goto L_0x00d9
        L_0x00c0:
            int r13 = r13.getHeight()
            int r13 = r13 + r8
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r12.container
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.end
            r0.resolve(r13)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = r12.container
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r0.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r0 = r0.dimension
            int r13 = r13 - r8
            r0.resolve(r13)
        L_0x00d8:
            r13 = r3
        L_0x00d9:
            r12.measureWidgets()
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r0 = r12.mRuns
            java.util.Iterator r0 = r0.iterator()
        L_0x00e2:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x0102
            java.lang.Object r1 = r0.next()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r1 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r1
            int r2 = r1.orientation
            if (r2 == r14) goto L_0x00f3
            goto L_0x00e2
        L_0x00f3:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r1.widget
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r7 = r12.container
            if (r2 != r7) goto L_0x00fe
            boolean r2 = r1.resolved
            if (r2 != 0) goto L_0x00fe
            goto L_0x00e2
        L_0x00fe:
            r1.applyToWidget()
            goto L_0x00e2
        L_0x0102:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.analyzer.WidgetRun> r0 = r12.mRuns
            java.util.Iterator r0 = r0.iterator()
        L_0x0108:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x013b
            java.lang.Object r1 = r0.next()
            androidx.constraintlayout.solver.widgets.analyzer.WidgetRun r1 = (androidx.constraintlayout.solver.widgets.analyzer.WidgetRun) r1
            int r2 = r1.orientation
            if (r2 == r14) goto L_0x0119
            goto L_0x0108
        L_0x0119:
            if (r13 != 0) goto L_0x0122
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r1.widget
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r7 = r12.container
            if (r2 != r7) goto L_0x0122
            goto L_0x0108
        L_0x0122:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.start
            boolean r2 = r2.resolved
            if (r2 != 0) goto L_0x0129
            goto L_0x013a
        L_0x0129:
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r1.end
            boolean r2 = r2.resolved
            if (r2 != 0) goto L_0x0130
            goto L_0x013a
        L_0x0130:
            boolean r2 = r1 instanceof androidx.constraintlayout.solver.widgets.analyzer.ChainRun
            if (r2 != 0) goto L_0x0108
            androidx.constraintlayout.solver.widgets.analyzer.DimensionDependency r1 = r1.dimension
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x0108
        L_0x013a:
            r3 = r5
        L_0x013b:
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r13 = r12.container
            r13.setHorizontalDimensionBehaviour(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r12 = r12.container
            r12.setVerticalDimensionBehaviour(r6)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer.directMeasureWithOrientation(boolean, int):boolean");
    }

    /* JADX WARNING: type inference failed for: r4v6, types: [boolean] */
    /* JADX WARNING: type inference failed for: r4v8 */
    /* JADX WARNING: type inference failed for: r4v9 */
    /* JADX WARNING: Removed duplicated region for block: B:110:0x022e  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x005b  */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0069  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0082  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x010c  */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x012e  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x0153  */
    /* JADX WARNING: Removed duplicated region for block: B:85:0x01b3  */
    /* JADX WARNING: Removed duplicated region for block: B:88:0x01c6  */
    /* JADX WARNING: Removed duplicated region for block: B:91:0x01e0  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x01eb  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x01ee  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void layout() {
        /*
            r18 = this;
            r1 = r18
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r2 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.FIXED
            r4 = 0
            r1.f15mX = r4
            r1.f16mY = r4
            int r0 = r18.getWidth()
            int r5 = java.lang.Math.max(r4, r0)
            int r0 = r18.getHeight()
            int r6 = java.lang.Math.max(r4, r0)
            r1.mWidthMeasuredTooSmall = r4
            r1.mHeightMeasuredTooSmall = r4
            int r0 = r1.mOptimizationLevel
            r7 = r0 & 64
            r8 = 1
            r9 = 64
            if (r7 != r9) goto L_0x002a
            r7 = r8
            goto L_0x002b
        L_0x002a:
            r7 = r4
        L_0x002b:
            if (r7 != 0) goto L_0x003a
            r7 = 128(0x80, float:1.794E-43)
            r0 = r0 & r7
            if (r0 != r7) goto L_0x0034
            r0 = r8
            goto L_0x0035
        L_0x0034:
            r0 = r4
        L_0x0035:
            if (r0 == 0) goto L_0x0038
            goto L_0x003a
        L_0x0038:
            r0 = r4
            goto L_0x003b
        L_0x003a:
            r0 = r8
        L_0x003b:
            androidx.constraintlayout.solver.LinearSystem r7 = r1.mSystem
            java.util.Objects.requireNonNull(r7)
            r7.newgraphOptimizer = r4
            int r9 = r1.mOptimizationLevel
            if (r9 == 0) goto L_0x004a
            if (r0 == 0) goto L_0x004a
            r7.newgraphOptimizer = r8
        L_0x004a:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.mListDimensionBehaviors
            r7 = r0[r8]
            r9 = r0[r4]
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r10 = r1.mChildren
            r11 = r0[r4]
            if (r11 == r2) goto L_0x005d
            r0 = r0[r8]
            if (r0 != r2) goto L_0x005b
            goto L_0x005d
        L_0x005b:
            r11 = r4
            goto L_0x005e
        L_0x005d:
            r11 = r8
        L_0x005e:
            r1.mHorizontalChainsSize = r4
            r1.mVerticalChainsSize = r4
            int r12 = r10.size()
            r0 = r4
        L_0x0067:
            if (r0 >= r12) goto L_0x007d
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r13 = r1.mChildren
            java.lang.Object r13 = r13.get(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r13
            boolean r14 = r13 instanceof androidx.constraintlayout.solver.widgets.WidgetContainer
            if (r14 == 0) goto L_0x007a
            androidx.constraintlayout.solver.widgets.WidgetContainer r13 = (androidx.constraintlayout.solver.widgets.WidgetContainer) r13
            r13.layout()
        L_0x007a:
            int r0 = r0 + 1
            goto L_0x0067
        L_0x007d:
            r0 = r4
            r14 = r0
            r13 = r8
        L_0x0080:
            if (r13 == 0) goto L_0x022a
            int r15 = r0 + 1
            androidx.constraintlayout.solver.LinearSystem r0 = r1.mSystem     // Catch:{ Exception -> 0x00ed }
            r0.reset()     // Catch:{ Exception -> 0x00ed }
            r1.mHorizontalChainsSize = r4     // Catch:{ Exception -> 0x00ed }
            r1.mVerticalChainsSize = r4     // Catch:{ Exception -> 0x00ed }
            androidx.constraintlayout.solver.LinearSystem r0 = r1.mSystem     // Catch:{ Exception -> 0x00ed }
            r1.createObjectVariables(r0)     // Catch:{ Exception -> 0x00ed }
            r0 = r4
        L_0x0093:
            if (r0 >= r12) goto L_0x00a7
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r8 = r1.mChildren     // Catch:{ Exception -> 0x00ed }
            java.lang.Object r8 = r8.get(r0)     // Catch:{ Exception -> 0x00ed }
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r8     // Catch:{ Exception -> 0x00ed }
            androidx.constraintlayout.solver.LinearSystem r4 = r1.mSystem     // Catch:{ Exception -> 0x00ed }
            r8.createObjectVariables(r4)     // Catch:{ Exception -> 0x00ed }
            int r0 = r0 + 1
            r4 = 0
            r8 = 1
            goto L_0x0093
        L_0x00a7:
            androidx.constraintlayout.solver.LinearSystem r0 = r1.mSystem     // Catch:{ Exception -> 0x00ed }
            r1.addChildrenToSolver(r0)     // Catch:{ Exception -> 0x00ed }
            androidx.constraintlayout.solver.LinearSystem r0 = r1.mSystem     // Catch:{ Exception -> 0x00ea }
            java.util.Objects.requireNonNull(r0)     // Catch:{ Exception -> 0x00ea }
            boolean r4 = r0.newgraphOptimizer     // Catch:{ Exception -> 0x00ea }
            if (r4 == 0) goto L_0x00e2
            r4 = 0
        L_0x00b6:
            int r8 = r0.mNumRows     // Catch:{ Exception -> 0x00ea }
            if (r4 >= r8) goto L_0x00c7
            androidx.constraintlayout.solver.ArrayRow[] r8 = r0.mRows     // Catch:{ Exception -> 0x00ea }
            r8 = r8[r4]     // Catch:{ Exception -> 0x00ea }
            boolean r8 = r8.isSimpleDefinition     // Catch:{ Exception -> 0x00ea }
            if (r8 != 0) goto L_0x00c4
            r4 = 0
            goto L_0x00c8
        L_0x00c4:
            int r4 = r4 + 1
            goto L_0x00b6
        L_0x00c7:
            r4 = 1
        L_0x00c8:
            if (r4 != 0) goto L_0x00d0
            androidx.constraintlayout.solver.OptimizedPriorityGoalRow r4 = r0.mGoal     // Catch:{ Exception -> 0x00ea }
            r0.minimizeGoal(r4)     // Catch:{ Exception -> 0x00ea }
            goto L_0x00e7
        L_0x00d0:
            r4 = 0
        L_0x00d1:
            int r8 = r0.mNumRows     // Catch:{ Exception -> 0x00ea }
            if (r4 >= r8) goto L_0x00e7
            androidx.constraintlayout.solver.ArrayRow[] r8 = r0.mRows     // Catch:{ Exception -> 0x00ea }
            r8 = r8[r4]     // Catch:{ Exception -> 0x00ea }
            androidx.constraintlayout.solver.SolverVariable r13 = r8.variable     // Catch:{ Exception -> 0x00ea }
            float r8 = r8.constantValue     // Catch:{ Exception -> 0x00ea }
            r13.computedValue = r8     // Catch:{ Exception -> 0x00ea }
            int r4 = r4 + 1
            goto L_0x00d1
        L_0x00e2:
            androidx.constraintlayout.solver.OptimizedPriorityGoalRow r4 = r0.mGoal     // Catch:{ Exception -> 0x00ea }
            r0.minimizeGoal(r4)     // Catch:{ Exception -> 0x00ea }
        L_0x00e7:
            r16 = 1
            goto L_0x0109
        L_0x00ea:
            r0 = move-exception
            r13 = 1
            goto L_0x00ee
        L_0x00ed:
            r0 = move-exception
        L_0x00ee:
            r0.printStackTrace()
            java.io.PrintStream r4 = java.lang.System.out
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            r8.<init>()
            r16 = r13
            java.lang.String r13 = "EXCEPTION : "
            r8.append(r13)
            r8.append(r0)
            java.lang.String r0 = r8.toString()
            r4.println(r0)
        L_0x0109:
            r0 = 2
            if (r16 == 0) goto L_0x012e
            androidx.constraintlayout.solver.LinearSystem r4 = r1.mSystem
            boolean[] r8 = androidx.constraintlayout.solver.widgets.Optimizer.flags
            r13 = 0
            r8[r0] = r13
            r1.updateFromSolver(r4)
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r8 = r1.mChildren
            int r8 = r8.size()
            r13 = 0
        L_0x011d:
            if (r13 >= r8) goto L_0x0146
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r0 = r1.mChildren
            java.lang.Object r0 = r0.get(r13)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r0
            r0.updateFromSolver(r4)
            int r13 = r13 + 1
            r0 = 2
            goto L_0x011d
        L_0x012e:
            androidx.constraintlayout.solver.LinearSystem r0 = r1.mSystem
            r1.updateFromSolver(r0)
            r0 = 0
        L_0x0134:
            if (r0 >= r12) goto L_0x0146
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r4 = r1.mChildren
            java.lang.Object r4 = r4.get(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r4
            androidx.constraintlayout.solver.LinearSystem r8 = r1.mSystem
            r4.updateFromSolver(r8)
            int r0 = r0 + 1
            goto L_0x0134
        L_0x0146:
            if (r11 == 0) goto L_0x01b3
            r0 = 8
            if (r15 >= r0) goto L_0x01b3
            boolean[] r0 = androidx.constraintlayout.solver.widgets.Optimizer.flags
            r4 = 2
            boolean r0 = r0[r4]
            if (r0 == 0) goto L_0x01b3
            r0 = 0
            r4 = 0
            r8 = 0
        L_0x0156:
            if (r0 >= r12) goto L_0x017e
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r13 = r1.mChildren
            java.lang.Object r13 = r13.get(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r13
            r16 = r11
            int r11 = r13.f15mX
            int r17 = r13.getWidth()
            int r11 = r17 + r11
            int r4 = java.lang.Math.max(r4, r11)
            int r11 = r13.f16mY
            int r13 = r13.getHeight()
            int r13 = r13 + r11
            int r8 = java.lang.Math.max(r8, r13)
            int r0 = r0 + 1
            r11 = r16
            goto L_0x0156
        L_0x017e:
            r16 = r11
            int r0 = r1.mMinWidth
            int r0 = java.lang.Math.max(r0, r4)
            int r4 = r1.mMinHeight
            int r4 = java.lang.Math.max(r4, r8)
            if (r9 != r2) goto L_0x019f
            int r8 = r18.getWidth()
            if (r8 >= r0) goto L_0x019f
            r1.setWidth(r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.mListDimensionBehaviors
            r8 = 0
            r0[r8] = r2
            r0 = 1
            r14 = 1
            goto L_0x01a0
        L_0x019f:
            r0 = 0
        L_0x01a0:
            if (r7 != r2) goto L_0x01b6
            int r8 = r18.getHeight()
            if (r8 >= r4) goto L_0x01b6
            r1.setHeight(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.mListDimensionBehaviors
            r4 = 1
            r0[r4] = r2
            r0 = 1
            r14 = 1
            goto L_0x01b6
        L_0x01b3:
            r16 = r11
            r0 = 0
        L_0x01b6:
            int r4 = r1.mMinWidth
            int r8 = r18.getWidth()
            int r4 = java.lang.Math.max(r4, r8)
            int r8 = r18.getWidth()
            if (r4 <= r8) goto L_0x01d0
            r1.setWidth(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.mListDimensionBehaviors
            r4 = 0
            r0[r4] = r3
            r0 = 1
            r14 = 1
        L_0x01d0:
            int r4 = r1.mMinHeight
            int r8 = r18.getHeight()
            int r4 = java.lang.Math.max(r4, r8)
            int r8 = r18.getHeight()
            if (r4 <= r8) goto L_0x01eb
            r1.setHeight(r4)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.mListDimensionBehaviors
            r4 = 1
            r0[r4] = r3
            r0 = r4
            r14 = r0
            goto L_0x01ec
        L_0x01eb:
            r4 = 1
        L_0x01ec:
            if (r14 != 0) goto L_0x0222
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r8 = r1.mListDimensionBehaviors
            r11 = 0
            r8 = r8[r11]
            if (r8 != r2) goto L_0x0208
            if (r5 <= 0) goto L_0x0208
            int r8 = r18.getWidth()
            if (r8 <= r5) goto L_0x0208
            r1.mWidthMeasuredTooSmall = r4
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.mListDimensionBehaviors
            r0[r11] = r3
            r1.setWidth(r5)
            r0 = r4
            r14 = r0
        L_0x0208:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r8 = r1.mListDimensionBehaviors
            r8 = r8[r4]
            if (r8 != r2) goto L_0x0222
            if (r6 <= 0) goto L_0x0222
            int r8 = r18.getHeight()
            if (r8 <= r6) goto L_0x0222
            r1.mHeightMeasuredTooSmall = r4
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.mListDimensionBehaviors
            r0[r4] = r3
            r1.setHeight(r6)
            r13 = 1
            r14 = 1
            goto L_0x0223
        L_0x0222:
            r13 = r0
        L_0x0223:
            r0 = r15
            r11 = r16
            r4 = 0
            r8 = 1
            goto L_0x0080
        L_0x022a:
            r1.mChildren = r10
            if (r14 == 0) goto L_0x0236
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r1.mListDimensionBehaviors
            r2 = 0
            r0[r2] = r9
            r2 = 1
            r0[r2] = r7
        L_0x0236:
            androidx.constraintlayout.solver.LinearSystem r0 = r1.mSystem
            java.util.Objects.requireNonNull(r0)
            androidx.constraintlayout.solver.Cache r0 = r0.mCache
            r1.resetSolverVariables(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer.layout():void");
    }

    public final void reset() {
        this.mSystem.reset();
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        super.reset();
    }

    public final void updateHierarchy() {
        ConstraintWidget.DimensionBehaviour dimensionBehaviour;
        BasicMeasure basicMeasure = this.mBasicMeasureSolver;
        Objects.requireNonNull(basicMeasure);
        basicMeasure.mVariableDimensionsWidgets.clear();
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            ConstraintWidget constraintWidget = this.mChildren.get(i);
            Objects.requireNonNull(constraintWidget);
            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = dimensionBehaviourArr[0];
            ConstraintWidget.DimensionBehaviour dimensionBehaviour3 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
            if (dimensionBehaviour2 == dimensionBehaviour3 || dimensionBehaviourArr[0] == (dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_PARENT) || dimensionBehaviourArr[1] == dimensionBehaviour3 || dimensionBehaviourArr[1] == dimensionBehaviour) {
                basicMeasure.mVariableDimensionsWidgets.add(constraintWidget);
            }
        }
        DependencyGraph dependencyGraph = this.mDependencyGraph;
        Objects.requireNonNull(dependencyGraph);
        dependencyGraph.mNeedBuildGraph = true;
    }

    public final void updateFromRuns(boolean z, boolean z2) {
        super.updateFromRuns(z, z2);
        int size = this.mChildren.size();
        for (int i = 0; i < size; i++) {
            this.mChildren.get(i).updateFromRuns(z, z2);
        }
    }
}
