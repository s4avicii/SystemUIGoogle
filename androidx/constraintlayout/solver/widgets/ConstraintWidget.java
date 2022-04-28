package androidx.constraintlayout.solver.widgets;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import androidx.constraintlayout.motion.widget.MotionController$$ExternalSyntheticOutline1;
import androidx.constraintlayout.solver.Cache;
import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.analyzer.ChainRun;
import androidx.constraintlayout.solver.widgets.analyzer.DependencyNode;
import androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun;
import androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun;
import com.android.systemui.plugins.FalsingManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class ConstraintWidget {
    public boolean hasBaseline = false;
    public ChainRun horizontalChainRun;
    public HorizontalWidgetRun horizontalRun = new HorizontalWidgetRun(this);
    public boolean[] isTerminalWidget = {true, true};
    public ArrayList<ConstraintAnchor> mAnchors;
    public ConstraintAnchor mBaseline;
    public int mBaselineDistance;
    public ConstraintAnchor mBottom;
    public ConstraintAnchor mCenter;
    public ConstraintAnchor mCenterX;
    public ConstraintAnchor mCenterY;
    public float mCircleConstraintAngle = 0.0f;
    public Object mCompanionWidget;
    public String mDebugName;
    public float mDimensionRatio;
    public int mDimensionRatioSide;
    public int mHeight;
    public float mHorizontalBiasPercent;
    public int mHorizontalChainStyle;
    public ConstraintWidget mHorizontalNextWidget;
    public int mHorizontalResolution = -1;
    public ConstraintAnchor mLeft;
    public ConstraintAnchor[] mListAnchors;
    public DimensionBehaviour[] mListDimensionBehaviors;
    public ConstraintWidget[] mListNextMatchConstraintsWidget;
    public int mMatchConstraintDefaultHeight = 0;
    public int mMatchConstraintDefaultWidth = 0;
    public int mMatchConstraintMaxHeight = 0;
    public int mMatchConstraintMaxWidth = 0;
    public int mMatchConstraintMinHeight = 0;
    public int mMatchConstraintMinWidth = 0;
    public float mMatchConstraintPercentHeight = 1.0f;
    public float mMatchConstraintPercentWidth = 1.0f;
    public int[] mMaxDimension = {Integer.MAX_VALUE, Integer.MAX_VALUE};
    public int mMinHeight;
    public int mMinWidth;
    public ConstraintWidget[] mNextChainWidget;
    public ConstraintWidget mParent;
    public float mResolvedDimensionRatio = 1.0f;
    public int mResolvedDimensionRatioSide = -1;
    public int[] mResolvedMatchConstraintDefault = new int[2];
    public ConstraintAnchor mRight;
    public ConstraintAnchor mTop;
    public String mType;
    public float mVerticalBiasPercent;
    public int mVerticalChainStyle;
    public ConstraintWidget mVerticalNextWidget;
    public int mVerticalResolution = -1;
    public int mVisibility;
    public float[] mWeight;
    public int mWidth;

    /* renamed from: mX */
    public int f15mX;

    /* renamed from: mY */
    public int f16mY;
    public boolean measured = false;
    public ChainRun verticalChainRun;
    public VerticalWidgetRun verticalRun = new VerticalWidgetRun(this);
    public int[] wrapMeasure = {0, 0};

    public enum DimensionBehaviour {
        FIXED,
        WRAP_CONTENT,
        MATCH_CONSTRAINT,
        MATCH_PARENT
    }

    public final void connect(ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, int i) {
        Objects.requireNonNull(constraintAnchor);
        if (constraintAnchor.mOwner == this) {
            ConstraintAnchor.Type type = constraintAnchor.mType;
            Objects.requireNonNull(constraintAnchor2);
            connect(type, constraintAnchor2.mOwner, constraintAnchor2.mType, i);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v6, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r18v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r31v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r17v3, resolved type: int} */
    /* JADX WARNING: Code restructure failed: missing block: B:275:0x04aa, code lost:
        if (r13.mVisibility == 8) goto L_0x04af;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:188:0x02ea  */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x02f4  */
    /* JADX WARNING: Removed duplicated region for block: B:198:0x0302  */
    /* JADX WARNING: Removed duplicated region for block: B:199:0x0305  */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x0309  */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x030c  */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x031e  */
    /* JADX WARNING: Removed duplicated region for block: B:228:0x03f2  */
    /* JADX WARNING: Removed duplicated region for block: B:234:0x040e  */
    /* JADX WARNING: Removed duplicated region for block: B:244:0x0450  */
    /* JADX WARNING: Removed duplicated region for block: B:247:0x0461  */
    /* JADX WARNING: Removed duplicated region for block: B:248:0x0463  */
    /* JADX WARNING: Removed duplicated region for block: B:250:0x0466  */
    /* JADX WARNING: Removed duplicated region for block: B:288:0x0520  */
    /* JADX WARNING: Removed duplicated region for block: B:290:0x0526  */
    /* JADX WARNING: Removed duplicated region for block: B:294:0x0585  */
    /* JADX WARNING: Removed duplicated region for block: B:297:0x0591  */
    /* JADX WARNING: Removed duplicated region for block: B:304:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void addToSolver(androidx.constraintlayout.solver.LinearSystem r50) {
        /*
            r49 = this;
            r13 = r49
            r9 = r50
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r7 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mLeft
            androidx.constraintlayout.solver.SolverVariable r6 = r9.createObjectVariable(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mRight
            androidx.constraintlayout.solver.SolverVariable r4 = r9.createObjectVariable(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mTop
            androidx.constraintlayout.solver.SolverVariable r3 = r9.createObjectVariable(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mBottom
            androidx.constraintlayout.solver.SolverVariable r1 = r9.createObjectVariable(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mBaseline
            androidx.constraintlayout.solver.SolverVariable r0 = r9.createObjectVariable(r0)
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r2 = r13.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r5 = r2.start
            boolean r8 = r5.resolved
            r15 = 7
            r14 = 1
            r12 = 0
            if (r8 == 0) goto L_0x00b3
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.end
            boolean r2 = r2.resolved
            if (r2 == 0) goto L_0x00b3
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r2 = r13.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r8 = r2.start
            boolean r8 = r8.resolved
            if (r8 == 0) goto L_0x00b3
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.end
            boolean r2 = r2.resolved
            if (r2 == 0) goto L_0x00b3
            int r2 = r5.value
            r9.addEquality(r6, r2)
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r2 = r13.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.end
            int r2 = r2.value
            r9.addEquality(r4, r2)
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r2 = r13.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.start
            int r2 = r2.value
            r9.addEquality(r3, r2)
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r2 = r13.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.end
            int r2 = r2.value
            r9.addEquality(r1, r2)
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r2 = r13.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r2 = r2.baseline
            int r2 = r2.value
            r9.addEquality(r0, r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r13.mParent
            if (r0 == 0) goto L_0x00b2
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r0.mListDimensionBehaviors
            r2 = r0[r12]
            if (r2 != r7) goto L_0x0078
            r2 = r14
            goto L_0x0079
        L_0x0078:
            r2 = r12
        L_0x0079:
            r0 = r0[r14]
            if (r0 != r7) goto L_0x007f
            r0 = r14
            goto L_0x0080
        L_0x007f:
            r0 = r12
        L_0x0080:
            if (r2 == 0) goto L_0x0099
            boolean[] r2 = r13.isTerminalWidget
            boolean r2 = r2[r12]
            if (r2 == 0) goto L_0x0099
            boolean r2 = r49.isInHorizontalChain()
            if (r2 != 0) goto L_0x0099
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r13.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mRight
            androidx.constraintlayout.solver.SolverVariable r2 = r9.createObjectVariable(r2)
            r9.addGreaterThan(r2, r4, r12, r15)
        L_0x0099:
            if (r0 == 0) goto L_0x00b2
            boolean[] r0 = r13.isTerminalWidget
            boolean r0 = r0[r14]
            if (r0 == 0) goto L_0x00b2
            boolean r0 = r49.isInVerticalChain()
            if (r0 != 0) goto L_0x00b2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r13.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            androidx.constraintlayout.solver.SolverVariable r0 = r9.createObjectVariable(r0)
            r9.addGreaterThan(r0, r1, r12, r15)
        L_0x00b2:
            return
        L_0x00b3:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r13.mParent
            r11 = 8
            if (r2 == 0) goto L_0x013a
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r2.mListDimensionBehaviors
            r5 = r2[r12]
            if (r5 != r7) goto L_0x00c1
            r5 = r14
            goto L_0x00c2
        L_0x00c1:
            r5 = r12
        L_0x00c2:
            r2 = r2[r14]
            if (r2 != r7) goto L_0x00c8
            r2 = r14
            goto L_0x00c9
        L_0x00c8:
            r2 = r12
        L_0x00c9:
            boolean r8 = r13.isChainHead(r12)
            if (r8 == 0) goto L_0x00d8
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = r13.mParent
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r8 = (androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer) r8
            r8.addChain(r13, r12)
            r8 = r14
            goto L_0x00dc
        L_0x00d8:
            boolean r8 = r49.isInHorizontalChain()
        L_0x00dc:
            boolean r10 = r13.isChainHead(r14)
            if (r10 == 0) goto L_0x00eb
            androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r13.mParent
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r10 = (androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer) r10
            r10.addChain(r13, r14)
            r10 = r14
            goto L_0x00ef
        L_0x00eb:
            boolean r10 = r49.isInVerticalChain()
        L_0x00ef:
            if (r8 != 0) goto L_0x010e
            if (r5 == 0) goto L_0x010e
            int r15 = r13.mVisibility
            if (r15 == r11) goto L_0x010e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r13.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r15.mTarget
            if (r15 != 0) goto L_0x010e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r13.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r15.mTarget
            if (r15 != 0) goto L_0x010e
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = r13.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r15.mRight
            androidx.constraintlayout.solver.SolverVariable r15 = r9.createObjectVariable(r15)
            r9.addGreaterThan(r15, r4, r12, r14)
        L_0x010e:
            if (r10 != 0) goto L_0x0131
            if (r2 == 0) goto L_0x0131
            int r15 = r13.mVisibility
            if (r15 == r11) goto L_0x0131
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r13.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r15.mTarget
            if (r15 != 0) goto L_0x0131
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r13.mBottom
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r15.mTarget
            if (r15 != 0) goto L_0x0131
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r13.mBaseline
            if (r15 != 0) goto L_0x0131
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = r13.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r15.mBottom
            androidx.constraintlayout.solver.SolverVariable r15 = r9.createObjectVariable(r15)
            r9.addGreaterThan(r15, r1, r12, r14)
        L_0x0131:
            r26 = r2
            r27 = r5
            r29 = r8
            r28 = r10
            goto L_0x0142
        L_0x013a:
            r26 = r12
            r27 = r26
            r28 = r27
            r29 = r28
        L_0x0142:
            int r2 = r13.mWidth
            int r5 = r13.mMinWidth
            if (r2 >= r5) goto L_0x0149
            goto L_0x014a
        L_0x0149:
            r5 = r2
        L_0x014a:
            int r8 = r13.mHeight
            int r10 = r13.mMinHeight
            if (r8 >= r10) goto L_0x0151
            goto L_0x0152
        L_0x0151:
            r10 = r8
        L_0x0152:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r15 = r13.mListDimensionBehaviors
            r11 = r15[r12]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r12 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r19 = r0
            if (r11 == r12) goto L_0x015e
            r11 = r14
            goto L_0x015f
        L_0x015e:
            r11 = 0
        L_0x015f:
            r0 = r15[r14]
            if (r0 == r12) goto L_0x0165
            r0 = r14
            goto L_0x0166
        L_0x0165:
            r0 = 0
        L_0x0166:
            int r14 = r13.mDimensionRatioSide
            r13.mResolvedDimensionRatioSide = r14
            r21 = r1
            float r1 = r13.mDimensionRatio
            r13.mResolvedDimensionRatio = r1
            r25 = r3
            int r3 = r13.mMatchConstraintDefaultWidth
            r22 = r5
            int r5 = r13.mMatchConstraintDefaultHeight
            r23 = 0
            int r23 = (r1 > r23 ? 1 : (r1 == r23 ? 0 : -1))
            r24 = 4
            r30 = r10
            if (r23 <= 0) goto L_0x02d0
            int r10 = r13.mVisibility
            r32 = r4
            r4 = 8
            if (r10 == r4) goto L_0x02d2
            r10 = 0
            r4 = r15[r10]
            if (r4 != r12) goto L_0x0192
            if (r3 != 0) goto L_0x0192
            r3 = 3
        L_0x0192:
            r4 = 1
            r10 = r15[r4]
            if (r10 != r12) goto L_0x019d
            if (r5 != 0) goto L_0x019d
            r34 = r6
            r5 = 3
            goto L_0x019f
        L_0x019d:
            r34 = r6
        L_0x019f:
            r10 = 0
            r6 = r15[r10]
            if (r6 != r12) goto L_0x0270
            r6 = r15[r4]
            if (r6 != r12) goto L_0x026e
            r4 = 3
            if (r3 != r4) goto L_0x026e
            if (r5 != r4) goto L_0x026e
            r4 = -1
            if (r14 != r4) goto L_0x01c6
            if (r11 == 0) goto L_0x01b7
            if (r0 != 0) goto L_0x01b7
            r13.mResolvedDimensionRatioSide = r10
            goto L_0x01c6
        L_0x01b7:
            if (r11 != 0) goto L_0x01c6
            if (r0 == 0) goto L_0x01c6
            r0 = 1
            r13.mResolvedDimensionRatioSide = r0
            if (r14 != r4) goto L_0x01c6
            r0 = 1065353216(0x3f800000, float:1.0)
            float r10 = r0 / r1
            r13.mResolvedDimensionRatio = r10
        L_0x01c6:
            int r0 = r13.mResolvedDimensionRatioSide
            if (r0 != 0) goto L_0x01de
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mTop
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x01da
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mBottom
            boolean r0 = r0.isConnected()
            if (r0 != 0) goto L_0x01de
        L_0x01da:
            r0 = 1
            r13.mResolvedDimensionRatioSide = r0
            goto L_0x01f6
        L_0x01de:
            r0 = 1
            int r1 = r13.mResolvedDimensionRatioSide
            if (r1 != r0) goto L_0x01f6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mLeft
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x01f3
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mRight
            boolean r0 = r0.isConnected()
            if (r0 != 0) goto L_0x01f6
        L_0x01f3:
            r0 = 0
            r13.mResolvedDimensionRatioSide = r0
        L_0x01f6:
            int r0 = r13.mResolvedDimensionRatioSide
            r1 = -1
            if (r0 != r1) goto L_0x024a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mTop
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x021b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mBottom
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x021b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mLeft
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x021b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mRight
            boolean r0 = r0.isConnected()
            if (r0 != 0) goto L_0x024a
        L_0x021b:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mTop
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x022f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mBottom
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x022f
            r0 = 0
            r13.mResolvedDimensionRatioSide = r0
            goto L_0x024a
        L_0x022f:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mLeft
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x024a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r13.mRight
            boolean r0 = r0.isConnected()
            if (r0 == 0) goto L_0x024a
            float r0 = r13.mResolvedDimensionRatio
            r1 = 1065353216(0x3f800000, float:1.0)
            float r10 = r1 / r0
            r13.mResolvedDimensionRatio = r10
            r0 = 1
            r13.mResolvedDimensionRatioSide = r0
        L_0x024a:
            int r0 = r13.mResolvedDimensionRatioSide
            r1 = -1
            if (r0 != r1) goto L_0x02c1
            int r0 = r13.mMatchConstraintMinWidth
            if (r0 <= 0) goto L_0x025c
            int r1 = r13.mMatchConstraintMinHeight
            if (r1 != 0) goto L_0x025c
            r1 = 0
            r13.mResolvedDimensionRatioSide = r1
            goto L_0x02c1
        L_0x025c:
            if (r0 != 0) goto L_0x02c1
            int r0 = r13.mMatchConstraintMinHeight
            if (r0 <= 0) goto L_0x02c1
            float r0 = r13.mResolvedDimensionRatio
            r1 = 1065353216(0x3f800000, float:1.0)
            float r10 = r1 / r0
            r13.mResolvedDimensionRatio = r10
            r0 = 1
            r13.mResolvedDimensionRatioSide = r0
            goto L_0x02c1
        L_0x026e:
            r0 = 0
            goto L_0x0271
        L_0x0270:
            r0 = r10
        L_0x0271:
            r4 = r15[r0]
            if (r4 != r12) goto L_0x0298
            r4 = 3
            if (r3 != r4) goto L_0x0298
            r13.mResolvedDimensionRatioSide = r0
            float r0 = (float) r8
            float r1 = r1 * r0
            int r0 = (int) r1
            r4 = 1
            r1 = r15[r4]
            if (r1 == r12) goto L_0x028c
            r33 = r5
            r35 = r24
            r31 = r30
            r30 = 0
            r5 = r0
            goto L_0x0295
        L_0x028c:
            r35 = r3
            r33 = r5
            r31 = r30
            r5 = r0
            r30 = r4
        L_0x0295:
            r0 = 1065353216(0x3f800000, float:1.0)
            goto L_0x02e0
        L_0x0298:
            r4 = 1
            r0 = r15[r4]
            if (r0 != r12) goto L_0x02c1
            r0 = 3
            if (r5 != r0) goto L_0x02c1
            r13.mResolvedDimensionRatioSide = r4
            r0 = -1
            if (r14 != r0) goto L_0x02ac
            r0 = 1065353216(0x3f800000, float:1.0)
            float r10 = r0 / r1
            r13.mResolvedDimensionRatio = r10
            goto L_0x02ae
        L_0x02ac:
            r0 = 1065353216(0x3f800000, float:1.0)
        L_0x02ae:
            float r1 = r13.mResolvedDimensionRatio
            float r2 = (float) r2
            float r1 = r1 * r2
            int r10 = (int) r1
            r1 = 0
            r2 = r15[r1]
            if (r2 == r12) goto L_0x02c5
            r35 = r3
            r31 = r10
            r5 = r22
            r33 = r24
            goto L_0x02de
        L_0x02c1:
            r0 = 1065353216(0x3f800000, float:1.0)
            r10 = r30
        L_0x02c5:
            r35 = r3
            r33 = r5
            r31 = r10
            r5 = r22
            r30 = 1
            goto L_0x02e0
        L_0x02d0:
            r32 = r4
        L_0x02d2:
            r34 = r6
            r0 = 1065353216(0x3f800000, float:1.0)
            r35 = r3
            r33 = r5
            r5 = r22
            r31 = r30
        L_0x02de:
            r30 = 0
        L_0x02e0:
            int[] r1 = r13.mResolvedMatchConstraintDefault
            r2 = 0
            r1[r2] = r35
            r2 = 1
            r1[r2] = r33
            if (r30 == 0) goto L_0x02f4
            int r1 = r13.mResolvedDimensionRatioSide
            r2 = -1
            if (r1 == 0) goto L_0x02f1
            if (r1 != r2) goto L_0x02f5
        L_0x02f1:
            r36 = 1
            goto L_0x02f7
        L_0x02f4:
            r2 = -1
        L_0x02f5:
            r36 = 0
        L_0x02f7:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r13.mListDimensionBehaviors
            r3 = 0
            r1 = r1[r3]
            if (r1 != r7) goto L_0x0305
            boolean r1 = r13 instanceof androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer
            if (r1 == 0) goto L_0x0305
            r37 = 1
            goto L_0x0307
        L_0x0305:
            r37 = 0
        L_0x0307:
            if (r37 == 0) goto L_0x030c
            r38 = 0
            goto L_0x030e
        L_0x030c:
            r38 = r5
        L_0x030e:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r13.mCenter
            boolean r1 = r1.isConnected()
            r3 = 1
            r39 = r1 ^ 1
            int r1 = r13.mHorizontalResolution
            r40 = 0
            r6 = 2
            if (r1 == r6) goto L_0x03f2
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r1 = r13.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r4 = r1.start
            boolean r5 = r4.resolved
            if (r5 == 0) goto L_0x036e
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            boolean r1 = r1.resolved
            if (r1 != 0) goto L_0x032d
            goto L_0x036e
        L_0x032d:
            int r1 = r4.value
            r4 = r34
            r9.addEquality(r4, r1)
            androidx.constraintlayout.solver.widgets.analyzer.HorizontalWidgetRun r1 = r13.horizontalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r1.end
            int r1 = r1.value
            r15 = r32
            r9.addEquality(r15, r1)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r13.mParent
            if (r1 == 0) goto L_0x035f
            if (r27 == 0) goto L_0x035f
            boolean[] r1 = r13.isTerminalWidget
            r5 = 0
            boolean r1 = r1[r5]
            if (r1 == 0) goto L_0x035f
            boolean r1 = r49.isInHorizontalChain()
            if (r1 != 0) goto L_0x035f
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r13.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mRight
            androidx.constraintlayout.solver.SolverVariable r1 = r9.createObjectVariable(r1)
            r14 = 7
            r9.addGreaterThan(r1, r15, r5, r14)
            goto L_0x0360
        L_0x035f:
            r14 = 7
        L_0x0360:
            r45 = r4
            r46 = r7
            r44 = r15
            r41 = r19
            r42 = r21
            r43 = r25
            goto L_0x0400
        L_0x036e:
            r15 = r32
            r4 = r34
            r14 = 7
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r13.mParent
            if (r1 == 0) goto L_0x0380
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mRight
            androidx.constraintlayout.solver.SolverVariable r1 = r9.createObjectVariable(r1)
            r20 = r1
            goto L_0x0382
        L_0x0380:
            r20 = r40
        L_0x0382:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r13.mParent
            if (r1 == 0) goto L_0x038f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mLeft
            androidx.constraintlayout.solver.SolverVariable r1 = r9.createObjectVariable(r1)
            r32 = r1
            goto L_0x0391
        L_0x038f:
            r32 = r40
        L_0x0391:
            boolean[] r1 = r13.isTerminalWidget
            r12 = 0
            boolean r5 = r1[r12]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r13.mListDimensionBehaviors
            r8 = r1[r12]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r13.mLeft
            r1 = r0
            r0 = r2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r13.mRight
            r2 = 8
            int r0 = r13.f15mX
            r2 = r12
            r12 = r0
            int r0 = r13.mMinWidth
            r16 = r14
            r14 = r0
            int[] r0 = r13.mMaxDimension
            r0 = r0[r2]
            r34 = r15
            r15 = r0
            float r0 = r13.mHorizontalBiasPercent
            r16 = r0
            int r0 = r13.mMatchConstraintMinWidth
            r22 = r0
            int r0 = r13.mMatchConstraintMaxWidth
            r23 = r0
            float r0 = r13.mMatchConstraintPercentWidth
            r24 = r0
            r0 = 1
            r2 = r0
            r41 = r19
            r0 = r49
            r42 = r21
            r1 = r50
            r43 = r25
            r3 = r27
            r17 = r4
            r44 = r34
            r4 = r26
            r45 = r17
            r6 = r32
            r46 = r7
            r7 = r20
            r9 = r37
            r13 = r38
            r17 = r36
            r18 = r29
            r19 = r28
            r20 = r35
            r21 = r33
            r25 = r39
            r0.applyConstraints(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
            goto L_0x03fe
        L_0x03f2:
            r46 = r7
            r41 = r19
            r42 = r21
            r43 = r25
            r44 = r32
            r45 = r34
        L_0x03fe:
            r13 = r49
        L_0x0400:
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r13.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r1 = r0.start
            boolean r2 = r1.resolved
            if (r2 == 0) goto L_0x0450
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.end
            boolean r0 = r0.resolved
            if (r0 == 0) goto L_0x0450
            int r0 = r1.value
            r9 = r50
            r7 = r43
            r9.addEquality(r7, r0)
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r13.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.end
            int r0 = r0.value
            r6 = r42
            r9.addEquality(r6, r0)
            androidx.constraintlayout.solver.widgets.analyzer.VerticalWidgetRun r0 = r13.verticalRun
            androidx.constraintlayout.solver.widgets.analyzer.DependencyNode r0 = r0.baseline
            int r0 = r0.value
            r1 = r41
            r9.addEquality(r1, r0)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r13.mParent
            if (r0 == 0) goto L_0x044b
            if (r28 != 0) goto L_0x044b
            if (r26 == 0) goto L_0x044b
            boolean[] r2 = r13.isTerminalWidget
            r4 = 1
            boolean r2 = r2[r4]
            if (r2 == 0) goto L_0x0448
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            androidx.constraintlayout.solver.SolverVariable r0 = r9.createObjectVariable(r0)
            r2 = 7
            r3 = 0
            r9.addGreaterThan(r0, r6, r3, r2)
            goto L_0x044e
        L_0x0448:
            r2 = 7
            r3 = 0
            goto L_0x044e
        L_0x044b:
            r2 = 7
            r3 = 0
            r4 = 1
        L_0x044e:
            r14 = r3
            goto L_0x045c
        L_0x0450:
            r9 = r50
            r1 = r41
            r6 = r42
            r7 = r43
            r2 = 7
            r3 = 0
            r4 = 1
            r14 = r4
        L_0x045c:
            int r0 = r13.mVerticalResolution
            r5 = 2
            if (r0 != r5) goto L_0x0463
            r12 = r3
            goto L_0x0464
        L_0x0463:
            r12 = r14
        L_0x0464:
            if (r12 == 0) goto L_0x0520
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r13.mListDimensionBehaviors
            r0 = r0[r4]
            r5 = r46
            if (r0 != r5) goto L_0x0475
            boolean r0 = r13 instanceof androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer
            if (r0 == 0) goto L_0x0475
            r17 = r4
            goto L_0x0477
        L_0x0475:
            r17 = r3
        L_0x0477:
            if (r17 == 0) goto L_0x047b
            r31 = r3
        L_0x047b:
            if (r30 == 0) goto L_0x0487
            int r0 = r13.mResolvedDimensionRatioSide
            if (r0 == r4) goto L_0x0484
            r5 = -1
            if (r0 != r5) goto L_0x0487
        L_0x0484:
            r18 = r4
            goto L_0x0489
        L_0x0487:
            r18 = r3
        L_0x0489:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r13.mParent
            if (r0 == 0) goto L_0x0494
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mBottom
            androidx.constraintlayout.solver.SolverVariable r0 = r9.createObjectVariable(r0)
            goto L_0x0496
        L_0x0494:
            r0 = r40
        L_0x0496:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r13.mParent
            if (r5 == 0) goto L_0x04a2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.mTop
            androidx.constraintlayout.solver.SolverVariable r5 = r9.createObjectVariable(r5)
            r40 = r5
        L_0x04a2:
            int r5 = r13.mBaselineDistance
            if (r5 > 0) goto L_0x04ad
            int r8 = r13.mVisibility
            r10 = 8
            if (r8 != r10) goto L_0x04d5
            goto L_0x04af
        L_0x04ad:
            r10 = 8
        L_0x04af:
            r9.addEquality(r1, r7, r5, r2)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r13.mBaseline
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 == 0) goto L_0x04ce
            androidx.constraintlayout.solver.SolverVariable r5 = r9.createObjectVariable(r5)
            r9.addEquality(r1, r5, r3, r2)
            if (r26 == 0) goto L_0x04cb
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r13.mBottom
            androidx.constraintlayout.solver.SolverVariable r1 = r9.createObjectVariable(r1)
            r2 = 5
            r9.addGreaterThan(r0, r1, r3, r2)
        L_0x04cb:
            r25 = r3
            goto L_0x04d7
        L_0x04ce:
            int r5 = r13.mVisibility
            if (r5 != r10) goto L_0x04d5
            r9.addEquality(r1, r7, r3, r2)
        L_0x04d5:
            r25 = r39
        L_0x04d7:
            r2 = 0
            boolean[] r1 = r13.isTerminalWidget
            boolean r5 = r1[r4]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r13.mListDimensionBehaviors
            r8 = r1[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r13.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r13.mBottom
            int r12 = r13.f16mY
            int r14 = r13.mMinHeight
            int[] r1 = r13.mMaxDimension
            r15 = r1[r4]
            float r1 = r13.mVerticalBiasPercent
            r16 = r1
            int r1 = r13.mMatchConstraintMinHeight
            r22 = r1
            int r1 = r13.mMatchConstraintMaxHeight
            r23 = r1
            float r1 = r13.mMatchConstraintPercentHeight
            r24 = r1
            r19 = r0
            r0 = r49
            r1 = r50
            r3 = r26
            r4 = r27
            r47 = r6
            r6 = r40
            r48 = r7
            r7 = r19
            r9 = r17
            r13 = r31
            r17 = r18
            r18 = r28
            r19 = r29
            r20 = r33
            r21 = r35
            r0.applyConstraints(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25)
            goto L_0x0524
        L_0x0520:
            r47 = r6
            r48 = r7
        L_0x0524:
            if (r30 == 0) goto L_0x0585
            r0 = r49
            int r1 = r0.mResolvedDimensionRatioSide
            r2 = -1082130432(0xffffffffbf800000, float:-1.0)
            r3 = 1
            if (r1 != r3) goto L_0x055a
            float r1 = r0.mResolvedDimensionRatio
            androidx.constraintlayout.solver.ArrayRow r3 = r50.createRow()
            androidx.constraintlayout.solver.ArrayLinkedVariables r4 = r3.variables
            r5 = r47
            r4.put(r5, r2)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r3.variables
            r4 = r48
            r6 = 1065353216(0x3f800000, float:1.0)
            r2.put(r4, r6)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r3.variables
            r7 = r44
            r2.put(r7, r1)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r3.variables
            float r1 = -r1
            r8 = r45
            r2.put(r8, r1)
            r1 = r50
            r1.addConstraint(r3)
            goto L_0x0589
        L_0x055a:
            r1 = r50
            r7 = r44
            r8 = r45
            r5 = r47
            r4 = r48
            r6 = 1065353216(0x3f800000, float:1.0)
            float r3 = r0.mResolvedDimensionRatio
            androidx.constraintlayout.solver.ArrayRow r9 = r50.createRow()
            androidx.constraintlayout.solver.ArrayLinkedVariables r10 = r9.variables
            r10.put(r7, r2)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r9.variables
            r2.put(r8, r6)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r9.variables
            r2.put(r5, r3)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r9.variables
            float r3 = -r3
            r2.put(r4, r3)
            r1.addConstraint(r9)
            goto L_0x0589
        L_0x0585:
            r0 = r49
            r1 = r50
        L_0x0589:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mCenter
            boolean r2 = r2.isConnected()
            if (r2 == 0) goto L_0x0648
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mCenter
            java.util.Objects.requireNonNull(r2)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            java.util.Objects.requireNonNull(r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r2.mOwner
            float r3 = r0.mCircleConstraintAngle
            r4 = 1119092736(0x42b40000, float:90.0)
            float r3 = r3 + r4
            double r3 = (double) r3
            double r3 = java.lang.Math.toRadians(r3)
            float r3 = (float) r3
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r0.mCenter
            int r4 = r4.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r0.getAnchor(r5)
            androidx.constraintlayout.solver.SolverVariable r6 = r1.createObjectVariable(r6)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r7 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r8 = r0.getAnchor(r7)
            androidx.constraintlayout.solver.SolverVariable r8 = r1.createObjectVariable(r8)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r9 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r0.getAnchor(r9)
            androidx.constraintlayout.solver.SolverVariable r10 = r1.createObjectVariable(r10)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r11 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.getAnchor(r11)
            androidx.constraintlayout.solver.SolverVariable r0 = r1.createObjectVariable(r0)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r2.getAnchor(r5)
            androidx.constraintlayout.solver.SolverVariable r5 = r1.createObjectVariable(r5)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r2.getAnchor(r7)
            androidx.constraintlayout.solver.SolverVariable r7 = r1.createObjectVariable(r7)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r2.getAnchor(r9)
            androidx.constraintlayout.solver.SolverVariable r9 = r1.createObjectVariable(r9)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.getAnchor(r11)
            androidx.constraintlayout.solver.SolverVariable r2 = r1.createObjectVariable(r2)
            androidx.constraintlayout.solver.ArrayRow r11 = r50.createRow()
            double r12 = (double) r3
            double r14 = java.lang.Math.sin(r12)
            double r3 = (double) r4
            double r14 = r14 * r3
            float r14 = (float) r14
            androidx.constraintlayout.solver.ArrayLinkedVariables r15 = r11.variables
            r16 = r10
            r10 = 1056964608(0x3f000000, float:0.5)
            r15.put(r7, r10)
            androidx.constraintlayout.solver.ArrayLinkedVariables r7 = r11.variables
            r7.put(r2, r10)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r11.variables
            r7 = -1090519040(0xffffffffbf000000, float:-0.5)
            r2.put(r8, r7)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r11.variables
            r2.put(r0, r7)
            float r0 = -r14
            r11.constantValue = r0
            r1.addConstraint(r11)
            androidx.constraintlayout.solver.ArrayRow r0 = r50.createRow()
            double r11 = java.lang.Math.cos(r12)
            double r11 = r11 * r3
            float r2 = (float) r11
            androidx.constraintlayout.solver.ArrayLinkedVariables r3 = r0.variables
            r3.put(r5, r10)
            androidx.constraintlayout.solver.ArrayLinkedVariables r3 = r0.variables
            r3.put(r9, r10)
            androidx.constraintlayout.solver.ArrayLinkedVariables r3 = r0.variables
            r3.put(r6, r7)
            androidx.constraintlayout.solver.ArrayLinkedVariables r3 = r0.variables
            r4 = r16
            r3.put(r4, r7)
            float r2 = -r2
            r0.constantValue = r2
            r1.addConstraint(r0)
        L_0x0648:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidget.addToSolver(androidx.constraintlayout.solver.LinearSystem):void");
    }

    public boolean allowedInBarrier() {
        if (this.mVisibility != 8) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:142:0x0245, code lost:
        if (r0 == false) goto L_0x022c;
     */
    /* JADX WARNING: Removed duplicated region for block: B:168:0x0289  */
    /* JADX WARNING: Removed duplicated region for block: B:169:0x02b4  */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x02c3  */
    /* JADX WARNING: Removed duplicated region for block: B:182:0x02e5  */
    /* JADX WARNING: Removed duplicated region for block: B:184:0x02e9  */
    /* JADX WARNING: Removed duplicated region for block: B:211:0x0330  */
    /* JADX WARNING: Removed duplicated region for block: B:225:0x0351  */
    /* JADX WARNING: Removed duplicated region for block: B:226:0x0357  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0076  */
    /* JADX WARNING: Removed duplicated region for block: B:230:0x0365 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:237:0x037d  */
    /* JADX WARNING: Removed duplicated region for block: B:239:0x038a A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0079  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x007d  */
    /* JADX WARNING: Removed duplicated region for block: B:262:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:266:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x009d  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00ba  */
    /* JADX WARNING: Removed duplicated region for block: B:92:0x0198  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void applyConstraints(androidx.constraintlayout.solver.LinearSystem r27, boolean r28, boolean r29, boolean r30, boolean r31, androidx.constraintlayout.solver.SolverVariable r32, androidx.constraintlayout.solver.SolverVariable r33, androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour r34, boolean r35, androidx.constraintlayout.solver.widgets.ConstraintAnchor r36, androidx.constraintlayout.solver.widgets.ConstraintAnchor r37, int r38, int r39, int r40, int r41, float r42, boolean r43, boolean r44, boolean r45, int r46, int r47, int r48, int r49, float r50, boolean r51) {
        /*
            r26 = this;
            r0 = r26
            r9 = r27
            r10 = r32
            r11 = r33
            r12 = r36
            r13 = r37
            r14 = r40
            r1 = r41
            r2 = r47
            r3 = r48
            r4 = r49
            r5 = r50
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r6 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.BOTTOM
            androidx.constraintlayout.solver.SolverVariable r15 = r9.createObjectVariable(r12)
            androidx.constraintlayout.solver.SolverVariable r8 = r9.createObjectVariable(r13)
            java.util.Objects.requireNonNull(r36)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r12.mTarget
            androidx.constraintlayout.solver.SolverVariable r7 = r9.createObjectVariable(r7)
            java.util.Objects.requireNonNull(r37)
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r13.mTarget
            androidx.constraintlayout.solver.SolverVariable r11 = r9.createObjectVariable(r11)
            boolean r16 = r36.isConnected()
            boolean r17 = r37.isConnected()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mCenter
            boolean r2 = r2.isConnected()
            if (r17 == 0) goto L_0x0047
            int r18 = r16 + 1
            goto L_0x0049
        L_0x0047:
            r18 = r16
        L_0x0049:
            if (r2 == 0) goto L_0x004d
            int r18 = r18 + 1
        L_0x004d:
            r13 = r18
            if (r43 == 0) goto L_0x0055
            r19 = r11
            r10 = 3
            goto L_0x0059
        L_0x0055:
            r10 = r46
            r19 = r11
        L_0x0059:
            int r11 = r34.ordinal()
            r5 = 1
            r20 = r6
            if (r11 == 0) goto L_0x006e
            if (r11 == r5) goto L_0x006e
            r5 = 2
            if (r11 == r5) goto L_0x0068
            goto L_0x006e
        L_0x0068:
            r5 = 4
            if (r10 != r5) goto L_0x006c
            goto L_0x006f
        L_0x006c:
            r11 = 1
            goto L_0x0070
        L_0x006e:
            r5 = 4
        L_0x006f:
            r11 = 0
        L_0x0070:
            int r5 = r0.mVisibility
            r6 = 8
            if (r5 != r6) goto L_0x0079
            r5 = 0
            r11 = 0
            goto L_0x007b
        L_0x0079:
            r5 = r39
        L_0x007b:
            if (r51 == 0) goto L_0x0098
            if (r16 != 0) goto L_0x0089
            if (r17 != 0) goto L_0x0089
            if (r2 != 0) goto L_0x0089
            r6 = r38
            r9.addEquality(r15, r6)
            goto L_0x0098
        L_0x0089:
            if (r16 == 0) goto L_0x0098
            if (r17 != 0) goto L_0x0098
            int r6 = r36.getMargin()
            r23 = r2
            r2 = 7
            r9.addEquality(r15, r7, r6, r2)
            goto L_0x009b
        L_0x0098:
            r23 = r2
            r2 = 7
        L_0x009b:
            if (r11 != 0) goto L_0x00ba
            if (r35 == 0) goto L_0x00b4
            r2 = 0
            r6 = 3
            r9.addEquality(r8, r15, r2, r6)
            r2 = 7
            if (r14 <= 0) goto L_0x00aa
            r9.addGreaterThan(r8, r15, r14, r2)
        L_0x00aa:
            r5 = 2147483647(0x7fffffff, float:NaN)
            if (r1 >= r5) goto L_0x011e
            r9.addLowerThan(r8, r15, r1, r2)
            goto L_0x011e
        L_0x00b4:
            r6 = 3
            r9.addEquality(r8, r15, r5, r2)
            goto L_0x011e
        L_0x00ba:
            r1 = 2
            r6 = 3
            if (r13 == r1) goto L_0x00d5
            if (r43 != 0) goto L_0x00d5
            r1 = 1
            if (r10 == r1) goto L_0x00c5
            if (r10 != 0) goto L_0x00d5
        L_0x00c5:
            int r1 = java.lang.Math.max(r3, r5)
            if (r4 <= 0) goto L_0x00cf
            int r1 = java.lang.Math.min(r4, r1)
        L_0x00cf:
            r2 = 7
            r9.addEquality(r8, r15, r1, r2)
            r11 = 0
            goto L_0x011e
        L_0x00d5:
            r1 = -2
            if (r3 != r1) goto L_0x00d9
            r3 = r5
        L_0x00d9:
            if (r4 != r1) goto L_0x00dc
            r4 = r5
        L_0x00dc:
            if (r5 <= 0) goto L_0x00e2
            r1 = 1
            if (r10 == r1) goto L_0x00e2
            r5 = 0
        L_0x00e2:
            if (r3 <= 0) goto L_0x00ec
            r1 = 7
            r9.addGreaterThan(r8, r15, r3, r1)
            int r5 = java.lang.Math.max(r5, r3)
        L_0x00ec:
            if (r4 <= 0) goto L_0x0103
            if (r29 == 0) goto L_0x00f5
            r1 = 1
            if (r10 != r1) goto L_0x00f5
            r1 = 0
            goto L_0x00f6
        L_0x00f5:
            r1 = 1
        L_0x00f6:
            if (r1 == 0) goto L_0x00fd
            r1 = 7
            r9.addLowerThan(r8, r15, r4, r1)
            goto L_0x00fe
        L_0x00fd:
            r1 = 7
        L_0x00fe:
            int r5 = java.lang.Math.min(r5, r4)
            goto L_0x0104
        L_0x0103:
            r1 = 7
        L_0x0104:
            r2 = 1
            if (r10 != r2) goto L_0x0127
            if (r29 == 0) goto L_0x010d
            r9.addEquality(r8, r15, r5, r1)
            goto L_0x011e
        L_0x010d:
            if (r44 == 0) goto L_0x0117
            r2 = 5
            r9.addEquality(r8, r15, r5, r2)
            r9.addLowerThan(r8, r15, r5, r1)
            goto L_0x011e
        L_0x0117:
            r2 = 5
            r9.addEquality(r8, r15, r5, r2)
            r9.addLowerThan(r8, r15, r5, r1)
        L_0x011e:
            r20 = r3
            r24 = r11
            r3 = 2
            r11 = r31
            goto L_0x0196
        L_0x0127:
            r1 = 2
            if (r10 != r1) goto L_0x018e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r2 = r12.mType
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.TOP
            r11 = r20
            if (r2 == r5) goto L_0x014e
            if (r2 != r11) goto L_0x0135
            goto L_0x014e
        L_0x0135:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r5 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.LEFT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.getAnchor(r5)
            androidx.constraintlayout.solver.SolverVariable r2 = r9.createObjectVariable(r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r0.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor$Type r11 = androidx.constraintlayout.solver.widgets.ConstraintAnchor.Type.RIGHT
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.getAnchor(r11)
            androidx.constraintlayout.solver.SolverVariable r5 = r9.createObjectVariable(r5)
            goto L_0x0162
        L_0x014e:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r0.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.getAnchor(r5)
            androidx.constraintlayout.solver.SolverVariable r2 = r9.createObjectVariable(r2)
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r0.mParent
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.getAnchor(r11)
            androidx.constraintlayout.solver.SolverVariable r5 = r9.createObjectVariable(r5)
        L_0x0162:
            androidx.constraintlayout.solver.ArrayRow r11 = r27.createRow()
            androidx.constraintlayout.solver.ArrayLinkedVariables r1 = r11.variables
            r6 = -1082130432(0xffffffffbf800000, float:-1.0)
            r1.put(r8, r6)
            androidx.constraintlayout.solver.ArrayLinkedVariables r1 = r11.variables
            r6 = 1065353216(0x3f800000, float:1.0)
            r1.put(r15, r6)
            androidx.constraintlayout.solver.ArrayLinkedVariables r1 = r11.variables
            r6 = r50
            r34 = r3
            r3 = 2
            r1.put(r5, r6)
            androidx.constraintlayout.solver.ArrayLinkedVariables r1 = r11.variables
            float r5 = -r6
            r1.put(r2, r5)
            r9.addConstraint(r11)
            r11 = r31
            r20 = r34
            r24 = 0
            goto L_0x0196
        L_0x018e:
            r34 = r3
            r3 = r1
            r20 = r34
            r24 = r11
            r11 = 1
        L_0x0196:
            if (r51 == 0) goto L_0x037d
            if (r44 == 0) goto L_0x01a7
            r1 = r32
            r4 = r33
            r6 = r3
            r12 = r8
            r31 = r11
            r5 = r13
            r2 = 7
            r3 = 0
            goto L_0x0388
        L_0x01a7:
            if (r16 != 0) goto L_0x01ae
            if (r17 != 0) goto L_0x01ae
            if (r23 != 0) goto L_0x01ae
            goto L_0x01b2
        L_0x01ae:
            if (r16 == 0) goto L_0x01ba
            if (r17 != 0) goto L_0x01ba
        L_0x01b2:
            r12 = r8
            r31 = r11
            r13 = r19
        L_0x01b7:
            r0 = 5
            goto L_0x035c
        L_0x01ba:
            if (r16 != 0) goto L_0x01d8
            if (r17 == 0) goto L_0x01d8
            int r0 = r37.getMargin()
            int r0 = -r0
            r13 = r19
            r6 = 7
            r9.addEquality(r8, r13, r0, r6)
            if (r29 == 0) goto L_0x01d4
            r1 = r32
            r2 = 0
            r5 = 5
            r9.addGreaterThan(r15, r1, r2, r5)
            goto L_0x035e
        L_0x01d4:
            r12 = r8
            r31 = r11
            goto L_0x01b7
        L_0x01d8:
            r1 = r32
            r13 = r19
            r2 = 0
            r3 = 3
            r5 = 5
            r6 = 7
            if (r16 == 0) goto L_0x035e
            if (r17 == 0) goto L_0x035e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r12.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r2.mOwner
            r3 = r37
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r3.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.mOwner
            androidx.constraintlayout.solver.widgets.ConstraintWidget r12 = r0.mParent
            if (r24 == 0) goto L_0x026e
            if (r10 != 0) goto L_0x0218
            if (r4 != 0) goto L_0x01ff
            if (r20 != 0) goto L_0x01ff
            r0 = r6
            r4 = r0
            r16 = 0
            r17 = 1
            goto L_0x0205
        L_0x01ff:
            r0 = 5
            r4 = 5
            r16 = 1
            r17 = 0
        L_0x0205:
            boolean r6 = r2 instanceof androidx.constraintlayout.solver.widgets.Barrier
            if (r6 != 0) goto L_0x020d
            boolean r6 = r5 instanceof androidx.constraintlayout.solver.widgets.Barrier
            if (r6 == 0) goto L_0x020e
        L_0x020d:
            r4 = 4
        L_0x020e:
            r6 = r4
            r4 = r16
            r19 = 5
            r16 = r0
            r0 = 0
            goto L_0x0278
        L_0x0218:
            r6 = 1
            if (r10 != r6) goto L_0x0220
            r0 = 1
            r4 = 1
            r6 = 7
            goto L_0x0271
        L_0x0220:
            r6 = 3
            if (r10 != r6) goto L_0x026b
            int r0 = r0.mResolvedDimensionRatioSide
            r6 = -1
            if (r0 != r6) goto L_0x0237
            if (r45 == 0) goto L_0x0233
            if (r29 == 0) goto L_0x022f
        L_0x022c:
            r0 = 7
            r4 = 5
            goto L_0x0260
        L_0x022f:
            r0 = 7
            r4 = 5
            r6 = 4
            goto L_0x0261
        L_0x0233:
            r0 = 7
            r4 = 5
            r6 = 7
            goto L_0x0261
        L_0x0237:
            if (r43 == 0) goto L_0x024a
            r0 = r47
            r6 = 2
            if (r0 == r6) goto L_0x0244
            r4 = 1
            if (r0 != r4) goto L_0x0242
            goto L_0x0244
        L_0x0242:
            r0 = 0
            goto L_0x0245
        L_0x0244:
            r0 = 1
        L_0x0245:
            if (r0 != 0) goto L_0x0248
            goto L_0x022c
        L_0x0248:
            r0 = 5
            goto L_0x025b
        L_0x024a:
            if (r4 <= 0) goto L_0x024e
            r0 = 5
            goto L_0x025e
        L_0x024e:
            if (r4 != 0) goto L_0x025d
            if (r20 != 0) goto L_0x025d
            if (r45 != 0) goto L_0x0256
            r0 = 7
            goto L_0x025e
        L_0x0256:
            if (r2 == r12) goto L_0x0248
            if (r5 == r12) goto L_0x0248
            r0 = 4
        L_0x025b:
            r4 = 4
            goto L_0x0260
        L_0x025d:
            r0 = 4
        L_0x025e:
            r4 = r0
            r0 = 5
        L_0x0260:
            r6 = 5
        L_0x0261:
            r16 = r0
            r19 = r6
            r0 = 1
            r17 = 1
            r6 = r4
            r4 = 1
            goto L_0x0278
        L_0x026b:
            r0 = 0
            r4 = 0
            goto L_0x0270
        L_0x026e:
            r0 = 1
            r4 = 1
        L_0x0270:
            r6 = 5
        L_0x0271:
            r16 = r6
            r6 = 4
            r17 = 0
            r19 = 5
        L_0x0278:
            if (r0 == 0) goto L_0x0283
            if (r7 != r13) goto L_0x0283
            if (r2 == r12) goto L_0x0283
            r21 = 0
            r23 = 0
            goto L_0x0287
        L_0x0283:
            r21 = r0
            r23 = 1
        L_0x0287:
            if (r4 == 0) goto L_0x02b4
            int r4 = r36.getMargin()
            int r25 = r37.getMargin()
            r0 = r27
            r31 = r11
            r11 = r1
            r1 = r15
            r46 = r10
            r22 = 0
            r10 = r2
            r2 = r7
            r3 = r4
            r4 = r42
            r14 = r5
            r18 = 5
            r5 = r13
            r11 = r6
            r6 = r8
            r18 = r11
            r11 = r7
            r7 = r25
            r22 = r12
            r12 = r8
            r8 = r19
            r0.addCentering(r1, r2, r3, r4, r5, r6, r7, r8)
            goto L_0x02c0
        L_0x02b4:
            r14 = r5
            r18 = r6
            r46 = r10
            r31 = r11
            r22 = r12
            r10 = r2
            r11 = r7
            r12 = r8
        L_0x02c0:
            r0 = 6
            if (r21 == 0) goto L_0x02e5
            if (r29 == 0) goto L_0x02d3
            if (r11 == r13) goto L_0x02d3
            if (r24 != 0) goto L_0x02d3
            boolean r1 = r10 instanceof androidx.constraintlayout.solver.widgets.Barrier
            if (r1 != 0) goto L_0x02d1
            boolean r1 = r14 instanceof androidx.constraintlayout.solver.widgets.Barrier
            if (r1 == 0) goto L_0x02d3
        L_0x02d1:
            r1 = r0
            goto L_0x02d5
        L_0x02d3:
            r1 = r16
        L_0x02d5:
            int r2 = r36.getMargin()
            r9.addGreaterThan(r15, r11, r2, r1)
            int r2 = r37.getMargin()
            int r2 = -r2
            r9.addLowerThan(r12, r13, r2, r1)
            goto L_0x02e7
        L_0x02e5:
            r1 = r16
        L_0x02e7:
            if (r23 == 0) goto L_0x032e
            if (r17 == 0) goto L_0x0316
            if (r45 == 0) goto L_0x02ef
            if (r30 == 0) goto L_0x0316
        L_0x02ef:
            r2 = r22
            if (r10 == r2) goto L_0x02f9
            if (r14 != r2) goto L_0x02f6
            goto L_0x02f9
        L_0x02f6:
            r6 = r18
            goto L_0x02fa
        L_0x02f9:
            r6 = r0
        L_0x02fa:
            boolean r0 = r10 instanceof androidx.constraintlayout.solver.widgets.Guideline
            if (r0 != 0) goto L_0x0302
            boolean r0 = r14 instanceof androidx.constraintlayout.solver.widgets.Guideline
            if (r0 == 0) goto L_0x0303
        L_0x0302:
            r6 = 5
        L_0x0303:
            boolean r0 = r10 instanceof androidx.constraintlayout.solver.widgets.Barrier
            if (r0 != 0) goto L_0x030b
            boolean r0 = r14 instanceof androidx.constraintlayout.solver.widgets.Barrier
            if (r0 == 0) goto L_0x030c
        L_0x030b:
            r6 = 5
        L_0x030c:
            r4 = r18
            if (r45 == 0) goto L_0x0311
            r6 = 5
        L_0x0311:
            int r6 = java.lang.Math.max(r6, r4)
            goto L_0x0319
        L_0x0316:
            r4 = r18
            r6 = r4
        L_0x0319:
            if (r29 == 0) goto L_0x031f
            int r6 = java.lang.Math.min(r1, r6)
        L_0x031f:
            int r0 = r36.getMargin()
            r9.addEquality(r15, r11, r0, r6)
            int r0 = r37.getMargin()
            int r0 = -r0
            r9.addEquality(r12, r13, r0, r6)
        L_0x032e:
            if (r29 == 0) goto L_0x0341
            r1 = r32
            if (r1 != r11) goto L_0x0339
            int r6 = r36.getMargin()
            goto L_0x033a
        L_0x0339:
            r6 = 0
        L_0x033a:
            if (r11 == r1) goto L_0x0341
            r0 = 5
            r9.addGreaterThan(r15, r1, r6, r0)
            goto L_0x0342
        L_0x0341:
            r0 = 5
        L_0x0342:
            if (r29 == 0) goto L_0x035c
            if (r24 == 0) goto L_0x035c
            if (r40 != 0) goto L_0x035c
            if (r20 != 0) goto L_0x035c
            if (r24 == 0) goto L_0x0357
            r10 = r46
            r1 = 3
            if (r10 != r1) goto L_0x0357
            r2 = 7
            r3 = 0
            r9.addGreaterThan(r12, r15, r3, r2)
            goto L_0x0363
        L_0x0357:
            r3 = 0
            r9.addGreaterThan(r12, r15, r3, r0)
            goto L_0x0363
        L_0x035c:
            r3 = 0
            goto L_0x0363
        L_0x035e:
            r3 = r2
            r0 = r5
            r12 = r8
            r31 = r11
        L_0x0363:
            if (r29 == 0) goto L_0x037c
            if (r31 == 0) goto L_0x037c
            r1 = r37
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x0374
            int r6 = r37.getMargin()
            r4 = r33
            goto L_0x0377
        L_0x0374:
            r4 = r33
            r6 = r3
        L_0x0377:
            if (r13 == r4) goto L_0x037c
            r9.addGreaterThan(r4, r12, r6, r0)
        L_0x037c:
            return
        L_0x037d:
            r1 = r32
            r4 = r33
            r12 = r8
            r31 = r11
            r5 = r13
            r2 = 7
            r3 = 0
            r6 = 2
        L_0x0388:
            if (r5 >= r6) goto L_0x03c5
            if (r29 == 0) goto L_0x03c5
            if (r31 == 0) goto L_0x03c5
            r9.addGreaterThan(r15, r1, r3, r2)
            if (r28 != 0) goto L_0x039c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r0.mBaseline
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 != 0) goto L_0x039a
            goto L_0x039c
        L_0x039a:
            r6 = r3
            goto L_0x039d
        L_0x039c:
            r6 = 1
        L_0x039d:
            if (r28 != 0) goto L_0x03bf
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mBaseline
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            if (r0 == 0) goto L_0x03bf
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r0.mOwner
            float r1 = r0.mDimensionRatio
            r5 = 0
            int r1 = (r1 > r5 ? 1 : (r1 == r5 ? 0 : -1))
            if (r1 == 0) goto L_0x03bd
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r0 = r0.mListDimensionBehaviors
            r1 = r0[r3]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r5) goto L_0x03bd
            r1 = 1
            r0 = r0[r1]
            if (r0 != r5) goto L_0x03bd
            r5 = r1
            goto L_0x03c0
        L_0x03bd:
            r5 = r3
            goto L_0x03c0
        L_0x03bf:
            r5 = r6
        L_0x03c0:
            if (r5 == 0) goto L_0x03c5
            r9.addGreaterThan(r4, r12, r3, r2)
        L_0x03c5:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidget.applyConstraints(androidx.constraintlayout.solver.LinearSystem, boolean, boolean, boolean, boolean, androidx.constraintlayout.solver.SolverVariable, androidx.constraintlayout.solver.SolverVariable, androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour, boolean, androidx.constraintlayout.solver.widgets.ConstraintAnchor, androidx.constraintlayout.solver.widgets.ConstraintAnchor, int, int, int, int, float, boolean, boolean, boolean, int, int, int, int, float, boolean):void");
    }

    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        ConstraintWidget constraintWidget2;
        ConstraintWidget constraintWidget3;
        this.mHorizontalResolution = constraintWidget.mHorizontalResolution;
        this.mVerticalResolution = constraintWidget.mVerticalResolution;
        this.mMatchConstraintDefaultWidth = constraintWidget.mMatchConstraintDefaultWidth;
        this.mMatchConstraintDefaultHeight = constraintWidget.mMatchConstraintDefaultHeight;
        int[] iArr = this.mResolvedMatchConstraintDefault;
        int[] iArr2 = constraintWidget.mResolvedMatchConstraintDefault;
        iArr[0] = iArr2[0];
        iArr[1] = iArr2[1];
        this.mMatchConstraintMinWidth = constraintWidget.mMatchConstraintMinWidth;
        this.mMatchConstraintMaxWidth = constraintWidget.mMatchConstraintMaxWidth;
        this.mMatchConstraintMinHeight = constraintWidget.mMatchConstraintMinHeight;
        this.mMatchConstraintMaxHeight = constraintWidget.mMatchConstraintMaxHeight;
        this.mMatchConstraintPercentHeight = constraintWidget.mMatchConstraintPercentHeight;
        this.mResolvedDimensionRatioSide = constraintWidget.mResolvedDimensionRatioSide;
        this.mResolvedDimensionRatio = constraintWidget.mResolvedDimensionRatio;
        int[] iArr3 = constraintWidget.mMaxDimension;
        this.mMaxDimension = Arrays.copyOf(iArr3, iArr3.length);
        this.mCircleConstraintAngle = constraintWidget.mCircleConstraintAngle;
        this.hasBaseline = constraintWidget.hasBaseline;
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mListDimensionBehaviors = (DimensionBehaviour[]) Arrays.copyOf(this.mListDimensionBehaviors, 2);
        ConstraintWidget constraintWidget4 = null;
        if (this.mParent == null) {
            constraintWidget2 = null;
        } else {
            constraintWidget2 = hashMap.get(constraintWidget.mParent);
        }
        this.mParent = constraintWidget2;
        this.mWidth = constraintWidget.mWidth;
        this.mHeight = constraintWidget.mHeight;
        this.mDimensionRatio = constraintWidget.mDimensionRatio;
        this.mDimensionRatioSide = constraintWidget.mDimensionRatioSide;
        this.f15mX = constraintWidget.f15mX;
        this.f16mY = constraintWidget.f16mY;
        this.mBaselineDistance = constraintWidget.mBaselineDistance;
        this.mMinWidth = constraintWidget.mMinWidth;
        this.mMinHeight = constraintWidget.mMinHeight;
        this.mHorizontalBiasPercent = constraintWidget.mHorizontalBiasPercent;
        this.mVerticalBiasPercent = constraintWidget.mVerticalBiasPercent;
        this.mCompanionWidget = constraintWidget.mCompanionWidget;
        this.mVisibility = constraintWidget.mVisibility;
        this.mDebugName = constraintWidget.mDebugName;
        this.mType = constraintWidget.mType;
        this.mHorizontalChainStyle = constraintWidget.mHorizontalChainStyle;
        this.mVerticalChainStyle = constraintWidget.mVerticalChainStyle;
        float[] fArr = this.mWeight;
        float[] fArr2 = constraintWidget.mWeight;
        fArr[0] = fArr2[0];
        fArr[1] = fArr2[1];
        ConstraintWidget[] constraintWidgetArr = this.mListNextMatchConstraintsWidget;
        ConstraintWidget[] constraintWidgetArr2 = constraintWidget.mListNextMatchConstraintsWidget;
        constraintWidgetArr[0] = constraintWidgetArr2[0];
        constraintWidgetArr[1] = constraintWidgetArr2[1];
        ConstraintWidget[] constraintWidgetArr3 = this.mNextChainWidget;
        ConstraintWidget[] constraintWidgetArr4 = constraintWidget.mNextChainWidget;
        constraintWidgetArr3[0] = constraintWidgetArr4[0];
        constraintWidgetArr3[1] = constraintWidgetArr4[1];
        ConstraintWidget constraintWidget5 = constraintWidget.mHorizontalNextWidget;
        if (constraintWidget5 == null) {
            constraintWidget3 = null;
        } else {
            constraintWidget3 = hashMap.get(constraintWidget5);
        }
        this.mHorizontalNextWidget = constraintWidget3;
        ConstraintWidget constraintWidget6 = constraintWidget.mVerticalNextWidget;
        if (constraintWidget6 != null) {
            constraintWidget4 = hashMap.get(constraintWidget6);
        }
        this.mVerticalNextWidget = constraintWidget4;
    }

    public final void createObjectVariables(LinearSystem linearSystem) {
        linearSystem.createObjectVariable(this.mLeft);
        linearSystem.createObjectVariable(this.mTop);
        linearSystem.createObjectVariable(this.mRight);
        linearSystem.createObjectVariable(this.mBottom);
        if (this.mBaselineDistance > 0) {
            linearSystem.createObjectVariable(this.mBaseline);
        }
    }

    public final DimensionBehaviour getDimensionBehaviour(int i) {
        if (i == 0) {
            return this.mListDimensionBehaviors[0];
        }
        if (i == 1) {
            return this.mListDimensionBehaviors[1];
        }
        return null;
    }

    public final int getHeight() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mHeight;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        r1 = r1.mBottom;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final androidx.constraintlayout.solver.widgets.ConstraintWidget getNextChainMember(int r2) {
        /*
            r1 = this;
            if (r2 != 0) goto L_0x000f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x001f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r2.mTarget
            if (r0 != r1) goto L_0x001f
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r2.mOwner
            return r1
        L_0x000f:
            r0 = 1
            if (r2 != r0) goto L_0x001f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mBottom
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x001f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r2.mTarget
            if (r0 != r1) goto L_0x001f
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r2.mOwner
            return r1
        L_0x001f:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidget.getNextChainMember(int):androidx.constraintlayout.solver.widgets.ConstraintWidget");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:9:0x0012, code lost:
        r1 = r1.mTop;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final androidx.constraintlayout.solver.widgets.ConstraintWidget getPreviousChainMember(int r2) {
        /*
            r1 = this;
            if (r2 != 0) goto L_0x000f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x001f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r2.mTarget
            if (r0 != r1) goto L_0x001f
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r2.mOwner
            return r1
        L_0x000f:
            r0 = 1
            if (r2 != r0) goto L_0x001f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x001f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r2.mTarget
            if (r0 != r1) goto L_0x001f
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r2.mOwner
            return r1
        L_0x001f:
            r1 = 0
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.ConstraintWidget.getPreviousChainMember(int):androidx.constraintlayout.solver.widgets.ConstraintWidget");
    }

    public final int getWidth() {
        if (this.mVisibility == 8) {
            return 0;
        }
        return this.mWidth;
    }

    public final int getX() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) {
            return this.f15mX;
        }
        return ((ConstraintWidgetContainer) constraintWidget).mPaddingLeft + this.f15mX;
    }

    public final int getY() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget == null || !(constraintWidget instanceof ConstraintWidgetContainer)) {
            return this.f16mY;
        }
        return ((ConstraintWidgetContainer) constraintWidget).mPaddingTop + this.f16mY;
    }

    public final boolean isChainHead(int i) {
        int i2 = i * 2;
        ConstraintAnchor[] constraintAnchorArr = this.mListAnchors;
        if (!(constraintAnchorArr[i2].mTarget == null || constraintAnchorArr[i2].mTarget.mTarget == constraintAnchorArr[i2])) {
            int i3 = i2 + 1;
            if (constraintAnchorArr[i3].mTarget == null || constraintAnchorArr[i3].mTarget.mTarget != constraintAnchorArr[i3]) {
                return false;
            }
            return true;
        }
        return false;
    }

    public final boolean isInHorizontalChain() {
        ConstraintAnchor constraintAnchor = this.mLeft;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mRight;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return false;
        }
        return true;
    }

    public final boolean isInVerticalChain() {
        ConstraintAnchor constraintAnchor = this.mTop;
        ConstraintAnchor constraintAnchor2 = constraintAnchor.mTarget;
        if (constraintAnchor2 != null && constraintAnchor2.mTarget == constraintAnchor) {
            return true;
        }
        ConstraintAnchor constraintAnchor3 = this.mBottom;
        ConstraintAnchor constraintAnchor4 = constraintAnchor3.mTarget;
        if (constraintAnchor4 == null || constraintAnchor4.mTarget != constraintAnchor3) {
            return false;
        }
        return true;
    }

    public void reset() {
        this.mLeft.reset();
        this.mTop.reset();
        this.mRight.reset();
        this.mBottom.reset();
        this.mBaseline.reset();
        this.mCenterX.reset();
        this.mCenterY.reset();
        this.mCenter.reset();
        this.mParent = null;
        this.mCircleConstraintAngle = 0.0f;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f15mX = 0;
        this.f16mY = 0;
        this.mBaselineDistance = 0;
        this.mMinWidth = 0;
        this.mMinHeight = 0;
        this.mHorizontalBiasPercent = 0.5f;
        this.mVerticalBiasPercent = 0.5f;
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        dimensionBehaviourArr[0] = dimensionBehaviour;
        dimensionBehaviourArr[1] = dimensionBehaviour;
        this.mCompanionWidget = null;
        this.mVisibility = 0;
        this.mType = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        float[] fArr = this.mWeight;
        fArr[0] = -1.0f;
        fArr[1] = -1.0f;
        this.mHorizontalResolution = -1;
        this.mVerticalResolution = -1;
        int[] iArr = this.mMaxDimension;
        iArr[0] = Integer.MAX_VALUE;
        iArr[1] = Integer.MAX_VALUE;
        this.mMatchConstraintDefaultWidth = 0;
        this.mMatchConstraintDefaultHeight = 0;
        this.mMatchConstraintPercentWidth = 1.0f;
        this.mMatchConstraintPercentHeight = 1.0f;
        this.mMatchConstraintMaxWidth = Integer.MAX_VALUE;
        this.mMatchConstraintMaxHeight = Integer.MAX_VALUE;
        this.mMatchConstraintMinWidth = 0;
        this.mMatchConstraintMinHeight = 0;
        this.mResolvedDimensionRatioSide = -1;
        this.mResolvedDimensionRatio = 1.0f;
        boolean[] zArr = this.isTerminalWidget;
        zArr[0] = true;
        zArr[1] = true;
    }

    public final void resetAnchors() {
        ConstraintWidget constraintWidget = this.mParent;
        if (constraintWidget != null && (constraintWidget instanceof ConstraintWidgetContainer)) {
            Objects.requireNonNull((ConstraintWidgetContainer) constraintWidget);
        }
        int size = this.mAnchors.size();
        for (int i = 0; i < size; i++) {
            this.mAnchors.get(i).reset();
        }
    }

    public void resetSolverVariables(Cache cache) {
        this.mLeft.resetSolverVariable();
        this.mTop.resetSolverVariable();
        this.mRight.resetSolverVariable();
        this.mBottom.resetSolverVariable();
        this.mBaseline.resetSolverVariable();
        this.mCenter.resetSolverVariable();
        this.mCenterX.resetSolverVariable();
        this.mCenterY.resetSolverVariable();
    }

    public final void setHeight(int i) {
        this.mHeight = i;
        int i2 = this.mMinHeight;
        if (i < i2) {
            this.mHeight = i2;
        }
    }

    public final void setHorizontalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[0] = dimensionBehaviour;
    }

    public final void setVerticalDimensionBehaviour(DimensionBehaviour dimensionBehaviour) {
        this.mListDimensionBehaviors[1] = dimensionBehaviour;
    }

    public final void setWidth(int i) {
        this.mWidth = i;
        int i2 = this.mMinWidth;
        if (i < i2) {
            this.mWidth = i2;
        }
    }

    public final String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        String str2 = "";
        if (this.mType != null) {
            str = MotionController$$ExternalSyntheticOutline1.m8m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("type: "), this.mType, " ");
        } else {
            str = str2;
        }
        sb.append(str);
        if (this.mDebugName != null) {
            str2 = MotionController$$ExternalSyntheticOutline1.m8m(VendorAtomValue$$ExternalSyntheticOutline1.m1m("id: "), this.mDebugName, " ");
        }
        sb.append(str2);
        sb.append("(");
        sb.append(this.f15mX);
        sb.append(", ");
        sb.append(this.f16mY);
        sb.append(") - (");
        sb.append(this.mWidth);
        sb.append(" x ");
        sb.append(this.mHeight);
        sb.append(")");
        return sb.toString();
    }

    public void updateFromRuns(boolean z, boolean z2) {
        int i;
        int i2;
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        HorizontalWidgetRun horizontalWidgetRun = this.horizontalRun;
        Objects.requireNonNull(horizontalWidgetRun);
        boolean z3 = z & horizontalWidgetRun.resolved;
        VerticalWidgetRun verticalWidgetRun = this.verticalRun;
        Objects.requireNonNull(verticalWidgetRun);
        boolean z4 = z2 & verticalWidgetRun.resolved;
        HorizontalWidgetRun horizontalWidgetRun2 = this.horizontalRun;
        int i3 = horizontalWidgetRun2.start.value;
        VerticalWidgetRun verticalWidgetRun2 = this.verticalRun;
        int i4 = verticalWidgetRun2.start.value;
        int i5 = horizontalWidgetRun2.end.value;
        int i6 = verticalWidgetRun2.end.value;
        int i7 = i6 - i4;
        if (i5 - i3 < 0 || i7 < 0 || i3 == Integer.MIN_VALUE || i3 == Integer.MAX_VALUE || i4 == Integer.MIN_VALUE || i4 == Integer.MAX_VALUE || i5 == Integer.MIN_VALUE || i5 == Integer.MAX_VALUE || i6 == Integer.MIN_VALUE || i6 == Integer.MAX_VALUE) {
            i5 = 0;
            i3 = 0;
            i6 = 0;
            i4 = 0;
        }
        int i8 = i5 - i3;
        int i9 = i6 - i4;
        if (z3) {
            this.f15mX = i3;
        }
        if (z4) {
            this.f16mY = i4;
        }
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        if (z3) {
            if (this.mListDimensionBehaviors[0] == dimensionBehaviour && i8 < (i2 = this.mWidth)) {
                i8 = i2;
            }
            this.mWidth = i8;
            int i10 = this.mMinWidth;
            if (i8 < i10) {
                this.mWidth = i10;
            }
        }
        if (z4) {
            if (this.mListDimensionBehaviors[1] == dimensionBehaviour && i9 < (i = this.mHeight)) {
                i9 = i;
            }
            this.mHeight = i9;
            int i11 = this.mMinHeight;
            if (i9 < i11) {
                this.mHeight = i11;
            }
        }
    }

    public void updateFromSolver(LinearSystem linearSystem) {
        int i;
        int i2;
        ConstraintAnchor constraintAnchor = this.mLeft;
        Objects.requireNonNull(linearSystem);
        int objectVariableValue = LinearSystem.getObjectVariableValue(constraintAnchor);
        int objectVariableValue2 = LinearSystem.getObjectVariableValue(this.mTop);
        int objectVariableValue3 = LinearSystem.getObjectVariableValue(this.mRight);
        int objectVariableValue4 = LinearSystem.getObjectVariableValue(this.mBottom);
        HorizontalWidgetRun horizontalWidgetRun = this.horizontalRun;
        DependencyNode dependencyNode = horizontalWidgetRun.start;
        if (dependencyNode.resolved) {
            DependencyNode dependencyNode2 = horizontalWidgetRun.end;
            if (dependencyNode2.resolved) {
                objectVariableValue = dependencyNode.value;
                objectVariableValue3 = dependencyNode2.value;
            }
        }
        VerticalWidgetRun verticalWidgetRun = this.verticalRun;
        DependencyNode dependencyNode3 = verticalWidgetRun.start;
        if (dependencyNode3.resolved) {
            DependencyNode dependencyNode4 = verticalWidgetRun.end;
            if (dependencyNode4.resolved) {
                objectVariableValue2 = dependencyNode3.value;
                objectVariableValue4 = dependencyNode4.value;
            }
        }
        int i3 = objectVariableValue4 - objectVariableValue2;
        if (objectVariableValue3 - objectVariableValue < 0 || i3 < 0 || objectVariableValue == Integer.MIN_VALUE || objectVariableValue == Integer.MAX_VALUE || objectVariableValue2 == Integer.MIN_VALUE || objectVariableValue2 == Integer.MAX_VALUE || objectVariableValue3 == Integer.MIN_VALUE || objectVariableValue3 == Integer.MAX_VALUE || objectVariableValue4 == Integer.MIN_VALUE || objectVariableValue4 == Integer.MAX_VALUE) {
            objectVariableValue = 0;
            objectVariableValue2 = 0;
            objectVariableValue3 = 0;
            objectVariableValue4 = 0;
        }
        int i4 = objectVariableValue3 - objectVariableValue;
        int i5 = objectVariableValue4 - objectVariableValue2;
        this.f15mX = objectVariableValue;
        this.f16mY = objectVariableValue2;
        if (this.mVisibility == 8) {
            this.mWidth = 0;
            this.mHeight = 0;
            return;
        }
        DimensionBehaviour[] dimensionBehaviourArr = this.mListDimensionBehaviors;
        DimensionBehaviour dimensionBehaviour = dimensionBehaviourArr[0];
        DimensionBehaviour dimensionBehaviour2 = DimensionBehaviour.FIXED;
        if (dimensionBehaviour == dimensionBehaviour2 && i4 < (i2 = this.mWidth)) {
            i4 = i2;
        }
        if (dimensionBehaviourArr[1] == dimensionBehaviour2 && i5 < (i = this.mHeight)) {
            i5 = i;
        }
        this.mWidth = i4;
        this.mHeight = i5;
        int i6 = this.mMinHeight;
        if (i5 < i6) {
            this.mHeight = i6;
        }
        int i7 = this.mMinWidth;
        if (i4 < i7) {
            this.mWidth = i7;
        }
    }

    public ConstraintWidget() {
        ConstraintAnchor constraintAnchor = new ConstraintAnchor(this, ConstraintAnchor.Type.LEFT);
        this.mLeft = constraintAnchor;
        ConstraintAnchor constraintAnchor2 = new ConstraintAnchor(this, ConstraintAnchor.Type.TOP);
        this.mTop = constraintAnchor2;
        ConstraintAnchor constraintAnchor3 = new ConstraintAnchor(this, ConstraintAnchor.Type.RIGHT);
        this.mRight = constraintAnchor3;
        ConstraintAnchor constraintAnchor4 = new ConstraintAnchor(this, ConstraintAnchor.Type.BOTTOM);
        this.mBottom = constraintAnchor4;
        ConstraintAnchor constraintAnchor5 = new ConstraintAnchor(this, ConstraintAnchor.Type.BASELINE);
        this.mBaseline = constraintAnchor5;
        this.mCenterX = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_X);
        this.mCenterY = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER_Y);
        ConstraintAnchor constraintAnchor6 = new ConstraintAnchor(this, ConstraintAnchor.Type.CENTER);
        this.mCenter = constraintAnchor6;
        this.mListAnchors = new ConstraintAnchor[]{constraintAnchor, constraintAnchor3, constraintAnchor2, constraintAnchor4, constraintAnchor5, constraintAnchor6};
        ArrayList<ConstraintAnchor> arrayList = new ArrayList<>();
        this.mAnchors = arrayList;
        DimensionBehaviour dimensionBehaviour = DimensionBehaviour.FIXED;
        this.mListDimensionBehaviors = new DimensionBehaviour[]{dimensionBehaviour, dimensionBehaviour};
        this.mParent = null;
        this.mWidth = 0;
        this.mHeight = 0;
        this.mDimensionRatio = 0.0f;
        this.mDimensionRatioSide = -1;
        this.f15mX = 0;
        this.f16mY = 0;
        this.mBaselineDistance = 0;
        this.mHorizontalBiasPercent = 0.5f;
        this.mVerticalBiasPercent = 0.5f;
        this.mVisibility = 0;
        this.mDebugName = null;
        this.mType = null;
        this.mHorizontalChainStyle = 0;
        this.mVerticalChainStyle = 0;
        this.mWeight = new float[]{-1.0f, -1.0f};
        this.mListNextMatchConstraintsWidget = new ConstraintWidget[]{null, null};
        this.mNextChainWidget = new ConstraintWidget[]{null, null};
        this.mHorizontalNextWidget = null;
        this.mVerticalNextWidget = null;
        arrayList.add(this.mLeft);
        this.mAnchors.add(this.mTop);
        this.mAnchors.add(this.mRight);
        this.mAnchors.add(this.mBottom);
        this.mAnchors.add(this.mCenterX);
        this.mAnchors.add(this.mCenterY);
        this.mAnchors.add(this.mCenter);
        this.mAnchors.add(this.mBaseline);
    }

    public ConstraintAnchor getAnchor(ConstraintAnchor.Type type) {
        switch (type.ordinal()) {
            case 0:
                return null;
            case 1:
                return this.mLeft;
            case 2:
                return this.mTop;
            case 3:
                return this.mRight;
            case 4:
                return this.mBottom;
            case 5:
                return this.mBaseline;
            case FalsingManager.VERSION /*6*/:
                return this.mCenter;
            case 7:
                return this.mCenterX;
            case 8:
                return this.mCenterY;
            default:
                throw new AssertionError(type.name());
        }
    }

    public final void immediateConnect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i, int i2) {
        getAnchor(type).connect(constraintWidget.getAnchor(type2), i, i2, true);
    }

    public final void connect(ConstraintAnchor.Type type, ConstraintWidget constraintWidget, ConstraintAnchor.Type type2, int i) {
        boolean z;
        ConstraintAnchor.Type type3 = ConstraintAnchor.Type.CENTER_Y;
        ConstraintAnchor.Type type4 = ConstraintAnchor.Type.CENTER_X;
        ConstraintAnchor.Type type5 = ConstraintAnchor.Type.LEFT;
        ConstraintAnchor.Type type6 = ConstraintAnchor.Type.TOP;
        ConstraintAnchor.Type type7 = ConstraintAnchor.Type.RIGHT;
        ConstraintAnchor.Type type8 = ConstraintAnchor.Type.BOTTOM;
        ConstraintAnchor.Type type9 = ConstraintAnchor.Type.CENTER;
        if (type == type9) {
            if (type2 == type9) {
                ConstraintAnchor anchor = getAnchor(type5);
                ConstraintAnchor anchor2 = getAnchor(type7);
                ConstraintAnchor anchor3 = getAnchor(type6);
                ConstraintAnchor anchor4 = getAnchor(type8);
                boolean z2 = true;
                if ((anchor == null || !anchor.isConnected()) && (anchor2 == null || !anchor2.isConnected())) {
                    connect(type5, constraintWidget, type5, 0);
                    connect(type7, constraintWidget, type7, 0);
                    z = true;
                } else {
                    z = false;
                }
                if ((anchor3 == null || !anchor3.isConnected()) && (anchor4 == null || !anchor4.isConnected())) {
                    connect(type6, constraintWidget, type6, 0);
                    connect(type8, constraintWidget, type8, 0);
                } else {
                    z2 = false;
                }
                if (z && z2) {
                    getAnchor(type9).connect(constraintWidget.getAnchor(type9), 0);
                } else if (z) {
                    getAnchor(type4).connect(constraintWidget.getAnchor(type4), 0);
                } else if (z2) {
                    getAnchor(type3).connect(constraintWidget.getAnchor(type3), 0);
                }
            } else if (type2 == type5 || type2 == type7) {
                connect(type5, constraintWidget, type2, 0);
                connect(type7, constraintWidget, type2, 0);
                getAnchor(type9).connect(constraintWidget.getAnchor(type2), 0);
            } else if (type2 == type6 || type2 == type8) {
                connect(type6, constraintWidget, type2, 0);
                connect(type8, constraintWidget, type2, 0);
                getAnchor(type9).connect(constraintWidget.getAnchor(type2), 0);
            }
        } else if (type == type4 && (type2 == type5 || type2 == type7)) {
            ConstraintAnchor anchor5 = getAnchor(type5);
            ConstraintAnchor anchor6 = constraintWidget.getAnchor(type2);
            ConstraintAnchor anchor7 = getAnchor(type7);
            anchor5.connect(anchor6, 0);
            anchor7.connect(anchor6, 0);
            getAnchor(type4).connect(anchor6, 0);
        } else if (type == type3 && (type2 == type6 || type2 == type8)) {
            ConstraintAnchor anchor8 = constraintWidget.getAnchor(type2);
            getAnchor(type6).connect(anchor8, 0);
            getAnchor(type8).connect(anchor8, 0);
            getAnchor(type3).connect(anchor8, 0);
        } else if (type == type4 && type2 == type4) {
            getAnchor(type5).connect(constraintWidget.getAnchor(type5), 0);
            getAnchor(type7).connect(constraintWidget.getAnchor(type7), 0);
            getAnchor(type4).connect(constraintWidget.getAnchor(type2), 0);
        } else if (type == type3 && type2 == type3) {
            getAnchor(type6).connect(constraintWidget.getAnchor(type6), 0);
            getAnchor(type8).connect(constraintWidget.getAnchor(type8), 0);
            getAnchor(type3).connect(constraintWidget.getAnchor(type2), 0);
        } else {
            ConstraintAnchor anchor9 = getAnchor(type);
            ConstraintAnchor anchor10 = constraintWidget.getAnchor(type2);
            if (anchor9.isValidConnection(anchor10)) {
                ConstraintAnchor.Type type10 = ConstraintAnchor.Type.BASELINE;
                if (type == type10) {
                    ConstraintAnchor anchor11 = getAnchor(type6);
                    ConstraintAnchor anchor12 = getAnchor(type8);
                    if (anchor11 != null) {
                        anchor11.reset();
                    }
                    if (anchor12 != null) {
                        anchor12.reset();
                    }
                    i = 0;
                } else if (type == type6 || type == type8) {
                    ConstraintAnchor anchor13 = getAnchor(type10);
                    if (anchor13 != null) {
                        anchor13.reset();
                    }
                    ConstraintAnchor anchor14 = getAnchor(type9);
                    Objects.requireNonNull(anchor14);
                    if (anchor14.mTarget != anchor10) {
                        anchor14.reset();
                    }
                    ConstraintAnchor opposite = getAnchor(type).getOpposite();
                    ConstraintAnchor anchor15 = getAnchor(type3);
                    if (anchor15.isConnected()) {
                        opposite.reset();
                        anchor15.reset();
                    }
                } else if (type == type5 || type == type7) {
                    ConstraintAnchor anchor16 = getAnchor(type9);
                    Objects.requireNonNull(anchor16);
                    if (anchor16.mTarget != anchor10) {
                        anchor16.reset();
                    }
                    ConstraintAnchor opposite2 = getAnchor(type).getOpposite();
                    ConstraintAnchor anchor17 = getAnchor(type4);
                    if (anchor17.isConnected()) {
                        opposite2.reset();
                        anchor17.reset();
                    }
                }
                anchor9.connect(anchor10, i);
            }
        }
    }
}
