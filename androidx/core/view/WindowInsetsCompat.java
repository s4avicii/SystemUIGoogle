package androidx.core.view;

import android.view.DisplayCutout;
import android.view.View;
import android.view.WindowInsets;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import java.util.Objects;
import java.util.WeakHashMap;

public final class WindowInsetsCompat {
    public static final WindowInsetsCompat CONSUMED = Impl30.CONSUMED;
    public final Impl mImpl;

    public static class BuilderImpl {
        public final WindowInsetsCompat mInsets;

        public BuilderImpl() {
            this(new WindowInsetsCompat());
        }

        public final void applyInsetTypes() {
        }

        public WindowInsetsCompat build() {
            throw null;
        }

        public void setStableInsets(Insets insets) {
            throw null;
        }

        public void setSystemWindowInsets(Insets insets) {
            throw null;
        }

        public BuilderImpl(WindowInsetsCompat windowInsetsCompat) {
            this.mInsets = windowInsetsCompat;
        }
    }

    public static class BuilderImpl29 extends BuilderImpl {
        public final WindowInsets.Builder mPlatBuilder;

        public BuilderImpl29() {
            this.mPlatBuilder = new WindowInsets.Builder();
        }

        public void setStableInsets(Insets insets) {
            this.mPlatBuilder.setStableInsets(insets.toPlatformInsets());
        }

        public void setSystemWindowInsets(Insets insets) {
            this.mPlatBuilder.setSystemWindowInsets(insets.toPlatformInsets());
        }

        public BuilderImpl29(WindowInsetsCompat windowInsetsCompat) {
            super(windowInsetsCompat);
            WindowInsets.Builder builder;
            WindowInsets windowInsets = windowInsetsCompat.toWindowInsets();
            if (windowInsets != null) {
                builder = new WindowInsets.Builder(windowInsets);
            } else {
                builder = new WindowInsets.Builder();
            }
            this.mPlatBuilder = builder;
        }

        public WindowInsetsCompat build() {
            applyInsetTypes();
            WindowInsetsCompat windowInsetsCompat = WindowInsetsCompat.toWindowInsetsCompat(this.mPlatBuilder.build(), (View) null);
            windowInsetsCompat.mImpl.setOverriddenInsets((Insets[]) null);
            return windowInsetsCompat;
        }
    }

    public static class BuilderImpl30 extends BuilderImpl29 {
        public BuilderImpl30() {
        }

        public BuilderImpl30(WindowInsetsCompat windowInsetsCompat) {
            super(windowInsetsCompat);
        }
    }

    public static class Impl {
        public static final WindowInsetsCompat CONSUMED;
        public final WindowInsetsCompat mHost;

