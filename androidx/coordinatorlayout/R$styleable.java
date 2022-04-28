package androidx.coordinatorlayout;

import android.content.res.AssetManager;
import com.android.p012wm.shell.C1777R;
import java.util.HashMap;
import kotlinx.coroutines.scheduling.TaskContext;

public class R$styleable implements TaskContext {
    public static final int[] CoordinatorLayout = {C1777R.attr.keylines, C1777R.attr.statusBarBackground};
    public static final int[] CoordinatorLayout_Layout = {16842931, C1777R.attr.layout_anchor, C1777R.attr.layout_anchorGravity, C1777R.attr.layout_behavior, C1777R.attr.layout_dodgeInsetEdges, C1777R.attr.layout_insetEdge, C1777R.attr.layout_keyline};
    public static final R$styleable INSTANCE = new R$styleable();

    public void afterTask() {
    }

    public int getTaskMode() {
        return 0;
    }

    public float predict(Object[] objArr) {
        return -1.0f;
    }

    public void release() {
    }

    public HashMap loadVocab(AssetManager assetManager) {
        return new HashMap();
    }
}
