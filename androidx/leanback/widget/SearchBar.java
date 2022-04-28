package androidx.leanback.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Handler;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.leanback.widget.SearchEditText;
import com.android.p012wm.shell.C1777R;
import java.util.Objects;

public class SearchBar extends RelativeLayout {
    public boolean mAutoStartRecognition;
    public int mBackgroundAlpha;
    public int mBackgroundSpeechAlpha;
    public Drawable mBarBackground;
    public int mBarHeight;
    public final Context mContext;
    public final Handler mHandler;
    public String mHint;
    public final InputMethodManager mInputMethodManager;
    public String mSearchQuery;
    public SearchEditText mSearchTextEditor;
    public SparseIntArray mSoundMap;
    public SoundPool mSoundPool;
    public SpeechOrbView mSpeechOrbView;
    public final int mTextColor;
    public final int mTextColorSpeechMode;
    public final int mTextHintColor;
    public final int mTextHintColorSpeechMode;

    public SearchBar(Context context) {
        this(context, (AttributeSet) null);
    }

    public SearchBar(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void onDetachedFromWindow() {
        this.mSoundPool.release();
        super.onDetachedFromWindow();
    }

    public final void setNextFocusDownId(int i) {
        this.mSpeechOrbView.setNextFocusDownId(i);
        this.mSearchTextEditor.setNextFocusDownId(i);
    }

    public final void updateUi(boolean z) {
        if (z) {
            this.mBarBackground.setAlpha(this.mBackgroundSpeechAlpha);
            if (this.mSpeechOrbView.isFocused()) {
                this.mSearchTextEditor.setTextColor(this.mTextHintColorSpeechMode);
                this.mSearchTextEditor.setHintTextColor(this.mTextHintColorSpeechMode);
            } else {
                this.mSearchTextEditor.setTextColor(this.mTextColorSpeechMode);
                this.mSearchTextEditor.setHintTextColor(this.mTextHintColorSpeechMode);
            }
        } else {
            this.mBarBackground.setAlpha(this.mBackgroundAlpha);
            this.mSearchTextEditor.setTextColor(this.mTextColor);
            this.mSearchTextEditor.setHintTextColor(this.mTextHintColor);
        }
        updateHint();
    }

    public SearchBar(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mHandler = new Handler();
        this.mAutoStartRecognition = false;
        this.mSoundMap = new SparseIntArray();
        this.mContext = context;
        Resources resources = getResources();
        LayoutInflater.from(getContext()).inflate(C1777R.layout.lb_search_bar, this, true);
        this.mBarHeight = getResources().getDimensionPixelSize(C1777R.dimen.lb_search_bar_height);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, this.mBarHeight);
        layoutParams.addRule(10, -1);
        setLayoutParams(layoutParams);
        setBackgroundColor(0);
        setClipChildren(false);
        this.mSearchQuery = "";
        this.mInputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        this.mTextColorSpeechMode = resources.getColor(C1777R.color.lb_search_bar_text_speech_mode);
        this.mTextColor = resources.getColor(C1777R.color.lb_search_bar_text);
        this.mBackgroundSpeechAlpha = resources.getInteger(C1777R.integer.lb_search_bar_speech_mode_background_alpha);
        this.mBackgroundAlpha = resources.getInteger(C1777R.integer.lb_search_bar_text_mode_background_alpha);
        this.mTextHintColorSpeechMode = resources.getColor(C1777R.color.lb_search_bar_hint_speech_mode);
        this.mTextHintColor = resources.getColor(C1777R.color.lb_search_bar_hint);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mSoundPool = new SoundPool(2, 1, 0);
        Context context = this.mContext;
        int[] iArr = {C1777R.raw.lb_voice_failure, C1777R.raw.lb_voice_open, C1777R.raw.lb_voice_no_input, C1777R.raw.lb_voice_success};
        for (int i = 0; i < 4; i++) {
            int i2 = iArr[i];
            this.mSoundMap.put(i2, this.mSoundPool.load(context, i2, 1));
        }
    }

    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mBarBackground = ((RelativeLayout) findViewById(C1777R.C1779id.lb_search_bar_items)).getBackground();
        this.mSearchTextEditor = (SearchEditText) findViewById(C1777R.C1779id.lb_search_text_editor);
        ImageView imageView = (ImageView) findViewById(C1777R.C1779id.lb_search_bar_badge);
        this.mSearchTextEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public final void onFocusChange(View view, boolean z) {
                if (z) {
                    SearchBar searchBar = SearchBar.this;
                    Objects.requireNonNull(searchBar);
                    searchBar.mHandler.post(new Runnable() {
                        public final void run() {
                            SearchBar.this.mSearchTextEditor.requestFocusFromTouch();
                            SearchBar.this.mSearchTextEditor.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 0, (float) SearchBar.this.mSearchTextEditor.getWidth(), (float) SearchBar.this.mSearchTextEditor.getHeight(), 0));
                            SearchBar.this.mSearchTextEditor.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), 1, (float) SearchBar.this.mSearchTextEditor.getWidth(), (float) SearchBar.this.mSearchTextEditor.getHeight(), 0));
                        }
                    });
                } else {
                    SearchBar searchBar2 = SearchBar.this;
                    Objects.requireNonNull(searchBar2);
                    searchBar2.mInputMethodManager.hideSoftInputFromWindow(searchBar2.mSearchTextEditor.getWindowToken(), 0);
                }
                SearchBar.this.updateUi(z);
            }
        });
        final C02312 r0 = new Runnable() {
            public final void run() {
                SearchBar searchBar = SearchBar.this;
                String obj = searchBar.mSearchTextEditor.getText().toString();
                Objects.requireNonNull(searchBar);
                if (!TextUtils.equals(searchBar.mSearchQuery, obj)) {
                    searchBar.mSearchQuery = obj;
                }
            }
        };
        this.mSearchTextEditor.addTextChangedListener(new TextWatcher() {
            public final void afterTextChanged(Editable editable) {
            }

            public final void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            public final void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                SearchBar searchBar = SearchBar.this;
                Objects.requireNonNull(searchBar);
                searchBar.mHandler.removeCallbacks(r0);
                SearchBar.this.mHandler.post(r0);
            }
        });
        SearchEditText searchEditText = this.mSearchTextEditor;
        C02334 r1 = new SearchEditText.OnKeyboardDismissListener() {
        };
        Objects.requireNonNull(searchEditText);
        searchEditText.mKeyboardDismissListener = r1;
        this.mSearchTextEditor.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (3 == i || i == 0) {
                    Objects.requireNonNull(SearchBar.this);
                }
                if (1 == i) {
                    Objects.requireNonNull(SearchBar.this);
                }
                if (2 != i) {
                    return false;
                }
                SearchBar searchBar = SearchBar.this;
                Objects.requireNonNull(searchBar);
                searchBar.mInputMethodManager.hideSoftInputFromWindow(searchBar.mSearchTextEditor.getWindowToken(), 0);
                SearchBar.this.mHandler.postDelayed(new Runnable() {
                    public final void run() {
                        SearchBar searchBar = SearchBar.this;
                        searchBar.mAutoStartRecognition = true;
                        searchBar.mSpeechOrbView.requestFocus();
                    }
                }, 500);
                return true;
            }
        });
        this.mSearchTextEditor.setPrivateImeOptions("escapeNorth,voiceDismiss");
        SpeechOrbView speechOrbView = (SpeechOrbView) findViewById(C1777R.C1779id.lb_search_bar_speech_orb);
        this.mSpeechOrbView = speechOrbView;
        C02366 r12 = new View.OnClickListener() {
            public final void onClick(View view) {
                SearchBar searchBar = SearchBar.this;
                Objects.requireNonNull(searchBar);
                if (!searchBar.hasFocus()) {
                    searchBar.requestFocus();
                }
            }
        };
        Objects.requireNonNull(speechOrbView);
        speechOrbView.mListener = r12;
        this.mSpeechOrbView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public final void onFocusChange(View view, boolean z) {
                if (z) {
                    SearchBar searchBar = SearchBar.this;
                    Objects.requireNonNull(searchBar);
                    searchBar.mInputMethodManager.hideSoftInputFromWindow(searchBar.mSearchTextEditor.getWindowToken(), 0);
                    SearchBar searchBar2 = SearchBar.this;
                    if (searchBar2.mAutoStartRecognition) {
                        if (!searchBar2.hasFocus()) {
                            searchBar2.requestFocus();
                        }
                        SearchBar.this.mAutoStartRecognition = false;
                    }
                } else {
                    Objects.requireNonNull(SearchBar.this);
                }
                SearchBar.this.updateUi(z);
            }
        });
        updateUi(hasFocus());
        updateHint();
    }

    public final void updateHint() {
        String string = getResources().getString(C1777R.string.lb_search_bar_hint);
        if (!TextUtils.isEmpty((CharSequence) null)) {
            if (this.mSpeechOrbView.isFocused()) {
                string = getResources().getString(C1777R.string.lb_search_bar_hint_with_title_speech, new Object[]{null});
            } else {
                string = getResources().getString(C1777R.string.lb_search_bar_hint_with_title, new Object[]{null});
            }
        } else if (this.mSpeechOrbView.isFocused()) {
            string = getResources().getString(C1777R.string.lb_search_bar_hint_speech);
        }
        this.mHint = string;
        SearchEditText searchEditText = this.mSearchTextEditor;
        if (searchEditText != null) {
            searchEditText.setHint(string);
        }
    }

    static {
        Class<SearchBar> cls = SearchBar.class;
    }
}
