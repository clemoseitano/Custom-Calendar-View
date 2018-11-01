/*
 * Copyright (c) 2016 Stacktips {link: http://stacktips.com}.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.stacktips.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.text.Spanned;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.imanoweb.calendarview.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayView extends LinearLayout {
    private Date date;
    private TextView mDayTextView;
    private TextView mEventHighlighterView;
    private View mTodayHighlighterView;
    private List<DayDecorator> decorators;
    private Context mContext;
    private View view;

    public DayView(Context context) {
        this(context, null, 0);
    }

    public DayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
            if (isInEditMode())
                return;
        }
        mContext = context;
        initialiseDayView();
    }

    /**
     * Initialise your views here. The default views are:
     * a {@link TextView} labeled as {@link #mDayTextView} with id of day_text, to display the day as a 2 digit number
     * a {@link TextView} labeled as {@link #mEventHighlighterView} with id of dot_text, to highlight the day with a dot
     * on the bottom.
     * a {@link View} labeled as {@link #mTodayHighlighterView} with id of underscore_text, to highlight today's view. You can
     * modify the color as you see fit with {@link #setUnderscoreColor(int)}.
     * You can always get the individual ids of the elements of this view to be modified in a
     * decorator instance, you can also hide and add more views to the layout instance.
     */
    private void initialiseDayView() {
        final LayoutInflater inflate = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflate.inflate(R.layout.custom_day_view, this, true);
        mDayTextView = view.findViewById(R.id.day_text);
        mEventHighlighterView = view.findViewById(R.id.dot_text);
        mTodayHighlighterView = view.findViewById(R.id.underscore_text);
        setAsPlainDay();
    }

    public void bind(Date date, List<DayDecorator> decorators) {
        this.date = date;
        this.decorators = decorators;

        final SimpleDateFormat df = new SimpleDateFormat("dd", Locale.getDefault());
        int day = Integer.parseInt(df.format(date));
        setText(String.valueOf(day));
    }

    public void setText(String s) {
        if (s.length() == 1)
            s = "0" + s;
        mDayTextView.setText(s);
    }

    public void decorate() {
        //Set custom decorators
        if (null != decorators) {
            for (DayDecorator decorator : decorators) {
                decorator.decorate(this);
            }
        }
    }

    public Date getDate() {
        return date;
    }

    public void setTypeface(Typeface customTypeface) {
        if (customTypeface != null)
            mDayTextView.setTypeface(customTypeface);
    }

    public void setTextColor(int dayOfWeekTextColor) {
        mDayTextView.setTextColor(dayOfWeekTextColor);
    }

    public void setDotColor(int dayOfWeekTextColor) {
        mEventHighlighterView.setTextColor(dayOfWeekTextColor);
    }

    public void setUnderscoreColor(int dayOfWeekTextColor) {
        mTodayHighlighterView.setBackgroundColor(dayOfWeekTextColor);
    }

    public void setAsPlainDay() {
        mTodayHighlighterView.setVisibility(INVISIBLE);
        mEventHighlighterView.setVisibility(INVISIBLE);
    }

    public void setAsSpecialDay() {
        mTodayHighlighterView.setVisibility(VISIBLE);
        mEventHighlighterView.setVisibility(VISIBLE);
    }

    public void setTextSize(int i) {
        mDayTextView.setTextSize(i);
    }

    public void setText(Spanned spanned) {
        mDayTextView.setText(spanned);
    }

    public CharSequence getText() {
        return mDayTextView.getText();
    }
}