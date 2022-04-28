package androidx.transition;

import android.graphics.Rect;
import android.util.Property;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import java.util.WeakHashMap;

public final class ViewUtils {
    public static final C04182 CLIP_BOUNDS = new Property<View, Rect>(Rect.class) {
        public final Object get(Object obj) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            return ViewCompat.Api18Impl.getClipBounds((View) obj);
        }

        public final void set(Object obj, Object obj2) {
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api18Impl.setClipBounds((View) obj, (Rect) obj2);
        }
    };
    public static final ViewUtilsApi29 IMPL = new ViewUtilsApi29();
    public static final C04171 TRANSITION_ALPHA = new Property<View, Float>() {
        {
            Class<Float> cls = Float.class;
        }

        public final Object get(Object obj) {
            return Float.valueOf(((View) obj).getTransitionAlpha());
        }

        public final void set(Object obj, Object obj2) {
            ((View) obj).setTransitionAlpha(((Float) obj2).floatValue());
        }
    };
}
