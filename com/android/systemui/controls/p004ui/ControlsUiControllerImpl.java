package com.android.systemui.controls.p004ui;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.service.controls.Control;
import android.util.Log;
import android.util.Pair;
import android.view.ContextThemeWrapper;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ListPopupWindow;
import com.android.systemui.controls.ControlsMetricsLogger;
import com.android.systemui.controls.CustomIconCache;
import com.android.systemui.controls.controller.ControlInfo;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.controller.StructureInfo;
import com.android.systemui.controls.management.ControlsListingController;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.phone.ShadeController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.util.concurrency.DelayableExecutor;
import dagger.Lazy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.jvm.internal.Intrinsics;

/* renamed from: com.android.systemui.controls.ui.ControlsUiControllerImpl */
/* compiled from: ControlsUiControllerImpl.kt */
public final class ControlsUiControllerImpl implements ControlsUiController {
    public static final ComponentName EMPTY_COMPONENT;
    public static final StructureInfo EMPTY_STRUCTURE;
    public Context activityContext;
    public final ActivityStarter activityStarter;
    public List<StructureInfo> allStructures;
    public final DelayableExecutor bgExecutor;
    public final Context context;
    public final ControlActionCoordinator controlActionCoordinator;
    public final LinkedHashMap controlViewsById = new LinkedHashMap();
    public final LinkedHashMap controlsById = new LinkedHashMap();
    public final Lazy<ControlsController> controlsController;
    public final Lazy<ControlsListingController> controlsListingController;
    public final ControlsMetricsLogger controlsMetricsLogger;
    public boolean hidden = true;
    public final CustomIconCache iconCache;
    public final KeyguardStateController keyguardStateController;
    public ControlsUiControllerImpl$createCallback$1 listingCallback;
    public final ControlsUiControllerImpl$special$$inlined$compareBy$1 localeComparator;
    public Runnable onDismiss;
    public final ControlsUiControllerImpl$onSeedingComplete$1 onSeedingComplete;
    public ViewGroup parent;
    public ListPopupWindow popup;
    public final ContextThemeWrapper popupThemedContext;
    public boolean retainCache;
    public StructureInfo selectedStructure = EMPTY_STRUCTURE;
    public final ShadeController shadeController;
    public final SharedPreferences sharedPreferences;
    public final DelayableExecutor uiExecutor;

    public final void hide() {
        this.hidden = true;
        ListPopupWindow listPopupWindow = this.popup;
        if (listPopupWindow != null) {
            listPopupWindow.dismissImmediate();
        }
        ControlsUiControllerImpl$createCallback$1 controlsUiControllerImpl$createCallback$1 = null;
        this.popup = null;
        for (Map.Entry value : this.controlViewsById.entrySet()) {
            ControlViewHolder controlViewHolder = (ControlViewHolder) value.getValue();
            Objects.requireNonNull(controlViewHolder);
            Dialog dialog = controlViewHolder.lastChallengeDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
            controlViewHolder.lastChallengeDialog = null;
            Dialog dialog2 = controlViewHolder.visibleDialog;
            if (dialog2 != null) {
                dialog2.dismiss();
            }
            controlViewHolder.visibleDialog = null;
        }
        this.controlActionCoordinator.closeDialogs();
        this.controlsController.get().unsubscribe();
        ViewGroup viewGroup = this.parent;
        if (viewGroup == null) {
            viewGroup = null;
        }
        viewGroup.removeAllViews();
        this.controlsById.clear();
        this.controlViewsById.clear();
        ControlsListingController controlsListingController2 = this.controlsListingController.get();
        ControlsUiControllerImpl$createCallback$1 controlsUiControllerImpl$createCallback$12 = this.listingCallback;
        if (controlsUiControllerImpl$createCallback$12 != null) {
            controlsUiControllerImpl$createCallback$1 = controlsUiControllerImpl$createCallback$12;
        }
        controlsListingController2.removeCallback(controlsUiControllerImpl$createCallback$1);
        if (!this.retainCache) {
            RenderInfo.iconMap.clear();
            RenderInfo.appIconMap.clear();
        }
    }

    static {
        ComponentName componentName = new ComponentName("", "");
        EMPTY_COMPONENT = componentName;
        EMPTY_STRUCTURE = new StructureInfo(componentName, "", new ArrayList());
    }

    public final void onActionResponse(ComponentName componentName, String str, int i) {
        this.uiExecutor.execute(new ControlsUiControllerImpl$onActionResponse$1(this, new ControlKey(componentName, str), i));
    }

