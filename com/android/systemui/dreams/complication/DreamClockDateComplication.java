package com.android.systemui.dreams.complication;

import android.content.Context;
import android.view.View;
import com.android.systemui.CoreStartable;
import com.android.systemui.dreams.DreamOverlayStateController;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.dagger.DreamClockDateComplicationComponent$Factory;
import com.google.android.systemui.titan.DaggerTitanGlobalRootComponent;
import java.util.Objects;

public final class DreamClockDateComplication implements Complication {
    public DreamClockDateComplicationComponent$Factory mComponentFactory;

    public static class Registrant extends CoreStartable {
        public final DreamClockDateComplication mComplication;
        public final DreamOverlayStateController mDreamOverlayStateController;

        public final void start() {
            this.mDreamOverlayStateController.addComplication(this.mComplication);
        }

        public Registrant(Context context, DreamOverlayStateController dreamOverlayStateController, DreamClockDateComplication dreamClockDateComplication) {
            super(context);
            this.mDreamOverlayStateController = dreamOverlayStateController;
            this.mComplication = dreamClockDateComplication;
        }
    }

    public final int getRequiredTypeAvailability() {
        return 2;
    }

    public static class DreamClockDateViewHolder implements Complication.ViewHolder {
        public final ComplicationLayoutParams mLayoutParams;
        public final View mView;

        public DreamClockDateViewHolder(View view, ComplicationLayoutParams complicationLayoutParams) {
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
        DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.DreamClockDateComplicationComponentImpl create = this.mComponentFactory.create();
        Objects.requireNonNull(create);
        return new DreamClockDateViewHolder(create.provideComplicationViewProvider.get(), create.provideLayoutParamsProvider.get());
    }

    public DreamClockDateComplication(DreamClockDateComplicationComponent$Factory dreamClockDateComplicationComponent$Factory) {
        this.mComponentFactory = dreamClockDateComplicationComponent$Factory;
    }
}
