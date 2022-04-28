package com.android.systemui.p006qs.carrier;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.text.TextUtils;
import android.util.Log;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline1;
import com.android.keyguard.CarrierTextManager;
import java.util.Objects;
import java.util.function.Consumer;

/* renamed from: com.android.systemui.qs.carrier.QSCarrierGroupController$$ExternalSyntheticLambda0 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QSCarrierGroupController$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ QSCarrierGroupController f$0;

    public /* synthetic */ QSCarrierGroupController$$ExternalSyntheticLambda0(QSCarrierGroupController qSCarrierGroupController) {
        this.f$0 = qSCarrierGroupController;
    }

    public final void accept(Object obj) {
        QSCarrierGroupController qSCarrierGroupController = this.f$0;
        CarrierTextManager.CarrierTextCallbackInfo carrierTextCallbackInfo = (CarrierTextManager.CarrierTextCallbackInfo) obj;
        Objects.requireNonNull(qSCarrierGroupController);
        if (!qSCarrierGroupController.mMainHandler.getLooper().isCurrentThread()) {
            qSCarrierGroupController.mMainHandler.obtainMessage(0, carrierTextCallbackInfo).sendToTarget();
            return;
        }
        qSCarrierGroupController.mNoSimTextView.setVisibility(8);
        if (carrierTextCallbackInfo.airplaneMode || !carrierTextCallbackInfo.anySimReady) {
            for (int i = 0; i < 3; i++) {
                CellSignalState[] cellSignalStateArr = qSCarrierGroupController.mInfos;
                cellSignalStateArr[i] = cellSignalStateArr[i].changeVisibility(false);
                QSCarrier qSCarrier = qSCarrierGroupController.mCarrierGroups[i];
                Objects.requireNonNull(qSCarrier);
                qSCarrier.mCarrierText.setText("");
                qSCarrierGroupController.mCarrierGroups[i].setVisibility(8);
            }
            qSCarrierGroupController.mNoSimTextView.setText(carrierTextCallbackInfo.carrierText);
            if (!TextUtils.isEmpty(carrierTextCallbackInfo.carrierText)) {
                qSCarrierGroupController.mNoSimTextView.setVisibility(0);
            }
        } else {
            boolean[] zArr = new boolean[3];
            if (carrierTextCallbackInfo.listOfCarriers.length == carrierTextCallbackInfo.subscriptionIds.length) {
                int i2 = 0;
                while (i2 < 3 && i2 < carrierTextCallbackInfo.listOfCarriers.length) {
                    int slotIndex = qSCarrierGroupController.getSlotIndex(carrierTextCallbackInfo.subscriptionIds[i2]);
                    if (slotIndex >= 3) {
                        GridLayoutManager$$ExternalSyntheticOutline1.m20m("updateInfoCarrier - slot: ", slotIndex, "QSCarrierGroup");
                    } else if (slotIndex == -1) {
                        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Invalid SIM slot index for subscription: ");
                        m.append(carrierTextCallbackInfo.subscriptionIds[i2]);
                        Log.e("QSCarrierGroup", m.toString());
                    } else {
                        CellSignalState[] cellSignalStateArr2 = qSCarrierGroupController.mInfos;
                        cellSignalStateArr2[slotIndex] = cellSignalStateArr2[slotIndex].changeVisibility(true);
                        zArr[slotIndex] = true;
                        QSCarrier qSCarrier2 = qSCarrierGroupController.mCarrierGroups[slotIndex];
                        String trim = carrierTextCallbackInfo.listOfCarriers[i2].toString().trim();
                        Objects.requireNonNull(qSCarrier2);
                        qSCarrier2.mCarrierText.setText(trim);
                        qSCarrierGroupController.mCarrierGroups[slotIndex].setVisibility(0);
                    }
                    i2++;
                }
                for (int i3 = 0; i3 < 3; i3++) {
                    if (!zArr[i3]) {
                        CellSignalState[] cellSignalStateArr3 = qSCarrierGroupController.mInfos;
                        cellSignalStateArr3[i3] = cellSignalStateArr3[i3].changeVisibility(false);
                        qSCarrierGroupController.mCarrierGroups[i3].setVisibility(8);
                    }
                }
            } else {
                Log.e("QSCarrierGroup", "Carrier information arrays not of same length");
            }
        }
        qSCarrierGroupController.handleUpdateState();
    }
}
