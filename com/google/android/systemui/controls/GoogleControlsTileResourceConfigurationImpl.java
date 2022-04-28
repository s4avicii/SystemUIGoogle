package com.google.android.systemui.controls;

import com.android.p012wm.shell.C1777R;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.ControlsTileResourceConfiguration;
import com.android.systemui.controls.controller.StructureInfo;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: GoogleControlsTileResourceConfigurationImpl.kt */
public final class GoogleControlsTileResourceConfigurationImpl implements ControlsTileResourceConfiguration {
    public final ControlsController controlsController;

    public final int getTileImageId() {
        StructureInfo preferredStructure = this.controlsController.getPreferredStructure();
        Objects.requireNonNull(preferredStructure);
        if (Intrinsics.areEqual(preferredStructure.componentName.getPackageName(), "com.google.android.apps.chromecast.app")) {
            return C1777R.C1778drawable.home_controls_icon;
        }
        return C1777R.C1778drawable.controls_icon;
    }

    public final int getTileTitleId() {
        StructureInfo preferredStructure = this.controlsController.getPreferredStructure();
        Objects.requireNonNull(preferredStructure);
        if (Intrinsics.areEqual(preferredStructure.componentName.getPackageName(), "com.google.android.apps.chromecast.app")) {
            return C1777R.string.home_controls_tile_title;
        }
        return C1777R.string.quick_controls_title;
    }

    public GoogleControlsTileResourceConfigurationImpl(ControlsController controlsController2) {
        this.controlsController = controlsController2;
    }
}
