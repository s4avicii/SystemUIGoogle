package com.android.systemui.privacy;

import androidx.fragment.R$styleable;
import com.android.systemui.privacy.PrivacyDialog;
import java.util.Comparator;
import java.util.Objects;

/* renamed from: com.android.systemui.privacy.PrivacyDialogController$filterAndSelect$lambda-6$$inlined$sortedByDescending$1 */
/* compiled from: Comparisons.kt */
public final class C0971xfdcce2a3<T> implements Comparator {
    public final int compare(T t, T t2) {
        PrivacyDialog.PrivacyElement privacyElement = (PrivacyDialog.PrivacyElement) t2;
        Objects.requireNonNull(privacyElement);
        Long valueOf = Long.valueOf(privacyElement.lastActiveTimestamp);
        PrivacyDialog.PrivacyElement privacyElement2 = (PrivacyDialog.PrivacyElement) t;
        Objects.requireNonNull(privacyElement2);
        return R$styleable.compareValues(valueOf, Long.valueOf(privacyElement2.lastActiveTimestamp));
    }
}
