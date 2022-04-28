package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.mediarouter.R$bool;
import androidx.viewpager2.widget.CompositeOnPageChangeCallback;
import androidx.viewpager2.widget.ViewPager2;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.TooltipManager;
import com.android.systemui.controls.controller.ControlsControllerImpl;
import com.android.systemui.controls.p004ui.ControlsActivity;
import com.android.systemui.controls.p004ui.ControlsUiController;
import com.android.systemui.util.LifecycleActivity;
import java.text.Collator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import kotlin.collections.EmptyList;

/* compiled from: ControlsFavoritingActivity.kt */
public final class ControlsFavoritingActivity extends LifecycleActivity {
    public CharSequence appName;
    public Runnable cancelLoadRunnable;
    public ControlsFavoritingActivity$onCreate$$inlined$compareBy$1 comparator;
    public ComponentName component;
    public final ControlsControllerImpl controller;
    public final ControlsFavoritingActivity$controlsModelCallback$1 controlsModelCallback;
    public final ControlsFavoritingActivity$currentUserTracker$1 currentUserTracker;
    public View doneButton;
    public final Executor executor;
    public boolean fromProviderSelector;
    public boolean isPagerLoaded;
    public List<StructureContainer> listOfStructures = EmptyList.INSTANCE;
    public final ControlsFavoritingActivity$listingCallback$1 listingCallback;
    public final ControlsListingController listingController;
    public TooltipManager mTooltipManager;
    public View otherAppsButton;
    public ManagementPageIndicator pageIndicator;
    public TextView statusText;
    public CharSequence structureExtra;
    public ViewPager2 structurePager;
    public TextView subtitleView;
    public TextView titleView;
    public final ControlsUiController uiController;

    public final void onBackPressed() {
        if (!this.fromProviderSelector) {
            startActivity(new Intent(getApplicationContext(), ControlsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this, new Pair[0]).toBundle());
        }
        animateExitAndFinish();
    }

    public final void onDestroy() {
        Runnable runnable = this.cancelLoadRunnable;
        if (runnable != null) {
            runnable.run();
        }
        super.onDestroy();
    }

    public ControlsFavoritingActivity(Executor executor2, ControlsControllerImpl controlsControllerImpl, ControlsListingController controlsListingController, BroadcastDispatcher broadcastDispatcher, ControlsUiController controlsUiController) {
        this.executor = executor2;
        this.controller = controlsControllerImpl;
        this.listingController = controlsListingController;
        this.uiController = controlsUiController;
        this.currentUserTracker = new ControlsFavoritingActivity$currentUserTracker$1(this, broadcastDispatcher);
        this.listingCallback = new ControlsFavoritingActivity$listingCallback$1(this);
        this.controlsModelCallback = new ControlsFavoritingActivity$controlsModelCallback$1(this);
    }

