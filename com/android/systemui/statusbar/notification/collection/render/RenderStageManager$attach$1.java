package com.android.systemui.statusbar.notification.collection.render;

import android.os.Trace;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.ShadeListBuilder;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnAfterRenderListListener;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* compiled from: RenderStageManager.kt */
public final /* synthetic */ class RenderStageManager$attach$1 implements ShadeListBuilder.OnRenderListListener {
    public final /* synthetic */ RenderStageManager $tmp0;

    public RenderStageManager$attach$1(RenderStageManager renderStageManager) {
        this.$tmp0 = renderStageManager;
    }

    public final void onRenderList(List<? extends ListEntry> list) {
        RenderStageManager renderStageManager = this.$tmp0;
        Objects.requireNonNull(renderStageManager);
        Trace.beginSection("RenderStageManager.onRenderList");
        try {
            NotifViewRenderer notifViewRenderer = renderStageManager.viewRenderer;
            if (notifViewRenderer != null) {
                notifViewRenderer.onRenderList(list);
                Trace.beginSection("RenderStageManager.dispatchOnAfterRenderList");
                NotifStackController stackController = notifViewRenderer.getStackController();
                Iterator it = renderStageManager.onAfterRenderListListeners.iterator();
                while (it.hasNext()) {
                    ((OnAfterRenderListListener) it.next()).onAfterRenderList(list, stackController);
                }
                Trace.endSection();
                renderStageManager.dispatchOnAfterRenderGroups(notifViewRenderer, list);
                renderStageManager.dispatchOnAfterRenderEntries(notifViewRenderer, list);
                notifViewRenderer.onDispatchComplete();
            }
        } catch (Throwable th) {
            throw th;
        } finally {
            Trace.endSection();
        }
    }
}
