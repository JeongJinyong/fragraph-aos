package com.depromeet.fragraph.feature.home.view.calendar

import android.view.View
import com.depromeet.fragraph.databinding.ItemHistoryCalendarBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewCalendarContainer constructor(
    view: View,
    dayClickCallback: (day: CalendarDay) -> Unit
): ViewContainer(view) {
    // Will be set when this container is bound. See the dayBinder.
    lateinit var day: CalendarDay
    val textView = ItemHistoryCalendarBinding.bind(view).tvHistoryCalenderDay

    init {
        view.setOnClickListener {
            if (day.owner == DayOwner.THIS_MONTH) {
                dayClickCallback(day)
            }
        }
    }
}