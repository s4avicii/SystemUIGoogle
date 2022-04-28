package com.android.systemui.p006qs;

import androidx.preference.R$id;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.MediaFlags;
import com.android.systemui.media.MediaHost;
import com.android.systemui.p006qs.customize.QSCustomizerController;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.plugins.p005qs.QSTile;
import java.util.ArrayList;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* renamed from: com.android.systemui.qs.QuickQSPanelController */
public final class QuickQSPanelController extends QSPanelControllerBase<QuickQSPanel> {
    public final MediaFlags mMediaFlags;
    public final QuickQSPanelController$$ExternalSyntheticLambda0 mOnConfigurationChangedListener = new QuickQSPanelController$$ExternalSyntheticLambda0(this);
    public final boolean mUsingCollapsedLandscapeMedia;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public QuickQSPanelController(QuickQSPanel quickQSPanel, QSTileHost qSTileHost, QSCustomizerController qSCustomizerController, boolean z, MediaHost mediaHost, boolean z2, MediaFlags mediaFlags, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager) {
        super(quickQSPanel, qSTileHost, qSCustomizerController, z, mediaHost, metricsLogger, uiEventLogger, qSLogger, dumpManager);
        this.mUsingCollapsedLandscapeMedia = z2;
        this.mMediaFlags = mediaFlags;
    }

    public final void setTiles() {
        ArrayList arrayList = new ArrayList();
        QSTileHost qSTileHost = this.mHost;
        Objects.requireNonNull(qSTileHost);
        for (QSTile add : qSTileHost.mTiles.values()) {
            arrayList.add(add);
            int size = arrayList.size();
            QuickQSPanel quickQSPanel = (QuickQSPanel) this.mView;
            Objects.requireNonNull(quickQSPanel);
            if (size == quickQSPanel.mMaxTiles) {
                break;
            }
        }
        setTiles(arrayList, true);
    }

    public int getRotation() {
        return R$id.getRotation(getContext());
    }

    public final void onInit() {
        super.onInit();
        updateMediaExpansion();
        MediaHost mediaHost = this.mMediaHost;
        Objects.requireNonNull(mediaHost);
        MediaHost.MediaHostStateHolder mediaHostStateHolder = mediaHost.state;
        Objects.requireNonNull(mediaHostStateHolder);
        if (!Boolean.TRUE.equals(Boolean.valueOf(mediaHostStateHolder.showsOnlyActiveMedia))) {
            mediaHostStateHolder.showsOnlyActiveMedia = true;
            Function0<Unit> function0 = mediaHostStateHolder.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }
        this.mMediaHost.init(1);
    }

    public final void onViewAttached() {
        super.onViewAttached();
        QuickQSPanel quickQSPanel = (QuickQSPanel) this.mView;
        QuickQSPanelController$$ExternalSyntheticLambda0 quickQSPanelController$$ExternalSyntheticLambda0 = this.mOnConfigurationChangedListener;
        Objects.requireNonNull(quickQSPanel);
        quickQSPanel.mOnConfigurationChangedListeners.add(quickQSPanelController$$ExternalSyntheticLambda0);
    }

    public final void onViewDetached() {
        super.onViewDetached();
        QuickQSPanel quickQSPanel = (QuickQSPanel) this.mView;
        QuickQSPanelController$$ExternalSyntheticLambda0 quickQSPanelController$$ExternalSyntheticLambda0 = this.mOnConfigurationChangedListener;
        Objects.requireNonNull(quickQSPanel);
        quickQSPanel.mOnConfigurationChangedListeners.remove(quickQSPanelController$$ExternalSyntheticLambda0);
    }

    public final void updateMediaExpansion() {
        int rotation = getRotation();
        boolean z = true;
        if (!(rotation == 1 || rotation == 3)) {
            z = false;
        }
        if (!this.mMediaFlags.useMediaSessionLayout() || (this.mUsingCollapsedLandscapeMedia && z)) {
            this.mMediaHost.setExpansion(0.0f);
        } else {
            this.mMediaHost.setExpansion(1.0f);
        }
    }

    public final void setListening(boolean z) {
        super.setListening(z);
    }

    public final void onConfigurationChanged() {
        updateMediaExpansion();
    }
}
