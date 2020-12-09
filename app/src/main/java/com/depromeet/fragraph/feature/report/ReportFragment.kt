package com.depromeet.fragraph.feature.report

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.databinding.FragmentReportBinding
import com.depromeet.fragraph.databinding.ViewHistoryCalendarBinding
import com.depromeet.fragraph.feature.report.adapter.recyclerview.HistoryRecyclerViewAdapter
import com.depromeet.fragraph.feature.report.adapter.recyclerview.HistoryRecyclerViewDecoration
import com.depromeet.fragraph.feature.report.adapter.recyclerview.HistoryRecyclerViewScrollListener
import com.depromeet.fragraph.feature.report.adapter.recyclerview.HistoryRecyclerViewSnapHelper
import com.depromeet.fragraph.feature.report.view.calendar.DayViewCalendarContainer
import com.depromeet.fragraph.feature.report.viewmodel.ReportViewModel
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import dagger.hilt.android.AndroidEntryPoint
import me.everything.android.ui.overscroll.OverScrollDecoratorHelper
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*

// Todo 안되있는 것 리스트

// api 연동
// 홈 화면에서 메모 클릭
// 스크롤 잘 안되는 거
// 향상을 더 하고싶은 경우
// 캘린더가 앞 뒤만 패딩
// 오늘 날짜만 폰트 칼라만 오렌지로
// 클릭 한 날짜도 폰트 오렌지
// 캘린더 좌측 정려

@AndroidEntryPoint
class ReportFragment: Fragment(R.layout.fragment_report) {

    private val reportViewModel: ReportViewModel by viewModels()

    lateinit var binding: FragmentReportBinding

    lateinit var historyAdapter: HistoryRecyclerViewAdapter

    lateinit var historyCalenderBinding: ViewHistoryCalendarBinding
    private val today = LocalDate.now()
    private var year = LocalDate.now().year
    private var month = LocalDate.now().month.value
    private val monthTitleFormatter = DateTimeFormatter.ofPattern("MMMM")
    private var selectedDate: LocalDate = LocalDate.now()
    lateinit var selectedCalendarDay: CalendarDay

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentReportBinding.bind(view)
            .apply {
                vm = reportViewModel
                lifecycleOwner = this@ReportFragment
            }

        historyCalenderBinding = binding.viewReportHistoryCalender

        // animation test
        val distance = 80000
        val scale: Float = resources.displayMetrics.density * distance
        val linearLayoutManager = LinearLayoutManager(
            requireContext(),
            RecyclerView.HORIZONTAL,
            false
        )
        historyAdapter = HistoryRecyclerViewAdapter(viewLifecycleOwner, scale, today.dayOfMonth) { position ->
            binding.rvHistories.scrollToPosition(position)
        } //.registerAdapterDataObserver(historyAdapter.HistoryRecyclerViewDataObserver())

        binding.rvHistories.apply {
            adapter = historyAdapter
            layoutManager = linearLayoutManager
            addItemDecoration(HistoryRecyclerViewDecoration())
            addOnScrollListener(
                HistoryRecyclerViewScrollListener(
                    linearLayoutManager,
                    historyAdapter,
                    2
                )
            )
            val snapHelper = HistoryRecyclerViewSnapHelper(this)
            snapHelper.attachToRecyclerView(this)
            OverScrollDecoratorHelper.setUpOverScroll(
                this,
                OverScrollDecoratorHelper.ORIENTATION_HORIZONTAL
            )
        }

        initCalender()

        reportViewModel.refreshData()

        reportViewModel.openRecommendationEvent.observe(viewLifecycleOwner, EventObserver {
            goKeywordSelect()
        })
    }

    private fun initCalender() {

        // setup
        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(10)
        val endMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        historyCalenderBinding.calendarViewHistory.setup(startMonth, endMonth, firstDayOfWeek)
        historyCalenderBinding.calendarViewHistory.scrollToMonth(currentMonth)

        // day binding
        historyCalenderBinding.calendarViewHistory.dayBinder = object : DayBinder<DayViewCalendarContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewCalendarContainer(view) { clickDay->
                historyAdapter.setLocaleDay(clickDay.day)
                reportViewModel.onCalendarClick(year, month, clickDay.day)
                selectedDate = clickDay.date
                historyCalenderBinding.calendarViewHistory.notifyDayChanged(clickDay)
                historyCalenderBinding.calendarViewHistory.notifyDayChanged(selectedCalendarDay)
            }

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewCalendarContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                textView.text = day.date.dayOfMonth.toString()
                if (day.owner == DayOwner.THIS_MONTH) {
                    when {
                        selectedDate == day.date -> {
                            selectedCalendarDay = day
                            textView.setBackgroundResource(R.drawable.bg_history_calender_select)
                            textView.setTextColor(requireContext().getColor(R.color.colorOrange))
                        }
                        today == day.date -> {
                            textView.background = null
                            textView.setTextColor(requireContext().getColor(R.color.colorOrange))
                        }
                        else -> {
                            textView.setTextColor(requireContext().getColor(R.color.colorBlackGray_1))
                            textView.background = null
                        }
                    }
                } else {
                    textView.setTextColor(requireContext().getColor(R.color.colorBlackGray_3))
                    textView.background = null
                }

                if (day.owner == DayOwner.THIS_MONTH) {
                    container.textView.setTextColor(requireContext().getColor(R.color.colorBlackGray_1))
                } else {
                    container.textView.setTextColor(requireContext().getColor(R.color.colorBlackGray_3))
                }
            }
        }

        // scroll listener
        historyCalenderBinding.calendarViewHistory.monthScrollListener = {
            historyCalenderBinding.tvHistoryCalenderYear.text = it.yearMonth.year.toString()
            historyCalenderBinding.tvHistoryCalenderMonth.text = monthTitleFormatter.format(it.yearMonth)
            year = it.yearMonth.year
            month = it.yearMonth.month.value
        }
    }

    private fun goKeywordSelect() {
        findNavController().navigate(R.id.action_reportFragment_to_keywordSelectFragment)
    }
}