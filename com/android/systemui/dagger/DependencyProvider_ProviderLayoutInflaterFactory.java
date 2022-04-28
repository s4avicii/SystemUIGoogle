package com.android.systemui.dagger;

import android.content.Context;
import android.view.LayoutInflater;
import com.android.systemui.flags.Flags;
import com.android.systemui.media.MediaFlags;
import com.android.systemui.media.nearby.NearbyMediaDevicesManager;
import com.android.systemui.statusbar.policy.BatteryController;
import com.google.android.systemui.reversecharging.ReverseChargingViewController;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import java.util.Objects;
import java.util.Optional;
import javax.inject.Provider;

public final class DependencyProvider_ProviderLayoutInflaterFactory implements Factory {
    public final /* synthetic */ int $r8$classId;
    public final Provider contextProvider;
    public final Object module;

    public /* synthetic */ DependencyProvider_ProviderLayoutInflaterFactory(Provider provider, Provider provider2, int i) {
        this.$r8$classId = i;
        this.contextProvider = provider;
        this.module = provider2;
    }

    public final Object get() {
        switch (this.$r8$classId) {
            case 0:
                Objects.requireNonNull((DependencyProvider) this.module);
                LayoutInflater from = LayoutInflater.from((Context) this.contextProvider.get());
                Objects.requireNonNull(from, "Cannot return null from a non-@Nullable @Provides method");
                return from;
            case 1:
                return get();
            default:
                return get();
        }
    }

    public DependencyProvider_ProviderLayoutInflaterFactory(DependencyProvider dependencyProvider, Provider provider) {
        this.$r8$classId = 0;
        this.module = dependencyProvider;
        this.contextProvider = provider;
    }

    /* renamed from: get  reason: collision with other method in class */
    public final Optional m196get() {
        Optional optional;
        Optional optional2;
        switch (this.$r8$classId) {
            case 1:
                MediaFlags mediaFlags = (MediaFlags) this.contextProvider.get();
                Lazy lazy = DoubleCheck.lazy((Provider) this.module);
                Objects.requireNonNull(mediaFlags);
                if (!mediaFlags.featureFlags.isEnabled(Flags.MEDIA_NEARBY_DEVICES)) {
                    optional2 = Optional.empty();
                } else {
                    optional2 = Optional.of((NearbyMediaDevicesManager) lazy.get());
                }
                Objects.requireNonNull(optional2, "Cannot return null from a non-@Nullable @Provides method");
                return optional2;
            default:
                Lazy lazy2 = DoubleCheck.lazy((Provider) this.module);
                if (((BatteryController) this.contextProvider.get()).isReverseSupported()) {
                    optional = Optional.of((ReverseChargingViewController) lazy2.get());
                } else {
                    optional = Optional.empty();
                }
                Objects.requireNonNull(optional, "Cannot return null from a non-@Nullable @Provides method");
                return optional;
        }
    }
}
