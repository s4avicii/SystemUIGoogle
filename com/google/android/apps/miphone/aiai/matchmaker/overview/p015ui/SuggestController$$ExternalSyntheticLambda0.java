package com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui;

import android.os.Bundle;
import android.util.Log;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.ContentParcelables$Contents;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$ErrorCode;
import com.google.android.apps.miphone.aiai.matchmaker.overview.api.generatedv2.SuggestParcelables$SetupInfo;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.ContentSuggestionsServiceWrapper;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.LogUtils;
import com.google.android.apps.miphone.aiai.matchmaker.overview.p015ui.utils.Utils;
import java.util.Objects;

/* renamed from: com.google.android.apps.miphone.aiai.matchmaker.overview.ui.SuggestController$$ExternalSyntheticLambda0 */
/* compiled from: D8$$SyntheticClass */
public final /* synthetic */ class SuggestController$$ExternalSyntheticLambda0 implements ContentSuggestionsServiceWrapper.BundleCallback {
    public final /* synthetic */ SuggestController f$0;

    public /* synthetic */ SuggestController$$ExternalSyntheticLambda0(SuggestController suggestController) {
        this.f$0 = suggestController;
    }

    public final void onResult(Bundle bundle) {
        ContentParcelables$Contents contentParcelables$Contents;
        String str;
        SuggestController suggestController = this.f$0;
        Objects.requireNonNull(suggestController);
        try {
            Objects.requireNonNull(suggestController.bundleUtils);
            Bundle bundle2 = bundle.getBundle("Contents");
            if (bundle2 == null) {
                contentParcelables$Contents = new ContentParcelables$Contents();
            } else {
                contentParcelables$Contents = new ContentParcelables$Contents(bundle2);
            }
            SuggestParcelables$SetupInfo suggestParcelables$SetupInfo = contentParcelables$Contents.setupInfo;
            if (suggestParcelables$SetupInfo == null) {
                LogUtils.m81e("System intelligence is unavailable.", (Throwable) null);
                return;
            }
            int i = Utils.$r8$clinit;
            if (suggestParcelables$SetupInfo.errorCode == SuggestParcelables$ErrorCode.ERROR_CODE_SUCCESS) {
                Log.i("AiAiSuggestUi", "Successfully connected to system intelligence: ");
                return;
            }
            String valueOf = String.valueOf(suggestParcelables$SetupInfo.errorMesssage);
            if (valueOf.length() != 0) {
                str = "Unable to connect to system intelligence: ".concat(valueOf);
            } else {
                str = new String("Unable to connect to system intelligence: ");
            }
            LogUtils.m81e(str, (Throwable) null);
        } catch (Exception e) {
            LogUtils.m81e("Unable to connect to system intelligence module.", e);
        }
    }
}
