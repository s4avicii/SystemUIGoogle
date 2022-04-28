package com.android.systemui.biometrics;

import android.graphics.PointF;
import com.android.keyguard.KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.biometrics.UdfpsAnimationView;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.phone.SystemUIDialogManager;
import com.android.systemui.statusbar.phone.panelstate.PanelExpansionStateManager;
import com.android.systemui.util.ViewController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Objects;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UdfpsAnimationViewController.kt */
public abstract class UdfpsAnimationViewController<T extends UdfpsAnimationView> extends ViewController<T> implements Dumpable {
    public final UdfpsAnimationViewController$dialogListener$1 dialogListener = new UdfpsAnimationViewController$dialogListener$1(this);
    public final SystemUIDialogManager dialogManager;
    public final DumpManager dumpManager;
    public final String dumpTag;
    public boolean notificationShadeVisible;
    public final UdfpsAnimationViewController$panelExpansionListener$1 panelExpansionListener;
    public final PanelExpansionStateManager panelExpansionStateManager;
    public final StatusBarStateController statusBarStateController;
    public final PointF touchTranslation;

    public int getPaddingX() {
        return 0;
    }

    public int getPaddingY() {
        return 0;
    }

    public abstract String getTag();

    public void onTouchOutsideView() {
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(Intrinsics.stringPlus("mNotificationShadeVisible=", Boolean.valueOf(this.notificationShadeVisible)));
        printWriter.println(Intrinsics.stringPlus("shouldPauseAuth()=", Boolean.valueOf(shouldPauseAuth())));
        KeyguardBiometricLockoutLogger$$ExternalSyntheticOutline0.m25m(getView().mPauseAuth, "isPauseAuth=", printWriter);
    }

    public final T getView() {
        T t = this.mView;
        Intrinsics.checkNotNull(t);
        return (UdfpsAnimationView) t;
    }

    public void onViewAttached() {
        this.panelExpansionStateManager.addExpansionListener(this.panelExpansionListener);
        SystemUIDialogManager systemUIDialogManager = this.dialogManager;
        UdfpsAnimationViewController$dialogListener$1 udfpsAnimationViewController$dialogListener$1 = this.dialogListener;
        Objects.requireNonNull(systemUIDialogManager);
        systemUIDialogManager.mListeners.add(udfpsAnimationViewController$dialogListener$1);
        this.dumpManager.registerDumpable(this.dumpTag, this);
    }

    public void onViewDetached() {
        PanelExpansionStateManager panelExpansionStateManager2 = this.panelExpansionStateManager;
        UdfpsAnimationViewController$panelExpansionListener$1 udfpsAnimationViewController$panelExpansionListener$1 = this.panelExpansionListener;
        Objects.requireNonNull(panelExpansionStateManager2);
        panelExpansionStateManager2.expansionListeners.remove(udfpsAnimationViewController$panelExpansionListener$1);
        SystemUIDialogManager systemUIDialogManager = this.dialogManager;
        UdfpsAnimationViewController$dialogListener$1 udfpsAnimationViewController$dialogListener$1 = this.dialogListener;
        Objects.requireNonNull(systemUIDialogManager);
        systemUIDialogManager.mListeners.remove(udfpsAnimationViewController$dialogListener$1);
        this.dumpManager.unregisterDumpable(this.dumpTag);
    }

    public boolean shouldPauseAuth() {
        if (this.notificationShadeVisible || this.dialogManager.shouldHideAffordance()) {
            return true;
        }
        return false;
    }

    public UdfpsAnimationViewController(T t, StatusBarStateController statusBarStateController2, PanelExpansionStateManager panelExpansionStateManager2, SystemUIDialogManager systemUIDialogManager, DumpManager dumpManager2) {
        super(t);
        this.statusBarStateController = statusBarStateController2;
        this.panelExpansionStateManager = panelExpansionStateManager2;
        this.dialogManager = systemUIDialogManager;
        this.dumpManager = dumpManager2;
        this.panelExpansionListener = new UdfpsAnimationViewController$panelExpansionListener$1(this, t);
        this.touchTranslation = new PointF(0.0f, 0.0f);
        this.dumpTag = getTag() + " (" + this + ')';
    }

    public final void updatePauseAuth() {
        boolean z;
        UdfpsAnimationView view = getView();
        boolean shouldPauseAuth = shouldPauseAuth();
        if (shouldPauseAuth != view.mPauseAuth) {
            view.mPauseAuth = shouldPauseAuth;
            view.updateAlpha();
            z = true;
        } else {
            z = false;
        }
        if (z) {
            getView().postInvalidate();
        }
    }

    public PointF getTouchTranslation() {
        return this.touchTranslation;
    }
}
