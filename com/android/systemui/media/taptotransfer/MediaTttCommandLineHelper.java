package com.android.systemui.media.taptotransfer;

import android.app.StatusBarManager;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.media.MediaRoute2Info;
import com.android.systemui.media.taptotransfer.sender.AlmostCloseToEndCast;
import com.android.systemui.media.taptotransfer.sender.AlmostCloseToStartCast;
import com.android.systemui.media.taptotransfer.sender.TransferFailed;
import com.android.systemui.media.taptotransfer.sender.TransferToReceiverSucceeded;
import com.android.systemui.media.taptotransfer.sender.TransferToReceiverTriggered;
import com.android.systemui.media.taptotransfer.sender.TransferToThisDeviceSucceeded;
import com.android.systemui.media.taptotransfer.sender.TransferToThisDeviceTriggered;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.theme.ThemeOverlayApplier;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.Pair;
import kotlin.collections.MapsKt___MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

/* compiled from: MediaTttCommandLineHelper.kt */
public final class MediaTttCommandLineHelper {
    public final Context context;
    public final Executor mainExecutor;
    public final Map<String, Integer> stateStringToStateInt;

    /* compiled from: MediaTttCommandLineHelper.kt */
    public final class ReceiverCommand implements Command {
        public ReceiverCommand() {
        }

        public final void execute(PrintWriter printWriter, List<String> list) {
            Object systemService = MediaTttCommandLineHelper.this.context.getSystemService("statusbar");
            Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.app.StatusBarManager");
            StatusBarManager statusBarManager = (StatusBarManager) systemService;
            MediaRoute2Info build = new MediaRoute2Info.Builder("id", "Test Name").addFeature("feature").setPackageName(ThemeOverlayApplier.SYSUI_PACKAGE).build();
            String str = list.get(0);
            if (Intrinsics.areEqual(str, "CloseToSender")) {
                statusBarManager.updateMediaTapToTransferReceiverDisplay(0, build, (Icon) null, (CharSequence) null);
            } else if (Intrinsics.areEqual(str, "FarFromSender")) {
                statusBarManager.updateMediaTapToTransferReceiverDisplay(1, build, (Icon) null, (CharSequence) null);
            } else {
                printWriter.println(Intrinsics.stringPlus("Invalid command name ", str));
            }
        }
    }

    /* compiled from: MediaTttCommandLineHelper.kt */
    public final class SenderCommand implements Command {
        public SenderCommand() {
        }

        public final void execute(PrintWriter printWriter, List<String> list) {
            boolean z;
            Executor executor;
            boolean z2 = false;
            MediaRoute2Info build = new MediaRoute2Info.Builder("id", list.get(0)).addFeature("feature").setPackageName(ThemeOverlayApplier.SYSUI_PACKAGE).build();
            String str = list.get(1);
            Integer num = MediaTttCommandLineHelper.this.stateStringToStateInt.get(str);
            if (num == null) {
                printWriter.println(Intrinsics.stringPlus("Invalid command name ", str));
                return;
            }
            Object systemService = MediaTttCommandLineHelper.this.context.getSystemService("statusbar");
            Objects.requireNonNull(systemService, "null cannot be cast to non-null type android.app.StatusBarManager");
            StatusBarManager statusBarManager = (StatusBarManager) systemService;
            int intValue = num.intValue();
            int intValue2 = num.intValue();
            if (intValue2 == 4 || intValue2 == 5) {
                z = true;
            } else {
                z = false;
            }
            MediaTttCommandLineHelper$SenderCommand$getUndoCallback$1 mediaTttCommandLineHelper$SenderCommand$getUndoCallback$1 = null;
            if (z) {
                executor = MediaTttCommandLineHelper.this.mainExecutor;
            } else {
                executor = null;
            }
            int intValue3 = num.intValue();
            if (intValue3 == 4 || intValue3 == 5) {
                z2 = true;
            }
            if (z2) {
                mediaTttCommandLineHelper$SenderCommand$getUndoCallback$1 = new MediaTttCommandLineHelper$SenderCommand$getUndoCallback$1(intValue3);
            }
            statusBarManager.updateMediaTapToTransferSenderDisplay(intValue, build, executor, mediaTttCommandLineHelper$SenderCommand$getUndoCallback$1);
        }
    }

    public MediaTttCommandLineHelper(CommandRegistry commandRegistry, Context context2, Executor executor) {
        this.context = context2;
        this.mainExecutor = executor;
        String simpleName = Reflection.getOrCreateKotlinClass(AlmostCloseToStartCast.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName);
        String simpleName2 = Reflection.getOrCreateKotlinClass(AlmostCloseToEndCast.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName2);
        String simpleName3 = Reflection.getOrCreateKotlinClass(TransferToReceiverTriggered.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName3);
        String simpleName4 = Reflection.getOrCreateKotlinClass(TransferToThisDeviceTriggered.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName4);
        String simpleName5 = Reflection.getOrCreateKotlinClass(TransferToReceiverSucceeded.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName5);
        String simpleName6 = Reflection.getOrCreateKotlinClass(TransferToThisDeviceSucceeded.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName6);
        String simpleName7 = Reflection.getOrCreateKotlinClass(TransferFailed.class).getSimpleName();
        Intrinsics.checkNotNull(simpleName7);
        this.stateStringToStateInt = MapsKt___MapsKt.mapOf(new Pair(simpleName, 0), new Pair(simpleName2, 1), new Pair(simpleName3, 2), new Pair(simpleName4, 3), new Pair(simpleName5, 4), new Pair(simpleName6, 5), new Pair(simpleName7, 6), new Pair("FarFromReceiver", 8));
        commandRegistry.registerCommand("media-ttt-chip-sender", new Function0<Command>(this) {
            public final /* synthetic */ MediaTttCommandLineHelper this$0;

            {
                this.this$0 = r1;
            }

            public final Object invoke() {
                return new SenderCommand();
            }
        });
        commandRegistry.registerCommand("media-ttt-chip-receiver", new Function0<Command>(this) {
            public final /* synthetic */ MediaTttCommandLineHelper this$0;

            {
                this.this$0 = r1;
            }

            public final Object invoke() {
                return new ReceiverCommand();
            }
        });
    }
}
