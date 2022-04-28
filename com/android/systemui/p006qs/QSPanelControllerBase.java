package com.android.systemui.p006qs;

import android.content.Context;
import android.content.res.Configuration;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.metrics.LogMaker;
import android.util.ArraySet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.widget.RemeasuringLinearLayout;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.MediaHost;
import com.android.systemui.p006qs.QSPanel;
import com.android.systemui.p006qs.QSPanelControllerBase;
import com.android.systemui.p006qs.customize.QSCustomizer;
import com.android.systemui.p006qs.customize.QSCustomizerController;
import com.android.systemui.p006qs.logging.QSLogger;
import com.android.systemui.plugins.p005qs.QSTile;
import com.android.systemui.plugins.p005qs.QSTileView;
import com.android.systemui.util.Utils;
import com.android.systemui.util.ViewController;
import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.UniqueObjectHostView;
import com.android.wifitrackerlib.WifiPickerTracker$$ExternalSyntheticLambda11;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/* renamed from: com.android.systemui.qs.QSPanelControllerBase */
public abstract class QSPanelControllerBase<T extends QSPanel> extends ViewController<T> implements Dumpable {
    public String mCachedSpecs = "";
    public final DumpManager mDumpManager;
    public final QSTileHost mHost;
    public int mLastOrientation;
    public final MediaHost mMediaHost;
    public final QSPanelControllerBase$$ExternalSyntheticLambda1 mMediaHostVisibilityListener = new QSPanelControllerBase$$ExternalSyntheticLambda1(this);
    public Consumer<Boolean> mMediaVisibilityChangedListener;
    public final MetricsLogger mMetricsLogger;
    @VisibleForTesting
    public final QSPanel.OnConfigurationChangedListener mOnConfigurationChangedListener = new QSPanel.OnConfigurationChangedListener() {
        public final void onConfigurationChange(Configuration configuration) {
            QSPanelControllerBase qSPanelControllerBase = QSPanelControllerBase.this;
            qSPanelControllerBase.mShouldUseSplitNotificationShade = Utils.shouldUseSplitNotificationShade(qSPanelControllerBase.getResources());
            QSPanelControllerBase.this.onConfigurationChanged();
            int i = configuration.orientation;
            QSPanelControllerBase qSPanelControllerBase2 = QSPanelControllerBase.this;
            if (i != qSPanelControllerBase2.mLastOrientation) {
                qSPanelControllerBase2.mLastOrientation = i;
                qSPanelControllerBase2.switchTileLayout(false);
            }
        }
    };
    public final QSPanelControllerBase$$ExternalSyntheticLambda0 mQSHostCallback = new QSPanelControllerBase$$ExternalSyntheticLambda0(this);
    public final QSLogger mQSLogger;
    public final QSCustomizerController mQsCustomizerController;
    public QSTileRevealController mQsTileRevealController;
    public final ArrayList<TileRecord> mRecords = new ArrayList<>();
    public float mRevealExpansion;
    public boolean mShouldUseSplitNotificationShade;
    public final UiEventLogger mUiEventLogger;
    public boolean mUsingHorizontalLayout;
    public Runnable mUsingHorizontalLayoutChangedListener;
    public final boolean mUsingMediaPlayer;

    public QSTileRevealController createTileRevealController() {
        return null;
    }

    public void onConfigurationChanged() {
    }

    public void setTiles() {
        QSTileHost qSTileHost = this.mHost;
        Objects.requireNonNull(qSTileHost);
        setTiles(qSTileHost.mTiles.values(), false);
    }

    /* renamed from: com.android.systemui.qs.QSPanelControllerBase$TileRecord */
    public static final class TileRecord {
        public QSPanel.C09941 callback;
        public QSTile tile;
        public QSTileView tileView;

        public TileRecord(QSTile qSTile, QSTileView qSTileView) {
            this.tile = qSTile;
            this.tileView = qSTileView;
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(getClass().getSimpleName() + ":");
        printWriter.println("  Tile records:");
        Iterator<TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            TileRecord next = it.next();
            if (next.tile instanceof Dumpable) {
                printWriter.print("    ");
                ((Dumpable) next.tile).dump(fileDescriptor, printWriter, strArr);
                printWriter.print("    ");
                printWriter.println(next.tileView.toString());
            }
        }
        if (this.mMediaHost != null) {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("  media bounds: ");
            m.append(this.mMediaHost.getCurrentBounds());
            printWriter.println(m.toString());
        }
    }

