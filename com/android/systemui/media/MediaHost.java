package com.android.systemui.media;

import android.graphics.Rect;
import android.util.ArraySet;
import com.android.systemui.util.animation.DisappearParameters;
import com.android.systemui.util.animation.MeasurementInput;
import com.android.systemui.util.animation.UniqueObjectHostView;
import java.util.Iterator;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

/* compiled from: MediaHost.kt */
public final class MediaHost implements MediaHostState {
    public final Rect currentBounds = new Rect();
    public UniqueObjectHostView hostView;
    public boolean inited;
    public final MediaHost$listener$1 listener = new MediaHost$listener$1(this);
    public boolean listeningToMediaData;
    public int location = -1;
    public final MediaDataManager mediaDataManager;
    public final MediaHierarchyManager mediaHierarchyManager;
    public final MediaHostStatesManager mediaHostStatesManager;
    public final MediaHostStateHolder state;
    public final int[] tmpLocationOnScreen = {0, 0};
    public ArraySet<Function1<Boolean, Unit>> visibleChangedListeners = new ArraySet<>();

    /* compiled from: MediaHost.kt */
    public static final class MediaHostStateHolder implements MediaHostState {
        public Function0<Unit> changedListener;
        public DisappearParameters disappearParameters;
        public float expansion;
        public boolean falsingProtectionNeeded;
        public int lastDisappearHash;
        public MeasurementInput measurementInput;
        public boolean showsOnlyActiveMedia;
        public boolean visible = true;

