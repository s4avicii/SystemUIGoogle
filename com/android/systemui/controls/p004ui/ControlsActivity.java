package com.android.systemui.controls.p004ui;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.ViewGroup;
import android.view.Window;
import androidx.mediarouter.R$bool;
import com.android.p012wm.shell.C1777R;
import com.android.settingslib.core.lifecycle.Lifecycle;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.controls.management.ControlsAnimations$observerForAnimations$1;
import com.android.systemui.util.LifecycleActivity;
import java.util.concurrent.Executor;

/* renamed from: com.android.systemui.controls.ui.ControlsActivity */
/* compiled from: ControlsActivity.kt */
public final class ControlsActivity extends LifecycleActivity {
    public final BroadcastDispatcher broadcastDispatcher;
    public ControlsActivity$initBroadcastReceiver$1 broadcastReceiver;
    public ViewGroup parent;
    public final ControlsUiController uiController;

    public ControlsActivity(ControlsUiController controlsUiController, BroadcastDispatcher broadcastDispatcher2) {
        this.uiController = controlsUiController;
        this.broadcastDispatcher = broadcastDispatcher2;
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1777R.layout.controls_fullscreen);
        Lifecycle lifecycle = this.lifecycle;
        Window window = getWindow();
        lifecycle.addObserver(new ControlsAnimations$observerForAnimations$1(getIntent(), (ViewGroup) requireViewById(C1777R.C1779id.control_detail_root), window));
        ((ViewGroup) requireViewById(C1777R.C1779id.control_detail_root)).setOnApplyWindowInsetsListener(ControlsActivity$onCreate$1$1.INSTANCE);
        this.broadcastReceiver = new ControlsActivity$initBroadcastReceiver$1(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        BroadcastDispatcher broadcastDispatcher2 = this.broadcastDispatcher;
        ControlsActivity$initBroadcastReceiver$1 controlsActivity$initBroadcastReceiver$1 = this.broadcastReceiver;
        if (controlsActivity$initBroadcastReceiver$1 == null) {
            controlsActivity$initBroadcastReceiver$1 = null;
        }
        BroadcastDispatcher.registerReceiver$default(broadcastDispatcher2, controlsActivity$initBroadcastReceiver$1, intentFilter, (Executor) null, (UserHandle) null, 28);
    }

    public final void onDestroy() {
        super.onDestroy();
        BroadcastDispatcher broadcastDispatcher2 = this.broadcastDispatcher;
        ControlsActivity$initBroadcastReceiver$1 controlsActivity$initBroadcastReceiver$1 = this.broadcastReceiver;
        if (controlsActivity$initBroadcastReceiver$1 == null) {
            controlsActivity$initBroadcastReceiver$1 = null;
        }
        broadcastDispatcher2.unregisterReceiver(controlsActivity$initBroadcastReceiver$1);
    }

    public final void onPause() {
        super.onPause();
        this.uiController.hide();
    }

    public final void onResume() {
        super.onResume();
        ViewGroup viewGroup = (ViewGroup) requireViewById(C1777R.C1779id.global_actions_controls);
        this.parent = viewGroup;
        viewGroup.setAlpha(0.0f);
        ControlsUiController controlsUiController = this.uiController;
        ViewGroup viewGroup2 = this.parent;
        ViewGroup viewGroup3 = null;
        if (viewGroup2 == null) {
            viewGroup2 = null;
        }
        controlsUiController.show(viewGroup2, new ControlsActivity$onResume$1(this), this);
        ViewGroup viewGroup4 = this.parent;
        if (viewGroup4 != null) {
            viewGroup3 = viewGroup4;
        }
        R$bool.enterAnimation(viewGroup3).start();
    }

    public final void onBackPressed() {
        finish();
    }
}