    public final QSPanel.QSTileLayout getTileLayout() {
        QSPanel qSPanel = (QSPanel) this.mView;
        Objects.requireNonNull(qSPanel);
        return qSPanel.mTileLayout;
    }

    public void onInit() {
        QSPanel qSPanel = (QSPanel) this.mView;
        Objects.requireNonNull(qSPanel);
        qSPanel.mTileLayout = qSPanel.getOrCreateTileLayout();
        if (qSPanel.mUsingMediaPlayer) {
            RemeasuringLinearLayout remeasuringLinearLayout = new RemeasuringLinearLayout(qSPanel.mContext);
            qSPanel.mHorizontalLinearLayout = remeasuringLinearLayout;
            remeasuringLinearLayout.setOrientation(0);
            qSPanel.mHorizontalLinearLayout.setClipChildren(false);
            qSPanel.mHorizontalLinearLayout.setClipToPadding(false);
            RemeasuringLinearLayout remeasuringLinearLayout2 = new RemeasuringLinearLayout(qSPanel.mContext);
            qSPanel.mHorizontalContentContainer = remeasuringLinearLayout2;
            remeasuringLinearLayout2.setOrientation(1);
            qSPanel.setHorizontalContentContainerClipping();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, -2, 1.0f);
            layoutParams.setMarginStart(0);
            layoutParams.setMarginEnd((int) qSPanel.mContext.getResources().getDimension(C1777R.dimen.qs_media_padding));
            layoutParams.gravity = 16;
            qSPanel.mHorizontalLinearLayout.addView(qSPanel.mHorizontalContentContainer, layoutParams);
            qSPanel.addView(qSPanel.mHorizontalLinearLayout, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
        QSLogger qSLogger = this.mQSLogger;
        QSPanel qSPanel2 = (QSPanel) this.mView;
        Objects.requireNonNull(qSPanel2);
        qSLogger.logAllTilesChangeListening(qSPanel2.mListening, ((QSPanel) this.mView).getDumpableTag(), "");
    }

    public void onViewDetached() {
        QSPanel qSPanel = (QSPanel) this.mView;
        QSPanel.OnConfigurationChangedListener onConfigurationChangedListener = this.mOnConfigurationChangedListener;
        Objects.requireNonNull(qSPanel);
        qSPanel.mOnConfigurationChangedListeners.remove(onConfigurationChangedListener);
        QSTileHost qSTileHost = this.mHost;
        QSPanelControllerBase$$ExternalSyntheticLambda0 qSPanelControllerBase$$ExternalSyntheticLambda0 = this.mQSHostCallback;
        Objects.requireNonNull(qSTileHost);
        qSTileHost.mCallbacks.remove(qSPanelControllerBase$$ExternalSyntheticLambda0);
        QSPanel qSPanel2 = (QSPanel) this.mView;
        Objects.requireNonNull(qSPanel2);
        qSPanel2.mTileLayout.setListening(false, this.mUiEventLogger);
        MediaHost mediaHost = this.mMediaHost;
        QSPanelControllerBase$$ExternalSyntheticLambda1 qSPanelControllerBase$$ExternalSyntheticLambda1 = this.mMediaHostVisibilityListener;
        Objects.requireNonNull(mediaHost);
        mediaHost.visibleChangedListeners.remove(qSPanelControllerBase$$ExternalSyntheticLambda1);
        Iterator<TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            it.next().tile.removeCallbacks();
        }
        this.mRecords.clear();
        this.mDumpManager.unregisterDumpable(((QSPanel) this.mView).getDumpableTag());
    }