    public final void onRefreshState(ComponentName componentName, List<Control> list) {
        boolean z = !this.keyguardStateController.isUnlocked();
        for (Control control : list) {
            ControlWithState controlWithState = (ControlWithState) this.controlsById.get(new ControlKey(componentName, control.getControlId()));
            if (controlWithState != null) {
                Log.d("ControlsUiController", Intrinsics.stringPlus("onRefreshState() for id: ", control.getControlId()));
                CustomIconCache customIconCache = this.iconCache;
                String controlId = control.getControlId();
                Icon customIcon = control.getCustomIcon();
                Objects.requireNonNull(customIconCache);
                if (!Intrinsics.areEqual(componentName, customIconCache.currentComponent)) {
                    synchronized (customIconCache.cache) {
                        customIconCache.cache.clear();
                    }
                    customIconCache.currentComponent = componentName;
                }
                synchronized (customIconCache.cache) {
                    if (customIcon != null) {
                        Icon icon = (Icon) customIconCache.cache.put(controlId, customIcon);
                    } else {
                        Icon icon2 = (Icon) customIconCache.cache.remove(controlId);
                    }
                }
                ControlWithState controlWithState2 = new ControlWithState(componentName, controlWithState.f48ci, control);
                ControlKey controlKey = new ControlKey(componentName, control.getControlId());
                this.controlsById.put(controlKey, controlWithState2);
                ControlViewHolder controlViewHolder = (ControlViewHolder) this.controlViewsById.get(controlKey);
                if (controlViewHolder != null) {
                    this.uiExecutor.execute(new ControlsUiControllerImpl$onRefreshState$1$1$1$1(controlViewHolder, controlWithState2, z));
                }
            }
        }
    }

    public final void reload(ViewGroup viewGroup) {
        if (!this.hidden) {
            ControlsListingController controlsListingController2 = this.controlsListingController.get();
            ControlsUiControllerImpl$createCallback$1 controlsUiControllerImpl$createCallback$1 = this.listingCallback;
            if (controlsUiControllerImpl$createCallback$1 == null) {
                controlsUiControllerImpl$createCallback$1 = null;
            }
            controlsListingController2.removeCallback(controlsUiControllerImpl$createCallback$1);
            this.controlsController.get().unsubscribe();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(viewGroup, "alpha", new float[]{1.0f, 0.0f});
            ofFloat.setInterpolator(new AccelerateInterpolator(1.0f));
            ofFloat.setDuration(200);
            ofFloat.addListener(new ControlsUiControllerImpl$reload$1(this, viewGroup));
            ofFloat.start();
        }
    }

