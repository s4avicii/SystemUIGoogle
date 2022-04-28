package com.google.android.systemui.smartspace;

import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class SmartSpaceController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ SmartSpaceController f$0;
    public final /* synthetic */ NewCardInfo f$1;
    public final /* synthetic */ SmartSpaceCard f$2;

    public /* synthetic */ SmartSpaceController$$ExternalSyntheticLambda1(SmartSpaceController smartSpaceController, NewCardInfo newCardInfo, SmartSpaceCard smartSpaceCard) {
        this.f$0 = smartSpaceController;
        this.f$1 = newCardInfo;
        this.f$2 = smartSpaceCard;
    }

    public final void run() {
        SmartSpaceController smartSpaceController = this.f$0;
        NewCardInfo newCardInfo = this.f$1;
        SmartSpaceCard smartSpaceCard = this.f$2;
        Objects.requireNonNull(smartSpaceController);
        Objects.requireNonNull(newCardInfo);
        if (newCardInfo.mIsPrimary) {
            smartSpaceController.mData.mCurrentCard = smartSpaceCard;
        } else {
            smartSpaceController.mData.mWeatherCard = smartSpaceCard;
        }
        smartSpaceController.mData.handleExpire();
        smartSpaceController.update();
    }
}
