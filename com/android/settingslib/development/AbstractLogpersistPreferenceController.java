package com.android.settingslib.development;

import android.content.IntentFilter;
import android.os.Bundle;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.preference.Preference;
import com.android.settingslib.core.lifecycle.LifecycleObserver;
import com.android.settingslib.core.lifecycle.events.OnCreate;
import com.android.settingslib.core.lifecycle.events.OnDestroy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public abstract class AbstractLogpersistPreferenceController extends DeveloperOptionsPreferenceController implements Preference.OnPreferenceChangeListener, LifecycleObserver, OnCreate, OnDestroy {
    public static final String ACTUAL_LOGPERSIST_PROPERTY = "logd.logpersistd";
    public static final String ACTUAL_LOGPERSIST_PROPERTY_BUFFER = "logd.logpersistd.buffer";
    public static final String SELECT_LOGPERSIST_PROPERTY_SERVICE = "logcatd";

    public final boolean onPreferenceChange(Preference preference, Serializable serializable) {
        return preference == null;
    }

    public final void onCreate(Bundle bundle) {
        LocalBroadcastManager localBroadcastManager;
        synchronized (LocalBroadcastManager.mLock) {
            localBroadcastManager = LocalBroadcastManager.mInstance;
            Objects.requireNonNull(localBroadcastManager);
        }
        IntentFilter intentFilter = new IntentFilter("com.android.settingslib.development.AbstractLogdSizePreferenceController.LOGD_SIZE_UPDATED");
        synchronized (localBroadcastManager.mReceivers) {
            LocalBroadcastManager.ReceiverRecord receiverRecord = new LocalBroadcastManager.ReceiverRecord(intentFilter);
            ArrayList arrayList = localBroadcastManager.mReceivers.get((Object) null);
            if (arrayList == null) {
                arrayList = new ArrayList(1);
                localBroadcastManager.mReceivers.put((Object) null, arrayList);
            }
            arrayList.add(receiverRecord);
            for (int i = 0; i < intentFilter.countActions(); i++) {
                String action = intentFilter.getAction(i);
                ArrayList arrayList2 = localBroadcastManager.mActions.get(action);
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                    localBroadcastManager.mActions.put(action, arrayList2);
                }
                arrayList2.add(receiverRecord);
            }
        }
    }

    public final void onDestroy() {
        LocalBroadcastManager localBroadcastManager;
        synchronized (LocalBroadcastManager.mLock) {
            localBroadcastManager = LocalBroadcastManager.mInstance;
            Objects.requireNonNull(localBroadcastManager);
        }
        synchronized (localBroadcastManager.mReceivers) {
            ArrayList remove = localBroadcastManager.mReceivers.remove((Object) null);
            if (remove != null) {
                for (int size = remove.size() - 1; size >= 0; size--) {
                    LocalBroadcastManager.ReceiverRecord receiverRecord = (LocalBroadcastManager.ReceiverRecord) remove.get(size);
                    receiverRecord.dead = true;
                    for (int i = 0; i < receiverRecord.filter.countActions(); i++) {
                        String action = receiverRecord.filter.getAction(i);
                        ArrayList arrayList = localBroadcastManager.mActions.get(action);
                        if (arrayList != null) {
                            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                                LocalBroadcastManager.ReceiverRecord receiverRecord2 = (LocalBroadcastManager.ReceiverRecord) arrayList.get(size2);
                                if (receiverRecord2.receiver == null) {
                                    receiverRecord2.dead = true;
                                    arrayList.remove(size2);
                                }
                            }
                            if (arrayList.size() <= 0) {
                                localBroadcastManager.mActions.remove(action);
                            }
                        }
                    }
                }
            }
        }
    }
}
