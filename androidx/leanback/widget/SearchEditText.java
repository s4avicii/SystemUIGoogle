package androidx.leanback.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import androidx.leanback.widget.SearchBar;
import java.util.Objects;

public class SearchEditText extends StreamingTextView {
    public OnKeyboardDismissListener mKeyboardDismissListener;

    public interface OnKeyboardDismissListener {
    }

    public SearchEditText(Context context) {
        this(context, (AttributeSet) null);
    }

    public SearchEditText(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 2132017941);
    }

    public SearchEditText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public final boolean onKeyPreIme(int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4 && this.mKeyboardDismissListener != null) {
            post(new Runnable() {
                public final void run() {
                    OnKeyboardDismissListener onKeyboardDismissListener = SearchEditText.this.mKeyboardDismissListener;
                    if (onKeyboardDismissListener != null) {
                        SearchBar.C02334 r0 = (SearchBar.C02334) onKeyboardDismissListener;
                        Objects.requireNonNull(r0);
                        Objects.requireNonNull(SearchBar.this);
                    }
                }
            });
        }
        return super.onKeyPreIme(i, keyEvent);
    }

    static {
        Class<SearchEditText> cls = SearchEditText.class;
    }
}
