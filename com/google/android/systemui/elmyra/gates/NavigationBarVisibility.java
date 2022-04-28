package com.google.android.systemui.elmyra.gates;

import android.content.Context;
import com.android.systemui.Dependency;
import com.android.systemui.assist.AssistManager;
import com.android.systemui.statusbar.CommandQueue;
import com.google.android.systemui.assist.AssistManagerGoogle;
import com.google.android.systemui.elmyra.actions.Action;
import com.google.android.systemui.elmyra.gates.Gate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class NavigationBarVisibility extends Gate {
    public final AssistManagerGoogle mAssistManager;
    public final C22541 mCommandQueueCallbacks;
    public final int mDisplayId;
    public final ArrayList mExceptions;
    public final C22552 mGateListener;
    public boolean mIsKeyguardShowing;
    public boolean mIsNavigationGestural;
    public boolean mIsNavigationHidden = false;
    public final KeyguardVisibility mKeyguardGate;
    public final NonGesturalNavigation mNavigationModeGate;

    public final boolean isBlocked() {
        if (this.mIsKeyguardShowing) {
            return false;
        }
        if (this.mIsNavigationGestural) {
            AssistManagerGoogle assistManagerGoogle = this.mAssistManager;
            Objects.requireNonNull(assistManagerGoogle);
            if (assistManagerGoogle.mNgaIsAssistant) {
                return false;
            }
        }
        for (int i = 0; i < this.mExceptions.size(); i++) {
            if (((Action) this.mExceptions.get(i)).isAvailable()) {
                return false;
            }
        }
        return this.mIsNavigationHidden;
    }

    public final void onActivate() {
        this.mKeyguardGate.activate();
        KeyguardVisibility keyguardVisibility = this.mKeyguardGate;
        Objects.requireNonNull(keyguardVisibility);
        this.mIsKeyguardShowing = keyguardVisibility.mKeyguardStateController.isShowing();
        this.mNavigationModeGate.activate();
        NonGesturalNavigation nonGesturalNavigation = this.mNavigationModeGate;
        Objects.requireNonNull(nonGesturalNavigation);
        this.mIsNavigationGestural = nonGesturalNavigation.mCurrentModeIsGestural;
    }

    public final void onDeactivate() {
        this.mNavigationModeGate.deactivate();
        NonGesturalNavigation nonGesturalNavigation = this.mNavigationModeGate;
        Objects.requireNonNull(nonGesturalNavigation);
        this.mIsNavigationGestural = nonGesturalNavigation.mCurrentModeIsGestural;
        this.mKeyguardGate.deactivate();
        KeyguardVisibility keyguardVisibility = this.mKeyguardGate;
        Objects.requireNonNull(keyguardVisibility);
        this.mIsKeyguardShowing = keyguardVisibility.mKeyguardStateController.isShowing();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" [mIsNavigationHidden -> ");
        sb.append(this.mIsNavigationHidden);
        sb.append("; mExceptions -> ");
        sb.append(this.mExceptions);
        sb.append("; mIsNavigationGestural -> ");
        sb.append(this.mIsNavigationGestural);
        sb.append("; isActiveAssistantNga() -> ");
        AssistManagerGoogle assistManagerGoogle = this.mAssistManager;
        Objects.requireNonNull(assistManagerGoogle);
        sb.append(assistManagerGoogle.mNgaIsAssistant);
        sb.append("]");
        return sb.toString();
    }

    public NavigationBarVisibility(Context context, List<Action> list) {
        super(context);
        C22541 r0 = new CommandQueue.Callbacks() {
            public final void setWindowState(int i, int i2, int i3) {
                boolean z;
                NavigationBarVisibility navigationBarVisibility = NavigationBarVisibility.this;
                if (navigationBarVisibility.mDisplayId == i && i2 == 2) {
                    if (i3 != 0) {
                        z = true;
                    } else {
                        z = false;
                    }
                    if (z != navigationBarVisibility.mIsNavigationHidden) {
                        navigationBarVisibility.mIsNavigationHidden = z;
                        navigationBarVisibility.notifyListener();
                    }
                }
            }
        };
        this.mCommandQueueCallbacks = r0;
        C22552 r1 = new Gate.Listener() {
            public final void onGateChanged(Gate gate) {
                if (gate.equals(NavigationBarVisibility.this.mKeyguardGate)) {
                    NavigationBarVisibility navigationBarVisibility = NavigationBarVisibility.this;
                    Objects.requireNonNull(navigationBarVisibility);
                    KeyguardVisibility keyguardVisibility = navigationBarVisibility.mKeyguardGate;
                    Objects.requireNonNull(keyguardVisibility);
                    navigationBarVisibility.mIsKeyguardShowing = keyguardVisibility.mKeyguardStateController.isShowing();
                } else if (gate.equals(NavigationBarVisibility.this.mNavigationModeGate)) {
                    NavigationBarVisibility navigationBarVisibility2 = NavigationBarVisibility.this;
                    Objects.requireNonNull(navigationBarVisibility2);
                    NonGesturalNavigation nonGesturalNavigation = navigationBarVisibility2.mNavigationModeGate;
                    Objects.requireNonNull(nonGesturalNavigation);
                    navigationBarVisibility2.mIsNavigationGestural = nonGesturalNavigation.mCurrentModeIsGestural;
                }
            }
        };
        this.mGateListener = r1;
        this.mExceptions = new ArrayList(list);
        ((CommandQueue) Dependency.get(CommandQueue.class)).addCallback((CommandQueue.Callbacks) r0);
        this.mDisplayId = context.getDisplayId();
        this.mAssistManager = (AssistManagerGoogle) Dependency.get(AssistManager.class);
        KeyguardVisibility keyguardVisibility = new KeyguardVisibility(context);
        this.mKeyguardGate = keyguardVisibility;
        keyguardVisibility.mListener = r1;
        NonGesturalNavigation nonGesturalNavigation = new NonGesturalNavigation(context);
        this.mNavigationModeGate = nonGesturalNavigation;
        nonGesturalNavigation.mListener = r1;
    }
}
