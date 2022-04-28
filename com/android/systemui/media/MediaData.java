package com.android.systemui.media;

import android.app.PendingIntent;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Icon;
import android.media.session.MediaSession;
import com.android.keyguard.FontInterpolator$VarFontKey$$ExternalSyntheticOutline0;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaData.kt */
public final class MediaData {
    public final List<MediaAction> actions;
    public final List<Integer> actionsToShowInCompact;
    public boolean active;
    public final String app;
    public final Icon appIcon;
    public final CharSequence artist;
    public final Icon artwork;
    public final int backgroundColor;
    public final PendingIntent clickIntent;
    public final MediaDeviceData device;
    public boolean hasCheckedForResume;
    public final boolean initialized;
    public final boolean isClearable;
    public final Boolean isPlaying;
    public long lastActive;
    public final String notificationKey;
    public final String packageName;
    public int playbackLocation;
    public Runnable resumeAction;
    public boolean resumption;
    public final MediaButton semanticActions;
    public final CharSequence song;
    public final MediaSession.Token token;
    public final int userId;

    public MediaData(int i, boolean z, int i2, String str, Icon icon, CharSequence charSequence, CharSequence charSequence2, Icon icon2, List<MediaAction> list, List<Integer> list2, MediaButton mediaButton, String str2, MediaSession.Token token2, PendingIntent pendingIntent, MediaDeviceData mediaDeviceData, boolean z2, Runnable runnable, int i3, boolean z3, String str3, boolean z4, Boolean bool, boolean z5, long j) {
        this.userId = i;
        this.initialized = z;
        this.backgroundColor = i2;
        this.app = str;
        this.appIcon = icon;
        this.artist = charSequence;
        this.song = charSequence2;
        this.artwork = icon2;
        this.actions = list;
        this.actionsToShowInCompact = list2;
        this.semanticActions = mediaButton;
        this.packageName = str2;
        this.token = token2;
        this.clickIntent = pendingIntent;
        this.device = mediaDeviceData;
        this.active = z2;
        this.resumeAction = runnable;
        this.playbackLocation = i3;
        this.resumption = z3;
        this.notificationKey = str3;
        this.hasCheckedForResume = z4;
        this.isPlaying = bool;
        this.isClearable = z5;
        this.lastActive = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MediaData)) {
            return false;
        }
        MediaData mediaData = (MediaData) obj;
        return this.userId == mediaData.userId && this.initialized == mediaData.initialized && this.backgroundColor == mediaData.backgroundColor && Intrinsics.areEqual(this.app, mediaData.app) && Intrinsics.areEqual(this.appIcon, mediaData.appIcon) && Intrinsics.areEqual(this.artist, mediaData.artist) && Intrinsics.areEqual(this.song, mediaData.song) && Intrinsics.areEqual(this.artwork, mediaData.artwork) && Intrinsics.areEqual(this.actions, mediaData.actions) && Intrinsics.areEqual(this.actionsToShowInCompact, mediaData.actionsToShowInCompact) && Intrinsics.areEqual(this.semanticActions, mediaData.semanticActions) && Intrinsics.areEqual(this.packageName, mediaData.packageName) && Intrinsics.areEqual(this.token, mediaData.token) && Intrinsics.areEqual(this.clickIntent, mediaData.clickIntent) && Intrinsics.areEqual(this.device, mediaData.device) && this.active == mediaData.active && Intrinsics.areEqual(this.resumeAction, mediaData.resumeAction) && this.playbackLocation == mediaData.playbackLocation && this.resumption == mediaData.resumption && Intrinsics.areEqual(this.notificationKey, mediaData.notificationKey) && this.hasCheckedForResume == mediaData.hasCheckedForResume && Intrinsics.areEqual(this.isPlaying, mediaData.isPlaying) && this.isClearable == mediaData.isClearable && this.lastActive == mediaData.lastActive;
    }

    public static MediaData copy$default(MediaData mediaData, List list, List list2, String str, MediaDeviceData mediaDeviceData, boolean z, MediaResumeListener$getResumeAction$1 mediaResumeListener$getResumeAction$1, boolean z2, boolean z3, Boolean bool, boolean z4, int i) {
        int i2;
        boolean z5;
        int i3;
        String str2;
        Icon icon;
        CharSequence charSequence;
        CharSequence charSequence2;
        Icon icon2;
        List<MediaAction> list3;
        List<Integer> list4;
        MediaButton mediaButton;
        String str3;
        MediaSession.Token token2;
        PendingIntent pendingIntent;
        MediaDeviceData mediaDeviceData2;
        boolean z6;
        Runnable runnable;
        boolean z7;
        String str4;
        boolean z8;
        Boolean bool2;
        boolean z9;
        long j;
        MediaData mediaData2 = mediaData;
        int i4 = i;
        int i5 = 0;
        if ((i4 & 1) != 0) {
            i2 = mediaData2.userId;
        } else {
            i2 = 0;
        }
        if ((i4 & 2) != 0) {
            z5 = mediaData2.initialized;
        } else {
            z5 = false;
        }
        if ((i4 & 4) != 0) {
            i3 = mediaData2.backgroundColor;
        } else {
            i3 = 0;
        }
        if ((i4 & 8) != 0) {
            str2 = mediaData2.app;
        } else {
            str2 = null;
        }
        if ((i4 & 16) != 0) {
            icon = mediaData2.appIcon;
        } else {
            icon = null;
        }
        if ((i4 & 32) != 0) {
            charSequence = mediaData2.artist;
        } else {
            charSequence = null;
        }
        if ((i4 & 64) != 0) {
            charSequence2 = mediaData2.song;
        } else {
            charSequence2 = null;
        }
        if ((i4 & 128) != 0) {
            icon2 = mediaData2.artwork;
        } else {
            icon2 = null;
        }
        if ((i4 & 256) != 0) {
            list3 = mediaData2.actions;
        } else {
            list3 = list;
        }
        if ((i4 & 512) != 0) {
            list4 = mediaData2.actionsToShowInCompact;
        } else {
            list4 = list2;
        }
        if ((i4 & 1024) != 0) {
            mediaButton = mediaData2.semanticActions;
        } else {
            mediaButton = null;
        }
        if ((i4 & 2048) != 0) {
            str3 = mediaData2.packageName;
        } else {
            str3 = str;
        }
        if ((i4 & 4096) != 0) {
            token2 = mediaData2.token;
        } else {
            token2 = null;
        }
        if ((i4 & 8192) != 0) {
            pendingIntent = mediaData2.clickIntent;
        } else {
            pendingIntent = null;
        }
        if ((i4 & 16384) != 0) {
            mediaDeviceData2 = mediaData2.device;
        } else {
            mediaDeviceData2 = mediaDeviceData;
        }
        if ((32768 & i4) != 0) {
            z6 = mediaData2.active;
        } else {
            z6 = z;
        }
        if ((65536 & i4) != 0) {
            runnable = mediaData2.resumeAction;
        } else {
            runnable = mediaResumeListener$getResumeAction$1;
        }
        if ((131072 & i4) != 0) {
            i5 = mediaData2.playbackLocation;
        }
        int i6 = i5;
        if ((262144 & i4) != 0) {
            z7 = mediaData2.resumption;
        } else {
            z7 = z2;
        }
        if ((524288 & i4) != 0) {
            str4 = mediaData2.notificationKey;
        } else {
            str4 = null;
        }
        if ((1048576 & i4) != 0) {
            z8 = mediaData2.hasCheckedForResume;
        } else {
            z8 = z3;
        }
        if ((2097152 & i4) != 0) {
            bool2 = mediaData2.isPlaying;
        } else {
            bool2 = bool;
        }
        if ((4194304 & i4) != 0) {
            z9 = mediaData2.isClearable;
        } else {
            z9 = z4;
        }
        if ((i4 & 8388608) != 0) {
            j = mediaData2.lastActive;
        } else {
            j = 0;
        }
        Objects.requireNonNull(mediaData);
        return new MediaData(i2, z5, i3, str2, icon, charSequence, charSequence2, icon2, list3, list4, mediaButton, str3, token2, pendingIntent, mediaDeviceData2, z6, runnable, i6, z7, str4, z8, bool2, z9, j);
    }

    public final int hashCode() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int hashCode = Integer.hashCode(this.userId) * 31;
        boolean z = this.initialized;
        boolean z2 = true;
        if (z) {
            z = true;
        }
        int m = FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.backgroundColor, (hashCode + (z ? 1 : 0)) * 31, 31);
        String str = this.app;
        int i12 = 0;
        if (str == null) {
            i = 0;
        } else {
            i = str.hashCode();
        }
        int i13 = (m + i) * 31;
        Icon icon = this.appIcon;
        if (icon == null) {
            i2 = 0;
        } else {
            i2 = icon.hashCode();
        }
        int i14 = (i13 + i2) * 31;
        CharSequence charSequence = this.artist;
        if (charSequence == null) {
            i3 = 0;
        } else {
            i3 = charSequence.hashCode();
        }
        int i15 = (i14 + i3) * 31;
        CharSequence charSequence2 = this.song;
        if (charSequence2 == null) {
            i4 = 0;
        } else {
            i4 = charSequence2.hashCode();
        }
        int i16 = (i15 + i4) * 31;
        Icon icon2 = this.artwork;
        if (icon2 == null) {
            i5 = 0;
        } else {
            i5 = icon2.hashCode();
        }
        int hashCode2 = (this.actionsToShowInCompact.hashCode() + ((this.actions.hashCode() + ((i16 + i5) * 31)) * 31)) * 31;
        MediaButton mediaButton = this.semanticActions;
        if (mediaButton == null) {
            i6 = 0;
        } else {
            i6 = mediaButton.hashCode();
        }
        int hashCode3 = (this.packageName.hashCode() + ((hashCode2 + i6) * 31)) * 31;
        MediaSession.Token token2 = this.token;
        if (token2 == null) {
            i7 = 0;
        } else {
            i7 = token2.hashCode();
        }
        int i17 = (hashCode3 + i7) * 31;
        PendingIntent pendingIntent = this.clickIntent;
        if (pendingIntent == null) {
            i8 = 0;
        } else {
            i8 = pendingIntent.hashCode();
        }
        int i18 = (i17 + i8) * 31;
        MediaDeviceData mediaDeviceData = this.device;
        if (mediaDeviceData == null) {
            i9 = 0;
        } else {
            i9 = mediaDeviceData.hashCode();
        }
        int i19 = (i18 + i9) * 31;
        boolean z3 = this.active;
        if (z3) {
            z3 = true;
        }
        int i20 = (i19 + (z3 ? 1 : 0)) * 31;
        Runnable runnable = this.resumeAction;
        if (runnable == null) {
            i10 = 0;
        } else {
            i10 = runnable.hashCode();
        }
        int m2 = FontInterpolator$VarFontKey$$ExternalSyntheticOutline0.m24m(this.playbackLocation, (i20 + i10) * 31, 31);
        boolean z4 = this.resumption;
        if (z4) {
            z4 = true;
        }
        int i21 = (m2 + (z4 ? 1 : 0)) * 31;
        String str2 = this.notificationKey;
        if (str2 == null) {
            i11 = 0;
        } else {
            i11 = str2.hashCode();
        }
        int i22 = (i21 + i11) * 31;
        boolean z5 = this.hasCheckedForResume;
        if (z5) {
            z5 = true;
        }
        int i23 = (i22 + (z5 ? 1 : 0)) * 31;
        Boolean bool = this.isPlaying;
        if (bool != null) {
            i12 = bool.hashCode();
        }
        int i24 = (i23 + i12) * 31;
        boolean z6 = this.isClearable;
        if (!z6) {
            z2 = z6;
        }
        return Long.hashCode(this.lastActive) + ((i24 + (z2 ? 1 : 0)) * 31);
    }

    public final String toString() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("MediaData(userId=");
        m.append(this.userId);
        m.append(", initialized=");
        m.append(this.initialized);
        m.append(", backgroundColor=");
        m.append(this.backgroundColor);
        m.append(", app=");
        m.append(this.app);
        m.append(", appIcon=");
        m.append(this.appIcon);
        m.append(", artist=");
        m.append(this.artist);
        m.append(", song=");
        m.append(this.song);
        m.append(", artwork=");
        m.append(this.artwork);
        m.append(", actions=");
        m.append(this.actions);
        m.append(", actionsToShowInCompact=");
        m.append(this.actionsToShowInCompact);
        m.append(", semanticActions=");
        m.append(this.semanticActions);
        m.append(", packageName=");
        m.append(this.packageName);
        m.append(", token=");
        m.append(this.token);
        m.append(", clickIntent=");
        m.append(this.clickIntent);
        m.append(", device=");
        m.append(this.device);
        m.append(", active=");
        m.append(this.active);
        m.append(", resumeAction=");
        m.append(this.resumeAction);
        m.append(", playbackLocation=");
        m.append(this.playbackLocation);
        m.append(", resumption=");
        m.append(this.resumption);
        m.append(", notificationKey=");
        m.append(this.notificationKey);
        m.append(", hasCheckedForResume=");
        m.append(this.hasCheckedForResume);
        m.append(", isPlaying=");
        m.append(this.isPlaying);
        m.append(", isClearable=");
        m.append(this.isClearable);
        m.append(", lastActive=");
        m.append(this.lastActive);
        m.append(')');
        return m.toString();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public /* synthetic */ MediaData(int r30, boolean r31, int r32, java.lang.String r33, android.graphics.drawable.Icon r34, java.lang.CharSequence r35, java.lang.CharSequence r36, android.graphics.drawable.Icon r37, java.util.List r38, java.util.List r39, com.android.systemui.media.MediaButton r40, java.lang.String r41, android.media.session.MediaSession.Token r42, android.app.PendingIntent r43, com.android.systemui.media.MediaDeviceData r44, boolean r45, java.lang.Runnable r46, int r47, boolean r48, java.lang.String r49, boolean r50, java.lang.Boolean r51, boolean r52, long r53, int r55) {
        /*
            r29 = this;
            r0 = r55
            r1 = r0 & 2
            r2 = 0
            if (r1 == 0) goto L_0x0009
            r5 = r2
            goto L_0x000b
        L_0x0009:
            r5 = r31
        L_0x000b:
            r1 = r0 & 1024(0x400, float:1.435E-42)
            r3 = 0
            if (r1 == 0) goto L_0x0012
            r14 = r3
            goto L_0x0014
        L_0x0012:
            r14 = r40
        L_0x0014:
            r1 = 131072(0x20000, float:1.83671E-40)
            r1 = r1 & r0
            if (r1 == 0) goto L_0x001c
            r21 = r2
            goto L_0x001e
        L_0x001c:
            r21 = r47
        L_0x001e:
            r1 = 262144(0x40000, float:3.67342E-40)
            r1 = r1 & r0
            if (r1 == 0) goto L_0x0026
            r22 = r2
            goto L_0x0028
        L_0x0026:
            r22 = r48
        L_0x0028:
            r1 = 524288(0x80000, float:7.34684E-40)
            r1 = r1 & r0
            if (r1 == 0) goto L_0x0030
            r23 = r3
            goto L_0x0032
        L_0x0030:
            r23 = r49
        L_0x0032:
            r1 = 1048576(0x100000, float:1.469368E-39)
            r1 = r1 & r0
            if (r1 == 0) goto L_0x003a
            r24 = r2
            goto L_0x003c
        L_0x003a:
            r24 = r50
        L_0x003c:
            r1 = 2097152(0x200000, float:2.938736E-39)
            r1 = r1 & r0
            if (r1 == 0) goto L_0x0044
            r25 = r3
            goto L_0x0046
        L_0x0044:
            r25 = r51
        L_0x0046:
            r1 = 4194304(0x400000, float:5.877472E-39)
            r1 = r1 & r0
            if (r1 == 0) goto L_0x004f
            r1 = 1
            r26 = r1
            goto L_0x0051
        L_0x004f:
            r26 = r52
        L_0x0051:
            r1 = 8388608(0x800000, float:1.17549435E-38)
            r0 = r0 & r1
            if (r0 == 0) goto L_0x005b
            r0 = 0
            r27 = r0
            goto L_0x005d
        L_0x005b:
            r27 = r53
        L_0x005d:
            r3 = r29
            r4 = r30
            r6 = r32
            r7 = r33
            r8 = r34
            r9 = r35
            r10 = r36
            r11 = r37
            r12 = r38
            r13 = r39
            r15 = r41
            r16 = r42
            r17 = r43
            r18 = r44
            r19 = r45
            r20 = r46
            r3.<init>(r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23, r24, r25, r26, r27)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaData.<init>(int, boolean, int, java.lang.String, android.graphics.drawable.Icon, java.lang.CharSequence, java.lang.CharSequence, android.graphics.drawable.Icon, java.util.List, java.util.List, com.android.systemui.media.MediaButton, java.lang.String, android.media.session.MediaSession$Token, android.app.PendingIntent, com.android.systemui.media.MediaDeviceData, boolean, java.lang.Runnable, int, boolean, java.lang.String, boolean, java.lang.Boolean, boolean, long, int):void");
    }
}
