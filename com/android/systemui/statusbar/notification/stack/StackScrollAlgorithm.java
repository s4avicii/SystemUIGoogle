package com.android.systemui.statusbar.notification.stack;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import com.android.internal.policy.SystemBarUtils;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.NotificationShelf;
import com.android.systemui.statusbar.notification.row.ExpandableView;
import com.android.systemui.statusbar.notification.row.FooterView;
import java.util.ArrayList;
import java.util.Objects;

public final class StackScrollAlgorithm {
    public boolean mClipNotificationScrollToTop;
    public int mCollapsedSize;
    public int mGapHeight;
    public float mHeadsUpInset;
    public final ViewGroup mHostView;
    public boolean mIsExpanded;
    public int mMarginBottom;
    public float mNotificationScrimPadding;
    public int mPaddingBetweenElements;
    public int mPinnedZTranslationExtra;
    public StackScrollAlgorithmState mTempAlgorithmState = new StackScrollAlgorithmState();

    public interface BypassController {
        boolean isBypassEnabled();
    }

    public interface SectionProvider {
        boolean beginsSection(View view, View view2);
    }

    public static class StackScrollAlgorithmState {
        public ExpandableView firstViewInShelf;
        public int mCurrentExpandedYPosition;
        public int mCurrentYPosition;
        public final ArrayList<ExpandableView> visibleChildren = new ArrayList<>();
    }

    public final int getMaxAllowedChildHeight(ExpandableView expandableView) {
        if (expandableView instanceof ExpandableView) {
            return expandableView.getIntrinsicHeight();
        }
        if (expandableView == null) {
            return this.mCollapsedSize;
        }
        return expandableView.getHeight();
    }

    public StackScrollAlgorithm(Context context, ViewGroup viewGroup) {
        this.mHostView = viewGroup;
        initView(context);
    }

    public static boolean childNeedsGapHeight(SectionProvider sectionProvider, int i, View view, View view2) {
        if (!sectionProvider.beginsSection(view, view2) || i <= 0 || (view2 instanceof SectionHeaderView) || (view instanceof FooterView)) {
            return false;
        }
        return true;
    }

    public final float getExpansionFractionWithoutShelf(StackScrollAlgorithmState stackScrollAlgorithmState, AmbientState ambientState) {
        boolean z;
        float f;
        float f2;
        Objects.requireNonNull(ambientState);
        NotificationShelf notificationShelf = ambientState.mShelf;
        if (notificationShelf == null || stackScrollAlgorithmState.firstViewInShelf == null) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            Objects.requireNonNull(notificationShelf);
            f = (float) notificationShelf.getHeight();
        } else {
            f = 0.0f;
        }
        if (!ambientState.isOnKeyguard() || (ambientState.mBypassController.isBypassEnabled() && ambientState.isPulseExpanding())) {
            f2 = this.mNotificationScrimPadding;
        } else {
            f2 = 0.0f;
        }
        float f3 = (ambientState.mStackHeight - f) - f2;
        float f4 = (ambientState.mStackEndHeight - f) - f2;
        if (f4 == 0.0f) {
            return 0.0f;
        }
        return f3 / f4;
    }

    public final void initView(Context context) {
        Resources resources = context.getResources();
        this.mPaddingBetweenElements = resources.getDimensionPixelSize(C1777R.dimen.notification_divider_height);
        this.mCollapsedSize = resources.getDimensionPixelSize(C1777R.dimen.notification_min_height);
        this.mClipNotificationScrollToTop = resources.getBoolean(C1777R.bool.config_clipNotificationScrollToTop);
        this.mHeadsUpInset = (float) (resources.getDimensionPixelSize(C1777R.dimen.heads_up_status_bar_padding) + SystemBarUtils.getStatusBarHeight(context));
        this.mPinnedZTranslationExtra = resources.getDimensionPixelSize(C1777R.dimen.heads_up_pinned_elevation);
        this.mGapHeight = resources.getDimensionPixelSize(C1777R.dimen.notification_section_divider_height);
        this.mNotificationScrimPadding = (float) resources.getDimensionPixelSize(C1777R.dimen.notification_side_paddings);
        this.mMarginBottom = resources.getDimensionPixelSize(C1777R.dimen.notification_panel_margin_bottom);
    }
}
