package com.google.android.systemui.communal.dreams;

import android.content.Intent;
import android.view.View;
import com.android.systemui.dreams.complication.Complication;
import com.android.systemui.dreams.complication.ComplicationLayoutParams;
import com.android.systemui.dreams.complication.ComplicationViewModel;
import com.android.systemui.plugins.ActivityStarter;
import com.google.android.systemui.communal.dreams.dagger.SetupDreamComponent$Factory;
import com.google.android.systemui.titan.DaggerTitanGlobalRootComponent;
import java.util.Objects;

public final class SetupDreamComplication implements Complication {
    public final SetupDreamComponent$Factory mComponentFactory;

    public static class SetupDreamViewHolder implements Complication.ViewHolder {
        public final ComplicationLayoutParams mComplicationLayoutParams;
        public final View mView;

        public SetupDreamViewHolder(View view, ComplicationLayoutParams complicationLayoutParams, ComplicationViewModel complicationViewModel, Intent intent, ActivityStarter activityStarter) {
            this.mView = view;
            this.mComplicationLayoutParams = complicationLayoutParams;
            view.setOnClickListener(new C2207x5426cca6(complicationViewModel, activityStarter, intent));
        }

        public final ComplicationLayoutParams getLayoutParams() {
            return this.mComplicationLayoutParams;
        }

        public final View getView() {
            return this.mView;
        }
    }

    public final Complication.ViewHolder createView(ComplicationViewModel complicationViewModel) {
        DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.SetupDreamComponentImpl create = this.mComponentFactory.create(complicationViewModel);
        Objects.requireNonNull(create);
        return new SetupDreamViewHolder(DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.namedView(), new ComplicationLayoutParams(9, 2, 0), create.model, new Intent("android.settings.DREAM_SETTINGS"), DaggerTitanGlobalRootComponent.TitanSysUIComponentImpl.this.activityStarter());
    }

    public SetupDreamComplication(SetupDreamComponent$Factory setupDreamComponent$Factory) {
        this.mComponentFactory = setupDreamComponent$Factory;
    }
}
