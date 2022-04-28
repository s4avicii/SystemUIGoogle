package com.android.p012wm.shell.bubbles;

import android.content.pm.ShortcutInfo;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/* renamed from: com.android.wm.shell.bubbles.BubbleData$$ExternalSyntheticLambda8 */
/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class BubbleData$$ExternalSyntheticLambda8 implements Predicate {
    public final /* synthetic */ String f$0;
    public final /* synthetic */ Set f$1;

    public /* synthetic */ BubbleData$$ExternalSyntheticLambda8(String str, HashSet hashSet) {
        this.f$0 = str;
        this.f$1 = hashSet;
    }

    public final boolean test(Object obj) {
        boolean z;
        ShortcutInfo shortcutInfo;
        String str = this.f$0;
        Set set = this.f$1;
        Bubble bubble = (Bubble) obj;
        Objects.requireNonNull(bubble);
        boolean equals = str.equals(bubble.mPackageName);
        boolean hasMetadataShortcutId = bubble.hasMetadataShortcutId();
        if (!equals || !hasMetadataShortcutId) {
            return false;
        }
        if (!bubble.hasMetadataShortcutId() || (shortcutInfo = bubble.mShortcutInfo) == null || !shortcutInfo.isEnabled() || !set.contains(bubble.mShortcutInfo.getId())) {
            z = false;
        } else {
            z = true;
        }
        if (!equals || z) {
            return false;
        }
        return true;
    }
}
