package com.android.systemui.media;

import android.content.Context;
import androidx.constraintlayout.widget.ConstraintSet;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.media.MediaHost;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.MeasurementOutput;
import com.android.systemui.util.animation.TransitionLayout;
import com.android.systemui.util.animation.TransitionLayoutController;
import com.android.systemui.util.animation.TransitionViewState;
import com.android.systemui.util.animation.WidgetState;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MediaViewController.kt */
public final class MediaViewController {
    public boolean animateNextStateChange;
    public long animationDelay;
    public long animationDuration;
    public final ConstraintSet collapsedLayout;
    public final ConfigurationController configurationController;
    public final MediaViewController$configurationListener$1 configurationListener;
    public final Context context;
    public int currentEndLocation;
    public int currentHeight;
    public int currentStartLocation;
    public float currentTransitionProgress;
    public int currentWidth;
    public final ConstraintSet expandedLayout;
    public boolean firstRefresh = true;
    public boolean isGutsVisible;
    public final TransitionLayoutController layoutController;
    public final MeasurementOutput measurement;
    public final MediaHostStatesManager mediaHostStatesManager;
    public boolean shouldHideGutsSettings;
    public Function0<Unit> sizeChangedListener;
    public final MediaViewController$stateCallback$1 stateCallback;
    public final CacheKey tmpKey;
    public final TransitionViewState tmpState;
    public final TransitionViewState tmpState2;
    public final TransitionViewState tmpState3;
    public TransitionLayout transitionLayout;
    public TYPE type;
    public final LinkedHashMap viewStates;

    /* compiled from: MediaViewController.kt */
    public enum TYPE {
        PLAYER,
        PLAYER_SESSION,
        RECOMMENDATION
    }

