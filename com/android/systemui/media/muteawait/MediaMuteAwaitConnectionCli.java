package com.android.systemui.media.muteawait;

import android.content.Context;
import android.media.AudioDeviceAttributes;
import android.media.AudioManager;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaMuteAwaitConnectionCli.kt */
public final class MediaMuteAwaitConnectionCli {
    public final Context context;

    /* compiled from: MediaMuteAwaitConnectionCli.kt */
    public final class MuteAwaitCommand implements Command {
        public MuteAwaitCommand() {
        }

        public final void execute(PrintWriter printWriter, List<String> list) {
            EmptyList emptyList = EmptyList.INSTANCE;
            AudioDeviceAttributes audioDeviceAttributes = new AudioDeviceAttributes(2, Integer.parseInt(list.get(0)), "address", list.get(1), emptyList, emptyList);
            String str = list.get(2);
            Object systemService = MediaMuteAwaitConnectionCli.this.context.getSystemService("audio");
            Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.media.AudioManager");
            AudioManager audioManager = (AudioManager) systemService;
            if (Intrinsics.areEqual(str, "start")) {
                audioManager.muteAwaitConnection(new int[]{1}, audioDeviceAttributes, 5, MediaMuteAwaitConnectionCliKt.TIMEOUT_UNITS);
            } else if (Intrinsics.areEqual(str, "cancel")) {
                audioManager.cancelMuteAwaitConnection(audioDeviceAttributes);
            } else {
                printWriter.println(Intrinsics.stringPlus("Must specify `start` or `cancel`; was ", str));
            }
        }
    }

    public MediaMuteAwaitConnectionCli(CommandRegistry commandRegistry, Context context2) {
        this.context = context2;
        commandRegistry.registerCommand("media-mute-await", new Function0<Command>(this) {
            public final /* synthetic */ MediaMuteAwaitConnectionCli this$0;

            {
                this.this$0 = r1;
            }

            public final Object invoke() {
                return new MuteAwaitCommand();
            }
        });
    }
}
