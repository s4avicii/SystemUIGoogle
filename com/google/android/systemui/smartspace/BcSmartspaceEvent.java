package com.google.android.systemui.smartspace;

public enum BcSmartspaceEvent implements EventEnum {
    SMARTSPACE_CARD_RECEIVED(759),
    SMARTSPACE_CARD_CLICK(760),
    SMARTSPACE_CARD_SEEN(800);
    
    private final int mId;

    /* access modifiers changed from: public */
    BcSmartspaceEvent(int i) {
        this.mId = i;
    }

    public final int getId() {
        return this.mId;
    }
}
