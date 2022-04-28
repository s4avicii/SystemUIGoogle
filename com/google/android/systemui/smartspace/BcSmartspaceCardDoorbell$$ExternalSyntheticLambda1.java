package com.google.android.systemui.smartspace;

import android.content.ContentResolver;
import android.net.Uri;
import com.google.android.systemui.smartspace.BcSmartspaceCardDoorbell;
import java.util.function.Function;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BcSmartspaceCardDoorbell$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ ContentResolver f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ BcSmartspaceCardDoorbell$$ExternalSyntheticLambda1(ContentResolver contentResolver, int i, float f) {
        this.f$0 = contentResolver;
        this.f$1 = i;
        this.f$2 = f;
    }

    public final Object apply(Object obj) {
        ContentResolver contentResolver = this.f$0;
        int i = this.f$1;
        float f = this.f$2;
        int i2 = BcSmartspaceCardDoorbell.$r8$clinit;
        BcSmartspaceCardDoorbell.DrawableWithUri drawableWithUri = new BcSmartspaceCardDoorbell.DrawableWithUri((Uri) obj, contentResolver, i, f);
        new BcSmartspaceCardDoorbell.LoadUriTask().execute(new BcSmartspaceCardDoorbell.DrawableWithUri[]{drawableWithUri});
        return drawableWithUri;
    }
}
