package com.google.android.systemui.smartspace;

import android.content.ContentResolver;
import android.net.Uri;
import com.google.android.systemui.smartspace.BcSmartspaceCardDoorbell;
import java.util.Objects;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2 implements Function {
    public final /* synthetic */ BcSmartspaceCardDoorbell f$0;
    public final /* synthetic */ ContentResolver f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ float f$3;

    public /* synthetic */ BcSmartspaceCardDoorbell$$ExternalSyntheticLambda2(BcSmartspaceCardDoorbell bcSmartspaceCardDoorbell, ContentResolver contentResolver, int i, float f) {
        this.f$0 = bcSmartspaceCardDoorbell;
        this.f$1 = contentResolver;
        this.f$2 = i;
        this.f$3 = f;
    }

    public final Object apply(Object obj) {
        BcSmartspaceCardDoorbell bcSmartspaceCardDoorbell = this.f$0;
        ContentResolver contentResolver = this.f$1;
        int i = this.f$2;
        float f = this.f$3;
        int i2 = BcSmartspaceCardDoorbell.$r8$clinit;
        Objects.requireNonNull(bcSmartspaceCardDoorbell);
        return (BcSmartspaceCardDoorbell.DrawableWithUri) bcSmartspaceCardDoorbell.mUriToDrawable.computeIfAbsent((Uri) obj, new BcSmartspaceCardDoorbell$$ExternalSyntheticLambda1(contentResolver, i, f));
    }
}
