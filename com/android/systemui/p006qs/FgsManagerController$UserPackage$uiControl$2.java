package com.android.systemui.p006qs;

import android.content.pm.PackageManager;
import com.android.systemui.p006qs.FgsManagerController;
import java.util.Objects;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

/* renamed from: com.android.systemui.qs.FgsManagerController$UserPackage$uiControl$2 */
/* compiled from: FgsManagerController.kt */
public final class FgsManagerController$UserPackage$uiControl$2 extends Lambda implements Function0<FgsManagerController.UIControl> {
    public final /* synthetic */ FgsManagerController this$0;
    public final /* synthetic */ FgsManagerController.UserPackage this$1;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FgsManagerController$UserPackage$uiControl$2(FgsManagerController fgsManagerController, FgsManagerController.UserPackage userPackage) {
        super(0);
        this.this$0 = fgsManagerController;
        this.this$1 = userPackage;
    }

    public final Object invoke() {
        PackageManager packageManager = this.this$0.packageManager;
        FgsManagerController.UserPackage userPackage = this.this$1;
        Objects.requireNonNull(userPackage);
        String str = userPackage.packageName;
        FgsManagerController.UserPackage userPackage2 = this.this$1;
        Objects.requireNonNull(userPackage2);
        int backgroundRestrictionExemptionReason = this.this$0.activityManager.getBackgroundRestrictionExemptionReason(packageManager.getPackageUidAsUser(str, userPackage2.userId));
        if (!(backgroundRestrictionExemptionReason == 10 || backgroundRestrictionExemptionReason == 11)) {
            if (backgroundRestrictionExemptionReason == 51 || backgroundRestrictionExemptionReason == 63) {
                return FgsManagerController.UIControl.HIDE_ENTRY;
            }
            if (!(backgroundRestrictionExemptionReason == 318 || backgroundRestrictionExemptionReason == 320 || backgroundRestrictionExemptionReason == 55 || backgroundRestrictionExemptionReason == 56)) {
                return FgsManagerController.UIControl.NORMAL;
            }
        }
        return FgsManagerController.UIControl.HIDE_BUTTON;
    }
}
