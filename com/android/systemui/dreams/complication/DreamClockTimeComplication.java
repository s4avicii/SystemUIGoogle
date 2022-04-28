package com.android.systemui.dreams.complication;

import android.content.Context;
import android.view.View;
import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.dagger.DreamClockTimeComplicationComponent$Factory;
import com.google.android.systemui.titan.DaggerTitanGlobalRootComponent;
import java.util.Objects;

public final class DreamClockTimeComplication implements Complication {
    public DreamClockTimeComplicationComponent$Factory mComponentFactory;

    public static class Registrant extends CoreStartable {
        public final DreamClockTimeComplication mComplication;
        public final DreamOverlayStateController mDreamOverlayStateController;

        public final void start() {
            this.mDreamOverlayStateController.addComplication(this.mComplication);
        }

        public Registrant(Context context, DreamOverlayStateController dreamOverlayStateController, DreamClockTimeComplication dreamClockTimeComplication) {
            super(context);
            this.mDreamOverlayStateController = dreamOverlayStateController;
            this.mComplication = dreamClockTimeComplication;
        }
    }

    public final int getRequiredTypeAvailability() {
        return 1;
    }

    public static class DreamClockTimeViewHolder implements Complication.ViewHolder {
        public final ComplicationLayoutParams mLayoutParams;
        public final View mView;

        public DreamClockTimeViewHolder(View view, ComplicationLayoutParams complicationLayoutParams) {
            this.mView = view;
            this.mLayoutParams = complicationLayoutParams;
        }

        public final ComplicationLayoutParams getLayoutParams() {
            return this.mLayoutParams;
        }

        public final View getView() {
            return this.mView;
        }
    }

    public final Complication.ViewHolder createView(ComplicationViewModel complicationViewModel) {
        DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.DreamClockTimeComplicationComponentImpl create = this.mComponentFactory.create();
        Objects.requireNonNull(create);
        return new DreamClockTimeViewHolder(create.provideComplicationViewProvider.get(), create.provideLayoutParamsProvider.get());
    }

    public DreamClockTimeComplication(DreamClockTimeComplicationComponent$Factory dreamClockTimeComplicationComponent$Factory) {
        this.mComponentFactory = dreamClockTimeComplicationComponent$Factory;
    }
}
