package com.android.systemui.statusbar.phone;

import androidx.collection.ArraySet;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.provider.OnReorderingAllowedListener;
import com.android.systemui.statusbar.notification.stack.C1383xbae1b0c2;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;
import java.util.Objects;

/* compiled from: R8$$SyntheticClass */
public final /* synthetic */ class HeadsUpManagerPhone$$ExternalSyntheticLambda0 implements OnReorderingAllowedListener {
    public final /* synthetic */ HeadsUpManagerPhone f$0;

    public /* synthetic */ HeadsUpManagerPhone$$ExternalSyntheticLambda0(HeadsUpManagerPhone headsUpManagerPhone) {
        this.f$0 = headsUpManagerPhone;
    }

    public final void onReorderingAllowed() {
        HeadsUpManagerPhone headsUpManagerPhone = this.f$0;
        Objects.requireNonNull(headsUpManagerPhone);
        C1383xbae1b0c2 notificationStackScrollLayoutController$$ExternalSyntheticLambda3 = (C1383xbae1b0c2) headsUpManagerPhone.mAnimationStateHandler;
        Objects.requireNonNull(notificationStackScrollLayoutController$$ExternalSyntheticLambda3);
        NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController$$ExternalSyntheticLambda3.f$0;
        Objects.requireNonNull(notificationStackScrollLayout);
        notificationStackScrollLayout.mHeadsUpGoingAwayAnimationsAllowed = false;
        ArraySet<NotificationEntry> arraySet = headsUpManagerPhone.mEntriesToRemoveWhenReorderingAllowed;
        Objects.requireNonNull(arraySet);
        ArraySet.ElementIterator elementIterator = new ArraySet.ElementIterator();
        while (elementIterator.hasNext()) {
            NotificationEntry notificationEntry = (NotificationEntry) elementIterator.next();
            Objects.requireNonNull(notificationEntry);
            if (headsUpManagerPhone.isAlerting(notificationEntry.mKey)) {
                headsUpManagerPhone.removeAlertEntry(notificationEntry.mKey);
            }
        }
        headsUpManagerPhone.mEntriesToRemoveWhenReorderingAllowed.clear();
        C1383xbae1b0c2 notificationStackScrollLayoutController$$ExternalSyntheticLambda32 = (C1383xbae1b0c2) headsUpManagerPhone.mAnimationStateHandler;
        Objects.requireNonNull(notificationStackScrollLayoutController$$ExternalSyntheticLambda32);
        NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayoutController$$ExternalSyntheticLambda32.f$0;
        Objects.requireNonNull(notificationStackScrollLayout2);
        notificationStackScrollLayout2.mHeadsUpGoingAwayAnimationsAllowed = true;
    }
}
