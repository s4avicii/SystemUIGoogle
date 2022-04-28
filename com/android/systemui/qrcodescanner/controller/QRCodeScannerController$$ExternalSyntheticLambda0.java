package com.android.systemui.qrcodescanner.controller;

import android.database.ContentObserver;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class QRCodeScannerController$$ExternalSyntheticLambda0 implements BiConsumer {
    public final /* synthetic */ QRCodeScannerController f$0;

    public /* synthetic */ QRCodeScannerController$$ExternalSyntheticLambda0(QRCodeScannerController qRCodeScannerController) {
        this.f$0 = qRCodeScannerController;
    }

    public final void accept(Object obj, Object obj2) {
        QRCodeScannerController qRCodeScannerController = this.f$0;
        Integer num = (Integer) obj;
        Objects.requireNonNull(qRCodeScannerController);
        qRCodeScannerController.mSecureSettings.unregisterContentObserver((ContentObserver) obj2);
    }
}
