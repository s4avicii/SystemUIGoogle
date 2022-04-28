package com.android.systemui.privacy.logging;

import com.android.systemui.privacy.PrivacyItem;
import java.util.Objects;
import kotlin.jvm.internal.PropertyReference1Impl;

/* compiled from: PrivacyLogger.kt */
final /* synthetic */ class PrivacyLogger$listToString$1 extends PropertyReference1Impl {
    public static final PrivacyLogger$listToString$1 INSTANCE = new PrivacyLogger$listToString$1();

    public PrivacyLogger$listToString$1() {
        super(PrivacyItem.class, "log", "getLog()Ljava/lang/String;", 0);
    }

    public final Object get(Object obj) {
        PrivacyItem privacyItem = (PrivacyItem) obj;
        Objects.requireNonNull(privacyItem);
        return privacyItem.log;
    }
}
