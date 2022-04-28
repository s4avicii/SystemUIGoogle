package com.android.p012wm.shell.bubbles;

import android.content.Context;
import android.content.LocusId;
import android.content.pm.LauncherApps;
import com.android.p012wm.shell.bubbles.storage.BubbleEntity;
import com.android.p012wm.shell.bubbles.storage.BubblePersistentRepository;
import com.android.p012wm.shell.bubbles.storage.BubbleVolatileRepository;
import com.android.p012wm.shell.common.ShellExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.internal.ContextScope;
import kotlinx.coroutines.scheduling.LimitingDispatcher;

/* renamed from: com.android.wm.shell.bubbles.BubbleDataRepository */
/* compiled from: BubbleDataRepository.kt */
public final class BubbleDataRepository {
    public final ContextScope ioScope;
    public StandaloneCoroutine job;
    public final LauncherApps launcherApps;
    public final ShellExecutor mainExecutor;
    public final BubblePersistentRepository persistentRepository;
    public final BubbleVolatileRepository volatileRepository;

    public static ArrayList transform(List list) {
        String str;
        ArrayList arrayList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            Bubble bubble = (Bubble) it.next();
            Objects.requireNonNull(bubble);
            int identifier = bubble.mUser.getIdentifier();
            String str2 = bubble.mPackageName;
            String str3 = bubble.mMetadataShortcutId;
            BubbleEntity bubbleEntity = null;
            if (str3 != null) {
                String str4 = bubble.mKey;
                int i = bubble.mDesiredHeight;
                int i2 = bubble.mDesiredHeightResId;
                String str5 = bubble.mTitle;
                int taskId = bubble.getTaskId();
                LocusId locusId = bubble.mLocusId;
                if (locusId == null) {
                    str = null;
                } else {
                    str = locusId.getId();
                }
                bubbleEntity = new BubbleEntity(identifier, str2, str3, str4, i, i2, str5, taskId, str);
            }
            if (bubbleEntity != null) {
                arrayList.add(bubbleEntity);
            }
        }
        return arrayList;
    }

    public BubbleDataRepository(Context context, LauncherApps launcherApps2, ShellExecutor shellExecutor) {
        this.launcherApps = launcherApps2;
        this.mainExecutor = shellExecutor;
        this.volatileRepository = new BubbleVolatileRepository(launcherApps2);
        this.persistentRepository = new BubblePersistentRepository(context);
        LimitingDispatcher limitingDispatcher = Dispatchers.f157IO;
        this.ioScope = new ContextScope(limitingDispatcher.get(Job.Key.$$INSTANCE) == null ? limitingDispatcher.plus(new JobImpl((Job) null)) : limitingDispatcher);
    }
}
