package androidx.viewpager2.adapter;

import android.os.Parcelable;

public interface StatefulAdapter {
    void restoreState();

    Parcelable saveState();
}
