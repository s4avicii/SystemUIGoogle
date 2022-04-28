package com.android.systemui.p006qs;

import android.view.View;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.util.ViewController;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QSFooterViewController */
public final class QSFooterViewController extends ViewController<QSFooterView> implements QSFooter {
    public final ActivityStarter mActivityStarter;
    public final TextView mBuildText;
    public final View mEditButton;
    public final FalsingManager mFalsingManager;
    public final PageIndicator mPageIndicator;
    public final QSPanelController mQsPanelController;
    public final UserTracker mUserTracker;

    public final void onViewDetached() {
    }

    public final void disable(int i) {
        QSFooterView qSFooterView = (QSFooterView) this.mView;
        Objects.requireNonNull(qSFooterView);
        boolean z = true;
        if ((i & 1) == 0) {
            z = false;
        }
        if (z != qSFooterView.mQsDisabled) {
            qSFooterView.mQsDisabled = z;
            qSFooterView.post(new QSFooterView$$ExternalSyntheticLambda0(qSFooterView, 0));
        }
    }

    public final void onViewAttached() {
        this.mBuildText.setOnLongClickListener(new QSFooterViewController$$ExternalSyntheticLambda1(this));
        this.mEditButton.setOnClickListener(new QSFooterViewController$$ExternalSyntheticLambda0(this, 0));
        QSPanelController qSPanelController = this.mQsPanelController;
        PageIndicator pageIndicator = this.mPageIndicator;
        Objects.requireNonNull(qSPanelController);
        QSPanel qSPanel = (QSPanel) qSPanelController.mView;
        Objects.requireNonNull(qSPanel);
        if (qSPanel.mTileLayout instanceof PagedTileLayout) {
            qSPanel.mFooterPageIndicator = pageIndicator;
            qSPanel.updatePageIndicator();
        }
        QSFooterView qSFooterView = (QSFooterView) this.mView;
        Objects.requireNonNull(qSFooterView);
        qSFooterView.post(new QSFooterView$$ExternalSyntheticLambda0(qSFooterView, 0));
    }

    public final void setExpandClickListener(View.OnClickListener onClickListener) {
        QSFooterView qSFooterView = (QSFooterView) this.mView;
        Objects.requireNonNull(qSFooterView);
        qSFooterView.mExpandClickListener = onClickListener;
    }

    public final void setExpanded(boolean z) {
        QSFooterView qSFooterView = (QSFooterView) this.mView;
        Objects.requireNonNull(qSFooterView);
        if (qSFooterView.mExpanded != z) {
            qSFooterView.mExpanded = z;
            qSFooterView.post(new QSFooterView$$ExternalSyntheticLambda0(qSFooterView, 0));
        }
    }

    public final void setExpansion(float f) {
        QSFooterView qSFooterView = (QSFooterView) this.mView;
        Objects.requireNonNull(qSFooterView);
        qSFooterView.mExpansionAmount = f;
        TouchAnimator touchAnimator = qSFooterView.mFooterAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(f);
        }
    }

    public final void setKeyguardShowing(boolean z) {
        QSFooterView qSFooterView = (QSFooterView) this.mView;
        Objects.requireNonNull(qSFooterView);
        float f = qSFooterView.mExpansionAmount;
        qSFooterView.mExpansionAmount = f;
        TouchAnimator touchAnimator = qSFooterView.mFooterAnimator;
        if (touchAnimator != null) {
            touchAnimator.setPosition(f);
        }
    }

    public final void setVisibility(int i) {
        boolean z;
        ((QSFooterView) this.mView).setVisibility(i);
        View view = this.mEditButton;
        if (i == 0) {
            z = true;
        } else {
            z = false;
        }
        view.setClickable(z);
    }

    public QSFooterViewController(QSFooterView qSFooterView, UserTracker userTracker, FalsingManager falsingManager, ActivityStarter activityStarter, QSPanelController qSPanelController) {
        super(qSFooterView);
        this.mUserTracker = userTracker;
        this.mQsPanelController = qSPanelController;
        this.mFalsingManager = falsingManager;
        this.mActivityStarter = activityStarter;
        QSFooterView qSFooterView2 = qSFooterView;
        this.mBuildText = (TextView) qSFooterView.findViewById(C1777R.C1779id.build);
        QSFooterView qSFooterView3 = qSFooterView;
        this.mPageIndicator = (PageIndicator) qSFooterView.findViewById(C1777R.C1779id.footer_page_indicator);
        QSFooterView qSFooterView4 = qSFooterView;
        this.mEditButton = qSFooterView.findViewById(16908291);
    }
}