    public final void animateExitAndFinish() {
        R$bool.exitAnimation((ViewGroup) requireViewById(C1777R.C1779id.controls_management_root), new ControlsFavoritingActivity$animateExitAndFinish$1(this)).start();
    }

    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        TooltipManager tooltipManager = this.mTooltipManager;
        if (tooltipManager != null) {
            tooltipManager.hide(false);
        }
    }

    public final void onCreate(Bundle bundle) {
        boolean z;
        super.onCreate(bundle);
        this.comparator = new ControlsFavoritingActivity$onCreate$$inlined$compareBy$1(Collator.getInstance(getResources().getConfiguration().getLocales().get(0)));
        this.appName = getIntent().getCharSequenceExtra("extra_app_label");
        this.structureExtra = getIntent().getCharSequenceExtra("extra_structure");
        this.component = (ComponentName) getIntent().getParcelableExtra("android.intent.extra.COMPONENT_NAME");
        this.fromProviderSelector = getIntent().getBooleanExtra("extra_from_provider_selector", false);
        setContentView(C1777R.layout.controls_management);
        this.lifecycle.addObserver(new ControlsAnimations$observerForAnimations$1(getIntent(), (ViewGroup) requireViewById(C1777R.C1779id.controls_management_root), getWindow()));
        ViewStub viewStub = (ViewStub) requireViewById(C1777R.C1779id.stub);
        viewStub.setLayoutResource(C1777R.layout.controls_management_favorites);
        viewStub.inflate();
        this.statusText = (TextView) requireViewById(C1777R.C1779id.status_message);
        Context applicationContext = getApplicationContext();
        if (applicationContext.getSharedPreferences(applicationContext.getPackageName(), 0).getInt("ControlsStructureSwipeTooltipCount", 0) < 2) {
            z = true;
        } else {
            z = false;
        }
        if (z) {
            TextView textView = this.statusText;
            if (textView == null) {
                textView = null;
            }
            TooltipManager tooltipManager = new TooltipManager(textView.getContext());
            this.mTooltipManager = tooltipManager;
            addContentView(tooltipManager.layout, new FrameLayout.LayoutParams(-2, -2, 51));
        }
        ManagementPageIndicator managementPageIndicator = (ManagementPageIndicator) requireViewById(C1777R.C1779id.structure_page_indicator);
        ControlsFavoritingActivity$bindViews$2$1 controlsFavoritingActivity$bindViews$2$1 = new ControlsFavoritingActivity$bindViews$2$1(this);
        Objects.requireNonNull(managementPageIndicator);
        managementPageIndicator.visibilityListener = controlsFavoritingActivity$bindViews$2$1;
        this.pageIndicator = managementPageIndicator;
        CharSequence charSequence = this.structureExtra;
        if (charSequence == null && (charSequence = this.appName) == null) {
            charSequence = getResources().getText(C1777R.string.controls_favorite_default_title);
        }
        TextView textView2 = (TextView) requireViewById(C1777R.C1779id.title);
        textView2.setText(charSequence);
        this.titleView = textView2;
        TextView textView3 = (TextView) requireViewById(C1777R.C1779id.subtitle);
        textView3.setText(textView3.getResources().getText(C1777R.string.controls_favorite_subtitle));
        this.subtitleView = textView3;
        ViewPager2 viewPager2 = (ViewPager2) requireViewById(C1777R.C1779id.structure_pager);
        this.structurePager = viewPager2;
        ControlsFavoritingActivity$bindViews$5 controlsFavoritingActivity$bindViews$5 = new ControlsFavoritingActivity$bindViews$5(this);
        Objects.requireNonNull(viewPager2);
        CompositeOnPageChangeCallback compositeOnPageChangeCallback = viewPager2.mExternalPageChangeCallbacks;
        Objects.requireNonNull(compositeOnPageChangeCallback);
        compositeOnPageChangeCallback.mCallbacks.add(controlsFavoritingActivity$bindViews$5);
        View requireViewById = requireViewById(C1777R.C1779id.other_apps);
        Button button = (Button) requireViewById;
        button.setOnClickListener(new ControlsFavoritingActivity$bindButtons$1$1(this, button));
        this.otherAppsButton = requireViewById;
        View requireViewById2 = requireViewById(C1777R.C1779id.done);
        Button button2 = (Button) requireViewById2;
        button2.setEnabled(false);
        button2.setOnClickListener(new ControlsFavoritingActivity$bindButtons$2$1(this));
        this.doneButton = requireViewById2;
    }

    public final void onPause() {
        super.onPause();
        TooltipManager tooltipManager = this.mTooltipManager;
        if (tooltipManager != null) {
            tooltipManager.hide(false);
        }
    }

    public final void onResume() {
        super.onResume();
        if (!this.isPagerLoaded) {
            ViewPager2 viewPager2 = this.structurePager;
            TextView textView = null;
            if (viewPager2 == null) {
                viewPager2 = null;
            }
            viewPager2.setAlpha(0.0f);
            ManagementPageIndicator managementPageIndicator = this.pageIndicator;
            if (managementPageIndicator == null) {
                managementPageIndicator = null;
            }
            managementPageIndicator.setAlpha(0.0f);
            ViewPager2 viewPager22 = this.structurePager;
            if (viewPager22 == null) {
                viewPager22 = null;
            }
            viewPager22.setAdapter(new StructureAdapter(EmptyList.INSTANCE));
            ControlsFavoritingActivity$setUpPager$1$1 controlsFavoritingActivity$setUpPager$1$1 = new ControlsFavoritingActivity$setUpPager$1$1(this);
            CompositeOnPageChangeCallback compositeOnPageChangeCallback = viewPager22.mExternalPageChangeCallbacks;
            Objects.requireNonNull(compositeOnPageChangeCallback);
            compositeOnPageChangeCallback.mCallbacks.add(controlsFavoritingActivity$setUpPager$1$1);
            ComponentName componentName = this.component;
            if (componentName != null) {
                TextView textView2 = this.statusText;
                if (textView2 != null) {
                    textView = textView2;
                }
                textView.setText(getResources().getText(17040572));
                this.controller.loadForComponent(componentName, new ControlsFavoritingActivity$loadControls$1$1(this, getResources().getText(C1777R.string.controls_favorite_other_zone_header)), new ControlsFavoritingActivity$loadControls$1$2(this));
            }
            this.isPagerLoaded = true;
        }
    }

    public final void onStart() {
        super.onStart();
        this.listingController.addCallback(this.listingCallback);
        this.currentUserTracker.startTracking();
    }

    public final void onStop() {
        super.onStop();
        this.listingController.removeCallback(this.listingCallback);
        this.currentUserTracker.stopTracking();
    }
}
