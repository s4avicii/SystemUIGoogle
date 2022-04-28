package com.android.systemui.p006qs;

import android.content.Context;
import com.android.internal.logging.InstanceId;
import com.android.internal.logging.UiEventLogger;
import java.util.ArrayList;

/* renamed from: com.android.systemui.qs.QSHost */
public interface QSHost {

    /* renamed from: com.android.systemui.qs.QSHost$Callback */
    public interface Callback {
        void onTilesChanged();
    }

    void collapsePanels();

    Context getContext();

    InstanceId getNewInstanceId();

    UiEventLogger getUiEventLogger();

    Context getUserContext();

    int getUserId();

    int indexOf(String str);

    void openPanels();

    void removeTile(String str);

    void removeTiles(ArrayList arrayList);

    void unmarkTileAsAutoAdded(String str);

    void warn();
}
