package androidx.constraintlayout.solver.widgets;

public final class Chain {
    /* JADX WARNING: Code restructure failed: missing block: B:163:0x0286, code lost:
        if (r3[r16].mTarget.mOwner == r9) goto L_0x028a;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x0114, code lost:
        if (r4[r2].mTarget.mOwner == r5) goto L_0x0118;
     */
    /* JADX WARNING: Removed duplicated region for block: B:119:0x01c7  */
    /* JADX WARNING: Removed duplicated region for block: B:171:0x02a5  */
    /* JADX WARNING: Removed duplicated region for block: B:192:0x0307  */
    /* JADX WARNING: Removed duplicated region for block: B:201:0x0332  */
    /* JADX WARNING: Removed duplicated region for block: B:202:0x0336  */
    /* JADX WARNING: Removed duplicated region for block: B:205:0x033e  */
    /* JADX WARNING: Removed duplicated region for block: B:237:0x045b A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:258:0x04c9 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:317:0x05c5  */
    /* JADX WARNING: Removed duplicated region for block: B:326:0x05df  */
    /* JADX WARNING: Removed duplicated region for block: B:327:0x05e2  */
    /* JADX WARNING: Removed duplicated region for block: B:330:0x05e8  */
    /* JADX WARNING: Removed duplicated region for block: B:373:0x06bb  */
    /* JADX WARNING: Removed duplicated region for block: B:379:0x06ee  */
    /* JADX WARNING: Removed duplicated region for block: B:388:0x0715  */
    /* JADX WARNING: Removed duplicated region for block: B:389:0x0718  */
    /* JADX WARNING: Removed duplicated region for block: B:392:0x071e  */
    /* JADX WARNING: Removed duplicated region for block: B:393:0x0721  */
    /* JADX WARNING: Removed duplicated region for block: B:396:0x0727  */
    /* JADX WARNING: Removed duplicated region for block: B:400:0x0736  */
    /* JADX WARNING: Removed duplicated region for block: B:402:0x0739  */
    /* JADX WARNING: Removed duplicated region for block: B:425:0x05c7 A[SYNTHETIC] */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x00f4  */
    /* JADX WARNING: Removed duplicated region for block: B:61:0x0104  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x011a  */
    /* JADX WARNING: Removed duplicated region for block: B:68:0x011d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r40, androidx.constraintlayout.solver.LinearSystem r41, int r42) {
        /*
            r0 = r40
            r10 = r41
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r11 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            r12 = 2
            if (r42 != 0) goto L_0x0012
            int r1 = r0.mHorizontalChainsSize
            androidx.constraintlayout.solver.widgets.ChainHead[] r2 = r0.mHorizontalChainsArray
            r14 = r1
            r15 = r2
            r16 = 0
            goto L_0x001a
        L_0x0012:
            int r1 = r0.mVerticalChainsSize
            androidx.constraintlayout.solver.widgets.ChainHead[] r2 = r0.mVerticalChainsArray
            r14 = r1
            r15 = r2
            r16 = r12
        L_0x001a:
            r9 = 0
        L_0x001b:
            if (r9 >= r14) goto L_0x076a
            r1 = r15[r9]
            java.util.Objects.requireNonNull(r1)
            boolean r2 = r1.mDefined
            r17 = 0
            r8 = 8
            r4 = 1
            if (r2 != 0) goto L_0x015f
            int r2 = r1.mOrientation
            int r2 = r2 * r12
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r1.mFirst
            r6 = r5
            r7 = 0
        L_0x0032:
            if (r7 != 0) goto L_0x0127
            int r13 = r1.mWidgetsCount
            int r13 = r13 + r4
            r1.mWidgetsCount = r13
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r13 = r5.mNextChainWidget
            int r3 = r1.mOrientation
            r13[r3] = r17
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r13 = r5.mListNextMatchConstraintsWidget
            r13[r3] = r17
            int r13 = r5.mVisibility
            if (r13 == r8) goto L_0x00f0
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = r5.getDimensionBehaviour(r3)
            if (r3 == r11) goto L_0x004f
            int r3 = r1.mOrientation
        L_0x004f:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r5.mListAnchors
            r3 = r3[r2]
            r3.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r5.mListAnchors
            int r13 = r2 + 1
            r3 = r3[r13]
            r3.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r5.mListAnchors
            r3 = r3[r2]
            r3.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r5.mListAnchors
            r3 = r3[r13]
            r3.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r1.mFirstVisibleWidget
            if (r3 != 0) goto L_0x0073
            r1.mFirstVisibleWidget = r5
        L_0x0073:
            r1.mLastVisibleWidget = r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r3 = r5.mListDimensionBehaviors
            int r13 = r1.mOrientation
            r8 = r3[r13]
            if (r8 != r11) goto L_0x00f0
            int[] r8 = r5.mResolvedMatchConstraintDefault
            r21 = r8[r13]
            r4 = 3
            if (r21 == 0) goto L_0x008d
            r12 = r8[r13]
            if (r12 == r4) goto L_0x008d
            r12 = r8[r13]
            r4 = 2
            if (r12 != r4) goto L_0x00f0
        L_0x008d:
            int r4 = r1.mWidgetsMatchCount
            r12 = 1
            int r4 = r4 + r12
            r1.mWidgetsMatchCount = r4
            float[] r4 = r5.mWeight
            r12 = r4[r13]
            r19 = 0
            int r24 = (r12 > r19 ? 1 : (r12 == r19 ? 0 : -1))
            if (r24 <= 0) goto L_0x00a7
            r24 = r7
            float r7 = r1.mTotalWeight
            r4 = r4[r13]
            float r7 = r7 + r4
            r1.mTotalWeight = r7
            goto L_0x00a9
        L_0x00a7:
            r24 = r7
        L_0x00a9:
            int r4 = r5.mVisibility
            r7 = 8
            if (r4 == r7) goto L_0x00be
            r3 = r3[r13]
            if (r3 != r11) goto L_0x00be
            r3 = r8[r13]
            if (r3 == 0) goto L_0x00bc
            r3 = r8[r13]
            r4 = 3
            if (r3 != r4) goto L_0x00be
        L_0x00bc:
            r3 = 1
            goto L_0x00bf
        L_0x00be:
            r3 = 0
        L_0x00bf:
            if (r3 == 0) goto L_0x00dd
            r3 = 0
            int r4 = (r12 > r3 ? 1 : (r12 == r3 ? 0 : -1))
            if (r4 >= 0) goto L_0x00ca
            r3 = 1
            r1.mHasUndefinedWeights = r3
            goto L_0x00cd
        L_0x00ca:
            r3 = 1
            r1.mHasDefinedWeights = r3
        L_0x00cd:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r3 = r1.mWeightedMatchConstraintsWidgets
            if (r3 != 0) goto L_0x00d8
            java.util.ArrayList r3 = new java.util.ArrayList
            r3.<init>()
            r1.mWeightedMatchConstraintsWidgets = r3
        L_0x00d8:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r3 = r1.mWeightedMatchConstraintsWidgets
            r3.add(r5)
        L_0x00dd:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r1.mFirstMatchConstraintWidget
            if (r3 != 0) goto L_0x00e3
            r1.mFirstMatchConstraintWidget = r5
        L_0x00e3:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r1.mLastMatchConstraintWidget
            if (r3 == 0) goto L_0x00ed
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r3 = r3.mListNextMatchConstraintsWidget
            int r4 = r1.mOrientation
            r3[r4] = r5
        L_0x00ed:
            r1.mLastMatchConstraintWidget = r5
            goto L_0x00f2
        L_0x00f0:
            r24 = r7
        L_0x00f2:
            if (r6 == r5) goto L_0x00fa
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r3 = r6.mNextChainWidget
            int r4 = r1.mOrientation
            r3[r4] = r5
        L_0x00fa:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r5.mListAnchors
            int r4 = r2 + 1
            r3 = r3[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x0116
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.mOwner
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r3.mListAnchors
            r6 = r4[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 == 0) goto L_0x0116
            r4 = r4[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = r4.mOwner
            if (r4 == r5) goto L_0x0118
        L_0x0116:
            r3 = r17
        L_0x0118:
            if (r3 == 0) goto L_0x011d
            r7 = r24
            goto L_0x011f
        L_0x011d:
            r3 = r5
            r7 = 1
        L_0x011f:
            r6 = r5
            r4 = 1
            r8 = 8
            r12 = 2
            r5 = r3
            goto L_0x0032
        L_0x0127:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r1.mFirstVisibleWidget
            if (r3 == 0) goto L_0x0132
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r3.mListAnchors
            r3 = r3[r2]
            r3.getMargin()
        L_0x0132:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r1.mLastVisibleWidget
            if (r3 == 0) goto L_0x013f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r3.mListAnchors
            int r2 = r2 + 1
            r2 = r3[r2]
            r2.getMargin()
        L_0x013f:
            r1.mLast = r5
            int r2 = r1.mOrientation
            if (r2 != 0) goto L_0x014c
            boolean r2 = r1.mIsRtl
            if (r2 == 0) goto L_0x014c
            r1.mHead = r5
            goto L_0x0150
        L_0x014c:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r1.mFirst
            r1.mHead = r2
        L_0x0150:
            boolean r2 = r1.mHasDefinedWeights
            if (r2 == 0) goto L_0x015a
            boolean r2 = r1.mHasUndefinedWeights
            if (r2 == 0) goto L_0x015a
            r2 = 1
            goto L_0x015b
        L_0x015a:
            r2 = 0
        L_0x015b:
            r1.mHasComplexMatchWeights = r2
            r2 = 1
            goto L_0x0160
        L_0x015f:
            r2 = r4
        L_0x0160:
            r1.mDefined = r2
            androidx.constraintlayout.solver.widgets.ConstraintWidget r12 = r1.mFirst
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = r1.mLast
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = r1.mFirstVisibleWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r1.mLastVisibleWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r1.mHead
            float r3 = r1.mTotalWeight
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r4 = r0.mListDimensionBehaviors
            r4 = r4[r42]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            if (r4 != r5) goto L_0x0178
            r4 = 1
            goto L_0x0179
        L_0x0178:
            r4 = 0
        L_0x0179:
            if (r42 != 0) goto L_0x019a
            int r5 = r2.mHorizontalChainStyle
            r6 = 1
            if (r5 != 0) goto L_0x0183
            r22 = 1
            goto L_0x0185
        L_0x0183:
            r22 = 0
        L_0x0185:
            if (r5 != r6) goto L_0x018d
            r21 = r6
            r23 = r9
            r9 = 2
            goto L_0x0192
        L_0x018d:
            r23 = r9
            r9 = 2
            r21 = 0
        L_0x0192:
            if (r5 != r9) goto L_0x0197
            r5 = r22
            goto L_0x01b2
        L_0x0197:
            r5 = r22
            goto L_0x01bc
        L_0x019a:
            r23 = r9
            r6 = 1
            r9 = 2
            int r5 = r2.mVerticalChainStyle
            if (r5 != 0) goto L_0x01a5
            r21 = r6
            goto L_0x01a7
        L_0x01a5:
            r21 = 0
        L_0x01a7:
            if (r5 != r6) goto L_0x01ab
            r6 = 1
            goto L_0x01ac
        L_0x01ab:
            r6 = 0
        L_0x01ac:
            if (r5 != r9) goto L_0x01b8
            r5 = r21
            r21 = r6
        L_0x01b2:
            r24 = r21
            r21 = r5
            r5 = 1
            goto L_0x01c1
        L_0x01b8:
            r5 = r21
            r21 = r6
        L_0x01bc:
            r24 = r21
            r21 = r5
            r5 = 0
        L_0x01c1:
            r26 = r3
            r9 = r12
            r6 = 0
        L_0x01c5:
            if (r6 != 0) goto L_0x029b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r9.mListAnchors
            r3 = r3[r16]
            if (r5 == 0) goto L_0x01d0
            r30 = 1
            goto L_0x01d2
        L_0x01d0:
            r30 = 4
        L_0x01d2:
            int r31 = r3.getMargin()
            r32 = r6
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r6 = r9.mListDimensionBehaviors
            r6 = r6[r42]
            if (r6 != r11) goto L_0x01e8
            int[] r6 = r9.mResolvedMatchConstraintDefault
            r6 = r6[r42]
            if (r6 != 0) goto L_0x01e8
            r33 = r14
            r6 = 1
            goto L_0x01eb
        L_0x01e8:
            r33 = r14
            r6 = 0
        L_0x01eb:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r14 = r3.mTarget
            if (r14 == 0) goto L_0x01f7
            if (r9 == r12) goto L_0x01f7
            int r14 = r14.getMargin()
            int r31 = r14 + r31
        L_0x01f7:
            r14 = r31
            if (r5 == 0) goto L_0x0204
            if (r9 == r12) goto L_0x0204
            if (r9 == r8) goto L_0x0204
            r31 = r15
            r30 = 5
            goto L_0x0206
        L_0x0204:
            r31 = r15
        L_0x0206:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r3.mTarget
            if (r15 == 0) goto L_0x0237
            if (r9 != r8) goto L_0x0219
            r34 = r2
            androidx.constraintlayout.solver.SolverVariable r2 = r3.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r15 = r15.mSolverVariable
            r35 = r12
            r12 = 6
            r10.addGreaterThan(r2, r15, r14, r12)
            goto L_0x0225
        L_0x0219:
            r34 = r2
            r35 = r12
            androidx.constraintlayout.solver.SolverVariable r2 = r3.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r12 = r15.mSolverVariable
            r15 = 7
            r10.addGreaterThan(r2, r12, r14, r15)
        L_0x0225:
            if (r6 == 0) goto L_0x022b
            if (r5 != 0) goto L_0x022b
            r2 = 5
            goto L_0x022d
        L_0x022b:
            r2 = r30
        L_0x022d:
            androidx.constraintlayout.solver.SolverVariable r6 = r3.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            r10.addEquality(r6, r3, r14, r2)
            goto L_0x023b
        L_0x0237:
            r34 = r2
            r35 = r12
        L_0x023b:
            if (r4 == 0) goto L_0x026c
            int r2 = r9.mVisibility
            r3 = 8
            if (r2 == r3) goto L_0x025b
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r9.mListDimensionBehaviors
            r2 = r2[r42]
            if (r2 != r11) goto L_0x025b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r9.mListAnchors
            int r3 = r16 + 1
            r3 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            r2 = r2[r16]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            r6 = 5
            r12 = 0
            r10.addGreaterThan(r3, r2, r12, r6)
            goto L_0x025c
        L_0x025b:
            r12 = 0
        L_0x025c:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r9.mListAnchors
            r2 = r2[r16]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r0.mListAnchors
            r3 = r3[r16]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            r6 = 7
            r10.addGreaterThan(r2, r3, r12, r6)
        L_0x026c:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r9.mListAnchors
            int r3 = r16 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x0288
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r2.mOwner
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r2.mListAnchors
            r6 = r3[r16]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 == 0) goto L_0x0288
            r3 = r3[r16]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.mOwner
            if (r3 == r9) goto L_0x028a
        L_0x0288:
            r2 = r17
        L_0x028a:
            if (r2 == 0) goto L_0x0290
            r9 = r2
            r6 = r32
            goto L_0x0291
        L_0x0290:
            r6 = 1
        L_0x0291:
            r15 = r31
            r14 = r33
            r2 = r34
            r12 = r35
            goto L_0x01c5
        L_0x029b:
            r34 = r2
            r35 = r12
            r33 = r14
            r31 = r15
            if (r7 == 0) goto L_0x0304
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            int r3 = r16 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x0304
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r7.mListAnchors
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r6 = r7.mListDimensionBehaviors
            r6 = r6[r42]
            if (r6 != r11) goto L_0x02c1
            int[] r6 = r7.mResolvedMatchConstraintDefault
            r6 = r6[r42]
            if (r6 != 0) goto L_0x02c1
            r6 = 1
            goto L_0x02c2
        L_0x02c1:
            r6 = 0
        L_0x02c2:
            if (r6 == 0) goto L_0x02da
            if (r5 != 0) goto L_0x02da
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r2.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r6.mOwner
            if (r9 != r0) goto L_0x02da
            androidx.constraintlayout.solver.SolverVariable r9 = r2.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            int r12 = r2.getMargin()
            int r12 = -r12
            r14 = 5
            r10.addEquality(r9, r6, r12, r14)
            goto L_0x02f0
        L_0x02da:
            r14 = 5
            if (r5 == 0) goto L_0x02f0
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r2.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r6.mOwner
            if (r9 != r0) goto L_0x02f0
            androidx.constraintlayout.solver.SolverVariable r9 = r2.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            int r12 = r2.getMargin()
            int r12 = -r12
            r15 = 4
            r10.addEquality(r9, r6, r12, r15)
        L_0x02f0:
            androidx.constraintlayout.solver.SolverVariable r6 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r9 = r13.mListAnchors
            r3 = r9[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            int r2 = r2.getMargin()
            int r2 = -r2
            r9 = 6
            r10.addLowerThan(r6, r3, r2, r9)
            goto L_0x0305
        L_0x0304:
            r14 = 5
        L_0x0305:
            if (r4 == 0) goto L_0x031f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r0.mListAnchors
            int r3 = r16 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r13.mListAnchors
            r6 = r4[r3]
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            r3 = r4[r3]
            int r3 = r3.getMargin()
            r4 = 7
            r10.addGreaterThan(r2, r6, r3, r4)
        L_0x031f:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r2 = r1.mWeightedMatchConstraintsWidgets
            if (r2 == 0) goto L_0x0451
            int r3 = r2.size()
            r4 = 1
            if (r3 <= r4) goto L_0x0451
            boolean r6 = r1.mHasUndefinedWeights
            if (r6 == 0) goto L_0x0336
            boolean r6 = r1.mHasComplexMatchWeights
            if (r6 != 0) goto L_0x0336
            int r6 = r1.mWidgetsMatchCount
            float r6 = (float) r6
            goto L_0x0338
        L_0x0336:
            r6 = r26
        L_0x0338:
            r15 = r17
            r9 = 0
            r12 = 0
        L_0x033c:
            if (r12 >= r3) goto L_0x0451
            java.lang.Object r22 = r2.get(r12)
            r4 = r22
            androidx.constraintlayout.solver.widgets.ConstraintWidget r4 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r4
            float[] r14 = r4.mWeight
            r14 = r14[r42]
            r19 = 0
            int r22 = (r14 > r19 ? 1 : (r14 == r19 ? 0 : -1))
            if (r22 >= 0) goto L_0x0374
            boolean r14 = r1.mHasComplexMatchWeights
            if (r14 == 0) goto L_0x036c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r4.mListAnchors
            int r4 = r16 + 1
            r4 = r0[r4]
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            r0 = r0[r16]
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            r22 = r2
            r2 = 0
            r14 = 4
            r10.addEquality(r4, r0, r2, r14)
            r27 = r14
            r4 = 7
            r14 = r2
            goto L_0x038e
        L_0x036c:
            r22 = r2
            r2 = 0
            r14 = 1065353216(0x3f800000, float:1.0)
            r27 = 4
            goto L_0x0379
        L_0x0374:
            r22 = r2
            r27 = 4
            r2 = 0
        L_0x0379:
            int r30 = (r14 > r2 ? 1 : (r14 == r2 ? 0 : -1))
            if (r30 != 0) goto L_0x039c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r4.mListAnchors
            int r2 = r16 + 1
            r2 = r0[r2]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            r0 = r0[r16]
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            r4 = 7
            r14 = 0
            r10.addEquality(r2, r0, r14, r4)
        L_0x038e:
            r36 = r3
            r18 = r4
            r30 = r6
            r37 = r11
            r29 = r14
            r19 = 0
            goto L_0x0441
        L_0x039c:
            r18 = 7
            r29 = 0
            if (r15 == 0) goto L_0x0434
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r15.mListAnchors
            r15 = r2[r16]
            androidx.constraintlayout.solver.SolverVariable r15 = r15.mSolverVariable
            int r32 = r16 + 1
            r2 = r2[r32]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r4.mListAnchors
            r36 = r3
            r3 = r0[r16]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            r0 = r0[r32]
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            r32 = r4
            androidx.constraintlayout.solver.ArrayRow r4 = r41.createRow()
            r37 = r11
            r11 = 0
            r4.constantValue = r11
            int r19 = (r6 > r11 ? 1 : (r6 == r11 ? 0 : -1))
            r11 = -1082130432(0xffffffffbf800000, float:-1.0)
            if (r19 == 0) goto L_0x0415
            int r19 = (r9 > r14 ? 1 : (r9 == r14 ? 0 : -1))
            if (r19 != 0) goto L_0x03d0
            goto L_0x0415
        L_0x03d0:
            r19 = 0
            int r38 = (r9 > r19 ? 1 : (r9 == r19 ? 0 : -1))
            if (r38 != 0) goto L_0x03e3
            androidx.constraintlayout.solver.ArrayLinkedVariables r0 = r4.variables
            r3 = 1065353216(0x3f800000, float:1.0)
            r0.put(r15, r3)
            androidx.constraintlayout.solver.ArrayLinkedVariables r0 = r4.variables
            r0.put(r2, r11)
            goto L_0x03f3
        L_0x03e3:
            r11 = 1065353216(0x3f800000, float:1.0)
            if (r30 != 0) goto L_0x03f6
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r4.variables
            r2.put(r3, r11)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r4.variables
            r3 = -1082130432(0xffffffffbf800000, float:-1.0)
            r2.put(r0, r3)
        L_0x03f3:
            r30 = r6
            goto L_0x0430
        L_0x03f6:
            float r9 = r9 / r6
            float r30 = r14 / r6
            float r9 = r9 / r30
            r30 = r6
            androidx.constraintlayout.solver.ArrayLinkedVariables r6 = r4.variables
            r6.put(r15, r11)
            androidx.constraintlayout.solver.ArrayLinkedVariables r6 = r4.variables
            r11 = -1082130432(0xffffffffbf800000, float:-1.0)
            r6.put(r2, r11)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r4.variables
            r2.put(r0, r9)
            androidx.constraintlayout.solver.ArrayLinkedVariables r0 = r4.variables
            float r2 = -r9
            r0.put(r3, r2)
            goto L_0x0430
        L_0x0415:
            r30 = r6
            r6 = r11
            r11 = 1065353216(0x3f800000, float:1.0)
            r19 = 0
            androidx.constraintlayout.solver.ArrayLinkedVariables r9 = r4.variables
            r9.put(r15, r11)
            androidx.constraintlayout.solver.ArrayLinkedVariables r9 = r4.variables
            r9.put(r2, r6)
            androidx.constraintlayout.solver.ArrayLinkedVariables r2 = r4.variables
            r2.put(r0, r11)
            androidx.constraintlayout.solver.ArrayLinkedVariables r0 = r4.variables
            r0.put(r3, r6)
        L_0x0430:
            r10.addConstraint(r4)
            goto L_0x043e
        L_0x0434:
            r36 = r3
            r32 = r4
            r30 = r6
            r37 = r11
            r19 = 0
        L_0x043e:
            r9 = r14
            r15 = r32
        L_0x0441:
            int r12 = r12 + 1
            r4 = 1
            r14 = 5
            r0 = r40
            r2 = r22
            r6 = r30
            r3 = r36
            r11 = r37
            goto L_0x033c
        L_0x0451:
            r37 = r11
            r18 = 7
            r27 = 4
            r29 = 0
            if (r8 == 0) goto L_0x04bf
            if (r8 == r7) goto L_0x045f
            if (r5 == 0) goto L_0x04bf
        L_0x045f:
            r0 = r35
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r0.mListAnchors
            r0 = r0[r16]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            int r2 = r16 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            if (r0 == 0) goto L_0x0473
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            r3 = r0
            goto L_0x0475
        L_0x0473:
            r3 = r17
        L_0x0475:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r1.mTarget
            if (r0 == 0) goto L_0x047d
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            r6 = r0
            goto L_0x047f
        L_0x047d:
            r6 = r17
        L_0x047f:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r8.mListAnchors
            r0 = r0[r16]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r7.mListAnchors
            r1 = r1[r2]
            if (r3 == 0) goto L_0x04b3
            if (r6 == 0) goto L_0x04b3
            if (r42 != 0) goto L_0x0492
            r2 = r34
            float r2 = r2.mHorizontalBiasPercent
            goto L_0x0496
        L_0x0492:
            r2 = r34
            float r2 = r2.mVerticalBiasPercent
        L_0x0496:
            r5 = r2
            int r4 = r0.getMargin()
            int r9 = r1.getMargin()
            androidx.constraintlayout.solver.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r0 = r1.mSolverVariable
            r11 = 5
            r1 = r41
            r12 = r7
            r7 = r0
            r14 = r8
            r8 = r9
            r15 = r23
            r19 = 2
            r9 = r11
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x04b9
        L_0x04b3:
            r12 = r7
            r14 = r8
            r15 = r23
            r19 = 2
        L_0x04b9:
            r18 = r13
            r20 = r15
            goto L_0x0701
        L_0x04bf:
            r12 = r7
            r14 = r8
            r15 = r23
            r0 = r35
            r19 = 2
            if (r21 == 0) goto L_0x05cf
            if (r14 == 0) goto L_0x05cf
            int r2 = r1.mWidgetsMatchCount
            if (r2 <= 0) goto L_0x04d6
            int r1 = r1.mWidgetsCount
            if (r1 != r2) goto L_0x04d6
            r26 = 1
            goto L_0x04d8
        L_0x04d6:
            r26 = r29
        L_0x04d8:
            r9 = r14
            r11 = r9
        L_0x04da:
            if (r11 == 0) goto L_0x04b9
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r11.mNextChainWidget
            r1 = r1[r42]
            r8 = r1
        L_0x04e1:
            if (r8 == 0) goto L_0x04ee
            int r1 = r8.mVisibility
            r7 = 8
            if (r1 != r7) goto L_0x04f0
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r8.mNextChainWidget
            r8 = r1[r42]
            goto L_0x04e1
        L_0x04ee:
            r7 = 8
        L_0x04f0:
            if (r8 != 0) goto L_0x04ff
            if (r11 != r12) goto L_0x04f5
            goto L_0x04ff
        L_0x04f5:
            r20 = r8
            r22 = r9
            r28 = r18
            r18 = 5
            goto L_0x05bf
        L_0x04ff:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r11.mListAnchors
            r1 = r1[r16]
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r1.mTarget
            if (r3 == 0) goto L_0x050c
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            goto L_0x050e
        L_0x050c:
            r3 = r17
        L_0x050e:
            if (r9 == r11) goto L_0x0519
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r9.mListAnchors
            int r4 = r16 + 1
            r3 = r3[r4]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            goto L_0x052e
        L_0x0519:
            if (r11 != r14) goto L_0x052e
            if (r9 != r11) goto L_0x052e
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r0.mListAnchors
            r4 = r3[r16]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r4.mTarget
            if (r4 == 0) goto L_0x052c
            r3 = r3[r16]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            goto L_0x052e
        L_0x052c:
            r3 = r17
        L_0x052e:
            int r1 = r1.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r11.mListAnchors
            int r5 = r16 + 1
            r4 = r4[r5]
            int r4 = r4.getMargin()
            if (r8 == 0) goto L_0x0553
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r8.mListAnchors
            r6 = r6[r16]
            androidx.constraintlayout.solver.SolverVariable r7 = r6.mSolverVariable
            r22 = r6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r11.mListAnchors
            r6 = r6[r5]
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
        L_0x054c:
            r39 = r22
            r22 = r6
            r6 = r39
            goto L_0x056b
        L_0x0553:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r13.mListAnchors
            r6 = r6[r5]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r6.mTarget
            if (r6 == 0) goto L_0x0560
            androidx.constraintlayout.solver.SolverVariable r7 = r6.mSolverVariable
            r22 = r6
            goto L_0x0564
        L_0x0560:
            r22 = r6
            r7 = r17
        L_0x0564:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r11.mListAnchors
            r6 = r6[r5]
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            goto L_0x054c
        L_0x056b:
            if (r6 == 0) goto L_0x0572
            int r6 = r6.getMargin()
            int r4 = r4 + r6
        L_0x0572:
            if (r9 == 0) goto L_0x057d
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r9.mListAnchors
            r6 = r6[r5]
            int r6 = r6.getMargin()
            int r1 = r1 + r6
        L_0x057d:
            if (r2 == 0) goto L_0x04f5
            if (r3 == 0) goto L_0x04f5
            if (r7 == 0) goto L_0x04f5
            if (r22 == 0) goto L_0x04f5
            if (r11 != r14) goto L_0x058f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r14.mListAnchors
            r1 = r1[r16]
            int r1 = r1.getMargin()
        L_0x058f:
            r6 = r1
            if (r11 != r12) goto L_0x059d
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r12.mListAnchors
            r1 = r1[r5]
            int r1 = r1.getMargin()
            r23 = r1
            goto L_0x059f
        L_0x059d:
            r23 = r4
        L_0x059f:
            if (r26 == 0) goto L_0x05a4
            r25 = r18
            goto L_0x05a6
        L_0x05a4:
            r25 = 5
        L_0x05a6:
            r5 = 1056964608(0x3f000000, float:0.5)
            r1 = r41
            r28 = r18
            r18 = 5
            r4 = r6
            r6 = r7
            r20 = 8
            r7 = r22
            r20 = r8
            r8 = r23
            r22 = r9
            r9 = r25
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
        L_0x05bf:
            int r1 = r11.mVisibility
            r9 = 8
            if (r1 == r9) goto L_0x05c7
            r22 = r11
        L_0x05c7:
            r11 = r20
            r9 = r22
            r18 = r28
            goto L_0x04da
        L_0x05cf:
            r28 = r18
            r9 = 8
            if (r24 == 0) goto L_0x04b9
            if (r14 == 0) goto L_0x04b9
            int r2 = r1.mWidgetsMatchCount
            if (r2 <= 0) goto L_0x05e2
            int r1 = r1.mWidgetsCount
            if (r1 != r2) goto L_0x05e2
            r26 = 1
            goto L_0x05e4
        L_0x05e2:
            r26 = r29
        L_0x05e4:
            r8 = r14
            r11 = r8
        L_0x05e6:
            if (r11 == 0) goto L_0x06a1
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r11.mNextChainWidget
            r1 = r1[r42]
        L_0x05ec:
            if (r1 == 0) goto L_0x05f7
            int r2 = r1.mVisibility
            if (r2 != r9) goto L_0x05f7
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r1.mNextChainWidget
            r1 = r1[r42]
            goto L_0x05ec
        L_0x05f7:
            if (r11 == r14) goto L_0x068b
            if (r11 == r12) goto L_0x068b
            if (r1 == 0) goto L_0x068b
            if (r1 != r12) goto L_0x0602
            r7 = r17
            goto L_0x0603
        L_0x0602:
            r7 = r1
        L_0x0603:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r11.mListAnchors
            r1 = r1[r16]
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r8.mListAnchors
            int r4 = r16 + 1
            r3 = r3[r4]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            int r1 = r1.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r11.mListAnchors
            r5 = r5[r4]
            int r5 = r5.getMargin()
            if (r7 == 0) goto L_0x0631
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r7.mListAnchors
            r6 = r6[r16]
            androidx.constraintlayout.solver.SolverVariable r9 = r6.mSolverVariable
            r18 = r7
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r6.mTarget
            if (r7 == 0) goto L_0x062e
            androidx.constraintlayout.solver.SolverVariable r7 = r7.mSolverVariable
            goto L_0x0649
        L_0x062e:
            r7 = r17
            goto L_0x0649
        L_0x0631:
            r18 = r7
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r12.mListAnchors
            r6 = r6[r16]
            if (r6 == 0) goto L_0x063c
            androidx.constraintlayout.solver.SolverVariable r7 = r6.mSolverVariable
            goto L_0x063e
        L_0x063c:
            r7 = r17
        L_0x063e:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r9 = r11.mListAnchors
            r9 = r9[r4]
            androidx.constraintlayout.solver.SolverVariable r9 = r9.mSolverVariable
            r39 = r9
            r9 = r7
            r7 = r39
        L_0x0649:
            if (r6 == 0) goto L_0x0653
            int r6 = r6.getMargin()
            int r6 = r6 + r5
            r20 = r6
            goto L_0x0655
        L_0x0653:
            r20 = r5
        L_0x0655:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r8.mListAnchors
            r4 = r5[r4]
            int r4 = r4.getMargin()
            int r4 = r4 + r1
            if (r26 == 0) goto L_0x0663
            r22 = r28
            goto L_0x0665
        L_0x0663:
            r22 = r27
        L_0x0665:
            if (r2 == 0) goto L_0x0682
            if (r3 == 0) goto L_0x0682
            if (r9 == 0) goto L_0x0682
            if (r7 == 0) goto L_0x0682
            r5 = 1056964608(0x3f000000, float:0.5)
            r1 = r41
            r6 = r27
            r6 = r9
            r23 = r8
            r8 = r20
            r20 = r15
            r15 = 8
            r9 = r22
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0688
        L_0x0682:
            r23 = r8
            r20 = r15
            r15 = 8
        L_0x0688:
            r8 = r18
            goto L_0x0691
        L_0x068b:
            r23 = r8
            r20 = r15
            r15 = r9
            r8 = r1
        L_0x0691:
            int r1 = r11.mVisibility
            if (r1 == r15) goto L_0x0697
            r23 = r11
        L_0x0697:
            r11 = r8
            r9 = r15
            r15 = r20
            r8 = r23
            r27 = 4
            goto L_0x05e6
        L_0x06a1:
            r20 = r15
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r14.mListAnchors
            r1 = r1[r16]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r0.mListAnchors
            r0 = r0[r16]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r0.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r12.mListAnchors
            int r3 = r16 + 1
            r11 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r2.mTarget
            if (r0 == 0) goto L_0x06ee
            if (r14 == r12) goto L_0x06ca
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r0 = r0.mSolverVariable
            int r1 = r1.getMargin()
            r9 = 4
            r10.addEquality(r2, r0, r1, r9)
            goto L_0x06ea
        L_0x06ca:
            r9 = 4
            if (r15 == 0) goto L_0x06ea
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r3 = r0.mSolverVariable
            int r4 = r1.getMargin()
            r5 = 1056964608(0x3f000000, float:0.5)
            androidx.constraintlayout.solver.SolverVariable r6 = r11.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r7 = r15.mSolverVariable
            int r8 = r11.getMargin()
            r0 = 4
            r1 = r41
            r18 = r13
            r13 = r9
            r9 = r0
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x06f1
        L_0x06ea:
            r18 = r13
            r13 = r9
            goto L_0x06f1
        L_0x06ee:
            r18 = r13
            r13 = 4
        L_0x06f1:
            if (r15 == 0) goto L_0x0701
            if (r14 == r12) goto L_0x0701
            androidx.constraintlayout.solver.SolverVariable r0 = r11.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r1 = r15.mSolverVariable
            int r2 = r11.getMargin()
            int r2 = -r2
            r10.addEquality(r0, r1, r2, r13)
        L_0x0701:
            if (r21 != 0) goto L_0x0705
            if (r24 == 0) goto L_0x075c
        L_0x0705:
            if (r14 == 0) goto L_0x075c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r14.mListAnchors
            r1 = r0[r16]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r12.mListAnchors
            int r3 = r16 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r1.mTarget
            if (r4 == 0) goto L_0x0718
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            goto L_0x071a
        L_0x0718:
            r4 = r17
        L_0x071a:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r2.mTarget
            if (r5 == 0) goto L_0x0721
            androidx.constraintlayout.solver.SolverVariable r5 = r5.mSolverVariable
            goto L_0x0723
        L_0x0721:
            r5 = r17
        L_0x0723:
            r6 = r18
            if (r6 == r12) goto L_0x0736
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r6.mListAnchors
            r5 = r5[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.mTarget
            if (r5 == 0) goto L_0x0733
            androidx.constraintlayout.solver.SolverVariable r5 = r5.mSolverVariable
            r17 = r5
        L_0x0733:
            r6 = r17
            goto L_0x0737
        L_0x0736:
            r6 = r5
        L_0x0737:
            if (r14 != r12) goto L_0x073d
            r1 = r0[r16]
            r2 = r0[r3]
        L_0x073d:
            if (r4 == 0) goto L_0x075c
            if (r6 == 0) goto L_0x075c
            r5 = 1056964608(0x3f000000, float:0.5)
            int r0 = r1.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r7 = r12.mListAnchors
            r3 = r7[r3]
            int r8 = r3.getMargin()
            androidx.constraintlayout.solver.SolverVariable r3 = r1.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r7 = r2.mSolverVariable
            r9 = 5
            r1 = r41
            r2 = r3
            r3 = r4
            r4 = r0
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
        L_0x075c:
            int r9 = r20 + 1
            r0 = r40
            r12 = r19
            r15 = r31
            r14 = r33
            r11 = r37
            goto L_0x001b
        L_0x076a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Chain.applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.LinearSystem, int):void");
    }
}
