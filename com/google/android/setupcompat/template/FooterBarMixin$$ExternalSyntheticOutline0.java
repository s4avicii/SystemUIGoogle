package com.google.android.setupcompat.template;

import android.content.Context;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.partnerconfig.PartnerConfigHelper;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.ServiceConfigurationError;
import kotlinx.coroutines.CoroutineExceptionHandler;
import kotlinx.coroutines.android.AndroidExceptionPreHandler;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class FooterBarMixin$$ExternalSyntheticOutline0 {
    /* renamed from: m */
    public static float m82m(Context context, Context context2, PartnerConfig partnerConfig, float f) {
        PartnerConfigHelper partnerConfigHelper = PartnerConfigHelper.get(context);
        Objects.requireNonNull(partnerConfigHelper);
        return partnerConfigHelper.getDimension(context2, partnerConfig, f);
    }

    /* renamed from: m */
    public static /* synthetic */ Iterator m83m() {
        try {
            return Arrays.asList(new CoroutineExceptionHandler[]{new AndroidExceptionPreHandler()}).iterator();
        } catch (Throwable th) {
            throw new ServiceConfigurationError(th.getMessage(), th);
        }
    }
}
