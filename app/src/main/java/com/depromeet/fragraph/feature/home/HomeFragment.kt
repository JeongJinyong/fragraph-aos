package com.depromeet.fragraph.feature.home

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.fragraph.R
import com.depromeet.fragraph.base.SharedViewModel
import com.depromeet.fragraph.core.event.EventObserver
import com.depromeet.fragraph.core.ext.dpToPx
import com.depromeet.fragraph.core.ui.memo_dialog.MemoViewModel
import com.depromeet.fragraph.core.ui.select_dialog.*
import com.depromeet.fragraph.core.util.KeyboardHelper
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
import java.time.temporal.WeekFields
import java.util.*
import javax.inject.Inject

// Todo 안되있는 것 리스트

// 메모 비어보이게

// === Home
// 스크롤 잘 안되는 거 (후순위)

// === Meditation
// x 버튼 클릭 모달 -> 문구 수정될듯 !!!



@AndroidEntryPoint
class HomeFragment: Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    private val memoViewModel: MemoViewModel by viewModels()

    private val selectDialogViewModel: SelectDialogViewModel by viewModels()

    private val sharedViewModel: SharedViewModel by activityViewModels()

    lateinit var binding: FragmentHomeBinding

    lateinit var historyAdapter: HistoryRecyclerViewAdapter

    lateinit var historyCalenderBinding: ViewHistoryCalendarBinding
    private val today = LocalDate.now()
    private var year = LocalDate.now().year
    private var month = LocalDate.now().month.value
    private var selectedDate: LocalDate = LocalDate.now()
    lateinit var selectedCalendarDay: CalendarDay

    @Inject
    lateinit var inputMethodManager: InputMethodManager

    lateinit var keyboardHelper: KeyboardHelper

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.bind(view)
            .apply {
                vm = homeViewModel
                lifecycleOwner = this@HomeFragment
            }

        historyCalenderBinding = binding.viewReportHistoryCalender

        binding.viewSelectDialog.apply {
            vm = selectDialogViewModel
            lifecycleOwner = this@HomeFragment
        }

        val memoBinding = binding.viewMemoDialog.apply {
            vm = memoViewModel
            lifecycleOwner = this@HomeFragment
        }

        keyboardHelper = KeyboardHelper(binding.root) {
            val lp = memoBinding.root.layoutParams as ConstraintLayout.LayoutParams
            if (it > 0) {
                lp.bottomMargin = it + requireContext().dpToPx(16f).toInt()
            } else {
                lp.bottomMargin = resources.getDimensionPixelSize(R.dimen.memo_bottom_margin)
            }
            memoBinding.root.layoutParams = lp
        }


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
//        homeViewModel.refreshData()

        homeViewModel.openRecommendationEvent.observe(viewLifecycleOwner, EventObserver {
            goKeywordSelect()
        })

        homeViewModel.openSelectDialogEvent.observe(viewLifecycleOwner, EventObserver {
            selectDialogViewModel.setDialogType(it)
        })

        homeViewModel.openMemoDialogEvent.observe(viewLifecycleOwner, EventObserver {
            memoViewModel.setMemoDefault(it.first, it.second, it.third)
        })

        homeViewModel.homeViewModelToastEvent.observe(viewLifecycleOwner, EventObserver {
            sharedViewModel.showToastMessage(it)
        })

        memoViewModel.memoCloseEvent.observe(viewLifecycleOwner, EventObserver {
            inputMethodManager.hideSoftInputFromWindow(memoBinding.etMemoContent.windowToken, 0)
            homeViewModel.hideAllBackground()
        })

        memoViewModel.savedMemoEvent.observe(viewLifecycleOwner, EventObserver {
            homeViewModel.refreshEditedData()
        })

        memoViewModel.memoToastMessageEvent.observe(viewLifecycleOwner, EventObserver {
            sharedViewModel.showToastMessage(it)
        })

        selectDialogViewModel.onBtcClickEvent.observe(viewLifecycleOwner, EventObserver {
            when (it) {
                HOME_HISTORY_DELETE_CANCEL -> {
                    homeViewModel.hideAllBackground()
                }
                HOME_HISTORY_DELETE_CONFIRM -> {
                    homeViewModel.deleteHistory()
                }
                HOME_HISTORY_EDIT_CANCEL -> {
                    homeViewModel.hideAllBackground()
                }
                HOME_HISTORY_EDIT_CONFIRM -> {
                    homeViewModel.openMemoDialog()
                }
            }
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
                homeViewModel.refreshCalendarData(year, month, clickDay.day)
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

    override fun onDestroy() {
        keyboardHelper.dismiss()
        super.onDestroy()
    }
}