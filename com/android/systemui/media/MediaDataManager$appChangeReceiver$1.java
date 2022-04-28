package com.android.systemui.media;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/* compiled from: MediaDataManager.kt */
public final class MediaDataManager$appChangeReceiver$1 extends BroadcastReceiver {
    public final /* synthetic */ MediaDataManager this$0;

    public MediaDataManager$appChangeReceiver$1(MediaDataManager mediaDataManager) {
        this.this$0 = mediaDataManager;
    }

    public final void onReceive(Context context, Intent intent) {
        String[] stringArrayExtra;
        String encodedSchemeSpecificPart;
        String action = intent.getAction();
        if (action != null) {
            int hashCode = action.hashCode();
            if (hashCode != -1001645458) {
                if (hashCode != -757780528) {
                    if (hashCode != 525384130 || !action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        return;
                    }
                } else if (!action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                    return;
                }
                Uri data = intent.getData();
                if (data != null && (encodedSchemeSpecificPart = data.getEncodedSchemeSpecificPart()) != null) {
                    MediaDataManager.access$removeAllForPackage(this.this$0, encodedSchemeSpecificPart);
                }
            } else if (action.equals("android.intent.action.PACKAGES_SUSPENDED") && (stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.changed_package_list")) != null) {
                MediaDataManager mediaDataManager = this.this$0;
                int i = 0;
                int length = stringArrayExtra.length;
                while (i < length) {
                    String str = stringArrayExtra[i];
                    i++;
                    MediaDataManager.access$removeAllForPackage(mediaDataManager, str);
                }
            }
        }
    }
}
