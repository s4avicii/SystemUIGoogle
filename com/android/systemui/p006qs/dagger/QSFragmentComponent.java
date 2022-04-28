package com.android.systemui.p006qs.dagger;

import com.android.systemui.p006qs.FooterActionsController;
import com.android.systemui.p006qs.QSAnimator;
import com.android.systemui.p006qs.QSContainerImplController;
import com.android.systemui.p006qs.QSFooter;
import com.android.systemui.p006qs.QSFragment;
import com.android.systemui.p006qs.QSPanelController;
import com.android.systemui.p006qs.QSSquishinessController;
import com.android.systemui.p006qs.QuickQSPanelController;
import com.android.systemui.p006qs.customize.QSCustomizerController;

/* renamed from: com.android.systemui.qs.dagger.QSFragmentComponent */
public interface QSFragmentComponent {

    /* renamed from: com.android.systemui.qs.dagger.QSFragmentComponent$Factory */
    public interface Factory {
        QSFragmentComponent create(QSFragment qSFragment);
    }

    QSAnimator getQSAnimator();

    QSContainerImplController getQSContainerImplController();

    QSCustomizerController getQSCustomizerController();

    QSFooter getQSFooter();

    FooterActionsController getQSFooterActionController();

    QSPanelController getQSPanelController();

    QSSquishinessController getQSSquishinessController();

    QuickQSPanelController getQuickQSPanelController();
}