        public final MediaHostStateHolder copy() {
            MeasurementInput measurementInput2;
            MediaHostStateHolder mediaHostStateHolder = new MediaHostStateHolder();
            mediaHostStateHolder.setExpansion(this.expansion);
            boolean z = this.showsOnlyActiveMedia;
            if (!Boolean.valueOf(z).equals(Boolean.valueOf(mediaHostStateHolder.showsOnlyActiveMedia))) {
                mediaHostStateHolder.showsOnlyActiveMedia = z;
                Function0<Unit> function0 = mediaHostStateHolder.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
            MeasurementInput measurementInput3 = this.measurementInput;
            if (measurementInput3 == null) {
                measurementInput2 = null;
            } else {
                measurementInput2 = new MeasurementInput(measurementInput3.widthMeasureSpec, measurementInput3.heightMeasureSpec);
            }
            boolean z2 = false;
            if (measurementInput2 != null && measurementInput2.equals(mediaHostStateHolder.measurementInput)) {
                z2 = true;
            }
            if (!z2) {
                mediaHostStateHolder.measurementInput = measurementInput2;
                Function0<Unit> function02 = mediaHostStateHolder.changedListener;
                if (function02 != null) {
                    function02.invoke();
                }
            }
            boolean z3 = this.visible;
            if (mediaHostStateHolder.visible != z3) {
                mediaHostStateHolder.visible = z3;
                Function0<Unit> function03 = mediaHostStateHolder.changedListener;
                if (function03 != null) {
                    function03.invoke();
                }
            }
            DisappearParameters disappearParameters2 = this.disappearParameters;
            Objects.requireNonNull(disappearParameters2);
            DisappearParameters disappearParameters3 = new DisappearParameters();
            disappearParameters3.disappearSize.set(disappearParameters2.disappearSize);
            disappearParameters3.gonePivot.set(disappearParameters2.gonePivot);
            disappearParameters3.contentTranslationFraction.set(disappearParameters2.contentTranslationFraction);
            disappearParameters3.disappearStart = disappearParameters2.disappearStart;
            disappearParameters3.disappearEnd = disappearParameters2.disappearEnd;
            disappearParameters3.fadeStartPosition = disappearParameters2.fadeStartPosition;
            mediaHostStateHolder.setDisappearParameters(disappearParameters3);
            boolean z4 = this.falsingProtectionNeeded;
            if (mediaHostStateHolder.falsingProtectionNeeded != z4) {
                mediaHostStateHolder.falsingProtectionNeeded = z4;
                Function0<Unit> function04 = mediaHostStateHolder.changedListener;
                if (function04 != null) {
                    function04.invoke();
                }
            }
            return mediaHostStateHolder;
        }

        public final boolean equals(Object obj) {
            boolean z;
            if (!(obj instanceof MediaHostState)) {
                return false;
            }
            MediaHostState mediaHostState = (MediaHostState) obj;
            if (!Objects.equals(this.measurementInput, mediaHostState.getMeasurementInput())) {
                return false;
            }
            if (this.expansion == mediaHostState.getExpansion()) {
                z = true;
            } else {
                z = false;
            }
            if (z && this.showsOnlyActiveMedia == mediaHostState.getShowsOnlyActiveMedia() && this.visible == mediaHostState.getVisible() && this.falsingProtectionNeeded == mediaHostState.getFalsingProtectionNeeded() && this.disappearParameters.equals(mediaHostState.getDisappearParameters())) {
                return true;
            }
            return false;
        }

        public final int hashCode() {
            int i;
            int i2;
            MeasurementInput measurementInput2 = this.measurementInput;
            if (measurementInput2 == null) {
                i = 0;
            } else {
                i = measurementInput2.hashCode();
            }
            int hashCode = Float.hashCode(this.expansion);
            int hashCode2 = Boolean.hashCode(this.falsingProtectionNeeded);
            int hashCode3 = (Boolean.hashCode(this.showsOnlyActiveMedia) + ((hashCode2 + ((hashCode + (i * 31)) * 31)) * 31)) * 31;
            if (this.visible) {
                i2 = 1;
            } else {
                i2 = 2;
            }
            return this.disappearParameters.hashCode() + ((hashCode3 + i2) * 31);
        }

        public MediaHostStateHolder() {
            DisappearParameters disappearParameters2 = new DisappearParameters();
            this.disappearParameters = disappearParameters2;
            this.lastDisappearHash = disappearParameters2.hashCode();
        }

        public final void setDisappearParameters(DisappearParameters disappearParameters2) {
            int hashCode = disappearParameters2.hashCode();
            if (!Integer.valueOf(this.lastDisappearHash).equals(Integer.valueOf(hashCode))) {
                this.disappearParameters = disappearParameters2;
                this.lastDisappearHash = hashCode;
                Function0<Unit> function0 = this.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }

        public final void setExpansion(float f) {
            if (!Float.valueOf(f).equals(Float.valueOf(this.expansion))) {
                this.expansion = f;
                Function0<Unit> function0 = this.changedListener;
                if (function0 != null) {
                    function0.invoke();
                }
            }
        }

        public final DisappearParameters getDisappearParameters() {
            return this.disappearParameters;
        }

        public final float getExpansion() {
            return this.expansion;
        }

        public final boolean getFalsingProtectionNeeded() {
            return this.falsingProtectionNeeded;
        }

        public final MeasurementInput getMeasurementInput() {
            return this.measurementInput;
        }

        public final boolean getShowsOnlyActiveMedia() {
            return this.showsOnlyActiveMedia;
        }

        public final boolean getVisible() {
            return this.visible;
        }
    }

    public final MediaHostStateHolder copy() {
        return this.state.copy();
    }

    public final void setExpansion(float f) {
        this.state.setExpansion(f);
    }

    public final DisappearParameters getDisappearParameters() {
        MediaHostStateHolder mediaHostStateHolder = this.state;
        Objects.requireNonNull(mediaHostStateHolder);
        return mediaHostStateHolder.disappearParameters;
    }

    public final float getExpansion() {
        MediaHostStateHolder mediaHostStateHolder = this.state;
        Objects.requireNonNull(mediaHostStateHolder);
        return mediaHostStateHolder.expansion;
    }

    public final boolean getFalsingProtectionNeeded() {
        MediaHostStateHolder mediaHostStateHolder = this.state;
        Objects.requireNonNull(mediaHostStateHolder);
        return mediaHostStateHolder.falsingProtectionNeeded;
    }

    public final UniqueObjectHostView getHostView() {
        UniqueObjectHostView uniqueObjectHostView = this.hostView;
        if (uniqueObjectHostView != null) {
            return uniqueObjectHostView;
        }
        return null;
    }

    public final MeasurementInput getMeasurementInput() {
        MediaHostStateHolder mediaHostStateHolder = this.state;
        Objects.requireNonNull(mediaHostStateHolder);
        return mediaHostStateHolder.measurementInput;
    }

    public final boolean getShowsOnlyActiveMedia() {
        MediaHostStateHolder mediaHostStateHolder = this.state;
        Objects.requireNonNull(mediaHostStateHolder);
        return mediaHostStateHolder.showsOnlyActiveMedia;
    }

    public final boolean getVisible() {
        MediaHostStateHolder mediaHostStateHolder = this.state;
        Objects.requireNonNull(mediaHostStateHolder);
        return mediaHostStateHolder.visible;
    }

    public final void init(int i) {
        if (!this.inited) {
            this.inited = true;
            this.location = i;
            MediaHierarchyManager mediaHierarchyManager2 = this.mediaHierarchyManager;
            Objects.requireNonNull(mediaHierarchyManager2);
            UniqueObjectHostView uniqueObjectHostView = new UniqueObjectHostView(mediaHierarchyManager2.context);
            uniqueObjectHostView.addOnAttachStateChangeListener(new MediaHierarchyManager$createUniqueObjectHost$1(mediaHierarchyManager2, uniqueObjectHostView));
            this.hostView = uniqueObjectHostView;
            this.visibleChangedListeners.add(new MediaHierarchyManager$register$1(this, mediaHierarchyManager2));
            MediaHost[] mediaHostArr = mediaHierarchyManager2.mediaHosts;
            int i2 = this.location;
            mediaHostArr[i2] = this;
            if (i2 == mediaHierarchyManager2.desiredLocation) {
                mediaHierarchyManager2.desiredLocation = -1;
            }
            if (i2 == mediaHierarchyManager2.currentAttachmentLocation) {
                mediaHierarchyManager2.currentAttachmentLocation = -1;
            }
            MediaHierarchyManager.updateDesiredLocation$default(mediaHierarchyManager2, false, 3);
            this.hostView = uniqueObjectHostView;
            setListeningToMediaData(true);
            getHostView().addOnAttachStateChangeListener(new MediaHost$init$1(this));
            UniqueObjectHostView hostView2 = getHostView();
            MediaHost$init$2 mediaHost$init$2 = new MediaHost$init$2(this, i);
            Objects.requireNonNull(hostView2);
            hostView2.measurementManager = mediaHost$init$2;
            MediaHostStateHolder mediaHostStateHolder = this.state;
            MediaHost$init$3 mediaHost$init$3 = new MediaHost$init$3(this, i);
            Objects.requireNonNull(mediaHostStateHolder);
            mediaHostStateHolder.changedListener = mediaHost$init$3;
            updateViewVisibility();
        }
    }

    public final void setListeningToMediaData(boolean z) {
        if (z != this.listeningToMediaData) {
            this.listeningToMediaData = z;
            if (z) {
                this.mediaDataManager.addListener(this.listener);
                return;
            }
            MediaDataManager mediaDataManager2 = this.mediaDataManager;
            MediaHost$listener$1 mediaHost$listener$1 = this.listener;
            Objects.requireNonNull(mediaDataManager2);
            MediaDataFilter mediaDataFilter = mediaDataManager2.mediaDataFilter;
            Objects.requireNonNull(mediaDataFilter);
            mediaDataFilter._listeners.remove(mediaHost$listener$1);
        }
    }

    public final void updateViewVisibility() {
        boolean z;
        MediaHostStateHolder mediaHostStateHolder = this.state;
        int i = 0;
        if (getShowsOnlyActiveMedia()) {
            z = this.mediaDataManager.hasActiveMedia();
        } else {
            MediaDataManager mediaDataManager2 = this.mediaDataManager;
            Objects.requireNonNull(mediaDataManager2);
            MediaDataFilter mediaDataFilter = mediaDataManager2.mediaDataFilter;
            Objects.requireNonNull(mediaDataFilter);
            if (!(!mediaDataFilter.userEntries.isEmpty())) {
                SmartspaceMediaData smartspaceMediaData = mediaDataFilter.smartspaceMediaData;
                Objects.requireNonNull(smartspaceMediaData);
                if (!smartspaceMediaData.isActive) {
                    z = false;
                }
            }
            z = true;
        }
        Objects.requireNonNull(mediaHostStateHolder);
        if (mediaHostStateHolder.visible != z) {
            mediaHostStateHolder.visible = z;
            Function0<Unit> function0 = mediaHostStateHolder.changedListener;
            if (function0 != null) {
                function0.invoke();
            }
        }
        if (!getVisible()) {
            i = 8;
        }
        if (i != getHostView().getVisibility()) {
            getHostView().setVisibility(i);
            Iterator<Function1<Boolean, Unit>> it = this.visibleChangedListeners.iterator();
            while (it.hasNext()) {
                it.next().invoke(Boolean.valueOf(getVisible()));
            }
        }
    }

    public MediaHost(MediaHostStateHolder mediaHostStateHolder, MediaHierarchyManager mediaHierarchyManager2, MediaDataManager mediaDataManager2, MediaHostStatesManager mediaHostStatesManager2) {
        this.state = mediaHostStateHolder;
        this.mediaHierarchyManager = mediaHierarchyManager2;
        this.mediaDataManager = mediaDataManager2;
        this.mediaHostStatesManager = mediaHostStatesManager2;
    }

    public final Rect getCurrentBounds() {
        getHostView().getLocationOnScreen(this.tmpLocationOnScreen);
        int i = 0;
        int paddingLeft = getHostView().getPaddingLeft() + this.tmpLocationOnScreen[0];
        int paddingTop = getHostView().getPaddingTop() + this.tmpLocationOnScreen[1];
        int width = (getHostView().getWidth() + this.tmpLocationOnScreen[0]) - getHostView().getPaddingRight();
        int height = (getHostView().getHeight() + this.tmpLocationOnScreen[1]) - getHostView().getPaddingBottom();
        if (width < paddingLeft) {
            paddingLeft = 0;
            width = 0;
        }
        if (height < paddingTop) {
            height = 0;
        } else {
            i = paddingTop;
        }
        this.currentBounds.set(paddingLeft, i, width, height);
        return this.currentBounds;
    }
}
