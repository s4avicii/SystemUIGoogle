package com.google.android.setupdesign.template;

import android.annotation.TargetApi;
import com.google.android.setupcompat.partnerconfig.PartnerConfig;
import com.google.android.setupcompat.template.Mixin;
import com.google.android.setupdesign.GlifLayout;

@TargetApi(14)
@Deprecated
public final class IllustrationProgressMixin implements Mixin {

    public enum ProgressConfig {
        ;
        
        private final PartnerConfig config;

        /* access modifiers changed from: public */
        ProgressConfig(PartnerConfig partnerConfig) {
            if (partnerConfig.getResourceType() == PartnerConfig.ResourceType.ILLUSTRATION) {
                this.config = partnerConfig;
                return;
            }
            throw new IllegalArgumentException("Illustration progress only allow illustration resource");
        }
    }

    public IllustrationProgressMixin(GlifLayout glifLayout) {
        ProgressConfig[] progressConfigArr = ProgressConfig.$VALUES;
        glifLayout.getContext();
    }
}
