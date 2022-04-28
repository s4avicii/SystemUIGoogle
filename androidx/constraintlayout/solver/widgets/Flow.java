package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public final class Flow extends VirtualLayout {
    public ConstraintWidget[] mAlignedBiggestElementsInCols = null;
    public ConstraintWidget[] mAlignedBiggestElementsInRows = null;
    public int[] mAlignedDimensions = null;
    public ArrayList<WidgetsList> mChainList = new ArrayList<>();
    public ConstraintWidget[] mDisplayedWidgets;
    public int mDisplayedWidgetsCount = 0;
    public float mFirstHorizontalBias = 0.5f;
    public int mFirstHorizontalStyle = -1;
    public float mFirstVerticalBias = 0.5f;
    public int mFirstVerticalStyle = -1;
    public int mHorizontalAlign = 2;
    public float mHorizontalBias = 0.5f;
    public int mHorizontalGap = 0;
    public int mHorizontalStyle = -1;
    public float mLastHorizontalBias = 0.5f;
    public int mLastHorizontalStyle = -1;
    public float mLastVerticalBias = 0.5f;
    public int mLastVerticalStyle = -1;
    public int mMaxElementsWrap = -1;
    public int mOrientation = 0;
    public int mVerticalAlign = 2;
    public float mVerticalBias = 0.5f;
    public int mVerticalGap = 0;
    public int mVerticalStyle = -1;
    public int mWrapMode = 0;

    public class WidgetsList {
        public ConstraintWidget biggest = null;
        public int biggestDimension = 0;
        public ConstraintAnchor mBottom;
        public int mCount = 0;
        public int mHeight = 0;
        public ConstraintAnchor mLeft;
        public int mMax = 0;
        public int mNbMatchConstraintsWidgets = 0;
        public int mOrientation;
        public int mPaddingBottom = 0;
        public int mPaddingLeft = 0;
        public int mPaddingRight = 0;
        public int mPaddingTop = 0;
        public ConstraintAnchor mRight;
        public int mStartIndex = 0;
        public ConstraintAnchor mTop;
        public int mWidth = 0;

        public WidgetsList(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2) {
            this.mOrientation = i;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            Objects.requireNonNull(Flow.this);
            this.mPaddingLeft = Flow.this.mResolvedPaddingLeft;
            this.mPaddingTop = Flow.this.mPaddingTop;
            this.mPaddingRight = Flow.this.mResolvedPaddingRight;
            this.mPaddingBottom = Flow.this.mPaddingBottom;
            this.mMax = i2;
        }

        public final void add(ConstraintWidget constraintWidget) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
            int i = 0;
            if (this.mOrientation == 0) {
                Objects.requireNonNull(constraintWidget);
                if (constraintWidget.mListDimensionBehaviors[0] == dimensionBehaviour) {
                    this.mNbMatchConstraintsWidgets++;
                }
                int widgetWidth = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                Flow flow = Flow.this;
                int i2 = flow.mHorizontalGap;
                if (constraintWidget.mVisibility != 8) {
                    i = i2;
                }
                this.mWidth = widgetWidth + i + this.mWidth;
                int widgetHeight = flow.getWidgetHeight(constraintWidget, this.mMax);
                if (this.biggest == null || this.biggestDimension < widgetHeight) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = widgetHeight;
                    this.mHeight = widgetHeight;
                }
            } else {
                Objects.requireNonNull(constraintWidget);
                if (constraintWidget.mListDimensionBehaviors[1] == dimensionBehaviour) {
                    this.mNbMatchConstraintsWidgets++;
                }
                int widgetWidth2 = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                int widgetHeight2 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                int i3 = Flow.this.mVerticalGap;
                if (constraintWidget.mVisibility != 8) {
                    i = i3;
                }
                this.mHeight = widgetHeight2 + i + this.mHeight;
                if (this.biggest == null || this.biggestDimension < widgetWidth2) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = widgetWidth2;
                    this.mWidth = widgetWidth2;
                }
            }
            this.mCount++;
        }

        /* JADX WARNING: Removed duplicated region for block: B:49:0x00b1  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void createConstraints(boolean r17, int r18, boolean r19) {
            /*
                r16 = this;
                r0 = r16
                int r1 = r0.mCount
                r2 = 0
                r3 = r2
            L_0x0006:
                if (r3 >= r1) goto L_0x0019
                androidx.constraintlayout.solver.widgets.Flow r4 = androidx.constraintlayout.solver.widgets.Flow.this
                androidx.constraintlayout.solver.widgets.ConstraintWidget[] r4 = r4.mDisplayedWidgets
                int r5 = r0.mStartIndex
                int r5 = r5 + r3
                r4 = r4[r5]
                if (r4 == 0) goto L_0x0016
                r4.resetAnchors()
            L_0x0016:
                int r3 = r3 + 1
                goto L_0x0006
            L_0x0019:
                if (r1 == 0) goto L_0x02e2
                androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r0.biggest
                if (r3 != 0) goto L_0x0021
                goto L_0x02e2
            L_0x0021:
                if (r19 == 0) goto L_0x0027
                if (r18 != 0) goto L_0x0027
                r4 = 1
                goto L_0x0028
            L_0x0027:
                r4 = r2
            L_0x0028:
                r5 = -1
                r6 = r2
                r7 = r5
                r8 = r7
            L_0x002c:
                if (r6 >= r1) goto L_0x004c
                if (r17 == 0) goto L_0x0034
                int r9 = r1 + -1
                int r9 = r9 - r6
                goto L_0x0035
            L_0x0034:
                r9 = r6
            L_0x0035:
                androidx.constraintlayout.solver.widgets.Flow r10 = androidx.constraintlayout.solver.widgets.Flow.this
                androidx.constraintlayout.solver.widgets.ConstraintWidget[] r10 = r10.mDisplayedWidgets
                int r11 = r0.mStartIndex
                int r11 = r11 + r9
                r9 = r10[r11]
                java.util.Objects.requireNonNull(r9)
                int r9 = r9.mVisibility
                if (r9 != 0) goto L_0x0049
                if (r7 != r5) goto L_0x0048
                r7 = r6
            L_0x0048:
                r8 = r6
            L_0x0049:
                int r6 = r6 + 1
                goto L_0x002c
            L_0x004c:
                r6 = 0
                int r9 = r0.mOrientation
                if (r9 != 0) goto L_0x0199
                androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r0.biggest
                androidx.constraintlayout.solver.widgets.Flow r10 = androidx.constraintlayout.solver.widgets.Flow.this
                int r10 = r10.mVerticalStyle
                java.util.Objects.requireNonNull(r9)
                r9.mVerticalChainStyle = r10
                int r10 = r0.mPaddingTop
                if (r18 <= 0) goto L_0x0065
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mVerticalGap
                int r10 = r10 + r11
            L_0x0065:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r0.mTop
                r11.connect(r12, r10)
                if (r19 == 0) goto L_0x0077
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r9.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mBottom
                int r12 = r0.mPaddingBottom
                r10.connect(r11, r12)
            L_0x0077:
                if (r18 <= 0) goto L_0x0084
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r0.mTop
                androidx.constraintlayout.solver.widgets.ConstraintWidget r10 = r10.mOwner
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r10.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mTop
                r10.connect(r11, r2)
            L_0x0084:
                androidx.constraintlayout.solver.widgets.Flow r10 = androidx.constraintlayout.solver.widgets.Flow.this
                int r10 = r10.mVerticalAlign
                r11 = 3
                if (r10 != r11) goto L_0x00ad
                boolean r10 = r9.hasBaseline
                if (r10 != 0) goto L_0x00ad
                r10 = r2
            L_0x0090:
                if (r10 >= r1) goto L_0x00ad
                if (r17 == 0) goto L_0x0098
                int r12 = r1 + -1
                int r12 = r12 - r10
                goto L_0x0099
            L_0x0098:
                r12 = r10
            L_0x0099:
                androidx.constraintlayout.solver.widgets.Flow r13 = androidx.constraintlayout.solver.widgets.Flow.this
                androidx.constraintlayout.solver.widgets.ConstraintWidget[] r13 = r13.mDisplayedWidgets
                int r14 = r0.mStartIndex
                int r14 = r14 + r12
                r12 = r13[r14]
                java.util.Objects.requireNonNull(r12)
                boolean r13 = r12.hasBaseline
                if (r13 == 0) goto L_0x00aa
                goto L_0x00ae
            L_0x00aa:
                int r10 = r10 + 1
                goto L_0x0090
            L_0x00ad:
                r12 = r9
            L_0x00ae:
                r10 = r2
            L_0x00af:
                if (r10 >= r1) goto L_0x02e2
                if (r17 == 0) goto L_0x00b7
                int r13 = r1 + -1
                int r13 = r13 - r10
                goto L_0x00b8
            L_0x00b7:
                r13 = r10
            L_0x00b8:
                androidx.constraintlayout.solver.widgets.Flow r14 = androidx.constraintlayout.solver.widgets.Flow.this
                androidx.constraintlayout.solver.widgets.ConstraintWidget[] r14 = r14.mDisplayedWidgets
                int r15 = r0.mStartIndex
                int r15 = r15 + r13
                r14 = r14[r15]
                if (r10 != 0) goto L_0x00cc
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r15 = r14.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mLeft
                int r3 = r0.mPaddingLeft
                r14.connect(r15, r11, r3)
            L_0x00cc:
                if (r13 != 0) goto L_0x00f1
                androidx.constraintlayout.solver.widgets.Flow r3 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r3.mHorizontalStyle
                float r13 = r3.mHorizontalBias
                int r15 = r0.mStartIndex
                if (r15 != 0) goto L_0x00e1
                int r15 = r3.mFirstHorizontalStyle
                if (r15 == r5) goto L_0x00e1
                float r3 = r3.mFirstHorizontalBias
            L_0x00de:
                r13 = r3
                r11 = r15
                goto L_0x00ea
            L_0x00e1:
                if (r19 == 0) goto L_0x00ea
                int r15 = r3.mLastHorizontalStyle
                if (r15 == r5) goto L_0x00ea
                float r3 = r3.mLastHorizontalBias
                goto L_0x00de
            L_0x00ea:
                java.util.Objects.requireNonNull(r14)
                r14.mHorizontalChainStyle = r11
                r14.mHorizontalBiasPercent = r13
            L_0x00f1:
                int r3 = r1 + -1
                if (r10 != r3) goto L_0x00fe
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mRight
                int r13 = r0.mPaddingRight
                r14.connect(r3, r11, r13)
            L_0x00fe:
                if (r6 == 0) goto L_0x0137
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r6.mRight
                androidx.constraintlayout.solver.widgets.Flow r13 = androidx.constraintlayout.solver.widgets.Flow.this
                int r13 = r13.mHorizontalGap
                r3.connect(r11, r13)
                if (r10 != r7) goto L_0x011c
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mLeft
                int r11 = r0.mPaddingLeft
                java.util.Objects.requireNonNull(r3)
                boolean r13 = r3.isConnected()
                if (r13 == 0) goto L_0x011c
                r3.mGoneMargin = r11
            L_0x011c:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r6.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r14.mLeft
                r3.connect(r11, r2)
                r3 = 1
                int r11 = r8 + 1
                if (r10 != r11) goto L_0x0137
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r6.mRight
                int r6 = r0.mPaddingRight
                java.util.Objects.requireNonNull(r3)
                boolean r11 = r3.isConnected()
                if (r11 == 0) goto L_0x0137
                r3.mGoneMargin = r6
            L_0x0137:
                if (r14 == r9) goto L_0x0192
                androidx.constraintlayout.solver.widgets.Flow r3 = androidx.constraintlayout.solver.widgets.Flow.this
                int r3 = r3.mVerticalAlign
                r6 = 3
                if (r3 != r6) goto L_0x0155
                boolean r3 = r12.hasBaseline
                if (r3 == 0) goto L_0x0155
                if (r14 == r12) goto L_0x0155
                java.util.Objects.requireNonNull(r14)
                boolean r3 = r14.hasBaseline
                if (r3 == 0) goto L_0x0155
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mBaseline
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r12.mBaseline
                r3.connect(r11, r2)
                goto L_0x0193
            L_0x0155:
                androidx.constraintlayout.solver.widgets.Flow r3 = androidx.constraintlayout.solver.widgets.Flow.this
                int r3 = r3.mVerticalAlign
                if (r3 == 0) goto L_0x018a
                r11 = 1
                if (r3 == r11) goto L_0x0182
                if (r4 == 0) goto L_0x0173
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mTop
                int r13 = r0.mPaddingTop
                r3.connect(r11, r13)
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mBottom
                int r13 = r0.mPaddingBottom
                r3.connect(r11, r13)
                goto L_0x0193
            L_0x0173:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mTop
                r3.connect(r11, r2)
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mBottom
                r3.connect(r11, r2)
                goto L_0x0193
            L_0x0182:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mBottom
                r3.connect(r11, r2)
                goto L_0x0193
            L_0x018a:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r14.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r9.mTop
                r3.connect(r11, r2)
                goto L_0x0193
            L_0x0192:
                r6 = 3
            L_0x0193:
                int r10 = r10 + 1
                r11 = r6
                r6 = r14
                goto L_0x00af
            L_0x0199:
                androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r0.biggest
                androidx.constraintlayout.solver.widgets.Flow r9 = androidx.constraintlayout.solver.widgets.Flow.this
                int r9 = r9.mHorizontalStyle
                java.util.Objects.requireNonNull(r3)
                r3.mHorizontalChainStyle = r9
                int r9 = r0.mPaddingLeft
                if (r18 <= 0) goto L_0x01ad
                androidx.constraintlayout.solver.widgets.Flow r10 = androidx.constraintlayout.solver.widgets.Flow.this
                int r10 = r10.mHorizontalGap
                int r9 = r9 + r10
            L_0x01ad:
                if (r17 == 0) goto L_0x01cf
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r3.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mRight
                r10.connect(r11, r9)
                if (r19 == 0) goto L_0x01c1
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r3.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r0.mLeft
                int r11 = r0.mPaddingRight
                r9.connect(r10, r11)
            L_0x01c1:
                if (r18 <= 0) goto L_0x01ee
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r0.mRight
                androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r9.mOwner
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r3.mRight
                r9.connect(r10, r2)
                goto L_0x01ee
            L_0x01cf:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r3.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mLeft
                r10.connect(r11, r9)
                if (r19 == 0) goto L_0x01e1
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r3.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r0.mRight
                int r11 = r0.mPaddingRight
                r9.connect(r10, r11)
            L_0x01e1:
                if (r18 <= 0) goto L_0x01ee
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r0.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r9.mOwner
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r10 = r3.mLeft
                r9.connect(r10, r2)
            L_0x01ee:
                r9 = r2
            L_0x01ef:
                if (r9 >= r1) goto L_0x02e2
                androidx.constraintlayout.solver.widgets.Flow r10 = androidx.constraintlayout.solver.widgets.Flow.this
                androidx.constraintlayout.solver.widgets.ConstraintWidget[] r10 = r10.mDisplayedWidgets
                int r11 = r0.mStartIndex
                int r11 = r11 + r9
                r10 = r10[r11]
                if (r9 != 0) goto L_0x0225
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r10.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r0.mTop
                int r13 = r0.mPaddingTop
                r10.connect(r11, r12, r13)
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r12 = r11.mVerticalStyle
                float r13 = r11.mVerticalBias
                int r14 = r0.mStartIndex
                if (r14 != 0) goto L_0x0218
                int r14 = r11.mFirstVerticalStyle
                if (r14 == r5) goto L_0x0218
                float r11 = r11.mFirstVerticalBias
            L_0x0215:
                r13 = r11
                r12 = r14
                goto L_0x0221
            L_0x0218:
                if (r19 == 0) goto L_0x0221
                int r14 = r11.mLastVerticalStyle
                if (r14 == r5) goto L_0x0221
                float r11 = r11.mLastVerticalBias
                goto L_0x0215
            L_0x0221:
                r10.mVerticalChainStyle = r12
                r10.mVerticalBiasPercent = r13
            L_0x0225:
                int r11 = r1 + -1
                if (r9 != r11) goto L_0x0232
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r10.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r0.mBottom
                int r13 = r0.mPaddingBottom
                r10.connect(r11, r12, r13)
            L_0x0232:
                if (r6 == 0) goto L_0x026b
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r10.mTop
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r6.mBottom
                androidx.constraintlayout.solver.widgets.Flow r13 = androidx.constraintlayout.solver.widgets.Flow.this
                int r13 = r13.mVerticalGap
                r11.connect(r12, r13)
                if (r9 != r7) goto L_0x0250
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r10.mTop
                int r12 = r0.mPaddingTop
                java.util.Objects.requireNonNull(r11)
                boolean r13 = r11.isConnected()
                if (r13 == 0) goto L_0x0250
                r11.mGoneMargin = r12
            L_0x0250:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r6.mBottom
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r12 = r10.mTop
                r11.connect(r12, r2)
                r11 = 1
                int r12 = r8 + 1
                if (r9 != r12) goto L_0x026b
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r6.mBottom
                int r11 = r0.mPaddingBottom
                java.util.Objects.requireNonNull(r6)
                boolean r12 = r6.isConnected()
                if (r12 == 0) goto L_0x026b
                r6.mGoneMargin = r11
            L_0x026b:
                if (r10 == r3) goto L_0x02dc
                r6 = 2
                if (r17 == 0) goto L_0x029b
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mHorizontalAlign
                if (r11 == 0) goto L_0x0293
                r12 = 1
                if (r11 == r12) goto L_0x028b
                if (r11 == r6) goto L_0x027c
                goto L_0x02dc
            L_0x027c:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mLeft
                r6.connect(r11, r2)
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mRight
                r6.connect(r11, r2)
                goto L_0x02dc
            L_0x028b:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mLeft
                r6.connect(r11, r2)
                goto L_0x02dc
            L_0x0293:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mRight
                r6.connect(r11, r2)
                goto L_0x02dc
            L_0x029b:
                androidx.constraintlayout.solver.widgets.Flow r11 = androidx.constraintlayout.solver.widgets.Flow.this
                int r11 = r11.mHorizontalAlign
                if (r11 == 0) goto L_0x02d3
                r12 = 1
                if (r11 == r12) goto L_0x02cb
                if (r11 == r6) goto L_0x02a7
                goto L_0x02dd
            L_0x02a7:
                if (r4 == 0) goto L_0x02bc
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mLeft
                int r13 = r0.mPaddingLeft
                r6.connect(r11, r13)
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r0.mRight
                int r13 = r0.mPaddingRight
                r6.connect(r11, r13)
                goto L_0x02dd
            L_0x02bc:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mLeft
                r6.connect(r11, r2)
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mRight
                r6.connect(r11, r2)
                goto L_0x02dd
            L_0x02cb:
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mRight
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mRight
                r6.connect(r11, r2)
                goto L_0x02dd
            L_0x02d3:
                r12 = 1
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r10.mLeft
                androidx.constraintlayout.solver.widgets.ConstraintAnchor r11 = r3.mLeft
                r6.connect(r11, r2)
                goto L_0x02dd
            L_0x02dc:
                r12 = 1
            L_0x02dd:
                int r9 = r9 + 1
                r6 = r10
                goto L_0x01ef
            L_0x02e2:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Flow.WidgetsList.createConstraints(boolean, int, boolean):void");
        }

        public final int getHeight() {
            if (this.mOrientation == 1) {
                return this.mHeight - Flow.this.mVerticalGap;
            }
            return this.mHeight;
        }

        public final int getWidth() {
            if (this.mOrientation == 0) {
                return this.mWidth - Flow.this.mHorizontalGap;
            }
            return this.mWidth;
        }

        public final void measureMatchConstraints(int i) {
            ConstraintWidget.DimensionBehaviour dimensionBehaviour = ConstraintWidget.DimensionBehaviour.FIXED;
            ConstraintWidget.DimensionBehaviour dimensionBehaviour2 = ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT;
            int i2 = this.mNbMatchConstraintsWidgets;
            if (i2 != 0) {
                int i3 = this.mCount;
                int i4 = i / i2;
                for (int i5 = 0; i5 < i3; i5++) {
                    Flow flow = Flow.this;
                    ConstraintWidget constraintWidget = flow.mDisplayedWidgets[this.mStartIndex + i5];
                    if (this.mOrientation == 0) {
                        if (constraintWidget != null) {
                            ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr = constraintWidget.mListDimensionBehaviors;
                            if (dimensionBehaviourArr[0] == dimensionBehaviour2) {
                                flow.measure(constraintWidget, dimensionBehaviour, i4, dimensionBehaviourArr[1], constraintWidget.getHeight());
                            }
                        }
                    } else if (constraintWidget != null) {
                        ConstraintWidget.DimensionBehaviour[] dimensionBehaviourArr2 = constraintWidget.mListDimensionBehaviors;
                        if (dimensionBehaviourArr2[1] == dimensionBehaviour2) {
                            flow.measure(constraintWidget, dimensionBehaviourArr2[0], constraintWidget.getWidth(), dimensionBehaviour, i4);
                        }
                    }
                }
                this.mWidth = 0;
                this.mHeight = 0;
                this.biggest = null;
                this.biggestDimension = 0;
                int i6 = this.mCount;
                for (int i7 = 0; i7 < i6; i7++) {
                    Flow flow2 = Flow.this;
                    ConstraintWidget constraintWidget2 = flow2.mDisplayedWidgets[this.mStartIndex + i7];
                    if (this.mOrientation == 0) {
                        int width = constraintWidget2.getWidth();
                        Flow flow3 = Flow.this;
                        int i8 = flow3.mHorizontalGap;
                        if (constraintWidget2.mVisibility == 8) {
                            i8 = 0;
                        }
                        this.mWidth = width + i8 + this.mWidth;
                        int widgetHeight = flow3.getWidgetHeight(constraintWidget2, this.mMax);
                        if (this.biggest == null || this.biggestDimension < widgetHeight) {
                            this.biggest = constraintWidget2;
                            this.biggestDimension = widgetHeight;
                            this.mHeight = widgetHeight;
                        }
                    } else {
                        int widgetWidth = flow2.getWidgetWidth(constraintWidget2, this.mMax);
                        int widgetHeight2 = Flow.this.getWidgetHeight(constraintWidget2, this.mMax);
                        int i9 = Flow.this.mVerticalGap;
                        Objects.requireNonNull(constraintWidget2);
                        if (constraintWidget2.mVisibility == 8) {
                            i9 = 0;
                        }
                        this.mHeight = widgetHeight2 + i9 + this.mHeight;
                        if (this.biggest == null || this.biggestDimension < widgetWidth) {
                            this.biggest = constraintWidget2;
                            this.biggestDimension = widgetWidth;
                            this.mWidth = widgetWidth;
                        }
                    }
                }
            }
        }

        public final void setup(int i, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int i2, int i3, int i4, int i5, int i6) {
            this.mOrientation = i;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = i2;
            this.mPaddingTop = i3;
            this.mPaddingRight = i4;
            this.mPaddingBottom = i5;
            this.mMax = i6;
        }
    }

    public final int getWidgetHeight(ConstraintWidget constraintWidget, int i) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.mListDimensionBehaviors[1] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i2 = constraintWidget.mMatchConstraintDefaultHeight;
            if (i2 == 0) {
                return 0;
            }
            if (i2 == 2) {
                int i3 = (int) (constraintWidget.mMatchConstraintPercentHeight * ((float) i));
                if (i3 != constraintWidget.getHeight()) {
                    measure(constraintWidget, constraintWidget.mListDimensionBehaviors[0], constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, i3);
                }
                return i3;
            }
        }
        return constraintWidget.getHeight();
    }

    public final int getWidgetWidth(ConstraintWidget constraintWidget, int i) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.mListDimensionBehaviors[0] == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
            int i2 = constraintWidget.mMatchConstraintDefaultWidth;
            if (i2 == 0) {
                return 0;
            }
            if (i2 == 2) {
                int i3 = (int) (constraintWidget.mMatchConstraintPercentWidth * ((float) i));
                if (i3 != constraintWidget.getWidth()) {
                    measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, i3, constraintWidget.mListDimensionBehaviors[1], constraintWidget.getHeight());
                }
                return i3;
            }
        }
        return constraintWidget.getWidth();
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v1, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v1, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r9v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v1, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: boolean} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v8, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v9, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v2, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v9, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v2, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v7, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r30v0, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v0, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v7, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r30v1, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r27v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v1, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v11, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v4, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v5, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v8, resolved type: java.lang.Object[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v6, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r29v3, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v26, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v27, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v28, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v29, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v43, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v44, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v30, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v32, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v33, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v34, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v35, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v53, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v54, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v36, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v39, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v28, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v22, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v18, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v29, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v50, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v51, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v45, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r15v13, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v54, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v64, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v24, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v19, resolved type: int[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v24, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v32, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v26, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v34, resolved type: int} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v70, resolved type: char} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r30v3, resolved type: java.lang.Object[]} */
    /* JADX WARNING: type inference failed for: r30v2 */
    /* JADX WARNING: Code restructure failed: missing block: B:182:0x029a, code lost:
        r3 = r35;
        r7 = r1;
        r6 = r5;
        r11 = r10;
        r1 = r34;
        r5 = r4;
        r10 = r9;
        r4 = r36;
        r9 = r8;
        r8 = r0;
        r0 = r33;
     */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:118:0x01a9  */
    /* JADX WARNING: Removed duplicated region for block: B:293:0x05f4  */
    /* JADX WARNING: Removed duplicated region for block: B:294:0x05f6  */
    /* JADX WARNING: Removed duplicated region for block: B:300:0x0604  */
    /* JADX WARNING: Removed duplicated region for block: B:301:0x0606  */
    /* JADX WARNING: Removed duplicated region for block: B:308:0x0620  */
    /* JADX WARNING: Removed duplicated region for block: B:309:0x0622  */
    /* JADX WARNING: Removed duplicated region for block: B:335:0x02b8 A[SYNTHETIC] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void measure(int r34, int r35, int r36, int r37) {
        /*
            r33 = this;
            r8 = r33
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r9 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r10 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            int r0 = r8.mWidgetsCount
            r11 = 1
            r12 = 0
            if (r0 <= 0) goto L_0x0090
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = r8.mParent
            if (r0 == 0) goto L_0x0015
            androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r0 = (androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer) r0
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measurer r0 = r0.mMeasurer
            goto L_0x0016
        L_0x0015:
            r0 = 0
        L_0x0016:
            if (r0 != 0) goto L_0x001b
            r0 = r12
            goto L_0x0087
        L_0x001b:
            r2 = r12
        L_0x001c:
            int r3 = r8.mWidgetsCount
            if (r2 >= r3) goto L_0x0086
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r3 = r8.mWidgets
            r3 = r3[r2]
            if (r3 != 0) goto L_0x0027
            goto L_0x0083
        L_0x0027:
            boolean r4 = r3 instanceof androidx.constraintlayout.solver.widgets.Guideline
            if (r4 == 0) goto L_0x002c
            goto L_0x0083
        L_0x002c:
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r4 = r3.getDimensionBehaviour(r12)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r5 = r3.getDimensionBehaviour(r11)
            if (r4 != r9) goto L_0x0042
            int r6 = r3.mMatchConstraintDefaultWidth
            if (r6 == r11) goto L_0x0042
            if (r5 != r9) goto L_0x0042
            int r6 = r3.mMatchConstraintDefaultHeight
            if (r6 == r11) goto L_0x0042
            r6 = r11
            goto L_0x0043
        L_0x0042:
            r6 = r12
        L_0x0043:
            if (r6 == 0) goto L_0x0046
            goto L_0x0083
        L_0x0046:
            if (r4 != r9) goto L_0x0049
            r4 = r10
        L_0x0049:
            if (r5 != r9) goto L_0x004c
            r5 = r10
        L_0x004c:
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure r6 = r8.mMeasure
            r6.horizontalBehavior = r4
            r6.verticalBehavior = r5
            int r4 = r3.getWidth()
            r6.horizontalDimension = r4
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure r4 = r8.mMeasure
            int r5 = r3.getHeight()
            r4.verticalDimension = r5
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure r4 = r8.mMeasure
            r5 = r0
            androidx.constraintlayout.widget.ConstraintLayout$Measurer r5 = (androidx.constraintlayout.widget.ConstraintLayout.Measurer) r5
            r5.measure(r3, r4)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure r4 = r8.mMeasure
            int r4 = r4.measuredWidth
            r3.setWidth(r4)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure r4 = r8.mMeasure
            int r4 = r4.measuredHeight
            r3.setHeight(r4)
            androidx.constraintlayout.solver.widgets.analyzer.BasicMeasure$Measure r4 = r8.mMeasure
            int r4 = r4.measuredBaseline
            r3.mBaselineDistance = r4
            if (r4 <= 0) goto L_0x0080
            r4 = r11
            goto L_0x0081
        L_0x0080:
            r4 = r12
        L_0x0081:
            r3.hasBaseline = r4
        L_0x0083:
            int r2 = r2 + 1
            goto L_0x001c
        L_0x0086:
            r0 = r11
        L_0x0087:
            if (r0 != 0) goto L_0x0090
            r8.mMeasuredWidth = r12
            r8.mMeasuredHeight = r12
            r8.mNeedsCallFromSolver = r12
            return
        L_0x0090:
            int r13 = r8.mResolvedPaddingLeft
            int r14 = r8.mResolvedPaddingRight
            int r15 = r8.mPaddingTop
            int r7 = r8.mPaddingBottom
            r0 = 2
            int[] r6 = new int[r0]
            int r2 = r35 - r13
            int r2 = r2 - r14
            int r3 = r8.mOrientation
            if (r3 != r11) goto L_0x00a5
            int r2 = r37 - r15
            int r2 = r2 - r7
        L_0x00a5:
            r5 = r2
            r2 = -1
            if (r3 != 0) goto L_0x00b6
            int r3 = r8.mHorizontalStyle
            if (r3 != r2) goto L_0x00af
            r8.mHorizontalStyle = r12
        L_0x00af:
            int r3 = r8.mVerticalStyle
            if (r3 != r2) goto L_0x00c2
            r8.mVerticalStyle = r12
            goto L_0x00c2
        L_0x00b6:
            int r3 = r8.mHorizontalStyle
            if (r3 != r2) goto L_0x00bc
            r8.mHorizontalStyle = r12
        L_0x00bc:
            int r3 = r8.mVerticalStyle
            if (r3 != r2) goto L_0x00c2
            r8.mVerticalStyle = r12
        L_0x00c2:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r2 = r8.mWidgets
            r3 = r12
            r4 = r3
        L_0x00c6:
            int r12 = r8.mWidgetsCount
            r1 = 8
            if (r3 >= r12) goto L_0x00dc
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r12 = r8.mWidgets
            r12 = r12[r3]
            java.util.Objects.requireNonNull(r12)
            int r12 = r12.mVisibility
            if (r12 != r1) goto L_0x00d9
            int r4 = r4 + 1
        L_0x00d9:
            int r3 = r3 + 1
            goto L_0x00c6
        L_0x00dc:
            if (r4 <= 0) goto L_0x00fa
            int r12 = r12 - r4
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r2 = new androidx.constraintlayout.solver.widgets.ConstraintWidget[r12]
            r3 = 0
            r4 = 0
        L_0x00e3:
            int r12 = r8.mWidgetsCount
            if (r3 >= r12) goto L_0x00fb
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r12 = r8.mWidgets
            r12 = r12[r3]
            java.util.Objects.requireNonNull(r12)
            int r0 = r12.mVisibility
            if (r0 == r1) goto L_0x00f6
            r2[r4] = r12
            int r4 = r4 + 1
        L_0x00f6:
            int r3 = r3 + 1
            r0 = 2
            goto L_0x00e3
        L_0x00fa:
            r4 = r12
        L_0x00fb:
            r12 = r2
            r8.mDisplayedWidgets = r12
            r8.mDisplayedWidgetsCount = r4
            int r0 = r8.mWrapMode
            if (r0 == 0) goto L_0x052e
            if (r0 == r11) goto L_0x02da
            r1 = 2
            if (r0 == r1) goto L_0x0114
            r0 = r34
            r3 = r35
            r4 = r36
            r5 = r37
            r1 = r11
            goto L_0x0554
        L_0x0114:
            int r0 = r8.mOrientation
            if (r0 != 0) goto L_0x013d
            int r1 = r8.mMaxElementsWrap
            if (r1 > 0) goto L_0x013a
            r1 = 0
            r2 = 0
            r3 = 0
        L_0x011f:
            if (r1 >= r4) goto L_0x0139
            if (r1 <= 0) goto L_0x0126
            int r9 = r8.mHorizontalGap
            int r2 = r2 + r9
        L_0x0126:
            r9 = r12[r1]
            if (r9 != 0) goto L_0x012b
            goto L_0x0136
        L_0x012b:
            int r9 = r8.getWidgetWidth(r9, r5)
            int r9 = r9 + r2
            if (r9 <= r5) goto L_0x0133
            goto L_0x0139
        L_0x0133:
            int r3 = r3 + 1
            r2 = r9
        L_0x0136:
            int r1 = r1 + 1
            goto L_0x011f
        L_0x0139:
            r1 = r3
        L_0x013a:
            r2 = r1
            r1 = 0
            goto L_0x0160
        L_0x013d:
            int r1 = r8.mMaxElementsWrap
            if (r1 > 0) goto L_0x015f
            r1 = 0
            r2 = 0
            r3 = 0
        L_0x0144:
            if (r1 >= r4) goto L_0x015e
            if (r1 <= 0) goto L_0x014b
            int r9 = r8.mVerticalGap
            int r2 = r2 + r9
        L_0x014b:
            r9 = r12[r1]
            if (r9 != 0) goto L_0x0150
            goto L_0x015b
        L_0x0150:
            int r9 = r8.getWidgetHeight(r9, r5)
            int r9 = r9 + r2
            if (r9 <= r5) goto L_0x0158
            goto L_0x015e
        L_0x0158:
            int r3 = r3 + 1
            r2 = r9
        L_0x015b:
            int r1 = r1 + 1
            goto L_0x0144
        L_0x015e:
            r1 = r3
        L_0x015f:
            r2 = 0
        L_0x0160:
            int[] r3 = r8.mAlignedDimensions
            if (r3 != 0) goto L_0x0169
            r3 = 2
            int[] r3 = new int[r3]
            r8.mAlignedDimensions = r3
        L_0x0169:
            if (r1 != 0) goto L_0x016d
            if (r0 == r11) goto L_0x0171
        L_0x016d:
            if (r2 != 0) goto L_0x018d
            if (r0 != 0) goto L_0x018d
        L_0x0171:
            r3 = r35
            r10 = r4
            r9 = r8
            r17 = r13
            r18 = r14
            r19 = r15
            r4 = r36
            r13 = r6
            r14 = r7
            r15 = r12
            r7 = r1
            r12 = r13
            r1 = r34
            r6 = r0
            r8 = r2
            r0 = r9
            r2 = r11
            r11 = r5
            r5 = r37
            goto L_0x02a9
        L_0x018d:
            r3 = r36
            r9 = r4
            r10 = r5
            r17 = r13
            r18 = r14
            r19 = r15
            r20 = 0
            r4 = r37
            r5 = r0
            r13 = r6
            r14 = r7
            r0 = r8
            r15 = r12
            r7 = r2
            r12 = r13
            r2 = r35
            r6 = r1
            r1 = r34
        L_0x01a7:
            if (r20 != 0) goto L_0x02b8
            if (r5 != 0) goto L_0x01bb
            float r6 = (float) r9
            float r11 = (float) r7
            float r6 = r6 / r11
            r33 = r0
            r34 = r1
            double r0 = (double) r6
            double r0 = java.lang.Math.ceil(r0)
            int r0 = (int) r0
            r1 = r0
            r0 = r7
            goto L_0x01c9
        L_0x01bb:
            r33 = r0
            r34 = r1
            float r0 = (float) r9
            float r1 = (float) r6
            float r0 = r0 / r1
            double r0 = (double) r0
            double r0 = java.lang.Math.ceil(r0)
            int r0 = (int) r0
            r1 = r6
        L_0x01c9:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r6 = r8.mAlignedBiggestElementsInCols
            if (r6 == 0) goto L_0x01d6
            int r7 = r6.length
            if (r7 >= r0) goto L_0x01d1
            goto L_0x01d6
        L_0x01d1:
            r7 = 0
            java.util.Arrays.fill(r6, r7)
            goto L_0x01db
        L_0x01d6:
            r7 = 0
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r6 = new androidx.constraintlayout.solver.widgets.ConstraintWidget[r0]
            r8.mAlignedBiggestElementsInCols = r6
        L_0x01db:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r6 = r8.mAlignedBiggestElementsInRows
            if (r6 == 0) goto L_0x01e7
            int r11 = r6.length
            if (r11 >= r1) goto L_0x01e3
            goto L_0x01e7
        L_0x01e3:
            java.util.Arrays.fill(r6, r7)
            goto L_0x01eb
        L_0x01e7:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r6 = new androidx.constraintlayout.solver.widgets.ConstraintWidget[r1]
            r8.mAlignedBiggestElementsInRows = r6
        L_0x01eb:
            r6 = 0
        L_0x01ec:
            if (r6 >= r0) goto L_0x0244
            r7 = 0
        L_0x01ef:
            if (r7 >= r1) goto L_0x023d
            int r11 = r7 * r0
            int r11 = r11 + r6
            r35 = r2
            r2 = 1
            if (r5 != r2) goto L_0x01fd
            int r2 = r6 * r1
            int r11 = r2 + r7
        L_0x01fd:
            int r2 = r15.length
            if (r11 < r2) goto L_0x0201
            goto L_0x0205
        L_0x0201:
            r2 = r15[r11]
            if (r2 != 0) goto L_0x0208
        L_0x0205:
            r36 = r3
            goto L_0x0236
        L_0x0208:
            int r11 = r8.getWidgetWidth(r2, r10)
            r36 = r3
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r3 = r8.mAlignedBiggestElementsInCols
            r21 = r3[r6]
            if (r21 == 0) goto L_0x021c
            r3 = r3[r6]
            int r3 = r3.getWidth()
            if (r3 >= r11) goto L_0x0220
        L_0x021c:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r3 = r8.mAlignedBiggestElementsInCols
            r3[r6] = r2
        L_0x0220:
            int r3 = r8.getWidgetHeight(r2, r10)
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r11 = r8.mAlignedBiggestElementsInRows
            r21 = r11[r7]
            if (r21 == 0) goto L_0x0232
            r11 = r11[r7]
            int r11 = r11.getHeight()
            if (r11 >= r3) goto L_0x0236
        L_0x0232:
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r3 = r8.mAlignedBiggestElementsInRows
            r3[r7] = r2
        L_0x0236:
            int r7 = r7 + 1
            r2 = r35
            r3 = r36
            goto L_0x01ef
        L_0x023d:
            r35 = r2
            r36 = r3
            int r6 = r6 + 1
            goto L_0x01ec
        L_0x0244:
            r35 = r2
            r36 = r3
            r2 = 0
            r3 = 0
        L_0x024a:
            if (r2 >= r0) goto L_0x0260
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r6 = r8.mAlignedBiggestElementsInCols
            r6 = r6[r2]
            if (r6 == 0) goto L_0x025d
            if (r2 <= 0) goto L_0x0257
            int r7 = r8.mHorizontalGap
            int r3 = r3 + r7
        L_0x0257:
            int r6 = r8.getWidgetWidth(r6, r10)
            int r6 = r6 + r3
            r3 = r6
        L_0x025d:
            int r2 = r2 + 1
            goto L_0x024a
        L_0x0260:
            r2 = 0
            r6 = 0
        L_0x0262:
            if (r2 >= r1) goto L_0x0278
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r7 = r8.mAlignedBiggestElementsInRows
            r7 = r7[r2]
            if (r7 == 0) goto L_0x0275
            if (r2 <= 0) goto L_0x026f
            int r11 = r8.mVerticalGap
            int r6 = r6 + r11
        L_0x026f:
            int r7 = r8.getWidgetHeight(r7, r10)
            int r7 = r7 + r6
            r6 = r7
        L_0x0275:
            int r2 = r2 + 1
            goto L_0x0262
        L_0x0278:
            r2 = 0
            r13[r2] = r3
            r2 = 1
            r13[r2] = r6
            if (r5 != 0) goto L_0x0287
            if (r3 <= r10) goto L_0x029a
            if (r0 <= r2) goto L_0x029a
            int r0 = r0 + -1
            goto L_0x028d
        L_0x0287:
            if (r6 <= r10) goto L_0x029a
            if (r1 <= r2) goto L_0x029a
            int r1 = r1 + -1
        L_0x028d:
            r7 = r0
            r6 = r1
            r0 = r33
            r1 = r34
            r3 = r36
            r11 = r2
            r2 = r35
            goto L_0x01a7
        L_0x029a:
            r3 = r35
            r7 = r1
            r6 = r5
            r11 = r10
            r1 = r34
            r5 = r4
            r10 = r9
            r4 = r36
            r9 = r8
            r8 = r0
            r0 = r33
        L_0x02a9:
            r20 = r2
            r2 = r3
            r3 = r4
            r4 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r20
            goto L_0x01a7
        L_0x02b8:
            r33 = r0
            r34 = r1
            r35 = r2
            r36 = r3
            r2 = r11
            int[] r0 = r8.mAlignedDimensions
            r1 = 0
            r0[r1] = r7
            r0[r2] = r6
            r8 = r33
            r0 = r34
            r1 = r35
            r2 = r36
            r6 = r12
            r7 = r14
            r13 = r17
            r14 = r18
            r15 = r19
            goto L_0x0550
        L_0x02da:
            int r11 = r8.mOrientation
            if (r4 != 0) goto L_0x02e8
            r0 = r34
            r1 = r35
            r2 = r36
            r4 = r37
            goto L_0x0550
        L_0x02e8:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r0 = r8.mChainList
            r0.clear()
            androidx.constraintlayout.solver.widgets.Flow$WidgetsList r3 = new androidx.constraintlayout.solver.widgets.Flow$WidgetsList
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r8.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r8.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r8.mRight
            r16 = r6
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r8.mBottom
            r17 = r0
            r0 = r3
            r18 = r1
            r1 = r33
            r19 = r2
            r2 = r11
            r27 = r13
            r13 = r3
            r3 = r19
            r28 = r14
            r14 = r4
            r4 = r18
            r29 = r5
            r5 = r17
            r30 = r16
            r31 = r7
            r7 = r29
            r0.<init>(r2, r3, r4, r5, r6, r7)
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r0 = r8.mChainList
            r0.add(r13)
            if (r11 != 0) goto L_0x03ad
            r3 = r13
            r0 = 0
            r1 = 0
            r13 = 0
        L_0x0325:
            if (r13 >= r14) goto L_0x03a5
            r7 = r12[r13]
            r6 = r29
            int r16 = r8.getWidgetWidth(r7, r6)
            java.util.Objects.requireNonNull(r7)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r7.mListDimensionBehaviors
            r4 = 0
            r2 = r2[r4]
            if (r2 != r9) goto L_0x033b
            int r0 = r0 + 1
        L_0x033b:
            r17 = r0
            int r0 = r8.mHorizontalGap
            int r2 = r1 + r0
            int r2 = r2 + r16
            if (r2 <= r6) goto L_0x034b
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r3.biggest
            if (r2 == 0) goto L_0x034b
            r2 = 1
            goto L_0x034c
        L_0x034b:
            r2 = 0
        L_0x034c:
            if (r2 != 0) goto L_0x0359
            if (r13 <= 0) goto L_0x0359
            int r4 = r8.mMaxElementsWrap
            if (r4 <= 0) goto L_0x0359
            int r4 = r13 % r4
            if (r4 != 0) goto L_0x0359
            r2 = 1
        L_0x0359:
            if (r2 == 0) goto L_0x0387
            androidx.constraintlayout.solver.widgets.Flow$WidgetsList r5 = new androidx.constraintlayout.solver.widgets.Flow$WidgetsList
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r8.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r8.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r8.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r8.mBottom
            r0 = r5
            r18 = r1
            r1 = r33
            r19 = r2
            r2 = r11
            r29 = r15
            r15 = r5
            r5 = r19
            r32 = r6
            r6 = r18
            r18 = r10
            r10 = r7
            r7 = r32
            r0.<init>(r2, r3, r4, r5, r6, r7)
            r15.mStartIndex = r13
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r0 = r8.mChainList
            r0.add(r15)
            r3 = r15
            goto L_0x0395
        L_0x0387:
            r32 = r6
            r18 = r10
            r29 = r15
            r10 = r7
            if (r13 <= 0) goto L_0x0395
            int r0 = r0 + r16
            int r0 = r0 + r1
            r1 = r0
            goto L_0x0397
        L_0x0395:
            r1 = r16
        L_0x0397:
            r3.add(r10)
            int r13 = r13 + 1
            r0 = r17
            r10 = r18
            r15 = r29
            r29 = r32
            goto L_0x0325
        L_0x03a5:
            r18 = r10
            r32 = r29
            r29 = r15
            goto L_0x0422
        L_0x03ad:
            r18 = r10
            r32 = r29
            r29 = r15
            r3 = r13
            r0 = 0
            r1 = 0
            r10 = 0
        L_0x03b7:
            if (r10 >= r14) goto L_0x0422
            r13 = r12[r10]
            r15 = r32
            int r16 = r8.getWidgetHeight(r13, r15)
            java.util.Objects.requireNonNull(r13)
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r2 = r13.mListDimensionBehaviors
            r4 = 1
            r2 = r2[r4]
            if (r2 != r9) goto L_0x03cd
            int r0 = r0 + 1
        L_0x03cd:
            r17 = r0
            int r0 = r8.mVerticalGap
            int r2 = r1 + r0
            int r2 = r2 + r16
            if (r2 <= r15) goto L_0x03dd
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r3.biggest
            if (r2 == 0) goto L_0x03dd
            r2 = 1
            goto L_0x03de
        L_0x03dd:
            r2 = 0
        L_0x03de:
            if (r2 != 0) goto L_0x03eb
            if (r10 <= 0) goto L_0x03eb
            int r4 = r8.mMaxElementsWrap
            if (r4 <= 0) goto L_0x03eb
            int r4 = r10 % r4
            if (r4 != 0) goto L_0x03eb
            r2 = 1
        L_0x03eb:
            if (r2 == 0) goto L_0x040b
            androidx.constraintlayout.solver.widgets.Flow$WidgetsList r7 = new androidx.constraintlayout.solver.widgets.Flow$WidgetsList
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r8.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r8.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r8.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r8.mBottom
            r0 = r7
            r1 = r33
            r2 = r11
            r19 = r9
            r9 = r7
            r7 = r15
            r0.<init>(r2, r3, r4, r5, r6, r7)
            r9.mStartIndex = r10
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r0 = r8.mChainList
            r0.add(r9)
            r3 = r9
            goto L_0x0414
        L_0x040b:
            r19 = r9
            if (r10 <= 0) goto L_0x0414
            int r0 = r0 + r16
            int r0 = r0 + r1
            r1 = r0
            goto L_0x0416
        L_0x0414:
            r1 = r16
        L_0x0416:
            r3.add(r13)
            int r10 = r10 + 1
            r32 = r15
            r0 = r17
            r9 = r19
            goto L_0x03b7
        L_0x0422:
            r15 = r32
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r1 = r8.mChainList
            int r1 = r1.size()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r8.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r8.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r8.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r8.mBottom
            int r6 = r8.mResolvedPaddingLeft
            int r7 = r8.mPaddingTop
            int r9 = r8.mResolvedPaddingRight
            int r10 = r8.mPaddingBottom
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r12 = r8.mListDimensionBehaviors
            r13 = 0
            r14 = r12[r13]
            r13 = r18
            if (r14 == r13) goto L_0x044b
            r14 = 1
            r12 = r12[r14]
            if (r12 != r13) goto L_0x0449
            goto L_0x044b
        L_0x0449:
            r12 = 0
            goto L_0x044c
        L_0x044b:
            r12 = 1
        L_0x044c:
            if (r0 <= 0) goto L_0x0473
            if (r12 == 0) goto L_0x0473
            r0 = 0
        L_0x0451:
            if (r0 >= r1) goto L_0x0473
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r12 = r8.mChainList
            java.lang.Object r12 = r12.get(r0)
            androidx.constraintlayout.solver.widgets.Flow$WidgetsList r12 = (androidx.constraintlayout.solver.widgets.Flow.WidgetsList) r12
            if (r11 != 0) goto L_0x0467
            int r13 = r12.getWidth()
            int r13 = r15 - r13
            r12.measureMatchConstraints(r13)
            goto L_0x0470
        L_0x0467:
            int r13 = r12.getHeight()
            int r13 = r15 - r13
            r12.measureMatchConstraints(r13)
        L_0x0470:
            int r0 = r0 + 1
            goto L_0x0451
        L_0x0473:
            r0 = 0
            r12 = 0
            r13 = 0
        L_0x0476:
            if (r0 >= r1) goto L_0x0527
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r14 = r8.mChainList
            java.lang.Object r14 = r14.get(r0)
            androidx.constraintlayout.solver.widgets.Flow$WidgetsList r14 = (androidx.constraintlayout.solver.widgets.Flow.WidgetsList) r14
            if (r11 != 0) goto L_0x04d2
            int r5 = r1 + -1
            if (r0 >= r5) goto L_0x0498
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r5 = r8.mChainList
            int r10 = r0 + 1
            java.lang.Object r5 = r5.get(r10)
            androidx.constraintlayout.solver.widgets.Flow$WidgetsList r5 = (androidx.constraintlayout.solver.widgets.Flow.WidgetsList) r5
            androidx.constraintlayout.solver.widgets.ConstraintWidget r5 = r5.biggest
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r5.mTop
            r32 = r1
            r10 = 0
            goto L_0x049e
        L_0x0498:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r8.mBottom
            int r10 = r8.mPaddingBottom
            r32 = r1
        L_0x049e:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r14.biggest
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mBottom
            r16 = r14
            r17 = r11
            r18 = r2
            r19 = r3
            r20 = r4
            r21 = r5
            r22 = r6
            r23 = r7
            r24 = r9
            r25 = r10
            r26 = r15
            r16.setup(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26)
            int r3 = r14.getWidth()
            int r3 = java.lang.Math.max(r12, r3)
            int r7 = r14.getHeight()
            int r7 = r7 + r13
            if (r0 <= 0) goto L_0x04cd
            int r12 = r8.mVerticalGap
            int r7 = r7 + r12
        L_0x04cd:
            r12 = r3
            r13 = r7
            r7 = 0
            r3 = r1
            goto L_0x0521
        L_0x04d2:
            r32 = r1
            int r1 = r32 + -1
            if (r0 >= r1) goto L_0x04e8
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r1 = r8.mChainList
            int r4 = r0 + 1
            java.lang.Object r1 = r1.get(r4)
            androidx.constraintlayout.solver.widgets.Flow$WidgetsList r1 = (androidx.constraintlayout.solver.widgets.Flow.WidgetsList) r1
            androidx.constraintlayout.solver.widgets.ConstraintWidget r1 = r1.biggest
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mLeft
            r4 = 0
            goto L_0x04ec
        L_0x04e8:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r8.mRight
            int r4 = r8.mResolvedPaddingRight
        L_0x04ec:
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r14.biggest
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r9.mRight
            r16 = r14
            r17 = r11
            r18 = r2
            r19 = r3
            r20 = r1
            r21 = r5
            r22 = r6
            r23 = r7
            r24 = r4
            r25 = r10
            r26 = r15
            r16.setup(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26)
            int r2 = r14.getWidth()
            int r2 = r2 + r12
            int r6 = r14.getHeight()
            int r6 = java.lang.Math.max(r13, r6)
            if (r0 <= 0) goto L_0x051b
            int r12 = r8.mHorizontalGap
            int r2 = r2 + r12
        L_0x051b:
            r12 = r2
            r13 = r6
            r2 = r9
            r6 = 0
            r9 = r4
            r4 = r1
        L_0x0521:
            int r0 = r0 + 1
            r1 = r32
            goto L_0x0476
        L_0x0527:
            r0 = 0
            r30[r0] = r12
            r0 = 1
            r30[r0] = r13
            goto L_0x053e
        L_0x052e:
            r30 = r6
            r31 = r7
            r27 = r13
            r28 = r14
            r29 = r15
            r14 = r4
            r15 = r5
            int r2 = r8.mOrientation
            if (r14 != 0) goto L_0x0557
        L_0x053e:
            r0 = r34
            r1 = r35
            r2 = r36
            r4 = r37
            r13 = r27
            r14 = r28
            r15 = r29
            r6 = r30
            r7 = r31
        L_0x0550:
            r3 = r1
            r5 = r4
            r1 = 1
            r4 = r2
        L_0x0554:
            r2 = 0
            goto L_0x05e6
        L_0x0557:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r0 = r8.mChainList
            int r0 = r0.size()
            if (r0 != 0) goto L_0x0576
            androidx.constraintlayout.solver.widgets.Flow$WidgetsList r9 = new androidx.constraintlayout.solver.widgets.Flow$WidgetsList
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r8.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r8.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r8.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r6 = r8.mBottom
            r0 = r9
            r1 = r33
            r7 = r15
            r0.<init>(r2, r3, r4, r5, r6, r7)
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r0 = r8.mChainList
            r0.add(r9)
            goto L_0x05bb
        L_0x0576:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.Flow$WidgetsList> r0 = r8.mChainList
            r1 = 0
            java.lang.Object r0 = r0.get(r1)
            r9 = r0
            androidx.constraintlayout.solver.widgets.Flow$WidgetsList r9 = (androidx.constraintlayout.solver.widgets.Flow.WidgetsList) r9
            java.util.Objects.requireNonNull(r9)
            r9.biggestDimension = r1
            r0 = 0
            r9.biggest = r0
            r9.mWidth = r1
            r9.mHeight = r1
            r9.mStartIndex = r1
            r9.mCount = r1
            r9.mNbMatchConstraintsWidgets = r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r0 = r8.mLeft
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r8.mTop
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r8.mRight
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r8.mBottom
            int r5 = r8.mResolvedPaddingLeft
            int r6 = r8.mPaddingTop
            int r7 = r8.mResolvedPaddingRight
            int r10 = r8.mPaddingBottom
            r16 = r9
            r17 = r2
            r18 = r0
            r19 = r1
            r20 = r3
            r21 = r4
            r22 = r5
            r23 = r6
            r24 = r7
            r25 = r10
            r26 = r15
            r16.setup(r17, r18, r19, r20, r21, r22, r23, r24, r25, r26)
        L_0x05bb:
            r2 = 0
        L_0x05bc:
            if (r2 >= r14) goto L_0x05c6
            r0 = r12[r2]
            r9.add(r0)
            int r2 = r2 + 1
            goto L_0x05bc
        L_0x05c6:
            int r0 = r9.getWidth()
            r2 = 0
            r30[r2] = r0
            int r0 = r9.getHeight()
            r1 = 1
            r30[r1] = r0
            r0 = r34
            r3 = r35
            r4 = r36
            r5 = r37
            r13 = r27
            r14 = r28
            r15 = r29
            r6 = r30
            r7 = r31
        L_0x05e6:
            r9 = r6[r2]
            int r9 = r9 + r13
            int r9 = r9 + r14
            r6 = r6[r1]
            int r6 = r6 + r15
            int r6 = r6 + r7
            r7 = -2147483648(0xffffffff80000000, float:-0.0)
            r10 = 1073741824(0x40000000, float:2.0)
            if (r0 != r10) goto L_0x05f6
            r0 = r3
            goto L_0x0602
        L_0x05f6:
            if (r0 != r7) goto L_0x05fd
            int r0 = java.lang.Math.min(r9, r3)
            goto L_0x0602
        L_0x05fd:
            if (r0 != 0) goto L_0x0601
            r0 = r9
            goto L_0x0602
        L_0x0601:
            r0 = r2
        L_0x0602:
            if (r4 != r10) goto L_0x0606
            r3 = r5
            goto L_0x0612
        L_0x0606:
            if (r4 != r7) goto L_0x060d
            int r3 = java.lang.Math.min(r6, r5)
            goto L_0x0612
        L_0x060d:
            if (r4 != 0) goto L_0x0611
            r3 = r6
            goto L_0x0612
        L_0x0611:
            r3 = r2
        L_0x0612:
            r8.mMeasuredWidth = r0
            r8.mMeasuredHeight = r3
            r8.setWidth(r0)
            r8.setHeight(r3)
            int r0 = r8.mWidgetsCount
            if (r0 <= 0) goto L_0x0622
            r11 = r1
            goto L_0x0623
        L_0x0622:
            r11 = r2
        L_0x0623:
            r8.mNeedsCallFromSolver = r11
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Flow.measure(int, int, int, int):void");
    }

    public final void addToSolver(LinearSystem linearSystem) {
        boolean z;
        boolean z2;
        ConstraintWidget constraintWidget;
        int i;
        super.addToSolver(linearSystem);
        ConstraintWidget constraintWidget2 = this.mParent;
        if (constraintWidget2 != null) {
            ConstraintWidgetContainer constraintWidgetContainer = (ConstraintWidgetContainer) constraintWidget2;
            Objects.requireNonNull(constraintWidgetContainer);
            z = constraintWidgetContainer.mIsRtl;
        } else {
            z = false;
        }
        int i2 = this.mWrapMode;
        if (i2 != 0) {
            if (i2 == 1) {
                int size = this.mChainList.size();
                for (int i3 = 0; i3 < size; i3++) {
                    WidgetsList widgetsList = this.mChainList.get(i3);
                    if (i3 == size - 1) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    widgetsList.createConstraints(z, i3, z2);
                }
            } else if (!(i2 != 2 || this.mAlignedDimensions == null || this.mAlignedBiggestElementsInCols == null || this.mAlignedBiggestElementsInRows == null)) {
                for (int i4 = 0; i4 < this.mDisplayedWidgetsCount; i4++) {
                    this.mDisplayedWidgets[i4].resetAnchors();
                }
                int[] iArr = this.mAlignedDimensions;
                int i5 = iArr[0];
                int i6 = iArr[1];
                ConstraintWidget constraintWidget3 = null;
                for (int i7 = 0; i7 < i5; i7++) {
                    if (z) {
                        i = (i5 - i7) - 1;
                    } else {
                        i = i7;
                    }
                    ConstraintWidget constraintWidget4 = this.mAlignedBiggestElementsInCols[i];
                    if (!(constraintWidget4 == null || constraintWidget4.mVisibility == 8)) {
                        if (i7 == 0) {
                            constraintWidget4.connect(constraintWidget4.mLeft, this.mLeft, this.mResolvedPaddingLeft);
                            constraintWidget4.mHorizontalChainStyle = this.mHorizontalStyle;
                            constraintWidget4.mHorizontalBiasPercent = this.mHorizontalBias;
                        }
                        if (i7 == i5 - 1) {
                            constraintWidget4.connect(constraintWidget4.mRight, this.mRight, this.mResolvedPaddingRight);
                        }
                        if (i7 > 0) {
                            constraintWidget4.connect(constraintWidget4.mLeft, constraintWidget3.mRight, this.mHorizontalGap);
                            constraintWidget3.connect(constraintWidget3.mRight, constraintWidget4.mLeft, 0);
                        }
                        constraintWidget3 = constraintWidget4;
                    }
                }
                for (int i8 = 0; i8 < i6; i8++) {
                    ConstraintWidget constraintWidget5 = this.mAlignedBiggestElementsInRows[i8];
                    if (!(constraintWidget5 == null || constraintWidget5.mVisibility == 8)) {
                        if (i8 == 0) {
                            constraintWidget5.connect(constraintWidget5.mTop, this.mTop, this.mPaddingTop);
                            constraintWidget5.mVerticalChainStyle = this.mVerticalStyle;
                            constraintWidget5.mVerticalBiasPercent = this.mVerticalBias;
                        }
                        if (i8 == i6 - 1) {
                            constraintWidget5.connect(constraintWidget5.mBottom, this.mBottom, this.mPaddingBottom);
                        }
                        if (i8 > 0) {
                            constraintWidget5.connect(constraintWidget5.mTop, constraintWidget3.mBottom, this.mVerticalGap);
                            constraintWidget3.connect(constraintWidget3.mBottom, constraintWidget5.mTop, 0);
                        }
                        constraintWidget3 = constraintWidget5;
                    }
                }
                for (int i9 = 0; i9 < i5; i9++) {
                    for (int i10 = 0; i10 < i6; i10++) {
                        int i11 = (i10 * i5) + i9;
                        if (this.mOrientation == 1) {
                            i11 = (i9 * i6) + i10;
                        }
                        ConstraintWidget[] constraintWidgetArr = this.mDisplayedWidgets;
                        if (!(i11 >= constraintWidgetArr.length || (constraintWidget = constraintWidgetArr[i11]) == null || constraintWidget.mVisibility == 8)) {
                            ConstraintWidget constraintWidget6 = this.mAlignedBiggestElementsInCols[i9];
                            ConstraintWidget constraintWidget7 = this.mAlignedBiggestElementsInRows[i10];
                            if (constraintWidget != constraintWidget6) {
                                constraintWidget.connect(constraintWidget.mLeft, constraintWidget6.mLeft, 0);
                                constraintWidget.connect(constraintWidget.mRight, constraintWidget6.mRight, 0);
                            }
                            if (constraintWidget != constraintWidget7) {
                                constraintWidget.connect(constraintWidget.mTop, constraintWidget7.mTop, 0);
                                constraintWidget.connect(constraintWidget.mBottom, constraintWidget7.mBottom, 0);
                            }
                        }
                    }
                }
            }
        } else if (this.mChainList.size() > 0) {
            this.mChainList.get(0).createConstraints(z, 0, true);
        }
        this.mNeedsCallFromSolver = false;
    }

    public final void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        super.copy(constraintWidget, hashMap);
        Flow flow = (Flow) constraintWidget;
        this.mHorizontalStyle = flow.mHorizontalStyle;
        this.mVerticalStyle = flow.mVerticalStyle;
        this.mFirstHorizontalStyle = flow.mFirstHorizontalStyle;
        this.mFirstVerticalStyle = flow.mFirstVerticalStyle;
        this.mLastHorizontalStyle = flow.mLastHorizontalStyle;
        this.mLastVerticalStyle = flow.mLastVerticalStyle;
        this.mHorizontalBias = flow.mHorizontalBias;
        this.mVerticalBias = flow.mVerticalBias;
        this.mFirstHorizontalBias = flow.mFirstHorizontalBias;
        this.mFirstVerticalBias = flow.mFirstVerticalBias;
        this.mLastHorizontalBias = flow.mLastHorizontalBias;
        this.mLastVerticalBias = flow.mLastVerticalBias;
        this.mHorizontalGap = flow.mHorizontalGap;
        this.mVerticalGap = flow.mVerticalGap;
        this.mHorizontalAlign = flow.mHorizontalAlign;
        this.mVerticalAlign = flow.mVerticalAlign;
        this.mWrapMode = flow.mWrapMode;
        this.mMaxElementsWrap = flow.mMaxElementsWrap;
        this.mOrientation = flow.mOrientation;
    }
}
