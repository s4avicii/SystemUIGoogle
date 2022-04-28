package com.android.systemui.media;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import java.lang.Thread;
import java.util.LinkedList;
import java.util.Objects;

public final class NotificationPlayer implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener {
    @GuardedBy({"mQueueAudioFocusLock"})
    public AudioManager mAudioManagerWithAudioFocus;
    public final LinkedList<Command> mCmdQueue = new LinkedList<>();
    public final Object mCompletionHandlingLock = new Object();
    @GuardedBy({"mCompletionHandlingLock"})
    public CreationAndCompletionThread mCompletionThread;
    @GuardedBy({"mCompletionHandlingLock"})
    public Looper mLooper;
    public int mNotificationRampTimeMs = 0;
    @GuardedBy({"mPlayerLock"})
    public MediaPlayer mPlayer;
    public final Object mPlayerLock = new Object();
    public final Object mQueueAudioFocusLock = new Object();
    public int mState = 2;
    public String mTag = "RingtonePlayer";
    @GuardedBy({"mCmdQueue"})
    public CmdThread mThread;
    @GuardedBy({"mCmdQueue"})
    public PowerManager.WakeLock mWakeLock;

    public final class CmdThread extends Thread {
        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public CmdThread() {
            /*
                r1 = this;
                com.android.systemui.media.NotificationPlayer.this = r2
                java.lang.String r0 = "NotificationPlayer-"
                java.lang.StringBuilder r0 = android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1.m1m(r0)
                java.lang.String r2 = r2.mTag
                r0.append(r2)
                java.lang.String r2 = r0.toString()
                r1.<init>(r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.NotificationPlayer.CmdThread.<init>(com.android.systemui.media.NotificationPlayer):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:60:0x00c0, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r7 = this;
            L_0x0000:
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this
                java.util.LinkedList<com.android.systemui.media.NotificationPlayer$Command> r0 = r0.mCmdQueue
                monitor-enter(r0)
                com.android.systemui.media.NotificationPlayer r1 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x00c7 }
                java.util.LinkedList<com.android.systemui.media.NotificationPlayer$Command> r1 = r1.mCmdQueue     // Catch:{ all -> 0x00c7 }
                java.lang.Object r1 = r1.removeFirst()     // Catch:{ all -> 0x00c7 }
                com.android.systemui.media.NotificationPlayer$Command r1 = (com.android.systemui.media.NotificationPlayer.Command) r1     // Catch:{ all -> 0x00c7 }
                monitor-exit(r0)     // Catch:{ all -> 0x00c7 }
                int r0 = r1.code
                r2 = 1
                r3 = 0
                if (r0 == r2) goto L_0x009d
                r2 = 2
                if (r0 == r2) goto L_0x001b
                goto L_0x00a2
            L_0x001b:
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this
                java.lang.Object r0 = r0.mPlayerLock
                monitor-enter(r0)
                com.android.systemui.media.NotificationPlayer r2 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x009a }
                android.media.MediaPlayer r4 = r2.mPlayer     // Catch:{ all -> 0x009a }
                r2.mPlayer = r3     // Catch:{ all -> 0x009a }
                monitor-exit(r0)     // Catch:{ all -> 0x009a }
                if (r4 == 0) goto L_0x0092
                long r5 = android.os.SystemClock.uptimeMillis()
                long r0 = r1.requestTime
                long r5 = r5 - r0
                r0 = 1000(0x3e8, double:4.94E-321)
                int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
                if (r0 <= 0) goto L_0x0053
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this
                java.lang.String r0 = r0.mTag
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "Notification stop delayed by "
                r1.append(r2)
                r1.append(r5)
                java.lang.String r2 = "msecs"
                r1.append(r2)
                java.lang.String r1 = r1.toString()
                android.util.Log.w(r0, r1)
            L_0x0053:
                r4.stop()     // Catch:{ Exception -> 0x0056 }
            L_0x0056:
                r4.release()
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this
                java.lang.Object r0 = r0.mQueueAudioFocusLock
                monitor-enter(r0)
                com.android.systemui.media.NotificationPlayer r1 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x008f }
                android.media.AudioManager r1 = r1.mAudioManagerWithAudioFocus     // Catch:{ all -> 0x008f }
                if (r1 == 0) goto L_0x006b
                r1.abandonAudioFocus(r3)     // Catch:{ all -> 0x008f }
                com.android.systemui.media.NotificationPlayer r1 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x008f }
                r1.mAudioManagerWithAudioFocus = r3     // Catch:{ all -> 0x008f }
            L_0x006b:
                monitor-exit(r0)     // Catch:{ all -> 0x008f }
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this
                java.lang.Object r1 = r0.mCompletionHandlingLock
                monitor-enter(r1)
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x008c }
                android.os.Looper r0 = r0.mLooper     // Catch:{ all -> 0x008c }
                if (r0 == 0) goto L_0x008a
                java.lang.Thread r0 = r0.getThread()     // Catch:{ all -> 0x008c }
                java.lang.Thread$State r0 = r0.getState()     // Catch:{ all -> 0x008c }
                java.lang.Thread$State r2 = java.lang.Thread.State.TERMINATED     // Catch:{ all -> 0x008c }
                if (r0 == r2) goto L_0x008a
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x008c }
                android.os.Looper r0 = r0.mLooper     // Catch:{ all -> 0x008c }
                r0.quit()     // Catch:{ all -> 0x008c }
            L_0x008a:
                monitor-exit(r1)     // Catch:{ all -> 0x008c }
                goto L_0x00a2
            L_0x008c:
                r7 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x008c }
                throw r7
            L_0x008f:
                r7 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x008f }
                throw r7
            L_0x0092:
                java.lang.String r0 = r2.mTag
                java.lang.String r1 = "STOP command without a player"
                android.util.Log.w(r0, r1)
                goto L_0x00a2
            L_0x009a:
                r7 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x009a }
                throw r7
            L_0x009d:
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this
                com.android.systemui.media.NotificationPlayer.m209$$Nest$mstartSound(r0, r1)
            L_0x00a2:
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this
                java.util.LinkedList<com.android.systemui.media.NotificationPlayer$Command> r1 = r0.mCmdQueue
                monitor-enter(r1)
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x00c4 }
                java.util.LinkedList<com.android.systemui.media.NotificationPlayer$Command> r0 = r0.mCmdQueue     // Catch:{ all -> 0x00c4 }
                int r0 = r0.size()     // Catch:{ all -> 0x00c4 }
                if (r0 != 0) goto L_0x00c1
                com.android.systemui.media.NotificationPlayer r7 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x00c4 }
                r7.mThread = r3     // Catch:{ all -> 0x00c4 }
                java.util.Objects.requireNonNull(r7)     // Catch:{ all -> 0x00c4 }
                android.os.PowerManager$WakeLock r7 = r7.mWakeLock     // Catch:{ all -> 0x00c4 }
                if (r7 == 0) goto L_0x00bf
                r7.release()     // Catch:{ all -> 0x00c4 }
            L_0x00bf:
                monitor-exit(r1)     // Catch:{ all -> 0x00c4 }
                return
            L_0x00c1:
                monitor-exit(r1)     // Catch:{ all -> 0x00c4 }
                goto L_0x0000
            L_0x00c4:
                r7 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x00c4 }
                throw r7
            L_0x00c7:
                r7 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x00c7 }
                throw r7
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.NotificationPlayer.CmdThread.run():void");
        }
    }

    public static final class Command {
        public AudioAttributes attributes;
        public int code;
        public Context context;
        public boolean looping;
        public long requestTime;
        public Uri uri;

        public Command() {
        }

        public Command(int i) {
        }

        public final String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("{ code=");
            m.append(this.code);
            m.append(" looping=");
            m.append(this.looping);
            m.append(" attributes=");
            m.append(this.attributes);
            m.append(" uri=");
            m.append(this.uri);
            m.append(" }");
            return m.toString();
        }
    }

    public final class CreationAndCompletionThread extends Thread {
        public Command mCmd;

        public CreationAndCompletionThread(Command command) {
            this.mCmd = command;
        }

        /*  JADX ERROR: IndexOutOfBoundsException in pass: RegionMakerVisitor
            java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0
            	at java.base/jdk.internal.util.Preconditions.outOfBounds(Preconditions.java:64)
            	at java.base/jdk.internal.util.Preconditions.outOfBoundsCheckIndex(Preconditions.java:70)
            	at java.base/jdk.internal.util.Preconditions.checkIndex(Preconditions.java:248)
            	at java.base/java.util.Objects.checkIndex(Objects.java:372)
            	at java.base/java.util.ArrayList.get(ArrayList.java:458)
            	at jadx.core.dex.nodes.InsnNode.getArg(InsnNode.java:101)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:611)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverseMonitorExits(RegionMaker.java:619)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:693)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
            	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
            	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
            	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
            */
        public final void run() {
            /*
                r8 = this;
                android.os.Looper.prepare()
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this
                android.os.Looper r1 = android.os.Looper.myLooper()
                r0.mLooper = r1
                monitor-enter(r8)
                com.android.systemui.media.NotificationPlayer$Command r0 = r8.mCmd     // Catch:{ all -> 0x011f }
                android.content.Context r0 = r0.context     // Catch:{ all -> 0x011f }
                java.lang.String r1 = "audio"
                java.lang.Object r0 = r0.getSystemService(r1)     // Catch:{ all -> 0x011f }
                android.media.AudioManager r0 = (android.media.AudioManager) r0     // Catch:{ all -> 0x011f }
                r1 = 0
                android.media.MediaPlayer r2 = new android.media.MediaPlayer     // Catch:{ Exception -> 0x00c2 }
                r2.<init>()     // Catch:{ Exception -> 0x00c2 }
                com.android.systemui.media.NotificationPlayer$Command r3 = r8.mCmd     // Catch:{ Exception -> 0x00c0 }
                android.media.AudioAttributes r4 = r3.attributes     // Catch:{ Exception -> 0x00c0 }
                if (r4 != 0) goto L_0x0039
                android.media.AudioAttributes$Builder r4 = new android.media.AudioAttributes$Builder     // Catch:{ Exception -> 0x00c0 }
                r4.<init>()     // Catch:{ Exception -> 0x00c0 }
                r5 = 5
                android.media.AudioAttributes$Builder r4 = r4.setUsage(r5)     // Catch:{ Exception -> 0x00c0 }
                r5 = 4
                android.media.AudioAttributes$Builder r4 = r4.setContentType(r5)     // Catch:{ Exception -> 0x00c0 }
                android.media.AudioAttributes r4 = r4.build()     // Catch:{ Exception -> 0x00c0 }
                r3.attributes = r4     // Catch:{ Exception -> 0x00c0 }
            L_0x0039:
                com.android.systemui.media.NotificationPlayer$Command r3 = r8.mCmd     // Catch:{ Exception -> 0x00c0 }
                android.media.AudioAttributes r3 = r3.attributes     // Catch:{ Exception -> 0x00c0 }
                r2.setAudioAttributes(r3)     // Catch:{ Exception -> 0x00c0 }
                com.android.systemui.media.NotificationPlayer$Command r3 = r8.mCmd     // Catch:{ Exception -> 0x00c0 }
                android.content.Context r4 = r3.context     // Catch:{ Exception -> 0x00c0 }
                android.net.Uri r3 = r3.uri     // Catch:{ Exception -> 0x00c0 }
                r2.setDataSource(r4, r3)     // Catch:{ Exception -> 0x00c0 }
                com.android.systemui.media.NotificationPlayer$Command r3 = r8.mCmd     // Catch:{ Exception -> 0x00c0 }
                boolean r3 = r3.looping     // Catch:{ Exception -> 0x00c0 }
                r2.setLooping(r3)     // Catch:{ Exception -> 0x00c0 }
                com.android.systemui.media.NotificationPlayer r3 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ Exception -> 0x00c0 }
                r2.setOnCompletionListener(r3)     // Catch:{ Exception -> 0x00c0 }
                com.android.systemui.media.NotificationPlayer r3 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ Exception -> 0x00c0 }
                r2.setOnErrorListener(r3)     // Catch:{ Exception -> 0x00c0 }
                r2.prepare()     // Catch:{ Exception -> 0x00c0 }
                com.android.systemui.media.NotificationPlayer$Command r3 = r8.mCmd     // Catch:{ Exception -> 0x00c0 }
                android.net.Uri r3 = r3.uri     // Catch:{ Exception -> 0x00c0 }
                if (r3 == 0) goto L_0x00a9
                java.lang.String r3 = r3.getEncodedPath()     // Catch:{ Exception -> 0x00c0 }
                if (r3 == 0) goto L_0x00a9
                com.android.systemui.media.NotificationPlayer$Command r3 = r8.mCmd     // Catch:{ Exception -> 0x00c0 }
                android.net.Uri r3 = r3.uri     // Catch:{ Exception -> 0x00c0 }
                java.lang.String r3 = r3.getEncodedPath()     // Catch:{ Exception -> 0x00c0 }
                int r3 = r3.length()     // Catch:{ Exception -> 0x00c0 }
                if (r3 <= 0) goto L_0x00a9
                boolean r3 = r0.isMusicActiveRemotely()     // Catch:{ Exception -> 0x00c0 }
                if (r3 != 0) goto L_0x00a9
                com.android.systemui.media.NotificationPlayer r3 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ Exception -> 0x00c0 }
                java.lang.Object r3 = r3.mQueueAudioFocusLock     // Catch:{ Exception -> 0x00c0 }
                monitor-enter(r3)     // Catch:{ Exception -> 0x00c0 }
                com.android.systemui.media.NotificationPlayer r4 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x00a6 }
                android.media.AudioManager r5 = r4.mAudioManagerWithAudioFocus     // Catch:{ all -> 0x00a6 }
                if (r5 != 0) goto L_0x00a4
                r5 = 3
                com.android.systemui.media.NotificationPlayer$Command r6 = r8.mCmd     // Catch:{ all -> 0x00a6 }
                boolean r7 = r6.looping     // Catch:{ all -> 0x00a6 }
                if (r7 == 0) goto L_0x0090
                r5 = 1
            L_0x0090:
                android.media.AudioAttributes r6 = r6.attributes     // Catch:{ all -> 0x00a6 }
                int r6 = r0.getFocusRampTimeMs(r5, r6)     // Catch:{ all -> 0x00a6 }
                r4.mNotificationRampTimeMs = r6     // Catch:{ all -> 0x00a6 }
                com.android.systemui.media.NotificationPlayer$Command r4 = r8.mCmd     // Catch:{ all -> 0x00a6 }
                android.media.AudioAttributes r4 = r4.attributes     // Catch:{ all -> 0x00a6 }
                r6 = 0
                r0.requestAudioFocus(r1, r4, r5, r6)     // Catch:{ all -> 0x00a6 }
                com.android.systemui.media.NotificationPlayer r4 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x00a6 }
                r4.mAudioManagerWithAudioFocus = r0     // Catch:{ all -> 0x00a6 }
            L_0x00a4:
                monitor-exit(r3)     // Catch:{ all -> 0x00a6 }
                goto L_0x00a9
            L_0x00a6:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x00a6 }
                throw r0     // Catch:{ Exception -> 0x00c0 }
            L_0x00a9:
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ InterruptedException -> 0x00b2 }
                int r0 = r0.mNotificationRampTimeMs     // Catch:{ InterruptedException -> 0x00b2 }
                long r3 = (long) r0     // Catch:{ InterruptedException -> 0x00b2 }
                java.lang.Thread.sleep(r3)     // Catch:{ InterruptedException -> 0x00b2 }
                goto L_0x00bc
            L_0x00b2:
                r0 = move-exception
                com.android.systemui.media.NotificationPlayer r3 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ Exception -> 0x00c0 }
                java.lang.String r3 = r3.mTag     // Catch:{ Exception -> 0x00c0 }
                java.lang.String r4 = "Exception while sleeping to sync notification playback with ducking"
                android.util.Log.e(r3, r4, r0)     // Catch:{ Exception -> 0x00c0 }
            L_0x00bc:
                r2.start()     // Catch:{ Exception -> 0x00c0 }
                goto L_0x00f8
            L_0x00c0:
                r0 = move-exception
                goto L_0x00c4
            L_0x00c2:
                r0 = move-exception
                r2 = r1
            L_0x00c4:
                if (r2 == 0) goto L_0x00ca
                r2.release()     // Catch:{ all -> 0x011f }
                r2 = r1
            L_0x00ca:
                com.android.systemui.media.NotificationPlayer r3 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x011f }
                java.lang.String r3 = r3.mTag     // Catch:{ all -> 0x011f }
                java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch:{ all -> 0x011f }
                r4.<init>()     // Catch:{ all -> 0x011f }
                java.lang.String r5 = "error loading sound for "
                r4.append(r5)     // Catch:{ all -> 0x011f }
                com.android.systemui.media.NotificationPlayer$Command r5 = r8.mCmd     // Catch:{ all -> 0x011f }
                android.net.Uri r5 = r5.uri     // Catch:{ all -> 0x011f }
                r4.append(r5)     // Catch:{ all -> 0x011f }
                java.lang.String r4 = r4.toString()     // Catch:{ all -> 0x011f }
                android.util.Log.w(r3, r4, r0)     // Catch:{ all -> 0x011f }
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x011f }
                java.util.Objects.requireNonNull(r0)     // Catch:{ all -> 0x011f }
                java.lang.Object r3 = r0.mQueueAudioFocusLock     // Catch:{ all -> 0x011f }
                monitor-enter(r3)     // Catch:{ all -> 0x011f }
                android.media.AudioManager r4 = r0.mAudioManagerWithAudioFocus     // Catch:{ all -> 0x011c }
                if (r4 == 0) goto L_0x00f7
                r4.abandonAudioFocus(r1)     // Catch:{ all -> 0x011c }
                r0.mAudioManagerWithAudioFocus = r1     // Catch:{ all -> 0x011c }
            L_0x00f7:
                monitor-exit(r3)     // Catch:{ all -> 0x011c }
            L_0x00f8:
                com.android.systemui.media.NotificationPlayer r0 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x011f }
                java.lang.Object r0 = r0.mPlayerLock     // Catch:{ all -> 0x011f }
                monitor-enter(r0)     // Catch:{ all -> 0x011f }
                com.android.systemui.media.NotificationPlayer r1 = com.android.systemui.media.NotificationPlayer.this     // Catch:{ all -> 0x0119 }
                android.media.MediaPlayer r3 = r1.mPlayer     // Catch:{ all -> 0x0119 }
                r1.mPlayer = r2     // Catch:{ all -> 0x0119 }
                monitor-exit(r0)     // Catch:{ all -> 0x0119 }
                if (r3 == 0) goto L_0x0111
                r3.pause()     // Catch:{ all -> 0x011f }
                r0 = 100
                java.lang.Thread.sleep(r0)     // Catch:{ InterruptedException -> 0x010e }
            L_0x010e:
                r3.release()     // Catch:{ all -> 0x011f }
            L_0x0111:
                r8.notify()     // Catch:{ all -> 0x011f }
                monitor-exit(r8)     // Catch:{ all -> 0x011f }
                android.os.Looper.loop()
                return
            L_0x0119:
                r1 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x0119 }
                throw r1     // Catch:{ all -> 0x011f }
            L_0x011c:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x011c }
                throw r0     // Catch:{ all -> 0x011f }
            L_0x011f:
                r0 = move-exception
                monitor-exit(r8)     // Catch:{ all -> 0x011f }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.NotificationPlayer.CreationAndCompletionThread.run():void");
        }
    }

    public final void onCompletion(MediaPlayer mediaPlayer) {
        synchronized (this.mQueueAudioFocusLock) {
            AudioManager audioManager = this.mAudioManagerWithAudioFocus;
            if (audioManager != null) {
                audioManager.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener) null);
                this.mAudioManagerWithAudioFocus = null;
            }
        }
        synchronized (this.mCmdQueue) {
            synchronized (this.mCompletionHandlingLock) {
                if (this.mCmdQueue.size() == 0) {
                    Looper looper = this.mLooper;
                    if (looper != null) {
                        looper.quit();
                    }
                    this.mCompletionThread = null;
                }
            }
        }
        synchronized (this.mPlayerLock) {
            if (mediaPlayer == this.mPlayer) {
                this.mPlayer = null;
            }
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }

    public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        String str = this.mTag;
        Log.e(str, "error " + i + " (extra=" + i2 + ") playing notification");
        onCompletion(mediaPlayer);
        return true;
    }

    /* renamed from: -$$Nest$mstartSound  reason: not valid java name */
    public static void m209$$Nest$mstartSound(NotificationPlayer notificationPlayer, Command command) {
        Objects.requireNonNull(notificationPlayer);
        try {
            synchronized (notificationPlayer.mCompletionHandlingLock) {
                Looper looper = notificationPlayer.mLooper;
                if (!(looper == null || looper.getThread().getState() == Thread.State.TERMINATED)) {
                    notificationPlayer.mLooper.quit();
                }
                CreationAndCompletionThread creationAndCompletionThread = new CreationAndCompletionThread(command);
                notificationPlayer.mCompletionThread = creationAndCompletionThread;
                synchronized (creationAndCompletionThread) {
                    notificationPlayer.mCompletionThread.start();
                    notificationPlayer.mCompletionThread.wait();
                }
            }
            long uptimeMillis = SystemClock.uptimeMillis() - command.requestTime;
            if (uptimeMillis > 1000) {
                String str = notificationPlayer.mTag;
                Log.w(str, "Notification sound delayed by " + uptimeMillis + "msecs");
            }
        } catch (Exception e) {
            String str2 = notificationPlayer.mTag;
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("error loading sound for ");
            m.append(command.uri);
            Log.w(str2, m.toString(), e);
        }
    }
}
