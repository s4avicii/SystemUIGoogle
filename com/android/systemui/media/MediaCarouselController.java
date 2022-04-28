package com.android.systemui.media;

import android.app.smartspace.SmartspaceAction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.MathUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.GridLayoutManager$$ExternalSyntheticOutline0;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.Dumpable;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.media.MediaDataManager;
import com.android.systemui.media.MediaHostStatesManager;
import com.android.systemui.media.MediaPlayerData;
import com.android.systemui.media.MediaViewController;
import com.android.systemui.p006qs.PageIndicator;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.FalsingManager;
import com.android.systemui.shared.system.SysUiStatsLog;
import com.android.systemui.statusbar.notification.collection.provider.OnReorderingAllowedListener;
import com.android.systemui.statusbar.notification.collection.provider.VisualStabilityProvider;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.Utils;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.animation.UniqueObjectHostView;
import com.android.systemui.util.concurrency.DelayableExecutor;
import com.android.systemui.util.time.SystemClock;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import javax.inject.Provider;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IteratorsJVMKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.SetsKt__SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaCarouselController.kt */
public final class MediaCarouselController implements Dumpable {
    public final ActivityStarter activityStarter;
    public int bgColor;
    public int carouselMeasureHeight;
    public int carouselMeasureWidth;
    public final Context context;
    public int currentCarouselHeight;
    public int currentCarouselWidth;
    public int currentEndLocation = -1;
    public int currentStartLocation = -1;
    public float currentTransitionProgress = 1.0f;
    public boolean currentlyExpanded;
    public boolean currentlyShowingOnlyActive;
    public MediaHostState desiredHostState;
    public int desiredLocation = -1;
    public boolean isRtl;
    public LinkedHashSet keysNeedRemoval = new LinkedHashSet();
    public final MediaScrollView mediaCarousel;
    public final MediaCarouselScrollHandler mediaCarouselScrollHandler;
    public final ViewGroup mediaContent;
    public final Provider<MediaControlPanel> mediaControlPanelFactory;
    public final MediaFlags mediaFlags;
    public final ViewGroup mediaFrame;
    public final MediaHostStatesManager mediaHostStatesManager;
    public final MediaDataManager mediaManager;
    public boolean needsReordering;
    public final PageIndicator pageIndicator;
    public boolean playersVisible;
    public View settingsButton;
    public boolean shouldScrollToActivePlayer;
    public final SystemClock systemClock;
    public Function0<Unit> updateUserVisibility;
    public final C08735 visualStabilityCallback;
    public final VisualStabilityProvider visualStabilityProvider;

