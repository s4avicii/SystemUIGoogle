package com.android.systemui.p006qs.external;

import android.content.ComponentName;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.logging.InstanceId;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.p006qs.QSTileHost;
import com.android.systemui.p006qs.tileimpl.QSIconViewImpl;
import com.android.systemui.p006qs.tileimpl.QSTileImpl;
import com.android.systemui.p006qs.tileimpl.QSTileViewImpl;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.qs.external.TileServiceRequestController */
/* compiled from: TileServiceRequestController.kt */
public final class TileServiceRequestController {
    public final CommandQueue commandQueue;
    public final TileServiceRequestController$commandQueueCallback$1 commandQueueCallback;
    public final CommandRegistry commandRegistry;
    public Function1<? super String, Unit> dialogCanceller;
    public final Function0<TileRequestDialog> dialogCreator;
    public final TileRequestDialogEventLogger eventLogger;
    public final QSTileHost qsTileHost;

    /* renamed from: com.android.systemui.qs.external.TileServiceRequestController$SingleShotConsumer */
    /* compiled from: TileServiceRequestController.kt */
    public static final class SingleShotConsumer<T> implements Consumer<T> {
        public final Consumer<T> consumer;
        public final AtomicBoolean dispatched = new AtomicBoolean(false);

        public final void accept(T t) {
            if (this.dispatched.compareAndSet(false, true)) {
                this.consumer.accept(t);
            }
        }

        public SingleShotConsumer(TileServiceRequestController$requestTileAdd$dialogResponse$1 tileServiceRequestController$requestTileAdd$dialogResponse$1) {
            this.consumer = tileServiceRequestController$requestTileAdd$dialogResponse$1;
        }
    }

    /* renamed from: com.android.systemui.qs.external.TileServiceRequestController$TileServiceRequestCommand */
    /* compiled from: TileServiceRequestController.kt */
    public final class TileServiceRequestCommand implements Command {
        public final void execute(PrintWriter printWriter, List<String> list) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(list.get(0));
            if (unflattenFromString == null) {
                Log.w("TileServiceRequestController", Intrinsics.stringPlus("Malformed componentName ", list.get(0)));
            } else {
                TileServiceRequestController.this.mo10516x2d391cca(unflattenFromString, list.get(1), list.get(2), (Icon) null, TileServiceRequestController$TileServiceRequestCommand$execute$1.INSTANCE);
            }
        }

