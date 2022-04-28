package com.android.systemui.recents;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.provider.Settings;
import com.android.systemui.CoreStartable;
import com.android.systemui.statusbar.CommandQueue;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;

public class Recents extends CoreStartable implements CommandQueue.Callbacks {
    public final CommandQueue mCommandQueue;
    public final RecentsImplementation mImpl;

    public final void appTransitionFinished(int i) {
        if (this.mContext.getDisplayId() == i) {
            Objects.requireNonNull(this.mImpl);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Objects.requireNonNull(this.mImpl);
    }

    public final boolean isUserSetup() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (Settings.Global.getInt(contentResolver, "device_provisioned", 0) == 0 || Settings.Secure.getInt(contentResolver, "user_setup_complete", 0) == 0) {
            return false;
        }
        return true;
    }

    public final void onBootCompleted() {
        Objects.requireNonNull(this.mImpl);
    }

    public final void onConfigurationChanged(Configuration configuration) {
        Objects.requireNonNull(this.mImpl);
    }

    public final void start() {
        this.mCommandQueue.addCallback((CommandQueue.Callbacks) this);
        this.mImpl.onStart(this.mContext);
    }

    public Recents(Context context, RecentsImplementation recentsImplementation, CommandQueue commandQueue) {
        super(context);
        this.mImpl = recentsImplementation;
        this.mCommandQueue = commandQueue;
    }

    public final void cancelPreloadRecentApps() {
        if (isUserSetup()) {
            Objects.requireNonNull(this.mImpl);
        }
    }

    public final void hideRecentApps(boolean z, boolean z2) {
        if (isUserSetup()) {
            this.mImpl.hideRecentApps(z, z2);
        }
    }

    public final void preloadRecentApps() {
        if (isUserSetup()) {
            Objects.requireNonNull(this.mImpl);
        }
    }

    public final void showRecentApps(boolean z) {
        if (isUserSetup()) {
            this.mImpl.showRecentApps(z);
        }
    }

    public final void toggleRecentApps() {
        if (isUserSetup()) {
            this.mImpl.toggleRecentApps();
        }
    }
}
