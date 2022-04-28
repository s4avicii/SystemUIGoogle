package com.android.systemui.statusbar.phone.panelstate;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: PanelExpansionStateManager.kt */
public final class PanelExpansionStateManager {
    public boolean expanded;
    public final ArrayList expansionListeners = new ArrayList();
    public float fraction;
    public int state;
    public final ArrayList stateListeners = new ArrayList();
    public boolean tracking;

    public final void addExpansionListener(PanelExpansionListener panelExpansionListener) {
        this.expansionListeners.add(panelExpansionListener);
        panelExpansionListener.onPanelExpansionChanged(this.fraction, this.expanded, this.tracking);
    }

    public final void updateStateInternal(int i) {
        PanelExpansionStateManagerKt.access$stateToString(this.state);
        PanelExpansionStateManagerKt.access$stateToString(i);
        this.state = i;
        Iterator it = this.stateListeners.iterator();
        while (it.hasNext()) {
            ((PanelStateListener) it.next()).onPanelStateChanged(i);
        }
    }
}
