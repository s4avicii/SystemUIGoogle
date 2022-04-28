package com.android.systemui.statusbar.notification.stack;

import android.app.Notification;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.service.notification.StatusBarNotification;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.NotificationHeaderView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.leanback.R$raw;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.widget.NotificationExpandButton;
import com.android.keyguard.KeyguardVisibilityHelper$$ExternalSyntheticLambda0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.R$array;
import com.android.systemui.statusbar.NotificationGroupingUtil;
import com.android.systemui.statusbar.TransformableView;
import com.android.systemui.statusbar.notification.NotificationFadeAware;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.HybridGroupManager;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.row.wrapper.NotificationViewWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class NotificationChildrenContainer extends ViewGroup implements NotificationFadeAware {
    public static final C13501 ALPHA_FADE_IN;
    @VisibleForTesting
    public static final int NUMBER_OF_CHILDREN_WHEN_COLLAPSED = 2;
    @VisibleForTesting
    public static final int NUMBER_OF_CHILDREN_WHEN_SYSTEM_EXPANDED = 5;
    public int mActualHeight;
    public final ArrayList mAttachedChildren;
    public int mChildPadding;
    public boolean mChildrenExpanded;
    public int mClipBottomAmount;
    public float mCollapsedBottomPadding;
    public ExpandableNotificationRow mContainingNotification;
    public boolean mContainingNotificationIsFaded;
    public NotificationHeaderView mCurrentHeader;
    public int mCurrentHeaderTranslation;
    public float mDividerAlpha;
    public int mDividerHeight;
    public final ArrayList mDividers;
    public boolean mEnableShadowOnChildNotifications;
    public ViewState mGroupOverFlowState;
    public NotificationGroupingUtil mGroupingUtil;
    public View.OnClickListener mHeaderClickListener;
    public int mHeaderHeight;
    public ViewState mHeaderViewState;
    public float mHeaderVisibleAmount;
    public boolean mHideDividersDuringExpand;
    public final HybridGroupManager mHybridGroupManager;
    public boolean mIsConversation;
    public boolean mIsLowPriority;
    public boolean mNeverAppliedGroupState;
    public NotificationHeaderView mNotificationHeader;
    public NotificationHeaderView mNotificationHeaderLowPriority;
    public int mNotificationHeaderMargin;
    public NotificationViewWrapper mNotificationHeaderWrapper;
    public NotificationViewWrapper mNotificationHeaderWrapperLowPriority;
    public int mNotificationTopPadding;
    public TextView mOverflowNumber;
    public int mRealHeight;
    public boolean mShowDividersWhenExpanded;
    public boolean mShowGroupCountInExpander;
    public int mTranslationForHeader;
    public int mUntruncatedChildCount;
    public boolean mUserLocked;

    public NotificationChildrenContainer(Context context) {
        this(context, (AttributeSet) null);
    }

    @VisibleForTesting
    public int getMaxAllowedVisibleChildren() {
        return getMaxAllowedVisibleChildren(false);
    }

    public final boolean hasOverlappingRendering() {
        return false;
    }

    public final boolean pointInView(float f, float f2, float f3) {
        float f4 = -f3;
        if (f < f4 || f2 < f4 || f >= ((float) (this.mRight - this.mLeft)) + f3 || f2 >= ((float) this.mRealHeight) + f3) {
            return false;
        }
        return true;
    }

    static {
        C13501 r0 = new AnimationProperties() {
            public AnimationFilter mAnimationFilter;

            {
                AnimationFilter animationFilter = new AnimationFilter();
                animationFilter.animateAlpha = true;
                this.mAnimationFilter = animationFilter;
            }

            public final AnimationFilter getAnimationFilter() {
                return this.mAnimationFilter;
            }
        };
        r0.duration = 200;
        ALPHA_FADE_IN = r0;
    }

    public NotificationChildrenContainer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x000d, code lost:
        if (r3.mUserLocked != false) goto L_0x000f;
     */
    @com.android.internal.annotations.VisibleForTesting
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getMaxAllowedVisibleChildren(boolean r3) {
        /*
            r2 = this;
            if (r3 != 0) goto L_0x0018
            boolean r3 = r2.mChildrenExpanded
            if (r3 != 0) goto L_0x000f
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = r2.mContainingNotification
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.mUserLocked
            if (r3 == 0) goto L_0x0018
        L_0x000f:
            boolean r3 = r2.showingAsLowPriority()
            if (r3 != 0) goto L_0x0018
            r2 = 8
            return r2
        L_0x0018:
            boolean r3 = r2.mIsLowPriority
            if (r3 != 0) goto L_0x004c
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = r2.mContainingNotification
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.mOnKeyguard
            r0 = 0
            if (r3 != 0) goto L_0x0031
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = r2.mContainingNotification
            java.util.Objects.requireNonNull(r3)
            boolean r3 = r3.isExpanded(r0)
            if (r3 != 0) goto L_0x004c
        L_0x0031:
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r3 = r2.mContainingNotification
            java.util.Objects.requireNonNull(r3)
            boolean r1 = r3.mIsHeadsUp
            if (r1 != 0) goto L_0x003e
            boolean r3 = r3.mHeadsupDisappearRunning
            if (r3 == 0) goto L_0x003f
        L_0x003e:
            r0 = 1
        L_0x003f:
            if (r0 == 0) goto L_0x004a
            com.android.systemui.statusbar.notification.row.ExpandableNotificationRow r2 = r2.mContainingNotification
            boolean r2 = r2.canShowHeadsUp()
            if (r2 == 0) goto L_0x004a
            goto L_0x004c
        L_0x004a:
            r2 = 2
            return r2
        L_0x004c:
            r2 = 5
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer.getMaxAllowedVisibleChildren(boolean):int");
    }

    public final int getMinHeight(int i, boolean z, int i2) {
        if (!z && showingAsLowPriority()) {
            return this.mNotificationHeaderLowPriority.getHeight();
        }
        int i3 = this.mNotificationHeaderMargin + i2;
        int size = this.mAttachedChildren.size();
        boolean z2 = true;
        int i4 = 0;
        for (int i5 = 0; i5 < size && i4 < i; i5++) {
            if (!z2) {
                i3 += this.mChildPadding;
            } else {
                z2 = false;
            }
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i5);
            Objects.requireNonNull(expandableNotificationRow);
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            Objects.requireNonNull(notificationContentView);
            i3 += notificationContentView.mSingleLineView.getHeight();
            i4++;
        }
        return (int) (((float) i3) + this.mCollapsedBottomPadding);
    }

    public final NotificationViewWrapper getWrapperForView(View view) {
        if (view == this.mNotificationHeader) {
            return this.mNotificationHeaderWrapper;
        }
        return this.mNotificationHeaderWrapperLowPriority;
    }

    public final View inflateDivider() {
        return LayoutInflater.from(this.mContext).inflate(C1777R.layout.notification_children_divider, this, false);
    }

    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int min = Math.min(this.mAttachedChildren.size(), 8);
        for (int i6 = 0; i6 < min; i6++) {
            View view = (View) this.mAttachedChildren.get(i6);
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            ((View) this.mDividers.get(i6)).layout(0, 0, getWidth(), this.mDividerHeight);
        }
        if (this.mOverflowNumber != null) {
            boolean z2 = true;
            if (getLayoutDirection() != 1) {
                z2 = false;
            }
            if (z2) {
                i5 = 0;
            } else {
                i5 = getWidth() - this.mOverflowNumber.getMeasuredWidth();
            }
            TextView textView = this.mOverflowNumber;
            textView.layout(i5, 0, this.mOverflowNumber.getMeasuredWidth() + i5, textView.getMeasuredHeight());
        }
        NotificationHeaderView notificationHeaderView = this.mNotificationHeader;
        if (notificationHeaderView != null) {
            notificationHeaderView.layout(0, 0, notificationHeaderView.getMeasuredWidth(), this.mNotificationHeader.getMeasuredHeight());
        }
        NotificationHeaderView notificationHeaderView2 = this.mNotificationHeaderLowPriority;
        if (notificationHeaderView2 != null) {
            notificationHeaderView2.layout(0, 0, notificationHeaderView2.getMeasuredWidth(), this.mNotificationHeaderLowPriority.getMeasuredHeight());
        }
    }

    public final void onMeasure(int i, int i2) {
        boolean z;
        boolean z2;
        int i3;
        int i4;
        boolean z3;
        int i5;
        TextView textView;
        int i6 = i;
        int mode = View.MeasureSpec.getMode(i2);
        boolean z4 = true;
        if (mode == 1073741824) {
            z = true;
        } else {
            z = false;
        }
        if (mode == Integer.MIN_VALUE) {
            z2 = true;
        } else {
            z2 = false;
        }
        int size = View.MeasureSpec.getSize(i2);
        if (z || z2) {
            i3 = View.MeasureSpec.makeMeasureSpec(size, Integer.MIN_VALUE);
        } else {
            i3 = i2;
        }
        int size2 = View.MeasureSpec.getSize(i);
        TextView textView2 = this.mOverflowNumber;
        if (textView2 != null) {
            textView2.measure(View.MeasureSpec.makeMeasureSpec(size2, Integer.MIN_VALUE), i3);
        }
        int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(this.mDividerHeight, 1073741824);
        int i7 = this.mNotificationHeaderMargin + this.mNotificationTopPadding;
        int min = Math.min(this.mAttachedChildren.size(), 8);
        int maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(true);
        if (min > maxAllowedVisibleChildren) {
            i4 = maxAllowedVisibleChildren - 1;
        } else {
            i4 = -1;
        }
        int i8 = 0;
        while (i8 < min) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i8);
            if (i8 == i4) {
                z3 = z4;
            } else {
                z3 = false;
            }
            if (!z3 || (textView = this.mOverflowNumber) == null) {
                i5 = 0;
            } else {
                i5 = textView.getMeasuredWidth();
            }
            Objects.requireNonNull(expandableNotificationRow);
            NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
            Objects.requireNonNull(notificationContentView);
            if (i5 != notificationContentView.mSingleLineWidthIndention) {
                notificationContentView.mSingleLineWidthIndention = i5;
                notificationContentView.mContainingNotification.forceLayout();
                notificationContentView.forceLayout();
            }
            expandableNotificationRow.measure(i6, i3);
            ((View) this.mDividers.get(i8)).measure(i6, makeMeasureSpec);
            if (expandableNotificationRow.getVisibility() != 8) {
                i7 = expandableNotificationRow.getMeasuredHeight() + this.mDividerHeight + i7;
            }
            i8++;
            z4 = true;
        }
        this.mRealHeight = i7;
        if (mode != 0) {
            i7 = Math.min(i7, size);
        }
        int makeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(this.mHeaderHeight, 1073741824);
        NotificationHeaderView notificationHeaderView = this.mNotificationHeader;
        if (notificationHeaderView != null) {
            notificationHeaderView.measure(i6, makeMeasureSpec2);
        }
        NotificationHeaderView notificationHeaderView2 = this.mNotificationHeaderLowPriority;
        if (notificationHeaderView2 != null) {
            notificationHeaderView2.measure(i6, makeMeasureSpec2);
        }
        setMeasuredDimension(size2, i7);
    }

    public final void onNotificationUpdated() {
        if (!this.mShowGroupCountInExpander) {
            ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
            Objects.requireNonNull(expandableNotificationRow);
            int i = expandableNotificationRow.mNotificationColor;
            TypedArray obtainStyledAttributes = new ContextThemeWrapper(this.mContext, 16974563).getTheme().obtainStyledAttributes(new int[]{16843829});
            try {
                int color = obtainStyledAttributes.getColor(0, i);
                obtainStyledAttributes.close();
                HybridGroupManager hybridGroupManager = this.mHybridGroupManager;
                TextView textView = this.mOverflowNumber;
                Objects.requireNonNull(hybridGroupManager);
                hybridGroupManager.mOverflowNumberColor = color;
                if (textView != null) {
                    textView.setTextColor(color);
                    return;
                }
                return;
            } catch (Throwable th) {
                th.addSuppressed(th);
            }
        } else {
            return;
        }
        throw th;
    }

    public final void recreateLowPriorityHeader(Notification.Builder builder) {
        NotificationHeaderView notificationHeaderView;
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry);
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        if (this.mIsLowPriority) {
            if (builder == null) {
                builder = Notification.Builder.recoverBuilder(getContext(), statusBarNotification.getNotification());
            }
            RemoteViews makeLowPriorityContentView = builder.makeLowPriorityContentView(true);
            if (this.mNotificationHeaderLowPriority == null) {
                NotificationHeaderView apply = makeLowPriorityContentView.apply(getContext(), this);
                this.mNotificationHeaderLowPriority = apply;
                apply.findViewById(16908980).setVisibility(0);
                this.mNotificationHeaderLowPriority.setOnClickListener(this.mHeaderClickListener);
                this.mNotificationHeaderWrapperLowPriority = NotificationViewWrapper.wrap(getContext(), this.mNotificationHeaderLowPriority, this.mContainingNotification);
                addView(this.mNotificationHeaderLowPriority, 0);
                invalidate();
            } else {
                makeLowPriorityContentView.reapply(getContext(), this.mNotificationHeaderLowPriority);
            }
            this.mNotificationHeaderWrapperLowPriority.onContentUpdated(this.mContainingNotification);
            NotificationHeaderView notificationHeaderView2 = this.mNotificationHeaderLowPriority;
            if (showingAsLowPriority()) {
                notificationHeaderView = this.mNotificationHeaderLowPriority;
            } else {
                notificationHeaderView = this.mNotificationHeader;
            }
            resetHeaderVisibilityIfNeeded(notificationHeaderView2, notificationHeaderView);
            return;
        }
        removeView(this.mNotificationHeaderLowPriority);
        this.mNotificationHeaderLowPriority = null;
        this.mNotificationHeaderWrapperLowPriority = null;
    }

    public final void recreateNotificationHeader(ExpandableNotificationRow.C13081 r4, boolean z) {
        this.mHeaderClickListener = r4;
        this.mIsConversation = z;
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        Objects.requireNonNull(expandableNotificationRow);
        NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
        Objects.requireNonNull(notificationEntry);
        Notification.Builder recoverBuilder = Notification.Builder.recoverBuilder(getContext(), notificationEntry.mSbn.getNotification());
        RemoteViews makeNotificationGroupHeader = recoverBuilder.makeNotificationGroupHeader();
        if (this.mNotificationHeader == null) {
            NotificationHeaderView apply = makeNotificationGroupHeader.apply(getContext(), this);
            this.mNotificationHeader = apply;
            apply.findViewById(16908980).setVisibility(0);
            this.mNotificationHeader.setOnClickListener(this.mHeaderClickListener);
            this.mNotificationHeaderWrapper = NotificationViewWrapper.wrap(getContext(), this.mNotificationHeader, this.mContainingNotification);
            addView(this.mNotificationHeader, 0);
            invalidate();
        } else {
            makeNotificationGroupHeader.reapply(getContext(), this.mNotificationHeader);
        }
        this.mNotificationHeaderWrapper.setExpanded(this.mChildrenExpanded);
        this.mNotificationHeaderWrapper.onContentUpdated(this.mContainingNotification);
        recreateLowPriorityHeader(recoverBuilder);
        updateHeaderVisibility(false);
        updateChildrenAppearance();
    }

    public final void removeNotification(ExpandableNotificationRow expandableNotificationRow) {
        int indexOf = this.mAttachedChildren.indexOf(expandableNotificationRow);
        this.mAttachedChildren.remove(expandableNotificationRow);
        removeView(expandableNotificationRow);
        final View view = (View) this.mDividers.remove(indexOf);
        removeView(view);
        getOverlay().add(view);
        R$raw.fadeOut(view, 210, (Runnable) new Runnable() {
            public final void run() {
                NotificationChildrenContainer.this.getOverlay().remove(view);
            }
        });
        Objects.requireNonNull(expandableNotificationRow);
        expandableNotificationRow.mIsSystemChildExpanded = false;
        expandableNotificationRow.setNotificationFaded(false);
        expandableNotificationRow.setUserLocked(false);
        if (!expandableNotificationRow.mRemoved) {
            NotificationGroupingUtil notificationGroupingUtil = this.mGroupingUtil;
            Objects.requireNonNull(notificationGroupingUtil);
            for (int i = 0; i < notificationGroupingUtil.mProcessors.size(); i++) {
                notificationGroupingUtil.mProcessors.get(i).apply(expandableNotificationRow, true);
            }
            notificationGroupingUtil.sanitizeTopLineViews(expandableNotificationRow);
        }
    }

    public final void resetHeaderVisibilityIfNeeded(NotificationHeaderView notificationHeaderView, NotificationHeaderView notificationHeaderView2) {
        if (notificationHeaderView != null) {
            if (!(notificationHeaderView == this.mCurrentHeader || notificationHeaderView == notificationHeaderView2)) {
                getWrapperForView(notificationHeaderView).setVisible(false);
                notificationHeaderView.setVisibility(4);
            }
            if (notificationHeaderView == notificationHeaderView2 && notificationHeaderView.getVisibility() != 0) {
                getWrapperForView(notificationHeaderView).setVisible(true);
                notificationHeaderView.setVisibility(0);
            }
        }
    }

    public final void setChildrenExpanded$1(boolean z) {
        this.mChildrenExpanded = z;
        updateExpansionStates();
        NotificationViewWrapper notificationViewWrapper = this.mNotificationHeaderWrapper;
        if (notificationViewWrapper != null) {
            notificationViewWrapper.setExpanded(z);
        }
        int size = this.mAttachedChildren.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            ((ExpandableNotificationRow) this.mAttachedChildren.get(i)).setChildrenExpanded(z);
        }
        NotificationHeaderView notificationHeaderView = this.mNotificationHeader;
        if (notificationHeaderView != null) {
            if (this.mChildrenExpanded || this.mUserLocked) {
                z2 = true;
            }
            notificationHeaderView.setAcceptAllTouches(z2);
        }
    }

    public final void setNotificationFaded(boolean z) {
        this.mContainingNotificationIsFaded = z;
        NotificationViewWrapper notificationViewWrapper = this.mNotificationHeaderWrapper;
        if (notificationViewWrapper != null) {
            notificationViewWrapper.setNotificationFaded(z);
        }
        NotificationViewWrapper notificationViewWrapper2 = this.mNotificationHeaderWrapperLowPriority;
        if (notificationViewWrapper2 != null) {
            notificationViewWrapper2.setNotificationFaded(z);
        }
        Iterator it = this.mAttachedChildren.iterator();
        while (it.hasNext()) {
            ((ExpandableNotificationRow) it.next()).setNotificationFaded(z);
        }
    }

    public final void setUserLocked(boolean z) {
        this.mUserLocked = z;
        boolean z2 = false;
        if (!z) {
            updateHeaderVisibility(false);
        }
        int size = this.mAttachedChildren.size();
        int i = 0;
        while (true) {
            boolean z3 = true;
            if (i >= size) {
                break;
            }
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i);
            if (!z || showingAsLowPriority()) {
                z3 = false;
            }
            expandableNotificationRow.setUserLocked(z3);
            i++;
        }
        NotificationHeaderView notificationHeaderView = this.mNotificationHeader;
        if (notificationHeaderView != null) {
            if (this.mChildrenExpanded || this.mUserLocked) {
                z2 = true;
            }
            notificationHeaderView.setAcceptAllTouches(z2);
        }
    }

    public final boolean showingAsLowPriority() {
        if (!this.mIsLowPriority) {
            return false;
        }
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        Objects.requireNonNull(expandableNotificationRow);
        if (!expandableNotificationRow.isExpanded(false)) {
            return true;
        }
        return false;
    }

    public final void updateChildrenAppearance() {
        View findViewById;
        Notification notification;
        View view;
        NotificationGroupingUtil notificationGroupingUtil = this.mGroupingUtil;
        Objects.requireNonNull(notificationGroupingUtil);
        ArrayList attachedChildren = notificationGroupingUtil.mRow.getAttachedChildren();
        if (attachedChildren != null) {
            ExpandableNotificationRow expandableNotificationRow = notificationGroupingUtil.mRow;
            Objects.requireNonNull(expandableNotificationRow);
            if (expandableNotificationRow.mIsSummaryWithChildren) {
                int i = 0;
                while (true) {
                    Notification notification2 = null;
                    if (i >= notificationGroupingUtil.mProcessors.size()) {
                        break;
                    }
                    NotificationGroupingUtil.Processor processor = notificationGroupingUtil.mProcessors.get(i);
                    Objects.requireNonNull(processor);
                    NotificationHeaderView notificationHeader = processor.mParentRow.getNotificationViewWrapper().getNotificationHeader();
                    if (notificationHeader == null) {
                        view = null;
                    } else {
                        view = notificationHeader.findViewById(processor.mId);
                    }
                    processor.mParentView = view;
                    if (processor.mExtractor != null) {
                        ExpandableNotificationRow expandableNotificationRow2 = processor.mParentRow;
                        Objects.requireNonNull(expandableNotificationRow2);
                        NotificationEntry notificationEntry = expandableNotificationRow2.mEntry;
                        Objects.requireNonNull(notificationEntry);
                        notification2 = notificationEntry.mSbn.getNotification();
                    }
                    processor.mParentData = notification2;
                    processor.mApply = !processor.mComparator.isEmpty(processor.mParentView);
                    i++;
                }
                for (int i2 = 0; i2 < attachedChildren.size(); i2++) {
                    ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) attachedChildren.get(i2);
                    for (int i3 = 0; i3 < notificationGroupingUtil.mProcessors.size(); i3++) {
                        NotificationGroupingUtil.Processor processor2 = notificationGroupingUtil.mProcessors.get(i3);
                        Objects.requireNonNull(processor2);
                        if (processor2.mApply) {
                            Objects.requireNonNull(expandableNotificationRow3);
                            NotificationContentView notificationContentView = expandableNotificationRow3.mPrivateLayout;
                            Objects.requireNonNull(notificationContentView);
                            View view2 = notificationContentView.mContractedChild;
                            if (!(view2 == null || (findViewById = view2.findViewById(processor2.mId)) == null)) {
                                if (processor2.mExtractor == null) {
                                    notification = null;
                                } else {
                                    NotificationEntry notificationEntry2 = expandableNotificationRow3.mEntry;
                                    Objects.requireNonNull(notificationEntry2);
                                    notification = notificationEntry2.mSbn.getNotification();
                                }
                                processor2.mApply = processor2.mComparator.compare(processor2.mParentView, findViewById, processor2.mParentData, notification);
                            }
                        }
                    }
                }
                for (int i4 = 0; i4 < attachedChildren.size(); i4++) {
                    ExpandableNotificationRow expandableNotificationRow4 = (ExpandableNotificationRow) attachedChildren.get(i4);
                    for (int i5 = 0; i5 < notificationGroupingUtil.mProcessors.size(); i5++) {
                        NotificationGroupingUtil.Processor processor3 = notificationGroupingUtil.mProcessors.get(i5);
                        Objects.requireNonNull(processor3);
                        processor3.apply(expandableNotificationRow4, false);
                    }
                    notificationGroupingUtil.sanitizeTopLineViews(expandableNotificationRow4);
                }
            }
        }
    }

    public final void updateChildrenClipping() {
        boolean z;
        int i;
        int i2;
        ExpandableNotificationRow expandableNotificationRow = this.mContainingNotification;
        Objects.requireNonNull(expandableNotificationRow);
        if (!expandableNotificationRow.mChildIsExpanding) {
            int size = this.mAttachedChildren.size();
            ExpandableNotificationRow expandableNotificationRow2 = this.mContainingNotification;
            Objects.requireNonNull(expandableNotificationRow2);
            int i3 = expandableNotificationRow2.mActualHeight - this.mClipBottomAmount;
            for (int i4 = 0; i4 < size; i4++) {
                ExpandableNotificationRow expandableNotificationRow3 = (ExpandableNotificationRow) this.mAttachedChildren.get(i4);
                if (expandableNotificationRow3.getVisibility() != 8) {
                    float translationY = expandableNotificationRow3.getTranslationY();
                    float f = ((float) expandableNotificationRow3.mActualHeight) + translationY;
                    float f2 = (float) i3;
                    boolean z2 = true;
                    if (translationY > f2) {
                        i = 0;
                        z = false;
                    } else {
                        if (f > f2) {
                            i = (int) (f - f2);
                        } else {
                            i = 0;
                        }
                        z = true;
                    }
                    if (expandableNotificationRow3.getVisibility() != 0) {
                        z2 = false;
                    }
                    if (z != z2) {
                        if (z) {
                            i2 = 0;
                        } else {
                            i2 = 4;
                        }
                        expandableNotificationRow3.setVisibility(i2);
                    }
                    expandableNotificationRow3.setClipBottomAmount(i);
                }
            }
        }
    }

    public final void updateExpansionStates() {
        if (!this.mChildrenExpanded && !this.mUserLocked) {
            int size = this.mAttachedChildren.size();
            for (int i = 0; i < size; i++) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i);
                boolean z = true;
                if (i != 0 || size != 1) {
                    z = false;
                }
                Objects.requireNonNull(expandableNotificationRow);
                expandableNotificationRow.mIsSystemChildExpanded = z;
            }
        }
    }

    public final void updateGroupOverflow() {
        View view;
        View view2 = null;
        if (this.mShowGroupCountInExpander) {
            NotificationViewWrapper notificationViewWrapper = this.mNotificationHeaderWrapper;
            if (notificationViewWrapper == null) {
                view = null;
            } else {
                view = notificationViewWrapper.getExpandButton();
            }
            if (view instanceof NotificationExpandButton) {
                ((NotificationExpandButton) view).setNumber(this.mUntruncatedChildCount);
            }
            NotificationViewWrapper notificationViewWrapper2 = this.mNotificationHeaderWrapperLowPriority;
            if (notificationViewWrapper2 != null) {
                view2 = notificationViewWrapper2.getExpandButton();
            }
            if (view2 instanceof NotificationExpandButton) {
                ((NotificationExpandButton) view2).setNumber(this.mUntruncatedChildCount);
                return;
            }
            return;
        }
        int maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(true);
        int i = this.mUntruncatedChildCount;
        if (i > maxAllowedVisibleChildren) {
            int i2 = i - maxAllowedVisibleChildren;
            HybridGroupManager hybridGroupManager = this.mHybridGroupManager;
            TextView textView = this.mOverflowNumber;
            Objects.requireNonNull(hybridGroupManager);
            if (textView == null) {
                textView = (TextView) ((LayoutInflater) hybridGroupManager.mContext.getSystemService(LayoutInflater.class)).inflate(C1777R.layout.hybrid_overflow_number, this, false);
                addView(textView);
                textView.setTextColor(hybridGroupManager.mOverflowNumberColor);
            }
            String string = hybridGroupManager.mContext.getResources().getString(C1777R.string.notification_group_overflow_indicator, new Object[]{Integer.valueOf(i2)});
            if (!string.equals(textView.getText())) {
                textView.setText(string);
            }
            textView.setContentDescription(String.format(hybridGroupManager.mContext.getResources().getQuantityString(C1777R.plurals.notification_group_overflow_description, i2), new Object[]{Integer.valueOf(i2)}));
            textView.setTextSize(0, hybridGroupManager.mOverflowNumberSize);
            textView.setPaddingRelative(textView.getPaddingStart(), textView.getPaddingTop(), hybridGroupManager.mOverflowNumberPadding, textView.getPaddingBottom());
            textView.setTextColor(hybridGroupManager.mOverflowNumberColor);
            this.mOverflowNumber = textView;
            if (this.mGroupOverFlowState == null) {
                this.mGroupOverFlowState = new ViewState();
                this.mNeverAppliedGroupState = true;
                return;
            }
            return;
        }
        TextView textView2 = this.mOverflowNumber;
        if (textView2 != null) {
            removeView(textView2);
            if (isShown() && isAttachedToWindow()) {
                final TextView textView3 = this.mOverflowNumber;
                addTransientView(textView3, getTransientViewCount());
                R$raw.fadeOut((View) textView3, 210, (Runnable) new Runnable() {
                    public final void run() {
                        NotificationChildrenContainer.this.removeTransientView(textView3);
                    }
                });
            }
            this.mOverflowNumber = null;
            this.mGroupOverFlowState = null;
        }
    }

    public final void updateHeaderVisibility(boolean z) {
        NotificationHeaderView notificationHeaderView;
        boolean z2;
        float f;
        NotificationHeaderView notificationHeaderView2 = this.mCurrentHeader;
        if (showingAsLowPriority()) {
            notificationHeaderView = this.mNotificationHeaderLowPriority;
        } else {
            notificationHeaderView = this.mNotificationHeader;
        }
        if (notificationHeaderView2 != notificationHeaderView) {
            if (z) {
                if (notificationHeaderView == null || notificationHeaderView2 == null) {
                    z = false;
                } else {
                    notificationHeaderView2.setVisibility(0);
                    notificationHeaderView.setVisibility(0);
                    NotificationViewWrapper wrapperForView = getWrapperForView(notificationHeaderView);
                    NotificationViewWrapper wrapperForView2 = getWrapperForView(notificationHeaderView2);
                    wrapperForView.transformFrom(wrapperForView2);
                    wrapperForView2.transformTo((TransformableView) wrapperForView, (Runnable) new KeyguardVisibilityHelper$$ExternalSyntheticLambda0(this, 5));
                    if (notificationHeaderView == this.mNotificationHeader) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (z2) {
                        f = 1.0f;
                    } else {
                        f = 0.0f;
                    }
                    float f2 = 1.0f - f;
                    int size = this.mAttachedChildren.size();
                    int i = 0;
                    while (i < size && i < 5) {
                        ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i);
                        expandableNotificationRow.setAlpha(f2);
                        ViewState viewState = new ViewState();
                        viewState.initFrom(expandableNotificationRow);
                        viewState.alpha = f;
                        C13501 r11 = ALPHA_FADE_IN;
                        Objects.requireNonNull(r11);
                        r11.delay = (long) (i * 50);
                        viewState.animateTo(expandableNotificationRow, r11);
                        i++;
                    }
                }
            }
            if (!z) {
                if (notificationHeaderView != null) {
                    getWrapperForView(notificationHeaderView).setVisible(true);
                    notificationHeaderView.setVisibility(0);
                }
                if (notificationHeaderView2 != null) {
                    NotificationViewWrapper wrapperForView3 = getWrapperForView(notificationHeaderView2);
                    if (wrapperForView3 != null) {
                        wrapperForView3.setVisible(false);
                    }
                    notificationHeaderView2.setVisibility(4);
                }
            }
            resetHeaderVisibilityIfNeeded(this.mNotificationHeader, notificationHeaderView);
            resetHeaderVisibilityIfNeeded(this.mNotificationHeaderLowPriority, notificationHeaderView);
            this.mCurrentHeader = notificationHeaderView;
        }
    }

    public NotificationChildrenContainer(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public final float getGroupExpandFraction() {
        int i;
        int i2;
        if (showingAsLowPriority()) {
            i = getMaxContentHeight();
        } else {
            i = this.mNotificationHeaderMargin + this.mCurrentHeaderTranslation + this.mNotificationTopPadding + this.mDividerHeight;
            int size = this.mAttachedChildren.size();
            int maxAllowedVisibleChildren = getMaxAllowedVisibleChildren(true);
            int i3 = 0;
            for (int i4 = 0; i4 < size && i3 < maxAllowedVisibleChildren; i4++) {
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i4);
                if (expandableNotificationRow.isExpanded(true)) {
                    i2 = expandableNotificationRow.getMaxExpandHeight();
                } else {
                    i2 = expandableNotificationRow.getShowingLayout().getMinHeight(true);
                }
                i = (int) (((float) i) + ((float) i2));
                i3++;
            }
        }
        int minHeight = getMinHeight(getMaxAllowedVisibleChildren(true), false, this.mCurrentHeaderTranslation);
        return Math.max(0.0f, Math.min(1.0f, ((float) (this.mActualHeight - minHeight)) / ((float) (i - minHeight))));
    }

    public final int getIntrinsicHeight() {
        float f;
        int i;
        int i2;
        int i3;
        float maxAllowedVisibleChildren = (float) getMaxAllowedVisibleChildren();
        if (showingAsLowPriority()) {
            return this.mNotificationHeaderLowPriority.getHeight();
        }
        int i4 = this.mNotificationHeaderMargin + this.mCurrentHeaderTranslation;
        int size = this.mAttachedChildren.size();
        if (this.mUserLocked) {
            f = getGroupExpandFraction();
        } else {
            f = 0.0f;
        }
        boolean z = this.mChildrenExpanded;
        boolean z2 = true;
        int i5 = 0;
        for (int i6 = 0; i6 < size && ((float) i5) < maxAllowedVisibleChildren; i6++) {
            if (z2) {
                if (this.mUserLocked) {
                    i = (int) (R$array.interpolate(0.0f, (float) (this.mNotificationTopPadding + this.mDividerHeight), f) + ((float) i4));
                } else {
                    if (z) {
                        i2 = this.mNotificationTopPadding + this.mDividerHeight;
                    } else {
                        i2 = 0;
                    }
                    i = i4 + i2;
                }
                z2 = false;
            } else if (this.mUserLocked) {
                i = (int) (R$array.interpolate((float) this.mChildPadding, (float) this.mDividerHeight, f) + ((float) i4));
            } else {
                if (z) {
                    i3 = this.mDividerHeight;
                } else {
                    i3 = this.mChildPadding;
                }
                i = i4 + i3;
            }
            i4 = i + ((ExpandableNotificationRow) this.mAttachedChildren.get(i6)).getIntrinsicHeight();
            i5++;
        }
        if (this.mUserLocked) {
            return (int) (R$array.interpolate(this.mCollapsedBottomPadding, 0.0f, f) + ((float) i4));
        } else if (!z) {
            return (int) (((float) i4) + this.mCollapsedBottomPadding);
        } else {
            return i4;
        }
    }

    public final int getMaxContentHeight() {
        int i;
        if (showingAsLowPriority()) {
            return getMinHeight(5, true, this.mCurrentHeaderTranslation);
        }
        int i2 = this.mNotificationHeaderMargin + this.mCurrentHeaderTranslation + this.mNotificationTopPadding;
        int size = this.mAttachedChildren.size();
        int i3 = 0;
        for (int i4 = 0; i4 < size && i3 < 8; i4++) {
            ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.mAttachedChildren.get(i4);
            if (expandableNotificationRow.isExpanded(true)) {
                i = expandableNotificationRow.getMaxExpandHeight();
            } else {
                i = expandableNotificationRow.getShowingLayout().getMinHeight(true);
            }
            i2 = (int) (((float) i2) + ((float) i));
            i3++;
        }
        if (i3 > 0) {
            return i2 + (i3 * this.mDividerHeight);
        }
        return i2;
    }

    public final void initDimens() {
        Resources resources = getResources();
        this.mChildPadding = resources.getDimensionPixelOffset(C1777R.dimen.notification_children_padding);
        this.mDividerHeight = resources.getDimensionPixelOffset(C1777R.dimen.notification_children_container_divider_height);
        this.mDividerAlpha = resources.getFloat(C1777R.dimen.notification_divider_alpha);
        this.mNotificationHeaderMargin = resources.getDimensionPixelOffset(C1777R.dimen.notification_children_container_margin_top);
        int dimensionPixelOffset = resources.getDimensionPixelOffset(C1777R.dimen.notification_children_container_top_padding);
        this.mNotificationTopPadding = dimensionPixelOffset;
        this.mHeaderHeight = this.mNotificationHeaderMargin + dimensionPixelOffset;
        this.mCollapsedBottomPadding = (float) resources.getDimensionPixelOffset(C1777R.dimen.notification_children_collapsed_bottom_padding);
        this.mEnableShadowOnChildNotifications = resources.getBoolean(C1777R.bool.config_enableShadowOnChildNotifications);
        this.mShowGroupCountInExpander = resources.getBoolean(C1777R.bool.config_showNotificationGroupCountInExpander);
        this.mShowDividersWhenExpanded = resources.getBoolean(C1777R.bool.config_showDividersWhenGroupNotificationExpanded);
        this.mHideDividersDuringExpand = resources.getBoolean(C1777R.bool.config_hideDividersDuringExpand);
        this.mTranslationForHeader = resources.getDimensionPixelOffset(17105387) - this.mNotificationHeaderMargin;
        HybridGroupManager hybridGroupManager = this.mHybridGroupManager;
        Objects.requireNonNull(hybridGroupManager);
        Resources resources2 = hybridGroupManager.mContext.getResources();
        hybridGroupManager.mOverflowNumberSize = (float) resources2.getDimensionPixelSize(C1777R.dimen.group_overflow_number_size);
        hybridGroupManager.mOverflowNumberPadding = resources2.getDimensionPixelSize(C1777R.dimen.group_overflow_number_padding);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateGroupOverflow();
    }

    public NotificationChildrenContainer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mDividers = new ArrayList();
        this.mAttachedChildren = new ArrayList();
        this.mCurrentHeaderTranslation = 0;
        this.mHeaderVisibleAmount = 1.0f;
        this.mContainingNotificationIsFaded = false;
        this.mHybridGroupManager = new HybridGroupManager(getContext());
        initDimens();
        setClipChildren(false);
    }

    @VisibleForTesting
    public ViewGroup getCurrentHeaderView() {
        return this.mCurrentHeader;
    }

    @VisibleForTesting
    public boolean isUserLocked() {
        return this.mUserLocked;
    }
}
