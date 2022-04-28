package com.android.keyguard.clock;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.dock.DockManager;
import com.android.systemui.plugins.ClockPlugin;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.settings.CurrentUserObservable;
import com.android.systemui.shared.plugins.PluginManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

public final class ClockManager {
    public final ArrayList mBuiltinClocks = new ArrayList();
    public final C05741 mContentObserver;
    public final ContentResolver mContentResolver;
    public final CurrentUserObservable mCurrentUserObservable;
    public final ClockManager$$ExternalSyntheticLambda0 mCurrentUserObserver;
    public final C05752 mDockEventListener;
    public final DockManager mDockManager;
    public final int mHeight;
    public boolean mIsDocked;
    public final ArrayMap mListeners;
    public final Handler mMainHandler;
    public final PluginManager mPluginManager;
    public final AvailableClocks mPreviewClocks;
    public final SettingsWrapper mSettingsWrapper;
    public final int mWidth;

    public final class AvailableClocks implements PluginListener<ClockPlugin> {
        public final ArrayList mClockInfo = new ArrayList();
        public final ArrayMap mClocks = new ArrayMap();
        public ClockPlugin mCurrentClock;

        public AvailableClocks() {
        }

        public final void onPluginConnected(Plugin plugin, Context context) {
            boolean z;
            ClockPlugin clockPlugin = (ClockPlugin) plugin;
            addClockPlugin(clockPlugin);
            boolean z2 = true;
            if (clockPlugin == this.mCurrentClock) {
                z = true;
            } else {
                z = false;
            }
            reloadCurrentClock();
            if (clockPlugin != this.mCurrentClock) {
                z2 = false;
            }
            if (z || z2) {
                ClockManager.this.reload();
            }
        }