        public void copyRootViewBounds(View view) {
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Impl)) {
                return false;
            }
            Impl impl = (Impl) obj;
            return isRound() == impl.isRound() && isConsumed() == impl.isConsumed() && Objects.equals(getSystemWindowInsets(), impl.getSystemWindowInsets()) && Objects.equals(getStableInsets(), impl.getStableInsets()) && Objects.equals(getDisplayCutout(), impl.getDisplayCutout());
        }

        public DisplayCutoutCompat getDisplayCutout() {
            return null;
        }

        public int hashCode() {
            return Objects.hash(new Object[]{Boolean.valueOf(isRound()), Boolean.valueOf(isConsumed()), getSystemWindowInsets(), getStableInsets(), getDisplayCutout()});
        }

        public boolean isConsumed() {
            return false;
        }

        public boolean isRound() {
            return false;
        }

        public void setOverriddenInsets(Insets[] insetsArr) {
        }

        public void setRootWindowInsets(WindowInsetsCompat windowInsetsCompat) {
        }

        static {
            WindowInsetsCompat build = new BuilderImpl30().build();
            Objects.requireNonNull(build);
            WindowInsetsCompat consumeDisplayCutout = build.mImpl.consumeDisplayCutout();
            Objects.requireNonNull(consumeDisplayCutout);
            WindowInsetsCompat consumeStableInsets = consumeDisplayCutout.mImpl.consumeStableInsets();
            Objects.requireNonNull(consumeStableInsets);
            CONSUMED = consumeStableInsets.mImpl.consumeSystemWindowInsets();
        }

        public Impl(WindowInsetsCompat windowInsetsCompat) {
            this.mHost = windowInsetsCompat;
        }

        public Insets getMandatorySystemGestureInsets() {
            return getSystemWindowInsets();
        }

        public Insets getSystemGestureInsets() {
            return getSystemWindowInsets();
        }

        public WindowInsetsCompat inset(int i, int i2, int i3, int i4) {
            return CONSUMED;
        }

        public WindowInsetsCompat consumeDisplayCutout() {
            return this.mHost;
        }

        public WindowInsetsCompat consumeStableInsets() {
            return this.mHost;
        }

        public WindowInsetsCompat consumeSystemWindowInsets() {
            return this.mHost;
        }

        public Insets getStableInsets() {
            return Insets.NONE;
        }

        public Insets getSystemWindowInsets() {
            return Insets.NONE;
        }
    }

    public static class Impl20 extends Impl {
        public Insets[] mOverriddenInsets;
        public final WindowInsets mPlatformInsets;
        public Insets mRootViewVisibleInsets;
        public WindowInsetsCompat mRootWindowInsets;
        public Insets mSystemWindowInsets = null;

        private Insets getVisibleInsets(View view) {
            throw new UnsupportedOperationException("getVisibleInsets() should not be called on API >= 30. Use WindowInsets.isVisible() instead.");
        }

        public final Insets getSystemWindowInsets() {
            if (this.mSystemWindowInsets == null) {
                this.mSystemWindowInsets = Insets.m10of(this.mPlatformInsets.getSystemWindowInsetLeft(), this.mPlatformInsets.getSystemWindowInsetTop(), this.mPlatformInsets.getSystemWindowInsetRight(), this.mPlatformInsets.getSystemWindowInsetBottom());
            }
            return this.mSystemWindowInsets;
        }

        public WindowInsetsCompat inset(int i, int i2, int i3, int i4) {
            BuilderImpl30 builderImpl30 = new BuilderImpl30(WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets, (View) null));
            builderImpl30.setSystemWindowInsets(WindowInsetsCompat.insetInsets(getSystemWindowInsets(), i, i2, i3, i4));
            builderImpl30.setStableInsets(WindowInsetsCompat.insetInsets(getStableInsets(), i, i2, i3, i4));
            return builderImpl30.build();
        }

        public boolean isRound() {
            return this.mPlatformInsets.isRound();
        }

        public Impl20(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat);
            this.mPlatformInsets = windowInsets;
        }

        public void copyRootViewBounds(View view) {
            Insets visibleInsets = getVisibleInsets(view);
            if (visibleInsets == null) {
                visibleInsets = Insets.NONE;
            }
            setRootViewData(visibleInsets);
        }

        public boolean equals(Object obj) {
            if (!super.equals(obj)) {
                return false;
            }
            return Objects.equals(this.mRootViewVisibleInsets, ((Impl20) obj).mRootViewVisibleInsets);
        }

        public void setOverriddenInsets(Insets[] insetsArr) {
            this.mOverriddenInsets = insetsArr;
        }

        public void setRootViewData(Insets insets) {
            this.mRootViewVisibleInsets = insets;
        }

        public void setRootWindowInsets(WindowInsetsCompat windowInsetsCompat) {
            this.mRootWindowInsets = windowInsetsCompat;
        }
    }

    public static class Impl21 extends Impl20 {
        public Insets mStableInsets = null;

        public WindowInsetsCompat consumeStableInsets() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeStableInsets(), (View) null);
        }

        public WindowInsetsCompat consumeSystemWindowInsets() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeSystemWindowInsets(), (View) null);
        }

        public final Insets getStableInsets() {
            if (this.mStableInsets == null) {
                this.mStableInsets = Insets.m10of(this.mPlatformInsets.getStableInsetLeft(), this.mPlatformInsets.getStableInsetTop(), this.mPlatformInsets.getStableInsetRight(), this.mPlatformInsets.getStableInsetBottom());
            }
            return this.mStableInsets;
        }

        public boolean isConsumed() {
            return this.mPlatformInsets.isConsumed();
        }

        public Impl21(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }
    }

    public static class Impl28 extends Impl21 {
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Impl28)) {
                return false;
            }
            Impl28 impl28 = (Impl28) obj;
            return Objects.equals(this.mPlatformInsets, impl28.mPlatformInsets) && Objects.equals(this.mRootViewVisibleInsets, impl28.mRootViewVisibleInsets);
        }

        public WindowInsetsCompat consumeDisplayCutout() {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.consumeDisplayCutout(), (View) null);
        }

        public DisplayCutoutCompat getDisplayCutout() {
            DisplayCutout displayCutout = this.mPlatformInsets.getDisplayCutout();
            if (displayCutout == null) {
                return null;
            }
            return new DisplayCutoutCompat(displayCutout);
        }

        public int hashCode() {
            return this.mPlatformInsets.hashCode();
        }

        public Impl28(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }
    }

    public static class Impl29 extends Impl28 {
        public Insets mMandatorySystemGestureInsets = null;
        public Insets mSystemGestureInsets = null;
        public Insets mTappableElementInsets = null;

        public Insets getMandatorySystemGestureInsets() {
            if (this.mMandatorySystemGestureInsets == null) {
                this.mMandatorySystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getMandatorySystemGestureInsets());
            }
            return this.mMandatorySystemGestureInsets;
        }

        public Insets getSystemGestureInsets() {
            if (this.mSystemGestureInsets == null) {
                this.mSystemGestureInsets = Insets.toCompatInsets(this.mPlatformInsets.getSystemGestureInsets());
            }
            return this.mSystemGestureInsets;
        }

        public WindowInsetsCompat inset(int i, int i2, int i3, int i4) {
            return WindowInsetsCompat.toWindowInsetsCompat(this.mPlatformInsets.inset(i, i2, i3, i4), (View) null);
        }

        public Impl29(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }
    }

    public WindowInsetsCompat(WindowInsets windowInsets) {
        this.mImpl = new Impl30(this, windowInsets);
    }

    public static Insets insetInsets(Insets insets, int i, int i2, int i3, int i4) {
        int max = Math.max(0, insets.left - i);
        int max2 = Math.max(0, insets.top - i2);
        int max3 = Math.max(0, insets.right - i3);
        int max4 = Math.max(0, insets.bottom - i4);
        if (max == i && max2 == i2 && max3 == i3 && max4 == i4) {
            return insets;
        }
        return Insets.m10of(max, max2, max3, max4);
    }

    public static WindowInsetsCompat toWindowInsetsCompat(WindowInsets windowInsets, View view) {
        Objects.requireNonNull(windowInsets);
        WindowInsetsCompat windowInsetsCompat = new WindowInsetsCompat(windowInsets);
        if (view != null) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            if (ViewCompat.Api19Impl.isAttachedToWindow(view)) {
                windowInsetsCompat.mImpl.setRootWindowInsets(ViewCompat.Api23Impl.getRootWindowInsets(view));
                windowInsetsCompat.mImpl.copyRootViewBounds(view.getRootView());
            }
        }
        return windowInsetsCompat;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WindowInsetsCompat)) {
            return false;
        }
        return Objects.equals(this.mImpl, ((WindowInsetsCompat) obj).mImpl);
    }

    @Deprecated
    public final int getSystemWindowInsetBottom() {
        return this.mImpl.getSystemWindowInsets().bottom;
    }

    @Deprecated
    public final int getSystemWindowInsetLeft() {
        return this.mImpl.getSystemWindowInsets().left;
    }

    @Deprecated
    public final int getSystemWindowInsetRight() {
        return this.mImpl.getSystemWindowInsets().right;
    }

    @Deprecated
    public final int getSystemWindowInsetTop() {
        return this.mImpl.getSystemWindowInsets().top;
    }

    public final int hashCode() {
        Impl impl = this.mImpl;
        if (impl == null) {
            return 0;
        }
        return impl.hashCode();
    }

    @Deprecated
    public final WindowInsetsCompat replaceSystemWindowInsets(int i, int i2, int i3, int i4) {
        BuilderImpl30 builderImpl30 = new BuilderImpl30(this);
        builderImpl30.setSystemWindowInsets(Insets.m10of(i, i2, i3, i4));
        return builderImpl30.build();
    }

    public final WindowInsets toWindowInsets() {
        Impl impl = this.mImpl;
        if (impl instanceof Impl20) {
            return ((Impl20) impl).mPlatformInsets;
        }
        return null;
    }

    public WindowInsetsCompat() {
        this.mImpl = new Impl(this);
    }

    public static class Impl30 extends Impl29 {
        public static final WindowInsetsCompat CONSUMED = WindowInsetsCompat.toWindowInsetsCompat(WindowInsets.CONSUMED, (View) null);

        public final void copyRootViewBounds(View view) {
        }

        public Impl30(WindowInsetsCompat windowInsetsCompat, WindowInsets windowInsets) {
            super(windowInsetsCompat, windowInsets);
        }
    }
}
