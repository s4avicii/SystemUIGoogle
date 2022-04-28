package com.android.systemui.toast;

import android.content.Context;
import android.view.LayoutInflater;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.Plugin;
import com.android.systemui.plugins.PluginListener;
import com.android.systemui.plugins.ToastPlugin;
import com.android.systemui.shared.plugins.PluginManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public final class ToastFactory implements Dumpable {
    public final LayoutInflater mLayoutInflater;
    public ToastPlugin mPlugin;

    public final SystemUIToast createToast(Context context, CharSequence charSequence, String str, int i, int i2) {
        boolean z;
        ToastPlugin toastPlugin = this.mPlugin;
        if (toastPlugin != null) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            CharSequence charSequence2 = charSequence;
            return new SystemUIToast(this.mLayoutInflater, context, charSequence, toastPlugin.createToast(charSequence, str, i), str, i, i2);
        }
        CharSequence charSequence3 = charSequence;
        String str2 = str;
        int i3 = i;
        return new SystemUIToast(this.mLayoutInflater, context, charSequence, (ToastPlugin.Toast) null, str, i, i2);
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "ToastFactory:", "    mAttachedPlugin=");
        m.append(this.mPlugin);
        printWriter.println(m.toString());
    }

    public ToastFactory(LayoutInflater layoutInflater, PluginManager pluginManager, DumpManager dumpManager) {
        this.mLayoutInflater = layoutInflater;
        dumpManager.registerDumpable("ToastFactory", this);
        pluginManager.addPluginListener(new PluginListener<ToastPlugin>() {
            public final void onPluginConnected(Plugin plugin, Context context) {
                ToastFactory.this.mPlugin = (ToastPlugin) plugin;
            }

            public final void onPluginDisconnected(Plugin plugin) {
                if (((ToastPlugin) plugin).equals(ToastFactory.this.mPlugin)) {
                    ToastFactory.this.mPlugin = null;
                }
            }
        }, ToastPlugin.class, false);
    }
}
