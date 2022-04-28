package com.android.p012wm.shell.bubbles;

import com.android.p012wm.shell.C1777R;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy;
import com.android.systemui.statusbar.phone.PhoneStatusBarPolicy$$ExternalSyntheticLambda1;
import java.util.Objects;

/* renamed from: com.android.wm.shell.bubbles.BubbleStackView$$ExternalSyntheticLambda23 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleStackView$$ExternalSyntheticLambda23 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ BubbleStackView$$ExternalSyntheticLambda23(Object obj, boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = z;
    }

    public final void run() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                BubbleStackView bubbleStackView = (BubbleStackView) this.f$0;
                if (!this.f$1) {
                    bubbleStackView.mManageMenuScrim.setVisibility(4);
                    bubbleStackView.mManageMenuScrim.setTranslationZ(0.0f);
                    return;
                }
                Objects.requireNonNull(bubbleStackView);
                return;
            default:
                PhoneStatusBarPolicy phoneStatusBarPolicy = (PhoneStatusBarPolicy) this.f$0;
                boolean z2 = this.f$1;
                boolean z3 = PhoneStatusBarPolicy.DEBUG;
                Objects.requireNonNull(phoneStatusBarPolicy);
                if (!z2 || (phoneStatusBarPolicy.mKeyguardStateController.isShowing() && !phoneStatusBarPolicy.mKeyguardStateController.isOccluded())) {
                    z = false;
                } else {
                    z = true;
                    phoneStatusBarPolicy.mIconController.setIcon(phoneStatusBarPolicy.mSlotManagedProfile, C1777R.C1778drawable.stat_sys_managed_profile_status, phoneStatusBarPolicy.mDevicePolicyManager.getString("SystemUi.STATUS_BAR_WORK_ICON_ACCESSIBILITY", new PhoneStatusBarPolicy$$ExternalSyntheticLambda1(phoneStatusBarPolicy)));
                }
                if (phoneStatusBarPolicy.mManagedProfileIconVisible != z) {
                    phoneStatusBarPolicy.mIconController.setIconVisibility(phoneStatusBarPolicy.mSlotManagedProfile, z);
                    phoneStatusBarPolicy.mManagedProfileIconVisible = z;
                    return;
                }
                return;
        }
    }
}
