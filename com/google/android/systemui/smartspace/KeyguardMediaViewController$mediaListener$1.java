package com.google.android.systemui.smartspace;

import android.media.MediaMetadata;
import com.android.systemui.statusbar.NotificationMediaManager;
import java.util.Objects;

/* compiled from: KeyguardMediaViewController.kt */
public final class KeyguardMediaViewController$mediaListener$1 implements NotificationMediaManager.MediaListener {
    public final /* synthetic */ KeyguardMediaViewController this$0;

    public KeyguardMediaViewController$mediaListener$1(KeyguardMediaViewController keyguardMediaViewController) {
        this.this$0 = keyguardMediaViewController;
    }

    public final void onPrimaryMetadataOrStateChanged(MediaMetadata mediaMetadata, int i) {
        KeyguardMediaViewController keyguardMediaViewController = this.this$0;
        Objects.requireNonNull(keyguardMediaViewController);
        keyguardMediaViewController.uiExecutor.execute(new C2312xbd560d81(this.this$0, mediaMetadata, i));
    }
}
