package com.google.android.systemui.smartspace;

import android.media.MediaMetadata;

/* renamed from: com.google.android.systemui.smartspace.KeyguardMediaViewController$mediaListener$1$onPrimaryMetadataOrStateChanged$1 */
/* compiled from: KeyguardMediaViewController.kt */
public final class C2312xbd560d81 implements Runnable {
    public final /* synthetic */ MediaMetadata $metadata;
    public final /* synthetic */ int $state;
    public final /* synthetic */ KeyguardMediaViewController this$0;

    public C2312xbd560d81(KeyguardMediaViewController keyguardMediaViewController, MediaMetadata mediaMetadata, int i) {
        this.this$0 = keyguardMediaViewController;
        this.$metadata = mediaMetadata;
        this.$state = i;
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x00ae  */
    /* JADX WARNING: Removed duplicated region for block: B:32:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r6 = this;
            com.google.android.systemui.smartspace.KeyguardMediaViewController r0 = r6.this$0
            android.media.MediaMetadata r1 = r6.$metadata
            int r6 = r6.$state
            java.util.Objects.requireNonNull(r0)
            boolean r6 = com.android.systemui.statusbar.NotificationMediaManager.isPlayingState(r6)
            r2 = 0
            if (r6 != 0) goto L_0x001f
            r0.title = r2
            r0.artist = r2
            com.android.systemui.plugins.BcSmartspaceDataPlugin$SmartspaceView r6 = r0.smartspaceView
            if (r6 != 0) goto L_0x001a
            goto L_0x00ba
        L_0x001a:
            r6.setMediaTarget(r2)
            goto L_0x00ba
        L_0x001f:
            if (r1 != 0) goto L_0x0023
            r6 = r2
            goto L_0x003c
        L_0x0023:
            java.lang.String r6 = "android.media.metadata.TITLE"
            java.lang.CharSequence r6 = r1.getText(r6)
            boolean r3 = android.text.TextUtils.isEmpty(r6)
            if (r3 == 0) goto L_0x003c
            android.content.Context r6 = r0.context
            android.content.res.Resources r6 = r6.getResources()
            r3 = 2131952861(0x7f1304dd, float:1.9542177E38)
            java.lang.String r6 = r6.getString(r3)
        L_0x003c:
            if (r1 != 0) goto L_0x0040
            r1 = r2
            goto L_0x0046
        L_0x0040:
            java.lang.String r3 = "android.media.metadata.ARTIST"
            java.lang.CharSequence r1 = r1.getText(r3)
        L_0x0046:
            java.lang.CharSequence r3 = r0.title
            boolean r3 = android.text.TextUtils.equals(r3, r6)
            if (r3 == 0) goto L_0x0057
            java.lang.CharSequence r3 = r0.artist
            boolean r3 = android.text.TextUtils.equals(r3, r1)
            if (r3 == 0) goto L_0x0057
            goto L_0x00ba
        L_0x0057:
            r0.title = r6
            r0.artist = r1
            if (r6 != 0) goto L_0x005e
            goto L_0x00a5
        L_0x005e:
            android.app.smartspace.SmartspaceAction$Builder r1 = new android.app.smartspace.SmartspaceAction$Builder
            java.lang.String r6 = r6.toString()
            java.lang.String r3 = "deviceMediaTitle"
            r1.<init>(r3, r6)
            java.lang.CharSequence r6 = r0.artist
            android.app.smartspace.SmartspaceAction$Builder r6 = r1.setSubtitle(r6)
            com.android.systemui.statusbar.NotificationMediaManager r1 = r0.mediaManager
            android.graphics.drawable.Icon r1 = r1.getMediaIcon()
            android.app.smartspace.SmartspaceAction$Builder r6 = r6.setIcon(r1)
            android.app.smartspace.SmartspaceAction r6 = r6.build()
            com.google.android.systemui.smartspace.KeyguardMediaViewController$init$2 r1 = r0.userTracker
            if (r1 != 0) goto L_0x0082
            r1 = r2
        L_0x0082:
            int r1 = r1.getCurrentUserId()
            android.os.UserHandle r1 = android.os.UserHandle.of(r1)
            android.app.smartspace.SmartspaceTarget$Builder r3 = new android.app.smartspace.SmartspaceTarget$Builder
            android.content.ComponentName r4 = r0.mediaComponent
            java.lang.String r5 = "deviceMedia"
            r3.<init>(r5, r4, r1)
            r1 = 31
            android.app.smartspace.SmartspaceTarget$Builder r1 = r3.setFeatureType(r1)
            android.app.smartspace.SmartspaceTarget$Builder r6 = r1.setHeaderAction(r6)
            android.app.smartspace.SmartspaceTarget r6 = r6.build()
            com.android.systemui.plugins.BcSmartspaceDataPlugin$SmartspaceView r1 = r0.smartspaceView
            if (r1 != 0) goto L_0x00a7
        L_0x00a5:
            r6 = r2
            goto L_0x00ac
        L_0x00a7:
            r1.setMediaTarget(r6)
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
        L_0x00ac:
            if (r6 != 0) goto L_0x00ba
            r0.title = r2
            r0.artist = r2
            com.android.systemui.plugins.BcSmartspaceDataPlugin$SmartspaceView r6 = r0.smartspaceView
            if (r6 != 0) goto L_0x00b7
            goto L_0x00ba
        L_0x00b7:
            r6.setMediaTarget(r2)
        L_0x00ba:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.systemui.smartspace.C2312xbd560d81.run():void");
    }
}