        public final void onPluginDisconnected(Plugin plugin) {
            boolean z;
            ClockPlugin clockPlugin = (ClockPlugin) plugin;
            String name = clockPlugin.getClass().getName();
            this.mClocks.remove(name);
            boolean z2 = false;
            int i = 0;
            while (true) {
                if (i >= this.mClockInfo.size()) {
                    break;
                }
                ClockInfo clockInfo = (ClockInfo) this.mClockInfo.get(i);
                Objects.requireNonNull(clockInfo);
                if (name.equals(clockInfo.mId)) {
                    this.mClockInfo.remove(i);
                    break;
                }
                i++;
            }
            if (clockPlugin == this.mCurrentClock) {
                z = true;
            } else {
                z = false;
            }
            reloadCurrentClock();
            if (clockPlugin == this.mCurrentClock) {
                z2 = true;
            }
            if (z || z2) {
                ClockManager.this.reload();
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:5:0x0031, code lost:
            if (r0 != null) goto L_0x0062;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void reloadCurrentClock() {
            /*
                r5 = this;
                com.android.keyguard.clock.ClockManager r0 = com.android.keyguard.clock.ClockManager.this
                boolean r0 = r0.isDocked()
                if (r0 == 0) goto L_0x0034
                com.android.keyguard.clock.ClockManager r0 = com.android.keyguard.clock.ClockManager.this
                com.android.keyguard.clock.SettingsWrapper r1 = r0.mSettingsWrapper
                com.android.systemui.settings.CurrentUserObservable r0 = r0.mCurrentUserObservable
                com.android.systemui.settings.CurrentUserObservable$1 r0 = r0.getCurrentUser()
                java.lang.Object r0 = r0.getValue()
                java.lang.Integer r0 = (java.lang.Integer) r0
                int r0 = r0.intValue()
                java.util.Objects.requireNonNull(r1)
                android.content.ContentResolver r1 = r1.mContentResolver
                java.lang.String r2 = "docked_clock_face"
                java.lang.String r0 = android.provider.Settings.Secure.getStringForUser(r1, r2, r0)
                if (r0 == 0) goto L_0x0034
                android.util.ArrayMap r1 = r5.mClocks
                java.lang.Object r0 = r1.get(r0)
                com.android.systemui.plugins.ClockPlugin r0 = (com.android.systemui.plugins.ClockPlugin) r0
                if (r0 == 0) goto L_0x0035
                goto L_0x0062
            L_0x0034:
                r0 = 0
            L_0x0035:
                com.android.keyguard.clock.ClockManager r1 = com.android.keyguard.clock.ClockManager.this
                com.android.keyguard.clock.SettingsWrapper r2 = r1.mSettingsWrapper
                com.android.systemui.settings.CurrentUserObservable r1 = r1.mCurrentUserObservable
                com.android.systemui.settings.CurrentUserObservable$1 r1 = r1.getCurrentUser()
                java.lang.Object r1 = r1.getValue()
                java.lang.Integer r1 = (java.lang.Integer) r1
                int r1 = r1.intValue()
                java.util.Objects.requireNonNull(r2)
                android.content.ContentResolver r3 = r2.mContentResolver
                java.lang.String r4 = "lock_screen_custom_clock_face"
                java.lang.String r3 = android.provider.Settings.Secure.getStringForUser(r3, r4, r1)
                java.lang.String r1 = r2.decode(r3, r1)
                if (r1 == 0) goto L_0x0062
                android.util.ArrayMap r0 = r5.mClocks
                java.lang.Object r0 = r0.get(r1)
                com.android.systemui.plugins.ClockPlugin r0 = (com.android.systemui.plugins.ClockPlugin) r0
            L_0x0062:
                r5.mCurrentClock = r0
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.keyguard.clock.ClockManager.AvailableClocks.reloadCurrentClock():void");
        }

        public final void addClockPlugin(ClockPlugin clockPlugin) {
            String name = clockPlugin.getClass().getName();
            this.mClocks.put(clockPlugin.getClass().getName(), clockPlugin);
            this.mClockInfo.add(new ClockInfo(clockPlugin.getName(), new ClockManager$AvailableClocks$$ExternalSyntheticLambda0(clockPlugin, 0), name, new ClockManager$AvailableClocks$$ExternalSyntheticLambda2(clockPlugin), new ClockManager$AvailableClocks$$ExternalSyntheticLambda1(this, clockPlugin)));
        }
    }

    public interface ClockChangedListener {
        void onClockChanged(ClockPlugin clockPlugin);
    }

    public final void reload() {
        this.mPreviewClocks.reloadCurrentClock();
        this.mListeners.forEach(new ClockManager$$ExternalSyntheticLambda2(this));
    }

    public ClockManager(Context context, LayoutInflater layoutInflater, PluginManager pluginManager, SysuiColorExtractor sysuiColorExtractor, ContentResolver contentResolver, CurrentUserObservable currentUserObservable, SettingsWrapper settingsWrapper, DockManager dockManager) {
        Handler handler = new Handler(Looper.getMainLooper());
        this.mMainHandler = handler;
        this.mContentObserver = new ContentObserver(handler) {
            public final void onChange(boolean z, Collection<Uri> collection, int i, int i2) {
                if (Objects.equals(Integer.valueOf(i2), ClockManager.this.mCurrentUserObservable.getCurrentUser().getValue())) {
                    ClockManager.this.reload();
                }
            }
        };
        this.mCurrentUserObserver = new ClockManager$$ExternalSyntheticLambda0(this);
        this.mDockEventListener = new DockManager.DockEventListener() {
            public final void onEvent(int i) {
                ClockManager clockManager = ClockManager.this;
                boolean z = true;
                if (!(i == 1 || i == 2)) {
                    z = false;
                }
                clockManager.mIsDocked = z;
                clockManager.reload();
            }
        };
        this.mListeners = new ArrayMap();
        this.mPluginManager = pluginManager;
        this.mContentResolver = contentResolver;
        this.mSettingsWrapper = settingsWrapper;
        this.mCurrentUserObservable = currentUserObservable;
        this.mDockManager = dockManager;
        this.mPreviewClocks = new AvailableClocks();
        Resources resources = context.getResources();
        addBuiltinClock(new ClockManager$$ExternalSyntheticLambda3(resources, layoutInflater, sysuiColorExtractor));
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        this.mWidth = displayMetrics.widthPixels;
        this.mHeight = displayMetrics.heightPixels;
    }

    public void addBuiltinClock(Supplier<ClockPlugin> supplier) {
        this.mPreviewClocks.addClockPlugin(supplier.get());
        this.mBuiltinClocks.add(supplier);
    }

    public ContentObserver getContentObserver() {
        return this.mContentObserver;
    }

    public boolean isDocked() {
        return this.mIsDocked;
    }
}
