package com.android.systemui.dreams.complication;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.dagger.DreamWeatherComplicationComponent$Factory;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.statusbar.lockscreen.LockscreenSmartspaceController;
import com.android.systemui.util.ViewController;
import com.google.android.systemui.titan.DaggerTitanGlobalRootComponent;
import java.util.Objects;

public final class DreamWeatherComplication implements Complication {
    public DreamWeatherComplicationComponent$Factory mComponentFactory;

    public static class DreamWeatherViewController extends ViewController<TextView> {
        public final LockscreenSmartspaceController mSmartSpaceController;
        public C0791xbf1b0f76 mSmartspaceTargetListener;

        public final void onViewAttached() {
            C0791xbf1b0f76 dreamWeatherComplication$DreamWeatherViewController$$ExternalSyntheticLambda0 = new C0791xbf1b0f76(this);
            this.mSmartspaceTargetListener = dreamWeatherComplication$DreamWeatherViewController$$ExternalSyntheticLambda0;
            LockscreenSmartspaceController lockscreenSmartspaceController = this.mSmartSpaceController;
            Objects.requireNonNull(lockscreenSmartspaceController);
            lockscreenSmartspaceController.execution.assertIsMainThread();
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin = lockscreenSmartspaceController.plugin;
            if (bcSmartspaceDataPlugin != null) {
                bcSmartspaceDataPlugin.registerListener(dreamWeatherComplication$DreamWeatherViewController$$ExternalSyntheticLambda0);
            }
        }

        public final void onViewDetached() {
            LockscreenSmartspaceController lockscreenSmartspaceController = this.mSmartSpaceController;
            C0791xbf1b0f76 dreamWeatherComplication$DreamWeatherViewController$$ExternalSyntheticLambda0 = this.mSmartspaceTargetListener;
            Objects.requireNonNull(lockscreenSmartspaceController);
            lockscreenSmartspaceController.execution.assertIsMainThread();
            BcSmartspaceDataPlugin bcSmartspaceDataPlugin = lockscreenSmartspaceController.plugin;
            if (bcSmartspaceDataPlugin != null) {
                bcSmartspaceDataPlugin.unregisterListener(dreamWeatherComplication$DreamWeatherViewController$$ExternalSyntheticLambda0);
            }
        }

        public DreamWeatherViewController(TextView textView, LockscreenSmartspaceController lockscreenSmartspaceController) {
            super(textView);
            this.mSmartSpaceController = lockscreenSmartspaceController;
        }
    }

    public static class Registrant extends CoreStartable {
        public final DreamWeatherComplication mComplication;
        public final DreamOverlayStateController mDreamOverlayStateController;
        public final LockscreenSmartspaceController mSmartSpaceController;

        public final void start() {
            if (this.mSmartSpaceController.isEnabled()) {
                this.mDreamOverlayStateController.addComplication(this.mComplication);
            }
        }

        public Registrant(Context context, LockscreenSmartspaceController lockscreenSmartspaceController, DreamOverlayStateController dreamOverlayStateController, DreamWeatherComplication dreamWeatherComplication) {
            super(context);
            this.mSmartSpaceController = lockscreenSmartspaceController;
            this.mDreamOverlayStateController = dreamOverlayStateController;
            this.mComplication = dreamWeatherComplication;
        }
    }

    public final int getRequiredTypeAvailability() {
        return 4;
    }

    public static class DreamWeatherViewHolder implements Complication.ViewHolder {
        public final ComplicationLayoutParams mLayoutParams;
        public final TextView mView;

        public DreamWeatherViewHolder(TextView textView, DreamWeatherViewController dreamWeatherViewController, ComplicationLayoutParams complicationLayoutParams) {
            this.mView = textView;
            this.mLayoutParams = complicationLayoutParams;
            dreamWeatherViewController.init();
        }

        public final ComplicationLayoutParams getLayoutParams() {
            return this.mLayoutParams;
        }

        public final View getView() {
            return this.mView;
        }
    }

    public final Complication.ViewHolder createView(ComplicationViewModel complicationViewModel) {
        DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.DreamWeatherComplicationComponentImpl create = this.mComponentFactory.create();
        Objects.requireNonNull(create);
        return new DreamWeatherViewHolder(create.provideComplicationViewProvider.get(), new DreamWeatherViewController(create.provideComplicationViewProvider.get(), DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.lockscreenSmartspaceControllerProvider.get()), create.provideLayoutParamsProvider.get());
    }

    public DreamWeatherComplication(DreamWeatherComplicationComponent$Factory dreamWeatherComplicationComponent$Factory) {
        this.mComponentFactory = dreamWeatherComplicationComponent$Factory;
    }
}
