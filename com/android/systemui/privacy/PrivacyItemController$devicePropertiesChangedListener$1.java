package com.android.systemui.privacy;

import android.provider.DeviceConfig;

/* compiled from: PrivacyItemController.kt */
public final class PrivacyItemController$devicePropertiesChangedListener$1 implements DeviceConfig.OnPropertiesChangedListener {
    public final /* synthetic */ PrivacyItemController this$0;

    public PrivacyItemController$devicePropertiesChangedListener$1(PrivacyItemController privacyItemController) {
        this.this$0 = privacyItemController;
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x005d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onPropertiesChanged(android.provider.DeviceConfig.Properties r8) {
        /*
            r7 = this;
            java.lang.String r0 = r8.getNamespace()
            java.lang.String r1 = "privacy"
            boolean r0 = r1.equals(r0)
            if (r0 == 0) goto L_0x00c6
            java.util.Set r0 = r8.getKeyset()
            java.lang.String r1 = "camera_mic_icons_enabled"
            boolean r0 = r0.contains(r1)
            java.lang.String r2 = "location_indicators_enabled"
            if (r0 != 0) goto L_0x0024
            java.util.Set r0 = r8.getKeyset()
            boolean r0 = r0.contains(r2)
            if (r0 == 0) goto L_0x00c6
        L_0x0024:
            java.util.Set r0 = r8.getKeyset()
            boolean r0 = r0.contains(r1)
            r3 = 0
            r4 = 1
            if (r0 == 0) goto L_0x0072
            com.android.systemui.privacy.PrivacyItemController r0 = r7.this$0
            boolean r1 = r8.getBoolean(r1, r4)
            r0.micCameraAvailable = r1
            com.android.systemui.privacy.PrivacyItemController r0 = r7.this$0
            java.util.Objects.requireNonNull(r0)
            boolean r1 = r0.micCameraAvailable
            if (r1 == 0) goto L_0x004c
            com.android.systemui.privacy.PrivacyItemController r1 = r7.this$0
            java.util.Objects.requireNonNull(r1)
            boolean r1 = r1.locationAvailable
            if (r1 == 0) goto L_0x004c
            r1 = r4
            goto L_0x004d
        L_0x004c:
            r1 = r3
        L_0x004d:
            r0.allIndicatorsAvailable = r1
            com.android.systemui.privacy.PrivacyItemController r0 = r7.this$0
            java.util.ArrayList r1 = r0.callbacks
            java.util.Iterator r1 = r1.iterator()
        L_0x0057:
            boolean r5 = r1.hasNext()
            if (r5 == 0) goto L_0x0072
            java.lang.Object r5 = r1.next()
            java.lang.ref.WeakReference r5 = (java.lang.ref.WeakReference) r5
            java.lang.Object r5 = r5.get()
            com.android.systemui.privacy.PrivacyItemController$Callback r5 = (com.android.systemui.privacy.PrivacyItemController.Callback) r5
            if (r5 != 0) goto L_0x006c
            goto L_0x0057
        L_0x006c:
            boolean r6 = r0.micCameraAvailable
            r5.onFlagMicCameraChanged(r6)
            goto L_0x0057
        L_0x0072:
            java.util.Set r0 = r8.getKeyset()
            boolean r0 = r0.contains(r2)
            if (r0 == 0) goto L_0x00bf
            com.android.systemui.privacy.PrivacyItemController r0 = r7.this$0
            boolean r8 = r8.getBoolean(r2, r3)
            java.util.Objects.requireNonNull(r0)
            r0.locationAvailable = r8
            com.android.systemui.privacy.PrivacyItemController r8 = r7.this$0
            java.util.Objects.requireNonNull(r8)
            boolean r0 = r8.micCameraAvailable
            if (r0 == 0) goto L_0x009a
            com.android.systemui.privacy.PrivacyItemController r0 = r7.this$0
            java.util.Objects.requireNonNull(r0)
            boolean r0 = r0.locationAvailable
            if (r0 == 0) goto L_0x009a
            r3 = r4
        L_0x009a:
            r8.allIndicatorsAvailable = r3
            com.android.systemui.privacy.PrivacyItemController r8 = r7.this$0
            java.util.ArrayList r0 = r8.callbacks
            java.util.Iterator r0 = r0.iterator()
        L_0x00a4:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x00bf
            java.lang.Object r1 = r0.next()
            java.lang.ref.WeakReference r1 = (java.lang.ref.WeakReference) r1
            java.lang.Object r1 = r1.get()
            com.android.systemui.privacy.PrivacyItemController$Callback r1 = (com.android.systemui.privacy.PrivacyItemController.Callback) r1
            if (r1 != 0) goto L_0x00b9
            goto L_0x00a4
        L_0x00b9:
            boolean r2 = r8.locationAvailable
            r1.onFlagLocationChanged(r2)
            goto L_0x00a4
        L_0x00bf:
            com.android.systemui.privacy.PrivacyItemController r7 = r7.this$0
            com.android.systemui.privacy.PrivacyItemController$MyExecutor r7 = r7.internalUiExecutor
            r7.updateListeningState()
        L_0x00c6:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.privacy.PrivacyItemController$devicePropertiesChangedListener$1.onPropertiesChanged(android.provider.DeviceConfig$Properties):void");
    }
}
