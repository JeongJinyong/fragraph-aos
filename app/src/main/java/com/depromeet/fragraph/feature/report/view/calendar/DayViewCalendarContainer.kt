package com.depromeet.fragraph.feature.report.view.calendar

import android.view.View
import com.depromeet.fragraph.databinding.ItemHistoryCalendarBinding
import com.depromeet.fragraph.databinding.ViewHistoryCalendarBinding
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.ViewContainer
import timber.log.Timber
import java.time.LocalDate

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