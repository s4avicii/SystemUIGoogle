package com.android.systemui.statusbar.policy;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.IndentingPrintWriter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.ContrastColorUtil;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$array;
import com.android.systemui.R$styleable;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class SmartReplyView extends ViewGroup {
    public static final SmartReplyView$$ExternalSyntheticLambda0 DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR = SmartReplyView$$ExternalSyntheticLambda0.INSTANCE;
    public static final int MEASURE_SPEC_ANY_LENGTH = View.MeasureSpec.makeMeasureSpec(0, 0);
    public final BreakIterator mBreakIterator;
    public PriorityQueue<Button> mCandidateButtonQueueForSqueezing;
    public int mCurrentBackgroundColor;
    public boolean mCurrentColorized;
    public int mCurrentRippleColor;
    public int mCurrentStrokeColor;
    public int mCurrentTextColor;
    public final int mDefaultBackgroundColor;
    public final int mDefaultStrokeColor;
    public final int mDefaultTextColor;
    public final int mDefaultTextColorDarkBg;
    public boolean mDidHideSystemReplies;
    public final int mHeightUpperLimit = R$array.getFontScaledHeight(this.mContext, C1777R.dimen.smart_reply_button_max_height);
    public long mLastDispatchDrawTime;
    public long mLastDrawChildTime;
    public long mLastMeasureTime;
    public int mMaxNumActions;
    public int mMaxSqueezeRemeasureAttempts;
    public int mMinNumSystemGeneratedReplies;
    public final double mMinStrokeContrast;
    public final int mRippleColor;
    public final int mRippleColorDarkBg;
    public boolean mSmartRepliesGeneratedByAssistant = false;
    public View mSmartReplyContainer;
    public final int mSpacing;
    public final int mStrokeWidth;
    public int mTotalSqueezeRemeasureAttempts;

    public enum SmartButtonType {
        REPLY,
        ACTION
    }

    public static class SmartSuggestionMeasures {
        public int mMaxChildHeight;
        public int mMeasuredWidth;

        public final Object clone() throws CloneNotSupportedException {
            return new SmartSuggestionMeasures(this.mMeasuredWidth, this.mMaxChildHeight);
        }

        public SmartSuggestionMeasures(int i, int i2) {
            this.mMeasuredWidth = i;
            this.mMaxChildHeight = i2;
        }
    }

    public static class SmartActions {
        public final List<Notification.Action> actions;
        public final boolean fromAssistant;

        public SmartActions(List<Notification.Action> list, boolean z) {
            this.actions = list;
            this.fromAssistant = z;
        }
    }

    public static class SmartReplies {
        public final List<CharSequence> choices;
        public final boolean fromAssistant;
        public final PendingIntent pendingIntent;
        public final RemoteInput remoteInput;

        public SmartReplies(List<CharSequence> list, RemoteInput remoteInput2, PendingIntent pendingIntent2, boolean z) {
            this.choices = list;
            this.remoteInput = remoteInput2;
            this.pendingIntent = pendingIntent2;
            this.fromAssistant = z;
        }
    }

    public final ArrayList filterActionsOrReplies(SmartButtonType smartButtonType) {
        ArrayList arrayList = new ArrayList();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (childAt.getVisibility() == 0 && (childAt instanceof Button) && layoutParams.mButtonType == smartButtonType) {
                arrayList.add(childAt);
            }
        }
        return arrayList;
    }

    public final ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-2, -2);
    }

    public final LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.mContext, attributeSet);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:82:0x01ec, code lost:
        r27 = r5;
     */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x025f  */
    /* JADX WARNING: Removed duplicated region for block: B:129:0x030e  */
    /* JADX WARNING: Removed duplicated region for block: B:137:0x0348  */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x0270 A[SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onMeasure(int r30, int r31) {
        /*
            r29 = this;
            r0 = r29
            r1 = r31
            com.android.systemui.statusbar.policy.SmartReplyView$SmartButtonType r2 = com.android.systemui.statusbar.policy.SmartReplyView.SmartButtonType.REPLY
            com.android.systemui.statusbar.policy.SmartReplyView$SmartButtonType r3 = com.android.systemui.statusbar.policy.SmartReplyView.SmartButtonType.ACTION
            int r4 = android.view.View.MeasureSpec.getMode(r30)
            if (r4 != 0) goto L_0x0012
            r4 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x0016
        L_0x0012:
            int r4 = android.view.View.MeasureSpec.getSize(r30)
        L_0x0016:
            int r6 = r29.getChildCount()
            r7 = 0
            r8 = r7
        L_0x001c:
            if (r8 >= r6) goto L_0x0034
            android.view.View r9 = r0.getChildAt(r8)
            android.view.ViewGroup$LayoutParams r9 = r9.getLayoutParams()
            com.android.systemui.statusbar.policy.SmartReplyView$LayoutParams r9 = (com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams) r9
            r9.show = r7
            r9.squeezeStatus = r7
            java.lang.String r10 = "reset"
            r9.mNoShowReason = r10
            int r8 = r8 + 1
            goto L_0x001c
        L_0x0034:
            r0.mTotalSqueezeRemeasureAttempts = r7
            java.util.PriorityQueue<android.widget.Button> r6 = r0.mCandidateButtonQueueForSqueezing
            boolean r6 = r6.isEmpty()
            java.lang.String r8 = "SmartReplyView"
            if (r6 != 0) goto L_0x004a
            java.lang.String r6 = "Single line button queue leaked between onMeasure calls"
            android.util.Log.wtf(r8, r6)
            java.util.PriorityQueue<android.widget.Button> r6 = r0.mCandidateButtonQueueForSqueezing
            r6.clear()
        L_0x004a:
            com.android.systemui.statusbar.policy.SmartReplyView$SmartSuggestionMeasures r6 = new com.android.systemui.statusbar.policy.SmartReplyView$SmartSuggestionMeasures
            int r9 = r0.mPaddingLeft
            int r10 = r0.mPaddingRight
            int r9 = r9 + r10
            r6.<init>(r9, r7)
            java.util.ArrayList r9 = r0.filterActionsOrReplies(r3)
            java.util.ArrayList r10 = r0.filterActionsOrReplies(r2)
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>(r9)
            r11.addAll(r10)
            java.util.ArrayList r9 = new java.util.ArrayList
            r9.<init>()
            r12 = 0
            int r13 = r0.mMaxNumActions
            java.util.Iterator r11 = r11.iterator()
            r14 = r7
            r15 = r14
        L_0x0072:
            boolean r16 = r11.hasNext()
            if (r16 == 0) goto L_0x02d5
            java.lang.Object r16 = r11.next()
            r7 = r16
            android.view.View r7 = (android.view.View) r7
            android.view.ViewGroup$LayoutParams r16 = r7.getLayoutParams()
            r5 = r16
            com.android.systemui.statusbar.policy.SmartReplyView$LayoutParams r5 = (com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams) r5
            r16 = r11
            r11 = -1
            if (r13 == r11) goto L_0x009e
            com.android.systemui.statusbar.policy.SmartReplyView$SmartButtonType r11 = r5.mButtonType
            if (r11 != r3) goto L_0x009e
            if (r14 < r13) goto L_0x009e
            java.lang.String r7 = "max-actions-shown"
            r5.mNoShowReason = r7
            r18 = r10
            r19 = r13
            r20 = r14
            goto L_0x00da
        L_0x009e:
            boolean r11 = r7 instanceof android.widget.TextView
            if (r11 == 0) goto L_0x00ab
            r11 = r7
            android.widget.TextView r11 = (android.widget.TextView) r11
            r11.nullLayouts()
            r7.forceLayout()
        L_0x00ab:
            int r11 = MEASURE_SPEC_ANY_LENGTH
            r7.measure(r11, r1)
            r11 = r7
            android.widget.Button r11 = (android.widget.Button) r11
            android.text.Layout r18 = r11.getLayout()
            r19 = r13
            java.lang.String r13 = "Button layout is null after measure."
            if (r18 != 0) goto L_0x00c0
            android.util.Log.wtf(r8, r13)
        L_0x00c0:
            r9.add(r7)
            r18 = r10
            int r10 = r11.getLineCount()
            r20 = r14
            r14 = 1
            if (r10 >= r14) goto L_0x00d3
            java.lang.String r7 = "line-count-0"
            r5.mNoShowReason = r7
            goto L_0x00da
        L_0x00d3:
            r14 = 2
            if (r10 <= r14) goto L_0x00e1
            java.lang.String r7 = "line-count-3+"
            r5.mNoShowReason = r7
        L_0x00da:
            r22 = r2
            r2 = r3
            r14 = r20
            goto L_0x02c7
        L_0x00e1:
            r14 = 1
            if (r10 != r14) goto L_0x00e9
            java.util.PriorityQueue<android.widget.Button> r10 = r0.mCandidateButtonQueueForSqueezing
            r10.add(r11)
        L_0x00e9:
            java.util.Objects.requireNonNull(r6)
            com.android.systemui.statusbar.policy.SmartReplyView$SmartSuggestionMeasures r10 = new com.android.systemui.statusbar.policy.SmartReplyView$SmartSuggestionMeasures
            int r11 = r6.mMeasuredWidth
            int r14 = r6.mMaxChildHeight
            r10.<init>(r11, r14)
            r21 = r10
            if (r12 != 0) goto L_0x0102
            com.android.systemui.statusbar.policy.SmartReplyView$SmartButtonType r10 = r5.mButtonType
            if (r10 != r2) goto L_0x0102
            com.android.systemui.statusbar.policy.SmartReplyView$SmartSuggestionMeasures r12 = new com.android.systemui.statusbar.policy.SmartReplyView$SmartSuggestionMeasures
            r12.<init>(r11, r14)
        L_0x0102:
            if (r15 != 0) goto L_0x0106
            r10 = 0
            goto L_0x0108
        L_0x0106:
            int r10 = r0.mSpacing
        L_0x0108:
            int r11 = r7.getMeasuredWidth()
            int r7 = r7.getMeasuredHeight()
            int r14 = r6.mMeasuredWidth
            int r10 = r10 + r11
            int r10 = r10 + r14
            r6.mMeasuredWidth = r10
            int r10 = r6.mMaxChildHeight
            int r7 = java.lang.Math.max(r10, r7)
            r6.mMaxChildHeight = r7
            int r7 = r6.mMeasuredWidth
            if (r7 <= r4) goto L_0x02a8
        L_0x0122:
            int r7 = r6.mMeasuredWidth
            if (r7 <= r4) goto L_0x027e
            java.util.PriorityQueue<android.widget.Button> r7 = r0.mCandidateButtonQueueForSqueezing
            boolean r7 = r7.isEmpty()
            if (r7 != 0) goto L_0x027e
            java.util.PriorityQueue<android.widget.Button> r7 = r0.mCandidateButtonQueueForSqueezing
            java.lang.Object r7 = r7.poll()
            android.widget.Button r7 = (android.widget.Button) r7
            java.lang.CharSequence r10 = r7.getText()
            java.lang.String r10 = r10.toString()
            android.text.method.TransformationMethod r11 = r7.getTransformationMethod()
            if (r11 != 0) goto L_0x0145
            goto L_0x014d
        L_0x0145:
            java.lang.CharSequence r10 = r11.getTransformation(r10, r7)
            java.lang.String r10 = r10.toString()
        L_0x014d:
            int r11 = r10.length()
            java.text.BreakIterator r14 = r0.mBreakIterator
            r14.setText(r10)
            java.text.BreakIterator r14 = r0.mBreakIterator
            r22 = r2
            int r2 = r11 / 2
            int r2 = r14.preceding(r2)
            r14 = -1
            if (r2 != r14) goto L_0x0177
            java.text.BreakIterator r2 = r0.mBreakIterator
            int r2 = r2.next()
            if (r2 != r14) goto L_0x0177
            r24 = r3
            r27 = r5
            r23 = r12
            r25 = r15
            r0 = -1
            r14 = -1
            goto L_0x01fb
        L_0x0177:
            android.text.TextPaint r2 = r7.getPaint()
            java.text.BreakIterator r14 = r0.mBreakIterator
            int r14 = r14.current()
            r24 = r3
            r23 = r12
            r12 = 0
            float r3 = android.text.Layout.getDesiredWidth(r10, r12, r14, r2)
            float r12 = android.text.Layout.getDesiredWidth(r10, r14, r11, r2)
            float r14 = java.lang.Math.max(r3, r12)
            int r3 = (r3 > r12 ? 1 : (r3 == r12 ? 0 : -1))
            if (r3 == 0) goto L_0x01ef
            if (r3 <= 0) goto L_0x019a
            r3 = 1
            goto L_0x019b
        L_0x019a:
            r3 = 0
        L_0x019b:
            int r12 = r0.mMaxSqueezeRemeasureAttempts
            r25 = r15
            r15 = 0
        L_0x01a0:
            if (r15 >= r12) goto L_0x01ec
            r26 = r12
            int r12 = r0.mTotalSqueezeRemeasureAttempts
            r17 = 1
            int r12 = r12 + 1
            r0.mTotalSqueezeRemeasureAttempts = r12
            java.text.BreakIterator r12 = r0.mBreakIterator
            if (r3 == 0) goto L_0x01b5
            int r12 = r12.previous()
            goto L_0x01b9
        L_0x01b5:
            int r12 = r12.next()
        L_0x01b9:
            r0 = -1
            if (r12 != r0) goto L_0x01bd
            goto L_0x01ec
        L_0x01bd:
            r27 = r5
            r0 = 0
            float r5 = android.text.Layout.getDesiredWidth(r10, r0, r12, r2)
            float r0 = android.text.Layout.getDesiredWidth(r10, r12, r11, r2)
            float r12 = java.lang.Math.max(r5, r0)
            int r28 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1))
            if (r28 >= 0) goto L_0x01f3
            if (r3 == 0) goto L_0x01d7
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 > 0) goto L_0x01dd
            goto L_0x01db
        L_0x01d7:
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 < 0) goto L_0x01dd
        L_0x01db:
            r0 = 1
            goto L_0x01de
        L_0x01dd:
            r0 = 0
        L_0x01de:
            if (r0 == 0) goto L_0x01e2
            r14 = r12
            goto L_0x01f3
        L_0x01e2:
            int r15 = r15 + 1
            r0 = r29
            r14 = r12
            r12 = r26
            r5 = r27
            goto L_0x01a0
        L_0x01ec:
            r27 = r5
            goto L_0x01f3
        L_0x01ef:
            r27 = r5
            r25 = r15
        L_0x01f3:
            double r2 = (double) r14
            double r2 = java.lang.Math.ceil(r2)
            int r0 = (int) r2
            r14 = r0
            r0 = -1
        L_0x01fb:
            if (r14 != r0) goto L_0x01fe
            goto L_0x025b
        L_0x01fe:
            int r0 = r7.getMeasuredWidth()
            r7.nullLayouts()
            r7.forceLayout()
            int r2 = r7.getPaddingLeft()
            int r3 = r7.getPaddingRight()
            int r3 = r3 + r2
            int r3 = r3 + r14
            android.graphics.drawable.Drawable[] r2 = r7.getCompoundDrawables()
            r5 = 0
            r2 = r2[r5]
            if (r2 != 0) goto L_0x021d
            r2 = 0
            goto L_0x022a
        L_0x021d:
            android.graphics.Rect r2 = r2.getBounds()
            int r2 = r2.width()
            int r5 = r7.getCompoundDrawablePadding()
            int r2 = r2 + r5
        L_0x022a:
            int r3 = r3 + r2
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            int r3 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r2)
            r7.measure(r3, r1)
            android.text.Layout r2 = r7.getLayout()
            if (r2 != 0) goto L_0x023d
            android.util.Log.wtf(r8, r13)
        L_0x023d:
            int r2 = r7.getMeasuredWidth()
            android.view.ViewGroup$LayoutParams r3 = r7.getLayoutParams()
            com.android.systemui.statusbar.policy.SmartReplyView$LayoutParams r3 = (com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams) r3
            int r5 = r7.getLineCount()
            r10 = 2
            if (r5 > r10) goto L_0x0258
            if (r2 < r0) goto L_0x0251
            goto L_0x0258
        L_0x0251:
            r5 = 1
            r3.squeezeStatus = r5
            int r14 = r0 - r2
            r0 = -1
            goto L_0x025d
        L_0x0258:
            r0 = 3
            r3.squeezeStatus = r0
        L_0x025b:
            r0 = -1
            r14 = -1
        L_0x025d:
            if (r14 == r0) goto L_0x0270
            int r2 = r6.mMaxChildHeight
            int r3 = r7.getMeasuredHeight()
            int r2 = java.lang.Math.max(r2, r3)
            r6.mMaxChildHeight = r2
            int r2 = r6.mMeasuredWidth
            int r2 = r2 - r14
            r6.mMeasuredWidth = r2
        L_0x0270:
            r0 = r29
            r2 = r22
            r12 = r23
            r3 = r24
            r15 = r25
            r5 = r27
            goto L_0x0122
        L_0x027e:
            r22 = r2
            r24 = r3
            r27 = r5
            r23 = r12
            r25 = r15
            int r0 = r6.mMeasuredWidth
            if (r0 <= r4) goto L_0x02a1
            r0 = 3
            markButtonsWithPendingSqueezeStatusAs(r0, r9)
            java.lang.String r0 = "overflow"
            r2 = r27
            r2.mNoShowReason = r0
            r14 = r20
            r6 = r21
            r12 = r23
            r2 = r24
            r15 = r25
            goto L_0x02c7
        L_0x02a1:
            r2 = r27
            r0 = 2
            markButtonsWithPendingSqueezeStatusAs(r0, r9)
            goto L_0x02b1
        L_0x02a8:
            r22 = r2
            r24 = r3
            r2 = r5
            r23 = r12
            r25 = r15
        L_0x02b1:
            r0 = 1
            r2.show = r0
            java.lang.String r0 = "n/a"
            r2.mNoShowReason = r0
            int r15 = r25 + 1
            com.android.systemui.statusbar.policy.SmartReplyView$SmartButtonType r0 = r2.mButtonType
            r2 = r24
            if (r0 != r2) goto L_0x02c3
            int r14 = r20 + 1
            goto L_0x02c5
        L_0x02c3:
            r14 = r20
        L_0x02c5:
            r12 = r23
        L_0x02c7:
            r7 = 0
            r0 = r29
            r3 = r2
            r11 = r16
            r10 = r18
            r13 = r19
            r2 = r22
            goto L_0x0072
        L_0x02d5:
            r3 = r7
            r18 = r10
            r0.mDidHideSystemReplies = r3
            boolean r2 = r0.mSmartRepliesGeneratedByAssistant
            if (r2 == 0) goto L_0x0332
            int r2 = r0.mMinNumSystemGeneratedReplies
            r3 = 1
            if (r2 > r3) goto L_0x02e4
            goto L_0x030b
        L_0x02e4:
            java.util.Iterator r2 = r18.iterator()
            r3 = 0
        L_0x02e9:
            boolean r4 = r2.hasNext()
            if (r4 == 0) goto L_0x0302
            java.lang.Object r4 = r2.next()
            android.view.View r4 = (android.view.View) r4
            android.view.ViewGroup$LayoutParams r4 = r4.getLayoutParams()
            com.android.systemui.statusbar.policy.SmartReplyView$LayoutParams r4 = (com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams) r4
            boolean r4 = r4.show
            if (r4 == 0) goto L_0x02e9
            int r3 = r3 + 1
            goto L_0x02e9
        L_0x0302:
            if (r3 == 0) goto L_0x030b
            int r2 = r0.mMinNumSystemGeneratedReplies
            if (r3 < r2) goto L_0x0309
            goto L_0x030b
        L_0x0309:
            r14 = 0
            goto L_0x030c
        L_0x030b:
            r14 = 1
        L_0x030c:
            if (r14 != 0) goto L_0x0332
            java.util.Iterator r2 = r18.iterator()
        L_0x0312:
            boolean r3 = r2.hasNext()
            if (r3 == 0) goto L_0x032c
            java.lang.Object r3 = r2.next()
            android.view.View r3 = (android.view.View) r3
            android.view.ViewGroup$LayoutParams r3 = r3.getLayoutParams()
            com.android.systemui.statusbar.policy.SmartReplyView$LayoutParams r3 = (com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams) r3
            r14 = 0
            r3.show = r14
            java.lang.String r4 = "not-enough-system-replies"
            r3.mNoShowReason = r4
            goto L_0x0312
        L_0x032c:
            r3 = 1
            r14 = 0
            r0.mDidHideSystemReplies = r3
            r6 = r12
            goto L_0x0334
        L_0x0332:
            r3 = 1
            r14 = 0
        L_0x0334:
            java.util.PriorityQueue<android.widget.Button> r2 = r0.mCandidateButtonQueueForSqueezing
            r2.clear()
            int r2 = r6.mMaxChildHeight
            r4 = 1073741824(0x40000000, float:2.0)
            int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r2, r4)
            int r5 = r29.getChildCount()
            r12 = r14
        L_0x0346:
            if (r12 >= r5) goto L_0x0381
            android.view.View r7 = r0.getChildAt(r12)
            android.view.ViewGroup$LayoutParams r8 = r7.getLayoutParams()
            com.android.systemui.statusbar.policy.SmartReplyView$LayoutParams r8 = (com.android.systemui.statusbar.policy.SmartReplyView.LayoutParams) r8
            boolean r9 = r8.show
            if (r9 != 0) goto L_0x035a
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
            r10 = 3
            goto L_0x037e
        L_0x035a:
            int r9 = r7.getMeasuredWidth()
            int r8 = r8.squeezeStatus
            r10 = 3
            if (r8 != r10) goto L_0x0368
            r8 = r3
            r9 = 2147483647(0x7fffffff, float:NaN)
            goto L_0x0369
        L_0x0368:
            r8 = r14
        L_0x0369:
            int r11 = r7.getMeasuredHeight()
            if (r11 == r2) goto L_0x0370
            r8 = r3
        L_0x0370:
            if (r8 == 0) goto L_0x037c
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
            int r9 = android.view.View.MeasureSpec.makeMeasureSpec(r9, r8)
            r7.measure(r9, r4)
            goto L_0x037e
        L_0x037c:
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
        L_0x037e:
            int r12 = r12 + 1
            goto L_0x0346
        L_0x0381:
            int r2 = r29.getSuggestedMinimumHeight()
            int r3 = r0.mPaddingTop
            int r4 = r6.mMaxChildHeight
            int r3 = r3 + r4
            int r4 = r0.mPaddingBottom
            int r3 = r3 + r4
            int r2 = java.lang.Math.max(r2, r3)
            int r3 = r29.getSuggestedMinimumWidth()
            int r4 = r6.mMeasuredWidth
            int r3 = java.lang.Math.max(r3, r4)
            r4 = r30
            int r3 = android.view.View.resolveSize(r3, r4)
            int r1 = android.view.View.resolveSize(r2, r1)
            r0.setMeasuredDimension(r3, r1)
            long r1 = android.os.SystemClock.elapsedRealtime()
            r0.mLastMeasureTime = r1
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.policy.SmartReplyView.onMeasure(int, int):void");
    }

    public final void setBackgroundTintColor(int i, boolean z) {
        int i2;
        int i3;
        if (i != this.mCurrentBackgroundColor || z != this.mCurrentColorized) {
            this.mCurrentBackgroundColor = i;
            this.mCurrentColorized = z;
            boolean isColorDark = Notification.Builder.isColorDark(i);
            if (isColorDark) {
                i2 = this.mDefaultTextColorDarkBg;
            } else {
                i2 = this.mDefaultTextColor;
            }
            int i4 = i | -16777216;
            int ensureTextContrast = ContrastColorUtil.ensureTextContrast(i2, i4, isColorDark);
            this.mCurrentTextColor = ensureTextContrast;
            if (!z) {
                ensureTextContrast = ContrastColorUtil.ensureContrast(this.mDefaultStrokeColor, i4, isColorDark, this.mMinStrokeContrast);
            }
            this.mCurrentStrokeColor = ensureTextContrast;
            if (isColorDark) {
                i3 = this.mRippleColorDarkBg;
            } else {
                i3 = this.mRippleColor;
            }
            this.mCurrentRippleColor = i3;
            int childCount = getChildCount();
            for (int i5 = 0; i5 < childCount; i5++) {
                setButtonColors((Button) getChildAt(i5));
            }
        }
    }

    public SmartReplyView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        int color = context.getColor(C1777R.color.smart_reply_button_background);
        this.mDefaultBackgroundColor = color;
        this.mDefaultTextColor = this.mContext.getColor(C1777R.color.smart_reply_button_text);
        this.mDefaultTextColorDarkBg = this.mContext.getColor(C1777R.color.smart_reply_button_text_dark_bg);
        int color2 = this.mContext.getColor(C1777R.color.smart_reply_button_stroke);
        this.mDefaultStrokeColor = color2;
        int color3 = this.mContext.getColor(C1777R.color.notification_ripple_untinted_color);
        this.mRippleColor = color3;
        this.mRippleColorDarkBg = Color.argb(Color.alpha(color3), 255, 255, 255);
        this.mMinStrokeContrast = ContrastColorUtil.calculateContrast(color2, color);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.SmartReplyView, 0, 0);
        int indexCount = obtainStyledAttributes.getIndexCount();
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < indexCount; i3++) {
            int index = obtainStyledAttributes.getIndex(i3);
            if (index == 1) {
                i2 = obtainStyledAttributes.getDimensionPixelSize(i3, 0);
            } else if (index == 0) {
                i = obtainStyledAttributes.getDimensionPixelSize(i3, 0);
            }
        }
        obtainStyledAttributes.recycle();
        this.mStrokeWidth = i;
        this.mSpacing = i2;
        this.mBreakIterator = BreakIterator.getLineInstance();
        setBackgroundTintColor(this.mDefaultBackgroundColor, false);
        this.mCandidateButtonQueueForSqueezing = new PriorityQueue<>(Math.max(getChildCount(), 1), DECREASING_MEASURED_WIDTH_WITHOUT_PADDING_COMPARATOR);
    }

    public static void markButtonsWithPendingSqueezeStatusAs(int i, ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            LayoutParams layoutParams = (LayoutParams) ((View) it.next()).getLayoutParams();
            if (layoutParams.squeezeStatus == 1) {
                layoutParams.squeezeStatus = i;
            }
        }
    }

    public final void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        this.mLastDispatchDrawTime = SystemClock.elapsedRealtime();
    }

    public final boolean drawChild(Canvas canvas, View view, long j) {
        if (!((LayoutParams) view.getLayoutParams()).show) {
            return false;
        }
        this.mLastDrawChildTime = SystemClock.elapsedRealtime();
        return super.drawChild(canvas, view, j);
    }

    public final void dump(IndentingPrintWriter indentingPrintWriter) {
        float f;
        float f2;
        indentingPrintWriter.println(this);
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.print("mMaxSqueezeRemeasureAttempts=");
        indentingPrintWriter.println(this.mMaxSqueezeRemeasureAttempts);
        indentingPrintWriter.print("mTotalSqueezeRemeasureAttempts=");
        indentingPrintWriter.println(this.mTotalSqueezeRemeasureAttempts);
        indentingPrintWriter.print("mMaxNumActions=");
        indentingPrintWriter.println(this.mMaxNumActions);
        indentingPrintWriter.print("mSmartRepliesGeneratedByAssistant=");
        indentingPrintWriter.println(this.mSmartRepliesGeneratedByAssistant);
        indentingPrintWriter.print("mMinNumSystemGeneratedReplies=");
        indentingPrintWriter.println(this.mMinNumSystemGeneratedReplies);
        indentingPrintWriter.print("mHeightUpperLimit=");
        indentingPrintWriter.println(this.mHeightUpperLimit);
        indentingPrintWriter.print("mDidHideSystemReplies=");
        indentingPrintWriter.println(this.mDidHideSystemReplies);
        long elapsedRealtime = SystemClock.elapsedRealtime();
        indentingPrintWriter.print("lastMeasureAge (s)=");
        long j = this.mLastMeasureTime;
        float f3 = Float.NaN;
        if (j == 0) {
            f = Float.NaN;
        } else {
            f = ((float) (elapsedRealtime - j)) / 1000.0f;
        }
        indentingPrintWriter.println(f);
        indentingPrintWriter.print("lastDrawChildAge (s)=");
        long j2 = this.mLastDrawChildTime;
        if (j2 == 0) {
            f2 = Float.NaN;
        } else {
            f2 = ((float) (elapsedRealtime - j2)) / 1000.0f;
        }
        indentingPrintWriter.println(f2);
        indentingPrintWriter.print("lastDispatchDrawAge (s)=");
        long j3 = this.mLastDispatchDrawTime;
        if (j3 != 0) {
            f3 = ((float) (elapsedRealtime - j3)) / 1000.0f;
        }
        indentingPrintWriter.println(f3);
        int childCount = getChildCount();
        indentingPrintWriter.print("children: num=");
        indentingPrintWriter.println(childCount);
        indentingPrintWriter.increaseIndent();
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            indentingPrintWriter.print("[");
            indentingPrintWriter.print(i);
            indentingPrintWriter.print("] type=");
            indentingPrintWriter.print(layoutParams.mButtonType);
            indentingPrintWriter.print(" squeezeStatus=");
            indentingPrintWriter.print(layoutParams.squeezeStatus);
            indentingPrintWriter.print(" show=");
            indentingPrintWriter.print(layoutParams.show);
            indentingPrintWriter.print(" noShowReason=");
            indentingPrintWriter.print(layoutParams.mNoShowReason);
            indentingPrintWriter.print(" view=");
            indentingPrintWriter.println(childAt);
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
    }

    public final ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams.width, layoutParams.height);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        boolean z2 = true;
        if (getLayoutDirection() != 1) {
            z2 = false;
        }
        int i7 = i3 - i;
        if (z2) {
            i5 = i7 - this.mPaddingRight;
        } else {
            i5 = this.mPaddingLeft;
        }
        int childCount = getChildCount();
        for (int i8 = 0; i8 < childCount; i8++) {
            View childAt = getChildAt(i8);
            if (((LayoutParams) childAt.getLayoutParams()).show) {
                int measuredWidth = childAt.getMeasuredWidth();
                int measuredHeight = childAt.getMeasuredHeight();
                if (z2) {
                    i6 = i5 - measuredWidth;
                } else {
                    i6 = i5;
                }
                childAt.layout(i6, 0, i6 + measuredWidth, measuredHeight);
                int i9 = measuredWidth + this.mSpacing;
                if (z2) {
                    i5 -= i9;
                } else {
                    i5 += i9;
                }
            }
        }
    }

    public final void setButtonColors(Button button) {
        Drawable background = button.getBackground();
        if (background instanceof RippleDrawable) {
            Drawable mutate = background.mutate();
            RippleDrawable rippleDrawable = (RippleDrawable) mutate;
            rippleDrawable.setColor(ColorStateList.valueOf(this.mCurrentRippleColor));
            Drawable drawable = rippleDrawable.getDrawable(0);
            if (drawable instanceof InsetDrawable) {
                Drawable drawable2 = ((InsetDrawable) drawable).getDrawable();
                if (drawable2 instanceof GradientDrawable) {
                    GradientDrawable gradientDrawable = (GradientDrawable) drawable2;
                    gradientDrawable.setColor(this.mCurrentBackgroundColor);
                    gradientDrawable.setStroke(this.mStrokeWidth, this.mCurrentStrokeColor);
                }
            }
            button.setBackground(mutate);
        }
        button.setTextColor(this.mCurrentTextColor);
    }

    @VisibleForTesting
    public static class LayoutParams extends ViewGroup.LayoutParams {
        public SmartButtonType mButtonType = SmartButtonType.REPLY;
        public String mNoShowReason = "new";
        public boolean show = false;
        public int squeezeStatus = 0;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        @VisibleForTesting
        public boolean isShown() {
            return this.show;
        }
    }
}
