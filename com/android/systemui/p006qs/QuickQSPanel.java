package com.android.systemui.p006qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSPanel;
import com.android.systemui.p006qs.QSPanelControllerBase;
import com.android.systemui.plugins.p005qs.QSTile;

/* renamed from: com.android.systemui.qs.QuickQSPanel */
public class QuickQSPanel extends QSPanel {
    public boolean mDisabledByPolicy;
    public int mMaxTiles = getResources().getInteger(C1777R.integer.quick_qs_panel_max_tiles);

    /* renamed from: com.android.systemui.qs.QuickQSPanel$QQSSideLabelTileLayout */
    public static class QQSSideLabelTileLayout extends SideLabelTileLayout {
        public boolean mLastSelected;

        public QQSSideLabelTileLayout(Context context) {
            super(context, (AttributeSet) null);
            setClipChildren(false);
            setClipToPadding(false);
            setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
            setMaxColumns(4);
        }

        public final void setExpansion(float f, float f2) {
            boolean z;
            if (f <= 0.0f || f >= 1.0f) {
                if (f == 1.0f || f2 < 0.0f) {
                    z = true;
                } else {
                    z = false;
                }
                if (this.mLastSelected != z) {
                    setImportantForAccessibility(4);
                    for (int i = 0; i < getChildCount(); i++) {
                        getChildAt(i).setSelected(z);
                    }
                    setImportantForAccessibility(0);
                    this.mLastSelected = z;
                }
            }
        }

        public final void onMeasure(int i, int i2) {
            updateMaxRows(10000, this.mRecords.size());
            super.onMeasure(i, i2);
        }

        public final void setListening(boolean z, UiEventLogger uiEventLogger) {
            boolean z2;
            if (this.mListening || !z) {
                z2 = false;
            } else {
                z2 = true;
            }
            super.setListening(z, uiEventLogger);
            if (z2) {
                for (int i = 0; i < getNumVisibleTiles(); i++) {
                    QSTile qSTile = this.mRecords.get(i).tile;
                    uiEventLogger.logWithInstanceId(QSEvent.QQS_TILE_VISIBLE, 0, qSTile.getMetricsSpec(), qSTile.getInstanceId());
                }
            }
        }

        public final void onConfigurationChanged(Configuration configuration) {
            super.onConfigurationChanged(configuration);
            updateResources();
        }

        public final boolean updateResources() {
            this.mCellHeightResId = C1777R.dimen.qs_quick_tile_size;
            boolean updateResources = super.updateResources();
            this.mMaxAllowedRows = getResources().getInteger(C1777R.integer.quick_qs_panel_max_rows);
            return updateResources;
        }
    }

    public final String getDumpableTag() {
        return "QuickQSPanel";
    }

    public final void updatePadding() {
    }

    public final void drawTile(QSPanelControllerBase.TileRecord tileRecord, QSTile.State state) {
        if (state instanceof QSTile.SignalState) {
            QSTile.SignalState signalState = new QSTile.SignalState();
            state.copyTo(signalState);
            signalState.activityIn = false;
            signalState.activityOut = false;
            state = signalState;
        }
        tileRecord.tileView.onStateChanged(state);
    }

    public final QSPanel.QSTileLayout getOrCreateTileLayout() {
        return new QQSSideLabelTileLayout(this.mContext);
    }

    public final void setHorizontalContentContainerClipping() {
        this.mHorizontalContentContainer.setClipToPadding(false);
        this.mHorizontalContentContainer.setClipChildren(false);
    }

    public final void setVisibility(int i) {
        if (this.mDisabledByPolicy) {
            if (getVisibility() != 8) {
                i = 8;
            } else {
                return;
            }
        }
        super.setVisibility(i);
    }

    public QuickQSPanel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public final void onTuningChanged(String str, String str2) {
        if ("qs_show_brightness".equals(str)) {
            super.onTuningChanged(str, "0");
        }
    }

    public final QSEvent closePanelEvent() {
        return QSEvent.QQS_PANEL_COLLAPSED;
    }

    public final QSEvent openPanelEvent() {
        return QSEvent.QQS_PANEL_EXPANDED;
    }
}
