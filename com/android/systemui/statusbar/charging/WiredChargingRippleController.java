package com.android.systemui.statusbar.charging;

import android.content.Context;
import android.os.SystemProperties;
import android.util.AttributeSet;
import android.view.WindowManager;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.UiEventLogger;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.Utils;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.flags.Flags;
import com.android.systemui.statusbar.commandline.Command;
import com.android.systemui.statusbar.commandline.CommandRegistry;
import com.android.systemui.statusbar.policy.BatteryController;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.time.SystemClock;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.functions.Function0;

/* compiled from: WiredChargingRippleController.kt */
public final class WiredChargingRippleController {
    public final BatteryController batteryController;
    public final ConfigurationController configurationController;
    public final Context context;
    public int debounceLevel;
    public Long lastTriggerTime;
    public float normalizedPortPosX;
    public float normalizedPortPosY;
    public Boolean pluggedIn;
    public ChargingRippleView rippleView;
    public final SystemClock systemClock;
    public final UiEventLogger uiEventLogger;
    public final WindowManager.LayoutParams windowLayoutParams;
    public final WindowManager windowManager;

    /* compiled from: WiredChargingRippleController.kt */
    public final class ChargingRippleCommand implements Command {
        public ChargingRippleCommand() {
        }

        public final void execute(PrintWriter printWriter, List<String> list) {
            WiredChargingRippleController.this.startRipple();
        }
    }

    /* compiled from: WiredChargingRippleController.kt */
    public enum WiredChargingRippleEvent implements UiEventLogger.UiEventEnum {
        ;
        
        private final int _id;

        /* access modifiers changed from: public */
        WiredChargingRippleEvent() {
            this._id = 829;
        }

        public final int getId() {
            return this._id;
        }
    }

    @VisibleForTesting
    public static /* synthetic */ void getRippleView$annotations() {
    }

    public final void startRipple() {
        ChargingRippleView chargingRippleView = this.rippleView;
        Objects.requireNonNull(chargingRippleView);
        if (!chargingRippleView.rippleInProgress && this.rippleView.getParent() == null) {
            this.windowLayoutParams.packageName = this.context.getOpPackageName();
            this.rippleView.addOnAttachStateChangeListener(new WiredChargingRippleController$startRipple$1(this));
            this.windowManager.addView(this.rippleView, this.windowLayoutParams);
            this.uiEventLogger.log(WiredChargingRippleEvent.CHARGING_RIPPLE_PLAYED);
        }
    }

    public final void updateRippleColor() {
        ChargingRippleView chargingRippleView = this.rippleView;
        int defaultColor = Utils.getColorAttr(this.context, 16843829).getDefaultColor();
        Objects.requireNonNull(chargingRippleView);
        chargingRippleView.rippleShader.setColor(defaultColor);
    }

    public WiredChargingRippleController(CommandRegistry commandRegistry, BatteryController batteryController2, ConfigurationController configurationController2, FeatureFlags featureFlags, Context context2, WindowManager windowManager2, SystemClock systemClock2, UiEventLogger uiEventLogger2) {
        this.batteryController = batteryController2;
        this.configurationController = configurationController2;
        this.context = context2;
        this.windowManager = windowManager2;
        this.systemClock = systemClock2;
        this.uiEventLogger = uiEventLogger2;
        if (featureFlags.isEnabled(Flags.CHARGING_RIPPLE)) {
            boolean z = SystemProperties.getBoolean("persist.debug.suppress-charging-ripple", false);
        }
        this.normalizedPortPosX = context2.getResources().getFloat(C1777R.dimen.physical_charger_port_location_normalized_x);
        this.normalizedPortPosY = context2.getResources().getFloat(C1777R.dimen.physical_charger_port_location_normalized_y);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.layoutInDisplayCutoutMode = 3;
        layoutParams.format = -3;
        layoutParams.type = 2006;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("Wired Charging Animation");
        layoutParams.flags = 24;
        layoutParams.setTrustedOverlay();
        this.windowLayoutParams = layoutParams;
        this.rippleView = new ChargingRippleView(context2, (AttributeSet) null);
        this.pluggedIn = Boolean.valueOf(batteryController2.isPluggedIn());
        commandRegistry.registerCommand("charging-ripple", new Function0<Command>(this) {
            public final /* synthetic */ WiredChargingRippleController this$0;

            {
                this.this$0 = r1;
            }

            public final Object invoke() {
                return new ChargingRippleCommand();
            }
        });
        updateRippleColor();
    }
}
