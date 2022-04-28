package com.android.systemui.media;

import android.service.notification.StatusBarNotification;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$loadMediaData$1 implements Runnable {
    public final /* synthetic */ String $key;
    public final /* synthetic */ String $oldKey;
    public final /* synthetic */ StatusBarNotification $sbn;
    public final /* synthetic */ MediaDataManager this$0;

    public MediaDataManager$loadMediaData$1(MediaDataManager mediaDataManager, String str, StatusBarNotification statusBarNotification, String str2) {
        this.this$0 = mediaDataManager;
        this.$key = str;
        this.$sbn = statusBarNotification;
        this.$oldKey = str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x02d6  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x02eb  */
    /* JADX WARNING: Removed duplicated region for block: B:161:0x0442  */
    /* JADX WARNING: Removed duplicated region for block: B:98:0x02d0  */
    /* JADX WARNING: Removed duplicated region for block: B:99:0x02d2  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r33 = this;
            r0 = r33
            com.android.systemui.media.MediaDataManager r6 = r0.this$0
            java.lang.String r7 = r0.$key
            android.service.notification.StatusBarNotification r8 = r0.$sbn
            java.lang.String r9 = r0.$oldKey
            java.util.Objects.requireNonNull(r6)
            android.app.Notification r0 = r8.getNotification()
            android.os.Bundle r0 = r0.extras
            java.lang.String r1 = "android.mediaSession"
            android.os.Parcelable r0 = r0.getParcelable(r1)
            r13 = r0
            android.media.session.MediaSession$Token r13 = (android.media.session.MediaSession.Token) r13
            com.android.systemui.media.MediaControllerFactory r0 = r6.mediaControllerFactory
            java.util.Objects.requireNonNull(r0)
            android.media.session.MediaController r10 = new android.media.session.MediaController
            android.content.Context r0 = r0.mContext
            r10.<init>(r0, r13)
            android.media.MediaMetadata r0 = r10.getMetadata()
            android.app.Notification r14 = r8.getNotification()
            r1 = 0
            if (r0 != 0) goto L_0x0035
            r2 = r1
            goto L_0x003b
        L_0x0035:
            java.lang.String r2 = "android.media.metadata.ART"
            android.graphics.Bitmap r2 = r0.getBitmap(r2)
        L_0x003b:
            if (r2 != 0) goto L_0x0047
            if (r0 != 0) goto L_0x0041
            r2 = r1
            goto L_0x0047
        L_0x0041:
            java.lang.String r2 = "android.media.metadata.ALBUM_ART"
            android.graphics.Bitmap r2 = r0.getBitmap(r2)
        L_0x0047:
            java.lang.String r11 = "MediaDataManager"
            r3 = 3
            if (r2 != 0) goto L_0x0077
            if (r0 == 0) goto L_0x0077
            java.lang.String[] r2 = com.android.systemui.media.MediaDataManagerKt.ART_URIS
            r4 = 0
        L_0x0051:
            if (r4 >= r3) goto L_0x0076
            r5 = r2[r4]
            int r4 = r4 + 1
            java.lang.String r12 = r0.getString(r5)
            boolean r15 = android.text.TextUtils.isEmpty(r12)
            if (r15 != 0) goto L_0x0051
            android.net.Uri r12 = android.net.Uri.parse(r12)
            android.graphics.Bitmap r12 = r6.loadBitmapFromUri(r12)
            if (r12 == 0) goto L_0x0051
            java.lang.String r2 = "loaded art from "
            java.lang.String r2 = kotlin.jvm.internal.Intrinsics.stringPlus(r2, r5)
            android.util.Log.d(r11, r2)
            r2 = r12
            goto L_0x0077
        L_0x0076:
            r2 = r1
        L_0x0077:
            if (r2 != 0) goto L_0x007e
            android.graphics.drawable.Icon r2 = r14.getLargeIcon()
            goto L_0x0082
        L_0x007e:
            android.graphics.drawable.Icon r2 = android.graphics.drawable.Icon.createWithBitmap(r2)
        L_0x0082:
            r12 = r2
            android.content.Context r2 = r6.context
            android.app.Notification$Builder r2 = android.app.Notification.Builder.recoverBuilder(r2, r14)
            java.lang.String r15 = r2.loadHeaderAppName()
            android.app.Notification r2 = r8.getNotification()
            android.graphics.drawable.Icon r16 = r2.getSmallIcon()
            kotlin.jvm.internal.Ref$ObjectRef r4 = new kotlin.jvm.internal.Ref$ObjectRef
            r4.<init>()
            if (r0 != 0) goto L_0x009e
            r2 = r1
            goto L_0x00a4
        L_0x009e:
            java.lang.String r2 = "android.media.metadata.DISPLAY_TITLE"
            java.lang.String r2 = r0.getString(r2)
        L_0x00a4:
            r4.element = r2
            if (r2 != 0) goto L_0x00b4
            if (r0 != 0) goto L_0x00ac
            r2 = r1
            goto L_0x00b2
        L_0x00ac:
            java.lang.String r2 = "android.media.metadata.TITLE"
            java.lang.String r2 = r0.getString(r2)
        L_0x00b2:
            r4.element = r2
        L_0x00b4:
            T r2 = r4.element
            if (r2 != 0) goto L_0x00cc
            android.os.Bundle r2 = r14.extras
            java.lang.String r3 = "android.title"
            java.lang.CharSequence r2 = r2.getCharSequence(r3)
            if (r2 != 0) goto L_0x00ca
            android.os.Bundle r2 = r14.extras
            java.lang.String r3 = "android.title.big"
            java.lang.CharSequence r2 = r2.getCharSequence(r3)
        L_0x00ca:
            r4.element = r2
        L_0x00cc:
            kotlin.jvm.internal.Ref$ObjectRef r5 = new kotlin.jvm.internal.Ref$ObjectRef
            r5.<init>()
            if (r0 != 0) goto L_0x00d5
            r0 = r1
            goto L_0x00db
        L_0x00d5:
            java.lang.String r2 = "android.media.metadata.ARTIST"
            java.lang.String r0 = r0.getString(r2)
        L_0x00db:
            r5.element = r0
            if (r0 != 0) goto L_0x00f3
            android.os.Bundle r0 = r14.extras
            java.lang.String r2 = "android.text"
            java.lang.CharSequence r0 = r0.getCharSequence(r2)
            if (r0 != 0) goto L_0x00f1
            android.os.Bundle r0 = r14.extras
            java.lang.String r2 = "android.bigText"
            java.lang.CharSequence r0 = r0.getCharSequence(r2)
        L_0x00f1:
            r5.element = r0
        L_0x00f3:
            kotlin.jvm.internal.Ref$ObjectRef r2 = new kotlin.jvm.internal.Ref$ObjectRef
            r2.<init>()
            android.app.Notification r0 = r8.getNotification()
            android.os.Bundle r0 = r0.extras
            java.lang.String r3 = "android.mediaRemoteDevice"
            boolean r0 = r0.containsKey(r3)
            if (r0 == 0) goto L_0x0166
            android.app.Notification r0 = r8.getNotification()
            android.os.Bundle r0 = r0.extras
            java.lang.CharSequence r1 = r0.getCharSequence(r3, r1)
            r33 = r3
            java.lang.String r3 = "android.mediaRemoteIcon"
            r17 = r4
            r4 = -1
            int r3 = r0.getInt(r3, r4)
            java.lang.String r4 = "android.mediaRemoteIntent"
            android.os.Parcelable r0 = r0.getParcelable(r4)
            android.app.PendingIntent r0 = (android.app.PendingIntent) r0
            java.lang.StringBuilder r4 = new java.lang.StringBuilder
            r4.<init>()
            r4.append(r7)
            r18 = r5
            java.lang.String r5 = " is RCN for "
            r4.append(r5)
            r4.append(r1)
            java.lang.String r4 = r4.toString()
            android.util.Log.d(r11, r4)
            if (r1 == 0) goto L_0x016c
            r4 = -1
            if (r3 <= r4) goto L_0x016c
            if (r0 == 0) goto L_0x014b
            boolean r4 = r0.isActivity()
            if (r4 == 0) goto L_0x014b
            r4 = 1
            goto L_0x014c
        L_0x014b:
            r4 = 0
        L_0x014c:
            java.lang.String r5 = r8.getPackageName()
            android.graphics.drawable.Icon r3 = android.graphics.drawable.Icon.createWithResource(r5, r3)
            android.content.Context r5 = r6.context
            android.content.Context r5 = r8.getPackageContext(r5)
            android.graphics.drawable.Drawable r3 = r3.loadDrawable(r5)
            com.android.systemui.media.MediaDeviceData r5 = new com.android.systemui.media.MediaDeviceData
            r5.<init>(r4, r3, r1, r0)
            r2.element = r5
            goto L_0x016c
        L_0x0166:
            r33 = r3
            r17 = r4
            r18 = r5
        L_0x016c:
            kotlin.jvm.internal.Ref$ObjectRef r4 = new kotlin.jvm.internal.Ref$ObjectRef
            r4.<init>()
            kotlin.collections.EmptyList r0 = kotlin.collections.EmptyList.INSTANCE
            r4.element = r0
            kotlin.jvm.internal.Ref$ObjectRef r5 = new kotlin.jvm.internal.Ref$ObjectRef
            r5.<init>()
            r5.element = r0
            kotlin.jvm.internal.Ref$ObjectRef r3 = new kotlin.jvm.internal.Ref$ObjectRef
            r3.<init>()
            com.android.systemui.media.MediaFlags r0 = r6.mediaFlags
            java.util.Objects.requireNonNull(r0)
            com.android.systemui.flags.FeatureFlags r0 = r0.featureFlags
            com.android.systemui.flags.BooleanFlag r1 = com.android.systemui.flags.Flags.MEDIA_SESSION_ACTIONS
            boolean r0 = r0.isEnabled((com.android.systemui.flags.BooleanFlag) r1)
            if (r0 == 0) goto L_0x0319
            android.media.session.PlaybackState r0 = r10.getPlaybackState()
            if (r0 == 0) goto L_0x0319
            java.lang.String r1 = r8.getPackageName()
            com.android.systemui.media.MediaButton r0 = new com.android.systemui.media.MediaButton
            r19 = r1
            r1 = 0
            r0.<init>(r1)
            android.media.session.PlaybackState r20 = r10.getPlaybackState()
            if (r20 != 0) goto L_0x01c0
            r26 = r33
            r27 = r2
            r24 = r7
            r23 = r9
            r22 = r12
            r28 = r13
            r33 = r14
            r29 = r15
            r21 = r18
            r9 = r0
            r13 = r3
            r12 = r4
            r15 = r5
            goto L_0x030d
        L_0x01c0:
            int r1 = r20.getState()
            boolean r1 = com.android.systemui.statusbar.NotificationMediaManager.isPlayingState(r1)
            if (r1 == 0) goto L_0x01f3
            long r21 = r20.getActions()
            r23 = 2
            r1 = r0
            r0 = r6
            r26 = r1
            r25 = r19
            r1 = r10
            r27 = r2
            r28 = r13
            r13 = r3
            r32 = r14
            r14 = r33
            r33 = r32
            r2 = r21
            r22 = r12
            r29 = r15
            r21 = r18
            r12 = r4
            r15 = r5
            r4 = r23
            com.android.systemui.media.MediaAction r0 = r0.getStandardAction(r1, r2, r4)
            goto L_0x0216
        L_0x01f3:
            r26 = r0
            r27 = r2
            r22 = r12
            r28 = r13
            r29 = r15
            r21 = r18
            r25 = r19
            r13 = r3
            r12 = r4
            r15 = r5
            r32 = r14
            r14 = r33
            r33 = r32
            long r2 = r20.getActions()
            r4 = 4
            r0 = r6
            r1 = r10
            com.android.systemui.media.MediaAction r0 = r0.getStandardAction(r1, r2, r4)
        L_0x0216:
            r4 = r26
            r4.playOrPause = r0
            long r2 = r20.getActions()
            r18 = 16
            r0 = r6
            r1 = r10
            r23 = r9
            r9 = r4
            r4 = r18
            com.android.systemui.media.MediaAction r18 = r0.getStandardAction(r1, r2, r4)
            long r2 = r20.getActions()
            r4 = 32
            com.android.systemui.media.MediaAction r0 = r0.getStandardAction(r1, r2, r4)
            r1 = 4
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>(r1)
            r3 = 0
        L_0x023c:
            if (r3 >= r1) goto L_0x0245
            int r3 = r3 + 1
            r4 = 0
            r2.add(r4)
            goto L_0x023c
        L_0x0245:
            r1 = 0
            r3 = 0
        L_0x0247:
            int r4 = r3 + 1
            java.util.List r5 = r20.getCustomActions()
            int r5 = r5.size()
            if (r5 <= r1) goto L_0x0287
            java.util.List r5 = r20.getCustomActions()
            java.lang.Object r5 = r5.get(r1)
            if (r5 != 0) goto L_0x025e
            goto L_0x0287
        L_0x025e:
            java.util.List r5 = r20.getCustomActions()
            java.lang.Object r5 = r5.get(r1)
            android.media.session.PlaybackState$CustomAction r5 = (android.media.session.PlaybackState.CustomAction) r5
            r19 = r4
            com.android.systemui.media.MediaAction r4 = new com.android.systemui.media.MediaAction
            r24 = r7
            int r7 = r5.getIcon()
            r26 = r14
            r14 = r25
            android.graphics.drawable.Icon r7 = android.graphics.drawable.Icon.createWithResource(r14, r7)
            com.android.systemui.media.MediaDataManager$getCustomAction$1 r14 = new com.android.systemui.media.MediaDataManager$getCustomAction$1
            r14.<init>(r10, r5)
            java.lang.CharSequence r5 = r5.getName()
            r4.<init>(r7, r14, r5)
            goto L_0x029b
        L_0x0287:
            r19 = r4
            r24 = r7
            r26 = r14
            java.lang.Integer r4 = java.lang.Integer.valueOf(r1)
            java.lang.String r5 = "not enough actions or action was null at "
            java.lang.String r4 = kotlin.jvm.internal.Intrinsics.stringPlus(r5, r4)
            android.util.Log.d(r11, r4)
            r4 = 0
        L_0x029b:
            if (r4 != 0) goto L_0x029e
            goto L_0x02a4
        L_0x029e:
            int r5 = r1 + 1
            r2.set(r1, r4)
            r1 = r5
        L_0x02a4:
            r4 = 3
            if (r3 != r4) goto L_0x0311
            android.os.Bundle r1 = r10.getExtras()
            if (r1 != 0) goto L_0x02af
            r1 = 1
            goto L_0x02bb
        L_0x02af:
            java.lang.String r3 = "android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_PREVIOUS"
            boolean r1 = r1.getBoolean(r3)
            r3 = 1
            if (r1 != r3) goto L_0x02ba
            r1 = r3
            goto L_0x02c1
        L_0x02ba:
            r1 = r3
        L_0x02bb:
            r3 = 0
            r32 = r3
            r3 = r1
            r1 = r32
        L_0x02c1:
            android.os.Bundle r4 = r10.getExtras()
            if (r4 != 0) goto L_0x02c8
            goto L_0x02d2
        L_0x02c8:
            java.lang.String r5 = "android.media.playback.ALWAYS_RESERVE_SPACE_FOR.ACTION_SKIP_TO_NEXT"
            boolean r4 = r4.getBoolean(r5)
            if (r4 != r3) goto L_0x02d2
            r3 = 1
            goto L_0x02d3
        L_0x02d2:
            r3 = 0
        L_0x02d3:
            if (r18 == 0) goto L_0x02d6
            goto L_0x02e3
        L_0x02d6:
            if (r1 != 0) goto L_0x02e1
            r1 = 0
            java.lang.Object r1 = r2.get(r1)
            com.android.systemui.media.MediaAction r1 = (com.android.systemui.media.MediaAction) r1
            r4 = 1
            goto L_0x02e6
        L_0x02e1:
            r18 = 0
        L_0x02e3:
            r4 = 0
            r1 = r18
        L_0x02e6:
            r9.prevOrCustom = r1
            if (r0 == 0) goto L_0x02eb
            goto L_0x02f9
        L_0x02eb:
            if (r3 != 0) goto L_0x02f8
            int r0 = r4 + 1
            java.lang.Object r1 = r2.get(r4)
            com.android.systemui.media.MediaAction r1 = (com.android.systemui.media.MediaAction) r1
            r4 = r0
            r0 = r1
            goto L_0x02f9
        L_0x02f8:
            r0 = 0
        L_0x02f9:
            r9.nextOrCustom = r0
            int r0 = r4 + 1
            java.lang.Object r1 = r2.get(r4)
            com.android.systemui.media.MediaAction r1 = (com.android.systemui.media.MediaAction) r1
            r9.startCustom = r1
            java.lang.Object r0 = r2.get(r0)
            com.android.systemui.media.MediaAction r0 = (com.android.systemui.media.MediaAction) r0
            r9.endCustom = r0
        L_0x030d:
            r13.element = r9
            goto L_0x041b
        L_0x0311:
            r3 = r19
            r7 = r24
            r14 = r26
            goto L_0x0247
        L_0x0319:
            r26 = r33
            r27 = r2
            r24 = r7
            r23 = r9
            r22 = r12
            r28 = r13
            r33 = r14
            r29 = r15
            r21 = r18
            r13 = r3
            r12 = r4
            r15 = r5
            android.app.Notification r0 = r8.getNotification()
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            android.app.Notification$Action[] r2 = r0.actions
            android.os.Bundle r0 = r0.extras
            java.lang.String r3 = "android.compactActions"
            int[] r0 = r0.getIntArray(r3)
            if (r0 != 0) goto L_0x0345
            r0 = 0
            goto L_0x035c
        L_0x0345:
            java.util.ArrayList r3 = new java.util.ArrayList
            int r4 = r0.length
            r3.<init>(r4)
            int r4 = r0.length
            r5 = 0
        L_0x034d:
            if (r5 >= r4) goto L_0x035b
            r7 = r0[r5]
            int r5 = r5 + 1
            java.lang.Integer r7 = java.lang.Integer.valueOf(r7)
            r3.add(r7)
            goto L_0x034d
        L_0x035b:
            r0 = r3
        L_0x035c:
            if (r0 != 0) goto L_0x0363
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
        L_0x0363:
            int r3 = r0.size()
            r4 = 3
            if (r3 <= r4) goto L_0x038c
            java.lang.String r3 = "Too many compact actions for "
            java.lang.StringBuilder r3 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r3)
            java.lang.String r5 = r8.getKey()
            r3.append(r5)
            java.lang.String r5 = ",limiting to first "
            r3.append(r5)
            r3.append(r4)
            java.lang.String r3 = r3.toString()
            android.util.Log.e(r11, r3)
            r3 = 0
            java.util.List r0 = r0.subList(r3, r4)
            goto L_0x038d
        L_0x038c:
            r3 = 0
        L_0x038d:
            if (r2 == 0) goto L_0x040a
            int r4 = r2.length
        L_0x0390:
            if (r3 >= r4) goto L_0x040a
            r5 = r2[r3]
            int r7 = r3 + 1
            android.graphics.drawable.Icon r9 = r5.getIcon()
            if (r9 != 0) goto L_0x03c2
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            r9.<init>()
            java.lang.String r14 = "No icon for action "
            r9.append(r14)
            r9.append(r3)
            r14 = 32
            r9.append(r14)
            java.lang.CharSequence r5 = r5.title
            r9.append(r5)
            java.lang.String r5 = r9.toString()
            android.util.Log.i(r11, r5)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            r0.remove(r3)
            goto L_0x0408
        L_0x03c2:
            android.app.PendingIntent r3 = r5.actionIntent
            if (r3 == 0) goto L_0x03cc
            com.android.systemui.media.MediaDataManager$createActionsFromNotification$runnable$1 r3 = new com.android.systemui.media.MediaDataManager$createActionsFromNotification$runnable$1
            r3.<init>(r5, r6)
            goto L_0x03cd
        L_0x03cc:
            r3 = 0
        L_0x03cd:
            android.graphics.drawable.Icon r9 = r5.getIcon()
            if (r9 != 0) goto L_0x03d4
            goto L_0x03dd
        L_0x03d4:
            int r9 = r9.getType()
            r14 = 2
            if (r9 != r14) goto L_0x03dd
            r9 = 1
            goto L_0x03de
        L_0x03dd:
            r9 = 0
        L_0x03de:
            if (r9 == 0) goto L_0x03f4
            java.lang.String r9 = r8.getPackageName()
            android.graphics.drawable.Icon r14 = r5.getIcon()
            kotlin.jvm.internal.Intrinsics.checkNotNull(r14)
            int r14 = r14.getResId()
            android.graphics.drawable.Icon r9 = android.graphics.drawable.Icon.createWithResource(r9, r14)
            goto L_0x03f8
        L_0x03f4:
            android.graphics.drawable.Icon r9 = r5.getIcon()
        L_0x03f8:
            int r14 = r6.themeText
            android.graphics.drawable.Icon r9 = r9.setTint(r14)
            com.android.systemui.media.MediaAction r14 = new com.android.systemui.media.MediaAction
            java.lang.CharSequence r5 = r5.title
            r14.<init>(r9, r3, r5)
            r1.add(r14)
        L_0x0408:
            r3 = r7
            goto L_0x0390
        L_0x040a:
            kotlin.Pair r2 = new kotlin.Pair
            r2.<init>(r1, r0)
            java.lang.Object r0 = r2.getFirst()
            r12.element = r0
            java.lang.Object r0 = r2.getSecond()
            r15.element = r0
        L_0x041b:
            android.app.Notification r0 = r8.getNotification()
            android.os.Bundle r0 = r0.extras
            r1 = r26
            boolean r0 = r0.containsKey(r1)
            if (r0 == 0) goto L_0x042d
            r0 = 2
        L_0x042a:
            r20 = r0
            goto L_0x0444
        L_0x042d:
            android.media.session.MediaController$PlaybackInfo r0 = r10.getPlaybackInfo()
            if (r0 != 0) goto L_0x0435
            r0 = 1
            goto L_0x043f
        L_0x0435:
            int r0 = r0.getPlaybackType()
            r1 = 1
            if (r0 != r1) goto L_0x043e
            r0 = r1
            goto L_0x0440
        L_0x043e:
            r0 = r1
        L_0x043f:
            r1 = 0
        L_0x0440:
            if (r1 == 0) goto L_0x042a
            r0 = 0
            goto L_0x042a
        L_0x0444:
            android.media.session.PlaybackState r0 = r10.getPlaybackState()
            if (r0 != 0) goto L_0x044c
            r0 = 0
            goto L_0x0458
        L_0x044c:
            int r0 = r0.getState()
            boolean r0 = com.android.systemui.statusbar.NotificationMediaManager.isPlayingState(r0)
            java.lang.Boolean r0 = java.lang.Boolean.valueOf(r0)
        L_0x0458:
            r25 = r0
            com.android.systemui.util.time.SystemClock r0 = r6.systemClock
            long r18 = r0.elapsedRealtime()
            com.android.systemui.util.concurrency.DelayableExecutor r14 = r6.foregroundExecutor
            com.android.systemui.media.MediaDataManager$loadMediaDataInBg$1 r11 = new com.android.systemui.media.MediaDataManager$loadMediaDataInBg$1
            r0 = r11
            r1 = r6
            r2 = r24
            r3 = r23
            r4 = r8
            r5 = r29
            r6 = r16
            r7 = r21
            r8 = r17
            r9 = r22
            r10 = r12
            r12 = r11
            r11 = r15
            r15 = r12
            r12 = r13
            r13 = r28
            r30 = r14
            r14 = r33
            r31 = r15
            r15 = r27
            r16 = r20
            r17 = r25
            r0.<init>(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            r0 = r30
            r1 = r31
            r0.execute(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaDataManager$loadMediaData$1.run():void");
    }
}
