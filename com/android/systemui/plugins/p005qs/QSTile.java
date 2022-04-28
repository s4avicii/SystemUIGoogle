package com.android.systemui.plugins.p005qs;

import android.content.Context;
import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.graphics.drawable.Drawable;
import android.metrics.LogMaker;
import android.view.View;
import com.android.internal.logging.InstanceId;
import com.android.systemui.plugins.annotations.Dependencies;
import com.android.systemui.plugins.annotations.DependsOn;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import java.util.Objects;
import java.util.function.Supplier;

@Dependencies({@DependsOn(target = QSIconView.class), @DependsOn(target = Callback.class), @DependsOn(target = Icon.class), @DependsOn(target = State.class)})
@ProvidesInterface(version = 3)
/* renamed from: com.android.systemui.plugins.qs.QSTile */
public interface QSTile {
    public static final int VERSION = 3;

    @ProvidesInterface(version = 1)
    /* renamed from: com.android.systemui.plugins.qs.QSTile$BooleanState */
    public static class BooleanState extends State {
        public static final int VERSION = 1;
        public boolean forceExpandIcon;
        public boolean value;

        public boolean copyTo(State state) {
            boolean z;
            BooleanState booleanState = (BooleanState) state;
            if (!super.copyTo(state) && booleanState.value == this.value && booleanState.forceExpandIcon == this.forceExpandIcon) {
                z = false;
            } else {
                z = true;
            }
            booleanState.value = this.value;
            booleanState.forceExpandIcon = this.forceExpandIcon;
            return z;
        }

        public State copy() {
            BooleanState booleanState = new BooleanState();
            copyTo(booleanState);
            return booleanState;
        }

        public StringBuilder toStringBuilder() {
            StringBuilder stringBuilder = super.toStringBuilder();
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(",value=");
            m.append(this.value);
            stringBuilder.insert(stringBuilder.length() - 1, m.toString());
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m(",forceExpandIcon=");
            m2.append(this.forceExpandIcon);
            stringBuilder.insert(stringBuilder.length() - 1, m2.toString());
            return stringBuilder;
        }
    }

    @ProvidesInterface(version = 2)
    /* renamed from: com.android.systemui.plugins.qs.QSTile$Callback */
    public interface Callback {
        public static final int VERSION = 2;

        void onStateChanged(State state);
    }

    @ProvidesInterface(version = 1)
    /* renamed from: com.android.systemui.plugins.qs.QSTile$Icon */
    public static abstract class Icon {
        public static final int VERSION = 1;

        public abstract Drawable getDrawable(Context context);

        public int getPadding() {
            return 0;
        }

        public String toString() {
            return "Icon";
        }

        public int hashCode() {
            return Icon.class.hashCode();
        }

        public Drawable getInvisibleDrawable(Context context) {
            return getDrawable(context);
        }
    }

    @ProvidesInterface(version = 1)
    /* renamed from: com.android.systemui.plugins.qs.QSTile$SignalState */
    public static final class SignalState extends BooleanState {
        public static final int VERSION = 1;
        public boolean activityIn;
        public boolean activityOut;
        public boolean isOverlayIconWide;
        public int overlayIconId;

        public boolean copyTo(State state) {
            boolean z;
            SignalState signalState = (SignalState) state;
            boolean z2 = signalState.activityIn;
            boolean z3 = this.activityIn;
            if (z2 == z3 && signalState.activityOut == this.activityOut && signalState.isOverlayIconWide == this.isOverlayIconWide && signalState.overlayIconId == this.overlayIconId) {
                z = false;
            } else {
                z = true;
            }
            signalState.activityIn = z3;
            signalState.activityOut = this.activityOut;
            signalState.isOverlayIconWide = this.isOverlayIconWide;
            signalState.overlayIconId = this.overlayIconId;
            if (super.copyTo(state) || z) {
                return true;
            }
            return false;
        }

        public State copy() {
            SignalState signalState = new SignalState();
            copyTo(signalState);
            return signalState;
        }

        public StringBuilder toStringBuilder() {
            StringBuilder stringBuilder = super.toStringBuilder();
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m(",activityIn=");
            m.append(this.activityIn);
            stringBuilder.insert(stringBuilder.length() - 1, m.toString());
            StringBuilder m2 = VendorAtomValue$$ExternalSyntheticOutline1.m1m(",activityOut=");
            m2.append(this.activityOut);
            stringBuilder.insert(stringBuilder.length() - 1, m2.toString());
            return stringBuilder;
        }
    }

    @ProvidesInterface(version = 2)
    /* renamed from: com.android.systemui.plugins.qs.QSTile$SlashState */
    public static class SlashState {
        public static final int VERSION = 2;
        public boolean isSlashed;
        public float rotation;

        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            try {
                return ((SlashState) obj).rotation == this.rotation && ((SlashState) obj).isSlashed == this.isSlashed;
            } catch (ClassCastException unused) {
                return false;
            }
        }

        public SlashState copy() {
            SlashState slashState = new SlashState();
            slashState.rotation = this.rotation;
            slashState.isSlashed = this.isSlashed;
            return slashState;
        }