    public MediaCarouselController(Context context2, Provider<MediaControlPanel> provider, VisualStabilityProvider visualStabilityProvider2, MediaHostStatesManager mediaHostStatesManager2, ActivityStarter activityStarter2, SystemClock systemClock2, DelayableExecutor delayableExecutor, MediaDataManager mediaDataManager, ConfigurationController configurationController, FalsingCollector falsingCollector, FalsingManager falsingManager, DumpManager dumpManager, MediaFlags mediaFlags2) {
        Context context3 = context2;
        VisualStabilityProvider visualStabilityProvider3 = visualStabilityProvider2;
        MediaHostStatesManager mediaHostStatesManager3 = mediaHostStatesManager2;
        MediaDataManager mediaDataManager2 = mediaDataManager;
        this.context = context3;
        this.mediaControlPanelFactory = provider;
        this.visualStabilityProvider = visualStabilityProvider3;
        this.mediaHostStatesManager = mediaHostStatesManager3;
        this.activityStarter = activityStarter2;
        this.systemClock = systemClock2;
        this.mediaManager = mediaDataManager2;
        this.mediaFlags = mediaFlags2;
        this.bgColor = context3.getColor(C1777R.color.material_dynamic_secondary95);
        boolean z = true;
        this.currentlyExpanded = true;
        MediaCarouselController$configListener$1 mediaCarouselController$configListener$1 = new MediaCarouselController$configListener$1(this);
        dumpManager.registerDumpable("MediaCarouselController", this);
        int i = 0;
        View inflate = LayoutInflater.from(context2).inflate(C1777R.layout.media_carousel, new UniqueObjectHostView(context3), false);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.view.ViewGroup");
        ViewGroup viewGroup = (ViewGroup) inflate;
        viewGroup.setLayoutDirection(3);
        this.mediaFrame = viewGroup;
        MediaScrollView mediaScrollView = (MediaScrollView) viewGroup.requireViewById(C1777R.C1779id.media_carousel_scroller);
        this.mediaCarousel = mediaScrollView;
        PageIndicator pageIndicator2 = (PageIndicator) viewGroup.requireViewById(C1777R.C1779id.media_page_indicator);
        this.pageIndicator = pageIndicator2;
        MediaCarouselScrollHandler mediaCarouselScrollHandler2 = new MediaCarouselScrollHandler(mediaScrollView, pageIndicator2, delayableExecutor, new Function0<Unit>(this) {
            public final Object invoke() {
                boolean z;
                MediaCarouselController mediaCarouselController = (MediaCarouselController) this.receiver;
                Objects.requireNonNull(mediaCarouselController);
                Objects.requireNonNull(MediaPlayerData.INSTANCE);
                Iterator it = MediaPlayerData.players().iterator();
                int i = 0;
                while (true) {
                    MediaDataManager mediaDataManager = null;
                    if (it.hasNext()) {
                        Object next = it.next();
                        int i2 = i + 1;
                        if (i >= 0) {
                            MediaControlPanel mediaControlPanel = (MediaControlPanel) next;
                            if (mediaControlPanel.mIsImpressed) {
                                int i3 = mediaControlPanel.mInstanceId;
                                int i4 = mediaControlPanel.mUid;
                                if (mediaControlPanel.mRecommendationViewHolder != null) {
                                    z = true;
                                } else {
                                    z = false;
                                }
                                MediaCarouselController.logSmartspaceCardReported$default(mediaCarouselController, 761, i3, i4, z, new int[]{mediaControlPanel.getSurfaceForSmartspaceLogging()}, 0, 0, -1, 0, 352);
                                mediaControlPanel.mIsImpressed = false;
                            }
                            i = i2;
                        } else {
                            SetsKt__SetsKt.throwIndexOverflow();
                            throw null;
                        }
                    } else {
                        MediaDataManager mediaDataManager2 = mediaCarouselController.mediaManager;
                        Objects.requireNonNull(mediaDataManager2);
                        MediaDataFilter mediaDataFilter = mediaDataManager2.mediaDataFilter;
                        Objects.requireNonNull(mediaDataFilter);
                        Log.d("MediaDataFilter", "Media carousel swiped away");
                        for (String str : CollectionsKt___CollectionsKt.toSet(mediaDataFilter.userEntries.keySet())) {
                            MediaDataManager mediaDataManager3 = mediaDataFilter.mediaDataManager;
                            if (mediaDataManager3 == null) {
                                mediaDataManager3 = null;
                            }
                            mediaDataManager3.mo8906x855293df(str, true, true);
                        }
                        SmartspaceMediaData smartspaceMediaData = mediaDataFilter.smartspaceMediaData;
                        Objects.requireNonNull(smartspaceMediaData);
                        if (smartspaceMediaData.isActive) {
                            SmartspaceMediaData smartspaceMediaData2 = mediaDataFilter.smartspaceMediaData;
                            Objects.requireNonNull(smartspaceMediaData2);
                            Intent intent = smartspaceMediaData2.dismissIntent;
                            if (intent == null) {
                                Log.w("MediaDataFilter", "Cannot create dismiss action click action: extras missing dismiss_intent.");
                            } else if (intent.getComponent() == null || !Intrinsics.areEqual(intent.getComponent().getClassName(), "com.google.android.apps.gsa.staticplugins.opa.smartspace.ExportedSmartspaceTrampolineActivity")) {
                                mediaDataFilter.context.sendBroadcast(intent);
                            } else {
                                mediaDataFilter.context.startActivity(intent);
                            }
                            SmartspaceMediaData smartspaceMediaData3 = MediaDataManagerKt.EMPTY_SMARTSPACE_MEDIA_DATA;
                            SmartspaceMediaData smartspaceMediaData4 = mediaDataFilter.smartspaceMediaData;
                            Objects.requireNonNull(smartspaceMediaData4);
                            String str2 = smartspaceMediaData4.targetId;
                            SmartspaceMediaData smartspaceMediaData5 = mediaDataFilter.smartspaceMediaData;
                            Objects.requireNonNull(smartspaceMediaData5);
                            mediaDataFilter.smartspaceMediaData = SmartspaceMediaData.copy$default(smartspaceMediaData3, str2, false, smartspaceMediaData5.isValid, (Intent) null, 0, 0, 506);
                        }
                        MediaDataManager mediaDataManager4 = mediaDataFilter.mediaDataManager;
                        if (mediaDataManager4 != null) {
                            mediaDataManager = mediaDataManager4;
                        }
                        SmartspaceMediaData smartspaceMediaData6 = mediaDataFilter.smartspaceMediaData;
                        Objects.requireNonNull(smartspaceMediaData6);
                        mediaDataManager.dismissSmartspaceRecommendation(smartspaceMediaData6.targetId, 0);
                        return Unit.INSTANCE;
                    }
                }
            }
        }, new Function0<Unit>(this) {
            public final Object invoke() {
                ((MediaCarouselController) this.receiver).updatePageIndicatorLocation();
                return Unit.INSTANCE;
            }
        }, new Function1<Boolean, Unit>(this) {
            public final Object invoke(Object obj) {
                boolean booleanValue = ((Boolean) obj).booleanValue();
                Objects.requireNonNull((MediaCarouselController) this.receiver);
                Objects.requireNonNull(MediaPlayerData.INSTANCE);
                for (MediaControlPanel closeGuts : MediaPlayerData.players()) {
                    closeGuts.closeGuts(booleanValue);
                }
                return Unit.INSTANCE;
            }
        }, falsingCollector, falsingManager, new Function1<Boolean, Unit>(this) {
            public final Object invoke(Object obj) {
                ((MediaCarouselController) this.receiver).logSmartspaceImpression(((Boolean) obj).booleanValue());
                return Unit.INSTANCE;
            }
        });
        this.mediaCarouselScrollHandler = mediaCarouselScrollHandler2;
        z = context2.getResources().getConfiguration().getLayoutDirection() != 1 ? false : z;
        if (z != this.isRtl) {
            this.isRtl = z;
            viewGroup.setLayoutDirection(z ? 1 : 0);
            Objects.requireNonNull(mediaCarouselScrollHandler2);
            Objects.requireNonNull(mediaScrollView);
            if (mediaScrollView.isLayoutRtl()) {
                ViewGroup viewGroup2 = mediaScrollView.contentContainer;
                i = 0 + ((viewGroup2 == null ? null : viewGroup2).getWidth() - mediaScrollView.getWidth());
            }
            mediaScrollView.setScrollX(i);
        }
        inflateSettingsButton();
        this.mediaContent = (ViewGroup) mediaScrollView.requireViewById(C1777R.C1779id.media_carousel);
        configurationController.addCallback(mediaCarouselController$configListener$1);
        C08735 r1 = new OnReorderingAllowedListener(this) {
            public final /* synthetic */ MediaCarouselController this$0;

            {
                this.this$0 = r1;
            }

            public final void onReorderingAllowed() {
                MediaCarouselController mediaCarouselController = this.this$0;
                ViewGroup viewGroup = null;
                int i = 0;
                if (mediaCarouselController.needsReordering) {
                    mediaCarouselController.needsReordering = false;
                    mediaCarouselController.reorderAllPlayers((MediaPlayerData.MediaSortKey) null);
                }
                MediaCarouselController mediaCarouselController2 = this.this$0;
                for (String removePlayer : mediaCarouselController2.keysNeedRemoval) {
                    mediaCarouselController2.removePlayer(removePlayer, true, true);
                }
                this.this$0.keysNeedRemoval.clear();
                Function0<Unit> function0 = this.this$0.updateUserVisibility;
                if (function0 != null) {
                    if (function0 == null) {
                        function0 = null;
                    }
                    function0.invoke();
                }
                MediaCarouselController mediaCarouselController3 = this.this$0;
                Objects.requireNonNull(mediaCarouselController3);
                MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController3.mediaCarouselScrollHandler;
                Objects.requireNonNull(mediaCarouselScrollHandler);
                MediaScrollView mediaScrollView = mediaCarouselScrollHandler.scrollView;
                Objects.requireNonNull(mediaScrollView);
                if (mediaScrollView.isLayoutRtl()) {
                    ViewGroup viewGroup2 = mediaScrollView.contentContainer;
                    if (viewGroup2 != null) {
                        viewGroup = viewGroup2;
                    }
                    i = 0 + (viewGroup.getWidth() - mediaScrollView.getWidth());
                }
                mediaScrollView.setScrollX(i);
            }
        };
        this.visualStabilityCallback = r1;
        Objects.requireNonNull(visualStabilityProvider2);
        visualStabilityProvider3.temporaryListeners.remove(r1);
        visualStabilityProvider3.allListeners.addIfAbsent(r1);
        mediaDataManager2.addListener(new MediaDataManager.Listener(this) {
            public final /* synthetic */ MediaCarouselController this$0;

            {
                this.this$0 = r1;
            }

            public final void onMediaDataLoaded(String str, String str2, MediaData mediaData, boolean z, int i) {
                boolean z2;
                MediaCarouselController mediaCarouselController;
                String str3 = str;
                MediaData mediaData2 = mediaData;
                Boolean bool = null;
                boolean z3 = false;
                if (this.this$0.addOrUpdatePlayer(str3, str2, mediaData2)) {
                    Objects.requireNonNull(MediaPlayerData.INSTANCE);
                    MediaControlPanel mediaPlayer = MediaPlayerData.getMediaPlayer(str);
                    if (mediaPlayer != null) {
                        MediaCarouselController.logSmartspaceCardReported$default(this.this$0, 759, mediaPlayer.mInstanceId, mediaPlayer.mUid, false, new int[]{4, 2}, 0, 0, MediaPlayerData.getMediaPlayerIndex(str), 0, 352);
                    }
                    MediaCarouselController mediaCarouselController2 = this.this$0;
                    Objects.requireNonNull(mediaCarouselController2);
                    MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController2.mediaCarouselScrollHandler;
                    Objects.requireNonNull(mediaCarouselScrollHandler);
                    if (mediaCarouselScrollHandler.visibleToUser) {
                        MediaCarouselController mediaCarouselController3 = this.this$0;
                        Objects.requireNonNull(mediaCarouselController3);
                        MediaCarouselScrollHandler mediaCarouselScrollHandler2 = mediaCarouselController3.mediaCarouselScrollHandler;
                        Objects.requireNonNull(mediaCarouselScrollHandler2);
                        if (mediaCarouselScrollHandler2.visibleMediaIndex == MediaPlayerData.getMediaPlayerIndex(str)) {
                            MediaCarouselController mediaCarouselController4 = this.this$0;
                            Objects.requireNonNull(mediaCarouselController4);
                            MediaCarouselScrollHandler mediaCarouselScrollHandler3 = mediaCarouselController4.mediaCarouselScrollHandler;
                            Objects.requireNonNull(mediaCarouselScrollHandler3);
                            mediaCarouselController4.logSmartspaceImpression(mediaCarouselScrollHandler3.qsExpanded);
                        }
                    }
                } else if (i != 0) {
                    Objects.requireNonNull(MediaPlayerData.INSTANCE);
                    Collection players = MediaPlayerData.players();
                    MediaCarouselController mediaCarouselController5 = this.this$0;
                    int i2 = 0;
                    for (Object next : players) {
                        int i3 = i2 + 1;
                        if (i2 >= 0) {
                            MediaControlPanel mediaControlPanel = (MediaControlPanel) next;
                            Objects.requireNonNull(mediaControlPanel);
                            if (mediaControlPanel.mRecommendationViewHolder == null) {
                                int abs = Math.abs(Math.floorMod(mediaControlPanel.mUid + ((int) mediaCarouselController5.systemClock.currentTimeMillis()), 8192));
                                mediaControlPanel.mInstanceId = abs;
                                mediaControlPanel.mIsImpressed = false;
                                mediaCarouselController = mediaCarouselController5;
                                MediaCarouselController.logSmartspaceCardReported$default(mediaCarouselController5, 759, abs, mediaControlPanel.mUid, false, new int[]{4, 2}, 0, 0, i2, i, 96);
                            } else {
                                mediaCarouselController = mediaCarouselController5;
                            }
                            i2 = i3;
                            mediaCarouselController5 = mediaCarouselController;
                        } else {
                            SetsKt__SetsKt.throwIndexOverflow();
                            throw null;
                        }
                    }
                    MediaCarouselController mediaCarouselController6 = this.this$0;
                    Objects.requireNonNull(mediaCarouselController6);
                    MediaCarouselScrollHandler mediaCarouselScrollHandler4 = mediaCarouselController6.mediaCarouselScrollHandler;
                    Objects.requireNonNull(mediaCarouselScrollHandler4);
                    if (mediaCarouselScrollHandler4.visibleToUser) {
                        MediaCarouselController mediaCarouselController7 = this.this$0;
                        Objects.requireNonNull(mediaCarouselController7);
                        MediaCarouselScrollHandler mediaCarouselScrollHandler5 = mediaCarouselController7.mediaCarouselScrollHandler;
                        Objects.requireNonNull(mediaCarouselScrollHandler5);
                        if (!mediaCarouselScrollHandler5.qsExpanded) {
                            MediaCarouselController mediaCarouselController8 = this.this$0;
                            Objects.requireNonNull(mediaCarouselController8);
                            MediaCarouselScrollHandler mediaCarouselScrollHandler6 = mediaCarouselController8.mediaCarouselScrollHandler;
                            Objects.requireNonNull(mediaCarouselScrollHandler6);
                            mediaCarouselController8.logSmartspaceImpression(mediaCarouselScrollHandler6.qsExpanded);
                        }
                    }
                }
                Boolean bool2 = mediaData2.isPlaying;
                if (bool2 != null) {
                    bool = Boolean.valueOf(!bool2.booleanValue());
                }
                if (bool == null) {
                    z2 = mediaData2.isClearable;
                } else {
                    z2 = bool.booleanValue();
                }
                if (z2 && !mediaData2.active) {
                    z3 = true;
                }
                if (!z3 || Utils.useMediaResumption(this.this$0.context)) {
                    this.this$0.keysNeedRemoval.remove(str3);
                    return;
                }
                MediaCarouselController mediaCarouselController9 = this.this$0;
                Objects.requireNonNull(mediaCarouselController9);
                VisualStabilityProvider visualStabilityProvider = mediaCarouselController9.visualStabilityProvider;
                Objects.requireNonNull(visualStabilityProvider);
                if (visualStabilityProvider.isReorderingAllowed) {
                    onMediaDataRemoved(str);
                } else {
                    this.this$0.keysNeedRemoval.add(str3);
                }
            }

            public final void onMediaDataRemoved(String str) {
                this.this$0.removePlayer(str, true, true);
            }

            public final void onSmartspaceMediaDataLoaded(String str, SmartspaceMediaData smartspaceMediaData, boolean z, boolean z2) {
                Iterator it;
                boolean z3;
                MediaCarouselController mediaCarouselController;
                SmartspaceMediaData smartspaceMediaData2 = smartspaceMediaData;
                boolean z4 = z;
                if (MediaCarouselControllerKt.DEBUG) {
                    Log.d("MediaCarouselController", "Loading Smartspace media update");
                }
                if (smartspaceMediaData2.isActive) {
                    int i = 2;
                    if (z2 && z4) {
                        Objects.requireNonNull(MediaPlayerData.INSTANCE);
                        Collection players = MediaPlayerData.players();
                        MediaCarouselController mediaCarouselController2 = this.this$0;
                        Iterator it2 = players.iterator();
                        boolean z5 = false;
                        int i2 = 0;
                        while (it2.hasNext()) {
                            Object next = it2.next();
                            int i3 = i2 + 1;
                            if (i2 >= 0) {
                                MediaControlPanel mediaControlPanel = (MediaControlPanel) next;
                                Objects.requireNonNull(mediaControlPanel);
                                if (mediaControlPanel.mRecommendationViewHolder == null) {
                                    int abs = Math.abs(Math.floorMod(mediaControlPanel.mUid + ((int) mediaCarouselController2.systemClock.currentTimeMillis()), 8192));
                                    mediaControlPanel.mInstanceId = abs;
                                    mediaControlPanel.mIsImpressed = z5;
                                    int[] iArr = new int[i];
                                    // fill-array-data instruction
                                    iArr[0] = 4;
                                    iArr[1] = 2;
                                    it = it2;
                                    z3 = z5;
                                    mediaCarouselController = mediaCarouselController2;
                                    MediaCarouselController.logSmartspaceCardReported$default(mediaCarouselController2, 759, abs, mediaControlPanel.mUid, false, iArr, 0, 0, i2, (int) (mediaCarouselController2.systemClock.currentTimeMillis() - smartspaceMediaData2.headphoneConnectionTimeMillis), 96);
                                } else {
                                    it = it2;
                                    z3 = z5;
                                    mediaCarouselController = mediaCarouselController2;
                                }
                                it2 = it;
                                mediaCarouselController2 = mediaCarouselController;
                                i2 = i3;
                                z5 = z3;
                                i = 2;
                            } else {
                                SetsKt__SetsKt.throwIndexOverflow();
                                throw null;
                            }
                        }
                    }
                    this.this$0.addSmartspaceMediaRecommendations(str, smartspaceMediaData2, z4);
                    Objects.requireNonNull(MediaPlayerData.INSTANCE);
                    MediaControlPanel mediaPlayer = MediaPlayerData.getMediaPlayer(str);
                    if (mediaPlayer != null) {
                        MediaCarouselController mediaCarouselController3 = this.this$0;
                        MediaCarouselController.logSmartspaceCardReported$default(mediaCarouselController3, 759, mediaPlayer.mInstanceId, mediaPlayer.mUid, true, new int[]{4, 2}, 0, 0, MediaPlayerData.getMediaPlayerIndex(str), (int) (mediaCarouselController3.systemClock.currentTimeMillis() - smartspaceMediaData2.headphoneConnectionTimeMillis), 96);
                    }
                    MediaCarouselController mediaCarouselController4 = this.this$0;
                    Objects.requireNonNull(mediaCarouselController4);
                    MediaCarouselScrollHandler mediaCarouselScrollHandler = mediaCarouselController4.mediaCarouselScrollHandler;
                    Objects.requireNonNull(mediaCarouselScrollHandler);
                    if (mediaCarouselScrollHandler.visibleToUser) {
                        MediaCarouselController mediaCarouselController5 = this.this$0;
                        Objects.requireNonNull(mediaCarouselController5);
                        MediaCarouselScrollHandler mediaCarouselScrollHandler2 = mediaCarouselController5.mediaCarouselScrollHandler;
                        Objects.requireNonNull(mediaCarouselScrollHandler2);
                        if (mediaCarouselScrollHandler2.visibleMediaIndex == MediaPlayerData.getMediaPlayerIndex(str)) {
                            MediaCarouselController mediaCarouselController6 = this.this$0;
                            Objects.requireNonNull(mediaCarouselController6);
                            MediaCarouselScrollHandler mediaCarouselScrollHandler3 = mediaCarouselController6.mediaCarouselScrollHandler;
                            Objects.requireNonNull(mediaCarouselScrollHandler3);
                            mediaCarouselController6.logSmartspaceImpression(mediaCarouselScrollHandler3.qsExpanded);
                            return;
                        }
                        return;
                    }
                    return;
                }
                onSmartspaceMediaDataRemoved(smartspaceMediaData2.targetId, true);
            }

            public final void onSmartspaceMediaDataRemoved(String str, boolean z) {
                if (MediaCarouselControllerKt.DEBUG) {
                    Log.d("MediaCarouselController", "My Smartspace media removal request is received");
                }
                if (!z) {
                    MediaCarouselController mediaCarouselController = this.this$0;
                    Objects.requireNonNull(mediaCarouselController);
                    VisualStabilityProvider visualStabilityProvider = mediaCarouselController.visualStabilityProvider;
                    Objects.requireNonNull(visualStabilityProvider);
                    if (!visualStabilityProvider.isReorderingAllowed) {
                        this.this$0.keysNeedRemoval.add(str);
                        return;
                    }
                }
                onMediaDataRemoved(str);
            }
        });
        viewGroup.addOnLayoutChangeListener(new View.OnLayoutChangeListener(this) {
            public final /* synthetic */ MediaCarouselController this$0;

            {
                this.this$0 = r1;
            }

            public final void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                this.this$0.updatePageIndicatorLocation();
            }
        });
        C08768 r12 = new MediaHostStatesManager.Callback(this) {
            public final /* synthetic */ MediaCarouselController this$0;

            {
                this.this$0 = r1;
            }

            public final void onHostStateChanged(int i, MediaHostState mediaHostState) {
                MediaCarouselController mediaCarouselController = this.this$0;
                int i2 = mediaCarouselController.desiredLocation;
                if (i == i2) {
                    mediaCarouselController.onDesiredLocationChanged(i2, mediaHostState, false, 200, 0);
                }
            }
        };
        Objects.requireNonNull(mediaHostStatesManager2);
        mediaHostStatesManager3.callbacks.add(r12);
    }

    public static void logSmartspaceCardReported$default(MediaCarouselController mediaCarouselController, int i, int i2, int i3, boolean z, int[] iArr, int i4, int i5, int i6, int i7, int i8) {
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        MediaCarouselController mediaCarouselController2 = mediaCarouselController;
        boolean z2 = z;
        int[] iArr2 = iArr;
        int i15 = i8;
        int i16 = 0;
        if ((i15 & 32) != 0) {
            i9 = 0;
        } else {
            i9 = i4;
        }
        if ((i15 & 64) != 0) {
            i10 = 0;
        } else {
            i10 = i5;
        }
        if ((i15 & 128) != 0) {
            MediaCarouselScrollHandler mediaCarouselScrollHandler2 = mediaCarouselController2.mediaCarouselScrollHandler;
            Objects.requireNonNull(mediaCarouselScrollHandler2);
            i11 = mediaCarouselScrollHandler2.visibleMediaIndex;
        } else {
            i11 = i6;
        }
        if ((i15 & 256) != 0) {
            i12 = 0;
        } else {
            i12 = i7;
        }
        Objects.requireNonNull(mediaCarouselController);
        if (!z2) {
            MediaDataManager mediaDataManager = mediaCarouselController2.mediaManager;
            Objects.requireNonNull(mediaDataManager);
            SmartspaceMediaData smartspaceMediaData = mediaDataManager.smartspaceMediaData;
            Objects.requireNonNull(smartspaceMediaData);
            if (!smartspaceMediaData.isActive) {
                Objects.requireNonNull(MediaPlayerData.INSTANCE);
                if (MediaPlayerData.smartspaceMediaData == null) {
                    return;
                }
            }
        }
        int childCount = mediaCarouselController2.mediaContent.getChildCount();
        int length = iArr2.length;
        while (i16 < length) {
            int i17 = iArr2[i16];
            int i18 = i16 + 1;
            if (z2) {
                i13 = 15;
            } else {
                i13 = 31;
            }
            int i19 = i17;
            int i20 = length;
            int i21 = i18;
            int i22 = i11;
            int i23 = i10;
            SysUiStatsLog.write(i, i2, i17, i11, childCount, i13, i3, i9, i10, i12);
            if (MediaCarouselControllerKt.DEBUG) {
                StringBuilder m = GridLayoutManager$$ExternalSyntheticOutline0.m19m("Log Smartspace card event id: ", i, " instance id: ", i2, " surface: ");
                m.append(i19);
                m.append(" rank: ");
                m.append(i22);
                m.append(" cardinality: ");
                m.append(childCount);
                m.append(" isRecommendationCard: ");
                m.append(z2);
                m.append(" uid: ");
                m.append(i3);
                m.append(" interactedSubcardRank: ");
                m.append(i9);
                m.append(" interactedSubcardCardinality: ");
                i14 = i23;
                m.append(i14);
                m.append(" received_latency_millis: ");
                m.append(i12);
                Log.d("MediaCarouselController", m.toString());
            } else {
                int i24 = i;
                int i25 = i2;
                int i26 = i3;
                i14 = i23;
            }
            iArr2 = iArr;
            i11 = i22;
            i10 = i14;
            length = i20;
            i16 = i21;
        }
    }

    public final boolean addOrUpdatePlayer(String str, String str2, MediaData mediaData) {
        TransitionLayout transitionLayout;
        Objects.requireNonNull(MediaPlayerData.INSTANCE);
        if (str2 != null && !Intrinsics.areEqual(str2, str)) {
            LinkedHashMap linkedHashMap = MediaPlayerData.mediaData;
            MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) linkedHashMap.remove(str2);
            if (mediaSortKey != null) {
                MediaPlayerData.removeMediaPlayer(str);
                MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) linkedHashMap.put(str, mediaSortKey);
            }
        }
        MediaControlPanel mediaPlayer = MediaPlayerData.getMediaPlayer(str);
        TreeMap<MediaPlayerData.MediaSortKey, MediaControlPanel> treeMap = MediaPlayerData.mediaPlayers;
        Set<MediaPlayerData.MediaSortKey> keySet = treeMap.keySet();
        MediaCarouselScrollHandler mediaCarouselScrollHandler2 = this.mediaCarouselScrollHandler;
        Objects.requireNonNull(mediaCarouselScrollHandler2);
        MediaPlayerData.MediaSortKey mediaSortKey3 = (MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(keySet, mediaCarouselScrollHandler2.visibleMediaIndex);
        if (mediaPlayer == null) {
            MediaControlPanel mediaControlPanel = this.mediaControlPanelFactory.get();
            if (this.mediaFlags.useMediaSessionLayout()) {
                Set<Integer> set = PlayerSessionViewHolder.controlsIds;
                View inflate = LayoutInflater.from(this.context).inflate(C1777R.layout.media_session_view, this.mediaContent, false);
                inflate.setLayerType(2, (Paint) null);
                inflate.setLayoutDirection(3);
                PlayerSessionViewHolder playerSessionViewHolder = new PlayerSessionViewHolder(inflate);
                playerSessionViewHolder.seekBar.setLayoutDirection(0);
                mediaControlPanel.attachPlayer(playerSessionViewHolder, MediaViewController.TYPE.PLAYER_SESSION);
            } else {
                Set<Integer> set2 = PlayerViewHolder.controlsIds;
                View inflate2 = LayoutInflater.from(this.context).inflate(C1777R.layout.media_view, this.mediaContent, false);
                inflate2.setLayerType(2, (Paint) null);
                inflate2.setLayoutDirection(3);
                PlayerViewHolder playerViewHolder = new PlayerViewHolder(inflate2);
                playerViewHolder.seekBar.setLayoutDirection(0);
                playerViewHolder.progressTimes.setLayoutDirection(0);
                mediaControlPanel.attachPlayer(playerViewHolder, MediaViewController.TYPE.PLAYER);
            }
            Objects.requireNonNull(mediaControlPanel);
            MediaViewController mediaViewController = mediaControlPanel.mMediaViewController;
            MediaCarouselController$addOrUpdatePlayer$1 mediaCarouselController$addOrUpdatePlayer$1 = new MediaCarouselController$addOrUpdatePlayer$1(this);
            Objects.requireNonNull(mediaViewController);
            mediaViewController.sizeChangedListener = mediaCarouselController$addOrUpdatePlayer$1;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
            MediaViewHolder mediaViewHolder = mediaControlPanel.mMediaViewHolder;
            if (!(mediaViewHolder == null || (transitionLayout = mediaViewHolder.player) == null)) {
                transitionLayout.setLayoutParams(layoutParams);
            }
            mediaControlPanel.bindPlayer(mediaData, str);
            boolean z = this.currentlyExpanded;
            SeekBarViewModel seekBarViewModel = mediaControlPanel.mSeekBarViewModel;
            Objects.requireNonNull(seekBarViewModel);
            seekBarViewModel.bgExecutor.execute(new SeekBarViewModel$listening$1(seekBarViewModel, z));
            SystemClock systemClock2 = this.systemClock;
            MediaPlayerData.removeMediaPlayer(str);
            MediaPlayerData.MediaSortKey mediaSortKey4 = new MediaPlayerData.MediaSortKey(false, mediaData, systemClock2.currentTimeMillis());
            MediaPlayerData.mediaData.put(str, mediaSortKey4);
            treeMap.put(mediaSortKey4, mediaControlPanel);
            mediaControlPanel.mMediaViewController.setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, true);
            reorderAllPlayers(mediaSortKey3);
        } else {
            mediaPlayer.bindPlayer(mediaData, str);
            SystemClock systemClock3 = this.systemClock;
            MediaPlayerData.removeMediaPlayer(str);
            MediaPlayerData.MediaSortKey mediaSortKey5 = new MediaPlayerData.MediaSortKey(false, mediaData, systemClock3.currentTimeMillis());
            MediaPlayerData.mediaData.put(str, mediaSortKey5);
            treeMap.put(mediaSortKey5, mediaPlayer);
            VisualStabilityProvider visualStabilityProvider2 = this.visualStabilityProvider;
            Objects.requireNonNull(visualStabilityProvider2);
            if (visualStabilityProvider2.isReorderingAllowed || this.shouldScrollToActivePlayer) {
                reorderAllPlayers(mediaSortKey3);
            } else {
                this.needsReordering = true;
            }
        }
        updatePageIndicator();
        this.mediaCarouselScrollHandler.onPlayersChanged();
        this.mediaFrame.setTag(C1777R.C1779id.requires_remeasuring, Boolean.TRUE);
        if (MediaPlayerData.players().size() != this.mediaContent.getChildCount()) {
            Log.wtf("MediaCarouselController", "Size of players list and number of views in carousel are out of sync");
        }
        if (mediaPlayer == null) {
            return true;
        }
        return false;
    }

    public final void addSmartspaceMediaRecommendations(String str, SmartspaceMediaData smartspaceMediaData, boolean z) {
        String str2;
        String str3;
        String str4;
        List<ViewGroup> list;
        List<ImageView> list2;
        List<SmartspaceAction> list3;
        int i;
        boolean z2;
        TransitionLayout transitionLayout;
        String str5 = "MediaCarouselController";
        if (MediaCarouselControllerKt.DEBUG) {
            Log.d(str5, "Updating smartspace target in carousel");
        }
        Objects.requireNonNull(MediaPlayerData.INSTANCE);
        if (MediaPlayerData.getMediaPlayer(str) != null) {
            Log.w(str5, "Skip adding smartspace target in carousel");
            return;
        }
        Iterator it = MediaPlayerData.mediaData.entrySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                str2 = null;
                break;
            }
            Map.Entry entry = (Map.Entry) it.next();
            MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) entry.getValue();
            Objects.requireNonNull(mediaSortKey);
            if (mediaSortKey.isSsMediaRec) {
                str2 = (String) entry.getKey();
                break;
            }
        }
        if (str2 != null) {
            Objects.requireNonNull(MediaPlayerData.INSTANCE);
            MediaPlayerData.removeMediaPlayer(str2);
        }
        MediaControlPanel mediaControlPanel = this.mediaControlPanelFactory.get();
        Set<Integer> set = RecommendationViewHolder.controlsIds;
        int i2 = 0;
        View inflate = LayoutInflater.from(this.context).inflate(C1777R.layout.media_smartspace_recommendations, this.mediaContent, false);
        inflate.setLayoutDirection(3);
        RecommendationViewHolder recommendationViewHolder = new RecommendationViewHolder(inflate);
        Objects.requireNonNull(mediaControlPanel);
        mediaControlPanel.mRecommendationViewHolder = recommendationViewHolder;
        mediaControlPanel.mMediaViewController.attach(recommendationViewHolder.recommendations, MediaViewController.TYPE.RECOMMENDATION);
        RecommendationViewHolder recommendationViewHolder2 = mediaControlPanel.mRecommendationViewHolder;
        Objects.requireNonNull(recommendationViewHolder2);
        recommendationViewHolder2.recommendations.setOnLongClickListener(new MediaControlPanel$$ExternalSyntheticLambda12(mediaControlPanel));
        RecommendationViewHolder recommendationViewHolder3 = mediaControlPanel.mRecommendationViewHolder;
        Objects.requireNonNull(recommendationViewHolder3);
        recommendationViewHolder3.cancel.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda2(mediaControlPanel, 0));
        RecommendationViewHolder recommendationViewHolder4 = mediaControlPanel.mRecommendationViewHolder;
        Objects.requireNonNull(recommendationViewHolder4);
        recommendationViewHolder4.settings.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda1(mediaControlPanel, 0));
        MediaViewController mediaViewController = mediaControlPanel.mMediaViewController;
        MediaCarouselController$addSmartspaceMediaRecommendations$2 mediaCarouselController$addSmartspaceMediaRecommendations$2 = new MediaCarouselController$addSmartspaceMediaRecommendations$2(this);
        Objects.requireNonNull(mediaViewController);
        mediaViewController.sizeChangedListener = mediaCarouselController$addSmartspaceMediaRecommendations$2;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        RecommendationViewHolder recommendationViewHolder5 = mediaControlPanel.mRecommendationViewHolder;
        if (!(recommendationViewHolder5 == null || (transitionLayout = recommendationViewHolder5.recommendations) == null)) {
            transitionLayout.setLayoutParams(layoutParams);
        }
        SmartspaceMediaData copy$default = SmartspaceMediaData.copy$default(smartspaceMediaData, (String) null, false, false, (Intent) null, this.bgColor, 0, 383);
        if (mediaControlPanel.mRecommendationViewHolder == null) {
            str3 = str5;
        } else {
            mediaControlPanel.mInstanceId = Math.abs(Math.floorMod(Objects.hashCode(copy$default.targetId), 8192));
            mediaControlPanel.mBackgroundColor = copy$default.backgroundColor;
            RecommendationViewHolder recommendationViewHolder6 = mediaControlPanel.mRecommendationViewHolder;
            Objects.requireNonNull(recommendationViewHolder6);
            TransitionLayout transitionLayout2 = recommendationViewHolder6.recommendations;
            transitionLayout2.setBackgroundTintList(ColorStateList.valueOf(mediaControlPanel.mBackgroundColor));
            List<SmartspaceAction> list4 = copy$default.recommendations;
            if (list4 == null || list4.isEmpty()) {
                str3 = str5;
                Log.w("MediaControlPanel", "Empty media recommendations");
            } else {
                try {
                    ApplicationInfo applicationInfo = mediaControlPanel.mContext.getPackageManager().getApplicationInfo(copy$default.packageName, 0);
                    mediaControlPanel.mUid = applicationInfo.uid;
                    PackageManager packageManager = mediaControlPanel.mContext.getPackageManager();
                    Drawable applicationIcon = packageManager.getApplicationIcon(applicationInfo);
                    ColorMatrix colorMatrix = new ColorMatrix();
                    colorMatrix.setSaturation(0.0f);
                    applicationIcon.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
                    RecommendationViewHolder recommendationViewHolder7 = mediaControlPanel.mRecommendationViewHolder;
                    Objects.requireNonNull(recommendationViewHolder7);
                    recommendationViewHolder7.cardIcon.setImageDrawable(applicationIcon);
                    CharSequence applicationLabel = packageManager.getApplicationLabel(applicationInfo);
                    if (applicationLabel.length() != 0) {
                        RecommendationViewHolder recommendationViewHolder8 = mediaControlPanel.mRecommendationViewHolder;
                        Objects.requireNonNull(recommendationViewHolder8);
                        recommendationViewHolder8.cardText.setText(applicationLabel);
                    }
                    mediaControlPanel.setSmartspaceRecItemOnClickListener(transitionLayout2, copy$default.cardAction, -1);
                    transitionLayout2.setContentDescription(mediaControlPanel.mContext.getString(C1777R.string.controls_media_smartspace_rec_description, new Object[]{applicationLabel}));
                    RecommendationViewHolder recommendationViewHolder9 = mediaControlPanel.mRecommendationViewHolder;
                    Objects.requireNonNull(recommendationViewHolder9);
                    List<ImageView> list5 = recommendationViewHolder9.mediaCoverItems;
                    RecommendationViewHolder recommendationViewHolder10 = mediaControlPanel.mRecommendationViewHolder;
                    Objects.requireNonNull(recommendationViewHolder10);
                    List<ViewGroup> list6 = recommendationViewHolder10.mediaCoverContainers;
                    RecommendationViewHolder recommendationViewHolder11 = mediaControlPanel.mRecommendationViewHolder;
                    Objects.requireNonNull(recommendationViewHolder11);
                    List<Integer> list7 = recommendationViewHolder11.mediaCoverItemsResIds;
                    RecommendationViewHolder recommendationViewHolder12 = mediaControlPanel.mRecommendationViewHolder;
                    Objects.requireNonNull(recommendationViewHolder12);
                    List<Integer> list8 = recommendationViewHolder12.mediaCoverContainersResIds;
                    MediaViewController mediaViewController2 = mediaControlPanel.mMediaViewController;
                    Objects.requireNonNull(mediaViewController2);
                    ConstraintSet constraintSet = mediaViewController2.expandedLayout;
                    MediaViewController mediaViewController3 = mediaControlPanel.mMediaViewController;
                    Objects.requireNonNull(mediaViewController3);
                    ConstraintSet constraintSet2 = mediaViewController3.collapsedLayout;
                    int min = Math.min(list4.size(), 6);
                    int i3 = 0;
                    while (i3 < min && i2 < min) {
                        int i4 = min;
                        SmartspaceAction smartspaceAction = list4.get(i3);
                        if (smartspaceAction.getIcon() == null) {
                            Log.w("MediaControlPanel", "No media cover is provided. Skipping this item...");
                            str4 = str5;
                            list2 = list5;
                            list = list6;
                            list3 = list4;
                        } else {
                            list2 = list5;
                            ImageView imageView = list5.get(i2);
                            list3 = list4;
                            imageView.setImageIcon(smartspaceAction.getIcon());
                            ViewGroup viewGroup = list6.get(i2);
                            mediaControlPanel.setSmartspaceRecItemOnClickListener(viewGroup, smartspaceAction, i2);
                            list = list6;
                            viewGroup.setOnLongClickListener(MediaControlPanel$$ExternalSyntheticLambda13.INSTANCE);
                            str4 = str5;
                            String string = smartspaceAction.getExtras().getString("artist_name", "");
                            if (string.isEmpty()) {
                                z2 = true;
                                imageView.setContentDescription(mediaControlPanel.mContext.getString(C1777R.string.controls_media_smartspace_rec_item_no_artist_description, new Object[]{smartspaceAction.getTitle(), applicationLabel}));
                                i = 3;
                            } else {
                                i = 3;
                                z2 = true;
                                imageView.setContentDescription(mediaControlPanel.mContext.getString(C1777R.string.controls_media_smartspace_rec_item_description, new Object[]{smartspaceAction.getTitle(), string, applicationLabel}));
                            }
                            if (i2 < i) {
                                MediaControlPanel.setVisibleAndAlpha(constraintSet2, list7.get(i2).intValue(), z2);
                                MediaControlPanel.setVisibleAndAlpha(constraintSet2, list8.get(i2).intValue(), z2);
                            } else {
                                MediaControlPanel.setVisibleAndAlpha(constraintSet2, list7.get(i2).intValue(), false);
                                MediaControlPanel.setVisibleAndAlpha(constraintSet2, list8.get(i2).intValue(), false);
                            }
                            MediaControlPanel.setVisibleAndAlpha(constraintSet, list7.get(i2).intValue(), true);
                            MediaControlPanel.setVisibleAndAlpha(constraintSet, list8.get(i2).intValue(), true);
                            i2++;
                        }
                        i3++;
                        list4 = list3;
                        min = i4;
                        list5 = list2;
                        list6 = list;
                        str5 = str4;
                    }
                    str3 = str5;
                    mediaControlPanel.mSmartspaceMediaItemsCount = i2;
                    RecommendationViewHolder recommendationViewHolder13 = mediaControlPanel.mRecommendationViewHolder;
                    Objects.requireNonNull(recommendationViewHolder13);
                    recommendationViewHolder13.dismiss.setOnClickListener(new MediaControlPanel$$ExternalSyntheticLambda4(mediaControlPanel, copy$default, 0));
                    mediaControlPanel.mController = null;
                    mediaControlPanel.mMediaViewController.refreshState();
                } catch (PackageManager.NameNotFoundException e) {
                    str3 = str5;
                    Log.w("MediaControlPanel", "Fail to get media recommendation's app info", e);
                }
            }
        }
        Objects.requireNonNull(MediaPlayerData.INSTANCE);
        TreeMap<MediaPlayerData.MediaSortKey, MediaControlPanel> treeMap = MediaPlayerData.mediaPlayers;
        Set<MediaPlayerData.MediaSortKey> keySet = treeMap.keySet();
        MediaCarouselScrollHandler mediaCarouselScrollHandler2 = this.mediaCarouselScrollHandler;
        Objects.requireNonNull(mediaCarouselScrollHandler2);
        SystemClock systemClock2 = this.systemClock;
        MediaPlayerData.shouldPrioritizeSs = z;
        MediaPlayerData.removeMediaPlayer(str);
        MediaPlayerData.MediaSortKey mediaSortKey2 = new MediaPlayerData.MediaSortKey(true, MediaData.copy$default(MediaPlayerData.EMPTY, (List) null, (List) null, (String) null, (MediaDeviceData) null, false, (MediaResumeListener$getResumeAction$1) null, false, false, Boolean.FALSE, false, 14680063), systemClock2.currentTimeMillis());
        MediaPlayerData.mediaData.put(str, mediaSortKey2);
        treeMap.put(mediaSortKey2, mediaControlPanel);
        MediaPlayerData.smartspaceMediaData = smartspaceMediaData;
        mediaControlPanel.mMediaViewController.setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, true);
        reorderAllPlayers((MediaPlayerData.MediaSortKey) CollectionsKt___CollectionsKt.elementAtOrNull(keySet, mediaCarouselScrollHandler2.visibleMediaIndex));
        updatePageIndicator();
        this.mediaFrame.setTag(C1777R.C1779id.requires_remeasuring, Boolean.TRUE);
        if (MediaPlayerData.players().size() != this.mediaContent.getChildCount()) {
            Log.wtf(str3, "Size of players list and number of views in carousel are out of sync");
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        Float f;
        printWriter.println(Intrinsics.stringPlus("keysNeedRemoval: ", this.keysNeedRemoval));
        Objects.requireNonNull(MediaPlayerData.INSTANCE);
        printWriter.println(Intrinsics.stringPlus("playerKeys: ", MediaPlayerData.mediaPlayers.keySet()));
        printWriter.println(Intrinsics.stringPlus("smartspaceMediaData: ", MediaPlayerData.smartspaceMediaData));
        printWriter.println(Intrinsics.stringPlus("shouldPrioritizeSs: ", Boolean.valueOf(MediaPlayerData.shouldPrioritizeSs)));
        printWriter.println("current size: " + this.currentCarouselWidth + " x " + this.currentCarouselHeight);
        printWriter.println(Intrinsics.stringPlus("location: ", Integer.valueOf(this.desiredLocation)));
        StringBuilder sb = new StringBuilder();
        sb.append("state: ");
        MediaHostState mediaHostState = this.desiredHostState;
        Boolean bool = null;
        if (mediaHostState == null) {
            f = null;
        } else {
            f = Float.valueOf(mediaHostState.getExpansion());
        }
        sb.append(f);
        sb.append(", only active ");
        MediaHostState mediaHostState2 = this.desiredHostState;
        if (mediaHostState2 != null) {
            bool = Boolean.valueOf(mediaHostState2.getShowsOnlyActiveMedia());
        }
        sb.append(bool);
        printWriter.println(sb.toString());
    }

    public final void inflateSettingsButton() {
        View inflate = LayoutInflater.from(this.context).inflate(C1777R.layout.media_carousel_settings_button, this.mediaFrame, false);
        Objects.requireNonNull(inflate, "null cannot be cast to non-null type android.view.View");
        View view = this.settingsButton;
        View view2 = null;
        if (view != null) {
            ViewGroup viewGroup = this.mediaFrame;
            if (view == null) {
                view = null;
            }
            viewGroup.removeView(view);
        }
        this.settingsButton = inflate;
        this.mediaFrame.addView(inflate);
        MediaCarouselScrollHandler mediaCarouselScrollHandler2 = this.mediaCarouselScrollHandler;
        Objects.requireNonNull(mediaCarouselScrollHandler2);
        mediaCarouselScrollHandler2.settingsButton = inflate;
        Resources resources = inflate.getResources();
        View view3 = mediaCarouselScrollHandler2.settingsButton;
        if (view3 == null) {
            view3 = null;
        }
        mediaCarouselScrollHandler2.cornerRadius = resources.getDimensionPixelSize(com.android.settingslib.Utils.getThemeAttr(view3.getContext(), 16844145));
        mediaCarouselScrollHandler2.updateSettingsPresentation();
        mediaCarouselScrollHandler2.scrollView.invalidateOutline();
        View view4 = this.settingsButton;
        if (view4 != null) {
            view2 = view4;
        }
        view2.setOnClickListener(new MediaCarouselController$inflateSettingsButton$2(this));
    }

    /* JADX WARNING: Code restructure failed: missing block: B:5:0x0034, code lost:
        if (r1.booleanValue() != false) goto L_0x003e;
     */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0045  */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x0047  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void logSmartspaceImpression(boolean r15) {
        /*
            r14 = this;
            com.android.systemui.media.MediaCarouselScrollHandler r1 = r14.mediaCarouselScrollHandler
            java.util.Objects.requireNonNull(r1)
            int r1 = r1.visibleMediaIndex
            com.android.systemui.media.MediaPlayerData r2 = com.android.systemui.media.MediaPlayerData.INSTANCE
            java.util.Objects.requireNonNull(r2)
            java.util.Collection r2 = com.android.systemui.media.MediaPlayerData.players()
            int r2 = r2.size()
            if (r2 <= r1) goto L_0x006f
            java.util.Collection r2 = com.android.systemui.media.MediaPlayerData.players()
            java.lang.Object r1 = kotlin.collections.CollectionsKt___CollectionsKt.elementAt(r2, r1)
            r11 = r1
            com.android.systemui.media.MediaControlPanel r11 = (com.android.systemui.media.MediaControlPanel) r11
            com.android.systemui.media.SmartspaceMediaData r1 = com.android.systemui.media.MediaPlayerData.smartspaceMediaData
            r2 = 0
            r12 = 1
            if (r1 == 0) goto L_0x0037
            boolean r1 = r1.isActive
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r1)
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L_0x0037
            goto L_0x003e
        L_0x0037:
            int r1 = com.android.systemui.media.MediaPlayerData.firstActiveMediaIndex()
            r3 = -1
            if (r1 == r3) goto L_0x0040
        L_0x003e:
            r1 = r12
            goto L_0x0041
        L_0x0040:
            r1 = r2
        L_0x0041:
            com.android.systemui.media.RecommendationViewHolder r3 = r11.mRecommendationViewHolder
            if (r3 == 0) goto L_0x0047
            r4 = r12
            goto L_0x0048
        L_0x0047:
            r4 = r2
        L_0x0048:
            if (r1 != 0) goto L_0x004d
            if (r15 != 0) goto L_0x004d
            return
        L_0x004d:
            r1 = 800(0x320, float:1.121E-42)
            int r3 = r11.mInstanceId
            int r5 = r11.mUid
            int[] r6 = new int[r12]
            int r7 = r11.getSurfaceForSmartspaceLogging()
            r6[r2] = r7
            r7 = 0
            r8 = 0
            r9 = 0
            r10 = 0
            r13 = 480(0x1e0, float:6.73E-43)
            r0 = r14
            r2 = r3
            r3 = r5
            r5 = r6
            r6 = r7
            r7 = r8
            r8 = r9
            r9 = r10
            r10 = r13
            logSmartspaceCardReported$default(r0, r1, r2, r3, r4, r5, r6, r7, r8, r9, r10)
            r11.mIsImpressed = r12
        L_0x006f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaCarouselController.logSmartspaceImpression(boolean):void");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: com.android.systemui.util.animation.TransitionViewState} */
    /* JADX WARNING: type inference failed for: r5v0 */
    /* JADX WARNING: type inference failed for: r5v1, types: [android.view.View] */
    /* JADX WARNING: type inference failed for: r5v2 */
    /* JADX WARNING: type inference failed for: r5v6 */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void onDesiredLocationChanged(int r9, com.android.systemui.media.MediaHostState r10, boolean r11, long r12, long r14) {
        /*
            r8 = this;
            if (r10 != 0) goto L_0x0004
            goto L_0x018f
        L_0x0004:
            r8.desiredLocation = r9
            r8.desiredHostState = r10
            float r0 = r10.getExpansion()
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            r1 = 0
            r2 = 1
            if (r0 <= 0) goto L_0x0015
            r0 = r2
            goto L_0x0016
        L_0x0015:
            r0 = r1
        L_0x0016:
            boolean r3 = r8.currentlyExpanded
            if (r3 == r0) goto L_0x004a
            r8.currentlyExpanded = r0
            com.android.systemui.media.MediaPlayerData r0 = com.android.systemui.media.MediaPlayerData.INSTANCE
            java.util.Objects.requireNonNull(r0)
            java.util.Collection r0 = com.android.systemui.media.MediaPlayerData.players()
            java.util.Iterator r0 = r0.iterator()
        L_0x0029:
            boolean r3 = r0.hasNext()
            if (r3 == 0) goto L_0x004a
            java.lang.Object r3 = r0.next()
            com.android.systemui.media.MediaControlPanel r3 = (com.android.systemui.media.MediaControlPanel) r3
            boolean r4 = r8.currentlyExpanded
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.media.SeekBarViewModel r3 = r3.mSeekBarViewModel
            java.util.Objects.requireNonNull(r3)
            com.android.systemui.util.concurrency.RepeatableExecutor r5 = r3.bgExecutor
            com.android.systemui.media.SeekBarViewModel$listening$1 r6 = new com.android.systemui.media.SeekBarViewModel$listening$1
            r6.<init>(r3, r4)
            r5.execute(r6)
            goto L_0x0029
        L_0x004a:
            boolean r0 = r8.currentlyExpanded
            if (r0 != 0) goto L_0x005e
            com.android.systemui.media.MediaDataManager r0 = r8.mediaManager
            boolean r0 = r0.hasActiveMedia()
            if (r0 != 0) goto L_0x005e
            boolean r0 = r10.getShowsOnlyActiveMedia()
            if (r0 == 0) goto L_0x005e
            r0 = r2
            goto L_0x005f
        L_0x005e:
            r0 = r1
        L_0x005f:
            com.android.systemui.media.MediaPlayerData r3 = com.android.systemui.media.MediaPlayerData.INSTANCE
            java.util.Objects.requireNonNull(r3)
            java.util.Collection r3 = com.android.systemui.media.MediaPlayerData.players()
            java.util.Iterator r3 = r3.iterator()
        L_0x006c:
            boolean r4 = r3.hasNext()
            r5 = 0
            if (r4 == 0) goto L_0x00c5
            java.lang.Object r4 = r3.next()
            com.android.systemui.media.MediaControlPanel r4 = (com.android.systemui.media.MediaControlPanel) r4
            if (r11 == 0) goto L_0x0089
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.media.MediaViewController r6 = r4.mMediaViewController
            java.util.Objects.requireNonNull(r6)
            r6.animateNextStateChange = r2
            r6.animationDuration = r12
            r6.animationDelay = r14
        L_0x0089:
            if (r0 == 0) goto L_0x009c
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.media.MediaViewController r6 = r4.mMediaViewController
            java.util.Objects.requireNonNull(r6)
            boolean r6 = r6.isGutsVisible
            if (r6 == 0) goto L_0x009c
            r6 = r11 ^ 1
            r4.closeGuts(r6)
        L_0x009c:
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.media.MediaViewController r4 = r4.mMediaViewController
            java.util.Objects.requireNonNull(r4)
            com.android.systemui.media.MediaHostStatesManager r6 = r4.mediaHostStatesManager
            java.util.Objects.requireNonNull(r6)
            java.util.LinkedHashMap r6 = r6.mediaHostStates
            java.lang.Integer r7 = java.lang.Integer.valueOf(r9)
            java.lang.Object r6 = r6.get(r7)
            com.android.systemui.media.MediaHostState r6 = (com.android.systemui.media.MediaHostState) r6
            if (r6 != 0) goto L_0x00b8
            goto L_0x00bc
        L_0x00b8:
            com.android.systemui.util.animation.TransitionViewState r5 = r4.obtainViewState(r6)
        L_0x00bc:
            if (r5 != 0) goto L_0x00bf
            goto L_0x006c
        L_0x00bf:
            com.android.systemui.util.animation.TransitionLayoutController r4 = r4.layoutController
            r4.setMeasureState(r5)
            goto L_0x006c
        L_0x00c5:
            com.android.systemui.media.MediaCarouselScrollHandler r9 = r8.mediaCarouselScrollHandler
            boolean r11 = r10.getShowsOnlyActiveMedia()
            r11 = r11 ^ r2
            java.util.Objects.requireNonNull(r9)
            r9.showsSettingsButton = r11
            com.android.systemui.media.MediaCarouselScrollHandler r9 = r8.mediaCarouselScrollHandler
            boolean r11 = r10.getFalsingProtectionNeeded()
            java.util.Objects.requireNonNull(r9)
            r9.falsingProtectionNeeded = r11
            boolean r9 = r10.getVisible()
            boolean r10 = r8.playersVisible
            if (r9 == r10) goto L_0x00ed
            r8.playersVisible = r9
            if (r9 == 0) goto L_0x00ed
            com.android.systemui.media.MediaCarouselScrollHandler r9 = r8.mediaCarouselScrollHandler
            r9.resetTranslation(r1)
        L_0x00ed:
            com.android.systemui.media.MediaHostState r9 = r8.desiredHostState
            if (r9 != 0) goto L_0x00f2
            goto L_0x00f8
        L_0x00f2:
            com.android.systemui.util.animation.MeasurementInput r9 = r9.getMeasurementInput()
            if (r9 != 0) goto L_0x00fa
        L_0x00f8:
            r9 = r1
            goto L_0x0100
        L_0x00fa:
            int r9 = r9.widthMeasureSpec
            int r9 = android.view.View.MeasureSpec.getSize(r9)
        L_0x0100:
            com.android.systemui.media.MediaHostState r10 = r8.desiredHostState
            if (r10 != 0) goto L_0x0105
            goto L_0x010b
        L_0x0105:
            com.android.systemui.util.animation.MeasurementInput r10 = r10.getMeasurementInput()
            if (r10 != 0) goto L_0x010d
        L_0x010b:
            r10 = r1
            goto L_0x0113
        L_0x010d:
            int r10 = r10.heightMeasureSpec
            int r10 = android.view.View.MeasureSpec.getSize(r10)
        L_0x0113:
            int r11 = r8.carouselMeasureWidth
            if (r9 == r11) goto L_0x0119
            if (r9 != 0) goto L_0x011f
        L_0x0119:
            int r11 = r8.carouselMeasureHeight
            if (r10 == r11) goto L_0x018f
            if (r10 == 0) goto L_0x018f
        L_0x011f:
            r8.carouselMeasureWidth = r9
            r8.carouselMeasureHeight = r10
            android.content.Context r10 = r8.context
            android.content.res.Resources r10 = r10.getResources()
            r11 = 2131166889(0x7f0706a9, float:1.7948036E38)
            int r10 = r10.getDimensionPixelSize(r11)
            int r10 = r10 + r9
            com.android.systemui.media.MediaHostState r11 = r8.desiredHostState
            if (r11 != 0) goto L_0x0136
            goto L_0x013c
        L_0x0136:
            com.android.systemui.util.animation.MeasurementInput r11 = r11.getMeasurementInput()
            if (r11 != 0) goto L_0x013e
        L_0x013c:
            r11 = r1
            goto L_0x0140
        L_0x013e:
            int r11 = r11.widthMeasureSpec
        L_0x0140:
            com.android.systemui.media.MediaHostState r12 = r8.desiredHostState
            if (r12 != 0) goto L_0x0145
            goto L_0x014b
        L_0x0145:
            com.android.systemui.util.animation.MeasurementInput r12 = r12.getMeasurementInput()
            if (r12 != 0) goto L_0x014d
        L_0x014b:
            r12 = r1
            goto L_0x014f
        L_0x014d:
            int r12 = r12.heightMeasureSpec
        L_0x014f:
            com.android.systemui.media.MediaScrollView r13 = r8.mediaCarousel
            r13.measure(r11, r12)
            com.android.systemui.media.MediaScrollView r11 = r8.mediaCarousel
            int r12 = r11.getMeasuredHeight()
            r11.layout(r1, r1, r9, r12)
            com.android.systemui.media.MediaCarouselScrollHandler r8 = r8.mediaCarouselScrollHandler
            java.util.Objects.requireNonNull(r8)
            r8.playerWidthPlusPadding = r10
            int r9 = r8.visibleMediaIndex
            int r9 = r9 * r10
            int r11 = r8.scrollIntoCurrentMedia
            if (r11 <= r10) goto L_0x016f
            int r11 = r11 - r10
            int r10 = r10 - r11
            int r10 = r10 + r9
            goto L_0x0171
        L_0x016f:
            int r10 = r9 + r11
        L_0x0171:
            com.android.systemui.media.MediaScrollView r8 = r8.scrollView
            java.util.Objects.requireNonNull(r8)
            boolean r9 = r8.isLayoutRtl()
            if (r9 == 0) goto L_0x018c
            android.view.ViewGroup r9 = r8.contentContainer
            if (r9 == 0) goto L_0x0181
            r5 = r9
        L_0x0181:
            int r9 = r5.getWidth()
            int r11 = r8.getWidth()
            int r9 = r9 - r11
            int r10 = r9 - r10
        L_0x018c:
            r8.setScrollX(r10)
        L_0x018f:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaCarouselController.onDesiredLocationChanged(int, com.android.systemui.media.MediaHostState, boolean, long, long):void");
    }

    public final void removePlayer(String str, boolean z, boolean z2) {
        TransitionLayout transitionLayout;
        boolean z3;
        TransitionLayout transitionLayout2;
        Objects.requireNonNull(MediaPlayerData.INSTANCE);
        MediaControlPanel removeMediaPlayer = MediaPlayerData.removeMediaPlayer(str);
        if (removeMediaPlayer != null) {
            MediaCarouselScrollHandler mediaCarouselScrollHandler2 = this.mediaCarouselScrollHandler;
            Objects.requireNonNull(mediaCarouselScrollHandler2);
            ViewGroup viewGroup = mediaCarouselScrollHandler2.mediaContent;
            MediaViewHolder mediaViewHolder = removeMediaPlayer.mMediaViewHolder;
            TransitionLayout transitionLayout3 = null;
            if (mediaViewHolder == null) {
                transitionLayout = null;
            } else {
                transitionLayout = mediaViewHolder.player;
            }
            int indexOfChild = viewGroup.indexOfChild(transitionLayout);
            int i = mediaCarouselScrollHandler2.visibleMediaIndex;
            boolean z4 = true;
            if (indexOfChild <= i) {
                z3 = true;
            } else {
                z3 = false;
            }
            if (z3) {
                mediaCarouselScrollHandler2.visibleMediaIndex = Math.max(0, i - 1);
            }
            if (!mediaCarouselScrollHandler2.scrollView.isLayoutRtl()) {
                z4 = z3;
            } else if (z3) {
                z4 = false;
            }
            if (z4) {
                MediaScrollView mediaScrollView = mediaCarouselScrollHandler2.scrollView;
                mediaScrollView.setScrollX(Math.max(mediaScrollView.getScrollX() - mediaCarouselScrollHandler2.playerWidthPlusPadding, 0));
            }
            ViewGroup viewGroup2 = this.mediaContent;
            MediaViewHolder mediaViewHolder2 = removeMediaPlayer.mMediaViewHolder;
            if (mediaViewHolder2 == null) {
                transitionLayout2 = null;
            } else {
                transitionLayout2 = mediaViewHolder2.player;
            }
            viewGroup2.removeView(transitionLayout2);
            ViewGroup viewGroup3 = this.mediaContent;
            RecommendationViewHolder recommendationViewHolder = removeMediaPlayer.mRecommendationViewHolder;
            if (recommendationViewHolder != null) {
                transitionLayout3 = recommendationViewHolder.recommendations;
            }
            viewGroup3.removeView(transitionLayout3);
            if (removeMediaPlayer.mSeekBarObserver != null) {
                SeekBarViewModel seekBarViewModel = removeMediaPlayer.mSeekBarViewModel;
                Objects.requireNonNull(seekBarViewModel);
                seekBarViewModel._progress.removeObserver(removeMediaPlayer.mSeekBarObserver);
            }
            SeekBarViewModel seekBarViewModel2 = removeMediaPlayer.mSeekBarViewModel;
            Objects.requireNonNull(seekBarViewModel2);
            seekBarViewModel2.bgExecutor.execute(new SeekBarViewModel$onDestroy$1(seekBarViewModel2));
            MediaViewController mediaViewController = removeMediaPlayer.mMediaViewController;
            Objects.requireNonNull(mediaViewController);
            MediaHostStatesManager mediaHostStatesManager2 = mediaViewController.mediaHostStatesManager;
            Objects.requireNonNull(mediaHostStatesManager2);
            mediaHostStatesManager2.controllers.remove(mediaViewController);
            mediaViewController.configurationController.removeCallback(mediaViewController.configurationListener);
            this.mediaCarouselScrollHandler.onPlayersChanged();
            updatePageIndicator();
            if (z) {
                this.mediaManager.dismissMediaData(str, 0);
            }
            if (z2) {
                this.mediaManager.dismissSmartspaceRecommendation(str, 0);
            }
        }
    }

    public final void reorderAllPlayers(MediaPlayerData.MediaSortKey mediaSortKey) {
        RecommendationViewHolder recommendationViewHolder;
        this.mediaContent.removeAllViews();
        Objects.requireNonNull(MediaPlayerData.INSTANCE);
        Iterator it = MediaPlayerData.players().iterator();
        while (true) {
            Unit unit = null;
            if (!it.hasNext()) {
                break;
            }
            MediaControlPanel mediaControlPanel = (MediaControlPanel) it.next();
            Objects.requireNonNull(mediaControlPanel);
            MediaViewHolder mediaViewHolder = mediaControlPanel.mMediaViewHolder;
            if (mediaViewHolder != null) {
                this.mediaContent.addView(mediaViewHolder.player);
                unit = Unit.INSTANCE;
            }
            if (unit == null && (recommendationViewHolder = mediaControlPanel.mRecommendationViewHolder) != null) {
                this.mediaContent.addView(recommendationViewHolder.recommendations);
            }
        }
        this.mediaCarouselScrollHandler.onPlayersChanged();
        if (this.shouldScrollToActivePlayer) {
            int i = 0;
            this.shouldScrollToActivePlayer = false;
            Objects.requireNonNull(MediaPlayerData.INSTANCE);
            int firstActiveMediaIndex = MediaPlayerData.firstActiveMediaIndex();
            int i2 = -1;
            if (firstActiveMediaIndex != -1 && mediaSortKey != null) {
                Iterator<T> it2 = MediaPlayerData.mediaPlayers.keySet().iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    T next = it2.next();
                    if (i < 0) {
                        SetsKt__SetsKt.throwIndexOverflow();
                        throw null;
                    } else if (Intrinsics.areEqual(mediaSortKey, (MediaPlayerData.MediaSortKey) next)) {
                        i2 = i;
                        break;
                    } else {
                        i++;
                    }
                }
                this.mediaCarouselScrollHandler.scrollToPlayer(i2, firstActiveMediaIndex);
            }
        }
    }

    public final void updatePageIndicator() {
        int childCount = this.mediaContent.getChildCount();
        this.pageIndicator.setNumPages(childCount);
        if (childCount == 1) {
            this.pageIndicator.setLocation(0.0f);
        }
        updatePageIndicatorAlpha();
    }

    public final void updatePageIndicatorAlpha() {
        boolean z;
        float f;
        float f2;
        MediaHostStatesManager mediaHostStatesManager2 = this.mediaHostStatesManager;
        Objects.requireNonNull(mediaHostStatesManager2);
        LinkedHashMap linkedHashMap = mediaHostStatesManager2.mediaHostStates;
        MediaHostState mediaHostState = (MediaHostState) linkedHashMap.get(Integer.valueOf(this.currentEndLocation));
        boolean z2 = false;
        if (mediaHostState == null) {
            z = false;
        } else {
            z = mediaHostState.getVisible();
        }
        MediaHostState mediaHostState2 = (MediaHostState) linkedHashMap.get(Integer.valueOf(this.currentStartLocation));
        if (mediaHostState2 != null) {
            z2 = mediaHostState2.getVisible();
        }
        float f3 = 1.0f;
        if (z2) {
            f = 1.0f;
        } else {
            f = 0.0f;
        }
        if (z) {
            f2 = 1.0f;
        } else {
            f2 = 0.0f;
        }
        if (!z || !z2) {
            float f4 = this.currentTransitionProgress;
            if (!z) {
                f4 = 1.0f - f4;
            }
            f3 = MathUtils.lerp(f, f2, MathUtils.constrain(MathUtils.map(0.95f, 1.0f, 0.0f, 1.0f, f4), 0.0f, 1.0f));
        }
        this.pageIndicator.setAlpha(f3);
    }

    public final void updatePageIndicatorLocation() {
        int i;
        int i2;
        if (this.isRtl) {
            i2 = this.pageIndicator.getWidth();
            i = this.currentCarouselWidth;
        } else {
            i2 = this.currentCarouselWidth;
            i = this.pageIndicator.getWidth();
        }
        PageIndicator pageIndicator2 = this.pageIndicator;
        MediaCarouselScrollHandler mediaCarouselScrollHandler2 = this.mediaCarouselScrollHandler;
        Objects.requireNonNull(mediaCarouselScrollHandler2);
        pageIndicator2.setTranslationX((((float) (i2 - i)) / 2.0f) + mediaCarouselScrollHandler2.contentTranslation);
        ViewGroup.LayoutParams layoutParams = this.pageIndicator.getLayoutParams();
        Objects.requireNonNull(layoutParams, "null cannot be cast to non-null type android.view.ViewGroup.MarginLayoutParams");
        PageIndicator pageIndicator3 = this.pageIndicator;
        pageIndicator3.setTranslationY((float) ((this.currentCarouselHeight - pageIndicator3.getHeight()) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin));
    }

    public static final void access$recreatePlayers(MediaCarouselController mediaCarouselController) {
        int i;
        Objects.requireNonNull(mediaCarouselController);
        mediaCarouselController.bgColor = mediaCarouselController.context.getColor(C1777R.color.material_dynamic_secondary95);
        PageIndicator pageIndicator2 = mediaCarouselController.pageIndicator;
        if (mediaCarouselController.mediaFlags.useMediaSessionLayout()) {
            i = mediaCarouselController.context.getColor(C1777R.color.material_dynamic_neutral_variant80);
        } else {
            i = mediaCarouselController.context.getColor(C1777R.color.material_dynamic_secondary10);
        }
        ColorStateList valueOf = ColorStateList.valueOf(i);
        Objects.requireNonNull(pageIndicator2);
        if (!valueOf.equals(pageIndicator2.mTint)) {
            pageIndicator2.mTint = valueOf;
            int childCount = pageIndicator2.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = pageIndicator2.getChildAt(i2);
                if (childAt instanceof ImageView) {
                    ((ImageView) childAt).setImageTintList(pageIndicator2.mTint);
                }
            }
        }
        Objects.requireNonNull(MediaPlayerData.INSTANCE);
        Set<Map.Entry> entrySet = MediaPlayerData.mediaData.entrySet();
        ArrayList arrayList = new ArrayList(CollectionsKt__IteratorsJVMKt.collectionSizeOrDefault(entrySet, 10));
        for (Map.Entry entry : entrySet) {
            Object key = entry.getKey();
            MediaPlayerData.MediaSortKey mediaSortKey = (MediaPlayerData.MediaSortKey) entry.getValue();
            Objects.requireNonNull(mediaSortKey);
            MediaData mediaData = mediaSortKey.data;
            MediaPlayerData.MediaSortKey mediaSortKey2 = (MediaPlayerData.MediaSortKey) entry.getValue();
            Objects.requireNonNull(mediaSortKey2);
            arrayList.add(new Triple(key, mediaData, Boolean.valueOf(mediaSortKey2.isSsMediaRec)));
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Triple triple = (Triple) it.next();
            String str = (String) triple.component1();
            MediaData mediaData2 = (MediaData) triple.component2();
            if (((Boolean) triple.component3()).booleanValue()) {
                Objects.requireNonNull(MediaPlayerData.INSTANCE);
                SmartspaceMediaData smartspaceMediaData = MediaPlayerData.smartspaceMediaData;
                mediaCarouselController.removePlayer(str, false, false);
                if (smartspaceMediaData != null) {
                    mediaCarouselController.addSmartspaceMediaRecommendations(smartspaceMediaData.targetId, smartspaceMediaData, MediaPlayerData.shouldPrioritizeSs);
                }
            } else {
                mediaCarouselController.removePlayer(str, false, false);
                mediaCarouselController.addOrUpdatePlayer(str, (String) null, mediaData2);
            }
        }
    }

    public static final void access$updateCarouselDimensions(MediaCarouselController mediaCarouselController) {
        float f;
        Objects.requireNonNull(mediaCarouselController);
        Objects.requireNonNull(MediaPlayerData.INSTANCE);
        int i = 0;
        int i2 = 0;
        for (MediaControlPanel mediaControlPanel : MediaPlayerData.players()) {
            Objects.requireNonNull(mediaControlPanel);
            MediaViewController mediaViewController = mediaControlPanel.mMediaViewController;
            int i3 = mediaViewController.currentWidth;
            TransitionLayout transitionLayout = mediaViewController.transitionLayout;
            float f2 = 0.0f;
            if (transitionLayout == null) {
                f = 0.0f;
            } else {
                f = transitionLayout.getTranslationX();
            }
            i = Math.max(i, i3 + ((int) f));
            int i4 = mediaViewController.currentHeight;
            TransitionLayout transitionLayout2 = mediaViewController.transitionLayout;
            if (transitionLayout2 != null) {
                f2 = transitionLayout2.getTranslationY();
            }
            i2 = Math.max(i2, i4 + ((int) f2));
        }
        if (i != mediaCarouselController.currentCarouselWidth || i2 != mediaCarouselController.currentCarouselHeight) {
            mediaCarouselController.currentCarouselWidth = i;
            mediaCarouselController.currentCarouselHeight = i2;
            MediaCarouselScrollHandler mediaCarouselScrollHandler2 = mediaCarouselController.mediaCarouselScrollHandler;
            Objects.requireNonNull(mediaCarouselScrollHandler2);
            int i5 = mediaCarouselScrollHandler2.carouselHeight;
            if (!(i2 == i5 && i == i5)) {
                mediaCarouselScrollHandler2.carouselWidth = i;
                mediaCarouselScrollHandler2.carouselHeight = i2;
                mediaCarouselScrollHandler2.scrollView.invalidateOutline();
            }
            mediaCarouselController.updatePageIndicatorLocation();
        }
    }
}
