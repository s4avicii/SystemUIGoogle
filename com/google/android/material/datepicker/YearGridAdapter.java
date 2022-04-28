package com.google.android.material.datepicker;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.p012wm.shell.C1777R;
import com.google.android.material.datepicker.MaterialCalendar;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public final class YearGridAdapter extends RecyclerView.Adapter<ViewHolder> {
    public final MaterialCalendar<?> materialCalendar;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView textView;

        public ViewHolder(TextView textView2) {
            super(textView2);
            this.textView = textView2;
        }
    }

    public final int getItemCount() {
        MaterialCalendar<?> materialCalendar2 = this.materialCalendar;
        Objects.requireNonNull(materialCalendar2);
        CalendarConstraints calendarConstraints = materialCalendar2.calendarConstraints;
        Objects.requireNonNull(calendarConstraints);
        return calendarConstraints.yearSpan;
    }

    public final void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        CalendarItemStyle calendarItemStyle;
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        MaterialCalendar<?> materialCalendar2 = this.materialCalendar;
        Objects.requireNonNull(materialCalendar2);
        CalendarConstraints calendarConstraints = materialCalendar2.calendarConstraints;
        Objects.requireNonNull(calendarConstraints);
        final int i2 = calendarConstraints.start.year + i;
        String string = viewHolder2.textView.getContext().getString(C1777R.string.mtrl_picker_navigate_to_year_description);
        viewHolder2.textView.setText(String.format(Locale.getDefault(), "%d", new Object[]{Integer.valueOf(i2)}));
        viewHolder2.textView.setContentDescription(String.format(string, new Object[]{Integer.valueOf(i2)}));
        MaterialCalendar<?> materialCalendar3 = this.materialCalendar;
        Objects.requireNonNull(materialCalendar3);
        CalendarStyle calendarStyle = materialCalendar3.calendarStyle;
        Calendar todayCalendar = UtcDates.getTodayCalendar();
        if (todayCalendar.get(1) == i2) {
            calendarItemStyle = calendarStyle.todayYear;
        } else {
            calendarItemStyle = calendarStyle.year;
        }
        MaterialCalendar<?> materialCalendar4 = this.materialCalendar;
        Objects.requireNonNull(materialCalendar4);
        for (Long longValue : materialCalendar4.dateSelector.getSelectedDays()) {
            todayCalendar.setTimeInMillis(longValue.longValue());
            if (todayCalendar.get(1) == i2) {
                calendarItemStyle = calendarStyle.selectedYear;
            }
        }
        calendarItemStyle.styleItem(viewHolder2.textView);
        viewHolder2.textView.setOnClickListener(new View.OnClickListener() {
            public final void onClick(View view) {
                int i = i2;
                MaterialCalendar<?> materialCalendar = YearGridAdapter.this.materialCalendar;
                Objects.requireNonNull(materialCalendar);
                Month create = Month.create(i, materialCalendar.current.month);
                MaterialCalendar<?> materialCalendar2 = YearGridAdapter.this.materialCalendar;
                Objects.requireNonNull(materialCalendar2);
                CalendarConstraints calendarConstraints = materialCalendar2.calendarConstraints;
                Objects.requireNonNull(calendarConstraints);
                if (create.firstOfMonth.compareTo(calendarConstraints.start.firstOfMonth) < 0) {
                    create = calendarConstraints.start;
                } else {
                    if (create.firstOfMonth.compareTo(calendarConstraints.end.firstOfMonth) > 0) {
                        create = calendarConstraints.end;
                    }
                }
                YearGridAdapter.this.materialCalendar.setCurrentMonth(create);
                YearGridAdapter.this.materialCalendar.setSelector(MaterialCalendar.CalendarSelector.DAY);
            }
        });
    }

    public YearGridAdapter(MaterialCalendar<?> materialCalendar2) {
        this.materialCalendar = materialCalendar2;
    }

    public final RecyclerView.ViewHolder onCreateViewHolder(RecyclerView recyclerView, int i) {
        return new ViewHolder((TextView) LayoutInflater.from(recyclerView.getContext()).inflate(C1777R.layout.mtrl_calendar_year, recyclerView, false));
    }
}