        public String toString() {
            StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("isSlashed=");
            m.append(this.isSlashed);
            m.append(",rotation=");
            m.append(this.rotation);
            return m.toString();
        }
    }

    @ProvidesInterface(version = 1)
    /* renamed from: com.android.systemui.plugins.qs.QSTile$State */
    public static class State {
        public static final int DEFAULT_STATE = 2;
        public static final int VERSION = 1;
        public CharSequence contentDescription;
        public boolean disabledByPolicy;
        public CharSequence dualLabelContentDescription;
        public boolean dualTarget = false;
        public String expandedAccessibilityClassName;
        public boolean handlesLongClick = true;
        public Icon icon;
        public Supplier<Icon> iconSupplier;
        public boolean isTransient = false;
        public CharSequence label;
        public CharSequence secondaryLabel;
        public boolean showRippleEffect = true;
        public Drawable sideViewCustomDrawable;
        public SlashState slash;
        public String spec;
        public int state = 2;
        public CharSequence stateDescription;

        public State copy() {
            State state2 = new State();
            copyTo(state2);
            return state2;
        }

        public boolean copyTo(State state2) {
            boolean z;
            SlashState slashState;
            if (state2 == null) {
                throw new IllegalArgumentException();
            } else if (state2.getClass().equals(getClass())) {
                if (!Objects.equals(state2.spec, this.spec) || !Objects.equals(state2.icon, this.icon) || !Objects.equals(state2.iconSupplier, this.iconSupplier) || !Objects.equals(state2.label, this.label) || !Objects.equals(state2.secondaryLabel, this.secondaryLabel) || !Objects.equals(state2.contentDescription, this.contentDescription) || !Objects.equals(state2.stateDescription, this.stateDescription) || !Objects.equals(state2.dualLabelContentDescription, this.dualLabelContentDescription) || !Objects.equals(state2.expandedAccessibilityClassName, this.expandedAccessibilityClassName) || !Objects.equals(Boolean.valueOf(state2.disabledByPolicy), Boolean.valueOf(this.disabledByPolicy)) || !Objects.equals(Integer.valueOf(state2.state), Integer.valueOf(this.state)) || !Objects.equals(Boolean.valueOf(state2.isTransient), Boolean.valueOf(this.isTransient)) || !Objects.equals(Boolean.valueOf(state2.dualTarget), Boolean.valueOf(this.dualTarget)) || !Objects.equals(state2.slash, this.slash) || !Objects.equals(Boolean.valueOf(state2.handlesLongClick), Boolean.valueOf(this.handlesLongClick)) || !Objects.equals(Boolean.valueOf(state2.showRippleEffect), Boolean.valueOf(this.showRippleEffect)) || !Objects.equals(state2.sideViewCustomDrawable, this.sideViewCustomDrawable)) {
                    z = true;
                } else {
                    z = false;
                }
                state2.spec = this.spec;
                state2.icon = this.icon;
                state2.iconSupplier = this.iconSupplier;
                state2.label = this.label;
                state2.secondaryLabel = this.secondaryLabel;
                state2.contentDescription = this.contentDescription;
                state2.stateDescription = this.stateDescription;
                state2.dualLabelContentDescription = this.dualLabelContentDescription;
                state2.expandedAccessibilityClassName = this.expandedAccessibilityClassName;
                state2.disabledByPolicy = this.disabledByPolicy;
                state2.state = this.state;
                state2.dualTarget = this.dualTarget;
                state2.isTransient = this.isTransient;
                SlashState slashState2 = this.slash;
                if (slashState2 != null) {
                    slashState = slashState2.copy();
                } else {
                    slashState = null;
                }
                state2.slash = slashState;
                state2.handlesLongClick = this.handlesLongClick;
                state2.showRippleEffect = this.showRippleEffect;
                state2.sideViewCustomDrawable = this.sideViewCustomDrawable;
                return z;
            } else {
                throw new IllegalArgumentException();
            }
        }

        public StringBuilder toStringBuilder() {
            StringBuilder sb = new StringBuilder(getClass().getSimpleName());
            sb.append('[');
            sb.append("spec=");
            sb.append(this.spec);
            sb.append(",icon=");
            sb.append(this.icon);
            sb.append(",iconSupplier=");
            sb.append(this.iconSupplier);
            sb.append(",label=");
            sb.append(this.label);
            sb.append(",secondaryLabel=");
            sb.append(this.secondaryLabel);
            sb.append(",contentDescription=");
            sb.append(this.contentDescription);
            sb.append(",stateDescription=");
            sb.append(this.stateDescription);
            sb.append(",dualLabelContentDescription=");
            sb.append(this.dualLabelContentDescription);
            sb.append(",expandedAccessibilityClassName=");
            sb.append(this.expandedAccessibilityClassName);
            sb.append(",disabledByPolicy=");
            sb.append(this.disabledByPolicy);
            sb.append(",dualTarget=");
            sb.append(this.dualTarget);
            sb.append(",isTransient=");
            sb.append(this.isTransient);
            sb.append(",state=");
            sb.append(this.state);
            sb.append(",slash=\"");
            sb.append(this.slash);
            sb.append("\"");
            sb.append(",sideViewCustomDrawable=");
            sb.append(this.sideViewCustomDrawable);
            sb.append(']');
            return sb;
        }

        public String toString() {
            return toStringBuilder().toString();
        }
    }

    void addCallback(Callback callback);

    @Deprecated
    void clearState() {
    }

    void click(View view);

    QSIconView createTileView(Context context);

    void destroy();

    InstanceId getInstanceId();

    @Deprecated
    int getMetricsCategory();

    State getState();

    CharSequence getTileLabel();

    String getTileSpec();

    boolean isAvailable();

    boolean isTileReady() {
        return false;
    }

    void longClick(View view);

    LogMaker populate(LogMaker logMaker) {
        return logMaker;
    }

    void refreshState();

    void removeCallback(Callback callback);

    void removeCallbacks();

    void secondaryClick(View view);

    void setDetailListening(boolean z);

    void setListening(Object obj, boolean z);

    void setTileSpec(String str);

    void userSwitch(int i);

    String getMetricsSpec() {
        return getClass().getSimpleName();
    }
}