    public final void setExpanded(boolean z) {
        QSPanel qSPanel = (QSPanel) this.mView;
        Objects.requireNonNull(qSPanel);
        if (qSPanel.mExpanded != z) {
            this.mQSLogger.logPanelExpanded(z, ((QSPanel) this.mView).getDumpableTag());
            QSPanel qSPanel2 = (QSPanel) this.mView;
            Objects.requireNonNull(qSPanel2);
            if (qSPanel2.mExpanded != z) {
                qSPanel2.mExpanded = z;
                if (!z) {
                    QSPanel.QSTileLayout qSTileLayout = qSPanel2.mTileLayout;
                    if (qSTileLayout instanceof PagedTileLayout) {
                        ((PagedTileLayout) qSTileLayout).setCurrentItem(0, false);
                    }
                }
            }
            this.mMetricsLogger.visibility(111, z);
            if (!z) {
                this.mUiEventLogger.log(((QSPanel) this.mView).closePanelEvent());
                QSCustomizerController qSCustomizerController = this.mQsCustomizerController;
                Objects.requireNonNull(qSCustomizerController);
                if (((QSCustomizer) qSCustomizerController.mView).isShown()) {
                    this.mQsCustomizerController.hide();
                    return;
                }
                return;
            }
            this.mUiEventLogger.log(((QSPanel) this.mView).openPanelEvent());
            for (int i = 0; i < this.mRecords.size(); i++) {
                QSTile qSTile = this.mRecords.get(i).tile;
                this.mMetricsLogger.write(qSTile.populate(new LogMaker(qSTile.getMetricsCategory()).setType(1)));
            }
        }
    }

    public void setListening(boolean z) {
        QSPanel qSPanel = (QSPanel) this.mView;
        Objects.requireNonNull(qSPanel);
        qSPanel.mListening = z;
        QSPanel qSPanel2 = (QSPanel) this.mView;
        Objects.requireNonNull(qSPanel2);
        if (qSPanel2.mTileLayout != null) {
            this.mQSLogger.logAllTilesChangeListening(z, ((QSPanel) this.mView).getDumpableTag(), this.mCachedSpecs);
            QSPanel qSPanel3 = (QSPanel) this.mView;
            Objects.requireNonNull(qSPanel3);
            qSPanel3.mTileLayout.setListening(z, this.mUiEventLogger);
        }
    }

    public final void setSquishinessFraction(float f) {
        QSPanel qSPanel = (QSPanel) this.mView;
        Objects.requireNonNull(qSPanel);
        if (Float.compare(f, qSPanel.mSquishinessFraction) != 0) {
            qSPanel.mSquishinessFraction = f;
            QSPanel.QSTileLayout qSTileLayout = qSPanel.mTileLayout;
            if (qSTileLayout != null) {
                qSTileLayout.setSquishinessFraction(f);
                if (qSPanel.getMeasuredWidth() != 0) {
                    qSPanel.updateViewPositions();
                }
            }
        }
    }

