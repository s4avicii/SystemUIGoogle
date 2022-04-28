package com.android.systemui.media;

/* compiled from: MediaSessionBasedFilter.kt */
public final class MediaSessionBasedFilter$onMediaDataLoaded$1 implements Runnable {
    public final /* synthetic */ MediaData $data;
    public final /* synthetic */ boolean $immediately;
    public final /* synthetic */ String $key;
    public final /* synthetic */ String $oldKey;
    public final /* synthetic */ MediaSessionBasedFilter this$0;

    public MediaSessionBasedFilter$onMediaDataLoaded$1(MediaData mediaData, String str, String str2, MediaSessionBasedFilter mediaSessionBasedFilter, boolean z) {
        this.$data = mediaData;
        this.$oldKey = str;
        this.$key = str2;
        this.this$0 = mediaSessionBasedFilter;
        this.$immediately = z;
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v0, resolved type: android.media.session.MediaController} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v1, resolved type: android.media.session.MediaController} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: android.media.session.MediaController} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v10, resolved type: android.media.session.MediaController} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void run() {
        /*
            r10 = this;
            com.android.systemui.media.MediaData r0 = r10.$data
            java.util.Objects.requireNonNull(r0)
            android.media.session.MediaSession$Token r0 = r0.token
            if (r0 != 0) goto L_0x000a
            goto L_0x0011
        L_0x000a:
            com.android.systemui.media.MediaSessionBasedFilter r1 = r10.this$0
            java.util.LinkedHashSet r1 = r1.tokensWithNotifications
            r1.add(r0)
        L_0x0011:
            java.lang.String r0 = r10.$oldKey
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0021
            java.lang.String r3 = r10.$key
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r3, r0)
            if (r0 != 0) goto L_0x0021
            r0 = r1
            goto L_0x0022
        L_0x0021:
            r0 = r2
        L_0x0022:
            if (r0 == 0) goto L_0x003f
            com.android.systemui.media.MediaSessionBasedFilter r3 = r10.this$0
            java.util.LinkedHashMap r3 = r3.keyedTokens
            java.lang.String r4 = r10.$oldKey
            java.lang.Object r3 = r3.remove(r4)
            java.util.Set r3 = (java.util.Set) r3
            if (r3 != 0) goto L_0x0033
            goto L_0x003f
        L_0x0033:
            com.android.systemui.media.MediaSessionBasedFilter r4 = r10.this$0
            java.lang.String r5 = r10.$key
            java.util.LinkedHashMap r4 = r4.keyedTokens
            java.lang.Object r3 = r4.put(r5, r3)
            java.util.Set r3 = (java.util.Set) r3
        L_0x003f:
            com.android.systemui.media.MediaData r3 = r10.$data
            java.util.Objects.requireNonNull(r3)
            android.media.session.MediaSession$Token r3 = r3.token
            r4 = 0
            if (r3 == 0) goto L_0x0095
            com.android.systemui.media.MediaSessionBasedFilter r3 = r10.this$0
            java.util.LinkedHashMap r3 = r3.keyedTokens
            java.lang.String r5 = r10.$key
            java.lang.Object r3 = r3.get(r5)
            java.util.Set r3 = (java.util.Set) r3
            if (r3 != 0) goto L_0x0059
            r3 = r4
            goto L_0x0068
        L_0x0059:
            com.android.systemui.media.MediaData r5 = r10.$data
            java.util.Objects.requireNonNull(r5)
            android.media.session.MediaSession$Token r5 = r5.token
            boolean r3 = r3.add(r5)
            java.lang.Boolean r3 = java.lang.Boolean.valueOf(r3)
        L_0x0068:
            if (r3 != 0) goto L_0x0095
            com.android.systemui.media.MediaSessionBasedFilter r3 = r10.this$0
            com.android.systemui.media.MediaData r5 = r10.$data
            java.lang.String r6 = r10.$key
            android.media.session.MediaSession$Token[] r7 = new android.media.session.MediaSession.Token[r1]
            java.util.Objects.requireNonNull(r5)
            android.media.session.MediaSession$Token r5 = r5.token
            r7[r2] = r5
            java.util.LinkedHashSet r5 = new java.util.LinkedHashSet
            int r8 = kotlin.collections.MapsKt__MapsJVMKt.mapCapacity(r1)
            r5.<init>(r8)
            r8 = r2
        L_0x0083:
            if (r8 >= r1) goto L_0x008d
            r9 = r7[r8]
            int r8 = r8 + 1
            r5.add(r9)
            goto L_0x0083
        L_0x008d:
            java.util.LinkedHashMap r3 = r3.keyedTokens
            java.lang.Object r3 = r3.put(r6, r5)
            java.util.Set r3 = (java.util.Set) r3
        L_0x0095:
            com.android.systemui.media.MediaSessionBasedFilter r3 = r10.this$0
            java.util.LinkedHashMap<java.lang.String, java.util.List<android.media.session.MediaController>> r3 = r3.packageControllers
            com.android.systemui.media.MediaData r5 = r10.$data
            java.util.Objects.requireNonNull(r5)
            java.lang.String r5 = r5.packageName
            java.lang.Object r3 = r3.get(r5)
            java.util.List r3 = (java.util.List) r3
            if (r3 != 0) goto L_0x00aa
            r5 = r4
            goto L_0x00d7
        L_0x00aa:
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Iterator r3 = r3.iterator()
        L_0x00b3:
            boolean r6 = r3.hasNext()
            if (r6 == 0) goto L_0x00d7
            java.lang.Object r6 = r3.next()
            r7 = r6
            android.media.session.MediaController r7 = (android.media.session.MediaController) r7
            android.media.session.MediaController$PlaybackInfo r7 = r7.getPlaybackInfo()
            if (r7 != 0) goto L_0x00c7
            goto L_0x00d0
        L_0x00c7:
            int r7 = r7.getPlaybackType()
            r8 = 2
            if (r7 != r8) goto L_0x00d0
            r7 = r1
            goto L_0x00d1
        L_0x00d0:
            r7 = r2
        L_0x00d1:
            if (r7 == 0) goto L_0x00b3
            r5.add(r6)
            goto L_0x00b3
        L_0x00d7:
            if (r5 != 0) goto L_0x00da
            goto L_0x00e1
        L_0x00da:
            int r3 = r5.size()
            if (r3 != r1) goto L_0x00e1
            goto L_0x00e2
        L_0x00e1:
            r1 = r2
        L_0x00e2:
            if (r1 == 0) goto L_0x00f1
            boolean r1 = r5.isEmpty()
            if (r1 == 0) goto L_0x00eb
            goto L_0x00ef
        L_0x00eb:
            java.lang.Object r4 = r5.get(r2)
        L_0x00ef:
            android.media.session.MediaController r4 = (android.media.session.MediaController) r4
        L_0x00f1:
            if (r0 != 0) goto L_0x016f
            if (r4 == 0) goto L_0x016f
            android.media.session.MediaSession$Token r0 = r4.getSessionToken()
            com.android.systemui.media.MediaData r1 = r10.$data
            java.util.Objects.requireNonNull(r1)
            android.media.session.MediaSession$Token r1 = r1.token
            boolean r0 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r1)
            if (r0 != 0) goto L_0x016f
            com.android.systemui.media.MediaSessionBasedFilter r0 = r10.this$0
            java.util.LinkedHashSet r0 = r0.tokensWithNotifications
            android.media.session.MediaSession$Token r1 = r4.getSessionToken()
            boolean r0 = r0.contains(r1)
            if (r0 != 0) goto L_0x0115
            goto L_0x016f
        L_0x0115:
            java.lang.String r0 = "filtering key="
            java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
            java.lang.String r1 = r10.$key
            r0.append(r1)
            java.lang.String r1 = " local="
            r0.append(r1)
            com.android.systemui.media.MediaData r1 = r10.$data
            java.util.Objects.requireNonNull(r1)
            android.media.session.MediaSession$Token r1 = r1.token
            r0.append(r1)
            java.lang.String r1 = " remote="
            r0.append(r1)
            android.media.session.MediaSession$Token r1 = r4.getSessionToken()
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r1 = "MediaSessionBasedFilter"
            android.util.Log.d(r1, r0)
            com.android.systemui.media.MediaSessionBasedFilter r0 = r10.this$0
            java.util.LinkedHashMap r0 = r0.keyedTokens
            java.lang.String r1 = r10.$key
            java.lang.Object r0 = r0.get(r1)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            java.util.Set r0 = (java.util.Set) r0
            android.media.session.MediaSession$Token r1 = r4.getSessionToken()
            boolean r0 = r0.contains(r1)
            if (r0 != 0) goto L_0x0187
            com.android.systemui.media.MediaSessionBasedFilter r0 = r10.this$0
            java.lang.String r10 = r10.$key
            java.util.Objects.requireNonNull(r0)
            java.util.concurrent.Executor r1 = r0.foregroundExecutor
            com.android.systemui.media.MediaSessionBasedFilter$dispatchMediaDataRemoved$1 r2 = new com.android.systemui.media.MediaSessionBasedFilter$dispatchMediaDataRemoved$1
            r2.<init>(r0, r10)
            r1.execute(r2)
            goto L_0x0187
        L_0x016f:
            com.android.systemui.media.MediaSessionBasedFilter r4 = r10.this$0
            java.lang.String r5 = r10.$key
            java.lang.String r6 = r10.$oldKey
            com.android.systemui.media.MediaData r7 = r10.$data
            boolean r8 = r10.$immediately
            java.util.Objects.requireNonNull(r4)
            java.util.concurrent.Executor r10 = r4.foregroundExecutor
            com.android.systemui.media.MediaSessionBasedFilter$dispatchMediaDataLoaded$1 r0 = new com.android.systemui.media.MediaSessionBasedFilter$dispatchMediaDataLoaded$1
            r3 = r0
            r3.<init>(r4, r5, r6, r7, r8)
            r10.execute(r0)
        L_0x0187:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaSessionBasedFilter$onMediaDataLoaded$1.run():void");
    }
}
