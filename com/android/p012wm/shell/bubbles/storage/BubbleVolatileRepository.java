package com.android.p012wm.shell.bubbles.storage;

import android.content.pm.LauncherApps;
import android.os.UserHandle;
import android.util.SparseArray;
import com.android.internal.annotations.VisibleForTesting;
import com.android.p012wm.shell.bubbles.ShortcutKey;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;

/* renamed from: com.android.wm.shell.bubbles.storage.BubbleVolatileRepository */
/* compiled from: BubbleVolatileRepository.kt */
public final class BubbleVolatileRepository {
    public int capacity = 16;
    public SparseArray<List<BubbleEntity>> entitiesByUser = new SparseArray<>();
    public final LauncherApps launcherApps;

    @VisibleForTesting
    public static /* synthetic */ void getCapacity$annotations() {
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
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:561)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processIf(RegionMaker.java:698)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:123)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMaker.processMonitorEnter(RegionMaker.java:598)
        	at jadx.core.dex.visitors.regions.RegionMaker.traverse(RegionMaker.java:133)
        	at jadx.core.dex.visitors.regions.RegionMaker.makeRegion(RegionMaker.java:86)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:49)
        */
    public final synchronized void addBubbles(int r7, java.util.List<com.android.p012wm.shell.bubbles.storage.BubbleEntity> r8) {
        /*
            r6 = this;
            monitor-enter(r6)
            boolean r0 = r8.isEmpty()     // Catch:{ all -> 0x0079 }
            if (r0 == 0) goto L_0x0009
            monitor-exit(r6)
            return
        L_0x0009:
            monitor-enter(r6)     // Catch:{ all -> 0x0079 }
            android.util.SparseArray<java.util.List<com.android.wm.shell.bubbles.storage.BubbleEntity>> r0 = r6.entitiesByUser     // Catch:{ all -> 0x0076 }
            java.lang.Object r0 = r0.get(r7)     // Catch:{ all -> 0x0076 }
            java.util.List r0 = (java.util.List) r0     // Catch:{ all -> 0x0076 }
            if (r0 != 0) goto L_0x001e
            java.util.ArrayList r0 = new java.util.ArrayList     // Catch:{ all -> 0x0076 }
            r0.<init>()     // Catch:{ all -> 0x0076 }
            android.util.SparseArray<java.util.List<com.android.wm.shell.bubbles.storage.BubbleEntity>> r1 = r6.entitiesByUser     // Catch:{ all -> 0x0076 }
            r1.put(r7, r0)     // Catch:{ all -> 0x0076 }
        L_0x001e:
            monitor-exit(r6)     // Catch:{ all -> 0x0079 }
            int r1 = r6.capacity     // Catch:{ all -> 0x0079 }
            java.util.List r8 = kotlin.collections.CollectionsKt___CollectionsKt.takeLast(r8, r1)     // Catch:{ all -> 0x0079 }
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch:{ all -> 0x0079 }
            r1.<init>()     // Catch:{ all -> 0x0079 }
            java.util.Iterator r2 = r8.iterator()     // Catch:{ all -> 0x0079 }
        L_0x002e:
            boolean r3 = r2.hasNext()     // Catch:{ all -> 0x0079 }
            if (r3 == 0) goto L_0x004a
            java.lang.Object r3 = r2.next()     // Catch:{ all -> 0x0079 }
            r4 = r3
            com.android.wm.shell.bubbles.storage.BubbleEntity r4 = (com.android.p012wm.shell.bubbles.storage.BubbleEntity) r4     // Catch:{ all -> 0x0079 }
            com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$addBubbles$uniqueBubbles$1$1 r5 = new com.android.wm.shell.bubbles.storage.BubbleVolatileRepository$addBubbles$uniqueBubbles$1$1     // Catch:{ all -> 0x0079 }
            r5.<init>(r4)     // Catch:{ all -> 0x0079 }
            boolean r4 = r0.removeIf(r5)     // Catch:{ all -> 0x0079 }
            if (r4 != 0) goto L_0x002e
            r1.add(r3)     // Catch:{ all -> 0x0079 }
            goto L_0x002e
        L_0x004a:
            int r2 = r0.size()     // Catch:{ all -> 0x0079 }
            int r3 = r8.size()     // Catch:{ all -> 0x0079 }
            int r2 = r2 + r3
            int r3 = r6.capacity     // Catch:{ all -> 0x0079 }
            int r2 = r2 - r3
            if (r2 <= 0) goto L_0x0069
            java.util.List r3 = kotlin.collections.CollectionsKt___CollectionsKt.take(r0, r2)     // Catch:{ all -> 0x0079 }
            r6.uncache(r3)     // Catch:{ all -> 0x0079 }
            java.util.List r0 = kotlin.collections.CollectionsKt___CollectionsKt.drop(r0, r2)     // Catch:{ all -> 0x0079 }
            java.util.ArrayList r2 = new java.util.ArrayList     // Catch:{ all -> 0x0079 }
            r2.<init>(r0)     // Catch:{ all -> 0x0079 }
            r0 = r2
        L_0x0069:
            r0.addAll(r8)     // Catch:{ all -> 0x0079 }
            android.util.SparseArray<java.util.List<com.android.wm.shell.bubbles.storage.BubbleEntity>> r8 = r6.entitiesByUser     // Catch:{ all -> 0x0079 }
            r8.put(r7, r0)     // Catch:{ all -> 0x0079 }
            r6.cache(r1)     // Catch:{ all -> 0x0079 }
            monitor-exit(r6)
            return
        L_0x0076:
            r7 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x0079 }
            throw r7     // Catch:{ all -> 0x0079 }
        L_0x0079:
            r7 = move-exception
            monitor-exit(r6)
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.p012wm.shell.bubbles.storage.BubbleVolatileRepository.addBubbles(int, java.util.List):void");
    }

    public final void cache(ArrayList arrayList) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            BubbleEntity bubbleEntity = (BubbleEntity) next;
            Objects.requireNonNull(bubbleEntity);
            ShortcutKey shortcutKey = new ShortcutKey(bubbleEntity.userId, bubbleEntity.packageName);
            Object obj = linkedHashMap.get(shortcutKey);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(shortcutKey, obj);
            }
            ((List) obj).add(next);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            ShortcutKey shortcutKey2 = (ShortcutKey) entry.getKey();
            List<BubbleEntity> list = (List) entry.getValue();
            LauncherApps launcherApps2 = this.launcherApps;
            Objects.requireNonNull(shortcutKey2);
            String str = shortcutKey2.pkg;
            ArrayList arrayList2 = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list, 10));
            for (BubbleEntity bubbleEntity2 : list) {
                Objects.requireNonNull(bubbleEntity2);
                arrayList2.add(bubbleEntity2.shortcutId);
            }
            launcherApps2.cacheShortcuts(str, arrayList2, UserHandle.of(shortcutKey2.userId), 1);
        }
    }

    public final void uncache(List<BubbleEntity> list) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (T next : list) {
            BubbleEntity bubbleEntity = (BubbleEntity) next;
            Objects.requireNonNull(bubbleEntity);
            ShortcutKey shortcutKey = new ShortcutKey(bubbleEntity.userId, bubbleEntity.packageName);
            Object obj = linkedHashMap.get(shortcutKey);
            if (obj == null) {
                obj = new ArrayList();
                linkedHashMap.put(shortcutKey, obj);
            }
            ((List) obj).add(next);
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            ShortcutKey shortcutKey2 = (ShortcutKey) entry.getKey();
            List<BubbleEntity> list2 = (List) entry.getValue();
            LauncherApps launcherApps2 = this.launcherApps;
            Objects.requireNonNull(shortcutKey2);
            String str = shortcutKey2.pkg;
            ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list2, 10));
            for (BubbleEntity bubbleEntity2 : list2) {
                Objects.requireNonNull(bubbleEntity2);
                arrayList.add(bubbleEntity2.shortcutId);
            }
            launcherApps2.uncacheShortcuts(str, arrayList, UserHandle.of(shortcutKey2.userId), 1);
        }
    }

    public BubbleVolatileRepository(LauncherApps launcherApps2) {
        this.launcherApps = launcherApps2;
    }
}
