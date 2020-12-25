package com.depromeet.fragraph.feature.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.databinding.FragmentHomeBinding
import com.depromeet.fragraph.databinding.ViewHistoryCalendarBinding
import com.depromeet.fragraph.feature.home.adapter.recyclerview.HistoryRecyclerViewAdapter
import com.depromeet.fragraph.feature.home.adapter.recyclerview.HistoryRecyclerViewDecoration
import com.depromeet.fragraph.feature.home.adapter.recyclerview.HistoryRecyclerViewScrollListener
import com.depromeet.fragraph.feature.home.adapter.recyclerview.HistoryRecyclerViewSnapHelper
import com.depromeet.fragraph.feature.home.view.calendar.DayViewCalendarContainer
import com.depromeet.fragraph.feature.home.viewmodel.HomeViewModel
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

// 스크롤 잘 안되는 거 (나중)
// 향상을 더 하고싶은 경우 (나중)

// 홈 화면에서 메모 클릭
// 글이 없으면 flip 이 되지 않는다.

// 메모 비어보이게
// 히스토리 뜰 때 관련 명상 향이면 bold 아니면 stroke 처리
// 메모 눌렀을 때 플로팅버튼 사라지도록 수정
// 향 선택 키워드 간격 정리 (슬라이드 가능하게)
// 뒤로가기 클릭시 (x) 버튼과 동일하게

// 바텀 네비? 다크톤으로
// 로그인 클릭시 lottie 애니메이션 재생

// === Home
// stroke 얇게

// === Keyword
// 바텀 아래에서 애니메이션

// === Meditation
// 배경화면 눌렀을 때는 모달말고 저장하기
// x 버튼 클릭 모달 -> 문구 수정될듯 !!!
// 마지막 향상 끝난 후 모달화면에서 취소버튼 삭제



@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    lateinit var binding: FragmentHomeBinding

    lateinit var historyAdapter: HistoryRecyclerViewAdapter

    lateinit var historyCalenderBinding: ViewHistoryCalendarBinding
    private val today = LocalDate.now()
    private var year = LocalDate.now().year
    private var month = LocalDate.now().month.value
    private var selectedDate: LocalDate = LocalDate.now()
    lateinit var selectedCalendarDay: CalendarDay

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
            .apply {
                vm = homeViewModel
                lifecycleOwner = this@HomeFragment
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
        }

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

        homeViewModel.refreshData()

        homeViewModel.openRecommendationEvent.observe(viewLifecycleOwner, EventObserver {
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
                homeViewModel.onCalendarClick(year, month, clickDay.day)
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
            }
        }

        // scroll listener
        historyCalenderBinding.calendarViewHistory.monthScrollListener = {
            historyCalenderBinding.tvHistoryCalenderYear.text = it.yearMonth.year.toString()
            historyCalenderBinding.tvHistoryCalenderMonth.text = "${it.yearMonth.monthValue} 월"
            year = it.yearMonth.year
            month = it.yearMonth.month.value
        }
    }

    private fun goKeywordSelect() {
        findNavController().navigate(R.id.action_homeFragment_to_keywordSelectFragment)
    }
}