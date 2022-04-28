package androidx.core.app;

import android.app.PendingIntent;
import android.content.res.Resources;
import android.os.Bundle;
import androidx.core.graphics.drawable.IconCompat;

public final class NotificationCompat$Action {
    public PendingIntent actionIntent;
    @Deprecated
    public int icon;
    public boolean mAllowGeneratedReplies;
    public final RemoteInput[] mDataOnlyRemoteInputs;
    public final Bundle mExtras;
    public IconCompat mIcon = null;
    public final boolean mIsContextual;
    public final RemoteInput[] mRemoteInputs;
    public final int mSemanticAction;
    public boolean mShowsUserInterface = true;
    public CharSequence title;

    public NotificationCompat$Action(String str, PendingIntent pendingIntent) {
        Bundle bundle = new Bundle();
        this.title = NotificationCompat$Builder.limitCharSequenceLength(str);
        this.actionIntent = pendingIntent;
        this.mExtras = bundle;
        this.mRemoteInputs = null;
        this.mDataOnlyRemoteInputs = null;
        this.mAllowGeneratedReplies = true;
        this.mSemanticAction = 0;
        this.mShowsUserInterface = true;
        this.mIsContextual = false;
    }

    public final IconCompat getIconCompat() {
        int i;
        if (this.mIcon == null && (i = this.icon) != 0) {
            this.mIcon = IconCompat.createWithResource((Resources) null, "", i);
        }
        return this.mIcon;
    }
}
