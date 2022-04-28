package com.android.keyguard;

import com.android.systemui.tuner.TunerService;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class KeyguardSliceViewController$$ExternalSyntheticLambda0 implements TunerService.Tunable {
    public final /* synthetic */ KeyguardSliceViewController f$0;

    public /* synthetic */ KeyguardSliceViewController$$ExternalSyntheticLambda0(KeyguardSliceViewController keyguardSliceViewController) {
        this.f$0 = keyguardSliceViewController;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x003e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onTuningChanged(java.lang.String r4, java.lang.String r5) {
        /*
            r3 = this;
            com.android.keyguard.KeyguardSliceViewController r3 = r3.f$0
            java.util.Objects.requireNonNull(r3)
            if (r5 != 0) goto L_0x0009
            java.lang.String r5 = "content://com.android.systemui.keyguard/main"
        L_0x0009:
            androidx.slice.widget.SliceLiveData$SliceLiveDataImpl r4 = r3.mLiveData
            r0 = 1
            r1 = 0
            if (r4 == 0) goto L_0x001e
            int r2 = r4.mActiveCount
            if (r2 <= 0) goto L_0x0015
            r2 = r0
            goto L_0x0016
        L_0x0015:
            r2 = r1
        L_0x0016:
            if (r2 == 0) goto L_0x001e
            com.android.keyguard.KeyguardSliceViewController$2 r1 = r3.mObserver
            r4.removeObserver(r1)
            goto L_0x001f
        L_0x001e:
            r0 = r1
        L_0x001f:
            android.net.Uri r4 = android.net.Uri.parse(r5)
            r3.mKeyguardSliceUri = r4
            T r4 = r3.mView
            com.android.keyguard.KeyguardSliceView r4 = (com.android.keyguard.KeyguardSliceView) r4
            android.content.Context r4 = r4.getContext()
            android.net.Uri r5 = r3.mKeyguardSliceUri
            androidx.collection.ArraySet r1 = androidx.slice.widget.SliceLiveData.SUPPORTED_SPECS
            androidx.slice.widget.SliceLiveData$SliceLiveDataImpl r1 = new androidx.slice.widget.SliceLiveData$SliceLiveDataImpl
            android.content.Context r4 = r4.getApplicationContext()
            r1.<init>(r4, r5)
            r3.mLiveData = r1
            if (r0 == 0) goto L_0x0043
            com.android.keyguard.KeyguardSliceViewController$2 r3 = r3.mObserver
            r1.observeForever(r3)
        L_0x0043:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.KeyguardSliceViewController$$ExternalSyntheticLambda0.onTuningChanged(java.lang.String, java.lang.String):void");
    }
}
