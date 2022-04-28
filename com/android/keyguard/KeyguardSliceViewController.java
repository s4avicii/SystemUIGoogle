package com.android.keyguard;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.net.Uri;
import android.os.Trace;
import android.view.Display;
import android.view.View;
import androidx.lifecycle.Observer;
import androidx.slice.Slice;
import androidx.slice.widget.ListContent;
import androidx.slice.widget.RowContent;
import androidx.slice.widget.SliceLiveData;
import com.android.keyguard.KeyguardSliceView;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.ViewController;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class KeyguardSliceViewController extends ViewController<KeyguardSliceView> implements Dumpable {
    public final ActivityStarter mActivityStarter;
    public HashMap mClickActions;
    public final ConfigurationController mConfigurationController;
    public C05301 mConfigurationListener = new ConfigurationController.ConfigurationListener() {
        public final void onDensityOrFontScaleChanged() {
            ((KeyguardSliceView) KeyguardSliceViewController.this.mView).onDensityOrFontScaleChanged();
        }

        public final void onThemeChanged() {
            KeyguardSliceView keyguardSliceView = (KeyguardSliceView) KeyguardSliceViewController.this.mView;
            Objects.requireNonNull(keyguardSliceView);
            for (int i = 0; i < keyguardSliceView.mRow.getChildCount(); i++) {
                View childAt = keyguardSliceView.mRow.getChildAt(i);
                if (childAt instanceof KeyguardSliceView.KeyguardSliceTextView) {
                    KeyguardSliceView.KeyguardSliceTextView keyguardSliceTextView = (KeyguardSliceView.KeyguardSliceTextView) childAt;
                    Objects.requireNonNull(keyguardSliceTextView);
                    keyguardSliceTextView.setTextAppearance(2132017919);
                }
            }
        }
    };
    public int mDisplayId;
    public final DumpManager mDumpManager;
    public Uri mKeyguardSliceUri;
    public SliceLiveData.SliceLiveDataImpl mLiveData;
    public C05312 mObserver = new Observer<Slice>() {
        public final void onChanged(Object obj) {
            Slice slice = (Slice) obj;
            KeyguardSliceViewController keyguardSliceViewController = KeyguardSliceViewController.this;
            keyguardSliceViewController.mSlice = slice;
            Trace.beginSection("KeyguardSliceViewController#showSlice");
            boolean z = false;
            if (slice == null) {
                KeyguardSliceView keyguardSliceView = (KeyguardSliceView) keyguardSliceViewController.mView;
                Objects.requireNonNull(keyguardSliceView);
                keyguardSliceView.mTitle.setVisibility(8);
                keyguardSliceView.mRow.setVisibility(8);
                keyguardSliceView.mHasHeader = false;
                Runnable runnable = keyguardSliceView.mContentChangeListener;
                if (runnable != null) {
                    runnable.run();
                }
                Trace.endSection();
                return;
            }
            ListContent listContent = new ListContent(slice);
            RowContent rowContent = listContent.mHeaderContent;
            if (rowContent != null && !rowContent.mSliceItem.hasHint("list_item")) {
                z = true;
            }
            List list = (List) listContent.mRowItems.stream().filter(KeyguardSliceViewController$$ExternalSyntheticLambda1.INSTANCE).collect(Collectors.toList());
            KeyguardSliceView keyguardSliceView2 = (KeyguardSliceView) keyguardSliceViewController.mView;
            if (!z) {
                rowContent = null;
            }
            keyguardSliceViewController.mClickActions = keyguardSliceView2.showSlice(rowContent, list);
            Trace.endSection();
        }
    };
    public Slice mSlice;
    public KeyguardSliceViewController$$ExternalSyntheticLambda0 mTunable = new KeyguardSliceViewController$$ExternalSyntheticLambda0(this);
    public final TunerService mTunerService;

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  mSlice: ");
        m.append(this.mSlice);
        printWriter.println(m.toString());
        printWriter.println("  mClickActions: " + this.mClickActions);
    }

    public final void onViewAttached() {
        SliceLiveData.SliceLiveDataImpl sliceLiveDataImpl;
        Display display = ((KeyguardSliceView) this.mView).getDisplay();
        if (display != null) {
            this.mDisplayId = display.getDisplayId();
        }
        this.mTunerService.addTunable(this.mTunable, "keyguard_slice_uri");
        if (this.mDisplayId == 0 && (sliceLiveDataImpl = this.mLiveData) != null) {
            sliceLiveDataImpl.observeForever(this.mObserver);
        }
        this.mConfigurationController.addCallback(this.mConfigurationListener);
        DumpManager dumpManager = this.mDumpManager;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("KeyguardSliceViewCtrl@");
        m.append(Integer.toHexString(hashCode()));
        dumpManager.registerDumpable(m.toString(), this);
    }

    public final void onViewDetached() {
        if (this.mDisplayId == 0) {
            this.mLiveData.removeObserver(this.mObserver);
        }
        this.mTunerService.removeTunable(this.mTunable);
        this.mConfigurationController.removeCallback(this.mConfigurationListener);
        DumpManager dumpManager = this.mDumpManager;
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("KeyguardSliceViewCtrl@");
        m.append(Integer.toHexString(hashCode()));
        dumpManager.unregisterDumpable(m.toString());
    }

    public KeyguardSliceViewController(KeyguardSliceView keyguardSliceView, ActivityStarter activityStarter, ConfigurationController configurationController, TunerService tunerService, DumpManager dumpManager) {
        super(keyguardSliceView);
        this.mActivityStarter = activityStarter;
        this.mConfigurationController = configurationController;
        this.mTunerService = tunerService;
        this.mDumpManager = dumpManager;
    }
}
