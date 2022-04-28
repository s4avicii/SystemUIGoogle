package com.android.systemui.shortcut;

import android.content.Context;
import android.os.RemoteException;
import android.view.IWindowManager;
import android.view.WindowManagerGlobal;
import com.android.p012wm.shell.legacysplitscreen.LegacySplitScreen;
import com.android.systemui.CoreStartable;
import com.android.systemui.shortcut.ShortcutKeyServiceProxy;
import java.util.Optional;

public class ShortcutKeyDispatcher extends CoreStartable implements ShortcutKeyServiceProxy.Callbacks {
    public ShortcutKeyServiceProxy mShortcutKeyServiceProxy = new ShortcutKeyServiceProxy(this);
    public final Optional<LegacySplitScreen> mSplitScreenOptional;
    public IWindowManager mWindowManagerService = WindowManagerGlobal.getWindowManagerService();

    public final void start() {
        try {
            this.mWindowManagerService.registerShortcutKey(281474976710727L, this.mShortcutKeyServiceProxy);
        } catch (RemoteException unused) {
        }
        try {
            this.mWindowManagerService.registerShortcutKey(281474976710728L, this.mShortcutKeyServiceProxy);
        } catch (RemoteException unused2) {
        }
    }

    public ShortcutKeyDispatcher(Context context, Optional<LegacySplitScreen> optional) {
        super(context);
        this.mSplitScreenOptional = optional;
    }
}
