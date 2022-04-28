package androidx.leanback.widget;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Property;
import android.view.ActionMode;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.EditText;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

@SuppressLint({"AppCompatCustomView"})
class StreamingTextView extends EditText {
    public static final Pattern SPLIT_PATTERN = Pattern.compile("\\S+");
    public static final C02401 STREAM_POSITION_PROPERTY = new Property<StreamingTextView, Integer>() {
        {
            Class<Integer> cls = Integer.class;
        }

        public final Object get(Object obj) {
            StreamingTextView streamingTextView = (StreamingTextView) obj;
            Objects.requireNonNull(streamingTextView);
            return Integer.valueOf(streamingTextView.mStreamPosition);
        }

        public final void set(Object obj, Object obj2) {
            StreamingTextView streamingTextView = (StreamingTextView) obj;
            int intValue = ((Integer) obj2).intValue();
            Objects.requireNonNull(streamingTextView);
            streamingTextView.mStreamPosition = intValue;
            streamingTextView.invalidate();
        }
    };
    public Bitmap mOneDot;
    public final Random mRandom = new Random();
    public int mStreamPosition;
    public ObjectAnimator mStreamingAnimation;
    public Bitmap mTwoDot;

    public StreamingTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public StreamingTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        Bitmap decodeResource = BitmapFactory.decodeResource(getResources(), C1777R.C1778drawable.lb_text_dot_one);
        this.mOneDot = Bitmap.createScaledBitmap(decodeResource, (int) (((float) decodeResource.getWidth()) * 1.3f), (int) (((float) decodeResource.getHeight()) * 1.3f), false);
        Bitmap decodeResource2 = BitmapFactory.decodeResource(getResources(), C1777R.C1778drawable.lb_text_dot_two);
        this.mTwoDot = Bitmap.createScaledBitmap(decodeResource2, (int) (((float) decodeResource2.getWidth()) * 1.3f), (int) (((float) decodeResource2.getHeight()) * 1.3f), false);
        this.mStreamPosition = -1;
        ObjectAnimator objectAnimator = this.mStreamingAnimation;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        setText("");
    }

    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName("androidx.leanback.widget.StreamingTextView");
    }

    public final void setCustomSelectionActionModeCallback(ActionMode.Callback callback) {
        super.setCustomSelectionActionModeCallback(callback);
    }
}
