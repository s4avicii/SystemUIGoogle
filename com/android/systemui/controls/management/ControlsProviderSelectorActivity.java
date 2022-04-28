package com.android.systemui.controls.management;

import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import androidx.mediarouter.R$bool;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.controller.ControlsController;
import com.android.systemui.controls.p004ui.ControlsActivity;
import com.android.systemui.controls.p004ui.ControlsUiController;
import com.android.systemui.util.LifecycleActivity;
import java.util.concurrent.Executor;

/* compiled from: ControlsProviderSelectorActivity.kt */
public final class ControlsProviderSelectorActivity extends LifecycleActivity {
    public final Executor backExecutor;
    public boolean backShouldExit;
    public final ControlsController controlsController;
    public final ControlsProviderSelectorActivity$currentUserTracker$1 currentUserTracker;
    public final Executor executor;
    public final ControlsListingController listingController;
    public RecyclerView recyclerView;
    public final ControlsUiController uiController;

    public final void onBackPressed() {
        if (!this.backShouldExit) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(getApplicationContext(), ControlsActivity.class));
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, new Pair[0]).toBundle());
        }
        R$bool.exitAnimation((ViewGroup) requireViewById(C1777R.C1779id.controls_management_root), new ControlsProviderSelectorActivity$animateExitAndFinish$1(this)).start();
    }

    public final void onDestroy() {
        this.currentUserTracker.stopTracking();
        super.onDestroy();
    }

    public ControlsProviderSelectorActivity(Executor executor2, Executor executor3, ControlsListingController controlsListingController, ControlsController controlsController2, BroadcastDispatcher broadcastDispatcher, ControlsUiController controlsUiController) {
        this.executor = executor2;
        this.backExecutor = executor3;
        this.listingController = controlsListingController;
        this.controlsController = controlsController2;
        this.uiController = controlsUiController;
        this.currentUserTracker = new ControlsProviderSelectorActivity$currentUserTracker$1(this, broadcastDispatcher);
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1777R.layout.controls_management);
        Lifecycle lifecycle = this.lifecycle;
        Window window = getWindow();
        lifecycle.addObserver(new ControlsAnimations$observerForAnimations$1(getIntent(), (ViewGroup) requireViewById(C1777R.C1779id.controls_management_root), window));
        ViewStub viewStub = (ViewStub) requireViewById(C1777R.C1779id.stub);
        viewStub.setLayoutResource(C1777R.layout.controls_management_apps);
        viewStub.inflate();
        RecyclerView recyclerView2 = (RecyclerView) requireViewById(C1777R.C1779id.list);
        this.recyclerView = recyclerView2;
        getApplicationContext();
        recyclerView2.setLayoutManager(new LinearLayoutManager(1));
        TextView textView = (TextView) requireViewById(C1777R.C1779id.title);
        textView.setText(textView.getResources().getText(C1777R.string.controls_providers_title));
        Button button = (Button) requireViewById(C1777R.C1779id.other_apps);
        button.setVisibility(0);
        button.setText(17039360);
        button.setOnClickListener(new ControlsProviderSelectorActivity$onCreate$3$1(this));
        requireViewById(C1777R.C1779id.done).setVisibility(8);
        this.backShouldExit = getIntent().getBooleanExtra("back_should_exit", false);
    }

    public final void onStart() {
        super.onStart();
        this.currentUserTracker.startTracking();
        RecyclerView recyclerView2 = this.recyclerView;
        RecyclerView recyclerView3 = null;
        if (recyclerView2 == null) {
            recyclerView2 = null;
        }
        recyclerView2.setAlpha(0.0f);
        RecyclerView recyclerView4 = this.recyclerView;
        if (recyclerView4 != null) {
            recyclerView3 = recyclerView4;
        }
        AppAdapter appAdapter = new AppAdapter(this.backExecutor, this.executor, this.lifecycle, this.listingController, LayoutInflater.from(this), new ControlsProviderSelectorActivity$onStart$1(this), new FavoritesRenderer(getResources(), new ControlsProviderSelectorActivity$onStart$2(this.controlsController)), getResources());
        appAdapter.registerAdapterDataObserver(new ControlsProviderSelectorActivity$onStart$3$1(this));
        recyclerView3.setAdapter(appAdapter);
    }

    public final void onStop() {
        super.onStop();
        this.currentUserTracker.stopTracking();
    }
}
