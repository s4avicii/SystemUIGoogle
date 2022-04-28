package com.android.p012wm.shell.bubbles;

import java.util.ArrayList;
import java.util.function.Consumer;

/* renamed from: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda3 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ ArrayList f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda3(ArrayList arrayList, int i) {
        this.f$0 = arrayList;
        this.f$1 = i;
    }

    public final void accept(Object obj) {
        ArrayList arrayList = this.f$0;
        Bubble bubble = (Bubble) obj;
        if (arrayList.size() < this.f$1) {
            arrayList.add(bubble);
        }
    }
}
