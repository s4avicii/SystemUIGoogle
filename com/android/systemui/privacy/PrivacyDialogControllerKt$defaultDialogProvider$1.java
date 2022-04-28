package com.android.systemui.privacy;

import android.content.Context;
import com.android.systemui.privacy.PrivacyDialogController;
import java.util.ArrayList;
import kotlin.jvm.functions.Function4;

/* compiled from: PrivacyDialogController.kt */
public final class PrivacyDialogControllerKt$defaultDialogProvider$1 implements PrivacyDialogController.DialogProvider {
    public final PrivacyDialog makeDialog(Context context, ArrayList arrayList, Function4 function4) {
        return new PrivacyDialog(context, arrayList, function4);
    }
}
