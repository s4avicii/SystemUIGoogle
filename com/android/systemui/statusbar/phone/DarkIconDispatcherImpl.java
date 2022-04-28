package com.android.systemui.statusbar.phone;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.graphics.Rect;
import android.util.ArrayMap;
import android.widget.ImageView;
import com.android.keyguard.LockIconView$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.CommandQueue;
import com.android.systemui.statusbar.phone.LightBarTransitionsController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

public final class DarkIconDispatcherImpl implements SysuiDarkIconDispatcher, LightBarTransitionsController.DarkIntensityApplier {
    public float mDarkIntensity;
    public int mDarkModeIconColorSingleTone;
    public int mIconTint = -1;
    public int mLightModeIconColorSingleTone;
    public final ArrayMap<Object, DarkIconDispatcher.DarkReceiver> mReceivers = new ArrayMap<>();
    public final ArrayList<Rect> mTintAreas = new ArrayList<>();
    public final LightBarTransitionsController mTransitionsController;

    public final void addDarkReceiver(DarkIconDispatcher.DarkReceiver darkReceiver) {
        this.mReceivers.put(darkReceiver, darkReceiver);
        darkReceiver.onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
    }

    public final void applyIconTint() {
        for (int i = 0; i < this.mReceivers.size(); i++) {
            this.mReceivers.valueAt(i).onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
        }
    }

    public final int getTintAnimationDuration() {
        return 120;
    }

    public final void removeDarkReceiver(DarkIconDispatcher.DarkReceiver darkReceiver) {
        this.mReceivers.remove(darkReceiver);
    }

    public final void applyDark(DarkIconDispatcher.DarkReceiver darkReceiver) {
        this.mReceivers.get(darkReceiver).onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
    }

    public final void applyDarkIntensity(float f) {
        this.mDarkIntensity = f;
        this.mIconTint = ((Integer) ArgbEvaluator.getInstance().evaluate(f, Integer.valueOf(this.mLightModeIconColorSingleTone), Integer.valueOf(this.mDarkModeIconColorSingleTone))).intValue();
        applyIconTint();
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = LockIconView$$ExternalSyntheticOutline0.m30m(printWriter, "DarkIconDispatcher: ", "  mIconTint: 0x");
        m.append(Integer.toHexString(this.mIconTint));
        printWriter.println(m.toString());
        printWriter.println("  mDarkIntensity: " + this.mDarkIntensity + "f");
        StringBuilder sb = new StringBuilder();
        sb.append("  mTintAreas: ");
        sb.append(this.mTintAreas);
        printWriter.println(sb.toString());
    }

    public final void removeDarkReceiver(ImageView imageView) {
        this.mReceivers.remove(imageView);
    }

    public final void setIconsDarkArea(ArrayList<Rect> arrayList) {
        if (arrayList != null || !this.mTintAreas.isEmpty()) {
            this.mTintAreas.clear();
            if (arrayList != null) {
                this.mTintAreas.addAll(arrayList);
            }
            applyIconTint();
        }
    }

    public DarkIconDispatcherImpl(Context context, CommandQueue commandQueue, DumpManager dumpManager) {
        this.mDarkModeIconColorSingleTone = context.getColor(C1777R.color.dark_mode_icon_color_single_tone);
        this.mLightModeIconColorSingleTone = context.getColor(C1777R.color.light_mode_icon_color_single_tone);
        this.mTransitionsController = new LightBarTransitionsController(context, this, commandQueue);
        dumpManager.registerDumpable("DarkIconDispatcherImpl", this);
    }

    public final void addDarkReceiver(ImageView imageView) {
        DarkIconDispatcherImpl$$ExternalSyntheticLambda0 darkIconDispatcherImpl$$ExternalSyntheticLambda0 = new DarkIconDispatcherImpl$$ExternalSyntheticLambda0(this, imageView);
        this.mReceivers.put(imageView, darkIconDispatcherImpl$$ExternalSyntheticLambda0);
        darkIconDispatcherImpl$$ExternalSyntheticLambda0.onDarkChanged(this.mTintAreas, this.mDarkIntensity, this.mIconTint);
    }

    public final LightBarTransitionsController getTransitionsController() {
        return this.mTransitionsController;
    }
}
