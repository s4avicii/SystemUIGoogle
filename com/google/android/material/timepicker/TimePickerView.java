package com.google.android.material.timepicker;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Checkable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorCompat;
import com.android.p012wm.shell.C1777R;
import com.android.systemui.plugins.FalsingManager;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.chip.Chip;
import java.util.Objects;
import java.util.WeakHashMap;

class TimePickerView extends ConstraintLayout {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final C21301 selectionListener;
    public final MaterialButtonToggleGroup toggle;

    public TimePickerView(Context context) {
        this(context, (AttributeSet) null);
    }

    public TimePickerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public final void updateToggleConstraints() {
        boolean z;
        if (this.toggle.getVisibility() == 0) {
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(this);
            WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            char c = 1;
            if (ViewCompat.Api17Impl.getLayoutDirection(this) == 0) {
                z = true;
            } else {
                z = false;
            }
            if (z) {
                c = 2;
            }
            if (constraintSet.mConstraints.containsKey(Integer.valueOf(C1777R.C1779id.material_clock_display))) {
                ConstraintSet.Constraint constraint = constraintSet.mConstraints.get(Integer.valueOf(C1777R.C1779id.material_clock_display));
                switch (c) {
                    case 1:
                        ConstraintSet.Layout layout = constraint.layout;
                        layout.leftToRight = -1;
                        layout.leftToLeft = -1;
                        layout.leftMargin = -1;
                        layout.goneLeftMargin = -1;
                        break;
                    case 2:
                        ConstraintSet.Layout layout2 = constraint.layout;
                        layout2.rightToRight = -1;
                        layout2.rightToLeft = -1;
                        layout2.rightMargin = -1;
                        layout2.goneRightMargin = -1;
                        break;
                    case 3:
                        ConstraintSet.Layout layout3 = constraint.layout;
                        layout3.topToBottom = -1;
                        layout3.topToTop = -1;
                        layout3.topMargin = -1;
                        layout3.goneTopMargin = -1;
                        break;
                    case 4:
                        ConstraintSet.Layout layout4 = constraint.layout;
                        layout4.bottomToTop = -1;
                        layout4.bottomToBottom = -1;
                        layout4.bottomMargin = -1;
                        layout4.goneBottomMargin = -1;
                        break;
                    case 5:
                        constraint.layout.baselineToBaseline = -1;
                        break;
                    case FalsingManager.VERSION:
                        ConstraintSet.Layout layout5 = constraint.layout;
                        layout5.startToEnd = -1;
                        layout5.startToStart = -1;
                        layout5.startMargin = -1;
                        layout5.goneStartMargin = -1;
                        break;
                    case 7:
                        ConstraintSet.Layout layout6 = constraint.layout;
                        layout6.endToStart = -1;
                        layout6.endToEnd = -1;
                        layout6.endMargin = -1;
                        layout6.goneEndMargin = -1;
                        break;
                    default:
                        throw new IllegalArgumentException("unknown constraint");
                }
            }
            constraintSet.applyTo(this);
        }
    }

    public TimePickerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        C21301 r5 = new View.OnClickListener() {
            public final void onClick(View view) {
                TimePickerView timePickerView = TimePickerView.this;
                int i = TimePickerView.$r8$clinit;
                Objects.requireNonNull(timePickerView);
            }
        };
        this.selectionListener = r5;
        LayoutInflater.from(context).inflate(C1777R.layout.material_timepicker, this);
        ClockFaceView clockFaceView = (ClockFaceView) findViewById(C1777R.C1779id.material_clock_face);
        MaterialButtonToggleGroup materialButtonToggleGroup = (MaterialButtonToggleGroup) findViewById(C1777R.C1779id.material_clock_period_toggle);
        this.toggle = materialButtonToggleGroup;
        C21312 r6 = new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            public final void onButtonChecked() {
                TimePickerView timePickerView = TimePickerView.this;
                int i = TimePickerView.$r8$clinit;
                Objects.requireNonNull(timePickerView);
            }
        };
        Objects.requireNonNull(materialButtonToggleGroup);
        materialButtonToggleGroup.onButtonCheckedListeners.add(r6);
        Chip chip = (Chip) findViewById(C1777R.C1779id.material_minute_tv);
        Chip chip2 = (Chip) findViewById(C1777R.C1779id.material_hour_tv);
        ClockHandView clockHandView = (ClockHandView) findViewById(C1777R.C1779id.material_clock_hand);
        WeakHashMap<View, ViewPropertyAnimatorCompat> weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api19Impl.setAccessibilityLiveRegion(chip, 2);
        ViewCompat.Api19Impl.setAccessibilityLiveRegion(chip2, 2);
        final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            public final boolean onDoubleTap(MotionEvent motionEvent) {
                TimePickerView timePickerView = TimePickerView.this;
                int i = TimePickerView.$r8$clinit;
                Objects.requireNonNull(timePickerView);
                return false;
            }
        });
        C21334 r3 = new View.OnTouchListener() {
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                if (((Checkable) view).isChecked()) {
                    return gestureDetector.onTouchEvent(motionEvent);
                }
                return false;
            }
        };
        chip.setOnTouchListener(r3);
        chip2.setOnTouchListener(r3);
        chip.setTag(C1777R.C1779id.selection_type, 12);
        chip2.setTag(C1777R.C1779id.selection_type, 10);
        chip.setOnClickListener(r5);
        chip2.setOnClickListener(r5);
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        updateToggleConstraints();
    }

    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (view == this && i == 0) {
            updateToggleConstraints();
        }
    }
}
