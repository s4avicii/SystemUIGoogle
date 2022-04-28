package androidx.fragment.app.strictmode;

import android.frameworks.stats.VendorAtomValue$$ExternalSyntheticOutline1;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

public final class FragmentTagUsageViolation extends Violation {
    private final ViewGroup mContainer;

    public final String getMessage() {
        StringBuilder m = VendorAtomValue$$ExternalSyntheticOutline1.m1m("Attempting to use <fragment> tag to add fragment ");
        m.append(this.mFragment);
        m.append(" to container ");
        m.append(this.mContainer);
        return m.toString();
    }

    public FragmentTagUsageViolation(Fragment fragment, ViewGroup viewGroup) {
        super(fragment);
        this.mContainer = viewGroup;
    }
}
