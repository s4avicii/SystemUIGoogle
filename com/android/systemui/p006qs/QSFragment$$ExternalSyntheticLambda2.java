package com.android.systemui.p006qs;

import android.view.View;
import com.android.systemui.plugins.p005qs.C0961QS;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.QSFragment$$ExternalSyntheticLambda2 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSFragment$$ExternalSyntheticLambda2 implements View.OnScrollChangeListener {
    public final /* synthetic */ QSFragment f$0;

    public /* synthetic */ QSFragment$$ExternalSyntheticLambda2(QSFragment qSFragment) {
        this.f$0 = qSFragment;
    }

    public final void onScrollChange(View view, int i, int i2, int i3, int i4) {
        QSFragment qSFragment = this.f$0;
        Objects.requireNonNull(qSFragment);
        QSAnimator qSAnimator = qSFragment.mQSAnimator;
        Objects.requireNonNull(qSAnimator);
        qSAnimator.mNeedsAnimatorUpdate = true;
        QuickStatusBarHeader quickStatusBarHeader = qSFragment.mHeader;
        Objects.requireNonNull(quickStatusBarHeader);
        quickStatusBarHeader.mStatusIconsView.setScrollY(i2);
        quickStatusBarHeader.mDatePrivacyView.setScrollY(i2);
        C0961QS.ScrollListener scrollListener = qSFragment.mScrollListener;
        if (scrollListener != null) {
            scrollListener.onQsPanelScrollChanged(i2);
        }
    }
}
