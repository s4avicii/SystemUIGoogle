package com.android.systemui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.ArraySet;
import com.android.internal.annotations.VisibleForTesting;
import com.android.systemui.broadcast.BroadcastDispatcher;
import java.util.Iterator;

public class SliceBroadcastRelayHandler extends CoreStartable {
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final C06441 mReceiver = new BroadcastReceiver() {
        public final void onReceive(Context context, Intent intent) {
            SliceBroadcastRelayHandler.this.handleIntent(intent);
        }
    };
    public final ArrayMap<Uri, BroadcastRelay> mRelays = new ArrayMap<>();

    public static class BroadcastRelay extends BroadcastReceiver {
        public final ArraySet<ComponentName> mReceivers = new ArraySet<>();
        public final Uri mUri;
        public final UserHandle mUserId;

        public final void onReceive(Context context, Intent intent) {
            intent.addFlags(268435456);
            Iterator<ComponentName> it = this.mReceivers.iterator();
            while (it.hasNext()) {
                intent.setComponent(it.next());
                intent.putExtra("uri", this.mUri.toString());
                context.sendBroadcastAsUser(intent, this.mUserId);
            }
        }

        public BroadcastRelay(Uri uri) {
            this.mUserId = new UserHandle(ContentProvider.getUserIdFromUri(uri));
            this.mUri = uri;
        }
    }

    public final void start() {
        IntentFilter intentFilter = new IntentFilter("com.android.settingslib.action.REGISTER_SLICE_RECEIVER");
        intentFilter.addAction("com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER");
        this.mBroadcastDispatcher.registerReceiver(this.mReceiver, intentFilter);
    }

    public SliceBroadcastRelayHandler(Context context, BroadcastDispatcher broadcastDispatcher) {
        super(context);
        this.mBroadcastDispatcher = broadcastDispatcher;
    }

    @VisibleForTesting
    public void handleIntent(Intent intent) {
        BroadcastRelay remove;
        if ("com.android.settingslib.action.REGISTER_SLICE_RECEIVER".equals(intent.getAction())) {
            Uri uri = (Uri) intent.getParcelableExtra("uri");
            ComponentName componentName = (ComponentName) intent.getParcelableExtra("receiver");
            IntentFilter intentFilter = (IntentFilter) intent.getParcelableExtra("filter");
            BroadcastRelay broadcastRelay = this.mRelays.get(uri);
            if (broadcastRelay == null) {
                broadcastRelay = new BroadcastRelay(uri);
                this.mRelays.put(uri, broadcastRelay);
            }
            Context context = this.mContext;
            broadcastRelay.mReceivers.add(componentName);
            context.registerReceiver(broadcastRelay, intentFilter, 2);
        } else if ("com.android.settingslib.action.UNREGISTER_SLICE_RECEIVER".equals(intent.getAction()) && (remove = this.mRelays.remove((Uri) intent.getParcelableExtra("uri"))) != null) {
            this.mContext.unregisterReceiver(remove);
        }
    }
}
