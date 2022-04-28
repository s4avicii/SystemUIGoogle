package com.android.systemui.controls.management;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.service.controls.Control;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.p004ui.RenderInfo;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.util.LifecycleActivity;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: ControlsRequestDialog.kt */
public class ControlsRequestDialog extends LifecycleActivity implements DialogInterface.OnClickListener, DialogInterface.OnCancelListener {
    public final ControlsRequestDialog$callback$1 callback = new ControlsRequestDialog$callback$1();
    public Control control;
    public ComponentName controlComponent;
    public final ControlsController controller;
    public final ControlsListingController controlsListingController;
    public final ControlsRequestDialog$currentUserTracker$1 currentUserTracker;
    public AlertDialog dialog;

    public final void onClick(DialogInterface dialogInterface, int i) {
        if (i == -1) {
            ControlsController controlsController = this.controller;
            ComponentName componentName = this.controlComponent;
            Control control2 = null;
            if (componentName == null) {
                componentName = null;
            }
            Control control3 = this.control;
            if (control3 == null) {
                control3 = null;
            }
            CharSequence structure = control3.getStructure();
            if (structure == null) {
                structure = "";
            }
            Control control4 = this.control;
            if (control4 == null) {
                control4 = null;
            }
            String controlId = control4.getControlId();
            Control control5 = this.control;
            if (control5 == null) {
                control5 = null;
            }
            CharSequence title = control5.getTitle();
            Control control6 = this.control;
            if (control6 == null) {
                control6 = null;
            }
            CharSequence subtitle = control6.getSubtitle();
            Control control7 = this.control;
            if (control7 != null) {
                control2 = control7;
            }
            controlsController.addFavorite(componentName, structure, new ControlInfo(controlId, title, subtitle, control2.getDeviceType()));
        }
        finish();
    }

    public final void onDestroy() {
        AlertDialog alertDialog = this.dialog;
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        this.currentUserTracker.stopTracking();
        this.controlsListingController.removeCallback(this.callback);
        super.onDestroy();
    }

    public ControlsRequestDialog(ControlsController controlsController, BroadcastDispatcher broadcastDispatcher, ControlsListingController controlsListingController2) {
        this.controller = controlsController;
        this.controlsListingController = controlsListingController2;
        this.currentUserTracker = new ControlsRequestDialog$currentUserTracker$1(this, broadcastDispatcher);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.currentUserTracker.startTracking();
        this.controlsListingController.addCallback(this.callback);
        int intExtra = getIntent().getIntExtra("android.intent.extra.USER_ID", -10000);
        int currentUserId = this.controller.getCurrentUserId();
        if (intExtra != currentUserId) {
            Log.w("ControlsRequestDialog", "Current user (" + currentUserId + ") different from request user (" + intExtra + ')');
            finish();
        }
        ComponentName componentName = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        if (componentName == null) {
            Log.e("ControlsRequestDialog", "Request did not contain componentName");
            finish();
            return;
        }
        this.controlComponent = componentName;
        Control parcelableExtra = getIntent().getParcelableExtra("android.service.controls.extra.CONTROL");
        if (parcelableExtra == null) {
            Log.e("ControlsRequestDialog", "Request did not contain control");
            finish();
            return;
        }
        this.control = parcelableExtra;
    }

    public final void onResume() {
        boolean z;
        boolean z2;
        super.onResume();
        ControlsListingController controlsListingController2 = this.controlsListingController;
        ComponentName componentName = this.controlComponent;
        ComponentName componentName2 = null;
        if (componentName == null) {
            componentName = null;
        }
        CharSequence appLabel = controlsListingController2.getAppLabel(componentName);
        if (appLabel == null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("The component specified (");
            ComponentName componentName3 = this.controlComponent;
            if (componentName3 != null) {
                componentName2 = componentName3;
            }
            m.append(componentName2.flattenToString());
            m.append(" is not a valid ControlsProviderService");
            Log.e("ControlsRequestDialog", m.toString());
            finish();
            return;
        }
        ControlsController controlsController = this.controller;
        ComponentName componentName4 = this.controlComponent;
        if (componentName4 == null) {
            componentName4 = null;
        }
        List<StructureInfo> favoritesForComponent = controlsController.getFavoritesForComponent(componentName4);
        if (!(favoritesForComponent instanceof Collection) || !favoritesForComponent.isEmpty()) {
            Iterator<T> it = favoritesForComponent.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                StructureInfo structureInfo = (StructureInfo) it.next();
                Objects.requireNonNull(structureInfo);
                List<ControlInfo> list = structureInfo.controls;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it2 = list.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            break;
                        }
                        ControlInfo controlInfo = (ControlInfo) it2.next();
                        Objects.requireNonNull(controlInfo);
                        String str = controlInfo.controlId;
                        Control control2 = this.control;
                        if (control2 == null) {
                            control2 = null;
                        }
                        if (Intrinsics.areEqual(str, control2.getControlId())) {
                            z2 = true;
                            continue;
                            break;
                        }
                    }
                }
                z2 = false;
                continue;
                if (z2) {
                    z = true;
                    break;
                }
            }
        }
        z = false;
        if (z) {
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m("The control ");
            Control control3 = this.control;
            if (control3 == null) {
                control3 = null;
            }
            m2.append(control3.getTitle());
            m2.append(" is already a favorite");
            Log.w("ControlsRequestDialog", m2.toString());
            finish();
        }
        SparseArray<Drawable> sparseArray = RenderInfo.iconMap;
        ComponentName componentName5 = this.controlComponent;
        if (componentName5 == null) {
            componentName5 = null;
        }
        Control control4 = this.control;
        if (control4 == null) {
            control4 = null;
        }
        RenderInfo lookup = RenderInfo.Companion.lookup(this, componentName5, control4.getDeviceType(), 0);
        View inflate = LayoutInflater.from(this).inflate(C1777R.layout.controls_dialog, (ViewGroup) null);
        ImageView imageView = (ImageView) inflate.requireViewById(C1777R.C1779id.icon);
        imageView.setImageDrawable(lookup.icon);
        imageView.setImageTintList(imageView.getContext().getResources().getColorStateList(lookup.foreground, imageView.getContext().getTheme()));
        TextView textView = (TextView) inflate.requireViewById(C1777R.C1779id.title);
        Control control5 = this.control;
        if (control5 == null) {
            control5 = null;
        }
        textView.setText(control5.getTitle());
        TextView textView2 = (TextView) inflate.requireViewById(C1777R.C1779id.subtitle);
        ComponentName componentName6 = this.control;
        if (componentName6 != null) {
            componentName2 = componentName6;
        }
        textView2.setText(componentName2.getSubtitle());
        inflate.requireViewById(C1777R.C1779id.control).setElevation(inflate.getResources().getFloat(C1777R.dimen.control_card_elevation));
        AlertDialog create = new AlertDialog.Builder(this).setTitle(getString(C1777R.string.controls_dialog_title)).setMessage(getString(C1777R.string.controls_dialog_message, new Object[]{appLabel})).setPositiveButton(C1777R.string.controls_dialog_ok, this).setNegativeButton(17039360, this).setOnCancelListener(this).setView(inflate).create();
        SystemUIDialog.registerDismissListener(create);
        create.setCanceledOnTouchOutside(true);
        this.dialog = create;
        create.show();
    }

    public final void onCancel(DialogInterface dialogInterface) {
        finish();
    }
}