    public final void show(ViewGroup viewGroup, Runnable runnable, Context context2) {
        Log.d("ControlsUiController", "show()");
        this.parent = viewGroup;
        this.onDismiss = runnable;
        this.activityContext = context2;
        this.hidden = false;
        this.retainCache = false;
        this.controlActionCoordinator.setActivityContext(context2);
        ArrayList favorites = this.controlsController.get().getFavorites();
        this.allStructures = favorites;
        ControlsUiControllerImpl$createCallback$1 controlsUiControllerImpl$createCallback$1 = null;
        if (favorites == null) {
            favorites = null;
        }
        this.selectedStructure = getPreferredStructure(favorites);
        if (this.controlsController.get().addSeedingFavoritesCallback(this.onSeedingComplete)) {
            this.listingCallback = new ControlsUiControllerImpl$createCallback$1(this, new ControlsUiControllerImpl$show$1(this));
        } else {
            StructureInfo structureInfo = this.selectedStructure;
            Objects.requireNonNull(structureInfo);
            if (structureInfo.controls.isEmpty()) {
                List<StructureInfo> list = this.allStructures;
                if (list == null) {
                    list = null;
                }
                if (list.size() <= 1) {
                    this.listingCallback = new ControlsUiControllerImpl$createCallback$1(this, new ControlsUiControllerImpl$show$2(this));
                }
            }
            StructureInfo structureInfo2 = this.selectedStructure;
            Objects.requireNonNull(structureInfo2);
            List<ControlInfo> list2 = structureInfo2.controls;
            ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(list2, 10));
            for (ControlInfo controlWithState : list2) {
                StructureInfo structureInfo3 = this.selectedStructure;
                Objects.requireNonNull(structureInfo3);
                arrayList.add(new ControlWithState(structureInfo3.componentName, controlWithState, (Control) null));
            }
            LinkedHashMap linkedHashMap = this.controlsById;
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Object next = it.next();
                ControlWithState controlWithState2 = (ControlWithState) next;
                StructureInfo structureInfo4 = this.selectedStructure;
                Objects.requireNonNull(structureInfo4);
                ComponentName componentName = structureInfo4.componentName;
                Objects.requireNonNull(controlWithState2);
                ControlInfo controlInfo = controlWithState2.f48ci;
                Objects.requireNonNull(controlInfo);
                linkedHashMap.put(new ControlKey(componentName, controlInfo.controlId), next);
            }
            this.listingCallback = new ControlsUiControllerImpl$createCallback$1(this, new ControlsUiControllerImpl$show$5(this));
            this.controlsController.get().subscribeToFavorites(this.selectedStructure);
        }
        ControlsListingController controlsListingController2 = this.controlsListingController.get();
        ControlsUiControllerImpl$createCallback$1 controlsUiControllerImpl$createCallback$12 = this.listingCallback;
        if (controlsUiControllerImpl$createCallback$12 != null) {
            controlsUiControllerImpl$createCallback$1 = controlsUiControllerImpl$createCallback$12;
        }
        controlsListingController2.addCallback(controlsUiControllerImpl$createCallback$1);
    }

    public final void startActivity(Intent intent) {
        Context context2;
        intent.putExtra("extra_animate", true);
        if (this.keyguardStateController.isShowing()) {
            this.activityStarter.postStartActivityDismissingKeyguard(intent, 0);
            return;
        }
        Context context3 = this.activityContext;
        if (context3 == null) {
            context2 = null;
        } else {
            context2 = context3;
        }
        if (context3 == null) {
            context3 = null;
        }
        context2.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context3, new Pair[0]).toBundle());
    }

    public final void startTargetedActivity(StructureInfo structureInfo, Class<?> cls) {
        Context context2 = this.activityContext;
        if (context2 == null) {
            context2 = null;
        }
        Intent intent = new Intent(context2, cls);
        Objects.requireNonNull(structureInfo);
        intent.putExtra("extra_app_label", this.controlsListingController.get().getAppLabel(structureInfo.componentName));
        intent.putExtra("extra_structure", structureInfo.structure);
        intent.putExtra("android.intent.extra.COMPONENT_NAME", structureInfo.componentName);
        startActivity(intent);
        this.retainCache = true;
    }

    public final void updatePreferences(StructureInfo structureInfo) {
        if (!Intrinsics.areEqual(structureInfo, EMPTY_STRUCTURE)) {
            SharedPreferences.Editor edit = this.sharedPreferences.edit();
            Objects.requireNonNull(structureInfo);
            edit.putString("controls_component", structureInfo.componentName.flattenToString()).putString("controls_structure", structureInfo.structure.toString()).commit();
        }
    }

    public ControlsUiControllerImpl(Lazy<ControlsController> lazy, Context context2, DelayableExecutor delayableExecutor, DelayableExecutor delayableExecutor2, Lazy<ControlsListingController> lazy2, SharedPreferences sharedPreferences2, ControlActionCoordinator controlActionCoordinator2, ActivityStarter activityStarter2, ShadeController shadeController2, CustomIconCache customIconCache, ControlsMetricsLogger controlsMetricsLogger2, KeyguardStateController keyguardStateController2) {
        this.controlsController = lazy;
        this.context = context2;
        this.uiExecutor = delayableExecutor;
        this.bgExecutor = delayableExecutor2;
        this.controlsListingController = lazy2;
        this.sharedPreferences = sharedPreferences2;
        this.controlActionCoordinator = controlActionCoordinator2;
        this.activityStarter = activityStarter2;
        this.shadeController = shadeController2;
        this.iconCache = customIconCache;
        this.controlsMetricsLogger = controlsMetricsLogger2;
        this.keyguardStateController = keyguardStateController2;
        this.popupThemedContext = new ContextThemeWrapper(context2, 2132017471);
        this.localeComparator = new ControlsUiControllerImpl$special$$inlined$compareBy$1(Collator.getInstance(context2.getResources().getConfiguration().getLocales().get(0)));
        this.onSeedingComplete = new ControlsUiControllerImpl$onSeedingComplete$1(this);
    }

    public final StructureInfo getPreferredStructure(List<StructureInfo> list) {
        ComponentName componentName;
        boolean z;
        if (list.isEmpty()) {
            return EMPTY_STRUCTURE;
        }
        T t = null;
        String string = this.sharedPreferences.getString("controls_component", (String) null);
        if (string == null) {
            componentName = null;
        } else {
            componentName = ComponentName.unflattenFromString(string);
        }
        if (componentName == null) {
            componentName = EMPTY_COMPONENT;
        }
        String string2 = this.sharedPreferences.getString("controls_structure", "");
        Iterator<T> it = list.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            T next = it.next();
            StructureInfo structureInfo = (StructureInfo) next;
            Objects.requireNonNull(structureInfo);
            if (!Intrinsics.areEqual(componentName, structureInfo.componentName) || !Intrinsics.areEqual(string2, structureInfo.structure)) {
                z = false;
                continue;
            } else {
                z = true;
                continue;
            }
            if (z) {
                t = next;
                break;
            }
        }
        StructureInfo structureInfo2 = (StructureInfo) t;
        if (structureInfo2 == null) {
            return list.get(0);
        }
        return structureInfo2;
    }
}
