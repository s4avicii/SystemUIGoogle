package com.android.systemui.p006qs;

import com.android.systemui.privacy.OngoingPrivacyChip;
import com.android.systemui.privacy.PrivacyItem;
import com.android.systemui.privacy.PrivacyItemController;
import java.util.List;
import java.util.Objects;

/* renamed from: com.android.systemui.qs.HeaderPrivacyIconsController$picCallback$1 */
/* compiled from: HeaderPrivacyIconsController.kt */
public final class HeaderPrivacyIconsController$picCallback$1 implements PrivacyItemController.Callback {
    public final /* synthetic */ HeaderPrivacyIconsController this$0;

    public HeaderPrivacyIconsController$picCallback$1(HeaderPrivacyIconsController headerPrivacyIconsController) {
        this.this$0 = headerPrivacyIconsController;
    }

    public final void onFlagLocationChanged(boolean z) {
        HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
        if (headerPrivacyIconsController.locationIndicatorsEnabled != z) {
            headerPrivacyIconsController.locationIndicatorsEnabled = z;
            headerPrivacyIconsController.updatePrivacyIconSlots();
            HeaderPrivacyIconsController headerPrivacyIconsController2 = this.this$0;
            OngoingPrivacyChip ongoingPrivacyChip = headerPrivacyIconsController2.privacyChip;
            Objects.requireNonNull(ongoingPrivacyChip);
            headerPrivacyIconsController2.setChipVisibility(!ongoingPrivacyChip.privacyList.isEmpty());
        }
    }

    public final void onFlagMicCameraChanged(boolean z) {
        HeaderPrivacyIconsController headerPrivacyIconsController = this.this$0;
        if (headerPrivacyIconsController.micCameraIndicatorsEnabled != z) {
            headerPrivacyIconsController.micCameraIndicatorsEnabled = z;
            headerPrivacyIconsController.updatePrivacyIconSlots();
            HeaderPrivacyIconsController headerPrivacyIconsController2 = this.this$0;
            OngoingPrivacyChip ongoingPrivacyChip = headerPrivacyIconsController2.privacyChip;
            Objects.requireNonNull(ongoingPrivacyChip);
            headerPrivacyIconsController2.setChipVisibility(!ongoingPrivacyChip.privacyList.isEmpty());
        }
    }

    public final void onPrivacyItemsChanged(List<PrivacyItem> list) {
        this.this$0.privacyChip.setPrivacyList(list);
        this.this$0.setChipVisibility(!list.isEmpty());
    }
}