        public TileServiceRequestCommand() {
        }
    }

    public TileServiceRequestController() {
        throw null;
    }

    public TileServiceRequestController(final QSTileHost qSTileHost, CommandQueue commandQueue2, CommandRegistry commandRegistry2, TileRequestDialogEventLogger tileRequestDialogEventLogger) {
        C10271 r0 = new Function0<TileRequestDialog>() {
            public final Object invoke() {
                QSTileHost qSTileHost = QSTileHost.this;
                Objects.requireNonNull(qSTileHost);
                return new TileRequestDialog(qSTileHost.mContext);
            }
        };
        this.qsTileHost = qSTileHost;
        this.commandQueue = commandQueue2;
        this.commandRegistry = commandRegistry2;
        this.eventLogger = tileRequestDialogEventLogger;
        this.dialogCreator = r0;
        this.commandQueueCallback = new TileServiceRequestController$commandQueueCallback$1(this);
    }

    /* renamed from: com.android.systemui.qs.external.TileServiceRequestController$Builder */
    /* compiled from: TileServiceRequestController.kt */
    public static final class Builder {
        public final CommandQueue commandQueue;
        public final CommandRegistry commandRegistry;

        public Builder(CommandQueue commandQueue2, CommandRegistry commandRegistry2) {
            this.commandQueue = commandQueue2;
            this.commandRegistry = commandRegistry2;
        }
    }

    public final void init() {
        this.commandRegistry.registerCommand("tile-service-add", new TileServiceRequestController$init$1(this));
        this.commandQueue.addCallback((CommandQueue.Callbacks) this.commandQueueCallback);
    }

    /* renamed from: requestTileAdd$frameworks__base__packages__SystemUI__android_common__SystemUI_core */
    public final void mo10516x2d391cca(ComponentName componentName, CharSequence charSequence, CharSequence charSequence2, Icon icon, Consumer<Integer> consumer) {
        boolean z;
        Drawable loadDrawable;
        Icon icon2 = icon;
        TileRequestDialogEventLogger tileRequestDialogEventLogger = this.eventLogger;
        Objects.requireNonNull(tileRequestDialogEventLogger);
        InstanceId newInstanceId = tileRequestDialogEventLogger.instanceIdSequence.newInstanceId();
        String packageName = componentName.getPackageName();
        if (this.qsTileHost.indexOf(CustomTile.toSpec(componentName)) != -1) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            consumer.accept(1);
            TileRequestDialogEventLogger tileRequestDialogEventLogger2 = this.eventLogger;
            Objects.requireNonNull(tileRequestDialogEventLogger2);
            tileRequestDialogEventLogger2.uiEventLogger.logWithInstanceId(TileRequestDialogEvent.TILE_REQUEST_DIALOG_TILE_ALREADY_ADDED, 0, packageName, newInstanceId);
            return;
        }
        SingleShotConsumer singleShotConsumer = new SingleShotConsumer(new TileServiceRequestController$requestTileAdd$dialogResponse$1(this, componentName, packageName, newInstanceId, consumer));
        TileServiceRequestController$createDialog$dialogClickListener$1 tileServiceRequestController$createDialog$dialogClickListener$1 = new TileServiceRequestController$createDialog$dialogClickListener$1(singleShotConsumer);
        TileRequestDialog invoke = this.dialogCreator.invoke();
        TileRequestDialog tileRequestDialog = invoke;
        Objects.requireNonNull(tileRequestDialog);
        QSTile.Icon icon3 = null;
        View inflate = LayoutInflater.from(tileRequestDialog.getContext()).inflate(C1777R.layout.tile_service_request_dialog, (ViewGroup) null);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) inflate;
        TextView textView = (TextView) viewGroup.requireViewById(C1777R.C1779id.text);
        textView.setText(textView.getContext().getString(C1777R.string.qs_tile_request_dialog_text, new Object[]{charSequence}));
        QSTileViewImpl qSTileViewImpl = new QSTileViewImpl(tileRequestDialog.getContext(), new QSIconViewImpl(tileRequestDialog.getContext()), true);
        QSTile.BooleanState booleanState = new QSTile.BooleanState();
        booleanState.label = charSequence2;
        booleanState.handlesLongClick = false;
        if (!(icon2 == null || (loadDrawable = icon2.loadDrawable(tileRequestDialog.getContext())) == null)) {
            icon3 = new QSTileImpl.DrawableIcon(loadDrawable);
        }
        if (icon3 == null) {
            icon3 = QSTileImpl.ResourceIcon.get(C1777R.C1778drawable.f173android);
        }
        booleanState.icon = icon3;
        qSTileViewImpl.onStateChanged(booleanState);
        qSTileViewImpl.post(new TileRequestDialog$createTileView$1(qSTileViewImpl));
        viewGroup.addView(qSTileViewImpl, viewGroup.getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_tile_service_request_tile_width), viewGroup.getContext().getResources().getDimensionPixelSize(C1777R.dimen.qs_quick_tile_size));
        viewGroup.setSelected(true);
        tileRequestDialog.setView(viewGroup, 0, 0, 0, 0);
        SystemUIDialog.setShowForAllUsers(tileRequestDialog);
        tileRequestDialog.setCanceledOnTouchOutside(true);
        tileRequestDialog.setOnCancelListener(new TileServiceRequestController$createDialog$1$1(singleShotConsumer));
        tileRequestDialog.setOnDismissListener(new TileServiceRequestController$createDialog$1$2(singleShotConsumer));
        tileRequestDialog.setPositiveButton(C1777R.string.qs_tile_request_dialog_add, tileServiceRequestController$createDialog$dialogClickListener$1);
        tileRequestDialog.setNegativeButton(C1777R.string.qs_tile_request_dialog_not_add, tileServiceRequestController$createDialog$dialogClickListener$1);
        SystemUIDialog systemUIDialog = invoke;
        this.dialogCanceller = new TileServiceRequestController$requestTileAdd$1$1(packageName, systemUIDialog, this);
        systemUIDialog.show();
        TileRequestDialogEventLogger tileRequestDialogEventLogger3 = this.eventLogger;
        Objects.requireNonNull(tileRequestDialogEventLogger3);
        tileRequestDialogEventLogger3.uiEventLogger.logWithInstanceId(TileRequestDialogEvent.TILE_REQUEST_DIALOG_SHOWN, 0, packageName, newInstanceId);
    }
}
