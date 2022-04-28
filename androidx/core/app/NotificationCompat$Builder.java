package androidx.core.app;

import android.app.Notification;
import android.content.Context;
import android.os.Bundle;
import java.util.ArrayList;

public final class NotificationCompat$Builder {
    public ArrayList<NotificationCompat$Action> mActions = new ArrayList<>();
    public boolean mAllowSystemGeneratedContextualActions;
    public String mChannelId;
    public CharSequence mContentText;
    public CharSequence mContentTitle;
    public Context mContext;
    public Bundle mExtras;
    public ArrayList<NotificationCompat$Action> mInvisibleActions = new ArrayList<>();
    public Notification mNotification;
    @Deprecated
    public ArrayList<String> mPeople;
    public ArrayList<Person> mPersonList = new ArrayList<>();
    public boolean mShowWhen = true;

    public static CharSequence limitCharSequenceLength(String str) {
        if (str != null && str.length() > 5120) {
            return str.subSequence(0, 5120);
        }
        return str;
    }

    /* JADX WARNING: type inference failed for: r6v1 */
    /* JADX WARNING: type inference failed for: r6v2, types: [java.lang.CharSequence[], java.lang.CharSequence, long[], android.net.Uri, java.lang.String] */
    /* JADX WARNING: type inference failed for: r6v3 */
    /* JADX WARNING: type inference failed for: r6v7 */
    /* JADX WARNING: type inference failed for: r6v9 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final android.app.Notification build() {
        /*
            r16 = this;
            r0 = r16
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            android.os.Bundle r1 = new android.os.Bundle
            r1.<init>()
            android.app.Notification$Builder r2 = new android.app.Notification$Builder
            android.content.Context r3 = r0.mContext
            java.lang.String r4 = r0.mChannelId
            r2.<init>(r3, r4)
            android.app.Notification r3 = r0.mNotification
            long r4 = r3.when
            android.app.Notification$Builder r4 = r2.setWhen(r4)
            int r5 = r3.icon
            int r6 = r3.iconLevel
            android.app.Notification$Builder r4 = r4.setSmallIcon(r5, r6)
            android.widget.RemoteViews r5 = r3.contentView
            android.app.Notification$Builder r4 = r4.setContent(r5)
            java.lang.CharSequence r5 = r3.tickerText
            r6 = 0
            android.app.Notification$Builder r4 = r4.setTicker(r5, r6)
            long[] r5 = r3.vibrate
            android.app.Notification$Builder r4 = r4.setVibrate(r5)
            int r5 = r3.ledARGB
            int r7 = r3.ledOnMS
            int r8 = r3.ledOffMS
            android.app.Notification$Builder r4 = r4.setLights(r5, r7, r8)
            int r5 = r3.flags
            r5 = r5 & 2
            r7 = 0
            r8 = 1
            if (r5 == 0) goto L_0x004c
            r5 = r8
            goto L_0x004d
        L_0x004c:
            r5 = r7
        L_0x004d:
            android.app.Notification$Builder r4 = r4.setOngoing(r5)
            int r5 = r3.flags
            r5 = r5 & 8
            if (r5 == 0) goto L_0x0059
            r5 = r8
            goto L_0x005a
        L_0x0059:
            r5 = r7
        L_0x005a:
            android.app.Notification$Builder r4 = r4.setOnlyAlertOnce(r5)
            int r5 = r3.flags
            r5 = r5 & 16
            if (r5 == 0) goto L_0x0066
            r5 = r8
            goto L_0x0067
        L_0x0066:
            r5 = r7
        L_0x0067:
            android.app.Notification$Builder r4 = r4.setAutoCancel(r5)
            int r5 = r3.defaults
            android.app.Notification$Builder r4 = r4.setDefaults(r5)
            java.lang.CharSequence r5 = r0.mContentTitle
            android.app.Notification$Builder r4 = r4.setContentTitle(r5)
            java.lang.CharSequence r5 = r0.mContentText
            android.app.Notification$Builder r4 = r4.setContentText(r5)
            android.app.Notification$Builder r4 = r4.setContentInfo(r6)
            android.app.Notification$Builder r4 = r4.setContentIntent(r6)
            android.app.PendingIntent r5 = r3.deleteIntent
            android.app.Notification$Builder r4 = r4.setDeleteIntent(r5)
            int r5 = r3.flags
            r5 = r5 & 128(0x80, float:1.794E-43)
            if (r5 == 0) goto L_0x0092
            goto L_0x0093
        L_0x0092:
            r8 = r7
        L_0x0093:
            android.app.Notification$Builder r4 = r4.setFullScreenIntent(r6, r8)
            android.app.Notification$Builder r4 = r4.setLargeIcon(r6)
            android.app.Notification$Builder r4 = r4.setNumber(r7)
            r4.setProgress(r7, r7, r7)
            android.app.Notification$Builder r4 = r2.setSubText(r6)
            android.app.Notification$Builder r4 = r4.setUsesChronometer(r7)
            r4.setPriority(r7)
            java.util.ArrayList<androidx.core.app.NotificationCompat$Action> r4 = r0.mActions
            java.util.Iterator r4 = r4.iterator()
        L_0x00b3:
            boolean r5 = r4.hasNext()
            java.lang.String r8 = "android.support.allowGeneratedReplies"
            if (r5 == 0) goto L_0x012e
            java.lang.Object r5 = r4.next()
            androidx.core.app.NotificationCompat$Action r5 = (androidx.core.app.NotificationCompat$Action) r5
            androidx.core.graphics.drawable.IconCompat r9 = r5.getIconCompat()
            android.app.Notification$Action$Builder r10 = new android.app.Notification$Action$Builder
            if (r9 == 0) goto L_0x00ce
            android.graphics.drawable.Icon r9 = r9.toIcon$1()
            goto L_0x00cf
        L_0x00ce:
            r9 = r6
        L_0x00cf:
            java.lang.CharSequence r11 = r5.title
            android.app.PendingIntent r12 = r5.actionIntent
            r10.<init>(r9, r11, r12)
            androidx.core.app.RemoteInput[] r9 = r5.mRemoteInputs
            if (r9 == 0) goto L_0x00f0
            int r11 = r9.length
            android.app.RemoteInput[] r12 = new android.app.RemoteInput[r11]
            int r13 = r9.length
            if (r13 > 0) goto L_0x00eb
            r9 = r7
        L_0x00e1:
            if (r9 >= r11) goto L_0x00f0
            r13 = r12[r9]
            r10.addRemoteInput(r13)
            int r9 = r9 + 1
            goto L_0x00e1
        L_0x00eb:
            r0 = r9[r7]
            android.app.RemoteInput$Builder r0 = new android.app.RemoteInput$Builder
            throw r6
        L_0x00f0:
            android.os.Bundle r9 = r5.mExtras
            if (r9 == 0) goto L_0x00fc
            android.os.Bundle r9 = new android.os.Bundle
            android.os.Bundle r11 = r5.mExtras
            r9.<init>(r11)
            goto L_0x0101
        L_0x00fc:
            android.os.Bundle r9 = new android.os.Bundle
            r9.<init>()
        L_0x0101:
            boolean r11 = r5.mAllowGeneratedReplies
            r9.putBoolean(r8, r11)
            boolean r8 = r5.mAllowGeneratedReplies
            r10.setAllowGeneratedReplies(r8)
            int r8 = r5.mSemanticAction
            java.lang.String r11 = "android.support.action.semanticAction"
            r9.putInt(r11, r8)
            int r8 = r5.mSemanticAction
            r10.setSemanticAction(r8)
            boolean r8 = r5.mIsContextual
            r10.setContextual(r8)
            boolean r5 = r5.mShowsUserInterface
            java.lang.String r8 = "android.support.action.showsUserInterface"
            r9.putBoolean(r8, r5)
            r10.addExtras(r9)
            android.app.Notification$Action r5 = r10.build()
            r2.addAction(r5)
            goto L_0x00b3
        L_0x012e:
            android.os.Bundle r4 = r0.mExtras
            if (r4 == 0) goto L_0x0135
            r1.putAll(r4)
        L_0x0135:
            boolean r4 = r0.mShowWhen
            r2.setShowWhen(r4)
            android.app.Notification$Builder r4 = r2.setLocalOnly(r7)
            android.app.Notification$Builder r4 = r4.setGroup(r6)
            android.app.Notification$Builder r4 = r4.setGroupSummary(r7)
            r4.setSortKey(r6)
            android.app.Notification$Builder r4 = r2.setCategory(r6)
            android.app.Notification$Builder r4 = r4.setColor(r7)
            android.app.Notification$Builder r4 = r4.setVisibility(r7)
            android.app.Notification$Builder r4 = r4.setPublicVersion(r6)
            android.net.Uri r5 = r3.sound
            android.media.AudioAttributes r3 = r3.audioAttributes
            r4.setSound(r5, r3)
            java.util.ArrayList<java.lang.String> r3 = r0.mPeople
            if (r3 == 0) goto L_0x017e
            boolean r4 = r3.isEmpty()
            if (r4 != 0) goto L_0x017e
            java.util.Iterator r3 = r3.iterator()
        L_0x016e:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L_0x017e
            java.lang.Object r4 = r3.next()
            java.lang.String r4 = (java.lang.String) r4
            r2.addPerson(r4)
            goto L_0x016e
        L_0x017e:
            java.util.ArrayList<androidx.core.app.NotificationCompat$Action> r3 = r0.mInvisibleActions
            int r3 = r3.size()
            if (r3 <= 0) goto L_0x023c
            android.os.Bundle r3 = r16.getExtras()
            java.lang.String r4 = "android.car.EXTENSIONS"
            android.os.Bundle r3 = r3.getBundle(r4)
            if (r3 != 0) goto L_0x0197
            android.os.Bundle r3 = new android.os.Bundle
            r3.<init>()
        L_0x0197:
            android.os.Bundle r5 = new android.os.Bundle
            r5.<init>(r3)
            android.os.Bundle r9 = new android.os.Bundle
            r9.<init>()
            r10 = r7
        L_0x01a2:
            java.util.ArrayList<androidx.core.app.NotificationCompat$Action> r11 = r0.mInvisibleActions
            int r11 = r11.size()
            if (r10 >= r11) goto L_0x022a
            java.lang.String r11 = java.lang.Integer.toString(r10)
            java.util.ArrayList<androidx.core.app.NotificationCompat$Action> r12 = r0.mInvisibleActions
            java.lang.Object r12 = r12.get(r10)
            androidx.core.app.NotificationCompat$Action r12 = (androidx.core.app.NotificationCompat$Action) r12
            java.lang.Object r13 = androidx.core.app.NotificationCompatJellybean.sExtrasLock
            android.os.Bundle r13 = new android.os.Bundle
            r13.<init>()
            androidx.core.graphics.drawable.IconCompat r14 = r12.getIconCompat()
            if (r14 == 0) goto L_0x01c8
            int r14 = r14.getResId()
            goto L_0x01c9
        L_0x01c8:
            r14 = r7
        L_0x01c9:
            java.lang.String r15 = "icon"
            r13.putInt(r15, r14)
            java.lang.CharSequence r14 = r12.title
            java.lang.String r15 = "title"
            r13.putCharSequence(r15, r14)
            android.app.PendingIntent r14 = r12.actionIntent
            java.lang.String r15 = "actionIntent"
            r13.putParcelable(r15, r14)
            android.os.Bundle r14 = r12.mExtras
            if (r14 == 0) goto L_0x01e9
            android.os.Bundle r14 = new android.os.Bundle
            android.os.Bundle r15 = r12.mExtras
            r14.<init>(r15)
            goto L_0x01ee
        L_0x01e9:
            android.os.Bundle r14 = new android.os.Bundle
            r14.<init>()
        L_0x01ee:
            boolean r15 = r12.mAllowGeneratedReplies
            r14.putBoolean(r8, r15)
            java.lang.String r15 = "extras"
            r13.putBundle(r15, r14)
            androidx.core.app.RemoteInput[] r14 = r12.mRemoteInputs
            if (r14 != 0) goto L_0x01fe
            r15 = r6
            goto L_0x0204
        L_0x01fe:
            int r15 = r14.length
            android.os.Bundle[] r15 = new android.os.Bundle[r15]
            int r6 = r14.length
            if (r6 > 0) goto L_0x0221
        L_0x0204:
            java.lang.String r6 = "remoteInputs"
            r13.putParcelableArray(r6, r15)
            boolean r6 = r12.mShowsUserInterface
            java.lang.String r14 = "showsUserInterface"
            r13.putBoolean(r14, r6)
            int r6 = r12.mSemanticAction
            java.lang.String r12 = "semanticAction"
            r13.putInt(r12, r6)
            r9.putBundle(r11, r13)
            int r10 = r10 + 1
            r6 = 0
            goto L_0x01a2
        L_0x0221:
            r0 = r14[r7]
            android.os.Bundle r0 = new android.os.Bundle
            r0.<init>()
            r6 = 0
            throw r6
        L_0x022a:
            java.lang.String r8 = "invisible_actions"
            r3.putBundle(r8, r9)
            r5.putBundle(r8, r9)
            android.os.Bundle r8 = r16.getExtras()
            r8.putBundle(r4, r3)
            r1.putBundle(r4, r5)
        L_0x023c:
            android.os.Bundle r1 = r0.mExtras
            android.app.Notification$Builder r1 = r2.setExtras(r1)
            r1.setRemoteInputHistory(r6)
            android.app.Notification$Builder r1 = r2.setBadgeIconType(r7)
            android.app.Notification$Builder r1 = r1.setSettingsText(r6)
            android.app.Notification$Builder r1 = r1.setShortcutId(r6)
            r3 = 0
            android.app.Notification$Builder r1 = r1.setTimeoutAfter(r3)
            r1.setGroupAlertBehavior(r7)
            java.lang.String r1 = r0.mChannelId
            boolean r1 = android.text.TextUtils.isEmpty(r1)
            if (r1 != 0) goto L_0x0271
            android.app.Notification$Builder r1 = r2.setSound(r6)
            android.app.Notification$Builder r1 = r1.setDefaults(r7)
            android.app.Notification$Builder r1 = r1.setLights(r7, r7, r7)
            r1.setVibrate(r6)
        L_0x0271:
            java.util.ArrayList<androidx.core.app.Person> r1 = r0.mPersonList
            java.util.Iterator r1 = r1.iterator()
        L_0x0277:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x02ac
            java.lang.Object r3 = r1.next()
            androidx.core.app.Person r3 = (androidx.core.app.Person) r3
            java.util.Objects.requireNonNull(r3)
            android.app.Person$Builder r3 = new android.app.Person$Builder
            r3.<init>()
            r4 = 0
            android.app.Person$Builder r3 = r3.setName(r4)
            android.app.Person$Builder r3 = r3.setIcon(r4)
            android.app.Person$Builder r3 = r3.setUri(r4)
            android.app.Person$Builder r3 = r3.setKey(r4)
            android.app.Person$Builder r3 = r3.setBot(r7)
            android.app.Person$Builder r3 = r3.setImportant(r7)
            android.app.Person r3 = r3.build()
            r2.addPerson(r3)
            goto L_0x0277
        L_0x02ac:
            r4 = 0
            boolean r0 = r0.mAllowSystemGeneratedContextualActions
            r2.setAllowSystemGeneratedContextualActions(r0)
            r2.setBubbleMetadata(r4)
            android.app.Notification r0 = r2.build()
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.app.NotificationCompat$Builder.build():android.app.Notification");
    }

    public final Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        return this.mExtras;
    }

    public NotificationCompat$Builder(Context context) {
        Notification notification = new Notification();
        this.mNotification = notification;
        this.mContext = context;
        this.mChannelId = "BAT";
        notification.when = System.currentTimeMillis();
        this.mNotification.audioStreamType = -1;
        this.mPeople = new ArrayList<>();
        this.mAllowSystemGeneratedContextualActions = true;
    }
}