    public final TransitionViewState obtainViewState(MediaHostState mediaHostState) {
        int i;
        int i2;
        boolean z;
        ConstraintSet constraintSet;
        Set<Integer> set;
        Set<Integer> set2;
        WidgetState widgetState;
        float f;
        float f2;
        boolean z2;
        if (mediaHostState == null || mediaHostState.getMeasurementInput() == null) {
            return null;
        }
        boolean z3 = this.isGutsVisible;
        CacheKey cacheKey = this.tmpKey;
        MeasurementInput measurementInput = mediaHostState.getMeasurementInput();
        boolean z4 = false;
        if (measurementInput == null) {
            i = 0;
        } else {
            i = measurementInput.heightMeasureSpec;
        }
        Objects.requireNonNull(cacheKey);
        cacheKey.heightMeasureSpec = i;
        MeasurementInput measurementInput2 = mediaHostState.getMeasurementInput();
        if (measurementInput2 == null) {
            i2 = 0;
        } else {
            i2 = measurementInput2.widthMeasureSpec;
        }
        cacheKey.widthMeasureSpec = i2;
        cacheKey.expansion = mediaHostState.getExpansion();
        cacheKey.gutsVisible = z3;
        TransitionViewState transitionViewState = (TransitionViewState) this.viewStates.get(cacheKey);
        if (transitionViewState != null) {
            return transitionViewState;
        }
        CacheKey cacheKey2 = new CacheKey(cacheKey.widthMeasureSpec, cacheKey.heightMeasureSpec, cacheKey.expansion, cacheKey.gutsVisible);
        if (this.transitionLayout == null) {
            return null;
        }
        if (mediaHostState.getExpansion() == 0.0f) {
            z = true;
        } else {
            z = false;
        }
        if (!z) {
            if (mediaHostState.getExpansion() == 1.0f) {
                z4 = true;
            }
            if (!z4) {
                MediaHost.MediaHostStateHolder copy = mediaHostState.copy();
                copy.setExpansion(0.0f);
                TransitionViewState obtainViewState = obtainViewState(copy);
                Objects.requireNonNull(obtainViewState, "null cannot be cast to non-null type com.android.systemui.util.animation.TransitionViewState");
                MediaHost.MediaHostStateHolder copy2 = mediaHostState.copy();
                copy2.setExpansion(1.0f);
                TransitionViewState obtainViewState2 = obtainViewState(copy2);
                Objects.requireNonNull(obtainViewState2, "null cannot be cast to non-null type com.android.systemui.util.animation.TransitionViewState");
                return this.layoutController.getInterpolatedState(obtainViewState, obtainViewState2, mediaHostState.getExpansion(), (TransitionViewState) null);
            }
        }
        TransitionLayout transitionLayout2 = this.transitionLayout;
        Intrinsics.checkNotNull(transitionLayout2);
        MeasurementInput measurementInput3 = mediaHostState.getMeasurementInput();
        Intrinsics.checkNotNull(measurementInput3);
        if (mediaHostState.getExpansion() > 0.0f) {
            constraintSet = this.expandedLayout;
        } else {
            constraintSet = this.collapsedLayout;
        }
        TransitionViewState transitionViewState2 = new TransitionViewState();
        transitionLayout2.calculateViewState(measurementInput3, constraintSet, transitionViewState2);
        int ordinal = this.type.ordinal();
        if (ordinal == 0) {
            set = PlayerViewHolder.controlsIds;
        } else if (ordinal == 1) {
            set = PlayerSessionViewHolder.controlsIds;
        } else if (ordinal == 2) {
            set = RecommendationViewHolder.controlsIds;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        int ordinal2 = this.type.ordinal();
        if (ordinal2 == 0) {
            set2 = PlayerViewHolder.gutsIds;
        } else if (ordinal2 == 1) {
            set2 = PlayerSessionViewHolder.gutsIds;
        } else if (ordinal2 == 2) {
            set2 = RecommendationViewHolder.gutsIds;
        } else {
            throw new NoWhenBranchMatchedException();
        }
        for (Number intValue : set) {
            WidgetState widgetState2 = (WidgetState) transitionViewState2.widgetStates.get(Integer.valueOf(intValue.intValue()));
            if (widgetState2 != null) {
                boolean z5 = this.isGutsVisible;
                if (z5) {
                    f2 = 0.0f;
                } else {
                    f2 = widgetState2.alpha;
                }
                widgetState2.alpha = f2;
                if (z5) {
                    z2 = true;
                } else {
                    z2 = widgetState2.gone;
                }
                widgetState2.gone = z2;
            }
        }
        for (Number intValue2 : set2) {
            int intValue3 = intValue2.intValue();
            WidgetState widgetState3 = (WidgetState) transitionViewState2.widgetStates.get(Integer.valueOf(intValue3));
            if (widgetState3 != null) {
                if (this.isGutsVisible) {
                    f = 1.0f;
                } else {
                    f = 0.0f;
                }
                widgetState3.alpha = f;
            }
            WidgetState widgetState4 = (WidgetState) transitionViewState2.widgetStates.get(Integer.valueOf(intValue3));
            if (widgetState4 != null) {
                widgetState4.gone = !this.isGutsVisible;
            }
        }
        if (this.shouldHideGutsSettings && (widgetState = (WidgetState) transitionViewState2.widgetStates.get(Integer.valueOf(C1777R.C1779id.settings))) != null) {
            widgetState.gone = true;
        }
        this.viewStates.put(cacheKey2, transitionViewState2);
        return transitionViewState2;
    }

    public final TransitionViewState updateViewStateToCarouselSize(TransitionViewState transitionViewState, int i, TransitionViewState transitionViewState2) {
        TransitionViewState transitionViewState3;
        if (transitionViewState == null) {
            transitionViewState3 = null;
        } else {
            transitionViewState3 = transitionViewState.copy(transitionViewState2);
        }
        if (transitionViewState3 == null) {
            return null;
        }
        MediaHostStatesManager mediaHostStatesManager2 = this.mediaHostStatesManager;
        Objects.requireNonNull(mediaHostStatesManager2);
        MeasurementOutput measurementOutput = (MeasurementOutput) mediaHostStatesManager2.carouselSizes.get(Integer.valueOf(i));
        if (measurementOutput != null) {
            transitionViewState3.height = Math.max(measurementOutput.measuredHeight, transitionViewState3.height);
            transitionViewState3.width = Math.max(measurementOutput.measuredWidth, transitionViewState3.width);
        }
        return transitionViewState3;
    }

    public final void attach(TransitionLayout transitionLayout2, TYPE type2) {
        this.type = type2;
        int ordinal = type2.ordinal();
        if (ordinal == 0) {
            this.collapsedLayout.load(this.context, (int) C1777R.xml.media_collapsed);
            this.expandedLayout.load(this.context, (int) C1777R.xml.media_expanded);
        } else if (ordinal == 1) {
            this.collapsedLayout.load(this.context, (int) C1777R.xml.media_session_collapsed);
            this.expandedLayout.load(this.context, (int) C1777R.xml.media_session_expanded);
        } else if (ordinal == 2) {
            this.collapsedLayout.load(this.context, (int) C1777R.xml.media_recommendation_collapsed);
            this.expandedLayout.load(this.context, (int) C1777R.xml.media_recommendation_expanded);
        }
        refreshState();
        this.transitionLayout = transitionLayout2;
        TransitionLayoutController transitionLayoutController = this.layoutController;
        Objects.requireNonNull(transitionLayoutController);
        transitionLayoutController.transitionLayout = transitionLayout2;
        int i = this.currentEndLocation;
        if (i != -1) {
            setCurrentState(this.currentStartLocation, i, this.currentTransitionProgress, true);
        }
    }

    public final void refreshState() {
        this.viewStates.clear();
        if (this.firstRefresh) {
            MediaHostStatesManager mediaHostStatesManager2 = this.mediaHostStatesManager;
            Objects.requireNonNull(mediaHostStatesManager2);
            for (Map.Entry value : mediaHostStatesManager2.mediaHostStates.entrySet()) {
                obtainViewState((MediaHostState) value.getValue());
            }
            this.firstRefresh = false;
        }
        setCurrentState(this.currentStartLocation, this.currentEndLocation, this.currentTransitionProgress, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00c7, code lost:
        if (r7.width != 0) goto L_0x00ce;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void setCurrentState(int r8, int r9, float r10, boolean r11) {
        /*
            r7 = this;
            r7.currentEndLocation = r9
            r7.currentStartLocation = r8
            r7.currentTransitionProgress = r10
            boolean r0 = r7.animateNextStateChange
            r1 = 1
            r2 = 0
            if (r0 == 0) goto L_0x0010
            if (r11 != 0) goto L_0x0010
            r0 = r1
            goto L_0x0011
        L_0x0010:
            r0 = r2
        L_0x0011:
            com.android.systemui.media.MediaHostStatesManager r3 = r7.mediaHostStatesManager
            java.util.Objects.requireNonNull(r3)
            java.util.LinkedHashMap r3 = r3.mediaHostStates
            java.lang.Integer r4 = java.lang.Integer.valueOf(r9)
            java.lang.Object r3 = r3.get(r4)
            com.android.systemui.media.MediaHostState r3 = (com.android.systemui.media.MediaHostState) r3
            if (r3 != 0) goto L_0x0025
            return
        L_0x0025:
            com.android.systemui.media.MediaHostStatesManager r4 = r7.mediaHostStatesManager
            java.util.Objects.requireNonNull(r4)
            java.util.LinkedHashMap r4 = r4.mediaHostStates
            java.lang.Integer r5 = java.lang.Integer.valueOf(r8)
            java.lang.Object r4 = r4.get(r5)
            com.android.systemui.media.MediaHostState r4 = (com.android.systemui.media.MediaHostState) r4
            com.android.systemui.util.animation.TransitionViewState r5 = r7.obtainViewState(r3)
            if (r5 != 0) goto L_0x003d
            return
        L_0x003d:
            com.android.systemui.util.animation.TransitionViewState r6 = r7.tmpState2
            com.android.systemui.util.animation.TransitionViewState r9 = r7.updateViewStateToCarouselSize(r5, r9, r6)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r9)
            com.android.systemui.util.animation.TransitionLayoutController r5 = r7.layoutController
            r5.setMeasureState(r9)
            r7.animateNextStateChange = r2
            com.android.systemui.util.animation.TransitionLayout r5 = r7.transitionLayout
            if (r5 != 0) goto L_0x0052
            return
        L_0x0052:
            com.android.systemui.util.animation.TransitionViewState r5 = r7.obtainViewState(r4)
            com.android.systemui.util.animation.TransitionViewState r6 = r7.tmpState3
            com.android.systemui.util.animation.TransitionViewState r8 = r7.updateViewStateToCarouselSize(r5, r8, r6)
            boolean r5 = r3.getVisible()
            if (r5 != 0) goto L_0x007d
            if (r8 == 0) goto L_0x00b8
            if (r4 == 0) goto L_0x00b8
            boolean r3 = r4.getVisible()
            if (r3 != 0) goto L_0x006d
            goto L_0x00b8
        L_0x006d:
            com.android.systemui.util.animation.TransitionLayoutController r9 = r7.layoutController
            com.android.systemui.util.animation.DisappearParameters r3 = r4.getDisappearParameters()
            com.android.systemui.util.animation.TransitionViewState r4 = r7.tmpState
            java.util.Objects.requireNonNull(r9)
            com.android.systemui.util.animation.TransitionViewState r9 = com.android.systemui.util.animation.TransitionLayoutController.getGoneState(r8, r3, r10, r4)
            goto L_0x00b8
        L_0x007d:
            r5 = 1065353216(0x3f800000, float:1.0)
            if (r4 == 0) goto L_0x0098
            boolean r4 = r4.getVisible()
            if (r4 != 0) goto L_0x0098
            com.android.systemui.util.animation.TransitionLayoutController r8 = r7.layoutController
            com.android.systemui.util.animation.DisappearParameters r3 = r3.getDisappearParameters()
            float r5 = r5 - r10
            com.android.systemui.util.animation.TransitionViewState r10 = r7.tmpState
            java.util.Objects.requireNonNull(r8)
            com.android.systemui.util.animation.TransitionViewState r9 = com.android.systemui.util.animation.TransitionLayoutController.getGoneState(r9, r3, r5, r10)
            goto L_0x00b8
        L_0x0098:
            int r3 = (r10 > r5 ? 1 : (r10 == r5 ? 0 : -1))
            if (r3 != 0) goto L_0x009e
            r3 = r1
            goto L_0x009f
        L_0x009e:
            r3 = r2
        L_0x009f:
            if (r3 != 0) goto L_0x00b8
            if (r8 != 0) goto L_0x00a4
            goto L_0x00b8
        L_0x00a4:
            r3 = 0
            int r3 = (r10 > r3 ? 1 : (r10 == r3 ? 0 : -1))
            if (r3 != 0) goto L_0x00ab
            r3 = r1
            goto L_0x00ac
        L_0x00ab:
            r3 = r2
        L_0x00ac:
            if (r3 == 0) goto L_0x00b0
            r9 = r8
            goto L_0x00b8
        L_0x00b0:
            com.android.systemui.util.animation.TransitionLayoutController r3 = r7.layoutController
            com.android.systemui.util.animation.TransitionViewState r4 = r7.tmpState
            com.android.systemui.util.animation.TransitionViewState r9 = r3.getInterpolatedState(r8, r9, r10, r4)
        L_0x00b8:
            com.android.systemui.util.animation.TransitionLayoutController r8 = r7.layoutController
            long r3 = r7.animationDuration
            long r5 = r7.animationDelay
            if (r0 == 0) goto L_0x00ca
            com.android.systemui.util.animation.TransitionViewState r7 = r8.currentState
            java.util.Objects.requireNonNull(r7)
            int r7 = r7.width
            if (r7 == 0) goto L_0x00cd
            goto L_0x00ce
        L_0x00ca:
            java.util.Objects.requireNonNull(r8)
        L_0x00cd:
            r1 = r2
        L_0x00ce:
            r7 = 0
            com.android.systemui.util.animation.TransitionViewState r10 = r9.copy(r7)
            r8.state = r10
            if (r11 != 0) goto L_0x010c
            com.android.systemui.util.animation.TransitionLayout r10 = r8.transitionLayout
            if (r10 != 0) goto L_0x00dc
            goto L_0x010c
        L_0x00dc:
            if (r1 == 0) goto L_0x00f6
            com.android.systemui.util.animation.TransitionViewState r9 = r8.currentState
            com.android.systemui.util.animation.TransitionViewState r7 = r9.copy(r7)
            r8.animationStartState = r7
            android.animation.ValueAnimator r7 = r8.animator
            r7.setDuration(r3)
            android.animation.ValueAnimator r7 = r8.animator
            r7.setStartDelay(r5)
            android.animation.ValueAnimator r7 = r8.animator
            r7.start()
            goto L_0x011e
        L_0x00f6:
            android.animation.ValueAnimator r7 = r8.animator
            boolean r7 = r7.isRunning()
            if (r7 != 0) goto L_0x011e
            com.android.systemui.util.animation.TransitionViewState r7 = r8.state
            r8.applyStateToLayout(r7)
            com.android.systemui.util.animation.TransitionViewState r7 = r8.currentState
            com.android.systemui.util.animation.TransitionViewState r7 = r9.copy(r7)
            r8.currentState = r7
            goto L_0x011e
        L_0x010c:
            android.animation.ValueAnimator r7 = r8.animator
            r7.cancel()
            com.android.systemui.util.animation.TransitionViewState r7 = r8.state
            r8.applyStateToLayout(r7)
            com.android.systemui.util.animation.TransitionViewState r7 = r8.currentState
            com.android.systemui.util.animation.TransitionViewState r7 = r9.copy(r7)
            r8.currentState = r7
        L_0x011e:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.MediaViewController.setCurrentState(int, int, float, boolean):void");
    }

    public MediaViewController(Context context2, ConfigurationController configurationController2, MediaHostStatesManager mediaHostStatesManager2) {
        this.context = context2;
        this.configurationController = configurationController2;
        this.mediaHostStatesManager = mediaHostStatesManager2;
        TransitionLayoutController transitionLayoutController = new TransitionLayoutController();
        this.layoutController = transitionLayoutController;
        this.measurement = new MeasurementOutput();
        this.type = TYPE.PLAYER;
        this.viewStates = new LinkedHashMap();
        this.currentEndLocation = -1;
        this.currentStartLocation = -1;
        this.currentTransitionProgress = 1.0f;
        this.tmpState = new TransitionViewState();
        this.tmpState2 = new TransitionViewState();
        this.tmpState3 = new TransitionViewState();
        this.tmpKey = new CacheKey(0);
        MediaViewController$configurationListener$1 mediaViewController$configurationListener$1 = new MediaViewController$configurationListener$1(this);
        this.configurationListener = mediaViewController$configurationListener$1;
        this.stateCallback = new MediaViewController$stateCallback$1(this);
        this.collapsedLayout = new ConstraintSet();
        this.expandedLayout = new ConstraintSet();
        Objects.requireNonNull(mediaHostStatesManager2);
        mediaHostStatesManager2.controllers.add(this);
        transitionLayoutController.sizeChangedListener = new Function2<Integer, Integer, Unit>(this) {
            public final /* synthetic */ MediaViewController this$0;

            {
                this.this$0 = r1;
            }

            public final Object invoke(Object obj, Object obj2) {
                int intValue = ((Number) obj).intValue();
                int intValue2 = ((Number) obj2).intValue();
                MediaViewController mediaViewController = this.this$0;
                Objects.requireNonNull(mediaViewController);
                mediaViewController.currentWidth = intValue;
                MediaViewController mediaViewController2 = this.this$0;
                Objects.requireNonNull(mediaViewController2);
                mediaViewController2.currentHeight = intValue2;
                MediaViewController mediaViewController3 = this.this$0;
                Objects.requireNonNull(mediaViewController3);
                Function0<Unit> function0 = mediaViewController3.sizeChangedListener;
                if (function0 == null) {
                    function0 = null;
                }
                function0.invoke();
                return Unit.INSTANCE;
            }
        };
        configurationController2.addCallback(mediaViewController$configurationListener$1);
    }
}
