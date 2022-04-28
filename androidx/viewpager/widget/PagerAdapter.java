package androidx.viewpager.widget;

import android.database.DataSetObservable;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

public abstract class PagerAdapter {
    public final DataSetObservable mObservable = new DataSetObservable();
    public DataSetObserver mViewPagerObserver;

    public abstract int getCount();

    public int getItemPosition(Object obj) {
        return -1;
    }

    public abstract boolean isViewFromObject(View view, Object obj);

    public final void notifyDataSetChanged() {
        synchronized (this) {
            DataSetObserver dataSetObserver = this.mViewPagerObserver;
            if (dataSetObserver != null) {
                dataSetObserver.onChanged();
            }
        }
        this.mObservable.notifyChanged();
    }

    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        throw new UnsupportedOperationException("Required method destroyItem was not overridden");
    }

    public Object instantiateItem(ViewGroup viewGroup, int i) {
        throw new UnsupportedOperationException("Required method instantiateItem was not overridden");
    }
}
