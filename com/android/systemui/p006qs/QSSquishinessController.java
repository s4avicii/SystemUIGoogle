package com.android.systemui.p006qs;

/* renamed from: com.android.systemui.qs.QSSquishinessController */
/* compiled from: QSSquishinessController.kt */
public final class QSSquishinessController {
    public final QSAnimator qsAnimator;
    public final QSPanelController qsPanelController;
    public final QuickQSPanelController quickQSPanelController;
    public float squishiness = 1.0f;

    public QSSquishinessController(QSAnimator qSAnimator, QSPanelController qSPanelController, QuickQSPanelController quickQSPanelController2) {
        this.qsAnimator = qSAnimator;
        this.qsPanelController = qSPanelController;
        this.quickQSPanelController = quickQSPanelController2;
    }
}