    public final boolean switchTileLayout(boolean z) {
        boolean z2;
        RemeasuringLinearLayout remeasuringLinearLayout;
        int i;
        int i2;
        RemeasuringLinearLayout remeasuringLinearLayout2;
        int i3;
        float f;
        int i4;
        int i5;
        int i6 = 2;
        int i7 = 0;
        if (!this.mShouldUseSplitNotificationShade && this.mUsingMediaPlayer && this.mMediaHost.getVisible() && this.mLastOrientation == 2) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z2 == this.mUsingHorizontalLayout && !z) {
            return false;
        }
        this.mUsingHorizontalLayout = z2;
        RemeasuringLinearLayout remeasuringLinearLayout3 = (QSPanel) this.mView;
        UniqueObjectHostView hostView = this.mMediaHost.getHostView();
        Objects.requireNonNull(remeasuringLinearLayout3);
        if (z2 != remeasuringLinearLayout3.mUsingHorizontalLayout || z) {
            remeasuringLinearLayout3.mUsingHorizontalLayout = z2;
            if (z2) {
                remeasuringLinearLayout = remeasuringLinearLayout3.mHorizontalContentContainer;
            } else {
                remeasuringLinearLayout = remeasuringLinearLayout3;
            }
            QSPanel.QSTileLayout qSTileLayout = remeasuringLinearLayout3.mTileLayout;
            if (remeasuringLinearLayout == remeasuringLinearLayout3) {
                i = remeasuringLinearLayout3.mMovableContentStartIndex;
            } else {
                i = 0;
            }
            QSPanel.switchToParent((View) qSTileLayout, remeasuringLinearLayout, i, remeasuringLinearLayout3.getDumpableTag());
            int i8 = i + 1;
            View view = remeasuringLinearLayout3.mFooter;
            if (view != null) {
                QSPanel.switchToParent(view, remeasuringLinearLayout, i8, remeasuringLinearLayout3.getDumpableTag());
            }
            if (remeasuringLinearLayout3.mUsingMediaPlayer) {
                if (z2) {
                    remeasuringLinearLayout2 = remeasuringLinearLayout3.mHorizontalLinearLayout;
                } else {
                    remeasuringLinearLayout2 = remeasuringLinearLayout3;
                }
                RemeasuringLinearLayout remeasuringLinearLayout4 = (ViewGroup) hostView.getParent();
                if (remeasuringLinearLayout4 != remeasuringLinearLayout2) {
                    if (remeasuringLinearLayout4 != null) {
                        remeasuringLinearLayout4.removeView(hostView);
                    }
                    remeasuringLinearLayout2.addView(hostView);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) hostView.getLayoutParams();
                    layoutParams.height = -2;
                    if (z2) {
                        i3 = 0;
                    } else {
                        i3 = -1;
                    }
                    layoutParams.width = i3;
                    if (z2) {
                        f = 1.0f;
                    } else {
                        f = 0.0f;
                    }
                    layoutParams.weight = f;
                    if (!z2 || (!(remeasuringLinearLayout3 instanceof QuickQSPanel))) {
                        i4 = Math.max(remeasuringLinearLayout3.mMediaTotalBottomMargin - remeasuringLinearLayout3.getPaddingBottom(), 0);
                    } else {
                        i4 = 0;
                    }
                    layoutParams.bottomMargin = i4;
                    if (!(remeasuringLinearLayout3 instanceof QuickQSPanel) || z2) {
                        i5 = 0;
                    } else {
                        i5 = remeasuringLinearLayout3.mMediaTopMargin;
                    }
                    layoutParams.topMargin = i5;
                }
            }
            QSPanel.QSTileLayout qSTileLayout2 = remeasuringLinearLayout3.mTileLayout;
            if (z2) {
                i2 = 2;
            } else {
                i2 = 1;
            }
            qSTileLayout2.setMinRows(i2);
            QSPanel.QSTileLayout qSTileLayout3 = remeasuringLinearLayout3.mTileLayout;
            if (!z2) {
                i6 = 4;
            }
            qSTileLayout3.setMaxColumns(i6);
            remeasuringLinearLayout3.updateMediaHostContentMargins(hostView);
            RemeasuringLinearLayout remeasuringLinearLayout5 = remeasuringLinearLayout3.mHorizontalLinearLayout;
            if (remeasuringLinearLayout5 != null && !(!(remeasuringLinearLayout3 instanceof QuickQSPanel))) {
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) remeasuringLinearLayout5.getLayoutParams();
                layoutParams2.bottomMargin = Math.max(remeasuringLinearLayout3.mMediaTotalBottomMargin - remeasuringLinearLayout3.getPaddingBottom(), 0);
                remeasuringLinearLayout3.mHorizontalLinearLayout.setLayoutParams(layoutParams2);
            }
            remeasuringLinearLayout3.updatePadding();
            RemeasuringLinearLayout remeasuringLinearLayout6 = remeasuringLinearLayout3.mHorizontalLinearLayout;
            if (!z2) {
                i7 = 8;
            }
            remeasuringLinearLayout6.setVisibility(i7);
        }
        updateMediaDisappearParameters();
        Runnable runnable = this.mUsingHorizontalLayoutChangedListener;
        if (runnable != null) {
            runnable.run();
        }
        return true;
    }

    public final void updateMediaDisappearParameters() {
        if (this.mUsingMediaPlayer) {
            DisappearParameters disappearParameters = this.mMediaHost.getDisappearParameters();
            if (this.mUsingHorizontalLayout) {
                Objects.requireNonNull(disappearParameters);
                disappearParameters.disappearSize.set(0.0f, 0.4f);
                disappearParameters.gonePivot.set(1.0f, 1.0f);
                disappearParameters.contentTranslationFraction.set(0.25f, 1.0f);
                disappearParameters.disappearEnd = 0.6f;
            } else {
                Objects.requireNonNull(disappearParameters);
                disappearParameters.disappearSize.set(1.0f, 0.0f);
                disappearParameters.gonePivot.set(0.0f, 1.0f);
                disappearParameters.contentTranslationFraction.set(0.0f, 1.05f);
                disappearParameters.disappearEnd = 0.95f;
            }
            Objects.requireNonNull(disappearParameters);
            disappearParameters.fadeStartPosition = 0.95f;
            disappearParameters.disappearStart = 0.0f;
            MediaHost mediaHost = this.mMediaHost;
            Objects.requireNonNull(mediaHost);
            mediaHost.state.setDisappearParameters(disappearParameters);
        }
    }

    public QSPanelControllerBase(T t, QSTileHost qSTileHost, QSCustomizerController qSCustomizerController, boolean z, MediaHost mediaHost, MetricsLogger metricsLogger, UiEventLogger uiEventLogger, QSLogger qSLogger, DumpManager dumpManager) {
        super(t);
        this.mHost = qSTileHost;
        this.mQsCustomizerController = qSCustomizerController;
        this.mUsingMediaPlayer = z;
        this.mMediaHost = mediaHost;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mQSLogger = qSLogger;
        this.mDumpManager = dumpManager;
        this.mShouldUseSplitNotificationShade = Utils.shouldUseSplitNotificationShade(getResources());
    }

    public void onViewAttached() {
        QSTileRevealController createTileRevealController = createTileRevealController();
        this.mQsTileRevealController = createTileRevealController;
        if (createTileRevealController != null) {
            if (this.mRevealExpansion == 1.0f) {
                createTileRevealController.mHandler.postDelayed(createTileRevealController.mRevealQsTiles, 500);
            } else {
                createTileRevealController.mHandler.removeCallbacks(createTileRevealController.mRevealQsTiles);
            }
        }
        MediaHost mediaHost = this.mMediaHost;
        QSPanelControllerBase$$ExternalSyntheticLambda1 qSPanelControllerBase$$ExternalSyntheticLambda1 = this.mMediaHostVisibilityListener;
        Objects.requireNonNull(mediaHost);
        mediaHost.visibleChangedListeners.add(qSPanelControllerBase$$ExternalSyntheticLambda1);
        QSPanel qSPanel = (QSPanel) this.mView;
        QSPanel.OnConfigurationChangedListener onConfigurationChangedListener = this.mOnConfigurationChangedListener;
        Objects.requireNonNull(qSPanel);
        qSPanel.mOnConfigurationChangedListeners.add(onConfigurationChangedListener);
        QSTileHost qSTileHost = this.mHost;
        QSPanelControllerBase$$ExternalSyntheticLambda0 qSPanelControllerBase$$ExternalSyntheticLambda0 = this.mQSHostCallback;
        Objects.requireNonNull(qSTileHost);
        qSTileHost.mCallbacks.add(qSPanelControllerBase$$ExternalSyntheticLambda0);
        setTiles();
        this.mLastOrientation = getResources().getConfiguration().orientation;
        switchTileLayout(true);
        this.mDumpManager.registerDumpable(((QSPanel) this.mView).getDumpableTag(), this);
    }

    public final void setTiles(Collection<QSTile> collection, boolean z) {
        QSTileRevealController qSTileRevealController;
        if (!z && (qSTileRevealController = this.mQsTileRevealController) != null) {
            ArraySet arraySet = new ArraySet();
            for (QSTile tileSpec : collection) {
                arraySet.add(tileSpec.getTileSpec());
            }
            Context context = qSTileRevealController.mContext;
            Set<String> stringSet = context.getSharedPreferences(context.getPackageName(), 0).getStringSet("QsTileSpecsRevealed", Collections.EMPTY_SET);
            if (!stringSet.isEmpty()) {
                QSCustomizerController qSCustomizerController = qSTileRevealController.mQsCustomizerController;
                Objects.requireNonNull(qSCustomizerController);
                if (!((QSCustomizer) qSCustomizerController.mView).isCustomizing()) {
                    arraySet.removeAll(stringSet);
                    qSTileRevealController.mTilesToReveal.addAll(arraySet);
                }
            }
            qSTileRevealController.addTileSpecsToRevealed(arraySet);
        }
        Iterator<TileRecord> it = this.mRecords.iterator();
        while (it.hasNext()) {
            TileRecord next = it.next();
            QSPanel qSPanel = (QSPanel) this.mView;
            Objects.requireNonNull(qSPanel);
            qSPanel.mTileLayout.removeTile(next);
            next.tile.removeCallback(next.callback);
        }
        this.mRecords.clear();
        this.mCachedSpecs = "";
        for (QSTile next2 : collection) {
            QSTileHost qSTileHost = this.mHost;
            Context context2 = getContext();
            Objects.requireNonNull(qSTileHost);
            int i = 0;
            while (i < qSTileHost.mQsFactories.size()) {
                QSTileView createTileView = qSTileHost.mQsFactories.get(i).createTileView(context2, next2, z);
                if (createTileView != null) {
                    TileRecord tileRecord = new TileRecord(next2, createTileView);
                    QSPanel qSPanel2 = (QSPanel) this.mView;
                    Objects.requireNonNull(qSPanel2);
                    QSPanel.C09941 r3 = new QSTile.Callback(tileRecord) {
                        public final /* synthetic */ QSPanelControllerBase.TileRecord val$tileRecord;

                        public final void onStateChanged(
/*
Method generation error in method: com.android.systemui.qs.QSPanel.1.onStateChanged(com.android.systemui.plugins.qs.QSTile$State):void, dex: classes.dex
                        jadx.core.utils.exceptions.JadxRuntimeException: Method args not loaded: com.android.systemui.qs.QSPanel.1.onStateChanged(com.android.systemui.plugins.qs.QSTile$State):void, class status: UNLOADED
                        	at jadx.core.dex.nodes.MethodNode.getArgRegs(MethodNode.java:278)
                        	at jadx.core.codegen.MethodGen.addDefinition(MethodGen.java:116)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:313)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.InsnGen.inlineAnonymousConstructor(InsnGen.java:676)
                        	at jadx.core.codegen.InsnGen.makeConstructor(InsnGen.java:607)
                        	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:364)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:250)
                        	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:221)
                        	at jadx.core.codegen.RegionGen.makeSimpleBlock(RegionGen.java:109)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:55)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeIf(RegionGen.java:142)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:62)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:250)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.RegionGen.makeRegionIndent(RegionGen.java:98)
                        	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:232)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:66)
                        	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:92)
                        	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:58)
                        	at jadx.core.codegen.MethodGen.addRegionInsns(MethodGen.java:211)
                        	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:204)
                        	at jadx.core.codegen.ClassGen.addMethodCode(ClassGen.java:318)
                        	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:271)
                        	at jadx.core.codegen.ClassGen.lambda$addInnerClsAndMethods$2(ClassGen.java:240)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.accept(ForEachOps.java:183)
                        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1540)
                        	at java.base/java.util.stream.SortedOps$RefSortingSink.end(SortedOps.java:395)
                        	at java.base/java.util.stream.Sink$ChainedReference.end(Sink.java:258)
                        	at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:485)
                        	at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:474)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp.evaluateSequential(ForEachOps.java:150)
                        	at java.base/java.util.stream.ForEachOps$ForEachOp$OfRef.evaluateSequential(ForEachOps.java:173)
                        	at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
                        	at java.base/java.util.stream.ReferencePipeline.forEach(ReferencePipeline.java:497)
                        	at jadx.core.codegen.ClassGen.addInnerClsAndMethods(ClassGen.java:236)
                        	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:227)
                        	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:112)
                        	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:78)
                        	at jadx.core.codegen.CodeGen.wrapCodeGen(CodeGen.java:44)
                        	at jadx.core.codegen.CodeGen.generateJavaCode(CodeGen.java:33)
                        	at jadx.core.codegen.CodeGen.generate(CodeGen.java:21)
                        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:61)
                        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:273)
                        
*/
                    };
                    tileRecord.tile.addCallback(r3);
                    tileRecord.callback = r3;
                    tileRecord.tileView.init(tileRecord.tile);
                    tileRecord.tile.refreshState();
                    QSPanel.QSTileLayout qSTileLayout = qSPanel2.mTileLayout;
                    if (qSTileLayout != null) {
                        qSTileLayout.addTile(tileRecord);
                    }
                    this.mRecords.add(tileRecord);
                    this.mCachedSpecs = (String) this.mRecords.stream().map(WifiPickerTracker$$ExternalSyntheticLambda11.INSTANCE$2).collect(Collectors.joining(","));
                } else {
                    i++;
                }
            }
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Default factory didn't create view for ");
            m.append(next2.getTileSpec());
            throw new RuntimeException(m.toString());
        }
    }
}
